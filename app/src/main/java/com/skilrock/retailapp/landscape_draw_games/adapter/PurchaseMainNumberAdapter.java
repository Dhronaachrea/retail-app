package com.skilrock.retailapp.landscape_draw_games.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.LuckySixBallColorMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PurchaseMainNumberAdapter extends RecyclerView.Adapter<PurchaseMainNumberAdapter.NestedGameViewHolder> implements AppConstants {

    private ArrayList<String> arrayList;
    private Context context;
    private String gameCode;

    public PurchaseMainNumberAdapter(ArrayList<String> game, Context context, String gameCode) {
        this.arrayList          = game;
        this.context            = context;
        this.gameCode           = gameCode;
    }

    @NonNull
    @Override
    public NestedGameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_purchase_main_numbers, viewGroup, false);
        return new NestedGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedGameViewHolder holder, int position) {
        String number = arrayList.get(position);

        holder.ball.setText(number);
        holder.ball.setBackgroundResource(R.drawable.bg_purchase_main_numbers);
        GradientDrawable background = (GradientDrawable) holder.ball.getBackground();
        background.setColor(Color.parseColor(getBallColor(number)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class NestedGameViewHolder extends RecyclerView.ViewHolder {

        TextView ball;

        NestedGameViewHolder(@NonNull View view) {
            super(view);
            ball = view.findViewById(R.id.tvMainNumber);
        }
    }

    public String getBallColor(String ballNumber) {
        try {
            if (gameCode.equalsIgnoreCase(AppConstants.LUCKY_SIX))
                return FiveByNinetyColors.getBallColor(LuckySixBallColorMap.getMapNumberConfig().get(ballNumber.trim()));
            else
                return FiveByNinetyColors.getBallColor(LuckySixBallColorMap.getMapNumberConfigFiveByNinty().get(ballNumber.trim()));

        } catch (Exception e) {
            return "#ff0000";
        }
    }

    private String formatTime(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("EEEE , dd MMM yyyy HH:mm");
        Date date;
        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }
}
