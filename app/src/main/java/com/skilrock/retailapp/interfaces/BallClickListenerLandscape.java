package com.skilrock.retailapp.interfaces;

import android.widget.TextView;

import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

import java.util.ArrayList;

public interface BallClickListenerLandscape {

    void onBallClicked(TextView textView, String ballNumber, String ballColor, DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball ball);
    ArrayList<String> getSelectedList();
    ArrayList<String> getBankerList();
    boolean isQpSelected();
    void resetGameToManual();
}