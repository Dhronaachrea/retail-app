package com.skilrock.retailapp.portrait_draw_games.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.drawgames.BaseActivityViewModel;

public abstract class BaseActivity extends AppCompatActivity {

    public TextView tvUserBalance, tvUsername;
    public LinearLayout llBalance;
    private BaseActivityViewModel viewModel;
    private final String RMS = "rms";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel   = ViewModelProviders.of(this).get(BaseActivityViewModel.class);
        viewModel.getLiveDataBalance().observe(this, observerBalance);
    }

    public void refreshBalance() {
        if (tvUserBalance != null && tvUsername != null) {
            try {
                String isDisplayUserBalance = ConfigData.getInstance().getConfigData().getDISPLAYUSERBALANCE();
                String isHead = PlayerData.getInstance().getLoginData().getResponseData().getData().getIsHead();
                if (isDisplayUserBalance.equalsIgnoreCase("YES") && isHead.equalsIgnoreCase("NO")) {
                    tvUsername.setText(getString(R.string.cash_in_hand));
                    String balance;
                    balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ?  PlayerData.getInstance().getUserBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this)) : PlayerData.getInstance().getUserBalance();
                    tvUserBalance.setText(balance);

                    tvUserBalance.setTextColor(PlayerData.getInstance().getLoginData().getResponseData().getData().getRawUserBalance() < 0.0 ?
                            getResources().getColor(R.color.colorAppOrangeDark) : getResources().getColor(R.color.colorGreen));
                } else {
                    tvUsername.setText(getString(R.string.balance_without_colon));
                    String balance;
                    balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? PlayerData.getInstance().getOrgBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this)) : PlayerData.getInstance().getOrgBalance();
                    tvUserBalance.setText(balance);
                }
            } catch (Exception e) {
                e.printStackTrace();
                String balance;
                balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this)) + PlayerData.getInstance().getOrgBalance() : PlayerData.getInstance().getOrgBalance();

                tvUserBalance.setText(balance);
            }
        }

        String isHead = PlayerData.getInstance().getLoginData().getResponseData().getData().getIsHead();

        if (isHead.equalsIgnoreCase("NO")) {
            /*if(tvUserBalance!= null)
                tvUserBalance.setOnClickListener(v -> ((MainActivity) getActivity()).callBalRefreshForSubUser());*/
        }
    }



    public void callBalanceApi(View view) {
        ProgressBarDialog.getProgressDialog().showProgress(BaseActivity.this);
        viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
    }

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (loginBean == null)
            Toast.makeText(BaseActivity.this, getString(R.string.there_was_problem_connecting_to_server), Toast.LENGTH_SHORT).show();
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                Utils.hideKeyboard(BaseActivity.this);
                PlayerData.getInstance().setLoginData(BaseActivity.this, loginBean);
                PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));
                Toast.makeText(BaseActivity.this, getString(R.string.your_balance_has_been_updated), Toast.LENGTH_SHORT).show();
                refreshBalance();
            } else {
                if (Utils.checkForSessionExpiryActivity(BaseActivity.this, loginBean.getResponseData().getStatusCode(), BaseActivity.this))
                    return;
                String errorMsg = Utils.getResponseMessage(BaseActivity.this, RMS, responseData.getStatusCode());
                Toast.makeText(BaseActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            String errorMsg = Utils.getResponseMessage(BaseActivity.this, RMS, loginBean.getResponseCode());
            Toast.makeText(BaseActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };


}
