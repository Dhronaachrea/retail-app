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
import com.skilrock.retailapp.interfaces.BallClickListener;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.portrait_draw_games.ui.LuckySixMainBetActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class NumbersPortraitLuckySixAdapter extends RecyclerView.Adapter<NumbersPortraitLuckySixAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> listNumber;
    private Context context;
    private BallClickListener listener;
    private ArrayList<String> listQpNumbers;

    public NumbersPortraitLuckySixAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> listNumber, Context context, BallClickListener listener, ArrayList<String> listQpNumbers) {
        this.listNumber         = listNumber;
        this.context            = context;
        this.listener           = listener;
        this.listQpNumbers      = listQpNumbers;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dg_number_box_lucky_six, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            holder.tvNumber.getLayoutParams().height = 75;
            holder.tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        }
        else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            holder.tvNumber.getLayoutParams().height = 60;
            holder.tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        }

        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball ball = listNumber.get(position);
        holder.tvNumber.setText(ball.getNumber());
        holder.tvNumber.setTag(ball.getColor());

        holder.tvNumber.setBackgroundResource(R.drawable.bg_numbers);
        GradientDrawable background = (GradientDrawable) holder.tvNumber.getBackground();
        background.setColor(Color.parseColor("#ffffff"));

        if (listQpNumbers != null && !listQpNumbers.isEmpty()) {
            if (listQpNumbers.contains(ball.getNumber())) {
                background.setColor(Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())));
                background.setStroke(2, listQpNumbers.contains(ball.getNumber()) ? Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())) : Color.parseColor("#ffffff"));
                holder.tvNumber.setTypeface(null, Typeface.BOLD);
                holder.tvNumber.setTextColor(Color.parseColor("#ffffff"));
            }
            else {
                background.setColor(Color.parseColor("#ffffff"));
                background.setStroke(2, Color.parseColor("#aaaaaa"));
                holder.tvNumber.setTypeface(null, Typeface.NORMAL);
                holder.tvNumber.setTextColor(Color.parseColor("#aaaaaa"));
            }
        }
        else {
            background.setColor(Color.parseColor("#ffffff"));
            background.setStroke(2, Color.parseColor("#aaaaaa"));
            holder.tvNumber.setTypeface(null, Typeface.NORMAL);
            holder.tvNumber.setTextColor(Color.parseColor("#aaaaaa"));
        }

        holder.tvNumber.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - LuckySixMainBetActivity.LAST_CLICK_TIME < LuckySixMainBetActivity.CLICK_GAP) {
                return;
            }
            LuckySixMainBetActivity.LAST_CLICK_TIME = SystemClock.elapsedRealtime();
            listener.onBallClicked(holder.tvNumber, ball.getNumber(), FiveByNinetyColors.getBallColor(ball.getColor()));
        });
    }

    @Override
    public int getItemCount() {
        return listNumber.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvNumber = view.findViewById(R.id.tvNumber);
        }
    }

}
