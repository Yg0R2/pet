package com.yg0r2.auth.web.rest.controller;

import com.yg0r2.auth.api.model.SignInRequest;
import com.yg0r2.auth.api.model.SignInResponse;
import com.yg0r2.auth.web.security.jwt.JwtAccessTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SignInRestController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtAccessTokenFactory jwtAccessTokenFactory;

    @PostMapping
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(createAuthentication(signInRequest));

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        return ResponseEntity
            .ok(createSignInResponse((UserDetails) authentication.getPrincipal()));
    }

    private SignInResponse createSignInResponse(UserDetails userDetails) {
        return new SignInResponse.Builder()
            .withAccessToken(jwtAccessTokenFactory.create(userDetails.getUsername()))
            .withSessionId(RequestContextHolder.currentRequestAttributes().getSessionId())
            .withUserName(userDetails.getUsername())
            .build();
    }

    private Authentication createAuthentication(SignInRequest signInRequest) {
        return new UsernamePasswordAuthenticationToken(signInRequest.getUserName(), signInRequest.getPassword());
    }

}
