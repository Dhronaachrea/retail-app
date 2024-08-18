package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class QrCodeRegistrationRequestBean {

    @SerializedName("type")
    @Expose
    private String type = "QRCODE";

    @SerializedName("qrCode")
    @Expose
    private String qrCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public @NotNull String toString() {
        return "QrCodeRegistrationRequestBean{" +
                "type='" + type + '\'' +
                ", qrCode='" + qrCode + '\'' +
                '}';
    }
}
