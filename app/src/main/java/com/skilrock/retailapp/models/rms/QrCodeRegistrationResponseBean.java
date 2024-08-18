package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class QrCodeRegistrationResponseBean {

    @SerializedName("responseCode")
    @Expose
    private int responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

    public static class ResponseData {

        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("statusCode")
        @Expose
        private int statusCode;

        @SerializedName("data")
        @Expose
        private Data data;

        public static class Data {

            @SerializedName("id")
            @Expose
            private int id;

            @SerializedName("orgId")
            @Expose
            private int orgId;

            @SerializedName("qrCode")
            @Expose
            private String qrCode;

            @SerializedName("createdAt")
            @Expose
            private long createdAt;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOrgId() {
                return orgId;
            }

            public void setOrgId(int orgId) {
                this.orgId = orgId;
            }

            public String getQrCode() {
                return qrCode;
            }

            public void setQrCode(String qrCode) {
                this.qrCode = qrCode;
            }

            public long getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(long createdAt) {
                this.createdAt = createdAt;
            }

            @Override
            public @NotNull String toString() {
                return "Data{" +
                        "id=" + id +
                        ", orgId=" + orgId +
                        ", qrCode='" + qrCode + '\'' +
                        ", createdAt=" + createdAt +
                        '}';
            }
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        @Override
        public @NotNull String toString() {
            return "ResponseData{" +
                    "message='" + message + '\'' +
                    ", statusCode=" + statusCode +
                    ", data=" + data +
                    '}';
        }
    }

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

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    @Override
    public @NotNull String toString() {
        return "QrCodeRegistration{" +
                "responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                ", responseData=" + responseData +
                '}';
    }
}
