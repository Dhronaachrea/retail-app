package com.skilrock.retailapp.ui.fragments.ola;

import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import androidx.annotation.RequiresApi;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.ola.OlaNetGamingAdapter;
import com.skilrock.retailapp.adapter.ola.OlaPendingTransactionAdapter;
import com.skilrock.retailapp.dialog.DateSelectionDialog;
import com.skilrock.retailapp.models.OlaNetGamingExecutionResponseBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.models.ola.OlaNetGamingResponseBean;
import com.skilrock.retailapp.models.ola.OlaPendingTransactionResponseBean;
import com.skilrock.retailapp.models.ola.OlaPlayerTransactionReportRequestBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.ola.OlaNetGamingViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class OlaNetGamingReportFragment extends BaseFragment implements View.OnClickListener, OlaPendingTransactionAdapter.IOnSettleListener, DateSelectionDialog.OnDateChange {

    private OlaNetGamingViewModel viewModel;
    private TextView tvStartDate, tvEndDate, tvRange, tentative_text;
    private ImageView ivProceed;
    private RecyclerView rvReport;
    private LinearLayout llFromDate, llEndDate, dates, select_range, total_ngr;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private int PAGE_INDEX = 1;
    private boolean isLoading = false;
    private int settledItemPos = 0;
    private ArrayList<OlaNetGamingResponseBean.TxnList> listRV = new ArrayList<>();
    private OlaNetGamingAdapter adapter;
    private boolean isDataFinished = false;
    private TextInputLayout tilMobile;
    private LinearLayout llNetCollection;
    private TextView tvTotalWager, tvTotalNgr, tvTotalWinning;
    private DateSelectionDialog dateSelectionDialog;
    private ArrayList<OlaNetGamingExecutionResponseBean.ResponseDatum> LIST_DATE_RANGE =new ArrayList<>() ;
    OlaNetGamingExecutionResponseBean.ResponseDatum data = null;
    int selectedPos = -1;
    private int noOfMonths = 0;
    boolean isEntry = false;
    boolean isLast = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ola_net_gaming, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(OlaNetGamingViewModel.class);
        viewModel.getLiveData().observe(this, observer);
        viewModel.getLiveDataDataExecution().observe(this, observerDateRange);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
        initScrollListener();
        callExecutionDate();
    }

    private void initializeWidgets(View view) {
        tvRange     = view.findViewById(R.id.tvRange);
        tvStartDate = view.findViewById(R.id.tvStartDate);
        tvEndDate = view.findViewById(R.id.tvEndDate);
        rvReport = view.findViewById(R.id.rv_report);
        ivProceed = view.findViewById(R.id.button_proceed);
        llFromDate = view.findViewById(R.id.containerFromDate);
        llEndDate = view.findViewById(R.id.containerEndDate);
        tilMobile = view.findViewById(R.id.til_mobile);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        tvTotalWager = view.findViewById(R.id.tvTotalWager);
        tvTotalNgr = view.findViewById(R.id.tvTotalNgr);
        tvTotalWinning = view.findViewById(R.id.tvTotalWinning);
        dates          = view.findViewById(R.id.dates);
        llNetCollection = view.findViewById(R.id.llNetCollection);
        llNetCollection.setVisibility(View.GONE);
        select_range = view.findViewById(R.id.select_range);
        tentative_text = view.findViewById(R.id.tentative_text);
        total_ngr      = view.findViewById(R.id.total_ngr);
        refreshBalance();
        listRV.clear();

        llFromDate.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
        ivProceed.setOnClickListener(this);
        select_range.setOnClickListener(this);
        tvStartDate.setText(Utils.getPreviousDate(7));
        tvEndDate.setText(Utils.getCurrentDate());
        tvRange.setText(getActivity().getText(R.string.select_net_gaming_range));
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
            noOfMonths = Utils.getNoOfMonths(menuBean,getActivity());
            Log.d("Months",""+noOfMonths);
            String mobileNumber = bundle.getString("MobileNumber");
            if (mobileNumber != null) {
                callNetGamingReport(true);
            }
        }

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
                    callNetGamingReport(true);
                }
                break;
            case R.id.select_range:
                    dateSelectionDialog = new DateSelectionDialog(getActivity(), LIST_DATE_RANGE,this);
                    dateSelectionDialog.show();
                if (dateSelectionDialog.getWindow() != null) {
                    dateSelectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dateSelectionDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                    break;


        }

    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));

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

    private void callExecutionDate() {
        ProgressBarDialog.getProgressDialog().showProgress(master);
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "netGamingRangeDetails");
        if (urlBean != null) {
            viewModel.callGetNetGamingExecutionDates(urlBean,noOfMonths);
        }

    }
    Observer<OlaNetGamingResponseBean> observer = new Observer<OlaNetGamingResponseBean>() {
        @Override
        public void onChanged(@Nullable OlaNetGamingResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();
            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.net_gaming_details), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                if (response.getResponseData() != null && response.getResponseData().getTxnList().size() < 1) {
                    if (adapter != null)
                        adapter.clear();
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.net_gaming_details), getString(R.string.no_data_found));
                } else {
                    PAGE_INDEX = 1;
                    isDataFinished = false;
                    ArrayList<OlaNetGamingResponseBean.TxnList> list = response.getResponseData().getTxnList();
                    //Log.w("log", "List Size: " + list.size());
                    if (isLast){
                        total_ngr.setVisibility(View.GONE);
                        tentative_text.setVisibility(View.VISIBLE);
                    }else {
                        total_ngr.setVisibility(View.VISIBLE);
                    }
                    llNetCollection.setVisibility(View.VISIBLE);
                    if (list != null) {
                        listRV.clear();
                        if (list.size() < AppConstants.PAGE_SIZE)
                            isDataFinished = true;
                        listRV.addAll(list);
                        llNetCollection.setVisibility(View.VISIBLE);
                        Double formatWager = Double.valueOf(response.getResponseData().getTotalWager());
                        tvTotalWager.setText(Utils.getFormattedCash(formatWager));
                        Double total_ngr = Double.valueOf(response.getResponseData().getTotalNGR());
                        if (total_ngr > 0.00){
                            tvTotalNgr.setText(Utils.getFormattedCash(total_ngr));
                        }else{
                            tvTotalNgr.setText("0.00");
                        }
                        Double formatWinning = Double.valueOf(response.getResponseData().getTotalWinning());
                        tvTotalWinning.setText(Utils.getFormattedCash(formatWinning));

                        setAdapter(isLast);
                    }
                }
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode())) return;

                if (adapter != null)
                    adapter.clear();
                    rvReport.setAdapter(null);
                llNetCollection.setVisibility(View.GONE);

                String errorMsg = Utils.getResponseMessage(master, "ola", response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.net_gaming_details), errorMsg);
            }
        }
    };



    Observer<OlaNetGamingExecutionResponseBean> observerDateRange = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(getContext(), getString(R.string.net_gaming_details), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            if (response.getResponseData()!=null && response.getResponseData().size() == 0){
                dates.setVisibility(View.VISIBLE);
            }else {
                select_range.setVisibility(View.VISIBLE);
                showDateSpinner(response);
            }
            }
         else {
            if (Utils.checkForSessionExpiry(master, response.getResponseCode())) return;

            String errorMsg = Utils.getResponseMessage(master, "ola", response.getResponseCode());
            Utils.showCustomErrorDialog(getContext(), getString(R.string.net_gaming_details), errorMsg);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDateSpinner(OlaNetGamingExecutionResponseBean response) {
        String fromdate = "";
        String description = "";
        for (OlaNetGamingExecutionResponseBean.ResponseDatum responseDatum : response.getResponseData()) {
            LIST_DATE_RANGE.add(responseDatum);
            if (responseDatum.getLast()) {
               isEntry = true;
               fromdate = String.format("%s",LocalDate.parse(responseDatum.getToDate()).plusDays(1));
               description = getDescription(String.format("%s",LocalDate.parse(responseDatum.getToDate()).plusDays(1))) + "  -  " + getDescription(Utils.getCurrentDateOla());
            }
        }
        if (isEntry){
            OlaNetGamingExecutionResponseBean.ResponseDatum responseDatum =new OlaNetGamingExecutionResponseBean.ResponseDatum();
            responseDatum.setDescription(description);
            responseDatum.setFromDate(fromdate);
            responseDatum.setToDate(Utils.getCurrentDateOla());
            responseDatum.setLast(false);
            responseDatum.setTentative(true);
            LIST_DATE_RANGE.add(responseDatum);
        }
    }
     String getDescription(String tDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMMM",Locale.ENGLISH);
        Date date = null;
        try {
            date = originalFormat.parse(tDate);
            Log.d("log", "Old Format: " + originalFormat.format(date));
            Log.d("log", "New Format: " + targetFormat.format(date));

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return targetFormat.format(date).toUpperCase();
    }

    private void setAdapter(boolean isLast) {
        adapter = new OlaNetGamingAdapter(getContext(), listRV, isLast);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            rvReport.setLayoutManager(new GridLayoutManager(getContext(), 2));
        else
            rvReport.setLayoutManager(new LinearLayoutManager(getContext()));

        rvReport.setItemAnimator(new DefaultItemAnimator());
        rvReport.setAdapter(adapter);
    }

    private void callNetGamingReport(boolean isFirstTime) {
        if (adapter != null) {
            //rvReport.post(() -> adapter.clear());
            //adapter.clear();
        }
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "netGamingDetails");
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
            model.setPageSize(100);
            model.setPageIndex(isFirstTime ? 1 : 1);

            model.setPlayerDomainCode(domain);
            //model.setPlayerUserName(getText(etMobile));
            model.setUserName(urlBean.getUserName());
            model.setRetOrgID(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setRetailDomainCode(urlBean.getRetailDomainCode());

            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                model.setPlrDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_OLA_MYANMAR")||
                        BuildConfig.FLAVOR.equalsIgnoreCase("pROD_OLA_MYANMAR")
                        ? "www.bet2winasia.com" : "www.igamew.com");
            else
                model.setPlrDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")
                        ? "www.camwinlotto.cm" : "www.camwinlotto.cm");

            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                model.setPlayerDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_OLA_MYANMAR")||
                        BuildConfig.FLAVOR.equalsIgnoreCase("pROD_OLA_MYANMAR")
                        ? "www.bet2winasia.com" : "www.igamew.com");
            else
                model.setPlayerDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")
                        ? "www.camwinlotto.cm" : "www.camwinlotto.cm");
            viewModel.callGetNetGamingReport(model);
        }
    }

    private void callNetGamingReportRange(int pos, Boolean last) {
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "netGamingDetails");
        if (urlBean != null) {

            ProgressBarDialog.getProgressDialog().showProgress(master);

            String domain = PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgCode() + "_" +
                    PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName();
            OlaPlayerTransactionReportRequestBean model = new OlaPlayerTransactionReportRequestBean();
            model.setAccept(urlBean.getAccept());
            model.setContentType(urlBean.getContentType());
            model.setFromDate(formatDateRange(LIST_DATE_RANGE.get(pos).getFromDate()));
            Log.d("LastDate",Utils.getCurrentDateOla());
            model.setToDate(formatDateRange(LIST_DATE_RANGE.get(pos).getToDate()));
            model.setUrl(urlBean.getUrl());
            model.setPassword(urlBean.getPassword());
            model.setPageSize(100);
            model.setPageIndex(1);

            model.setPlayerDomainCode(domain);
            //model.setPlayerUserName(getText(etMobile));
            model.setUserName(urlBean.getUserName());
            model.setRetOrgID(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setRetailDomainCode(urlBean.getRetailDomainCode());

            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                model.setPlrDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_OLA_MYANMAR") ||
                        BuildConfig.FLAVOR.equalsIgnoreCase("pROD_OLA_MYANMAR")
                        ? "www.bet2winasia.com" : "www.igamew.com");
            else
                model.setPlrDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")
                        ? "www.camwinlotto.cm" : "www.camwinlotto.cm");

            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                model.setPlayerDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_OLA_MYANMAR") ||
                        BuildConfig.FLAVOR.equalsIgnoreCase("pROD_OLA_MYANMAR")
                        ? "www.bet2winasia.com" : "www.igamew.com");
            else
                model.setPlayerDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")
                        ? "www.camwinlotto.cm" : "www.camwinlotto.cm");
            viewModel.callGetNetGamingReport(model);
        }
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private String formatDate(TextView tvDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd-mm-yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("dd/mm/yyyy");
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

    private String formatDateRange(String Date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-mm-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("dd/mm/yyyy");
        Date date = null;
        try {
            date = originalFormat.parse(Date);
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
            callNetGamingReport(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMoreAfterApiResponse(ArrayList<OlaPendingTransactionResponseBean.ResponseDatum> listData) {
  /*      try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
    public void onSettle(int postion, int txnId) {

    }

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        super.onDestroy();
    }

    @Override
    public void onDateSelect(int position) {
        if (position != -1) {
            if (LIST_DATE_RANGE.get(position).isTentative()){
                isLast = true;
            }else{
                tentative_text.setVisibility(View.GONE);
                isLast = false;
            }
            tvRange.setText(LIST_DATE_RANGE.get(position).getDescription());
            callNetGamingReportRange(position,LIST_DATE_RANGE.get(position).getLast());
        }
    }
}
