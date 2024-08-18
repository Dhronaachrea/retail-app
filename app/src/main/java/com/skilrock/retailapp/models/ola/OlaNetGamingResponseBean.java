package com.skilrock.retailapp.models.ola;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OlaNetGamingResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

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

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public class ResponseData {

        @SerializedName("errorCode")
        @Expose
        private Integer errorCode;
        @SerializedName("txnList")
        @Expose
        private ArrayList<TxnList> txnList = null;
        @SerializedName("totalNGR")
        @Expose
        private String totalNGR;
        @SerializedName("totalWinning")
        @Expose
        private String totalWinning;
        @SerializedName("totalWager")
        @Expose
        private String totalWager;
        @SerializedName("errorMessage")
        @Expose
        private Object errorMessage;

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public ArrayList<TxnList> getTxnList() {
            return txnList;
        }

        public void setTxnList(ArrayList<TxnList> txnList) {
            this.txnList = txnList;
        }

        public String getTotalNGR() {
            return totalNGR;
        }

        public void setTotalNGR(String totalNGR) {
            this.totalNGR = totalNGR;
        }

        public String getTotalWinning() {
            return totalWinning;
        }

        public void setTotalWinning(String totalWinning) {
            this.totalWinning = totalWinning;
        }

        public String getTotalWager() {
            return totalWager;
        }

        public void setTotalWager(String totalWager) {
            this.totalWager = totalWager;
        }

        public Object getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(Object errorMessage) {
            this.errorMessage = errorMessage;
        }

    }
    public class TxnList {

        @SerializedName("playerWager")
        @Expose
        private String playerWager;
        @SerializedName("playerNGR")
        @Expose
        private String playerNGR;
        @SerializedName("playerWinning")
        @Expose
        private String playerWinning;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("playerId")
        @Expose
        private String playerId;

        @SerializedName("ggrTax")
        @Expose
        private double ggrTax;
        @SerializedName("bonusToCash")
        @Expose
        private double bonusToCash;

        public String getPlayerWager() {
            return playerWager;
        }

        public void setPlayerWager(String playerWager) {
            this.playerWager = playerWager;
        }

        public String getPlayerNGR() {
            return playerNGR;
        }

        public void setPlayerNGR(String playerNGR) {
            this.playerNGR = playerNGR;
        }

        public String getPlayerWinning() {
            return playerWinning;
        }

        public void setPlayerWinning(String playerWinning) {
            this.playerWinning = playerWinning;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public double getGgrTax() {
            return ggrTax;
        }

        public void setGgrTax(double ggrTax) {
            this.ggrTax = ggrTax;
        }

        public double getBonusToCash() {
            return bonusToCash;
        }

        public void setBonusToCash(double bonusToCash) {
            this.bonusToCash = bonusToCash;
        }
    }
}