package com.skilrock.retailapp.adapter.drawgame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.DateSelectionDialog;
import com.skilrock.retailapp.models.OlaNetGamingExecutionResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class DateSelectDialogAdapter extends RecyclerView.Adapter<DateSelectDialogAdapter.DateViewHolder> {

    private Context context;
    ArrayList<OlaNetGamingExecutionResponseBean.ResponseDatum> listDate;
    private OnDateSelectionListener listener;
    int selectedPosition = -1;

    public DateSelectDialogAdapter(Context context, ArrayList<OlaNetGamingExecutionResponseBean.ResponseDatum> listDate, DateSelectionDialog listener) {
        this.context    = context;
        this.listDate   = listDate;
        this.listener   = listener;
    }

    @NonNull
    @Override
    public DateSelectDialogAdapter.DateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.date_selection_row_dialog, viewGroup, false);

        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateSelectDialogAdapter.DateViewHolder holder, int position) {
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            holder.tvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
        else if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_TPS570))
            holder.tvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);

        if (listDate.get(position).isSelected()) {
            holder.tvDate.setBackground(context.getResources().getDrawable(R.drawable.solid_red_rounded));
            holder.tvDate.setTextColor(Color.parseColor("#ffffff"));
            holder.tvDate.setTypeface(null, Typeface.BOLD);
            listener.onDateSelected(position);
        }
        else {
            holder.tvDate.setBackground(context.getResources().getDrawable(R.drawable.rounded_grey_outline_low_radius));
            holder.tvDate.setTextColor(Color.parseColor("#6d6d6d"));
            holder.tvDate.setTypeface(null, Typeface.NORMAL);
        }

        holder.tvDate.setText(" "+listDate.get(position).getDescription());
        holder.tvDate.setOnClickListener(v -> {
            //selectedPosition = position;
            setSelected(position);
            //listener.onAmountSelected(amount);
            notifyDataSetChanged();
        });
    }

    private void setSelected(int position) {
        for (int index = 0; index < listDate.size(); index++) {
            listDate.get(index).setSelected(position == index);
        }
    }



    @Override
    public int getItemCount() {
        return listDate.size();
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;

        DateViewHolder(@NonNull View view) {
            super(view);
            tvDate = view.findViewById(R.id.tvBetType);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnDateSelectionListener {
        void onDateSelected(int amount);
    }
}