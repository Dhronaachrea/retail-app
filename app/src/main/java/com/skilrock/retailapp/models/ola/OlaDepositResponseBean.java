package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaDepositResponseBean {

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

        @SerializedName("txnId")
        @Expose
        private String txnId;

        @SerializedName("txnDate")
        @Expose
        private String txnDate;

        @SerializedName("plrTxnId")
        @Expose
        private String plrTxnId;

        @SerializedName("balancePreTxn")
        @Expose
        private Double balancePreTxn;

        @SerializedName("balancePostTxn")
        @Expose
        private Double balancePostTxn;

        public String getTxnId() {
            return txnId;
        }

        public void setTxnId(String txnId) {
            this.txnId = txnId;
        }

        public String getTxnDate() {
            return txnDate;
        }

        public void setTxnDate(String txnDate) {
            this.txnDate = txnDate;
        }

        public String getPlrTxnId() {
            return plrTxnId;
        }

        public void setPlrTxnId(String plrTxnId) {
            this.plrTxnId = plrTxnId;
        }

        public Double getBalancePreTxn() {
            return balancePreTxn;
        }

        public void setBalancePreTxn(Double balancePreTxn) {
            this.balancePreTxn = balancePreTxn;
        }

        public Double getBalancePostTxn() {
            return balancePostTxn;
        }

        public void setBalancePostTxn(Double balancePostTxn) {
            this.balancePostTxn = balancePostTxn;
        }

        @Override
        public String toString() {
            return "ResponseData{" + "txnId=" + txnId + ", txnDate='" + txnDate + '\'' + ", plrTxnId=" + plrTxnId + ", balancePreTxn=" + balancePreTxn + ", balancePostTxn=" + balancePostTxn + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "OlaDepositResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
