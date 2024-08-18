package com.skilrock.retailapp.sle_game_portrait;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PayVerifyTicket {

    /**
     * retailerName : sleRetailer
     * ticketNo : 6708113097500000046
     * gameName : Soccer
     * gameTypeName : Soccer 13
     * drawTime : 2019-11-05 18:00:00
     * drawName : SUNDAY-S13
     * boardCount : 1
     * message : Already Claimed
     * totalWinAmt : 2466.68
     * totalPay : 2466.68
     * totalTax : 0.00
     * claimPndAmt : 0
     * balance : .00
     * mode : PWT
     * currSymbol : USD
     * reprintCountPWT : 0
     * orgName : AFRICA LOTTO
     * pwtDateTime : 2019-11-06 15:02:59
     * advMessage : {}
     * responseCode : 0
     * responseMsg : SUCCESS
     * messageCode : 10055
     * fiscalazationTaxAmount : 0
     * fiscalazationTaxPercentage : 0
     */

    private String retailerName;
    private String ticketNo;
    private String gameName;
    private String gameTypeName;
    private String drawTime;
    private String drawName;
    private int boardCount;
    private String message;
    private String totalWinAmt;
    private String totalPay;
    private String totalTax;
    private int claimPndAmt;
    private String balance;
    private String mode;
    private String currSymbol;
    private int reprintCountPWT;
    private String orgName;
    private String pwtDateTime;
    private AdvMessageBean advMessage;
    private int responseCode;
    private String responseMsg;
    private int messageCode;
    private int fiscalazationTaxAmount;
    private int fiscalazationTaxPercentage;

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameTypeName() {
        return gameTypeName;
    }

    public void setGameTypeName(String gameTypeName) {
        this.gameTypeName = gameTypeName;
    }

    public String getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(String drawTime) {
        this.drawTime = drawTime;
    }

    public String getDrawName() {
        return drawName;
    }

    public void setDrawName(String drawName) {
        this.drawName = drawName;
    }

    public int getBoardCount() {
        return boardCount;
    }

    public void setBoardCount(int boardCount) {
        this.boardCount = boardCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotalWinAmt() {
        return totalWinAmt;
    }

    public void setTotalWinAmt(String totalWinAmt) {
        this.totalWinAmt = totalWinAmt;
    }

    public String getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public int getClaimPndAmt() {
        return claimPndAmt;
    }

    public void setClaimPndAmt(int claimPndAmt) {
        this.claimPndAmt = claimPndAmt;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCurrSymbol() {
        return currSymbol;
    }

    public void setCurrSymbol(String currSymbol) {
        this.currSymbol = currSymbol;
    }

    public int getReprintCountPWT() {
        return reprintCountPWT;
    }

    public void setReprintCountPWT(int reprintCountPWT) {
        this.reprintCountPWT = reprintCountPWT;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPwtDateTime() {
        return pwtDateTime;
    }

    public void setPwtDateTime(String pwtDateTime) {
        this.pwtDateTime = pwtDateTime;
    }

    public AdvMessageBean getAdvMessage() {
        return advMessage;
    }

    public void setAdvMessage(AdvMessageBean advMessage) {
        this.advMessage = advMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
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

    public static class AdvMessageBean {
        @SerializedName("top")
        @Expose
        private List<PurchaseBeanSle.Top> top = null;
        @SerializedName("bottom")
        @Expose
        private List<PurchaseBeanSle.Bottom> bottom = null;
        public List<PurchaseBeanSle.Top> getTop() {
            return top;
        }

        public void setTop(List<PurchaseBeanSle.Top> top) {
            this.top = top;
        }

        public List<PurchaseBeanSle.Bottom> getBottom() {
            return bottom;
        }

        public void setBottom(List<PurchaseBeanSle.Bottom> bottom) {
            this.bottom = bottom;
        }
    }
    public class Bottom {

        @SerializedName("msgMode")
        @Expose
        private String msgMode;
        @SerializedName("msgText")
        @Expose
        private String msgText;
        @SerializedName("msgType")
        @Expose
        private String msgType;

        public String getMsgMode() {
            return msgMode;
        }

        public void setMsgMode(String msgMode) {
            this.msgMode = msgMode;
        }

        public String getMsgText() {
            return msgText;
        }

        public void setMsgText(String msgText) {
            this.msgText = msgText;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

    }

    public class Top {

        @SerializedName("msgMode")
        @Expose
        private String msgMode;
        @SerializedName("msgText")
        @Expose
        private String msgText;
        @SerializedName("msgType")
        @Expose
        private String msgType;

        public String getMsgMode() {
            return msgMode;
        }

        public void setMsgMode(String msgMode) {
            this.msgMode = msgMode;
        }

        public String getMsgText() {
            return msgText;
        }

        public void setMsgText(String msgText) {
            this.msgText = msgText;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

    }
}
