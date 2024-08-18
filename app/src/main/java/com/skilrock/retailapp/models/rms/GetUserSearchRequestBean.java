package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserSearchRequestBean {

    @SerializedName("orgId")
    @Expose
    private long orgId;
    @SerializedName("domainId")
    @Expose
    private long domainId;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }

    @NonNull
    @Override
    public String toString() {
        return "GetUserSearchRequestBean{" + "orgId=" + orgId + ", domainId=" + domainId + '}';
    }
}
