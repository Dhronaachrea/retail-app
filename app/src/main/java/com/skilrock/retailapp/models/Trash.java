package com.skilrock.retailapp.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Trash {

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

        @Override
        public String toString() {
            return "ResponseData{" + "message='" + message + '\'' + ", statusCode=" + statusCode + ", data=" + data + '}';
        }

        public static class Data {

            @SerializedName("createdAt")
            @Expose
            private String createdAt;

            @SerializedName("txnValue")
            @Expose
            private String txnValue;

            @SerializedName("narration")
            @Expose
            private String narration;

            @SerializedName("txnType")
            @Expose
            private String txnType;

            @SerializedName("txnBy")
            @Expose
            private String txnBy;

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

            public String getNarration() {
                return narration;
            }

            public void setNarration(String narration) {
                this.narration = narration;
            }

            public String getTxnType() {
                return txnType;
            }

            public void setTxnType(String txnType) {
                this.txnType = txnType;
            }

            public String getTxnBy() {
                return txnBy;
            }

            public void setTxnBy(String txnBy) {
                this.txnBy = txnBy;
            }

            public String getTxnId() {
                return txnId;
            }

            public void setTxnId(String txnId) {
                this.txnId = txnId;
            }
        }

    }

    @NonNull
    @Override
    public String toString() {
        return "Trash{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }



}
