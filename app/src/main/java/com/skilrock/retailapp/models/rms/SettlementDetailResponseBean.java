package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SettlementDetailResponseBean {
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

    public class SettlementInfo {

        @SerializedName("organizationName")
        @Expose
        private String organizationName;
        @SerializedName("closingBalance")
        @Expose
        private String closingBalance;
        @SerializedName("doerUserName")
        @Expose
        private String doerUserName;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("settlementFromDate")
        @Expose
        private String settlementFromDate;
        @SerializedName("settlementToDate")
        @Expose
        private String settlementToDate;
        @SerializedName("openingBalance")
        @Expose
        private String openingBalance;

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getClosingBalance() {
            return closingBalance;
        }

        public void setClosingBalance(String closingBalance) {
            this.closingBalance = closingBalance;
        }

        public String getDoerUserName() {
            return doerUserName;
        }

        public void setDoerUserName(String doerUserName) {
            this.doerUserName = doerUserName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getOpeningBalance() {
            return openingBalance;
        }

        public void setOpeningBalance(String openingBalance) {
            this.openingBalance = openingBalance;
        }

        public String getSettlementFromDate() {
            return settlementFromDate;
        }

        public void setSettlementFromDate(String settlementFromDate) {
            this.settlementFromDate = settlementFromDate;
        }

        public String getSettlementToDate() {
            return settlementToDate;
        }

        public void setSettlementToDate(String settlementToDate) {
            this.settlementToDate = settlementToDate;
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
        @SerializedName("remark")
        @Expose
        private String remark;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            remark = remark;
        }
    }

    public class Data {

        @SerializedName("settlementInfo")
        @Expose
        private SettlementInfo settlementInfo;
        @SerializedName("transactions")
        @Expose
        private ArrayList<Transaction> transactions = null;

        public SettlementInfo getSettlementInfo() {
            return settlementInfo;
        }

        public void setSettlementInfo(SettlementInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }

        public ArrayList<Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(ArrayList<Transaction> transactions) {
            this.transactions = transactions;
        }
    }
}