package com.ml.hotel_ml_auth_service.repository;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends UserDetailsService {

}
