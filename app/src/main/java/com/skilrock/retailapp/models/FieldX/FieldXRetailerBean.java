package com.skilrock.retailapp.models.FieldX;

import com.skilrock.retailapp.models.Trash;

import java.util.ArrayList;

public class FieldXRetailerBean {
    private int responseCode;
    private String responseMessage;
    private Trash.ResponseData responseData;

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

    public Trash.ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(Trash.ResponseData responseData) {
        this.responseData = responseData;
    }

    public class ResponseData {
        private String message;
        private String statusCode;
        private Data data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public class Data {
            private ArrayList<AssignOrg> assignOrg;

            public ArrayList<AssignOrg> getAssignOrg() {
                return assignOrg;
            }

            public void setAssignOrg(ArrayList<AssignOrg> assignOrg) {
                this.assignOrg = assignOrg;
            }
        }

        public class AssignOrg {
            private String country;
            private String zipCode;
            private String orgName;
            private String balance;
            private String city;
            private String addressTwo;
            private String creditLimit;
            private String state;
            private String addressOne;
            private String orgId;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getZipCode() {
                return zipCode;
            }

            public void setZipCode(String zipCode) {
                this.zipCode = zipCode;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAddressTwo() {
                return addressTwo;
            }

            public void setAddressTwo(String addressTwo) {
                this.addressTwo = addressTwo;
            }

            public String getCreditLimit() {
                return creditLimit;
            }

            public void setCreditLimit(String creditLimit) {
                this.creditLimit = creditLimit;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getAddressOne() {
                return addressOne;
            }

            public void setAddressOne(String addressOne) {
                this.addressOne = addressOne;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }
        }
    }
}
