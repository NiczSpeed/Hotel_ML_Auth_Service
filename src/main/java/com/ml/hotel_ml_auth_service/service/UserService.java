package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.exception.UserNotFoundException;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.ml.hotel_ml_auth_service.mapper.UserMapper.Instance;

@Service
public class UserService {


    private final AuthenticationManager authenticationManager;

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
    public void saveUser(String message) {
        byte[] decodedBytes = Base64.getDecoder().decode(message);
        message = new String(decodedBytes);
        JSONObject json = new JSONObject(message);
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
    public void login(String message) throws UserNotFoundException {
        byte[] decodedBytes = Base64.getDecoder().decode(message);
        message = new String(decodedBytes);
        JSONObject json = new JSONObject(message);
        UserDto userDto = new UserDto();
        userDto.setEmail(json.optString("email"));
        userDto.setPassword(json.optString("password"));
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        if(auth.isAuthenticated()) {
            String token = generateJwtToken(userDto.getEmail());
            sendJwtToken(token);
        }
        else{throw new ResponseStatusException(HttpStatus.FORBIDDEN);}
    }

    public String sendJwtToken(String message){
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("jwt_topic",Base64.getEncoder().encodeToString(message.getBytes()));
        future.whenComplete((result, exception) -> {
            if (exception != null) System.out.println(exception.getMessage());
            else System.out.println("Message sent successfully");
        });
        return message;
    }

    public String generateJwtToken(String email) {
        return jwtGeneratorService.generateToken(email);
    }

    public void validateJwtToken(String jwtToken) {
        jwtGeneratorService.validateToken(jwtToken);
    }

}
