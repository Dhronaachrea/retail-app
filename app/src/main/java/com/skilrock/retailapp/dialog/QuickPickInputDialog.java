package com.skilrock.retailapp.dialog;

import android.annotation.SuppressLint;
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

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.InputListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class QuickPickInputDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String info, hint, etText;
    private TextView tvCount;
    private InputListener listener;
    private int minLimit, maxLimit, count;

    public QuickPickInputDialog(Context context, InputListener listener, String info, int minLimit, int maxLimit) {
        super(context);
        this.context    = context;
        this.listener   = listener;
        this.info       = info;
        this.minLimit   = minLimit;
        this.maxLimit   = maxLimit;
        this.count      = minLimit;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_input_quick_pick);
        initializeWidgets();
    }

    @SuppressLint("SetTextI18n")
    private void initializeWidgets() {
        tvCount             = findViewById(R.id.et_input);
        Button btnSubmit    = findViewById(R.id.btn_submit);
        Button btnCancel    = findViewById(R.id.btn_cancel);
        TextView tvInfo     = findViewById(R.id.tvInfo);
        ImageView ivMinus   = findViewById(R.id.ivMinus);
        ImageView ivPlus    = findViewById(R.id.ivPlus);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        tvCount.setText(Integer.toString(minLimit));
        tvInfo.setText(info);
        ivMinus.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMinus:
                Utils.vibrate(context);
                if (count > minLimit)
                    tvCount.setText(Integer.toString(--count));
                break;
            case R.id.ivPlus:
                Utils.vibrate(context);
                if (count < maxLimit)
                    tvCount.setText(Integer.toString(++count));
                break;
            case R.id.btn_submit:
                Utils.vibrate(context);
                listener.onInputReceived(count + "");
                dismiss();
                break;
            case R.id.btn_cancel:
                Utils.vibrate(context);
                listener.onInputReceived("");
                dismiss();
                break;
        }
    }
}
