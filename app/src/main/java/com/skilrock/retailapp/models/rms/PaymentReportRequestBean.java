package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentReportRequestBean {

    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("orgId")
    @Expose
    private Long orgId;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("orgTypeCode")
    @Expose
    private String orgTypeCode;

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "PaymentReportRequestBean{" + "endDate='" + endDate + '\'' + ", orgId=" + orgId + ", startDate='" + startDate + '\'' + ", orgTypeCode='" + orgTypeCode + '\'' + '}';
    }
}
