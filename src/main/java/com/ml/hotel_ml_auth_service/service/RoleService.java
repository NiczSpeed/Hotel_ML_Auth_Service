package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.RoleDto;
import com.ml.hotel_ml_auth_service.exception.RoleNotExistException;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.model.Role;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role findRoleByName(String name) {
        Role role = roleRepository.findByName(name).orElseThrow(RoleNotExistException::new);
        Hibernate.initialize(role.getUsers());
        Hibernate.initialize(role.getPrivileges());
        return role;
    }
}
