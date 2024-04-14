package com.ml.hotel_ml_auth_service.repository;

import com.ml.hotel_ml_auth_service.dto.PrivilegeDto;
import com.ml.hotel_ml_auth_service.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {

    Privilege findByName(String name);

}
