package com.yg0r2.auth.web.security.jwt;

import com.yg0r2.auth.web.security.jwt.exceptions.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtClaimExtractor {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String extractSubject(String jwtToken) {
        return Optional.ofNullable(jwtToken)
            .map(this::parseToken)
            .map(Claims::getSubject)
            .orElseThrow(() -> new InvalidJwtTokenException("JWT token is invalid."));
    }

    private Claims parseToken(String jwtToken) {
        return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(jwtToken)
            .getBody();
    }

}
