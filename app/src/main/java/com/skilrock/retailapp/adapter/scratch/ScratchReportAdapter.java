package com.skilrock.retailapp.adapter.scratch;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ScratchInventoryReportListener;
import com.skilrock.retailapp.models.scratch.ScratchReportBean;

import java.util.ArrayList;

public class ScratchReportAdapter extends RecyclerView.Adapter<ScratchReportAdapter.ScratchReportViewHolder> {

    private ArrayList<ScratchReportBean.GameWiseBookDetailList> list;
    private Context context;
    private boolean isCardClickable;
    private ScratchInventoryReportListener listener;

    public ScratchReportAdapter(Context context, ArrayList<ScratchReportBean.GameWiseBookDetailList> list, ScratchInventoryReportListener listener) {
        this.list               = list;
        this.context            = context;
        this.isCardClickable    = true;
        this.listener           = listener;
    }

    @NonNull
    @Override
    public ScratchReportAdapter.ScratchReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_scratch_report, viewGroup, false);

        return new ScratchReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScratchReportAdapter.ScratchReportViewHolder holder, int position) {
        ScratchReportBean.GameWiseBookDetailList data = list.get(position);
        holder.tvGameNumber.setText(String.valueOf(data.getGameNumber()));
        holder.tvGameName.setText(data.getGameName());

        if (data.getInTransitPacksList() == null) holder.tvInTransit.setText("0");
        else holder.tvInTransit.setText(String.valueOf(data.getInTransitPacksList().size()));

        if (data.getReceivedPacksList() == null) holder.tvReceived.setText("0");
        else holder.tvReceived.setText(String.valueOf(data.getReceivedPacksList().size()));

        if (data.getActivatedPacksList() == null) holder.tvActivated.setText("0");
        else holder.tvActivated.setText(String.valueOf(data.getActivatedPacksList().size()));

        if (data.getInvoicedPacksList() == null) holder.tvInvoiced.setText("0");
        else holder.tvInvoiced.setText(String.valueOf(data.getInvoicedPacksList().size()));

        holder.llInvoice.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ScratchReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvGameName, tvGameNumber, tvInTransit, tvReceived, tvActivated, tvInvoiced;
        LinearLayout llContainer, llInvoice;

        ScratchReportViewHolder(@NonNull View view) {
            super(view);
            tvGameName      = view.findViewById(R.id.tvGameName);
            tvGameNumber    = view.findViewById(R.id.tvGameNumber);
            tvInTransit     = view.findViewById(R.id.tvInTransit);
            tvReceived      = view.findViewById(R.id.tvReceived);
            tvActivated     = view.findViewById(R.id.tvActivated);
            tvInvoiced      = view.findViewById(R.id.tvInvoiced);
            llContainer     = view.findViewById(R.id.ll_container);
            llInvoice       = view.findViewById(R.id.llInvoice);
            llContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (isCardClickable) {
                ArrayList<String> listInTransit = list.get(getAdapterPosition()).getInTransitPacksList();
                ArrayList<String> listReceived  = list.get(getAdapterPosition()).getReceivedPacksList();
                ArrayList<String> listActivated = list.get(getAdapterPosition()).getActivatedPacksList();
                ArrayList<String> listInvoiced  = list.get(getAdapterPosition()).getInvoicedPacksList();

                if (!((listInTransit == null || listInTransit.isEmpty()) && (listReceived == null || listReceived.isEmpty()) &&
                        (listActivated == null || listActivated.isEmpty()) && (listInvoiced == null || listInvoiced.isEmpty()))) {
                    isCardClickable = false;
                    /*ScratchReportDialog scratchReportDialog = new ScratchReportDialog(context,
                            list.get(getAdapterPosition()).getInTransitPacksList(), list.get(getAdapterPosition()).getReceivedPacksList(),
                            list.get(getAdapterPosition()).getActivatedPacksList(), list.get(getAdapterPosition()).getInvoicedPacksList());
                    scratchReportDialog.show();
                    if (scratchReportDialog.getWindow() != null) {
                        scratchReportDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        scratchReportDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }

                    scratchReportDialog.setOnDismissListener(arg0 -> isCardClickable = true);*/

                    listener.onCardClick(listInTransit, listReceived, listActivated, listInvoiced);


                }
                else Toast.makeText(context, context.getString(R.string.no_data), Toast.LENGTH_SHORT).show();

                /*if (listIntransit == null ||   && list.get(getAdapterPosition()).getReceivedPacksList() == null &&
                        list.get(getAdapterPosition()).getActivatedPacksList() == null && list.get(getAdapterPosition()).getActivatedPacksList() == null) {

                }
                else if (list.get(getAdapterPosition()).getInTransitPacksList() == null && list.get(getAdapterPosition()).getReceivedPacksList() == null &&
                        list.get(getAdapterPosition()).getActivatedPacksList() == null && list.get(getAdapterPosition()).getActivatedPacksList() == null) {

                }*/


            }
        }
    }

    public void setCardClickableTrue() {
        isCardClickable = true;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
