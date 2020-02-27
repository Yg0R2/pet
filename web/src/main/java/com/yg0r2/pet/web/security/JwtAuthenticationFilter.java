package com.yg0r2.pet.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.core.api.model.RequestParams;
import com.yg0r2.pet.service.exceptions.PetServiceInternalException;
import com.yg0r2.user.api.model.UserEntry;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final AuthenticationManager authenticationManager;
    private final JwtTokenFactory jwtTokenFactory;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenFactory jwtTokenFactory) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenFactory = jwtTokenFactory;

        setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return Optional.of(request)
            .map(this::getUserEntry)
            .map(this::createAuthenticationToken)
            .map(authenticationManager::authenticate)
            .orElseThrow(() -> new InternalAuthenticationServiceException("Unable to authenticate user."));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setHeader(RequestParams.AUTHORIZATION.getValue(), jwtTokenFactory.create(((User) authResult.getPrincipal()).getUsername()));
    }

    private UserEntry getUserEntry(HttpServletRequest request) {
        try {
            return OBJECT_MAPPER.readValue(request.getInputStream(), UserEntry.class);
        }
        catch (IOException exception) {
            throw new PetServiceInternalException("Unable to parse request to UserEntity.");
        }
    }

    private AbstractAuthenticationToken createAuthenticationToken(UserEntry userEntry) {
        return new UsernamePasswordAuthenticationToken(userEntry.getNickName(), userEntry.getPassword(), List.of());
    }

}
