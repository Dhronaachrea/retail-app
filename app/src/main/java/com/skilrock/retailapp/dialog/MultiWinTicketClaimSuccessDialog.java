package com.skilrock.retailapp.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.MultiTicketClaimAdapter;
import com.skilrock.retailapp.models.scratch.GetVerifyTicketResponseBean;
import com.skilrock.retailapp.ui.fragments.scratch.MultiSaleTicketCartGelsaFragment;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class MultiWinTicketClaimSuccessDialog extends Dialog implements View.OnClickListener {

    private final Context context;


    ArrayList<GetVerifyTicketResponseBean> ticketsData;

    private MultiTicketClaimAdapter mAdapter;

    private MultiSaleTicketCartGelsaFragment.SuccessTicketDialogCallback callback;

    private int noOfTickets   = 0;
    private float totalAmount = 0;
    private float taxAmount   = 0;
    private float netAmount   = 0;

    public MultiWinTicketClaimSuccessDialog(Context context, ArrayList<GetVerifyTicketResponseBean> ticketsData, MultiSaleTicketCartGelsaFragment.SuccessTicketDialogCallback callback) {
        super(context);
        this.context        = context;
        this.ticketsData    = ticketsData;
        this.callback       = callback;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_multi_ticket_claim_success);
        initializeWidgets();
    }

    @SuppressLint("SetTextI18n")
    private void initializeWidgets() {
        RecyclerView rvTicketList     = findViewById(R.id.rvTicketList);
        TextView tvAmount = findViewById(R.id.amount);
        Button closeBtn = findViewById(R.id.closeBtn);
        TextView tvTaxAmount = findViewById(R.id.tvTaxAmount);
        TextView tvNetAmount = findViewById(R.id.tvNetAmount);

        closeBtn.setOnClickListener(this);

        for (GetVerifyTicketResponseBean ticketData : ticketsData) {
            noOfTickets += ticketData.getTicketAndStatusList().size();
            for (GetVerifyTicketResponseBean.TicketAndStatus ticket : ticketData.getTicketAndStatusList()) {
                totalAmount += Double.parseDouble(ticket.getWinAmount());
                Log.i("TaG","----==--=-=-=-=-->>>" + ticket.getNetAmount());
                netAmount += Double.parseDouble(ticket.getNetAmount());
                taxAmount += Double.parseDouble(ticket.getTaxAmount());
            }
        }

        tvAmount.setText(Utils.getUkFormat(totalAmount) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));
        tvTaxAmount.setText(Utils.getUkFormat(taxAmount) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));
        tvNetAmount.setText(Utils.getUkFormat(netAmount) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));




        if (rvTicketList != null) {
            mAdapter = new MultiTicketClaimAdapter(context,ticketsData, true);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rvTicketList.setLayoutManager(linearLayoutManager);
            rvTicketList.setHasFixedSize(true);
            rvTicketList.setItemAnimator(new DefaultItemAnimator());
            rvTicketList.setAdapter(mAdapter);
        } else {
            Log.e("TaG", "RecyclerView is null");
        }

    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.closeBtn : {
                callback.onResult();
                dismiss();
            }

        }
    }


}
