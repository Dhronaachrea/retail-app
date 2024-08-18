package com.skilrock.retailapp.sle_game_portrait;

import java.io.Serializable;
import java.util.List;

public class ResultPrintBean implements Serializable {

    /**
     * DrawWiseResultList : [{"claimEndDate":"","claimStartDate":"","drawDateTime":"05-12-2019 18:15:00","drawDisplayType":"[H, D, A]","drawFreezeTime":"","drawId":97,"drawName":"Pool","drawNo":0,"drawStatus":"","drawWinningDetail":[],"eventMasterList":[{"awayTeamCode":"BD1","awayTeamName":"Bangladesh1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"AUS1-vs-BD1","eventFullName":"Australia1-vs-Bangladesh1","eventId":1006,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"AUS1","homeTeamName":"Australia1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Australia1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"Eng1","awayTeamName":"England1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"BD1-vs-Eng1","eventFullName":"Bangladesh1-vs-England1","eventId":1007,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"BD1","homeTeamName":"Bangladesh1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Bangladesh1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"In1","awayTeamName":"India1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"Eng1-vs-In1","eventFullName":"England1-vs-India1","eventId":1008,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Eng1","homeTeamName":"England1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"England1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"NZ1","awayTeamName":"Newzeland1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-NZ1","eventFullName":"India1-vs-Newzeland1","eventId":1009,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"India1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"pak1","awayTeamName":"Pakistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"NZ1-vs-pak1","eventFullName":"Newzeland1-vs-Pakistan1","eventId":1010,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"NZ1","homeTeamName":"Newzeland1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Newzeland1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"pak1-vs-SA1","eventFullName":"Pakistan1-vs-South Africa1","eventId":1011,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"pak1","homeTeamName":"Pakistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Pakistan1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SL1","awayTeamName":"Srilanka1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"SA1-vs-SL1","eventFullName":"South Africa1-vs-Srilanka1","eventId":1012,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SA1","homeTeamName":"South Africa1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"South Africa1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"SL1-vs-WI1","eventFullName":"Srilanka1-vs-Westindies1","eventId":1013,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SL1","homeTeamName":"Srilanka1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Srilanka1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"ZM1","awayTeamName":"Zimwambe","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"WI1-vs-ZM1","eventFullName":"Westindies1-vs-Zimwambe","eventId":1014,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"WI1","homeTeamName":"Westindies1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Westindies1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"Afg1","awayTeamName":"Afganistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"ZM1-vs-Afg1","eventFullName":"Zimwambe-vs-Afganistan1","eventId":1015,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"ZM1","homeTeamName":"Zimwambe","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Zimwambe","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"AUS1","awayTeamName":"Australia1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"Afg1-vs-AUS1","eventFullName":"Afganistan1-vs-Australia1","eventId":1005,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Afg1","homeTeamName":"Afganistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Afganistan1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"pak1-vs-SA1","eventFullName":"Pakistan1-vs-South Africa1","eventId":1022,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"pak1","homeTeamName":"Pakistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:05:00","venueName":"Pakistan1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SL1","awayTeamName":"Srilanka1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"SA1-vs-SL1","eventFullName":"South Africa1-vs-Srilanka1","eventId":1023,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SA1","homeTeamName":"South Africa1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:05:00","venueName":"South Africa1","winninOptionCode":"H","winningOption":"HOME"}],"eventOptionMap":null,"gameTypeId":0,"gameTypeName":"","merchantId":0,"prizePoolAmount":0,"purchaseTableName":0,"saleStartTime":"","verificationDate":""},{"claimEndDate":"","claimStartDate":"","drawDateTime":"28-11-2019 11:45:00","drawDisplayType":"[H, D, A]","drawFreezeTime":"","drawId":95,"drawName":"S13","drawNo":0,"drawStatus":"","drawWinningDetail":[],"eventMasterList":[{"awayTeamCode":"In1","awayTeamName":"India1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"Eng1-vs-In1","eventFullName":"England1-vs-India1","eventId":977,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Eng1","homeTeamName":"England1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"England1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"NZ1","awayTeamName":"Newzeland1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-NZ1","eventFullName":"India1-vs-Newzeland1","eventId":978,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"India1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"pak1","awayTeamName":"Pakistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"NZ1-vs-pak1","eventFullName":"Newzeland1-vs-Pakistan1","eventId":979,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"NZ1","homeTeamName":"Newzeland1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Newzeland1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"pak1-vs-SA1","eventFullName":"Pakistan1-vs-South Africa1","eventId":980,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"pak1","homeTeamName":"Pakistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Pakistan1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"SL1","awayTeamName":"Srilanka1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"SA1-vs-SL1","eventFullName":"South Africa1-vs-Srilanka1","eventId":981,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SA1","homeTeamName":"South Africa1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"South Africa1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"SL1-vs-WI1","eventFullName":"Srilanka1-vs-Westindies1","eventId":983,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SL1","homeTeamName":"Srilanka1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Srilanka1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"ZM1","awayTeamName":"Zimwambe","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"WI1-vs-ZM1","eventFullName":"Westindies1-vs-Zimwambe","eventId":985,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"WI1","homeTeamName":"Westindies1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Westindies1","winninOptionCode":"C","winningOption":"CANCEL"},{"awayTeamCode":"Afg1","awayTeamName":"Afganistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"ZM1-vs-Afg1","eventFullName":"Zimwambe-vs-Afganistan1","eventId":986,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"ZM1","homeTeamName":"Zimwambe","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Zimwambe","winninOptionCode":"C","winningOption":"CANCEL"},{"awayTeamCode":"AUS1","awayTeamName":"Australia1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"WI1-vs-AUS1","eventFullName":"Westindies1-vs-Australia1","eventId":987,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"WI1","homeTeamName":"Westindies1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Westindies1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"BD1","awayTeamName":"Bangladesh1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"SL1-vs-BD1","eventFullName":"Srilanka1-vs-Bangladesh1","eventId":988,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SL1","homeTeamName":"Srilanka1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Srilanka1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"AUS1","awayTeamName":"Australia1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"Afg1-vs-AUS1","eventFullName":"Afganistan1-vs-Australia1","eventId":974,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Afg1","homeTeamName":"Afganistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Afganistan1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"BD1","awayTeamName":"Bangladesh1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"AUS1-vs-BD1","eventFullName":"Australia1-vs-Bangladesh1","eventId":975,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"AUS1","homeTeamName":"Australia1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Australia1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"Eng1","awayTeamName":"England1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"BD1-vs-Eng1","eventFullName":"Bangladesh1-vs-England1","eventId":976,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"BD1","homeTeamName":"Bangladesh1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:35:00","venueName":"Bangladesh1","winninOptionCode":"A","winningOption":"AWAY"}],"eventOptionMap":null,"gameTypeId":0,"gameTypeName":"","merchantId":0,"prizePoolAmount":0,"purchaseTableName":0,"saleStartTime":"","verificationDate":""},{"claimEndDate":"","claimStartDate":"","drawDateTime":"28-11-2019 11:15:00","drawDisplayType":"[H, D, A]","drawFreezeTime":"","drawId":94,"drawName":"S13","drawNo":0,"drawStatus":"","drawWinningDetail":[],"eventMasterList":[{"awayTeamCode":"pak1","awayTeamName":"Pakistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"BD1-vs-pak1","eventFullName":"Bangladesh1-vs-Pakistan1","eventId":958,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"BD1","homeTeamName":"Bangladesh1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"Bangladesh1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"pak1","awayTeamName":"Pakistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"NZ1-vs-pak1","eventFullName":"Newzeland1-vs-Pakistan1","eventId":959,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"NZ1","homeTeamName":"Newzeland1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"Newzeland1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"BD1-vs-SA1","eventFullName":"Bangladesh1-vs-South Africa1","eventId":960,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"BD1","homeTeamName":"Bangladesh1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"Bangladesh1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"SA1-vs-WI1","eventFullName":"South Africa1-vs-Westindies1","eventId":961,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SA1","homeTeamName":"South Africa1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"South Africa1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"SL1","awayTeamName":"Srilanka1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-SL1","eventFullName":"India1-vs-Srilanka1","eventId":963,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"India1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"ZM1","awayTeamName":"Zimwambe","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"WI1-vs-ZM1","eventFullName":"Westindies1-vs-Zimwambe","eventId":964,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"WI1","homeTeamName":"Westindies1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"Westindies1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"Eng1","awayTeamName":"England1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"SA1-vs-Eng1","eventFullName":"South Africa1-vs-England1","eventId":966,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SA1","homeTeamName":"South Africa1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"South Africa1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"Eng1-vs-WI1","eventFullName":"England1-vs-Westindies1","eventId":967,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Eng1","homeTeamName":"England1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"England1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"ZM1","awayTeamName":"Zimwambe","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"BD1-vs-ZM1","eventFullName":"Bangladesh1-vs-Zimwambe","eventId":968,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"BD1","homeTeamName":"Bangladesh1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"Bangladesh1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"BD1-vs-WI1","eventFullName":"Bangladesh1-vs-Westindies1","eventId":969,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"BD1","homeTeamName":"Bangladesh1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"Bangladesh1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"NZ1-vs-WI1","eventFullName":"Newzeland1-vs-Westindies1","eventId":970,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"NZ1","homeTeamName":"Newzeland1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"Newzeland1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"pak1-vs-WI1","eventFullName":"Pakistan1-vs-Westindies1","eventId":971,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"pak1","homeTeamName":"Pakistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"Pakistan1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"ZM1","awayTeamName":"Zimwambe","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-28 11:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"AUS1-vs-ZM1","eventFullName":"Australia1-vs-Zimwambe","eventId":972,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"AUS1","homeTeamName":"Australia1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"28-11-2019 11:05:00","venueName":"Australia1","winninOptionCode":"A","winningOption":"AWAY"}],"eventOptionMap":null,"gameTypeId":0,"gameTypeName":"","merchantId":0,"prizePoolAmount":0,"purchaseTableName":0,"saleStartTime":"","verificationDate":""},{"claimEndDate":"","claimStartDate":"","drawDateTime":"27-11-2019 10:35:00","drawDisplayType":"[H, D, A]","drawFreezeTime":"","drawId":89,"drawName":"S13","drawNo":0,"drawStatus":"","drawWinningDetail":[],"eventMasterList":[{"awayTeamCode":"Eng1","awayTeamName":"England1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"WI1-vs-Eng1","eventFullName":"Westindies1-vs-England1","eventId":874,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"WI1","homeTeamName":"Westindies1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"Westindies1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"ZM1","awayTeamName":"Zimwambe","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"pak1-vs-ZM1","eventFullName":"Pakistan1-vs-Zimwambe","eventId":875,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"pak1","homeTeamName":"Pakistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"Pakistan1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"NZ1","awayTeamName":"Newzeland1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-NZ1","eventFullName":"India1-vs-Newzeland1","eventId":876,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"India1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"Eng1","awayTeamName":"England1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-Eng1","eventFullName":"India1-vs-England1","eventId":877,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"India1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-SA1","eventFullName":"India1-vs-South Africa1","eventId":878,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"India1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"AUS1","awayTeamName":"Australia1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-AUS1","eventFullName":"India1-vs-Australia1","eventId":879,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"India1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"SL1","awayTeamName":"Srilanka1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-SL1","eventFullName":"India1-vs-Srilanka1","eventId":880,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"India1","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"pak1","awayTeamName":"Pakistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"ZM1-vs-pak1","eventFullName":"Zimwambe-vs-Pakistan1","eventId":881,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"ZM1","homeTeamName":"Zimwambe","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"Zimwambe","winninOptionCode":"D","winningOption":"DRAW"},{"awayTeamCode":"BD1","awayTeamName":"Bangladesh1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-BD1","eventFullName":"India1-vs-Bangladesh1","eventId":882,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"India1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"AUS1-vs-SA1","eventFullName":"Australia1-vs-South Africa1","eventId":884,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"AUS1","homeTeamName":"Australia1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"Australia1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"Eng1-vs-SA1","eventFullName":"England1-vs-South Africa1","eventId":871,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Eng1","homeTeamName":"England1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"England1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"BD1-vs-WI1","eventFullName":"Bangladesh1-vs-Westindies1","eventId":872,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"BD1","homeTeamName":"Bangladesh1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"Bangladesh1","winninOptionCode":"A","winningOption":"AWAY"},{"awayTeamCode":"NZ1","awayTeamName":"Newzeland1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-27 10:30:00.0","entryTime":"","eventDescription":"","eventDisplay":"Eng1-vs-NZ1","eventFullName":"England1-vs-Newzeland1","eventId":873,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Eng1","homeTeamName":"England1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"27-11-2019 10:25:00","venueName":"England1","winninOptionCode":"A","winningOption":"AWAY"}],"eventOptionMap":null,"gameTypeId":0,"gameTypeName":"","merchantId":0,"prizePoolAmount":0,"purchaseTableName":0,"saleStartTime":"","verificationDate":""},{"claimEndDate":"","claimStartDate":"","drawDateTime":"26-11-2019 18:45:00","drawDisplayType":"[H, D, A]","drawFreezeTime":"","drawId":88,"drawName":"S13","drawNo":0,"drawStatus":"","drawWinningDetail":[],"eventMasterList":[{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"NZ1-vs-WI1","eventFullName":"Newzeland1-vs-Westindies1","eventId":852,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"NZ1","homeTeamName":"Newzeland1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Newzeland1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SL1","awayTeamName":"Srilanka1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"Eng1-vs-SL1","eventFullName":"England1-vs-Srilanka1","eventId":853,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Eng1","homeTeamName":"England1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"England1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"WI1-vs-SA1","eventFullName":"Westindies1-vs-South Africa1","eventId":854,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"WI1","homeTeamName":"Westindies1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Westindies1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"pak1","awayTeamName":"Pakistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"NZ1-vs-pak1","eventFullName":"Newzeland1-vs-Pakistan1","eventId":855,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"NZ1","homeTeamName":"Newzeland1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Newzeland1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-SA1","eventFullName":"India1-vs-South Africa1","eventId":856,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"India1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"ZM1","awayTeamName":"Zimwambe","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"SL1-vs-ZM1","eventFullName":"Srilanka1-vs-Zimwambe","eventId":857,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SL1","homeTeamName":"Srilanka1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Srilanka1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"BD1","awayTeamName":"Bangladesh1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"SL1-vs-BD1","eventFullName":"Srilanka1-vs-Bangladesh1","eventId":859,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SL1","homeTeamName":"Srilanka1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Srilanka1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"NZ1","awayTeamName":"Newzeland1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"WI1-vs-NZ1","eventFullName":"Westindies1-vs-Newzeland1","eventId":862,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"WI1","homeTeamName":"Westindies1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Westindies1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"In1","awayTeamName":"India1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"Eng1-vs-In1","eventFullName":"England1-vs-India1","eventId":864,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Eng1","homeTeamName":"England1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"England1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"pak1","awayTeamName":"Pakistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"AUS1-vs-pak1","eventFullName":"Australia1-vs-Pakistan1","eventId":865,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"AUS1","homeTeamName":"Australia1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Australia1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"AUS1","awayTeamName":"Australia1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"WI1-vs-AUS1","eventFullName":"Westindies1-vs-Australia1","eventId":866,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"WI1","homeTeamName":"Westindies1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Westindies1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"NZ1","awayTeamName":"Newzeland1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"AUS1-vs-NZ1","eventFullName":"Australia1-vs-Newzeland1","eventId":867,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"AUS1","homeTeamName":"Australia1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Australia1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"NZ1","awayTeamName":"Newzeland1","awayTeamOdds":"","drawOdds":"","endTime":"2019-11-26 18:40:00.0","entryTime":"","eventDescription":"","eventDisplay":"BD1-vs-NZ1","eventFullName":"Bangladesh1-vs-Newzeland1","eventId":849,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"BD1","homeTeamName":"Bangladesh1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"26-11-2019 18:35:00","venueName":"Bangladesh1","winninOptionCode":"H","winningOption":"HOME"}],"eventOptionMap":null,"gameTypeId":0,"gameTypeName":"","merchantId":0,"prizePoolAmount":0,"purchaseTableName":0,"saleStartTime":"","verificationDate":""}]
     * gameTypeDispName : Soccer 13
     * responseCode : 0
     * responseMsg : SUCCESS
     */

    private String gameTypeDispName;
    private int responseCode;
    private String responseMsg;
    private List<DrawWiseResultListBean> DrawWiseResultList;

    public String getGameTypeDispName() {
        return gameTypeDispName;
    }

    public void setGameTypeDispName(String gameTypeDispName) {
        this.gameTypeDispName = gameTypeDispName;
    }

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

    public List<DrawWiseResultListBean> getDrawWiseResultList() {
        return DrawWiseResultList;
    }

    public void setDrawWiseResultList(List<DrawWiseResultListBean> DrawWiseResultList) {
        this.DrawWiseResultList = DrawWiseResultList;
    }

    public static class DrawWiseResultListBean implements  Serializable {
        /**
         * claimEndDate :
         * claimStartDate :
         * drawDateTime : 05-12-2019 18:15:00
         * drawDisplayType : [H, D, A]
         * drawFreezeTime :
         * drawId : 97
         * drawName : Pool
         * drawNo : 0
         * drawStatus :
         * drawWinningDetail : []
         * eventMasterList : [{"awayTeamCode":"BD1","awayTeamName":"Bangladesh1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"AUS1-vs-BD1","eventFullName":"Australia1-vs-Bangladesh1","eventId":1006,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"AUS1","homeTeamName":"Australia1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Australia1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"Eng1","awayTeamName":"England1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"BD1-vs-Eng1","eventFullName":"Bangladesh1-vs-England1","eventId":1007,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"BD1","homeTeamName":"Bangladesh1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Bangladesh1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"In1","awayTeamName":"India1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"Eng1-vs-In1","eventFullName":"England1-vs-India1","eventId":1008,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Eng1","homeTeamName":"England1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"England1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"NZ1","awayTeamName":"Newzeland1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"In1-vs-NZ1","eventFullName":"India1-vs-Newzeland1","eventId":1009,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"In1","homeTeamName":"India1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"India1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"pak1","awayTeamName":"Pakistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"NZ1-vs-pak1","eventFullName":"Newzeland1-vs-Pakistan1","eventId":1010,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"NZ1","homeTeamName":"Newzeland1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Newzeland1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"pak1-vs-SA1","eventFullName":"Pakistan1-vs-South Africa1","eventId":1011,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"pak1","homeTeamName":"Pakistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Pakistan1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SL1","awayTeamName":"Srilanka1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"SA1-vs-SL1","eventFullName":"South Africa1-vs-Srilanka1","eventId":1012,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SA1","homeTeamName":"South Africa1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"South Africa1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"WI1","awayTeamName":"Westindies1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"SL1-vs-WI1","eventFullName":"Srilanka1-vs-Westindies1","eventId":1013,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SL1","homeTeamName":"Srilanka1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Srilanka1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"ZM1","awayTeamName":"Zimwambe","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"WI1-vs-ZM1","eventFullName":"Westindies1-vs-Zimwambe","eventId":1014,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"WI1","homeTeamName":"Westindies1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Westindies1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"Afg1","awayTeamName":"Afganistan1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"ZM1-vs-Afg1","eventFullName":"Zimwambe-vs-Afganistan1","eventId":1015,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"ZM1","homeTeamName":"Zimwambe","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Zimwambe","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"AUS1","awayTeamName":"Australia1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:05:00.0","entryTime":"","eventDescription":"","eventDisplay":"Afg1-vs-AUS1","eventFullName":"Afganistan1-vs-Australia1","eventId":1005,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"Afg1","homeTeamName":"Afganistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:00:00","venueName":"Afganistan1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SA1","awayTeamName":"South Africa1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"pak1-vs-SA1","eventFullName":"Pakistan1-vs-South Africa1","eventId":1022,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"pak1","homeTeamName":"Pakistan1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:05:00","venueName":"Pakistan1","winninOptionCode":"H","winningOption":"HOME"},{"awayTeamCode":"SL1","awayTeamName":"Srilanka1","awayTeamOdds":"","drawOdds":"","endTime":"2019-12-05 18:10:00.0","entryTime":"","eventDescription":"","eventDisplay":"SA1-vs-SL1","eventFullName":"South Africa1-vs-Srilanka1","eventId":1023,"eventOptionsList":[],"favTeam":"","gameId":0,"homeTeamCode":"SA1","homeTeamName":"South Africa1","homeTeamOdds":"","leagueName":"worldcup2020","noOfEvents":0,"startTime":"05-12-2019 18:05:00","venueName":"South Africa1","winninOptionCode":"H","winningOption":"HOME"}]
         * eventOptionMap : null
         * gameTypeId : 0
         * gameTypeName :
         * merchantId : 0
         * prizePoolAmount : 0
         * purchaseTableName : 0
         * saleStartTime :
         * verificationDate :
         */

        private String claimEndDate;
        private String claimStartDate;
        private String drawDateTime;
        private String drawDisplayType;
        private String drawFreezeTime;
        private int drawId;
        private String drawName;
        private int drawNo;
        private String drawStatus;
        private Object eventOptionMap;
        private int gameTypeId;
        private String gameTypeName;
        private int merchantId;
        private int prizePoolAmount;
        private int purchaseTableName;
        private String saleStartTime;
        private String verificationDate;
        private List<?> drawWinningDetail;
        private List<EventMasterListBean> eventMasterList;

        public String getClaimEndDate() {
            return claimEndDate;
        }

        public void setClaimEndDate(String claimEndDate) {
            this.claimEndDate = claimEndDate;
        }

        public String getClaimStartDate() {
            return claimStartDate;
        }

        public void setClaimStartDate(String claimStartDate) {
            this.claimStartDate = claimStartDate;
        }

        public String getDrawDateTime() {
            return drawDateTime;
        }

        public void setDrawDateTime(String drawDateTime) {
            this.drawDateTime = drawDateTime;
        }

        public String getDrawDisplayType() {
            return drawDisplayType;
        }

        public void setDrawDisplayType(String drawDisplayType) {
            this.drawDisplayType = drawDisplayType;
        }

        public String getDrawFreezeTime() {
            return drawFreezeTime;
        }

        public void setDrawFreezeTime(String drawFreezeTime) {
            this.drawFreezeTime = drawFreezeTime;
        }

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

        public int getDrawNo() {
            return drawNo;
        }

        public void setDrawNo(int drawNo) {
            this.drawNo = drawNo;
        }

        public String getDrawStatus() {
            return drawStatus;
        }

        public void setDrawStatus(String drawStatus) {
            this.drawStatus = drawStatus;
        }

        public Object getEventOptionMap() {
            return eventOptionMap;
        }

        public void setEventOptionMap(Object eventOptionMap) {
            this.eventOptionMap = eventOptionMap;
        }

        public int getGameTypeId() {
            return gameTypeId;
        }

        public void setGameTypeId(int gameTypeId) {
            this.gameTypeId = gameTypeId;
        }

        public String getGameTypeName() {
            return gameTypeName;
        }

        public void setGameTypeName(String gameTypeName) {
            this.gameTypeName = gameTypeName;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public int getPrizePoolAmount() {
            return prizePoolAmount;
        }

        public void setPrizePoolAmount(int prizePoolAmount) {
            this.prizePoolAmount = prizePoolAmount;
        }

        public int getPurchaseTableName() {
            return purchaseTableName;
        }

        public void setPurchaseTableName(int purchaseTableName) {
            this.purchaseTableName = purchaseTableName;
        }

        public String getSaleStartTime() {
            return saleStartTime;
        }

        public void setSaleStartTime(String saleStartTime) {
            this.saleStartTime = saleStartTime;
        }

        public String getVerificationDate() {
            return verificationDate;
        }

        public void setVerificationDate(String verificationDate) {
            this.verificationDate = verificationDate;
        }

        public List<?> getDrawWinningDetail() {
            return drawWinningDetail;
        }

        public void setDrawWinningDetail(List<?> drawWinningDetail) {
            this.drawWinningDetail = drawWinningDetail;
        }

        public List<EventMasterListBean> getEventMasterList() {
            return eventMasterList;
        }

        public void setEventMasterList(List<EventMasterListBean> eventMasterList) {
            this.eventMasterList = eventMasterList;
        }

        public static class EventMasterListBean implements  Serializable {
            /**
             * awayTeamCode : BD1
             * awayTeamName : Bangladesh1
             * awayTeamOdds :
             * drawOdds :
             * endTime : 2019-12-05 18:05:00.0
             * entryTime :
             * eventDescription :
             * eventDisplay : AUS1-vs-BD1
             * eventFullName : Australia1-vs-Bangladesh1
             * eventId : 1006
             * eventOptionsList : []
             * favTeam :
             * gameId : 0
             * homeTeamCode : AUS1
             * homeTeamName : Australia1
             * homeTeamOdds :
             * leagueName : worldcup2020
             * noOfEvents : 0
             * startTime : 05-12-2019 18:00:00
             * venueName : Australia1
             * winninOptionCode : H
             * winningOption : HOME
             */

            private String awayTeamCode;
            private String awayTeamName;
            private String awayTeamOdds;
            private String drawOdds;
            private String endTime;
            private String entryTime;
            private String eventDescription;
            private String eventDisplay;
            private String eventFullName;
            private int eventId;
            private String favTeam;
            private int gameId;
            private String homeTeamCode;
            private String homeTeamName;
            private String homeTeamOdds;
            private String leagueName;
            private int noOfEvents;
            private String startTime;
            private String venueName;
            private String winninOptionCode;
            private String winningOption;
            private List<?> eventOptionsList;

            public String getAwayTeamCode() {
                return awayTeamCode;
            }

            public void setAwayTeamCode(String awayTeamCode) {
                this.awayTeamCode = awayTeamCode;
            }

            public String getAwayTeamName() {
                return awayTeamName;
            }

            public void setAwayTeamName(String awayTeamName) {
                this.awayTeamName = awayTeamName;
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

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getEntryTime() {
                return entryTime;
            }

            public void setEntryTime(String entryTime) {
                this.entryTime = entryTime;
            }

            public String getEventDescription() {
                return eventDescription;
            }

            public void setEventDescription(String eventDescription) {
                this.eventDescription = eventDescription;
            }

            public String getEventDisplay() {
                return eventDisplay;
            }

            public void setEventDisplay(String eventDisplay) {
                this.eventDisplay = eventDisplay;
            }

            public String getEventFullName() {
                return eventFullName;
            }

            public void setEventFullName(String eventFullName) {
                this.eventFullName = eventFullName;
            }

            public int getEventId() {
                return eventId;
            }

            public void setEventId(int eventId) {
                this.eventId = eventId;
            }

            public String getFavTeam() {
                return favTeam;
            }

            public void setFavTeam(String favTeam) {
                this.favTeam = favTeam;
            }

            public int getGameId() {
                return gameId;
            }

            public void setGameId(int gameId) {
                this.gameId = gameId;
            }

            public String getHomeTeamCode() {
                return homeTeamCode;
            }

            public void setHomeTeamCode(String homeTeamCode) {
                this.homeTeamCode = homeTeamCode;
            }

            public String getHomeTeamName() {
                return homeTeamName;
            }

            public void setHomeTeamName(String homeTeamName) {
                this.homeTeamName = homeTeamName;
            }

            public String getHomeTeamOdds() {
                return homeTeamOdds;
            }

            public void setHomeTeamOdds(String homeTeamOdds) {
                this.homeTeamOdds = homeTeamOdds;
            }

            public String getLeagueName() {
                return leagueName;
            }

            public void setLeagueName(String leagueName) {
                this.leagueName = leagueName;
            }

            public int getNoOfEvents() {
                return noOfEvents;
            }

            public void setNoOfEvents(int noOfEvents) {
                this.noOfEvents = noOfEvents;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getVenueName() {
                return venueName;
            }

            public void setVenueName(String venueName) {
                this.venueName = venueName;
            }

            public String getWinninOptionCode() {
                return winninOptionCode;
            }

            public void setWinninOptionCode(String winninOptionCode) {
                this.winninOptionCode = winninOptionCode;
            }

            public String getWinningOption() {
                return winningOption;
            }

            public void setWinningOption(String winningOption) {
                this.winningOption = winningOption;
            }

            public List<?> getEventOptionsList() {
                return eventOptionsList;
            }

            public void setEventOptionsList(List<?> eventOptionsList) {
                this.eventOptionsList = eventOptionsList;
            }
        }
    }
}
