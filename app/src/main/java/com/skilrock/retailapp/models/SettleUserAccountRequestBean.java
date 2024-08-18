package com.skilrock.retailapp.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettleUserAccountRequestBean {

    @SerializedName("settleAmount")
    @Expose
    private String settleAmount;

    @SerializedName("settleAction")
    @Expose
    private String settleAction;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    public String getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(String settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getSettleAction() {
        return settleAction;
    }

    public void setSettleAction(String settleAction) {
        this.settleAction = settleAction;
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
        return "SettleUserAccountRequestBean{" + "settleAmount=" + settleAmount + ", settleAction='" + settleAction + '\'' + ", userId=" + userId + '}';
    }
}
