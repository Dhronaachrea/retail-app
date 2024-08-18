package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class SearchUserRequestBean {

    @SerializedName("domainId")
    @Expose
    private Integer domainId;
    @SerializedName("orgId")
    @Expose
    private Integer orgId;

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @NotNull
    @Override
    public String toString() {
        return "SearchUserRequestBean{" +
                "domainId=" + domainId +
                ", orgId=" + orgId +
                '}';
    }
}