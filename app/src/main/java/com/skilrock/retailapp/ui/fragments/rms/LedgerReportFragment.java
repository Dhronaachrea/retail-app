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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.LedgerReportAdapter;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LedgerReportResponseBean;
import com.skilrock.retailapp.models.rms.PaymentReportRequestBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.LedgerReportViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class LedgerReportFragment extends BaseFragment implements View.OnClickListener {

    private LedgerReportViewModel viewModel;
    private TextView tvStartDate, tvEndDate, tvOpeningBal, tvClosingBal;
    private ImageView ivProceed;
    private RecyclerView rvReport;
    private LinearLayout llFromDate, llEndDate, llOpeningBal;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private LedgerReportAdapter adapter;
    private final String RMS = "rms";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI) ? R.layout.fragment_ledger_report_landscape : R.layout.fragment_ledger_report, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            viewModel = ViewModelProviders.of(this).get(LedgerReportViewModel.class);
            viewModel.getLiveDataPaymentReport().observe(this, observer);
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
        tvOpeningBal            = view.findViewById(R.id.tv_opening_balance);
        tvClosingBal            = view.findViewById(R.id.tv_closing_balance);
        llOpeningBal            = view.findViewById(R.id.view_opening_balance);

        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        llFromDate.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
        ivProceed.setOnClickListener(this);
        llOpeningBal.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        tvStartDate.setText(Utils.getPreviousDate(7));
        tvEndDate.setText(Utils.getCurrentDate());

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
                    PaymentReportRequestBean model = new PaymentReportRequestBean();
                    model.setEndDate(formatDate(tvEndDate));
                    model.setOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
                    model.setStartDate(formatDate(tvStartDate));
                    model.setOrgTypeCode(AppConstants.ORG_TYPE_CODE);
                    viewModel.callLedgerReportApi(PlayerData.getInstance().getToken(), menuBean.getBasePath() + menuBean.getRelativePath(),
                            PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId(),
                            formatDate(tvStartDate), formatDate(tvEndDate), "en", AppConstants.appType);
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

        /*try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy"); //"yyyy-mm-dd"

            Date date1 = format.parse(tvStartDate.getText().toString().trim());
            Date date2 = format.parse(tvEndDate.getText().toString().trim());

            if (date2.compareTo(date1) < 0) {
                Toast.makeText(master, getString(R.string.from_date_greater_than_to_date), Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }*/

        return true;
    }

    Observer<LedgerReportResponseBean> observer = new Observer<LedgerReportResponseBean>() {
        @Override
        public void onChanged(@Nullable LedgerReportResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ledger_report), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ledger_report), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ledger_report), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0) {
                if (response.getResponseData().getData() == null || response.getResponseData().getData().getTransaction() == null)
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.ledger_report), getString(R.string.no_data_found));
                else if (response.getResponseData().getData().getTransaction().size() < 1) {
                    tvOpeningBal.setText(response.getResponseData().getData().getBalance().getOpeningBalance());
                    tvClosingBal.setText(response.getResponseData().getData().getBalance().getClosingBalance());

                    if (response.getResponseData().getData().getBalance().getRawClosingBalance() != null)
                        tvClosingBal.setTextColor(response.getResponseData().getData().getBalance().getRawClosingBalance() < 0.0 ? master.getResources().getColor(R.color.colorAppOrange) :
                                master.getResources().getColor(R.color.colorGreen));

                    if (response.getResponseData().getData().getBalance().getRawOpeningBalance() != null)
                        tvOpeningBal.setTextColor(response.getResponseData().getData().getBalance().getRawOpeningBalance() < 0.0 ? master.getResources().getColor(R.color.colorAppOrange) :
                                master.getResources().getColor(R.color.colorGreen));

                    llOpeningBal.setVisibility(View.VISIBLE);

                    Utils.showCustomErrorDialog(getContext(), getString(R.string.ledger_report), getString(R.string.no_data_found));
                } else {
                    adapter = new LedgerReportAdapter(response.getResponseData().getData().getTransaction(), getContext());

                    tvOpeningBal.setText(response.getResponseData().getData().getBalance().getOpeningBalance());
                    tvClosingBal.setText(response.getResponseData().getData().getBalance().getClosingBalance());

                    if (response.getResponseData().getData().getBalance().getRawClosingBalance() != null)
                        tvClosingBal.setTextColor(response.getResponseData().getData().getBalance().getRawClosingBalance() < 0.0 ? master.getResources().getColor(R.color.colorAppOrange) :
                                master.getResources().getColor(R.color.colorGreen));

                    if (response.getResponseData().getData().getBalance().getRawOpeningBalance() != null)
                        tvOpeningBal.setTextColor(response.getResponseData().getData().getBalance().getRawOpeningBalance() < 0.0 ? master.getResources().getColor(R.color.colorAppOrange) :
                                master.getResources().getColor(R.color.colorGreen));

                    if(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
                        rvReport.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    else
                        rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvReport.setItemAnimator(new DefaultItemAnimator());
                    rvReport.setAdapter(adapter);
                    llOpeningBal.setVisibility(View.VISIBLE);
                }
            }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ledger_report), getString(R.string.something_went_wrong));
        }
    };

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