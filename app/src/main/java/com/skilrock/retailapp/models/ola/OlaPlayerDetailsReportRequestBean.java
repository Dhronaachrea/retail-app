package com.skilrock.retailapp.models.ola;

public class OlaPlayerDetailsReportRequestBean {

    private String url;
    private String contentType;
    private String accept;
    private String username;
    private String password;
    private String fromDate;
    private String toDate;
    private Integer offset;
    private Integer limit;
    private String mobileNo;
    private String plrDomainCode;
    private String retailDomainCode;
    private Long retailOrgId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPlrDomainCode() {
        return plrDomainCode;
    }

    public void setPlrDomainCode(String plrDomainCode) {
        this.plrDomainCode = plrDomainCode;
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

    @Override
    public String toString() {
        return "OlaPlayerDetailsReportRequestBean{" + "url='" + url + '\'' + ", contentType='" + contentType + '\'' + ", accept='" + accept + '\'' + ", username='" + username + '\'' + ", password='" + password + '\'' + ", fromDate='" + fromDate + '\'' + ", toDate='" + toDate + '\'' + ", offset=" + offset + ", limit=" + limit + ", mobileNo='" + mobileNo + '\'' + ", plrDomainCode='" + plrDomainCode + '\'' + ", retailDomainCode='" + retailDomainCode + '\'' + ", retailOrgId=" + retailOrgId + '}';
    }
}
