package com.skilrock.retailapp.landscape_draw_games.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class ViewMoreResultBallsAdapter extends RecyclerView.Adapter<ViewMoreResultBallsAdapter.NestedGameViewHolder> implements AppConstants {

    private ArrayList<String> arrayList;
    private Context context;
    private HashMap<String, String> mapNumberConfig;
    private String type, gameCode;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.RunTimeFlagInfo> runTimeFlagInfos;

    public ViewMoreResultBallsAdapter(ArrayList<String> game, HashMap<String, String> mapNumberConfig,
                                      Context context, String type, String gameCode,ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.RunTimeFlagInfo> runTimeFlagInfos) {
        this.arrayList = game;
        this.mapNumberConfig = mapNumberConfig;
        this.context = context;
        this.type = type;
        this.gameCode = gameCode;
        this.runTimeFlagInfos = runTimeFlagInfos;
    }

    @NonNull
    @Override
    public NestedGameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (type.equalsIgnoreCase("bigger")) {
            if (gameCode.equalsIgnoreCase(FIVE_BY_NINETY))
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_view_more_ball_five_by_ninety, viewGroup, false);
            else
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_view_more_ball_bigger, viewGroup, false);
        } else {
            if (gameCode.equalsIgnoreCase(LUCKY_SIX))
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_view_more_result_ball, viewGroup, false);
            else
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_more_result_ball_super_keno, viewGroup, false);
        }

        return new NestedGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedGameViewHolder holder, int position) {
        if (type.equalsIgnoreCase("bigger")) {
            if (holder.viewSaperator != null && position == arrayList.size() - 1)
                holder.viewSaperator.setVisibility(View.GONE);
        }

        if (type.equalsIgnoreCase("bigger"))
            holder.ball.setStrokeWidth(5);
        else
            holder.ball.setStrokeWidth(3);
        holder.ball.setText(arrayList.get(position));
        holder.ball.setStrokeColor(getBallColor(arrayList.get(position)));
        holder.ball.setSolidColor("#FFFFFF");

        if (gameCode.equalsIgnoreCase(LUCKY_SIX) && isCloverNumber(arrayList.get(position))) {
            holder.ball.setTextColor(context.getResources().getColor(R.color.white));
            if (holder.imageClover != null)
                holder.imageClover.setVisibility(View.VISIBLE);

            if (holder.textClover != null) {
                holder.textClover.setVisibility(View.VISIBLE);
                holder.textClover.setText(arrayList.get(position));
            }
        }

        if (holder.imageSaperator != null && !type.equalsIgnoreCase("bigger") && (position == arrayList.size() - 1 || position == (arrayList.size() / 2) - 1)) {
            holder.imageSaperator.setVisibility(View.GONE);
        }
    }

    private boolean isCloverNumber(String number) {
        try {
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.RunTimeFlagInfo runTimeFlagInfo : runTimeFlagInfos) {
                if (Integer.parseInt(runTimeFlagInfo.getBallValue().toString()) == Integer.parseInt(number))
                    return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class NestedGameViewHolder extends RecyclerView.ViewHolder {

        CircularTextView ball;
        TextView textClover;
        ImageView imageSaperator, imageClover;
        RelativeLayout viewSaperator;

        NestedGameViewHolder(@NonNull View view) {
            super(view);
            ball = view.findViewById(R.id.ball1text);
            imageSaperator = view.findViewById(R.id.image_saparator);
            imageClover = view.findViewById(R.id.img_clover);
            viewSaperator = view.findViewById(R.id.view_saperator);
            textClover = view.findViewById(R.id.clover_number);
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
