package com.ml.hotel_ml_auth_service.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity(name = "grantAdminLogs")
@Table(name = "GRANT_ADMIN_LOG")
public class GrantAdminLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "grantor")
    private String grantor;

    @Column(name = "grantee")
    private String grantee;

}
