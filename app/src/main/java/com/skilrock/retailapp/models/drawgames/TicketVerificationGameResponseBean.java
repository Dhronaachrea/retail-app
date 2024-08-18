package com.skilrock.retailapp.models.drawgames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TicketVerificationGameResponseBean {

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

        @SerializedName("ticketNumber")
        @Expose
        private String ticketNumber;
        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("gameCode")
        @Expose
        private String gameCode;
        @SerializedName("gameId")
        @Expose
        private Integer gameId;
        @SerializedName("drawData")
        @Expose
        private ArrayList<DrawDatum> drawData = null;
        @SerializedName("prizeAmount")
        @Expose
        private String prizeAmount;
        @SerializedName("totalPay")
        @Expose
        private String totalPay;
        @SerializedName("currencySymbol")
        @Expose
        private String currencySymbol;
        @SerializedName("merchantCode")
        @Expose
        private String merchantCode;
        @SerializedName("orgName")
        @Expose
        private String orgName;
        @SerializedName("retailerName")
        @Expose
        private String retailerName;
        @SerializedName("reprintCountPwt")
        @Expose
        private String reprintCountPwt;
        @SerializedName("pwtTicketType")
        @Expose
        private String pwtTicketType;

        public String getTicketNumber() {
            return ticketNumber;
        }

        public void setTicketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getGameCode() {
            return gameCode;
        }

        public void setGameCode(String gameCode) {
            this.gameCode = gameCode;
        }

        public Integer getGameId() {
            return gameId;
        }

        public void setGameId(Integer gameId) {
            this.gameId = gameId;
        }

        public ArrayList<DrawDatum> getDrawData() {
            return drawData;
        }

        public void setDrawData(ArrayList<DrawDatum> drawData) {
            this.drawData = drawData;
        }

        public String getPrizeAmount() {
            return prizeAmount;
        }

        public void setPrizeAmount(String prizeAmount) {
            this.prizeAmount = prizeAmount;
        }

        public String getTotalPay() {
            return totalPay;
        }

        public void setTotalPay(String totalPay) {
            this.totalPay = totalPay;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public void setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getRetailerName() {
            return retailerName;
        }

        public void setRetailerName(String retailerName) {
            this.retailerName = retailerName;
        }

        public String getReprintCountPwt() {
            return reprintCountPwt;
        }

        public void setReprintCountPwt(String reprintCountPwt) {
            this.reprintCountPwt = reprintCountPwt;
        }

        public String getPwtTicketType() {
            return pwtTicketType;
        }

        public void setPwtTicketType(String pwtTicketType) {
            this.pwtTicketType = pwtTicketType;
        }

    }

    public class DrawDatum {

        @SerializedName("drawId")
        @Expose
        private Integer drawId;
        @SerializedName("drawName")
        @Expose
        private String drawName;
        @SerializedName("drawDate")
        @Expose
        private String drawDate;
        @SerializedName("drawTime")
        @Expose
        private String drawTime;
        @SerializedName("winStatus")
        @Expose
        private String winStatus;
        @SerializedName("winningAmount")
        @Expose
        private String winningAmount;
        @SerializedName("panelWinList")
        @Expose
        private ArrayList<PanelWinList> panelWinList = null;
        @SerializedName("winCode")
        @Expose
        private Integer winCode;

        public Integer getDrawId() {
            return drawId;
        }

        public void setDrawId(Integer drawId) {
            this.drawId = drawId;
        }

        public String getDrawName() {
            return drawName;
        }

        public void setDrawName(String drawName) {
            this.drawName = drawName;
        }

        public String getDrawDate() {
            return drawDate;
        }

        public void setDrawDate(String drawDate) {
            this.drawDate = drawDate;
        }

        public String getDrawTime() {
            return drawTime;
        }

        public void setDrawTime(String drawTime) {
            this.drawTime = drawTime;
        }

        public String getWinStatus() {
            return winStatus;
        }

        public void setWinStatus(String winStatus) {
            this.winStatus = winStatus;
        }

        public String getWinningAmount() {
            return winningAmount;
        }

        public void setWinningAmount(String winningAmount) {
            this.winningAmount = winningAmount;
        }

        public ArrayList<PanelWinList> getPanelWinList() {
            return panelWinList;
        }

        public void setPanelWinList(ArrayList<PanelWinList> panelWinList) {
            this.panelWinList = panelWinList;
        }

        public Integer getWinCode() {
            return winCode;
        }

        public void setWinCode(Integer winCode) {
            this.winCode = winCode;
        }

    }

    public class PanelWinList {

        @SerializedName("panelId")
        @Expose
        private Integer panelId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("playType")
        @Expose
        private String playType;
        @SerializedName("winningAmt")
        @Expose
        private Integer winningAmt;
        @SerializedName("valid")
        @Expose
        private Boolean valid;

        public Integer getPanelId() {
            return panelId;
        }

        public void setPanelId(Integer panelId) {
            this.panelId = panelId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPlayType() {
            return playType;
        }

        public void setPlayType(String playType) {
            this.playType = playType;
        }

        public Integer getWinningAmt() {
            return winningAmt;
        }

        public void setWinningAmt(Integer winningAmt) {
            this.winningAmt = winningAmt;
        }

        public Boolean getValid() {
            return valid;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }
    }
}