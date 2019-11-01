package com.yg0r2.pet.service;

import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.client.PetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PetClientTestService {

    @Autowired
    private PetClient petClient;

    public PetEntry get(long id) {
        return petClient.get(id);
    }

    public PetEntry create() {
        PetEntry petEntry = new PetEntry.Builder()
            .withTitle(UUID.randomUUID().toString())
            .build();

        return petClient.create(petEntry);
    }

}
