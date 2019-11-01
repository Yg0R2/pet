package com.yg0r2.pet.client;

import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.client.configuration.PetClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ComponentScan(value = {"com.yg0r2.pet", "com.yg0r2.core"})
public class PetClient {

    @Autowired
    private PetClientConfig petClientConfig;
    @Autowired
    private RestTemplate clientRestTemplate;

    public PetEntry create(PetEntry petEntry) {
        return clientRestTemplate.postForObject(petClientConfig.getCreateUrl(), petEntry, PetEntry.class);
    }

    public PetEntry get(long id) {
        String url = String.format(petClientConfig.getGetUrlPattern(), id);

        return clientRestTemplate.getForObject(url, PetEntry.class);
    }

}
