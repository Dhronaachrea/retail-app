package com.skilrock.retailapp.ui.fragments.rms;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.AccountSettlementAdapter;
import com.skilrock.retailapp.interfaces.UserSelectListener;
import com.skilrock.retailapp.models.SettleUserAccountRequestBean;
import com.skilrock.retailapp.models.SimpleRmsResponseBean;
import com.skilrock.retailapp.models.UserAccountResponseBean;
import com.skilrock.retailapp.models.ola.OrgUserResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CommaDigitsInputFilter;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.AccountSettlementViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccountSettlementFragment extends BaseFragment implements View.OnClickListener, UserSelectListener {

    private AccountSettlementViewModel viewModel;
    private RecyclerView rvSettlementList;
    private String mUserId, mUserName, mName;
    private TextView tvClosingBalance, tvBalance, tvUserName, tvOpeningBal, tvName, tvFromDate, tvToDate;
    private EditText etCash;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private TextInputLayout tilCash;
    private String formatter;
    private int decimalDegits = 2;
    private AccountSettlementAdapter adapter;
    private boolean isZeroValue = false;
    private Button btSettleNow, btSettleLater;
    private CardView cardViewBalance;
    private boolean isError = false;
    double cashAmount = 0.0;
    private boolean isAllowedZeroSettlement = false;
    private boolean isSettledNow = true;
    private double mBalance = 0;
    private List<OrgUserResponseBean.UserData> mUserDataList = new ArrayList<>();
    private final String RMS = "rms";
    private boolean isFirstTime = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_settlement, container, false);

        initializeWidgets(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(this).get(AccountSettlementViewModel.class);
            /*  viewModel.getLiveDataOrgUser().observe(this, observer);*/
            viewModel.getUserAccountLiveData().observe(this, observerSettlementList);
            viewModel.getLiveSettleUserAccount().observe(this, observerSettleUserAccount);
            viewModel.getLiveSettleUserAccount().observe(this, observerSettleLater);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        rvSettlementList = view.findViewById(R.id.rv_report);
        tvClosingBalance = view.findViewById(R.id.tv_closing_balance);
        tvBalance = view.findViewById(R.id.tv_balance);
        etCash = view.findViewById(R.id.et_cash);
        tvUserName = view.findViewById(R.id.tv_user_name);
        tvOpeningBal = view.findViewById(R.id.tv_opening_balance);
        tvFromDate = view.findViewById(R.id.tv_from_date);
        tvToDate = view.findViewById(R.id.tv_to_date);
        tvName = view.findViewById(R.id.tv_name);
        tilCash = view.findViewById(R.id.til_cash);
        cardViewBalance = view.findViewById(R.id.card_view_left);
        btSettleNow = view.findViewById(R.id.bt_settle_now);
        btSettleLater = view.findViewById(R.id.bt_settle_later);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        btSettleNow.setOnClickListener(this);
        btSettleLater.setOnClickListener(this);

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
                mUserId = bundle.getString("user_id");
                mUserName = bundle.getString("user_name");
                mName = bundle.getString("name");
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

   /*     String url = Utils.getRmsUrlDetails(menuBean, getContext(), "doUserSearch");
        Integer domain = (int) PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId();
*/

        isFirstTime = true;
       /* viewModel.callGetOrgUserApi(PlayerData.getInstance().getToken(), url,
                PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId(), domain);*/

        rvSettlementList.setVisibility(View.GONE);
        cardViewBalance.setVisibility(View.GONE);

        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "getUserAcount");

        ProgressBarDialog.getProgressDialog().showProgress(master);
        viewModel.callGetUserAccountApi(PlayerData.getInstance().getToken(), url, mUserId, "en", AppConstants.appType);

        if (ConfigData.getInstance().getConfigData() != null && ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE() != null)
            decimalDegits = Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE());

        formatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(master));

        etCash.setFilters(new InputFilter[]{new CommaDigitsInputFilter(10, decimalDegits, formatter)});

        //etCash.setKeyListener(DigitsKeyListener.getInstance("1234567890" + Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(master))));
        etCash.setKeyListener(DigitsKeyListener.getInstance("1234567890, " + Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(master))));

        etCash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               /* if (etCash.getText().toString().indexOf(formatter) != etCash.getText().toString().length() -1) {
                    if (etCash.getText().toString().substring(etCash.getText().toString().length() - 1).equalsIgnoreCase(formatter)) {
                        etCash.setText(etCash.getText().toString().substring(0, etCash.getText().toString().length() - 1));
                        int pos = etCash.getText().length();
                        etCash.setSelection(pos);
                    }
                }*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.contains(formatter)) {
                    String[] data = str.split(formatter);
                    if (data.length > 1) {
                        if (data[1].length() > decimalDegits) {
                            String txt = data[0] + formatter + data[1].substring(0, decimalDegits);
                            etCash.setText(txt);
                            etCash.setSelection(txt.length());
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                tilCash.setError(null);
                isError = false;
                isZeroValue = false;
                tilCash.setErrorEnabled(false);
                calculateCash(s.toString());
            }
        });
       /* view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                settleLaterAccount(AppConstants.SETTLE_LATER);
                return false;
            }
            return false;
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_settle_now:
                isSettledNow = true;
                settleAccount(AppConstants.SETTLE_NOW);
                break;
            case R.id.bt_settle_later:
                isSettledNow = false;
                settleAccount(AppConstants.SETTLE_LATER);
                break;
        }
    }

    private void settleAccount(String settleAction) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        if (!isSettledNow) {
            if (etCash.getText().toString().isEmpty()) {
                isError = false;
                isZeroValue = true;
            } else {
                if (getFormattedCashAmount(etCash.getText().toString()) != null && getFormattedCashAmount(etCash.getText().toString()) == 0) {
                    isError = false;
                    isZeroValue = true;
                }
            }
        }

        if (isError && isSettledNow) {
            Log.d("TAg", "111111111111111111111111111111111");
            Toast.makeText(master, getString(R.string.enter_valid_amount), Toast.LENGTH_SHORT).show();
            return;
        } else if (!isSettledNow && isError && !isZeroValue) {
            Log.d("TAg", "222222222222222222222222222222222");
            Toast.makeText(master, getString(R.string.enter_valid_amount), Toast.LENGTH_SHORT).show();
            return;
        }

        SettleUserAccountRequestBean settleUserAccountRequestBean = new SettleUserAccountRequestBean();
        settleUserAccountRequestBean.setSettleAction(settleAction);

        if (!isSettledNow && isZeroValue)
            settleUserAccountRequestBean.setSettleAmount("0.0");
        else {
            Log.d("TAg", "cashAmount: " + cashAmount);
            settleUserAccountRequestBean.setSettleAmount(Utils.removeExponential(String.valueOf(Math.abs(cashAmount))));
        }

        settleUserAccountRequestBean.setUserId(Integer.valueOf(mUserId));

        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "settleUserAccount");

        ProgressBarDialog.getProgressDialog().showProgress(master);

        viewModel.callSettleUserAccountApi(PlayerData.getInstance().getToken(), url, settleUserAccountRequestBean);
    }

    public void settleLaterAccount(String settleAction) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            SettleUserAccountRequestBean settleUserAccountRequestBean = new SettleUserAccountRequestBean();
            settleUserAccountRequestBean.setSettleAction(settleAction);
            settleUserAccountRequestBean.setSettleAmount("0.0");
            settleUserAccountRequestBean.setUserId(Integer.valueOf(mUserId));
            String url = Utils.getRmsUrlDetails(menuBean, getContext(), "settleUserAccount");
            viewModel.callSettleLaterApi(PlayerData.getInstance().getToken(), url, settleUserAccountRequestBean);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    Observer<OrgUserResponseBean> observer = new Observer<OrgUserResponseBean>() {
        @Override
        public void onChanged(@Nullable OrgUserResponseBean response) {
            if (progressDialog != null)
                progressDialog.dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.something_went_wrong), 1, getFragmentManager());

            else if (response.getResponseCode() != 0) {
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                      return;

                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() == 0) {
                mUserDataList = response.getResponseData().getData();
                setUserListAdapter(response.getResponseData().getData());
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.account_settlement), getString(R.string.something_went_wrong));
        }
    };*/

    Observer<UserAccountResponseBean> observerSettlementList = new Observer<UserAccountResponseBean>() {
        @Override
        public void onChanged(@Nullable UserAccountResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.something_went_wrong), 1, getFragmentManager());
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (response.getResponseData().getStatusCode() == 706) {
                    Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.no_data_found), 1, getFragmentManager());
                } else if (response.getResponseData().getStatusCode() == 3053) {
                    String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                    Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), errorMsg, 1, getFragmentManager());
                } else {
                    if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                        return;
                    String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                    Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), errorMsg, 1, getFragmentManager());
                }
            } else if (response.getResponseData().getStatusCode() == 0) {
                if (response.getResponseData().getData() == null)
                    Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.no_data_found), 1, getFragmentManager());
                else if (response.getResponseData().getData().getTransactions().size() < 1)
                    Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.no_data_found), 1, getFragmentManager());
                else {

                    if (response.getResponseData().getData().getTransactions().isEmpty()) {
                        Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.no_data_found), 1, getFragmentManager());
                        return;
                    }

                    tvBalance.setText(response.getResponseData().getData().getSettelmentInfo().getTotalBalance());
                    mBalance = Utils.getFormattedAmount(Double.parseDouble(response.getResponseData().getData().getSettelmentInfo().getUnformattedTotalBalance()));
                    tvFromDate.setText(": " + Utils.formatDate(response.getResponseData().getData().getSettelmentInfo().getSettlementFromDate().split(" ")[0]) + " "
                            + Utils.formatTime(response.getResponseData().getData().getSettelmentInfo().getSettlementFromDate().split(" ")[1]));

                    tvToDate.setText(Utils.formatDate(response.getResponseData().getData().getSettelmentInfo().getSettlementToDate().split(" ")[0]) + " "
                            + Utils.formatTime(response.getResponseData().getData().getSettelmentInfo().getSettlementToDate().split(" ")[1]));

                    tvUserName.setText(" (" + mUserName + ")");
                    tvName.setText(": " + mName);
                    tvOpeningBal.setText(": " + response.getResponseData().getData().getSettelmentInfo().getOpeningBalance());

                    cardViewBalance.setVisibility(View.VISIBLE);
                    rvSettlementList.setVisibility(View.VISIBLE);

                    setCash(mBalance);
                    cashAmount = mBalance;
                    setAdapter(response);
                }
            } else
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.something_went_wrong), 1, getFragmentManager());
            isFirstTime = false;
        }
    };

    Observer<SimpleRmsResponseBean> observerSettleUserAccount = new Observer<SimpleRmsResponseBean>() {
        @Override
        public void onChanged(@Nullable SimpleRmsResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.something_went_wrong), 1, getFragmentManager());
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.account_settlement), errorMsg);
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (response.getResponseData().getStatusCode() == 3053) {
                    String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                    Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), errorMsg, 1, getFragmentManager());
                } else {
                    if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                        return;
                    //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                    String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                    Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), errorMsg, 1, getFragmentManager());
                }
            } else if (response.getResponseData().getStatusCode() == 0) {
                String successMsg;

                if (isSettledNow)
                    successMsg = getString(R.string.account_settled_success);
                else
                    //successMsg = getString(R.string.settle_later_success);
                    successMsg = getString(R.string.success);

                Utils.showCustomSuccessDialog(master, getFragmentManager(), "", successMsg, 1);
            } else
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.something_went_wrong), 1, getFragmentManager());
        }
    };

    Observer<SimpleRmsResponseBean> observerSettleLater = new Observer<SimpleRmsResponseBean>() {
        @Override
        public void onChanged(@Nullable SimpleRmsResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) {
                // Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.something_went_wrong), 1, getFragmentManager());
            } else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                //  String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                //  Utils.showCustomErrorDialog(getContext(), getString(R.string.account_settlement), errorMsg);
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (response.getResponseData().getStatusCode() == 3053) {
                    String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                    //   Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), errorMsg, 1, getFragmentManager());
                } else {
                    if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                        return;
                    //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                    String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                    // Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), errorMsg, 1, getFragmentManager());
                }
            } else if (response.getResponseData().getStatusCode() == 0) {
                String successMsg;

                if (isSettledNow)
                    successMsg = getString(R.string.account_settled_success);
                else
                    //successMsg = getString(R.string.settle_later_success);
                    successMsg = getString(R.string.success);
                // Utils.showCustomSuccessDialog(master, getFragmentManager(), "", successMsg, 1);
            } else {
                // Utils.showCustomErrorDialogPop(getContext(), getString(R.string.account_settlement), getString(R.string.something_went_wrong), 1, getFragmentManager());
            }
        }
    };

    private void setAdapter(UserAccountResponseBean response) {
        adapter = new AccountSettlementAdapter(response.getResponseData().getData().getTransactions(), getContext());

        rvSettlementList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSettlementList.setItemAnimator(new DefaultItemAnimator());
        rvSettlementList.setAdapter(adapter);

        /*if (response.getResponseData().getData().getTransactions().size() == 1) {
            rvSettlementList.setBackgroundResource(0);
            rvSettlementList.setClickable(false);
            rvSettlementList.setEnabled(false);
        }*/
    }

 /*   private void setUserListAdapter(List<OrgUserResponseBean.UserData> list) {
        List<String> listSpinner = new ArrayList<>();

        for (OrgUserResponseBean.UserData data : list) {
            listSpinner.add(data.getUserName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsers.setAdapter(adapter);

        if (listSpinner.size() == 1) {
            spinnerUsers.setBackgroundResource(0);
            spinnerUsers.setClickable(false);
            spinnerUsers.setEnabled(false);
        }
    }*/

    @Override
    public void onUserClicked(int position, String settlementId) {
    }

    private void setCash(double balance) {
        Log.d("TAg", " --> balance: " + balance);
        if (balance < 0) {
            etCash.setText(changeFormatter(getBalanceToView(Math.abs(balance)), formatter));
            etCash.setHint(getString(R.string.cash_in_required) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) + changeFormatter(getBalanceToView(Math.abs(balance)), formatter));
            //etCash.setHint(getString(R.string.cash_in_required) + " " + getBalanceToView(Math.abs(balance)));
            tilCash.setHint(getString(R.string.cash_in_required) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) + changeFormatter(getBalanceToView(Math.abs(balance)), formatter));
            //tilCash.setHint(getString(R.string.cash_in_required) + " " + getBalanceToView(Math.abs(balance)));
        } else {
            etCash.setText(changeFormatter(getBalanceToView(Math.abs(balance)), formatter));
            etCash.setHint(getString(R.string.cash_out_required_of) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) + changeFormatter(getBalanceToView(Math.abs(balance)), formatter));
            //etCash.setHint(getString(R.string.cash_out_required_of) + " " + getBalanceToView(Math.abs(balance)));
            tilCash.setHint(getString(R.string.cash_out_required_of) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) + changeFormatter(getBalanceToView(Math.abs(balance)), formatter));
            //tilCash.setHint(getString(R.string.cash_out_required_of) + " " + getBalanceToView(Math.abs(balance)));
        }
    }

    private String getBalanceToView(double balance) {
        String text = "";
        if (formatter.equalsIgnoreCase(",")) {
            if (decimalDegits == 0) {
                text = String.valueOf(balance).split("\\.")[0];
                return removeExponentital(text);
            } else {
                text = String.valueOf(balance).replace(".", ",");
                return removeExponentital(text);
            }
        } else {
            if (decimalDegits == 0) {
                text = String.valueOf(balance).split("\\.")[0];
                return removeExponentital(text);
            } else {
                text = String.valueOf(balance);
                return removeExponentital(text);
            }
        }
    }

    static String removeExponentital(String amount) {
        if (amount.contains(",")) {
            String formattedText = String.format(Locale.US, "%.2f",  Double.parseDouble(amount.replaceAll(",", ".")));
            formattedText = formattedText.replace(".", ",");
            return formattedText;
        } else {
            return String.format(Locale.US, "%.2f", Double.parseDouble(amount));
        }
    }

    private void calculateCash(String cash) {
        Log.d("TAg", "---------------- calculateCash");

        try {
            double cashValue = 0.0;

            if (cash.isEmpty() || cash.equalsIgnoreCase(".") || cash.equalsIgnoreCase(",")) {
                Log.d("TAg", "------------222---- calculateCash");

                isError = true;

                if (cash.isEmpty())
                    isZeroValue = true;

                String bal = Utils.getNegativeValueFormatted(getBalanceToView(Utils.getFormattedAmount(mBalance + cashValue)), SharedPrefUtil.getLanguage(master), formatter);
                bal = Utils.getAmountValueFormatted(bal, SharedPrefUtil.getLanguage(master));

                tvClosingBalance.setText(changeFormatter(bal, formatter));
                return;
            }

            Log.d("TAg", "------------cash---- " + cash);
            if (getFormattedCashAmount(cash) == null) {
                Log.d("TAg", "------------333---- calculateCash");
                isError = true;
                tilCash.setErrorEnabled(true);
                tilCash.setError(getString(R.string.invalid_amount));
                tvClosingBalance.setText("N/A");
                tilCash.requestFocus();
                return;
            }

            cashValue = getFormattedCashAmount(cash);

            cashValue = Utils.getFormattedAmount(cashValue);

            if (cashValue <= 0) {
                Log.d("TAg", "------------444---- calculateCash");
                isError = true;
                isZeroValue = true;
                String bal = Utils.getNegativeValueFormatted(getBalanceToView(Utils.getFormattedAmount(mBalance + cashValue)), SharedPrefUtil.getLanguage(master), formatter);
                bal = Utils.getAmountValueFormatted(bal, SharedPrefUtil.getLanguage(master));

                tvClosingBalance.setText(changeFormatter(bal, formatter));
                return;
            }

            if (cashValue > Math.abs(mBalance)) {
                cashAmount = cashValue;
                Log.d("TAg", "------------555---- calculateCash");
                isError = true;
                tilCash.setErrorEnabled(true);
                tilCash.setError(getString(R.string.invalid_amount));
                tilCash.requestFocus();
              /*  if (mBalance < 0) {
                    tvClosingBalance.setText(Utils.getNegativeValueFormatted(getBalanceToView(Utils.getFormattedAmount(mBalance + cashValue)), AppConstants.LANGUAGE_ENGLISH, formatter));
                } else {
                    tvClosingBalance.setText(Utils.getNegativeValueFormatted(getBalanceToView(Utils.getFormattedAmount(mBalance - cashValue)), AppConstants.LANGUAGE_ENGLISH, formatter));
                }*/
                tvClosingBalance.setText("N/A");
                return;
            }

            cashAmount = cashValue;
            if (mBalance < 0) {
                String bal = Utils.getNegativeValueFormatted(getBalanceToView(Utils.getFormattedAmount(mBalance + cashValue)), SharedPrefUtil.getLanguage(master), formatter);
                bal = Utils.getAmountValueFormatted(bal, SharedPrefUtil.getLanguage(master));
                Log.d("TAg", "bal if: " + bal);
                Log.d("TAg", "formatter if: " + formatter);
                tvClosingBalance.setText(changeFormatter(bal, formatter));
            } else {
                String bal = Utils.getNegativeValueFormatted(getBalanceToView(Utils.getFormattedAmount(mBalance - cashValue)), SharedPrefUtil.getLanguage(master), formatter);
                bal = Utils.getAmountValueFormatted(bal, SharedPrefUtil.getLanguage(master));
                Log.d("TAg", "bal else: " + bal);
                Log.d("TAg", "formatter else: " + formatter);

                tvClosingBalance.setText(changeFormatter(bal, formatter));
            }

        } catch (Exception e) {
            isError = true;
            tvClosingBalance.setText("N/A");
            e.printStackTrace();
        }
    }

    private Double getFormattedCashAmount(String strAmount) {
        try {
            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.SISAL)) {
                return Double.parseDouble(strAmount.replaceAll(",", "."));
            } else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
                return Double.parseDouble(strAmount.replaceAll(",", ".").replaceAll(" ", ""));
            } else {
                return Double.parseDouble(strAmount);
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Double getNewFormatCashAmount(String strAmount) {
        try {
            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
                return Double.parseDouble(strAmount.replaceAll(",", "."));
            } else {
                return Double.parseDouble(strAmount);
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String getCurrencySaperator() {
        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.SISAL)) {
            return ",";
        } else {
            return ".";
        }
    }

    private String getFormattedCash(String amount){
        return String.format(Locale.US, "%.2f", Double.parseDouble(amount));
    }

    private String getFormattedCash(Double amount){
        return String.format(Locale.US, "%.2f",amount);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        settleLaterAccount(AppConstants.SETTLE_LATER);
    }

    private String changeFormatter(String amount, String formatter) {
        String balance;
        if (formatter.contains(",")) {
            balance = amount.replace(".", formatter);
            if (decimalDegits == 0) {
                balance = String.valueOf(balance).split("\\" + formatter)[0];
            }
        } else {
            balance = amount.replace(",", formatter);
            if (decimalDegits == 0) {
                balance = String.valueOf(balance).split("\\" + formatter)[0];
            }
        }

        return balance;
    }
}
