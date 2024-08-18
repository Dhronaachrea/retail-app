package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlaRegistrationMyanmarResponseBean {

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

    @NonNull
    @Override
    public String toString() {
        return "OlaRegistrationMyanmarResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }

    public static class ResponseData {

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("regDate")
        @Expose
        private String regDate;

        @SerializedName("retUserName")
        @Expose
        private String retUserName;

        @SerializedName("orgBalance")
        @Expose
        private Double orgBalance;

        @SerializedName("orgName")
        @Expose
        private String orgName;

        @SerializedName("player")
        @Expose
        private Player player;

        @SerializedName("depositResponseVO")
        @Expose
        private DepositResponseVO depositResponseVO;

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

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
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

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public DepositResponseVO getDepositResponseVO() {
            return depositResponseVO;
        }

        public void setDepositResponseVO(DepositResponseVO depositResponseVO) {
            this.depositResponseVO = depositResponseVO;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", regDate='" + regDate + '\'' + ", retUserName='" + retUserName + '\'' + ", orgBalance=" + orgBalance + ", orgName='" + orgName + '\'' + ", player=" + player + ", depositResponseVO=" + depositResponseVO + '}';
        }

        public static class DepositResponseVO {

            @SerializedName("txnId")
            @Expose
            private Integer txnId;

            @SerializedName("txnDate")
            @Expose
            private String txnDate;

            @SerializedName("plrTxnId")
            @Expose
            private Integer plrTxnId;

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

            @SerializedName("plrDepositAmount")
            @Expose
            private Double plrDepositAmount;

            public Double getPlrDepositAmount() {
                return plrDepositAmount;
            }

            public void setPlrDepositAmount(Double plrDepositAmount) {
                this.plrDepositAmount = plrDepositAmount;
            }

            public Integer getTxnId() {
                return txnId;
            }

            public void setTxnId(Integer txnId) {
                this.txnId = txnId;
            }

            public String getTxnDate() {
                return txnDate;
            }

            public void setTxnDate(String txnDate) {
                this.txnDate = txnDate;
            }

            public Integer getPlrTxnId() {
                return plrTxnId;
            }

            public void setPlrTxnId(Integer plrTxnId) {
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

        public static class Player {

            @SerializedName("playerId")
            @Expose
            private Integer playerId;

            @SerializedName("merchantPlayerId")
            @Expose
            private Integer merchantPlayerId;

            @SerializedName("username")
            @Expose
            private String username;

            @SerializedName("domainId")
            @Expose
            private Integer domainId;

            @SerializedName("firstName")
            @Expose
            private Object firstName;

            @SerializedName("lastName")
            @Expose
            private Object lastName;

            @SerializedName("dateOfBirth")
            @Expose
            private String dateOfBirth;

            @SerializedName("password")
            @Expose
            private String password;

            @SerializedName("email")
            @Expose
            private Object email;

            @SerializedName("phone")
            @Expose
            private Object phone;

            @SerializedName("address")
            @Expose
            private Object address;

            @SerializedName("city")
            @Expose
            private Object city;

            @SerializedName("state")
            @Expose
            private Object state;

            @SerializedName("country")
            @Expose
            private Object country;

            @SerializedName("registrationDate")
            @Expose
            private String registrationDate;

            @SerializedName("registrationType")
            @Expose
            private String registrationType;

            @SerializedName("userId")
            @Expose
            private Integer userId;

            @SerializedName("merchantOrgId")
            @Expose
            private Integer merchantOrgId;

            @SerializedName("registrationRequest")
            @Expose
            private Object registrationRequest;

            @SerializedName("egender")
            @Expose
            private Object egender;

            @SerializedName("estatus")
            @Expose
            private String estatus;

            public Integer getPlayerId() {
                return playerId;
            }

            public void setPlayerId(Integer playerId) {
                this.playerId = playerId;
            }

            public Integer getMerchantPlayerId() {
                return merchantPlayerId;
            }

            public void setMerchantPlayerId(Integer merchantPlayerId) {
                this.merchantPlayerId = merchantPlayerId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public Integer getDomainId() {
                return domainId;
            }

            public void setDomainId(Integer domainId) {
                this.domainId = domainId;
            }

            public Object getFirstName() {
                return firstName;
            }

            public void setFirstName(Object firstName) {
                this.firstName = firstName;
            }

            public Object getLastName() {
                return lastName;
            }

            public void setLastName(Object lastName) {
                this.lastName = lastName;
            }

            public String getDateOfBirth() {
                return dateOfBirth;
            }

            public void setDateOfBirth(String dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getState() {
                return state;
            }

            public void setState(Object state) {
                this.state = state;
            }

            public Object getCountry() {
                return country;
            }

            public void setCountry(Object country) {
                this.country = country;
            }

            public String getRegistrationDate() {
                return registrationDate;
            }

            public void setRegistrationDate(String registrationDate) {
                this.registrationDate = registrationDate;
            }

            public String getRegistrationType() {
                return registrationType;
            }

            public void setRegistrationType(String registrationType) {
                this.registrationType = registrationType;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public Integer getMerchantOrgId() {
                return merchantOrgId;
            }

            public void setMerchantOrgId(Integer merchantOrgId) {
                this.merchantOrgId = merchantOrgId;
            }

            public Object getRegistrationRequest() {
                return registrationRequest;
            }

            public void setRegistrationRequest(Object registrationRequest) {
                this.registrationRequest = registrationRequest;
            }

            public Object getEgender() {
                return egender;
            }

            public void setEgender(Object egender) {
                this.egender = egender;
            }

            public String getEstatus() {
                return estatus;
            }

            public void setEstatus(String estatus) {
                this.estatus = estatus;
            }

            @NonNull
            @Override
            public String toString() {
                return "Player{" + "playerId=" + playerId + ", merchantPlayerId=" + merchantPlayerId + ", username='" + username + '\'' + ", domainId=" + domainId + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", password='" + password + '\'' + ", email=" + email + ", phone=" + phone + ", address=" + address + ", city=" + city + ", state=" + state + ", country=" + country + ", registrationDate=" + registrationDate + ", registrationType='" + registrationType + '\'' + ", userId=" + userId + ", merchantOrgId=" + merchantOrgId + ", registrationRequest=" + registrationRequest + ", egender=" + egender + ", estatus='" + estatus + '\'' + '}';
            }
        }
    }
}
