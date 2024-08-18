package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DeviceRegistrationBean {

    @SerializedName("brandId")
    @Expose
    private Integer brandId;

    @SerializedName("inventoryId")
    @Expose
    private Integer inventoryId;

    @SerializedName("modelId")
    @Expose
    private Integer modelId;

    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;

    @SerializedName("serviceCode")
    @Expose
    private ArrayList<String> serviceCode = null;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ArrayList<String> getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(ArrayList<String> serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Override
    public String toString() {
        return "DeviceRegistrationBean{" + "brandId=" + brandId + ", inventoryId=" + inventoryId + ", modelId=" + modelId + ", serialNumber='" + serialNumber + '\'' + ", serviceCode=" + serviceCode + '}';
    }
}
