package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashManagementUserRequestBean {

    @SerializedName("domainId")
    @Expose
    private Long domainId;
    @SerializedName("orgId")
    @Expose
    private Long orgId;

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @NonNull
    @Override
    public String toString() {
        return "CashManagementUserRequestBean{" + "domainId=" + domainId + ", orgId=" + orgId + '}';
    }
}
