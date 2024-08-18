package com.skilrock.retailapp.ui.activities.rms;

import android.app.ProgressDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.ForgotPasswordDialog;
import com.skilrock.retailapp.interfaces.OtpListener;
import com.skilrock.retailapp.models.rms.ForgotPasswordResetBean;
import com.skilrock.retailapp.models.rms.ForgotPasswordResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.ForgotPasswordViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ForgotPasswordViewModel viewModel;
    private ProgressDialog progressDialog;
    private TextInputEditText etUserName;
    private EditText etMobile;
    private TextView tvCountryCode;
    private ForgotPasswordDialog dialog;
    private Button button;
    private String mTitle;
    private final String RMS = "rms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.fragment_forgot_password);

        viewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
        viewModel.getLiveDataForgotPasswordOtp().observe(this, observerOtp);
        viewModel.getLiveDataForgotPasswordResendOtp().observe(this, observerResendOtp);
        viewModel.getLiveDataForgotPasswordReset().observe(this, observerReset);
        if (SharedPrefUtil.getLanguage(ForgotPasswordActivity.this).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        initializeWidgets();
    }

    private void initializeWidgets() {
        etUserName              = findViewById(R.id.et_username);
        etMobile                = findViewById(R.id.et_mobile_number);
        tvCountryCode           = findViewById(R.id.tvCountryCode);
        button                  = findViewById(R.id.button);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        TextView tvUsername     = findViewById(R.id.tvUserName);
        TextView tvUserBalance  = findViewById(R.id.tvUserBalance);
        findViewById(R.id.tvBal).setVisibility(View.GONE);
        //findViewById(R.id.iv_drawer).setVisibility(View.GONE);
        tvUsername.setText("");
        tvUserBalance.setText("");
        etMobile.setMaxEms(15);
        etMobile.setMinEms(8);
        button.setOnClickListener(this);
        tvCountryCode.setOnClickListener(this);
        findViewById(R.id.iv_drawer).setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTitle.setText(bundle.getString("title"));
            mTitle = bundle.getString("title");
        }

        Utils.countryCodeFocusOperation(ForgotPasswordActivity.this, BuildConfig.app_name);
        etMobile.setOnEditorActionListener(this::onEditorAction);
    }

    public void onClickBack(View view) {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCountryCode:
                Utils.openCountryCodeDialogForForgotPassword(ForgotPasswordActivity.this, BuildConfig.app_name);
                break;
            case R.id.button:
                if (validate())
                    callOtpApi(false);
                break;
            case R.id.iv_drawer:
                finish();
                break;
        }
    }

    private boolean validate() {
        Utils.vibrate(ForgotPasswordActivity.this);
        if (TextUtils.isEmpty(getText(etUserName))) {
            etUserName.setError(getString(R.string.this_field_cannot_be_empty));
            etUserName.requestFocus();
            findViewById(R.id.til_user_name).startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(getText(etMobile))) {
            etMobile.setError(getString(R.string.this_field_cannot_be_empty));
            etMobile.requestFocus();
            findViewById(R.id.til_mobile).startAnimation(Utils.shakeError());
            return false;
        }

        if (etMobile.getText().toString().length() < 8 || etMobile.getText().toString().length() > 15) {
            etMobile.setError(getString(R.string.mobile_length_error));
            etMobile.requestFocus();
            findViewById(R.id.til_mobile).startAnimation(Utils.shakeError());
            return false;
        }

        return true;
    }

    Observer<ForgotPasswordResponseBean> observerOtp = new Observer<ForgotPasswordResponseBean>() {
        @Override
        public void onChanged(@Nullable ForgotPasswordResponseBean response) {
            if (progressDialog != null)
                progressDialog.dismiss();
            if (response == null)
                Utils.showCustomErrorDialog(ForgotPasswordActivity.this, getString(R.string.forgot_pin_password), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(ForgotPasswordActivity.this, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(ForgotPasswordActivity.this, getString(R.string.forgot_pin_password), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(ForgotPasswordActivity.this, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(ForgotPasswordActivity.this, getString(R.string.forgot_pin_password), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                String info = getString(R.string.otp_success_sent_to) + " " + getText(etMobile);
                dialog = new ForgotPasswordDialog(ForgotPasswordActivity.this, otpListener, mTitle, info);
                dialog.setCancelable(false);
                dialog.show();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            }
            else
                Utils.showCustomErrorDialog(ForgotPasswordActivity.this, getString(R.string.forgot_pin_password), getString(R.string.something_went_wrong));
        }
    };

    Observer<ForgotPasswordResponseBean> observerResendOtp = new Observer<ForgotPasswordResponseBean>() {
        @Override
        public void onChanged(@Nullable ForgotPasswordResponseBean response) {
            if (progressDialog != null)
                progressDialog.dismiss();
            if (response == null)
                Toast.makeText(ForgotPasswordActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(ForgotPasswordActivity.this, RMS, response.getResponseCode());
                Toast.makeText(ForgotPasswordActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(ForgotPasswordActivity.this, RMS, response.getResponseData().getStatusCode());
                Toast.makeText(ForgotPasswordActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                dialog.startOtpTimer();
                Toast.makeText(ForgotPasswordActivity.this, getString(R.string.otp_sent_successfully), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(ForgotPasswordActivity.this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
        }
    };

    Observer<ForgotPasswordResponseBean> observerReset = new Observer<ForgotPasswordResponseBean>() {
        @Override
        public void onChanged(@Nullable ForgotPasswordResponseBean response) {
            if (progressDialog != null)
                progressDialog.dismiss();
            if (response == null)
                Utils.showCustomErrorDialog(ForgotPasswordActivity.this, getString(R.string.forgot_pin_password), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(ForgotPasswordActivity.this, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(ForgotPasswordActivity.this, getString(R.string.forgot_pin_password), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(ForgotPasswordActivity.this, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(ForgotPasswordActivity.this, getString(R.string.forgot_pin_password), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                String msg = getString(R.string.pass_change_success);
                Utils.showCustomSuccessDialogAndLogout(ForgotPasswordActivity.this, "", msg);
            }
            else
                Utils.showCustomErrorDialog(ForgotPasswordActivity.this, getString(R.string.forgot_pin_password), getString(R.string.something_went_wrong));
        }
    };

    private void callOtpApi(boolean isResendOtp) {
        if (!Utils.isNetworkConnected(ForgotPasswordActivity.this)) {
            Toast.makeText( ForgotPasswordActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog = Utils.getProgressDialog(ForgotPasswordActivity.this);
        if (isResendOtp)
            viewModel.callResendOtp(getText(etUserName), getText(tvCountryCode) + getText(etMobile));
        else
            viewModel.callOtp(getText(etUserName), getText(tvCountryCode) + getText(etMobile));
    }

    private void callResetApi(String otp, String newPassword, String confirmPassword) {
        if (!Utils.isNetworkConnected(ForgotPasswordActivity.this)) {
            Toast.makeText( ForgotPasswordActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        ForgotPasswordResetBean model = new ForgotPasswordResetBean();
        model.setUserName(getText(etUserName));
        model.setMobileNumber(getText(tvCountryCode) + getText(etMobile));
        model.setOtp(otp);
        model.setNewPassword(newPassword);
        model.setConfirmNewPassword(confirmPassword);
        progressDialog = Utils.getProgressDialog(ForgotPasswordActivity.this);
        viewModel.callReset(model);
    }

    OtpListener otpListener = new OtpListener() {
        @Override
        public void onOtpReceived(String otp, ArrayList<String> listData) {
            callResetApi(otp, listData.get(0), listData.get(1));
        }

        @Override
        public void onResendOtp() {
            callOtpApi(true);
        }
    };

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    private boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            button.performClick();
            return true;
        }
        return false;
    }

}
