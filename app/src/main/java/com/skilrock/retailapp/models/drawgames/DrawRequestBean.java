package com.skilrock.retailapp.models.drawgames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DrawRequestBean {

    @SerializedName("gameName")
    @Expose
    private String gameName;
    @SerializedName("betCode")
    @Expose
    private String betCode;

    @SerializedName("betName")
    @Expose
    private String betName;

    @SerializedName("pickCode")
    @Expose
    private String pickCode;

    @SerializedName("pickName")
    @Expose
    private String pickName;

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
    private String totalNumbers;

    @SerializedName("winMode")
    @Expose
    private String winMode;

    @SerializedName("amount")
    @Expose
    private double amount;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getBetName() {
        return betName;
    }

    public void setBetName(String betName) {
        this.betName = betName;
    }

    public String getPickCode() {
        return pickCode;
    }

    public void setPickCode(String pickCode) {
        this.pickCode = pickCode;
    }

    public String getPickName() {
        return pickName;
    }

    public void setPickName(String pickName) {
        this.pickName = pickName;
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

    public String getTotalNumbers() {
        return totalNumbers;
    }

    public void setTotalNumbers(String totalNumbers) {
        this.totalNumbers = totalNumbers;
    }

    public String getWinMode() {
        return winMode;
    }

    public void setWinMode(String winMode) {
        this.winMode = winMode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}