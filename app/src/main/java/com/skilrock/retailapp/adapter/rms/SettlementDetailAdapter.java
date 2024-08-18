package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.UserSelectListener;
import com.skilrock.retailapp.models.rms.SettlementDetailResponseBean;

import java.util.ArrayList;

public class SettlementDetailAdapter extends RecyclerView.Adapter<SettlementDetailAdapter.PaymentViewHolder> {

    private ArrayList<SettlementDetailResponseBean.Transaction> listTransaction ;
    private Context context;
    private UserSelectListener clickListener;

    public SettlementDetailAdapter(ArrayList<SettlementDetailResponseBean.Transaction> listTransaction, Context context) {
        this.listTransaction    = listTransaction;
        this.context            = context;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_settlement_details, viewGroup, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, final int position) {
        SettlementDetailResponseBean.Transaction transaction = listTransaction.get(position);

        holder.tvService.setText(transaction.getService());
        holder.tvAmount.setText(transaction.getTxnValue());

        holder.tvAmount.setTextColor(transaction.getTxnType().equalsIgnoreCase("cash in") ?
                context.getResources().getColor(R.color.colorGreen) : context.getResources().getColor(R.color.colorDue));
     /*   holder.tvFromDate.setText(Utils.formatDate(transaction.getFromDate().split(" ")[0]) + " "
                + Utils.formatTime(transaction.getFromDate().split(" ")[1]));

        holder.tvToDate.setText(Utils.formatDate(transaction.getToDate().split(" ")[0]) + " "
                + Utils.formatTime(transaction.getToDate().split(" ")[1]));*/

        holder.tvBalance.setText(transaction.getBalancePostTransaction());
        /*holder.tvRemark.setText(transaction.getRemark());*/
        holder.tvTnxType.setText(context.getString(R.string.txn_type_) + " " + transaction.getTxnType());
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView tvAmount, tvFromDate, tvToDate, tvService, tvBalance, tvTnxType, tvRemark;
        LinearLayout rlRow;

        PaymentViewHolder(@NonNull View view) {
            super(view);
            tvService = view.findViewById(R.id.tv_service);
            tvAmount = view.findViewById(R.id.tv_balance_post_settlement);
            tvRemark = view.findViewById(R.id.tv_remark);
            rlRow = view.findViewById(R.id.relative_root);
            tvBalance = view.findViewById(R.id.tv_balance);
            tvTnxType = view.findViewById(R.id.tv_transaction_type);
        }
    }

    public void clear() {
        listTransaction.clear();
        notifyDataSetChanged();
    }
}
