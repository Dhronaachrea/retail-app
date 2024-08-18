package com.skilrock.retailapp.adapter.drawgame;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FiveByNinetyColors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;


public class LastWinningLuckySixAdapter extends RecyclerView.Adapter<LastWinningLuckySixAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> game;
    private Context context;
    private HashMap<String, String> mapNumberConfig = new HashMap<>();
    private String gameCode;

    public LastWinningLuckySixAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> game,
                                      HashMap<String, String> mapNumberConfig, Context context, String gameCode) {
        this.game = game;
        this.mapNumberConfig = mapNumberConfig;
        this.context = context;
        this.gameCode = gameCode;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_last_winning, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO lastDrawWinningResultVO = game.get(position);

        if (lastDrawWinningResultVO.getWinningNumber() == null || !lastDrawWinningResultVO.getWinningNumber().contains(",")) {
            holder.llContainer.setVisibility(View.GONE);
            return;
        }

        String[] winningNumbers = lastDrawWinningResultVO.getWinningNumber().split(",");

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(winningNumbers));
        NestedLastWinningAdapter nestedLastWinningAdapter = new NestedLastWinningAdapter(arrayList,
                mapNumberConfig, context, gameCode, lastDrawWinningResultVO.getRunTimeFlagInfo());
        holder.recyclerView.setAdapter(nestedLastWinningAdapter);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 18));

        holder.tvTime.setText(formatTime(lastDrawWinningResultVO.getLastDrawDateTime()));

        if (lastDrawWinningResultVO.getWinningMultiplierInfo().getMultiplierCode().equalsIgnoreCase("clear")) {
            holder.blueFlag.setVisibility(View.VISIBLE);
            holder.triColorFlag.setVisibility(View.GONE);
        } else {
            holder.blueFlag.setVisibility(View.GONE);
            holder.llFlag.setVisibility(View.VISIBLE);
            holder.tvWin.setText(String.format("Win:%sx", lastDrawWinningResultVO.getWinningMultiplierInfo().getValue()));
        }
    }

    @Override
    public int getItemCount() {
        return 1; // TODO: 22/11/19 remove hard coded one length
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvWin;
        LinearLayout llContainer, llFlag;
        ImageView triColorFlag, blueFlag;
        RecyclerView recyclerView;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvTime = view.findViewById(R.id.tvLastDrawTime);
            tvWin = view.findViewById(R.id.tv_win);
            triColorFlag = view.findViewById(R.id.image_flag_1);
            blueFlag = view.findViewById(R.id.image_flag_2);
            llContainer = view.findViewById(R.id.ll_container);
            llFlag = view.findViewById(R.id.view_flag);
            recyclerView = view.findViewById(R.id.recyclerView);
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat(" EEEE, dd MMM yyyy\n HH:mm");
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