package com.ml.hotel_ml_auth_service.controller;

import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/home")
//    public ResponseEntity<List<User>> getUserInfo (){
//        return new ResponseEntity<>(userService.getSomeUserDetails(), HttpStatus.OK);
//    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
    }

}
