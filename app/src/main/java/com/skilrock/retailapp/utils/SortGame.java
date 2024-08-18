package com.skilrock.retailapp.utils;

import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

import java.util.Comparator;

public class SortGame implements Comparator<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> {

    @Override
    public int compare(DrawFetchGameDataResponseBean.ResponseData.GameRespVO o1, DrawFetchGameDataResponseBean.ResponseData.GameRespVO o2) {
        return Integer.parseInt(o1.getGameOrder()) - Integer.parseInt(o2.getGameOrder());
    }

}
