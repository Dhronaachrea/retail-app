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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
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
import com.skilrock.retailapp.adapter.rms.SaleReportAdapter;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.SaleReportResponseBean;
import com.skilrock.retailapp.models.rms.SaleWinningReportRequestBean;
import com.skilrock.retailapp.models.rms.ServiceListResponseBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.SaleWinningReportViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SaleWinningReportFragment extends BaseFragment implements View.OnClickListener {

    private SaleWinningReportViewModel viewModel;
    private TextView tvStartDate, tvEndDate;
    private ImageView ivProceed;
    private LinearLayout llNetCollection;
    private RecyclerView rvReport;
    private LinearLayout llFromDate, llEndDate;
    private TextView tvSale, tvWinnig, tvNetCollection, tvTotalComm;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private List<ServiceListResponseBean.Datum> serviceList = new ArrayList<>();
    private SaleReportAdapter adapter;
    private final String RMS = "rms";
    private String mServiceCode;
    private Spinner spinnerService;
    private String mService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_winning_report, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(this).get(SaleWinningReportViewModel.class);
            viewModel.getLiveDataSaleReport().observe(this, observer);
            viewModel.getLiveServiceList().observe(this, observerServiceList);
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
        tvStartDate             = view.findViewById(R.id.tvStartDate);
        tvEndDate               = view.findViewById(R.id.tvEndDate);
        rvReport                = view.findViewById(R.id.rv_report);
        ivProceed               = view.findViewById(R.id.button_proceed);
        llFromDate              = view.findViewById(R.id.containerFromDate);
        llEndDate               = view.findViewById(R.id.containerEndDate);
        spinnerService          = view.findViewById(R.id.spinnerUser);
        tvTotalComm             = view.findViewById(R.id.tvTotalComm);
        llNetCollection         = view.findViewById(R.id.llNetCollection);
        tvSale                  = view.findViewById(R.id.tvSale);
        tvWinnig                = view.findViewById(R.id.tvWinning);
        tvNetCollection         = view.findViewById(R.id.tvNetCollection);

        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername     = view.findViewById(R.id.tvUserName);
        tvUserBalance  = view.findViewById(R.id.tvUserBalance);
        llNetCollection.setVisibility(View.GONE);

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

        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mServiceCode = serviceList.get(position).getServiceCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tvStartDate.setText(Utils.getPreviousDate(30));
        tvEndDate.setText(Utils.getCurrentDate());

        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "getServiceList");
        ProgressBarDialog.getProgressDialog().showProgress(master);

        viewModel.callGetServiceListApi(PlayerData.getInstance().getToken(), url);

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
                if (validate()) {
                    if (adapter != null) adapter.clear();

                    ProgressBarDialog.getProgressDialog().showProgress(master);
                    SaleWinningReportRequestBean model = new SaleWinningReportRequestBean();
                    model.setEndDate(formatDate(tvEndDate));
                    model.setStartDate(formatDate(tvStartDate));
                    model.setOrgTypeCode(AppConstants.ORG_TYPE_CODE);
                    model.setAppType(AppConstants.appType);
                    model.setLanguageCode(SharedPrefUtil.getLanguage(master));
                    model.setOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
                    model.setServiceCode(mServiceCode);

                    String url = Utils.getRmsUrlDetails(menuBean, getContext(), "getSaleReport");
                    viewModel.callSaleReportApi(PlayerData.getInstance().getToken(), url, model);
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

    Observer<ServiceListResponseBean> observerServiceList = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null)
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.sale_report), getString(R.string.something_went_wrong), 1, getFragmentManager());
        else if (response.getResponseCode() != 0) {
            //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
            String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.sale_report), errorMsg, 1, getFragmentManager());
        }
        else if(response.getResponseData().getStatusCode() != 0) {
            if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                return;
            //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
            String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.sale_report), errorMsg, 1, getFragmentManager());
        } else if (response.getResponseData().getStatusCode() == 0) {
            serviceList = response.getResponseData().getData();
            setServiceListAdapter(response.getResponseData().getData());
        } else
            Utils.showCustomErrorDialog(getContext(), getString(R.string.sale_report), getString(R.string.something_went_wrong));
    };


    Observer<SaleReportResponseBean> observer = new Observer<SaleReportResponseBean>() {
        @Override
        public void onChanged(@Nullable SaleReportResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();
            llNetCollection.setVisibility(View.GONE);

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.sale_report), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.sale_report), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                llNetCollection.setVisibility(View.GONE);
                String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.sale_report), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0) {
                if (response.getResponseData().getData() == null || response.getResponseData().getData().getTransactionData() == null)
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.sale_report), getString(R.string.no_data_found));
                else if (response.getResponseData().getData().getTransactionData().size() < 1) {
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.sale_report), getString(R.string.no_data_found));
                } else {
                    setReportAdapter(response);
                }
            }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ledger_report), getString(R.string.something_went_wrong));
        }
    };

    private void setReportAdapter(SaleReportResponseBean responseBean){
        adapter = new SaleReportAdapter(responseBean.getResponseData().getData().getTransactionData(), getContext());

        if(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            rvReport.setLayoutManager(new GridLayoutManager(getContext(), 2));
        else
            rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReport.setItemAnimator(new DefaultItemAnimator());
        rvReport.setAdapter(adapter);

        llNetCollection.setVisibility(View.VISIBLE);
        tvSale.setText(responseBean.getResponseData().getData().getTotal().getSumOfSale());
        tvWinnig.setText(responseBean.getResponseData().getData().getTotal().getSumOfWinning());
        tvNetCollection.setText(responseBean.getResponseData().getData().getTotal().getNetSale());
        tvTotalComm.setText(responseBean.getResponseData().getData().getTotal().getNetCommision());

        if (responseBean.getResponseData().getData().getTotal().getRawNetSale() != null)
            tvNetCollection.setTextColor(Double.parseDouble(responseBean.getResponseData().getData().getTotal().getRawNetSale()) < 0.0 ? master.getResources().getColor(R.color.colorAppOrange) :
                    master.getResources().getColor(R.color.colorGreen));
    }

    private void setServiceListAdapter(List<ServiceListResponseBean.Datum> list) {
        List<String> listSpinner = new ArrayList<>();

        for (ServiceListResponseBean.Datum data : list) {
            listSpinner.add(data.getServiceName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerService.setAdapter(adapter);
        spinnerService.setTextDirection(View.TEXT_ALIGNMENT_VIEW_START);
        spinnerService.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        if (listSpinner.size() == 1) {
            spinnerService.setBackgroundResource(0);
            spinnerService.setClickable(false);
            spinnerService.setEnabled(false);
        }
    }

    private String formatDate(TextView tvDate)  {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
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
}