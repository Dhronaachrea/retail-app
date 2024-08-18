package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaPlayerForgotPasswordRequestBean {

    @SerializedName("playerOperationType")
    @Expose
    private String playerOperationType;

    @SerializedName("playerUserName")
    @Expose
    private String playerUserName;

    @SerializedName("plrDomainCode")
    @Expose
    private String plrDomainCode;

    @SerializedName("retOrgId")
    @Expose
    private Long retOrgId;

    @SerializedName("retUserId")
    @Expose
    private Integer retUserId;

    @SerializedName("retailDomainCode")
    @Expose
    private String retailDomainCode;

    @SerializedName("token")
    @Expose
    private String token;

    public String getPlayerOperationType() {
        return playerOperationType;
    }

    public void setPlayerOperationType(String playerOperationType) {
        this.playerOperationType = playerOperationType;
    }

    public String getPlayerUserName() {
        return playerUserName;
    }

    public void setPlayerUserName(String playerUserName) {
        this.playerUserName = playerUserName;
    }

    public String getPlrDomainCode() {
        return plrDomainCode;
    }

    public void setPlrDomainCode(String plrDomainCode) {
        this.plrDomainCode = plrDomainCode;
    }

    public Long getRetOrgId() {
        return retOrgId;
    }

    public void setRetOrgId(Long retOrgId) {
        this.retOrgId = retOrgId;
    }

    public Integer getRetUserId() {
        return retUserId;
    }

    public void setRetUserId(Integer retUserId) {
        this.retUserId = retUserId;
    }

    public String getRetailDomainCode() {
        return retailDomainCode;
    }

    public void setRetailDomainCode(String retailDomainCode) {
        this.retailDomainCode = retailDomainCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public String toString() {
        return "OlaPlayerForgotPasswordRequestBean{" + "playerOperationType='" + playerOperationType + '\'' + ", playerUserName='" + playerUserName + '\'' + ", plrDomainCode='" + plrDomainCode + '\'' + ", retOrgId=" + retOrgId + ", retUserId=" + retUserId + ", retailDomainCode='" + retailDomainCode + '\'' + ", token='" + token + '\'' + '}';
    }
}
