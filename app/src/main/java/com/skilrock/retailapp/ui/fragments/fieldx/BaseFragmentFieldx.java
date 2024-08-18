package com.skilrock.retailapp.ui.fragments.fieldx;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.FieldXPaymentDialog;
import com.skilrock.retailapp.interfaces.IOnBackPressed;
import com.skilrock.retailapp.models.FieldX.AllTaskItem;
import com.skilrock.retailapp.models.FieldX.FieldXChallanBean;
import com.skilrock.retailapp.models.FieldX.FieldXChallanItems;
import com.skilrock.retailapp.models.FieldX.FieldxGetPayAmountBean;
import com.skilrock.retailapp.models.FieldX.FieldxRetailerResponseBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.FieldXViewModel.FieldXAllTaskViewModel;
import com.skilrock.retailapp.viewmodels.rms.HomeViewModel;

import java.util.ArrayList;

public class BaseFragmentFieldx extends Fragment implements IOnBackPressed {
    public static ArrayList<FieldXChallanItems> challans = new ArrayList<>();
    public static ArrayList<AllTaskItem> allTask = new ArrayList<>();
    public static HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBeanFieldx;
    public static MainActivity master;
    private final String FIELDX = "fieldx";
    private final String RMS = "rms";
    public TextView tvUserBalance, tvUsername;
    public boolean addPack = true;
    public HomeViewModel homeViewModel;
    public FieldXAllTaskViewModel fieldXAllTaskViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            master = (MainActivity) context;
        }
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getLiveFieldxRetailer().observe(this, observerRetailer);
        homeViewModel.getLiveFieldxChallan().observe(this, observerChallan);
        fieldXAllTaskViewModel = ViewModelProviders.of(this).get(FieldXAllTaskViewModel.class);
        fieldXAllTaskViewModel.getFieldxGetPayAmount().observe(this, observerGetPayAmount);
    }

    Observer<FieldxGetPayAmountBean> observerGetPayAmount = new Observer<FieldxGetPayAmountBean>() {
        @Override
        public void onChanged(@Nullable FieldxGetPayAmountBean fieldxGetPayAmountBean) {
            ProgressBarDialog.getProgressDialog().dismiss();
            if (fieldxGetPayAmountBean == null) {
                Utils.showCustomErrorDialog(getContext(), getString(R.string.payment), getString(R.string.something_went_wrong));
            }
            else {
                if (fieldxGetPayAmountBean.getResponseCode() == 0) {
                    FieldxGetPayAmountBean.ResponseData respDta = fieldxGetPayAmountBean.getResponseData();
                    if (respDta.getStatusCode() == 0) {
                        FieldxGetPayAmountBean.ResponseData.Data data = respDta.getData();
                        FieldXPaymentDialog dialog = new FieldXPaymentDialog(getActivity(), data.getRawMinimumDueAmount(), data.getMinimumDueAmount(), data.getOrgId(), data.getOrgName(), (FieldXHomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag("FieldXHomeFragment"), FieldXHomeFragment.getMenuBeanFieldx());
                        dialog.show();
                    } else {
                        if (Utils.checkForSessionExpiry(getActivity(), respDta.getStatusCode()))
                            return;

                        String errorMsg = Utils.getResponseMessage(master, RMS, respDta.getStatusCode());
                        Utils.showCustomErrorDialog(getActivity(), getString(R.string.filedx_collection), errorMsg);
                    }
                } else {
                    if (Utils.checkForSessionExpiry(getActivity(), fieldxGetPayAmountBean.getResponseCode()))
                        return;

                    String errorMsg = Utils.getResponseMessage(master, RMS, fieldxGetPayAmountBean.getResponseCode());
                    Utils.showCustomErrorDialog(getActivity(), getString(R.string.filedx_collection), errorMsg);
                }
            }
        }
    };

    Observer<FieldxRetailerResponseBean> observerRetailer = new Observer<FieldxRetailerResponseBean>() {
        @Override
        public void onChanged(@Nullable FieldxRetailerResponseBean fieldXRetailerBean) {
            allTask.clear();
            ProgressBarDialog.getProgressDialog().dismiss();
            if (fieldXRetailerBean == null)
                Utils.showCustomErrorDialog(master, "", getString(R.string.something_went_wrong));
            else {
                if (fieldXRetailerBean.getResponseCode() == 0) {
                    FieldxRetailerResponseBean.ResponseData responseData = fieldXRetailerBean.getResponseData();
                    if (responseData.getStatusCode() == 0) {
                        FieldxRetailerResponseBean.ResponseData.Data data = responseData.getData();
                        ArrayList<FieldxRetailerResponseBean.ResponseData.Data.AssignOrg> org = data.getAssignOrg();
                        for (int i = 0; i < org.size(); i++) {
                            FieldxRetailerResponseBean.ResponseData.Data.AssignOrg jsonObject = org.get(i);

                            AllTaskItem allTaskItem = new AllTaskItem(jsonObject.getOrgId(), jsonObject.getOrgName(),
                                    jsonObject.getCountry() + ", " + jsonObject.getState(), jsonObject.getBalance(),
                                    getTask(jsonObject.getOrgId(), "delivery"),
                                    getTask(jsonObject.getOrgId(), "pickup"),
                                    getAmountButtonStatua(jsonObject.getBalance()),
                                    jsonObject.getCountry() + ", " + jsonObject.getState() + ", " + jsonObject.getCity(), jsonObject.getOrgTypeCode());

                            allTask.add(allTaskItem);
                        }
                        ((FieldXHomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag("FieldXHomeFragment")).filterFieldxRetailerData();
                    } else {
                        if (Utils.checkForSessionExpiry(getActivity(), responseData.getStatusCode()))
                            return;

                        String errorMsg = Utils.getResponseMessage(master, RMS, responseData.getStatusCode());
                        Utils.showCustomErrorDialogAndLogout(getActivity(), getString(R.string.fieldx_retailer), errorMsg, true);
                    }
                } else {
                    if (Utils.checkForSessionExpiry(master, fieldXRetailerBean.getResponseCode()))
                        return;

                    String errorMsg = Utils.getResponseMessage(master, RMS, fieldXRetailerBean.getResponseCode());
                    Utils.showCustomErrorDialogAndLogout(getContext(), getActivity().getString(R.string.fieldx_retailer), errorMsg, true);
                }
            }
        }
    };

    Observer<FieldXChallanBean> observerChallan = new Observer<FieldXChallanBean>() {
        @Override
        public void onChanged(@Nullable FieldXChallanBean fieldXChallanBean) {
            challans.clear();
            ProgressBarDialog.getProgressDialog().dismiss();
            if (fieldXChallanBean == null) {
                Utils.showCustomErrorDialog(master, "", getString(R.string.something_went_wrong));
            } else {
                if (fieldXChallanBean.getResponse() != null && fieldXChallanBean.getResponse().getDlDetails() != null) {
                    FieldXChallanBean.Response resp = fieldXChallanBean.getResponse();
                    FieldXChallanBean.Response.DlDetails dl = resp.getDlDetails();
                    ArrayList<FieldXChallanBean.Response.DlDetails.SALE_PARTIALLY_RECEIVED> sale_partially = dl.getSALE_PARTIALLY_RECEIVED();
                    ArrayList<FieldXChallanBean.Response.DlDetails.SALE_IN_TRANSIT> sale_in = dl.getSALE_IN_TRANSIT();
                    ArrayList<FieldXChallanBean.Response.DlDetails.SALE_RETURN> pickup = dl.getSALE_RETURN();
                    challans.clear();
                    for (int i = 0; i < sale_partially.size(); i++) {
                        FieldXChallanItems challanItems = new FieldXChallanItems(sale_partially.get(i).getOrgId(), "delivery", sale_partially.get(i).getDlChallanNumber(), sale_partially.get(i).getDlId(), sale_partially.get(i).getUserName(), sale_partially.get(i).getDlDate().split(" ")[0]);
                        challans.add(challanItems);
                    }
                    for (int i = 0; i < sale_in.size(); i++) {
                        FieldXChallanItems challanItems = new FieldXChallanItems(sale_in.get(i).getOrgId(), "delivery", sale_in.get(i).getDlChallanNumber(), sale_in.get(i).getDlId(), sale_in.get(i).getUserName(), sale_in.get(i).getDlDate().split(" ")[0]);
                        challans.add(challanItems);
                    }
                    for (int i = 0; i < pickup.size(); i++) {
                        FieldXChallanItems challanItems = new FieldXChallanItems(pickup.get(i).getOrgId(), "pickup", pickup.get(i).getDlChallanNumber(), pickup.get(i).getDlId(), pickup.get(i).getUserName(), pickup.get(i).getDlDate().split(" ")[0]);
                        challans.add(challanItems);
                    }
                }
                getRetailerList();
            }
        }
    };

    private int getTask(String orgId, String task) {
        for (int i = 0; i < challans.size(); i++) {
            if (challans.get(i).getOrId().equalsIgnoreCase(orgId)) {
                if (challans.get(i).getTask().equalsIgnoreCase(task))
                    return View.VISIBLE;
            }
        }
        return View.GONE;
    }

    private int getAmountButtonStatua(String balance) {
        if (balance.charAt(0) == '-')
            return View.VISIBLE;
        return View.GONE;
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
                        balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? PlayerData.getInstance().getUserBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) : PlayerData.getInstance().getUserBalance();
                        tvUserBalance.setText(balance);
                        tvUserBalance.setTextColor(PlayerData.getInstance().getLoginData().getResponseData().getData().getRawUserBalance() < 0.0 ? master.getResources().getColor(R.color.colorAppOrangeDark) : master.getResources().getColor(R.color.colorGreen));
                    } else {
                        tvUsername.setText(getString(R.string.balance_without_colon));
                        String balance;
                        balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? PlayerData.getInstance().getOrgBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) : PlayerData.getInstance().getOrgBalance();
                        tvUserBalance.setText(balance);
                    }
                } else {
                    tvUserBalance.setText(PlayerData.getInstance().getOrgBalance());
                }
            } catch (Exception e) {
                e.printStackTrace();
                tvUserBalance.setText(PlayerData.getInstance().getOrgBalance());
            }
        }
        String isHead = PlayerData.getInstance().getLoginData().getResponseData().getData().getIsHead();

        if (isHead.equalsIgnoreCase("NO")) {
            if (tvUserBalance != null)
                tvUserBalance.setOnClickListener(v -> ((MainActivity) getActivity()).callBalRefreshForSubUser());
        }
    }

    public void onBackPressed() {
    }


    public static HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList getMenuBeanFieldx() {
        return menuBeanFieldx;
    }

    public void getRetailerList() {
        ProgressBarDialog.getProgressDialog().showProgress(master);
        try {
            if (!Utils.isNetworkConnected(master)) {
                ProgressBarDialog.getProgressDialog().dismiss();
                Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return;
            }
            UrlBean urlBean = Utils.getFieldxRetailerUrlDetails(getMenuBeanFieldx(), "getRetList");
            homeViewModel.getRetailerApi(urlBean);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getFieldxRetailerData() {
        ProgressBarDialog.getProgressDialog().showProgress(master);
        refreshBalance();
        try {
            if (!Utils.isNetworkConnected(master)) {
                ProgressBarDialog.getProgressDialog().dismiss();
                Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return;
            }
            UrlBean urlBean = Utils.getFieldxChallanUrlDetails(getMenuBeanFieldx(), "getRetailerChallans");
            homeViewModel.getFieldxChallanApi(urlBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
