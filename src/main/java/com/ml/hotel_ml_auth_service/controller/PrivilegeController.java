//package com.ml.hotel_ml_auth_service.controller;
//
//import com.ml.hotel_ml_auth_service.dto.PrivilegeDto;
//import com.ml.hotel_ml_auth_service.dto.RoleDto;
//import com.ml.hotel_ml_auth_service.service.PrivilegeService;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
//@RestController
//public class PrivilegeController {
//
//    private final PrivilegeService privilegeService;
//
//    @Autowired
//    public PrivilegeController(PrivilegeService privilegeService) {
//        this.privilegeService = privilegeService;
//    }
//
////    @Order(1)
////    @PostConstruct()
////    public void init() {
////        PrivilegeDto privilegeDto = new PrivilegeDto(UUID.randomUUID(), "READ", null);
////        privilegeService.save(privilegeDto);
////    }
//
//}
