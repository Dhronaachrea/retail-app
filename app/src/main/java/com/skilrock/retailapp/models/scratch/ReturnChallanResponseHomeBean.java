package com.skilrock.retailapp.models.scratch;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnChallanResponseHomeBean implements Parcelable {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("dlChallanId")
    @Expose
    private Integer dlChallanId;

    @SerializedName("dlChallanNumber")
    @Expose
    private String dlChallanNumber;

    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    @SerializedName("retailerName")
    @Expose
    private String retailerName;

    @SerializedName("games")
    @Expose
    private ArrayList<Game> games = null;

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

    public Integer getDlChallanId() {
        return dlChallanId;
    }

    public void setDlChallanId(Integer dlChallanId) {
        this.dlChallanId = dlChallanId;
    }

    public String getDlChallanNumber() {
        return dlChallanNumber;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public void setDlChallanNumber(String dlChallanNumber) {
        this.dlChallanNumber = dlChallanNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public static class Game implements Parcelable {

        @SerializedName("gameId")
        @Expose
        private Integer gameId;

        @SerializedName("gameNumber")
        @Expose
        private Integer gameNumber;

        @SerializedName("booksQuantity")
        @Expose
        private Integer booksQuantity;

        @SerializedName("gameName")
        @Expose
        private String gameName;

        @SerializedName("ticketsQuantity")
        @Expose
        private Integer ticketsQuantity;

        @SerializedName("ticketsPerBooks")
        @Expose
        private Integer ticketsPerBooks;

        public Integer getGameId() {
            return gameId;
        }

        public void setGameId(Integer gameId) {
            this.gameId = gameId;
        }

        public Integer getGameNumber() {
            return gameNumber;
        }

        public void setGameNumber(Integer gameNumber) {
            this.gameNumber = gameNumber;
        }

        public Integer getBooksQuantity() {
            return booksQuantity;
        }

        public void setBooksQuantity(Integer booksQuantity) {
            this.booksQuantity = booksQuantity;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public Integer getTicketsQuantity() {
            return ticketsQuantity;
        }

        public void setTicketsQuantity(Integer ticketsQuantity) {
            this.ticketsQuantity = ticketsQuantity;
        }

        public Integer getTicketsPerBooks() {
            return ticketsPerBooks;
        }

        public void setTicketsPerBooks(Integer ticketsPerBooks) {
            this.ticketsPerBooks = ticketsPerBooks;
        }

        @NonNull
        @Override
        public String toString() {
            return "Game{" + "gameId=" + gameId + ", gameNumber=" + gameNumber + ", booksQuantity=" + booksQuantity + ", gameName='" + gameName + '\'' + ", ticketsQuantity=" + ticketsQuantity + ", ticketsPerBooks=" + ticketsPerBooks + '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.gameId);
            dest.writeValue(this.gameNumber);
            dest.writeValue(this.booksQuantity);
            dest.writeString(this.gameName);
            dest.writeValue(this.ticketsQuantity);
            dest.writeValue(this.ticketsPerBooks);
        }

        public Game() {
        }

        protected Game(Parcel in) {
            this.gameId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.gameNumber = (Integer) in.readValue(Integer.class.getClassLoader());
            this.booksQuantity = (Integer) in.readValue(Integer.class.getClassLoader());
            this.gameName = in.readString();
            this.ticketsQuantity = (Integer) in.readValue(Integer.class.getClassLoader());
            this.ticketsPerBooks = (Integer) in.readValue(Integer.class.getClassLoader());
        }

        public static final Creator<Game> CREATOR = new Creator<Game>() {
            @Override
            public Game createFromParcel(Parcel source) {
                return new Game(source);
            }

            @Override
            public Game[] newArray(int size) {
                return new Game[size];
            }
        };
    }

    @NonNull
    @Override
    public String toString() {
        return "ReturnChallanResponseHomeBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", retailerName=" + retailerName+ ", dlChallanId=" + dlChallanId + ", dlChallanNumber='" + dlChallanNumber + '\'' + ", dateTime='" + dateTime + '\'' + ", games=" + games + '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.responseCode);
        dest.writeString(this.responseMessage);
        dest.writeValue(this.dlChallanId);
        dest.writeString(this.dlChallanNumber);
        dest.writeString(this.dateTime);
        dest.writeList(this.games);
    }

    public ReturnChallanResponseHomeBean() {
    }

    protected ReturnChallanResponseHomeBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.dlChallanId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dlChallanNumber = in.readString();
        this.dateTime = in.readString();
        this.games = new ArrayList<Game>();
        in.readList(this.games, Game.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReturnChallanResponseHomeBean> CREATOR = new Parcelable.Creator<ReturnChallanResponseHomeBean>() {
        @Override
        public ReturnChallanResponseHomeBean createFromParcel(Parcel source) {
            return new ReturnChallanResponseHomeBean(source);
        }

        @Override
        public ReturnChallanResponseHomeBean[] newArray(int size) {
            return new ReturnChallanResponseHomeBean[size];
        }
    };
}