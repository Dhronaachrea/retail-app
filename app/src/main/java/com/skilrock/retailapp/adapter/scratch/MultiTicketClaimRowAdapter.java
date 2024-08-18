package com.skilrock.retailapp.adapter.scratch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.ChallanBookSelectionDialog;
import com.skilrock.retailapp.models.scratch.GetVerifyTicketResponseBean;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

public class MultiTicketClaimRowAdapter extends RecyclerView.Adapter<MultiTicketClaimRowAdapter.MultiSaleTicketRowViewHolder> {


    private ChallanBookSelectionDialog dialog;
    private List<GetVerifyTicketResponseBean.TicketAndStatus> ticketAndStatusList = null ;

    private Context context;

    private MultiSaleTicketAdapter.RowAdapterCallBack callBack;

    private boolean  showTextWhite = false;
    private boolean  showInvalidTickets = false;
    public MultiTicketClaimRowAdapter(Context context) {
        this.context = context;
    }

    public MultiTicketClaimRowAdapter(Context context, boolean showTextWhite) {
        this.context = context;
        this.showTextWhite = showTextWhite;
    }
    public MultiTicketClaimRowAdapter(boolean showInvalidTickets, Context context) {
        this.context = context;
        this.showInvalidTickets = showInvalidTickets;
    }

    public MultiTicketClaimRowAdapter(Context context, boolean showWhiteText, List<GetVerifyTicketResponseBean.TicketAndStatus> ticketAndStatusList) {
        this.context = context;
        this.showTextWhite = showTextWhite;
        this.ticketAndStatusList = ticketAndStatusList;
    }
    public MultiTicketClaimRowAdapter(Context context, List<GetVerifyTicketResponseBean.TicketAndStatus> ticketAndStatusList, boolean showInvalidTickets) {
        this.context = context;
        this.showInvalidTickets = showInvalidTickets;
        this.ticketAndStatusList = ticketAndStatusList;
    }

    public MultiTicketClaimRowAdapter(Context context, MultiSaleTicketAdapter.RowAdapterCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MultiSaleTicketRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multi_sale_ticket_row, viewGroup, false);

        return new MultiSaleTicketRowViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MultiSaleTicketRowViewHolder holder, int position) {


        Float amount = Float.parseFloat(ticketAndStatusList.get(position).getWinAmount());
        holder.tvTicketNumber.setText(ticketAndStatusList.get(position).getTicketNumber());
        holder.tvAmount.setText(Utils.getUkFormat(amount) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
        if(this.showTextWhite) {
            holder.tvTicketNumber.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvAmount.setTextColor(context.getResources().getColor(R.color.white));
            holder.deleteBtn.setVisibility(View.GONE);
        } else if (showInvalidTickets) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.llGameList.setVisibility(View.GONE);
            holder.llInvalidGameList.setVisibility(View.VISIBLE);
            holder.tvCount.setText((position + 1) + ":" );
            holder.tvInvalidTicketNo.setText(ticketAndStatusList.get(position).getTicketNumber());

        }

    }

    @Override
    public int getItemCount() {
        return ticketAndStatusList != null ? ticketAndStatusList.size() : 0;
    }


    public class MultiSaleTicketRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTicketNumber;
        TextView tvAmount;
        TextView tvCount;
        TextView tvInvalidTicketNo;
        LinearLayoutCompat llGameList;
        LinearLayoutCompat llInvalidGameList;
        AppCompatButton deleteBtn;

        MultiSaleTicketRowViewHolder(@NonNull View view) {
            super(view);
            tvTicketNumber = view.findViewById(R.id.tvTicketNumber);
            tvAmount = view.findViewById(R.id.tvAmount);
            tvCount = view.findViewById(R.id.tvCount);
            tvInvalidTicketNo = view.findViewById(R.id.tvInvalidTicketNo);
            llGameList = view.findViewById(R.id.llGameList);
            llInvalidGameList = view.findViewById(R.id.llInvalidGameList);
            deleteBtn = view.findViewById(R.id.deleteBtn);

            deleteBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteBtn: {
                    try{
                        callBack.onClick(ticketAndStatusList.get(getAdapterPosition()).getTicketNumber());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void closePacksDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setTicketAndStatusListData( List<GetVerifyTicketResponseBean.TicketAndStatus> data) {
        this.ticketAndStatusList = data;
        notifyDataSetChanged();
    }
}
