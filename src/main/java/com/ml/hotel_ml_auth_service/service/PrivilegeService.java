package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.exception.PrivilegeNotExistException;
import com.ml.hotel_ml_auth_service.model.Privilege;
import com.ml.hotel_ml_auth_service.repository.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    public Privilege findByName(String name) {
        return privilegeRepository.findByName(name).orElseThrow(PrivilegeNotExistException::new);
    }
}
