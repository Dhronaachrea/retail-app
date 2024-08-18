package com.skilrock.retailapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SbsWinPayResponse implements Parcelable {
    @SerializedName("responseHttpStatusCode")
    @Expose
    private Integer responseHttpStatusCode;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

    public Integer getResponseHttpStatusCode() {
        return responseHttpStatusCode;
    }

    public void setResponseHttpStatusCode(Integer responseHttpStatusCode) {
        this.responseHttpStatusCode = responseHttpStatusCode;
    }

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

    public static class ResponseData implements Parcelable {

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.message);
            dest.writeValue(this.statusCode);
            dest.writeParcelable(this.data, flags);
        }

        public ResponseData() {
        }

        protected ResponseData(Parcel in) {
            this.message = in.readString();
            this.statusCode = (Integer) in.readValue(Integer.class.getClassLoader());
            this.data = in.readParcelable(Data.class.getClassLoader());
        }

        public static final Creator<ResponseData> CREATOR = new Creator<ResponseData>() {
            @Override
            public ResponseData createFromParcel(Parcel source) {
                return new ResponseData(source);
            }

            @Override
            public ResponseData[] newArray(int size) {
                return new ResponseData[size];
            }
        };
    }
    public static class Data implements Parcelable {
        @SerializedName("dateTime")
        @Expose
        String dateTime;
        @SerializedName("ticketNo")
        @Expose
        private String ticketNo;
        @SerializedName("ticket")
        @Expose
        private Ticket ticket;
        @SerializedName("transactions")
        @Expose
        private List<Transaction> transactions = null;
        @SerializedName("paidDateTime")
        @Expose
        String paidDateTime;
        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public Ticket getTicket() {
            return ticket;
        }

        public void setTicket(Ticket ticket) {
            this.ticket = ticket;
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<Transaction> transactions) {
            this.transactions = transactions;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getPaidDateTime() {
            return paidDateTime;
        }

        public void setPaidDateTime(String paidDateTime) {
            this.paidDateTime = paidDateTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.dateTime);
            dest.writeString(this.ticketNo);
            dest.writeParcelable(this.ticket, flags);
            dest.writeTypedList(this.transactions);
            dest.writeString(this.paidDateTime);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.dateTime = in.readString();
            this.ticketNo = in.readString();
            this.ticket = in.readParcelable(Ticket.class.getClassLoader());
            this.transactions = in.createTypedArrayList(Transaction.CREATOR);
            this.paidDateTime = in.readString();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }
    public static class Transaction implements Parcelable {

        @SerializedName("transactionId")
        @Expose
        private Integer transactionId;
        @SerializedName("ticketNo")
        @Expose
        private String ticketNo;
        @SerializedName("playerId")
        @Expose
        private Integer playerId;
        @SerializedName("ticketType")
        @Expose
        private String ticketType;
        @SerializedName("transactionDate")
        @Expose
        private String transactionDate;
        @SerializedName("stake")
        @Expose
        private Double stake;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("winningStatus")
        @Expose
        private String winningStatus;
        @SerializedName("payout")
        @Expose
        private Double payout;
        @SerializedName("payoutStatus")
        @Expose
        private String payoutStatus;
        @SerializedName("selectedSystems")
        @Expose
        private String selectedSystems;
        @SerializedName("winnableAmount")
        @Expose
        private Double winnableAmount;

        public Integer getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Integer transactionId) {
            this.transactionId = transactionId;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public Integer getPlayerId() {
            return playerId;
        }

        public void setPlayerId(Integer playerId) {
            this.playerId = playerId;
        }

        public String getTicketType() {
            return ticketType;
        }

        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
        }

        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
        }

        public Double getStake() {
            return stake;
        }

        public void setStake(Double stake) {
            this.stake = stake;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWinningStatus() {
            return winningStatus;
        }

        public void setWinningStatus(String winningStatus) {
            this.winningStatus = winningStatus;
        }

        public Double getPayout() {
            return payout;
        }

        public void setPayout(Double payout) {
            this.payout = payout;
        }

        public String getPayoutStatus() {
            return payoutStatus;
        }

        public void setPayoutStatus(String payoutStatus) {
            this.payoutStatus = payoutStatus;
        }

        public String getSelectedSystems() {
            return selectedSystems;
        }

        public void setSelectedSystems(String selectedSystems) {
            this.selectedSystems = selectedSystems;
        }


        public Double getWinnableAmount() {
            return winnableAmount;
        }

        public void setWinnableAmount(Double winnableAmount) {
            this.winnableAmount = winnableAmount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.transactionId);
            dest.writeString(this.ticketNo);
            dest.writeValue(this.playerId);
            dest.writeString(this.ticketType);
            dest.writeString(this.transactionDate);
            dest.writeValue(this.stake);
            dest.writeString(this.status);
            dest.writeString(this.winningStatus);
            dest.writeValue(this.payout);
            dest.writeString(this.payoutStatus);
            dest.writeString(this.selectedSystems);
            dest.writeValue(this.winnableAmount);
        }

        public Transaction() {
        }

        protected Transaction(Parcel in) {
            this.transactionId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.ticketNo = in.readString();
            this.playerId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.ticketType = in.readString();
            this.transactionDate = in.readString();
            this.stake = (Double) in.readValue(Double.class.getClassLoader());
            this.status = in.readString();
            this.winningStatus = in.readString();
            this.payout = (Double) in.readValue(Double.class.getClassLoader());
            this.payoutStatus = in.readString();
            this.selectedSystems = in.readString();
            this.winnableAmount = (Double) in.readValue(Double.class.getClassLoader());
        }

        public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
            @Override
            public Transaction createFromParcel(Parcel source) {
                return new Transaction(source);
            }

            @Override
            public Transaction[] newArray(int size) {
                return new Transaction[size];
            }
        };
    }
    public static class Ticket implements Parcelable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("year")
        @Expose
        private Integer year;
        @SerializedName("month")
        @Expose
        private Integer month;
        @SerializedName("day")
        @Expose
        private Integer day;
        @SerializedName("retailerUserId")
        @Expose
        private Integer retailerUserId;
        @SerializedName("deviceId")
        @Expose
        private String deviceId;
        @SerializedName("randomNo")
        @Expose
        private Integer randomNo;
        @SerializedName("rmsServiceId")
        @Expose
        private Integer rmsServiceId;
        @SerializedName("rmsGameId")
        @Expose
        private Integer rmsGameId;
        @SerializedName("dayOfYear")
        @Expose
        private Integer dayOfYear;
        @SerializedName("retailerCode")
        @Expose
        private Integer retailerCode;
        @SerializedName("txnOfDay")
        @Expose
        private Integer txnOfDay;
        @SerializedName("ticketNo")
        @Expose
        private String ticketNo;
        @SerializedName("ticketType")
        @Expose
        private String ticketType;
        @SerializedName("txnCount")
        @Expose
        private Integer txnCount;
        @SerializedName("saleAmount")
        @Expose
        private Double saleAmount;
        @SerializedName("payoutAmount")
        @Expose
        private Double payoutAmount;

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("reprintCount")
        @Expose
        private Integer reprintCount;
        @SerializedName("lastTicketNo")
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        @SerializedName("paidAt")
        @Expose
        private String paidAt;

        public String getPaidAt() {
            return paidAt;
        }

        public void setPaidAt(String paidAt) {
            this.paidAt = paidAt;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public Integer getRetailerUserId() {
            return retailerUserId;
        }

        public void setRetailerUserId(Integer retailerUserId) {
            this.retailerUserId = retailerUserId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getRandomNo() {
            return randomNo;
        }

        public void setRandomNo(Integer randomNo) {
            this.randomNo = randomNo;
        }

        public Integer getRmsServiceId() {
            return rmsServiceId;
        }

        public void setRmsServiceId(Integer rmsServiceId) {
            this.rmsServiceId = rmsServiceId;
        }

        public Integer getRmsGameId() {
            return rmsGameId;
        }

        public void setRmsGameId(Integer rmsGameId) {
            this.rmsGameId = rmsGameId;
        }

        public Integer getDayOfYear() {
            return dayOfYear;
        }

        public void setDayOfYear(Integer dayOfYear) {
            this.dayOfYear = dayOfYear;
        }

        public Integer getRetailerCode() {
            return retailerCode;
        }

        public void setRetailerCode(Integer retailerCode) {
            this.retailerCode = retailerCode;
        }

        public Integer getTxnOfDay() {
            return txnOfDay;
        }

        public void setTxnOfDay(Integer txnOfDay) {
            this.txnOfDay = txnOfDay;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public String getTicketType() {
            return ticketType;
        }

        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
        }

        public Integer getTxnCount() {
            return txnCount;
        }

        public void setTxnCount(Integer txnCount) {
            this.txnCount = txnCount;
        }

        public Double getSaleAmount() {
            return saleAmount;
        }

        public void setSaleAmount(Double saleAmount) {
            this.saleAmount = saleAmount;
        }

        public Double getPayoutAmount() {
            return payoutAmount;
        }

        public void setPayoutAmount(Double payoutAmount) {
            this.payoutAmount = payoutAmount;
        }



        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getReprintCount() {
            return reprintCount;
        }

        public void setReprintCount(Integer reprintCount) {
            this.reprintCount = reprintCount;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeValue(this.year);
            dest.writeValue(this.month);
            dest.writeValue(this.day);
            dest.writeValue(this.retailerUserId);
            dest.writeString(this.deviceId);
            dest.writeValue(this.randomNo);
            dest.writeValue(this.rmsServiceId);
            dest.writeValue(this.rmsGameId);
            dest.writeValue(this.dayOfYear);
            dest.writeValue(this.retailerCode);
            dest.writeValue(this.txnOfDay);
            dest.writeString(this.ticketNo);
            dest.writeString(this.ticketType);
            dest.writeValue(this.txnCount);
            dest.writeValue(this.saleAmount);
            dest.writeValue(this.payoutAmount);
            dest.writeString(this.status);
            dest.writeValue(this.reprintCount);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
            dest.writeString(this.paidAt);
        }

        public Ticket() {
        }

        protected Ticket(Parcel in) {
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.year = (Integer) in.readValue(Integer.class.getClassLoader());
            this.month = (Integer) in.readValue(Integer.class.getClassLoader());
            this.day = (Integer) in.readValue(Integer.class.getClassLoader());
            this.retailerUserId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.deviceId = in.readString();
            this.randomNo = (Integer) in.readValue(Integer.class.getClassLoader());
            this.rmsServiceId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.rmsGameId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.dayOfYear = (Integer) in.readValue(Integer.class.getClassLoader());
            this.retailerCode = (Integer) in.readValue(Integer.class.getClassLoader());
            this.txnOfDay = (Integer) in.readValue(Integer.class.getClassLoader());
            this.ticketNo = in.readString();
            this.ticketType = in.readString();
            this.txnCount = (Integer) in.readValue(Integer.class.getClassLoader());
            this.saleAmount = (Double) in.readValue(Double.class.getClassLoader());
            this.payoutAmount = (Double) in.readValue(Double.class.getClassLoader());
            this.status = in.readString();
            this.reprintCount = (Integer) in.readValue(Integer.class.getClassLoader());
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
            this.paidAt = in.readString();
        }

        public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
            @Override
            public Ticket createFromParcel(Parcel source) {
                return new Ticket(source);
            }

            @Override
            public Ticket[] newArray(int size) {
                return new Ticket[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.responseHttpStatusCode);
        dest.writeValue(this.responseCode);
        dest.writeString(this.responseMessage);
        dest.writeParcelable(this.responseData, flags);
    }

    public SbsWinPayResponse() {
    }

    protected SbsWinPayResponse(Parcel in) {
        this.responseHttpStatusCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.responseData = in.readParcelable(ResponseData.class.getClassLoader());
    }

    public static final Parcelable.Creator<SbsWinPayResponse> CREATOR = new Parcelable.Creator<SbsWinPayResponse>() {
        @Override
        public SbsWinPayResponse createFromParcel(Parcel source) {
            return new SbsWinPayResponse(source);
        }

        @Override
        public SbsWinPayResponse[] newArray(int size) {
            return new SbsWinPayResponse[size];
        }
    };
}

