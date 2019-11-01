package com.yg0r2.pet.client.configuration;

import com.yg0r2.core.client.YamlPropertyLoaderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(value = {
    @PropertySource(value = {"classpath:pet-client.yaml"}, factory = YamlPropertyLoaderFactory.class),
    @PropertySource(value = {"classpath:pet-client-${spring.profiles.active}.yaml"}, factory = YamlPropertyLoaderFactory.class, ignoreResourceNotFound = true)
})
public class PetClientConfig {

    @Value("${clients.pet.url.create}")
    private String createUrl;
    @Value("${clients.pet.url.get}")
    private String getUrl;

    public String getCreateUrl() {
        return createUrl;
    }

    public String getGetUrl() {
        return getUrl;
    }

}
