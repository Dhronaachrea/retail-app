package com.skilrock.retailapp.portrait_draw_games.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.portrait_draw_games.ui.FiveByNinetyMainBetActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class PickTypePortraitFiveByNinetyAdapter extends RecyclerView.Adapter<PickTypePortraitFiveByNinetyAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType>  game;
    private Context context;
    private OnPickTypeClickListener clickListener;

    public PickTypePortraitFiveByNinetyAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType>  game, Context context, OnPickTypeClickListener clickListener) {
        this.game           = game;
        this.clickListener  = clickListener;
        this.context        = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pick_type_row_portrait, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO))
            holder.tvType.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570))
            holder.tvType.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);

        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType betRespVO = game.get(position);
        try {
            holder.tvType.setText(betRespVO.getName().replace("Balls",""));
        } catch(Exception e) {
            e.printStackTrace();
            holder.tvType.setText(betRespVO.getName());
        }

        if (betRespVO.getIsSelected() == 0) {
            holder.tvType.setBackground(context.getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            holder.tvType.setTextColor(Color.parseColor("#d30e24"));
            holder.tvType.setTypeface(null, Typeface.NORMAL);
        }
        else {
            holder.tvType.setBackground(context.getResources().getDrawable(R.drawable.solid_red_rounded));
            holder.tvType.setTextColor(Color.parseColor("#ffffff"));
            holder.tvType.setTypeface(null, Typeface.BOLD);
        }

        holder.tvType.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - FiveByNinetyMainBetActivity.LAST_CLICK_TIME < FiveByNinetyMainBetActivity.CLICK_GAP) {
                return;
            }
            FiveByNinetyMainBetActivity.LAST_CLICK_TIME = SystemClock.elapsedRealtime();
            setSelected(position);
            clickListener.onPickTypeClick(betRespVO);
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

    public void onClick(int position, boolean resetNumbersGrid){
        if (resetNumbersGrid)
            clickListener.onPickTypeClick(game.get(position));
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
        void onPickTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType);
    }
}
