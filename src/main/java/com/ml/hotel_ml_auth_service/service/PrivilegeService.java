package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.PrivilegeDto;
import com.ml.hotel_ml_auth_service.mapper.PrivilegeMapper;
import com.ml.hotel_ml_auth_service.model.Privilege;
import com.ml.hotel_ml_auth_service.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    protected Privilege save(PrivilegeDto privilegeDto) {
       return privilegeRepository.save(PrivilegeMapper.Instance.mapPrivilegeDtoToPrivilege(privilegeDto));
    }

}
