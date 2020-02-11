package com.yg0r2.pet.service;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.client.PetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetClientTestService {

    @Autowired
    private PetClient petClient;

    public PetEntry get(long id, RequestContext requestContext) {
        return petClient.get(id, requestContext);
    }

    public PetEntry create(PetEntry petEntry, RequestContext requestContext) {
        return petClient.create(petEntry, requestContext);
    }

}
