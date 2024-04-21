package com.ml.hotel_ml_auth_service.controller;

import com.ml.hotel_ml_auth_service.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivilegeController {

    @Autowired
    public PrivilegeController(PrivilegeService privilegeService) {
    }

}
