package com.skilrock.retailapp.models.drawgames;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class SpinAndWinSelectedNumbersBean {

    @SerializedName("selectedData")
    @Expose
    private ArrayList<SelectedData> selectedData = null;

    public ArrayList<SelectedData> getSelectedData() {
        return selectedData;
    }

    public void setSelectedData(ArrayList<SelectedData> selectedData) {
        this.selectedData = selectedData;
    }

    public static class SelectedData {

        @SerializedName("amount")
        @Expose
        private String amount;

        @SerializedName("selectedNumbers")
        @Expose
        private String selectedNumbers;

        @SerializedName("pickType")
        @Expose
        private String pickType;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getSelectedNumbers() {
            return selectedNumbers;
        }

        public void setSelectedNumbers(String selectedNumbers) {
            this.selectedNumbers = selectedNumbers;
        }

        public String getPickType() {
            return pickType;
        }

        public void setPickType(String pickType) {
            this.pickType = pickType;
        }

        @NonNull
        @Override
        public String toString() {
            return "SelectedData{" + "amount='" + amount + '\'' + ", selectedNumbers='" + selectedNumbers + '\'' + ", pickType='" + pickType + '\'' + '}';
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof SelectedData) {
                if (this.selectedNumbers.equalsIgnoreCase(((SelectedData) obj).getSelectedNumbers())
                        && this.pickType.equalsIgnoreCase(((SelectedData) obj).getPickType())) {
                    //this.setAmount(String.valueOf(Integer.parseInt(this.amount) + Integer.parseInt(((SelectedData) obj).getAmount())));
                    return true;
                }
            }
            return false;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "SpinAndWinSelectedNumbersBean{" + "selectedData=" + selectedData + '}';
    }
}
