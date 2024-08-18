package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceListResponseBean {

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
    public class ResponseData {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

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

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }
    }

    public class Datum {

        @SerializedName("engineServiceId")
        @Expose
        private String engineServiceId;
        @SerializedName("serviceCode")
        @Expose
        private String serviceCode;
        @SerializedName("commDefinationLevel")
        @Expose
        private String commDefinationLevel;
        @SerializedName("serviceDisplayName")
        @Expose
        private String serviceDisplayName;
        @SerializedName("serviceId")
        @Expose
        private Integer serviceId;
        @SerializedName("serviceName")
        @Expose
        private String serviceName;
        @SerializedName("combinedTierTxn")
        @Expose
        private String combinedTierTxn;

        public String getEngineServiceId() {
            return engineServiceId;
        }

        public void setEngineServiceId(String engineServiceId) {
            this.engineServiceId = engineServiceId;
        }

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getCommDefinationLevel() {
            return commDefinationLevel;
        }

        public void setCommDefinationLevel(String commDefinationLevel) {
            this.commDefinationLevel = commDefinationLevel;
        }

        public String getServiceDisplayName() {
            return serviceDisplayName;
        }

        public void setServiceDisplayName(String serviceDisplayName) {
            this.serviceDisplayName = serviceDisplayName;
        }

        public Integer getServiceId() {
            return serviceId;
        }

        public void setServiceId(Integer serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getCombinedTierTxn() {
            return combinedTierTxn;
        }

        public void setCombinedTierTxn(String combinedTierTxn) {
            this.combinedTierTxn = combinedTierTxn;
        }
    }
}