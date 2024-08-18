package com.skilrock.retailapp.models.drawgames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketCancelBean {

    @SerializedName("autoCancel")
    String autoCancel;
    @SerializedName("cancelChannel")
    String cancelChannel;
    @SerializedName("gameCode")
    String gameCode;
    @SerializedName("userId")
    String userId;
    @SerializedName("ticketNumber")
    String ticketNumber;
    @SerializedName("isAutoCancel")
    Boolean isAutoCancel;
    @SerializedName("sessionId")
    String sessionId;
    @SerializedName("terminalId")
    @Expose
    private String terminalId;
    @SerializedName("modelCode")
    @Expose
    private String modelCode;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getAutoCancel() {
        return autoCancel;
    }

    public void setAutoCancel(Boolean autoCancel) {
        isAutoCancel = autoCancel;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setAutoCancel(String autoCancel) {
        this.autoCancel = autoCancel;
    }

    public String getCancelChannel() {
        return cancelChannel;
    }

    public void setCancelChannel(String cancelChannel) {
        this.cancelChannel = cancelChannel;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Override
    public String toString() {
        return "TicketCancelBean{" +
                "autoCancel='" + autoCancel + '\'' +
                ", cancelChannel='" + cancelChannel + '\'' +
                ", gameCode='" + gameCode + '\'' +
                ", userId='" + userId + '\'' +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", isAutoCancel=" + isAutoCancel +
                ", sessionId='" + sessionId + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", modelCode='" + modelCode + '\'' +
                '}';
    }
}
