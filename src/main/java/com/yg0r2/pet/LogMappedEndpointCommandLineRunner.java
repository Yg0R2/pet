package com.yg0r2.pet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class LogMappedEndpointCommandLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogMappedEndpointCommandLineRunner.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) {
        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods().entrySet().stream()
            .map(entry -> entry.getKey() + " - " + entry.getValue())
            .forEach(LOGGER::info);
    }

}
