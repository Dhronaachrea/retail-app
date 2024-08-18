package com.skilrock.retailapp.adapter.drawgame;

import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.FiveByNinetyBetAmountBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class BetAmountAdapter extends RecyclerView.Adapter<BetAmountAdapter.GameViewHolder> implements AppConstants {

    //private ArrayList<Integer> listBetAmounts;
    private Context context;
    private OnBetAmountClickListener clickListener;
    private ArrayList<FiveByNinetyBetAmountBean> listBetAmounts;

    /*public BetAmountAdapter(ArrayList<Integer> listBetAmounts, Context context, OnBetAmountClickListener clickListener) {
        this.listBetAmounts = listBetAmounts;
        this.clickListener  = clickListener;
        this.context        = context;
    }*/

    public BetAmountAdapter(ArrayList<FiveByNinetyBetAmountBean> listBetAmounts, Context context, OnBetAmountClickListener clickListener) {
        this.listBetAmounts = listBetAmounts;
        this.clickListener  = clickListener;
        this.context        = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bet_amount_row, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        holder.tvAmount.setBackground(listBetAmounts.get(position).isSelected() ? context.getResources().getDrawable(R.drawable.red_solid) :
                context.getResources().getDrawable(R.drawable.background_bet_deselected));

        holder.tvAmount.setTextColor(listBetAmounts.get(position).isSelected() ? context.getResources().getColor(R.color.colorWhite) :
                context.getResources().getColor(R.color.colorBlack));

        if (listBetAmounts.get(position).isSelected())
            holder.tvAmount.setTypeface(holder.tvAmount.getTypeface(), Typeface.BOLD);
        else
            holder.tvAmount.setTypeface(holder.tvAmount.getTypeface(), Typeface.NORMAL);

        int amount = listBetAmounts.get(position).getAmount();
        //String formattedAmount = amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        holder.tvAmount.setText(getAmountWithCurrency(String.valueOf(amount)));
        holder.tvAmount.setTag(amount);
        //holder.tvAmount.setOnClickListener(v -> clickListener.onBetAmountClick(amount));
        holder.tvAmount.setOnClickListener(v -> {
            setSelected(position);
            clickListener.onBetAmountClick(amount);
            listBetAmounts.get(position).setSelected(true);
        });
    }

    public void setSelected(int position) {
        for (int index = 0; index < listBetAmounts.size(); index++) {
            listBetAmounts.get(index).setSelected(position == index);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listBetAmounts.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvAmount;

        GameViewHolder(@NonNull View view) {
            super(view);
            setIsRecyclable(false);
            tvAmount = view.findViewById(R.id.tvBetType);
        }
    }

    public interface OnBetAmountClickListener{
        void onBetAmountClick(int amount);
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
