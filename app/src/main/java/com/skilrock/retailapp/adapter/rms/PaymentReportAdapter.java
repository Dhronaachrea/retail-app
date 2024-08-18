package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.ola.PaymentReportResponseBean;

import java.util.ArrayList;

public class PaymentReportAdapter extends RecyclerView.Adapter<PaymentReportAdapter.PaymentViewHolder> {

    private ArrayList<PaymentReportResponseBean.ResponseData.Data.Transaction> listTransaction ;
    private Context context;

    public PaymentReportAdapter(ArrayList<PaymentReportResponseBean.ResponseData.Data.Transaction> listTransaction, Context context) {
        this.listTransaction    = listTransaction;
        this.context            = context;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_payment_report, viewGroup, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        PaymentReportResponseBean.ResponseData.Data.Transaction payment = listTransaction.get(position);
        if (TextUtils.isEmpty(payment.getTxnType())) {
            holder.tvTitle.setText("#");
            holder.tvDescription.setText(context.getString(R.string.n_a));
        }
        else {
            holder.tvTitle.setText(String.valueOf(payment.getTxnType().charAt(0)));
            holder.tvDescription.setText(payment.getNarration());
        }

        if (TextUtils.isEmpty(payment.getTxnValue()))
            holder.tvAmount.setText("0.0");
        else
            holder.tvAmount.setText(payment.getTxnValue());

        String transactionId = context.getString(R.string.transaction_id) + " " + payment.getTxnId();
        holder.tvTransId.setText(transactionId);

        if (TextUtils.isEmpty(payment.getCreatedAt())) {
            holder.divider1.setVisibility(View.GONE);
            holder.divider2.setVisibility(View.GONE);
            holder.tvDate.setVisibility(View.GONE);
            holder.tvTime.setVisibility(View.GONE);
        }
        else {
            String[] date = payment.getCreatedAt().split(" ");
            holder.tvDate.setText(date[0]);
            try {
                String strDate = date[1].split(":")[0] + ":" + date[1].split(":")[1];
                holder.tvTime.setText(strDate);
            } catch (Exception e) {
                holder.divider1.setVisibility(View.GONE);
                holder.tvTime.setVisibility(View.GONE);
            }
        }

        String type;
        if (TextUtils.isEmpty(payment.getTxnType()))
            type = context.getString(R.string.type) + " " + context.getString(R.string.n_a);
        else
            type = context.getString(R.string.type) + " " + payment.getTxnType();

        holder.tvTransType.setText(type);

        String by;
        if (TextUtils.isEmpty(payment.getTxnBy()))
            by = context.getString(R.string.by) + " " + context.getString(R.string.n_a);
        else
            by = context.getString(R.string.by) + " " + payment.getTxnBy();

        holder.tvTransactionBy.setText(by);

        if (payment.getBalancePostTxn() == null)
            holder.tvBalancePost.setVisibility(View.GONE);
        else
            holder.tvBalancePost.setText(String.format(context.getString(R.string.balance_post_transaction), String.valueOf(payment.getBalancePostTxn())));

    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDescription, tvAmount, tvTime, tvDate, tvTransId, tvTransactionBy, tvTransType, tvBalancePost;
        View divider1, divider2;

        PaymentViewHolder(@NonNull View view) {
            super(view);
            tvTitle         = view.findViewById(R.id.tvTitle);
            tvDescription   = view.findViewById(R.id.tvDescription);
            tvAmount        = view.findViewById(R.id.tvAmount);
            tvTime          = view.findViewById(R.id.tvTime);
            tvDate          = view.findViewById(R.id.tvDate);
            tvTransId       = view.findViewById(R.id.tvTransId);
            tvTransactionBy = view.findViewById(R.id.tvTransactionBy);
            tvTransType     = view.findViewById(R.id.tvTransType);
            divider1        = view.findViewById(R.id.divider1);
            divider2        = view.findViewById(R.id.divider2);
            tvBalancePost   = view.findViewById(R.id.tvBalancePost);
        }
    }

    public void clear() {
        listTransaction.clear();
        notifyDataSetChanged();
    }

}
