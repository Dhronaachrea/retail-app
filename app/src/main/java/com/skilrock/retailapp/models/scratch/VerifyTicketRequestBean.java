package com.skilrock.retailapp.models.scratch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VerifyTicketRequestBean {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userSessionId")
    @Expose
    private String userSessionId;
    @SerializedName("pwtTicketList")
    @Expose
    private ArrayList<PwtTicketList> pwtTicketList = null;

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

    public ArrayList<PwtTicketList> getPwtTicketList() {
        return pwtTicketList;
    }

    public void setPwtTicketList(ArrayList<PwtTicketList> pwtTicketList) {
        this.pwtTicketList = pwtTicketList;
    }

    public static class PwtTicketList {

        @SerializedName("ticketNumber")
        @Expose
        private String ticketNumber;
        @SerializedName("virnNumber")
        @Expose
        private String virnNumber;

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

        @Override
        public String toString() {
            return "PwtTicketList{" + "ticketNumber='" + ticketNumber + '\'' + ", virnNumber='" + virnNumber + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "VerifyTicketRequestBean{" + "userName='" + userName + '\'' + ", userSessionId='" + userSessionId + '\'' + ", pwtTicketList=" + pwtTicketList + '}';
    }
}
