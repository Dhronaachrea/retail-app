package com.skilrock.retailapp.models;

public class UrlBean {

    private String url;
    private String clientId;
    private String clientSecret;
    private String contentType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "UrlBean{" + "url='" + url + '\'' + ", clientId='" + clientId + '\'' + ", clientSecret='" + clientSecret + '\'' + ", contentType='" + contentType + '\'' + '}';
    }
}
