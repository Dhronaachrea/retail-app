package com.skilrock.retailapp.ui.fragments.ola;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.ola.OlaPlayerForgotPasswordRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerForgotPasswordResponseBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchResponseBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.ola.OlaPlayerForgotPasswordViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class OlaPlayerForgotPasswordFragment extends BaseFragment implements View.OnClickListener {

    private OlaPlayerForgotPasswordViewModel viewModel;
    private EditText etMobile;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private TextInputLayout tilMobile;
    private Button button;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ola_forgot_password, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(OlaPlayerForgotPasswordViewModel.class);
        viewModel.getDepositLiveData().observe(this, observer);
        viewModel.getPlsSearchLiveData().observe(this, observerPlrSearch);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        etMobile                = view.findViewById(R.id.et_mobile_number);
        tilMobile               = view.findViewById(R.id.til_mobile);
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

        etMobile.setOnEditorActionListener(this::onEditorAction);
    }

    @Override
    public void onClick(View v) {
        if (validate())
            callPlayerSearchApi();
    }

    private void callPlayerSearchApi() {
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "searchPlayer");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master);

            String domain = PlayerData.getInstance().getDomain();
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

    private void callResetPasswordApi() {
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "forgetPassword");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master);

            String domain = PlayerData.getInstance().getDomain();
            OlaPlayerForgotPasswordRequestBean model = new OlaPlayerForgotPasswordRequestBean();
            model.setPlayerOperationType("PASSWORD_CHANGE");
            model.setPlayerUserName(getText(etMobile));
            model.setPlrDomainCode(domain);

            model.setRetailDomainCode(urlBean.getRetailDomainCode());
            model.setRetOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setRetUserId(Integer.parseInt(PlayerData.getInstance().getUserId()));

            model.setToken(PlayerData.getInstance().getToken().split(" ")[1]);

            viewModel.callOlaPlayerForgotPasswordApi(urlBean, model);
        }
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (TextUtils.isEmpty(getText(etMobile))) {
            etMobile.setError(getString(R.string.this_field_cannot_be_empty));
            etMobile.requestFocus();
            tilMobile.startAnimation(Utils.shakeError());
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

    Observer<OlaPlayerForgotPasswordResponseBean> observer = new Observer<OlaPlayerForgotPasswordResponseBean>() {
        @Override
        public void onChanged(@Nullable OlaPlayerForgotPasswordResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                //String msg = response.getResponseData().getResponseMessage();
                String msg = "New PIN has been sent to Player's Registered Mobile Number.";
                if (response.getResponseData().getErrorCode() == 0)
                    Utils.showCustomSuccessDialog(master, getFragmentManager(), "", msg, 1);
                else
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), msg);
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                    return;

                String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), errorMsg);
            }
        }
    };

    Observer<OlaPlayerSearchResponseBean> observerPlrSearch = new Observer<OlaPlayerSearchResponseBean>() {

        @Override
        public void onChanged(@Nullable OlaPlayerSearchResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                if (response.getResponseData() != null && response.getResponseData().getPlayers().size() < 1) {
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), getString(R.string.no_player_with_this_number));
                }
                else {
                    ConfirmationListener listener = OlaPlayerForgotPasswordFragment.this::callResetPasswordApi;
                    ArrayList<OlaPlayerSearchResponseBean.ResponseData.Player> list = Objects.requireNonNull(response.getResponseData()).getPlayers();
                    String playerName = list.get(0).getFirstName() + " " + list.get(0).getLastName();
                    String displayMsg = "You are about to reset password for <font color=#606060>" + playerName
                            + " (" + etMobile.getText().toString().trim() + ")</font>.";
                    Utils.showConfirmationDialog(getContext(), true, displayMsg, listener);
                }
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode())) return;

                if (response.getResponseCode() == 1070)
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), getString(R.string.no_player_with_this_number));
                else {
                    String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), errorMsg);
                }
            }
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
