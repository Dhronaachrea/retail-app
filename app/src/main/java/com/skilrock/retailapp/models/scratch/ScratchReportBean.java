package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScratchReportBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("orgName")
    @Expose
    private String orgName;

    @SerializedName("gameWiseBookDetailList")
    @Expose
    private ArrayList<GameWiseBookDetailList> gameWiseBookDetailList = null;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public ArrayList<GameWiseBookDetailList> getGameWiseBookDetailList() {
        return gameWiseBookDetailList;
    }

    public void setGameWiseBookDetailList(ArrayList<GameWiseBookDetailList> gameWiseBookDetailList) {
        this.gameWiseBookDetailList = gameWiseBookDetailList;
    }

    public static class GameWiseBookDetailList {

        @SerializedName("gameId")
        @Expose
        private Integer gameId;

        @SerializedName("gameName")
        @Expose
        private String gameName;

        @SerializedName("gameNumber")
        @Expose
        private Integer gameNumber;

        @SerializedName("inTransitPacksList")
        @Expose
        private ArrayList<String> inTransitPacksList = null;

        @SerializedName("receivedPacksList")
        @Expose
        private ArrayList<String> receivedPacksList = null;

        @SerializedName("activatedPacksList")
        @Expose
        private ArrayList<String> activatedPacksList = null;

        @SerializedName("invoicedPacksList")
        @Expose
        private ArrayList<String> invoicedPacksList = null;

        public Integer getGameId() {
            return gameId;
        }

        public void setGameId(Integer gameId) {
            this.gameId = gameId;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public Integer getGameNumber() {
            return gameNumber;
        }

        public void setGameNumber(Integer gameNumber) {
            this.gameNumber = gameNumber;
        }

        public ArrayList<String> getInTransitPacksList() {
            return inTransitPacksList;
        }

        public void setInTransitPacksList(ArrayList<String> inTransitPacksList) {
            this.inTransitPacksList = inTransitPacksList;
        }

        public ArrayList<String> getReceivedPacksList() {
            return receivedPacksList;
        }

        public void setReceivedPacksList(ArrayList<String> receivedPacksList) {
            this.receivedPacksList = receivedPacksList;
        }

        public ArrayList<String> getActivatedPacksList() {
            return activatedPacksList;
        }

        public void setActivatedPacksList(ArrayList<String> activatedPacksList) {
            this.activatedPacksList = activatedPacksList;
        }

        public ArrayList<String> getInvoicedPacksList() {
            return invoicedPacksList;
        }

        public void setInvoicedPacksList(ArrayList<String> invoicedPacksList) {
            this.invoicedPacksList = invoicedPacksList;
        }

        @NonNull
        @Override
        public String toString() {
            return "GameWiseBookDetailList{" + "gameId=" + gameId + ", gameName='" + gameName + '\'' + ", gameNumber=" + gameNumber + ", inTransitPacksList=" + inTransitPacksList + ", receivedPacksList=" + receivedPacksList + ", activatedPacksList=" + activatedPacksList + ", invoicedPacksList=" + invoicedPacksList + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "ScratchReportBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", orgName='" + orgName + '\'' + ", gameWiseBookDetailList=" + gameWiseBookDetailList + '}';
    }
}
