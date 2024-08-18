package com.skilrock.retailapp.adapter.FieldX;

import android.app.Activity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.FieldX.AllTaskItem;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.fragments.fieldx.FieldXDeliveryChallan;
import com.skilrock.retailapp.ui.fragments.fieldx.FieldXHomeFragment;
import com.skilrock.retailapp.ui.fragments.fieldx.FieldXPickupChallan;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.FieldXViewModel.FieldXAllTaskViewModel;

import java.util.ArrayList;

public class AllTaskAdapter extends RecyclerView.Adapter<AllTaskAdapter.MyViewHolder> {
    private static FieldXAllTaskViewModel viewModel;
    private boolean enableCollect;
    ArrayList<AllTaskItem> item = new ArrayList<>();
    private Activity activity;
    private Context context;
    private static FieldXHomeFragment fieldXHomeFragment;

    private AllTaskAdapter() {
    }

    public AllTaskAdapter(ArrayList<AllTaskItem> item, Activity activity, Context context) {
        this.item = item;
        this.activity = activity;
        this.context=context;
    }

    public AllTaskAdapter(ArrayList<AllTaskItem> item, Activity activity, Context context, boolean enableCollect) {
        this.item = item;
        this.activity = activity;
        this.context  = context;
        this.enableCollect = enableCollect;
    }

    public AllTaskAdapter(ArrayList<AllTaskItem> item, Activity activity, Context context, boolean enableCollect, FieldXAllTaskViewModel viewModel) {
        this.item               = item;
        this.activity           = activity;
        this.context            = context;
        this.enableCollect      = enableCollect;
        this.viewModel = viewModel;
    }

    public static AllTaskAdapter getInstance() {
        return new AllTaskAdapter();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_task_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.id.setText("("+activity.getString(R.string.orgId) + item.get(i).getOrgId()+")");
        viewHolder.name.setText(item.get(i).getName());
        viewHolder.address.setText(item.get(i).getAddress());
        viewHolder.delivery.setVisibility(item.get(i).isDelivery());
        viewHolder.pickup.setVisibility(item.get(i).isPickup());

        viewHolder.orgType.setText(item.get(i).getOrgType().equalsIgnoreCase("RET") ? activity.getString(R.string.retailer)
                : activity.getString(R.string.master_agent));

        /*if (enableCollect == true || item.get(i).isCollect() == View.VISIBLE)
            viewHolder.collect.setVisibility(View.VISIBLE);*/
       // viewHolder.amount.setText(item.get(i).getAmount());
        viewHolder.amount.setText(item.get(i).getAmount());
        viewHolder.delivery.setOnClickListener(v -> {
            if (!Utils.isNetworkConnected(activity)) {
                Toast.makeText(activity, activity.getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("index", 0);
            bundle.putString("title", activity.getString(R.string.pack_recieve));
            bundle.putString("orgId", item.get(i).getOrgId());
            bundle.putParcelable("MenuBean", FieldXHomeFragment.getMenuBeanFieldx());
            ((MainActivity) activity).openFragment(new FieldXDeliveryChallan(), "FieldXDeliveryChallan", true, bundle);
        });
        viewHolder.pickup.setOnClickListener(v -> {
            if (!Utils.isNetworkConnected(activity)) {
                Toast.makeText(activity, activity.getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("index", 0);
            bundle.putString("title", activity.getString(R.string.pack_return));
            bundle.putParcelable("MenuBean", FieldXHomeFragment.getMenuBean());
            bundle.putString("orgId", item.get(i).getOrgId());
            ((MainActivity) activity).openFragment(new FieldXPickupChallan(), "FieldXPickupChallan", true, bundle);
        });
        viewHolder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!Utils.isNetworkConnected(activity)) {
                        Toast.makeText(activity, activity.getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                        return;
                    }
                    ProgressBarDialog.getProgressDialog().showProgress(activity);
                    UrlBean urlBean = Utils.getPayAmountDetailUrl(FieldXHomeFragment.getMenuBeanFieldx(), "getPayAmount");
                    viewModel.getFieldXPayAmount(urlBean, PlayerData.getInstance().getToken(), item.get(i).getOrgId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, address, amount, dlId, orgType;
        Button collect, delivery, pickup;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.orgId);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            amount = itemView.findViewById(R.id.amount);
            collect = itemView.findViewById(R.id.collect);
            delivery = itemView.findViewById(R.id.deliver);
            pickup = itemView.findViewById(R.id.pickup);
            dlId = itemView.findViewById(R.id.dlId);
            orgType = itemView.findViewById(R.id.orgType);
        }
    }
}
