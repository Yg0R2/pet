package com.yg0r2.pet.web.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello")
public class DummyRestController {

    @GetMapping
    public String hello() {
        return "Hello World";
    }

}
