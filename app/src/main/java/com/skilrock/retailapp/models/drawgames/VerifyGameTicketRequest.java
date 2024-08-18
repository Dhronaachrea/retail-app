package com.skilrock.retailapp.models.drawgames;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyGameTicketRequest {

    @SerializedName("merchantCode")
    @Expose
    private String merchantCode;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("ticketNumber")
    @Expose
    private String ticketNumber;
    @SerializedName("sessionId")
    @Expose
    private String sessionId;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @NonNull
    @Override
    public String toString() {
        return "VerifyGameTicketRequest{" + "merchantCode='" + merchantCode + '\'' + ", userName='" + userName + '\'' + ", ticketNumber='" + ticketNumber + '\'' + ", sessionId='" + sessionId + '\'' + '}';
    }
}