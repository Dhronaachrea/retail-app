package com.skilrock.retailapp.dialog;

import android.app.Activity;
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
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class ErrorDialogWinningClaim extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String header, message;
    private int popCount = 0;
    private boolean isPerformLogout = false;
    private FragmentManager fragmentManager;
    private ErrorDialogListener errorDialogListener;
    private Activity activity;

    public ErrorDialogWinningClaim(Context context, String header, String message) {
        super(context);
        this.context    = context;
        this.header     = header;
        this.message    = message;
        setCancelable(false);
    }

    public ErrorDialogWinningClaim(Context context, String header, String message, boolean isPerformLogout) {
        super(context);
        this.context            = context;
        this.header             = header;
        this.message            = message;
        this.isPerformLogout    = isPerformLogout;
        setCancelable(false);
    }

    public ErrorDialogWinningClaim(Context context, String header, String message, boolean isPerformLogout, Activity activity) {
        super(context);
        this.context            = context;
        this.header             = header;
        this.message            = message;
        this.isPerformLogout    = isPerformLogout;
        this.activity           = activity;
        setCancelable(false);
    }

    public ErrorDialogWinningClaim(Context context, String header, String message, int popCount, FragmentManager fragmentManager) {
        super(context);
        this.context            = context;
        this.header             = header;
        this.message            = message;
        this.popCount           = popCount;
        this.fragmentManager    = fragmentManager;
        setCancelable(false);
    }

    public ErrorDialogWinningClaim(Context context, String header, String message, ErrorDialogListener errorDialogListener) {
        super(context);
        this.context                = context;
        this.header                 = header;
        this.message                = message;
        this.errorDialogListener    = errorDialogListener;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.error_dialog_winning);
        initializeWidgets();
    }

    private void initializeWidgets() {
        LinearLayout llContainer    = findViewById(R.id.llContainer);
        TextView tvHeader           = findViewById(R.id.tvHeader);
        TextView tvMessage          = findViewById(R.id.tvMessage);
        Button button               = findViewById(R.id.button);

        button.setOnClickListener(this);
        tvHeader.setText(TextUtils.isEmpty(header) ? context.getString(R.string.service_error) : header);
        tvMessage.setText(message);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Utils.vibrate(context);
                dismiss();
                if (isPerformLogout) {
                    if (activity != null)
                        Utils.performLogoutCleanUp(activity);
                    else
                        Utils.performLogoutCleanUp(context);
                }
                else
                    for (int i = 0; i < popCount; i++)
                        if (fragmentManager != null)
                            fragmentManager.popBackStack();
                if (errorDialogListener != null)
                    errorDialogListener.onDialogClosed();
                break;


        }
    }
}
