package com.skilrock.retailapp.models.drawgames;

import androidx.annotation.NonNull;

public class QuickPickBean {

    private int number;
    private boolean isSelected;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @NonNull
    @Override
    public String toString() {
        return "QuickPickBean{" + "number=" + number + ", isSelected=" + isSelected + '}';
    }
}
