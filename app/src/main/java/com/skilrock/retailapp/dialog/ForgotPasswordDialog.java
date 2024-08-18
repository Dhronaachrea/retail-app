package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.google.android.material.textfield.TextInputLayout;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.OtpListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class ForgotPasswordDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String title;
    private int pwdType = 2;
    private String passType;
    int minPassLength = 5;
    private String digits;
    private OtpListener otpListener;
    private TextInputLayout layoutOtp, layoutPassword, layoutConPassword;
    private EditText etOtp, etPassword, etConfirmPassword;
    private String info;
    private TextView tvTimer, tvResendOTP;

    public ForgotPasswordDialog(Context context, OtpListener otpListener, String title, String info) {
        super(context);
        this.context        = context;
        this.otpListener    = otpListener;
        this.title          = title;
        this.info           = info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_forgot_password);
        initializeWidgets();
    }

    private void initializeWidgets() {
        etOtp               = findViewById(R.id.et_otp);
        layoutOtp           = findViewById(R.id.layout_otp);
        layoutPassword      = findViewById(R.id.til_password);
        layoutConPassword   = findViewById(R.id.til_confirm_password);
        etPassword          = findViewById(R.id.et_password);
        etConfirmPassword   = findViewById(R.id.et_confirm_password);
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

        countDownTimer.start();

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.SISAL)) {
            minPassLength = 5;
            pwdType = InputType.TYPE_CLASS_NUMBER;
            etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
            layoutPassword.setHint(context.getString(R.string.new_pin));
            layoutConPassword.setHint(context.getString(R.string.confirm_pin));
            digits = context.getString(R.string.number_only_digits);
            passType = context.getString(R.string.pin);
        } else {
            minPassLength = 8;
            pwdType = InputType.TYPE_CLASS_TEXT;
            etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
            digits = context.getString(R.string.alpha_numeric);
            passType = context.getString(R.string.password_);
        }

        etPassword.setInputType(pwdType | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        etConfirmPassword.setInputType(pwdType | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        etPassword.setKeyListener(DigitsKeyListener.getInstance(digits));
        etConfirmPassword.setKeyListener(DigitsKeyListener.getInstance(digits));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Utils.vibrate(context);
                if (validate()) {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(getText(etPassword));
                    list.add(getText(etConfirmPassword));
                    otpListener.onOtpReceived(getText(etOtp), list);
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
        if (TextUtils.isEmpty(getText(etOtp))) {
            etOtp.setError(context.getString(R.string.this_field_cannot_be_empty));
            etOtp.requestFocus();
            layoutOtp.startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(getText(etPassword))) {
            //etPassword.setError(context.getString(R.string.this_field_cannot_be_empty));
            Utils.showRedToast(context, context.getString(R.string.please_enter_password));
            etPassword.requestFocus();
            layoutPassword.startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(getText(etConfirmPassword))) {
            //etConfirmPassword.setError(context.getString(R.string.this_field_cannot_be_empty));
            Utils.showRedToast(context, context.getString(R.string.please_enter_confirn_password));
            etConfirmPassword.requestFocus();
            layoutConPassword.startAnimation(Utils.shakeError());
            return false;
        }
        if (!getText(etPassword).equals(getText(etConfirmPassword))) {
            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.ACDC)) {
                //etPassword.setError(context.getString(R.string.password_doesnot_match));
                Utils.showRedToast(context, context.getString(R.string.password_doesnot_match));
            } else {
                //etPassword.setError(context.getString(R.string.pin_mismatch));
                Utils.showRedToast(context, context.getString(R.string.pin_mismatch));
            }

            etPassword.requestFocus();
            layoutPassword.startAnimation(Utils.shakeError());
            layoutConPassword.startAnimation(Utils.shakeError());
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

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }
}
