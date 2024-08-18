package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuickOrderRequestBean {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userSessionId")
    @Expose
    private String userSessionId;
    @SerializedName("gameOrderList")
    @Expose
    private List<GameOrderList> gameOrderList = null;

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

    public List<GameOrderList> getGameOrderList() {
        return gameOrderList;
    }

    public void setGameOrderList(List<GameOrderList> gameOrderList) {
        this.gameOrderList = gameOrderList;
    }

    @NonNull
    @Override
    public String toString() {
        return "QuickOrderRequestBean{" + "userName='" + userName + '\'' + ", userSessionId='" + userSessionId + '\'' + ", gameOrderList=" + gameOrderList + '}';
    }

    public static class GameOrderList {

        @SerializedName("gameId")
        @Expose
        private Integer gameId;
        @SerializedName("booksQuantity")
        @Expose
        private Integer booksQuantity;

        public Integer getGameId() {
            return gameId;
        }

        public void setGameId(Integer gameId) {
            this.gameId = gameId;
        }

        public Integer getBooksQuantity() {
            return booksQuantity;
        }

        public void setBooksQuantity(Integer booksQuantity) {
            this.booksQuantity = booksQuantity;
        }

        @NonNull
        @Override
        public String toString() {
            return "GameOrderList{" + "gameId=" + gameId + ", booksQuantity=" + booksQuantity + '}';
        }
    }

}
