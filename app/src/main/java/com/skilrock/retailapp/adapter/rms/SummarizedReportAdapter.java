package com.skilrock.retailapp.adapter.rms;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.SummarizedReportResponseBean;
import com.skilrock.retailapp.utils.AppConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SummarizedReportAdapter extends RecyclerView.Adapter<SummarizedReportAdapter.PaymentViewHolder> implements AppConstants {

    private ArrayList<SummarizedReportResponseBean.LedgerDatum> listTransaction ;
    private Context context;
    private String type;
    public SummarizedReportAdapter(ArrayList<SummarizedReportResponseBean.LedgerDatum> listTransaction, Context context, String type) {
        this.type               = type;
        this.listTransaction    = listTransaction;
        this.context            = context;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_summarized_report, viewGroup, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        SummarizedReportResponseBean.LedgerDatum ledgerData = listTransaction.get(position);

        if (type.equalsIgnoreCase(AppConstants.DATE_WISE_TYPE)) {
            holder.tvLabel.setText(context.getString(R.string.date));
            holder.tvLabelValue.setText(formatDate(ledgerData.getDate()));
        } else {
            holder.tvLabel.setText(context.getString(R.string.user_));
            holder.tvLabelValue.setText(ledgerData.getUserName());
        }

        if (type.equalsIgnoreCase(AppConstants.DATE_WISE_TYPE) || type.equalsIgnoreCase(AppConstants.USER_WISE_TYPE)) {
            holder.viewDefault.setVisibility(View.GONE);
            holder.viewLabel.setVisibility(View.VISIBLE);

            SummarizedLedgerReportItemAdapter adapter = new SummarizedLedgerReportItemAdapter(ledgerData.getTxnData(), context, type);
            holder.recyclerViewItem.setLayoutManager(new LinearLayoutManager(context));
            holder.recyclerViewItem.setItemAnimator(new DefaultItemAnimator());
            holder.recyclerViewItem.setAdapter(adapter);
        } else {
            holder.viewLabel.setVisibility(View.GONE);
            holder.viewDefault.setVisibility(View.VISIBLE);
            holder.tvKey1.setText(ledgerData.getKey1Name() + ":");
            holder.tvKey1Value.setText(ledgerData.getKey1());
            holder.tvKey2.setText(ledgerData.getKey2Name() + ":");
            holder.tvKey2Value.setText(ledgerData.getKey2());
            holder.tvAmount.setText(ledgerData.getNetAmount());
            holder.tvService.setText(ledgerData.getServiceName());

            if (ledgerData.getRawNetAmount() != null)
                holder.tvAmount.setTextColor(ledgerData.getRawNetAmount() < 0.0 ? context.getResources().getColor(R.color.colorAppOrange) :
                        context.getResources().getColor(R.color.colorGreen));
        }
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView tvLabel, tvLabelValue;
        TextView tvBalance, tvPerticular, tvAmount, tvKey1, tvKey1Value, tvKey2, tvKey2Value, tvService;
        RecyclerView recyclerViewItem;
        LinearLayout viewLabel;
        RelativeLayout viewDefault;

        PaymentViewHolder(@NonNull View view) {
            super(view);
            tvLabel = view.findViewById(R.id.tv_label);
            tvLabelValue = view.findViewById(R.id.tv_label_value);
            recyclerViewItem = view.findViewById(R.id.rv_item);
            viewDefault = view.findViewById(R.id.view_default);
            tvAmount = view.findViewById(R.id.tv_amount);
            viewLabel = view.findViewById(R.id.view_label);
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

    static String formatDate(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("d MMM yy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = originalFormat.parse(dateTime);

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }
}
