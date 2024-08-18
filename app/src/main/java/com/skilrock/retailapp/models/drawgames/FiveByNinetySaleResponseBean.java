package com.skilrock.retailapp.models.drawgames;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FiveByNinetySaleResponseBean implements Parcelable {

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


        @SerializedName("gameId")
        @Expose
        private Integer gameId;

        @SerializedName("gameName")
        @Expose
        private String gameName;

        @SerializedName("gameCode")
        @Expose
        private String gameCode;

        @SerializedName("totalPurchaseAmount")
        @Expose
        private Double totalPurchaseAmount;

        @SerializedName("playerPurchaseAmount")
        @Expose
        private Double playerPurchaseAmount;

        @SerializedName("purchaseTime")
        @Expose
        private String purchaseTime;

        @SerializedName("ticketNumber")
        @Expose
        private String ticketNumber;

        @SerializedName("panelData")
        @Expose
        private ArrayList<PanelData> panelData = null;

        @SerializedName("drawData")
        @Expose
        private ArrayList<DrawData> drawData = null;

        @SerializedName("merchantCode")
        @Expose
        private String merchantCode;

        @SerializedName("currencyCode")
        @Expose
        private String currencyCode;

        @SerializedName("partyType")
        @Expose
        private String partyType;

        @SerializedName("channel")
        @Expose
        private String channel;

        @SerializedName("availableBal")
        @Expose
        private String availableBal;

        @SerializedName("noOfDraws")
        @Expose
        private Integer noOfDraws;

        @SerializedName("validationCode")
        @Expose
        private String validationCode;

        @SerializedName("ticketExpiry")
        @Expose
        private String ticketExpiry;

        @SerializedName("advMessages")
        private AdvMessageBean advMessages;


        public AdvMessageBean getAdvMessages() {
            return advMessages;
        }

        public void setAdvMessages(AdvMessageBean advMessages) {
            this.advMessages = advMessages;
        }

        public String getTicketExpiry() {
            return ticketExpiry;
        }

        public void setTicketExpiry(String ticketExpiry) {
            this.ticketExpiry = ticketExpiry;
        }

        public String getValidationCode() {
            return validationCode;
        }

        public void setValidationCode(String validationCode) {
            this.validationCode = validationCode;
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

        public String getGameCode() {
            return gameCode;
        }

        public void setGameCode(String gameCode) {
            this.gameCode = gameCode;
        }

        public Double getTotalPurchaseAmount() {
            return totalPurchaseAmount;
        }

        public void setTotalPurchaseAmount(Double totalPurchaseAmount) {
            this.totalPurchaseAmount = totalPurchaseAmount;
        }

        public Double getPlayerPurchaseAmount() {
            return playerPurchaseAmount;
        }

        public void setPlayerPurchaseAmount(Double playerPurchaseAmount) {
            this.playerPurchaseAmount = playerPurchaseAmount;
        }

        public String getPurchaseTime() {
            return purchaseTime;
        }

        public void setPurchaseTime(String purchaseTime) {
            this.purchaseTime = purchaseTime;
        }

        public String getTicketNumber() {
            return ticketNumber;
        }

        public void setTicketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
        }

        public ArrayList<PanelData> getPanelData() {
            return panelData;
        }

        public void setPanelData(ArrayList<PanelData> panelData) {
            this.panelData = panelData;
        }

        public ArrayList<DrawData> getDrawData() {
            return drawData;
        }

        public void setDrawData(ArrayList<DrawData> drawData) {
            this.drawData = drawData;
        }

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getPartyType() {
            return partyType;
        }

        public void setPartyType(String partyType) {
            this.partyType = partyType;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getAvailableBal() {
            return availableBal;
        }

        public void setAvailableBal(String availableBal) {
            this.availableBal = availableBal;
        }

        public Integer getNoOfDraws() {
            return noOfDraws;
        }

        public void setNoOfDraws(Integer noOfDraws) {
            this.noOfDraws = noOfDraws;
        }

        public static class PanelData implements Parcelable {

            @SerializedName("betType")
            @Expose
            private String betType;

            @SerializedName("pickType")
            @Expose
            private String pickType;

            @SerializedName("pickConfig")
            @Expose
            private String pickConfig;

            @SerializedName("betAmountMultiple")
            @Expose
            private Integer betAmountMultiple;

            @SerializedName("quickPick")
            @Expose
            private Boolean quickPick;

            @SerializedName("pickedValues")
            @Expose
            private String pickedValues;

            @SerializedName("qpPreGenerated")
            @Expose
            private Boolean qpPreGenerated;

            @SerializedName("numberOfLines")
            @Expose
            private Integer numberOfLines;

            @SerializedName("unitCost")
            @Expose
            private Double unitCost;

            @SerializedName("panelPrice")
            @Expose
            private Double panelPrice;

            @SerializedName("playerPanelPrice")
            @Expose
            private Double playerPanelPrice;

            @SerializedName("betDisplayName")
            @Expose
            private String betDisplayName;

            @SerializedName("pickDisplayName")
            @Expose
            private String pickDisplayName;

            public String getBetType() {
                return betType;
            }

            public void setBetType(String betType) {
                this.betType = betType;
            }

            public String getPickType() {
                return pickType;
            }

            public void setPickType(String pickType) {
                this.pickType = pickType;
            }

            public String getPickConfig() {
                return pickConfig;
            }

            public void setPickConfig(String pickConfig) {
                this.pickConfig = pickConfig;
            }

            public Integer getBetAmountMultiple() {
                return betAmountMultiple;
            }

            public void setBetAmountMultiple(Integer betAmountMultiple) {
                this.betAmountMultiple = betAmountMultiple;
            }

            public Boolean getQuickPick() {
                return quickPick;
            }

            public void setQuickPick(Boolean quickPick) {
                this.quickPick = quickPick;
            }

            public String getPickedValues() {
                return pickedValues;
            }

            public void setPickedValues(String pickedValues) {
                this.pickedValues = pickedValues;
            }

            public Boolean getQpPreGenerated() {
                return qpPreGenerated;
            }

            public void setQpPreGenerated(Boolean qpPreGenerated) {
                this.qpPreGenerated = qpPreGenerated;
            }

            public Integer getNumberOfLines() {
                return numberOfLines;
            }

            public void setNumberOfLines(Integer numberOfLines) {
                this.numberOfLines = numberOfLines;
            }

            public Double getUnitCost() {
                return unitCost;
            }

            public void setUnitCost(Double unitCost) {
                this.unitCost = unitCost;
            }

            public Double getPanelPrice() {
                return panelPrice;
            }

            public void setPanelPrice(Double panelPrice) {
                this.panelPrice = panelPrice;
            }

            public Double getPlayerPanelPrice() {
                return playerPanelPrice;
            }

            public void setPlayerPanelPrice(Double playerPanelPrice) {
                this.playerPanelPrice = playerPanelPrice;
            }

            public String getBetDisplayName() {
                return betDisplayName;
            }

            public void setBetDisplayName(String betDisplayName) {
                this.betDisplayName = betDisplayName;
            }

            public String getPickDisplayName() {
                return pickDisplayName;
            }

            public void setPickDisplayName(String pickDisplayName) {
                this.pickDisplayName = pickDisplayName;
            }

            @NonNull
            @Override
            public String toString() {
                return "PanelData{" +
                        "betType='" + betType + '\'' +
                        ", pickType='" + pickType + '\'' +
                        ", pickConfig='" + pickConfig + '\'' +
                        ", betAmountMultiple=" + betAmountMultiple +
                        ", quickPick=" + quickPick +
                        ", pickedValues='" + pickedValues + '\'' +
                        ", qpPreGenerated=" + qpPreGenerated +
                        ", numberOfLines=" + numberOfLines +
                        ", unitCost=" + unitCost +
                        ", panelPrice=" + panelPrice +
                        ", playerPanelPrice=" + playerPanelPrice +
                        ", betDisplayName='" + betDisplayName + '\'' +
                        ", pickDisplayName='" + pickDisplayName + '\'' +
                        '}';
            }

            public PanelData() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.betType);
                dest.writeString(this.pickType);
                dest.writeString(this.pickConfig);
                dest.writeValue(this.betAmountMultiple);
                dest.writeValue(this.quickPick);
                dest.writeString(this.pickedValues);
                dest.writeValue(this.qpPreGenerated);
                dest.writeValue(this.numberOfLines);
                dest.writeValue(this.unitCost);
                dest.writeValue(this.panelPrice);
                dest.writeValue(this.playerPanelPrice);
                dest.writeString(this.betDisplayName);
                dest.writeString(this.pickDisplayName);
            }

            protected PanelData(Parcel in) {
                this.betType = in.readString();
                this.pickType = in.readString();
                this.pickConfig = in.readString();
                this.betAmountMultiple = (Integer) in.readValue(Integer.class.getClassLoader());
                this.quickPick = (Boolean) in.readValue(Boolean.class.getClassLoader());
                this.pickedValues = in.readString();
                this.qpPreGenerated = (Boolean) in.readValue(Boolean.class.getClassLoader());
                this.numberOfLines = (Integer) in.readValue(Integer.class.getClassLoader());
                this.unitCost = (Double) in.readValue(Double.class.getClassLoader());
                this.panelPrice = (Double) in.readValue(Double.class.getClassLoader());
                this.playerPanelPrice = (Double) in.readValue(Double.class.getClassLoader());
                this.betDisplayName = in.readString();
                this.pickDisplayName = in.readString();
            }

            public static final Creator<PanelData> CREATOR = new Creator<PanelData>() {
                @Override
                public PanelData createFromParcel(Parcel source) {
                    return new PanelData(source);
                }

                @Override
                public PanelData[] newArray(int size) {
                    return new PanelData[size];
                }
            };
        }

        public static class DrawData implements Parcelable {

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

            @NonNull
            @Override
            public String toString() {
                return "DrawDatum{" + "drawName='" + drawName + '\'' + ", drawDate='" + drawDate + '\'' + ", drawTime='" + drawTime + '\'' + '}';
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

            public DrawData() {
            }

            protected DrawData(Parcel in) {
                this.drawName = in.readString();
                this.drawDate = in.readString();
                this.drawTime = in.readString();
            }

            public static final Creator<DrawData> CREATOR = new Creator<DrawData>() {
                @Override
                public DrawData createFromParcel(Parcel source) {
                    return new DrawData(source);
                }

                @Override
                public DrawData[] newArray(int size) {
                    return new DrawData[size];
                }
            };
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseData{" +
                    "gameId=" + gameId +
                    ", gameName='" + gameName + '\'' +
                    ", gameCode='" + gameCode + '\'' +
                    ", totalPurchaseAmount=" + totalPurchaseAmount +
                    ", playerPurchaseAmount=" + playerPurchaseAmount +
                    ", purchaseTime='" + purchaseTime + '\'' +
                    ", ticketNumber='" + ticketNumber + '\'' +
                    ", panelData=" + panelData +
                    ", drawData=" + drawData +
                    ", merchantCode='" + merchantCode + '\'' +
                    ", currencyCode='" + currencyCode + '\'' +
                    ", partyType='" + partyType + '\'' +
                    ", channel='" + channel + '\'' +
                    ", availableBal='" + availableBal + '\'' +
                    ", noOfDraws=" + noOfDraws +
                    ", validationCode='" + validationCode + '\'' +
                    ", ticketExpiry=" + ticketExpiry +
                    '}';
        }

        public ResponseData() {
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

                @NonNull
                @Override
                public String toString() {
                    return "Bottom{" + "msgMode='" + msgMode + '\'' + ", msgText='" + msgText + '\'' + ", msgType='" + msgType + '\'' + '}';
                }
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

                @NonNull
                @Override
                public String toString() {
                    return "Top{" + "msgMode='" + msgMode + '\'' + ", msgText='" + msgText + '\'' + ", msgType='" + msgType + '\'' + '}';
                }
            }

            public AdvMessageBean() {
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

            @NonNull
            @Override
            public String toString() {
                return "AdvMessageBean{" + "top=" + top + ", bottom=" + bottom + '}';
            }
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.gameId);
            dest.writeString(this.gameName);
            dest.writeString(this.gameCode);
            dest.writeValue(this.totalPurchaseAmount);
            dest.writeValue(this.playerPurchaseAmount);
            dest.writeString(this.purchaseTime);
            dest.writeString(this.ticketNumber);
            dest.writeTypedList(this.panelData);
            dest.writeTypedList(this.drawData);
            dest.writeString(this.merchantCode);
            dest.writeString(this.currencyCode);
            dest.writeString(this.partyType);
            dest.writeString(this.channel);
            dest.writeString(this.availableBal);
            dest.writeValue(this.noOfDraws);
            dest.writeString(this.validationCode);
            dest.writeString(this.ticketExpiry);
            dest.writeParcelable(this.advMessages, flags);
        }

        protected ResponseData(Parcel in) {
            this.gameId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.gameName = in.readString();
            this.gameCode = in.readString();
            this.totalPurchaseAmount = (Double) in.readValue(Double.class.getClassLoader());
            this.playerPurchaseAmount = (Double) in.readValue(Double.class.getClassLoader());
            this.purchaseTime = in.readString();
            this.ticketNumber = in.readString();
            this.panelData = in.createTypedArrayList(PanelData.CREATOR);
            this.drawData = in.createTypedArrayList(DrawData.CREATOR);
            this.merchantCode = in.readString();
            this.currencyCode = in.readString();
            this.partyType = in.readString();
            this.channel = in.readString();
            this.availableBal = in.readString();
            this.noOfDraws = (Integer) in.readValue(Integer.class.getClassLoader());
            this.validationCode = in.readString();
            this.ticketExpiry = in.readString();
            this.advMessages = in.readParcelable(AdvMessageBean.class.getClassLoader());
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

    @NonNull
    @Override
    public String toString() {
        return "FiveByNinetySaleResponseBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", responseData=" + responseData + '}';
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

    public FiveByNinetySaleResponseBean() {
    }

    protected FiveByNinetySaleResponseBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.responseData = in.readParcelable(ResponseData.class.getClassLoader());
    }

    public static final Parcelable.Creator<FiveByNinetySaleResponseBean> CREATOR = new Parcelable.Creator<FiveByNinetySaleResponseBean>() {
        @Override
        public FiveByNinetySaleResponseBean createFromParcel(Parcel source) {
            return new FiveByNinetySaleResponseBean(source);
        }

        @Override
        public FiveByNinetySaleResponseBean[] newArray(int size) {
            return new FiveByNinetySaleResponseBean[size];
        }
    };
}
