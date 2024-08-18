package com.skilrock.retailapp.landscape_draw_games.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.landscape_draw_games.count_down_timers.MyCountDownTimer;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class DrawGameAdapter extends RecyclerView.Adapter<DrawGameAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> gameList;
    private Activity context;
    private GameSelectionListener listner;
    private GameViewHolder gameViewHolder;
    private String currentTime;
    private boolean isCreating;

    public DrawGameAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> game, Activity context, GameSelectionListener listener, String currentTime, boolean isCreating) {
        this.gameList = game;
        this.context = context;
        this.listner = listener;
        this.isCreating = isCreating;
        this.currentTime = currentTime;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_draw_game_menu, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        gameViewHolder = holder;
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO = gameList.get(position);
        switch (gameRespVO.getGameCode().toLowerCase()) {
            case LUCKY_SIX:
                holder.imageGame.setImageDrawable(context.getResources().getDrawable(R.drawable.lucky_6));
                break;
            case FULL_ROULETTE:
                holder.imageGame.setImageDrawable(context.getResources().getDrawable(R.drawable.spin_win));
                break;
            case FIVE_BY_NINETY:
                holder.imageGame.setImageDrawable(context.getResources().getDrawable(R.drawable.five_by_ninety));
                break;
            case THAI_LOTTERY:
                //holder.imageGame.setImageDrawable(context.getResources().getDrawable(R.drawable.thai_lottery));
                break;
            case SUPER_KENO:
                //holder.imageGame.setImageDrawable(context.getResources().getDrawable(R.drawable.super_keno));
                break;
        }

        if (gameRespVO.isSelected())
            holder.container.setBackgroundColor(Color.parseColor("#00143D"));
        else
            holder.container.setBackgroundColor(Color.parseColor("#012161"));

        holder.container.setOnClickListener(v -> {
            listner.onGameSelection(gameRespVO);
        });
        String fetchTime = getFetchTime(gameRespVO);

        if(!gameRespVO.isCountDownStarted()) {
            Log.d("setCountDStartedAdapter", gameRespVO.getGameCode());
            Log.d("setCountDCurrentTime", currentTime);

            if (holder.countDownTimer != null)
                holder.countDownTimer.cancel();

            holder.countDownTimer = MyCountDownTimer.getMyCountDownTimer(getTimeDifference(fetchTime, gameRespVO.getCurrentDate()), 1000);
            holder.countDownTimer.createCountDownTimer(holder.tvTime, gameRespVO.getGameCode(), true, context);
            gameRespVO.setCountDownStarted(true);
        }
    }

    public void setSelection(int index) {
        for (int i = 0; i < gameList.size(); i++) {
            if (i == index)
                gameList.get(i).setSelected(true);
            else
                gameList.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvTimeFormat;
        ImageView imageGame;
        LinearLayout container;
        MyCountDownTimer countDownTimer;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvTime = view.findViewById(R.id.tv_time);
            imageGame = view.findViewById(R.id.image_game);
            container = view.findViewById(R.id.container);
        }
    }

    private String getFetchTime(DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO) {
        ArrayList<  DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> arrayList = gameRespVO.getDrawRespVOs();
        Comparator<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> compareByOrder =
                (o1, o2) -> o1.getDrawDateTime().compareTo(o2.getDrawDateTime());
        Collections.sort(arrayList, compareByOrder);
        return arrayList.get(0).getDrawSaleStopTime();
    }

    private long getTimeDifference(String fetchDate, String currentDate_) {
        long minutes = 0;
        long diff = 0;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date futureDate = dateFormat.parse(fetchDate);
            Date currentDate = dateFormat.parse(currentDate_);

            diff = futureDate.getTime() - currentDate.getTime();
            /*long seconds = diff / 1000;
            minutes = seconds / 60;*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    public interface GameSelectionListener {
        void onGameSelection(DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGameObject);
    }
}
