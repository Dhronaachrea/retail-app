package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.ola.OperationalReportResponseBean;

public class OperationalReportAdapter extends RecyclerView.Adapter<OperationalReportAdapter.OperationalViewHolder> {

    private OperationalReportResponseBean.ResponseData.Data data ;
    private Context context;

    public OperationalReportAdapter(OperationalReportResponseBean.ResponseData.Data data, Context context) {
        this.data       = data;
        this.context    = context;
    }

    @NonNull
    @Override
    public OperationalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_operational_report, viewGroup, false);
        return new OperationalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationalViewHolder holder, int position) {
        OperationalReportResponseBean.ResponseData.GameWiseDatum gameWiseData = data.getGameWiseData().get(position);
        holder.tvGameTitle.setText(gameWiseData.getGameName());
        holder.tvGameTitleAmt1.setText(gameWiseData.getSales());
        holder.tvGameTitleAmt2.setText(gameWiseData.getClaims());
        holder.tvGameTitleAmt3.setText(gameWiseData.getClaimTax());
    }

    @Override
    public int getItemCount() {
        return data.getGameWiseData().size();
    }

    class OperationalViewHolder extends RecyclerView.ViewHolder {

        TextView tvGameTitle, tvGameTitleAmt1, tvGameTitleAmt2, tvGameTitleAmt3;

        OperationalViewHolder(@NonNull View view) {
            super(view);
            tvGameTitle       = view.findViewById(R.id.tvGameTitle);
            tvGameTitleAmt1   = view.findViewById(R.id.tvGameTitleAmt1);
            tvGameTitleAmt2   = view.findViewById(R.id.tvGameTitleAmt2);
            tvGameTitleAmt3   = view.findViewById(R.id.tvGameTitleAmt3);
        }
    }

    public void clear() {
        data.getGameWiseData().clear();
        notifyDataSetChanged();
    }

}
