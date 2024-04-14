package com.ml.hotel_ml_auth_service.dto;

import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class RoleDto {

    private UUID uuid;
    @NonNull
    private String name;
    private Collection<UserDto> users;
    private Collection<PrivilegeDto> privileges;

}
