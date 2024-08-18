package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.ola.InventoryFlowReportResponseBean;

import java.util.ArrayList;

public class InventoryFlowReportAdapterOpeningBalance extends RecyclerView.Adapter<InventoryFlowReportAdapterOpeningBalance.InventoryFlowViewHolder> {

    private ArrayList<InventoryFlowReportResponseBean.GameWiseOpeningBalanceDatum> openingBalanceGameWiseData;
    private Context context;

    public InventoryFlowReportAdapterOpeningBalance(ArrayList<InventoryFlowReportResponseBean.GameWiseOpeningBalanceDatum> openingBalanceGameWiseData, Context context) {
        this.openingBalanceGameWiseData   = openingBalanceGameWiseData;
        this.context                      = context;
    }

    @NonNull
    @Override
    public InventoryFlowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_inventory_flow_report_received, viewGroup, false);
        return new InventoryFlowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryFlowViewHolder holder, int position) {
        InventoryFlowReportResponseBean.GameWiseOpeningBalanceDatum gameData = openingBalanceGameWiseData.get(position);
        holder.tvGameData.setText(gameData.getGameName());
        holder.tvGameDataBooks.setText((gameData.getTotalBooks().toString()));
        holder.tvGameDataTickets.setText((gameData.getTotalTickets().toString()));
    }

    @Override
    public int getItemCount() {
        return openingBalanceGameWiseData.size();
    }

    class InventoryFlowViewHolder extends RecyclerView.ViewHolder {

        TextView tvGameData, tvGameDataBooks, tvGameDataTickets;

        InventoryFlowViewHolder(@NonNull View view) {
            super(view);
            tvGameData          = view.findViewById(R.id.tvGameData);
            tvGameDataBooks     = view.findViewById(R.id.tvGameDataBooks);
            tvGameDataTickets   = view.findViewById(R.id.tvGameDataTickets);
        }
    }

    public void clear() {
        openingBalanceGameWiseData.clear();
        notifyDataSetChanged();
    }

}
