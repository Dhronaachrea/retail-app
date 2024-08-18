package com.skilrock.retailapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SbsWinVerifyResponse {
    @SerializedName("responseHttpStatusCode")
    @Expose
    private Integer responseHttpStatusCode;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

    public Integer getResponseHttpStatusCode() {
        return responseHttpStatusCode;
    }

    public void setResponseHttpStatusCode(Integer responseHttpStatusCode) {
        this.responseHttpStatusCode = responseHttpStatusCode;
    }

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
        private Data data;

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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }


    public class Data {

        @SerializedName("ticketNo")
        @Expose
        private String ticketNo;
        @SerializedName("ticket")
        @Expose
        private Ticket ticket;
        @SerializedName("transactions")
        @Expose
        private List<Transaction> transactions = null;

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public Ticket getTicket() {
            return ticket;
        }

        public void setTicket(Ticket ticket) {
            this.ticket = ticket;
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<Transaction> transactions) {
            this.transactions = transactions;
        }

    }

    public class Ticket {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("year")
        @Expose
        private Integer year;
        @SerializedName("month")
        @Expose
        private Integer month;
        @SerializedName("day")
        @Expose
        private Integer day;
        @SerializedName("retailerUserId")
        @Expose
        private Integer retailerUserId;
        @SerializedName("deviceId")
        @Expose
        private String deviceId;
        @SerializedName("randomNo")
        @Expose
        private Integer randomNo;
        @SerializedName("rmsServiceId")
        @Expose
        private Integer rmsServiceId;
        @SerializedName("rmsGameId")
        @Expose
        private Integer rmsGameId;
        @SerializedName("dayOfYear")
        @Expose
        private Integer dayOfYear;
        @SerializedName("retailerCode")
        @Expose
        private Integer retailerCode;
        @SerializedName("txnOfDay")
        @Expose
        private Integer txnOfDay;
        @SerializedName("ticketNo")
        @Expose
        private String ticketNo;
        @SerializedName("ticketType")
        @Expose
        private String ticketType;
        @SerializedName("txnCount")
        @Expose
        private Integer txnCount;
        @SerializedName("saleAmount")
        @Expose
        private Double saleAmount;
        @SerializedName("payoutAmount")
        @Expose
        private Double payoutAmount;
        @SerializedName("payoutRetailerUserId")
        @Expose
        private Object payoutRetailerUserId;
        @SerializedName("paidAt")
        @Expose
        private Object paidAt;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("reprintCount")
        @Expose
        private Integer reprintCount;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public Integer getRetailerUserId() {
            return retailerUserId;
        }

        public void setRetailerUserId(Integer retailerUserId) {
            this.retailerUserId = retailerUserId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getRandomNo() {
            return randomNo;
        }

        public void setRandomNo(Integer randomNo) {
            this.randomNo = randomNo;
        }

        public Integer getRmsServiceId() {
            return rmsServiceId;
        }

        public void setRmsServiceId(Integer rmsServiceId) {
            this.rmsServiceId = rmsServiceId;
        }

        public Integer getRmsGameId() {
            return rmsGameId;
        }

        public void setRmsGameId(Integer rmsGameId) {
            this.rmsGameId = rmsGameId;
        }

        public Integer getDayOfYear() {
            return dayOfYear;
        }

        public void setDayOfYear(Integer dayOfYear) {
            this.dayOfYear = dayOfYear;
        }

        public Integer getRetailerCode() {
            return retailerCode;
        }

        public void setRetailerCode(Integer retailerCode) {
            this.retailerCode = retailerCode;
        }

        public Integer getTxnOfDay() {
            return txnOfDay;
        }

        public void setTxnOfDay(Integer txnOfDay) {
            this.txnOfDay = txnOfDay;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public String getTicketType() {
            return ticketType;
        }

        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
        }

        public Integer getTxnCount() {
            return txnCount;
        }

        public void setTxnCount(Integer txnCount) {
            this.txnCount = txnCount;
        }

        public Double getSaleAmount() {
            return saleAmount;
        }

        public void setSaleAmount(Double saleAmount) {
            this.saleAmount = saleAmount;
        }

        public Double getPayoutAmount() {
            return payoutAmount;
        }

        public void setPayoutAmount(Double payoutAmount) {
            this.payoutAmount = payoutAmount;
        }

        public Object getPayoutRetailerUserId() {
            return payoutRetailerUserId;
        }

        public void setPayoutRetailerUserId(Object payoutRetailerUserId) {
            this.payoutRetailerUserId = payoutRetailerUserId;
        }

        public Object getPaidAt() {
            return paidAt;
        }

        public void setPaidAt(Object paidAt) {
            this.paidAt = paidAt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getReprintCount() {
            return reprintCount;
        }

        public void setReprintCount(Integer reprintCount) {
            this.reprintCount = reprintCount;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Transaction {

        @SerializedName("transactionId")
        @Expose
        private Integer transactionId;
        @SerializedName("ticketNo")
        @Expose
        private String ticketNo;
        @SerializedName("playerId")
        @Expose
        private Integer playerId;
        @SerializedName("ticketType")
        @Expose
        private String ticketType;
        @SerializedName("transactionDate")
        @Expose
        private String transactionDate;
        @SerializedName("stake")
        @Expose
        private Double stake;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("winningStatus")
        @Expose
        private String winningStatus;
        @SerializedName("payout")
        @Expose
        private Double payout;
        @SerializedName("payoutStatus")
        @Expose
        private String payoutStatus;
        @SerializedName("selectedSystems")
        @Expose
        private String selectedSystems;
        @SerializedName("mtsTicketId")
        @Expose
        private String mtsTicketId;
        @SerializedName("winnableAmount")
        @Expose
        private Double winnableAmount;

        public Integer getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Integer transactionId) {
            this.transactionId = transactionId;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public Integer getPlayerId() {
            return playerId;
        }

        public void setPlayerId(Integer playerId) {
            this.playerId = playerId;
        }

        public String getTicketType() {
            return ticketType;
        }

        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
        }

        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
        }

        public Double getStake() {
            return stake;
        }

        public void setStake(Double stake) {
            this.stake = stake;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWinningStatus() {
            return winningStatus;
        }

        public void setWinningStatus(String winningStatus) {
            this.winningStatus = winningStatus;
        }

        public Double getPayout() {
            return payout;
        }

        public void setPayout(Double payout) {
            this.payout = payout;
        }

        public String getPayoutStatus() {
            return payoutStatus;
        }

        public void setPayoutStatus(String payoutStatus) {
            this.payoutStatus = payoutStatus;
        }

        public String getSelectedSystems() {
            return selectedSystems;
        }

        public void setSelectedSystems(String selectedSystems) {
            this.selectedSystems = selectedSystems;
        }

        public String getMtsTicketId() {
            return mtsTicketId;
        }

        public void setMtsTicketId(String mtsTicketId) {
            this.mtsTicketId = mtsTicketId;
        }

        public Double getWinnableAmount() {
            return winnableAmount;
        }

        public void setWinnableAmount(Double winnableAmount) {
            this.winnableAmount = winnableAmount;
        }

    }

}
