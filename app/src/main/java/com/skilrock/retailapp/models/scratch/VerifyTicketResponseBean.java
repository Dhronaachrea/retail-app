package com.skilrock.retailapp.models.scratch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VerifyTicketResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("winningTicketResponse")
    @Expose
    private ArrayList<WinningTicketResponse> winningTicketResponse = null;

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

    public ArrayList<WinningTicketResponse> getWinningTicketResponse() {
        return winningTicketResponse;
    }

    public void setWinningTicketResponse(ArrayList<WinningTicketResponse> winningTicketResponse) {
        this.winningTicketResponse = winningTicketResponse;
    }

    public static class WinningTicketResponse {

        @SerializedName("gameId")
        @Expose
        private Integer gameId;
        @SerializedName("ticketNumber")
        @Expose
        private String ticketNumber;
        @SerializedName("virnNumber")
        @Expose
        private String virnNumber;
        @SerializedName("winningAmount")
        @Expose
        private Integer winningAmount;
        @SerializedName("winningCode")
        @Expose
        private Integer winningCode;
        @SerializedName("winningStatus")
        @Expose
        private String winningStatus;
        @SerializedName("tdsPercentage")
        @Expose
        private Integer tdsPercentage;
        @SerializedName("tdsAmount")
        @Expose
        private Integer tdsAmount;
        @SerializedName("netWinningAmount")
        @Expose
        private Integer netWinningAmount;

        public Integer getGameId() {
            return gameId;
        }

        public void setGameId(Integer gameId) {
            this.gameId = gameId;
        }

        public String getTicketNumber() {
            return ticketNumber;
        }

        public void setTicketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
        }

        public String getVirnNumber() {
            return virnNumber;
        }

        public void setVirnNumber(String virnNumber) {
            this.virnNumber = virnNumber;
        }

        public Integer getWinningAmount() {
            return winningAmount;
        }

        public void setWinningAmount(Integer winningAmount) {
            this.winningAmount = winningAmount;
        }

        public Integer getWinningCode() {
            return winningCode;
        }

        public void setWinningCode(Integer winningCode) {
            this.winningCode = winningCode;
        }

        public String getWinningStatus() {
            return winningStatus;
        }

        public void setWinningStatus(String winningStatus) {
            this.winningStatus = winningStatus;
        }

        public Integer getTdsPercentage() {
            return tdsPercentage;
        }

        public void setTdsPercentage(Integer tdsPercentage) {
            this.tdsPercentage = tdsPercentage;
        }

        public Integer getTdsAmount() {
            return tdsAmount;
        }

        public void setTdsAmount(Integer tdsAmount) {
            this.tdsAmount = tdsAmount;
        }

        public Integer getNetWinningAmount() {
            return netWinningAmount;
        }

        public void setNetWinningAmount(Integer netWinningAmount) {
            this.netWinningAmount = netWinningAmount;
        }

        @Override
        public String toString() {
            return "WinningTicketResponse{" + "gameId=" + gameId + ", ticketNumber='" + ticketNumber + '\'' + ", virnNumber='" + virnNumber + '\'' + ", winningAmount=" + winningAmount + ", winningCode='" + winningCode + '\'' + ", winningStatus='" + winningStatus + '\'' + ", tdsPercentage=" + tdsPercentage + ", tdsAmount=" + tdsAmount + ", netWinningAmount=" + netWinningAmount + '}';
        }
    }

    @Override
    public String toString() {
        return "VerifyTicketResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", winningTicketResponse=" + winningTicketResponse + '}';
    }
}
