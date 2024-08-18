package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleWinningReportRequestBean {

    @SerializedName("appType")
    @Expose
    private String appType;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("languageCode")
    @Expose
    private String languageCode;
    @SerializedName("orgId")
    @Expose
    private Long orgId;
    @SerializedName("orgTypeCode")
    @Expose
    private String orgTypeCode;
    @SerializedName("serviceCode")
    @Expose
    private String serviceCode;
    @SerializedName("startDate")
    @Expose
    private String startDate;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}