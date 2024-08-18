package com.skilrock.retailapp.adapter.FieldX;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.FieldX.FieldXCollectionReportItem;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FieldXCollectionReportAdapter extends RecyclerView.Adapter<FieldXCollectionReportAdapter.FieldXCollectionViewHolder> {
    private ArrayList<FieldXCollectionReportItem> listcollection = new ArrayList<>();
    private Context context;

    public FieldXCollectionReportAdapter(ArrayList<FieldXCollectionReportItem> listcollection, Context context) {
        this.listcollection = listcollection;
        this.context = context;
    }

    @NonNull
    @Override
    public FieldXCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_fieldx_collection_report, viewGroup, false);
        return new FieldXCollectionReportAdapter.FieldXCollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FieldXCollectionViewHolder viewHolder, int i) {
        String formattedDate = Utils.formatTimeResultForFieldXChallan(listcollection.get(i).getDate());
        viewHolder.tvCollectionDate.setText(formattedDate.split(" ")[0] + " " + formattedDate.split(" ")[1]);
        viewHolder.tvCollectionYear.setText(formattedDate.split(" ")[2]);
//        viewHolder.tvBalance.setTextColor(transaction.getTransactionMode().equalsIgnoreCase(DEBIT) ?
//                context.getResources().getColor(R.color.colorDue) : context.getResources().getColor(R.color.colorGreen));
        viewHolder.tvBalance.setText(getAmountWithCurrency(listcollection.get(i).getBalance()));
        viewHolder.tvNetAmount.setText(getAmountWithCurrency(listcollection.get(i).getNetAMount()));
        viewHolder.tvServiceName.setText(listcollection.get(i).getServiceName());
        viewHolder.tvTxnTYpe.setText(context.getString(R.string.orgId) + " " + listcollection.get(i).getTxnTYpe());
        viewHolder.tvTime.setText(Utils.formatTimeForFieldX(listcollection.get(i).getCreatedAt().split(" ")[1]));
    }

    private String getDate(String date, String targetFormatD) {
        String formattedDate;
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat targetFormat = new SimpleDateFormat(targetFormatD);
            Date dateFor = originalFormat.parse(date);
            formattedDate = targetFormat.format(dateFor);
            return formattedDate.toUpperCase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return date;
        }
    }

    @Override
    public int getItemCount() {
        return listcollection.size();
    }

    public class FieldXCollectionViewHolder extends RecyclerView.ViewHolder{
        TextView tvTxnTYpe, tvCollectionDate, tvBalance, tvNetAmount, tvServiceName, tvCollectionYear, tvTime;
        public FieldXCollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCollectionYear = itemView.findViewById(R.id.tvCollectionYear);
            tvTxnTYpe        = itemView.findViewById(R.id.tvTxnTYpe);
            tvCollectionDate = itemView.findViewById(R.id.tvCollectionDate);
            tvBalance        = itemView.findViewById(R.id.tvBalance);
            tvNetAmount      = itemView.findViewById(R.id.tvNetAmount);
            tvServiceName    = itemView.findViewById(R.id.tvServiceName);
            tvTime           = itemView.findViewById(R.id.tvTime);
        }
    }

    private String getAmountWithCurrency(String strAmount) {
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount),
                    Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context)),
                    Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context)),
                    Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(strAmount);
        }
        return formattedAmount;
    }
}
