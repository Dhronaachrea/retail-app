package com.skilrock.retailapp.sle_game_portrait;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.fragment.app.Fragment;

import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentDrawSelectionSle extends Fragment {

    private List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeans;
    private int currentDrawSelected = 0;
    private Context context;

    public FragmentDrawSelectionSle(Context context, List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeans){
        this.context = context;
        this.drawDataBeans = drawDataBeans;
    }

}
