package com.yg0r2.pet.dao.repository;

import com.yg0r2.pet.dao.model.PetEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<PetEntity, Long> {

}
