package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

import static com.ml.hotel_ml_auth_service.mapper.UserMapper.Instance;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<User> getSomeUserDetails() {
        return userRepository.findAll();
    }

    @KafkaListener(topics = "register_topic", groupId = "hotel_ml_auth_service")
    public void test(String message) {
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

}
