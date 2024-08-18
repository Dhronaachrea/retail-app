package com.skilrock.retailapp.models.drawgames;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResultResponseBean implements Parcelable {

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


        @SerializedName("gameCode")
        @Expose
        private String gameCode;
        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("lastDrawId")
        @Expose
        private Integer lastDrawId;
        @SerializedName("resultData")
        @Expose
        private List<ResultDatum> resultData = null;

        public String getGameCode() {
            return gameCode;
        }

        public void setGameCode(String gameCode) {
            this.gameCode = gameCode;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public Integer getLastDrawId() {
            return lastDrawId;
        }

        public void setLastDrawId(Integer lastDrawId) {
            this.lastDrawId = lastDrawId;
        }

        public List<ResultDatum> getResultData() {
            return resultData;
        }

        public void setResultData(List<ResultDatum> resultData) {
            this.resultData = resultData;
        }

        public static class ResultDatum implements Parcelable {


            @SerializedName("resultDate")
            @Expose
            private String resultDate;
            @SerializedName("resultInfo")
            @Expose
            private List<ResultInfo> resultInfo = null;

            public String getResultDate() {
                return resultDate;
            }

            public void setResultDate(String resultDate) {
                this.resultDate = resultDate;
            }

            public List<ResultInfo> getResultInfo() {
                return resultInfo;
            }

            public void setResultInfo(List<ResultInfo> resultInfo) {
                this.resultInfo = resultInfo;
            }


            public static class ResultInfo implements Parcelable {

                @SerializedName("drawName")
                @Expose
                private String drawName;
                @SerializedName("drawTime")
                @Expose
                private String drawTime;
                @SerializedName("winningNo")
                @Expose
                private String winningNo;
                @SerializedName("winningMultiplierInfo")
                @Expose
                private WinningMultiplierInfo winningMultiplierInfo;
                @SerializedName("runTimeFlagInfo")
                @Expose
                private List<RunTimeFlagInfo> runTimeFlagInfo = null;
                @SerializedName("matchInfo")
                @Expose
                private List<MatchInfo> matchInfo = null;
                @SerializedName("sideBetMatchInfo")
                @Expose
                private List<SideBetMatchInfo> sideBetMatchInfo = null;

                public String getDrawName() {
                    return drawName;
                }

                public void setDrawName(String drawName) {
                    this.drawName = drawName;
                }

                public String getDrawTime() {
                    return drawTime;
                }

                public void setDrawTime(String drawTime) {
                    this.drawTime = drawTime;
                }

                public String getWinningNo() {
                    return winningNo;
                }

                public void setWinningNo(String winningNo) {
                    this.winningNo = winningNo;
                }

                public WinningMultiplierInfo getWinningMultiplierInfo() {
                    return winningMultiplierInfo;
                }

                public void setWinningMultiplierInfo(WinningMultiplierInfo winningMultiplierInfo) {
                    this.winningMultiplierInfo = winningMultiplierInfo;
                }

                public List<RunTimeFlagInfo> getRunTimeFlagInfo() {
                    return runTimeFlagInfo;
                }

                public void setRunTimeFlagInfo(List<RunTimeFlagInfo> runTimeFlagInfo) {
                    this.runTimeFlagInfo = runTimeFlagInfo;
                }

                public List<MatchInfo> getMatchInfo() {
                    return matchInfo;
                }

                public void setMatchInfo(List<MatchInfo> matchInfo) {
                    this.matchInfo = matchInfo;
                }

                public List<SideBetMatchInfo> getSideBetMatchInfo() {
                    return sideBetMatchInfo;
                }

                public void setSideBetMatchInfo(List<SideBetMatchInfo> sideBetMatchInfo) {
                    this.sideBetMatchInfo = sideBetMatchInfo;
                }


                public static class MatchInfo implements Parcelable {

                    @SerializedName("match")
                    @Expose
                    private String match;
                    @SerializedName("noOfWinners")
                    @Expose
                    private String noOfWinners;
                    @SerializedName("amount")
                    @Expose
                    private String amount;
                    @SerializedName("prizeRank")
                    @Expose
                    private Integer prizeRank;
                    @SerializedName("mode")
                    @Expose
                    private String mode;

                    public String getMatch() {
                        return match;
                    }

                    public void setMatch(String match) {
                        this.match = match;
                    }

                    public String getNoOfWinners() {
                        return noOfWinners;
                    }

                    public void setNoOfWinners(String noOfWinners) {
                        this.noOfWinners = noOfWinners;
                    }

                    public String getAmount() {
                        return amount;
                    }

                    public void setAmount(String amount) {
                        this.amount = amount;
                    }

                    public Integer getPrizeRank() {
                        return prizeRank;
                    }

                    public void setPrizeRank(Integer prizeRank) {
                        this.prizeRank = prizeRank;
                    }

                    public String getMode() {
                        return mode;
                    }

                    public void setMode(String mode) {
                        this.mode = mode;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.match);
                        dest.writeString(this.noOfWinners);
                        dest.writeString(this.amount);
                        dest.writeValue(this.prizeRank);
                        dest.writeString(this.mode);
                    }

                    public MatchInfo() {
                    }

                    protected MatchInfo(Parcel in) {
                        this.match = in.readString();
                        this.noOfWinners = in.readString();
                        this.amount = in.readString();
                        this.prizeRank = (Integer) in.readValue(Integer.class.getClassLoader());
                        this.mode = in.readString();
                    }

                    public static final Creator<MatchInfo> CREATOR = new Creator<MatchInfo>() {
                        @Override
                        public MatchInfo createFromParcel(Parcel source) {
                            return new MatchInfo(source);
                        }

                        @Override
                        public MatchInfo[] newArray(int size) {
                            return new MatchInfo[size];
                        }
                    };
                }

                public static class RunTimeFlagInfo implements Parcelable {
                    @SerializedName("multiplierCode")
                    @Expose
                    private String multiplierCode;
                    @SerializedName("value")
                    @Expose
                    private Double value;
                    @SerializedName("ballValue")
                    @Expose
                    private Integer ballValue;

                    public String getMultiplierCode() {
                        return multiplierCode;
                    }

                    public void setMultiplierCode(String multiplierCode) {
                        this.multiplierCode = multiplierCode;
                    }

                    public Double getValue() {
                        return value;
                    }

                    public void setValue(Double value) {
                        this.value = value;
                    }

                    public Integer getBallValue() {
                        return ballValue;
                    }

                    public void setBallValue(Integer ballValue) {
                        this.ballValue = ballValue;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.multiplierCode);
                        dest.writeValue(this.value);
                        dest.writeValue(this.ballValue);
                    }

                    public RunTimeFlagInfo() {
                    }

                    protected RunTimeFlagInfo(Parcel in) {
                        this.multiplierCode = in.readString();
                        this.value = (Double) in.readValue(Double.class.getClassLoader());
                        this.ballValue = (Integer) in.readValue(Integer.class.getClassLoader());
                    }

                    public static final Creator<RunTimeFlagInfo> CREATOR = new Creator<RunTimeFlagInfo>() {
                        @Override
                        public RunTimeFlagInfo createFromParcel(Parcel source) {
                            return new RunTimeFlagInfo(source);
                        }

                        @Override
                        public RunTimeFlagInfo[] newArray(int size) {
                            return new RunTimeFlagInfo[size];
                        }
                    };
                }


                    public static class WinningMultiplierInfo implements Parcelable {
                        @SerializedName("multiplierCode")
                        @Expose
                        private String multiplierCode;
                        @SerializedName("value")
                        @Expose
                        private Double value;

                        public String getMultiplierCode() {
                            return multiplierCode;
                        }

                        public void setMultiplierCode(String multiplierCode) {
                            this.multiplierCode = multiplierCode;
                        }

                        public Double getValue() {
                            return value;
                        }

                        public void setValue(Double value) {
                            this.value = value;
                        }

                        @Override
                        public int describeContents() {
                            return 0;
                        }

                        @Override
                        public void writeToParcel(Parcel dest, int flags) {
                            dest.writeString(this.multiplierCode);
                            dest.writeValue(this.value);
                        }

                        public WinningMultiplierInfo() {
                        }

                        protected WinningMultiplierInfo(Parcel in) {
                            this.multiplierCode = in.readString();
                            this.value = (Double) in.readValue(Double.class.getClassLoader());
                        }

                        public static final Creator<WinningMultiplierInfo> CREATOR = new Creator<WinningMultiplierInfo>() {
                            @Override
                            public WinningMultiplierInfo createFromParcel(Parcel source) {
                                return new WinningMultiplierInfo(source);
                            }

                            @Override
                            public WinningMultiplierInfo[] newArray(int size) {
                                return new WinningMultiplierInfo[size];
                            }
                        };
                    }

                public static class SideBetMatchInfo implements Parcelable {
                    @SerializedName("betDisplayName")
                    @Expose
                    private String betDisplayName;
                    @SerializedName("betCode")
                    @Expose
                    private String betCode;
                    @SerializedName("result")
                    @Expose
                    private String result;
                    @SerializedName("rank")
                    @Expose
                    private Integer rank;
                    @SerializedName("pickTypeName")
                    @Expose
                    private String pickTypeName;
                    @SerializedName("pickTypeCode")
                    @Expose
                    private String pickTypeCode;

                    public String getBetDisplayName() {
                        return betDisplayName;
                    }

                    public void setBetDisplayName(String betDisplayName) {
                        this.betDisplayName = betDisplayName;
                    }

                    public String getBetCode() {
                        return betCode;
                    }

                    public void setBetCode(String betCode) {
                        this.betCode = betCode;
                    }

                    public String getResult() {
                        return result;
                    }

                    public void setResult(String result) {
                        this.result = result;
                    }

                    public Integer getRank() {
                        return rank;
                    }

                    public void setRank(Integer rank) {
                        this.rank = rank;
                    }

                    public String getPickTypeName() {
                        return pickTypeName;
                    }

                    public void setPickTypeName(String pickTypeName) {
                        this.pickTypeName = pickTypeName;
                    }

                    public String getPickTypeCode() {
                        return pickTypeCode;
                    }

                    public void setPickTypeCode(String pickTypeCode) {
                        this.pickTypeCode = pickTypeCode;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.betDisplayName);
                        dest.writeString(this.betCode);
                        dest.writeString(this.result);
                        dest.writeValue(this.rank);
                        dest.writeString(this.pickTypeName);
                        dest.writeString(this.pickTypeCode);
                    }

                    public SideBetMatchInfo() {
                    }

                    protected SideBetMatchInfo(Parcel in) {
                        this.betDisplayName = in.readString();
                        this.betCode = in.readString();
                        this.result = in.readString();
                        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
                        this.pickTypeName = in.readString();
                        this.pickTypeCode = in.readString();
                    }

                    public static final Creator<SideBetMatchInfo> CREATOR = new Creator<SideBetMatchInfo>() {
                        @Override
                        public SideBetMatchInfo createFromParcel(Parcel source) {
                            return new SideBetMatchInfo(source);
                        }

                        @Override
                        public SideBetMatchInfo[] newArray(int size) {
                            return new SideBetMatchInfo[size];
                        }
                    };
                }

                public ResultInfo() {
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.drawName);
                    dest.writeString(this.drawTime);
                    dest.writeString(this.winningNo);
                    dest.writeParcelable(this.winningMultiplierInfo, flags);
                    dest.writeList(this.runTimeFlagInfo);
                    dest.writeTypedList(this.matchInfo);
                    dest.writeList(this.sideBetMatchInfo);
                }

                protected ResultInfo(Parcel in) {
                    this.drawName = in.readString();
                    this.drawTime = in.readString();
                    this.winningNo = in.readString();
                    this.winningMultiplierInfo = in.readParcelable(WinningMultiplierInfo.class.getClassLoader());
                    this.runTimeFlagInfo = new ArrayList<RunTimeFlagInfo>();
                    in.readList(this.runTimeFlagInfo, RunTimeFlagInfo.class.getClassLoader());
                    this.matchInfo = in.createTypedArrayList(MatchInfo.CREATOR);
                    this.sideBetMatchInfo = new ArrayList<SideBetMatchInfo>();
                    in.readList(this.sideBetMatchInfo, SideBetMatchInfo.class.getClassLoader());
                }

                public static final Creator<ResultInfo> CREATOR = new Creator<ResultInfo>() {
                    @Override
                    public ResultInfo createFromParcel(Parcel source) {
                        return new ResultInfo(source);
                    }

                    @Override
                    public ResultInfo[] newArray(int size) {
                        return new ResultInfo[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.resultDate);
                dest.writeList(this.resultInfo);
            }

            public ResultDatum() {
            }

            protected ResultDatum(Parcel in) {
                this.resultDate = in.readString();
                this.resultInfo = new ArrayList<ResultInfo>();
                in.readList(this.resultInfo, ResultInfo.class.getClassLoader());
            }

            public static final Creator<ResultDatum> CREATOR = new Creator<ResultDatum>() {
                @Override
                public ResultDatum createFromParcel(Parcel source) {
                    return new ResultDatum(source);
                }

                @Override
                public ResultDatum[] newArray(int size) {
                    return new ResultDatum[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.gameCode);
            dest.writeString(this.gameName);
            dest.writeValue(this.lastDrawId);
            dest.writeList(this.resultData);
        }

        public ResponseDatum() {
        }

        protected ResponseDatum(Parcel in) {
            this.gameCode = in.readString();
            this.gameName = in.readString();
            this.lastDrawId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.resultData = new ArrayList<ResultDatum>();
            in.readList(this.resultData, ResultDatum.class.getClassLoader());
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

    public ResultResponseBean() {
    }

    protected ResultResponseBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.responseData = new ArrayList<ResponseDatum>();
        in.readList(this.responseData, ResponseDatum.class.getClassLoader());
    }

    public static final Parcelable.Creator<ResultResponseBean> CREATOR = new Parcelable.Creator<ResultResponseBean>() {
        @Override
        public ResultResponseBean createFromParcel(Parcel source) {
            return new ResultResponseBean(source);
        }

        @Override
        public ResultResponseBean[] newArray(int size) {
            return new ResultResponseBean[size];
        }
    };
}





