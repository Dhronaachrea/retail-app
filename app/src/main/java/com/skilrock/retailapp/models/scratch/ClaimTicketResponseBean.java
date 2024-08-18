package com.skilrock.retailapp.models.scratch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClaimTicketResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("transactionNumber")
    @Expose
    private String transactionNumber;
    @SerializedName("transactionStatus")
    @Expose
    private String transactionStatus;
    @SerializedName("claimTranscationResponse")
    @Expose
    private ArrayList<ClaimTranscationResponse> claimTranscationResponse = null;
    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;

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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public ArrayList<ClaimTranscationResponse> getClaimTranscationResponse() {
        return claimTranscationResponse;
    }

    public void setClaimTranscationResponse(ArrayList<ClaimTranscationResponse> claimTranscationResponse) {
        this.claimTranscationResponse = claimTranscationResponse;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public static class ClaimTranscationResponse {

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

    }

}
