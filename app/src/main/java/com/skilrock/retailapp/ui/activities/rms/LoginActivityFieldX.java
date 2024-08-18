package com.skilrock.retailapp.ui.activities.rms;

import android.app.Activity;
import android.app.ProgressDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.LanguageSelectionDialog;
import com.skilrock.retailapp.interfaces.EventListener;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.rms.TokenBean;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.LoginViewModel;

import java.util.Objects;

public class LoginActivityFieldX extends AppCompatActivity implements View.OnClickListener, AppConstants {

    private LoginViewModel loginViewModel;
    private EditText etUsername, etPassword;
    private ProgressDialog progressDialog;
    private TextView textLanguage;
    private Button btnLogin;
    private TextInputLayout layoutPassword, layoutUserName;
    private final String RMS = "rms";
    private ImageView imageLogo;
    private LinearLayout llLanguage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();

        if (SharedPrefUtil.getLanguage(LoginActivityFieldX.this).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        setContentView(R.layout.login_fragment_fieldx);
        /*if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.ACDC))
            setContentView(R.layout.login_fragment_acdc);
        else
            setContentView(R.layout.login_fragment_sisal);*/
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        initializeWidgets();
    }

    public void initializeWidgets() {
        etUsername      = findViewById(R.id.et_username);
        etPassword      = findViewById(R.id.et_password);
        layoutPassword  = findViewById(R.id.textInputLayoutPassword);
        layoutUserName  = findViewById(R.id.textInputLayoutUser);
        btnLogin        = findViewById(R.id.btn_login);
        llLanguage      = findViewById(R.id.view_language);
        textLanguage    = findViewById(R.id.tv_language);
        imageLogo       = findViewById(R.id.image_logo);

        if (!TextUtils.isEmpty(SharedPrefUtil.getUserName(LoginActivityFieldX.this))) {
            etUsername.setText(SharedPrefUtil.getUserName(LoginActivityFieldX.this));
            etPassword.requestFocus();
        }

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.ACDC))
            llLanguage.setVisibility(View.GONE);

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.SISAL))
            Utils.setMaxLength(etPassword, 5);

        btnLogin.setOnClickListener(this);
        llLanguage.setOnClickListener(this);
        findViewById(R.id.forgot_password).setOnClickListener(this);

        etPassword.setOnEditorActionListener(this::onEditorAction);

        languageCallBack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Utils.vibrate(this);
                //Utils.playWinningSound(LoginActivity.this, R.raw.small_1);

                if (validate()) {
                    Context context = LoginActivityFieldX.this;
                    if (SharedPrefUtil.getString(context, SharedPrefUtil.AUTH_TOKEN).isEmpty()) {
                        progressDialog = Utils.getProgressDialog(context);
                        loginViewModel.getLiveDataToken().observe(this, observerTokenBean);
                        loginViewModel.getLiveDataLogin().observe(this, observerLoginBean);
                        loginViewModel.callTokenApi(etUsername.getText().toString().trim(), etPassword.getText().toString().trim(), "NA", "NA");
                    }
                }
                break;

            case R.id.forgot_password:
                Utils.vibrate(LoginActivityFieldX.this);
                if (Utils.isNetworkConnected(LoginActivityFieldX.this)) {
                    Bundle bundle = new Bundle();

                    if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.ACDC))
                        bundle.putString("title", getString(R.string.forgot_password_title));
                    else
                        bundle.putString("title", getString(R.string.forgot_pin_title));

                    Intent intent = new Intent( LoginActivityFieldX.this, ForgotPasswordActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivityFieldX.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                break;
            case R.id.view_language:
                onChangeLanguage();
                break;
        }
    }

    Observer<TokenBean> observerTokenBean = new Observer<TokenBean>() {
        @Override
        public void onChanged(@Nullable TokenBean tokenBean) {
            if (progressDialog != null)
                progressDialog.dismiss();

            if (tokenBean == null)
                Utils.showCustomErrorDialog( LoginActivityFieldX.this, getString(R.string.login_error), getString(R.string.something_went_wrong));
            else if (tokenBean.getResponseCode() == 0) {
                TokenBean.ResponseData responseData = tokenBean.getResponseData();
                if (responseData.getStatusCode() == 0) {
                    progressDialog = Utils.getProgressDialog( LoginActivityFieldX.this);
                    loginViewModel.callLoginApi("Bearer " + tokenBean.getResponseData().getAuthToken());
                }
                else {
                    //String errorMsg = TextUtils.isEmpty(responseData.getMessage()) ? getString(R.string.problem_in_loggin_in) : responseData.getMessage();
                    String errorMsg = Utils.getResponseMessage(LoginActivityFieldX.this, RMS, responseData.getStatusCode());
                    Utils.showCustomErrorDialog( LoginActivityFieldX.this, getString(R.string.login_error), errorMsg);
                }
            }
            else {
                //String errorMsg = TextUtils.isEmpty(tokenBean.getResponseMessage()) ? getString(R.string.some_internal_error) : tokenBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(LoginActivityFieldX.this, RMS, tokenBean.getResponseCode());
                Utils.showCustomErrorDialog( LoginActivityFieldX.this, getString(R.string.login_error), errorMsg);
            }
        }
    };

    Observer<LoginBean> observerLoginBean = new Observer<LoginBean>() {
        @Override
        public void onChanged(@Nullable LoginBean loginBean) {
            if (progressDialog != null)
                progressDialog.dismiss();

            if (loginBean == null)
                Utils.showCustomErrorDialog( LoginActivityFieldX.this, getString(R.string.login_error), getString(R.string.something_went_wrong));
            else if (loginBean.getResponseCode() == 0) {
                LoginBean.ResponseData responseData = loginBean.getResponseData();
                if (responseData.getStatusCode() == 0) {
                    if (responseData.getData().getOrgTypeCode().equalsIgnoreCase("BO")) {
                        saveUserName(etUsername.getText().toString());
                        PlayerData.getInstance().setLoginData(LoginActivityFieldX.this, loginBean);
                        Intent intent = new Intent(LoginActivityFieldX.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                        Utils.showCustomErrorDialog(LoginActivityFieldX.this, getString(R.string.login_error), getString(R.string.unauthorized_user_msg));
                }
                else {
                    //String errorMsg = TextUtils.isEmpty(responseData.getMessage()) ? getString(R.string.problem_in_loggin_in) : responseData.getMessage();
                    String errorMsg = Utils.getResponseMessage(LoginActivityFieldX.this, RMS, responseData.getStatusCode());
                    Utils.showCustomErrorDialog( LoginActivityFieldX.this, getString(R.string.login_error), errorMsg);
                }
            }
            else {
                //String errorMsg = TextUtils.isEmpty(loginBean.getResponseMessage()) ? getString(R.string.some_internal_error) : loginBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(LoginActivityFieldX.this, RMS, loginBean.getResponseCode());
                Utils.showCustomErrorDialog( LoginActivityFieldX.this, getString(R.string.login_error), errorMsg);
            }
        }
    };

    private void onChangeLanguage() {
        Log.w("log", "App Name: " + BuildConfig.app_name);
        Log.w("log", "App Language: " + SharedPrefUtil.getString(this, SharedPrefUtil.APP_LANGUAGE));
        EventListener listener = this::languageCallBack;

        LanguageSelectionDialog dialog = new LanguageSelectionDialog(LoginActivityFieldX.this, listener);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void languageCallBack() {
        switch (SharedPrefUtil.getLanguage(LoginActivityFieldX.this)) {
            case LANGUAGE_ARABIC:
                textLanguage.setText(AppConstants.ARABIC);
                break;
            case LANGUAGE_ENGLISH:
                textLanguage.setText(AppConstants.ENGLISH);
                break;
            case LANGUAGE_FRENCH:
                textLanguage.setText(AppConstants.FRENCH);
                break;
            default:
                textLanguage.setText(AppConstants.FRENCH);
                break;
        }
    }
    private boolean validate() {
        if (TextUtils.isEmpty(etUsername.getText().toString().trim())) {
            etUsername.setError(getString(R.string.enter_user_name));
            etUsername.requestFocus();
            layoutUserName.startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.ACDC))
                etPassword.setError(getString(R.string.enter_password));
            else
                etPassword.setError(getString(R.string.enter_pin));

            etPassword.requestFocus();
            layoutPassword.startAnimation(Utils.shakeError());
            return false;
        }
        if (!Utils.isNetworkConnected(LoginActivityFieldX.this)) {
            Toast.makeText( LoginActivityFieldX.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("log", "~~~ LOGIN FRAGMENT: onDestroyView() ~~~");
        loginViewModel.getLiveDataToken().removeObserver(observerTokenBean);
        loginViewModel.getLiveDataLogin().removeObserver(observerLoginBean);
    }

    private boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            btnLogin.performClick();
            return true;
        }
        return false;
    }

    private void saveUserName(String name) {
        SharedPrefUtil.putUserName(LoginActivityFieldX.this, name);
    }
}
