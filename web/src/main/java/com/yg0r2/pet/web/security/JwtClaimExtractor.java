package com.yg0r2.pet.web.security;

import com.yg0r2.core.api.model.RequestParams;
import com.yg0r2.pet.web.security.exceptions.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class JwtClaimExtractor {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String extractSubject(HttpServletRequest request) {
        return getClaims(request).getSubject();
    }

    private Claims getClaims(HttpServletRequest request) {
        return Optional.ofNullable(request)
            .map(r -> r.getHeader(RequestParams.AUTHORIZATION.getValue()))
            .filter(jwtToken -> jwtToken.startsWith(JwtKeys.BEARER_PREFIX.getKey()))
            .map(jwtToken -> jwtToken.substring(JwtKeys.BEARER_PREFIX.getKey().length()))
            .map(this::parseToken)
            .orElseThrow(() -> new InvalidJwtTokenException("JWT token is invalid."));
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
    }

}
