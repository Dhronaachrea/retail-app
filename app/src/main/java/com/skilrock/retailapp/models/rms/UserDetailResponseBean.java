package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class UserDetailResponseBean implements Serializable {

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

    public static class ResponseData implements Serializable {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("data")
        @Expose
        private Data data;

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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public static class Data implements Serializable {

        @SerializedName("orgId")
        @Expose
        private Integer orgId;
        @SerializedName("roleId")
        @Expose
        private Integer roleId;
        @SerializedName("isHead")
        @Expose
        private String isHead;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("middleName")
        @Expose
        private String middleName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("contactPerson")
        @Expose
        private String contactPerson;
        @SerializedName("mobileNumber")
        @Expose
        private String mobileNumber;
        @SerializedName("altMobileNumber")
        @Expose
        private String altMobileNumber;
        @SerializedName("emailId")
        @Expose
        private String emailId;
        @SerializedName("addressOne")
        @Expose
        private String addressOne;
        @SerializedName("addressTwo")
        @Expose
        private String addressTwo;
        @SerializedName("zipCode")
        @Expose
        private String zipCode;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("countryCode")
        @Expose
        private String countryCode;
        @SerializedName("servingRegion")
        @Expose
        private Object servingRegion;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("posVerificationRequired")
        @Expose
        private Integer posVerificationRequired;
        @SerializedName("blockDate")
        @Expose
        private String blockDate;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("mobileCode")
        @Expose
        private String mobileCode;

        public Integer getOrgId() {
            return orgId;
        }

        public void setOrgId(Integer orgId) {
            this.orgId = orgId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public String getIsHead() {
            return isHead;
        }

        public void setIsHead(String isHead) {
            this.isHead = isHead;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getAltMobileNumber() {
            return altMobileNumber;
        }

        public void setAltMobileNumber(String altMobileNumber) {
            this.altMobileNumber = altMobileNumber;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getAddressOne() {
            return addressOne;
        }

        public void setAddressOne(String addressOne) {
            this.addressOne = addressOne;
        }

        public String getAddressTwo() {
            return addressTwo;
        }

        public void setAddressTwo(String addressTwo) {
            this.addressTwo = addressTwo;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public Object getServingRegion() {
            return servingRegion;
        }

        public void setServingRegion(Object servingRegion) {
            this.servingRegion = servingRegion;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getPosVerificationRequired() {
            return posVerificationRequired;
        }

        public void setPosVerificationRequired(Integer posVerificationRequired) {
            this.posVerificationRequired = posVerificationRequired;
        }

        public String getBlockDate() {
            return blockDate;
        }

        public void setBlockDate(String blockDate) {
            this.blockDate = blockDate;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @NotNull
        @Override
        public String toString() {
            return "Data{" +
                    "orgId=" + orgId +
                    ", roleId=" + roleId +
                    ", isHead='" + isHead + '\'' +
                    ", username='" + username + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", middleName='" + middleName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", contactPerson='" + contactPerson + '\'' +
                    ", mobileNumber='" + mobileNumber + '\'' +
                    ", altMobileNumber='" + altMobileNumber + '\'' +
                    ", emailId='" + emailId + '\'' +
                    ", addressOne='" + addressOne + '\'' +
                    ", addressTwo='" + addressTwo + '\'' +
                    ", zipCode='" + zipCode + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    ", servingRegion=" + servingRegion +
                    ", status=" + status +
                    ", posVerificationRequired=" + posVerificationRequired +
                    ", blockDate='" + blockDate + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        public String getMobileCode() {
            return mobileCode;
        }

        public void setMobileCode(String mobileCode) {
            this.mobileCode = mobileCode;
        }
    }
}