package com.skilrock.retailapp.models.drawgames;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WinningClaimPayResponseBean implements Parcelable {

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

    public static class DrawDatum implements Parcelable {

        @SerializedName("drawId")
        @Expose
        private Integer drawId;
        @SerializedName("drawName")
        @Expose
        private String drawName;
        @SerializedName("drawDate")
        @Expose
        private String drawDate;
        @SerializedName("drawTime")
        @Expose
        private String drawTime;
        @SerializedName("winStatus")
        @Expose
        private String winStatus;
        @SerializedName("winningAmount")
        @Expose
        private String winningAmount;
        @SerializedName("panelWinList")
        @Expose
        private List<PanelWinList> panelWinList = null;
        @SerializedName("winCode")
        @Expose
        private Integer winCode;

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

        public String getWinStatus() {
            return winStatus;
        }

        public void setWinStatus(String winStatus) {
            this.winStatus = winStatus;
        }

        public String getWinningAmount() {
            return winningAmount;
        }

        public void setWinningAmount(String winningAmount) {
            this.winningAmount = winningAmount;
        }

        public List<PanelWinList> getPanelWinList() {
            return panelWinList;
        }

        public void setPanelWinList(List<PanelWinList> panelWinList) {
            this.panelWinList = panelWinList;
        }

        public Integer getWinCode() {
            return winCode;
        }

        public void setWinCode(Integer winCode) {
            this.winCode = winCode;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.drawId);
            dest.writeString(this.drawName);
            dest.writeString(this.drawDate);
            dest.writeString(this.drawTime);
            dest.writeString(this.winStatus);
            dest.writeString(this.winningAmount);
            dest.writeList(this.panelWinList);
            dest.writeValue(this.winCode);
        }

        public DrawDatum() {
        }

        protected DrawDatum(Parcel in) {
            this.drawId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.drawName = in.readString();
            this.drawDate = in.readString();
            this.drawTime = in.readString();
            this.winStatus = in.readString();
            this.winningAmount = in.readString();
            this.panelWinList = new ArrayList<PanelWinList>();
            in.readList(this.panelWinList, PanelWinList.class.getClassLoader());
            this.winCode = (Integer) in.readValue(Integer.class.getClassLoader());
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


    public static class PanelDatum implements Parcelable {

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
        private Integer unitCost;
        @SerializedName("panelPrice")
        @Expose
        private Integer panelPrice;
        @SerializedName("playerPanelPrice")
        @Expose
        private Integer playerPanelPrice;
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

        public Integer getUnitCost() {
            return unitCost;
        }

        public void setUnitCost(Integer unitCost) {
            this.unitCost = unitCost;
        }

        public Integer getPanelPrice() {
            return panelPrice;
        }

        public void setPanelPrice(Integer panelPrice) {
            this.panelPrice = panelPrice;
        }

        public Integer getPlayerPanelPrice() {
            return playerPanelPrice;
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

        public void setPlayerPanelPrice(Integer playerPanelPrice) {
            this.playerPanelPrice = playerPanelPrice;
        }


        public PanelDatum() {
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

        protected PanelDatum(Parcel in) {
            this.betType = in.readString();
            this.pickType = in.readString();
            this.pickConfig = in.readString();
            this.betAmountMultiple = (Integer) in.readValue(Integer.class.getClassLoader());
            this.quickPick = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.pickedValues = in.readString();
            this.qpPreGenerated = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.numberOfLines = (Integer) in.readValue(Integer.class.getClassLoader());
            this.unitCost = (Integer) in.readValue(Integer.class.getClassLoader());
            this.panelPrice = (Integer) in.readValue(Integer.class.getClassLoader());
            this.playerPanelPrice = (Integer) in.readValue(Integer.class.getClassLoader());
            this.betDisplayName = in.readString();
            this.pickDisplayName = in.readString();
        }

        public static final Creator<PanelDatum> CREATOR = new Creator<PanelDatum>() {
            @Override
            public PanelDatum createFromParcel(Parcel source) {
                return new PanelDatum(source);
            }

            @Override
            public PanelDatum[] newArray(int size) {
                return new PanelDatum[size];
            }
        };
    }

    public static class PanelWinList implements Parcelable {

        @SerializedName("panelId")
        @Expose
        private Integer panelId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("playType")
        @Expose
        private String playType;
        @SerializedName("winningAmt")
        @Expose
        private Integer winningAmt;
        @SerializedName("valid")
        @Expose
        private Boolean valid;

        public Integer getPanelId() {
            return panelId;
        }

        public void setPanelId(Integer panelId) {
            this.panelId = panelId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPlayType() {
            return playType;
        }

        public void setPlayType(String playType) {
            this.playType = playType;
        }

        public Integer getWinningAmt() {
            return winningAmt;
        }

        public void setWinningAmt(Integer winningAmt) {
            this.winningAmt = winningAmt;
        }

        public Boolean getValid() {
            return valid;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.panelId);
            dest.writeString(this.status);
            dest.writeString(this.playType);
            dest.writeValue(this.winningAmt);
            dest.writeValue(this.valid);
        }

        public PanelWinList() {
        }

        protected PanelWinList(Parcel in) {
            this.panelId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.status = in.readString();
            this.playType = in.readString();
            this.winningAmt = (Integer) in.readValue(Integer.class.getClassLoader());
            this.valid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        }

        public static final Creator<PanelWinList> CREATOR = new Creator<PanelWinList>() {
            @Override
            public PanelWinList createFromParcel(Parcel source) {
                return new PanelWinList(source);
            }

            @Override
            public PanelWinList[] newArray(int size) {
                return new PanelWinList[size];
            }
        };
    }

    public static class ResponseData implements Parcelable {
        @SerializedName("ticketNumber")
        @Expose
        private String ticketNumber;
        @SerializedName("doneByUserId")
        @Expose
        private Integer doneByUserId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("balance")
        @Expose
        private Double balance;
        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("gameCode")
        @Expose
        private String gameCode;
        @SerializedName("drawData")
        @Expose
        private List<DrawDatum> drawData = null;
        @SerializedName("prizeAmount")
        @Expose
        private String prizeAmount;
        @SerializedName("totalPay")
        @Expose
        private String totalPay;
        @SerializedName("currencySymbol")
        @Expose
        private String currencySymbol;
        @SerializedName("merchantCode")
        @Expose
        private String merchantCode;
        @SerializedName("orgName")
        @Expose
        private String orgName;
        @SerializedName("retailerName")
        @Expose
        private String retailerName;
        @SerializedName("reprintCountPwt")
        @Expose
        private String reprintCountPwt;

        @SerializedName("panelData")
        @Expose
        private List<PanelDatum> panelData = null;
        @SerializedName("merchantTxnId")
        @Expose
        private Integer merchantTxnId;
        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("totalPurchaseAmount")
        @Expose
        private double totalPurchaseAmount;

        @SerializedName("advMessages")
        private AdvMessageBean advMessages;

        public AdvMessageBean getAdvMessages() {
            return advMessages;
        }

        public void setAdvMessages(AdvMessageBean advMessages) {
            this.advMessages = advMessages;
        }

        public double getTotalPurchaseAmount() {
            return totalPurchaseAmount;
        }

        public void setTotalPurchaseAmount(double totalPurchaseAmount) {
            this.totalPurchaseAmount = totalPurchaseAmount;
        }

        public String getTicketNumber() {
            return ticketNumber;
        }

        public void setTicketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
        }

        public Integer getDoneByUserId() {
            return doneByUserId;
        }

        public void setDoneByUserId(Integer doneByUserId) {
            this.doneByUserId = doneByUserId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Double getBalance() {
            return balance;
        }

        public void setBalance(Double balance) {
            this.balance = balance;
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

        public List<DrawDatum> getDrawData() {
            return drawData;
        }

        public void setDrawData(List<DrawDatum> drawData) {
            this.drawData = drawData;
        }

        public String getPrizeAmount() {
            return prizeAmount;
        }

        public void setPrizeAmount(String prizeAmount) {
            this.prizeAmount = prizeAmount;
        }

        public String getTotalPay() {
            return totalPay;
        }

        public void setTotalPay(String totalPay) {
            this.totalPay = totalPay;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public void setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getRetailerName() {
            return retailerName;
        }

        public void setRetailerName(String retailerName) {
            this.retailerName = retailerName;
        }

        public String getReprintCountPwt() {
            return reprintCountPwt;
        }

        public void setReprintCountPwt(String reprintCountPwt) {
            this.reprintCountPwt = reprintCountPwt;
        }


        public List<PanelDatum> getPanelData() {
            return panelData;
        }

        public void setPanelData(List<PanelDatum> panelData) {
            this.panelData = panelData;
        }


        public Integer getMerchantTxnId() {
            return merchantTxnId;
        }

        public void setMerchantTxnId(Integer merchantTxnId) {
            this.merchantTxnId = merchantTxnId;
        }


        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
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
            dest.writeString(this.ticketNumber);
            dest.writeValue(this.doneByUserId);
            dest.writeString(this.status);
            dest.writeValue(this.balance);
            dest.writeString(this.gameName);
            dest.writeString(this.gameCode);
            dest.writeTypedList(this.drawData);
            dest.writeString(this.prizeAmount);
            dest.writeString(this.totalPay);
            dest.writeString(this.currencySymbol);
            dest.writeString(this.merchantCode);
            dest.writeString(this.orgName);
            dest.writeString(this.retailerName);
            dest.writeString(this.reprintCountPwt);
            dest.writeTypedList(this.panelData);
            dest.writeValue(this.merchantTxnId);
            dest.writeValue(this.success);
            dest.writeDouble(this.totalPurchaseAmount);
            dest.writeParcelable(this.advMessages, flags);
        }

        protected ResponseData(Parcel in) {
            this.ticketNumber = in.readString();
            this.doneByUserId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.status = in.readString();
            this.balance = (Double) in.readValue(Double.class.getClassLoader());
            this.gameName = in.readString();
            this.gameCode = in.readString();
            this.drawData = in.createTypedArrayList(DrawDatum.CREATOR);
            this.prizeAmount = in.readString();
            this.totalPay = in.readString();
            this.currencySymbol = in.readString();
            this.merchantCode = in.readString();
            this.orgName = in.readString();
            this.retailerName = in.readString();
            this.reprintCountPwt = in.readString();
            this.panelData = in.createTypedArrayList(PanelDatum.CREATOR);
            this.merchantTxnId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.success = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.totalPurchaseAmount = in.readDouble();
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

    public WinningClaimPayResponseBean() {
    }

    protected WinningClaimPayResponseBean(Parcel in) {
        this.responseCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.responseMessage = in.readString();
        this.responseData = in.readParcelable(ResponseData.class.getClassLoader());
    }

    public static final Parcelable.Creator<WinningClaimPayResponseBean> CREATOR = new Parcelable.Creator<WinningClaimPayResponseBean>() {
        @Override
        public WinningClaimPayResponseBean createFromParcel(Parcel source) {
            return new WinningClaimPayResponseBean(source);
        }

        @Override
        public WinningClaimPayResponseBean[] newArray(int size) {
            return new WinningClaimPayResponseBean[size];
        }
    };
}



