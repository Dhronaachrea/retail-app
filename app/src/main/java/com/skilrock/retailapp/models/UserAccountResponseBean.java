package com.skilrock.retailapp.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserAccountResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public class ResponseData {

        @SerializedName("data")
        @Expose
        private Data data;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

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

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "data=" + data + ", message='" + message + '\'' + ", statusCode=" + statusCode + '}';
        }
    }

    public class SettelmentInfo {

        @SerializedName("totalBalance")
        @Expose
        private String totalBalance;
        @SerializedName("openingBalance")
        @Expose
        private String openingBalance;
        @SerializedName("settlementFromDate")
        @Expose
        private String settlementFromDate;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("settlementToDate")
        @Expose
        private String settlementToDate;
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

        @NonNull
        @Override
        public String toString() {
            return "SettelmentInfo{" + "totalBalance='" + totalBalance + '\'' + ", openingBalance='" + openingBalance + '\'' + ", unformattedTotalBalance='" + unformattedTotalBalance + '\'' + '}';
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
        @SerializedName("txnType")
        @Expose
        private String txnType;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("remark")
        @Expose
        private String remark;
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

        @NonNull
        @Override
        public String toString() {
            return "Transaction{" + "fromDate='" + fromDate + '\'' + ", txnValue='" + txnValue + '\'' + ", service='" + service + '\'' + ", toDate='" + toDate + '\'' + ", txnType='" + txnType + '\'' + ", balancePostTransaction='" + balancePostTransaction + '\'' + '}';
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
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

        @NonNull
        @Override
        public String toString() {
            return "Data{" + "transactions=" + transactions + ", settelmentInfo=" + settelmentInfo + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "UserAccountResponseBean{" + "responseCode=" + responseCode + ", responseData=" + responseData + ", responseMessage='" + responseMessage + '\'' + '}';
    }
}