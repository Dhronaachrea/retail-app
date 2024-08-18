package com.skilrock.retailapp.models.drawgames;

import androidx.annotation.NonNull;

public class DrawFetchGameDataRequestBean {

    private String lastTicketNumber;
    private String retailerId;
    private String sessionId;

    public String getLastTicketNumber() {
        return lastTicketNumber;
    }

    public void setLastTicketNumber(String lastTicketNumber) {
        this.lastTicketNumber = lastTicketNumber;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
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
        return "DrawFetchGameDataRequestBean{" + "lastTicketNumber='" + lastTicketNumber + '\'' + ", retailerId='" + retailerId + '\'' + ", sessionId='" + sessionId + '\'' + '}';
    }
}
