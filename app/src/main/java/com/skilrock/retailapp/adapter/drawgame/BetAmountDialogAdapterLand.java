package com.skilrock.retailapp.adapter.drawgame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.FiveByNinetyBetAmountBean;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class BetAmountDialogAdapterLand extends RecyclerView.Adapter<BetAmountDialogAdapterLand.BetAmountViewHolder> {

    private Context context;
    private ArrayList<FiveByNinetyBetAmountBean> listAmount;
    private OnAmountSelectionListener listener;
    int selectedPosition = -1;

    public BetAmountDialogAdapterLand(Context context, ArrayList<FiveByNinetyBetAmountBean> listAmount, OnAmountSelectionListener listener) {
        this.context    = context;
        this.listAmount = listAmount;
        this.listener   = listener;
    }

    @NonNull
    @Override
    public BetAmountDialogAdapterLand.BetAmountViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bet_amount_row_dialog, viewGroup, false);

        return new BetAmountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BetAmountDialogAdapterLand.BetAmountViewHolder holder, int position) {
      /*  if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            holder.tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        else if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_TPS570))
            holder.tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);*/

        if (listAmount.get(position).isSelected()) {
            holder.tvAmount.setBackground(context.getResources().getDrawable(R.drawable.solid_red_rounded));
            holder.tvAmount.setTextColor(Color.parseColor("#ffffff"));
            holder.tvAmount.setTypeface(null, Typeface.BOLD);
            listener.onAmountSelected(listAmount.get(position).getAmount(), position);
        }
        else {
            holder.tvAmount.setBackground(context.getResources().getDrawable(R.drawable.rounded_grey_outline_low_radius));
            holder.tvAmount.setTextColor(Color.parseColor("#6d6d6d"));
            holder.tvAmount.setTypeface(null, Typeface.NORMAL);
        }
        int amount = listAmount.get(position).getAmount();
        String strAmount = getFormattedAmount(amount);
        holder.tvAmount.setText(getAmountWithCurrency(strAmount));
        holder.tvAmount.setTag(amount);
        holder.tvAmount.setOnClickListener(v -> {
            //selectedPosition = position;
            setSelected(position);
            //listener.onAmountSelected(amount);
            notifyDataSetChanged();
        });
    }

    private void setSelected(int position) {
        for (int index = 0; index < listAmount.size(); index++) {
            listAmount.get(index).setSelected(position == index);
        }
    }

    private String getFormattedAmount(int amt) {
        String amount;
        try {
            amount =""+amt;
            //amount = Utils.getBalanceToView(amt, Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE()));
        } catch (Exception e) {
            e.printStackTrace();
            amount = String.valueOf(amt);
        }
        return amount;
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

    @Override
    public int getItemCount() {
        return listAmount.size();
    }

    public class BetAmountViewHolder extends RecyclerView.ViewHolder {

        TextView tvAmount;

        BetAmountViewHolder(@NonNull View view) {
            super(view);
            tvAmount = view.findViewById(R.id.tvBetType);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnAmountSelectionListener {
        void onAmountSelected(int amount, int position);
    }
}