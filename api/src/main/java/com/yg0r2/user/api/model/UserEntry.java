package com.yg0r2.user.api.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yg0r2.core.api.model.CoreEntry;

import java.util.Objects;

@JsonDeserialize(builder = UserEntry.Builder.class)
public final class UserEntry implements CoreEntry {

    private final long id;
    private final String nickName;
    private final String password;

    private UserEntry(Builder builder) {
        id = Objects.requireNonNullElse(builder.id, DEFAULT_ID);
        nickName = Objects.requireNonNull(builder.nickName);
        password = Objects.requireNonNull(builder.password);
    }

    public long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {

        private Long id;
        private String nickName;
        private String password;

        public Builder withId(Long id) {
            this.id = id;

            return this;
        }

        public Builder withNickName(String nickName) {
            this.nickName = nickName;

            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;

            return this;
        }

        public UserEntry build() {
            return new UserEntry(this);
        }

    }

}
