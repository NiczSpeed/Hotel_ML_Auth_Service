package com.ml.hotel_ml_auth_service.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDetailsDto {

    private String email;
    private LocalDate creationDate;
    private String firstName;
    private String lastName;
    private String role;

}
