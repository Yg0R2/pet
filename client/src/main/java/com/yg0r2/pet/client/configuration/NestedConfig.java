package com.yg0r2.pet.client.configuration;

public class NestedConfig {

    private String createUrl;
    private String getAllUrl;
    private String getByIdUrl;

    public String getCreateUrl() {
        return createUrl;
    }

    public void setCreateUrl(String createUrl) {
        this.createUrl = createUrl;
    }

    public String getGetAllUrl() {
        return getAllUrl;
    }

    public void setGetAllUrl(String getAllUrl) {
        this.getAllUrl = getAllUrl;
    }

    public String getGetByIdUrl() {
        return getByIdUrl;
    }

    public void setGetByIdUrl(String getByIdUrl) {
        this.getByIdUrl = getByIdUrl;
    }

}
