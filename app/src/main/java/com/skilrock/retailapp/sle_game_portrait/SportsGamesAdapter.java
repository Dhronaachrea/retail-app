package com.skilrock.retailapp.sle_game_portrait;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SportsGamesAdapter extends RecyclerView.Adapter<SportsGamesAdapter.GameViewHolder> implements AppConstants {

    private List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean> gameList;
    private Activity context;
    private GameViewHolder gameViewHolder;
    private String currentTime;
    private SportsListSelectionLis sportsListSelectionLis;


    public SportsGamesAdapter(Activity context, List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean> game, SportsListSelectionLis sportsListSelectionLis) {
        this.gameList           = game;
        this.context            = context;
        this.currentTime        = currentTime;
        this.sportsListSelectionLis = sportsListSelectionLis;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.draw_games_card, null, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        gameViewHolder = holder;
        gameViewHolder.tvName.setText(gameList.get(position).getGameTypeDisplayName());
        if(gameList.get(position).getGameTypeDevName().equalsIgnoreCase("soccer13")){
            gameViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.soccer_main));
        }else if(gameList.get(position).getGameTypeDevName().equalsIgnoreCase("soccer6")){
            gameViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.soccer_six));
        }else if(gameList.get(position).getGameTypeDevName().equalsIgnoreCase("soccer4")){
            gameViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.soccer_four));
        }
        gameViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportsListSelectionLis.gameName(gameList.get(position).getGameTypeDevName());
            }
        });
//        gameViewHolder.tvStartsIn.setVisibility(View.INVISIBLE);


    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void setTimer(String time){
        if(gameViewHolder != null){
            gameViewHolder.tvTimer.setText(time);
        }
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivIcon;
        LinearLayout container;
        CountDownTimer timer;
        TextView tvTimer;
        CardView cardView;
        TextView tvStartsIn;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvName      = view.findViewById(R.id.tv_module);
            ivIcon      = view.findViewById(R.id.ivIcon);
            container   = view.findViewById(R.id.ll_container);
            tvTimer     = view.findViewById(R.id.tvTimer);
            cardView    = view.findViewById(R.id.card_view);
            tvStartsIn  = view.findViewById(R.id.tvStartsIn);
        }
    }

    private String getFetchTime(DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> arrayList = gameRespVO.getDrawRespVOs();
        Comparator<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> compareByOrder =
                (o1, o2) -> o1.getDrawDateTime().compareTo(o2.getDrawDateTime());
        Collections.sort(arrayList, compareByOrder);
        return arrayList.get(0).getDrawSaleStopTime();
    }

    private long getTimeDifference(String fetchDate, String currentDate_) {
        long minutes = 0;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date futureDate = dateFormat.parse(fetchDate);
            Date currentDate = dateFormat.parse(currentDate_);
            long diff = futureDate.getTime() - currentDate.getTime();
            long seconds = diff / 1000;
            minutes = seconds / 60;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minutes * 1000 * 60;
    }

    public interface SportsListSelectionLis {
        void gameName(String gameName);
    }
}
