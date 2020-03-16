package com.yg0r2.pet.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/hello")
public class DummyController {

    @GetMapping
    public String hello() {
        return "hello";
    }

}
