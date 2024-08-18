package com.skilrock.retailapp.adapter.scratch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.scratch.GetVerifyTicketResponseBean;
import com.skilrock.retailapp.ui.fragments.scratch.MultiSaleTicketCartGelsaFragment;

import java.util.List;

public class MultiTicketClaimAdapter extends RecyclerView.Adapter<MultiTicketClaimAdapter.MultiSaleTicketViewHolder> {

    private MultiTicketClaimRowAdapter multiTicketClaimRowAdapter;
    private List<GetVerifyTicketResponseBean> ticketsData;
    private Context context;

    private MultiSaleTicketCartGelsaFragment.DeleteTicketCallBack mCallBack;

    private GetVerifyTicketResponseBean.TicketAndStatus ticketAndStatusList;
    boolean showWhiteText = false;
    boolean showInvalidTicket = false;

    public MultiTicketClaimAdapter(Context context, List<GetVerifyTicketResponseBean> ticketsData, MultiSaleTicketCartGelsaFragment.DeleteTicketCallBack mCallBack) {
        this.context = context;
        this.ticketsData = ticketsData;
        this.mCallBack = mCallBack;
    }

    //for success dialog
    public MultiTicketClaimAdapter(Context context, List<GetVerifyTicketResponseBean> ticketsData, boolean showWhiteText) {
        this.context = context;
        this.ticketsData = ticketsData;
        this.showWhiteText = showWhiteText;
    }
    // for invalid ticket dialog
    public MultiTicketClaimAdapter(Context context, boolean showInvalidTicket, List<GetVerifyTicketResponseBean> ticketsData) {
        this.context = context;
        this.ticketsData = ticketsData;
        this.showInvalidTicket = showInvalidTicket;
    }

    public MultiTicketClaimAdapter(Context context, boolean showWhiteText) {
        this.context = context;
        this.showWhiteText = showWhiteText;
    }

    public MultiTicketClaimAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public MultiSaleTicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multi_sale_row, viewGroup, false);

        return new MultiSaleTicketViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MultiSaleTicketViewHolder holder, int position) {


        Log.i("TaG","rv ticket data ---------------------> "+ticketsData.get(position).getTicketAndStatusList());

        multiTicketClaimRowAdapter.setTicketAndStatusListData(ticketsData.get(position).getTicketAndStatusList());

        Log.i("TaG","rv ---------------------> "+ticketsData.get(position).getGameName());

        int ticketCount = ticketsData.get(position).getTicketAndStatusList().size();
        holder.tvGameName.setText(ticketsData.get(position).getGameName() + " (" + ticketCount + (ticketCount == 1 ? " " + context.getString(R.string.ticket) : " " + context.getString(R.string.tickets)) + " )");


        if (this.showWhiteText) {
            holder.tvGameName.setTextColor(context.getResources().getColor(R.color.white));
            holder.dotLine.setBackground(context.getResources().getDrawable(R.drawable.dot_line_land));
        }

    }

    @Override
    public int getItemCount() {
        return ticketsData.size();
    }



    public class MultiSaleTicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvGameName;
        RecyclerView rvTicketList;
        View dotLine;

        MultiSaleTicketViewHolder(@NonNull View view) {
            super(view);
            tvGameName = view.findViewById(R.id.tvGameName);
            rvTicketList = view.findViewById(R.id.rvTicketList);
            dotLine = view.findViewById(R.id.dotLine);


            if (showInvalidTicket) {
                multiTicketClaimRowAdapter = new MultiTicketClaimRowAdapter(showInvalidTicket, context);
            } else {
                if (showWhiteText) {
                    multiTicketClaimRowAdapter = new MultiTicketClaimRowAdapter(context, showWhiteText);
                } else {
                    multiTicketClaimRowAdapter = new MultiTicketClaimRowAdapter(context, ticketNo -> {
                            Log.i("TaG","cross btn click ======" + ticketNo);
                            mCallBack.onResult(ticketNo);
                    });
                }

            }
            rvTicketList.setLayoutManager(new LinearLayoutManager(view.getContext()));
            rvTicketList.setHasFixedSize(true);
            rvTicketList.setItemAnimator(new DefaultItemAnimator());
            rvTicketList.setAdapter(multiTicketClaimRowAdapter);
        }

        @Override
        public void onClick(View v) {
            /*if (isCardClickable) {
                isCardClickable = false;
                ArrayList<ChallanResponseBean.GameWiseDetail.BookList> bookList = listGameWiseDetails.get(getAdapterPosition()).getBookList();
                if (bookList != null && bookList.size() > 0) {
                    dialog = new ChallanBookSelectionDialog(context, bookList, listGameWiseDetails.get(getAdapterPosition()).getGameId(), listener, mapSelectedBooks, getAdapterPosition(), listGameWiseDetails.get(getAdapterPosition()).getGameName());
                    dialog.show();
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }

                    dialog.setOnDismissListener(arg0 -> isCardClickable = true);

                } else Toast.makeText(context, context.getString(R.string.no_books_found), Toast.LENGTH_SHORT).show();
            }*/
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

    public void setTicketsData(List<GetVerifyTicketResponseBean> ticketsData) {
        this.ticketsData = ticketsData;
        notifyDataSetChanged();
    }

    @FunctionalInterface
    public interface RowAdapterCallBack {
        void onClick(String ticketNo);
    }

}
