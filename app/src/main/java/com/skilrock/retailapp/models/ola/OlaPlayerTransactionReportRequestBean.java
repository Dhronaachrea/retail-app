package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaPlayerTransactionReportRequestBean {

    private String url;
    private String userName;
    private String password;
    private String contentType;
    private String accept;
    private String txnType;
    private String fromDate;
    private String toDate;
    private Integer pageIndex;
    private Integer pageSize;
    private String playerDomainCode;
    private String playerUserName;
    private String retailDomainCode;
    private Long retOrgID;
    @SerializedName("plrDomainCode")
    @Expose
    private String plrDomainCode;

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

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
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

    public String getPlayerDomainCode() {
        return playerDomainCode;
    }

    public void setPlayerDomainCode(String playerDomainCode) {
        this.playerDomainCode = playerDomainCode;
    }

    public String getPlayerUserName() {
        return playerUserName;
    }

    public void setPlayerUserName(String playerUserName) {
        this.playerUserName = playerUserName;
    }

    public String getRetailDomainCode() {
        return retailDomainCode;
    }

    public void setRetailDomainCode(String retailDomainCode) {
        this.retailDomainCode = retailDomainCode;
    }

    public Long getRetOrgID() {
        return retOrgID;
    }

    public void setRetOrgID(Long retOrgID) {
        this.retOrgID = retOrgID;
    }

    @NonNull
    @Override
    public String toString() {
        return "OlaPlayerLedgerReportRequestBean{" + "url='" + url + '\'' + ", userName='" + userName + '\'' + ", password='" + password + '\'' + ", contentType='" + contentType + '\'' + ", accept='" + accept + '\'' + ", txnType='" + txnType + '\'' + ", fromDate='" + fromDate + '\'' + ", toDate='" + toDate + '\'' + ", pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", playerDomainCode='" + playerDomainCode + '\'' + ", playerUserName='" + playerUserName + '\'' + ", retailDomainCode='" + retailDomainCode + '\'' + ", retOrgID=" + retOrgID + '}';
    }

    public String getPlrDomainCode() {
        return plrDomainCode;
    }

    public void setPlrDomainCode(String plrDomainCode) {
        this.plrDomainCode = plrDomainCode;
    }
}
