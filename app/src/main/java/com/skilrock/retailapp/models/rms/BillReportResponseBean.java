package com.skilrock.retailapp.models.rms;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BillReportResponseBean {

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

            @SerializedName("orgBillDetails")
            @Expose
            private ArrayList<OrgBillDetail> orgBillDetails = null;

            @SerializedName("overAllTotal")
            @Expose
            private OverAllTotal overAllTotal;

            public ArrayList<OrgBillDetail> getOrgBillDetails() {
                return orgBillDetails;
            }

            public void setOrgBillDetails(ArrayList<OrgBillDetail> orgBillDetails) {
                this.orgBillDetails = orgBillDetails;
            }

            public OverAllTotal getOverAllTotal() {
                return overAllTotal;
            }

            public void setOverAllTotal(OverAllTotal overAllTotal) {
                this.overAllTotal = overAllTotal;
            }

            public static class OrgBillDetail {

                @SerializedName("total")
                @Expose
                private Total total;

                @SerializedName("orgName")
                @Expose
                private String orgName;

                @SerializedName("orgId")
                @Expose
                private Integer orgId;

                @SerializedName("billData")
                @Expose
                private ArrayList<BillData> billData = null;

                public Total getTotal() {
                    return total;
                }

                public void setTotal(Total total) {
                    this.total = total;
                }

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

                public ArrayList<BillData> getBillData() {
                    return billData;
                }

                public void setBillData(ArrayList<BillData> billData) {
                    this.billData = billData;
                }

                public static class Total {

                    @SerializedName("totalBillAmount")
                    @Expose
                    private String totalBillAmount;

                    @SerializedName("totalRecieveAmount")
                    @Expose
                    private String totalRecieveAmount;

                    @SerializedName("totalDueAmount")
                    @Expose
                    private String totalDueAmount;

                    public String getTotalBillAmount() {
                        return totalBillAmount;
                    }

                    public void setTotalBillAmount(String totalBillAmount) {
                        this.totalBillAmount = totalBillAmount;
                    }

                    public String getTotalRecieveAmount() {
                        return totalRecieveAmount;
                    }

                    public void setTotalRecieveAmount(String totalRecieveAmount) {
                        this.totalRecieveAmount = totalRecieveAmount;
                    }

                    public String getTotalDueAmount() {
                        return totalDueAmount;
                    }

                    public void setTotalDueAmount(String totalDueAmount) {
                        this.totalDueAmount = totalDueAmount;
                    }

                    @NonNull
                    @Override
                    public String toString() {
                        return "Total{" + "totalBillAmount='" + totalBillAmount + '\'' + ", totalRecieveAmount='" + totalRecieveAmount + '\'' + ", totalDueAmount='" + totalDueAmount + '\'' + '}';
                    }
                }

                public static class BillData {

                    @SerializedName("dueDate")
                    @Expose
                    private String dueDate;

                    @SerializedName("billDate")
                    @Expose
                    private String billDate;

                    @SerializedName("receivedAmount")
                    @Expose
                    private String receivedAmount;

                    @SerializedName("disputedAmount")
                    @Expose
                    private String disputedAmount;

                    @SerializedName("billCreationDate")
                    @Expose
                    private String billCreationDate;

                    @SerializedName("isDisputed")
                    @Expose
                    private String isDisputed;

                    @SerializedName("billAmount")
                    @Expose
                    private String billAmount;

                    @SerializedName("dueAmount")
                    @Expose
                    private String dueAmount;

                    @SerializedName("disputeStatus")
                    @Expose
                    private String disputeStatus;

                    @SerializedName("billId")
                    @Expose
                    private String billId;

                    @SerializedName("billNumber")
                    @Expose
                    private String billNumber;

                    @SerializedName("billDuration")
                    @Expose
                    private String billDuration;

                    @SerializedName("showPayButton")
                    @Expose
                    private String showPayButton;

                    @SerializedName("status")
                    @Expose
                    private String status;

                    public String getDueDate() {
                        return dueDate;
                    }

                    public void setDueDate(String dueDate) {
                        this.dueDate = dueDate;
                    }

                    public String getBillDate() {
                        return billDate;
                    }

                    public void setBillDate(String billDate) {
                        this.billDate = billDate;
                    }

                    public String getReceivedAmount() {
                        return receivedAmount;
                    }

                    public void setReceivedAmount(String receivedAmount) {
                        this.receivedAmount = receivedAmount;
                    }

                    public String getDisputedAmount() {
                        return disputedAmount;
                    }

                    public void setDisputedAmount(String disputedAmount) {
                        this.disputedAmount = disputedAmount;
                    }

                    public String getBillCreationDate() {
                        return billCreationDate;
                    }

                    public void setBillCreationDate(String billCreationDate) {
                        this.billCreationDate = billCreationDate;
                    }

                    public String getIsDisputed() {
                        return isDisputed;
                    }

                    public void setIsDisputed(String isDisputed) {
                        this.isDisputed = isDisputed;
                    }

                    public String getBillAmount() {
                        return billAmount;
                    }

                    public void setBillAmount(String billAmount) {
                        this.billAmount = billAmount;
                    }

                    public String getDueAmount() {
                        return dueAmount;
                    }

                    public void setDueAmount(String dueAmount) {
                        this.dueAmount = dueAmount;
                    }

                    public String getDisputeStatus() {
                        return disputeStatus;
                    }

                    public void setDisputeStatus(String disputeStatus) {
                        this.disputeStatus = disputeStatus;
                    }

                    public String getBillId() {
                        return billId;
                    }

                    public void setBillId(String billId) {
                        this.billId = billId;
                    }

                    public String getBillNumber() {
                        return billNumber;
                    }

                    public void setBillNumber(String billNumber) {
                        this.billNumber = billNumber;
                    }

                    public String getBillDuration() {
                        return billDuration;
                    }

                    public void setBillDuration(String billDuration) {
                        this.billDuration = billDuration;
                    }

                    public String getShowPayButton() {
                        return showPayButton;
                    }

                    public void setShowPayButton(String showPayButton) {
                        this.showPayButton = showPayButton;
                    }

                    public String getStatus() {
                        return status;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }

                    @NonNull
                    @Override
                    public String toString() {
                        return "BillDatum{" + "dueDate='" + dueDate + '\'' + ", billDate='" + billDate + '\'' + ", receivedAmount='" + receivedAmount + '\'' + ", disputedAmount='" + disputedAmount + '\'' + ", billCreationDate='" + billCreationDate + '\'' + ", isDisputed='" + isDisputed + '\'' + ", billAmount='" + billAmount + '\'' + ", dueAmount='" + dueAmount + '\'' + ", disputeStatus='" + disputeStatus + '\'' + ", billId='" + billId + '\'' + ", billNumber='" + billNumber + '\'' + ", billDuration='" + billDuration + '\'' + ", showPayButton='" + showPayButton + '\'' + ", status='" + status + '\'' + '}';
                    }
                }

                @NonNull
                @Override
                public String toString() {
                    return "OrgBillDetail{" + "total=" + total + ", orgName='" + orgName + '\'' + ", orgId=" + orgId + ", billData=" + billData + '}';
                }
            }

            public static class OverAllTotal {

                @SerializedName("overallDueAmount")
                @Expose
                private String overallDueAmount;

                @SerializedName("overallBillAmount")
                @Expose
                private String overallBillAmount;

                @SerializedName("overallReceiveAmount")
                @Expose
                private String overallReceiveAmount;

                public String getOverallDueAmount() {
                    return overallDueAmount;
                }

                public void setOverallDueAmount(String overallDueAmount) {
                    this.overallDueAmount = overallDueAmount;
                }

                public String getOverallBillAmount() {
                    return overallBillAmount;
                }

                public void setOverallBillAmount(String overallBillAmount) {
                    this.overallBillAmount = overallBillAmount;
                }

                public String getOverallReceiveAmount() {
                    return overallReceiveAmount;
                }

                public void setOverallReceiveAmount(String overallReceiveAmount) {
                    this.overallReceiveAmount = overallReceiveAmount;
                }

                @NonNull
                @Override
                public String toString() {
                    return "OverAllTotal{" + "overallDueAmount='" + overallDueAmount + '\'' + ", overallBillAmount='" + overallBillAmount + '\'' + ", overallReceiveAmount='" + overallReceiveAmount + '\'' + '}';
                }
            }

            @NonNull
            @Override
            public String toString() {
                return "Data{" + "orgBillDetails=" + orgBillDetails + ", overAllTotal=" + overAllTotal + '}';
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
        return "BillReportResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
    }
}
