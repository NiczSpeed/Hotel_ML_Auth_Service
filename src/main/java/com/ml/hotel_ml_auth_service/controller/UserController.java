package com.ml.hotel_ml_auth_service.controller;

import com.ml.hotel_ml_auth_service.dto.RoleDto;
import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.service.RoleService;
import com.ml.hotel_ml_auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {


    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//    @GetMapping("/home")
//    public ResponseEntity<List<User>> getUserInfo (){
//        return new ResponseEntity<>(userService.getSomeUserDetails(), HttpStatus.OK);
//    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto, @RequestBody RoleDto roleDto) {
        roleService.save(roleDto);

        return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
    }

}
