package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaRegistrationRequestBean {

    @SerializedName("plrMerchantCode")
    @Expose
    private String plrMerchantCode;

    @SerializedName("retailMerchantCode")
    @Expose
    private String retailMerchantCode;

    @SerializedName("plrDomainCode")
    @Expose
    private String plrDomainCode;

    @SerializedName("retailDomainCode")
    @Expose
    private String retailDomainCode;

    @SerializedName("retUserId")
    @Expose
    private Integer retUserId;

    @SerializedName("retOrgId")
    @Expose
    private Long retOrgId;

    @SerializedName("playerUserName")
    @Expose
    private String playerUserName;

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("depositAmt")
    @Expose
    private Double depositAmt;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("deviceType")
    @Expose
    private String deviceType;

    @SerializedName("terminalId")
    @Expose
    private String terminalId;

    @SerializedName("appType")
    @Expose
    private String appType;

    @SerializedName("paymentType")
    @Expose
    private String paymentType;

    public String getPlrMerchantCode() {
        return plrMerchantCode;
    }

    public void setPlrMerchantCode(String plrMerchantCode) {
        this.plrMerchantCode = plrMerchantCode;
    }

    public String getRetailMerchantCode() {
        return retailMerchantCode;
    }

    public void setRetailMerchantCode(String retailMerchantCode) {
        this.retailMerchantCode = retailMerchantCode;
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

    public Integer getRetUserId() {
        return retUserId;
    }

    public void setRetUserId(Integer retUserId) {
        this.retUserId = retUserId;
    }

    public Long getRetOrgId() {
        return retOrgId;
    }

    public void setRetOrgId(Long retOrgId) {
        this.retOrgId = retOrgId;
    }

    public String getPlayerUserName() {
        return playerUserName;
    }

    public void setPlayerUserName(String playerUserName) {
        this.playerUserName = playerUserName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Double getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(Double depositAmt) {
        this.depositAmt = depositAmt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @NonNull
    @Override
    public String toString() {
        return "OlaRegistrationRequestBean{" + "plrMerchantCode='" + plrMerchantCode + '\'' + ", retailMerchantCode='" + retailMerchantCode + '\'' + ", plrDomainCode='" + plrDomainCode + '\'' + ", retailDomainCode='" + retailDomainCode + '\'' + ", retUserId=" + retUserId + ", retOrgId=" + retOrgId + ", playerUserName='" + playerUserName + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", phone='" + phone + '\'' + ", address='" + address + '\'' + ", country='" + country + '\'' + ", otp='" + otp + '\'' + ", depositAmt=" + depositAmt + ", token='" + token + '\'' + ", deviceType='" + deviceType + '\'' + ", terminalId='" + terminalId + '\'' + ", appType='" + appType + '\'' + ", paymentType='" + paymentType + '\'' + '}';
    }
}
