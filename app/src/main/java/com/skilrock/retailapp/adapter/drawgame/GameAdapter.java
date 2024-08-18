package com.skilrock.retailapp.adapter.drawgame;

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

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> game;
    private Context context;

    public GameAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> game, Context context) {
        this.game   = game;
        this.context    = context;
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
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO = game.get(position);
        switch (gameRespVO.getGameCode().toLowerCase()) {
            case LUCKY_SIX:
                holder.imageGame.setImageDrawable(context.getResources().getDrawable(R.drawable.layer_808));
                break;
            case FULL_ROULETTE:
                holder.imageGame.setImageDrawable(context.getResources().getDrawable(R.drawable.power_play));
                break;
            case FIVE_BY_NINTY:
                holder.imageGame.setImageDrawable(context.getResources().getDrawable(R.drawable.lucky_number_red));
                break;
        }

//        holder.tvTime.setText("");
//        holder.tvTimeFormat.setText("");
    }

    @Override
    public int getItemCount() {
        return game.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvTimeFormat;
        ImageView imageGame;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvTime                = view.findViewById(R.id.tv_time);
            imageGame             = view.findViewById(R.id.image_game);
        }
    }
}
