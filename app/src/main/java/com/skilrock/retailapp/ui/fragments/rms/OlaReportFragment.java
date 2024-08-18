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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.skilrock.retailapp.adapter.rms.OlaReportAdapter;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.OlaReportResponseBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.OlaReportViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class OlaReportFragment extends BaseFragment implements View.OnClickListener {

    private OlaReportViewModel viewModel;
    private TextView tvStartDate, tvEndDate;
    private RecyclerView rvReport;
    private LinearLayout llNetCollection;
    private TextView tvDeposit, tvWithdraw, tvNetCollection;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private OlaReportAdapter adapter;
    private final String RMS = "rms";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_ola, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            viewModel = ViewModelProviders.of(this).get(OlaReportViewModel.class);
            viewModel.getLiveData().observe(this, observer);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("log", "File Name: OlaReportFragment.java");
        initializeWidgets(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        tvStartDate             = view.findViewById(R.id.tvStartDate);
        tvEndDate               = view.findViewById(R.id.tvEndDate);
        rvReport                = view.findViewById(R.id.rv_report);
        llNetCollection         = view.findViewById(R.id.llNetCollection);
        tvDeposit               = view.findViewById(R.id.tvDeposit);
        tvWithdraw              = view.findViewById(R.id.tvWithdraw);
        tvNetCollection         = view.findViewById(R.id.tvNetCollection);
        ImageView ivProceed     = view.findViewById(R.id.button_proceed);
        LinearLayout llFromDate = view.findViewById(R.id.containerFromDate);
        LinearLayout llEndDate  = view.findViewById(R.id.containerEndDate);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
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

        tvStartDate.setText(Utils.getPreviousDate(1));
        tvEndDate.setText(Utils.getCurrentDate());
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
                    viewModel.olaReportApi(menuBean.getBasePath() + menuBean.getRelativePath(), formatDate(tvStartDate), formatDate(tvEndDate));
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
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

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

    Observer<OlaReportResponseBean> observer = new Observer<OlaReportResponseBean>() {
        @Override
        public void onChanged(@Nullable OlaReportResponseBean response) {
            llNetCollection.setVisibility(View.GONE);
            ProgressBarDialog.getProgressDialog().dismiss();
            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.deposit_withdrawal), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.deposit_withdrawal), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.deposit_withdrawal), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                ArrayList<OlaReportResponseBean.ResponseData.Data.Transaction> transaction = response.getResponseData().getData().getTransaction();
                if (transaction == null)
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.deposit_withdrawal), getString(R.string.no_data_found));
                else if (transaction.size() < 1)
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.deposit_withdrawal), getString(R.string.no_data_found));
                else {
                    OlaReportResponseBean.ResponseData.Data.Total total = response.getResponseData().getData().getTotal();
                    llNetCollection.setVisibility(View.VISIBLE);
                    /*tvDeposit.setText(String.format("%s%s", getString(R.string.dollar), total.getDEPOSIT()));
                    tvWithdraw.setText(String.format("%s%s", getString(R.string.dollar), total.getWITHDRAWAL()));
                    tvNetCollection.setText(String.format("%s%s", getString(R.string.dollar), total.getDEPOSIT() - total.getWITHDRAWAL()));*/
                    tvDeposit.setText(total.getFinalDeposit());
                    tvWithdraw.setText(total.getWithdrawal());
                    tvNetCollection.setText(total.getNetCollection());
                    if (total.getRawNetCollection() > 0)
                        tvNetCollection.setTextColor(ContextCompat.getColor(master, R.color.colorPaid));
                    else if (total.getRawNetCollection() < 0)
                        tvNetCollection.setTextColor(ContextCompat.getColor(master, R.color.colorDue));
                    else
                        tvNetCollection.setTextColor(ContextCompat.getColor(master, R.color.colorDarkGrey));
                    adapter = new OlaReportAdapter(getContext(), transaction);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    rvReport.setLayoutManager(mLayoutManager);
                    rvReport.setItemAnimator(new DefaultItemAnimator());
                    rvReport.setAdapter(adapter);
                }
            }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.deposit_withdrawal), getString(R.string.something_went_wrong));
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