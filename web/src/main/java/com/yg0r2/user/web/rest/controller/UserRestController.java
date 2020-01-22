package com.yg0r2.user.web.rest.controller;

import com.yg0r2.user.api.model.UserEntry;
import com.yg0r2.user.service.UserService;
import com.yg0r2.user.service.exceptions.UserNotFoundException;
import com.yg0r2.user.web.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserTransformer userTransformer;

    @GetMapping
    public Set<UserEntry> getAll() {
        return userService.getAll().stream()
            .map(userTransformer::transform)
            .collect(Collectors.toSet());
    }

    @GetMapping(path = "/{id}")
    public UserEntry getById(@Min(0) @PathVariable long id) {
        return Optional.of(id)
            .map(userService::getById)
            .map(userTransformer::transform)
            .orElseThrow(() -> new UserNotFoundException(""));
    }

}
