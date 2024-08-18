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


import com.android.volley.RequestQueue;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.FieldX.FieldXChallanItems;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.ReceiveBookViewModel;

import java.util.ArrayList;


public class FieldXChallanAdapter extends RecyclerView.Adapter<FieldXChallanAdapter.MyViewHolder> {
    private Activity activity;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private ArrayList<FieldXChallanItems> item = new ArrayList<>();
    private RequestQueue requestQueue;
    private String title;
    private ViewGroup viewGroup;
    private static ReceiveBookViewModel receiveBookViewModel;

    public FieldXChallanAdapter(ArrayList<FieldXChallanItems> item, HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Activity activity, String title, ReceiveBookViewModel receiveBookViewModel) {
        this.item = item;
        this.menuBean = menuBean;
        this.activity = activity;
        this.title = title;
        this.receiveBookViewModel = receiveBookViewModel;
    }

    private FieldXChallanAdapter() {
    }

    public static FieldXChallanAdapter getInstance() {
        return new FieldXChallanAdapter();
    }

    @NonNull
    @Override
    public FieldXChallanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fieldx_challan_item, viewGroup, false);
        this.viewGroup=viewGroup;
        return new FieldXChallanAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FieldXChallanAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.challanNo.setText(item.get(i).getChallanNo());
        viewHolder.challanId.setText(activity.getString(R.string.dlId)+ item.get(i).getDlId());
        viewHolder.challaneDate.setText(Utils.formatTimeResultForFieldXChallan(item.get(i).getChallanDate()));
        viewHolder.challan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(activity)) {
                    Toast.makeText(activity, activity.getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                disableAllItem();
                try {
                    UrlBean urlBean = Utils.getFieldxChallanUrlDetails(menuBean, "dlDetails");
                    receiveBookViewModel.callChallanApi(urlBean, item.get(i).getDlId(), true, item.get(i).getChallanNo(), item.get(i).getDlId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void disableAllItem() {
        for (int i = 0; i < viewGroup.getChildCount(); i++)
            viewGroup.getChildAt(i).setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView challanNo, challanId, challaneDate;
        private LinearLayout challan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            challanId = itemView.findViewById(R.id.dlId);
            challanNo = itemView.findViewById(R.id.challan_number);
            challan=itemView.findViewById(R.id.challan);
            challaneDate = itemView.findViewById(R.id.challan_date);
        }
    }
}
