package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.PrivilegeDto;
import com.ml.hotel_ml_auth_service.exception.PrivilegeNotExistException;
import com.ml.hotel_ml_auth_service.mapper.PrivilegeMapper;
import com.ml.hotel_ml_auth_service.model.Privilege;
import com.ml.hotel_ml_auth_service.repository.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Transactional
    public Privilege findByName(String name) {
        Privilege privilege = privilegeRepository.findByName(name).orElseThrow(PrivilegeNotExistException::new);
        Hibernate.initialize(privilege.getRoles());
        return privilege;
    }
}
