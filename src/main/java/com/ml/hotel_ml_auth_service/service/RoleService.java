package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.RoleDto;
import com.ml.hotel_ml_auth_service.exception.RoleNotExistException;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.model.Role;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(RoleNotExistException::new);
    }
}
