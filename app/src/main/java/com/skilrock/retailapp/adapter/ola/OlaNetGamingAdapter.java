package com.skilrock.retailapp.adapter.ola;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.ola.OlaNetGamingResponseBean;
import com.skilrock.retailapp.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OlaNetGamingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<OlaNetGamingResponseBean.TxnList> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private IOnSettleListener onSettleListener;
    OlaNetGamingResponseBean.TxnList data = null;
    private boolean isLast = false;

    public OlaNetGamingAdapter(Context context, ArrayList<OlaNetGamingResponseBean.TxnList> listData, boolean isLast) {
        this.context            = context;
        this.listData           = listData;
        this.isLast             = isLast;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ola_net_gaming_detail, parent, false);
            return new OlaReportViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OlaReportViewHolder)
            populateItemRows((OlaReportViewHolder) holder, position,context);
        else if (holder instanceof LoadingViewHolder)
            showLoadingView((LoadingViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size()+1;
    }



    private void setContentBg(View view) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg);
    }

    private void setHeaderBg( View view) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg);
    }
    private class OlaReportViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName, tvTotalWager, tvTotalWinning, tv_ngr, tvBill, tvTax;

        OlaReportViewHolder(@NonNull View view) {
            super(view);
            tvUserName      = view.findViewById(R.id.tv_user_name);
            tvTotalWager    = view.findViewById(R.id.tv_total_wager);
            tvTotalWinning  = view.findViewById(R.id.tv_total_winning);
            tv_ngr          = view.findViewById(R.id.tv_ngr);
            tvBill          = view.findViewById(R.id.tv_bil);
            tvTax           = view.findViewById(R.id.tv_tax);

        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void populateItemRows(OlaReportViewHolder holder, int position,Context context) {
        int pos = holder.getAdapterPosition();
        if (pos == 0){
            setHeaderBg(holder.tvUserName);
            setHeaderBg(holder.tvTotalWager);
            setHeaderBg(holder.tvTotalWinning);
            setHeaderBg(holder.tvBill);
            setHeaderBg(holder.tvTax);
            holder.tvUserName.setText(context.getText(R.string.player));
            holder.tvTotalWager.setText(context.getText(R.string.wager));
            holder.tvTotalWinning.setText(context.getText(R.string.winning_ngr));
            holder.tvBill.setText(R.string.bonus);
            holder.tvTax.setText(R.string.tax);

            if (isLast){
                holder.tv_ngr.setVisibility(View.GONE);
            }else {
                setHeaderBg(holder.tv_ngr);
                holder.tv_ngr.setVisibility(View.VISIBLE);
                holder.tv_ngr.setText(context.getText(R.string.ngr));
            }

        }else {
            setContentBg(holder.tvUserName);
            setContentBg(holder.tvTotalWager);
            setContentBg(holder.tvTotalWinning);
            setContentBg(holder.tvBill);
            setContentBg(holder.tvTax);
            data = listData.get(position - 1);
            holder.tvUserName.setText(data.getUserName());
            Double totalWager = Double.valueOf(data.getPlayerWager());
            holder.tvTotalWager.setText(Utils.getFormattedCash(totalWager));
            Double totalWining = Double.valueOf(data.getPlayerWinning());
            holder.tvTotalWinning.setText(Utils.getFormattedCash(totalWining));
            holder.tvBill.setText(String.format("%s", data.getBonusToCash()));
            holder.tvTax.setText(String.format("%s", data.getGgrTax()));
            if (isLast){
                holder.tv_ngr.setVisibility(View.GONE);
            }else {
                setContentBg(holder.tv_ngr);
                holder.tv_ngr.setVisibility(View.VISIBLE);
                Double ngr = Double.valueOf(data.getPlayerNGR());
                if (ngr > 0.00){
                    holder.tv_ngr.setText(Utils.getFormattedCash(ngr));
                }else{
                    holder.tv_ngr.setText("0.00");
                }

            }

        }

    }

    private void showLoadingView(LoadingViewHolder holder, int position) {
    }

    public void clear() {
        listData.clear();
        notifyDataSetChanged();
    }


    public static String formatTime(long time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    public interface IOnSettleListener{
        void onSettle(int postion, int txnId);
    }
}
