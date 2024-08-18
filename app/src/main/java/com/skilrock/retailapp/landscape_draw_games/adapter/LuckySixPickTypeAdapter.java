package com.skilrock.retailapp.landscape_draw_games.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class LuckySixPickTypeAdapter extends RecyclerView.Adapter<LuckySixPickTypeAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType>  game;
    private Context context;
    private OnPickTypeClickListener clickListener;

    public LuckySixPickTypeAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType>  game, Context context, OnPickTypeClickListener clickListener) {
        this.game           = game;
        this.clickListener  = clickListener;
        this.context        = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_luckysix_pick, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType betRespVO = game.get(position);

        holder.tvType.setVisibility(!betRespVO.getRange().get(0).getPickMode().equalsIgnoreCase("FixedSet") ?
                View.VISIBLE : View.GONE);
        holder.viewColorBar.setVisibility(!betRespVO.getRange().get(0).getPickMode().equalsIgnoreCase("FixedSet") ?
                View.GONE : View.VISIBLE);

        if (!betRespVO.getRange().get(0).getPickMode().equalsIgnoreCase("FixedSet")) {

            holder.tvType.setText(betRespVO.getName());
            holder.tvType.setBackground(betRespVO.getIsSelected() == 0 ? context.getResources().getDrawable(R.drawable.background_bet_deselected) :
                    context.getResources().getDrawable(R.drawable.red_solid));

            holder.tvType.setTextColor(betRespVO.getIsSelected() == 0 ? context.getResources().getColor(R.color.colorBlack) :
                    context.getResources().getColor(R.color.colorWhite));

            if (betRespVO.getIsSelected() == 1)
                holder.tvType.setTypeface(holder.tvType.getTypeface(), Typeface.BOLD);
            else
                holder.tvType.setTypeface(null, Typeface.NORMAL);

        } else {
            holder.tvColorTitle.setText(betRespVO.getName().replace("Balls", ""));
            holder.viewColorBar.setBackground(betRespVO.getIsSelected() == 0 ? context.getResources().getDrawable(R.drawable.background_bet_deselected) :
                    context.getResources().getDrawable(R.drawable.red_solid));
            holder.tvColorTitle.setTextColor(betRespVO.getIsSelected() == 0 ? context.getResources().getColor(R.color.colorBlack) :
                    context.getResources().getColor(R.color.colorWhite));

            if (betRespVO.getIsSelected() == 1)
                holder.tvColorTitle.setTypeface(holder.tvColorTitle.getTypeface(), Typeface.BOLD);
            else
                holder.tvColorTitle.setTypeface(null, Typeface.NORMAL);

            holder.colorBar.setBackgroundColor(Color.parseColor(FiveByNinetyColors.getBallColor(betRespVO.getName().replace("Balls", ""))));
        }

        holder.container.setOnClickListener(v -> {
            setSelected(position);
            Utils.popUpAnimation(context, holder.tvType);
            clickListener.onPickTypeClick(betRespVO, false);
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

    public void onClick(int position, boolean isManualClick){
        clickListener.onPickTypeClick(game.get(position), isManualClick);
        setSelected(position);
        notifyDataSetChanged();
    }
    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvType, tvColorTitle;
        RelativeLayout viewColorBar;
        LinearLayout container;
        View colorBar;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvType = view.findViewById(R.id.tvBetType);
            tvColorTitle = view.findViewById(R.id.tv_title);
            viewColorBar = view.findViewById(R.id.view_color_bar);
            container = view.findViewById(R.id.container);
            colorBar = view.findViewById(R.id.color_bar);
        }
    }

    public interface OnPickTypeClickListener {
        void onPickTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType, boolean isManualClick);
    }
}
