package com.skilrock.retailapp.models.scratch;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitPackReturnResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("invalidInventory")
    @Expose
    private ArrayList<InvalidInventory> invalidInventory = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ArrayList<InvalidInventory> getInvalidInventory() {
        return invalidInventory;
    }

    public void setInvalidInventory(ArrayList<InvalidInventory> invalidInventory) {
        this.invalidInventory = invalidInventory;
    }

    public static class InvalidInventory {

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

        @Override
        public String toString() {
            return "InvalidInventory{" +
                    "gameId=" + gameId +
                    ", bookList=" + bookList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SubmitPackReturnResponseBean{" +
                "responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                ", invalidInventory=" + invalidInventory +
                '}';
    }
}