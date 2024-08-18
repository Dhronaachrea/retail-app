package com.skilrock.retailapp.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.MultiSaleTicketAdapter;
import com.skilrock.retailapp.models.scratch.GetTicketStatusResponse;
import com.skilrock.retailapp.ui.fragments.scratch.MultiSaleTicketCartGelsaFragment;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

public class MultiSaleTicketInvalidDialog extends Dialog implements View.OnClickListener {

    private final Context context;
    List<GetTicketStatusResponse.Game> ticketData;
    private MultiSaleTicketAdapter mAdapter;
    private int invalidTicketCount = 0;

    private MultiSaleTicketCartGelsaFragment.InvalidTicketDialogCallback callBack;

    public MultiSaleTicketInvalidDialog(Context context, List<GetTicketStatusResponse.Game> ticketData, int invalidTicketCount, MultiSaleTicketCartGelsaFragment.InvalidTicketDialogCallback callBack) {
        super(context);
        this.context        = context;
        this.ticketData     = ticketData;
        this.callBack     = callBack;
        this.invalidTicketCount     = invalidTicketCount;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_multi_sale_invalid_ticket);
        initializeWidgets();
    }

    @SuppressLint("SetTextI18n")
    private void initializeWidgets() {
        AppCompatButton closeBtn             = findViewById(R.id.closeBtn);
        RecyclerView rvInvalidTicketList     = findViewById(R.id.rvInvalidTicketList);
        TextView tvInvalidTicketCount     = findViewById(R.id.tvInvalidTicketCount);


        int noOfInvalidTickets = 0;
        for (GetTicketStatusResponse.Game a : ticketData) {
            noOfInvalidTickets += a.getTicketAndStatusList().size();
        }


        if (ticketData.size()>1) {
            tvInvalidTicketCount.setText(noOfInvalidTickets + " " + context.getString(R.string.tickets_are_not_available));
        }
        else {
            tvInvalidTicketCount.setText(noOfInvalidTickets + " " + context.getString(R.string.ticket_are_not_available));

        }
        if (rvInvalidTicketList != null) {
            mAdapter = new MultiSaleTicketAdapter(context, true, ticketData);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rvInvalidTicketList.setLayoutManager(linearLayoutManager);
            rvInvalidTicketList.setHasFixedSize(true);
            rvInvalidTicketList.setItemAnimator(new DefaultItemAnimator());
            rvInvalidTicketList.setAdapter(mAdapter);
        } else {
            Log.e("TaG", "RecyclerView is null");
        }

        closeBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {

            case R.id.closeBtn:
                callBack.onResult();
                dismiss();
                break;
        }
    }


}
