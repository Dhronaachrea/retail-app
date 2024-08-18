package com.skilrock.retailapp.sle_game_portrait;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PurchaseBeanSle {

    /**
     * responseCode : 0
     * responseMsg : Success
     * tktData : {"purchaseDate":"2019-12-12","reprintCount":0,"retailerName":"vineet org","currSymbol":"USD","orgName":"AFRICA LOTTO","expiryPeriod":"2019-12-13 15:45:00.0","purchaseTime":"15:04:03","ticketNo":"6913113461890000010","brCd":"6913113461890000010","trxId":"2381","ticketAmt":"1.00","balance":"-0.94","gameId":1,"gameTypeId":1,"gameName":"Soccer","gameTypeName":"Soccer 13","eventType":"[H, D, A]","boardData":[{"drawId":96,"drawName":"pool draw","drawDate":"2019-12-19","drawTime":"12:00:24","noOfLines":1,"boardPrice":1,"unitPrice":1,"eventData":[{"eventLeague":"Royal championship","eventVenue":"Camroon4","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon4","eventDisplayAway":"Camroon5","eventCodeHome":"C4","eventCodeAway":"C5","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon5","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon5","eventDisplayAway":"Camroon6","eventCodeHome":"C5","eventCodeAway":"C6","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon6","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon6","eventDisplayAway":"Camroon7","eventCodeHome":"C6","eventCodeAway":"C7","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon7","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon7","eventDisplayAway":"Camroon8","eventCodeHome":"C7","eventCodeAway":"C8","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon8","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon8","eventDisplayAway":"Camroon9","eventCodeHome":"C8","eventCodeAway":"C9","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon9","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon9","eventDisplayAway":"Camroon10","eventCodeHome":"C9","eventCodeAway":"C10","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon10","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon10","eventDisplayAway":"Camroon11","eventCodeHome":"C10","eventCodeAway":"C11","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon11","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon11","eventDisplayAway":"Camroon12","eventCodeHome":"C11","eventCodeAway":"C12","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon12","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon12","eventDisplayAway":"Camroon13","eventCodeHome":"C12","eventCodeAway":"C13","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon13","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon13","eventDisplayAway":"Camroon14","eventCodeHome":"C13","eventCodeAway":"C14","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon1","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon1","eventDisplayAway":"Camroon2","eventCodeHome":"C1","eventCodeAway":"C2","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon2","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon2","eventDisplayAway":"Camroon3","eventCodeHome":"C2","eventCodeAway":"C3","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon3","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon3","eventDisplayAway":"Camroon4","eventCodeHome":"C3","eventCodeAway":"C4","selection":"D"}]}],"jackpotMsg":""}
     * mode : Buy
     * sampleStatus : NO
     * advMessage : {}
     * fiscalazationTaxAmount : 0
     * fiscalazationTaxPercentage : 0
     */

    private int responseCode;
    private String responseMsg;
    private TktDataBean tktData;
    private String mode;
    private String sampleStatus;
    private AdvMessageBean advMessage;
    private int fiscalazationTaxAmount;
    private int fiscalazationTaxPercentage;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public TktDataBean getTktData() {
        return tktData;
    }

    public void setTktData(TktDataBean tktData) {
        this.tktData = tktData;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public AdvMessageBean getAdvMessage() {
        return advMessage;
    }

    public void setAdvMessage(AdvMessageBean advMessage) {
        this.advMessage = advMessage;
    }

    public int getFiscalazationTaxAmount() {
        return fiscalazationTaxAmount;
    }

    public void setFiscalazationTaxAmount(int fiscalazationTaxAmount) {
        this.fiscalazationTaxAmount = fiscalazationTaxAmount;
    }

    public int getFiscalazationTaxPercentage() {
        return fiscalazationTaxPercentage;
    }

    public void setFiscalazationTaxPercentage(int fiscalazationTaxPercentage) {
        this.fiscalazationTaxPercentage = fiscalazationTaxPercentage;
    }

    public static class TktDataBean {
        /**
         * purchaseDate : 2019-12-12
         * reprintCount : 0
         * retailerName : vineet org
         * currSymbol : USD
         * orgName : AFRICA LOTTO
         * expiryPeriod : 2019-12-13 15:45:00.0
         * purchaseTime : 15:04:03
         * ticketNo : 6913113461890000010
         * brCd : 6913113461890000010
         * trxId : 2381
         * ticketAmt : 1.00
         * balance : -0.94
         * gameId : 1
         * gameTypeId : 1
         * gameName : Soccer
         * gameTypeName : Soccer 13
         * eventType : [H, D, A]
         * boardData : [{"drawId":96,"drawName":"pool draw","drawDate":"2019-12-19","drawTime":"12:00:24","noOfLines":1,"boardPrice":1,"unitPrice":1,"eventData":[{"eventLeague":"Royal championship","eventVenue":"Camroon4","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon4","eventDisplayAway":"Camroon5","eventCodeHome":"C4","eventCodeAway":"C5","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon5","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon5","eventDisplayAway":"Camroon6","eventCodeHome":"C5","eventCodeAway":"C6","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon6","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon6","eventDisplayAway":"Camroon7","eventCodeHome":"C6","eventCodeAway":"C7","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon7","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon7","eventDisplayAway":"Camroon8","eventCodeHome":"C7","eventCodeAway":"C8","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon8","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon8","eventDisplayAway":"Camroon9","eventCodeHome":"C8","eventCodeAway":"C9","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon9","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon9","eventDisplayAway":"Camroon10","eventCodeHome":"C9","eventCodeAway":"C10","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon10","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon10","eventDisplayAway":"Camroon11","eventCodeHome":"C10","eventCodeAway":"C11","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon11","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon11","eventDisplayAway":"Camroon12","eventCodeHome":"C11","eventCodeAway":"C12","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon12","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon12","eventDisplayAway":"Camroon13","eventCodeHome":"C12","eventCodeAway":"C13","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon13","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon13","eventDisplayAway":"Camroon14","eventCodeHome":"C13","eventCodeAway":"C14","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon1","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon1","eventDisplayAway":"Camroon2","eventCodeHome":"C1","eventCodeAway":"C2","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon2","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon2","eventDisplayAway":"Camroon3","eventCodeHome":"C2","eventCodeAway":"C3","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon3","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon3","eventDisplayAway":"Camroon4","eventCodeHome":"C3","eventCodeAway":"C4","selection":"D"}]}]
         * jackpotMsg :
         */

        private String purchaseDate;
        private int reprintCount;
        private String retailerName;
        private String currSymbol;
        private String orgName;
        private String expiryPeriod;
        private String purchaseTime;
        private int    expiryDays;
        private String ticketNo;
        private String brCd;
        private String trxId;
        private String ticketAmt;
        private String balance;
        private int gameId;
        private int gameTypeId;
        private String gameName;
        private String gameTypeName;
        private String eventType;
        private String jackpotMsg;
        private List<BoardDataBean> boardData;

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public int getReprintCount() {
            return reprintCount;
        }

        public void setReprintCount(int reprintCount) {
            this.reprintCount = reprintCount;
        }

        public String getRetailerName() {
            return retailerName;
        }

        public void setRetailerName(String retailerName) {
            this.retailerName = retailerName;
        }

        public String getCurrSymbol() {
            return currSymbol;
        }

        public void setCurrSymbol(String currSymbol) {
            this.currSymbol = currSymbol;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getExpiryPeriod() {
            return expiryPeriod;
        }

        public void setExpiryPeriod(String expiryPeriod) {
            this.expiryPeriod = expiryPeriod;
        }

        public String getPurchaseTime() {
            return purchaseTime;
        }

        public void setPurchaseTime(String purchaseTime) {
            this.purchaseTime = purchaseTime;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public String getBrCd() {
            return brCd;
        }

        public void setBrCd(String brCd) {
            this.brCd = brCd;
        }

        public String getTrxId() {
            return trxId;
        }

        public void setTrxId(String trxId) {
            this.trxId = trxId;
        }

        public String getTicketAmt() {
            return ticketAmt;
        }

        public void setTicketAmt(String ticketAmt) {
            this.ticketAmt = ticketAmt;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public int getGameTypeId() {
            return gameTypeId;
        }

        public void setGameTypeId(int gameTypeId) {
            this.gameTypeId = gameTypeId;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getGameTypeName() {
            return gameTypeName;
        }

        public void setGameTypeName(String gameTypeName) {
            this.gameTypeName = gameTypeName;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getJackpotMsg() {
            return jackpotMsg;
        }

        public void setJackpotMsg(String jackpotMsg) {
            this.jackpotMsg = jackpotMsg;
        }

        public List<BoardDataBean> getBoardData() {
            return boardData;
        }

        public void setBoardData(List<BoardDataBean> boardData) {
            this.boardData = boardData;
        }

        public int getExpiryDays() {
            return expiryDays;
        }

        public void setExpiryDays(int expiryDays) {
            this.expiryDays = expiryDays;
        }

        public static class BoardDataBean {
            /**
             * drawId : 96
             * drawName : pool draw
             * drawDate : 2019-12-19
             * drawTime : 12:00:24
             * noOfLines : 1
             * boardPrice : 1
             * unitPrice : 1
             * eventData : [{"eventLeague":"Royal championship","eventVenue":"Camroon4","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon4","eventDisplayAway":"Camroon5","eventCodeHome":"C4","eventCodeAway":"C5","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon5","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon5","eventDisplayAway":"Camroon6","eventCodeHome":"C5","eventCodeAway":"C6","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon6","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon6","eventDisplayAway":"Camroon7","eventCodeHome":"C6","eventCodeAway":"C7","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon7","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon7","eventDisplayAway":"Camroon8","eventCodeHome":"C7","eventCodeAway":"C8","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon8","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon8","eventDisplayAway":"Camroon9","eventCodeHome":"C8","eventCodeAway":"C9","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon9","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon9","eventDisplayAway":"Camroon10","eventCodeHome":"C9","eventCodeAway":"C10","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon10","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon10","eventDisplayAway":"Camroon11","eventCodeHome":"C10","eventCodeAway":"C11","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon11","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon11","eventDisplayAway":"Camroon12","eventCodeHome":"C11","eventCodeAway":"C12","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon12","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon12","eventDisplayAway":"Camroon13","eventCodeHome":"C12","eventCodeAway":"C13","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon13","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon13","eventDisplayAway":"Camroon14","eventCodeHome":"C13","eventCodeAway":"C14","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon1","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon1","eventDisplayAway":"Camroon2","eventCodeHome":"C1","eventCodeAway":"C2","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon2","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon2","eventDisplayAway":"Camroon3","eventCodeHome":"C2","eventCodeAway":"C3","selection":"D"},{"eventLeague":"Royal championship","eventVenue":"Camroon3","eventDate":"2019-11-28 15:00:00.0","eventDisplayHome":"Camroon3","eventDisplayAway":"Camroon4","eventCodeHome":"C3","eventCodeAway":"C4","selection":"D"}]
             */

            private int drawId;
            private String drawName;
            private String drawDate;
            private String drawTime;
            private int noOfLines;
            private int boardPrice;
            private int unitPrice;
            private int betAmountMultiple;
            private List<EventDataBean> eventData;

            public int getDrawId() {
                return drawId;
            }

            public void setDrawId(int drawId) {
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

            public int getNoOfLines() {
                return noOfLines;
            }

            public void setNoOfLines(int noOfLines) {
                this.noOfLines = noOfLines;
            }

            public int getBoardPrice() {
                return boardPrice;
            }

            public void setBoardPrice(int boardPrice) {
                this.boardPrice = boardPrice;
            }

            public int getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(int unitPrice) {
                this.unitPrice = unitPrice;
            }

            public List<EventDataBean> getEventData() {
                return eventData;
            }

            public void setEventData(List<EventDataBean> eventData) {
                this.eventData = eventData;
            }

            public int getBetAmountMultiple() {
                return betAmountMultiple;
            }

            public void setBetAmountMultiple(int betAmountMultiple) {
                this.betAmountMultiple = betAmountMultiple;
            }

            public static class EventDataBean {
                /**
                 * eventLeague : Royal championship
                 * eventVenue : Camroon4
                 * eventDate : 2019-11-28 15:00:00.0
                 * eventDisplayHome : Camroon4
                 * eventDisplayAway : Camroon5
                 * eventCodeHome : C4
                 * eventCodeAway : C5
                 * selection : D
                 */

                private String eventLeague;
                private String eventVenue;
                private String eventDate;
                private String eventDisplayHome;
                private String eventDisplayAway;
                private String eventCodeHome;
                private String eventCodeAway;
                private String selection;

                public String getEventLeague() {
                    return eventLeague;
                }

                public void setEventLeague(String eventLeague) {
                    this.eventLeague = eventLeague;
                }

                public String getEventVenue() {
                    return eventVenue;
                }

                public void setEventVenue(String eventVenue) {
                    this.eventVenue = eventVenue;
                }

                public String getEventDate() {
                    return eventDate;
                }

                public void setEventDate(String eventDate) {
                    this.eventDate = eventDate;
                }

                public String getEventDisplayHome() {
                    return eventDisplayHome;
                }

                public void setEventDisplayHome(String eventDisplayHome) {
                    this.eventDisplayHome = eventDisplayHome;
                }

                public String getEventDisplayAway() {
                    return eventDisplayAway;
                }

                public void setEventDisplayAway(String eventDisplayAway) {
                    this.eventDisplayAway = eventDisplayAway;
                }

                public String getEventCodeHome() {
                    return eventCodeHome;
                }

                public void setEventCodeHome(String eventCodeHome) {
                    this.eventCodeHome = eventCodeHome;
                }

                public String getEventCodeAway() {
                    return eventCodeAway;
                }

                public void setEventCodeAway(String eventCodeAway) {
                    this.eventCodeAway = eventCodeAway;
                }

                public String getSelection() {
                    return selection;
                }

                public void setSelection(String selection) {
                    this.selection = selection;
                }
            }
        }
    }

    public static class AdvMessageBean {
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
    }
    public class Bottom {

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

    }

    public class Top {

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

    }
}
