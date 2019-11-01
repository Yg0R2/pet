package com.yg0r2.pet.client;

import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.client.configuration.PetClientConfig;
import org.springframework.web.client.RestTemplate;

public class DefaultPetClient implements PetClient {

    private final PetClientConfig petClientConfig;
    private final RestTemplate restTemplate;

    public DefaultPetClient(RestTemplate restTemplate, PetClientConfig petClientConfig) {
        this.restTemplate = restTemplate;
        this.petClientConfig = petClientConfig;
    }

    public PetEntry create(PetEntry petEntry) {
        return restTemplate.postForObject(petClientConfig.getCreateUrl(), petEntry, PetEntry.class);
    }

    public PetEntry get(long id) {
        String url = String.format(petClientConfig.getGetUrlPattern(), id);

        return restTemplate.getForObject(url, PetEntry.class);
    }

}
