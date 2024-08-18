package com.skilrock.retailapp.adapter.drawgame;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.PatternClickListener;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;

import java.util.ArrayList;

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> game;
    private Context context;
    private PatternClickListener clickListener;

    public PatternAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> game,
                          Context context, PatternClickListener clickListener) {
        this.game = game;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_pattern, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType = game.get(position);

        holder.row.setBackground(pickType.getIsSelected() == 0 ? context.getResources().getDrawable(R.drawable.background_bet_deselected) :
                context.getResources().getDrawable(R.drawable.background_bet_selected));

        switch (pickType.getCode().toLowerCase()) {
            case "increasing":
                holder.imagePattern.setImageDrawable(context.getResources().getDrawable(R.drawable.increasing));
                break;
            case "decreasing":
                holder.imagePattern.setImageDrawable(context.getResources().getDrawable(R.drawable.decreasing));
                break;
            case "hillpattern":
                holder.imagePattern.setImageDrawable(context.getResources().getDrawable(R.drawable.hill));
                break;
            case "valley":
                holder.imagePattern.setImageDrawable(context.getResources().getDrawable(R.drawable.random));
                break;
        }

        holder.tvPatternName.setText(pickType.getName());

        holder.row.setOnClickListener(v -> {
            clickListener.onPatternClicked(game.get(position));
            setSelected(position);
            notifyDataSetChanged();
        });
    }

    private void setSelected(int position) {
        for (int index = 0; index < game.size(); index++) {
            if (position == index)
                game.get(index).setIsSelected(1);
            else
                game.get(index).setIsSelected(0);
        }
    }

    @Override
    public int getItemCount() {
        return game.size();
    }


    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvPatternName, tvPrice;
        ImageView imagePattern;
        LinearLayout row;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvPatternName = view.findViewById(R.id.tv_pattern_name);
            tvPrice = view.findViewById(R.id.tv_pattern_price);
            imagePattern = view.findViewById(R.id.image_logo);
            row = view.findViewById(R.id.row);
        }
    }

    public interface OnBetTypeClickListener{
        void onBetTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO);
    }
}
