package com.skilrock.retailapp.adapter.drawgame;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class SchemaAdapter extends RecyclerView.Adapter<SchemaAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> matchDetailList;
    private Context context;
    private OnBetTypeClickListener clickListener;

    public SchemaAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> matchDetail, Context context) {
        this.matchDetailList = matchDetail;
        this.context = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.schema_row, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail matchDetails = matchDetailList.get(position);

        holder.tvMatch.setText(matchDetails.getMatch() + " (" + matchDetails.getType() + ")");
        //holder.tvPrice.setText(context.getString(R.string.dollar) + matchDetails.getPrizeAmount());
        holder.tvPrice.setText(getAmountWithCurrency(matchDetails.getPrizeAmount()));
        holder.tvRank.setText(" - Rank " + matchDetails.getRank());
    }


    @Override
    public int getItemCount() {
        return matchDetailList.size();
    }


    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvMatch, tvPrice, tvRank;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvMatch = view.findViewById(R.id.tv_match);
            tvPrice = view.findViewById(R.id.tv_price);
            tvRank = view.findViewById(R.id.tv_rank);
        }
    }

    public interface OnBetTypeClickListener{
        void onBetTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO);
    }

    private String getAmountWithCurrency(String strAmount) {
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount),
                    Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context)),
                    Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context)),
                    Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(strAmount);
        }
        return formattedAmount;
    }
}
