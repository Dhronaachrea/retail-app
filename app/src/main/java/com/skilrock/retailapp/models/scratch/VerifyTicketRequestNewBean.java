package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyTicketRequestNewBean {

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("barcodeNumber")
    @Expose
    private String barcodeNumber;

    @SerializedName("userSessionId")
    @Expose
    private String userSessionId;

    @SerializedName("modelCode")
    @Expose
    private String modelCode;

    @SerializedName("terminalId")
    @Expose
    private String terminalId;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    @NonNull
    @Override
    public String toString() {
        return "VerifyTicketRequestNewBean{" + "userName='" + userName + '\'' + ", barcodeNumber='" + barcodeNumber + '\'' + ", userSessionId='" + userSessionId + '\'' + ", modelCode='" + modelCode + '\'' + ", terminalId='" + terminalId + '\'' + '}';
    }
}
