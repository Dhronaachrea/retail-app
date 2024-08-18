package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashManagementPaymentRequestBean {

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("txnTypeCode")
    @Expose
    private String txnTypeCode;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTxnTypeCode() {
        return txnTypeCode;
    }

    public void setTxnTypeCode(String txnTypeCode) {
        this.txnTypeCode = txnTypeCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public String toString() {
        return "CashManagementPaymentResponseBean{" + "amount=" + amount + ", remarks='" + remarks + '\'' + ", txnTypeCode='" + txnTypeCode + '\'' + ", userId=" + userId + '}';
    }
}
