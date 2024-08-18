package com.skilrock.retailapp.adapter.drawgame;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.FiveByNinetyColors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LastWinningFiveByNinetyAdapter extends RecyclerView.Adapter<LastWinningFiveByNinetyAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> game;
    private Context context;
    private HashMap<String, String> mapNumberConfig = new HashMap<>();

    public LastWinningFiveByNinetyAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> game, HashMap<String, String> mapNumberConfig, Context context) {
        this.game = game;
        this.mapNumberConfig = mapNumberConfig;
        this.context = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_draw_game, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO lastDrawWinningResultVO = game.get(position);

        if (lastDrawWinningResultVO.getWinningNumber() == null || !lastDrawWinningResultVO.getWinningNumber().contains(","))
            return;

        if (lastDrawWinningResultVO.getWinningNumber() == null || !lastDrawWinningResultVO.getWinningNumber().contains(","))
            return;

        String[] winningNumbers = lastDrawWinningResultVO.getWinningNumber().split(",");

        holder.ball1.setStrokeWidth(3);
        holder.ball1.setText(winningNumbers[0]);
        holder.ball1.setStrokeColor(getBallColor(winningNumbers[0]));
        holder.ball1.setSolidColor("#90000000");

        holder.ball2.setStrokeWidth(3);
        holder.ball2.setText(winningNumbers[1]);
        holder.ball2.setStrokeColor(getBallColor(winningNumbers[1]));
        holder.ball2.setSolidColor("#90000000");

        holder.ball3.setStrokeWidth(3);
        holder.ball3.setText(winningNumbers[2]);
        holder.ball3.setStrokeColor(getBallColor(winningNumbers[2]));
        holder.ball3.setSolidColor("#90000000");

        holder.ball4.setStrokeWidth(3);
        holder.ball4.setText(winningNumbers[3]);
        holder.ball4.setStrokeColor(getBallColor(winningNumbers[3]));
        holder.ball4.setSolidColor("#90000000");

        holder.ball5.setStrokeWidth(3);
        holder.ball5.setText(winningNumbers[4]);
        holder.ball5.setStrokeColor(getBallColor(winningNumbers[4]));
        holder.ball5.setSolidColor("#90000000");

        holder.tvTime.setText(formatTime(lastDrawWinningResultVO.getLastDrawDateTime()));
    }

    @Override
    public int getItemCount() {
        if (game.size() > 2)
            return 3;
        return game.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvTimeFormat;
        CircularTextView ball1, ball2, ball3, ball4, ball5;
        ImageView imageGame;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvTime = view.findViewById(R.id.tv_time);
            imageGame = view.findViewById(R.id.image_game);
            ball1 = view.findViewById(R.id.ball1text);
            ball2 = view.findViewById(R.id.ball2text);
            ball3 = view.findViewById(R.id.ball3text);
            ball4 = view.findViewById(R.id.ball4text);
            ball5 = view.findViewById(R.id.ball5text);
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