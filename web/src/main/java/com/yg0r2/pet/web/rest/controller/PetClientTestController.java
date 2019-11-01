package com.yg0r2.pet.web.rest.controller;

import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.service.PetClientTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/api/test/client")
public class PetClientTestController {

    @Autowired
    private PetClientTestService petClientTestService;

    @GetMapping("/{id}")
    public PetEntry get(@PathVariable long id) {
        return petClientTestService.get(id);
    }

    @PostMapping
    public String generate(@QueryParam("amount") @Min(1) int amount) {
        for (int i = 0; i < amount; i++) {
            petClientTestService.create();
        }

        return "Success";
    }

}
