package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MultiClaimTicketResponseBean implements Serializable {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("response")
    @Expose
    private Response response;

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

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response implements Serializable {
        @SerializedName("data")
        @Expose
        private List<Data> data;

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
    }

    public static class Data implements Serializable {
        @SerializedName("barCode")
        @Expose
        private String barCode;

        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("response")
        @Expose
        private TicketResponse response;

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public TicketResponse getResponse() {
            return response;
        }

        public void setResponse(TicketResponse response) {
            this.response = response;
        }
    }

    public static class TicketResponse implements Serializable {
        @SerializedName("transactionId")
        @Expose
        private String transactionId;

        @SerializedName("transactionNumber")
        @Expose
        private String transactionNumber;

        @SerializedName("transactionDate")
        @Expose
        private String transactionDate;

        @SerializedName("ticketNumber")
        @Expose
        private String ticketNumber;

        @SerializedName("virnNumber")
        @Expose
        private String virnNumber;

        @SerializedName("winningAmount")
        @Expose
        private Double winningAmount;

        @SerializedName("taxAmount")
        @Expose
        private Double taxAmount;

        @SerializedName("commissionAmount")
        @Expose
        private Double commissionAmount;

        @SerializedName("tdsAmount")
        @Expose
        private Double tdsAmount;

        @SerializedName("netWinningAmount")
        @Expose
        private Double netWinningAmount;

        @SerializedName("soldByOrg")
        @Expose
        private String soldByOrg;

        @SerializedName("claimedLocation")
        @Expose
        private String claimedLocation;

        @SerializedName("claimedByOrg")
        @Expose
        private String claimedByOrg;

        @SerializedName("txnStatus")
        @Expose
        private String txnStatus;

        @SerializedName("gameName")
        @Expose
        private String gameName;

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

        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
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

        public Double getWinningAmount() {
            return winningAmount;
        }

        public void setWinningAmount(Double winningAmount) {
            this.winningAmount = winningAmount;
        }

        public Double getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(Double taxAmount) {
            this.taxAmount = taxAmount;
        }

        public Double getCommissionAmount() {
            return commissionAmount;
        }

        public void setCommissionAmount(Double commissionAmount) {
            this.commissionAmount = commissionAmount;
        }

        public Double getTdsAmount() {
            return tdsAmount;
        }

        public void setTdsAmount(Double tdsAmount) {
            this.tdsAmount = tdsAmount;
        }

        public Double getNetWinningAmount() {
            return netWinningAmount;
        }

        public void setNetWinningAmount(Double netWinningAmount) {
            this.netWinningAmount = netWinningAmount;
        }

        public String getSoldByOrg() {
            return soldByOrg;
        }

        public void setSoldByOrg(String soldByOrg) {
            this.soldByOrg = soldByOrg;
        }

        public String getClaimedLocation() {
            return claimedLocation;
        }

        public void setClaimedLocation(String claimedLocation) {
            this.claimedLocation = claimedLocation;
        }

        public String getClaimedByOrg() {
            return claimedByOrg;
        }

        public void setClaimedByOrg(String claimedByOrg) {
            this.claimedByOrg = claimedByOrg;
        }

        public String getTxnStatus() {
            return txnStatus;
        }

        public void setTxnStatus(String txnStatus) {
            this.txnStatus = txnStatus;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "ClaimTicketResponseBean{" +
                "responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                ", response=" + response +
                '}';
    }
}