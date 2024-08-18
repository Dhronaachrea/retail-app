package com.skilrock.retailapp.models.drawgames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WinningClaimVerifyRequestBean {

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

    @SerializedName("lastPWTTicket")
    @Expose
    private String lastPWTTicket;

    public String getLastPWTTicket() {
        return lastPWTTicket;
    }

    public void setLastPWTTicket(String lastPWTTicket) {
        this.lastPWTTicket = lastPWTTicket;
    }

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

    @Override
    public String toString() {
        return "WinningClaimVerifyRequestBean{" +
                "merchantCode='" + merchantCode + '\'' +
                ", userName='" + userName + '\'' +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", lastPWTTicket='" + lastPWTTicket + '\'' +
                '}';
    }
}
