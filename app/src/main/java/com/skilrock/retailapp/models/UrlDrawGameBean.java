package com.skilrock.retailapp.models;

import androidx.annotation.NonNull;

public class UrlDrawGameBean {

    private String url;
    private String userName;
    private String password;
    private String contentType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @NonNull
    @Override
    public String toString() {
        return "UrlDrawGameBean{" + "url='" + url + '\'' + ", userName='" + userName + '\'' + ", password='" + password + '\'' + ", contentType='" + contentType + '\'' + '}';
    }
}
