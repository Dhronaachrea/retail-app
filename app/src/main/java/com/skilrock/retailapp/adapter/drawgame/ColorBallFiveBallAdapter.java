package com.skilrock.retailapp.adapter.drawgame;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.FiveBallColorListener;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.FiveByNinetyColors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ColorBallFiveBallAdapter extends RecyclerView.Adapter<ColorBallFiveBallAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> game;
    private Context context;
    private FiveBallColorListener listener;

    public ColorBallFiveBallAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> game,
                                    Context context, FiveBallColorListener listener) {
        this.game = game;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_color_ball, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType = game.get(position);

        if (pickType.getIsSelected() == 1) {
            holder.ball.setStrokeWidth(5);
            holder.ball.setText("");
            holder.ball.setStrokeColor(getStringBetween(pickType.getRange().get(0).getPickValue()));
            holder.ball.setSolidColor(getStringBetween(pickType.getRange().get(0).getPickValue()));
            holder.imageViewCheck.setVisibility(View.VISIBLE);
        } else {
            holder.ball.setStrokeWidth(5);
            holder.ball.setText("");
            holder.ball.setStrokeColor(getStringBetween(pickType.getRange().get(0).getPickValue()));
            holder.ball.setSolidColor("#FFFFFF");
            holder.imageViewCheck.setVisibility(View.GONE);
        }

        if (pickType.getName().toLowerCase().contains("black")) {
            holder.ball.setSolidColor("#FFFFFF");
        }

        holder.ball.setOnClickListener(v -> {
            listener.onFiveBallClicked(game.get(position));
            setSelected(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return game.size();
    }

    private void setSelected(int position) {
        for (int index = 0; index < game.size(); index++) {
            if (position == index)
                game.get(index).setIsSelected(1);
            else
                game.get(index).setIsSelected(0);
        }
    }

    private int getSelectedCount() {
        int count = 0;
        for (int index = 0; index < game.size(); index++) {
            if (game.get(index).getIsSelected() == 1)
                count++;
        }

        return count;
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        CircularTextView ball;
        ImageView imageViewCheck;

        GameViewHolder(@NonNull View view) {
            super(view);
            ball = view.findViewById(R.id.colorBall);
            imageViewCheck = view.findViewById(R.id.image_tick);
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

    private String getStringBetween(String input){
        input = input.substring(input.indexOf("<")+1, input.lastIndexOf("<"));

        return FiveByNinetyColors.getBallColor(input);
        //return input;
    }
}
