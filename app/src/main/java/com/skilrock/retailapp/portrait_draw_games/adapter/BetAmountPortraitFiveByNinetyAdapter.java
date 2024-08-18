package com.skilrock.retailapp.portrait_draw_games.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.FiveByNinetyBetAmountBean;
import com.skilrock.retailapp.portrait_draw_games.ui.FiveByNinetyMainBetActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class BetAmountPortraitFiveByNinetyAdapter extends RecyclerView.Adapter<BetAmountPortraitFiveByNinetyAdapter.GameViewHolder> implements AppConstants {

    //private ArrayList<Integer> listBetAmounts;
    private Context context;
    private OnBetAmountClickListener clickListener;
    private ArrayList<FiveByNinetyBetAmountBean> listBetAmounts;

    /*public BetAmountAdapter(ArrayList<Integer> listBetAmounts, Context context, OnBetAmountClickListener clickListener) {
        this.listBetAmounts = listBetAmounts;
        this.clickListener  = clickListener;
        this.context        = context;
    }*/

    public BetAmountPortraitFiveByNinetyAdapter(ArrayList<FiveByNinetyBetAmountBean> listBetAmounts, Context context, OnBetAmountClickListener clickListener) {
        this.listBetAmounts = listBetAmounts;
        this.clickListener  = clickListener;
        this.context        = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bet_amount_row_portrait, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO))
            holder.tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570))
            holder.tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);

        if (listBetAmounts.get(position).isSelected()) {
            holder.tvAmount.setBackground(context.getResources().getDrawable(R.drawable.solid_yellow_rounded));
            holder.tvAmount.setTextColor(Color.parseColor("#ffffff"));
            holder.tvAmount.setTypeface(null, Typeface.BOLD);
        }
        else {
            holder.tvAmount.setBackground(context.getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            holder.tvAmount.setTextColor(Color.parseColor("#d30e24"));
            holder.tvAmount.setTypeface(null, Typeface.NORMAL);
        }
        int amount = listBetAmounts.get(position).getAmount();
        //String formattedAmount = "$" + amount;
        //String strAmount = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)) + amount;
        String strAmount = String.valueOf(amount);
        holder.tvAmount.setText(strAmount);
        holder.tvAmount.setTag(amount);
        //holder.tvAmount.setOnClickListener(v -> clickListener.onBetAmountClick(amount));
        holder.tvAmount.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - FiveByNinetyMainBetActivity.LAST_CLICK_TIME < FiveByNinetyMainBetActivity.CLICK_GAP) {
                return;
            }
            FiveByNinetyMainBetActivity.LAST_CLICK_TIME = SystemClock.elapsedRealtime();
            setSelected(position);
            clickListener.onBetAmountClick(amount);
            listBetAmounts.get(position).setSelected(true);
            notifyDataSetChanged();
        });
    }

    private void setSelected(int position) {
        for (int index = 0; index < listBetAmounts.size(); index++) {
            listBetAmounts.get(index).setSelected(position == index);
        }
    }

    @Override
    public int getItemCount() {
        return listBetAmounts.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvAmount;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvAmount = view.findViewById(R.id.tvBetType);
        }
    }

    public interface OnBetAmountClickListener{
        void onBetAmountClick(int amount);
    }
}
