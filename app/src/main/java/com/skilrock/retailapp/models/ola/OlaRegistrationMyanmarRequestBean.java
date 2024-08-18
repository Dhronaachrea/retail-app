package com.skilrock.retailapp.models.ola;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class OlaRegistrationMyanmarRequestBean {

    @SerializedName("appType")
    @Expose
    private String appType;

    @SerializedName("depositAmt")
    @Expose
    private Double depositAmt;

    @SerializedName("deviceType")
    @Expose
    private String deviceType;

    @SerializedName("paymentType")
    @Expose
    private String paymentType;

    @SerializedName("plrDomainCode")
    @Expose
    private String plrDomainCode;

    @SerializedName("plrMerchantCode")
    @Expose
    private String plrMerchantCode;

    @SerializedName("retOrgId")
    @Expose
    private Long retOrgId;

    @SerializedName("retUserId")
    @Expose
    private Integer retUserId;

    @SerializedName("retailDomainCode")
    @Expose
    private String retailDomainCode;

    @SerializedName("retailMerchantCode")
    @Expose
    private String retailMerchantCode;

    @SerializedName("terminalId")
    @Expose
    private String terminalId;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("playerUserName")
    @Expose
    private String playerUserName;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("modelCode")
    @Expose
    private String modelCode;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlayerUserName() {
        return playerUserName;
    }

    public void setPlayerUserName(String playerUserName) {
        this.playerUserName = playerUserName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Double getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(Double depositAmt) {
        this.depositAmt = depositAmt;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPlrDomainCode() {
        return plrDomainCode;
    }

    public void setPlrDomainCode(String plrDomainCode) {
        this.plrDomainCode = plrDomainCode;
    }

    public String getPlrMerchantCode() {
        return plrMerchantCode;
    }

    public void setPlrMerchantCode(String plrMerchantCode) {
        this.plrMerchantCode = plrMerchantCode;
    }

    public Long getRetOrgId() {
        return retOrgId;
    }

    public void setRetOrgId(Long retOrgId) {
        this.retOrgId = retOrgId;
    }

    public Integer getRetUserId() {
        return retUserId;
    }

    public void setRetUserId(Integer retUserId) {
        this.retUserId = retUserId;
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

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NotNull
    @Override
    public String toString() {
        return "OlaRegistrationMyanmarRequestBean{" +
                "appType='" + appType + '\'' +
                ", depositAmt=" + depositAmt +
                ", deviceType='" + deviceType + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", plrDomainCode='" + plrDomainCode + '\'' +
                ", plrMerchantCode='" + plrMerchantCode + '\'' +
                ", retOrgId=" + retOrgId +
                ", retUserId=" + retUserId +
                ", retailDomainCode='" + retailDomainCode + '\'' +
                ", retailMerchantCode='" + retailMerchantCode + '\'' +
                ", terminalId=" + terminalId +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", playerUserName='" + playerUserName + '\'' +
                '}';
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }
}
