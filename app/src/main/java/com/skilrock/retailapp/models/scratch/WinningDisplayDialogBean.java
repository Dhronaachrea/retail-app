package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

public class WinningDisplayDialogBean {

    private String transactionNumber;
    private String transactionDate;
    private String ticketNumber;
    private String winningAmount;
    private String tdsAmount;
    private String netWinningAmount;
    private String taxAmount;
    private String virnNumber;
    private String commissionAmount;
    private String gameName;
    private String soldByOrg;

    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getVirnNumber() {
        return virnNumber;
    }

    public void setVirnNumber(String virnNumber) {
        this.virnNumber = virnNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
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

    public String getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(String tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public String getNetWinningAmount() {
        return netWinningAmount;
    }

    public void setNetWinningAmount(String netWinningAmount) {
        this.netWinningAmount = netWinningAmount;
    }

    @NonNull
    @Override
    public String toString() {
        return "WinningDisplayDialogBean{" + "transactionNumber='" + transactionNumber + '\'' + ", transactionDate='" + transactionDate + '\'' + ", ticketNumber='" + ticketNumber + '\'' + ", winningAmount='" + winningAmount + '\'' + ", tdsAmount='" + tdsAmount + '\'' + ", netWinningAmount='" + netWinningAmount + '\'' + ", virnNumber='" + virnNumber + '\'' + ", taxAmount='" + taxAmount + '\'' + '}';
    }
}
