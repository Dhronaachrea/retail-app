package com.skilrock.retailapp.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.skilrock.retailapp.R;

import java.util.Objects;

public class UpdateProgressBarDialog {

    private static UpdateProgressBarDialog progresDialog;
    public android.app.ProgressDialog dialog;
    public Context context;

    private UpdateProgressBarDialog() {
    }

    public static synchronized UpdateProgressBarDialog getProgressDialog() {
        if (progresDialog == null) {
            progresDialog = new UpdateProgressBarDialog();
        }

        return progresDialog;
    }

    public void showProgress(Context context) {
        dismiss();

        dialog = new android.app.ProgressDialog(context);
        Window window = dialog.getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            Objects.requireNonNull(dialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setDimAmount(0.7f);
        }
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.custom_progress_dialog);

        TextView tvTitle = dialog.findViewById(R.id.tv_text);

        dialog.show();
        countDownTimer.start();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }
    }

    private CountDownTimer countDownTimer = new CountDownTimer(1000*59, 3200) {

        public void onTick(long millisUntilFinished) {

        }

        public void onFinish() {
            dismiss();
        }
    };
}