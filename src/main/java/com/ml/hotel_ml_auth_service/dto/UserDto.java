package com.ml.hotel_ml_auth_service.dto;

import com.ml.hotel_ml_auth_service.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class UserDto {

    private UUID uuid;
    private String email;
    private String password;
    private Collection<Role> roles;
    private String firstName;
    private String lastName;
    private boolean isAccountNonExpired = true;
    private boolean isEnabled;

}
