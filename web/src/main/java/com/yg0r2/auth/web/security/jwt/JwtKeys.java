package com.yg0r2.auth.web.security.jwt;

public enum JwtKeys {

    BEARER_PREFIX("Bearer ");

    private final String value;

    JwtKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
