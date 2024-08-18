package com.skilrock.retailapp.models.rms;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchUserResponseBeanNew {

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SearchUserResponseBeanNew.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("responseCode");
        sb.append('=');
        sb.append(((this.responseCode == null)?"<null>":this.responseCode));
        sb.append(',');
        sb.append("responseMessage");
        sb.append('=');
        sb.append(((this.responseMessage == null)?"<null>":this.responseMessage));
        sb.append(',');
        sb.append("responseData");
        sb.append('=');
        sb.append(((this.responseData == null)?"<null>":this.responseData));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public class Data {

        @SerializedName("csvUrl")
        @Expose
        private String csvUrl;
        @SerializedName("userSearchData")
        @Expose
        private ArrayList<UserSearchDatum> userSearchData = null;
        @SerializedName("xlsUrl")
        @Expose
        private String xlsUrl;

        public String getCsvUrl() {
            return csvUrl;
        }

        public void setCsvUrl(String csvUrl) {
            this.csvUrl = csvUrl;
        }

        public ArrayList<UserSearchDatum> getUserSearchData() {
            return userSearchData;
        }

        public void setUserSearchData(ArrayList<UserSearchDatum> userSearchData) {
            this.userSearchData = userSearchData;
        }

        public String getXlsUrl() {
            return xlsUrl;
        }

        public void setXlsUrl(String xlsUrl) {
            this.xlsUrl = xlsUrl;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Data.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("csvUrl");
            sb.append('=');
            sb.append(((this.csvUrl == null)?"<null>":this.csvUrl));
            sb.append(',');
            sb.append("userSearchData");
            sb.append('=');
            sb.append(((this.userSearchData == null)?"<null>":this.userSearchData));
            sb.append(',');
            sb.append("xlsUrl");
            sb.append('=');
            sb.append(((this.xlsUrl == null)?"<null>":this.xlsUrl));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }

    public class ResponseData {

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

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ResponseData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("message");
            sb.append('=');
            sb.append(((this.message == null)?"<null>":this.message));
            sb.append(',');
            sb.append("statusCode");
            sb.append('=');
            sb.append(((this.statusCode == null)?"<null>":this.statusCode));
            sb.append(',');
            sb.append("data");
            sb.append('=');
            sb.append(((this.data == null)?"<null>":this.data));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }

    public class UserSearchDatum {

        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("mobileCode")
        @Expose
        private String mobileCode;
        @SerializedName("mobileCodeNumber")
        @Expose
        private String mobileCodeNumber;
        @SerializedName("mobileNumber")
        @Expose
        private String mobileNumber;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("emailId")
        @Expose
        private String emailId;
        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("orgName")
        @Expose
        private String orgName;
        @SerializedName("orgTypeCode")
        @Expose
        private String orgTypeCode;
        @SerializedName("orgType")
        @Expose
        private String orgType;
        @SerializedName("orgId")
        @Expose
        private Integer orgId;
        @SerializedName("isHead")
        @Expose
        private String isHead;
        @SerializedName("userStatus")
        @Expose
        private String userStatus;
        @SerializedName("orgStatus")
        @Expose
        private String orgStatus;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("displayBalance")
        @Expose
        private String displayBalance;
        @SerializedName("rawdisplayBalance")
        @Expose
        private Double rawdisplayBalance;
        @SerializedName("roleDisplayName")
        @Expose
        private String roleDisplayName;
        @SerializedName("warehouseRequired")
        @Expose
        private String warehouseRequired;
        @SerializedName("regionRequired")
        @Expose
        private String regionRequired;
        @SerializedName("roleName")
        @Expose
        private String roleName;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("isFinanceHead")
        @Expose
        private String isFinanceHead;
        @SerializedName("createdAt")
        @Expose
        private Long createdAt;
        @SerializedName("registrationDate")
        @Expose
        private String registrationDate;
        @SerializedName("domainId")
        @Expose
        private Integer domainId;
        @SerializedName("roleId")
        @Expose
        private Integer roleId;
        @SerializedName("parentMagtOrgId")
        @Expose
        private Integer parentMagtOrgId;
        @SerializedName("parentAgtOrgId")
        @Expose
        private Integer parentAgtOrgId;
        @SerializedName("parentSagtOrgId")
        @Expose
        private Integer parentSagtOrgId;
        @SerializedName("orgCode")
        @Expose
        private String orgCode;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobileCode() {
            return mobileCode;
        }

        public void setMobileCode(String mobileCode) {
            this.mobileCode = mobileCode;
        }

        public String getMobileCodeNumber() {
            return mobileCodeNumber;
        }

        public void setMobileCodeNumber(String mobileCodeNumber) {
            this.mobileCodeNumber = mobileCodeNumber;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
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

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOrgTypeCode() {
            return orgTypeCode;
        }

        public void setOrgTypeCode(String orgTypeCode) {
            this.orgTypeCode = orgTypeCode;
        }

        public String getOrgType() {
            return orgType;
        }

        public void setOrgType(String orgType) {
            this.orgType = orgType;
        }

        public Integer getOrgId() {
            return orgId;
        }

        public void setOrgId(Integer orgId) {
            this.orgId = orgId;
        }

        public String getIsHead() {
            return isHead;
        }

        public void setIsHead(String isHead) {
            this.isHead = isHead;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getOrgStatus() {
            return orgStatus;
        }

        public void setOrgStatus(String orgStatus) {
            this.orgStatus = orgStatus;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDisplayBalance() {
            return displayBalance;
        }

        public void setDisplayBalance(String displayBalance) {
            this.displayBalance = displayBalance;
        }

        public Double getRawdisplayBalance() {
            return rawdisplayBalance;
        }

        public void setRawdisplayBalance(Double rawdisplayBalance) {
            this.rawdisplayBalance = rawdisplayBalance;
        }

        public String getRoleDisplayName() {
            return roleDisplayName;
        }

        public void setRoleDisplayName(String roleDisplayName) {
            this.roleDisplayName = roleDisplayName;
        }

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

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getIsFinanceHead() {
            return isFinanceHead;
        }

        public void setIsFinanceHead(String isFinanceHead) {
            this.isFinanceHead = isFinanceHead;
        }

        public Long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Long createdAt) {
            this.createdAt = createdAt;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }

        public void setRegistrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
        }

        public Integer getDomainId() {
            return domainId;
        }

        public void setDomainId(Integer domainId) {
            this.domainId = domainId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public Integer getParentMagtOrgId() {
            return parentMagtOrgId;
        }

        public void setParentMagtOrgId(Integer parentMagtOrgId) {
            this.parentMagtOrgId = parentMagtOrgId;
        }

        public Integer getParentAgtOrgId() {
            return parentAgtOrgId;
        }

        public void setParentAgtOrgId(Integer parentAgtOrgId) {
            this.parentAgtOrgId = parentAgtOrgId;
        }

        public Integer getParentSagtOrgId() {
            return parentSagtOrgId;
        }

        public void setParentSagtOrgId(Integer parentSagtOrgId) {
            this.parentSagtOrgId = parentSagtOrgId;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(UserSearchDatum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("userName");
            sb.append('=');
            sb.append(((this.userName == null)?"<null>":this.userName));
            sb.append(',');
            sb.append("name");
            sb.append('=');
            sb.append(((this.name == null)?"<null>":this.name));
            sb.append(',');
            sb.append("mobileCode");
            sb.append('=');
            sb.append(((this.mobileCode == null)?"<null>":this.mobileCode));
            sb.append(',');
            sb.append("mobileCodeNumber");
            sb.append('=');
            sb.append(((this.mobileCodeNumber == null)?"<null>":this.mobileCodeNumber));
            sb.append(',');
            sb.append("mobileNumber");
            sb.append('=');
            sb.append(((this.mobileNumber == null)?"<null>":this.mobileNumber));
            sb.append(',');
            sb.append("firstName");
            sb.append('=');
            sb.append(((this.firstName == null)?"<null>":this.firstName));
            sb.append(',');
            sb.append("lastName");
            sb.append('=');
            sb.append(((this.lastName == null)?"<null>":this.lastName));
            sb.append(',');
            sb.append("emailId");
            sb.append('=');
            sb.append(((this.emailId == null)?"<null>":this.emailId));
            sb.append(',');
            sb.append("userId");
            sb.append('=');
            sb.append(((this.userId == null)?"<null>":this.userId));
            sb.append(',');
            sb.append("orgName");
            sb.append('=');
            sb.append(((this.orgName == null)?"<null>":this.orgName));
            sb.append(',');
            sb.append("orgTypeCode");
            sb.append('=');
            sb.append(((this.orgTypeCode == null)?"<null>":this.orgTypeCode));
            sb.append(',');
            sb.append("orgType");
            sb.append('=');
            sb.append(((this.orgType == null)?"<null>":this.orgType));
            sb.append(',');
            sb.append("orgId");
            sb.append('=');
            sb.append(((this.orgId == null)?"<null>":this.orgId));
            sb.append(',');
            sb.append("isHead");
            sb.append('=');
            sb.append(((this.isHead == null)?"<null>":this.isHead));
            sb.append(',');
            sb.append("userStatus");
            sb.append('=');
            sb.append(((this.userStatus == null)?"<null>":this.userStatus));
            sb.append(',');
            sb.append("orgStatus");
            sb.append('=');
            sb.append(((this.orgStatus == null)?"<null>":this.orgStatus));
            sb.append(',');
            sb.append("type");
            sb.append('=');
            sb.append(((this.type == null)?"<null>":this.type));
            sb.append(',');
            sb.append("displayBalance");
            sb.append('=');
            sb.append(((this.displayBalance == null)?"<null>":this.displayBalance));
            sb.append(',');
            sb.append("rawdisplayBalance");
            sb.append('=');
            sb.append(((this.rawdisplayBalance == null)?"<null>":this.rawdisplayBalance));
            sb.append(',');
            sb.append("roleDisplayName");
            sb.append('=');
            sb.append(((this.roleDisplayName == null)?"<null>":this.roleDisplayName));
            sb.append(',');
            sb.append("warehouseRequired");
            sb.append('=');
            sb.append(((this.warehouseRequired == null)?"<null>":this.warehouseRequired));
            sb.append(',');
            sb.append("regionRequired");
            sb.append('=');
            sb.append(((this.regionRequired == null)?"<null>":this.regionRequired));
            sb.append(',');
            sb.append("roleName");
            sb.append('=');
            sb.append(((this.roleName == null)?"<null>":this.roleName));
            sb.append(',');
            sb.append("city");
            sb.append('=');
            sb.append(((this.city == null)?"<null>":this.city));
            sb.append(',');
            sb.append("state");
            sb.append('=');
            sb.append(((this.state == null)?"<null>":this.state));
            sb.append(',');
            sb.append("country");
            sb.append('=');
            sb.append(((this.country == null)?"<null>":this.country));
            sb.append(',');
            sb.append("isFinanceHead");
            sb.append('=');
            sb.append(((this.isFinanceHead == null)?"<null>":this.isFinanceHead));
            sb.append(',');
            sb.append("createdAt");
            sb.append('=');
            sb.append(((this.createdAt == null)?"<null>":this.createdAt));
            sb.append(',');
            sb.append("registrationDate");
            sb.append('=');
            sb.append(((this.registrationDate == null)?"<null>":this.registrationDate));
            sb.append(',');
            sb.append("domainId");
            sb.append('=');
            sb.append(((this.domainId == null)?"<null>":this.domainId));
            sb.append(',');
            sb.append("roleId");
            sb.append('=');
            sb.append(((this.roleId == null)?"<null>":this.roleId));
            sb.append(',');
            sb.append("parentMagtOrgId");
            sb.append('=');
            sb.append(((this.parentMagtOrgId == null)?"<null>":this.parentMagtOrgId));
            sb.append(',');
            sb.append("parentAgtOrgId");
            sb.append('=');
            sb.append(((this.parentAgtOrgId == null)?"<null>":this.parentAgtOrgId));
            sb.append(',');
            sb.append("parentSagtOrgId");
            sb.append('=');
            sb.append(((this.parentSagtOrgId == null)?"<null>":this.parentSagtOrgId));
            sb.append(',');
            sb.append("orgCode");
            sb.append('=');
            sb.append(((this.orgCode == null)?"<null>":this.orgCode));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }

}
