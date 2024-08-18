package com.skilrock.retailapp.ui.fragments.rms;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.ForgotPasswordViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener {

    private ForgotPasswordViewModel viewModel;
    private TextInputEditText etUserName;
    private EditText etMobile;
    private View view;
    private TextView tvCountryCode;
    private ForgotPasswordDialog dialog;
    private Button button;
    private final String RMS = "rms";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
        viewModel.getLiveDataForgotPasswordOtp().observe(this, observerOtp);
        viewModel.getLiveDataForgotPasswordResendOtp().observe(this, observerResendOtp);
        viewModel.getLiveDataForgotPasswordReset().observe(this, observerReset);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        this.view               = view;
        etUserName              = view.findViewById(R.id.et_username);
        etMobile                = view.findViewById(R.id.et_mobile_number);
        tvCountryCode           = view.findViewById(R.id.tvCountryCode);
        button                  = view.findViewById(R.id.button);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        view.findViewById(R.id.tvBal).setVisibility(View.GONE);
        view.findViewById(R.id.iv_drawer).setVisibility(View.GONE);
        tvUsername.setText("");
        tvUserBalance.setText("");

        button.setOnClickListener(this);
        tvCountryCode.setOnClickListener(this);
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
        }

        Utils.countryCodeFocusOperation(Objects.requireNonNull(getActivity()), BuildConfig.app_name);
        etMobile.setOnEditorActionListener(this::onEditorAction);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCountryCode:
                Utils.openCountryCodeDialog(getActivity());
                break;
            case R.id.button:
                if (validate())
                    callOtpApi(false);
                break;
        }
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (TextUtils.isEmpty(getText(etUserName))) {
            etUserName.setError(getString(R.string.this_field_cannot_be_empty));
            etUserName.requestFocus();
            view.findViewById(R.id.til_user_name).startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(getText(etMobile))) {
            etMobile.setError(getString(R.string.this_field_cannot_be_empty));
            etMobile.requestFocus();
            view.findViewById(R.id.til_mobile).startAnimation(Utils.shakeError());
            return false;
        }
        if (getContext() != null) {
            if (!Utils.isNetworkConnected(getContext())) {
                Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    Observer<ForgotPasswordResponseBean> observerOtp = new Observer<ForgotPasswordResponseBean>() {
        @Override
        public void onChanged(@Nullable ForgotPasswordResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.service_error), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.service_error), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.service_error), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                String info = getString(R.string.otp_success_sent_to) + " " + getText(etMobile);
                dialog = new ForgotPasswordDialog(master, otpListener, "Forgot Password", info);
                dialog.setCancelable(false);
                dialog.show();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.service_error), getString(R.string.something_went_wrong));
        }
    };

    Observer<ForgotPasswordResponseBean> observerResendOtp = new Observer<ForgotPasswordResponseBean>() {
        @Override
        public void onChanged(@Nullable ForgotPasswordResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Toast.makeText(master, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            else if (response.getResponseCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                Toast.makeText(master, errorMsg, Toast.LENGTH_LONG).show();
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                Toast.makeText(master, errorMsg, Toast.LENGTH_LONG).show();
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                dialog.startOtpTimer();
                Toast.makeText(master, getString(R.string.otp_sent_successfully), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
        }
    };

    Observer<ForgotPasswordResponseBean> observerReset = new Observer<ForgotPasswordResponseBean>() {
        @Override
        public void onChanged(@Nullable ForgotPasswordResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();
            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.service_error), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.service_error), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.service_error), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                String msg = response.getResponseData().getData();
                Utils.showCustomSuccessDialogAndLogout(getContext(), getFragmentManager(), "", msg, true);
            }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.service_error), getString(R.string.something_went_wrong));
        }
    };

    private void callOtpApi(boolean isResendOtp) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        ProgressBarDialog.getProgressDialog().showProgress(master);
        if (isResendOtp)
            viewModel.callResendOtp(getText(etUserName), getText(tvCountryCode) + getText(etMobile));
        else
            viewModel.callOtp(getText(etUserName), getText(tvCountryCode) + getText(etMobile));
    }

    private void callResetApi(String otp, String newPassword, String confirmPassword) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        ForgotPasswordResetBean model = new ForgotPasswordResetBean();
        model.setUserName(getText(etUserName));
        model.setMobileNumber(getText(tvCountryCode) + getText(etMobile));
        model.setOtp(otp);
        model.setNewPassword(newPassword);
        model.setConfirmNewPassword(confirmPassword);
        ProgressBarDialog.getProgressDialog().showProgress(master);
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
