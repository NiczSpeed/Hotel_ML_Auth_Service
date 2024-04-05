package com.ml.hotel_ml_auth_service.dto;

import com.ml.hotel_ml_auth_service.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class PrivilegeDto {

    private UUID uuid;
    private String name;
    private Collection<Role> roles;

}
