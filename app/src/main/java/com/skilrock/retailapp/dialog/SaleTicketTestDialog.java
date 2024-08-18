package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.SalesTicketTestDialogListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class SaleTicketTestDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private SalesTicketTestDialogListener salesTicketTestDialogListenerVar;
    TextInputEditText etTicketNumber;

    public SaleTicketTestDialog(Context context, SalesTicketTestDialogListener salesTicketTestDialogListener) {
        super(context);
        this.context                        = context;
        this.salesTicketTestDialogListenerVar  = salesTicketTestDialogListener;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_sale_ticket_test_dialog);
        initializeWidgets();
    }

    private void initializeWidgets() {
        etTicketNumber      = findViewById(R.id.et_ticket_number);
        Button button       = findViewById(R.id.btnProceed);
        ImageView btnClose  = findViewById(R.id.btnClose);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        Log.d("TAg", "salesTicketTestDialogListenerVar: " + salesTicketTestDialogListenerVar);
        button.setOnClickListener(this);
        btnClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnProceed:
                Log.d("TAg", "R.id.button:");

                Utils.vibrate(context);
                if (salesTicketTestDialogListenerVar != null)
                    salesTicketTestDialogListenerVar.afterTicketNumberProceed(etTicketNumber.getText().toString());
                dismiss();
                break;
            case R.id.btnClose:
                dismiss();
                break;

            default:
                Log.d("TAg", "Default");

                dismiss();
                break;
        }
    }
}
