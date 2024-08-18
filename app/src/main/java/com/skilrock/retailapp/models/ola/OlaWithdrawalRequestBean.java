package com.skilrock.retailapp.models.ola;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaWithdrawalRequestBean {

    @SerializedName("authenticationCode")
    @Expose
    private String authenticationCode;

    @SerializedName("currencyId")
    @Expose
    private Integer currencyId;

    @SerializedName("deviceID")
    @Expose
    private String deviceID;

    @SerializedName("mobileNO")
    @Expose
    private String mobileNO;

    @SerializedName("paymentTypeCode")
    @Expose
    private String paymentTypeCode;

    @SerializedName("paymentTypeId")
    @Expose
    private Integer paymentTypeId;

    @SerializedName("playerUserName")
    @Expose
    private String playerUserName;

    @SerializedName("plrDomainCode")
    @Expose
    private String plrDomainCode;

    @SerializedName("plrMerchantCode")
    @Expose
    private String plrMerchantCode;

    @SerializedName("retailDomainCode")
    @Expose
    private String retailDomainCode;

    @SerializedName("retailMerchantCode")
    @Expose
    private String retailMerchantCode;

    @SerializedName("retOrgId")
    @Expose
    private Long retOrgId;

    @SerializedName("retUserId")
    @Expose
    private Integer retUserId;

    @SerializedName("device")
    @Expose
    private String device;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("appType")
    @Expose
    private String appType;

    @SerializedName("withdrawalAmt")
    @Expose
    private Double withdrawalAmt;

    @SerializedName("withdrawalChannel")
    @Expose
    private String withdrawalChannel;
    @SerializedName("modelCode")
    @Expose
    private String modelCode;

    public String getAuthenticationCode() {
        return authenticationCode;
    }

    public void setAuthenticationCode(String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPlayerUserName() {
        return playerUserName;
    }

    public void setPlayerUserName(String playerUserName) {
        this.playerUserName = playerUserName;
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Double getWithdrawalAmt() {
        return withdrawalAmt;
    }

    public void setWithdrawalAmt(Double withdrawalAmt) {
        this.withdrawalAmt = withdrawalAmt;
    }

    public String getWithdrawalChannel() {
        return withdrawalChannel;
    }

    public void setWithdrawalChannel(String withdrawalChannel) {
        this.withdrawalChannel = withdrawalChannel;
    }

    @Override
    public String toString() {
        return "OlaWithdrawalRequestBean{" +
                "authenticationCode='" + authenticationCode + '\'' +
                ", currencyId=" + currencyId +
                ", deviceID='" + deviceID + '\'' +
                ", mobileNO='" + mobileNO + '\'' +
                ", paymentTypeCode='" + paymentTypeCode + '\'' +
                ", paymentTypeId=" + paymentTypeId +
                ", playerUserName='" + playerUserName + '\'' +
                ", plrDomainCode='" + plrDomainCode + '\'' +
                ", plrMerchantCode='" + plrMerchantCode + '\'' +
                ", retailDomainCode='" + retailDomainCode + '\'' +
                ", retailMerchantCode='" + retailMerchantCode + '\'' +
                ", retOrgId=" + retOrgId +
                ", retUserId=" + retUserId +
                ", device='" + device + '\'' +
                ", token='" + token + '\'' +
                ", appType='" + appType + '\'' +
                ", withdrawalAmt=" + withdrawalAmt +
                ", withdrawalChannel='" + withdrawalChannel + '\'' +
                ", modelCode='" + modelCode + '\'' +
                '}';
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }
}
