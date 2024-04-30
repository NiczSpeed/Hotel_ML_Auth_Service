package com.ml.hotel_ml_auth_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class RoleDto {
    private UUID uuid;
    private String name;
    private Set<PrivilegeDto> privileges;
}
