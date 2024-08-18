package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.MultiSaleTicketAdapter;
import com.skilrock.retailapp.ui.fragments.scratch.SaleTicketGelsaFragment;
import com.skilrock.retailapp.utils.Utils;

public class MultiSaleSeriesWarningDialog extends Dialog implements View.OnClickListener {

    private final Context context;
    private MultiSaleTicketAdapter mAdapter;
    private SaleTicketGelsaFragment.DialogCallback callback;
    public MultiSaleSeriesWarningDialog(Context context, SaleTicketGelsaFragment.DialogCallback callback) {
        super(context);
        this.context   = context;
        this.callback  = callback;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_multi_sale_warning);
        initializeWidgets();
    }

    private void initializeWidgets() {
        Button addSeparately                 = findViewById(R.id.add_separately);
        Button startSeries                   = findViewById(R.id.start_series);

        addSeparately.setOnClickListener(this);
        startSeries.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.add_separately: {
                // add call back
                callback.onResult(true);
                dismiss();
                break;
            }
            case R.id.start_series: {

                callback.onResult(false);
                dismiss();
            }
        }
    }


}
