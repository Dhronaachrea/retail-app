package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.LedgerReportResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class LedgerReportAdapter extends RecyclerView.Adapter<LedgerReportAdapter.PaymentViewHolder> implements AppConstants {

    private ArrayList<LedgerReportResponseBean.Transaction> listTransaction ;
    private Context context;

    public LedgerReportAdapter(ArrayList<LedgerReportResponseBean.Transaction> listTransaction, Context context) {
        this.listTransaction    = listTransaction;
        this.context            = context;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI) ? R.layout.row_ledger_report_landscape :R.layout.row_ledger_report, viewGroup, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        LedgerReportResponseBean.Transaction transaction = listTransaction.get(position);

        holder.tvBalance.setText(transaction.getAvailableBalance());
        holder.tvAmount.setText(transaction.getAmount());

        holder.tvAmount.setTextColor(transaction.getTransactionMode().equalsIgnoreCase(DEBIT) ?
                context.getResources().getColor(R.color.colorDue) : context.getResources().getColor(R.color.colorGreen));

        holder.tvService.setText(transaction.getServiceDisplayName());
        holder.tvPerticular.setText(transaction.getParticular());

        holder.tvDate.setText(Utils.formatDate(transaction.getCreatedAt().split(" ")[0]));
        holder.tvTime.setText(Utils.formatTime(transaction.getCreatedAt().split(" ")[1]));
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView tvBalance, tvPerticular, tvAmount, tvTime, tvDate, tvService;

        PaymentViewHolder(@NonNull View view) {
            super(view);
            tvBalance            = view.findViewById(R.id.tv_balance);
            tvPerticular         = view.findViewById(R.id.tv_perticular);
            tvAmount             = view.findViewById(R.id.tv_amount);
            tvTime               = view.findViewById(R.id.tv_time);
            tvService            = view.findViewById(R.id.tv_service);
            tvDate               = view.findViewById(R.id.tv_date);
        }
    }

    public void clear() {
        listTransaction.clear();
        notifyDataSetChanged();
    }
}
