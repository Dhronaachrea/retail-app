package com.skilrock.retailapp.sle_game_portrait;

import java.util.List;

public class SaleBean {

    /**
     * gameId : 1
     * gameName : Soccer13
     * userId : 6
     * gameTypeId : 1
     * noOfBoard : 1
     * playerName : 0707338966
     * merchantCode : Weaver
     * sessionId : R5O4a-lPNZ2FuBzZhRMHty2VnHjcMw2D_SuSxip34EQ
     * drawInfo : [{"drawId":40,"betAmtMul":1,"drawStatus":"ACTIVE","drawFreezeTime":"2019-11-07 16:00:00","drawDateTime":"2019-11-08 11:55:00","saleStartTime":"2019-11-07 11:55:00","eventData":[{"eventId":18,"eventSelected":"H"},{"eventId":19,"eventSelected":"H"},{"eventId":26,"eventSelected":"H"},{"eventId":24,"eventSelected":"H"},{"eventId":21,"eventSelected":"H"},{"eventId":9,"eventSelected":"H"},{"eventId":20,"eventSelected":"H"},{"eventId":23,"eventSelected":"H"},{"eventId":22,"eventSelected":"H"},{"eventId":25,"eventSelected":"H"},{"eventId":13,"eventSelected":"H"},{"eventId":8,"eventSelected":"H"},{"eventId":10,"eventSelected":"H"}]}]
     */

    private int gameId;
    private String gameName;
    private String userId;
    private int gameTypeId;
    private int noOfBoard;
    private String playerName;
    private String merchantCode;
    private String sessionId;

    public int getUnitePrice() {
        return unitePrice;
    }

    public void setUnitePrice(int unitePrice) {
        this.unitePrice = unitePrice;
    }

    private int unitePrice;
    private List<DrawInfoBean> drawInfo;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getGameTypeId() {
        return gameTypeId;
    }

    public void setGameTypeId(int gameTypeId) {
        this.gameTypeId = gameTypeId;
    }

    public int getNoOfBoard() {
        return noOfBoard;
    }

    public void setNoOfBoard(int noOfBoard) {
        this.noOfBoard = noOfBoard;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<DrawInfoBean> getDrawInfo() {
        return drawInfo;
    }

    public void setDrawInfo(List<DrawInfoBean> drawInfo) {
        this.drawInfo = drawInfo;
    }

    public static class DrawInfoBean {
        /**
         * drawId : 40
         * betAmtMul : 1
         * drawStatus : ACTIVE
         * drawFreezeTime : 2019-11-07 16:00:00
         * drawDateTime : 2019-11-08 11:55:00
         * saleStartTime : 2019-11-07 11:55:00
         * eventData : [{"eventId":18,"eventSelected":"H"},{"eventId":19,"eventSelected":"H"},{"eventId":26,"eventSelected":"H"},{"eventId":24,"eventSelected":"H"},{"eventId":21,"eventSelected":"H"},{"eventId":9,"eventSelected":"H"},{"eventId":20,"eventSelected":"H"},{"eventId":23,"eventSelected":"H"},{"eventId":22,"eventSelected":"H"},{"eventId":25,"eventSelected":"H"},{"eventId":13,"eventSelected":"H"},{"eventId":8,"eventSelected":"H"},{"eventId":10,"eventSelected":"H"}]
         */

        private int drawId;
        private int betAmtMul;
        private String drawStatus;
        private String drawFreezeTime;
        private String drawDateTime;
        private String saleStartTime;
        private List<EventDataBean> eventData;

        public int getDrawId() {
            return drawId;
        }

        public void setDrawId(int drawId) {
            this.drawId = drawId;
        }

        public int getBetAmtMul() {
            return betAmtMul;
        }

        public void setBetAmtMul(int betAmtMul) {
            this.betAmtMul = betAmtMul;
        }

        public String getDrawStatus() {
            return drawStatus;
        }

        public void setDrawStatus(String drawStatus) {
            this.drawStatus = drawStatus;
        }

        public String getDrawFreezeTime() {
            return drawFreezeTime;
        }

        public void setDrawFreezeTime(String drawFreezeTime) {
            this.drawFreezeTime = drawFreezeTime;
        }

        public String getDrawDateTime() {
            return drawDateTime;
        }

        public void setDrawDateTime(String drawDateTime) {
            this.drawDateTime = drawDateTime;
        }

        public String getSaleStartTime() {
            return saleStartTime;
        }

        public void setSaleStartTime(String saleStartTime) {
            this.saleStartTime = saleStartTime;
        }

        public List<EventDataBean> getEventData() {
            return eventData;
        }

        public void setEventData(List<EventDataBean> eventData) {
            this.eventData = eventData;
        }

        public static class EventDataBean {
            /**
             * eventId : 18
             * eventSelected : H
             */

            private int eventId;
            private String eventSelected;

            public boolean isHomeSelected() {
                return homeSelected;
            }

            public void setHomeSelected(boolean homeSelected) {
                this.homeSelected = homeSelected;
            }

            public boolean isDrawSelected() {
                return drawSelected;
            }

            public void setDrawSelected(boolean drawSelected) {
                this.drawSelected = drawSelected;
            }

            public boolean isHomePlusSelected() {
                return homePlusSelected;
            }

            public void setHomePlusSelected(boolean homePlusSelected) {
                this.homePlusSelected = homePlusSelected;
            }

            public boolean isAwaySelected() {
                return awaySelected;
            }

            public void setAwaySelected(boolean awaySelected) {
                this.awaySelected = awaySelected;
            }

            public boolean isAwayPlusSelected() {
                return awayPlusSelected;
            }

            public void setAwayPlusSelected(boolean awayPlusSelected) {
                this.awayPlusSelected = awayPlusSelected;
            }

            private boolean homeSelected = false;
            private boolean drawSelected = false;
            private boolean homePlusSelected = false;
            private boolean awaySelected = false;
            private boolean awayPlusSelected = false;

            public int getEventId() {
                return eventId;
            }

            public void setEventId(int eventId) {
                this.eventId = eventId;
            }

            public String getEventSelected() {
                return eventSelected;
            }

            public void setEventSelected(String eventSelected) {
                this.eventSelected = eventSelected;
            }
        }
    }
}
