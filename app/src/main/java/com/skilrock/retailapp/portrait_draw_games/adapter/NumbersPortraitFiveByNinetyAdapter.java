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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.BallClickListener;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.portrait_draw_games.ui.FiveByNinetyMainBetActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class NumbersPortraitFiveByNinetyAdapter extends RecyclerView.Adapter<NumbersPortraitFiveByNinetyAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> listNumber;
    private Context context;
    private BallClickListener listener;
    private ArrayList<String> listQpNumbers;
    private ArrayList<String> listBankerActive, listBankerInActive;
    private boolean isBanker;

    public NumbersPortraitFiveByNinetyAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> listNumber, Context context, BallClickListener listener, ArrayList<String> listQpNumbers, boolean isBanker, ArrayList<String> listBankerActive, ArrayList<String> listBankerInActive) {
        this.listNumber         = listNumber;
        this.context            = context;
        this.listener           = listener;
        this.listQpNumbers      = listQpNumbers;
        this.listBankerActive   = listBankerActive;
        this.listBankerInActive = listBankerInActive;
        this.isBanker           = isBanker;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dg_number_box, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            holder.rlParent.getLayoutParams().height = 75;
            holder.tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        }
        else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            holder.rlParent.getLayoutParams().height = 60;
            holder.tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        }

        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball ball = listNumber.get(position);
        holder.tvNumber.setText(ball.getNumber());
        holder.tvNumber.setTag(ball.getColor());

        holder.tvNumber.setBackgroundResource(R.drawable.bg_numbers);
        GradientDrawable background = (GradientDrawable) holder.tvNumber.getBackground();
        background.setColor(Color.parseColor("#ffffff"));

        if (listQpNumbers != null && !listQpNumbers.isEmpty()) {
            holder.ivBlur.setVisibility(View.GONE);
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
        else if (isBanker) {
            if (listBankerActive != null && !listBankerActive.isEmpty() && listBankerActive.contains(ball.getNumber())) {
                holder.ivBlur.setVisibility(View.GONE);
                background.setColor(Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())));
                background.setStroke(2, listBankerActive.contains(ball.getNumber()) ? Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())) : Color.parseColor("#ffffff"));
                holder.tvNumber.setTypeface(null, Typeface.BOLD);
                holder.tvNumber.setTextColor(Color.parseColor("#ffffff"));
            }
            else if(listBankerInActive != null && !listBankerInActive.isEmpty() && listBankerInActive.contains(ball.getNumber())) {
                holder.ivBlur.setVisibility(View.VISIBLE);
                background.setColor(Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())));
                background.setStroke(2, listBankerInActive.contains(ball.getNumber()) ? Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())) : Color.parseColor("#ffffff"));
                holder.tvNumber.setTypeface(null, Typeface.BOLD);
                holder.tvNumber.setTextColor(Color.parseColor("#ffffff"));
            }else {
                holder.ivBlur.setVisibility(View.GONE);
                background.setColor(Color.parseColor("#ffffff"));
                background.setStroke(2, Color.parseColor("#aaaaaa"));
                holder.tvNumber.setTypeface(null, Typeface.NORMAL);
                holder.tvNumber.setTextColor(Color.parseColor("#aaaaaa"));
            }
        }
        else {
            holder.ivBlur.setVisibility(View.GONE);
            background.setColor(Color.parseColor("#ffffff"));
            background.setStroke(2, Color.parseColor("#aaaaaa"));
            holder.tvNumber.setTypeface(null, Typeface.NORMAL);
            holder.tvNumber.setTextColor(Color.parseColor("#aaaaaa"));
        }

        holder.tvNumber.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - FiveByNinetyMainBetActivity.LAST_CLICK_TIME < FiveByNinetyMainBetActivity.CLICK_GAP) {
                return;
            }
            FiveByNinetyMainBetActivity.LAST_CLICK_TIME = SystemClock.elapsedRealtime();
            listener.onBallClicked(holder.tvNumber, ball.getNumber(), FiveByNinetyColors.getBallColor(ball.getColor()));
        });
    }

    @Override
    public int getItemCount() {
        return listNumber.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber;
        RelativeLayout rlParent;
        ImageView ivBlur;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvNumber    = view.findViewById(R.id.tvNumber);
            rlParent    = view.findViewById(R.id.rlParent);
            ivBlur      = view.findViewById(R.id.ivBlur);
        }
    }

}
