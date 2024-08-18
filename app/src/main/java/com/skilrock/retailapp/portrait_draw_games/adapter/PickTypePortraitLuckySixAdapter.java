package com.skilrock.retailapp.portrait_draw_games.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
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
import com.skilrock.retailapp.portrait_draw_games.ui.LuckySixMainBetActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class PickTypePortraitLuckySixAdapter extends RecyclerView.Adapter<PickTypePortraitLuckySixAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType>  game;
    private Context context;
    private OnPickTypeClickListener clickListener;

    public PickTypePortraitLuckySixAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType>  game, Context context, OnPickTypeClickListener clickListener) {
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
        setBottomColorBar(betRespVO, holder.tvBottomColor);
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
            if (SystemClock.elapsedRealtime() - LuckySixMainBetActivity.LAST_CLICK_TIME < LuckySixMainBetActivity.CLICK_GAP) {
                return;
            }
            LuckySixMainBetActivity.LAST_CLICK_TIME = SystemClock.elapsedRealtime();
            setSelected(position);
            clickListener.onPickTypeClick(betRespVO);
            notifyDataSetChanged();
        });
    }

    private void setBottomColorBar(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType betRespVO, TextView textView) {
        if (betRespVO.getCode().equalsIgnoreCase("RedBalls")) {
            textView.setVisibility(View.VISIBLE);
            setBottomColor(textView, FiveByNinetyColors.RED);
        }
        else if (betRespVO.getCode().equalsIgnoreCase("GreenBalls")) {
            textView.setVisibility(View.VISIBLE);
            setBottomColor(textView, FiveByNinetyColors.GREEN);
        }
        else if (betRespVO.getCode().equalsIgnoreCase("BlueBalls")) {
            textView.setVisibility(View.VISIBLE);
            setBottomColor(textView, FiveByNinetyColors.BLUE);
        }
        else if (betRespVO.getCode().equalsIgnoreCase("MagentaBalls")) {
            textView.setVisibility(View.VISIBLE);
            setBottomColor(textView, FiveByNinetyColors.MAGENTA);
        }
        else if (betRespVO.getCode().equalsIgnoreCase("BrownBalls")) {
            textView.setVisibility(View.VISIBLE);
            setBottomColor(textView, FiveByNinetyColors.BROWN);
        }
        else if (betRespVO.getCode().equalsIgnoreCase("CyanBalls")) {
            textView.setVisibility(View.VISIBLE);
            setBottomColor(textView, FiveByNinetyColors.CYAN);
        }
        else if (betRespVO.getCode().equalsIgnoreCase("OrangeBalls")) {
            textView.setVisibility(View.VISIBLE);
            setBottomColor(textView, FiveByNinetyColors.ORANGE);
        }
        else if (betRespVO.getCode().equalsIgnoreCase("GreyBalls")) {
            textView.setVisibility(View.VISIBLE);
            setBottomColor(textView, FiveByNinetyColors.GREY);
        }
        else {
            textView.setVisibility(View.GONE);
        }
    }

    private void setBottomColor(TextView textView, String color) {
        GradientDrawable background = (GradientDrawable) textView.getBackground();
        background.setColor(Color.parseColor(FiveByNinetyColors.getBallColor(color)));
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

        TextView tvType, tvBottomColor;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvType          = view.findViewById(R.id.tvBetType);
            tvBottomColor   = view.findViewById(R.id.tvBottomColor);
        }
    }

    public interface OnPickTypeClickListener {
        void onPickTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType);
    }
}
