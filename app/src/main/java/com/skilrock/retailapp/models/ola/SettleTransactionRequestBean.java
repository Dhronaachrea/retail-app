package com.skilrock.retailapp.models.ola;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettleTransactionRequestBean {

    @SerializedName("plrDomainCode")
    @Expose
    private String plrDomainCode;

    @SerializedName("reatilUserID")
    @Expose
    private Integer reatilUserID;
    @SerializedName("retailDomainCode")
    @Expose
    private String retailDomainCode;
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("txnID")
    @Expose
    private Integer txnID;

    public String getPlrDomainCode() {
        return plrDomainCode;
    }

    public void setPlrDomainCode(String plrDomainCode) {
        this.plrDomainCode = plrDomainCode;
    }

    public Integer getReatilUserID() {
        return reatilUserID;
    }

    public void setReatilUserID(Integer reatilUserID) {
        this.reatilUserID = reatilUserID;
    }

    public String getRetailDomainCode() {
        return retailDomainCode;
    }

    public void setRetailDomainCode(String retailDomainCode) {
        this.retailDomainCode = retailDomainCode;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Integer getTxnID() {
        return txnID;
    }

    public void setTxnID(Integer txnID) {
        this.txnID = txnID;
    }

    @Override
    public String toString() {
        return "SettleTransactionRequestBean{" +
                "plrDomainCode='" + plrDomainCode + '\'' +
                ", reatilUserID=" + reatilUserID +
                ", retailDomainCode='" + retailDomainCode + '\'' +
                ", sessionID='" + sessionID + '\'' +
                ", txnID=" + txnID +
                '}';
    }
}
