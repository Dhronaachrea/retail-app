package com.skilrock.retailapp.portrait_draw_games.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.ResultResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

public class ResultItemAdapterDge extends RecyclerView.Adapter<ResultItemAdapterDge.ViewHolder> {
    private List<ResultResponseBean.ResponseDatum.ResultDatum.ResultInfo.SideBetMatchInfo> eventMasterList;
    private  View view;
    private Context context;

    public ResultItemAdapterDge(List<ResultResponseBean.ResponseDatum.ResultDatum.ResultInfo.SideBetMatchInfo> eventMasterList, Context context) {
        this.eventMasterList        = eventMasterList;
        this.context                = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_type_t2mini_sle_item, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_type_sle_item, viewGroup, false);
        }
        return new ResultItemAdapterDge.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvMatch.setText(eventMasterList.get(i).getBetDisplayName());
        viewHolder.tvOutcome.setText(eventMasterList.get(i).getPickTypeName());




    }

    @Override
    public int getItemCount() {
        return eventMasterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView tvOutcome, tvMatch, tvWinMul, tvWinMulVal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOutcome       =   itemView.findViewById(R.id.tv_outcome);
            tvMatch         =   itemView.findViewById(R.id.tv_match);



        }
    }
}
