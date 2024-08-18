package com.skilrock.retailapp.models;

public class UrlOlaBean {

    private String url;
    private String userName;
    private String password;
    private String contentType;
    private String retailDomainCode;
    private String retailMerchantCode;
    private String accept;

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

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

    public String getRetailDomainCode() {
        return retailDomainCode;
    }

    public void setRetailDomainCode(String retailDomainCode) {
        this.retailDomainCode = retailDomainCode;
    }

    public String getRetailMerchantCode() {
        return retailMerchantCode;
    }

    public void setRetailMerchantCode(String retailMerchantCode) {
        this.retailMerchantCode = retailMerchantCode;
    }

    @Override
    public String toString() {
        return "UrlOlaBean{" + "url='" + url + '\'' + ", userName='" + userName + '\'' + ", password='" + password + '\'' + ", contentType='" + contentType + '\'' + ", retailDomainCode='" + retailDomainCode + '\'' + ", retailMerchantCode='" + retailMerchantCode + '\'' + '}';
    }
}
