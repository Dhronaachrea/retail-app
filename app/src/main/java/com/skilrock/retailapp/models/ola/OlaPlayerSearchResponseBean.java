package com.skilrock.retailapp.models.ola;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OlaPlayerSearchResponseBean {

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

        @SerializedName("totalResults")
        @Expose
        private Integer totalResults = -1;

        @SerializedName("players")
        @Expose
        private ArrayList<Player> players = null;

        public Integer getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }

        public ArrayList<Player> getPlayers() {
            return players;
        }

        public void setPlayers(ArrayList<Player> players) {
            this.players = players;
        }

        public class Player {

            @SerializedName("playerID")
            @Expose
            private Integer playerID;

            @SerializedName("merchantPlayerID")
            @Expose
            private Integer merchantPlayerID;

            @SerializedName("username")
            @Expose
            private String username;

            @SerializedName("domainID")
            @Expose
            private Integer domainID;

            @SerializedName("firstName")
            @Expose
            private String firstName;

            @SerializedName("lastName")
            @Expose
            private String lastName;

            @SerializedName("dateOfBirth")
            @Expose
            private String dateOfBirth;

            @SerializedName("email")
            @Expose
            private String email;

            @SerializedName("phone")
            @Expose
            private String phone;

            @SerializedName("address")
            @Expose
            private String address;

            @SerializedName("city")
            @Expose
            private String city;

            @SerializedName("state")
            @Expose
            private String state;

            @SerializedName("country")
            @Expose
            private String country;

            @SerializedName("registrationDate")
            @Expose
            private String registrationDate;

            @SerializedName("registrationType")
            @Expose
            private String registrationType;

            @SerializedName("userId")
            @Expose
            private Integer userId;

            @SerializedName("merchantOrgID")
            @Expose
            private Integer merchantOrgID;

            @SerializedName("egender")
            @Expose
            private String egender;

            @SerializedName("estatus")
            @Expose
            private String estatus;

            public Integer getPlayerID() {
                return playerID;
            }

            public void setPlayerID(Integer playerID) {
                this.playerID = playerID;
            }

            public Integer getMerchantPlayerID() {
                return merchantPlayerID;
            }

            public void setMerchantPlayerID(Integer merchantPlayerID) {
                this.merchantPlayerID = merchantPlayerID;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public Integer getDomainID() {
                return domainID;
            }

            public void setDomainID(Integer domainID) {
                this.domainID = domainID;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getDateOfBirth() {
                return dateOfBirth;
            }

            public void setDateOfBirth(String dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
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

            public Integer getMerchantOrgID() {
                return merchantOrgID;
            }

            public void setMerchantOrgID(Integer merchantOrgID) {
                this.merchantOrgID = merchantOrgID;
            }

            public String getEgender() {
                return egender;
            }

            public void setEgender(String egender) {
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
                return "Player{" + "playerID=" + playerID + ", merchantPlayerID=" + merchantPlayerID + ", username='" + username + '\'' + ", domainID=" + domainID + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", dateOfBirth=" + dateOfBirth + ", email='" + email + '\'' + ", phone='" + phone + '\'' + ", address='" + address + '\'' + ", city='" + city + '\'' + ", state='" + state + '\'' + ", country='" + country + '\'' + ", registrationDate=" + registrationDate + ", registrationType='" + registrationType + '\'' + ", userId=" + userId + ", merchantOrgID=" + merchantOrgID + ", egender='" + egender + '\'' + ", estatus='" + estatus + '\'' + '}';
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" + "totalResults=" + totalResults + ", players=" + players + '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "OlaPlayerSearchResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
