package com.skilrock.retailapp.models.drawgames;

import androidx.annotation.NonNull;

public class BankerBean {

    private String number;
    private boolean isSelected;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
        return "BankerBean{" + "number='" + number + '\'' + ", isSelected=" + isSelected + ", color='" + color + '\'' + '}';
    }
}
