//package com.ml.hotel_ml_auth_service.controller;
//
//import com.ml.hotel_ml_auth_service.dto.RoleDto;
//import com.ml.hotel_ml_auth_service.service.PrivilegeService;
//import com.ml.hotel_ml_auth_service.service.RoleService;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
//@RestController
//public class RoleController {
//
//    private final RoleService roleService;
//    private final PrivilegeService privilegeService;
//
//    @Autowired
//    public RoleController(RoleService roleService, PrivilegeService privilegeService) {
//        this.roleService = roleService;
//        this.privilegeService = privilegeService;
//    }
//
////    @Order(2)
////    @PostConstruct()
////    public void init() {
////        RoleDto roleDto = new RoleDto(UUID.randomUUID(), "USER", null, privilegeService.getPrivilegeByName("READ"));
////        roleService.save(roleDto);
////    }
//
//}
