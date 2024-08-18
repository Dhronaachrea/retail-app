package com.skilrock.retailapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPreAppVersionBean {

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

        public static class Data {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("appTypeId")
            @Expose
            private Integer appTypeId;
            @SerializedName("appId")
            @Expose
            private Integer appId;
            @SerializedName("version")
            @Expose
            private String version;
            @SerializedName("isMandatory")
            @Expose
            private String isMandatory;
            @SerializedName("fileSize")
            @Expose
            private String fileSize;
            @SerializedName("downloadStatus")
            @Expose
            private String downloadStatus;
            @SerializedName("versionStatus")
            @Expose
            private String versionStatus;
            @SerializedName("downloadUrl")
            @Expose
            private String downloadUrl;
            @SerializedName("createdBy")
            @Expose
            private Integer createdBy;
            @SerializedName("appRemark")
            @Expose
            private String appRemark;
            @SerializedName("createdAt")
            @Expose
            private Long createdAt;
            @SerializedName("isLatest")
            @Expose
            private String isLatest;
            @SerializedName("updatedAt")
            @Expose
            private Long updatedAt;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getAppTypeId() {
                return appTypeId;
            }

            public void setAppTypeId(Integer appTypeId) {
                this.appTypeId = appTypeId;
            }

            public Integer getAppId() {
                return appId;
            }

            public void setAppId(Integer appId) {
                this.appId = appId;
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

            public String getFileSize() {
                return fileSize;
            }

            public void setFileSize(String fileSize) {
                this.fileSize = fileSize;
            }

            public String getDownloadStatus() {
                return downloadStatus;
            }

            public void setDownloadStatus(String downloadStatus) {
                this.downloadStatus = downloadStatus;
            }

            public String getVersionStatus() {
                return versionStatus;
            }

            public void setVersionStatus(String versionStatus) {
                this.versionStatus = versionStatus;
            }

            public String getDownloadUrl() {
                return downloadUrl;
            }

            public void setDownloadUrl(String downloadUrl) {
                this.downloadUrl = downloadUrl;
            }

            public Integer getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(Integer createdBy) {
                this.createdBy = createdBy;
            }

            public String getAppRemark() {
                return appRemark;
            }

            public void setAppRemark(String appRemark) {
                this.appRemark = appRemark;
            }

            public Long getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(Long createdAt) {
                this.createdAt = createdAt;
            }

            public String getIsLatest() {
                return isLatest;
            }

            public void setIsLatest(String isLatest) {
                this.isLatest = isLatest;
            }

            public Long getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(Long updatedAt) {
                this.updatedAt = updatedAt;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Data.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
                sb.append("id");
                sb.append('=');
                sb.append(((this.id == null)?"<null>":this.id));
                sb.append(',');
                sb.append("appTypeId");
                sb.append('=');
                sb.append(((this.appTypeId == null)?"<null>":this.appTypeId));
                sb.append(',');
                sb.append("appId");
                sb.append('=');
                sb.append(((this.appId == null)?"<null>":this.appId));
                sb.append(',');
                sb.append("version");
                sb.append('=');
                sb.append(((this.version == null)?"<null>":this.version));
                sb.append(',');
                sb.append("isMandatory");
                sb.append('=');
                sb.append(((this.isMandatory == null)?"<null>":this.isMandatory));
                sb.append(',');
                sb.append("fileSize");
                sb.append('=');
                sb.append(((this.fileSize == null)?"<null>":this.fileSize));
                sb.append(',');
                sb.append("downloadStatus");
                sb.append('=');
                sb.append(((this.downloadStatus == null)?"<null>":this.downloadStatus));
                sb.append(',');
                sb.append("versionStatus");
                sb.append('=');
                sb.append(((this.versionStatus == null)?"<null>":this.versionStatus));
                sb.append(',');
                sb.append("downloadUrl");
                sb.append('=');
                sb.append(((this.downloadUrl == null)?"<null>":this.downloadUrl));
                sb.append(',');
                sb.append("createdBy");
                sb.append('=');
                sb.append(((this.createdBy == null)?"<null>":this.createdBy));
                sb.append(',');
                sb.append("appRemark");
                sb.append('=');
                sb.append(((this.appRemark == null)?"<null>":this.appRemark));
                sb.append(',');
                sb.append("createdAt");
                sb.append('=');
                sb.append(((this.createdAt == null)?"<null>":this.createdAt));
                sb.append(',');
                sb.append("isLatest");
                sb.append('=');
                sb.append(((this.isLatest == null)?"<null>":this.isLatest));
                sb.append(',');
                sb.append("updatedAt");
                sb.append('=');
                sb.append(((this.updatedAt == null)?"<null>":this.updatedAt));
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GetPreAppVersionBean.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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

}



