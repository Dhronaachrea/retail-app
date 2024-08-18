package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.UserSelectListener;
import com.skilrock.retailapp.models.rms.SettlementListResponseBean;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class SettlementListAdapter extends RecyclerView.Adapter<SettlementListAdapter.PaymentViewHolder> {

    private ArrayList<SettlementListResponseBean.Data> listTransaction ;
    private Context context;
    private UserSelectListener clickListener;

    public SettlementListAdapter(ArrayList<SettlementListResponseBean.Data> listTransaction, Context context, UserSelectListener clickListener) {
        this.listTransaction    = listTransaction;
        this.context            = context;
        this.clickListener      = clickListener;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_settlement_list, viewGroup, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, final int position) {
        SettlementListResponseBean.Data transaction = listTransaction.get(position);

        holder.tvDoerUser.setText(transaction.getUserName());
        holder.tvAmount.setText(transaction.getBalancePostSettelment());
        holder.tvFromDate.setText(Utils.formatDate(transaction.getFromDate().split(" ")[0]) + " "
                + Utils.formatTime(transaction.getFromDate().split(" ")[1]));

        holder.tvToDate.setText(Utils.formatDate(transaction.getToDate().split(" ")[0]) + " "
                + Utils.formatTime(transaction.getToDate().split(" ")[1]));

        holder.tvDate.setText(Utils.formatDate(transaction.getCreatedAt().split(" ")[0]));
        holder.tvTime.setText(Utils.formatTime(transaction.getCreatedAt().split(" ")[1]));

        holder.rlRow.setOnClickListener(v -> clickListener.onUserClicked(position, String.valueOf(transaction.getSettlementid())));
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView tvAmount, tvTime, tvDate, tvFromDate, tvToDate, tvDoerUser;
        RelativeLayout rlRow;

        PaymentViewHolder(@NonNull View view) {
            super(view);
            tvDoerUser      = view.findViewById(R.id.tv_doer_user);
            tvFromDate      = view.findViewById(R.id.tv_from_date);
            tvAmount        = view.findViewById(R.id.tv_balance_post_settlement);
            tvTime          = view.findViewById(R.id.tv_time);
            tvToDate        = view.findViewById(R.id.tv_to_date);
            tvDate          = view.findViewById(R.id.tv_date);
            rlRow           = view.findViewById(R.id.relative_root);
        }
    }

    public void clear() {
        listTransaction.clear();
        notifyDataSetChanged();
    }
}
