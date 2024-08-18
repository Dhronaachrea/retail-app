package com.skilrock.retailapp.portrait_draw_games.adapter;

import android.content.Context;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.portrait_draw_games.ui.BetSelectionActivity;
import com.skilrock.retailapp.utils.AppConstants;

import java.util.ArrayList;

public class MainBetTypePortraitAdapter extends RecyclerView.Adapter<MainBetTypePortraitAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO>  game;
    private Context context;
    private OnMainBetTypeClickListener clickListener;

    public MainBetTypePortraitAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO>  game, Context context, OnMainBetTypeClickListener clickListener) {
        this.game           = game;
        this.clickListener  = clickListener;
        this.context        = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bet_type_row_portrait, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO = game.get(position);
        /*holder.tvType.setBackground(betRespVO.getIsSelected() == 0 ? context.getResources().getDrawable(R.drawable.background_bet_deselected) :
                context.getResources().getDrawable(R.drawable.background_bet_selected));*/

      /*  holder.tvType.setTextColor(betRespVO.getIsSelected() == 0 ? context.getResources().getColor(R.color.colorBlack) :
                context.getResources().getColor(R.color.colorWhite));*/

        holder.tvType.setText(betRespVO.getBetDispName());
        holder.tvType.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - BetSelectionActivity.LAST_CLICK_TIME < BetSelectionActivity.CLICK_GAP) {
                return;
            }
            BetSelectionActivity.LAST_CLICK_TIME = SystemClock.elapsedRealtime();
            setSelected(position);
            clickListener.onMainBetTypeClick(game.get(position));
            notifyDataSetChanged();
        });
    }

    private void setSelected(int position) {
        for (int index = 0; index < game.size(); index++) {
            if (position == index)
                game.get(index).setIsSelected(1);
            else
                game.get(index).setIsSelected(0);
        }
    }

    @Override
    public int getItemCount() {
        return game.size();
    }

    public void onClick(int position){
        clickListener.onMainBetTypeClick(game.get(position));
        setSelected(position);
        notifyDataSetChanged();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvType;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvType = view.findViewById(R.id.tvBetType);
        }
    }

    public interface OnMainBetTypeClickListener{
        void onMainBetTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO);
    }
}
