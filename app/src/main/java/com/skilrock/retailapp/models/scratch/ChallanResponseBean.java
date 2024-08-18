package com.skilrock.retailapp.models.scratch;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChallanResponseBean implements Parcelable {

    @SerializedName("dlChallanId")
    @Expose
    private String dlChallanId;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("dlChallanNumber")
    @Expose
    private String dlChallanNumber;
    @SerializedName("dlDate")
    @Expose
    private String dlDate;
    @SerializedName("dlStatus")
    @Expose
    private String dlStatus;
    @SerializedName("fromOrg")
    @Expose
    private String fromOrg;
    @SerializedName("toOrg")
    @Expose
    private String toOrg;
    @SerializedName("generatedBy")
    @Expose
    private String generatedBy;
    @SerializedName("gameWiseDetails")
    @Expose
    private ArrayList<GameWiseDetail> gameWiseDetails = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getDlChallanId() {
        return dlChallanId;
    }

    public void setDlChallanId(String dlChallanId) {
        this.dlChallanId = dlChallanId;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getDlChallanNumber() {
        return dlChallanNumber;
    }

    public void setDlChallanNumber(String dlChallanNumber) {
        this.dlChallanNumber = dlChallanNumber;
    }

    public String getDlDate() {
        return dlDate;
    }

    public void setDlDate(String dlDate) {
        this.dlDate = dlDate;
    }

    public String getDlStatus() {
        return dlStatus;
    }

    public void setDlStatus(String dlStatus) {
        this.dlStatus = dlStatus;
    }

    public String getFromOrg() {
        return fromOrg;
    }

    public void setFromOrg(String fromOrg) {
        this.fromOrg = fromOrg;
    }

    public String getToOrg() {
        return toOrg;
    }

    public void setToOrg(String toOrg) {
        this.toOrg = toOrg;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public ArrayList<GameWiseDetail> getGameWiseDetails() {
        return gameWiseDetails;
    }

    public void setGameWiseDetails(ArrayList<GameWiseDetail> gameWiseDetails) {
        this.gameWiseDetails = gameWiseDetails;
    }

    @Override
    public String toString() {
        return "ChallanResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", dlChallanNumber='" + dlChallanNumber + '\'' + ", dlDate='" + dlDate + '\'' + ", dlStatus='" + dlStatus + '\'' + ", fromOrg='" + fromOrg + '\'' + ", toOrg='" + toOrg + '\'' + ", generatedBy='" + generatedBy + '\'' + ", gameWiseDetails=" + gameWiseDetails + '}';
    }

    public static class GameWiseDetail implements Parcelable {

        @SerializedName("gameId")
        @Expose
        private Integer gameId;
        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("gameNumber")
        @Expose
        private Integer gameNumber;
        @SerializedName("bookList")
        @Expose
        private ArrayList<BookList> bookList = null;

        private int scannedBooks = 0;

        public int getScannedBooks() {
            return scannedBooks;
        }

        public void setScannedBooks(int scannedBooks) {
            this.scannedBooks = scannedBooks;
        }

        public Integer getGameNumber() {
            return gameNumber;
        }

        public void setGameNumber(Integer gameNumber) {
            this.gameNumber = gameNumber;
        }

        public Integer getGameId() {
            return gameId;
        }

        public void setGameId(Integer gameId) {
            this.gameId = gameId;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public ArrayList<BookList> getBookList() {
            return bookList;
        }

        public void setBookList(ArrayList<BookList> bookList) {
            this.bookList = bookList;
        }

        @Override
        public String toString() {
            return "GameWiseDetail{" + "gameId=" + gameId + ", gameName='" + gameName + '\'' + ", gameNumber=" + gameNumber + ", bookList=" + bookList + '}';
        }

        public static class BookList implements Parcelable {

            @SerializedName("bookNumber")
            @Expose
            private String bookNumber;

            @SerializedName("status")
            @Expose
            private String status;

            private boolean isChecked;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getBookNumber() {
                return bookNumber;
            }

            public void setBookNumber(String bookNumber) {
                this.bookNumber = bookNumber;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            @NonNull
            @Override
            public String toString() {
                return "BookList{" + "bookNumber='" + bookNumber + '\'' + ", status='" + status + '\'' + ", isChecked=" + isChecked + '}';
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.bookNumber);
                dest.writeString(this.status);
                dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
            }

            public BookList() {
            }

            protected BookList(Parcel in) {
                this.bookNumber = in.readString();
                this.status = in.readString();
                this.isChecked = in.readByte() != 0;
            }

            public static final Creator<BookList> CREATOR = new Creator<BookList>() {
                @Override
                public BookList createFromParcel(Parcel source) {
                    return new BookList(source);
                }

                @Override
                public BookList[] newArray(int size) {
                    return new BookList[size];
                }
            };
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.gameId);
            dest.writeString(this.gameName);
            dest.writeValue(this.gameNumber);
            dest.writeTypedList(this.bookList);
            dest.writeInt(this.scannedBooks);
        }

        public GameWiseDetail() {
        }

        protected GameWiseDetail(Parcel in) {
            this.gameId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.gameName = in.readString();
            this.gameNumber = (Integer) in.readValue(Integer.class.getClassLoader());
            this.bookList = in.createTypedArrayList(BookList.CREATOR);
            this.scannedBooks = in.readInt();
        }

        public static final Creator<GameWiseDetail> CREATOR = new Creator<GameWiseDetail>() {
            @Override
            public GameWiseDetail createFromParcel(Parcel source) {
                return new GameWiseDetail(source);
            }

            @Override
            public GameWiseDetail[] newArray(int size) {
                return new GameWiseDetail[size];
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
        dest.writeString(this.dlChallanNumber);
        dest.writeString(this.dlDate);
        dest.writeString(this.dlStatus);
        dest.writeString(this.fromOrg);
        dest.writeString(this.toOrg);
        dest.writeString(this.generatedBy);
        dest.writeList(this.gameWiseDetails);
    }

    public ChallanResponseBean() {
    }

    protected ChallanResponseBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.dlChallanNumber = in.readString();
        this.dlDate = in.readString();
        this.dlStatus = in.readString();
        this.fromOrg = in.readString();
        this.toOrg = in.readString();
        this.generatedBy = in.readString();
        this.gameWiseDetails = new ArrayList<GameWiseDetail>();
        in.readList(this.gameWiseDetails, GameWiseDetail.class.getClassLoader());
    }

    public static final Creator<ChallanResponseBean> CREATOR = new Creator<ChallanResponseBean>() {
        @Override
        public ChallanResponseBean createFromParcel(Parcel source) {
            return new ChallanResponseBean(source);
        }

        @Override
        public ChallanResponseBean[] newArray(int size) {
            return new ChallanResponseBean[size];
        }
    };
}
