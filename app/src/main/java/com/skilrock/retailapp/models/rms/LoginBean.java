package com.skilrock.retailapp.models.rms;

import org.jetbrains.annotations.NotNull;

public class LoginBean {

    private int responseCode;
    private String responseMessage;
    private String token;
    private ResponseData responseData;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    @NotNull
    @Override
    public String toString() {
        return "LoginBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", token='" + token + '\'' + ", responseData=" + responseData + '}';
    }

    public static class ResponseData {

        private String message;
        private int statusCode;
        private Data data;

        public void setMessage(String message) {
            this.message = message;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public Data getData() {
            return data;
        }

        @NotNull
        @Override
        public String toString() {
            return "ResponseData{" + "message='" + message + '\'' + ", statusCode=" + statusCode + ", data=" + data + '}';
        }

        public static class Data {
            private String orgTypeCode, orgName, accessSelfMerchantOnly, walletType, userId, walletMode, balance,
                    merchantId, parentAgtOrgId, parentMagtOrgId, creditLimit, parentSagtOrgId, username, orgCode, userStatus,isAffiliate, mobileNumber, mobileCode;

            private long orgId;
            private long domainId;

            private String lastName, isHead, orgStatus, firstName, accessSelfDomainOnly, regionBinding, userBalance, qrCode;
            private Double rawUserBalance;

            public String getQrCode() {
                return qrCode;
            }

            public void setQrCode(String qrCode) {
                this.qrCode = qrCode;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getIsHead() {
                return isHead;
            }

            public void setIsHead(String isHead) {
                this.isHead = isHead;
            }

            public String getOrgStatus() {
                return orgStatus;
            }

            public void setOrgStatus(String orgStatus) {
                this.orgStatus = orgStatus;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getAccessSelfDomainOnly() {
                return accessSelfDomainOnly;
            }

            public void setAccessSelfDomainOnly(String accessSelfDomainOnly) {
                this.accessSelfDomainOnly = accessSelfDomainOnly;
            }

            public String getRegionBinding() {
                return regionBinding;
            }

            public void setRegionBinding(String regionBinding) {
                this.regionBinding = regionBinding;
            }

            public String getUserBalance() {
                return userBalance;
            }

            public void setUserBalance(String userBalance) {
                this.userBalance = userBalance;
            }

            public String getUserStatus() {
                return userStatus;
            }

            public void setUserStatus(String userStatus) {
                this.userStatus = userStatus;
            }

            public long getDomainId() {
                return domainId;
            }

            public void setDomainId(long domainId) {
                this.domainId = domainId;
            }

            public String getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(String orgCode) {
                this.orgCode = orgCode;
            }

            public String getOrgTypeCode() {
                return orgTypeCode;
            }

            public void setOrgTypeCode(String orgTypeCode) {
                this.orgTypeCode = orgTypeCode;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getAccessSelfMerchantOnly() {
                return accessSelfMerchantOnly;
            }

            public void setAccessSelfMerchantOnly(String accessSelfMerchantOnly) {
                this.accessSelfMerchantOnly = accessSelfMerchantOnly;
            }

            public String getWalletType() {
                return walletType;
            }

            public void setWalletType(String walletType) {
                this.walletType = walletType;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public long getOrgId() {
                return orgId;
            }

            public void setOrgId(long orgId) {
                this.orgId = orgId;
            }

            public String getWalletMode() {
                return walletMode;
            }

            public void setWalletMode(String walletMode) {
                this.walletMode = walletMode;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }

            public String getParentAgtOrgId() {
                return parentAgtOrgId;
            }

            public void setParentAgtOrgId(String parentAgtOrgId) {
                this.parentAgtOrgId = parentAgtOrgId;
            }

            public String getParentMagtOrgId() {
                return parentMagtOrgId;
            }

            public void setParentMagtOrgId(String parentMagtOrgId) {
                this.parentMagtOrgId = parentMagtOrgId;
            }

            public String getCreditLimit() {
                return creditLimit;
            }

            public void setCreditLimit(String creditLimit) {
                this.creditLimit = creditLimit;
            }

            public String getMobileNumber() { return mobileNumber; }

            public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

            public String getMobileCode() { return mobileCode; }

            public void setMobileCode(String mobileCode) { this.mobileCode = mobileCode; }

            public String getParentSagtOrgId() {
                return parentSagtOrgId;
            }

            public void setParentSagtOrgId(String parentSagtOrgId) {
                this.parentSagtOrgId = parentSagtOrgId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public Double getRawUserBalance() {
                return rawUserBalance;
            }

            public void setRawUserBalance(Double rawUserBalance) {
                this.rawUserBalance = rawUserBalance;
            }

            public String getIsAffiliate() {
                return isAffiliate;
            }

            public void setIsAffiliate(String isAffiliate) {
                this.isAffiliate = isAffiliate;
            }

            @Override
            public String toString() {
                return "Data{" +
                        "orgTypeCode='" + orgTypeCode + '\'' +
                        ", orgName='" + orgName + '\'' +
                        ", accessSelfMerchantOnly='" + accessSelfMerchantOnly + '\'' +
                        ", walletType='" + walletType + '\'' +
                        ", userId='" + userId + '\'' +
                        ", walletMode='" + walletMode + '\'' +
                        ", balance='" + balance + '\'' +
                        ", merchantId='" + merchantId + '\'' +
                        ", parentAgtOrgId='" + parentAgtOrgId + '\'' +
                        ", parentMagtOrgId='" + parentMagtOrgId + '\'' +
                        ", creditLimit='" + creditLimit + '\'' +
                        ", parentSagtOrgId='" + parentSagtOrgId + '\'' +
                        ", username='" + username + '\'' +
                        ", orgCode='" + orgCode + '\'' +
                        ", userStatus='" + userStatus + '\'' +
                        ", isAffiliate='" + isAffiliate + '\'' +
                        ", orgId=" + orgId +
                        ", domainId=" + domainId +
                        ", mobileNo=" + mobileNumber +
                        ", mobileCode=" + mobileCode +
                        ", lastName='" + lastName + '\'' +
                        ", isHead='" + isHead + '\'' +
                        ", orgStatus='" + orgStatus + '\'' +
                        ", firstName='" + firstName + '\'' +
                        ", accessSelfDomainOnly='" + accessSelfDomainOnly + '\'' +
                        ", regionBinding='" + regionBinding + '\'' +
                        ", userBalance='" + userBalance + '\'' +
                        ", qrCode='" + qrCode + '\'' +
                        ", rawUserBalance=" + rawUserBalance +
                        '}';
            }
        }
    }
}
