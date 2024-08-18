package com.skilrock.retailapp.adapter.drawgame;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.FirstColorBallSelectListener;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.FiveByNinetyColors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ColorBallAdapter extends RecyclerView.Adapter<ColorBallAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> game;
    private Context context;
    private FirstColorBallSelectListener listener;

    public ColorBallAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> game, Context context
            , FirstColorBallSelectListener listener) {
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
            holder.ball1.setStrokeWidth(3);
            holder.ball1.setText("");
            holder.ball1.setStrokeColor(getStringBetween(pickType.getRange().get(0).getPickValue()));
            holder.ball1.setSolidColor(getStringBetween(pickType.getRange().get(0).getPickValue()));
            holder.imageViewTick.setVisibility(View.VISIBLE);
        } else {
            holder.ball1.setStrokeWidth(3);
            holder.ball1.setText("");

            holder.ball1.setStrokeColor(getStringBetween(pickType.getRange().get(0).getPickValue()));
            holder.ball1.setSolidColor("#80FFFFFF");
            holder.imageViewTick.setVisibility(View.GONE);
        }

        if (pickType.getName().toLowerCase().contains("black")) {
            holder.ball1.setSolidColor("#FFFFFF");
        }

        holder.ball1.setOnClickListener(v -> {
            listener.onFirstBallClicked(game.get(position));
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


    class GameViewHolder extends RecyclerView.ViewHolder {

        CircularTextView ball1;
        ImageView imageViewTick;
        RelativeLayout container;

        GameViewHolder(@NonNull View view) {
            super(view);
            ball1 = view.findViewById(R.id.colorBall);
            imageViewTick = view.findViewById(R.id.image_tick);
            container = view.findViewById(R.id.container);
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
