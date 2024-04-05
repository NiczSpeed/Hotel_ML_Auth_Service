package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.mapper.UserMapper;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getSomeUserDetails(){
        return userRepository.findAll();
    }


    public User save(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(UserMapper.Instance.userDtoToUser(userDto));
    }
}
