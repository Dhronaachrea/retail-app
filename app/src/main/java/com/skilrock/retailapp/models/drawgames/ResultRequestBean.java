package com.skilrock.retailapp.models.drawgames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultRequestBean {

    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("gameCode")
    @Expose
    private String gameCode;
    @SerializedName("merchantCode")
    @Expose
    private String merchantCode;
    @SerializedName("orderByOperator")
    @Expose
    private String orderByOperator;
    @SerializedName("orderByType")
    @Expose
    private String orderByType;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("size")
    @Expose
    private Integer size;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getOrderByOperator() {
        return orderByOperator;
    }

    public void setOrderByOperator(String orderByOperator) {
        this.orderByOperator = orderByOperator;
    }

    public String getOrderByType() {
        return orderByType;
    }

    public void setOrderByType(String orderByType) {
        this.orderByType = orderByType;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
