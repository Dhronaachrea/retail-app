package com.skilrock.retailapp.models.drawgames;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PanelBean implements Parcelable {

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
    private Integer totalNumbers;

    @SerializedName("winMode")
    @Expose
    private String winMode;

    @SerializedName("amount")
    @Expose
    private double amount;

    @SerializedName("selectedBetAmount")
    @Expose
    private int selectedBetAmount;

    @SerializedName("numberOfDraws")
    @Expose
    private int numberOfDraws;

    @SerializedName("numberOfLines")
    @Expose
    private int numberOfLines;

    @SerializedName("sideBetHeader")
    @Expose
    private String sideBetHeader;

    @SerializedName("isMainBet")
    @Expose
    private boolean isMainBet;

    @SerializedName("colorCode")
    @Expose
    private String colorCode;

    @SerializedName("listSelectedNumber")
    @Expose
    private ArrayList<String> listSelectedNumber;

    @SerializedName("listSelectedNumberUpperLine")
    @Expose
    private ArrayList<String> listSelectedNumberUpperLine;

    @SerializedName("listSelectedNumberLowerLine")
    @Expose
    private ArrayList<String> listSelectedNumberLowerLine;

    @SerializedName("unitPrice")
    @Expose
    private double unitPrice;

    public ArrayList<String> getListSelectedNumberUpperLine() {
        return listSelectedNumberUpperLine;
    }

    public void setListSelectedNumberUpperLine(ArrayList<String> listSelectedNumberUpperLine) {
        this.listSelectedNumberUpperLine = listSelectedNumberUpperLine;
    }

    public ArrayList<String> getListSelectedNumberLowerLine() {
        return listSelectedNumberLowerLine;
    }

    public void setListSelectedNumberLowerLine(ArrayList<String> listSelectedNumberLowerLine) {
        this.listSelectedNumberLowerLine = listSelectedNumberLowerLine;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getBetCode() {
        return betCode;
    }

    public void setBetCode(String betCode) {
        this.betCode = betCode;
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

    public Integer getTotalNumbers() {
        return totalNumbers;
    }

    public void setTotalNumbers(Integer totalNumbers) {
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

    public int getSelectedBetAmount() {
        return selectedBetAmount;
    }

    public void setSelectedBetAmount(int selectedBetAmount) {
        this.selectedBetAmount = selectedBetAmount;
    }

    public int getNumberOfDraws() {
        return numberOfDraws;
    }

    public void setNumberOfDraws(int numberOfDraws) {
        this.numberOfDraws = numberOfDraws;
    }

    public int getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    public String getSideBetHeader() {
        return sideBetHeader;
    }

    public void setSideBetHeader(String sideBetHeader) {
        this.sideBetHeader = sideBetHeader;
    }

    public boolean isMainBet() {
        return isMainBet;
    }

    public void setMainBet(boolean mainBet) {
        isMainBet = mainBet;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public ArrayList<String> getListSelectedNumber() {
        return listSelectedNumber;
    }

    public void setListSelectedNumber(ArrayList<String> listSelectedNumber) {
        this.listSelectedNumber = listSelectedNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return "PanelBean{" + "gameName='" + gameName + '\'' + ", betCode='" + betCode + '\'' + ", betName='" + betName + '\'' + ", pickCode='" + pickCode + '\'' + ", pickName='" + pickName + '\'' + ", pickConfig='" + pickConfig + '\'' + ", betAmountMultiple=" + betAmountMultiple + ", quickPick=" + quickPick + ", pickedValues='" + pickedValues + '\'' + ", qpPreGenerated=" + qpPreGenerated + ", totalNumbers=" + totalNumbers + ", winMode='" + winMode + '\'' + ", amount=" + amount + ", selectedBetAmount=" + selectedBetAmount + ", numberOfDraws=" + numberOfDraws + ", numberOfLines=" + numberOfLines + ", sideBetHeader='" + sideBetHeader + '\'' + ", isMainBet=" + isMainBet + ", colorCode='" + colorCode + '\'' + ", listSelectedNumber=" + listSelectedNumber + ", listSelectedNumberUpperLine=" + listSelectedNumberUpperLine + ", listSelectedNumberLowerLine=" + listSelectedNumberLowerLine + '}';
    }

    public PanelBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gameName);
        dest.writeString(this.betCode);
        dest.writeString(this.betName);
        dest.writeString(this.pickCode);
        dest.writeString(this.pickName);
        dest.writeString(this.pickConfig);
        dest.writeValue(this.betAmountMultiple);
        dest.writeValue(this.quickPick);
        dest.writeString(this.pickedValues);
        dest.writeValue(this.qpPreGenerated);
        dest.writeValue(this.totalNumbers);
        dest.writeString(this.winMode);
        dest.writeDouble(this.amount);
        dest.writeInt(this.selectedBetAmount);
        dest.writeInt(this.numberOfDraws);
        dest.writeInt(this.numberOfLines);
        dest.writeString(this.sideBetHeader);
        dest.writeByte(this.isMainBet ? (byte) 1 : (byte) 0);
        dest.writeString(this.colorCode);
        dest.writeStringList(this.listSelectedNumber);
        dest.writeStringList(this.listSelectedNumberUpperLine);
        dest.writeStringList(this.listSelectedNumberLowerLine);
        dest.writeDouble(this.unitPrice);
    }

    protected PanelBean(Parcel in) {
        this.gameName = in.readString();
        this.betCode = in.readString();
        this.betName = in.readString();
        this.pickCode = in.readString();
        this.pickName = in.readString();
        this.pickConfig = in.readString();
        this.betAmountMultiple = (Integer) in.readValue(Integer.class.getClassLoader());
        this.quickPick = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.pickedValues = in.readString();
        this.qpPreGenerated = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.totalNumbers = (Integer) in.readValue(Integer.class.getClassLoader());
        this.winMode = in.readString();
        this.amount = in.readDouble();
        this.selectedBetAmount = in.readInt();
        this.numberOfDraws = in.readInt();
        this.numberOfLines = in.readInt();
        this.sideBetHeader = in.readString();
        this.isMainBet = in.readByte() != 0;
        this.colorCode = in.readString();
        this.listSelectedNumber = in.createStringArrayList();
        this.listSelectedNumberUpperLine = in.createStringArrayList();
        this.listSelectedNumberLowerLine = in.createStringArrayList();
        this.unitPrice = in.readDouble();
    }

    public static final Creator<PanelBean> CREATOR = new Creator<PanelBean>() {
        @Override
        public PanelBean createFromParcel(Parcel source) {
            return new PanelBean(source);
        }

        @Override
        public PanelBean[] newArray(int size) {
            return new PanelBean[size];
        }
    };

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
