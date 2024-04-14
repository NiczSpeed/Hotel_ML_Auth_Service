package com.ml.hotel_ml_auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private UUID uuid;
    private String name;
    private Collection<UserDto> users;
    private Collection<PrivilegeDto> privileges;

}
