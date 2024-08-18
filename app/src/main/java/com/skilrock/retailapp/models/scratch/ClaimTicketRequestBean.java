package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimTicketRequestBean {

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("barcodeNumber")
    @Expose
    private String barcodeNumber;

    @SerializedName("userSessionId")
    @Expose
    private String userSessionId;

    @SerializedName("requestId")
    @Expose
    private String requestId;

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
        return "ClaimTicketRequestBean{" + "userName='" + userName + '\'' + ", barcodeNumber='" + barcodeNumber + '\'' + ", userSessionId='" + userSessionId + '\'' + ", requestId='" + requestId + '\'' + ", modelCode='" + modelCode + '\'' + ", terminalId='" + terminalId + '\'' + '}';
    }
}
