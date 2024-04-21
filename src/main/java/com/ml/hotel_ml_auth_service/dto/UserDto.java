package com.ml.hotel_ml_auth_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private String password;
    private LocalDate creationDate = LocalDate.now();
    private Set<RoleDto> roles;
    private String firstName;
    private String lastName;
    private boolean isAccountNonExpired = true;
    private boolean isEnabled = true;
}
