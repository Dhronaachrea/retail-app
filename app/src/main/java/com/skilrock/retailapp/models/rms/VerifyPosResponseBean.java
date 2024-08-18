package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyPosResponseBean {

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

    @NonNull
    @Override
    public String toString() {
        return "VerifyPosResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
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

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "message='" + message + '\'' + ", statusCode=" + statusCode + ", data=" + data + '}';
        }

        public static class Data {

            @SerializedName("latestVersion")
            @Expose
            private LatestVersion latestVersion;

            public LatestVersion getLatestVersion() {
                return latestVersion;
            }

            public void setLatestVersion(LatestVersion latestVersion) {
                this.latestVersion = latestVersion;
            }

            @NonNull
            @Override
            public String toString() {
                return "Data{" + "latestVersion=" + latestVersion + '}';
            }

            public static class LatestVersion {

                @SerializedName("fileSize")
                @Expose
                private String fileSize;

                @SerializedName("downloadURL")
                @Expose
                private String downloadURL;

                @SerializedName("version")
                @Expose
                private String version;

                @SerializedName("isMandatory")
                @Expose
                private String isMandatory;

                @SerializedName("appRemark")
                @Expose
                private String appRemark;

                public String getAppRemark() {
                    return appRemark;
                }

                public void setAppRemark(String appRemark) {
                    this.appRemark = appRemark;
                }

                public String getFileSize() {
                    return fileSize;
                }

                public void setFileSize(String fileSize) {
                    this.fileSize = fileSize;
                }

                public String getDownloadURL() {
                    return downloadURL;
                }

                public void setDownloadURL(String downloadURL) {
                    this.downloadURL = downloadURL;
                }

                public String getVersion() {
                    return version;
                }

                public void setVersion(String version) {
                    this.version = version;
                }

                public String getIsMandatory() {
                    return isMandatory;
                }

                public void setIsMandatory(String isMandatory) {
                    this.isMandatory = isMandatory;
                }

                public boolean isEmpty() {
                    return fileSize == null && downloadURL == null && appRemark == null && version == null && isMandatory == null;
                }

                @NonNull
                @Override
                public String toString() {
                    return "LatestVersion{" + "fileSize='" + fileSize + '\'' + ", downloadURL='" + downloadURL + '\'' + ", version='" + version + '\'' + ", isMandatory='" + isMandatory + '\'' + ", appRemark='" + appRemark + '\'' + '}';
                }
            }
        }
    }
}
