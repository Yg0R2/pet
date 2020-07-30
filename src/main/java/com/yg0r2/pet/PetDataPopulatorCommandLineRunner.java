package com.yg0r2.pet;

import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.IntStream;

@Component
public class PetDataPopulatorCommandLineRunner implements CommandLineRunner {

    @Autowired
    private PetService petService;

    @Override
    public void run(String... args) {
        IntStream.range(0, 5)
            .mapToObj(i -> createPetEntry())
            .forEach(petService::create);
    }

    private PetEntry createPetEntry() {
        return PetEntry.builder()
            .withTitle(UUID.randomUUID().toString())
            .build();
    }

}
