package com.yg0r2.pet.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChildAppController {

    @RequestMapping("/api/asd")
    public ModelAndView index() {
        return new ModelAndView("asdqwe");
    }

}
