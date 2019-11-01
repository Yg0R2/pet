package com.yg0r2.core.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientsCommonConfiguration {

    @Bean
    public RestTemplate clientRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate;
    }

}
