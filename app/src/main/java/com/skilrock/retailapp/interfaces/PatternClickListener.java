package com.skilrock.retailapp.interfaces;

import android.widget.TextView;

import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

public interface PatternClickListener {

    void onPatternClicked(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType);

}
