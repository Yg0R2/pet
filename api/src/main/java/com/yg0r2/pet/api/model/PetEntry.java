package com.yg0r2.pet.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Optional;

@JsonDeserialize(builder = PetEntry.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(allowGetters = true, value = "id")
public class PetEntry {

    private final long id;

    private PetEntry(Builder builder) {
        this.id = Optional.ofNullable(builder.id).orElse(0L);
    }

    public long getId() {
        return id;
    }

    public static class Builder {

        private Long id;

        public Builder withId(Long id) {
            this.id = id;

            return this;
        }

        public PetEntry build() {
            return new PetEntry(this);
        }

    }

}
