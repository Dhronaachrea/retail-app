package com.skilrock.retailapp.sle_game_portrait.sle_land_scape;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.skilrock.retailapp.R;
import com.skilrock.retailapp.sle_game_portrait.SleFetchDataB2C;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;


public class SleDrawAdapterLand extends RecyclerView.Adapter<SleDrawAdapterLand.MyViewHolder> {
    Context context;
    List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeansB2C;
    GetDraws listner;
    View view;

    public SleDrawAdapterLand(Context context, List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeansB2C, GetDraws listner) {
        this.context=context;
        this.drawDataBeansB2C=drawDataBeansB2C;
        this.listner=listner;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_draw_land, parent, false);
        this.view=view;
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
          String split []=drawDataBeansB2C.get(position).getDrawDateTime().split(" ");
          String split_time []=split[1].split(":");
           holder.draw_name.setText(drawDataBeansB2C.get(position).getDrawDisplayString().toUpperCase()+", "+ PrintUtil.getPrintDateFormatDraws(split[0])+"  "+split_time[0]+":"+split_time[1]);
           holder.draw_price.setText(Utils.getBalanceToView(drawDataBeansB2C.get(position).getPrizePoolAmount(),","," ",0));
        if (((ActivityGamePlayLand) context).lastSelectedPosition == position) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.selected_layout.setBackground(context.getResources().getDrawable(R.drawable.rb_left_round_new_draw_selection));
            }

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.selected_layout.setBackground(context.getResources().getDrawable(R.drawable.bg_purchase_main_number_draw));
            }
            holder.draw_name.setTextColor(context.getResources().getColor(R.color.draw_name_land));
        }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ActivityGamePlayLand) context).lastSelectedPosition = position;
                    listner.onItemClick(position);
                    notifyDataSetChanged();
                }
            });

    }

    @Override
    public int getItemCount() {
        return drawDataBeansB2C.size();
    }


    public interface GetDraws{
        void onItemClick(int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView draw_name,draw_price;
        LinearLayout selected_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            draw_name = itemView.findViewById(R.id.draw_name);
            draw_price = itemView.findViewById(R.id.draw_price);
            selected_layout=itemView.findViewById(R.id.selected_layout);

        }
    }

    private String getFormattedAmount(int amt) {
        String amount;
        try {
            amount = Utils.getBalanceToView(amt, Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE()));
        } catch (Exception e) {
            e.printStackTrace();
            amount = String.valueOf(amt);
        }
        return amount;
    }
}
