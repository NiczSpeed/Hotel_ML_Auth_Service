package com.ml.hotel_ml_auth_service.dto;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private String name;
    private Collection<UserDto> users;
    private Collection<PrivilegeDto> privileges;

}
