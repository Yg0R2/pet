package com.yg0r2.auth.web.security.jwt;

import com.yg0r2.core.api.model.HeaderParams;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class JwtTokenExtractor {

    public String extract(HttpServletRequest request) {
        return Optional.ofNullable(request)
            .map(r -> r.getHeader(HeaderParams.AUTHORIZATION.getValue()))
            .filter(accessToken -> accessToken.startsWith(JwtKeys.BEARER_PREFIX.getValue()))
            .map(accessToken -> accessToken.substring(JwtKeys.BEARER_PREFIX.getValue().length()))
            .orElse(null);
    }

}
