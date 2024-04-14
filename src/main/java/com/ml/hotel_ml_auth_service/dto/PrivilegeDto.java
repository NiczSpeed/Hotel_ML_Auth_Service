package com.ml.hotel_ml_auth_service.dto;

import com.ml.hotel_ml_auth_service.model.Role;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PrivilegeDto {

    private UUID uuid;
    @NonNull
    private String name;
    private Collection<Role> roles;

}
