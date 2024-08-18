package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoleListData {

    @SerializedName("warehouseRequired")
    @Expose
    private String warehouseRequired;
    @SerializedName("regionRequired")
    @Expose
    private String regionRequired;
    @SerializedName("roleDisplayName")
    @Expose
    private String roleDisplayName;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;

    public String getWarehouseRequired() {
        return warehouseRequired;
    }

    public void setWarehouseRequired(String warehouseRequired) {
        this.warehouseRequired = warehouseRequired;
    }

    public String getRegionRequired() {
        return regionRequired;
    }

    public void setRegionRequired(String regionRequired) {
        this.regionRequired = regionRequired;
    }

    public String getRoleDisplayName() {
        return roleDisplayName;
    }

    public void setRoleDisplayName(String roleDisplayName) {
        this.roleDisplayName = roleDisplayName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}