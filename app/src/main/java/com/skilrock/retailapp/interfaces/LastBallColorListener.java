package com.skilrock.retailapp.interfaces;

import android.widget.TextView;

import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

public interface LastBallColorListener {

    void onLastBallClicked(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType);

}
