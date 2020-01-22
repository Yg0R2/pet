package com.yg0r2.signin.web.rest.controller;

import com.yg0r2.signin.web.exceptions.UnableToSignInException;
import com.yg0r2.user.api.model.UserEntry;
import com.yg0r2.user.dao.model.UserEntity;
import com.yg0r2.user.service.UserService;
import com.yg0r2.user.web.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/sign-in")
public class SignInRestController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserTransformer userTransformer;

    @PostMapping
    public ResponseEntity<?> signIn(@Valid @RequestBody UserEntry userEntry) {
        Optional.of(userEntry)
            .map(userTransformer::transform)
            .map(this::encodeUserPassword)
            .map(userService::create)
            .orElseThrow(() -> new UnableToSignInException("Failed to sign in user."));

        return ResponseEntity.ok("User " + userEntry.getNickName() + " successfully created.");
    }

    private UserEntity encodeUserPassword(UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));

        return userEntity;
    }

}
