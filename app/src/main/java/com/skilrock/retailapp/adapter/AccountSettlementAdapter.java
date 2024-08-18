package com.skilrock.retailapp.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.UserAccountResponseBean;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class AccountSettlementAdapter extends RecyclerView.Adapter<AccountSettlementAdapter.PaymentViewHolder> {

    private ArrayList<UserAccountResponseBean.Transaction> listTransaction ;
    private Context context;

    public AccountSettlementAdapter(ArrayList<UserAccountResponseBean.Transaction> listTransaction, Context context) {
        this.listTransaction    = listTransaction;
        this.context            = context;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_account_settlement, viewGroup, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        UserAccountResponseBean.Transaction transaction = listTransaction.get(position);

        holder.tvService.setText(transaction.getService());
        holder.tvAmount.setText(transaction.getTxnValue());
        holder.tvBalance.setText(String.format("%s\n%s", context.getString(R.string.bal), transaction.getBalancePostTransaction()));
        String strDateFrom = Utils.formatDate(transaction.getFromDate().split(" ")[0]) + " " + Utils.formatTime(transaction.getFromDate().split(" ")[1]);
        String strDateTo = Utils.formatDate(transaction.getToDate().split(" ")[0]) + " " + Utils.formatTime(transaction.getToDate().split(" ")[1]);

        String duration = transaction.getDuration();
        if (duration.contains("to")) {
            try {
                String fromDate = duration.split("to")[0].trim();
                String toDate = duration.split("to")[1].trim();
                String date = Utils.formatDate(fromDate.split(" ")[1]) + " " + Utils.formatTime(fromDate.split(" ")[2]) + " - " + Utils.formatDate(toDate.split(" ")[0]) + " " + Utils.formatTime(toDate.split(" ")[1]);
                holder.tvFromDate.setText(date);
            }
            catch (Exception e) {
                holder.tvFromDate.setText(transaction.getDuration());
            }
        }
        else {
            try {
                String date = Utils.formatDate(transaction.getDuration().split(" ")[0]) + " " + Utils.formatTime(transaction.getDuration().split(" ")[1]);
                holder.tvFromDate.setText(date);
            }
            catch(Exception e) {
                holder.tvFromDate.setText(transaction.getDuration());
            }
        }

        //String duration = Utils.formatDate(transaction.getDuration().split(" ")[0]) + " " + Utils.formatTime(transaction.getDuration().split(" ")[1]);
        //holder.tvFromDate.setText(transaction.getDuration());
        //holder.tvToDate.setText(strDateTo);
        holder.tvRemark.setText(transaction.getRemark());
        holder.txnType.setText(transaction.getTxnType());
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView tvBalance, tvAmount, tvService, tvTnxType, tvRemark, tvFromDate, tvToDate, txnType;

        PaymentViewHolder(@NonNull View view) {
            super(view);
            tvBalance            = view.findViewById(R.id.tv_balance);
            tvAmount             = view.findViewById(R.id.tv_amount);
            tvService            = view.findViewById(R.id.tv_service);
            tvFromDate           = view.findViewById(R.id.tv_from_date);
            tvToDate             = view.findViewById(R.id.tv_to_date);
            tvRemark             = view.findViewById(R.id.tv_remark);
            tvTnxType            = view.findViewById(R.id.tv_cash_out_title);
            txnType              = view.findViewById(R.id.tv_txn_type);
        }
    }
}
