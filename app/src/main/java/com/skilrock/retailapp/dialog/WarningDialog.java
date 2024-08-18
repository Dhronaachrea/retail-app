package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.ui.activities.rms.ForgotPasswordActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class WarningDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String header, message;
    private int popCount = 0;
    private boolean isPerformLogout = false;
    private boolean isFromActivity = false;
    private FragmentManager fragmentManager;
    private String buttonText = null;

    public WarningDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount) {
        super(context);
        this.context            = context;
        this.header             = header;
        this.message            = message;
        this.popCount           = popCount;
        this.fragmentManager    = fragmentManager;
        setCancelable(false);
    }

    public WarningDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount, String buttonText) {
        super(context);
        this.context            = context;
        this.header             = header;
        this.message            = message;
        this.popCount           = popCount;
        this.fragmentManager    = fragmentManager;
        this.buttonText         = buttonText;
        setCancelable(false);
    }

    public WarningDialog(Context context, FragmentManager fragmentManager, String header, String message, boolean isPerformLogout) {
        super(context);
        this.context            = context;
        this.header             = header;
        this.message            = message;
        this.isPerformLogout    = isPerformLogout;
        this.fragmentManager    = fragmentManager;
        setCancelable(false);
    }

    public WarningDialog(Context context, String header, String message) {
        super(context);
        this.context            = context;
        this.header             = header;
        this.message            = message;
        isFromActivity          = true;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_warning_dialog);
        initializeWidgets();
    }

    private void initializeWidgets() {
        TextView tvHeader = findViewById(R.id.tvHeader);
        TextView tvMessage = findViewById(R.id.tvMessage);
        Button button = findViewById(R.id.button);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        if (buttonText != null && buttonText.isEmpty())
            button.setText(buttonText);

        button.setOnClickListener(this);
        tvHeader.setText(TextUtils.isEmpty(header) ? context.getString(R.string.success) : header);
        tvMessage.setText(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Utils.vibrate(context);
                dismiss();
                if (isPerformLogout)
                    Utils.performLogoutCleanUp(context);
                else if (isFromActivity) {
                    ((ForgotPasswordActivity) context).finish();
                }
                else {
                    for (int i = 0; i < popCount; i++)
                        fragmentManager.popBackStack();
                }
                break;
        }
    }
}
