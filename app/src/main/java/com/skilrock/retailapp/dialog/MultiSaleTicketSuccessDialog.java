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
import com.skilrock.retailapp.adapter.scratch.MultiSaleTicketAdapter;
import com.skilrock.retailapp.models.scratch.GetTicketStatusResponse;
import com.skilrock.retailapp.ui.fragments.scratch.MultiSaleTicketCartGelsaFragment;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

public class MultiSaleTicketSuccessDialog extends Dialog implements View.OnClickListener {

    private final Context context;

    GetTicketStatusResponse.Data ticketsData;

    private MultiSaleTicketAdapter mAdapter;

    private MultiSaleTicketCartGelsaFragment.SuccessTicketDialogCallback callback;

    private int noOfTickets = 0;

    private float totalAmount = 0;

    public MultiSaleTicketSuccessDialog(Context context, GetTicketStatusResponse.Data ticketsData, MultiSaleTicketCartGelsaFragment.SuccessTicketDialogCallback callback) {
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
        setContentView(R.layout.dialog_multi_sale_ticket_success);
        initializeWidgets();
    }

    @SuppressLint("SetTextI18n")
    private void initializeWidgets() {
        RecyclerView rvTicketList     = findViewById(R.id.rvTicketList);
        TextView tvAmount = findViewById(R.id.amount);
        Button closeBtn = findViewById(R.id.closeBtn);
        TextView tvTicketCount = findViewById(R.id.tvTicketCount);

        closeBtn.setOnClickListener(this);

        for (GetTicketStatusResponse.Game ticketData : ticketsData.getData()) {
            noOfTickets += ticketData.getTicketAndStatusList().size();
            totalAmount += (float) (ticketData.getTicketPrice() * ticketData.getTicketAndStatusList().size());
        }

        tvAmount.setText(Utils.getUkFormat(totalAmount) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));
        tvTicketCount.setText(context.getString(R.string.total_no_of_tickets) + " " + noOfTickets);

        if (rvTicketList != null) {
            mAdapter = new MultiSaleTicketAdapter(context,ticketsData.getData(), true);

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
