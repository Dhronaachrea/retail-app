package com.skilrock.retailapp.landscape_draw_games.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.skilrock.retailapp.R;

import java.util.Objects;

public class ConfirmPurchaseDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private ConfirmPurchaseListener listener;
    private ImageView imageCancel;

    public ConfirmPurchaseDialog(Context context, ConfirmPurchaseListener listener) {
        super(context);
        this.context            = context;
        this.listener           = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setDimAmount(0.7f);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationZoom;

        setContentView(R.layout.layour_confirm_purchase_dialog);

        initializeWidgets();
    }

    private void initializeWidgets() {
        Button buttonDone               = findViewById(R.id.btn_done);
        Button buttonNo               = findViewById(R.id.btn_no);
        imageCancel                 = findViewById(R.id.image_cancel);

        imageCancel.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
        buttonDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_done:
                listener.onPurchase();
                dismiss();
                break;
            case R.id.image_cancel:
                dismiss();
                break;
            case R.id.btn_no:
                dismiss();
                break;
        }
    }

    public interface ConfirmPurchaseListener {
        void onPurchase();
    }
}
