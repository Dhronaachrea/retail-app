package com.skilrock.retailapp.models.FieldX;

public class FieldxGetPayAmountBean {
    private int responseCode;
    private String responseMessage;
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

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public class ResponseData{
        private String message;
        private int statusCode;
        private Data data;

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
        public class Data{
            private String minimumDueAmount;
            private String orgId;
            private String rawMinimumDueAmount;
            private String orgName;

            public String getMinimumDueAmount() {
                return minimumDueAmount;
            }

            public void setMinimumDueAmount(String minimumDueAmount) {
                this.minimumDueAmount = minimumDueAmount;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public String getRawMinimumDueAmount() {
                return rawMinimumDueAmount;
            }

            public void setRawMinimumDueAmount(String rawMinimumDueAmount) {
                this.rawMinimumDueAmount = rawMinimumDueAmount;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }
        }
    }
}
