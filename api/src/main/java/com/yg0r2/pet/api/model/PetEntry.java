package com.yg0r2.pet.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yg0r2.core.api.model.CoreEntry;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;

@JsonDeserialize(builder = PetEntry.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(allowGetters = true, value = "id")
public class PetEntry implements CoreEntry {

    private final long id;

    @NotBlank
    private final String title;

    private PetEntry(Builder builder) {
        this.id = Optional.ofNullable(builder.id).orElse(DEFAULT_ID);
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

    public static Builder builder() {
        return new PetEntry.Builder();
    }

    public static class Builder {

        private Long id;
        private String title;

        private Builder() {
        }

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
