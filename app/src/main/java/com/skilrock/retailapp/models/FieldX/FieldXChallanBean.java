package com.skilrock.retailapp.models.FieldX;

import org.json.JSONObject;

import java.util.ArrayList;

public class FieldXChallanBean {
    private int responseCode;
    private String responseMessage;
    private Response response;

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

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {
        private DlDetails dlDetails;

        public DlDetails getDlDetails() {
            return dlDetails;
        }

        public void setDlDetails(DlDetails dlDetails) {
            this.dlDetails = dlDetails;
        }

        public class DlDetails extends JSONObject {
            private ArrayList<SALE_PARTIALLY_RECEIVED> SALE_PARTIALLY_RECEIVED;
            private ArrayList<SALE_IN_TRANSIT> SALE_IN_TRANSIT;
            private ArrayList<SALE_RETURN> SALE_RETURN;

            public ArrayList<SALE_PARTIALLY_RECEIVED> getSALE_PARTIALLY_RECEIVED() {
                return SALE_PARTIALLY_RECEIVED;
            }

            public void setSALE_PARTIALLY_RECEIVED(ArrayList<SALE_PARTIALLY_RECEIVED> SALE_PARTIALLY_RECEIVED) {
                this.SALE_PARTIALLY_RECEIVED = SALE_PARTIALLY_RECEIVED;
            }

            public ArrayList<SALE_IN_TRANSIT> getSALE_IN_TRANSIT() {
                return SALE_IN_TRANSIT;
            }

            public void setSALE_IN_TRANSIT(ArrayList<SALE_IN_TRANSIT> SALE_IN_TRANSIT) {
                this.SALE_IN_TRANSIT = SALE_IN_TRANSIT;
            }

            public ArrayList<SALE_RETURN> getSALE_RETURN() {
                return SALE_RETURN;
            }

            public void setSALE_RETURN(ArrayList<SALE_RETURN> SALE_RETURN) {
                this.SALE_RETURN = SALE_RETURN;
            }

            public class SALE_PARTIALLY_RECEIVED {
                private String dlId;
                private String dlChallanNumber;
                private String dlDate;
                private String dlStatus;
                private String userName;
                private String challanDetailsPath;
                private String orgId;

                public String getDlId() {
                    return dlId;
                }

                public void setDlId(String dlId) {
                    this.dlId = dlId;
                }

                public String getDlChallanNumber() {
                    return dlChallanNumber;
                }

                public void setDlChallanNumber(String dlChallanNumber) {
                    this.dlChallanNumber = dlChallanNumber;
                }

                public String getDlDate() {
                    return dlDate;
                }

                public void setDlDate(String dlDate) {
                    this.dlDate = dlDate;
                }

                public String getDlStatus() {
                    return dlStatus;
                }

                public void setDlStatus(String dlStatus) {
                    this.dlStatus = dlStatus;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getChallanDetailsPath() {
                    return challanDetailsPath;
                }

                public void setChallanDetailsPath(String challanDetailsPath) {
                    this.challanDetailsPath = challanDetailsPath;
                }

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
                    this.orgId = orgId;
                }
            }

            public class SALE_IN_TRANSIT {
                private String dlId;
                private String dlChallanNumber;
                private String dlDate;
                private String dlStatus;
                private String userName;
                private String challanDetailsPath;
                private String orgId;

                public String getDlId() {
                    return dlId;
                }

                public void setDlId(String dlId) {
                    this.dlId = dlId;
                }

                public String getDlChallanNumber() {
                    return dlChallanNumber;
                }

                public void setDlChallanNumber(String dlChallanNumber) {
                    this.dlChallanNumber = dlChallanNumber;
                }

                public String getDlDate() {
                    return dlDate;
                }

                public void setDlDate(String dlDate) {
                    this.dlDate = dlDate;
                }

                public String getDlStatus() {
                    return dlStatus;
                }

                public void setDlStatus(String dlStatus) {
                    this.dlStatus = dlStatus;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getChallanDetailsPath() {
                    return challanDetailsPath;
                }

                public void setChallanDetailsPath(String challanDetailsPath) {
                    this.challanDetailsPath = challanDetailsPath;
                }

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
                    this.orgId = orgId;
                }
            }

            public class SALE_RETURN {
                private String dlId;
                private String dlChallanNumber;
                private String dlDate;
                private String dlStatus;
                private String userName;
                private String challanDetailsPath;
                private String orgId;

                public String getDlId() {
                    return dlId;
                }

                public void setDlId(String dlId) {
                    this.dlId = dlId;
                }

                public String getDlChallanNumber() {
                    return dlChallanNumber;
                }

                public void setDlChallanNumber(String dlChallanNumber) {
                    this.dlChallanNumber = dlChallanNumber;
                }

                public String getDlDate() {
                    return dlDate;
                }

                public void setDlDate(String dlDate) {
                    this.dlDate = dlDate;
                }

                public String getDlStatus() {
                    return dlStatus;
                }

                public void setDlStatus(String dlStatus) {
                    this.dlStatus = dlStatus;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getChallanDetailsPath() {
                    return challanDetailsPath;
                }

                public void setChallanDetailsPath(String challanDetailsPath) {
                    this.challanDetailsPath = challanDetailsPath;
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
}
