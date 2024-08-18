package com.skilrock.retailapp.models.FieldX;

import java.util.ArrayList;

public class FieldxCollectionReportResponseBean {
    private String responseMessage;
    private int responseCode;
    private ResponseData responseData;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
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
        private ArrayList<Data> data;

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

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data{
            private String orgName;
            private String serviceCode;
            private double orgCommValue;
            private double orgBalancePostTxn;
            private String txnTypeCode;
            private String txnType;
            private String serviceName;
            private int orgTdsValue;
            private int orgId;
            private String createdAt;
            private int orgNetAmount;
            private int orgChargesValue;
            private String txnDate;
            private int orgDueAmount;

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                orgName = orgName;
            }

            public String getServiceCode() {
                return serviceCode;
            }

            public void setServiceCode(String serviceCode) {
                this.serviceCode = serviceCode;
            }

            public double getOrgCommValue() {
                return orgCommValue;
            }

            public void setOrgCommValue(double orgCommValue) {
                this.orgCommValue = orgCommValue;
            }

            public double getOrgBalancePostTxn() {
                return orgBalancePostTxn;
            }

            public void setOrgBalancePostTxn(double orgBalancePostTxn) {
                this.orgBalancePostTxn = orgBalancePostTxn;
            }

            public String getTxnTypeCode() {
                return txnTypeCode;
            }

            public void setTxnTypeCode(String txnTypeCode) {
                this.txnTypeCode = txnTypeCode;
            }

            public String getTxnType() {
                return txnType;
            }

            public void setTxnType(String txnType) {
                this.txnType = txnType;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public int getOrgTdsValue() {
                return orgTdsValue;
            }

            public void setOrgTdsValue(int orgTdsValue) {
                this.orgTdsValue = orgTdsValue;
            }

            public int getOrgId() {
                return orgId;
            }

            public void setOrgId(int orgId) {
                this.orgId = orgId;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int getOrgNetAmount() {
                return orgNetAmount;
            }

            public void setOrgNetAmount(int orgNetAmount) {
                this.orgNetAmount = orgNetAmount;
            }

            public int getOrgChargesValue() {
                return orgChargesValue;
            }

            public void setOrgChargesValue(int orgChargesValue) {
                this.orgChargesValue = orgChargesValue;
            }

            public String getTxnDate() {
                return txnDate;
            }

            public void setTxnDate(String txnDate) {
                this.txnDate = txnDate;
            }

            public int getOrgDueAmount() {
                return orgDueAmount;
            }

            public void setOrgDueAmount(int orgDueAmount) {
                this.orgDueAmount = orgDueAmount;
            }
        }
    }
}
