package com.yg0r2.core.client;

import com.yg0r2.core.client.exception.PropertySourceNotFoundException;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.List;

public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {

    private static final YamlPropertySourceLoader YAML_PROPERTY_SOURCE_LOADER = new YamlPropertySourceLoader();

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        if (encodedResource == null) {
            return super.createPropertySource(name, encodedResource);
        }

        return loadYamlResources(encodedResource.getResource()).stream()
            .findFirst()
            .orElseThrow(() -> new PropertySourceNotFoundException("Property source not found: " + encodedResource.getResource().getFilename()));
    }

    private List<PropertySource<?>> loadYamlResources(Resource resource) throws IOException {
        return YAML_PROPERTY_SOURCE_LOADER.load(resource.getFilename(), resource);
    }

}
