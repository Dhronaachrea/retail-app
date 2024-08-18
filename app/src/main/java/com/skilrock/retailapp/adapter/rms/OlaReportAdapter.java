package com.skilrock.retailapp.adapter.rms;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.OlaReportResponseBean;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OlaReportAdapter extends RecyclerView.Adapter<OlaReportAdapter.OlaReportViewHolder> {

    private Context context;
    private ArrayList<OlaReportResponseBean.ResponseData.Data.Transaction> listTransaction;

    public OlaReportAdapter(Context context, ArrayList<OlaReportResponseBean.ResponseData.Data.Transaction> listTransaction) {
        this.context            = context;
        this.listTransaction    = listTransaction;
    }

    @NonNull
    @Override
    public OlaReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_ola_report, viewGroup, false);

        return new OlaReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OlaReportViewHolder holder, int position) {
        OlaReportResponseBean.ResponseData.Data.Transaction transaction = listTransaction.get(position);

        if (transaction.getTxnTyeCode().trim().equalsIgnoreCase("Withdrawal"))
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.colorPaid));
        else if (transaction.getTxnTyeCode().trim().equalsIgnoreCase("Deposit"))
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.colorDue));
        else if (transaction.getTxnTyeCode().trim().equalsIgnoreCase("Deposit Return"))
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.colorPartiallyPaid));

        if (transaction.getTxnDate().contains(" ")) {
            holder.tvDate.setText(Utils.formatDate(transaction.getTxnDate().split(" ")[0]));
            holder.tvTime.setText(Utils.formatTime(transaction.getTxnDate().split(" ")[1]));
        }

        holder.tvType.setText(transaction.getTxnTyeCode());
        holder.tvMobile.setText(String.format("Player: %s", transaction.getPlayerMobile()));
        String userId = String.format(context.getString(R.string.user_id_place_holder), transaction.getUserId()) + " | " +
                String.format(context.getString(R.string.txn_id_place_holder), transaction.getTxnId());
        holder.tvUserId.setText(userId);

        holder.tvAmount.setText(transaction.getTxnValue());
        holder.tvBalance.setText(transaction.getBalancePostTxn());

        holder.tvTvDateTime.setText(Utils.formatDate(transaction.getTxnDate()));
    }

    private String formatDate(String tvDate)  {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat(ConfigData.getInstance().getConfigData().getDEFAULTDATEFORMAT());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yy" );
        Date date = null;
        try {
            date = originalFormat.parse(tvDate.trim());
            Log.d("log", "Old Format: " + originalFormat.format(date));
            Log.d("log", "New Format: " + targetFormat.format(date));

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return targetFormat.format(date);
    }

    public void clear() {
        listTransaction.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    class OlaReportViewHolder extends RecyclerView.ViewHolder {

        TextView tvType, tvMobile, tvUserId, tvTxnId, tvAmount, tvTvDateTime, tvTime, tvDate, tvBalance;

        OlaReportViewHolder(@NonNull View view) {
            super(view);
            tvType = view.findViewById(R.id.tvType);
            tvMobile = view.findViewById(R.id.tvMobile);
            tvUserId = view.findViewById(R.id.tvUserId);
            tvTxnId = view.findViewById(R.id.tvTxnId);
            tvAmount = view.findViewById(R.id.tvAmount);
            tvBalance = view.findViewById(R.id.tv_balance);
            tvTvDateTime = view.findViewById(R.id.tvTvDateTime);
            tvTime = view.findViewById(R.id.tv_time);
            tvDate = view.findViewById(R.id.tv_date);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
