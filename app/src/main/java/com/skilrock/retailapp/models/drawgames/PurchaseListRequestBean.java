package com.skilrock.retailapp.models.drawgames;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseListRequestBean {

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
    @SerializedName("Numbers")
    @Expose
    private List<String> numbers = null;
    @SerializedName("winMode")
    @Expose
    private String winMode;
    @SerializedName("betTypeName")
    @Expose
    private String betTypeName;
    @SerializedName("pickTypeName")
    @Expose
    private String pickTypeName;
    @SerializedName("numberOfLines")
    @Expose
    private Integer numberOfLines;
    @SerializedName("price")
    @Expose
    private Double price;

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

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public String getWinMode() {
        return winMode;
    }

    public void setWinMode(String winMode) {
        this.winMode = winMode;
    }

    public String getBetTypeName() {
        return betTypeName;
    }

    public void setBetTypeName(String betTypeName) {
        this.betTypeName = betTypeName;
    }

    public String getPickTypeName() {
        return pickTypeName;
    }

    public void setPickTypeName(String pickTypeName) {
        this.pickTypeName = pickTypeName;
    }

    public Integer getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(Integer numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}