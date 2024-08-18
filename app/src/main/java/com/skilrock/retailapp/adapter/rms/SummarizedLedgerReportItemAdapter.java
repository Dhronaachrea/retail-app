package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.SummarizedReportResponseBean;
import com.skilrock.retailapp.utils.AppConstants;

import java.util.ArrayList;

public class SummarizedLedgerReportItemAdapter extends RecyclerView.Adapter<SummarizedLedgerReportItemAdapter.PaymentViewHolder> implements AppConstants {

    private ArrayList<SummarizedReportResponseBean.TxnDatum> listTransaction ;
    private Context context;
    private String type;
    public SummarizedLedgerReportItemAdapter(ArrayList<SummarizedReportResponseBean.TxnDatum> listTransaction, Context context, String type) {
        this.type               = type;
        this.listTransaction    = listTransaction;
        this.context            = context;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_summarized_report_item, viewGroup, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        SummarizedReportResponseBean.TxnDatum ledgerData = listTransaction.get(position);

        holder.tvKey1.setText(new StringBuilder().append(ledgerData.getKey1Name()).append(":").toString());
        holder.tvKey1Value.setText(ledgerData.getKey1());
        holder.tvKey2.setText(new StringBuilder().append(ledgerData.getKey2Name()).append(":").toString());
        holder.tvKey2Value.setText(ledgerData.getKey2());
        holder.tvAmount.setText(ledgerData.getNetAmount());
        holder.tvService.setText(ledgerData.getServiceName());

        if (ledgerData.getRawNetAmount() != null)
            holder.tvAmount.setTextColor(ledgerData.getRawNetAmount() < 0.0 ? context.getResources().getColor(R.color.colorAppOrange) :
                    context.getResources().getColor(R.color.colorGreen));
    }

    @Override
    public int getItemCount() {
        if (listTransaction != null) {
            return listTransaction.size();
        } else return 0;
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView tvBalance, tvPerticular, tvAmount, tvKey1, tvKey1Value, tvKey2, tvKey2Value, tvService;

        PaymentViewHolder(@NonNull View view) {
            super(view);
            tvBalance = view.findViewById(R.id.tv_balance);
            tvPerticular = view.findViewById(R.id.tv_perticular);
            tvAmount = view.findViewById(R.id.tv_amount);
            tvService = view.findViewById(R.id.tv_service);
            tvKey1 = view.findViewById(R.id.tv_key1);
            tvKey1Value = view.findViewById(R.id.tv_key1_value);
            tvKey2 = view.findViewById(R.id.tv_key2);
            tvKey2Value = view.findViewById(R.id.tv_key2_value);
        }
    }

    public void clear() {
        listTransaction.clear();
        notifyDataSetChanged();
    }
}
