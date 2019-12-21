package com.yg0r2.pet.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yg0r2.core.api.model.RequestContext;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@JsonDeserialize(builder = PetServiceRequestContext.Builder.class)
public final class PetServiceRequestContext implements RequestContext<PetEntry> {

    private final String authorization;
    private final String requestId;
    private final String sessionId;

    private PetServiceRequestContext(Builder builder) {
        authorization = builder.authorization;
        requestId = Objects.requireNonNull(builder.requestId);
        sessionId = Objects.requireNonNull(builder.sessionId);
    }

    @Override
    public String getAuthorization() {
        return authorization;
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
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

        private String authorization;
        private String requestId;
        private String sessionId;

        public Builder withAuthorization(String authorization) {
            this.authorization = authorization;

            return this;
        }

        public Builder withRequestId(String requestId) {
            this.requestId = requestId;

            return this;
        }

        public Builder withSessionId(String sessionId) {
            this.sessionId = sessionId;

            return this;
        }

        public PetServiceRequestContext build() {
            return new PetServiceRequestContext(this);
        }

    }

}
