package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class MultiClaimTicketRequest implements Serializable {

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("barcodeNumber")
    @Expose
    private String barcodeNumber;

    @SerializedName("userSessionId")
    @Expose
    private String userSessionId;

    @SerializedName("barcodeNumberList")
    @Expose
    private List<String> barcodeNumberList;

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

    public List<String> getBarcodeNumberList() {
        return barcodeNumberList;
    }

    public void setBarcodeNumberList(List<String> barcodeNumberList) {
        this.barcodeNumberList = barcodeNumberList;
    }

    @NonNull
    @Override
    public String toString() {
        return "MultiClaimTicketRequest{" +
                "userName='" + userName + '\'' +
                ", barcodeNumber='" + barcodeNumber + '\'' +
                ", userSessionId='" + userSessionId + '\'' +
                ", barcodeNumberList=" + barcodeNumberList +
                '}';
    }
}
