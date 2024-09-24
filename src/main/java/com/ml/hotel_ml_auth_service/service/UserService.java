package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.exception.UserNotFoundException;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import static com.ml.hotel_ml_auth_service.mapper.UserMapper.Instance;

@Service
public class UserService {


    private final AuthenticationManager authenticationManager;

    Logger logger = Logger.getLogger(getClass().getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final KafkaTemplate kafkaTemplate;
    private final JwtGeneratorService jwtGeneratorService;


    @Autowired
    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, KafkaTemplate kafkaTemplate, JwtGeneratorService jwtGeneratorService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.jwtGeneratorService = jwtGeneratorService;
    }

    public List<User> getSomeUserDetails() {
        return userRepository.findAll();
    }

    @KafkaListener(topics = "register_topic", groupId = "hotel_ml_auth_service")
    private void saveUser(String message) {
        try {
            JSONObject json = decodeMessage(message);
            String messageId = json.optString("messageId");

            if (!checkIfUserExists(json.optString("email"))) {
                UserDto userDto = new UserDto();
                userDto.setEmail(json.optString("email"));
                userDto.setPassword(json.optString("password"));
                userDto.setFirstName(json.optString("firstName"));
                userDto.setLastName(json.optString("lastName"));
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
        userDto.setRoles(RoleMapper.Instance.mapRoleSetToRoleDtoSet((roleRepository.findByName("USER"))));
        return userRepository.save(Instance.mapUserDtoToUser(userDto));
    }

    @KafkaListener(topics = "login_topic", groupId = "hotel_ml_auth_service")
    private void login(String message) throws UserNotFoundException {
        JSONObject json = decodeMessage(message);
        String messageId = json.optString("messageId");
        UserDto userDto = new UserDto();
        userDto.setEmail(json.optString("email"));
        userDto.setPassword(json.optString("password"));
        try {
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

    @KafkaListener(topics = "user_details_topic", groupId = "hotel_ml_auth_service")
    private void userDetails(String message) throws UserNotFoundException {
        try {
            JSONObject json = decodeMessage(message);
            String messageId = json.optString("messageId");
            UserDto userDto = Instance.mapUserToUserDto(userRepository.findUserByEmail(json.optString("email")));
            userDto.setPassword("");
            JSONObject userDetailsJson = new JSONObject(userDto);
            logger.info("Data was sent!");
            logger.severe(userDetailsJson.toString());
            sendEncodedMessage(userDetailsJson.toString(), messageId, "user_details_request_topic");
        } catch (Exception e) {
            logger.severe("Error while saving user: " + e.getMessage());
        }
    }

    private String sendEncodedMessage(String message, String messageId, String topic) {
        JSONObject json = new JSONObject();
        json.put("messageId", messageId);
        json.put("message", message);
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


}
