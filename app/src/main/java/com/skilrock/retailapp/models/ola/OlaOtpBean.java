package com.skilrock.retailapp.models.ola;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaOtpBean {

    @SerializedName("amount")
    @Expose
    private Double amount;

    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    @SerializedName("plrDomainCode")
    @Expose
    private String plrDomainCode;

    @SerializedName("plrMerchantCode")
    @Expose
    private String plrMerchantCode;

    @SerializedName("retailDomainCode")
    @Expose
    private String retailDomainCode;

    @SerializedName("retailMerchantCode")
    @Expose
    private String retailMerchantCode;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPlrDomainCode() {
        return plrDomainCode;
    }

    public void setPlrDomainCode(String plrDomainCode) {
        this.plrDomainCode = plrDomainCode;
    }

    public String getPlrMerchantCode() {
        return plrMerchantCode;
    }

    public void setPlrMerchantCode(String plrMerchantCode) {
        this.plrMerchantCode = plrMerchantCode;
    }

    public String getRetailDomainCode() {
        return retailDomainCode;
    }

    public void setRetailDomainCode(String retailDomainCode) {
        this.retailDomainCode = retailDomainCode;
    }

    public String getRetailMerchantCode() {
        return retailMerchantCode;
    }

    public void setRetailMerchantCode(String retailMerchantCode) {
        this.retailMerchantCode = retailMerchantCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OlaRegistrationOtpRequestBean{" + "amount=" + amount + ", mobileNo='" + mobileNo + '\'' + ", plrDomainCode='" + plrDomainCode + '\'' + ", plrMerchantCode='" + plrMerchantCode + '\'' + ", retailDomainCode='" + retailDomainCode + '\'' + ", retailMerchantCode='" + retailMerchantCode + '\'' + ", token='" + token + '\'' + ", type='" + type + '\'' + ", userId=" + userId + '}';
    }
}
