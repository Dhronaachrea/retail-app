package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyPosRequestBean {

    @SerializedName("latitudes")
    @Expose
    private String latitudes;

    @SerializedName("longitudes")
    @Expose
    private String longitudes;

    @SerializedName("modelCode")
    @Expose
    private String modelCode;

    @SerializedName("simType")
    @Expose
    private String simType;

    @SerializedName("terminalId")
    @Expose
    private String terminalId;

    @SerializedName("version")
    @Expose
    private String version;

    public String getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(String latitudes) {
        this.latitudes = latitudes;
    }

    public String getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(String longitudes) {
        this.longitudes = longitudes;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getSimType() {
        return simType;
    }

    public void setSimType(String simType) {
        this.simType = simType;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @NonNull
    @Override
    public String toString() {
        return "VerifyPosRequestBean{" + "latitudes='" + latitudes + '\'' + ", longitudes='" + longitudes + '\'' + ", modelCode='" + modelCode + '\'' + ", simType='" + simType + '\'' + ", terminalId='" + terminalId + '\'' + ", version='" + version + '\'' + '}';
    }
}
