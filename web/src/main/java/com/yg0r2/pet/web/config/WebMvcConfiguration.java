package com.yg0r2.pet.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    public static final String FORWARD_TO_INDEX_HTML = "forward:/index.html";

    @Value("${cors.allowed-origins:-}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(allowedOrigins);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(FORWARD_TO_INDEX_HTML);
        registry.addViewController("/{x:[\\w\\-]+}").setViewName(FORWARD_TO_INDEX_HTML);
        registry.addViewController("/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}").setViewName(FORWARD_TO_INDEX_HTML);
    }

}
