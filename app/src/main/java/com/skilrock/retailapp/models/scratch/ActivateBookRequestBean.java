package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivateBookRequestBean {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userSessionId")
    @Expose
    private String userSessionId;
    @SerializedName("gameType")
    @Expose
    private String gameType;
    @SerializedName("bookNumbers")
    @Expose
    private String[] bookNumbers = null;
    @SerializedName("packNumbers")
    @Expose
    private String[] packNumbers = null;

    public String[] getPackNumbers() {
        return packNumbers;
    }

    public void setPackNumbers(String[] packNumbers) {
        this.packNumbers = packNumbers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String[] getBookNumbers() {
        return bookNumbers;
    }

    public void setBookNumbers(String[] bookNumbers) {
        this.bookNumbers = bookNumbers;
    }

    @NonNull
    @Override
    public String toString() {
        return "ActivateBookRequestBean{" + "userName='" + userName + '\'' + ", userSessionId='" + userSessionId + '\'' + ", gameType='" + gameType + '\'' + ", bookNumbers=" + bookNumbers[0] + '}';
    }
}
