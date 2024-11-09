package com.ml.hotel_ml_auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GrantAdminLogDto {

    private String grantor;
    private String grantee;

}
