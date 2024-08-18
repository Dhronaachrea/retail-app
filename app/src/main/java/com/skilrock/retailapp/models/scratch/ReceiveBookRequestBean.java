package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReceiveBookRequestBean {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userSessionId")
    @Expose
    private String userSessionId;
    @SerializedName("dlChallanNumber")
    @Expose
    private String dlChallanNumber;
    @SerializedName("receiveType")
    @Expose
    private String receiveType;
    @SerializedName("bookInfo")
    @Expose
    private ArrayList<BookInfo> bookInfo = null;

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

    public String getDlChallanNumber() {
        return dlChallanNumber;
    }

    public void setDlChallanNumber(String dlChallanNumber) {
        this.dlChallanNumber = dlChallanNumber;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public ArrayList<BookInfo> getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(ArrayList<BookInfo> bookInfo) {
        this.bookInfo = bookInfo;
    }

    public static class BookInfo {

        @SerializedName("gameType")
        @Expose
        private String gameType;
        @SerializedName("gameId")
        @Expose
        private Integer gameId;
        @SerializedName("bookList")
        @Expose
        private ArrayList<String> bookList = null;

        public String getGameType() {
            return gameType;
        }

        public void setGameType(String gameType) {
            this.gameType = gameType;
        }

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
            return "BookInfo{" + "gameType='" + gameType + '\'' + ", gameId=" + gameId + ", bookList=" + bookList + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "ReceiveBookRequestBean{" + "userName='" + userName + '\'' + ", userSessionId='" + userSessionId + '\'' + ", dlChallanNumber='" + dlChallanNumber + '\'' + ", receiveType='" + receiveType + '\'' + ", bookInfo=" + bookInfo + '}';
    }
}
