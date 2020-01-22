package com.yg0r2.pet.web.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenFactory {

    private static final Duration EXPIRATION_DURATION = Duration.ofMinutes(5);

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String create(String subject) {
        String token = Jwts.builder()
            .setSubject(subject)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DURATION.toMillis()))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

        return JwtKeys.BEARER_PREFIX.getKey() + token;
    }

}
