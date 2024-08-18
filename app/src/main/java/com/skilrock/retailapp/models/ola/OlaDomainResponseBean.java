package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OlaDomainResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("responseData")
    @Expose
    private ArrayList<ResponseData> responseData = null;

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

    public ArrayList<ResponseData> getResponseData() {
        return responseData;
    }

    public void setResponseData(ArrayList<ResponseData> responseData) {
        this.responseData = responseData;
    }

    public static class ResponseData {

        @SerializedName("merchantName")
        @Expose
        private String merchantName;

        @SerializedName("merchantCode")
        @Expose
        private String merchantCode;

        @SerializedName("domainCode")
        @Expose
        private String domainCode;

        @SerializedName("domainDisplayName")
        @Expose
        private String domainDisplayName;

        @SerializedName("registrationJson")
        @Expose
        private String registrationJson;

        @SerializedName("depositJson")
        @Expose
        private String depositJson;

        @SerializedName("withdrawalJson")
        @Expose
        private String withdrawalJson;

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }

        public String getDomainCode() {
            return domainCode;
        }

        public void setDomainCode(String domainCode) {
            this.domainCode = domainCode;
        }

        public String getDomainDisplayName() {
            return domainDisplayName;
        }

        public void setDomainDisplayName(String domainDisplayName) {
            this.domainDisplayName = domainDisplayName;
        }

        public String getRegistrationJson() {
            return registrationJson;
        }

        public void setRegistrationJson(String registrationJson) {
            this.registrationJson = registrationJson;
        }

        public String getDepositJson() {
            return depositJson;
        }

        public void setDepositJson(String depositJson) {
            this.depositJson = depositJson;
        }

        public String getWithdrawalJson() {
            return withdrawalJson;
        }

        public void setWithdrawalJson(String withdrawalJson) {
            this.withdrawalJson = withdrawalJson;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "merchantName='" + merchantName + '\'' + ", merchantCode='" + merchantCode + '\'' + ", domainCode='" + domainCode + '\'' + ", domainDisplayName='" + domainDisplayName + '\'' + ", registrationJson='" + registrationJson + '\'' + ", depositJson='" + depositJson + '\'' + ", withdrawalJson='" + withdrawalJson + '\'' + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "OlaDomainResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
