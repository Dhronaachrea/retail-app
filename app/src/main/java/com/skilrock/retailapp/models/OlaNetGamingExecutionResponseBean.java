package com.skilrock.retailapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OlaNetGamingExecutionResponseBean implements Parcelable {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseData")
    @Expose
    private List<ResponseDatum> responseData = null;

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

    public List<ResponseDatum> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<ResponseDatum> responseData) {
        this.responseData = responseData;
    }


    public static class ResponseDatum implements Parcelable {

        @SerializedName("fromDate")
        @Expose
        private String fromDate;
        @SerializedName("toDate")
        @Expose
        private String toDate;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("last")
        @Expose
        private Boolean last;
        public boolean isSelected = false;
        private  boolean isTentative  = false;

        public boolean isTentative() {
            return isTentative;
        }

        public void setTentative(boolean tentative) {
            isTentative = tentative;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getLast() {
            return last;
        }

        public void setLast(Boolean last) {
            this.last = last;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.fromDate);
            dest.writeString(this.toDate);
            dest.writeString(this.description);
            dest.writeValue(this.last);
            dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isTentative ? (byte) 1 : (byte) 0);
        }

        public ResponseDatum() {
        }

        protected ResponseDatum(Parcel in) {
            this.fromDate = in.readString();
            this.toDate = in.readString();
            this.description = in.readString();
            this.last = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.isSelected = in.readByte() != 0;
            this.isTentative = in.readByte() != 0;
        }

        public static final Creator<ResponseDatum> CREATOR = new Creator<ResponseDatum>() {
            @Override
            public ResponseDatum createFromParcel(Parcel source) {
                return new ResponseDatum(source);
            }

            @Override
            public ResponseDatum[] newArray(int size) {
                return new ResponseDatum[size];
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
        dest.writeList(this.responseData);
    }

    public OlaNetGamingExecutionResponseBean() {
    }

    protected OlaNetGamingExecutionResponseBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.responseData = new ArrayList<ResponseDatum>();
        in.readList(this.responseData, ResponseDatum.class.getClassLoader());
    }

    public static final Parcelable.Creator<OlaNetGamingExecutionResponseBean> CREATOR = new Parcelable.Creator<OlaNetGamingExecutionResponseBean>() {
        @Override
        public OlaNetGamingExecutionResponseBean createFromParcel(Parcel source) {
            return new OlaNetGamingExecutionResponseBean(source);
        }

        @Override
        public OlaNetGamingExecutionResponseBean[] newArray(int size) {
            return new OlaNetGamingExecutionResponseBean[size];
        }
    };
}