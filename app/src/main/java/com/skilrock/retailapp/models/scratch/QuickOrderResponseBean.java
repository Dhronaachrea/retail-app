package com.skilrock.retailapp.models.scratch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuickOrderResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("orderDateTime")
    @Expose
    private String orderDateTime;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    @Override
    public String toString() {
        return "QuickOrderResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", orderId=" + orderId + ", orderDateTime='" + orderDateTime + '\'' + '}';
    }
}
