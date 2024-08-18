package com.skilrock.retailapp.models.drawgames;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayPwtRequestBean {

    @SerializedName("interfaceType")
    @Expose
    private String interfaceType;
    @SerializedName("merchantCode")
    @Expose
    private String merchantCode;
    @SerializedName("saleMerCode")
    @Expose
    private String saleMerCode;
    @SerializedName("sessionId")
    @Expose
    private String sessionId;
    @SerializedName("ticketNumber")
    @Expose
    private String ticketNumber;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("verificationCode")
    @Expose
    private String verificationCode;

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getSaleMerCode() {
        return saleMerCode;
    }

    public void setSaleMerCode(String saleMerCode) {
        this.saleMerCode = saleMerCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @NonNull
    @Override
    public String toString() {
        return "PayPwtRequestBean{" + "interfaceType='" + interfaceType + '\'' + ", merchantCode='" + merchantCode + '\'' + ", saleMerCode='" + saleMerCode + '\'' + ", sessionId='" + sessionId + '\'' + ", ticketNumber='" + ticketNumber + '\'' + ", userName='" + userName + '\'' + ", verificationCode='" + verificationCode + '\'' + '}';
    }
}