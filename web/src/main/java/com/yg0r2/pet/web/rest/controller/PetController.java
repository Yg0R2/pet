package com.yg0r2.pet.web.rest.controller;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.service.PetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping(value = "/api/pet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PetController extends AbstractPetServiceController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetEntry createPetEntry(@Valid @RequestBody PetEntry petEntry, @Valid @ModelAttribute RequestContext requestContext) {
        return petService.create(petEntry);
    }

    @GetMapping
    public List<PetEntry> getPetEntries(@Valid @ModelAttribute RequestContext requestContext) {
        return petService.getAll();
    }

    @GetMapping("/{id}")
    public PetEntry getPetEntry(@PathVariable @Min(1) long id, @Valid @ModelAttribute RequestContext requestContext) {
        return petService.get(id);
    }

}
