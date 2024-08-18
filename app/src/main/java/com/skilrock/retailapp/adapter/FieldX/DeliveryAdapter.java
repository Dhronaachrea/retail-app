package com.skilrock.retailapp.adapter.FieldX;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.FieldX.AllTaskItem;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.activities.fieldx.PaymentActivity;
import com.skilrock.retailapp.ui.fragments.scratch.PackReceiveFragment;
import com.skilrock.retailapp.ui.fragments.scratch.PackReturnFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static androidx.core.content.ContextCompat.startActivity;

public class DeliveryAdapter extends RecyclerView.Adapter<AllTaskAdapter.MyViewHolder> {
    ArrayList<AllTaskItem> item = new ArrayList<>();
    Double amount;
    private Activity activity;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;

    private DeliveryAdapter() {
    }

    public DeliveryAdapter(ArrayList<AllTaskItem> item) {
        this.item = item;
    }

    public DeliveryAdapter(ArrayList<AllTaskItem> item, HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Activity activity) {
        this.item = item;
        this.menuBean = menuBean;
        this.activity = activity;
    }

    public static DeliveryAdapter getInstance() {
        return new DeliveryAdapter();
    }

    @NonNull
    @Override
    public AllTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_task_layout, viewGroup, false);
        return new AllTaskAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTaskAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.id.setText("Org ID : " + item.get(i).getOrgId());
        viewHolder.name.setText(item.get(i).getName());
        viewHolder.address.setText(item.get(i).getAddress());
        amount = Double.parseDouble(item.get(i).getAmount().replaceAll("\\s", ""));
//        if (item.get(i).getEnable().containsKey(item.get(i).getOrgId())) {
        for (HashMap<String, String> button : item.get(i).getEnable().keySet()) {
            if (item.get(i).getEnable().get(button) != null && item.get(i).getEnable().get(button).equalsIgnoreCase(item.get(i).getOrgId())) {
                if (button.containsKey("delivery")) {
                    viewHolder.delivery.setVisibility(View.VISIBLE);
                    viewHolder.delivery.setEnabled(true);
                    Log.e("delivery", "enable");
                } else if (button.containsKey("pickup")) {
                    Log.e("pickup", "enable");
                    viewHolder.pickup.setVisibility(View.VISIBLE);
                    viewHolder.pickup.setEnabled(true);
                }
            }
            //   }
        }
        if (amount < 0) {
            viewHolder.collect.setVisibility(View.VISIBLE);
            viewHolder.collect.setEnabled(true);
            // viewHolder.collect.setBackgroundColor(viewHolder.collect.getContext().getResources().getColor(R.color.collect_enable_color));
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        viewHolder.amount.setText(formatter.format(amount));
        viewHolder.delivery.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("index", 0);
            bundle.putString("title", "Pack Receive");
            bundle.putParcelable("MenuBean", menuBean);
            //HashMap<String,String>map=new HashMap<>();
            int c = 0;
            for (HashMap<String, String> map : item.get(i).getEnable().keySet()) {
                if (item.get(i).getEnable().get(map).equalsIgnoreCase(item.get(i).getOrgId()) && map.get("delivery") != null && c == i) {
                    Log.e("map", map.toString());
                    bundle.putString("url", map.get("delivery"));
                    Log.e("challane_id", bundle.toString());
                    break;
                }
                c++;
            }
            c = 0;
            for (HashMap<String, String> map : item.get(i).getChallanNo().keySet()) {
                if (item.get(i).getChallanNo().get(map).equalsIgnoreCase(item.get(i).getOrgId()) && map.get("delivery") != null && c == i) {
                    Log.e("map", map.toString());
                    bundle.putString("challan", map.get("delivery"));
                    Log.e("challane_id", bundle.toString());
                    break;
                }
                c++;
            }
            ((MainActivity) activity).openFragment(new PackReceiveFragment(), "ReceiveBookFragment", true, bundle);
        });
        viewHolder.pickup.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("index", 0);
            bundle.putString("title", "Pack Return");
            bundle.putParcelable("MenuBean", menuBean);
            for (HashMap<String, String> map : item.get(i).getEnable().keySet()) {
                if (item.get(i).getEnable().get(map).equalsIgnoreCase(item.get(i).getOrgId()) && map.get("pickup") != null) {
                    Log.e("map", map.toString());
                    bundle.putString("retailer", map.get("pickup"));
                    Log.e("challane_id", bundle.toString());
                    break;
                }
            }
//            HashMap<String,String>map=item.get(i).getEnable().get(item.get(i).getEnable().get("pickup")));
//            bundle.putString("url",map.get("delivery"));
            ((MainActivity) activity).openFragment(new PackReturnFragment(), "PackReturnFragment", true, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, address, amount, dlId;
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
            collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), PaymentActivity.class);
                    intent.putExtra("amount", amount.getText());
                    intent.putExtra("id", id.getText().toString());
                    intent.putExtra("position", String.valueOf(getAdapterPosition()));
                    intent.putExtra("view", amount.toString());
                    Log.e("postion", getLayoutPosition() + "");
                    startActivity(itemView.getContext(), intent, null);
                }
            });
        }
    }
}
