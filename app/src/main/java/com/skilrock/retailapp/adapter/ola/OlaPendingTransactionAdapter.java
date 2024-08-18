package com.skilrock.retailapp.adapter.ola;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.ola.OlaPendingTransactionResponseBean;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OlaPendingTransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<OlaPendingTransactionResponseBean.ResponseDatum> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private IOnSettleListener onSettleListener;

    public OlaPendingTransactionAdapter(Context context, ArrayList<OlaPendingTransactionResponseBean.ResponseDatum> listData, IOnSettleListener onSettleListener) {
        this.context            = context;
        this.listData           = listData;
        this.onSettleListener   = onSettleListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ola_pending_transaction, parent, false);
            return new OlaReportViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OlaReportViewHolder)
            populateItemRows((OlaReportViewHolder) holder, position);
        else if (holder instanceof LoadingViewHolder)
            showLoadingView((LoadingViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class OlaReportViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvDate, tvTransactionType, tvTransactionId, tvGameName, tvAmount, tvClosingAmount, tvDueAmountCaption;
        Button btSettle;

        OlaReportViewHolder(@NonNull View view) {
            super(view);
            tvTime              = view.findViewById(R.id.tvTime);
            tvDate              = view.findViewById(R.id.tvDate);
            tvTransactionType   = view.findViewById(R.id.tvStatus);
            tvTransactionId     = view.findViewById(R.id.tvBillNumber);
            tvGameName          = view.findViewById(R.id.tvBillDate);
            tvAmount            = view.findViewById(R.id.tvAmount);
            tvClosingAmount     = view.findViewById(R.id.tvDueAmount);
            tvDueAmountCaption  = view.findViewById(R.id.tvDueAmountCaption);
            btSettle            = view.findViewById(R.id.button_settle);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void populateItemRows(OlaReportViewHolder holder, int position) {
        OlaPendingTransactionResponseBean.ResponseDatum data = listData.get(position);

        holder.tvDate.setText(Utils.formatTime(data.getTransactionDate().split(" ")[1]));
        holder.tvTime.setText(formatDate(data.getTransactionDate().split(" ")[0]));

        holder.tvTransactionType.setText(Utils.getPlrTxnType(data.getTransactionType()));
        holder.tvTransactionId.setText(String.format(context.getString(R.string.txn_id_place_holder), data.getTransactionId()));

        holder.tvGameName.setText(data.getStatus());
        holder.tvAmount.setText(String.format("%s%s", Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), String.valueOf(data.getAmount())));
/*
        if (holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Withdrawal"))
            holder.tvAmount.setTextColor(Color.parseColor("#e96262"));
        else if (holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Deposit"))
            holder.tvAmount.setTextColor(Color.parseColor("#89c86f"));
        else if (holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Wager"))
            holder.tvAmount.setTextColor(Color.parseColor("#ff9377"));
        else if (holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Winning"))
            holder.tvAmount.setTextColor(Color.parseColor("#4285F4"));*/

        holder.btSettle.setVisibility(data.getStatus().equalsIgnoreCase("FAILED") ? View.GONE : View.VISIBLE);
        holder.btSettle.setOnClickListener(v -> {
            onSettleListener.onSettle(position, data.getTransactionId());
        });
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {
    }

    public void clear() {
        listData.clear();
        notifyDataSetChanged();
    }

    static String formatDate(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("d MMM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }

    public static String formatTime(long time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    public interface IOnSettleListener{
        void onSettle(int postion, int txnId);
    }
}
