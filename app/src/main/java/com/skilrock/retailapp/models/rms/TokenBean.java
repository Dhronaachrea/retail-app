package com.skilrock.retailapp.models.rms;

public class TokenBean {
    private int responseCode;
    private String responseMessage;
    private ResponseData responseData;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public static class ResponseData {
        private int statusCode;
        private int userId;
        private String message;
        private String authToken;
        private String timestamp;
        private String expiryTime;
        private String issueAt;

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getExpiryTime() {
            return expiryTime;
        }

        public void setExpiryTime(String expiryTime) {
            this.expiryTime = expiryTime;
        }

        public String getIssueAt() {
            return issueAt;
        }

        public void setIssueAt(String issueAt) {
            this.issueAt = issueAt;
        }

        public String getAuthToken() {
            return authToken;
        }
    }
}
