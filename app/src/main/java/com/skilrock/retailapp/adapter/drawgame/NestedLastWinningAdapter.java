package com.skilrock.retailapp.adapter.drawgame;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.FiveByNinetyColors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;

public class NestedLastWinningAdapter extends RecyclerView.Adapter<NestedLastWinningAdapter.NestedGameViewHolder> implements AppConstants {

    private ArrayList<String> arrayList;
    private Context context;
    private HashMap<String, String> mapNumberConfig;
    private String gameCode, clover;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.RunTimeFlagInfo> timeFlagInfos;

    public NestedLastWinningAdapter(ArrayList<String> game, HashMap<String, String> mapNumberConfig,
                                    Context context, String betCode,  ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.RunTimeFlagInfo> timeFlagInfos) {
        this.arrayList          = game;
        this.mapNumberConfig    = mapNumberConfig;
        this.context            = context;
        this.gameCode           = betCode;
        this.timeFlagInfos      = timeFlagInfos;
    }

    @NonNull
    @Override
    public NestedGameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (gameCode.equalsIgnoreCase(AppConstants.LUCKY_SIX))
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_nested_last_winning_lucky_six, viewGroup, false);
        else
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_nested_last_winning, viewGroup, false);
        return new NestedGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedGameViewHolder holder, int position) {
        if (gameCode.equalsIgnoreCase(SUPER_KENO)) {
            if (position > 9) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(32, 5, 0, 0);
                holder.container.setLayoutParams(params);
            }
        } else {
           /*if(position == (arrayList.size() / 2) + 1){
               LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                       LinearLayout.LayoutParams.WRAP_CONTENT,
                       LinearLayout.LayoutParams.WRAP_CONTENT
               );
               params.setMargins(5, 5, 0, 0);
               holder.container.setLayoutParams(params);

           }*/
        }
        holder.ball.setStrokeWidth(3);
        holder.ball.setText(arrayList.get(position));
        holder.ball.setStrokeColor(getBallColor(arrayList.get(position)));
        holder.ball.setSolidColor("#90000000");

        if (gameCode.equalsIgnoreCase(LUCKY_SIX) && isCloverNumber(arrayList.get(position))) {
            holder.ball.setTextColor(context.getResources().getColor(R.color.white));
            if (holder.imageClover != null)
                holder.imageClover.setVisibility(View.VISIBLE);

            if (holder.textClover != null) {
                holder.textClover.setVisibility(View.VISIBLE);
                holder.textClover.setText(arrayList.get(position));
                holder.textClover.setTextColor(context.getResources().getColor(R.color.black));

            }
        }

        if (gameCode.equalsIgnoreCase(LUCKY_SIX))
            if (holder.imageSaperator != null && (position == arrayList.size() - 1 || position == (arrayList.size() / 2))) {
                holder.imageSaperator.setVisibility(View.GONE);
            }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class NestedGameViewHolder extends RecyclerView.ViewHolder {

        CircularTextView ball;
        LinearLayout container;
        TextView textClover;
        ImageView imageSaperator, imageClover;

        NestedGameViewHolder(@NonNull View view) {
            super(view);
            ball = view.findViewById(R.id.ball1text);
            container = view.findViewById(R.id.container);
            imageClover = view.findViewById(R.id.img_clover);
            textClover = view.findViewById(R.id.clover_number);
            imageSaperator = view.findViewById(R.id.image_saparator);
        }
    }

    private boolean isCloverNumber(String number) {
        try {
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.RunTimeFlagInfo runTimeFlagInfo : timeFlagInfos) {
                if (Integer.parseInt(runTimeFlagInfo.getBallValue().toString()) == Integer.parseInt(number))
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public String getBallColor(String ballNumber) {
        try {
            return FiveByNinetyColors.getBallColor(mapNumberConfig.get(ballNumber.trim()));
        }
        catch(Exception e) {
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