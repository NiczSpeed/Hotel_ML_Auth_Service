package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.model.MyUserDetails;
import com.ml.hotel_ml_auth_service.repository.UserDetailsRepository;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements UserDetailsRepository {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

}
