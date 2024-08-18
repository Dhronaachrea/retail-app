package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

public class OlaRegistrationPrintBean {

    private String date;
    private String userNameTag;
    private String userNameValue;
    private String passwordTag;
    private String passwordValue;
    private String depositAmountTag;
    private String depositAmountValue;
    private boolean isDepositError;

    public boolean isDepositError() {
        return isDepositError;
    }

    public void setDepositError(boolean depositError) {
        isDepositError = depositError;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserNameTag() {
        return userNameTag;
    }

    public void setUserNameTag(String userNameTag) {
        this.userNameTag = userNameTag;
    }

    public String getUserNameValue() {
        return userNameValue;
    }

    public void setUserNameValue(String userNameValue) {
        this.userNameValue = userNameValue;
    }

    public String getPasswordTag() {
        return passwordTag;
    }

    public void setPasswordTag(String passwordTag) {
        this.passwordTag = passwordTag;
    }

    public String getPasswordValue() {
        return passwordValue;
    }

    public void setPasswordValue(String passwordValue) {
        this.passwordValue = passwordValue;
    }

    public String getDepositAmountTag() {
        return depositAmountTag;
    }

    public void setDepositAmountTag(String depositAmountTag) {
        this.depositAmountTag = depositAmountTag;
    }

    public String getDepositAmountValue() {
        return depositAmountValue;
    }

    public void setDepositAmountValue(String depositAmountValue) {
        this.depositAmountValue = depositAmountValue;
    }

    @NonNull
    @Override
    public String toString() {
        return "OlaRegistrationPrintBean{" + "date='" + date + '\'' + ", userNameTag='" + userNameTag + '\'' + ", userNameValue='" + userNameValue + '\'' + ", passwordTag='" + passwordTag + '\'' + ", passwordValue='" + passwordValue + '\'' + ", depositAmountTag='" + depositAmountTag + '\'' + ", depositAmountValue='" + depositAmountValue + '\'' + ", isDepositError=" + isDepositError + '}';
    }
}
