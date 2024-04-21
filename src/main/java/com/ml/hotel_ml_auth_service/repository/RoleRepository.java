package com.ml.hotel_ml_auth_service.repository;

import com.ml.hotel_ml_auth_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Set<Role> findByName(String name);

}
