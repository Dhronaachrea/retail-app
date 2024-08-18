package com.skilrock.retailapp.models.FieldX;

import androidx.annotation.NonNull;

public class FieldXUrlBean {
    private String url;
    private String clientId;
    private String clientSecret;

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

    @NonNull
    @Override
    public String toString() {
        return "FieldXUrlBean{" + "url='" + url + '\'' + ", clientId='" + clientId + '\'' + ", clientSecret='" + clientSecret + '\'' + '}';
    }
}
