package com.yg0r2.core.api.model;

public enum HeaderParams {

    AUTHORIZATION("Authorization"),
    CONTENT_TYPE("Content-Type"),
    REQUEST_ID("RequestId"),
    SESSION_ID("SessionId");

    private final String value;

    HeaderParams(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
