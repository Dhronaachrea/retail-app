package com.skilrock.retailapp.ui.fragments;

import android.content.Context;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.IOnBackPressed;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

public abstract class BaseFragment extends Fragment implements IOnBackPressed {

    public static MainActivity master;
    public TextView tvUserBalance, tvUsername;
    public boolean addPack = true;
    private final String RMS = "rms";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            master = (MainActivity) context;
        }
    }

    public boolean isAddPack() {
        return addPack;
    }

    public void setAddPack(boolean addPack) {
        this.addPack = addPack;
    }

    public void allowBackAction(boolean isBackAllowed) {
        master.isBackAllowed = isBackAllowed;
    }

    public void allowClick(boolean isAllowed) {
        master.isClickAllowed = isAllowed;
    }

    public boolean isClickAllowed() {
        return master.isClickAllowed;
    }

    public void refreshBalance() {
        if (tvUserBalance != null && tvUsername != null) {
            try {
                if (ConfigData.getInstance().getConfigData() != null) {
                    String isDisplayUserBalance = ConfigData.getInstance().getConfigData().getDISPLAYUSERBALANCE();
                    String isHead = PlayerData.getInstance().getLoginData().getResponseData().getData().getIsHead();
                    if (isDisplayUserBalance.equalsIgnoreCase("YES") && isHead.equalsIgnoreCase("NO")) {
                        tvUsername.setText(getString(R.string.cash_in_hand));
                        String balance;
                        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS)) {
                            balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) + " " + PlayerData.getInstance().getUserBalance() : PlayerData.getInstance().getUserBalance();
                        }
                        else {
                            balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? PlayerData.getInstance().getUserBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) : PlayerData.getInstance().getUserBalance();
                        }
                        tvUserBalance.setText(balance);

                        tvUserBalance.setTextColor(PlayerData.getInstance().getLoginData().getResponseData().getData().getRawUserBalance() < 0.0 ? master.getResources().getColor(R.color.colorAppOrangeDark) : master.getResources().getColor(R.color.colorGreen));
                    } else {
                        tvUsername.setText(getString(R.string.balance_cation));
                        String balance;
                        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS)) {
                            balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) + " " + PlayerData.getInstance().getOrgBalance() : PlayerData.getInstance().getOrgBalance();
                        }
                        else {
                            balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? PlayerData.getInstance().getOrgBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) : PlayerData.getInstance().getOrgBalance();
                        }
                        tvUserBalance.setText(balance);
                    }
                }
                else {
                    tvUserBalance.setText(PlayerData.getInstance().getOrgBalance());
                }
            } catch (Exception e) {
                e.printStackTrace();
                tvUserBalance.setText(PlayerData.getInstance().getOrgBalance());
            }
        }

        String isHead = PlayerData.getInstance().getLoginData().getResponseData().getData().getIsHead();

        if (isHead.equalsIgnoreCase("NO")) {
            if(tvUserBalance!= null)
                tvUserBalance.setOnClickListener(v -> ((MainActivity) getActivity()).callBalRefreshForSubUser());
        }
    }

    public void onBackPressed() {

    }

}
