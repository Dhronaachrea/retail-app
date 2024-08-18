package com.skilrock.retailapp.portrait_draw_games.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.GameName;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.portrait_draw_games.ui.ResultDrawDialogActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

import static com.skilrock.retailapp.utils.AppConstants.FIVE_BY_NINETY;
import static com.skilrock.retailapp.utils.AppConstants.LUCKY_SIX;
import static com.skilrock.retailapp.utils.AppConstants.SPIN_WIN;
import static com.skilrock.retailapp.utils.AppConstants.SUPER_KENO;
import static com.skilrock.retailapp.utils.AppConstants.THAI_LOTTERY;

public class ResultDrawGameAdapter extends RecyclerView.Adapter<ResultDrawGameAdapter.ViewHolder> {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> gameList;
    private ResultDrawDialogActivity context;
    private GameName listner;

    public ResultDrawGameAdapter(ResultDrawDialogActivity context, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> games, GameName listner) {
        this.context = context;
        this.gameList = games;
        this.listner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.draw_game_result_v2pro, viewGroup, false);
        else if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.draw_game_result_t2mini, viewGroup, false);
        else
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.draw_game_result, viewGroup, false);
        return new ResultDrawGameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO = gameList.get(position);

        switch (gameRespVO.getGameCode().toLowerCase()) {
            case LUCKY_SIX:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.lucky_6));
                break;
            case SPIN_WIN:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.spin_win));
                break;
            case FIVE_BY_NINETY:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.five_by_ninety));
                break;
            case THAI_LOTTERY:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.thai_lottery));
                break;
            case SUPER_KENO:
                holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.super_keno));
                break;
        }

        if (((ResultDrawDialogActivity) context).lastSelectedPosition == position) {
            holder.ll_container.setBackground(context.getResources().getDrawable(R.drawable.result_game_card_shape_selection));
        } else {
            holder.ll_container.setBackground(context.getResources().getDrawable(R.drawable.result_game_card_shape));
        }

        holder.tv_gameName.setText(gameRespVO.getGameName());
        holder.ll_container.setOnClickListener(view -> {
            ((ResultDrawDialogActivity) context).lastSelectedPosition = position;
            listner.getGameName(gameList.get(position).getGameCode(), gameList.get(position).getGameName());

        });

    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_container;
        TextView tv_gameName;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_container = itemView.findViewById(R.id.ll_container);
            tv_gameName = itemView.findViewById(R.id.tv_gameName);
            ivIcon = itemView.findViewById(R.id.ivIcon);

        }
    }
}
