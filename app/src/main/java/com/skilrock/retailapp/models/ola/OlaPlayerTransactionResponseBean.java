package com.skilrock.retailapp.models.ola;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OlaPlayerTransactionResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("responseData")
    @Expose
    private ArrayList<ResponseData> responseData = null;

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

    public ArrayList<ResponseData> getResponseData() {
        return responseData;
    }

    public void setResponseData(ArrayList<ResponseData> responseData) {
        this.responseData = responseData;
    }

    public static class ResponseData {

        @SerializedName("transactionId")
        @Expose
        private Integer transactionId;

        @SerializedName("transactionDate")
        @Expose
        private String transactionDate;

        @SerializedName("particular")
        @Expose
        private String particular;

        @SerializedName("txnType")
        @Expose
        private String txnType;

        @SerializedName("currencyId")
        @Expose
        private Integer currencyId;

        @SerializedName("debitAmount")
        @Expose
        private Double debitAmount;

        @SerializedName("txnAmount")
        @Expose
        private Double txnAmount;

        @SerializedName("balance")
        @Expose
        private Double balance;

        @SerializedName("openingBalance")
        @Expose
        private Double openingBalance;

        @SerializedName("subwalletTxn")
        @Expose
        private String subwalletTxn;

        @SerializedName("currency")
        @Expose
        private String currency;

        @SerializedName("withdrawableBalance")
        @Expose
        private Double withdrawableBalance;

        public Integer getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Integer transactionId) {
            this.transactionId = transactionId;
        }

        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
        }

        public String getParticular() {
            return particular;
        }

        public void setParticular(String particular) {
            this.particular = particular;
        }

        public String getTxnType() {
            return txnType;
        }

        public void setTxnType(String txnType) {
            this.txnType = txnType;
        }

        public Integer getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(Integer currencyId) {
            this.currencyId = currencyId;
        }

        public Double getDebitAmount() {
            return debitAmount;
        }

        public void setDebitAmount(Double debitAmount) {
            this.debitAmount = debitAmount;
        }

        public Double getTxnAmount() {
            return txnAmount;
        }

        public void setTxnAmount(Double txnAmount) {
            this.txnAmount = txnAmount;
        }

        public Double getBalance() {
            return balance;
        }

        public void setBalance(Double balance) {
            this.balance = balance;
        }

        public Double getOpeningBalance() {
            return openingBalance;
        }

        public void setOpeningBalance(Double openingBalance) {
            this.openingBalance = openingBalance;
        }

        public String getSubwalletTxn() {
            return subwalletTxn;
        }

        public void setSubwalletTxn(String subwalletTxn) {
            this.subwalletTxn = subwalletTxn;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Double getWithdrawableBalance() {
            return withdrawableBalance;
        }

        public void setWithdrawableBalance(Double withdrawableBalance) {
            this.withdrawableBalance = withdrawableBalance;
        }

    }


}
