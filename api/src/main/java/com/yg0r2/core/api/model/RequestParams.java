package com.yg0r2.core.api.model;

public enum RequestParams {

    AUTHORIZATION("Authorization"),
    CONTENT_TYPE("Content-Type"),
    REQUEST_ID("RequestId"),
    SESSION_ID("SessionId");

    private final String value;

    RequestParams(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
