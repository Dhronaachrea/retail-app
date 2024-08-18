package com.skilrock.retailapp.adapter.FieldX;

import android.app.Activity;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.FieldX.AllTaskItem;
import com.skilrock.retailapp.ui.fragments.fieldx.FieldXHomeFragment;
import com.skilrock.retailapp.viewmodels.FieldXViewModel.FieldXAllTaskViewModel;

import java.util.ArrayList;

public class AllRetailerAdapter extends RecyclerView.Adapter<AllRetailerAdapter.MyViewHolder> {
    private static FieldXAllTaskViewModel viewModel;
    private boolean enableCollect;
    ArrayList<AllTaskItem> item = new ArrayList<>();
    private Activity activity;
    private static FieldXHomeFragment fieldXHomeFragment;

    private AllRetailerAdapter() {
    }

    public AllRetailerAdapter(ArrayList<AllTaskItem> item, Activity activity) {
        this.item = item;
        this.activity = activity;
    }

    public static AllRetailerAdapter getInstance() {
        return new AllRetailerAdapter();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_all_retailer, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.id.setText("("+activity.getString(R.string.orgId) + item.get(i).getOrgId()+")");
        viewHolder.name.setText(item.get(i).getName());
        viewHolder.address.setText(item.get(i).getAddress());
        viewHolder.amount.setText(item.get(i).getAmount());

        viewHolder.orgType.setText(item.get(i).getOrgType().equalsIgnoreCase("RET") ? activity.getString(R.string.retailer)
                : activity.getString(R.string.master_agent));
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, address, amount, dlId, orgType;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.orgId);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            amount = itemView.findViewById(R.id.amount);
            dlId = itemView.findViewById(R.id.dlId);
            orgType = itemView.findViewById(R.id.orgType);
        }
    }
}
