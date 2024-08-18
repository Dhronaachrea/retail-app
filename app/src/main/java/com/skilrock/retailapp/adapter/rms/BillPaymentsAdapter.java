package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.BillPaymentsResponseBean;

import java.util.ArrayList;

public class BillPaymentsAdapter extends RecyclerView.Adapter<BillPaymentsAdapter.BillPaymentViewHolder> {

    private Context context;
    private ArrayList<BillPaymentsResponseBean.ResponseData.Data> listPaymentData;

    public BillPaymentsAdapter(Context context, ArrayList<BillPaymentsResponseBean.ResponseData.Data> listPaymentData) {
        this.context            = context;
        this.listPaymentData    = listPaymentData;
    }

    @NonNull
    @Override
    public BillPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_bill_payments, viewGroup, false);
        return new BillPaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillPaymentViewHolder holder, int position) {
        BillPaymentsResponseBean.ResponseData.Data data = listPaymentData.get(position);
        holder.tvAmount.setText(String.format(context.getString(R.string.adjusted_amount), data.getAdjustedAmount()));
        String msg = context.getString(R.string.bill_transaction_id) + " " + data.getPaymentTransactionId();
        String[] date = data.getDate().split(" ");
        try {
            String strDate = date[1].split(":")[0] + ":" + date[1].split(":")[1];
            msg = msg.concat(" | " + strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg = msg.concat(" | " + date[0]);
        holder.tvDate.setText(msg);
    }

    @Override
    public int getItemCount() {
        return listPaymentData.size();
    }


    class BillPaymentViewHolder extends RecyclerView.ViewHolder {

        TextView tvAmount, tvDate;

        BillPaymentViewHolder(@NonNull View view) {
            super(view);
            tvAmount    = view.findViewById(R.id.tvAmount);
            tvDate      = view.findViewById(R.id.tvDate);
        }
    }

}
