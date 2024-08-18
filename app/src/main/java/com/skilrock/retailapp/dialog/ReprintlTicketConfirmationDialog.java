package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Locale;
import java.util.Objects;

public class ReprintlTicketConfirmationDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private ConfirmationListener listener;

    public ReprintlTicketConfirmationDialog(Context context, ConfirmationListener listener) {
        super(context);
        this.context    = context;
        this.listener   = listener;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_reprint_ticket_confirmation);
        initializeWidgets();
    }

    private void initializeWidgets() {
        Button btnSubmit    = findViewById(R.id.btn_submit);
        Button btnCancel    = findViewById(R.id.btn_cancel);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")) {
            btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else if (Locale.getDefault().getLanguage().equalsIgnoreCase("fr")) {
            btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        }

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Utils.vibrate(context);
                listener.onConfirmationAccepted();
                dismiss();
                break;
            case R.id.btn_cancel:
                Utils.vibrate(context);
                dismiss();
                break;
        }
    }
}
