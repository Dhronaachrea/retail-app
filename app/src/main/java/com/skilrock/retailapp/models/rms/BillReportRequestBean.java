package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillReportRequestBean {

    @SerializedName("billNumber")
    @Expose
    private String billNumber;

    @SerializedName("billStatus")
    @Expose
    private String billStatus;

    @SerializedName("domainId")
    @Expose
    private Long domainId;

    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("orgId")
    @Expose
    private Long orgId;

    @SerializedName("orgTypeCode")
    @Expose
    private String orgTypeCode;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
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

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
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
        return "BillReportRequestBean{" + "billNumber='" + billNumber + '\'' + ", billStatus='" + billStatus + '\'' + ", domainId=" + domainId + ", endDate='" + endDate + '\'' + ", orgId=" + orgId + ", orgTypeCode='" + orgTypeCode + '\'' + ", startDate='" + startDate + '\'' + '}';
    }
}
