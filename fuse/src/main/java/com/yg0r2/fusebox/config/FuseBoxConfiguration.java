package com.yg0r2.fusebox.config;

import com.yg0r2.fuse.domain.FuseConfig;
import com.yg0r2.fusebox.FuseBox;
import com.yg0r2.fusebox.FuseBoxThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Configuration
public class FuseBoxConfiguration {

    @Autowired
    private FuseBoxServiceConfiguration fuseBoxServiceConfiguration;

    @Bean
    public FuseBox fuseBox() {
        return new FuseBox(fuseBoxServiceConfiguration.buildFuseConfigs());
    }

    @Bean
    public ExecutorService fuseBoxExecutorService() {
        return Executors.newCachedThreadPool(new FuseBoxThreadFactory("fuse-"));
    }

    @Configuration
    @ConfigurationProperties(prefix = "fuse-box", ignoreUnknownFields = false)
    static class FuseBoxServiceConfiguration {

        private Map<String, FuseConfig.ConfigBuilder> services;

        public void setServices(Map<String, FuseConfig.ConfigBuilder> services) {
            this.services = services;
        }

        private Map<String, FuseConfig> buildFuseConfigs() {
            return Optional.ofNullable(services)
                .map(Map::entrySet)
                .orElseGet(Collections::emptySet)
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, this::buildFuseConfig));
        }

        private FuseConfig buildFuseConfig(Map.Entry<String, FuseConfig.ConfigBuilder> entry) {
            return entry.getValue()
                .setServiceName(entry.getKey())
                .build();
        }

    }

}
