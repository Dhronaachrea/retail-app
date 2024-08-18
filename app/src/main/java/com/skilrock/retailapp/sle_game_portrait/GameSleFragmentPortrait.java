package com.skilrock.retailapp.sle_game_portrait;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skilrock.retailapp.R;

import java.util.List;

@SuppressLint("ValidFragment")
public class GameSleFragmentPortrait extends Fragment implements View.OnClickListener {

    private LinearLayout gameView,title_add_to_purchase;
    private RecyclerView rv_numbers;
    private CardView card_bet;
    private int numberOfColoumn = 2;
    private int currentDrawSelected = 0;
    private Context context;
    private TextView tvAddBet;
    private SleGamePlayAdapter sleGamePlayAdapter;
    private int totalNumberOfMatch = 3;
    private List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeans;

    public GameSleFragmentPortrait(Context context, int numberOfColoumn, List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeans, int totalNumberOfMatch){
        this.context = context;
        this.numberOfColoumn = numberOfColoumn;
        this.drawDataBeans = drawDataBeans;
        this.totalNumberOfMatch = totalNumberOfMatch;
    }

    private void resetData(){
        for(ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean eventDataBean:drawDataBeans.get(currentDrawSelected).getEventData()){
            eventDataBean.setAwayPlusSelected(false);
            eventDataBean.setAwaySelected(false);
            eventDataBean.setDrawSelected(false);
            eventDataBean.setHomeSelected(false);
            eventDataBean.setHomePlusSelected(false);
        }
        sleGamePlayAdapter.notifyDataSetChanged();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        gameView = (LinearLayout) inflater.inflate(R.layout.fragment_five_by_ninty_game,container,false);
        card_bet = gameView.findViewById(R.id.card_bet);
        tvAddBet = gameView.findViewById(R.id.tvAddBet);
        title_add_to_purchase = gameView.findViewById(R.id.title_add_to_purchase);
        if(drawDataBeans.size() > 1){
            title_add_to_purchase.setVisibility(View.VISIBLE);
            card_bet.setVisibility(View.GONE);
        }else if(drawDataBeans.size() == 1){
            title_add_to_purchase.setVisibility(View.GONE);
            card_bet.setVisibility(View.VISIBLE);
        }
        rv_numbers = gameView.findViewById(R.id.rv_numbers);
        rv_numbers.setLayoutManager(new GridLayoutManager(context,numberOfColoumn));
        sleGamePlayAdapter = new SleGamePlayAdapter(context,drawDataBeans.get(currentDrawSelected).getEventData(),totalNumberOfMatch);
        rv_numbers.setAdapter(sleGamePlayAdapter);

        tvAddBet.setOnClickListener(this);
        return gameView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvAddBet){
            resetData();
        }
    }
}
