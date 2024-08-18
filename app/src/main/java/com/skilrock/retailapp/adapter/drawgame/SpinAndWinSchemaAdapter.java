package com.skilrock.retailapp.adapter.drawgame;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.LinkedHashMap;

public class SpinAndWinSchemaAdapter extends RecyclerView.Adapter<SpinAndWinSchemaAdapter.GameViewHolder> implements AppConstants {

    private LinkedHashMap<String, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail>> matchDetailList;
    private Context context;
    private OnBetTypeClickListener clickListener;

    public SpinAndWinSchemaAdapter(LinkedHashMap<String, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail>> matchDetail, Context context) {
        this.matchDetailList = matchDetail;
        this.context = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_schema_spin_and_win, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
      /*  DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail matchDetails = matchDetailList.get(position);

       *//* holder.tvMatch.setText(matchDetails.getMatch() + " (" + matchDetails.getType() + ")");
        holder.tvPrice.setText(getAmountWithCurrency(matchDetails.getPrizeAmount()));*//*

        SpinAndWinNestedSchemaAdapter schemaAdapter = new SpinAndWinNestedSchemaAdapter(matchDetailList, context);

        holder.rvSchema.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.rvSchema.setHasFixedSize(true);
        holder.rvSchema.setItemAnimator(new DefaultItemAnimator());
        holder.rvSchema.setAdapter(schemaAdapter);*/

        String betType = new ArrayList<String>(matchDetailList.keySet()).get(position);

        if (betType == null) return;

        holder.tvBetType.setText(betType);

        SpinAndWinNestedSchemaAdapter schemaAdapter = new SpinAndWinNestedSchemaAdapter(matchDetailList.get(betType), context);

        holder.rvSchema.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.rvSchema.setHasFixedSize(true);
        holder.rvSchema.setItemAnimator(new DefaultItemAnimator());
        holder.rvSchema.setAdapter(schemaAdapter);
    }

    public String getAmountWithCurrency(String strAmount) {
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount),
                    Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context)),
                    Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context)),
                    Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " +
                    Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(strAmount);
        }
        return formattedAmount;
    }

    @Override
    public int getItemCount() {
        return matchDetailList.size();
    }


    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvBetType;
        RecyclerView rvSchema;

        GameViewHolder(@NonNull View view) {
            super(view);
            rvSchema = view.findViewById(R.id.rv_schema_nested);
            tvBetType = view.findViewById(R.id.tv_betType);
        }
    }

    public interface OnBetTypeClickListener{
        void onBetTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO);
    }
}
