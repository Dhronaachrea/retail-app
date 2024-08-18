package com.skilrock.retailapp.portrait_draw_games.adapter;

import android.app.Activity;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DrawGamesAdapter extends RecyclerView.Adapter<DrawGamesAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> gameList;
    private Activity context;
    private GameSelectionListener listner;
    private GameViewHolder gameViewHolder;
    private String currentTime;
    private DrawResultTime drawTimeListener;
    private boolean isShortestTime;

    public DrawGamesAdapter(Activity context, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> game, GameSelectionListener listener, String currentTime, DrawResultTime drawTimeListener) {
        this.gameList           = game;
        this.context            = context;
        this.listner            = listener;
        this.currentTime        = currentTime;
        this.drawTimeListener   = drawTimeListener;
        this.isShortestTime     = true;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.draw_games_card, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        gameViewHolder = holder;
        holder.tvStartsIn.setVisibility(View.VISIBLE);
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO = gameList.get(position);
        switch (gameRespVO.getGameCode().toLowerCase()) {
            case LUCKY_SIX:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.lucky_6));
                break;
            case SPIN_WIN:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.spin_win));
                break;
            case FIVE_BY_NINETY:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.five_by_ninety));
                break;
            case THAI_LOTTERY:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thai_lottery));
                break;
            case SUPER_KENO:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.super_keno));
                break;
        }

        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO))
            holder.tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570))
            holder.tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        holder.tvName.setText(gameRespVO.getGameName());
        holder.container.setOnClickListener(v -> listner.onGameSelection(gameRespVO));

        if (holder.timer != null) {
            holder.timer.cancel();
        }

        holder.timer = new CountDownTimer(Utils.getTimeDifference(getFetchTime(gameRespVO), currentTime), 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (millisUntilFinished / 1000);
                int mins = progress / 60;
                int hours = mins / 60;
                int days = hours / 24;

                String textMins = (mins % 60) + " " + context.getString(R.string.min) + " ";
                String textSeconds = (progress % 60) + " " + context.getString(R.string.sec);
                String textHours = hours > 1 ?  ((hours % 24) + " " + context.getString(R.string.hrs) + " ") : ((hours % 24) + " " + context.getString(R.string.hr) + " ");
                String textDays = days > 1 ? (days + " " + context.getString(R.string.days) + " ") : (days + " " + context.getString(R.string.day) + " ");

                if (days > 0)
                    holder.tvTimer.setText(textDays);
                else if (hours > 0)
                    holder.tvTimer.setText(mins > 0 ? (textHours + textMins) : (textHours));
                else if (mins > 0)
                    holder.tvTimer.setText((textMins + textSeconds));
                else
                    holder.tvTimer.setText(textSeconds);

            }

            @Override
            public void onFinish() {
                if (isShortestTime) {
                    isShortestTime = false;
                    drawTimeListener.onDrawResultTime(gameRespVO);
                    holder.tvStartsIn.setVisibility(View.GONE);
                    holder.tvTimer.setText(context.getString(R.string.updating_draw));
                }
            }
        }.start();

    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvStartsIn, tvTimer;
        ImageView ivIcon;
        LinearLayout container;
        CountDownTimer timer;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvName      = view.findViewById(R.id.tv_module);
            ivIcon      = view.findViewById(R.id.ivIcon);
            container   = view.findViewById(R.id.ll_container);
            tvTimer     = view.findViewById(R.id.tvTimer);
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

    public interface GameSelectionListener {
        void onGameSelection(DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGameObject);
    }

    public interface DrawResultTime {
        void onDrawResultTime(DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGameObject);
    }

}
