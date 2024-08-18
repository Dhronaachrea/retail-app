package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaWithdrawalResponseBean {

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

        @SerializedName("retailerBalance")
        @Expose
        private Double retailerBalance;

        public Double getRetailerBalance() {
            return retailerBalance;
        }

        public void setRetailerBalance(Double retailerBalance) {
            this.retailerBalance = retailerBalance;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "retailerBalance=" + retailerBalance + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "OlaWithdrawalResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
