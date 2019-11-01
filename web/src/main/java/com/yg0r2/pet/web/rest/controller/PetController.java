package com.yg0r2.pet.web.rest.controller;

import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping(value = "/api/pet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetEntry createPetEntry(@RequestBody @Valid PetEntry petEntry) {
        return petService.create(petEntry);
    }

    @GetMapping
    public List<PetEntry> getPetEntries() {
        return petService.getAll();
    }

    @GetMapping("/{id}")
    public PetEntry getPetEntry(@PathVariable long id) {
        return petService.get(id);
    }

}
