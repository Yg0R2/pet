package com.yg0r2.pet.client;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.core.client.CoreClient;
import com.yg0r2.pet.api.model.PetEntry;
import org.springframework.http.ResponseEntity;

public interface PetClient extends CoreClient {

    PetEntry create(PetEntry petEntry, RequestContext requestContext);

    PetEntry get(long id, RequestContext requestContext);

}
