package com.yg0r2.pet.client.configuration;

import com.yg0r2.core.client.YamlPropertyLoaderFactory;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "clients.pet.urls", ignoreUnknownFields = false)
@PropertySources(value = {
    @PropertySource(value = {"classpath:pet-client.yaml"}, factory = YamlPropertyLoaderFactory.class),
    @PropertySource(value = {"classpath:pet-client-${spring.profiles.active}.yaml"}, factory = YamlPropertyLoaderFactory.class, ignoreResourceNotFound = true)
})
public class PetClientConfig {

    private String createUrl;
    private String getAllUrl;
    private String getByIdUrl;
    @NestedConfigurationProperty
    private NestedConfig nestedConfig;

    public String getCreateUrl() {
        return createUrl;
    }

    public void setCreateUrl(String createUrl) {
        this.createUrl = createUrl;
    }

    public String getGetAllUrl() {
        return getAllUrl;
    }

    public void setGetAllUrl(String getAllUrl) {
        this.getAllUrl = getAllUrl;
    }

    public String getGetByIdUrl() {
        return getByIdUrl;
    }

    public void setGetByIdUrl(String getByIdUrl) {
        this.getByIdUrl = getByIdUrl;
    }

    public NestedConfig getNestedConfig() {
        return nestedConfig;
    }

    public void setNested(NestedConfig nestedConfig) {
        this.nestedConfig = nestedConfig;
    }

}
