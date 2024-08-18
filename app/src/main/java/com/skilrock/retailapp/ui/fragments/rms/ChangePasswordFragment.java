package com.skilrock.retailapp.ui.fragments.rms;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.ChangePasswordRequestBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.ChangePasswordViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener {

    private ChangePasswordViewModel changePasswordViewModel;
    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private TextInputLayout tilOldPassword, tilNewPassword, tilConPassword;
    private String URL;
    private int minPassLength = 5;
    private String mTitle = "";
    private int pwdType = 2;
    private String passType;
    private String digits;
    private Button btnChangePassword;
    private final String RMS = "rms";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);

        changePasswordViewModel.getLiveDataChangePassword().observe(this, changePasswordResponseBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (changePasswordResponseBean == null)
                Utils.showCustomErrorDialog(getContext(), mTitle, getString(R.string.something_went_wrong));
            else if (changePasswordResponseBean.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(changePasswordResponseBean.getResponseMessage())?getString(R.string.some_internal_error):changePasswordResponseBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, changePasswordResponseBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), mTitle, errorMsg);
            }
            else if(changePasswordResponseBean.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, changePasswordResponseBean.getResponseData().getStatusCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(changePasswordResponseBean.getResponseData().getMessage())?getString(R.string.some_internal_error):changePasswordResponseBean.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, changePasswordResponseBean.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), mTitle, errorMsg);
            } else if (changePasswordResponseBean.getResponseData().getStatusCode() == 0) {
                String successMsg = "";

                if (passType.equalsIgnoreCase(getString(R.string.pin))) {
                    successMsg = getString(R.string.pin_changed_success);
                } else {
                    successMsg = getString(R.string.password_changed_success);
                }

                Utils.showCustomSuccessDialogAndLogout(getContext(), getFragmentManager(), "", successMsg, true);
            } else
                Utils.showCustomErrorDialog(getContext(), mTitle, getString(R.string.something_went_wrong));
        });
    }

    private void initializeWidgets(View view) {
        etOldPassword           = view.findViewById(R.id.et_old_password);
        etNewPassword           = view.findViewById(R.id.et_new_password);
        etConfirmPassword       = view.findViewById(R.id.et_confirm_password);
        tilOldPassword          = view.findViewById(R.id.til_old_password);
        tilNewPassword          = view.findViewById(R.id.til_new_password);
        tilConPassword          = view.findViewById(R.id.til_con_password);
        btnChangePassword       = view.findViewById(R.id.button_proceed);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.SISAL))
            Utils.setMaxLength(etOldPassword, 5);

        btnChangePassword.setOnClickListener(this);
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            tvTitle.setText(bundle.getString("title"));
            if (activity != null)
                activity.setTitle(bundle.getString("title"));
            mTitle = bundle.getString("title");
            URL = bundle.getString("url");
        }

        etConfirmPassword.setOnEditorActionListener(this::onEditorAction);

        String pwdRegex = ConfigData.getInstance().getConfigData().getRETAILPASSWORDREGEX();

        if (pwdRegex.equals("^[0-9]{5,5}$")) {
            minPassLength = 5;
            pwdType = InputType.TYPE_CLASS_NUMBER;
            etNewPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
            etOldPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
            tilOldPassword.setHint(getString(R.string.old_pin));
            tilNewPassword.setHint(getString(R.string.new_pin));
            tilConPassword.setHint(getString(R.string.confirm_pin));
            digits = getString(R.string.number_only_digits);
            passType = getString(R.string.pin);
        } else if (pwdRegex.equals("^[0-9]{8,16}$")) {
            minPassLength = 8;
            pwdType = InputType.TYPE_CLASS_NUMBER;
            etNewPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
            digits = getString(R.string.number_only_digits);
            passType = getString(R.string.password_);
        } else {
            minPassLength = 8;
            pwdType = InputType.TYPE_CLASS_TEXT;
            etNewPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
            digits = getString(R.string.alpha_numeric);
            passType = getString(R.string.password_);
        }

        etNewPassword.setInputType(pwdType | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        etConfirmPassword.setInputType(pwdType | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        etNewPassword.setKeyListener(DigitsKeyListener.getInstance(digits));
        etConfirmPassword.setKeyListener(DigitsKeyListener.getInstance(digits));

    }

    @Override
    public void onClick(View v) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        if (validate()) {
            if (!TextUtils.isEmpty(URL)) {
                ChangePasswordRequestBean model = new ChangePasswordRequestBean();
                model.setOldPassword(getText(etOldPassword));
                model.setNewPassword(getText(etNewPassword));
                model.setConfirmNewPassword(getText(etConfirmPassword));
                ProgressBarDialog.getProgressDialog().showProgress(master);

                changePasswordViewModel.changePasswordApi(PlayerData.getInstance().getToken(), URL, model);
            } else Toast.makeText(getContext(), getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(getText(etOldPassword))) {
            etOldPassword.setError(getString(R.string.required_filed));
            etOldPassword.requestFocus();
            tilOldPassword.startAnimation(Utils.shakeError());
            return false;
        }
        else if (TextUtils.isEmpty(getText(etNewPassword))) {
            etNewPassword.setError(getString(R.string.required_filed));
            etNewPassword.requestFocus();
            tilNewPassword.startAnimation(Utils.shakeError());
            return false;
        }
        else if (TextUtils.isEmpty(getText(etConfirmPassword))) {
            etConfirmPassword.setError(getString(R.string.required_filed));
            etConfirmPassword.requestFocus();
            tilConPassword.startAnimation(Utils.shakeError());
            return false;
        } else if (!getText(etNewPassword).equalsIgnoreCase(getText(etConfirmPassword))) {
            if (passType.equalsIgnoreCase(getString(R.string.pin)))
                etConfirmPassword.setError(getString(R.string.pin_mismatch));
            else
                etConfirmPassword.setError(getString(R.string.password_mismatch));

            etConfirmPassword.requestFocus();
            tilNewPassword.startAnimation(Utils.shakeError());
            tilConPassword.startAnimation(Utils.shakeError());
            return false;
        }
       /* else if (getText(etOldPassword).length() < 8) {
            etOldPassword.setError(getString(R.string.password_min_length));
            etOldPassword.requestFocus();
            tilOldPassword.startAnimation(Utils.shakeError());
            return false;
        }
        else if (getText(etNewPassword).length() < 8) {
            etNewPassword.setError(getString(R.string.password_min_length));
            etNewPassword.requestFocus();
            tilNewPassword.startAnimation(Utils.shakeError());
            return false;
        }
        else if (getText(etConfirmPassword).length() < 8) {
            etConfirmPassword.setError(getString(R.string.password_min_length));
            etConfirmPassword.requestFocus();
            tilConPassword.startAnimation(Utils.shakeError());
            return false;
        }*/

        if (getText(etNewPassword).length() < minPassLength) {
            etNewPassword.setError(getString(R.string.password_min_length, passType, minPassLength));
            etNewPassword.requestFocus();
            tilNewPassword.startAnimation(Utils.shakeError());
            return false;
        }

        if (getText(etConfirmPassword).length() < minPassLength) {
            etConfirmPassword.setError(getString(R.string.password_min_length, passType, minPassLength));
            etConfirmPassword.requestFocus();
            tilConPassword.startAnimation(Utils.shakeError());
            return false;
        }

        if (!validatePassword(etNewPassword.getText().toString())) {
            if (passType.equalsIgnoreCase(getString(R.string.pin)))
                etNewPassword.setError(getString(R.string.invalid_new_pin));
            else
                etNewPassword.setError(getString(R.string.invalid_new_password));

            etNewPassword.requestFocus();
            tilNewPassword.startAnimation(Utils.shakeError());
            return false;
        }

        if (!validatePassword(etConfirmPassword.getText().toString())) {
            if (passType.equalsIgnoreCase(getString(R.string.pin)))
                etNewPassword.setError(getString(R.string.invalid_new_pin));
            else
                etNewPassword.setError(getString(R.string.invalid_new_password));

            etConfirmPassword.requestFocus();
            etConfirmPassword.startAnimation(Utils.shakeError());
            return false;

        } else if (getText(etOldPassword).equalsIgnoreCase(getText(etNewPassword))) {
            if (passType.equalsIgnoreCase(getString(R.string.pin)))
                etNewPassword.setError(getString(R.string.same_old_new_pin));
            else
                etNewPassword.setError(getString(R.string.same_old_new_password));

            etNewPassword.requestFocus();
            tilOldPassword.startAnimation(Utils.shakeError());
            tilNewPassword.startAnimation(Utils.shakeError());
            return false;
        }
        return true;
    }

    private boolean validatePassword(String text) {
        String pwdRegex = ConfigData.getInstance().getConfigData().getRETAILPASSWORDREGEX();
        Pattern pattern = Pattern.compile(pwdRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    DialogInterface.OnClickListener changePasswordListener = (dialog, which) -> Utils.performLogoutCleanUp(getContext());

    private boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            btnChangePassword.performClick();
            return true;
        }
        return false;
    }

}
