package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.OtpListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class OtpDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String title;
    private EditText etOtp;
    private OtpListener otpListener;
    private TextInputLayout layoutOtp;
    private String info;
    private TextView tvTimer, tvResendOTP;
    private boolean isRegistration = false;

    public OtpDialog(Context context, boolean isRegistration, OtpListener otpListener, String title, String info) {
        super(context);
        this.context            = context;
        this.otpListener        = otpListener;
        this.title              = title;
        this.info               = info;
        this.isRegistration     = isRegistration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_otp);
        initializeWidgets();
    }

    private void initializeWidgets() {
        etOtp               = findViewById(R.id.et_otp);
        layoutOtp           = findViewById(R.id.layout_otp);
        tvTimer             = findViewById(R.id.tvTimer);
        tvResendOTP         = findViewById(R.id.tvResendOTP);
        Button btnSubmit    = findViewById(R.id.btn_submit);
        Button btnCancel    = findViewById(R.id.btn_cancel);
        TextView tvTitle    = findViewById(R.id.tvHeader);
        TextView tvInfo     = findViewById(R.id.tvInfo);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        tvTitle.setText(title);
        tvInfo.setText(info);
        tvTimer.setTypeface(null, Typeface.ITALIC);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvResendOTP.setOnClickListener(this);

        if (isRegistration)
            btnSubmit.setText(context.getString(R.string.register));

        countDownTimer.start();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(1000*59, 100) {

        public void onTick(long millisUntilFinished) {
            String seconds = Long.toString(millisUntilFinished/1000);
            tvTimer.setText(String.format(context.getString(R.string.resend_otp_timer), seconds));
        }

        public void onFinish() {
            tvTimer.setTypeface(null, Typeface.NORMAL);
            tvTimer.setText(context.getString(R.string.didn_t_receive_otp));
            tvResendOTP.setVisibility(View.VISIBLE);
        }
    };

    public void startOtpTimer() {
        tvResendOTP.setVisibility(View.GONE);
        tvTimer.setTypeface(null, Typeface.ITALIC);
        countDownTimer.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Utils.vibrate(context);
                if (validate()) {
                    otpListener.onOtpReceived(etOtp.getText().toString().trim(), null);
                    dismiss();
                }
                break;
            case R.id.btn_cancel:
                Utils.vibrate(context);
                dismiss();
                break;
            case R.id.tvResendOTP:
                Utils.vibrateLong(context);
                otpListener.onResendOtp();
                break;
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etOtp.getText().toString().trim())) {
            etOtp.setError(context.getString(R.string.enter_otp));
            etOtp.requestFocus();
            layoutOtp.startAnimation(Utils.shakeError());
            return false;
        }
        if (context != null) {
            if (!Utils.isNetworkConnected(getContext())) {
                Toast.makeText(context, context.getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}
