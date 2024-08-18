package com.skilrock.retailapp.adapter.ola;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.ola.OlaPlayerTransactionResponseBean;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class OlaPlayerTransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<OlaPlayerTransactionResponseBean.ResponseData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public OlaPlayerTransactionAdapter(Context context, ArrayList<OlaPlayerTransactionResponseBean.ResponseData> listData) {
        this.context            = context;
        this.listData           = listData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ola_player_transaction, parent, false);
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
        OlaPlayerTransactionResponseBean.ResponseData data = listData.get(position);
        String[] date = data.getTransactionDate().split(" ");
        holder.tvDate.setText(Utils.getDateMonthYearName(date[0]));
        try {
            //String strDate = date[1].split(":")[0] + ":" + date[1].split(":")[1];
            String strDate = date[1].split("\\.")[0];
            holder.tvTime.setText(strDate);
        } catch (Exception e) {
            holder.tvTime.setText(date[1]);
        }

        holder.tvTransactionType.setText(Utils.getPlrTxnType(data.getTxnType()));
        holder.tvTransactionId.setText(String.format(context.getString(R.string.txn_id_place_holder), data.getTransactionId()));
        if (holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Wager") ||
                holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Winning")) {
            try {
                if (data.getParticular().split("_")[0].equalsIgnoreCase("JP")) {
                    String particular = data.getParticular().split("_")[3];
                    holder.tvGameName.setText(particular);
                } else {
                    String particular = data.getParticular().split("_")[2];
                    holder.tvGameName.setText(particular);
                }
            } catch (Exception e) {
                holder.tvGameName.setText(data.getParticular());
                e.printStackTrace();
            }
        }
        else
            holder.tvGameName.setText(data.getParticular());

        if (holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Withdrawal"))
            holder.tvAmount.setTextColor(Color.parseColor("#e96262"));
        else if (holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Deposit"))
            holder.tvAmount.setTextColor(Color.parseColor("#89c86f"));
        else if (holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Wager"))
            holder.tvAmount.setTextColor(Color.parseColor("#ff9377"));
        else if (holder.tvTransactionType.getText().toString().trim().equalsIgnoreCase("Winning"))
            holder.tvAmount.setTextColor(Color.parseColor("#4285F4"));

        holder.tvClosingAmount.setText(String.valueOf(data.getBalance()));
        holder.tvAmount.setText(String.valueOf(data.getTxnAmount()));



        /*if (TextUtils.isEmpty(data.getParticular())) {
            holder.tvTitle.setText("#");
            holder.tvDescription.setText(context.getString(R.string.n_a));
        }
        else {
            holder.tvTitle.setText(String.valueOf(data.getParticular().charAt(0)));
            holder.tvDescription.setText(data.getParticular());
        }

        holder.tvAmount.setText(String.valueOf(data.getTxnAmount()));

        if (TextUtils.isEmpty(data.getTransactionDate())) {
            holder.divider2.setVisibility(View.GONE);
            holder.tvDate.setVisibility(View.GONE);
            holder.tvTime.setVisibility(View.GONE);
        }
        else {
            String[] date = data.getTransactionDate().split(" ");
            holder.tvDate.setText(date[0]);
            try {
                String strDate = date[1].split(":")[0] + ":" + date[1].split(":")[1];
                holder.tvTime.setText(strDate);
            } catch (Exception e) {
                holder.tvTime.setVisibility(View.GONE);
            }
        }

        String balance = context.getString(R.string.balance) + " " + data.getBalance();
        holder.tvTransType.setText(balance);

        String type;
        if (TextUtils.isEmpty(data.getTxnType()))
            type = context.getString(R.string.type) + " " + context.getString(R.string.n_a);
        else
            type = context.getString(R.string.type) + " " + data.getTxnType();

        holder.tvTransactionBy.setText(type);*/
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {
        //ProgressBar would be displayed
    }

    public void clear() {
        listData.clear();
        notifyDataSetChanged();
    }



}
