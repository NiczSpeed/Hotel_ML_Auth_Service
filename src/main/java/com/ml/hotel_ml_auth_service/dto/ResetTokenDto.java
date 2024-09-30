package com.ml.hotel_ml_auth_service.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder
public class ResetTokenDto {

    private String email;
    private String token;
    private LocalDateTime expiration;

}
