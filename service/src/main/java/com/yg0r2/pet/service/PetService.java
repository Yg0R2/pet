package com.yg0r2.pet.service;

import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.dao.model.PetEntity;
import com.yg0r2.pet.dao.repository.PetRepository;
import com.yg0r2.pet.service.exceptions.PetEntryNotFoundException;
import com.yg0r2.pet.service.exceptions.UnableToCreatePetEntryException;
import com.yg0r2.pet.service.transformer.PetTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetTransformer petTransformer;

    @Transactional
    public PetEntry create(PetEntry petEntry) {
        return Optional.ofNullable(petEntry)
            .map(petTransformer::transform)
            .map(petRepository::save)
            .map(petTransformer::transform)
            .orElseThrow(() -> new UnableToCreatePetEntryException("Unable to create PetEntry: " + petEntry));
    }

    @Transactional
    public void delete(long id) {
        petRepository.deleteById(id);
    }

    public List<PetEntry> getAll() {
        Iterable<PetEntity> petEntities = Optional.ofNullable(petRepository.findAll())
            .orElse(Collections.emptyList());

        return StreamSupport.stream(petEntities.spliterator(), false)
            .map(petTransformer::transform)
            .collect(Collectors.toList());
    }

    public PetEntry get(long id) {
        return petRepository.findById(id)
            .map(petTransformer::transform)
            .orElseThrow(() -> new PetEntryNotFoundException("PetEntry not found with id: " + id));
    }

}
