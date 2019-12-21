package com.yg0r2.pet.client;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.core.client.AbstractCoreClient;
import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.client.configuration.PetClientConfig;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class DefaultPetClient extends AbstractCoreClient implements PetClient {

    private final PetClientConfig petClientConfig;
    private final RestTemplate restTemplate;

    public DefaultPetClient(RestTemplate restTemplate, PetClientConfig petClientConfig) {
        this.restTemplate = restTemplate;
        this.petClientConfig = petClientConfig;
    }

    public PetEntry create(PetEntry petEntry, RequestContext requestContext) {
        RequestEntity<PetEntry> requestEntity = new RequestEntity<>(petEntry, createHeaders(requestContext), HttpMethod.POST, URI.create(petClientConfig.getCreateUrl()));

        return restTemplate.exchange(requestEntity, PetEntry.class)
            .getBody();
    }

    public PetEntry get(long id, RequestContext requestContext) {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders(requestContext));

        return restTemplate.exchange(petClientConfig.getGetByIdUrl(), HttpMethod.GET, entity, PetEntry.class, id)
            .getBody();
    }

}
