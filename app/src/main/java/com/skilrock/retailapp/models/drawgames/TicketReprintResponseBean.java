package com.skilrock.retailapp.models.drawgames;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TicketReprintResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("responseData")
    @Expose
    private FiveByNinetySaleResponseBean.ResponseData responseData;

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

    public FiveByNinetySaleResponseBean.ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(FiveByNinetySaleResponseBean.ResponseData responseData) {
        this.responseData = responseData;
    }

    public static class ResponseData {

        @SerializedName("gameId")
        @Expose
        private Integer gameId;

        @SerializedName("gameName")
        @Expose
        private String gameName;

        @SerializedName("gameCode")
        @Expose
        private String gameCode;

        @SerializedName("totalPurchaseAmount")
        @Expose
        private Double totalPurchaseAmount;

        @SerializedName("playerPurchaseAmount")
        @Expose
        private Double playerPurchaseAmount;

        @SerializedName("purchaseTime")
        @Expose
        private String purchaseTime;

        @SerializedName("ticketNumber")
        @Expose
        private String ticketNumber;

        @SerializedName("panelData")
        @Expose
        private ArrayList<FiveByNinetySaleResponseBean.ResponseData.PanelData> panelData = null;

        @SerializedName("drawData")
        @Expose
        private ArrayList<FiveByNinetySaleResponseBean.ResponseData.DrawData> drawData = null;

        @SerializedName("merchantCode")
        @Expose
        private String merchantCode;

        @SerializedName("currencyCode")
        @Expose
        private String currencyCode;

        @SerializedName("partyType")
        @Expose
        private String partyType;

        @SerializedName("channel")
        @Expose
        private String channel;

        @SerializedName("availableBal")
        @Expose
        private String availableBal;

        @SerializedName("noOfDraws")
        @Expose
        private Integer noOfDraws;

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

        public String getGameCode() {
            return gameCode;
        }

        public void setGameCode(String gameCode) {
            this.gameCode = gameCode;
        }

        public Double getTotalPurchaseAmount() {
            return totalPurchaseAmount;
        }

        public void setTotalPurchaseAmount(Double totalPurchaseAmount) {
            this.totalPurchaseAmount = totalPurchaseAmount;
        }

        public Double getPlayerPurchaseAmount() {
            return playerPurchaseAmount;
        }

        public void setPlayerPurchaseAmount(Double playerPurchaseAmount) {
            this.playerPurchaseAmount = playerPurchaseAmount;
        }

        public String getPurchaseTime() {
            return purchaseTime;
        }

        public void setPurchaseTime(String purchaseTime) {
            this.purchaseTime = purchaseTime;
        }

        public String getTicketNumber() {
            return ticketNumber;
        }

        public void setTicketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
        }

        public ArrayList<FiveByNinetySaleResponseBean.ResponseData.PanelData> getPanelData() {
            return panelData;
        }

        public void setPanelData(ArrayList<FiveByNinetySaleResponseBean.ResponseData.PanelData> panelData) {
            this.panelData = panelData;
        }

        public ArrayList<FiveByNinetySaleResponseBean.ResponseData.DrawData> getDrawData() {
            return drawData;
        }

        public void setDrawData(ArrayList<FiveByNinetySaleResponseBean.ResponseData.DrawData> drawData) {
            this.drawData = drawData;
        }

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getPartyType() {
            return partyType;
        }

        public void setPartyType(String partyType) {
            this.partyType = partyType;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getAvailableBal() {
            return availableBal;
        }

        public void setAvailableBal(String availableBal) {
            this.availableBal = availableBal;
        }

        public Integer getNoOfDraws() {
            return noOfDraws;
        }

        public void setNoOfDraws(Integer noOfDraws) {
            this.noOfDraws = noOfDraws;
        }

        public static class PanelData {

            @SerializedName("betType")
            @Expose
            private String betType;

            @SerializedName("pickType")
            @Expose
            private String pickType;

            @SerializedName("pickConfig")
            @Expose
            private String pickConfig;

            @SerializedName("betAmountMultiple")
            @Expose
            private Integer betAmountMultiple;

            @SerializedName("quickPick")
            @Expose
            private Boolean quickPick;

            @SerializedName("pickedValues")
            @Expose
            private String pickedValues;

            @SerializedName("qpPreGenerated")
            @Expose
            private Boolean qpPreGenerated;

            @SerializedName("numberOfLines")
            @Expose
            private Integer numberOfLines;

            @SerializedName("unitCost")
            @Expose
            private Double unitCost;

            @SerializedName("panelPrice")
            @Expose
            private Double panelPrice;

            @SerializedName("playerPanelPrice")
            @Expose
            private Double playerPanelPrice;

            public String getBetType() {
                return betType;
            }

            public void setBetType(String betType) {
                this.betType = betType;
            }

            public String getPickType() {
                return pickType;
            }

            public void setPickType(String pickType) {
                this.pickType = pickType;
            }

            public String getPickConfig() {
                return pickConfig;
            }

            public void setPickConfig(String pickConfig) {
                this.pickConfig = pickConfig;
            }

            public Integer getBetAmountMultiple() {
                return betAmountMultiple;
            }

            public void setBetAmountMultiple(Integer betAmountMultiple) {
                this.betAmountMultiple = betAmountMultiple;
            }

            public Boolean getQuickPick() {
                return quickPick;
            }

            public void setQuickPick(Boolean quickPick) {
                this.quickPick = quickPick;
            }

            public String getPickedValues() {
                return pickedValues;
            }

            public void setPickedValues(String pickedValues) {
                this.pickedValues = pickedValues;
            }

            public Boolean getQpPreGenerated() {
                return qpPreGenerated;
            }

            public void setQpPreGenerated(Boolean qpPreGenerated) {
                this.qpPreGenerated = qpPreGenerated;
            }

            public Integer getNumberOfLines() {
                return numberOfLines;
            }

            public void setNumberOfLines(Integer numberOfLines) {
                this.numberOfLines = numberOfLines;
            }

            public Double getUnitCost() {
                return unitCost;
            }

            public void setUnitCost(Double unitCost) {
                this.unitCost = unitCost;
            }

            public Double getPanelPrice() {
                return panelPrice;
            }

            public void setPanelPrice(Double panelPrice) {
                this.panelPrice = panelPrice;
            }

            public Double getPlayerPanelPrice() {
                return playerPanelPrice;
            }

            public void setPlayerPanelPrice(Double playerPanelPrice) {
                this.playerPanelPrice = playerPanelPrice;
            }

            @NonNull
            @Override
            public String toString() {
                return "PanelData{" + "betType='" + betType + '\'' + ", pickType='" + pickType + '\'' + ", pickConfig='" + pickConfig + '\'' + ", betAmountMultiple=" + betAmountMultiple + ", quickPick=" + quickPick + ", pickedValues='" + pickedValues + '\'' + ", qpPreGenerated=" + qpPreGenerated + ", numberOfLines=" + numberOfLines + ", unitCost=" + unitCost + ", panelPrice=" + panelPrice + ", playerPanelPrice=" + playerPanelPrice + '}';
            }
        }

        public static class DrawData {

            @SerializedName("drawName")
            @Expose
            private String drawName;

            @SerializedName("drawDate")
            @Expose
            private String drawDate;

            @SerializedName("drawTime")
            @Expose
            private String drawTime;

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

            @NonNull
            @Override
            public String toString() {
                return "DrawDatum{" + "drawName='" + drawName + '\'' + ", drawDate='" + drawDate + '\'' + ", drawTime='" + drawTime + '\'' + '}';
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "gameId=" + gameId + ", gameName='" + gameName + '\'' + ", gameCode='" + gameCode + '\'' + ", totalPurchaseAmount=" + totalPurchaseAmount + ", playerPurchaseAmount=" + playerPurchaseAmount + ", purchaseTime='" + purchaseTime + '\'' + ", ticketNumber='" + ticketNumber + '\'' + ", panelData=" + panelData + ", drawData=" + drawData + ", merchantCode='" + merchantCode + '\'' + ", currencyCode='" + currencyCode + '\'' + ", partyType='" + partyType + '\'' + ", channel='" + channel + '\'' + ", availableBal='" + availableBal + '\'' + ", noOfDraws=" + noOfDraws + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "FiveByNinetySaleResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
