package com.skilrock.retailapp.ui.fragments.rms;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.OrgUserListAdapter;
import com.skilrock.retailapp.interfaces.UserSelectListener;
import com.skilrock.retailapp.models.SettleUserAccountRequestBean;
import com.skilrock.retailapp.models.ola.OrgUserResponseBeanNew;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.AccountSettlementViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends BaseFragment implements View.OnClickListener, UserSelectListener {

    private AccountSettlementViewModel viewModel;
    private RecyclerView rvSettlementList;
    private String mUserId;
    private TextView tvClosingBalance, tvBalance;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private int decimalDegits = 2;
    private ArrayList<OrgUserResponseBeanNew.UserSearchDatum> arrayList = new ArrayList<OrgUserResponseBeanNew.UserSearchDatum>();
    private OrgUserListAdapter adapter;
    private boolean isError = false;
    double cashAmount = 0.0;
    private boolean isAllowedZeroSettlement = false;
    private boolean isSettledNow = true;
    private double mBalance = 0;
    private List<OrgUserResponseBeanNew.UserSearchDatum> mUserDataList = new ArrayList<>();
    private final String RMS = "rms";
    private boolean isFirstTime = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_list, container, false);
        initializeWidgets(rootView);
        Log.d("TAg", "UserListFragment::");

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(this).get(AccountSettlementViewModel.class);
            viewModel.getLiveDataOrgUser().observe(this, observer);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        rvSettlementList        = view.findViewById(R.id.rv_user);
        tvClosingBalance        = view.findViewById(R.id.tv_closing_balance);
        tvBalance               = view.findViewById(R.id.tv_balance);

        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "doUserSearch");
        Integer domain = (int) PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId();

        ProgressBarDialog.getProgressDialog().showProgress(master);

        isFirstTime = true;
        viewModel.callGetOrgUserApi(PlayerData.getInstance().getToken(), url,
                PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId(), domain);
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
        if (isError) {
            Toast.makeText(master, getString(R.string.enter_valid_amount), Toast.LENGTH_SHORT).show();
            return;
        }

        SettleUserAccountRequestBean settleUserAccountRequestBean = new SettleUserAccountRequestBean();
        settleUserAccountRequestBean.setSettleAction(settleAction);
        settleUserAccountRequestBean.setSettleAmount(Utils.removeExponential(String.valueOf(cashAmount)));
        settleUserAccountRequestBean.setUserId(Integer.valueOf(mUserId));

        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "settleUserAccount");

        ProgressBarDialog.getProgressDialog().showProgress(master);

        viewModel.callSettleUserAccountApi(PlayerData.getInstance().getToken(), url, settleUserAccountRequestBean);
    }

    Observer<OrgUserResponseBeanNew> observer = new Observer<OrgUserResponseBeanNew>() {
        @Override
        public void onChanged(@Nullable OrgUserResponseBeanNew response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.user), getString(R.string.something_went_wrong), 1, getFragmentManager());

            else if (response.getResponseCode() != 0) {
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.user), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;

                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.user), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() == 0) {
                mUserDataList = response.getResponseData().getData().getUserSearchData();

                setAdapter(response.getResponseData().getData().getUserSearchData());
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.user), getString(R.string.something_went_wrong));
        }
    };

    private void setAdapter(ArrayList<OrgUserResponseBeanNew.UserSearchDatum> list) {
        arrayList = new ArrayList<OrgUserResponseBeanNew.UserSearchDatum>();

        if (list.isEmpty()) return;

        for (int index = 0; index < list.size(); index++) {
            try {
                if (list.get(index).getRawdisplayBalance() != 0.0) {
                    arrayList.add(list.get(index));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (arrayList.isEmpty()) {
            String errorMsg = getString(R.string.no_data_found);
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.user), errorMsg, 1, getFragmentManager());
            return;
        }

        adapter = new OrgUserListAdapter(arrayList, getContext(), this);

        rvSettlementList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSettlementList.setItemAnimator(new DefaultItemAnimator());
        rvSettlementList.setAdapter(adapter);
    }

    @Override
    public void onUserClicked(int position, String mUserId) {
        ((MainActivity) getActivity()).openAccountSettlement(mUserId, arrayList.get(position).getUserName(), arrayList.get(position).getName());
    }
}
