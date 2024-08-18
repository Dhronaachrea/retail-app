package com.skilrock.retailapp.ui.fragments.rms;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.CustomRemarksDialog;
import com.skilrock.retailapp.interfaces.RemarkListener;
import com.skilrock.retailapp.models.rms.CashManagementPaymentRequestBean;
import com.skilrock.retailapp.models.rms.CashManagementPaymentResponseBean;
import com.skilrock.retailapp.models.rms.CashManagementTxnTypeResponseBean;
import com.skilrock.retailapp.models.rms.CashManagementUserResponseBeanNew;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.TransactionRemarksResponseBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CommaDigitsInputFilter;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.CashManagementViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CashManagementFragment extends BaseFragment implements View.OnClickListener {

    private CashManagementViewModel viewModel;
    private EditText etAmount, etRemark;
    private TextInputLayout tilAmount, tilRemark;
    private Spinner spinnerUser, spinnerTrnsType;
    private Button button;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private HashMap<String, String> hashMapUser = new HashMap<>();
    private String amount;
    private String formatter;
    private String tnxType = "";
    private CustomRemarksDialog dialog;
    private HashMap<String, String> hashMapTransactionType = new HashMap<>();
    private final String RMS = "rms";
    private int decimalDegits;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cash_management, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            viewModel = ViewModelProviders.of(this).get(CashManagementViewModel.class);
            viewModel.getLiveDataUser().observe(this, observerUser);
            viewModel.getLiveDataTxnType().observe(this, observerTransactionType);
            viewModel.getLiveDataPayment().observe(this, observerPayment);
            viewModel.getiveRemarks().observe(this, observerRemarks);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
        callUsersApi();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etAmount            = view.findViewById(R.id.et_amount);
        etRemark            = view.findViewById(R.id.et_remark);
        tilRemark           = view.findViewById(R.id.til_remark);
        tilAmount           = view.findViewById(R.id.til_amount);
        spinnerTrnsType     = view.findViewById(R.id.spinnerTransactionType);
        spinnerUser         = view.findViewById(R.id.spinnerSelectUser);
        button              = view.findViewById(R.id.button_proceed);
        TextView tvTitle    = view.findViewById(R.id.tvTitle);
        tvUsername          = view.findViewById(R.id.tvUserName);
        tvUserBalance       = view.findViewById(R.id.tvUserBalance);
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

        if (ConfigData.getInstance().getConfigData() != null && ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE() != null)
            decimalDegits = Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE());

        formatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(master));

        etAmount.setFilters(new InputFilter[]{new CommaDigitsInputFilter(10, decimalDegits, formatter)});

        etAmount.setKeyListener(DigitsKeyListener.getInstance("1234567890" + Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(master))));

        etRemark.setOnClickListener(v -> {
            if (!Utils.isNetworkConnected(master)) {
                Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return;
            }
            String url = Utils.getRmsUrlDetails(menuBean, getContext(), "getTxnRemarks");
            ProgressBarDialog.getProgressDialog().showProgress(master);
            viewModel.getRemarksApi(url, hashMapTransactionType.get(spinnerTrnsType.getSelectedItem().toString()));
        });

        spinnerTrnsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                etRemark.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.contains(formatter)) {
                    String[] data = str.split(formatter);
                    if (data.length > 1) {
                        if (data[1].length() > decimalDegits) {
                            String txt = data[0] + formatter + data[1].substring(0, decimalDegits);
                            etAmount.setText(txt);
                            etAmount.setSelection(txt.length());
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_proceed:
                if (validate())
                    callPaymentApi();
                break;
        }
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (getText(etAmount).equalsIgnoreCase("")) {
            etAmount.setError(getString(R.string.this_field_cannot_be_empty));
            etAmount.requestFocus();
            tilAmount.startAnimation(Utils.shakeError());
            return false;
        }
        if (getText(etRemark).equalsIgnoreCase("")) {
            /*etRemark.setError(getString(R.string.this_field_cannot_be_empty));
            etRemark.requestFocus();*/
            Toast.makeText(master, getString(R.string.please_enter_remark), Toast.LENGTH_SHORT).show();
            tilRemark.startAnimation(Utils.shakeError());
            return false;
        }
        if (getText(etAmount).equalsIgnoreCase(".") || getText(etAmount).equalsIgnoreCase(",")) {
            etAmount.setError(getString(R.string.invalid_input));
            etAmount.requestFocus();
            tilAmount.startAnimation(Utils.shakeError());
            return false;
        }

        if (getFormattedAmount(etAmount.getText().toString()) == null) {
            return false;
        }

        if (getFormattedAmount(etAmount.getText().toString()) == 0) {
            etAmount.setError(getString(R.string.amt_must_be_greater_than_zero));
            etAmount.requestFocus();
            tilAmount.startAnimation(Utils.shakeError());
            return false;
        }

        return true;
    }

    Observer<CashManagementUserResponseBeanNew> observerUser = new Observer<CashManagementUserResponseBeanNew>() {
        @Override
        public void onChanged(@Nullable CashManagementUserResponseBeanNew response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.cash_management), getString(R.string.something_went_wrong), 1, getFragmentManager());
            else if (response.getResponseCode() != 0) {
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.cash_management), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;

                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.cash_management), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() == 0) {
                hashMapUser.clear();
                for (CashManagementUserResponseBeanNew.UserSearchDatum data : response.getResponseData().getData().getUserSearchData())
                    hashMapUser.put(data.getUserName() + " (" + data.getName() + ")", String.valueOf(data.getUserId()));
                populateUserSpinner();
                callTransactionTypeApi();
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), getString(R.string.something_went_wrong));
        }
    };

    Observer<CashManagementTxnTypeResponseBean> observerTransactionType = new Observer<CashManagementTxnTypeResponseBean>() {
        @Override
        public void onChanged(@Nullable CashManagementTxnTypeResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.cash_management), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.cash_management), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() == 0) {
                hashMapTransactionType.clear();
                for (CashManagementTxnTypeResponseBean.ResponseData.Data data : response.getResponseData().getData())
                    hashMapTransactionType.put(data.getTxnTypeName(), data.getTxnTypeCode());
                populateTransactionTypeSpinner();
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), getString(R.string.something_went_wrong));
        }
    };

    Observer<CashManagementPaymentResponseBean> observerPayment = new Observer<CashManagementPaymentResponseBean>() {
        @Override
        public void onChanged(@Nullable CashManagementPaymentResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), errorMsg);
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0) {
                String successMsg = getString(R.string.transaction_success);
                Utils.showCustomSuccessDialog(master, getFragmentManager(), "", successMsg, 1);
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), getString(R.string.something_went_wrong));
        }
    };

    Observer<TransactionRemarksResponseBean> observerRemarks = new Observer<TransactionRemarksResponseBean>() {
        @Override
        public void onChanged(@Nullable TransactionRemarksResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), errorMsg);
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0) {
                ProgressBarDialog.getProgressDialog().dismiss();

                dialog = new CustomRemarksDialog(master, otpListener, getString(R.string.add_remark), getString(R.string.select_remark), response.getResponseData().getData(), etRemark.getText().toString(), Boolean.parseBoolean(etRemark.getTag().toString()), etRemark.getText().toString());
                //dialog.setCancelable(false);
                dialog.show();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.cash_management), getString(R.string.something_went_wrong));
        }
    };

    private void populateUserSpinner() {
        ArrayList<String> listUsers = new ArrayList<>(hashMapUser.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, listUsers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);

        if (listUsers.size() == 1) {
            spinnerUser.setBackgroundResource(0);
            spinnerUser.setClickable(false);
            spinnerUser.setEnabled(false);
        }
    }

    private void populateTransactionTypeSpinner() {
        ArrayList<String> listTxn = new ArrayList<>(hashMapTransactionType.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, listTxn);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrnsType.setAdapter(adapter);

        if (listTxn.size() == 1) {
            spinnerTrnsType.setBackgroundResource(0);
            spinnerTrnsType.setClickable(false);
            spinnerTrnsType.setEnabled(false);
        }
    }

    private void callUsersApi() {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "doUserSearch");
        if (url != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master);
            viewModel.userSearchApi(url);
        } else
            Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
    }

    private void callTransactionTypeApi() {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "getTxnType");
        if (url != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master);
            viewModel.txnTypeApi(url);
        } else
            Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
    }

    private void callPaymentApi() {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "doIntraOrgPayment");
        if (url != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master);
            CashManagementPaymentRequestBean model = new CashManagementPaymentRequestBean();
            model.setAmount(Utils.removeExponential(etAmount.getText().toString()));
            model.setRemarks(getText(etRemark));
            model.setUserId(Integer.parseInt(Objects.requireNonNull(hashMapUser.get(spinnerUser.getSelectedItem().toString()))));
            model.setTxnTypeCode(hashMapTransactionType.get(spinnerTrnsType.getSelectedItem().toString()));
            viewModel.paymentApi(url, model);
        } else
            Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
    }

    private Double getFormattedAmount(String strAmount) {
        try {
            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.SISAL)) {
                return Double.parseDouble(strAmount.replaceAll(",", "."));
            } else {
                return Double.parseDouble(strAmount);
            }
        } catch (Exception e) {
            etAmount.setError(getString(R.string.invalid_input));
            etAmount.requestFocus();
            tilAmount.startAnimation(Utils.shakeError());
            return null;
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

    RemarkListener otpListener = new RemarkListener() {
        @Override
        public void onRemarkListener(String remark, boolean isFromOther) {
            etRemark.setText(remark);
            etRemark.setTag(isFromOther);
        }
    };
}