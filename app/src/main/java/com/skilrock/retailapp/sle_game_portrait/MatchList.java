package com.skilrock.retailapp.sle_game_portrait;

import java.io.Serializable;
import java.util.List;

public class MatchList implements Serializable {

    /**
     * responseMsg : success
     * responseCode : 0
     * matchListData : {"gameData":[{"gameId":1,"gameDevname":"SOCCER","gameDisplayName":"Soccer","gameTypeData":[{"gameTypeId":1,"gameTypeDevName":"soccer13","gameTypeDisplayName":"Soccer 13","eventType":"[H, D, A]","upcomingDrawStartTime":"","areEventsMappedForUpcomingDraw":false,"drawData":[{"drawId":90,"drawNumber":29,"drawDateTime":"30-11-2019 10:49:00","drawDisplayString":"Soccer 13","eventData":[{"eventId":169,"eventDisplay":"ajau-vs-mils","eventDiscription":"ajau-vs-mils","eventLeague":"Myanmaar","eventVenue":"AJ Auxerre","startTime":"15-11-2019 10:00:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"AJ Auxerre","eventCodeHome":"ajau","eventDisplayAway":"Milsami orhei","eventCodeAway":"mils","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":170,"eventDisplay":"gle-vs-hbto","eventDiscription":"gle-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:35:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":173,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":171,"eventDisplay":"ener-vs-csk","eventDiscription":"ener-vs-csk","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":174,"eventDisplay":"gle-vs-ARRT","eventDiscription":"gle-vs-ARRT","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"FC Ararat","eventCodeAway":"ARRT","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":177,"eventDisplay":"ener-vs-kryl","eventDiscription":"ener-vs-kryl","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:50:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":178,"eventDisplay":"dumb-vs-kryl","eventDiscription":"dumb-vs-kryl","eventLeague":"Myanmaar","eventVenue":"dumbaton","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"dumbaton","eventCodeHome":"dumb","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":179,"eventDisplay":"ener-vs-INDY","eventDiscription":"ener-vs-INDY","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":180,"eventDisplay":"ARRT-vs-hbto","eventDiscription":"ARRT-vs-hbto","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":181,"eventDisplay":"CKRK-vs-INDY","eventDiscription":"CKRK-vs-INDY","eventLeague":"Myanmaar","eventVenue":"Cukaricki","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"Cukaricki","eventCodeHome":"CKRK","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":183,"eventDisplay":"CKRK-vs-hbto","eventDiscription":"CKRK-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Cukaricki","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"Cukaricki","eventCodeHome":"CKRK","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":184,"eventDisplay":"ARRT-vs-MM11","eventDiscription":"ARRT-vs-MM11","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"Myanmaar11","eventCodeAway":"MM11","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":185,"eventDisplay":"dny-vs-csk","eventDiscription":"dny-vs-csk","eventLeague":"Myanmaar","eventVenue":"dnyapro mchz","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"dnyapro mchz","eventCodeHome":"dny","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]}]},{"gameTypeId":2,"gameTypeDevName":"soccer6","gameTypeDisplayName":"Soccer 6","eventType":"[D, H+, H, A+, A]","upcomingDrawStartTime":"","areEventsMappedForUpcomingDraw":false,"drawData":[{"drawId":91,"drawNumber":30,"drawDateTime":"15-11-2019 23:55:00","drawDisplayString":"Soccer 6","eventData":[{"eventId":169,"eventDisplay":"ajau-vs-mils","eventDiscription":"ajau-vs-mils","eventLeague":"Myanmaar","eventVenue":"AJ Auxerre","startTime":"15-11-2019 10:00:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"AJ Auxerre","eventCodeHome":"ajau","eventDisplayAway":"Milsami orhei","eventCodeAway":"mils","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":170,"eventDisplay":"gle-vs-hbto","eventDiscription":"gle-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:35:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":173,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":171,"eventDisplay":"ener-vs-csk","eventDiscription":"ener-vs-csk","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":174,"eventDisplay":"gle-vs-ARRT","eventDiscription":"gle-vs-ARRT","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"FC Ararat","eventCodeAway":"ARRT","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":180,"eventDisplay":"ARRT-vs-hbto","eventDiscription":"ARRT-vs-hbto","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]},{"drawId":92,"drawNumber":31,"drawDateTime":"30-11-2019 00:00:00","drawDisplayString":"Soccer 6","eventData":[{"eventId":191,"eventDisplay":"ajau-vs-akhm","eventDiscription":"ajau-vs-akhm","eventLeague":"Myanmaar","eventVenue":"AJ Auxerre","startTime":"18-11-2019 00:05:00","endTime":"20-11-2019 18:20:00","eventDisplayHome":"AJ Auxerre","eventCodeHome":"ajau","eventDisplayAway":"akhmat grozny","eventCodeAway":"akhm","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":192,"eventDisplay":"dny-vs-kryl","eventDiscription":"dny-vs-kryl","eventLeague":"Myanmaar","eventVenue":"dnyapro mchz","startTime":"18-11-2019 03:20:00","endTime":"20-11-2019 18:05:00","eventDisplayHome":"dnyapro mchz","eventCodeHome":"dny","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":196,"eventDisplay":"csk-vs-INDY","eventDiscription":"csk-vs-INDY","eventLeague":"Myanmaar","eventVenue":"cska sofia","startTime":"18-11-2019 04:15:00","endTime":"20-11-2019 22:25:00","eventDisplayHome":"cska sofia","eventCodeHome":"csk","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":197,"eventDisplay":"CCTA-vs-hbto","eventDiscription":"CCTA-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Cucuta Dep","startTime":"18-11-2019 05:40:00","endTime":"22-11-2019 18:15:00","eventDisplayHome":"Cucuta Dep","eventCodeHome":"CCTA","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":195,"eventDisplay":"dumb-vs-INDY","eventDiscription":"dumb-vs-INDY","eventLeague":"Myanmaar","eventVenue":"dumbaton","startTime":"18-11-2019 06:20:00","endTime":"20-11-2019 23:25:00","eventDisplayHome":"dumbaton","eventCodeHome":"dumb","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":194,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"18-11-2019 06:35:00","endTime":"20-11-2019 20:20:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]}]},{"gameTypeId":3,"gameTypeDevName":"soccer4","gameTypeDisplayName":"Soccer 4","eventType":"[H+, H, D, A, A+]","upcomingDrawStartTime":"","areEventsMappedForUpcomingDraw":false,"drawData":[{"drawId":93,"drawNumber":32,"drawDateTime":"30-11-2019 19:00:00","drawDisplayString":"S4","eventData":[{"eventId":195,"eventDisplay":"dumb-vs-INDY","eventDiscription":"dumb-vs-INDY","eventLeague":"Myanmaar","eventVenue":"dumbaton","startTime":"18-11-2019 06:20:00","endTime":"20-11-2019 23:25:00","eventDisplayHome":"dumbaton","eventCodeHome":"dumb","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":194,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"18-11-2019 06:35:00","endTime":"20-11-2019 20:20:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":199,"eventDisplay":"gle-vs-ener","eventDiscription":"gle-vs-ener","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"18-11-2019 09:00:00","endTime":"21-11-2019 18:10:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"energetyk bdu","eventCodeAway":"ener","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":193,"eventDisplay":"anna-vs-hbto","eventDiscription":"anna-vs-hbto","eventLeague":"Myanmaar","eventVenue":"annan athletic","startTime":"18-11-2019 10:35:00","endTime":"21-11-2019 18:20:00","eventDisplayHome":"annan athletic","eventCodeHome":"anna","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]}]}]}]}
     */

    private String responseMsg;
    private int responseCode;
    private MatchListDataBean matchListData;

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public MatchListDataBean getMatchListData() {
        return matchListData;
    }

    public void setMatchListData(MatchListDataBean matchListData) {
        this.matchListData = matchListData;
    }

    public static class MatchListDataBean {
        private List<GameDataBean> gameData;

        public List<GameDataBean> getGameData() {
            return gameData;
        }

        public void setGameData(List<GameDataBean> gameData) {
            this.gameData = gameData;
        }

        public static class GameDataBean {
            /**
             * gameId : 1
             * gameDevname : SOCCER
             * gameDisplayName : Soccer
             * gameTypeData : [{"gameTypeId":1,"gameTypeDevName":"soccer13","gameTypeDisplayName":"Soccer 13","eventType":"[H, D, A]","upcomingDrawStartTime":"","areEventsMappedForUpcomingDraw":false,"drawData":[{"drawId":90,"drawNumber":29,"drawDateTime":"30-11-2019 10:49:00","drawDisplayString":"Soccer 13","eventData":[{"eventId":169,"eventDisplay":"ajau-vs-mils","eventDiscription":"ajau-vs-mils","eventLeague":"Myanmaar","eventVenue":"AJ Auxerre","startTime":"15-11-2019 10:00:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"AJ Auxerre","eventCodeHome":"ajau","eventDisplayAway":"Milsami orhei","eventCodeAway":"mils","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":170,"eventDisplay":"gle-vs-hbto","eventDiscription":"gle-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:35:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":173,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":171,"eventDisplay":"ener-vs-csk","eventDiscription":"ener-vs-csk","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":174,"eventDisplay":"gle-vs-ARRT","eventDiscription":"gle-vs-ARRT","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"FC Ararat","eventCodeAway":"ARRT","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":177,"eventDisplay":"ener-vs-kryl","eventDiscription":"ener-vs-kryl","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:50:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":178,"eventDisplay":"dumb-vs-kryl","eventDiscription":"dumb-vs-kryl","eventLeague":"Myanmaar","eventVenue":"dumbaton","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"dumbaton","eventCodeHome":"dumb","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":179,"eventDisplay":"ener-vs-INDY","eventDiscription":"ener-vs-INDY","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":180,"eventDisplay":"ARRT-vs-hbto","eventDiscription":"ARRT-vs-hbto","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":181,"eventDisplay":"CKRK-vs-INDY","eventDiscription":"CKRK-vs-INDY","eventLeague":"Myanmaar","eventVenue":"Cukaricki","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"Cukaricki","eventCodeHome":"CKRK","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":183,"eventDisplay":"CKRK-vs-hbto","eventDiscription":"CKRK-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Cukaricki","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"Cukaricki","eventCodeHome":"CKRK","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":184,"eventDisplay":"ARRT-vs-MM11","eventDiscription":"ARRT-vs-MM11","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"Myanmaar11","eventCodeAway":"MM11","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":185,"eventDisplay":"dny-vs-csk","eventDiscription":"dny-vs-csk","eventLeague":"Myanmaar","eventVenue":"dnyapro mchz","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"dnyapro mchz","eventCodeHome":"dny","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]}]},{"gameTypeId":2,"gameTypeDevName":"soccer6","gameTypeDisplayName":"Soccer 6","eventType":"[D, H+, H, A+, A]","upcomingDrawStartTime":"","areEventsMappedForUpcomingDraw":false,"drawData":[{"drawId":91,"drawNumber":30,"drawDateTime":"15-11-2019 23:55:00","drawDisplayString":"Soccer 6","eventData":[{"eventId":169,"eventDisplay":"ajau-vs-mils","eventDiscription":"ajau-vs-mils","eventLeague":"Myanmaar","eventVenue":"AJ Auxerre","startTime":"15-11-2019 10:00:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"AJ Auxerre","eventCodeHome":"ajau","eventDisplayAway":"Milsami orhei","eventCodeAway":"mils","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":170,"eventDisplay":"gle-vs-hbto","eventDiscription":"gle-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:35:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":173,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":171,"eventDisplay":"ener-vs-csk","eventDiscription":"ener-vs-csk","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":174,"eventDisplay":"gle-vs-ARRT","eventDiscription":"gle-vs-ARRT","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"FC Ararat","eventCodeAway":"ARRT","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":180,"eventDisplay":"ARRT-vs-hbto","eventDiscription":"ARRT-vs-hbto","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]},{"drawId":92,"drawNumber":31,"drawDateTime":"30-11-2019 00:00:00","drawDisplayString":"Soccer 6","eventData":[{"eventId":191,"eventDisplay":"ajau-vs-akhm","eventDiscription":"ajau-vs-akhm","eventLeague":"Myanmaar","eventVenue":"AJ Auxerre","startTime":"18-11-2019 00:05:00","endTime":"20-11-2019 18:20:00","eventDisplayHome":"AJ Auxerre","eventCodeHome":"ajau","eventDisplayAway":"akhmat grozny","eventCodeAway":"akhm","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":192,"eventDisplay":"dny-vs-kryl","eventDiscription":"dny-vs-kryl","eventLeague":"Myanmaar","eventVenue":"dnyapro mchz","startTime":"18-11-2019 03:20:00","endTime":"20-11-2019 18:05:00","eventDisplayHome":"dnyapro mchz","eventCodeHome":"dny","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":196,"eventDisplay":"csk-vs-INDY","eventDiscription":"csk-vs-INDY","eventLeague":"Myanmaar","eventVenue":"cska sofia","startTime":"18-11-2019 04:15:00","endTime":"20-11-2019 22:25:00","eventDisplayHome":"cska sofia","eventCodeHome":"csk","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":197,"eventDisplay":"CCTA-vs-hbto","eventDiscription":"CCTA-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Cucuta Dep","startTime":"18-11-2019 05:40:00","endTime":"22-11-2019 18:15:00","eventDisplayHome":"Cucuta Dep","eventCodeHome":"CCTA","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":195,"eventDisplay":"dumb-vs-INDY","eventDiscription":"dumb-vs-INDY","eventLeague":"Myanmaar","eventVenue":"dumbaton","startTime":"18-11-2019 06:20:00","endTime":"20-11-2019 23:25:00","eventDisplayHome":"dumbaton","eventCodeHome":"dumb","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":194,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"18-11-2019 06:35:00","endTime":"20-11-2019 20:20:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]}]},{"gameTypeId":3,"gameTypeDevName":"soccer4","gameTypeDisplayName":"Soccer 4","eventType":"[H+, H, D, A, A+]","upcomingDrawStartTime":"","areEventsMappedForUpcomingDraw":false,"drawData":[{"drawId":93,"drawNumber":32,"drawDateTime":"30-11-2019 19:00:00","drawDisplayString":"S4","eventData":[{"eventId":195,"eventDisplay":"dumb-vs-INDY","eventDiscription":"dumb-vs-INDY","eventLeague":"Myanmaar","eventVenue":"dumbaton","startTime":"18-11-2019 06:20:00","endTime":"20-11-2019 23:25:00","eventDisplayHome":"dumbaton","eventCodeHome":"dumb","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":194,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"18-11-2019 06:35:00","endTime":"20-11-2019 20:20:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":199,"eventDisplay":"gle-vs-ener","eventDiscription":"gle-vs-ener","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"18-11-2019 09:00:00","endTime":"21-11-2019 18:10:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"energetyk bdu","eventCodeAway":"ener","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":193,"eventDisplay":"anna-vs-hbto","eventDiscription":"anna-vs-hbto","eventLeague":"Myanmaar","eventVenue":"annan athletic","startTime":"18-11-2019 10:35:00","endTime":"21-11-2019 18:20:00","eventDisplayHome":"annan athletic","eventCodeHome":"anna","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]}]}]
             */

            private int gameId;
            private String gameDevname;
            private String gameDisplayName;
            private List<GameTypeDataBean> gameTypeData;

            public int getGameId() {
                return gameId;
            }

            public void setGameId(int gameId) {
                this.gameId = gameId;
            }

            public String getGameDevname() {
                return gameDevname;
            }

            public void setGameDevname(String gameDevname) {
                this.gameDevname = gameDevname;
            }

            public String getGameDisplayName() {
                return gameDisplayName;
            }

            public void setGameDisplayName(String gameDisplayName) {
                this.gameDisplayName = gameDisplayName;
            }

            public List<GameTypeDataBean> getGameTypeData() {
                return gameTypeData;
            }

            public void setGameTypeData(List<GameTypeDataBean> gameTypeData) {
                this.gameTypeData = gameTypeData;
            }

            public static class GameTypeDataBean {
                /**
                 * gameTypeId : 1
                 * gameTypeDevName : soccer13
                 * gameTypeDisplayName : Soccer 13
                 * eventType : [H, D, A]
                 * upcomingDrawStartTime :
                 * areEventsMappedForUpcomingDraw : false
                 * drawData : [{"drawId":90,"drawNumber":29,"drawDateTime":"30-11-2019 10:49:00","drawDisplayString":"Soccer 13","eventData":[{"eventId":169,"eventDisplay":"ajau-vs-mils","eventDiscription":"ajau-vs-mils","eventLeague":"Myanmaar","eventVenue":"AJ Auxerre","startTime":"15-11-2019 10:00:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"AJ Auxerre","eventCodeHome":"ajau","eventDisplayAway":"Milsami orhei","eventCodeAway":"mils","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":170,"eventDisplay":"gle-vs-hbto","eventDiscription":"gle-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:35:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":173,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":171,"eventDisplay":"ener-vs-csk","eventDiscription":"ener-vs-csk","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":174,"eventDisplay":"gle-vs-ARRT","eventDiscription":"gle-vs-ARRT","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"FC Ararat","eventCodeAway":"ARRT","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":177,"eventDisplay":"ener-vs-kryl","eventDiscription":"ener-vs-kryl","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:50:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":178,"eventDisplay":"dumb-vs-kryl","eventDiscription":"dumb-vs-kryl","eventLeague":"Myanmaar","eventVenue":"dumbaton","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"dumbaton","eventCodeHome":"dumb","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":179,"eventDisplay":"ener-vs-INDY","eventDiscription":"ener-vs-INDY","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":180,"eventDisplay":"ARRT-vs-hbto","eventDiscription":"ARRT-vs-hbto","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":181,"eventDisplay":"CKRK-vs-INDY","eventDiscription":"CKRK-vs-INDY","eventLeague":"Myanmaar","eventVenue":"Cukaricki","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"Cukaricki","eventCodeHome":"CKRK","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":183,"eventDisplay":"CKRK-vs-hbto","eventDiscription":"CKRK-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Cukaricki","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"Cukaricki","eventCodeHome":"CKRK","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":184,"eventDisplay":"ARRT-vs-MM11","eventDiscription":"ARRT-vs-MM11","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"Myanmaar11","eventCodeAway":"MM11","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":185,"eventDisplay":"dny-vs-csk","eventDiscription":"dny-vs-csk","eventLeague":"Myanmaar","eventVenue":"dnyapro mchz","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"dnyapro mchz","eventCodeHome":"dny","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]}]
                 */

                private int gameTypeId;
                private String gameTypeDevName;
                private String gameTypeDisplayName;
                private String eventType;
                private String upcomingDrawStartTime;
                private boolean areEventsMappedForUpcomingDraw;
                private List<DrawDataBean> drawData;

                public int getGameTypeId() {
                    return gameTypeId;
                }

                public void setGameTypeId(int gameTypeId) {
                    this.gameTypeId = gameTypeId;
                }

                public String getGameTypeDevName() {
                    return gameTypeDevName;
                }

                public void setGameTypeDevName(String gameTypeDevName) {
                    this.gameTypeDevName = gameTypeDevName;
                }

                public String getGameTypeDisplayName() {
                    return gameTypeDisplayName;
                }

                public void setGameTypeDisplayName(String gameTypeDisplayName) {
                    this.gameTypeDisplayName = gameTypeDisplayName;
                }

                public String getEventType() {
                    return eventType;
                }

                public void setEventType(String eventType) {
                    this.eventType = eventType;
                }

                public String getUpcomingDrawStartTime() {
                    return upcomingDrawStartTime;
                }

                public void setUpcomingDrawStartTime(String upcomingDrawStartTime) {
                    this.upcomingDrawStartTime = upcomingDrawStartTime;
                }

                public boolean isAreEventsMappedForUpcomingDraw() {
                    return areEventsMappedForUpcomingDraw;
                }

                public void setAreEventsMappedForUpcomingDraw(boolean areEventsMappedForUpcomingDraw) {
                    this.areEventsMappedForUpcomingDraw = areEventsMappedForUpcomingDraw;
                }

                public List<DrawDataBean> getDrawData() {
                    return drawData;
                }

                public void setDrawData(List<DrawDataBean> drawData) {
                    this.drawData = drawData;
                }

                public static class DrawDataBean {
                    /**
                     * drawId : 90
                     * drawNumber : 29
                     * drawDateTime : 30-11-2019 10:49:00
                     * drawDisplayString : Soccer 13
                     * eventData : [{"eventId":169,"eventDisplay":"ajau-vs-mils","eventDiscription":"ajau-vs-mils","eventLeague":"Myanmaar","eventVenue":"AJ Auxerre","startTime":"15-11-2019 10:00:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"AJ Auxerre","eventCodeHome":"ajau","eventDisplayAway":"Milsami orhei","eventCodeAway":"mils","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":170,"eventDisplay":"gle-vs-hbto","eventDiscription":"gle-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:35:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":173,"eventDisplay":"ener-vs-hbto","eventDiscription":"ener-vs-hbto","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:40:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":171,"eventDisplay":"ener-vs-csk","eventDiscription":"ener-vs-csk","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:15:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":174,"eventDisplay":"gle-vs-ARRT","eventDiscription":"gle-vs-ARRT","eventLeague":"Myanmaar","eventVenue":"Gletoran fc","startTime":"15-11-2019 13:55:00","endTime":"15-11-2019 14:10:00","eventDisplayHome":"Gletoran fc","eventCodeHome":"gle","eventDisplayAway":"FC Ararat","eventCodeAway":"ARRT","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":177,"eventDisplay":"ener-vs-kryl","eventDiscription":"ener-vs-kryl","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:50:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":178,"eventDisplay":"dumb-vs-kryl","eventDiscription":"dumb-vs-kryl","eventLeague":"Myanmaar","eventVenue":"dumbaton","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"dumbaton","eventCodeHome":"dumb","eventDisplayAway":"krylya sovetov","eventCodeAway":"kryl","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":179,"eventDisplay":"ener-vs-INDY","eventDiscription":"ener-vs-INDY","eventLeague":"Myanmaar","eventVenue":"energetyk bdu","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"energetyk bdu","eventCodeHome":"ener","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":180,"eventDisplay":"ARRT-vs-hbto","eventDiscription":"ARRT-vs-hbto","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":181,"eventDisplay":"CKRK-vs-INDY","eventDiscription":"CKRK-vs-INDY","eventLeague":"Myanmaar","eventVenue":"Cukaricki","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"Cukaricki","eventCodeHome":"CKRK","eventDisplayAway":"Indy Eleven ","eventCodeAway":"INDY","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":183,"eventDisplay":"CKRK-vs-hbto","eventDiscription":"CKRK-vs-hbto","eventLeague":"Myanmaar","eventVenue":"Cukaricki","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"Cukaricki","eventCodeHome":"CKRK","eventDisplayAway":"hb torshavn","eventCodeAway":"hbto","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":184,"eventDisplay":"ARRT-vs-MM11","eventDiscription":"ARRT-vs-MM11","eventLeague":"Myanmaar","eventVenue":"FC Ararat","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"FC Ararat","eventCodeHome":"ARRT","eventDisplayAway":"Myanmaar11","eventCodeAway":"MM11","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""},{"eventId":185,"eventDisplay":"dny-vs-csk","eventDiscription":"dny-vs-csk","eventLeague":"Myanmaar","eventVenue":"dnyapro mchz","startTime":"15-11-2019 14:00:00","endTime":"15-11-2019 14:20:00","eventDisplayHome":"dnyapro mchz","eventCodeHome":"dny","eventDisplayAway":"cska sofia","eventCodeAway":"csk","homeTeamOdds":"","awayTeamOdds":"","drawOdds":"","favTeam":""}]
                     */

                    private int drawId;
                    private int drawNumber;
                    private String drawDateTime;
                    private String drawDisplayString;
                    private List<EventDataBean> eventData;

                    public int getDrawId() {
                        return drawId;
                    }

                    public void setDrawId(int drawId) {
                        this.drawId = drawId;
                    }

                    public int getDrawNumber() {
                        return drawNumber;
                    }

                    public void setDrawNumber(int drawNumber) {
                        this.drawNumber = drawNumber;
                    }

                    public String getDrawDateTime() {
                        return drawDateTime;
                    }

                    public void setDrawDateTime(String drawDateTime) {
                        this.drawDateTime = drawDateTime;
                    }

                    public String getDrawDisplayString() {
                        return drawDisplayString;
                    }

                    public void setDrawDisplayString(String drawDisplayString) {
                        this.drawDisplayString = drawDisplayString;
                    }

                    public List<EventDataBean> getEventData() {
                        return eventData;
                    }

                    public void setEventData(List<EventDataBean> eventData) {
                        this.eventData = eventData;
                    }

                    public static class EventDataBean {
                        /**
                         * eventId : 169
                         * eventDisplay : ajau-vs-mils
                         * eventDiscription : ajau-vs-mils
                         * eventLeague : Myanmaar
                         * eventVenue : AJ Auxerre
                         * startTime : 15-11-2019 10:00:00
                         * endTime : 15-11-2019 14:10:00
                         * eventDisplayHome : AJ Auxerre
                         * eventCodeHome : ajau
                         * eventDisplayAway : Milsami orhei
                         * eventCodeAway : mils
                         * homeTeamOdds :
                         * awayTeamOdds :
                         * drawOdds :
                         * favTeam :
                         */

                        private int eventId;
                        private String eventDisplay;
                        private String eventDiscription;
                        private String eventLeague;
                        private String eventVenue;
                        private String startTime;
                        private String endTime;
                        private String eventDisplayHome;
                        private String eventCodeHome;
                        private String eventDisplayAway;
                        private String eventCodeAway;
                        private String homeTeamOdds;
                        private String awayTeamOdds;
                        private String drawOdds;
                        private String favTeam;

                        public int getEventId() {
                            return eventId;
                        }

                        public void setEventId(int eventId) {
                            this.eventId = eventId;
                        }

                        public String getEventDisplay() {
                            return eventDisplay;
                        }

                        public void setEventDisplay(String eventDisplay) {
                            this.eventDisplay = eventDisplay;
                        }

                        public String getEventDiscription() {
                            return eventDiscription;
                        }

                        public void setEventDiscription(String eventDiscription) {
                            this.eventDiscription = eventDiscription;
                        }

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

                        public String getStartTime() {
                            return startTime;
                        }

                        public void setStartTime(String startTime) {
                            this.startTime = startTime;
                        }

                        public String getEndTime() {
                            return endTime;
                        }

                        public void setEndTime(String endTime) {
                            this.endTime = endTime;
                        }

                        public String getEventDisplayHome() {
                            return eventDisplayHome;
                        }

                        public void setEventDisplayHome(String eventDisplayHome) {
                            this.eventDisplayHome = eventDisplayHome;
                        }

                        public String getEventCodeHome() {
                            return eventCodeHome;
                        }

                        public void setEventCodeHome(String eventCodeHome) {
                            this.eventCodeHome = eventCodeHome;
                        }

                        public String getEventDisplayAway() {
                            return eventDisplayAway;
                        }

                        public void setEventDisplayAway(String eventDisplayAway) {
                            this.eventDisplayAway = eventDisplayAway;
                        }

                        public String getEventCodeAway() {
                            return eventCodeAway;
                        }

                        public void setEventCodeAway(String eventCodeAway) {
                            this.eventCodeAway = eventCodeAway;
                        }

                        public String getHomeTeamOdds() {
                            return homeTeamOdds;
                        }

                        public void setHomeTeamOdds(String homeTeamOdds) {
                            this.homeTeamOdds = homeTeamOdds;
                        }

                        public String getAwayTeamOdds() {
                            return awayTeamOdds;
                        }

                        public void setAwayTeamOdds(String awayTeamOdds) {
                            this.awayTeamOdds = awayTeamOdds;
                        }

                        public String getDrawOdds() {
                            return drawOdds;
                        }

                        public void setDrawOdds(String drawOdds) {
                            this.drawOdds = drawOdds;
                        }

                        public String getFavTeam() {
                            return favTeam;
                        }

                        public void setFavTeam(String favTeam) {
                            this.favTeam = favTeam;
                        }
                    }
                }
            }
        }
    }
}
