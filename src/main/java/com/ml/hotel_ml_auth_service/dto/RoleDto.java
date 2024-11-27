package com.ml.hotel_ml_auth_service.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class RoleDto {
    private UUID uuid;
    private String name;
    private Set<PrivilegeDto> privileges;
}
