package com.skilrock.retailapp.sle_game_portrait;

public class SaleCancelTicket {

    /**
     * isSuccess : true
     * refundAmount : 1
     * ticketNumber : 4002113461890000040
     * gameTypeName : Soccer 13
     * Balance : -483.16
     * mode : Cancel
     * cancelDateTime : 2019-12-12 17:09:36
     * orgName : AFRICA LOTTO
     * currSymbol : USD
     * fiscalazationTaxAmount : 0
     * fiscalazationTaxPercentage : 0
     */

    private boolean isSuccess;
    private int refundAmount;
    private String ticketNumber;
    private String gameTypeName;
    private String Balance;
    private String mode;
    private String cancelDateTime;
    private String orgName;
    private String currSymbol;
    private int fiscalazationTaxAmount;
    private int fiscalazationTaxPercentage;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getGameTypeName() {
        return gameTypeName;
    }

    public void setGameTypeName(String gameTypeName) {
        this.gameTypeName = gameTypeName;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCancelDateTime() {
        return cancelDateTime;
    }

    public void setCancelDateTime(String cancelDateTime) {
        this.cancelDateTime = cancelDateTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCurrSymbol() {
        return currSymbol;
    }

    public void setCurrSymbol(String currSymbol) {
        this.currSymbol = currSymbol;
    }

    public int getFiscalazationTaxAmount() {
        return fiscalazationTaxAmount;
    }

    public void setFiscalazationTaxAmount(int fiscalazationTaxAmount) {
        this.fiscalazationTaxAmount = fiscalazationTaxAmount;
    }

    public int getFiscalazationTaxPercentage() {
        return fiscalazationTaxPercentage;
    }

    public void setFiscalazationTaxPercentage(int fiscalazationTaxPercentage) {
        this.fiscalazationTaxPercentage = fiscalazationTaxPercentage;
    }
}
