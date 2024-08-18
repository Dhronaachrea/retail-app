package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.BillReportResponseBean;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class BillReportAdapter extends RecyclerView.Adapter<BillReportAdapter.BillViewHolder> {

    private ArrayList<BillReportResponseBean.ResponseData.Data.OrgBillDetail.BillData> listBill;
    private Context context;

    public BillReportAdapter(ArrayList<BillReportResponseBean.ResponseData.Data.OrgBillDetail.BillData> listBill, Context context) {
        this.listBill   = listBill;
        this.context    = context;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_bill_report, viewGroup, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        BillReportResponseBean.ResponseData.Data.OrgBillDetail.BillData billData = listBill.get(position);
        String[] billDurationDate = billData.getBillDuration().split(" to ");
        holder.tvFromDate.setText(String.format(context.getString(R.string.from_col), billDurationDate[0]));
        holder.tvToDate.setText(String.format(context.getString(R.string.to_col), billDurationDate[1]));
        //holder.tvFromDate.setText(billDurationDate[0]);
        //holder.tvToDate.setText(billDurationDate[1]);
        holder.tvBillNumber.setText(billData.getBillNumber());
        //holder.tvBillNumber.setText(String.format(context.getString(R.string.from_col), billDurationDate[0]));
        holder.tvBillDate.setText(Utils.getDateMonthName(billData.getBillDate()));
        holder.tvAmount.setText(billData.getBillAmount());
        //holder.tvStatus.setText(billData.getBillNumber());
        if (billData.getStatus().trim().equalsIgnoreCase("PARTIALLY PAID")) {
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.colorPartiallyPaid));
            holder.tvDueAmount.setText(billData.getDueAmount());
            holder.tvStatus.setText(billData.getStatus());
            holder.tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPartiallyPaid));
        }
        else if (billData.getStatus().trim().equalsIgnoreCase("DUE")) {
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.colorDue));
            holder.tvDueAmount.setText(billData.getDueAmount());
            holder.tvStatus.setText(billData.getStatus());
            holder.tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorDue));
        }
        else if (billData.getStatus().trim().equalsIgnoreCase("PAID")) {
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.colorPaid));
            holder.tvDueAmountCaption.setVisibility(View.INVISIBLE);
            holder.tvDueAmount.setVisibility(View.INVISIBLE);
            holder.tvStatus.setText(billData.getStatus());
            holder.tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPaid));
        }

    }

    @Override
    public int getItemCount() {
        return listBill.size();
    }

    class BillViewHolder extends RecyclerView.ViewHolder {

        TextView tvFromDate, tvToDate, tvBillNumber, tvBillDate, tvAmount, tvDueAmount, tvDueAmountCaption, tvStatus;

        BillViewHolder(@NonNull View view) {
            super(view);
            tvFromDate          = view.findViewById(R.id.tvFromDate);
            tvToDate            = view.findViewById(R.id.tvToDate);
            tvStatus            = view.findViewById(R.id.tvStatus);
            tvBillNumber        = view.findViewById(R.id.tvBillNumber);
            tvBillDate          = view.findViewById(R.id.tvBillDate);
            tvAmount            = view.findViewById(R.id.tvAmount);
            tvDueAmount         = view.findViewById(R.id.tvDueAmount);
            tvDueAmountCaption  = view.findViewById(R.id.tvDueAmountCaption);
        }
    }

    public void clear() {
        listBill.clear();
        notifyDataSetChanged();
    }

}
