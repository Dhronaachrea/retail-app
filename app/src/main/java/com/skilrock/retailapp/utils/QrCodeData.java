package com.skilrock.retailapp.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class QrCodeData {

    @SerializedName("merchantId")
    @Expose
    private String merchantId;

    @SerializedName("destinationAccount")
    @Expose
    private String destinationAccount;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("name")
    @Expose
    private String name;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String toString() {
        return "QrCodeData{" +
                "merchantId='" + merchantId + '\'' +
                ", destinationAccount='" + destinationAccount + '\'' +
                ", amount='" + amount + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
