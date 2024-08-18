package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.SaleReportResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class SaleReportAdapter extends RecyclerView.Adapter<SaleReportAdapter.PaymentViewHolder> implements AppConstants {

    private ArrayList<SaleReportResponseBean.TransactionDatum> listTransaction ;
    private Context context;

    public SaleReportAdapter(ArrayList<SaleReportResponseBean.TransactionDatum> listTransaction, Context context) {
        this.listTransaction    = listTransaction;
        this.context            = context;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI) ? R.layout.row_sale_report_landscape :R.layout.row_sale_report, viewGroup, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        SaleReportResponseBean.TransactionDatum transaction = listTransaction.get(position);

        holder.tvAmount.setTextColor(transaction.getTxnTypeCode().equalsIgnoreCase("sale") ? context.getResources().getColor(R.color.colorDue) : context.getResources().getColor(R.color.colorGreen));
        holder.tvTxnId.setText(String.format("%s %s", context.getString(R.string.transaction_id), transaction.getTxnId()));
        holder.tvTxnType.setText(String.format("%s%s %s", transaction.getTxnTypeCode(), ":", transaction.getGameName()));
        holder.tvAmount.setText(transaction.getTxnValue());
        holder.tvBalance.setText(transaction.getOrgNetAmount());
        holder.tvOrgTds.setText(String.format("%s %s", context.getString(R.string.user_id), transaction.getUserId()));
        holder.tvDate.setText(Utils.formatDate(transaction.getCreatedAt().split(" ")[0]));
        holder.tvTime.setText(Utils.formatTime(transaction.getCreatedAt().split(" ")[1]));
        holder.tvCommissionAmount.setText(transaction.getOrgCommValue());
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView tvBalance, tvOrgTds, tvAmount, tvTime, tvDate, tvTxnType, tvTxnId, tvCommissionAmount;

        PaymentViewHolder(@NonNull View view) {
            super(view);
            tvBalance            = view.findViewById(R.id.tv_balance);
            tvOrgTds             = view.findViewById(R.id.tv_tds_vale);
            tvAmount             = view.findViewById(R.id.tv_amount);
            tvTime               = view.findViewById(R.id.tv_time);
            tvTxnType            = view.findViewById(R.id.tv_txn_type);
            tvDate               = view.findViewById(R.id.tv_date);
            tvTxnId              = view.findViewById(R.id.tv_txn_id);
            tvCommissionAmount   = view.findViewById(R.id.tv_commission_amount);
        }
    }

    public void clear() {
        listTransaction.clear();
        notifyDataSetChanged();
    }
}
