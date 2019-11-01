package com.yg0r2.pet.client;

import com.yg0r2.pet.api.model.PetEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PetClient {

    @Value("${clients.pet.url.create}")
    private String createUrl;
    @Value("${clients.pet.url.getPattern}")
    private String getUrlPattern;

    @Autowired
    private RestTemplate restTemplate;

    public PetEntry create(PetEntry petEntry) {
        return restTemplate.postForObject(createUrl, petEntry, PetEntry.class);
    }

    public PetEntry get(long id) {
        String url = String.format(getUrlPattern, id);

        return restTemplate.getForObject(url, PetEntry.class);
    }

}
