package com.skilrock.retailapp.interfaces;

import android.widget.TextView;

import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

import java.util.ArrayList;

public interface FirstFiveBallColorListener {

    void onFirstFiveBallClicked( ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypes);

}
