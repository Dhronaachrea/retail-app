package com.skilrock.retailapp.models.FieldX;

public class FieldXChallanItems {
    private String orId;
    private String task;
    private String challanNo;
    private String dlId;
    private String retailer;
    private String challanDate;

    public String getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(String challanDate) {
        this.challanDate = challanDate;
    }

    public FieldXChallanItems(String orId, String task, String challanNo, String dlId, String retailer, String challanDate) {
        this.orId = orId;
        this.task = task;
        this.challanNo = challanNo;
        this.dlId = dlId;
        this.retailer = retailer;
        this.challanDate = challanDate;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public String getDlId() {
        return dlId;
    }

    public void setDlId(String dlId) {
        this.dlId = dlId;
    }

    public String getOrId() {
        return orId;
    }

    public void setOrId(String orId) {
        this.orId = orId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
