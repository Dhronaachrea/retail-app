package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaRegistrationResponseBean {

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

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("phone")
        @Expose
        private String phone;

        @SerializedName("regDate")
        @Expose
        private String regDate;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("retUserName")
        @Expose
        private String retUserName;

        @SerializedName("orgBalance")
        @Expose
        private Double orgBalance;

        @SerializedName("orgName")
        @Expose
        private String orgName;

        @SerializedName("depositResponseVO")
        @Expose
        private DepositResponseVO depositResponseVO;

        public DepositResponseVO getDepositResponseVO() {
            return depositResponseVO;
        }

        public void setDepositResponseVO(DepositResponseVO depositResponseVO) {
            this.depositResponseVO = depositResponseVO;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRetUserName() {
            return retUserName;
        }

        public void setRetUserName(String retUserName) {
            this.retUserName = retUserName;
        }

        public Double getOrgBalance() {
            return orgBalance;
        }

        public void setOrgBalance(Double orgBalance) {
            this.orgBalance = orgBalance;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public static class DepositResponseVO {

            @SerializedName("txnId")
            @Expose
            private Long txnId;

            @SerializedName("txnDate")
            @Expose
            private String txnDate;

            @SerializedName("plrTxnId")
            @Expose
            private Long plrTxnId;

            @SerializedName("balancePreTxn")
            @Expose
            private Double balancePreTxn;

            @SerializedName("balancePostTxn")
            @Expose
            private Double balancePostTxn;

            @SerializedName("respMsg")
            @Expose
            private String respMsg;

            @SerializedName("respCode")
            @Expose
            private Integer respCode;

            public Long getTxnId() {
                return txnId;
            }

            public void setTxnId(Long txnId) {
                this.txnId = txnId;
            }

            public String getTxnDate() {
                return txnDate;
            }

            public void setTxnDate(String txnDate) {
                this.txnDate = txnDate;
            }

            public Long getPlrTxnId() {
                return plrTxnId;
            }

            public void setPlrTxnId(Long plrTxnId) {
                this.plrTxnId = plrTxnId;
            }

            public Double getBalancePreTxn() {
                return balancePreTxn;
            }

            public void setBalancePreTxn(Double balancePreTxn) {
                this.balancePreTxn = balancePreTxn;
            }

            public Double getBalancePostTxn() {
                return balancePostTxn;
            }

            public void setBalancePostTxn(Double balancePostTxn) {
                this.balancePostTxn = balancePostTxn;
            }

            public String getRespMsg() {
                return respMsg;
            }

            public void setRespMsg(String respMsg) {
                this.respMsg = respMsg;
            }

            public Integer getRespCode() {
                return respCode;
            }

            public void setRespCode(Integer respCode) {
                this.respCode = respCode;
            }

            @NonNull
            @Override
            public String toString() {
                return "DepositResponseVO{" + "txnId=" + txnId + ", txnDate='" + txnDate + '\'' + ", plrTxnId=" + plrTxnId + ", balancePreTxn=" + balancePreTxn + ", balancePostTxn=" + balancePostTxn + ", respMsg='" + respMsg + '\'' + ", respCode=" + respCode + '}';
            }
        }


        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", phone='" + phone + '\'' + ", regDate='" + regDate + '\'' + ", email='" + email + '\'' + ", retUserName='" + retUserName + '\'' + ", orgBalance=" + orgBalance + ", orgName='" + orgName + '\'' + ", depositResponseVO=" + depositResponseVO + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "OlaRegistrationResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
