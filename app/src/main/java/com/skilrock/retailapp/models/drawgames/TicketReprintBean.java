package com.skilrock.retailapp.models.drawgames;

import com.google.gson.annotations.SerializedName;

public class TicketReprintBean {

    @SerializedName("gameCode")
    String gameCode;
    @SerializedName("purchaseChannel")
    String purchaseChannel;
    @SerializedName("ticketNumber")
    String ticketNumber;
    @SerializedName("isPwt")
    Boolean isPwt;

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getPurchaseChannel() {
        return purchaseChannel;
    }

    public void setPurchaseChannel(String purchaseChannel) {
        this.purchaseChannel = purchaseChannel;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Boolean getPwt() {
        return isPwt;
    }

    public void setPwt(Boolean pwt) {
        isPwt = pwt;
    }
}
