package com.skilrock.retailapp.adapter.drawgame;

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
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FiveByNinetyColors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LastWinningSpinAndWinAdapter extends RecyclerView.Adapter<LastWinningSpinAndWinAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> game;
    private Context context;
    private HashMap<String, String> mapNumberConfig = new HashMap<>();

    public LastWinningSpinAndWinAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> game, HashMap<String, String> mapNumberConfig, Context context) {
        this.game = game;
        this.mapNumberConfig = mapNumberConfig;
        this.context = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_spin_and_win_last_winning, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO lastDrawWinningResultVO = game.get(position);

        if (lastDrawWinningResultVO.getWinningNumber() == null) return;

        holder.tvNumber.setText(lastDrawWinningResultVO.getWinningNumber());
        holder.tvTime.setText(formatTime(lastDrawWinningResultVO.getLastDrawDateTime()));
        holder.tvNumber.setTextColor(context.getResources().getColor(R.color.white));

        holder.tvNumber.setBackgroundResource(R.drawable.spin_and_win_number);
        GradientDrawable background = (GradientDrawable) holder.tvNumber.getBackground();

        background.setColor(Color.parseColor(getBallColor(lastDrawWinningResultVO.getWinningNumber())));

        if (position == game.size() - 1)
            holder.viewSaperator.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return game.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvNumber;
        View viewSaperator;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvTime = view.findViewById(R.id.tv_time);
            tvNumber = view.findViewById(R.id.tvNumber);
            viewSaperator = view.findViewById(R.id.view_saperator);

        }
    }
    public String getBallColor(String ballNumber) {
        try {
            return FiveByNinetyColors.getBallColor(mapNumberConfig.get(ballNumber.trim()));
        } catch (Exception e) {
            return "#ff0000";
        }
    }

    private String formatTime(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("HH:mm");
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