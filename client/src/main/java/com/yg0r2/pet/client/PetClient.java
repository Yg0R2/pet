package com.yg0r2.pet.client;

import com.yg0r2.pet.api.model.PetEntry;

public interface PetClient {

    PetEntry create(PetEntry petEntry);

    PetEntry get(long id);

}
