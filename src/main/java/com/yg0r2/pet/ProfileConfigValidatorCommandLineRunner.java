package com.yg0r2.pet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("!default")
@Component
public class ProfileConfigValidatorCommandLineRunner implements CommandLineRunner {

    private static final String CONFIG_FILE_PATTERN = "application-%s.yaml";
    private static final String ERROR_MESSAGE = "Application cannot start, because %s config file is missing.";

    @Value("${spring.profiles.active}")
    private List<String> activeProfiles;

    @Override
    public void run(String... args) {
        activeProfiles.stream()
            .map(activeProfile -> String.format(CONFIG_FILE_PATTERN, activeProfile))
            .map(ClassPathResource::new)
            .forEach(this::validateResource);
    }

    private void validateResource(ClassPathResource classPathResource) {
        if (!classPathResource.exists()) {
            throw new RuntimeException(String.format(ERROR_MESSAGE, classPathResource.getFilename()));
        }
    }

}
