package com.skilrock.retailapp.models.rms;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class UserRegistrationRequestBean {

    @SerializedName("addressOne")
    @Expose
    private String addressOne;
    @SerializedName("addressTwo")
    @Expose
    private String addressTwo;
    @SerializedName("altMobileNumber")
    @Expose
    private String altMobileNumber;
    @SerializedName("cityCode")
    @Expose
    private String cityCode;
    @SerializedName("contactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("domainId")
    @Expose
    private Integer domainId;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("onlyClient")
    @Expose
    private Boolean onlyClient;
    @SerializedName("orgId")
    @Expose
    private Integer orgId;
    @SerializedName("regionIds")
    @Expose
    private List<Integer> regionIds = null;
    @SerializedName("requestFrom")
    @Expose
    private String requestFrom;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("stateCode")
    @Expose
    private String stateCode;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("warehouseId")
    @Expose
    private Integer warehouseId;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobileCode")
    @Expose
    private String mobileCode;

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

    public String getAltMobileNumber() {
        return altMobileNumber;
    }

    public void setAltMobileNumber(String altMobileNumber) {
        this.altMobileNumber = altMobileNumber;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Boolean getOnlyClient() {
        return onlyClient;
    }

    public void setOnlyClient(Boolean onlyClient) {
        this.onlyClient = onlyClient;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public List<Integer> getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(List<Integer> regionIds) {
        this.regionIds = regionIds;
    }

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Override
    public String toString() {
        return "UserRegistrationRequestBean{" +
                "addressOne='" + addressOne + '\'' +
                ", addressTwo='" + addressTwo + '\'' +
                ", altMobileNumber='" + altMobileNumber + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", domainId=" + domainId +
                ", emailId='" + emailId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", onlyClient=" + onlyClient +
                ", orgId=" + orgId +
                ", regionIds=" + regionIds +
                ", requestFrom='" + requestFrom + '\'' +
                ", roleId=" + roleId +
                ", stateCode='" + stateCode + '\'' +
                ", userName='" + userName + '\'' +
                ", warehouseId=" + warehouseId +
                ", zipCode='" + zipCode + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                        ", mobileCode='" + mobileCode + '\'' +
                '}';
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }
}