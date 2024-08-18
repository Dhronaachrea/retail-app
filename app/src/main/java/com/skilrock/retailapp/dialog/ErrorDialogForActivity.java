package com.skilrock.retailapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class ErrorDialogForActivity extends Dialog implements
        View.OnClickListener {

    private Activity activity;
    private String header, message;

    public ErrorDialogForActivity(Activity activity, String header, String message) {
        super(activity);
        this.activity   = activity;
        this.header     = header;
        this.message    = message;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_error_dialog);
        initializeWidgets();
    }

    private void initializeWidgets() {
        TextView tvHeader   = findViewById(R.id.tvHeader);
        TextView tvMessage  = findViewById(R.id.tvMessage);
        Button button       = findViewById(R.id.button);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        button.setOnClickListener(this);
        tvHeader.setText(TextUtils.isEmpty(header) ? activity.getString(R.string.service_error) : header);
        tvMessage.setText(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Utils.vibrate(activity);
                dismiss();
                activity.finish();

        }
    }
}
