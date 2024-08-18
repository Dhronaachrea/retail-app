package com.skilrock.retailapp.models.drawgames;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FiveByNinetySaleRequestBean {

    @SerializedName("merchantCode")
    @Expose
    private String merchantCode;

    @SerializedName("merchantData")
    @Expose
    private MerchantData merchantData;

    @SerializedName("noOfDraws")
    @Expose
    private Integer noOfDraws;

    @SerializedName("gameCode")
    @Expose
    private String gameCode;

    @SerializedName("isAdvancePlay")
    @Expose
    private Boolean isAdvancePlay;

    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    @SerializedName("purchaseDeviceId")
    @Expose
    private String purchaseDeviceId;

    @SerializedName("purchaseDeviceType")
    @Expose
    private String purchaseDeviceType;

    @SerializedName("lastTicketNumber")
    @Expose
    private String lastTicketNumber;

    @SerializedName("drawData")
    @Expose
    private ArrayList<DrawData> drawData = null;

    @SerializedName("panelData")
    @Expose
    private ArrayList<PanelData> panelData = null;

    @SerializedName("modelCode")
    @Expose
    private String modelCode;

    public String getPurchaseDeviceId() {
        return purchaseDeviceId;
    }

    public void setPurchaseDeviceId(String purchaseDeviceId) {
        this.purchaseDeviceId = purchaseDeviceId;
    }

    public Boolean getAdvancePlay() {
        return isAdvancePlay;
    }

    public void setAdvancePlay(Boolean advancePlay) {
        isAdvancePlay = advancePlay;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public MerchantData getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(MerchantData merchantData) {
        this.merchantData = merchantData;
    }

    public Integer getNoOfDraws() {
        return noOfDraws;
    }

    public void setNoOfDraws(Integer noOfDraws) {
        this.noOfDraws = noOfDraws;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public Boolean getIsAdvancePlay() {
        return isAdvancePlay;
    }

    public void setIsAdvancePlay(Boolean isAdvancePlay) {
        this.isAdvancePlay = isAdvancePlay;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPurchaseDeviceType() {
        return purchaseDeviceType;
    }

    public void setPurchaseDeviceType(String purchaseDeviceType) {
        this.purchaseDeviceType = purchaseDeviceType;
    }

    public String getLastTicketNumber() {
        return lastTicketNumber;
    }

    public void setLastTicketNumber(String lastTicketNumber) {
        this.lastTicketNumber = lastTicketNumber;
    }

    public ArrayList<DrawData> getDrawData() {
        return drawData;
    }

    public void setDrawData(ArrayList<DrawData> drawData) {
        this.drawData = drawData;
    }

    public ArrayList<PanelData> getPanelData() {
        return panelData;
    }

    public void setPanelData(ArrayList<PanelData> panelData) {
        this.panelData = panelData;
    }

    public static class MerchantData {

        @SerializedName("userId")
        @Expose
        private Integer userId;

        @SerializedName("userName")
        @Expose
        private String userName;

        @SerializedName("sessionId")
        @Expose
        private String sessionId;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        @NonNull
        @Override
        public String toString() {
            return "MerchantData{" + "userId=" + userId + ", userName='" + userName + '\'' + ", sessionId='" + sessionId + '\'' + '}';
        }
    }

    public static class DrawData {

        @SerializedName("drawId")
        @Expose
        private String drawId;

        public String getDrawId() {
            return drawId;
        }

        public void setDrawId(String drawId) {
            this.drawId = drawId;
        }

        @NonNull
        @Override
        public String toString() {
            return "DrawData{" + "drawId='" + drawId + '\'' + '}';
        }
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

        @SerializedName("totalNumbers")
        @Expose
        private Integer totalNumbers;

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

        public Integer getTotalNumbers() {
            return totalNumbers;
        }

        public void setTotalNumbers(Integer totalNumbers) {
            this.totalNumbers = totalNumbers;
        }

        @NonNull
        @Override
        public String toString() {
            return "PanelData{" + "betType='" + betType + '\'' + ", pickType='" + pickType + '\'' + ", pickConfig='" + pickConfig + '\'' + ", betAmountMultiple=" + betAmountMultiple + ", quickPick=" + quickPick + ", pickedValues='" + pickedValues + '\'' + ", qpPreGenerated=" + qpPreGenerated + ", totalNumbers=" + totalNumbers + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "FiveByNinetySaleRequestBean{" + "merchantCode='" + merchantCode + '\'' + ", merchantData=" + merchantData + ", noOfDraws=" + noOfDraws + ", gameCode='" + gameCode + '\'' + ", isAdvancePlay=" + isAdvancePlay + ", currencyCode='" + currencyCode + '\'' + ", purchaseDeviceId=" + purchaseDeviceId + ", purchaseDeviceType='" + purchaseDeviceType + '\'' + ", lastTicketNumber='" + lastTicketNumber + '\'' + ", drawData=" + drawData + ", panelData=" + panelData + ", modelCode=" + modelCode + '}';
    }
}
