package com.ml.hotel_ml_auth_service.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private LocalDate creationDate;
    private Set<RoleDto> roles;
    private String firstName;
    private String lastName;
    private Boolean isAccountNonExpired;
    private Boolean isEnabled;
}
