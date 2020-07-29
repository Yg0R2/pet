package com.yg0r2.auth.web.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtAccessTokenFactory {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expirationDuration}")
    private Duration expirationDuration;

    public String create(String subject) {
        String token = Jwts.builder()
            .setSubject(subject)
            .setExpiration(new Date(System.currentTimeMillis() + expirationDuration.toMillis()))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

        return JwtKeys.BEARER_PREFIX.getValue() + token;
    }

}
