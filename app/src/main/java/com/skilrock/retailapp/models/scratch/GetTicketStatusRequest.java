package com.skilrock.retailapp.models.scratch;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetTicketStatusRequest {

    @SerializedName("ticketNumberList")
    List<String> ticketNumberList;

    @SerializedName("userName")
    String userName;

    @SerializedName("userSessionId")
    String userSessionId;


    public void setTicketNumberList(List<String> ticketNumberList) {
        this.ticketNumberList = ticketNumberList;
    }
    public List<String> getTicketNumberList() {
        return ticketNumberList;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }
    public String getUserSessionId() {
        return userSessionId;
    }

}