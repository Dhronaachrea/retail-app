package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SaleReportResponseBean {

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

        @SerializedName("orgTypeCode")
        @Expose
        private String orgTypeCode;
        @SerializedName("total")
        @Expose
        private Total total;
        @SerializedName("orgName")
        @Expose
        private String orgName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("transactionData")
        @Expose
        private ArrayList<TransactionDatum> transactionData = null;
        @SerializedName("orgId")
        @Expose
        private String orgId;

        public String getOrgTypeCode() {
            return orgTypeCode;
        }

        public void setOrgTypeCode(String orgTypeCode) {
            this.orgTypeCode = orgTypeCode;
        }

        public Total getTotal() {
            return total;
        }

        public void setTotal(Total total) {
            this.total = total;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public ArrayList<TransactionDatum> getTransactionData() {
            return transactionData;
        }

        public void setTransactionData(ArrayList<TransactionDatum> transactionData) {
            this.transactionData = transactionData;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

    }
    public static class Total {

        @SerializedName("sumOfWinning")
        @Expose
        private String sumOfWinning;
        @SerializedName("netSale")
        @Expose
        private String netSale;
        @SerializedName("sumOfSaleReturn")
        @Expose
        private String sumOfSaleReturn;
        @SerializedName("sumOfSale")
        @Expose
        private String sumOfSale;
        @SerializedName("rawNetSale")
        @Expose
        private String rawNetSale;
        @SerializedName("netCommision")
        @Expose
        private String netCommision;
        @SerializedName("rawNetCommission")
        @Expose
        private String rawNetCommission;

        public String getNetCommision() {
            return netCommision;
        }

        public void setNetCommision(String netCommision) {
            this.netCommision = netCommision;
        }

        public String getRawNetCommission() {
            return rawNetCommission;
        }

        public void setRawNetCommission(String rawNetCommission) {
            this.rawNetCommission = rawNetCommission;
        }

        public String getSumOfWinning() {
            return sumOfWinning;
        }

        public void setSumOfWinning(String sumOfWinning) {
            this.sumOfWinning = sumOfWinning;
        }

        public String getNetSale() {
            return netSale;
        }

        public void setNetSale(String netSale) {
            this.netSale = netSale;
        }

        public String getSumOfSaleReturn() {
            return sumOfSaleReturn;
        }

        public void setSumOfSaleReturn(String sumOfSaleReturn) {
            this.sumOfSaleReturn = sumOfSaleReturn;
        }

        public String getSumOfSale() {
            return sumOfSale;
        }

        public void setSumOfSale(String sumOfSale) {
            this.sumOfSale = sumOfSale;
        }

        public String getRawNetSale() {
            return rawNetSale;
        }

        public void setRawNetSale(String rawNetSale) {
            this.rawNetSale = rawNetSale;
        }

        @NonNull
        @Override
        public String toString() {
            return "Total{" + "sumOfWinning='" + sumOfWinning + '\'' + ", netSale='" + netSale + '\'' + ", sumOfSaleReturn='" + sumOfSaleReturn + '\'' + ", sumOfSale='" + sumOfSale + '\'' + ", rawNetSale='" + rawNetSale + '\'' + ", netCommision='" + netCommision + '\'' + ", rawNetCommission='" + rawNetCommission + '\'' + '}';
        }
    }
    public static class TransactionDatum {

        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("txnValue")
        @Expose
        private String txnValue;
        @SerializedName("orgCommValue")
        @Expose
        private String orgCommValue;
        @SerializedName("balancePostTxn")
        @Expose
        private String balancePostTxn;
        @SerializedName("txnTypeCode")
        @Expose
        private String txnTypeCode;
        @SerializedName("orgTdsValue")
        @Expose
        private String orgTdsValue;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("orgDueAmount")
        @Expose
        private String orgDueAmount;
        @SerializedName("orgNetAmount")
        @Expose
        private String orgNetAmount;
        @SerializedName("txnId")
        @Expose
        private String txnId;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getTxnValue() {
            return txnValue;
        }

        public void setTxnValue(String txnValue) {
            this.txnValue = txnValue;
        }

        public String getOrgCommValue() {
            return orgCommValue;
        }

        public void setOrgCommValue(String orgCommValue) {
            this.orgCommValue = orgCommValue;
        }

        public String getBalancePostTxn() {
            return balancePostTxn;
        }

        public void setBalancePostTxn(String balancePostTxn) {
            this.balancePostTxn = balancePostTxn;
        }

        public String getTxnTypeCode() {
            return txnTypeCode;
        }

        public void setTxnTypeCode(String txnTypeCode) {
            this.txnTypeCode = txnTypeCode;
        }

        public String getOrgTdsValue() {
            return orgTdsValue;
        }

        public void setOrgTdsValue(String orgTdsValue) {
            this.orgTdsValue = orgTdsValue;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrgDueAmount() {
            return orgDueAmount;
        }

        public void setOrgDueAmount(String orgDueAmount) {
            this.orgDueAmount = orgDueAmount;
        }

        public String getTxnId() {
            return txnId;
        }

        public void setTxnId(String txnId) {
            this.txnId = txnId;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getOrgNetAmount() {
            return orgNetAmount;
        }

        public void setOrgNetAmount(String orgNetAmount) {
            this.orgNetAmount = orgNetAmount;
        }
    }
}