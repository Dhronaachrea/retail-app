package com.skilrock.retailapp.models.drawgames;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuickPickRequestBean {

    @SerializedName("noOfLines")
    @Expose
    private Integer noOfLines;

    @SerializedName("numbersPicked")
    @Expose
    private String numbersPicked;

    public Integer getNoOfLines() {
        return noOfLines;
    }

    public void setNoOfLines(Integer noOfLines) {
        this.noOfLines = noOfLines;
    }

    public String getNumbersPicked() {
        return numbersPicked;
    }

    public void setNumbersPicked(String numbersPicked) {
        this.numbersPicked = numbersPicked;
    }

    @NonNull
    @Override
    public String toString() {
        return "QuickPickRequestBean{" + "noOfLines=" + noOfLines + ", numbersPicked='" + numbersPicked + '\'' + '}';
    }
}
