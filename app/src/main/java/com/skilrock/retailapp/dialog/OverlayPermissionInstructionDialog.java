package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.GameChangeListener;
import com.skilrock.retailapp.interfaces.OverlayPermissionListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class OverlayPermissionInstructionDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private OverlayPermissionListener listener;

    public OverlayPermissionInstructionDialog(Context context, OverlayPermissionListener listener) {
        super(context);
        this.context            = context;
        this.listener           = listener;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_overlay_permission_instructions);
        initializeWidgets();
    }

    private void initializeWidgets() {
        Button btnSubmit        = findViewById(R.id.btn_submit);
        TextView tvInstructions = findViewById(R.id.tvInstructions);
        TextView tvCountDown = findViewById(R.id.tv_countdown);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        String instructions = context.getString(R.string.step_1) + "\n\n" +
                context.getString(R.string.step_2) + "\n\n" +
                context.getString(R.string.step_3) ;/*+ "\n\n" +
                context.getString(R.string.step_4);*/

        String countDown = "*"+context.getString(R.string.automatically_redirected) + " "
                + BuildConfig.app_name + " " +
                context.getString(R.string.after_10_seconds);

        tvCountDown.setVisibility(View.VISIBLE);
        tvInstructions.setText(instructions);
        tvCountDown.setText(countDown);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            Utils.vibrate(context);
            listener.onOverlayDialogOk();
            dismiss();
        }
    }
}
