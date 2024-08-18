package com.skilrock.retailapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class BetDeviceRequestBean {

    public BetDeviceRequestBean(String domainId, String modelName, String orgId, String posType, String terminalId, String userId) {
         this.domainId      = domainId;
         this.modelName     = modelName;
         this.orgId         = orgId;
         this.posType       = posType;
         this.terminalId    = terminalId;
         this.userId        = userId;
    }

    @SerializedName("domainId")
    @Expose
    private String domainId;

    @SerializedName("modelName")
    @Expose
    private String modelName;

    @SerializedName("orgId")
    @Expose
    private String orgId;

    @SerializedName("posType")
    @Expose
    private String posType;

    @SerializedName("terminalId")
    @Expose
    private String terminalId;

    @SerializedName("userId")
    @Expose
    private String userId;

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }


    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public String toString() {
        return "BetDeviceRequestBean{" + "domainId='" + domainId + '\'' + ", modelName='" + modelName + '\'' + ", orgId='" + orgId + '\'' +  ", posType='" + posType + '\'' + ", terminalId='" + terminalId + '\'' + ", userId='" + userId + '\'' + '}';
    }
}
