package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public List<User> getSomeUserDetails(){
        return userRepository.findAll();
    }


    public User save(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userDto.setRoles(RoleMapper.Instance.mapRoleSetToRoleDtoSet((roleRepository.findByName("USER"))));
        return userRepository.save(Instance.mapUserDtoToUser(userDto));
    }
}
