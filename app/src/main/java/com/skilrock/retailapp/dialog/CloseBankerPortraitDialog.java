package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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

public class CloseBankerPortraitDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String info, header;
    private OnCartItemDelete listener;
    private int index;

    public CloseBankerPortraitDialog(Context context, OnCartItemDelete listener, String header, String info, int index) {
        super(context);
        this.context    = context;
        this.listener   = listener;
        this.info       = info;
        this.header     = header;
        this.index      = index;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.close_banker_portrait);
        initializeWidgets();
    }

    private void initializeWidgets() {
        Button btnSubmit    = findViewById(R.id.btn_submit);
        Button btnCancel    = findViewById(R.id.btn_cancel);
        TextView tvInfo     = findViewById(R.id.tvInfo);
        TextView tvHeader   = findViewById(R.id.tvHeader);

        tvHeader.setText(header);
        tvInfo.setText(info);
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
                listener.onCartItemDelete(true, index);
                dismiss();
                break;
            case R.id.btn_cancel:
                Utils.vibrate(context);
                listener.onCartItemDelete(false, index);
                dismiss();
                break;
        }
    }

    public interface OnCartItemDelete {
        void onCartItemDelete(boolean flag, int index);
    }
}
