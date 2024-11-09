package com.ml.hotel_ml_auth_service.repository;

import com.ml.hotel_ml_auth_service.model.GrantAdminLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GrantAdminLogRepository extends JpaRepository<GrantAdminLog, UUID> {
}
