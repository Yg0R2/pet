package com.yg0r2.auth.web.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JwtTokenValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenValidator.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    public boolean isValid(String jwtToken) {
        if (Objects.isNull(jwtToken) || jwtToken.isBlank()) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken);

            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
