package com.yg0r2.pet.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

@JsonDeserialize(builder = PetEntry.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(allowGetters = true, value = "id")
public class PetEntry {

    private final long id;

    @NotNull
    private final String title;

    private PetEntry(Builder builder) {
        this.id = Optional.ofNullable(builder.id).orElse(0L);
        this.title = Objects.requireNonNull(builder.title);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class Builder {

        private Long id;
        private String title;

        public Builder withId(Long id) {
            this.id = id;

            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;

            return this;
        }

        public PetEntry build() {
            return new PetEntry(this);
        }

    }

}
