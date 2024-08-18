package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubmitReturnRequestBean {
    @SerializedName("retUserName")
    @Expose
    private String retUserName;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userSessionId")
    @Expose
    private String userSessionId;
    @SerializedName("dlChallanNumber")
    @Expose
    private String dlChallanNumber;
    @SerializedName("packsToReturn")
    @Expose
    private ArrayList<PacksToReturn> packsToReturn = null;

    public String getRetUserName() {
        return retUserName;
    }

    public void setRetUserName(String retUserName) {
        this.retUserName = retUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDlChallanNumber() {
        return dlChallanNumber;
    }

    public void setDlChallanNumber(String dlChallanNumber) {
        this.dlChallanNumber = dlChallanNumber;
    }

    public ArrayList<PacksToReturn> getPacksToReturn() {
        return packsToReturn;
    }

    public void setPacksToReturn(ArrayList<PacksToReturn> packsToReturn) {
        this.packsToReturn = packsToReturn;
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
        return "SubmitReturnRequestBean{" + "userName='" + userName + '\'' + ", userSessionId='" + userSessionId + '\'' + ", packsToReturn=" + packsToReturn + '}';
    }

    public static class PacksToReturn {
        @SerializedName("gameId")
        @Expose
        private Integer gameId;
        @SerializedName("bookList")
        @Expose
        private ArrayList<String> bookList = null;

        public Integer getGameId() {
            return gameId;
        }

        public void setGameId(Integer gameId) {
            this.gameId = gameId;
        }

        public ArrayList<String> getBookList() {
            return bookList;
        }

        public void setBookList(ArrayList<String> bookList) {
            this.bookList = bookList;
        }

        @NonNull
        @Override
        public String toString() {
            return "PacksToReturn{" + "gameId=" + gameId + ", bookList=" + bookList + '}';
        }
    }
}