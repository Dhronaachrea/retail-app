package com.skilrock.retailapp.ui.fragments.rms;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.ScratchAndDrawGameModuleAdapter;
import com.skilrock.retailapp.interfaces.ModuleListener;
import com.skilrock.retailapp.landscape_draw_games.ui.activities.DrawGameBaseActivity;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaDepositFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaDepositMyanmarFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaDepositPayprFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaMyPromoFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaNetGamingReportFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaPendingTransactionCamroonFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaPendingTransactionFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaPlayerDetailsReportFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaPlayerForgotPasswordFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaPlayerSearchReportFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaPlayerSearchReportMyanmarFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaPlayerTransactionReportFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaPlayerTransactionReportMyanmarFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaRegistrationFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaRegistrationMyanmarFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaRegistrationPayprFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaWithdrawalFragment;
import com.skilrock.retailapp.ui.fragments.ola.OlaWithdrawalMyanmarFragment;
import com.skilrock.retailapp.ui.fragments.scratch.MultiWinningClaimFragment;
import com.skilrock.retailapp.ui.fragments.scratch.PackActivationFragment;
import com.skilrock.retailapp.ui.fragments.scratch.PackActivationLandscapeFragment;
import com.skilrock.retailapp.ui.fragments.scratch.PackOrderFragment;
import com.skilrock.retailapp.ui.fragments.scratch.PackReceiveFragment;
import com.skilrock.retailapp.ui.fragments.scratch.PackReceiveLandscapeFragment;
import com.skilrock.retailapp.ui.fragments.scratch.PackReturnFragment;
import com.skilrock.retailapp.ui.fragments.scratch.SaleTicketFragment;
import com.skilrock.retailapp.ui.fragments.scratch.SaleTicketGelsaFragment;
import com.skilrock.retailapp.ui.fragments.scratch.ScratchReportFragment;
import com.skilrock.retailapp.ui.fragments.scratch.WinningClaimFragment;
import com.skilrock.retailapp.ui.fragments.scratch.WinningClaimLandscapeFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.StringMapOla;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ScratchAndDrawGameFragment extends BaseFragment {

    private ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> list;
    //private TextView tvUserBalance;
    Bundle bundle = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            return inflater.inflate(R.layout.scratch_fragment_landscape, container, false);
        else
            return inflater.inflate(R.layout.scratch_fragment, container, false);
    }

    ModuleListener moduleListener = (menuCode, index, menuBean) -> {
        bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("title", StringMapOla.getCaption(menuBean.getMenuCode(), menuBean.getCaption()));
        bundle.putParcelable("MenuBean", menuBean);
        switch (menuCode.trim()) {
            case AppConstants.SCRATCH_SALE:
                if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.GELSA_RETAIL)) {
                    master.openFragment(new SaleTicketGelsaFragment(), "SaleTicketGelsaFragment", true, bundle);
                } else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
                    master.openFragment(new SaleTicketGelsaFragment(), "SaleTicketGelsaFragment", true, bundle);
                }  else {
                    master.openFragment(new SaleTicketFragment(), "SaleTicketFragment", true, bundle);
                }
                break;
            case AppConstants.SCRATCH_ORDER_BOOK:
                master.openFragment(new PackOrderFragment(), "QuickOrderFragment", true, bundle);
                break;
            case AppConstants.SCRATCH_ACTIVATE_BOOK:
                if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
                    master.openFragment(new PackActivationLandscapeFragment(), "PackActivationLandscapeFragment", true, bundle);
                else
                    master.openFragment(new PackActivationFragment(), "ActiveBookFragment", true, bundle);
                break;
            case AppConstants.SCRATCH_RECEIVE_BOOK:
                if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
                    master.openFragment(new PackReceiveLandscapeFragment(), "PackReceiveLandscapeFragment", true, bundle);
                else
                    master.openFragment(new PackReceiveFragment(), "ReceiveBookFragment", true, bundle);
                break;
            case AppConstants.SCRATCH_WIN_CLAIM:
                if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
                    master.openFragment(new WinningClaimLandscapeFragment(), "WinningClaimLandscapeFragment", true, bundle);
                else if(BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
//                    master.openFragment(new WinningClaimFragment(), "WinningClaimFragment", true, bundle);
                    master.openFragment(new MultiWinningClaimFragment(), "MultiWinningClaimFragment", true, bundle);
                } else
                    master.openFragment(new WinningClaimFragment(), "WinningClaimFragment", true, bundle);
                break;
            case AppConstants.SCRATCH_RETURN_BOOK:
                master.openFragment(new PackReturnFragment(), "PackReturnFragment", true, bundle);
                break;
            case AppConstants.SCRATCH_INV_REPORT:
                master.openFragment(new ScratchReportFragment(), "ScratchReportFragment", true, bundle);
                break;
            case AppConstants.SCRATCH_INV_SUMMARY_REPORT:
                master.openFragment(new InventoryFlowReportFragment(), "InventoryFlowReportFragment", true, bundle);
                break;
            case AppConstants.OLA_REGISTER:
                try {
                    if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                        Toast.makeText(master, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                    else {
                        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR)) {
                            master.openFragment(new OlaRegistrationMyanmarFragment(), "OlaRegistrationMyanmarFragment", true, bundle);
                            //master.openFragment(new OlaRegistrationFragmentMyanmar(), "OlaRegistrationFragmentMyanmar", true, bundle);
                        }
                        else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS))
                            master.openFragment(new OlaRegistrationPayprFragment(), "OlaRegistrationPayprFragment", true, bundle);
                        else
                            master.openFragment(new OlaRegistrationFragment(), "OlaRegistrationFragment", true, bundle);
                    }
                } catch (Exception e) {
                    if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                        master.openFragment(new OlaRegistrationMyanmarFragment(), "OlaRegistrationMyanmarFragment", true, bundle);
                    else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS))
                        master.openFragment(new OlaRegistrationPayprFragment(), "OlaRegistrationPayprFragment", true, bundle);
                    else
                        master.openFragment(new OlaRegistrationFragment(), "OlaRegistrationFragment", true, bundle);
                }
                break;
            case AppConstants.OLA_DEPOSIT:
                try {
                    if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                        Toast.makeText(master, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                    else {
                        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR) ||
                                BuildConfig.app_name.equalsIgnoreCase(AppConstants.CAMEROON))
                            master.openFragment(new OlaDepositMyanmarFragment(), "OlaDepositMyanmarFragment", true, bundle);
                        else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS))
                            master.openFragment(new OlaDepositPayprFragment(), "OlaDepositPayprFragment", true, bundle);
                        else
                            master.openFragment(new OlaDepositFragment(), "OlaDepositFragment", true, bundle);
                    }
                } catch (Exception e) {
                    if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR) ||
                            BuildConfig.app_name.equalsIgnoreCase(AppConstants.CAMEROON))
                        master.openFragment(new OlaDepositMyanmarFragment(), "OlaDepositMyanmarFragment", true, bundle);
                    else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS))
                        master.openFragment(new OlaDepositPayprFragment(), "OlaDepositPayprFragment", true, bundle);
                    else
                        master.openFragment(new OlaDepositFragment(), "OlaDepositFragment", true, bundle);
                }
                break;
            case AppConstants.OLA_WITHDRAW:
                try {
                    if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                        Toast.makeText(master, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                    else {
                        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR) ||
                                BuildConfig.app_name.equalsIgnoreCase(AppConstants.CAMEROON))
                            master.openFragment(new OlaWithdrawalMyanmarFragment(), "OlaWithdrawalMyanmarFragment", true, bundle);
                        else
                            master.openFragment(new OlaWithdrawalFragment(), "OlaWithdrawalFragment", true, bundle);
                    }
                } catch (Exception e) {
                    master.openFragment(new OlaWithdrawalFragment(), "OlaWithdrawalFragment", true, bundle);
                }
                break;
            case AppConstants.OLA_MY_PROMO:
                try {
                    if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                        Toast.makeText(master, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                    else {
                        master.openFragment(new OlaMyPromoFragment(), "OlaMyPromoFragment", true, bundle);
                    }
                } catch (Exception e) {
                    master.openFragment(new OlaMyPromoFragment(), "OlaMyPromoFragment", true, bundle);
                }
                break;
            case AppConstants.OLA_PLR_LEDGER:
                if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                    master.openFragment(new OlaPlayerTransactionReportMyanmarFragment(), "OlaPlayerTransactionReportMyanmarFragment", true, bundle);
                else
                    master.openFragment(new OlaPlayerTransactionReportFragment(), "OlaPlayerLedgerReportFragment", true, bundle);

                break;
            case AppConstants.OLA_PLR_DETAILS:
                bundle.putParcelable("PlayerSearch", getPlayerTransactionMenuDetails());
                master.openFragment(new OlaPlayerDetailsReportFragment(), "OlaPlayerDetailsReportFragment", true, bundle);
                break;
            case AppConstants.OLA_PLR_SEARCH:
                bundle.putParcelable("PlayerSearch", getPlayerTransactionMenuDetails());

                if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                    master.openFragment(new OlaPlayerSearchReportMyanmarFragment(), "OlaPlayerSearchReportMyanmarFragment", true, bundle);
                else
                    master.openFragment(new OlaPlayerSearchReportFragment(), "OlaPlayerSearchReportFragment", true, bundle);

                break;
            case AppConstants.OLA_PLR_PASSWORD:
                try {
                    if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                        Toast.makeText(master, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                    else
                        master.openFragment(new OlaPlayerForgotPasswordFragment(), "OlaPlayerForgotPasswordFragment", true, bundle);
                } catch (Exception e) {
                    master.openFragment(new OlaPlayerForgotPasswordFragment(), "OlaPlayerForgotPasswordFragment", true, bundle);
                }
                break;
            case AppConstants.OLA_PLR_PENDING_TXN:
                bundle.putParcelable("PlayerSearch", getPlayerTransactionMenuDetails());

                if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.CAMEROON))
                    master.openFragment(new OlaPendingTransactionCamroonFragment(), "OlaPendingTransactionCamroonFragment", true, bundle);
                else
                    master.openFragment(new OlaPendingTransactionFragment(), "OlaPendingTransactionFragment", true, bundle);

                break;
            case AppConstants.OLA_NET_GAMING_DETAILS:
                bundle.putParcelable("PlayerSearch", getPlayerTransactionMenuDetails());
                master.openFragment(new OlaNetGamingReportFragment(), "OlaNetGamingReport", true, bundle);

                break;
            case AppConstants.DGE_RESULT_LIST:
                break;
            case AppConstants.DGE_PRIZE_LIST:
                break;
            case AppConstants.DGE_GAME_LIST:
                Intent intent = new Intent(master, DrawGameBaseActivity.class);
                intent.putExtras(bundle);
                master.startActivity(intent);
                //Toast.makeText(master, "Game List", Toast.LENGTH_SHORT).show();
                break;
            case AppConstants.DGE_SALE:
                break;
            case AppConstants.DGE_QUICK_PICK:
                break;
            case AppConstants.DGE_REPRINT:
                break;
            case AppConstants.DGE_CANCEL_TICKET:
                break;
            case AppConstants.DGE_WIN_CLAIM:
                break;
        }
    };



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        RecyclerView rvScratchAndDrawGameMenu = view.findViewById(R.id.rv_scratch_menu);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        LinearLayout llBack = view.findViewById(R.id.llBack);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        ImageView ivModule = view.findViewById(R.id.ivModule);
        ImageView ivDrawer = view.findViewById(R.id.iv_drawer);
        refreshBalance();
        rvScratchAndDrawGameMenu.setHasFixedSize(true);
        rvScratchAndDrawGameMenu.setLayoutManager(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI) ? new GridLayoutManager(master, 4) : new GridLayoutManager(master, 2));
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {



            list = bundle.getParcelableArrayList("ListScratchGameModule");
            ScratchAndDrawGameModuleAdapter scratchAndDrawGameModuleAdapter = new ScratchAndDrawGameModuleAdapter(list, moduleListener);
            rvScratchAndDrawGameMenu.setAdapter(scratchAndDrawGameModuleAdapter);
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));//isSingleMenu
                master.IS_SINGLE_MENU = bundle.getBoolean("isSingleMenu");

                if (bundle.getString("title").equalsIgnoreCase("SCRATCH")) {
                    //tvTitle.setVisibility(View.GONE);
                    ivModule.setVisibility(View.VISIBLE);
                    ivModule.setBackgroundResource(R.drawable.icon_scratch);
                } else if (bundle.getString("title").equalsIgnoreCase("PLAYER MANAGEMENT") ||
                        bundle.getString("title").equalsIgnoreCase(getString(R.string.player_management_caption))) {
                    ivModule.setVisibility(View.VISIBLE);
                    ivModule.setBackgroundResource(R.drawable.group_1595);
                } else {
                    tvTitle.setVisibility(View.VISIBLE);
                    ivModule.setVisibility(View.GONE);
                }

            }
        }

        llBack.setOnClickListener(v -> {
            if (master.IS_SINGLE_MENU)
                master.onClickOpenDrawer(llBack);
            else
                Objects.requireNonNull(getFragmentManager()).popBackStack();
        });

        if (master.IS_SINGLE_MENU) {
            master.enableDrawer(true);
            ivDrawer.setImageResource(R.drawable.icon_drawer);
        }

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS)) {
            ivModule.setVisibility(View.GONE);
        }
    }

    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList getPlayerTransactionMenuDetails() {
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).getMenuCode().trim().equalsIgnoreCase(AppConstants.OLA_PLR_LEDGER))
                return list.get(index);
        }
        return null;
    }

    public void updateBalance() {
        tvUserBalance.setText(PlayerData.getInstance().getOrgBalance());
    }

    /*public void refreshBalance() {
        tvUserBalance.setText(PlayerData.getInstance().getOrgBalance());
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isBalanceUpdate")) {
            refreshBalance();
        } else {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(getActivity(), getString(R.string.winning), errorMsg);
        }
    }

    /*@Override
    public void onBackPressed() {
        Log.w("log", "IS_SINGLE_MENU: " + IS_SINGLE_MENU);
        if (IS_SINGLE_MENU)
            master.finish();
        else
            super.onBackPressed();
    }*/
}