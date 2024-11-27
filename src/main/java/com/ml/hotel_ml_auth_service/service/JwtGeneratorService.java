package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.exception.UserNotFoundException;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.*;

@Service
public class JwtGeneratorService implements Serializable {


    private final String secretKey;
    private final long jwtExpiration;
    private final UserRepository userRepository;

    public JwtGeneratorService(
            @Value(value = "${security.jwt.secret.key}") String secretKey,
            @Value(value = "${security.jwt.expiration.time}") long jwtExpiration,
            UserRepository userRepository) {
        this.secretKey = secretKey;
        this.jwtExpiration = jwtExpiration;
        this.userRepository = userRepository;
    }


    public Jws<Claims> validateToken(final String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        Set<String> roles = new HashSet<>();
        User user = userRepository.findUserByEmail(email);
        user.getRoles().forEach(role -> roles.add(role.getName()));
        claims.put("roles", roles);
        return createToken(claims, email);
    }

    public String createToken(Map<String, Object> claims, String email) {
        return Jwts
                .builder()
                .subject(email)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String encodeData(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

}
