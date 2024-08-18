package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SummarizedReportResponseBean {

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

    public class Data {

        @SerializedName("ledgerData")
        @Expose
        private ArrayList<LedgerDatum> ledgerData = null;
        @SerializedName("closingBalance")
        @Expose
        private String clossingBalance;
        @SerializedName("rawOpeningBalance")
        @Expose
        private Double rawOpeningBalance;
        @SerializedName("rawClosingBalance")
        @Expose
        private Double rawClosingBalance;
        @SerializedName("openingBalance")
        @Expose
        private String openingBalance;


        public ArrayList<LedgerDatum> getLedgerData() {
            return ledgerData;
        }

        public void setLedgerData(ArrayList<LedgerDatum> ledgerData) {
            this.ledgerData = ledgerData;
        }

        public String getClossingBalance() {
            return clossingBalance;
        }

        public void setClossingBalance(String clossingBalance) {
            this.clossingBalance = clossingBalance;
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

    public class UserLedgerDatum {

        @SerializedName("txnData")
        @Expose
        private List<TxnDatum> txnData = null;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("userId")
        @Expose
        private String userId;

        public List<TxnDatum> getTxnData() {
            return txnData;
        }

        public void setTxnData(List<TxnDatum> txnData) {
            this.txnData = txnData;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public class LedgerDatum {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("txnData")
        @Expose
        private ArrayList<TxnDatum> txnData = null;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("key1")
        @Expose
        private String key1;
        @SerializedName("key2")
        @Expose
        private String key2;
        @SerializedName("rawNetAmount")
        @Expose
        private Double rawNetAmount;
        @SerializedName("netAmount")
        @Expose
        private String netAmount;
        @SerializedName("serviceCode")
        @Expose
        private String serviceCode;
        @SerializedName("key1Name")
        @Expose
        private String key1Name;
        @SerializedName("serviceName")
        @Expose
        private String serviceName;
        @SerializedName("key2Name")
        @Expose
        private String key2Name;

        public String getKey1() {
            return key1;
        }

        public void setKey1(String key1) {
            this.key1 = key1;
        }

        public String getKey2() {
            return key2;
        }

        public void setKey2(String key2) {
            this.key2 = key2;
        }

        public String getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(String netAmount) {
            this.netAmount = netAmount;
        }

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getKey1Name() {
            return key1Name;
        }

        public void setKey1Name(String key1Name) {
            this.key1Name = key1Name;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getKey2Name() {
            return key2Name;
        }

        public void setKey2Name(String key2Name) {
            this.key2Name = key2Name;
        }

        public ArrayList<TxnDatum> getTxnData() {
            return txnData;
        }

        public void setTxnData(ArrayList<TxnDatum> txnData) {
            this.txnData = txnData;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Double getRawNetAmount() {
            return rawNetAmount;
        }

        public void setRawNetAmount(Double rawNetAmount) {
            this.rawNetAmount = rawNetAmount;
        }
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

    public class TxnDatum {

        @SerializedName("key1")
        @Expose
        private String key1;
        @SerializedName("key2")
        @Expose
        private String key2;
        @SerializedName("netAmount")
        @Expose
        private String netAmount;
        @SerializedName("rawNetAmount")
        @Expose
        private Double rawNetAmount;
        @SerializedName("serviceCode")
        @Expose
        private String serviceCode;
        @SerializedName("key1Name")
        @Expose
        private String key1Name;
        @SerializedName("serviceName")
        @Expose
        private String serviceName;
        @SerializedName("key2Name")
        @Expose
        private String key2Name;

        public String getKey1() {
            return key1;
        }

        public void setKey1(String key1) {
            this.key1 = key1;
        }

        public String getKey2() {
            return key2;
        }

        public void setKey2(String key2) {
            this.key2 = key2;
        }

        public String getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(String netAmount) {
            this.netAmount = netAmount;
        }

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getKey1Name() {
            return key1Name;
        }

        public void setKey1Name(String key1Name) {
            this.key1Name = key1Name;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getKey2Name() {
            return key2Name;
        }

        public void setKey2Name(String key2Name) {
            this.key2Name = key2Name;
        }

        public Double getRawNetAmount() {
            return rawNetAmount;
        }

        public void setRawNetAmount(Double rawNetAmount) {
            this.rawNetAmount = rawNetAmount;
        }
    }
}