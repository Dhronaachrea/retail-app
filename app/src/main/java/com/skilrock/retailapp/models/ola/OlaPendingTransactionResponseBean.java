package com.skilrock.retailapp.models.ola;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaPendingTransactionResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseData")
    @Expose
    private ArrayList<ResponseDatum> responseData = null;

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

    public ArrayList<ResponseDatum> getResponseData() {
        return responseData;
    }

    public void setResponseData(ArrayList<ResponseDatum> responseData) {
        this.responseData = responseData;
    }

    public static class ResponseDatum {

        @SerializedName("transactionId")
        @Expose
        private Integer transactionId;
        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("plrId")
        @Expose
        private Integer plrId;
        @SerializedName("transactionType")
        @Expose
        private String transactionType;
        @SerializedName("transactionDate")
        @Expose
        private String transactionDate;
        @SerializedName("status")
        @Expose
        private String status;

        public Integer getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Integer transactionId) {
            this.transactionId = transactionId;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Integer getPlrId() {
            return plrId;
        }

        public void setPlrId(Integer plrId) {
            this.plrId = plrId;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}