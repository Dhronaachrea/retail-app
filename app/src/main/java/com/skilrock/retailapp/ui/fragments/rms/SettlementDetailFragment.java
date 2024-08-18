package com.skilrock.retailapp.ui.fragments.rms;

import android.annotation.SuppressLint;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.SettlementDetailAdapter;
import com.skilrock.retailapp.interfaces.UserSelectListener;
import com.skilrock.retailapp.models.ola.OrgUserResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.SettlementDetailResponseBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.SettlementDetailViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettlementDetailFragment extends BaseFragment implements View.OnClickListener, UserSelectListener {

    private SettlementDetailViewModel viewModel;
    private TextView tvOrgName, tvUserName, tvOpeningBal, tvClosingBal, tvFromDate, tvToDate;
    private ImageView ivProceed;
    private RecyclerView rvSettlementList;
    private String mSettlementId;
    private LinearLayout llFromDate, llEndDate, llOpeningBal;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private SettlementDetailAdapter adapter;
    private List<OrgUserResponseBean.UserData> mUserDataList = new ArrayList<>();
    private Spinner spinnerUsers;
    private final String RMS = "rms";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settlement_detail, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(this).get(SettlementDetailViewModel.class);
            viewModel.getLiveDataSettlementList().observe(this, observerSettlementDetails);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        tvOrgName               = view.findViewById(R.id.tv_org_name);
        tvUserName              = view.findViewById(R.id.tv_user_name);
        rvSettlementList        = view.findViewById(R.id.rv_report);
        ivProceed               = view.findViewById(R.id.button_proceed);
        llFromDate              = view.findViewById(R.id.containerFromDate);
        llEndDate               = view.findViewById(R.id.containerEndDate);
        spinnerUsers            = view.findViewById(R.id.spinnerUser);
        tvOpeningBal            = view.findViewById(R.id.tv_opening_balance);
        tvClosingBal            = view.findViewById(R.id.tv_closing_balance);
        llOpeningBal            = view.findViewById(R.id.view_opening_balance);
        tvFromDate              = view.findViewById(R.id.tv_from_date);
        tvToDate                = view.findViewById(R.id.tv_to_date);

        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);

        tvUsername.setText(String.format(getString(R.string.limit_col), PlayerData.getInstance().getCreditLimit()));
        refreshBalance();

        llFromDate.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
        ivProceed.setOnClickListener(this);

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
            mSettlementId = bundle.getString("settlement_id");
        }

        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "getSettlementDetails");

        ProgressBarDialog.getProgressDialog().showProgress(master);

        viewModel.callGetSettlementDetailApi(PlayerData.getInstance().getToken(), url,
                mSettlementId, "en", AppConstants.appType);
    }

    @Override
    public void onClick(View view) {
    }

    Observer<SettlementDetailResponseBean> observerSettlementDetails = new Observer<SettlementDetailResponseBean>() {
        @Override
        public void onChanged(@Nullable SettlementDetailResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                if (response.getResponseData().getData() == null)
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), getString(R.string.no_data_found));
                else if (response.getResponseData().getData().getTransactions().size() < 1)
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), getString(R.string.no_data_found));
                else {
                    setAdapter(response);

                    tvOpeningBal.setText(response.getResponseData().getData().getSettlementInfo().getOpeningBalance());
                    tvClosingBal.setText(response.getResponseData().getData().getSettlementInfo().getClosingBalance());
                    tvOrgName.setText(response.getResponseData().getData().getSettlementInfo().getOrganizationName());
                    tvUserName.setText(response.getResponseData().getData().getSettlementInfo().getDoerUserName());
                }
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), getString(R.string.something_went_wrong));
        }
    };

    private void setAdapter(SettlementDetailResponseBean response) {
        adapter = new SettlementDetailAdapter(response.getResponseData().getData().getTransactions(), getContext());

        rvSettlementList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSettlementList.setItemAnimator(new DefaultItemAnimator());
        rvSettlementList.setAdapter(adapter);
        tvFromDate.setText(Utils.formatDate(response.getResponseData().getData().getSettlementInfo().getSettlementFromDate().split(" ")[0]) + " "
                + Utils.formatTime(response.getResponseData().getData().getSettlementInfo().getSettlementFromDate().split(" ")[1]));

        tvToDate.setText(Utils.formatDate(response.getResponseData().getData().getSettlementInfo().getSettlementToDate().split(" ")[0]) + " "
                + Utils.formatTime(response.getResponseData().getData().getSettlementInfo().getSettlementToDate().split(" ")[1]));
    }

    private String formatDate(TextView tvDate) {
        if (tvDate.getText().toString().isEmpty() || tvDate.getText().toString().equalsIgnoreCase(getString(R.string.from_date)) || tvDate.getText().toString().equalsIgnoreCase(getString(R.string.to_date))) {

            return "";
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd-mm-yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = originalFormat.parse(tvDate.getText().toString().trim());
            Log.d("log", "Old Format: " + originalFormat.format(date));
            Log.d("log", "New Format: " + targetFormat.format(date));

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return targetFormat.format(date);
    }

    @Override
    public void onUserClicked(int position, String settlementId) {

    }
}