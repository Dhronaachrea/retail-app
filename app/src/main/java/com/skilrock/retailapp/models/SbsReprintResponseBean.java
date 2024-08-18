package com.skilrock.retailapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SbsReprintResponseBean implements Parcelable {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;
    @SerializedName("responseHttpStatusCode")
    @Expose
    private Integer responseHttpStatusCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public Integer getResponseHttpStatusCode() {
        return responseHttpStatusCode;
    }

    public void setResponseHttpStatusCode(Integer responseHttpStatusCode) {
        this.responseHttpStatusCode = responseHttpStatusCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public static class ResponseData implements Parcelable {

        @SerializedName("data")
        @Expose
        private Data data;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.data, flags);
            dest.writeString(this.message);
            dest.writeValue(this.statusCode);
        }

        public ResponseData() {
        }

        protected ResponseData(Parcel in) {
            this.data = in.readParcelable(Data.class.getClassLoader());
            this.message = in.readString();
            this.statusCode = (Integer) in.readValue(Integer.class.getClassLoader());
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
        private String dateTime;
        @SerializedName("ticket")
        @Expose
        private Ticket ticket;
        @SerializedName("ticketNo")
        @Expose
        private String ticketNo;
        @SerializedName("ticketStatus")
        @Expose
        private String ticketStatus;
        @SerializedName("transactions")
        @Expose
        private List<Transaction> transactions = null;

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public Ticket getTicket() {
            return ticket;
        }

        public void setTicket(Ticket ticket) {
            this.ticket = ticket;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public String getTicketStatus() {
            return ticketStatus;
        }

        public void setTicketStatus(String ticketStatus) {
            this.ticketStatus = ticketStatus;
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<Transaction> transactions) {
            this.transactions = transactions;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.dateTime);
            dest.writeParcelable(this.ticket, flags);
            dest.writeString(this.ticketNo);
            dest.writeString(this.ticketStatus);
            dest.writeList(this.transactions);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.dateTime = in.readString();
            this.ticket = in.readParcelable(Ticket.class.getClassLoader());
            this.ticketNo = in.readString();
            this.ticketStatus = in.readString();
            this.transactions = new ArrayList<Transaction>();
            in.readList(this.transactions, Transaction.class.getClassLoader());
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
    public static class Ticket implements Parcelable {

        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("day")
        @Expose
        private Integer day;
        @SerializedName("dayOfYear")
        @Expose
        private Integer dayOfYear;
        @SerializedName("deviceId")
        @Expose
        private String deviceId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("lastRandomNo")
        @Expose
        private Integer lastRandomNo;
        @SerializedName("lastReprintAt")
        @Expose
        private String lastReprintAt;
        @SerializedName("lastTicketNo")
        @Expose
        private String lastTicketNo;
        @SerializedName("month")
        @Expose
        private Integer month;
        @SerializedName("randomNo")
        @Expose
        private Integer randomNo;
        @SerializedName("reprintCount")
        @Expose
        private Integer reprintCount;
        @SerializedName("retailerCode")
        @Expose
        private Integer retailerCode;
        @SerializedName("retailerUserId")
        @Expose
        private Integer retailerUserId;
        @SerializedName("rmsGameId")
        @Expose
        private Integer rmsGameId;
        @SerializedName("rmsServiceId")
        @Expose
        private Integer rmsServiceId;
        @SerializedName("saleAmount")
        @Expose
        private Double saleAmount;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("ticketDate")
        @Expose
        private String ticketDate;
        @SerializedName("ticketNo")
        @Expose
        private String ticketNo;
        @SerializedName("ticketType")
        @Expose
        private String ticketType;
        @SerializedName("txnCount")
        @Expose
        private Integer txnCount;
        @SerializedName("txnOfDay")
        @Expose
        private Integer txnOfDay;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("year")
        @Expose
        private Integer year;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public Integer getDayOfYear() {
            return dayOfYear;
        }

        public void setDayOfYear(Integer dayOfYear) {
            this.dayOfYear = dayOfYear;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getLastRandomNo() {
            return lastRandomNo;
        }

        public void setLastRandomNo(Integer lastRandomNo) {
            this.lastRandomNo = lastRandomNo;
        }

        public String getLastReprintAt() {
            return lastReprintAt;
        }

        public void setLastReprintAt(String lastReprintAt) {
            this.lastReprintAt = lastReprintAt;
        }

        public String getLastTicketNo() {
            return lastTicketNo;
        }

        public void setLastTicketNo(String lastTicketNo) {
            this.lastTicketNo = lastTicketNo;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getRandomNo() {
            return randomNo;
        }

        public void setRandomNo(Integer randomNo) {
            this.randomNo = randomNo;
        }

        public Integer getReprintCount() {
            return reprintCount;
        }

        public void setReprintCount(Integer reprintCount) {
            this.reprintCount = reprintCount;
        }

        public Integer getRetailerCode() {
            return retailerCode;
        }

        public void setRetailerCode(Integer retailerCode) {
            this.retailerCode = retailerCode;
        }

        public Integer getRetailerUserId() {
            return retailerUserId;
        }

        public void setRetailerUserId(Integer retailerUserId) {
            this.retailerUserId = retailerUserId;
        }

        public Integer getRmsGameId() {
            return rmsGameId;
        }

        public void setRmsGameId(Integer rmsGameId) {
            this.rmsGameId = rmsGameId;
        }

        public Integer getRmsServiceId() {
            return rmsServiceId;
        }

        public void setRmsServiceId(Integer rmsServiceId) {
            this.rmsServiceId = rmsServiceId;
        }

        public Double getSaleAmount() {
            return saleAmount;
        }

        public void setSaleAmount(Double saleAmount) {
            this.saleAmount = saleAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTicketDate() {
            return ticketDate;
        }

        public void setTicketDate(String ticketDate) {
            this.ticketDate = ticketDate;
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

        public Integer getTxnOfDay() {
            return txnOfDay;
        }

        public void setTxnOfDay(Integer txnOfDay) {
            this.txnOfDay = txnOfDay;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.createdAt);
            dest.writeValue(this.day);
            dest.writeValue(this.dayOfYear);
            dest.writeString(this.deviceId);
            dest.writeValue(this.id);
            dest.writeValue(this.lastRandomNo);
            dest.writeString(this.lastReprintAt);
            dest.writeString(this.lastTicketNo);
            dest.writeValue(this.month);
            dest.writeValue(this.randomNo);
            dest.writeValue(this.reprintCount);
            dest.writeValue(this.retailerCode);
            dest.writeValue(this.retailerUserId);
            dest.writeValue(this.rmsGameId);
            dest.writeValue(this.rmsServiceId);
            dest.writeValue(this.saleAmount);
            dest.writeString(this.status);
            dest.writeString(this.ticketDate);
            dest.writeString(this.ticketNo);
            dest.writeString(this.ticketType);
            dest.writeValue(this.txnCount);
            dest.writeValue(this.txnOfDay);
            dest.writeString(this.updatedAt);
            dest.writeValue(this.year);
        }

        public Ticket() {
        }

        protected Ticket(Parcel in) {
            this.createdAt = in.readString();
            this.day = (Integer) in.readValue(Integer.class.getClassLoader());
            this.dayOfYear = (Integer) in.readValue(Integer.class.getClassLoader());
            this.deviceId = in.readString();
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.lastRandomNo = (Integer) in.readValue(Integer.class.getClassLoader());
            this.lastReprintAt = in.readString();
            this.lastTicketNo = in.readString();
            this.month = (Integer) in.readValue(Integer.class.getClassLoader());
            this.randomNo = (Integer) in.readValue(Integer.class.getClassLoader());
            this.reprintCount = (Integer) in.readValue(Integer.class.getClassLoader());
            this.retailerCode = (Integer) in.readValue(Integer.class.getClassLoader());
            this.retailerUserId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.rmsGameId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.rmsServiceId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.saleAmount = (Double) in.readValue(Double.class.getClassLoader());
            this.status = in.readString();
            this.ticketDate = in.readString();
            this.ticketNo = in.readString();
            this.ticketType = in.readString();
            this.txnCount = (Integer) in.readValue(Integer.class.getClassLoader());
            this.txnOfDay = (Integer) in.readValue(Integer.class.getClassLoader());
            this.updatedAt = in.readString();
            this.year = (Integer) in.readValue(Integer.class.getClassLoader());
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
    public static class Transaction implements Parcelable {

        @SerializedName("betResponseDetailBeans")
        @Expose
        private List<BetResponseDetailBean> betResponseDetailBeans = null;
        @SerializedName("isCancelable")
        @Expose
        private Boolean isCancelable;
        @SerializedName("mtsTicketId")
        @Expose
        private String mtsTicketId;
        @SerializedName("payout")
        @Expose
        private Double payout;
        @SerializedName("selectedSystems")
        @Expose
        private String selectedSystems;
        @SerializedName("stake")
        @Expose
        private Double stake;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("ticketType")
        @Expose
        private String ticketType;
        @SerializedName("transactionDate")
        @Expose
        private String transactionDate;
        @SerializedName("transactionId")
        @Expose
        private Integer transactionId;
        @SerializedName("transactionIdAndTicketType")
        @Expose
        private String transactionIdAndTicketType;
        @SerializedName("winnableAmount")
        @Expose
        private Double winnableAmount;

        public List<BetResponseDetailBean> getBetResponseDetailBeans() {
            return betResponseDetailBeans;
        }

        public void setBetResponseDetailBeans(List<BetResponseDetailBean> betResponseDetailBeans) {
            this.betResponseDetailBeans = betResponseDetailBeans;
        }

        public Boolean getIsCancelable() {
            return isCancelable;
        }

        public void setIsCancelable(Boolean isCancelable) {
            this.isCancelable = isCancelable;
        }

        public String getMtsTicketId() {
            return mtsTicketId;
        }

        public void setMtsTicketId(String mtsTicketId) {
            this.mtsTicketId = mtsTicketId;
        }

        public Double getPayout() {
            return payout;
        }

        public void setPayout(Double payout) {
            this.payout = payout;
        }

        public String getSelectedSystems() {
            return selectedSystems;
        }

        public void setSelectedSystems(String selectedSystems) {
            this.selectedSystems = selectedSystems;
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

        public Integer getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Integer transactionId) {
            this.transactionId = transactionId;
        }

        public String getTransactionIdAndTicketType() {
            return transactionIdAndTicketType;
        }

        public void setTransactionIdAndTicketType(String transactionIdAndTicketType) {
            this.transactionIdAndTicketType = transactionIdAndTicketType;
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
            dest.writeList(this.betResponseDetailBeans);
            dest.writeValue(this.isCancelable);
            dest.writeString(this.mtsTicketId);
            dest.writeValue(this.payout);
            dest.writeString(this.selectedSystems);
            dest.writeValue(this.stake);
            dest.writeString(this.status);
            dest.writeString(this.ticketType);
            dest.writeString(this.transactionDate);
            dest.writeValue(this.transactionId);
            dest.writeString(this.transactionIdAndTicketType);
            dest.writeValue(this.winnableAmount);
        }

        public Transaction() {
        }

        protected Transaction(Parcel in) {
            this.betResponseDetailBeans = new ArrayList<BetResponseDetailBean>();
            in.readList(this.betResponseDetailBeans, BetResponseDetailBean.class.getClassLoader());
            this.isCancelable = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.mtsTicketId = in.readString();
            this.payout = (Double) in.readValue(Double.class.getClassLoader());
            this.selectedSystems = in.readString();
            this.stake = (Double) in.readValue(Double.class.getClassLoader());
            this.status = in.readString();
            this.ticketType = in.readString();
            this.transactionDate = in.readString();
            this.transactionId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.transactionIdAndTicketType = in.readString();
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

    public static class BetResponseDetailBean implements Parcelable {

        @SerializedName("awayTeamName")
        @Expose
        private String awayTeamName;
        @SerializedName("betSelectionDetail")
        @Expose
        private String betSelectionDetail;
        @SerializedName("eventDate")
        @Expose
        private String eventDate;
        @SerializedName("eventDateActual")
        @Expose
        private String eventDateActual;
        @SerializedName("eventId")
        @Expose
        private Integer eventId;
        @SerializedName("eventName")
        @Expose
        private String eventName;
        @SerializedName("eventResult")
        @Expose
        private String eventResult;
        @SerializedName("homeTeamName")
        @Expose
        private String homeTeamName;
        @SerializedName("marketName")
        @Expose
        private String marketName;
        @SerializedName("oddValue")
        @Expose
        private Double oddValue;
        @SerializedName("outcomeName")
        @Expose
        private String outcomeName;
        @SerializedName("sport")
        @Expose
        private String sport;
        @SerializedName("sportName")
        @Expose
        private String sportName;

        public String getAwayTeamName() {
            return awayTeamName;
        }

        public void setAwayTeamName(String awayTeamName) {
            this.awayTeamName = awayTeamName;
        }

        public String getBetSelectionDetail() {
            return betSelectionDetail;
        }

        public void setBetSelectionDetail(String betSelectionDetail) {
            this.betSelectionDetail = betSelectionDetail;
        }

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public String getEventDateActual() {
            return eventDateActual;
        }

        public void setEventDateActual(String eventDateActual) {
            this.eventDateActual = eventDateActual;
        }

        public Integer getEventId() {
            return eventId;
        }

        public void setEventId(Integer eventId) {
            this.eventId = eventId;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getEventResult() {
            return eventResult;
        }

        public void setEventResult(String eventResult) {
            this.eventResult = eventResult;
        }

        public String getHomeTeamName() {
            return homeTeamName;
        }

        public void setHomeTeamName(String homeTeamName) {
            this.homeTeamName = homeTeamName;
        }

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }


        public Double getOddValue() {
            return oddValue;
        }

        public void setOddValue(Double oddValue) {
            this.oddValue = oddValue;
        }

        public String getOutcomeName() {
            return outcomeName;
        }

        public void setOutcomeName(String outcomeName) {
            this.outcomeName = outcomeName;
        }

        public String getSport() {
            return sport;
        }

        public void setSport(String sport) {
            this.sport = sport;
        }

        public String getSportName() {
            return sportName;
        }

        public void setSportName(String sportName) {
            this.sportName = sportName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.awayTeamName);
            dest.writeString(this.betSelectionDetail);
            dest.writeString(this.eventDate);
            dest.writeString(this.eventDateActual);
            dest.writeValue(this.eventId);
            dest.writeString(this.eventName);
            dest.writeString(this.eventResult);
            dest.writeString(this.homeTeamName);
            dest.writeString(this.marketName);
            dest.writeValue(this.oddValue);
            dest.writeString(this.outcomeName);
            dest.writeString(this.sport);
            dest.writeString(this.sportName);
        }

        public BetResponseDetailBean() {
        }

        protected BetResponseDetailBean(Parcel in) {
            this.awayTeamName = in.readString();
            this.betSelectionDetail = in.readString();
            this.eventDate = in.readString();
            this.eventDateActual = in.readString();
            this.eventId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.eventName = in.readString();
            this.eventResult = in.readString();
            this.homeTeamName = in.readString();
            this.marketName = in.readString();
            this.oddValue = (Double) in.readValue(Double.class.getClassLoader());
            this.outcomeName = in.readString();
            this.sport = in.readString();
            this.sportName = in.readString();
        }

        public static final Creator<BetResponseDetailBean> CREATOR = new Creator<BetResponseDetailBean>() {
            @Override
            public BetResponseDetailBean createFromParcel(Parcel source) {
                return new BetResponseDetailBean(source);
            }

            @Override
            public BetResponseDetailBean[] newArray(int size) {
                return new BetResponseDetailBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.responseCode);
        dest.writeParcelable(this.responseData, flags);
        dest.writeValue(this.responseHttpStatusCode);
        dest.writeString(this.responseMessage);
    }

    public SbsReprintResponseBean() {
    }

    protected SbsReprintResponseBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseData = in.readParcelable(ResponseData.class.getClassLoader());
        this.responseHttpStatusCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
    }

    public static final Parcelable.Creator<SbsReprintResponseBean> CREATOR = new Parcelable.Creator<SbsReprintResponseBean>() {
        @Override
        public SbsReprintResponseBean createFromParcel(Parcel source) {
            return new SbsReprintResponseBean(source);
        }

        @Override
        public SbsReprintResponseBean[] newArray(int size) {
            return new SbsReprintResponseBean[size];
        }
    };
}
