package com.skilrock.retailapp.models.scratch;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReturnChallanResponseBean implements Parcelable {
    public static final Parcelable.Creator<ReturnChallanResponseBean> CREATOR = new Parcelable.Creator<ReturnChallanResponseBean>() {
        @Override
        public ReturnChallanResponseBean createFromParcel(Parcel source) {
            return new ReturnChallanResponseBean(source);
        }

        @Override
        public ReturnChallanResponseBean[] newArray(int size) {
            return new ReturnChallanResponseBean[size];
        }
    };
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("response")
    @Expose
    private Response response;

    public ReturnChallanResponseBean() {
    }

    protected ReturnChallanResponseBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.response = in.readParcelable(Response.class.getClassLoader());
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

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReturnChallanResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", response=" + response + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.responseCode);
        dest.writeString(this.responseMessage);
        dest.writeParcelable(this.response, flags);
    }

    public static class Response implements Parcelable {
        public static final Creator<Response> CREATOR = new Creator<Response>() {
            @Override
            public Response createFromParcel(Parcel source) {
                return new Response(source);
            }

            @Override
            public Response[] newArray(int size) {
                return new Response[size];
            }
        };
        @SerializedName("gameDetails")
        @Expose
        private ArrayList<GameDetail> gameDetails = null;

        public Response() {
        }

        protected Response(Parcel in) {
            this.gameDetails = new ArrayList<GameDetail>();
            in.readList(this.gameDetails, GameDetail.class.getClassLoader());
        }

        public ArrayList<GameDetail> getGameDetails() {
            return gameDetails;
        }

        public void setGameDetails(ArrayList<GameDetail> gameDetails) {
            this.gameDetails = gameDetails;
        }

        @NonNull
        @Override
        public String toString() {
            return "Response{" + "gameDetails=" + gameDetails + '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.gameDetails);
        }

        public static class GameDetail implements Parcelable {
            public static final Creator<GameDetail> CREATOR = new Creator<GameDetail>() {
                @Override
                public GameDetail createFromParcel(Parcel source) {
                    return new GameDetail(source);
                }

                @Override
                public GameDetail[] newArray(int size) {
                    return new GameDetail[size];
                }
            };
            @SerializedName("gameId")
            @Expose
            private Integer gameId;
            @SerializedName("bookList")
            @Expose
            private ArrayList<String> bookList = null;

            public GameDetail() {
            }

            protected GameDetail(Parcel in) {
                this.gameId = (Integer) in.readValue(Integer.class.getClassLoader());
                this.bookList = in.createStringArrayList();
            }

            public Integer getGameId() {
                return gameId;
            }

            public void setGameId(Integer gameId) {
                this.gameId = gameId;
            }

            public ArrayList<String> getBookList() {
                return bookList;
            }

            public void setBookList(ArrayList<String> bookList) {
                this.bookList = bookList;
            }

            @NonNull
            @Override
            public String toString() {
                return "GameDetail{" + "gameId=" + gameId + ", bookList=" + bookList + '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(this.gameId);
                dest.writeStringList(this.bookList);
            }
        }
    }
}