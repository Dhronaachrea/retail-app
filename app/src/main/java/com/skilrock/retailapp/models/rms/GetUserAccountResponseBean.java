package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetUserAccountResponseBean {

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

        @SerializedName("transactions")
        @Expose
        private ArrayList<Transaction> transactions = null;
        @SerializedName("settelmentInfo")
        @Expose
        private SettelmentInfo settelmentInfo;

        public ArrayList<Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(ArrayList<Transaction> transactions) {
            this.transactions = transactions;
        }

        public SettelmentInfo getSettelmentInfo() {
            return settelmentInfo;
        }

        public void setSettelmentInfo(SettelmentInfo settelmentInfo) {
            this.settelmentInfo = settelmentInfo;
        }
    }

    public class SettelmentInfo {

        @SerializedName("totalBalance")
        @Expose
        private String totalBalance;
        @SerializedName("openingBalance")
        @Expose
        private String openingBalance;
        @SerializedName("unformattedTotalBalance")
        @Expose
        private String unformattedTotalBalance;

        public String getTotalBalance() {
            return totalBalance;
        }

        public void setTotalBalance(String totalBalance) {
            this.totalBalance = totalBalance;
        }

        public String getOpeningBalance() {
            return openingBalance;
        }

        public void setOpeningBalance(String openingBalance) {
            this.openingBalance = openingBalance;
        }

        public String getUnformattedTotalBalance() {
            return unformattedTotalBalance;
        }

        public void setUnformattedTotalBalance(String unformattedTotalBalance) {
            this.unformattedTotalBalance = unformattedTotalBalance;
        }
    }

    public class Transaction {

        @SerializedName("fromDate")
        @Expose
        private String fromDate;
        @SerializedName("txnValue")
        @Expose
        private String txnValue;
        @SerializedName("service")
        @Expose
        private String service;
        @SerializedName("toDate")
        @Expose
        private String toDate;
        @SerializedName("txnType")
        @Expose
        private String txnType;
        @SerializedName("balancePostTransaction")
        @Expose
        private String balancePostTransaction;

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getTxnValue() {
            return txnValue;
        }

        public void setTxnValue(String txnValue) {
            this.txnValue = txnValue;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public String getTxnType() {
            return txnType;
        }

        public void setTxnType(String txnType) {
            this.txnType = txnType;
        }

        public String getBalancePostTransaction() {
            return balancePostTransaction;
        }

        public void setBalancePostTransaction(String balancePostTransaction) {
            this.balancePostTransaction = balancePostTransaction;
        }
    }
}