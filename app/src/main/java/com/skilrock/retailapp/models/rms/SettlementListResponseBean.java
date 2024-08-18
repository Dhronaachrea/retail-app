package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SettlementListResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

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

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public class ResponseData {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("data")
        @Expose
        private ArrayList<Data> data = null;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }
    }

    public static class Data {

        @SerializedName("fromDate")
        @Expose
        private String fromDate;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("organizationName")
        @Expose
        private String organizationName;
        @SerializedName("balancePostSettelment")
        @Expose
        private String balancePostSettelment;
        @SerializedName("toDate")
        @Expose
        private String toDate;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("doerUsername")
        @Expose
        private String doerUsername;
        @SerializedName("settlementid")
        @Expose
        private Integer settlementid;
        @SerializedName("userName")
        @Expose
        private String userName;

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getBalancePostSettelment() {
            return balancePostSettelment;
        }

        public void setBalancePostSettelment(String balancePostSettelment) {
            this.balancePostSettelment = balancePostSettelment;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public String getDoerUsername() {
            return doerUsername;
        }

        public void setDoerUsername(String doerUsername) {
            this.doerUsername = doerUsername;
        }

        public Integer getSettlementid() {
            return settlementid;
        }

        public void setSettlementid(Integer settlementid) {
            this.settlementid = settlementid;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}