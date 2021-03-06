package com.yg0r2.pet.web.rest.controller;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.service.PetClientTestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/api/test/client")
public class PetClientTestRestController extends AbstractPetServiceController {

    @Autowired
    private PetClientTestService petClientTestService;

    @GetMapping("/{id}")
    public PetEntry get(@PathVariable long id, @Valid @ModelAttribute RequestContext requestContext) {
        return petClientTestService.get(id, requestContext);
    }

    @PostMapping
    public String generate(@QueryParam("amount") @Min(1) int amount, @Valid @ModelAttribute RequestContext requestContext) {
        for (int i = 0; i < amount; i++) {
            PetEntry petEntry = PetEntry.builder()
                .withTitle(UUID.randomUUID().toString())
                .build();

            petClientTestService.create(petEntry, requestContext);
        }

        return "Success";
    }

}
