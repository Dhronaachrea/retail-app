package com.skilrock.retailapp.models.FieldX;

import java.util.HashMap;

public class AllTaskItem {
    private int collect;
    private int pickup;
    private int delivery;
    private String orgId;
    private String name;
    private String address;
    private String fullAddress;
    private String amount;
    private String challanNumber;
    private String orgType;
    private String challanId;
    private boolean task;
    private String dlDate;
    private Double distnace;

    public Double getDistnace() {
        return distnace;
    }

    public void setDistnace(Double distnace) {
        this.distnace = distnace;
    }

    public String getDlDate() {
        return dlDate;
    }

    public void setDlDate(String dlDate) {
        this.dlDate = dlDate;
    }

    public int isDelivery() {
        return delivery;
    }

    public int isCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public int isPickup() {
        return pickup;
    }

    public void setPickup(int pickup) {
        this.pickup = pickup;
    }

    public boolean isTask() {
        return task;
    }

    public void setTask(boolean task) {
        this.task = task;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    private String retailer;
    private HashMap<HashMap<String, String>, String> enable = new HashMap<>();
    private HashMap<HashMap<String, String>, String> challanNo = new HashMap<>();

    public AllTaskItem(String challanId, String challanNumber) {
        this.challanNumber = challanNumber;
        this.challanId = challanId;
    }

    public AllTaskItem( String challanId, String challanNumber, String retailer) {
        this.challanNumber = challanNumber;
        this.challanId = challanId;
        this.retailer = retailer;
    }
    public AllTaskItem(String orgId, String name, String address, String amount, int delivery, int pickup, int collect,
                       String fullAddress, String orgType) {
        this.orgId = orgId;
        this.name = name;
        this.address = address;
        this.amount = amount;
        this.enable = enable;
        this.delivery =delivery;
        this.pickup= pickup;
        this.collect= collect;
        this.fullAddress= fullAddress;
        this.orgType = orgType;
    }

    public AllTaskItem(String orgId, String name, String address, String amount, HashMap<HashMap<String, String>, String> enable, HashMap<HashMap<String, String>,
            String> challanNo, int delivery, int pickup, int collect, String orgType) {
        this.orgId = orgId;
        this.name = name;
        this.address = address;
        this.amount = amount;
        this.enable = enable;
        this.challanNo = challanNo;
        this.delivery =delivery;
        this.pickup= pickup;
        this.collect= collect;
        this.orgType = orgType;
    }

    public AllTaskItem(String orgId, String name, String address, String amount, HashMap<HashMap<String, String>,
            String> enable) {
        this.orgId = orgId;
        this.name = name;
        this.address = address;
        this.amount = amount;
        this.enable = enable;
    }

    public AllTaskItem(String orgId, String name, String address, String amount) {
        this.orgId = orgId;
        this.name = name;
        this.address = address;
        this.amount = amount;
    }

    public String getChallanNumber() {
        return challanNumber;
    }

    public void setChallanNumber(String challanNumber) {
        this.challanNumber = challanNumber;
    }

    public String getChallanId() {
        return challanId;
    }

    public void setChallanId(String challanId) {
        this.challanId = challanId;
    }

    public HashMap<HashMap<String, String>, String> getEnable() {
        return enable;
    }

    public void setEnable(HashMap<HashMap<String, String>, String> enable) {
        this.enable = enable;
    }

    public HashMap<HashMap<String, String>, String> getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(HashMap<HashMap<String, String>, String> challanNo) {
        this.challanNo = challanNo;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}
