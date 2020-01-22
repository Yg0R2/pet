package com.yg0r2.pet.web.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        SecurityContextHolder.clearContext();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Unable to authenticate user.");
        response.flushBuffer();
    }

}
