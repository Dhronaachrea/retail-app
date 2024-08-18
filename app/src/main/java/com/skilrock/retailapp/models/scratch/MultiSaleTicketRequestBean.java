package com.skilrock.retailapp.models.scratch;

import java.util.List;

public class MultiSaleTicketRequestBean {
    private List<String> bookNumberList;
    private String fromTicket;
    private String gameType;
    private String modelCode;
//    private int retailerOrgId;
    private String soldChannel;
    private String terminalId;
    private List<String> ticketNumberList;
    private String toTicket;
    private String userName;
    private String userSessionId;

    // Getters and Setters
    public List<String> getBookNumberList() {
        return bookNumberList;
    }

    public void setBookNumberList(List<String> bookNumberList) {
        this.bookNumberList = bookNumberList;
    }

    public String getFromTicket() {
        return fromTicket;
    }

    public void setFromTicket(String fromTicket) {
        this.fromTicket = fromTicket;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

//    public int getRetailerOrgId() {
//        return retailerOrgId;
//    }

//    public void setRetailerOrgId(int retailerOrgId) {
//        this.retailerOrgId = retailerOrgId;
//    }

    public String getSoldChannel() {
        return soldChannel;
    }

    public void setSoldChannel(String soldChannel) {
        this.soldChannel = soldChannel;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public List<String> getTicketNumberList() {
        return ticketNumberList;
    }

    public void setTicketNumberList(List<String> ticketNumberList) {
        this.ticketNumberList = ticketNumberList;
    }

    public String getToTicket() {
        return toTicket;
    }

    public void setToTicket(String toTicket) {
        this.toTicket = toTicket;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    @Override
    public String toString() {
        return "TicketResponse{" +
                "bookNumberList=" + bookNumberList +
                ", fromTicket='" + fromTicket + '\'' +
                ", gameType='" + gameType + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", soldChannel='" + soldChannel + '\'' +
                ", terminalId=" + terminalId +
                ", ticketNumberList=" + ticketNumberList +
                ", toTicket='" + toTicket + '\'' +
                ", userName='" + userName + '\'' +
                ", userSessionId='" + userSessionId + '\'' +
                '}';
    }

}
