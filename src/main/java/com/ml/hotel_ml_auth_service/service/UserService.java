package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.GrantAdminLogDto;
import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.dto.UserResponseDetailsDto;
import com.ml.hotel_ml_auth_service.exception.UserNotFoundException;
import com.ml.hotel_ml_auth_service.mapper.GrantAdminLogMapper;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.model.GrantAdminLog;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.repository.GrantAdminLogRepository;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import static com.ml.hotel_ml_auth_service.mapper.UserMapper.Instance;

@Service
@RequiredArgsConstructor
public class UserService {


    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    Logger logger = Logger.getLogger(getClass().getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate kafkaTemplate;
    private final JwtGeneratorService jwtGeneratorService;
    private final GrantAdminLogRepository grantAdminLogRepository;

    @KafkaListener(topics = "register_topic", groupId = "hotel_ml_auth_service")
    private void saveUser(String message) {
        try {
            JSONObject json = decodeMessage(message);
            String messageId = json.optString("messageId");

            if (!checkIfUserExists(json.optString("email"))) {
//                UserDto userDto = new UserDto();
//                userDto.setEmail(json.optString("email"));
//                userDto.setPassword(json.optString("password"));
//                userDto.setFirstName(json.optString("firstName"));
//                userDto.setLastName(json.optString("lastName"));
                UserDto userDto = UserDto.builder()
                        .email(json.optString("email"))
                        .password(json.optString("password"))
                        .firstName(json.optString("firstName"))
                        .lastName(json.optString("lastName"))
                        .build();
                save(userDto);
                logger.info("User saved: " + userDto);
                sendRequestMessage("User Successfully added!", messageId, "success_request_topic");
            } else {
                logger.info("User already exists!");
                sendRequestMessage("User already Exist!!", messageId, "error_request_topic");
            }
        } catch (Exception e) {
            logger.severe("Error while saving user: " + e.getMessage());
        }
    }

    public User save(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(RoleMapper.Instance.mapRoleSetToRoleDtoSet((Set.of(roleService.findRoleByName("USER")))));
        return userRepository.save(Instance.mapUserDtoToUser(userDto));
    }

    @KafkaListener(topics = "login_topic", groupId = "hotel_ml_auth_service")
    private void login(String message) throws UserNotFoundException {
        try {
            JSONObject json = decodeMessage(message);
            String messageId = json.optString("messageId");
//            UserDto userDto = new UserDto();
            UserDto userDto = UserDto.builder()
                    .email(json.optString("email"))
                    .password(json.optString("password"))
                    .build();
            userDto.setEmail(json.optString("email"));
            userDto.setPassword(json.optString("password"));
            if (isUserAuthenticated(userDto.getEmail(), userDto.getPassword())) {
                String token = generateJwtToken(userDto.getEmail());
                sendEncodedMessage(token, messageId, "jwt_topic");
                logger.info("User successfully logged in, token was send!");
            } else {
                sendRequestMessage("Invalid username or password!", messageId, "error_request_topic");
            }
        } catch (Exception e) {
            logger.severe("Error while saving user: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "grant_admin_topic", groupId = "hotel_ml_auth_service")
    private void grantAdmin(String message) throws UserNotFoundException {
        try {
            JSONObject json = decodeMessage(message);
            String messageId = json.optString("messageId");
            User grantee = userRepository.findUserByEmail(json.optString("granteeEmail"));
            if (grantee == null) {
                sendRequestMessage("Error:User with such an email address does not exist!", messageId, "error_request_topic");
                logger.severe("User with such an email address does not exist!");
            } else if (grantee.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
                sendRequestMessage("Error:User already has Admin role!", messageId, "error_request_topic");
                logger.severe("User already has Admin role!");
            } else {
                grantee.setRoles(Set.of(roleService.findRoleByName("ADMIN")));
                userRepository.save(grantee);
                GrantAdminLogDto grantAdminLogDto = GrantAdminLogDto.builder()
                        .grantor(json.optString("grantorEmail"))
                        .grantee(json.optString("granteeEmail"))
                        .build();
                GrantAdminLog grantAdminLog = GrantAdminLogMapper.Instance.mapGrantAdminLogDtoToGrantAdminLog(grantAdminLogDto);
                grantAdminLogRepository.save(grantAdminLog);
                sendRequestMessage("Admin role was successfully granted!", messageId, "success_request_topic");
                logger.info("Admin role was successfully granted!");
            }
        } catch (Exception e) {
            logger.severe("Error while saving user: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "user_details_topic", groupId = "hotel_ml_auth_service")
    private void userDetails(String message) throws UserNotFoundException {
        try {
            JSONObject json = decodeMessage(message);
            String messageId = json.optString("messageId");
            User user = userRepository.findUserByEmail(json.optString("email"));
            UserResponseDetailsDto userResponseDetailsDto = Instance.mapUserToUserResponseDetailsDto(user);
            JSONObject userDetailsJson = new JSONObject(userResponseDetailsDto);
            logger.severe(userDetailsJson.toString());
            logger.info("Data was sent!");
            sendEncodedMessage(userDetailsJson.toString(), messageId, "user_details_request_topic");
        } catch (Exception e) {
            logger.severe("Error while saving user: " + e.getMessage());
        }
    }

    private String sendEncodedMessage(String message, String messageId, String topic) {
        JSONObject json = new JSONObject();
        json.put("messageId", messageId);
        if (message.contains("{")) {
            JSONObject messageJson = new JSONObject(message);
            json.put("message", messageJson);
        } else {
            json.put("message", message);
        }
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, Base64.getEncoder().encodeToString(json.toString().getBytes()));
        future.whenComplete((result, exception) -> {
            if (exception != null) logger.severe(exception.getMessage());
            else logger.info("Message send successfully!");
        });
        return message;
    }

    private String generateJwtToken(String email) {
        return jwtGeneratorService.generateToken(email);
    }

    private void validateJwtToken(String jwtToken) {
        jwtGeneratorService.validateToken(jwtToken);
    }

    private JSONObject decodeMessage(String message) {
        byte[] decodedBytes = Base64.getDecoder().decode(message);
        message = new String(decodedBytes);
        return new JSONObject(message);
    }

    boolean checkIfUserExists(String email) {
        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private String sendRequestMessage(String message, String messageId, String topic) {
        JSONObject json = new JSONObject();
        json.put("messageId", messageId);
        json.put("message", message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, json.toString());
        future.whenComplete((result, exception) -> {
            if (exception != null) logger.severe(exception.getMessage());
            else logger.info("Message send successfully!");
        });
        return message;
    }

    private boolean isUserAuthenticated(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return authentication.isAuthenticated();
        } catch (Exception e) {
            return false;
        }


    }

//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
//    }

}
