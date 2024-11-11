package com.ml.hotel_ml_auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private LocalDate creationDate = LocalDate.now();
    private Set<RoleDto> roles;
    private String firstName;
    private String lastName;
    private Boolean isAccountNonExpired = true;
    private Boolean isEnabled = true;
}
