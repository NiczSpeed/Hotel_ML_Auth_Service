package com.ml.hotel_ml_auth_service.dto;

import lombok.*;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private String name;
    private Set<UserDto> users;
    private Set<PrivilegeDto> privileges;

}
