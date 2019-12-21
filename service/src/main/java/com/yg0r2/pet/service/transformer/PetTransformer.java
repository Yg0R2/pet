package com.yg0r2.pet.service.transformer;

import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.dao.model.PetEntity;

import org.springframework.stereotype.Component;

@Component
public class PetTransformer {

    public PetEntity transform(PetEntry petEntry) {
        PetEntity petEntity = new PetEntity();

        petEntity.setTitle(petEntry.getTitle());

        return petEntity;
    }

    public PetEntry transform(PetEntity petEntity) {
        return PetEntry.builder()
            .withId(petEntity.getId())
            .withTitle(petEntity.getTitle())
            .build();
    }

}
