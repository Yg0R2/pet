package com.yg0r2.auth.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

public final class SignInResponse {

    @NotBlank
    private final String accessToken;
    @NotBlank
    private final String sessionId;
    @NotBlank
    private final String userName;

    public SignInResponse(Builder builder) {
        accessToken = builder.accessToken;
        sessionId = builder.sessionId;
        userName = builder.userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserName() {
        return userName;
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

        private String accessToken;
        private String sessionId;
        private String userName;

        public Builder withAccessToken(String accessToken) {
            this.accessToken = accessToken;

            return this;
        }

        public Builder withSessionId(String sessionId) {
            this.sessionId = sessionId;

            return this;
        }

        public Builder withUserName(String userName) {
            this.userName = userName;

            return this;
        }

        public SignInResponse build() {
            return new SignInResponse(this);
        }

    }

}
