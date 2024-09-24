package com.ml.hotel_ml_auth_service.repository;

import com.ml.hotel_ml_auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
}
