package com.skilrock.retailapp.models.drawgames;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TicketCancelResponseBean implements Parcelable {

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

    public static class ResponseData implements Parcelable {

        @SerializedName("autoCancel")
        @Expose
        private String autoCancel;
        @SerializedName("gameCode")
        @Expose
        private String gameCode;
        @SerializedName("cancelChannel")
        @Expose
        private String cancelChannel;
        @SerializedName("refundAmount")
        @Expose
        private String refundAmount;
        @SerializedName("ticketNo")
        @Expose
        private String ticketNo;
        @SerializedName("merchantreftxnid")
        @Expose
        private String merchantreftxnid;
        @SerializedName("retailerBalance")
        @Expose
        private String retailerBalance;
        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("enginetxid")
        @Expose
        private String enginetxid;
        @SerializedName("drawData")
        @Expose
        private List<DrawDatum> drawData = null;

        public String getAutoCancel() {
            return autoCancel;
        }

        public void setAutoCancel(String autoCancel) {
            this.autoCancel = autoCancel;
        }

        public String getGameCode() {
            return gameCode;
        }

        public void setGameCode(String gameCode) {
            this.gameCode = gameCode;
        }

        public String getCancelChannel() {
            return cancelChannel;
        }

        public void setCancelChannel(String cancelChannel) {
            this.cancelChannel = cancelChannel;
        }

        public String getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(String refundAmount) {
            this.refundAmount = refundAmount;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public String getMerchantreftxnid() {
            return merchantreftxnid;
        }

        public void setMerchantreftxnid(String merchantreftxnid) {
            this.merchantreftxnid = merchantreftxnid;
        }

        public String getRetailerBalance() {
            return retailerBalance;
        }

        public void setRetailerBalance(String retailerBalance) {
            this.retailerBalance = retailerBalance;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getEnginetxid() {
            return enginetxid;
        }

        public void setEnginetxid(String enginetxid) {
            this.enginetxid = enginetxid;
        }

        public List<DrawDatum> getDrawData() {
            return drawData;
        }

        public void setDrawData(List<DrawDatum> drawData) {
            this.drawData = drawData;
        }


        public static class DrawDatum implements Parcelable {

            @SerializedName("drawName")
            @Expose
            private String drawName;
            @SerializedName("drawDate")
            @Expose
            private String drawDate;
            @SerializedName("drawTime")
            @Expose
            private String drawTime;

            public String getDrawName() {
                return drawName;
            }

            public void setDrawName(String drawName) {
                this.drawName = drawName;
            }

            public String getDrawDate() {
                return drawDate;
            }

            public void setDrawDate(String drawDate) {
                this.drawDate = drawDate;
            }

            public String getDrawTime() {
                return drawTime;
            }

            public void setDrawTime(String drawTime) {
                this.drawTime = drawTime;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.drawName);
                dest.writeString(this.drawDate);
                dest.writeString(this.drawTime);
            }

            public DrawDatum() {
            }

            protected DrawDatum(Parcel in) {
                this.drawName = in.readString();
                this.drawDate = in.readString();
                this.drawTime = in.readString();
            }

            public static final Creator<DrawDatum> CREATOR = new Creator<DrawDatum>() {
                @Override
                public DrawDatum createFromParcel(Parcel source) {
                    return new DrawDatum(source);
                }

                @Override
                public DrawDatum[] newArray(int size) {
                    return new DrawDatum[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.autoCancel);
            dest.writeString(this.gameCode);
            dest.writeString(this.cancelChannel);
            dest.writeString(this.refundAmount);
            dest.writeString(this.ticketNo);
            dest.writeString(this.merchantreftxnid);
            dest.writeString(this.retailerBalance);
            dest.writeString(this.gameName);
            dest.writeString(this.enginetxid);
            dest.writeList(this.drawData);
        }

        public ResponseData() {
        }

        protected ResponseData(Parcel in) {
            this.autoCancel = in.readString();
            this.gameCode = in.readString();
            this.cancelChannel = in.readString();
            this.refundAmount = in.readString();
            this.ticketNo = in.readString();
            this.merchantreftxnid = in.readString();
            this.retailerBalance = in.readString();
            this.gameName = in.readString();
            this.enginetxid = in.readString();
            this.drawData = new ArrayList<DrawDatum>();
            in.readList(this.drawData, DrawDatum.class.getClassLoader());
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.responseCode);
        dest.writeString(this.responseMessage);
        dest.writeParcelable(this.responseData, flags);
    }

    public TicketCancelResponseBean() {
    }

    protected TicketCancelResponseBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.responseData = in.readParcelable(ResponseData.class.getClassLoader());
    }

    public static final Parcelable.Creator<TicketCancelResponseBean> CREATOR = new Parcelable.Creator<TicketCancelResponseBean>() {
        @Override
        public TicketCancelResponseBean createFromParcel(Parcel source) {
            return new TicketCancelResponseBean(source);
        }

        @Override
        public TicketCancelResponseBean[] newArray(int size) {
            return new TicketCancelResponseBean[size];
        }
    };
}
