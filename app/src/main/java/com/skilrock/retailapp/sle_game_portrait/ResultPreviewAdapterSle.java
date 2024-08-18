package com.skilrock.retailapp.sle_game_portrait;

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
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

import static com.skilrock.retailapp.utils.Utils.getPrintDateFormatDaySleResult;

public class ResultPreviewAdapterSle extends RecyclerView.Adapter<ResultPreviewAdapterSle.ViewHolder> {

    private Context context;
    private List<ResultPrintBean.DrawWiseResultListBean> resultData;
    private OnPrintClickListener listener;
    private  View view;
    private int repeat=0;
    private ResultItemAdapterSle resultItemAdapter;

    public ResultPreviewAdapterSle(Context context, List<ResultPrintBean.DrawWiseResultListBean> resultData, ResultPreviewActivitySle listener) {
        this.context    = context;
        this.resultData = resultData;
        this.listener   = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_type_t2mini_sle, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_list_type_sle, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String[] date = resultData.get(i).getDrawDateTime().split(" ");
        viewHolder.tvDrawTime.setText(resultData.get(i).getDrawName() + "- " + getPrintDateFormatDaySleResult(date[0]) + " " + date[1]);

        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        resultItemAdapter = new ResultItemAdapterSle(resultData.get(i).getEventMasterList());
        viewHolder.recyclerView.setAdapter(resultItemAdapter);
        resultItemAdapter.notifyDataSetChanged();
        viewHolder.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
            }
        });
        viewHolder.llPrint.setOnClickListener(v -> {
            listener.onPrintClick(i);
        });

    }

    @Override
    public int getItemCount() {
        return resultData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llPrint;
        private TextView tvDrawTime;
        private RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDrawTime      = itemView.findViewById(R.id.tv_draw_time);
            llPrint         = itemView.findViewById(R.id.ll_print);
            recyclerView    = itemView.findViewById(R.id.rv_item);
        }
    }

    public interface OnPrintClickListener{
        void onPrintClick(int position);
    }
}
