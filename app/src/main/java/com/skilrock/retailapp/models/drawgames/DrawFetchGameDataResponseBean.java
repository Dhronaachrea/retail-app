package com.skilrock.retailapp.models.drawgames;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DrawFetchGameDataResponseBean implements Parcelable {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

    @SerializedName("updatingGameCode")
    @Expose
    private String updatingGameCode;

    public String getUpdatingGameCode() {
        return updatingGameCode;
    }

    public void setUpdatingGameCode(String updatingGameCode) {
        this.updatingGameCode = updatingGameCode;
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

        @SerializedName("gameRespVOs")
        @Expose
        private ArrayList<GameRespVO> gameRespVOs = null;

        @SerializedName("currentDate")
        @Expose
        private String currentDate;

        public ArrayList<GameRespVO> getGameRespVOs() {
            return gameRespVOs;
        }

        public void setGameRespVOs(ArrayList<GameRespVO> gameRespVOs) {
            this.gameRespVOs = gameRespVOs;
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public static class GameRespVO implements Parcelable {

            @SerializedName("id")
            @Expose
            private Integer id;

            @SerializedName("gameNumber")
            @Expose
            private Integer gameNumber;

            @SerializedName("gameName")
            @Expose
            private String gameName;

            @SerializedName("gameCode")
            @Expose
            private String gameCode;

            @SerializedName("familyCode")
            @Expose
            private String familyCode;

            @SerializedName("lastDrawResult")
            @Expose
            private String lastDrawResult;

            @SerializedName("displayOrder")
            @Expose
            private String displayOrder;

            @SerializedName("drawFrequencyType")
            @Expose
            private String drawFrequencyType;

            @SerializedName("timeToFetchUpdatedGameInfo")
            @Expose
            private String timeToFetchUpdatedGameInfo;

            @SerializedName("numberConfig")
            @Expose
            private NumberConfig numberConfig;

            @SerializedName("runTimeFlag")
            @Expose
            private RunTimeFlag runTimeFlag;

            @SerializedName("betRespVOs")
            @Expose
            private ArrayList<BetRespVO> betRespVOs = null;

            @SerializedName("drawRespVOs")
            @Expose
            private ArrayList<DrawRespVO> drawRespVOs = null;

            @SerializedName("nativeCurrency")
            @Expose
            private String nativeCurrency;

            @SerializedName("drawEvent")
            @Expose
            private String drawEvent;

            @SerializedName("gameStatus")
            @Expose
            private String gameStatus;

            @SerializedName("gameOrder")
            @Expose
            private String gameOrder;

            @SerializedName("consecutiveDraw")
            @Expose
            private String consecutiveDraw;

            @SerializedName("maxAdvanceDraws")
            @Expose
            private Integer maxAdvanceDraws;

            @SerializedName("drawPrizeMultiplier")
            @Expose
            private DrawPrizeMultiplier drawPrizeMultiplier;

            @SerializedName("lastDrawFreezeTime")
            @Expose
            private String lastDrawFreezeTime;

            @SerializedName("lastDrawDateTime")
            @Expose
            private String lastDrawDateTime;

            @SerializedName("lastDrawSaleStopTime")
            @Expose
            private String lastDrawSaleStopTime;

            @SerializedName("lastDrawTime")
            @Expose
            private String lastDrawTime;

            @SerializedName("ticket_expiry")
            @Expose
            private Integer ticketExpiry;

            @SerializedName("maxPanelAllowed")
            @Expose
            private Integer maxPanelAllowed;

            @SerializedName("currencyRespVOs")
            @Expose
            private ArrayList<CurrencyRespVO> currencyRespVOs = null;

            @SerializedName("lastDrawWinningResultVOs")
            @Expose
            private ArrayList<LastDrawWinningResultVO> lastDrawWinningResultVOs = null;

            @SerializedName("hotNumbers")
            @Expose
            private String hotNumbers;

            @SerializedName("coldNumbers")
            @Expose
            private String coldNumbers;

            @SerializedName("gameSchemas")
            @Expose
            private GameSchemas gameSchemas;

            @SerializedName("isSelected")
            @Expose
            private boolean isSelected;

            @SerializedName("isCountDownStarted")
            @Expose
            private boolean isCountDownStarted;

            @SerializedName("currentDate")
            @Expose
            private String currentDate;

            public String getCurrentDate() {
                return currentDate;
            }

            public void setCurrentDate(String currentDate) {
                this.currentDate = currentDate;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public boolean isCountDownStarted() {
                return isCountDownStarted;
            }

            public void setCountDownStarted(boolean countDownStarted) {
                isCountDownStarted = countDownStarted;
            }

            public GameSchemas getGameSchemas() {
                return gameSchemas;
            }

            public void setGameSchemas(GameSchemas gameSchemas) {
                this.gameSchemas = gameSchemas;
            }

            public Integer getMaxPanelAllowed() {
                return maxPanelAllowed;
            }

            public void setMaxPanelAllowed(Integer maxPanelAllowed) {
                this.maxPanelAllowed = maxPanelAllowed;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getGameNumber() {
                return gameNumber;
            }

            public void setGameNumber(Integer gameNumber) {
                this.gameNumber = gameNumber;
            }

            public String getGameName() {
                return gameName;
            }

            public void setGameName(String gameName) {
                this.gameName = gameName;
            }

            public String getGameCode() {
                return gameCode;
            }

            public void setGameCode(String gameCode) {
                this.gameCode = gameCode;
            }

            public String getFamilyCode() {
                return familyCode;
            }

            public void setFamilyCode(String familyCode) {
                this.familyCode = familyCode;
            }

            public String getLastDrawResult() {
                return lastDrawResult;
            }

            public void setLastDrawResult(String lastDrawResult) {
                this.lastDrawResult = lastDrawResult;
            }

            public String getDisplayOrder() {
                return displayOrder;
            }

            public void setDisplayOrder(String displayOrder) {
                this.displayOrder = displayOrder;
            }

            public String getDrawFrequencyType() {
                return drawFrequencyType;
            }

            public void setDrawFrequencyType(String drawFrequencyType) {
                this.drawFrequencyType = drawFrequencyType;
            }

            public String getTimeToFetchUpdatedGameInfo() {
                return timeToFetchUpdatedGameInfo;
            }

            public void setTimeToFetchUpdatedGameInfo(String timeToFetchUpdatedGameInfo) {
                this.timeToFetchUpdatedGameInfo = timeToFetchUpdatedGameInfo;
            }

            public NumberConfig getNumberConfig() {
                return numberConfig;
            }

            public void setNumberConfig(NumberConfig numberConfig) {
                this.numberConfig = numberConfig;
            }

            public RunTimeFlag getRunTimeFlag() {
                return runTimeFlag;
            }

            public void setRunTimeFlag(RunTimeFlag runTimeFlag) {
                this.runTimeFlag = runTimeFlag;
            }

            public ArrayList<BetRespVO> getBetRespVOs() {
                return betRespVOs;
            }

            public void setBetRespVOs(ArrayList<BetRespVO> betRespVOs) {
                this.betRespVOs = betRespVOs;
            }

            public ArrayList<DrawRespVO> getDrawRespVOs() {
                return drawRespVOs;
            }

            public void setDrawRespVOs(ArrayList<DrawRespVO> drawRespVOs) {
                this.drawRespVOs = drawRespVOs;
            }

            public String getNativeCurrency() {
                return nativeCurrency;
            }

            public void setNativeCurrency(String nativeCurrency) {
                this.nativeCurrency = nativeCurrency;
            }

            public String getDrawEvent() {
                return drawEvent;
            }

            public void setDrawEvent(String drawEvent) {
                this.drawEvent = drawEvent;
            }

            public String getGameStatus() {
                return gameStatus;
            }

            public void setGameStatus(String gameStatus) {
                this.gameStatus = gameStatus;
            }

            public String getGameOrder() {
                return gameOrder;
            }

            public void setGameOrder(String gameOrder) {
                this.gameOrder = gameOrder;
            }

            public String getConsecutiveDraw() {
                return consecutiveDraw;
            }

            public void setConsecutiveDraw(String consecutiveDraw) {
                this.consecutiveDraw = consecutiveDraw;
            }

            public Integer getMaxAdvanceDraws() {
                return maxAdvanceDraws;
            }

            public void setMaxAdvanceDraws(Integer maxAdvanceDraws) {
                this.maxAdvanceDraws = maxAdvanceDraws;
            }

            public DrawPrizeMultiplier getDrawPrizeMultiplier() {
                return drawPrizeMultiplier;
            }

            public void setDrawPrizeMultiplier(DrawPrizeMultiplier drawPrizeMultiplier) {
                this.drawPrizeMultiplier = drawPrizeMultiplier;
            }

            public String getLastDrawFreezeTime() {
                return lastDrawFreezeTime;
            }

            public void setLastDrawFreezeTime(String lastDrawFreezeTime) {
                this.lastDrawFreezeTime = lastDrawFreezeTime;
            }

            public String getLastDrawDateTime() {
                return lastDrawDateTime;
            }

            public void setLastDrawDateTime(String lastDrawDateTime) {
                this.lastDrawDateTime = lastDrawDateTime;
            }

            public String getLastDrawSaleStopTime() {
                return lastDrawSaleStopTime;
            }

            public void setLastDrawSaleStopTime(String lastDrawSaleStopTime) {
                this.lastDrawSaleStopTime = lastDrawSaleStopTime;
            }

            public String getLastDrawTime() {
                return lastDrawTime;
            }

            public void setLastDrawTime(String lastDrawTime) {
                this.lastDrawTime = lastDrawTime;
            }

            public Integer getTicketExpiry() {
                return ticketExpiry;
            }

            public void setTicketExpiry(Integer ticketExpiry) {
                this.ticketExpiry = ticketExpiry;
            }

            public ArrayList<CurrencyRespVO> getCurrencyRespVOs() {
                return currencyRespVOs;
            }

            public void setCurrencyRespVOs(ArrayList<CurrencyRespVO> currencyRespVOs) {
                this.currencyRespVOs = currencyRespVOs;
            }

            public ArrayList<LastDrawWinningResultVO> getLastDrawWinningResultVOs() {
                return lastDrawWinningResultVOs;
            }

            public void setLastDrawWinningResultVOs(ArrayList<LastDrawWinningResultVO> lastDrawWinningResultVOs) {
                this.lastDrawWinningResultVOs = lastDrawWinningResultVOs;
            }

            public String getHotNumbers() {
                return hotNumbers;
            }

            public void setHotNumbers(String hotNumbers) {
                this.hotNumbers = hotNumbers;
            }

            public String getColdNumbers() {
                return coldNumbers;
            }

            public void setColdNumbers(String coldNumbers) {
                this.coldNumbers = coldNumbers;
            }

            @NonNull
            @Override
            public String toString() {
                return "GameRespVO{" + "id=" + id + ", gameNumber=" + gameNumber + ", gameName='" + gameName + '\'' + ", gameCode='" + gameCode + '\'' + ", familyCode='" + familyCode + '\'' + ", lastDrawResult='" + lastDrawResult + '\'' + ", displayOrder='" + displayOrder + '\'' + ", drawFrequencyType='" + drawFrequencyType + '\'' + ", timeToFetchUpdatedGameInfo='" + timeToFetchUpdatedGameInfo + '\'' + ", numberConfig=" + numberConfig + ", runTimeFlag=" + runTimeFlag + ", betRespVOs=" + betRespVOs + ", drawRespVOs=" + drawRespVOs + ", nativeCurrency='" + nativeCurrency + '\'' + ", drawEvent='" + drawEvent + '\'' + ", gameStatus='" + gameStatus + '\'' + ", gameOrder='" + gameOrder + '\'' + ", consecutiveDraw='" + consecutiveDraw + '\'' + ", maxAdvanceDraws=" + maxAdvanceDraws + ", drawPrizeMultiplier=" + drawPrizeMultiplier + ", lastDrawFreezeTime='" + lastDrawFreezeTime + '\'' + ", lastDrawDateTime='" + lastDrawDateTime + '\'' + ", lastDrawSaleStopTime='" + lastDrawSaleStopTime + '\'' + ", lastDrawTime='" + lastDrawTime + '\'' + ", ticketExpiry=" + ticketExpiry + ", maxPanelAllowed=" + maxPanelAllowed + ", currencyRespVOs=" + currencyRespVOs + ", lastDrawWinningResultVOs=" + lastDrawWinningResultVOs + ", hotNumbers='" + hotNumbers + '\'' + ", coldNumbers='" + coldNumbers + '\'' + ", gameSchemas=" + gameSchemas + '}';
            }

            public static class GameSchemas implements Parcelable {

                @SerializedName("gameDevName")
                @Expose
                private String gameDevName;

                @SerializedName("matchDetail")
                @Expose
                private ArrayList<MatchDetail> matchDetail = null;

                public String getGameDevName() {
                    return gameDevName;
                }

                public void setGameDevName(String gameDevName) {
                    this.gameDevName = gameDevName;
                }

                public ArrayList<MatchDetail> getMatchDetail() {
                    return matchDetail;
                }

                public void setMatchDetail(ArrayList<MatchDetail> matchDetail) {
                    this.matchDetail = matchDetail;
                }

                @NonNull
                @Override
                public String toString() {
                    return "GameSchemas{" + "gameDevName='" + gameDevName + '\'' + ", matchDetail=" + matchDetail + '}';
                }

                public static class MatchDetail implements Parcelable {

                    @SerializedName("match")
                    @Expose
                    private String match;

                    @SerializedName("rank")
                    @Expose
                    private Integer rank;

                    @SerializedName("type")
                    @Expose
                    private String type;

                    @SerializedName("prizeAmount")
                    @Expose
                    private String prizeAmount;

                    @SerializedName("betType")
                    @Expose
                    private String betType;
                    @SerializedName("winMode")
                    @Expose
                    private String winMode;

                    public String getMatch() {
                        return match;
                    }

                    public void setMatch(String match) {
                        this.match = match;
                    }

                    public Integer getRank() {
                        return rank;
                    }

                    public void setRank(Integer rank) {
                        this.rank = rank;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getPrizeAmount() {
                        return prizeAmount;
                    }

                    public void setPrizeAmount(String prizeAmount) {
                        this.prizeAmount = prizeAmount;
                    }

                    public String getBetType() {
                        return betType;
                    }

                    public void setBetType(String betType) {
                        this.betType = betType;
                    }

                    @NonNull
                    @Override
                    public String toString() {
                        return "MatchDetail{" + "match='" + match + '\'' + ", rank=" + rank + ", type='" + type + '\'' + ", prizeAmount='" + prizeAmount + '\'' + ", betType='" + betType + '\'' + '}';
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.match);
                        dest.writeValue(this.rank);
                        dest.writeString(this.type);
                        dest.writeString(this.prizeAmount);
                        dest.writeString(this.betType);
                        dest.writeString(this.winMode);
                    }

                    public MatchDetail() {
                    }

                    protected MatchDetail(Parcel in) {
                        this.match = in.readString();
                        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
                        this.type = in.readString();
                        this.prizeAmount = in.readString();
                        this.betType = in.readString();
                        this.winMode = in.readString();

                    }

                    public static final Creator<MatchDetail> CREATOR = new Creator<MatchDetail>() {
                        @Override
                        public MatchDetail createFromParcel(Parcel source) {
                            return new MatchDetail(source);
                        }

                        @Override
                        public MatchDetail[] newArray(int size) {
                            return new MatchDetail[size];
                        }
                    };

                    public String getWinMode() {
                        return winMode;
                    }

                    public void setWinMode(String winMode) {
                        this.winMode = winMode;
                    }
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.gameDevName);
                    dest.writeList(this.matchDetail);
                }

                public GameSchemas() {
                }

                protected GameSchemas(Parcel in) {
                    this.gameDevName = in.readString();
                    this.matchDetail = new ArrayList<MatchDetail>();
                    in.readList(this.matchDetail, MatchDetail.class.getClassLoader());
                }

                public static final Creator<GameSchemas> CREATOR = new Creator<GameSchemas>() {
                    @Override
                    public GameSchemas createFromParcel(Parcel source) {
                        return new GameSchemas(source);
                    }

                    @Override
                    public GameSchemas[] newArray(int size) {
                        return new GameSchemas[size];
                    }
                };
            }

            public static class NumberConfig implements Parcelable {

                @SerializedName("range")
                @Expose
                private ArrayList<Range> range = null;

                public ArrayList<Range> getRange() {
                    return range;
                }

                public void setRange(ArrayList<Range> range) {
                    this.range = range;
                }

                public static class Range implements Parcelable {

                    @SerializedName("ball")
                    @Expose
                    private ArrayList<Ball> ball = null;

                    public ArrayList<Ball> getBall() {
                        return ball;
                    }

                    public void setBall(ArrayList<Ball> ball) {
                        this.ball = ball;
                    }

                    public static class Ball implements Parcelable {

                        @SerializedName("color")
                        @Expose
                        private String color;

                        @SerializedName("label")
                        @Expose
                        private String label;

                        @SerializedName("number")
                        @Expose
                        private String number;

                        @SerializedName("selected")
                        @Expose
                        private boolean selected;

                        public boolean isSelected() {
                            return selected;
                        }

                        public void setSelected(boolean selected) {
                            this.selected = selected;
                        }

                        public String getColor() {
                            return color;
                        }

                        public void setColor(String color) {
                            this.color = color;
                        }

                        public String getLabel() {
                            return label;
                        }

                        public void setLabel(String label) {
                            this.label = label;
                        }

                        public String getNumber() {
                            return number;
                        }

                        public void setNumber(String number) {
                            this.number = number;
                        }


                        public Ball() {
                        }

                        @NonNull
                        @Override
                        public String toString() {
                            return "Ball{" + "color='" + color + '\'' + ", label='" + label + '\'' + ", number='" + number + '\'' + '}';
                        }

                        @Override
                        public int describeContents() {
                            return 0;
                        }

                        @Override
                        public void writeToParcel(Parcel dest, int flags) {
                            dest.writeString(this.color);
                            dest.writeString(this.label);
                            dest.writeString(this.number);
                            dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
                        }

                        protected Ball(Parcel in) {
                            this.color = in.readString();
                            this.label = in.readString();
                            this.number = in.readString();
                            this.selected = in.readByte() != 0;
                        }

                        public static final Creator<Ball> CREATOR = new Creator<Ball>() {
                            @Override
                            public Ball createFromParcel(Parcel source) {
                                return new Ball(source);
                            }

                            @Override
                            public Ball[] newArray(int size) {
                                return new Ball[size];
                            }
                        };
                    }


                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeList(this.ball);
                    }

                    public Range() {
                    }

                    protected Range(Parcel in) {
                        this.ball = new ArrayList<Ball>();
                        in.readList(this.ball, Ball.class.getClassLoader());
                    }

                    public static final Creator<Range> CREATOR = new Creator<Range>() {
                        @Override
                        public Range createFromParcel(Parcel source) {
                            return new Range(source);
                        }

                        @Override
                        public Range[] newArray(int size) {
                            return new Range[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeList(this.range);
                }

                public NumberConfig() {
                }

                protected NumberConfig(Parcel in) {
                    this.range = new ArrayList<Range>();
                    in.readList(this.range, Range.class.getClassLoader());
                }

                public static final Creator<NumberConfig> CREATOR = new Creator<NumberConfig>() {
                    @Override
                    public NumberConfig createFromParcel(Parcel source) {
                        return new NumberConfig(source);
                    }

                    @Override
                    public NumberConfig[] newArray(int size) {
                        return new NumberConfig[size];
                    }
                };
            }

            public static class RunTimeFlag implements Parcelable {

                @SerializedName("range")
                @Expose
                private ArrayList<Range> range = null;

                public ArrayList<Range> getRange() {
                    return range;
                }

                public void setRange(ArrayList<Range> range) {
                    this.range = range;
                }

                public static class Range implements Parcelable {

                    @SerializedName("flagCount")
                    @Expose
                    private String flagCount;

                    public String getFlagCount() {
                        return flagCount;
                    }

                    public void setFlagCount(String flagCount) {
                        this.flagCount = flagCount;
                    }


                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.flagCount);
                    }

                    public Range() {
                    }

                    protected Range(Parcel in) {
                        this.flagCount = in.readString();
                    }

                    public static final Creator<Range> CREATOR = new Creator<Range>() {
                        @Override
                        public Range createFromParcel(Parcel source) {
                            return new Range(source);
                        }

                        @Override
                        public Range[] newArray(int size) {
                            return new Range[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeList(this.range);
                }

                public RunTimeFlag() {
                }

                protected RunTimeFlag(Parcel in) {
                    this.range = new ArrayList<Range>();
                    in.readList(this.range, Range.class.getClassLoader());
                }

                public static final Creator<RunTimeFlag> CREATOR = new Creator<RunTimeFlag>() {
                    @Override
                    public RunTimeFlag createFromParcel(Parcel source) {
                        return new RunTimeFlag(source);
                    }

                    @Override
                    public RunTimeFlag[] newArray(int size) {
                        return new RunTimeFlag[size];
                    }
                };
            }

            public static class BetRespVO implements Parcelable {

                @SerializedName("unitPrice")
                @Expose
                private Double unitPrice;
                @SerializedName("maxBetAmtMul")
                @Expose
                private Integer maxBetAmtMul;
                @Expose
                private Integer isSelected;
                @SerializedName("betDispName")
                @Expose
                private String betDispName;
                @SerializedName("betCode")
                @Expose
                private String betCode;
                @SerializedName("betName")
                @Expose
                private String betName;
                @SerializedName("pickTypeData")
                @Expose
                private PickTypeData pickTypeData;
                @SerializedName("inputCount")
                @Expose
                private String inputCount;
                @SerializedName("winMode")
                @Expose
                private String winMode;

                public String getWinMode() {
                    return winMode;
                }

                public void setWinMode(String winMode) {
                    this.winMode = winMode;
                }

                public Double getUnitPrice() {
                    return unitPrice;
                }

                public void setUnitPrice(Double unitPrice) {
                    this.unitPrice = unitPrice;
                }

                public Integer getMaxBetAmtMul() {
                    return maxBetAmtMul;
                }

                public void setMaxBetAmtMul(Integer maxBetAmtMul) {
                    this.maxBetAmtMul = maxBetAmtMul;
                }

                public String getBetDispName() {
                    return betDispName;
                }

                public void setBetDispName(String betDispName) {
                    this.betDispName = betDispName;
                }

                public String getBetCode() {
                    return betCode;
                }

                public void setBetCode(String betCode) {
                    this.betCode = betCode;
                }

                public String getBetName() {
                    return betName;
                }

                public void setBetName(String betName) {
                    this.betName = betName;
                }

                public PickTypeData getPickTypeData() {
                    return pickTypeData;
                }

                public void setPickTypeData(PickTypeData pickTypeData) {
                    this.pickTypeData = pickTypeData;
                }

                public String getInputCount() {
                    return inputCount;
                }

                public void setInputCount(String inputCount) {
                    this.inputCount = inputCount;
                }

                public Integer getIsSelected() {
                    return isSelected;
                }

                public void setIsSelected(Integer isSelected) {
                    this.isSelected = isSelected;
                }

                public static class PickTypeData implements Parcelable {

                    @SerializedName("pickType")
                    @Expose
                    private ArrayList<PickType> pickType = null;

                    public ArrayList<PickType> getPickType() {
                        return pickType;
                    }

                    public void setPickType(ArrayList<PickType> pickType) {
                        this.pickType = pickType;
                    }

                    public static class PickType implements Parcelable {

                        @SerializedName("name")
                        @Expose
                        private String name;
                        @SerializedName("code")
                        @Expose
                        private String code;
                        @Expose
                        private Integer isSelected;
                        @SerializedName("range")
                        @Expose
                        private ArrayList<Range> range = null;
                        @SerializedName("coordinate")
                        @Expose
                        private String coordinate;
                        @SerializedName("description")
                        @Expose
                        private String description;

                        public String getDescription() {
                            return description;
                        }

                        public void setDescription(String description) {
                            this.description = description;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getCode() {
                            return code;
                        }

                        public void setCode(String code) {
                            this.code = code;
                        }

                        public ArrayList<Range> getRange() {
                            return range;
                        }

                        public void setRange(ArrayList<Range> range) {
                            this.range = range;
                        }

                        public String getCoordinate() {
                            return coordinate;
                        }

                        public void setCoordinate(String coordinate) {
                            this.coordinate = coordinate;
                        }

                        public Integer getIsSelected() {
                            return isSelected;
                        }

                        public void setIsSelected(Integer isSelected) {
                            this.isSelected = isSelected;
                        }

                        public static class Range implements Parcelable {

                            @SerializedName("pickMode")
                            @Expose
                            private String pickMode;
                            @SerializedName("pickCount")
                            @Expose
                            private String pickCount;
                            @SerializedName("pickValue")
                            @Expose
                            private String pickValue;
                            @SerializedName("pickConfig")
                            @Expose
                            private String pickConfig;
                            @SerializedName("qpAllowed")
                            @Expose
                            private String qpAllowed;

                            public String getPickMode() {
                                return pickMode;
                            }

                            public void setPickMode(String pickMode) {
                                this.pickMode = pickMode;
                            }

                            public String getPickCount() {
                                return pickCount;
                            }

                            public void setPickCount(String pickCount) {
                                this.pickCount = pickCount;
                            }

                            public String getPickValue() {
                                return pickValue;
                            }

                            public void setPickValue(String pickValue) {
                                this.pickValue = pickValue;
                            }

                            public String getPickConfig() {
                                return pickConfig;
                            }

                            public void setPickConfig(String pickConfig) {
                                this.pickConfig = pickConfig;
                            }

                            public String getQpAllowed() {
                                return qpAllowed;
                            }

                            public void setQpAllowed(String qpAllowed) {
                                this.qpAllowed = qpAllowed;
                            }


                            @Override
                            public int describeContents() {
                                return 0;
                            }

                            @Override
                            public void writeToParcel(Parcel dest, int flags) {
                                dest.writeString(this.pickMode);
                                dest.writeString(this.pickCount);
                                dest.writeString(this.pickValue);
                                dest.writeString(this.pickConfig);
                                dest.writeString(this.qpAllowed);
                            }

                            public Range() {
                            }

                            protected Range(Parcel in) {
                                this.pickMode = in.readString();
                                this.pickCount = in.readString();
                                this.pickValue = in.readString();
                                this.pickConfig = in.readString();
                                this.qpAllowed = in.readString();
                            }

                            public static final Creator<Range> CREATOR = new Creator<Range>() {
                                @Override
                                public Range createFromParcel(Parcel source) {
                                    return new Range(source);
                                }

                                @Override
                                public Range[] newArray(int size) {
                                    return new Range[size];
                                }
                            };
                        }

                        @Override
                        public int describeContents() {
                            return 0;
                        }

                        @Override
                        public void writeToParcel(Parcel dest, int flags) {
                            dest.writeString(this.name);
                            dest.writeString(this.code);
                            dest.writeValue(this.isSelected);
                            dest.writeList(this.range);
                            dest.writeString(this.coordinate);
                            dest.writeString(this.description);
                        }

                        public PickType() {
                        }

                        protected PickType(Parcel in) {
                            this.name = in.readString();
                            this.code = in.readString();
                            this.isSelected = (Integer) in.readValue(Integer.class.getClassLoader());
                            this.range = new ArrayList<Range>();
                            in.readList(this.range, Range.class.getClassLoader());
                            this.coordinate = in.readString();
                            this.description = in.readString();
                        }

                        public static final Creator<PickType> CREATOR = new Creator<PickType>() {
                            @Override
                            public PickType createFromParcel(Parcel source) {
                                return new PickType(source);
                            }

                            @Override
                            public PickType[] newArray(int size) {
                                return new PickType[size];
                            }
                        };
                    }


                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeList(this.pickType);
                    }

                    public PickTypeData() {
                    }

                    protected PickTypeData(Parcel in) {
                        this.pickType = new ArrayList<PickType>();
                        in.readList(this.pickType, PickType.class.getClassLoader());
                    }

                    public static final Creator<PickTypeData> CREATOR = new Creator<PickTypeData>() {
                        @Override
                        public PickTypeData createFromParcel(Parcel source) {
                            return new PickTypeData(source);
                        }

                        @Override
                        public PickTypeData[] newArray(int size) {
                            return new PickTypeData[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeValue(this.unitPrice);
                    dest.writeValue(this.maxBetAmtMul);
                    dest.writeValue(this.isSelected);
                    dest.writeString(this.betDispName);
                    dest.writeString(this.betCode);
                    dest.writeString(this.betName);
                    dest.writeParcelable(this.pickTypeData, flags);
                    dest.writeString(this.inputCount);
                    dest.writeString(this.winMode);
                }

                public BetRespVO() {
                }

                protected BetRespVO(Parcel in) {
                    this.unitPrice = (Double) in.readValue(Double.class.getClassLoader());
                    this.maxBetAmtMul = (Integer) in.readValue(Integer.class.getClassLoader());
                    this.isSelected = (Integer) in.readValue(Integer.class.getClassLoader());
                    this.betDispName = in.readString();
                    this.betCode = in.readString();
                    this.betName = in.readString();
                    this.pickTypeData = in.readParcelable(PickTypeData.class.getClassLoader());
                    this.inputCount = in.readString();
                    this.winMode = in.readString();
                }

                public static final Creator<BetRespVO> CREATOR = new Creator<BetRespVO>() {
                    @Override
                    public BetRespVO createFromParcel(Parcel source) {
                        return new BetRespVO(source);
                    }

                    @Override
                    public BetRespVO[] newArray(int size) {
                        return new BetRespVO[size];
                    }
                };
            }

            public static class DrawRespVO implements Parcelable {

                @SerializedName("drawId")
                @Expose
                private Integer drawId;
                @SerializedName("drawName")
                @Expose
                private String drawName;
                @SerializedName("drawDay")
                @Expose
                private String drawDay;
                @SerializedName("drawDateTime")
                @Expose
                private String drawDateTime;
                @SerializedName("drawFreezeTime")
                @Expose
                private String drawFreezeTime;
                @SerializedName("drawSaleStopTime")
                @Expose
                private String drawSaleStopTime;
                @SerializedName("drawStatus")
                @Expose
                private String drawStatus;
                private boolean isSelected;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

                public Integer getDrawId() {
                    return drawId;
                }

                public void setDrawId(Integer drawId) {
                    this.drawId = drawId;
                }

                public String getDrawName() {
                    return drawName;
                }

                public void setDrawName(String drawName) {
                    this.drawName = drawName;
                }

                public String getDrawDay() {
                    return drawDay;
                }

                public void setDrawDay(String drawDay) {
                    this.drawDay = drawDay;
                }

                public String getDrawDateTime() {
                    return drawDateTime;
                }

                public void setDrawDateTime(String drawDateTime) {
                    this.drawDateTime = drawDateTime;
                }

                public String getDrawFreezeTime() {
                    return drawFreezeTime;
                }

                public void setDrawFreezeTime(String drawFreezeTime) {
                    this.drawFreezeTime = drawFreezeTime;
                }

                public String getDrawSaleStopTime() {
                    return drawSaleStopTime;
                }

                public void setDrawSaleStopTime(String drawSaleStopTime) {
                    this.drawSaleStopTime = drawSaleStopTime;
                }

                public String getDrawStatus() {
                    return drawStatus;
                }

                public void setDrawStatus(String drawStatus) {
                    this.drawStatus = drawStatus;
                }

                @NonNull
                @Override
                public String toString() {
                    return "DrawRespVO{" + "drawId=" + drawId + ", drawName='" + drawName + '\'' + ", drawDay='" + drawDay + '\'' + ", drawDateTime='" + drawDateTime + '\'' + ", drawFreezeTime='" + drawFreezeTime + '\'' + ", drawSaleStopTime='" + drawSaleStopTime + '\'' + ", drawStatus='" + drawStatus + '\'' + ", isSelected=" + isSelected + '}';
                }


                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeValue(this.drawId);
                    dest.writeString(this.drawName);
                    dest.writeString(this.drawDay);
                    dest.writeString(this.drawDateTime);
                    dest.writeString(this.drawFreezeTime);
                    dest.writeString(this.drawSaleStopTime);
                    dest.writeString(this.drawStatus);
                    dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
                }

                public DrawRespVO() {
                }

                protected DrawRespVO(Parcel in) {
                    this.drawId = (Integer) in.readValue(Integer.class.getClassLoader());
                    this.drawName = in.readString();
                    this.drawDay = in.readString();
                    this.drawDateTime = in.readString();
                    this.drawFreezeTime = in.readString();
                    this.drawSaleStopTime = in.readString();
                    this.drawStatus = in.readString();
                    this.isSelected = in.readByte() != 0;
                }

                public static final Creator<DrawRespVO> CREATOR = new Creator<DrawRespVO>() {
                    @Override
                    public DrawRespVO createFromParcel(Parcel source) {
                        return new DrawRespVO(source);
                    }

                    @Override
                    public DrawRespVO[] newArray(int size) {
                        return new DrawRespVO[size];
                    }
                };
            }

            public static class CurrencyRespVO implements Parcelable {

                @SerializedName("currencyCode")
                @Expose
                private String currencyCode;
                @SerializedName("value")
                @Expose
                private Double value;
                @SerializedName("description")
                @Expose
                private String description;

                public String getCurrencyCode() {
                    return currencyCode;
                }

                public void setCurrencyCode(String currencyCode) {
                    this.currencyCode = currencyCode;
                }

                public Double getValue() {
                    return value;
                }

                public void setValue(Double value) {
                    this.value = value;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }


                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.currencyCode);
                    dest.writeValue(this.value);
                    dest.writeString(this.description);
                }

                public CurrencyRespVO() {
                }

                protected CurrencyRespVO(Parcel in) {
                    this.currencyCode = in.readString();
                    this.value = (Double) in.readValue(Double.class.getClassLoader());
                    this.description = in.readString();
                }

                public static final Creator<CurrencyRespVO> CREATOR = new Creator<CurrencyRespVO>() {
                    @Override
                    public CurrencyRespVO createFromParcel(Parcel source) {
                        return new CurrencyRespVO(source);
                    }

                    @Override
                    public CurrencyRespVO[] newArray(int size) {
                        return new CurrencyRespVO[size];
                    }
                };
            }

            public static class LastDrawWinningResultVO implements Parcelable {

                @SerializedName("lastDrawDateTime")
                @Expose
                private String lastDrawDateTime;
                @SerializedName("winningNumber")
                @Expose
                private String winningNumber;
                @SerializedName("winningMultiplierInfo")
                @Expose
                private WinningMultiplierInfo winningMultiplierInfo;
                @SerializedName("runTimeFlagInfo")
                @Expose
                private ArrayList<RunTimeFlagInfo> runTimeFlagInfo;
                @SerializedName("sideBetMatchInfo")
                @Expose
                private ArrayList<SideBetMatchInfo> sideBetMatchInfo = null;

                public String getLastDrawDateTime() {
                    return lastDrawDateTime;
                }

                public void setLastDrawDateTime(String lastDrawDateTime) {
                    this.lastDrawDateTime = lastDrawDateTime;
                }

                public String getWinningNumber() {
                    return winningNumber;
                }

                public void setWinningNumber(String winningNumber) {
                    this.winningNumber = winningNumber;
                }


                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.lastDrawDateTime);
                    dest.writeString(this.winningNumber);
                }

                protected LastDrawWinningResultVO(Parcel in) {
                    this.lastDrawDateTime = in.readString();
                    this.winningNumber = in.readString();
                }

                public static final Creator<LastDrawWinningResultVO> CREATOR = new Creator<LastDrawWinningResultVO>() {
                    @Override
                    public LastDrawWinningResultVO createFromParcel(Parcel source) {
                        return new LastDrawWinningResultVO(source);
                    }

                    @Override
                    public LastDrawWinningResultVO[] newArray(int size) {
                        return new LastDrawWinningResultVO[size];
                    }
                };

                public WinningMultiplierInfo getWinningMultiplierInfo() {
                    return winningMultiplierInfo;
                }

                public void setWinningMultiplierInfo(WinningMultiplierInfo winningMultiplierInfo) {
                    this.winningMultiplierInfo = winningMultiplierInfo;
                }

                public ArrayList<SideBetMatchInfo> getSideBetMatchInfo() {
                    return sideBetMatchInfo;
                }

                public void setSideBetMatchInfo(ArrayList<SideBetMatchInfo> sideBetMatchInfo) {
                    this.sideBetMatchInfo = sideBetMatchInfo;
                }

                public ArrayList<RunTimeFlagInfo> getRunTimeFlagInfo() {
                    return runTimeFlagInfo;
                }

                public void setRunTimeFlagInfo(ArrayList<RunTimeFlagInfo> runTimeFlagInfo) {
                    this.runTimeFlagInfo = runTimeFlagInfo;
                }
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

            public static class DrawPrizeMultiplier implements Parcelable {

                private ArrayList<Multiplier> multiplier;

                public ArrayList<Multiplier> getMultiplier() {
                    return multiplier;
                }

                public void setMultiplier(ArrayList<Multiplier> multiplier) {
                    this.multiplier = multiplier;
                }

                public static class Multiplier implements Parcelable {

                    private String name;
                    private String odds;
                    private String value;
                    private String attribute;
                    public String getName() {
                        return name;
                    }
                    public void setName(String name) {
                        this.name = name;
                    }
                    public String getOdds() {
                        return odds;
                    }
                    public void setOdds(String odds) {
                        this.odds = odds;
                    }
                    public String getValue() {
                        return value;
                    }
                    public void setValue(String value) {
                        this.value = value;
                    }
                    public String getAttribute() {
                        return attribute;
                    }
                    public void setAttribute(String attribute) {
                        this.attribute = attribute;
                    }


                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.name);
                        dest.writeString(this.odds);
                        dest.writeString(this.value);
                        dest.writeString(this.attribute);
                    }

                    public Multiplier() {
                    }

                    protected Multiplier(Parcel in) {
                        this.name = in.readString();
                        this.odds = in.readString();
                        this.value = in.readString();
                        this.attribute = in.readString();
                    }

                    public static final Creator<Multiplier> CREATOR = new Creator<Multiplier>() {
                        @Override
                        public Multiplier createFromParcel(Parcel source) {
                            return new Multiplier(source);
                        }

                        @Override
                        public Multiplier[] newArray(int size) {
                            return new Multiplier[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeList(this.multiplier);
                }

                public DrawPrizeMultiplier() {
                }

                protected DrawPrizeMultiplier(Parcel in) {
                    this.multiplier = new ArrayList<Multiplier>();
                    in.readList(this.multiplier, Multiplier.class.getClassLoader());
                }

                public static final Creator<DrawPrizeMultiplier> CREATOR = new Creator<DrawPrizeMultiplier>() {
                    @Override
                    public DrawPrizeMultiplier createFromParcel(Parcel source) {
                        return new DrawPrizeMultiplier(source);
                    }

                    @Override
                    public DrawPrizeMultiplier[] newArray(int size) {
                        return new DrawPrizeMultiplier[size];
                    }
                };
            }

            public GameRespVO() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(this.id);
                dest.writeValue(this.gameNumber);
                dest.writeString(this.gameName);
                dest.writeString(this.gameCode);
                dest.writeString(this.familyCode);
                dest.writeString(this.lastDrawResult);
                dest.writeString(this.displayOrder);
                dest.writeString(this.drawFrequencyType);
                dest.writeString(this.timeToFetchUpdatedGameInfo);
                dest.writeParcelable(this.numberConfig, flags);
                dest.writeParcelable(this.runTimeFlag, flags);
                dest.writeTypedList(this.betRespVOs);
                dest.writeTypedList(this.drawRespVOs);
                dest.writeString(this.nativeCurrency);
                dest.writeString(this.drawEvent);
                dest.writeString(this.gameStatus);
                dest.writeString(this.gameOrder);
                dest.writeString(this.consecutiveDraw);
                dest.writeValue(this.maxAdvanceDraws);
                dest.writeParcelable(this.drawPrizeMultiplier, flags);
                dest.writeString(this.lastDrawFreezeTime);
                dest.writeString(this.lastDrawDateTime);
                dest.writeString(this.lastDrawSaleStopTime);
                dest.writeString(this.lastDrawTime);
                dest.writeValue(this.ticketExpiry);
                dest.writeValue(this.maxPanelAllowed);
                dest.writeTypedList(this.currencyRespVOs);
                dest.writeTypedList(this.lastDrawWinningResultVOs);
                dest.writeString(this.hotNumbers);
                dest.writeString(this.coldNumbers);
                dest.writeParcelable(this.gameSchemas, flags);
                dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
                dest.writeByte(this.isCountDownStarted ? (byte) 1 : (byte) 0);
                dest.writeString(this.currentDate);
            }

            protected GameRespVO(Parcel in) {
                this.id = (Integer) in.readValue(Integer.class.getClassLoader());
                this.gameNumber = (Integer) in.readValue(Integer.class.getClassLoader());
                this.gameName = in.readString();
                this.gameCode = in.readString();
                this.familyCode = in.readString();
                this.lastDrawResult = in.readString();
                this.displayOrder = in.readString();
                this.drawFrequencyType = in.readString();
                this.timeToFetchUpdatedGameInfo = in.readString();
                this.numberConfig = in.readParcelable(NumberConfig.class.getClassLoader());
                this.runTimeFlag = in.readParcelable(RunTimeFlag.class.getClassLoader());
                this.betRespVOs = in.createTypedArrayList(BetRespVO.CREATOR);
                this.drawRespVOs = in.createTypedArrayList(DrawRespVO.CREATOR);
                this.nativeCurrency = in.readString();
                this.drawEvent = in.readString();
                this.gameStatus = in.readString();
                this.gameOrder = in.readString();
                this.consecutiveDraw = in.readString();
                this.maxAdvanceDraws = (Integer) in.readValue(Integer.class.getClassLoader());
                this.drawPrizeMultiplier = in.readParcelable(DrawPrizeMultiplier.class.getClassLoader());
                this.lastDrawFreezeTime = in.readString();
                this.lastDrawDateTime = in.readString();
                this.lastDrawSaleStopTime = in.readString();
                this.lastDrawTime = in.readString();
                this.ticketExpiry = (Integer) in.readValue(Integer.class.getClassLoader());
                this.maxPanelAllowed = (Integer) in.readValue(Integer.class.getClassLoader());
                this.currencyRespVOs = in.createTypedArrayList(CurrencyRespVO.CREATOR);
                this.lastDrawWinningResultVOs = in.createTypedArrayList(LastDrawWinningResultVO.CREATOR);
                this.hotNumbers = in.readString();
                this.coldNumbers = in.readString();
                this.gameSchemas = in.readParcelable(GameSchemas.class.getClassLoader());
                this.isSelected = in.readByte() != 0;
                this.isCountDownStarted = in.readByte() != 0;
                this.currentDate = in.readString();
            }

            public static final Creator<GameRespVO> CREATOR = new Creator<GameRespVO>() {
                @Override
                public GameRespVO createFromParcel(Parcel source) {
                    return new GameRespVO(source);
                }

                @Override
                public GameRespVO[] newArray(int size) {
                    return new GameRespVO[size];
                }
            };
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.gameRespVOs);
            dest.writeString(this.currentDate);
        }

        public ResponseData() {
        }

        protected ResponseData(Parcel in) {
            this.gameRespVOs = in.createTypedArrayList(GameRespVO.CREATOR);
            this.currentDate = in.readString();
        }

        public static final Parcelable.Creator<ResponseData> CREATOR = new Parcelable.Creator<ResponseData>() {
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


    public DrawFetchGameDataResponseBean() {
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
        dest.writeString(this.updatingGameCode);
    }

    protected DrawFetchGameDataResponseBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.responseData = in.readParcelable(ResponseData.class.getClassLoader());
        this.updatingGameCode = in.readString();
    }

    public static final Creator<DrawFetchGameDataResponseBean> CREATOR = new Creator<DrawFetchGameDataResponseBean>() {
        @Override
        public DrawFetchGameDataResponseBean createFromParcel(Parcel source) {
            return new DrawFetchGameDataResponseBean(source);
        }

        @Override
        public DrawFetchGameDataResponseBean[] newArray(int size) {
            return new DrawFetchGameDataResponseBean[size];
        }
    };
}