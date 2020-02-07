package com.yg0r2.pet.web.security;

public enum JwtKeys {

    BEARER_PREFIX("Bearer ");

    private final String key;

    JwtKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
