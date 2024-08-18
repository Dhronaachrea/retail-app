package com.skilrock.retailapp.models.ola;

public class OlaPlayerSearchRequestBean {

    private String url;
    private String password;
    private String contentType;
    private String accept;
    private String userName;
    private String retailDomainCode;
    private Long retailOrgId;
    private String plrDomainCode;
    private Integer pageIndex;
    private Integer pageSize;
    private String firstName;
    private String lastName;
    private String phone;
    private String plrUserName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRetailDomainCode() {
        return retailDomainCode;
    }

    public void setRetailDomainCode(String retailDomainCode) {
        this.retailDomainCode = retailDomainCode;
    }

    public Long getRetailOrgId() {
        return retailOrgId;
    }

    public void setRetailOrgId(Long retailOrgId) {
        this.retailOrgId = retailOrgId;
    }

    public String getPlrDomainCode() {
        return plrDomainCode;
    }

    public void setPlrDomainCode(String plrDomainCode) {
        this.plrDomainCode = plrDomainCode;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "OlaPlayerSearchRequestBean{" + "url='" + url + '\'' + ", password='" + password + '\'' + ", contentType='" + contentType + '\'' + ", accept='" + accept + '\'' + ", userName='" + userName + '\'' + ", retailDomainCode='" + retailDomainCode + '\'' + ", retailOrgId=" + retailOrgId + ", plrDomainCode='" + plrDomainCode + '\'' + ", pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", phone='" + phone + '\'' + '}';
    }

    public String getPlrUserName() {
        return plrUserName;
    }

    public void setPlrUserName(String plrUserName) {
        this.plrUserName = plrUserName;
    }
}
