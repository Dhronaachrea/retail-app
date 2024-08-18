package com.skilrock.retailapp.ui.fragments.ola;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.OtpDialog;
import com.skilrock.retailapp.dialog.SuccessDialog;
import com.skilrock.retailapp.interfaces.OtpListener;
import com.skilrock.retailapp.models.ResponseCodeMessageBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.models.ola.OlaOtpBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchResponseBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationRequestBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.ola.OlaRegistrationViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class OlaRegistrationFragment extends BaseFragment implements View.OnClickListener {

    private OlaRegistrationViewModel viewModel;
    private EditText etFirstName, etLastName, etAddress, etMobile, etAmount;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private View view;
    private OtpDialog dialog;
    private Button button;
    private boolean isNumberRegistered = false;
    private TextView tvRegistered;
    private boolean isFragmentActive = true;
    private String depositErrorMessage = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ola_registration, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(OlaRegistrationViewModel.class);
        viewModel.getOtpLiveData().observe(this, observerOtp);
        viewModel.getResendOtpLiveData().observe(this, observerResendOtp);
        viewModel.getLiveDataRegistration().observe(this, observerRegistration);
        viewModel.getPlsSearchLiveData().observe(this, observerPlrSearch);
        viewModel.getLiveDataBalance().observe(this, observerBalance);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        this.view               = view;
        etFirstName             = view.findViewById(R.id.et_first_name);
        etLastName              = view.findViewById(R.id.et_last_name);
        etAddress               = view.findViewById(R.id.et_address);
        etMobile                = view.findViewById(R.id.et_mobile_number);
        etAmount                = view.findViewById(R.id.et_amount);
        button                  = view.findViewById(R.id.button);
        tvRegistered            = view.findViewById(R.id.tvRegistered);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        button.setOnClickListener(this);
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        etAddress.setOnEditorActionListener(this::onEditorAction);
        etMobile.setOnFocusChangeListener(this::onFocusChange);
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.contains(".")) {
                    String[] data = str.split("\\.");
                    if (data.length > 1) {
                        if (data[1].length() > 2) {
                            String txt = data[0] + "." + data[1].substring(0, 2);
                            etAmount.setText(txt);
                            etAmount.setSelection(txt.length());
                        }
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (validate()) {
            //etAddress.
            callOtpApi(false);
        }
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (TextUtils.isEmpty(getText(etMobile))) {
            etMobile.setError(getString(R.string.this_field_cannot_be_empty));
            etMobile.requestFocus();
            view.findViewById(R.id.til_mobile).startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(getText(etFirstName))) {
            etFirstName.setError(getString(R.string.this_field_cannot_be_empty));
            etFirstName.requestFocus();
            view.findViewById(R.id.til_first_name).startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(getText(etLastName))) {
            etLastName.setError(getString(R.string.this_field_cannot_be_empty));
            etLastName.requestFocus();
            view.findViewById(R.id.til_last_name).startAnimation(Utils.shakeError());
            return false;
        }
        if (getText(etAmount).equalsIgnoreCase(".")) {
            etAmount.setError("Invalid Input");
            etAmount.requestFocus();
            view.findViewById(R.id.til_amount).startAnimation(Utils.shakeError());
            return false;
        }
        if (!TextUtils.isEmpty(getText(etAmount)) && Double.parseDouble(getText(etAmount)) < 1) {
            etAmount.setError("Amount must be equal to or greater than 1");
            etAmount.requestFocus();
            view.findViewById(R.id.til_amount).startAnimation(Utils.shakeError());
            return false;
        }

        if (TextUtils.isEmpty(getText(etAddress))) {
            etAddress.setError(getString(R.string.this_field_cannot_be_empty));
            etAddress.requestFocus();
            view.findViewById(R.id.til_address).startAnimation(Utils.shakeError());
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

    Observer<ResponseCodeMessageBean> observerOtp = new Observer<ResponseCodeMessageBean>() {
        @Override
        public void onChanged(@Nullable ResponseCodeMessageBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) Utils.showCustomErrorDialog(getContext(), getString(R.string.registration_error), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                String info = getString(R.string.otp_success_sent_to) + " " + getText(etMobile);
                dialog = new OtpDialog(master, true, otpListener, getString(R.string.registration), info);
                dialog.setCancelable(false);
                dialog.show();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                    return;

                String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.registration_error), errorMsg);
            }
        }
    };

    Observer<ResponseCodeMessageBean> observerResendOtp = new Observer<ResponseCodeMessageBean>() {
        @Override
        public void onChanged(@Nullable ResponseCodeMessageBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) Toast.makeText(master, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            else if (response.getResponseCode() == 0) {
                Toast.makeText(master, getString(R.string.otp_sent_successfully), Toast.LENGTH_SHORT).show();
                dialog.startOtpTimer();
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                    return;

                String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                Toast.makeText(master, errorMsg, Toast.LENGTH_LONG).show();
            }
        }
    };

    Observer<OlaRegistrationResponseBean> observerRegistration = new Observer<OlaRegistrationResponseBean>() {
        @Override
        public void onChanged(@Nullable OlaRegistrationResponseBean response) {
            depositErrorMessage = "";
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) Utils.showCustomErrorDialog(getContext(), getString(R.string.registration_error), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                OlaRegistrationResponseBean.ResponseData.DepositResponseVO depositResponseVO = response.getResponseData().getDepositResponseVO();
                if (depositResponseVO != null) {
                    if (depositResponseVO.getRespCode() != 0)
                        depositErrorMessage = "\n\n(Deposit Failed: " + depositResponseVO.getRespMsg() + ")";
                }
                ProgressBarDialog.getProgressDialog().showProgress(master);
                viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                    return;

                String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.registration_error), errorMsg);
            }
        }
    };

    Observer<LoginBean> observerBalance = new Observer<LoginBean>() {
        @Override
        public void onChanged(@Nullable LoginBean loginBean) {
            ProgressBarDialog.getProgressDialog().dismiss();

            String errorMsg = getString(R.string.registration_success_error_fetch_balance);

            if (loginBean == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.registration), errorMsg, 1, getFragmentManager());
            else if (loginBean.getResponseCode() == 0) {
                LoginBean.ResponseData responseData = loginBean.getResponseData();
                if (responseData.getStatusCode() == 0) {
                    PlayerData.getInstance().setLoginData(master, loginBean);
                    refreshBalance();
                    String successMsg = getString(R.string.player_registered_success) + depositErrorMessage;
                    SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), "", successMsg, 1);
                    dialog.show();
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                } else {
                    Utils.showCustomErrorDialogPop(getContext(), getString(R.string.registration), errorMsg, 1, getFragmentManager());
                }
            } else {
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.registration), errorMsg, 1, getFragmentManager());
            }
        }
    };

    Observer<OlaPlayerSearchResponseBean> observerPlrSearch = new Observer<OlaPlayerSearchResponseBean>() {

        @Override
        public void onChanged(@Nullable OlaPlayerSearchResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            openKeyboard();
            if (response == null) {
                Utils.showCustomErrorDialog(getContext(), getString(R.string.registration_error), getString(R.string.something_went_wrong));
            }
            else if (response.getResponseCode() == 0) {
                if ((response.getResponseData() != null && response.getResponseData().getPlayers().size() < 1) ||
                        (response.getResponseData() != null &&  response.getResponseData().getTotalResults() == 0)) {
                    // NOT REGISTERED
                    isNumberRegistered = false;
                    tvRegistered.setVisibility(View.GONE);
                    etMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_check, 0);
                }
                else {
                    // REGISTERED
                    isNumberRegistered = true;
                    tvRegistered.setVisibility(View.VISIBLE);
                    tvRegistered.setText(getString(R.string.this_number_is_already_registered));
                    etMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_cross, 0);
                }
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode())) return;

                if (response.getResponseCode() == 1070) {
                    // NOT REGISTERED
                    isNumberRegistered = false;
                    tvRegistered.setVisibility(View.GONE);
                    etMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_check, 0);
                }
                else {
                    //String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                    //Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), errorMsg);
                    //Toast.makeText(master, "", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void callOtpApi(boolean isResendOtp) {
        etAddress.requestFocus();
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "otp");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master);
            double amount;
            if (TextUtils.isEmpty(getText(etAmount)))
                amount = 0;
            else
                amount = Double.parseDouble(getText(etAmount));
            String domain = PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgCode() + "_" +
                    PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName();
            OlaOtpBean model = new OlaOtpBean();
            model.setAmount(amount);
            model.setMobileNo(getText(etMobile));
            model.setPlrDomainCode(domain);
            model.setPlrMerchantCode(AppConstants.PLR_MERCHANT_CODE);
            model.setRetailDomainCode(urlBean.getRetailDomainCode());
            model.setRetailMerchantCode(urlBean.getRetailMerchantCode());
            model.setToken(PlayerData.getInstance().getToken().split(" ")[1]);
            model.setUserId(Integer.parseInt(PlayerData.getInstance().getUserId()));
            model.setType("REGISTRATION");

            if (isResendOtp)
                viewModel.callResendOtp(urlBean, model);
            else
                viewModel.callOtp(urlBean, model);

        }
    }

    private void callRegistrationApi(String otp) {
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "registration");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master);
            double amount;
            if (TextUtils.isEmpty(getText(etAmount)))
                amount = 0;
            else
                amount = Double.parseDouble(getText(etAmount));

            String domain = PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgCode() + "_" +
                    PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName();
            OlaRegistrationRequestBean model = new OlaRegistrationRequestBean();
            model.setFirstName(getText(etFirstName));
            model.setLastName(getText(etLastName));
            model.setAddress(getText(etAddress));
            model.setPhone(getText(etMobile));
            model.setDepositAmt(amount);
            model.setOtp(otp);
            model.setPlrDomainCode(domain);
            model.setPlrMerchantCode(AppConstants.PLR_MERCHANT_CODE);
            model.setRetailDomainCode(urlBean.getRetailDomainCode());
            model.setRetailMerchantCode(urlBean.getRetailMerchantCode());
            model.setToken(PlayerData.getInstance().getToken().split(" ")[1]);
            model.setRetOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setRetUserId(Integer.parseInt(PlayerData.getInstance().getUserId()));
            model.setPlayerUserName(getText(etMobile));
            model.setCountry(AppConstants.COUNTRY);
            model.setDeviceType(AppConstants.DEVICE_TYPE);
            model.setTerminalId(AppConstants.TERMINAL_ID);
            model.setAppType(AppConstants.APP_TYPE);
            model.setPaymentType(AppConstants.PAYMENT_TYPE);

            viewModel.callOlaRegistration(urlBean, model);
        }
    }

    private void callPlayerSearchApi() {
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "searchPlayer");
        if (urlBean != null) {
            Log.e("log", "~~~ callPlayerSearchApi() ~~~");
            ProgressBarDialog.getProgressDialog().showProgress(master);

            String domain = PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgCode() + "_" +
                    PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName();
            OlaPlayerSearchRequestBean model = new OlaPlayerSearchRequestBean();
            model.setAccept(urlBean.getAccept());
            model.setContentType(urlBean.getContentType());
            model.setPageIndex(1);
            model.setPageSize(AppConstants.PAGE_SIZE);
            model.setPassword(urlBean.getPassword());
            model.setPhone(getText(etMobile));
            model.setPlrDomainCode(domain);
            model.setRetailDomainCode(urlBean.getRetailDomainCode());
            model.setRetailOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setUrl(urlBean.getUrl());
            model.setUserName(urlBean.getUserName());

            viewModel.callOlaPlayerSearchApi(model);
        }
    }

    OtpListener otpListener = new OtpListener() {
        @Override
        public void onOtpReceived(String otp, ArrayList<String> listData) {
            callRegistrationApi(otp);
        }

        @Override
        public void onResendOtp() {
            callOtpApi(true);
        }
    };

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
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

    private void onFocusChange(View view1, boolean hasFocus) {
        if (!hasFocus) {
            if (getText(etMobile).length() == 0) {
                tvRegistered.setVisibility(View.VISIBLE);
                tvRegistered.setText(getString(R.string.plz_enter_mobile_number));
                etMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_cross, 0);
            }
            else if (getText(etMobile).length() != 10) {
                tvRegistered.setVisibility(View.VISIBLE);
                tvRegistered.setText(getString(R.string.invalid_mobile_number));
                etMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_cross, 0);
            }
            else {
                if (isFragmentActive)
                    callPlayerSearchApi();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFragmentActive = false;
    }

    private void openKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager)master.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null){
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }
    }

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        super.onDestroy();
    }
}
