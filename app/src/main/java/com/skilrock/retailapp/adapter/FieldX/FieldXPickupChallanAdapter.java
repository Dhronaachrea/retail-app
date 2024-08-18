package com.skilrock.retailapp.adapter.FieldX;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.FieldX.FieldXChallanItems;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.PackReturnViewModel;

import java.util.ArrayList;

public class FieldXPickupChallanAdapter extends RecyclerView.Adapter<FieldXPickupChallanAdapter.MyViewHolder>{
    private Activity activity;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private ArrayList<FieldXChallanItems> item = new ArrayList<>();
    private String title;
    private ViewGroup viewGroup;
    private static PackReturnViewModel packReturnViewModel;

    public FieldXPickupChallanAdapter(ArrayList<FieldXChallanItems> item, HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Activity activity, String title, PackReturnViewModel packReturnViewModel) {
        this.item = item;
        this.menuBean = menuBean;
        this.activity = activity;
        this.title    =title;
        this.packReturnViewModel = packReturnViewModel;
    }

    private FieldXPickupChallanAdapter() {
    }

    public static FieldXPickupChallanAdapter getInstance() {
        return new FieldXPickupChallanAdapter();
    }

    @NonNull
    @Override
    public FieldXPickupChallanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fieldx_challan_item, viewGroup, false);
        this.viewGroup=viewGroup;
        return new FieldXPickupChallanAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FieldXPickupChallanAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.challanNo.setText(item.get(i).getChallanNo());
        viewHolder.challanId.setText(activity.getString(R.string.dlId)+ item.get(i).getDlId());
        viewHolder.challanDate.setText(Utils.formatTimeResultForFieldXChallan(item.get(i).getChallanDate().split(" ")[0]));
        viewHolder.challan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(activity)) {
                    Toast.makeText(activity, activity.getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                disableAllItems();

                try {
                    UrlBean urlBean = Utils.getFieldxChallanUrlDetails(menuBean, "getReturnNote");
                    if (urlBean != null) {
                        ProgressBarDialog.getProgressDialog().showProgress(activity);
                        packReturnViewModel.callReturnListApi(urlBean, PlayerData.getInstance().getUsername(), item.get(i).getChallanNo().trim(), item.get(i).getRetailer());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void disableAllItems() {
        for(int i=0;i<viewGroup.getChildCount();i++)
            viewGroup.getChildAt(i).setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView challanNo, challanId, challanDate;
        private LinearLayout challan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            challanId   = itemView.findViewById(R.id.dlId);
            challanNo   = itemView.findViewById(R.id.challan_number);
            challan     =itemView.findViewById(R.id.challan);
            challanDate = itemView.findViewById(R.id.challan_date);
        }
    }
}
