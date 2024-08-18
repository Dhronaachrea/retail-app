package com.skilrock.retailapp.adapter.drawgame;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.ResultResponseBean;
import com.skilrock.retailapp.portrait_draw_games.adapter.ResultItemAdapterDge;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

import static com.skilrock.retailapp.utils.PrintUtil.getPrintDateFormatResult;

public class ResultPreviewAdapter extends RecyclerView.Adapter<ResultPreviewAdapter.ViewHolder> {

    private Context context;
    private List<ResultResponseBean.ResponseDatum> resultData;
    private OnPrintClickListener listener;
    private  View view;
    private ResultItemAdapterDge resultItemAdapter;


    public ResultPreviewAdapter(Context context, List<ResultResponseBean.ResponseDatum> resultData, OnPrintClickListener listener) {
        this.context    = context;
        this.resultData = resultData;
        this.listener   = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)){
              view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_type_t2mini, viewGroup, false);
        } else  if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO)){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_type_v2pro, viewGroup, false);
        }
         else {
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_type, viewGroup, false);
         }
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvDrawTime.setText(getPrintDateFormatResult(resultData.get(0).getResultData().get(0).getResultDate())+ ",  "+ resultData.get(0).getResultData().get(0).getResultInfo().get(i).getDrawTime());
        viewHolder.tvWinningNo.setText(resultData.get(0).getResultData().get(0).getResultInfo().get(i).getWinningNo().replaceAll(",", " "));
        viewHolder.tvDrawNo.setText("#"+resultData.get(0).getLastDrawId());

        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        resultItemAdapter = new ResultItemAdapterDge(resultData.get(0).getResultData().get(0).getResultInfo().get(i).getSideBetMatchInfo(), context);
        viewHolder.recyclerView.setAdapter(resultItemAdapter);
        resultItemAdapter.notifyDataSetChanged();

        if (resultData.get(0).getResultData().get(0).getResultInfo().get(i).getWinningMultiplierInfo() != null) {
            viewHolder.llWinMul.setVisibility(View.VISIBLE);
            viewHolder.tvWinMul.setText(context.getString(R.string.winning_multiplier));
            viewHolder.tvWinMulVal.setText(String.format(String.format("%s (%%s", resultData.get(0).getResultData().get(0).getResultInfo().get(i).
                 getWinningMultiplierInfo().getMultiplierCode()), resultData.get(0).getResultData().get(0).getResultInfo().get(i).getWinningMultiplierInfo().getValue()) + ")");
        }
        viewHolder.llPrint.setOnClickListener(v -> {
            listener.onPrintClick(i);
        });

    }

    @Override
    public int getItemCount() {
        return resultData.get(0).getResultData().get(0).getResultInfo().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDrawTime, tvDrawNo, tvWinningNo, tvWinMul, tvWinMulVal;
        LinearLayout llPrint, llWinMul;
        private RecyclerView recyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDrawTime      =   itemView.findViewById(R.id.tv_draw_time);
            tvDrawNo        =   itemView.findViewById(R.id.tv_draw_no);
            tvWinningNo     =   itemView.findViewById(R.id.tv_winning_number);
            llPrint         =   itemView.findViewById(R.id.ll_print);
            recyclerView    =   itemView.findViewById(R.id.rv_item);
            tvWinMul        =   itemView.findViewById(R.id.tv_win_mul);
            tvWinMulVal     =   itemView.findViewById(R.id.tv_win_mul_val);
            llWinMul        =   itemView.findViewById(R.id.ll_win_mul);

        }
    }

    public interface OnPrintClickListener{
        void onPrintClick(int position);
    }
}
