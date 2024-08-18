package com.skilrock.retailapp.interfaces;


import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

public interface RefreshGameListener {
    void onShowLoader(String gameCode, boolean show);
    void onUpdateGame(DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO);
}