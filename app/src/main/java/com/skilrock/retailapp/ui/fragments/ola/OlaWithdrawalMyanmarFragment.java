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
import com.skilrock.retailapp.dialog.OtpDialog;
import com.skilrock.retailapp.dialog.SuccessDialog;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.models.ola.OlaWithdrawalRequestBean;
import com.skilrock.retailapp.models.ola.OlaWithdrawalResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.ola.OlaWithdrawalViewModel;

import java.util.Objects;

public class OlaWithdrawalMyanmarFragment extends BaseFragment implements View.OnClickListener {

    private OlaWithdrawalViewModel viewModel;
    private EditText etPlayerName, etAmount, etWithdrawalCode;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private View view;
    private OtpDialog dialog;
    private Button button;
    private final String OLA = "ola";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ola_withdrawal_myanmar, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(OlaWithdrawalViewModel.class);
        viewModel.getLiveDataWithdrawal().observe(this, observerWithdrawal);
        viewModel.getLiveDataBalance().observe(this, observerBalance);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        this.view               = view;
        etPlayerName            = view.findViewById(R.id.et_mobile_number);
        etWithdrawalCode        = view.findViewById(R.id.et_withdraw_code);
        etAmount                = view.findViewById(R.id.et_amount);
        button                  = view.findViewById(R.id.button);
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

        etAmount.setOnEditorActionListener(this::onEditorAction);
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
            ConfirmationListener listener = OlaWithdrawalMyanmarFragment.this::callWithdrawalApi;
            String msg = getString(R.string.are_you_sure_to_withdrawal) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getActivity()))+" <font color=#606060>" + getText(etAmount) +
                    " </font>" + getString(R.string.for_player) + " <font color=#606060>" + getText(etPlayerName) + "</font>?";
            Utils.showConfirmationDialog(getContext(), false, msg, listener);
        }
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (TextUtils.isEmpty(getText(etPlayerName))) {
            etPlayerName.setError(getString(R.string.this_field_cannot_be_empty));
            etPlayerName.requestFocus();
            view.findViewById(R.id.til_mobile).startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(getText(etWithdrawalCode))) {
            etWithdrawalCode.setError(getString(R.string.this_field_cannot_be_empty));
            etWithdrawalCode.requestFocus();
            view.findViewById(R.id.til_withdraw_code).startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(getText(etAmount))) {
            etAmount.setError(getString(R.string.this_field_cannot_be_empty));
            etAmount.requestFocus();
            view.findViewById(R.id.til_amount).startAnimation(Utils.shakeError());
            return false;
        }
        if (getText(etAmount).equalsIgnoreCase(".")) {
            etAmount.setError(getString(R.string.invalid_input));
            etAmount.requestFocus();
            view.findViewById(R.id.til_amount).startAnimation(Utils.shakeError());
            return false;
        }
        if (Double.parseDouble(getText(etAmount)) == 0) {
            etAmount.setError(getString(R.string.greater_than_zero));
            etAmount.requestFocus();
            view.findViewById(R.id.til_amount).startAnimation(Utils.shakeError());
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

    Observer<OlaWithdrawalResponseBean> observerWithdrawal = new Observer<OlaWithdrawalResponseBean>() {
        @Override
        public void onChanged(@Nullable OlaWithdrawalResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) Utils.showCustomErrorDialog(getContext(), getString(R.string.withdrawal_error), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                ProgressBarDialog.getProgressDialog().showProgress(master);
                viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                    return;

                String errorMsg = Utils.getResponseMessage(master, OLA, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.withdrawal_error), errorMsg);
            }
        }
    };

    private void callWithdrawalApi() {
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "withdraw");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master);
            String domain = PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgCode() + "_" +
                    PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName();
            OlaWithdrawalRequestBean model = new OlaWithdrawalRequestBean();
            model.setAuthenticationCode(etWithdrawalCode.getText().toString());
            model.setMobileNO(getText(etPlayerName));
            model.setWithdrawalAmt(Double.parseDouble(getText(etAmount)));

            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                model.setPlrDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_OLA_MYANMAR")||
                        BuildConfig.FLAVOR.equalsIgnoreCase("pROD_OLA_MYANMAR")
                        ? "www.bet2winasia.com" : "www.igamew.com");
            else
                model.setPlrDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")
                        ? "www.camwinlotto.cm" : "www.camwinlotto.cm");

            model.setPlrMerchantCode(AppConstants.PLR_MERCHANT_CODE);
            model.setRetailDomainCode(urlBean.getRetailDomainCode());
            model.setRetailMerchantCode(urlBean.getRetailMerchantCode());
            model.setToken(PlayerData.getInstance().getToken().split(" ")[1]);
            model.setRetOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setRetUserId(Integer.parseInt(PlayerData.getInstance().getUserId()));
            model.setPlayerUserName(getText(etPlayerName));
            model.setAppType(AppConstants.APP_TYPE);
            model.setCurrencyId(AppConstants.CURRENCY_ID);
            model.setDevice(AppConstants.DEVICE_TYPE);
            model.setPaymentTypeCode(AppConstants.PAYMENT_TYPE);
            model.setPaymentTypeId(AppConstants.PAYMENT_TYPE_ID);
            model.setWithdrawalChannel(AppConstants.WITHDRAWAL_CHANNEL);
            model.setDeviceID(Utils.getDeviceSerialNumber());
            model.setModelCode(Utils.getModelCode());

            viewModel.callOlaWithdrawal(urlBean, model);
        }
    }

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        String errorMsg = getString(R.string.withdrawn_success_error_fetch_balance);

        if (loginBean == null)
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.withdrawal), errorMsg, 1, getFragmentManager());
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                PlayerData.getInstance().setLoginData(master, loginBean);
                refreshBalance();
                String successMsg = getString(R.string.amt_withdraw_success);
                SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), "", successMsg, 1);
                dialog.show();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            } else {
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.withdrawal), errorMsg, 1, getFragmentManager());
            }
        } else {
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.withdrawal), errorMsg, 1, getFragmentManager());
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

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        super.onDestroy();
    }
}
