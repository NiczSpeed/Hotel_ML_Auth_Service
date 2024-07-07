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
        JSONObject json = decodeMessage(message);
        UserDto userDto = new UserDto();
        userDto.setEmail(json.optString("email"));
        userDto.setPassword(json.optString("password"));
        userDto.setFirstName(json.optString("firstName"));
        userDto.setLastName(json.optString("lastName"));
        save(userDto);
    }

    public User save(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(RoleMapper.Instance.mapRoleSetToRoleDtoSet((roleRepository.findByName("USER"))));
        return userRepository.save(Instance.mapUserDtoToUser(userDto));
    }

    @KafkaListener(topics = "login_topic", groupId = "hotel_ml_auth_service")
    private void login(String message) throws UserNotFoundException {
        JSONObject json = decodeMessage(message);
        UserDto userDto = new UserDto();
        userDto.setEmail(json.optString("email"));
        userDto.setPassword(json.optString("password"));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
            String token = generateJwtToken(userDto.getEmail());
            sendJwtToken(token);
        } catch (Exception e) {
            sendJwtToken("Invalid username or password");
        }
    }

    private String sendJwtToken(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("jwt_topic", Base64.getEncoder().encodeToString(message.getBytes()));
        future.whenComplete((result, exception) -> {
            if (exception != null) logger.severe(exception.getMessage());
            else logger.info("Message sent successfully");
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

}
