package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LedgerReportResponseBean {

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

    public static class ResponseData {

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

    public static class Data {

        @SerializedName("balance")
        @Expose
        private Balance balance;
        @SerializedName("transaction")
        @Expose
        private ArrayList<Transaction> transaction = null;

        public Balance getBalance() {
            return balance;
        }

        public void setBalance(Balance balance) {
            this.balance = balance;
        }

        public ArrayList<Transaction> getTransaction() {
            return transaction;
        }

        public void setTransaction(ArrayList<Transaction> transaction) {
            this.transaction = transaction;
        }
    }

    public static class Balance {

        @SerializedName("closingBalance")
        @Expose
        private String closingBalance;
        @SerializedName("openingBalance")
        @Expose
        private String openingBalance;
        @SerializedName("rawOpeningBalance")
        @Expose
        private Double rawOpeningBalance;
        @SerializedName("rawClosingBalance")
        @Expose
        private Double rawClosingBalance;

        public String getClosingBalance() {
            return closingBalance;
        }

        public void setClosingBalance(String closingBalance) {
            this.closingBalance = closingBalance;
        }

        public String getOpeningBalance() {
            return openingBalance;
        }

        public void setOpeningBalance(String openingBalance) {
            this.openingBalance = openingBalance;
        }

        public Double getRawOpeningBalance() {
            return rawOpeningBalance;
        }

        public void setRawOpeningBalance(Double rawOpeningBalance) {
            this.rawOpeningBalance = rawOpeningBalance;
        }

        public Double getRawClosingBalance() {
            return rawClosingBalance;
        }

        public void setRawClosingBalance(Double rawClosingBalance) {
            this.rawClosingBalance = rawClosingBalance;
        }
    }

    public static class Transaction {

        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("transactionMode")
        @Expose
        private String transactionMode;
        @SerializedName("particular")
        @Expose
        private String particular;
        @SerializedName("serviceDisplayName")
        @Expose
        private String serviceDisplayName;
        @SerializedName("availableBalance")
        @Expose
        private String availableBalance;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTransactionMode() {
            return transactionMode;
        }

        public void setTransactionMode(String transactionMode) {
            this.transactionMode = transactionMode;
        }

        public String getParticular() {
            return particular;
        }

        public void setParticular(String particular) {
            this.particular = particular;
        }

        public String getServiceDisplayName() {
            return serviceDisplayName;
        }

        public void setServiceDisplayName(String serviceDisplayName) {
            this.serviceDisplayName = serviceDisplayName;
        }

        public String getAvailableBalance() {
            return availableBalance;
        }

        public void setAvailableBalance(String availableBalance) {
            this.availableBalance = availableBalance;
        }
    }
}