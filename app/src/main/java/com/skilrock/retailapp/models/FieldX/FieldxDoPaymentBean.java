package com.skilrock.retailapp.models.FieldX;

public class FieldxDoPaymentBean {
    private int responseCode;
    private String responseMessage;
    private Response responseData;

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

    public Response getResponseData() {
        return responseData;
    }

    public void setResponseData(Response responseData) {
        this.responseData = responseData;
    }

    public class Response{
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
            private String amount;
            private String balancePostTxn;
            private String createdAt;
            private String orgAddress;
            private String orgName;
            private String raisedBy;
            private String remark;
            private String transactionId;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getBalancePostTxn() {
                return balancePostTxn;
            }

            public void setBalancePostTxn(String balancePostTxn) {
                this.balancePostTxn = balancePostTxn;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getOrgAddress() {
                return orgAddress;
            }

            public void setOrgAddress(String orgAddress) {
                this.orgAddress = orgAddress;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getRaisedBy() {
                return raisedBy;
            }

            public void setRaisedBy(String raisedBy) {
                this.raisedBy = raisedBy;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getTransactionId() {
                return transactionId;
            }

            public void setTransactionId(String transactionId) {
                this.transactionId = transactionId;
            }
        }
    }
}
