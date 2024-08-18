package com.skilrock.retailapp.sle_game_portrait;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SbsPurchaseBean implements Parcelable {
    @SerializedName("ticketNo")
    @Expose
    private String ticketNo;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("saleAmount")
    @Expose
    private Double saleAmount;

    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
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

    public Double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Double saleAmount) {
        this.saleAmount = saleAmount;
    }

    public static class Transaction implements Parcelable {

        @SerializedName("transactionId")
        @Expose
        private Integer transactionId;
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
        @SerializedName("transactionIdAndTicketType")
        @Expose
        private String transactionIdAndTicketType;
        @SerializedName("selectedSystems")
        @Expose
        private String selectedSystems;
        @SerializedName("isCancelable")
        @Expose
        private Boolean isCancelable;
        @SerializedName("authoriserRemarks")
        @Expose
        private String authoriserRemarks;

        @SerializedName("betResponseDetailBeans")
        @Expose
        private List<BetResponseDetailBean> betResponseDetailBeans = null;
        @SerializedName("winnableAmount")
        @Expose
        private Double winnableAmount;

        public Integer getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Integer transactionId) {
            this.transactionId = transactionId;
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

        public String getTransactionIdAndTicketType() {
            return transactionIdAndTicketType;
        }

        public void setTransactionIdAndTicketType(String transactionIdAndTicketType) {
            this.transactionIdAndTicketType = transactionIdAndTicketType;
        }

        public String getSelectedSystems() {
            return selectedSystems;
        }

        public void setSelectedSystems(String selectedSystems) {
            this.selectedSystems = selectedSystems;
        }

        public Boolean getIsCancelable() {
            return isCancelable;
        }

        public void setIsCancelable(Boolean isCancelable) {
            this.isCancelable = isCancelable;
        }

        public String getAuthoriserRemarks() {
            return authoriserRemarks;
        }

        public void setAuthoriserRemarks(String authoriserRemarks) {
            this.authoriserRemarks = authoriserRemarks;
        }



        public List<BetResponseDetailBean> getBetResponseDetailBeans() {
            return betResponseDetailBeans;
        }

        public void setBetResponseDetailBeans(List<BetResponseDetailBean> betResponseDetailBeans) {
            this.betResponseDetailBeans = betResponseDetailBeans;
        }

        public Double getWinnableAmount() {
            return winnableAmount;
        }

        public void setWinnableAmount(Double winnableAmount) {
            this.winnableAmount = winnableAmount;
        }
        public static class BetResponseDetailBean implements Parcelable {

            @SerializedName("sport")
            @Expose
            private String sport;
            @SerializedName("eventName")
            @Expose
            private String eventName;
            @SerializedName("homeTeamName")
            @Expose
            private String homeTeamName;
            @SerializedName("awayTeamName")
            @Expose
            private String awayTeamName;
            @SerializedName("eventDate")
            @Expose
            private String eventDate;
            @SerializedName("eventDateActual")
            @Expose
            private String eventDateActual;
            @SerializedName("marketName")
            @Expose
            private String marketName;
            @SerializedName("outcomeName")
            @Expose
            private String outcomeName;
            @SerializedName("oddValue")
            @Expose
            private Double oddValue;
            @SerializedName("betSelectionDetail")
            @Expose
            private String betSelectionDetail;
            @SerializedName("eventResult")
            @Expose
            private String eventResult;
            @SerializedName("sportName")
            @Expose
            private String sportName;

            @SerializedName("eventId")
            @Expose
            private Integer eventId;

            public Integer getEventId() {
                return eventId;
            }

            public void setEventId(Integer eventId) {
                this.eventId = eventId;
            }

            public String getSport() {
                return sport;
            }

            public void setSport(String sport) {
                this.sport = sport;
            }

            public String getEventName() {
                return eventName;
            }

            public void setEventName(String eventName) {
                this.eventName = eventName;
            }

            public String getHomeTeamName() {
                return homeTeamName;
            }

            public void setHomeTeamName(String homeTeamName) {
                this.homeTeamName = homeTeamName;
            }

            public String getAwayTeamName() {
                return awayTeamName;
            }

            public void setAwayTeamName(String awayTeamName) {
                this.awayTeamName = awayTeamName;
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

            public String getMarketName() {
                return marketName;
            }

            public void setMarketName(String marketName) {
                this.marketName = marketName;
            }

            public String getOutcomeName() {
                return outcomeName;
            }

            public void setOutcomeName(String outcomeName) {
                this.outcomeName = outcomeName;
            }

            public Double getOddValue() {
                return oddValue;
            }

            public void setOddValue(Double oddValue) {
                this.oddValue = oddValue;
            }

            public String getBetSelectionDetail() {
                return betSelectionDetail;
            }

            public void setBetSelectionDetail(String betSelectionDetail) {
                this.betSelectionDetail = betSelectionDetail;
            }

            public String getEventResult() {
                return eventResult;
            }

            public void setEventResult(String eventResult) {
                this.eventResult = eventResult;
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
                dest.writeString(this.sport);
                dest.writeString(this.eventName);
                dest.writeString(this.homeTeamName);
                dest.writeString(this.awayTeamName);
                dest.writeString(this.eventDate);
                dest.writeString(this.eventDateActual);
                dest.writeString(this.marketName);
                dest.writeString(this.outcomeName);
                dest.writeValue(this.oddValue);
                dest.writeString(this.betSelectionDetail);
                dest.writeString(this.eventResult);
                dest.writeString(this.sportName);
                dest.writeValue(this.eventId);
            }

            public BetResponseDetailBean() {
            }

            protected BetResponseDetailBean(Parcel in) {
                this.sport = in.readString();
                this.eventName = in.readString();
                this.homeTeamName = in.readString();
                this.awayTeamName = in.readString();
                this.eventDate = in.readString();
                this.eventDateActual = in.readString();
                this.marketName = in.readString();
                this.outcomeName = in.readString();
                this.oddValue = (Double) in.readValue(Double.class.getClassLoader());
                this.betSelectionDetail = in.readString();
                this.eventResult = in.readString();
                this.sportName = in.readString();
                this.eventId = (Integer) in.readValue(Integer.class.getClassLoader());
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
            dest.writeValue(this.transactionId);
            dest.writeString(this.ticketType);
            dest.writeString(this.transactionDate);
            dest.writeValue(this.stake);
            dest.writeString(this.status);
            dest.writeString(this.winningStatus);
            dest.writeValue(this.payout);
            dest.writeString(this.payoutStatus);
            dest.writeString(this.transactionIdAndTicketType);
            dest.writeString(this.selectedSystems);
            dest.writeValue(this.isCancelable);
            dest.writeString(this.authoriserRemarks);
            dest.writeList(this.betResponseDetailBeans);
            dest.writeValue(this.winnableAmount);
        }

        public Transaction() {
        }

        protected Transaction(Parcel in) {
            this.transactionId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.ticketType = in.readString();
            this.transactionDate = in.readString();
            this.stake = (Double) in.readValue(Double.class.getClassLoader());
            this.status = in.readString();
            this.winningStatus = in.readString();
            this.payout = (Double) in.readValue(Double.class.getClassLoader());
            this.payoutStatus = in.readString();
            this.transactionIdAndTicketType = in.readString();
            this.selectedSystems = in.readString();
            this.isCancelable = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.authoriserRemarks = in.readString();
            this.betResponseDetailBeans = new ArrayList<BetResponseDetailBean>();
            in.readList(this.betResponseDetailBeans, BetResponseDetailBean.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ticketNo);
        dest.writeString(this.dateTime);
        dest.writeValue(this.saleAmount);
        dest.writeList(this.transactions);
    }

    public SbsPurchaseBean() {
    }

    protected SbsPurchaseBean(Parcel in) {
        this.ticketNo = in.readString();
        this.dateTime = in.readString();
        this.saleAmount = (Double) in.readValue(Double.class.getClassLoader());
        this.transactions = new ArrayList<Transaction>();
        in.readList(this.transactions, Transaction.class.getClassLoader());
    }

    public static final Parcelable.Creator<SbsPurchaseBean> CREATOR = new Parcelable.Creator<SbsPurchaseBean>() {
        @Override
        public SbsPurchaseBean createFromParcel(Parcel source) {
            return new SbsPurchaseBean(source);
        }

        @Override
        public SbsPurchaseBean[] newArray(int size) {
            return new SbsPurchaseBean[size];
        }
    };
}
