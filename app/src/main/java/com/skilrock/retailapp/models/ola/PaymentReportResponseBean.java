package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentReportResponseBean {

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
        private ArrayList<Data> data = null;

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

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public static class Data {

            @SerializedName("orgName")
            @Expose
            private String orgName;

            @SerializedName("orgId")
            @Expose
            private Integer orgId;

            @SerializedName("transaction")
            @Expose
            private ArrayList<Transaction> transaction = null;

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public Integer getOrgId() {
                return orgId;
            }

            public void setOrgId(Integer orgId) {
                this.orgId = orgId;
            }

            public ArrayList<Transaction> getTransaction() {
                return transaction;
            }

            public void setTransaction(ArrayList<Transaction> transaction) {
                this.transaction = transaction;
            }

            public static class Transaction {

                @SerializedName("createdAt")
                @Expose
                private String createdAt;

                @SerializedName("txnValue")
                @Expose
                private String txnValue;

                @SerializedName("narration")
                @Expose
                private String narration;

                @SerializedName("balancePostTxn")
                @Expose
                private String balancePostTxn;

                @SerializedName("txnType")
                @Expose
                private String txnType;

                @SerializedName("txnBy")
                @Expose
                private String txnBy;

                @SerializedName("txnId")
                @Expose
                private String txnId;

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getTxnValue() {
                    return txnValue;
                }

                public void setTxnValue(String txnValue) {
                    this.txnValue = txnValue;
                }

                public String getNarration() {
                    return narration;
                }

                public void setNarration(String narration) {
                    this.narration = narration;
                }

                public String getBalancePostTxn() {
                    return balancePostTxn;
                }

                public void setBalancePostTxn(String balancePostTxn) {
                    this.balancePostTxn = balancePostTxn;
                }

                public String getTxnType() {
                    return txnType;
                }

                public void setTxnType(String txnType) {
                    this.txnType = txnType;
                }

                public String getTxnBy() {
                    return txnBy;
                }

                public void setTxnBy(String txnBy) {
                    this.txnBy = txnBy;
                }

                public String getTxnId() {
                    return txnId;
                }

                public void setTxnId(String txnId) {
                    this.txnId = txnId;
                }

                @NonNull
                @Override
                public String toString() {
                    return "Transaction{" + "createdAt='" + createdAt + '\'' + ", txnValue='" + txnValue + '\'' + ", narration='" + narration + '\'' + ", balancePostTxn='" + balancePostTxn + '\'' + ", txnType='" + txnType + '\'' + ", txnBy='" + txnBy + '\'' + ", txnId='" + txnId + '\'' + '}';
                }
            }

            @NonNull
            @Override
            public String toString() {
                return "Data{" + "orgName='" + orgName + '\'' + ", orgId=" + orgId + ", transaction=" + transaction + '}';
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
        return "PaymentReportResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
