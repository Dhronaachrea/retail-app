package com.skilrock.retailapp.models.ola;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaDepositRequestBean {

    @Override
    public String toString() {
        return "OlaDepositRequestBean{" +
                "depositAmt=" + depositAmt +
                ", retailerUserName='" + retailerUserName + '\'' +
                ", playerUserName='" + playerUserName + '\'' +
                ", retailMerchantCode='" + retailMerchantCode + '\'' +
                ", retUserId=" + retUserId +
                ", token='" + token + '\'' +
                ", plrMerchantCode='" + plrMerchantCode + '\'' +
                ", plrDomainCode='" + plrDomainCode + '\'' +
                ", retailDomainCode='" + retailDomainCode + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", appType='" + appType + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", modelCode='" + modelCode + '\'' +
                '}';
    }

    @SerializedName("depositAmt")
    @Expose
    private Double depositAmt;

    @SerializedName("retailerUserName")
    @Expose
    private String retailerUserName;

    @SerializedName("playerUserName")
    @Expose
    private String playerUserName;

    @SerializedName("retailMerchantCode")
    @Expose
    private String retailMerchantCode;

    @SerializedName("retUserId")
    @Expose
    private Integer retUserId;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("plrMerchantCode")
    @Expose
    private String plrMerchantCode;

    @SerializedName("plrDomainCode")
    @Expose
    private String plrDomainCode;

    @SerializedName("retailDomainCode")
    @Expose
    private String retailDomainCode;

    @SerializedName("deviceType")
    @Expose
    private String deviceType;

    @SerializedName("appType")
    @Expose
    private String appType;

    @SerializedName("terminalId")
    @Expose
    private String terminalId;

    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("modelCode")
    @Expose
    private String modelCode;

    public Double getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(Double depositAmt) {
        this.depositAmt = depositAmt;
    }

    public String getRetailerUserName() {
        return retailerUserName;
    }

    public void setRetailerUserName(String retailerUserName) {
        this.retailerUserName = retailerUserName;
    }

    public String getPlayerUserName() {
        return playerUserName;
    }

    public void setPlayerUserName(String playerUserName) {
        this.playerUserName = playerUserName;
    }

    public String getRetailMerchantCode() {
        return retailMerchantCode;
    }

    public void setRetailMerchantCode(String retailMerchantCode) {
        this.retailMerchantCode = retailMerchantCode;
    }

    public Integer getRetUserId() {
        return retUserId;
    }

    public void setRetUserId(Integer retUserId) {
        this.retUserId = retUserId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPlrMerchantCode() {
        return plrMerchantCode;
    }

    public void setPlrMerchantCode(String plrMerchantCode) {
        this.plrMerchantCode = plrMerchantCode;
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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }
}
