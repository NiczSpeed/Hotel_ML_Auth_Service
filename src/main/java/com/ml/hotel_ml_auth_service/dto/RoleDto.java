package com.ml.hotel_ml_auth_service.dto;

import com.ml.hotel_ml_auth_service.model.Privilege;
import com.ml.hotel_ml_auth_service.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class RoleDto {

    private UUID uuid;
    private String name;
    private Collection<UserDto> users;
    private Collection<PrivilegeDto> privileges;

}
