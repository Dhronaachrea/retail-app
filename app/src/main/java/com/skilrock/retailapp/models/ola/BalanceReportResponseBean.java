package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceReportResponseBean {

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

            @SerializedName("payments")
            @Expose
            private String payments;

            @SerializedName("claims")
            @Expose
            private String claims;

            @SerializedName("closingBalance")
            @Expose
            private String closingBalance;

            @SerializedName("claimTax")
            @Expose
            private String claimTax;

            @SerializedName("commission")
            @Expose
            private String commission;

            @SerializedName("openingBalance")
            @Expose
            private String openingBalance;

            @SerializedName("sales")
            @Expose
            private String sales;

            @SerializedName("creditDebitTxn")
            @Expose
            private String creditDebitTxn;

            @SerializedName("winningsCommission")
            @Expose
            private String winningsCommission;
            @SerializedName("salesCommission")
            @Expose
            private String salesCommission;

            public String getWinningsCommission() {
                return winningsCommission;
            }

            public void setWinningsCommission(String winningsCommission) {
                this.winningsCommission = winningsCommission;
            }

            public String getSalesCommission() {
                return salesCommission;
            }

            public void setSalesCommission(String salesCommission) {
                this.salesCommission = salesCommission;
            }

            public String getPayments() {
                return payments;
            }

            public void setPayments(String payments) {
                this.payments = payments;
            }

            public String getClaims() {
                return claims;
            }

            public void setClaims(String claims) {
                this.claims = claims;
            }

            public String getClosingBalance() {
                return closingBalance;
            }

            public void setClosingBalance(String closingBalance) {
                this.closingBalance = closingBalance;
            }

            public String getClaimTax() {
                return claimTax;
            }

            public void setClaimTax(String claimTax) {
                this.claimTax = claimTax;
            }

            public String getCommission() {
                return commission;
            }

            public void setCommission(String commission) {
                this.commission = commission;
            }

            public String getOpeningBalance() {
                return openingBalance;
            }

            public void setOpeningBalance(String openingBalance) {
                this.openingBalance = openingBalance;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public void setCreditDebitTxn(String creditDebitTxn) {
                this.creditDebitTxn = creditDebitTxn;
            }

            public String getCreditDebitTxn() {
                return creditDebitTxn;
            }

            @NonNull
            @Override
            public String toString() {
                return "Data{" + "payments='" + payments + '\'' + ", claims=" + claims + ", closingBalance=" + closingBalance + ", claimTax=" + claimTax + ", commission=" + commission + ", openingBalance=" + openingBalance + ", sales=" + sales + ", creditDebitTxn=" + creditDebitTxn + '}';
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
