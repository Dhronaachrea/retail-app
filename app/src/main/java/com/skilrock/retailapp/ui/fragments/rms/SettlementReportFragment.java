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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.SettlementListAdapter;
import com.skilrock.retailapp.interfaces.UserSelectListener;
import com.skilrock.retailapp.models.ola.OrgUserResponseBeanNew;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.SettlementListResponseBean;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.SettlementReportViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SettlementReportFragment extends BaseFragment implements View.OnClickListener, UserSelectListener {

    private SettlementReportViewModel viewModel;
    private TextView tvStartDate, tvEndDate, tvOpeningBal, tvClosingBal;
    private ImageView ivProceed;
    private RecyclerView rvSettlementList;
    private String mUserId;
    private LinearLayout llFromDate, llEndDate, llOpeningBal;
    private HashMap<String, String> hashMapUser = new HashMap<>();
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private SettlementListAdapter adapter;
    //private List<OrgUserResponseBean.UserData> mUserDataList = new ArrayList<>();
    private ArrayList<SettlementListResponseBean.Data> mReportList = new ArrayList<>();
    private Spinner spinnerUsers;
    private String mStartDate, mEndDate;
    private final String RMS = "rms";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settlement_report, container, false);

        initializeWidgets(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(this).get(SettlementReportViewModel.class);
            viewModel.getLiveDataOrgUser().observe(this, observer);
            viewModel.getLiveDataSettlementList().observe(this, observerSettlementList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        tvStartDate         = view.findViewById(R.id.tvStartDate);
        tvEndDate           = view.findViewById(R.id.tvEndDate);
        rvSettlementList    = view.findViewById(R.id.rv_report);
        ivProceed           = view.findViewById(R.id.button_proceed);
        llFromDate          = view.findViewById(R.id.containerFromDate);
        llEndDate           = view.findViewById(R.id.containerEndDate);
        spinnerUsers        = view.findViewById(R.id.spinnerUser);

        TextView tvTitle    = view.findViewById(R.id.tvTitle);
        tvUsername          = view.findViewById(R.id.tvUserName);
        tvUserBalance       = view.findViewById(R.id.tvUserBalance);

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
        }

        spinnerUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mUserId = String.valueOf(mUserDataList.get(position).getUserId());
                mUserId = hashMapUser.get(spinnerUsers.getSelectedItem().toString());
                //hashMapUser.get(spinnerUser.getSelectedItem().toString()))
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (hashMapUser != null && !hashMapUser.isEmpty()) {
            setUserListAdapter();
        } else {
            String url = Utils.getRmsUrlDetails(menuBean, getContext(), "doUserSearch");
            Integer domain = (int) PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId();
            ProgressBarDialog.getProgressDialog().showProgress(master);

            viewModel.callGetOrgUserApi(PlayerData.getInstance().getToken(), url,
                    PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId(), domain);
        }

        if (mReportList != null && !mReportList.isEmpty()) {
            setAdapter(mReportList);
            tvStartDate.setText(mStartDate);
            tvEndDate.setText(mEndDate);
        } else {
            tvStartDate.setText(Utils.getPreviousDate(15));
            tvEndDate.setText(Utils.getCurrentDate());
        }

        if (SharedPrefUtil.getLanguage(master).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            ivProceed.setRotation(180f);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.containerFromDate:
                Utils.openStartDateDialog(master, tvStartDate, tvEndDate);
                break;
            case R.id.containerEndDate:
                if (tvStartDate.getText().toString().equalsIgnoreCase(getString(R.string.start_date)))
                    Toast.makeText(master, getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();
                else
                    Utils.openEndDateDialog(master, tvStartDate, tvEndDate);
                break;
            case R.id.button_proceed:
                if (!Utils.isNetworkConnected(master)) {
                    Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                mReportList.clear();

                if (adapter != null)
                    adapter.notifyDataSetChanged();

                String url = Utils.getRmsUrlDetails(menuBean, getContext(), "getSettlementList");

                ProgressBarDialog.getProgressDialog().showProgress(master);

                viewModel.callGetSettlementListApi(PlayerData.getInstance().getToken(), url,
                        mUserId, formatDate(tvStartDate) + " 00:00:00", formatDate(tvEndDate) + " 23:59:59", "en", AppConstants.appType);

                mStartDate = tvStartDate.getText().toString();
                mEndDate = tvEndDate.getText().toString();
                break;
        }
    }

    Observer<OrgUserResponseBeanNew> observer = new Observer<OrgUserResponseBeanNew>() {
        @Override
        public void onChanged(@Nullable OrgUserResponseBeanNew response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.settlement_report), getString(R.string.something_went_wrong), 1, getFragmentManager());

            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.settlement_report), errorMsg, 1, getFragmentManager());
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.settlement_report), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() == 0) {
                //mUserDataList = response.getResponseData().getData();
                hashMapUser.clear();
                for (OrgUserResponseBeanNew.UserSearchDatum data : response.getResponseData().getData().getUserSearchData())
                    hashMapUser.put(data.getUserName() + " (" + data.getName() + ")", String.valueOf(data.getUserId()));
                setUserListAdapter();
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), getString(R.string.something_went_wrong));
        }
    };

    Observer<SettlementListResponseBean> observerSettlementList = new Observer<SettlementListResponseBean>() {
        @Override
        public void onChanged(@Nullable SettlementListResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), errorMsg);
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0) {
                if (response.getResponseData().getData() == null)
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), getString(R.string.no_data_found));
                else if (response.getResponseData().getData().size() < 1)
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), getString(R.string.no_data_found));
                else {
                    mReportList = response.getResponseData().getData();
                    setAdapter(response.getResponseData().getData());
                }
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.settlement_report), getString(R.string.something_went_wrong));
        }
    };

    private void setAdapter(ArrayList<SettlementListResponseBean.Data> arrayList) {
        adapter = new SettlementListAdapter(arrayList, getContext(), this);

        rvSettlementList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSettlementList.setItemAnimator(new DefaultItemAnimator());
        rvSettlementList.setAdapter(adapter);
    }

    private void setUserListAdapter() {
        List<String> listSpinner = new ArrayList<>(hashMapUser.keySet());

        /*for (OrgUserResponseBean.UserData data : list) {
            listSpinner.add(data.getUserName());
        }*/

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerUsers.setAdapter(adapter);

        if (listSpinner.size() == 1) {
            spinnerUsers.setBackgroundResource(0);
            spinnerUsers.setClickable(false);
            spinnerUsers.setEnabled(false);
        }
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
        ((MainActivity) getActivity()).openSettlementDetails(settlementId);
    }
}