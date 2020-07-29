package com.yg0r2.auth.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

@JsonDeserialize(builder = SignInRequest.Builder.class)
public final class SignInRequest {

    @NotBlank
    private final String userName;
    @NotBlank
    private final String password;

    public SignInRequest(Builder builder) {
        userName = builder.userName;
        password = builder.password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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

        private String userName;
        private String password;

        public Builder withUserName(String userName) {
            this.userName = userName;

            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;

            return this;
        }

        public SignInRequest build() {
            return new SignInRequest(this);
        }

    }

}
