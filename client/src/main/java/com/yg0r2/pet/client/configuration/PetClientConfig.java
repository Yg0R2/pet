package com.yg0r2.pet.client.configuration;

import com.yg0r2.core.client.YamlPropertyLoaderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:pet-client.yaml", "classpath:pet-client-${spring.profiles.active}.yaml"}, factory = YamlPropertyLoaderFactory.class)
public class PetClientConfig {

    @Value("${clients.pet.url.create}")
    private String createUrl;
    @Value("${clients.pet.url.getPattern}")
    private String getUrlPattern;

    public String getCreateUrl() {
        return createUrl;
    }

    public String getGetUrlPattern() {
        return getUrlPattern;
    }

}
