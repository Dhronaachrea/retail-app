package com.skilrock.retailapp.adapter.drawgame;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;

import java.util.ArrayList;

public class PickTypeAdapter extends RecyclerView.Adapter<PickTypeAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType>  game;
    private Context context;
    private OnPickTypeClickListener clickListener;

    public PickTypeAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType>  game, Context context, OnPickTypeClickListener clickListener) {
        this.game           = game;
        this.clickListener  = clickListener;
        this.context        = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bet_type_row, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType betRespVO = game.get(position);
        holder.tvType.setText(betRespVO.getName());
        holder.tvType.setBackground(betRespVO.getIsSelected() == 0 ? context.getResources().getDrawable(R.drawable.background_bet_deselected) :
                context.getResources().getDrawable(R.drawable.red_solid));

        holder.tvType.setTextColor(betRespVO.getIsSelected() == 0 ? context.getResources().getColor(R.color.colorBlack) :
                context.getResources().getColor(R.color.colorWhite));

        if (betRespVO.getIsSelected() == 1)
            holder.tvType.setTypeface(holder.tvType.getTypeface(), Typeface.BOLD);
        else
            holder.tvType.setTypeface(null, Typeface.NORMAL);

        // Animation expandIn = AnimationUtils.loadAnimation(context, R.anim.anim_pop);

        holder.tvType.setOnClickListener(v -> {
            setSelected(position);
            clickListener.onPickTypeClick(betRespVO, false);
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

    public void onClick(int position, boolean isManualClick){
        clickListener.onPickTypeClick(game.get(position), isManualClick);
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

    public interface OnPickTypeClickListener {
        void onPickTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType, boolean isManualClick);
    }
}