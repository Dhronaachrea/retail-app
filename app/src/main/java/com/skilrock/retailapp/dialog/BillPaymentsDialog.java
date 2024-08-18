package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.BillPaymentsAdapter;
import com.skilrock.retailapp.models.rms.BillPaymentsResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class BillPaymentsDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private ArrayList<BillPaymentsResponseBean.ResponseData.Data> listPaymentData;
    private BillPaymentsAdapter adapter;

    public BillPaymentsDialog(Context context, ArrayList<BillPaymentsResponseBean.ResponseData.Data> listPaymentData) {
        super(context);
        this.context            = context;
        this.listPaymentData    = listPaymentData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_bill_payments);
        initializeWidgets();
    }

    private void initializeWidgets() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button btnBack          = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        adapter = new BillPaymentsAdapter(getContext(), listPaymentData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                dismiss();
                break;
        }
    }
}
