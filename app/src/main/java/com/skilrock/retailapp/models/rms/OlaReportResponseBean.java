package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OlaReportResponseBean {

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

            @SerializedName("orgTypeCode")
            @Expose
            private String orgTypeCode;

            @SerializedName("total")
            @Expose
            private Total total;

            @SerializedName("address")
            @Expose
            private String address;

            @SerializedName("orgName")
            @Expose
            private String orgName;

            @SerializedName("orgId")
            @Expose
            private String orgId;

            @SerializedName("transaction")
            @Expose
            private ArrayList<Transaction> transaction = null;

            public String getOrgTypeCode() {
                return orgTypeCode;
            }

            public void setOrgTypeCode(String orgTypeCode) {
                this.orgTypeCode = orgTypeCode;
            }

            public Total getTotal() {
                return total;
            }

            public void setTotal(Total total) {
                this.total = total;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public ArrayList<Transaction> getTransaction() {
                return transaction;
            }

            public void setTransaction(ArrayList<Transaction> transaction) {
                this.transaction = transaction;
            }

            public static class Total {

                @SerializedName("netCollection")
                @Expose
                private String netCollection;
                @SerializedName("wITHDRAWAL")
                @Expose
                private Double wITHDRAWAL;
                @SerializedName("depositReturn")
                @Expose
                private String depositReturn;
                @SerializedName("dEPOSIT")
                @Expose
                private Double dEPOSIT;
                @SerializedName("finalDeposit")
                @Expose
                private String finalDeposit;
                @SerializedName("rawNetCollection")
                @Expose
                private Double rawNetCollection;
                @SerializedName("deposit")
                @Expose
                private String deposit;
                @SerializedName("withdrawal")
                @Expose
                private String withdrawal;

                public String getNetCollection() {
                    return netCollection;
                }

                public void setNetCollection(String netCollection) {
                    this.netCollection = netCollection;
                }

                public Double getWITHDRAWAL() {
                    return wITHDRAWAL;
                }

                public void setWITHDRAWAL(Double wITHDRAWAL) {
                    this.wITHDRAWAL = wITHDRAWAL;
                }

                public String getDepositReturn() {
                    return depositReturn;
                }

                public void setDepositReturn(String depositReturn) {
                    this.depositReturn = depositReturn;
                }

                public Double getDEPOSIT() {
                    return dEPOSIT;
                }

                public void setDEPOSIT(Double dEPOSIT) {
                    this.dEPOSIT = dEPOSIT;
                }

                public String getFinalDeposit() {
                    return finalDeposit;
                }

                public void setFinalDeposit(String finalDeposit) {
                    this.finalDeposit = finalDeposit;
                }

                public Double getRawNetCollection() {
                    return rawNetCollection;
                }

                public void setRawNetCollection(Double rawNetCollection) {
                    this.rawNetCollection = rawNetCollection;
                }

                public String getDeposit() {
                    return deposit;
                }

                public void setDeposit(String deposit) {
                    this.deposit = deposit;
                }

                public String getWithdrawal() {
                    return withdrawal;
                }

                public void setWithdrawal(String withdrawal) {
                    this.withdrawal = withdrawal;
                }

            }

            public static class Transaction {

                @SerializedName("txnTyeCode")
                @Expose
                private String txnTyeCode;

                @SerializedName("depositReturn")
                @Expose
                private String depositReturn;

                @SerializedName("txnValue")
                @Expose
                private String txnValue;

                @SerializedName("Player_Mobile")
                @Expose
                private String playerMobile;

                @SerializedName("balancePostTxn")
                @Expose
                private String balancePostTxn;

                @SerializedName("deposit")
                @Expose
                private String deposit;

                @SerializedName("withdrawal")
                @Expose
                private String withdrawal;

                @SerializedName("createdAt")
                @Expose
                private String createdAt;

                @SerializedName("txnDate")
                @Expose
                private String txnDate;

                @SerializedName("txnTime")
                @Expose
                private String txnTime;

                @SerializedName("txnId")
                @Expose
                private Integer txnId;

                @SerializedName("userId")
                @Expose
                private String userId;

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getTxnTyeCode() {
                    return txnTyeCode;
                }

                public void setTxnTyeCode(String txnTyeCode) {
                    this.txnTyeCode = txnTyeCode;
                }

                public String getDepositReturn() {
                    return depositReturn;
                }

                public void setDepositReturn(String depositReturn) {
                    this.depositReturn = depositReturn;
                }

                public String getTxnValue() {
                    return txnValue;
                }

                public void setTxnValue(String txnValue) {
                    this.txnValue = txnValue;
                }

                public String getPlayerMobile() {
                    return playerMobile;
                }

                public void setPlayerMobile(String playerMobile) {
                    this.playerMobile = playerMobile;
                }

                public String getBalancePostTxn() {
                    return balancePostTxn;
                }

                public void setBalancePostTxn(String balancePostTxn) {
                    this.balancePostTxn = balancePostTxn;
                }

                public String getDeposit() {
                    return deposit;
                }

                public void setDeposit(String deposit) {
                    this.deposit = deposit;
                }

                public String getWithdrawal() {
                    return withdrawal;
                }

                public void setWithdrawal(String withdrawal) {
                    this.withdrawal = withdrawal;
                }

                public String getTxnDate() {
                    return txnDate;
                }

                public void setTxnDate(String txnDate) {
                    this.txnDate = txnDate;
                }

                public Integer getTxnId() {
                    return txnId;
                }

                public void setTxnId(Integer txnId) {
                    this.txnId = txnId;
                }

                @NonNull
                @Override
                public String toString() {
                    return "Transaction{" + "txnTyeCode='" + txnTyeCode + '\'' + ", depositReturn='" + depositReturn + '\'' + ", txnValue='" + txnValue + '\'' + ", playerMobile='" + playerMobile + '\'' + ", balancePostTxn='" + balancePostTxn + '\'' + ", deposit='" + deposit + '\'' + ", withdrawal='" + withdrawal + '\'' + ", txnDate='" + txnDate + '\'' + ", txnId=" + txnId + ", userId='" + userId + '\'' + '}';
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getTxnTime() {
                    return txnTime;
                }

                public void setTxnTime(String txnTime) {
                    this.txnTime = txnTime;
                }
            }

            @NonNull
            @Override
            public String toString() {
                return "Data{" + "orgTypeCode='" + orgTypeCode + '\'' + ", total=" + total + ", address='" + address + '\'' + ", orgName='" + orgName + '\'' + ", orgId='" + orgId + '\'' + ", transaction=" + transaction + '}';
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
        return "OlaReportResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
