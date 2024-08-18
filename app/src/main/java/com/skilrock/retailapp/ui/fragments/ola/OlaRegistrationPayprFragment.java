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
import android.text.TextUtils;
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

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.OlaRegistrationSuccessDialog;
import com.skilrock.retailapp.interfaces.DialogCloseListener;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarRequestBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.scratch.OlaRegistrationPrintBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;
import com.skilrock.retailapp.viewmodels.ola.OlaRegistrationViewModel;

import java.util.Objects;

public class OlaRegistrationPayprFragment extends BaseFragment implements View.OnClickListener {

    private OlaRegistrationViewModel viewModel;
    private EditText etAmount;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private View view;
    private Button button;
    private String depositErrorMessage = "";
    byte data []=null;
    private OlaRegistrationMyanmarResponseBean REGISTRATION_RESPONSE;
    private final String OLA = "ola";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ola_registration_paypr, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(OlaRegistrationViewModel.class);
        viewModel.getLiveDataRegistrationMyanmar().observe(this, observerRegistrationMyanmar);
        viewModel.getLiveDataBalance().observe(this, observerBalance);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        this.view               = view;
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
    }

    @Override
    public void onClick(View v) {
        if (validate()) {
            callRegistrationMyanmarApi();
        }
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (getText(etAmount).equalsIgnoreCase(".")) {
            etAmount.setError(getString(R.string.invalid_input));
            etAmount.requestFocus();
            view.findViewById(R.id.til_amount).startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(getText(etAmount))) {
            etAmount.setError(getString(R.string.this_field_cannot_be_empty));
            etAmount.requestFocus();
            view.findViewById(R.id.til_amount).startAnimation(Utils.shakeError());
            return false;
        }
        if (!TextUtils.isEmpty(getText(etAmount)) && Double.parseDouble(getText(etAmount)) < 1) {
            etAmount.setError(getString(R.string.amt_greater_than_one));
            etAmount.requestFocus();
            view.findViewById(R.id.til_amount).startAnimation(Utils.shakeError());
            return false;
        }
        return true;
    }

    Observer<OlaRegistrationMyanmarResponseBean> observerRegistrationMyanmar = new Observer<OlaRegistrationMyanmarResponseBean>() {
        @Override
        public void onChanged(@Nullable OlaRegistrationMyanmarResponseBean response) {
            depositErrorMessage = "";
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) Utils.showCustomErrorDialog(getContext(), getString(R.string.registration_error), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                REGISTRATION_RESPONSE = response;
                OlaRegistrationMyanmarResponseBean.ResponseData.DepositResponseVO depositResponseVO = response.getResponseData().getDepositResponseVO();
                if (depositResponseVO != null) {
                    if (depositResponseVO.getRespCode() != 0)
                        depositErrorMessage = "\n\n(Deposit Failed: " + depositResponseVO.getRespMsg() + ")";
                }

                ProgressBarDialog.getProgressDialog().showProgress(master);
                viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                    return;

                String errorMsg = Utils.getResponseMessage(master, OLA, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.registration_error), errorMsg);
            }
        }
    };

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (REGISTRATION_RESPONSE != null) {
            if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO)) {
                printReceipt(REGISTRATION_RESPONSE);
            }
            String USER_NAME = REGISTRATION_RESPONSE.getResponseData().getUsername();
            String PASSWORD = REGISTRATION_RESPONSE.getResponseData().getPassword();
            boolean IS_DEPOSIT_ERROR;
            String DEPOSIT_AMOUNT;
            if (REGISTRATION_RESPONSE.getResponseData().getDepositResponseVO().getRespCode() != 0) {
                DEPOSIT_AMOUNT = REGISTRATION_RESPONSE.getResponseData().getDepositResponseVO().getRespMsg();
                IS_DEPOSIT_ERROR = true;
            } else {
                DEPOSIT_AMOUNT = "" + REGISTRATION_RESPONSE.getResponseData().getDepositResponseVO().getPlrDepositAmount();
                IS_DEPOSIT_ERROR = false;
            }

            String RETAILER_INFO = "(" + getString(R.string.sorry) + " " + PlayerData.getInstance().getUsername() + ", " + getString(R.string.problem_updating_balance) + ")";

            if (loginBean == null) {
                openDialog(USER_NAME, PASSWORD, DEPOSIT_AMOUNT, IS_DEPOSIT_ERROR, RETAILER_INFO);
            } else if (loginBean.getResponseCode() == 0) {
                LoginBean.ResponseData responseData = loginBean.getResponseData();
                if (responseData.getStatusCode() == 0) {
                    PlayerData.getInstance().setLoginData(master, loginBean);
                    refreshBalance();
                    openDialog(USER_NAME, PASSWORD, DEPOSIT_AMOUNT, IS_DEPOSIT_ERROR, "");
                } else {
                    openDialog(USER_NAME, PASSWORD, DEPOSIT_AMOUNT, IS_DEPOSIT_ERROR, RETAILER_INFO);
                }
            } else {
                openDialog(USER_NAME, PASSWORD, DEPOSIT_AMOUNT, IS_DEPOSIT_ERROR, RETAILER_INFO);
            }
        }
        else {
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.registration), getString(R.string.something_went_wrong), 1, getFragmentManager());
        }
    };

    private void openDialog(String userName, String password, String depositAmount, boolean isDepositError, String retailerInfo) {
        DialogCloseListener listener = () -> REGISTRATION_RESPONSE = null;
        OlaRegistrationSuccessDialog dialog = new OlaRegistrationSuccessDialog(master, getFragmentManager(), userName, password, depositAmount, isDepositError, retailerInfo, 1, listener);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void printReceipt(OlaRegistrationMyanmarResponseBean model) {
        OlaRegistrationPrintBean bean = new OlaRegistrationPrintBean();
        bean.setDate(model.getResponseData().getRegDate());

        bean.setUserNameTag(getString(R.string.user_name));
        bean.setUserNameValue(model.getResponseData().getUsername());

        bean.setPasswordTag(getString(R.string.password));
        bean.setPasswordValue(model.getResponseData().getPassword());

        if (model.getResponseData().getDepositResponseVO().getRespCode() != 0) {
            bean.setDepositAmountTag(getString(R.string.deposit_error_without_colon));
            bean.setDepositAmountValue(REGISTRATION_RESPONSE.getResponseData().getDepositResponseVO().getRespMsg());
            bean.setDepositError(true);
        } else {
            bean.setDepositAmountTag(getString(R.string.deposit_amt));
            bean.setDepositAmountValue(Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) + " " + model.getResponseData().getDepositResponseVO().getPlrDepositAmount());
            bean.setDepositError(false);
        }

        Log.i("log", "Print Data: " + bean);
        data = PrintUtil.printOlaRegistrationReceipt(bean, getActivity());
        AidlUtil.getInstance().sendRawData(data);
    }

    private void callRegistrationMyanmarApi() {
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
            OlaRegistrationMyanmarRequestBean model = new OlaRegistrationMyanmarRequestBean();
            model.setAppType(AppConstants.APP_TYPE);
            model.setDepositAmt(amount);
            model.setDeviceType(AppConstants.DEVICE_TYPE);
            model.setPaymentType(AppConstants.PAYMENT_TYPE);
            model.setPlrDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_OLA_MYANMAR")||
                    BuildConfig.FLAVOR.equalsIgnoreCase("pROD_OLA_MYANMAR")
                    ? "www.bet2winasia.com" : "www.igamew.com");
            model.setPlrMerchantCode(AppConstants.PLR_MERCHANT_CODE);
            model.setRetOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setRetUserId(Integer.parseInt(PlayerData.getInstance().getUserId()));
            model.setRetailDomainCode(urlBean.getRetailDomainCode());
            model.setRetailMerchantCode(urlBean.getRetailMerchantCode());
            model.setModelCode(Utils.getModelCode());
            model.setTerminalId(Utils.getDeviceSerialNumber());
            model.setToken(PlayerData.getInstance().getToken().split(" ")[1]);
            viewModel.callOlaMyanmarRegistration(urlBean, model);
        }
    }

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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        super.onDestroy();
    }
}
