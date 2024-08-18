package com.skilrock.retailapp.models.FieldX;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FieldxDoPaymentRequestBean {
    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("appType")
    @Expose
    private String appType;

    @SerializedName("clientIp")
    @Expose
    private String clientIp;

    @SerializedName("device")
    @Expose
    private String device;

    @SerializedName("orgId")
    @Expose
    private String orgId;

    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "FieldxDoPaymentRequestBean{" +
                "amount='" + amount + '\'' +
                ", appType='" + appType + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", device='" + device + '\'' +
                ", orgId='" + orgId + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
