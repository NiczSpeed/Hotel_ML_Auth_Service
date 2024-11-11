package com.ml.hotel_ml_auth_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
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
