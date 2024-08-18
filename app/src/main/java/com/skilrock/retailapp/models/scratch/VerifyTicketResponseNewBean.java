package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyTicketResponseNewBean {

    /*{
        "responseCode": 1000,
            "responseMessage": "Success",
            "winningAmount": 99.38,
            "taxAmount": 19.38,
            "netWinningAmount": 80,
            "ticketNumber": "163-050108-061",
            "virnNumber": "16322823774",
            "gameName": "FZZY ВІП Мільйонер",
            "soldByOrg": "0000001_shaileshret"
    }*/


    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("ticketNumber")
    @Expose
    private String ticketNumber;
    @SerializedName("virnNumber")
    @Expose
    private String virnNumber;
    @SerializedName("winningAmount")
    @Expose
    private String winningAmount;
    @SerializedName("taxAmount")
    @Expose
    private String taxAmount;
    @SerializedName("gameName")
    @Expose
    private String gameName;
    @SerializedName("soldByOrg")
    @Expose
    private String soldByOrg;
    @SerializedName("netWinningAmount")
    @Expose
    private String netWinningAmount;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getVirnNumber() {
        return virnNumber;
    }

    public void setVirnNumber(String virnNumber) {
        this.virnNumber = virnNumber;
    }

    public String getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(String winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    public String getSoldByOrg() {
        return soldByOrg;
    }

    public void setSoldByOrg(String soldByOrg) {
        this.soldByOrg = soldByOrg;
    }

    public String getNetWinningAmount() { return netWinningAmount; }

    public void setNetWinningAmount(String netWinningAmount) { this.netWinningAmount = netWinningAmount; }

    @NonNull
    @Override
    public String toString() {
        return "VerifyTicketResponseNewBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", ticketNumber='" + ticketNumber + '\'' + ", virnNumber='" + virnNumber + '\'' + ", winningAmount=" + winningAmount + '\'' + ", taxAmount=" + taxAmount + ", gameName=" + gameName +", soldByOrg=" + soldByOrg + '}';
    }
}
