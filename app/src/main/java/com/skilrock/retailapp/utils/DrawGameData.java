package com.skilrock.retailapp.utils;

import android.content.Context;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

import java.util.ArrayList;
import java.util.HashMap;

public class DrawGameData {

    private static DrawFetchGameDataResponseBean fullResponse = null;
    private static DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGame = null;
    private static HashMap<String, String> MAP_GAME_SCHEMA = new HashMap<>();

    public static DrawFetchGameDataResponseBean getFullResponse() {
        return fullResponse;
    }

    public static void setFullResponse(DrawFetchGameDataResponseBean fullResponse) {
        DrawGameData.fullResponse = fullResponse;
    }

    public static DrawFetchGameDataResponseBean.ResponseData.GameRespVO getSelectedGame() {
        return selectedGame;
    }

    public static void setSelectedGame(DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGame) {
        DrawGameData.selectedGame = selectedGame;

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> matchDetail = selectedGame.getGameSchemas().getMatchDetail();
        if (matchDetail != null) {
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail data: matchDetail)
                MAP_GAME_SCHEMA.put(data.getBetType(), data.getPrizeAmount());
        }
    }

    public static DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO getSelectedBet(String betCode) {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVOs: selectedGame.getBetRespVOs()) {
            if (betRespVOs.getBetCode().equalsIgnoreCase(betCode))
                return betRespVOs;
        }
        return null;
    }

    public static String getPriceForSideBet(String code, Context context) {
        String text = MAP_GAME_SCHEMA.get(code);
        if (text != null) {
            String formattedAmount = Utils.getBalanceToView(Double.parseDouble(text), Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
            return context.getString(R.string.pays) + " " + formattedAmount;
        }
        return null;
    }
}
