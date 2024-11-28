package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.GrantAdminLogDto;
import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.dto.UserResponseDetailsDto;
import com.ml.hotel_ml_auth_service.exception.ErrorWhileEncodeException;
import com.ml.hotel_ml_auth_service.exception.UserNotFoundException;
import com.ml.hotel_ml_auth_service.mapper.GrantAdminLogMapper;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.model.GrantAdminLog;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.repository.GrantAdminLogRepository;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import com.ml.hotel_ml_auth_service.utils.EncryptorUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final EncryptorUtil encryptorUtil;
    private final UserDetailsService userDetailsService;

    @KafkaListener(topics = "register_topic", groupId = "hotel_ml_auth_service")
    private void saveUser(String message) {
        try {
            String decodedMessage = encryptorUtil.decrypt(message);
            JSONObject json = new JSONObject(decodedMessage);
            JSONObject jsonMessage = json.getJSONObject("message");
            String messageId = json.optString("messageId");
            String email = jsonMessage.optString("email");

            if (userRepository.findAll().stream().noneMatch(user -> email.equals(user.getEmail()))) {
                UserDto userDto = UserDto.builder().email(jsonMessage.optString("email")).creationDate(LocalDate.now()).isEnabled(true).isAccountNonExpired(true).password(passwordEncoder.encode(jsonMessage.optString("password"))).roles(RoleMapper.Instance.mapRoleSetToRoleDtoSet((Set.of(roleService.findRoleByName("USER"))))).firstName(jsonMessage.optString("firstName")).lastName(jsonMessage.optString("lastName")).build();
                User user = Instance.mapUserDtoToUser(userDto);
                userRepository.save(user);
                sendRequestMessage("User Successfully added.", messageId, "success_request_topic");
                logger.info("User Successfully added:Message was sent.");
            } else {
                sendRequestMessage("Error:User already Exist!", messageId, "error_request_topic");
                logger.severe("Error:User already exists!");
            }
        } catch (Exception e) {
            logger.severe("Error while saving user: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "login_topic", groupId = "hotel_ml_auth_service")
    protected void login(String message) throws UserNotFoundException {
        try {
            String decodedMessage = encryptorUtil.decrypt(message);
            JSONObject json = new JSONObject(decodedMessage);
            JSONObject jsonMessage = json.getJSONObject("message");
            String messageId = json.optString("messageId");
            UserDto userDto = UserDto.builder().email(jsonMessage.optString("email")).password(jsonMessage.optString("password")).build();
            if (isUserAuthenticated(userDto.getEmail(), userDto.getPassword())) {
                String token = generateJwtToken(userDto.getEmail());
                sendEncodedMessage(token, messageId, "jwt_topic");
                logger.info("User successfully logged in, token was send:Message was sent.");
            } else {
                sendRequestMessage("Error:Invalid username or password!", messageId, "error_request_topic");
                logger.severe("Error:Invalid username or password!");
            }
        } catch (Exception e) {
            logger.severe("Error while login user: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "update_user_topic", groupId = "hotel_ml_auth_service")
    protected void updateUser(String message) throws UserNotFoundException {
        try {
            String decodedMessage = encryptorUtil.decrypt(message);
            JSONObject json = new JSONObject(decodedMessage);
            JSONObject jsonMessage = json.getJSONObject("message");
            String messageId = json.optString("messageId");
            User user = userRepository.findUserByEmail(jsonMessage.optString("currentEmail"));
            if (user == null) {
                sendRequestMessage("Error:User with such an email address does not exist!", messageId, "error_request_topic");
                logger.severe("Error:User with such an email address does not exist!");
            } else {
                if (userRepository.findUserByEmail(jsonMessage.optString("email")) != null && userRepository.findUserByEmail(jsonMessage.optString("email")).getEmail().equals(jsonMessage.optString("currentEmial"))) {
                    sendRequestMessage("Error:This email address is already taken!", messageId, "error_request_topic");
                    logger.severe("Error:This email address is already taken!");
                } else {
                    if (jsonMessage.has("email")) user.setEmail(jsonMessage.optString("email"));
                    if (jsonMessage.has("password"))
                        user.setPassword(passwordEncoder.encode(jsonMessage.optString("password")));
                    if (jsonMessage.has("firstName")) user.setFirstName(jsonMessage.optString("firstName"));
                    if (jsonMessage.has("lastName")) user.setLastName(jsonMessage.optString("lastName"));
                    userRepository.save(user);
                    refreshAuthentication(user.getEmail());
                    sendRequestMessage("Your account has been successfully updated!", messageId, "success_request_topic");
                    logger.info("Your account has been successfully updated:Message was sent.");
                }

            }
        } catch (Exception e) {
            logger.severe("Error while saving user: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "grant_admin_topic", groupId = "hotel_ml_auth_service")
    private void grantAdmin(String message) throws UserNotFoundException {
        try {
            String decodedMessage = encryptorUtil.decrypt(message);
            JSONObject json = new JSONObject(decodedMessage);
            JSONObject jsonMessage = json.getJSONObject("message");
            String messageId = json.optString("messageId");
            User grantee = userRepository.findUserByEmail(jsonMessage.optString("granteeEmail"));
            if (grantee == null) {
                sendRequestMessage("Error:User with such an email address does not exist!", messageId, "error_request_topic");
                logger.severe("Error:User with such an email address does not exist!");
            } else if (grantee.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
                sendRequestMessage("Error:User already has Admin role!", messageId, "error_request_topic");
                logger.severe("Error:User already has Admin role!");
            } else {
                grantee.setRoles(Set.of(roleService.findRoleByName("ADMIN")));
                userRepository.save(grantee);
                GrantAdminLogDto grantAdminLogDto = GrantAdminLogDto.builder().grantor(jsonMessage.optString("grantorEmail")).grantee(jsonMessage.optString("granteeEmail")).build();
                GrantAdminLog grantAdminLog = GrantAdminLogMapper.Instance.mapGrantAdminLogDtoToGrantAdminLog(grantAdminLogDto);
                grantAdminLogRepository.save(grantAdminLog);
                sendRequestMessage("Admin role was successfully granted!", messageId, "success_request_topic");
                logger.info("Admin role was successfully granted:Message was sent.");
            }
        } catch (Exception e) {
            logger.severe("Error while saving user: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "user_details_topic", groupId = "hotel_ml_auth_service")
    private void userDetails(String message) throws UserNotFoundException {
        try {
            String decodedMessage = encryptorUtil.decrypt(message);
            JSONObject json = new JSONObject(decodedMessage);
            JSONObject jsonMessage = json.getJSONObject("message");
            String messageId = json.optString("messageId");
            User user = userRepository.findUserByEmail(jsonMessage.optString("email"));
            UserResponseDetailsDto userResponseDetailsDto = Instance.mapUserToUserResponseDetailsDto(user);
            JSONObject userDetailsJson = new JSONObject(userResponseDetailsDto);
            sendEncodedMessage(userDetailsJson.toString(), messageId, "user_details_request_topic");
            logger.info("User details were successfully updated:Message was sent.");
        } catch (Exception e) {
            logger.severe("Error while saving user: " + e.getMessage());
        }
    }

    private void sendEncodedMessage(String message, String messageId, String topic) {
        try {
            JSONObject json = new JSONObject();
            json.put("messageId", messageId);
            if (message != null) {
                switch (message) {
                    case String s when s.contains("[") -> json.put("message", new JSONArray(s));
                    case String s when s.contains("{") -> json.put("message", new JSONObject(s));
                    default -> json.put("message", message);
                }
            }
            String encodedMessage = encryptorUtil.encrypt(json.toString());
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, encodedMessage);
            future.whenComplete((result, exception) -> {
                if (exception != null) logger.severe(exception.getMessage());
            });
        } catch (Exception e) {
            throw new ErrorWhileEncodeException();
        }
    }

    private void refreshAuthentication(String email) {
        UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(email);
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }

    private String generateJwtToken(String email) {
        return jwtGeneratorService.generateToken(email);
    }

    private void sendRequestMessage(String message, String messageId, String topic) {
        JSONObject json = new JSONObject();
        json.put("messageId", messageId);
        json.put("message", message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, json.toString());
        future.whenComplete((result, exception) -> {
            if (exception != null) logger.severe(exception.getMessage());
        });
    }

    private boolean isUserAuthenticated(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return authentication.isAuthenticated();
        } catch (Exception e) {
            return false;
        }
    }
}
