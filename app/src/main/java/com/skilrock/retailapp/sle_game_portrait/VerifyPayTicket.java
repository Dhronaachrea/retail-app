package com.skilrock.retailapp.sle_game_portrait;

public class VerifyPayTicket {

    /**
     * responseCode : 0
     * responseMsg : SUCCESS
     * currDate : 2019-11-07|currTime:11:07:57
     * ticketNo : 6708113097500000046
     * gameName : Soccer
     * gameTypeName : Soccer 13
     * drawTime : 2019-11-05 18:00:00
     * drawName : SUNDAY-S13
     * boardCount : 1
     * message : Already Claimed
     * prizeAmt : 2466.68
     * totalPay : 2466.68 USD
     * totalClmPend : 0
     * claimStatus : false
     * messageCode : 10055
     * saleMerCode : NEWRMS
     */

    private int responseCode;
    private String responseMsg;
    private String currDate;
    private String ticketNo;
    private String gameName;
    private String gameTypeName;
    private String drawTime;
    private String drawName;
    private int boardCount;
    private String message;
    private String prizeAmt;
    private String totalPay;
    private int totalClmPend;
    private boolean claimStatus;
    private int messageCode = -1;
    private String saleMerCode;

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

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
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

    public String getPrizeAmt() {
        return prizeAmt;
    }

    public void setPrizeAmt(String prizeAmt) {
        this.prizeAmt = prizeAmt;
    }

    public String getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }

    public int getTotalClmPend() {
        return totalClmPend;
    }

    public void setTotalClmPend(int totalClmPend) {
        this.totalClmPend = totalClmPend;
    }

    public boolean isClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(boolean claimStatus) {
        this.claimStatus = claimStatus;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public String getSaleMerCode() {
        return saleMerCode;
    }

    public void setSaleMerCode(String saleMerCode) {
        this.saleMerCode = saleMerCode;
    }
}
