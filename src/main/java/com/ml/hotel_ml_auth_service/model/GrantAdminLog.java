package com.ml.hotel_ml_auth_service.model;

import com.ml.hotel_ml_auth_service.utils.converters.StringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "grantAdminLogs")
@Table(name = "GRANT_ADMIN_LOG")
public class GrantAdminLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "grantor")
    @Convert(converter = StringConverter.class)
    private String grantor;

    @Column(name = "grantee")
    @Convert(converter = StringConverter.class)
    private String grantee;

}
