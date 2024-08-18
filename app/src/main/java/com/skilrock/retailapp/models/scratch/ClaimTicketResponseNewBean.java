package com.skilrock.retailapp.models.scratch;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ClaimTicketResponseNewBean implements Parcelable {


    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("transactionId")
    @Expose
    private Integer transactionId;
    @SerializedName("transactionNumber")
    @Expose
    private String transactionNumber;
    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("ticketNumber")
    @Expose
    private String ticketNumber;
    @SerializedName("taxAmount")
    @Expose
    private String taxAmount;
    @SerializedName("virnNumber")
    @Expose
    private String virnNumber;
    @SerializedName("winningAmount")
    @Expose
    private String winningAmount;
    @SerializedName("commissionAmount")
    @Expose
    private String commissionAmount;
    @SerializedName("tdsAmount")
    @Expose
    private String tdsAmount;
    @SerializedName("netWinningAmount")
    @Expose
    private String netWinningAmount;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    @SerializedName("advMessages")
    private AdvMessageBean advMessages;

    public AdvMessageBean getAdvMessages() {
        return advMessages;
    }

    public void setAdvMessages(AdvMessageBean advMessages) {
        this.advMessages = advMessages;
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

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getVirnNumber() {
        return virnNumber;
    }

    public void setVirnNumber(String virnNumber) {
        this.virnNumber = virnNumber;
    }

    public String getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(String winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(String tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getNetWinningAmount() {
        return netWinningAmount;
    }

    public void setNetWinningAmount(String netWinningAmount) {
        this.netWinningAmount = netWinningAmount;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @NonNull
    @Override
    public String toString() {
        return "ClaimTicketResponseNewBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", transactionId=" + transactionId + ", transactionNumber='" + transactionNumber + '\'' + ", transactionDate='" + transactionDate + '\'' + ", ticketNumber='" + ticketNumber + '\'' + ", virnNumber='" + virnNumber + '\'' + ", winningAmount=" + winningAmount + ", commissionAmount=" + commissionAmount + ", tdsAmount=" + tdsAmount + ", netWinningAmount=" + netWinningAmount + ", taxAmount=" + taxAmount + ", requestId='" + requestId + '\'' + '}';
    }

    public ClaimTicketResponseNewBean() {
    }

    public static class AdvMessageBean implements Parcelable {
         @SerializedName("top")
         @Expose
         private List<Top> top = null;

         @SerializedName("bottom")
         @Expose
         private List<Bottom> bottom = null;

         public List<Top> getTop() {
             return top;
         }

         public void setTop(List<Top> top) {
             this.top = top;
         }

         public List<Bottom> getBottom() {
             return bottom;
         }

         public void setBottom(List<Bottom> bottom) {
             this.bottom = bottom;
         }

         public static class Bottom implements Parcelable {
             @SerializedName("msgMode")
             @Expose
             private String msgMode;

             @SerializedName("msgText")
             @Expose
             private String msgText;

             @SerializedName("msgType")
             @Expose
             private String msgType;

             public String getMsgMode() {
                 return msgMode;
             }

             public void setMsgMode(String msgMode) {
                 this.msgMode = msgMode;
             }

             public String getMsgText() {
                 return msgText;
             }

             public void setMsgText(String msgText) {
                 this.msgText = msgText;
             }

             public String getMsgType() {
                 return msgType;
             }

             public void setMsgType(String msgType) {
                 this.msgType = msgType;
             }

             @Override
             public int describeContents() {
                 return 0;
             }

             @Override
             public void writeToParcel(Parcel dest, int flags) {
                 dest.writeString(this.msgMode);
                 dest.writeString(this.msgText);
                 dest.writeString(this.msgType);
             }

             public Bottom() {
             }

             protected Bottom(Parcel in) {
                 this.msgMode = in.readString();
                 this.msgText = in.readString();
                 this.msgType = in.readString();
             }

             public static final Creator<Bottom> CREATOR = new Creator<Bottom>() {
                 @Override
                 public Bottom createFromParcel(Parcel source) {
                     return new Bottom(source);
                 }

                 @Override
                 public Bottom[] newArray(int size) {
                     return new Bottom[size];
                 }
             };
         }

         public static class Top implements Parcelable {
             @SerializedName("msgMode")
             @Expose
             private String msgMode;

             @SerializedName("msgText")
             @Expose
             private String msgText;

             @SerializedName("msgType")
             @Expose
             private String msgType;

             public String getMsgMode() {
                 return msgMode;
             }

             public void setMsgMode(String msgMode) {
                 this.msgMode = msgMode;
             }

             public String getMsgText() {
                 return msgText;
             }

             public void setMsgText(String msgText) {
                 this.msgText = msgText;
             }

             public String getMsgType() {
                 return msgType;
             }

             public void setMsgType(String msgType) {
                 this.msgType = msgType;
             }

             @Override
             public int describeContents() {
                 return 0;
             }

             @Override
             public void writeToParcel(Parcel dest, int flags) {
                 dest.writeString(this.msgMode);
                 dest.writeString(this.msgText);
                 dest.writeString(this.msgType);
             }

             public Top() {
             }

             protected Top(Parcel in) {
                 this.msgMode = in.readString();
                 this.msgText = in.readString();
                 this.msgType = in.readString();
             }

             public static final Creator<Top> CREATOR = new Creator<Top>() {
                 @Override
                 public Top createFromParcel(Parcel source) {
                     return new Top(source);
                 }

                 @Override
                 public Top[] newArray(int size) {
                     return new Top[size];
                 }
             };
         }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.top);
            dest.writeList(this.bottom);
        }

        public AdvMessageBean() {
        }

        protected AdvMessageBean(Parcel in) {
            this.top = new ArrayList<Top>();
            in.readList(this.top, Top.class.getClassLoader());
            this.bottom = new ArrayList<Bottom>();
            in.readList(this.bottom, Bottom.class.getClassLoader());
        }

        public static final Creator<AdvMessageBean> CREATOR = new Creator<AdvMessageBean>() {
            @Override
            public AdvMessageBean createFromParcel(Parcel source) {
                return new AdvMessageBean(source);
            }

            @Override
            public AdvMessageBean[] newArray(int size) {
                return new AdvMessageBean[size];
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
        dest.writeValue(this.transactionId);
        dest.writeString(this.transactionNumber);
        dest.writeString(this.transactionDate);
        dest.writeString(this.ticketNumber);
        dest.writeString(this.virnNumber);
        dest.writeString(this.winningAmount);
        dest.writeString(this.commissionAmount);
        dest.writeString(this.tdsAmount);
        dest.writeString(this.netWinningAmount);
        dest.writeString(this.requestId);
        dest.writeParcelable(this.advMessages, flags);
    }

    protected ClaimTicketResponseNewBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.transactionId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.transactionNumber = in.readString();
        this.transactionDate = in.readString();
        this.ticketNumber = in.readString();
        this.virnNumber = in.readString();
        this.winningAmount = in.readString();
        this.commissionAmount = in.readString();
        this.tdsAmount = in.readString();
        this.netWinningAmount = in.readString();
        this.requestId = in.readString();
        this.advMessages = in.readParcelable(AdvMessageBean.class.getClassLoader());
    }

    public static final Creator<ClaimTicketResponseNewBean> CREATOR = new Creator<ClaimTicketResponseNewBean>() {
        @Override
        public ClaimTicketResponseNewBean createFromParcel(Parcel source) {
            return new ClaimTicketResponseNewBean(source);
        }

        @Override
        public ClaimTicketResponseNewBean[] newArray(int size) {
            return new ClaimTicketResponseNewBean[size];
        }
    };
}
