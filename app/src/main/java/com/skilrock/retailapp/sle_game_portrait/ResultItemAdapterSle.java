package com.skilrock.retailapp.sle_game_portrait;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

public class ResultItemAdapterSle extends RecyclerView.Adapter<ResultItemAdapterSle.ViewHolder> {
    private List<ResultPrintBean.DrawWiseResultListBean.EventMasterListBean> eventMasterList;
    private  View view;

    public ResultItemAdapterSle(List<ResultPrintBean.DrawWiseResultListBean.EventMasterListBean> eventMasterList) {
        this.eventMasterList = eventMasterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_type_t2mini_sle_item, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_type_sle_item, viewGroup, false);
        }
        return new ResultItemAdapterSle.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
             viewHolder.tvMatch.setText(eventMasterList.get(i).getEventDisplay().replaceAll("-", " "));
             viewHolder.tvOutcome.setText(eventMasterList.get(i).getWinningOption());


    }

    @Override
    public int getItemCount() {
        return eventMasterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOutcome, tvMatch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOutcome = itemView.findViewById(R.id.tv_outcome);
            tvMatch   = itemView.findViewById(R.id.tv_match);
        }
    }
}
