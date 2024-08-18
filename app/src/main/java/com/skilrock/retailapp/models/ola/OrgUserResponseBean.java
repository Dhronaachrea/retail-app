package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrgUserResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public static class ResponseData {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("data")
        @Expose
        private ArrayList<UserData> data = null;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public ArrayList<UserData> getData() {
            return data;
        }

        public void setData(ArrayList<UserData> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "message='" + message + '\'' + ", statusCode=" + statusCode + ", data=" + data + '}';
        }
    }

    public static class UserData {

        @SerializedName("wareHouseRequired")
        @Expose
        private String wareHouseRequired;
        @SerializedName("orgName")
        @Expose
        private String orgName;
        @SerializedName("userStatus")
        @Expose
        private String userStatus;
        @SerializedName("mobileNumber")
        @Expose
        private String mobileNumber;
        @SerializedName("emailId")
        @Expose
        private String emailId;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("rawdisplayBalance")
        @Expose
        private Double rawdisplayBalance;
        @SerializedName("displayBalance")
        @Expose
        private String displayBalance;
        @SerializedName("orgStatus")
        @Expose
        private String orgStatus;
        @SerializedName("orgType")
        @Expose
        private String orgType;
        @SerializedName("regionRequired")
        @Expose
        private String regionRequired;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("roleName")
        @Expose
        private String roleName;

        public String getWareHouseRequired() {
            return wareHouseRequired;
        }

        public void setWareHouseRequired(String wareHouseRequired) {
            this.wareHouseRequired = wareHouseRequired;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getDisplayBalance() {
            return displayBalance;
        }

        public void setDisplayBalance(String displayBalance) {
            this.displayBalance = displayBalance;
        }

        public String getOrgStatus() {
            return orgStatus;
        }

        public void setOrgStatus(String orgStatus) {
            this.orgStatus = orgStatus;
        }

        public String getOrgType() {
            return orgType;
        }

        public void setOrgType(String orgType) {
            this.orgType = orgType;
        }

        public String getRegionRequired() {
            return regionRequired;
        }

        public void setRegionRequired(String regionRequired) {
            this.regionRequired = regionRequired;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public Double getRawdisplayBalance() {
            return rawdisplayBalance;
        }

        public void setRawdisplayBalance(Double rawdisplayBalance) {
            this.rawdisplayBalance = rawdisplayBalance;
        }

        @NonNull
        @Override
        public String toString() {
            return "UserData{" + "wareHouseRequired='" + wareHouseRequired + '\'' + ", orgName='" + orgName + '\'' + ", userStatus='" + userStatus + '\'' + ", mobileNumber='" + mobileNumber + '\'' + ", emailId='" + emailId + '\'' + ", userName='" + userName + '\'' + ", userId=" + userId + ", rawdisplayBalance=" + rawdisplayBalance + ", displayBalance='" + displayBalance + '\'' + ", orgStatus='" + orgStatus + '\'' + ", orgType='" + orgType + '\'' + ", regionRequired='" + regionRequired + '\'' + ", name='" + name + '\'' + ", roleName='" + roleName + '\'' + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "OrgUserResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}