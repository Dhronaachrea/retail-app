package com.skilrock.retailapp.ui.fragments.ola;

import android.annotation.SuppressLint;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.ola.OlaPlayerTransactionAdapter;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.ola.OlaPlayerTransactionReportRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerTransactionResponseBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.ola.OlaPlayerTransactionReportViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class OlaPlayerTransactionReportMyanmarFragment extends BaseFragment implements View.OnClickListener {

    private OlaPlayerTransactionReportViewModel viewModel;
    private TextView tvStartDate, tvEndDate;
    private ImageView ivProceed;
    private RecyclerView rvReport;
    private LinearLayout llFromDate, llEndDate;
    private EditText etUserName;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private int PAGE_INDEX = 1;
    private boolean isLoading = false;
    private ArrayList<OlaPlayerTransactionResponseBean.ResponseData> listRV = new ArrayList<>();
    private OlaPlayerTransactionAdapter adapter;
    private boolean isDataFinished = false;
    private TextInputLayout tilUserName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_myanmar_ledger, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(OlaPlayerTransactionReportViewModel.class);
        viewModel.getLiveData().observe(this, observer);
        viewModel.getLiveDataLoadMore().observe(this, observerLoadMore);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
        initScrollListener();
    }

    private void initializeWidgets(View view) {
        tvStartDate             = view.findViewById(R.id.tvStartDate);
        tvEndDate               = view.findViewById(R.id.tvEndDate);
        rvReport                = view.findViewById(R.id.rv_report);
        ivProceed               = view.findViewById(R.id.button_proceed);
        llFromDate              = view.findViewById(R.id.containerFromDate);
        llEndDate               = view.findViewById(R.id.containerEndDate);
        etUserName                = view.findViewById(R.id.et_user_name);
        tilUserName               = view.findViewById(R.id.til_user_name);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        refreshBalance();
        listRV.clear();

        llFromDate.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
        ivProceed.setOnClickListener(this);

        tvStartDate.setText(Utils.getPreviousDate(7));
        tvEndDate.setText(Utils.getCurrentDate());

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
            String userName = bundle.getString("userName");
            if (userName != null) {
                etUserName.setText(userName);
                callLedgerReportApi(true);
            }
        }

        etUserName.setOnEditorActionListener(this::onEditorAction);
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
                Utils.hideKeyboard(Objects.requireNonNull(getActivity()));
                if (validate()) {
                    callLedgerReportApi(true);
                }
                break;
        }

    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            etUserName.setError(getString(R.string.this_field_cannot_be_empty));
            etUserName.requestFocus();
            tilUserName.startAnimation(Utils.shakeError());
            return false;
        }
        if (tvStartDate.getText().toString().equalsIgnoreCase(getString(R.string.start_date))) {
            Toast.makeText(master, getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tvEndDate.getText().toString().equalsIgnoreCase(getString(R.string.end_date))) {
            Toast.makeText(master, getString(R.string.select_end_date), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    Observer<OlaPlayerTransactionResponseBean> observer = new Observer<OlaPlayerTransactionResponseBean>() {
        @Override
        public void onChanged(@Nullable OlaPlayerTransactionResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) Utils.showCustomErrorDialog(getContext(), getString(R.string.player_transaction), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                if (response.getResponseData() != null && response.getResponseData().size() < 1) {
                    if (adapter != null)
                        adapter.clear();
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.player_transaction), getString(R.string.no_data_found));
                }
                else {
                    PAGE_INDEX = 1;
                    isDataFinished = false;
                    ArrayList<OlaPlayerTransactionResponseBean.ResponseData> list = response.getResponseData();
                    //Log.w("log", "List Size: " + list.size());
                    if (list != null) {
                        listRV.clear();
                        if (list.size() < AppConstants.PAGE_SIZE)
                            isDataFinished = true;
                        listRV.addAll(list);
                        adapter = new OlaPlayerTransactionAdapter(getContext(), listRV);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        rvReport.setLayoutManager(mLayoutManager);
                        rvReport.setItemAnimator(new DefaultItemAnimator());
                        rvReport.setAdapter(adapter);
                    }
                }
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode())) return;

                if (adapter != null)
                    adapter.clear();
                String errorMsg = Utils.getResponseMessage(master, "ola", response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.player_transaction), errorMsg);
            }
        }
    };

    Observer<OlaPlayerTransactionResponseBean> observerLoadMore = response -> {

        if (response == null) {
            loadMoreAfterApiResponse(null);
            Toast.makeText(master, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }
        else if (response.getResponseCode() == 0) {
            if (response.getResponseData() != null && response.getResponseData().size() < 1) {
                Toast.makeText(master, getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
                isDataFinished = true;
                loadMoreAfterApiResponse(null);
            }
            else {
                if (Objects.requireNonNull(response.getResponseData()).size() < AppConstants.PAGE_SIZE)
                    isDataFinished = true;
                loadMoreAfterApiResponse(response.getResponseData());
            }
        } else {
            loadMoreAfterApiResponse(null);
            if (Utils.checkForSessionExpiry(master, response.getResponseCode())) return;

            String errorMsg = Utils.getResponseMessage(master, "ola", response.getResponseCode());
            Toast.makeText(master, errorMsg, Toast.LENGTH_LONG).show();
        }
    };

    private void callLedgerReportApi(boolean isFirstTime) {
        if (adapter != null) {
            //rvReport.post(() -> adapter.clear());
            //adapter.clear();
        }
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "playerLedger");
        if (urlBean != null) {
            if (isFirstTime)
                ProgressBarDialog.getProgressDialog().showProgress(master);

            String domain = PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgCode() + "_" +
                    PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName();
            OlaPlayerTransactionReportRequestBean model = new OlaPlayerTransactionReportRequestBean();
            model.setAccept(urlBean.getAccept());
            model.setContentType(urlBean.getContentType());
            model.setFromDate(formatDate(tvStartDate));
            model.setToDate(formatDate(tvEndDate));

            model.setUrl(urlBean.getUrl());
            model.setPassword(urlBean.getPassword());
            model.setPageSize(AppConstants.PAGE_SIZE);
            model.setPageIndex(isFirstTime?1:++PAGE_INDEX);

            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                model.setPlayerDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_OLA_MYANMAR") ||
                        BuildConfig.FLAVOR.equalsIgnoreCase("pROD_OLA_MYANMAR")
                        ? "www.bet2winasia.com" : "www.igamew.com");
            else
                model.setPlayerDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")
                        ? "www.camwinlotto.cm" : "www.camwinlotto.cm");

            model.setPlayerUserName(getText(etUserName));
            model.setUserName(urlBean.getUserName());
            model.setRetOrgID(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setRetailDomainCode(urlBean.getRetailDomainCode());
            model.setTxnType(AppConstants.TRANSACTION_TYPE);

            if (isFirstTime)
                viewModel.callLedgerReportApi(model);
            else
                viewModel.loadMore(model);
        }
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private String formatDate(TextView tvDate)  {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd-mm-yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("dd/mm/yyyy" );
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

    private void initScrollListener() {
        rvReport.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (listRV != null && listRV.size() > 0) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listRV.size() - 1) {
                            if (!isDataFinished) {
                                loadMore();
                                isLoading = true;
                            }
                        }
                    }
                }
            }
        });
    }

    private void loadMore() {
        try {
            listRV.add(null);
            //rvReport.post(() -> adapter.notifyItemInserted(listRV.size() - 1));
            adapter.notifyItemInserted(listRV.size() - 1);
            callLedgerReportApi(false);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadMoreAfterApiResponse(ArrayList<OlaPlayerTransactionResponseBean.ResponseData> listData) {
        try {
            if (listRV != null && listRV.size() > 0) {
                listRV.remove(listRV.size() - 1);
                if (listData != null) {
                    int scrollPosition = listRV.size();
                    //rvReport.post(() -> adapter.notifyItemRemoved(scrollPosition));
                    adapter.notifyItemRemoved(scrollPosition);
                    listRV.addAll(listData);
                }
                adapter.notifyDataSetChanged();
                isLoading = false;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            ivProceed.performClick();
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
