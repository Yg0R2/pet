package com.yg0r2.pet.client.configuration;

import com.yg0r2.fuse.service.proxy.FuseProxyFactory;
import com.yg0r2.pet.client.DefaultPetClient;
import com.yg0r2.pet.client.PetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScans(value = {
    @ComponentScan(value = {"com.yg0r2.fuse", "com.yg0r2.fusebox"}),
    @ComponentScan(value = {"com.yg0r2.core.client"}),
    @ComponentScan(value = {"com.yg0r2.pet.client"})
})
public class PetClientConfiguration {

    @Autowired
    private PetClientConfig petClientConfig;
    @Autowired
    private RestTemplate clientRestTemplate;
    @Autowired
    private FuseProxyFactory fuseProxyFactory;

    @Bean
    public PetClient petClient() {
        PetClient petClient = new DefaultPetClient(clientRestTemplate, petClientConfig);

        return fuseProxyFactory.create(petClient, PetClient.class, "pet");
    }

}
