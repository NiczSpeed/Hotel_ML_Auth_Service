package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.RoleDto;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.model.Role;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(RoleDto roleDto) {
       return roleRepository.save(RoleMapper.Instance.mapRoleDtoToRole(roleDto));
    }


}
