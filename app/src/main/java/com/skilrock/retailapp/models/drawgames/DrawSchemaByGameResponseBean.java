package com.skilrock.retailapp.models.drawgames;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrawSchemaByGameResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseData")
    @Expose
    private List<ResponseDatum> responseData = null;

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

    public List<ResponseDatum> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<ResponseDatum> responseData) {
        this.responseData = responseData;
    }

    public class ResponseDatum {

        @SerializedName("gameDevName")
        @Expose
        private String gameDevName;
        @SerializedName("matchDetail")
        @Expose
        private ArrayList<MatchDetail> matchDetail = null;

        public String getGameDevName() {
            return gameDevName;
        }

        public void setGameDevName(String gameDevName) {
            this.gameDevName = gameDevName;
        }

        public ArrayList<MatchDetail> getMatchDetail() {
            return matchDetail;
        }

        public void setMatchDetail(ArrayList<MatchDetail> matchDetail) {
            this.matchDetail = matchDetail;
        }
    }

    public class MatchDetail {

        @SerializedName("match")
        @Expose
        private String match;
        @SerializedName("rank")
        @Expose
        private Integer rank;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("prizeAmount")
        @Expose
        private String prizeAmount;
        @SerializedName("betType")
        @Expose
        private String betType;
        @SerializedName("winMode")
        @Expose
        private String winMode;

        public String getMatch() {
            return match;
        }

        public void setMatch(String match) {
            this.match = match;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrizeAmount() {
            return prizeAmount;
        }

        public void setPrizeAmount(String prizeAmount) {
            this.prizeAmount = prizeAmount;
        }

        public String getBetType() {
            return betType;
        }

        public void setBetType(String betType) {
            this.betType = betType;
        }

        public String getWinMode() {
            return winMode;
        }

        public void setWinMode(String winMode) {
            this.winMode = winMode;
        }
    }
}