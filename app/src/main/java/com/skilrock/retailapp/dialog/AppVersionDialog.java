package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.AppVersionListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class AppVersionDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String message;
    private AppVersionListener listener;
    private boolean isForcefulDownload;
    private String url;

    public AppVersionDialog(Context context, boolean isForcefulDownload, String url, String message, AppVersionListener listener) {
        super(context);
        this.context            = context;
        this.message            = message;
        this.url                = url;
        this.listener           = listener;
        this.isForcefulDownload = isForcefulDownload;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_app_version);
        initializeWidgets();
    }

    private void initializeWidgets() {
        Button btnSubmit    = findViewById(R.id.btn_submit);
        Button btnCancel    = findViewById(R.id.btn_cancel);
        TextView tvHeader   = findViewById(R.id.tvHeader);
        TextView tvMessage  = findViewById(R.id.tvMessage);
        ImageView ivIcon    = findViewById(R.id.ivIcon);

        ivIcon.setImageDrawable(Utils.getLogo(context, BuildConfig.app_name));
        tvHeader.setText(BuildConfig.app_name);
        tvMessage.setText(message);
        btnCancel.setVisibility(isForcefulDownload ? View.GONE : View.VISIBLE);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
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
            case R.id.btn_submit:
                Utils.vibrate(context);
                listener.onUpdateSelected(true, url);
                dismiss();
                break;
            case R.id.btn_cancel:
                Utils.vibrate(context);
                listener.onUpdateSelected(false, url);
                dismiss();
                break;
        }
    }
}
