package com.skilrock.retailapp.models.FieldX;

public class FieldXCollectionReportItem {
    private String createdAt;
    String date, txnTYpe, serviceName, balance, netAMount;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public FieldXCollectionReportItem(String date, String txnTYpe, String serviceName, String balance, String netAMount, String createdAt) {
        this.date = date;
        this.txnTYpe = txnTYpe;
        this.serviceName = serviceName;
        this.balance = balance;
        this.netAMount = netAMount;
        this.createdAt = createdAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTxnTYpe() {
        return txnTYpe;
    }

    public void setTxnTYpe(String txnTYpe) {
        this.txnTYpe = txnTYpe;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getNetAMount() {
        return netAMount;
    }

    public void setNetAMount(String netAMount) {
        this.netAMount = netAMount;
    }
}
