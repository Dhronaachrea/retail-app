package com.skilrock.retailapp.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionBean {

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

        public static class Data {

            @SerializedName("appVersion")
            @Expose
            private String appVersion;

            @SerializedName("downloadPath")
            @Expose
            private String downloadPath;

            @SerializedName("appType")
            @Expose
            private String appType;

            @SerializedName("appRemark")
            @Expose
            private String appRemark;

            @SerializedName("downloadMode")
            @Expose
            private String downloadMode;

            public String getAppVersion() {
                return appVersion;
            }

            public void setAppVersion(String appVersion) {
                this.appVersion = appVersion;
            }

            public String getDownloadPath() {
                return downloadPath;
            }

            public void setDownloadPath(String downloadPath) {
                this.downloadPath = downloadPath;
            }

            public String getAppType() {
                return appType;
            }

            public void setAppType(String appType) {
                this.appType = appType;
            }

            public String getAppRemark() {
                return appRemark;
            }

            public void setAppRemark(String appRemark) {
                this.appRemark = appRemark;
            }

            public String getDownloadMode() {
                return downloadMode;
            }

            public void setDownloadMode(String downloadMode) {
                this.downloadMode = downloadMode;
            }

            @NonNull
            @Override
            public String toString() {
                return "Data{" + "appVersion='" + appVersion + '\'' + ", downloadPath='" + downloadPath + '\'' + ", appType='" + appType + '\'' + ", appRemark='" + appRemark + '\'' + ", downloadMode='" + downloadMode + '\'' + '}';
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "message='" + message + '\'' + ", statusCode=" + statusCode + ", data=" + data + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "VersionBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
