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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.SummarizedReportAdapter;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.PaymentReportRequestBean;
import com.skilrock.retailapp.models.rms.SummarizedReportResponseBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.SummarizedReportViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class SummarizedLedgerReportFragment extends BaseFragment implements View.OnClickListener {

    private SummarizedReportViewModel viewModel;
    private TextView tvStartDate, tvEndDate, tvOpeningBal, tvClosingBal, tvUserWise, tvDateWise, tvDefaultWise;
    private ImageView ivProceed;
    private RecyclerView rvReport;
    private String mType = "default";
    private LinearLayout viewType;
    private String reportType = "default";
    private LinearLayout llFromDate, llEndDate, llOpeningBal, llClosingBalance;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private SummarizedReportAdapter adapter;
    private final String RMS = "rms";
    private Animation animationBottomOut, animationBottomIn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summarized_report, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(this).get(SummarizedReportViewModel.class);
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
        llClosingBalance        = view.findViewById(R.id.view_closing_balance);
        tvUserWise              = view.findViewById(R.id.tv_user_wise);
        tvDateWise              = view.findViewById(R.id.tv_date_wise);
        tvDefaultWise           = view.findViewById(R.id.tv_default_wise);
        viewType                = view.findViewById(R.id.view_type);

        animationBottomOut = AnimationUtils.loadAnimation(master, R.anim.anim_slide_out_bottom);
        animationBottomIn = AnimationUtils.loadAnimation(master, R.anim.anim_slide_in_bottom);
        tvUserWise.setVisibility(View.GONE);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        llFromDate.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
        ivProceed.setOnClickListener(this);
        tvUserWise.setOnClickListener(this);
        tvDateWise.setOnClickListener(this);
        tvDefaultWise.setOnClickListener(this);

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

        setDefaultSelected();
      /*  rvReport.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && viewType.getVisibility() == View.VISIBLE) {
                    viewType.setAnimation(animationBottomOut);
                    viewType.setVisibility(View.GONE);
                } else if (dy < 0 && viewType.getVisibility() != View.VISIBLE) {
                    viewType.setAnimation(animationBottomIn);
                    viewType.setVisibility(View.VISIBLE);
                }
            }
        });*/

        rvReport.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && viewType.isShown()) hideViews();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) showViews();

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

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
                else Utils.openEndDateDialog(master, tvStartDate, tvEndDate);
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
                    viewModel.callSummarizedLedgerReportApi(PlayerData.getInstance().getToken(), menuBean.getBasePath() + menuBean.getRelativePath(),
                            PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId(),
                            formatDate(tvStartDate), formatDate(tvEndDate), "en", AppConstants.appType, mType);
                }
                break;
            case R.id.tv_user_wise:
                setReportType(AppConstants.USER_WISE_TYPE);
                break;
            case R.id.tv_date_wise:
                setReportType(AppConstants.DATE_WISE_TYPE);
                break;
            case R.id.tv_default_wise:
                setReportType(AppConstants.DEFAULT_WISE_TYPE);
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

    Observer<SummarizedReportResponseBean> observer = new Observer<SummarizedReportResponseBean>() {
        @Override
        public void onChanged(@Nullable SummarizedReportResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.summarize_ledger_report), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.summarize_ledger_report), errorMsg);
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.summarize_ledger_report), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0)
                if (response.getResponseData().getData().getLedgerData().isEmpty()) {
                    String errorMsg = getString(R.string.no_data_found);
                    Toast.makeText(master, errorMsg, Toast.LENGTH_SHORT).show();
                    tvOpeningBal.setText(response.getResponseData().getData().getOpeningBalance());
                    tvClosingBal.setText(response.getResponseData().getData().getClossingBalance());
                } else {
                    setAdapter(response);
                    setOpeningBalanceVisiblity(reportType.equalsIgnoreCase(AppConstants.USER_WISE_TYPE));
                }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.summarize_ledger_report), getString(R.string.something_went_wrong));
        }
    };

    private void hideViews() {
        if (viewType.getVisibility() == View.VISIBLE) {
            viewType.setVisibility(View.GONE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,
                    0,
                    0,
                    200);
            animate.setDuration(250);
            animate.setFillAfter(true);
            viewType.startAnimation(animate);
        }
    }

    private void showViews() {
        if (viewType.getVisibility() == View.GONE) {
            viewType.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,
                    0,
                    viewType.getHeight(),
                    0);
            animate.setDuration(300);
            animate.setFillAfter(true);
            viewType.startAnimation(animate);
        }
    }

    private void setAdapter(SummarizedReportResponseBean response){
        adapter = new SummarizedReportAdapter(response.getResponseData().getData().getLedgerData(), getContext(), reportType);

        tvOpeningBal.setText(response.getResponseData().getData().getOpeningBalance());
        tvClosingBal.setText(response.getResponseData().getData().getClossingBalance());
        llClosingBalance.setVisibility(response.getResponseData().getData().getClossingBalance() == null ||
                response.getResponseData().getData().getClossingBalance().isEmpty() ? View.GONE : View.VISIBLE);

        if (response.getResponseData().getData().getRawClosingBalance() != null)
            tvClosingBal.setTextColor(response.getResponseData().getData().getRawClosingBalance() < 0.0 ? master.getResources().getColor(R.color.colorAppOrange) :
                    master.getResources().getColor(R.color.colorGreen));

        if (response.getResponseData().getData().getRawOpeningBalance() != null)
            tvOpeningBal.setTextColor(response.getResponseData().getData().getRawOpeningBalance() < 0.0 ? master.getResources().getColor(R.color.colorAppOrange) :
                    master.getResources().getColor(R.color.colorGreen));

        rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReport.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReport.setItemAnimator(new DefaultItemAnimator());
        rvReport.setAdapter(adapter);
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

    private void setOpeningBalanceVisiblity(boolean visiblity) {
        llOpeningBal.setVisibility(visiblity ? View.GONE : View.VISIBLE);
        llClosingBalance.setVisibility(visiblity ? View.GONE : View.VISIBLE);
    }

    private void setReportType(String reportType_) {
        switch (reportType_) {

            case AppConstants.USER_WISE_TYPE:
                tvDateWise.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvDateWise.setTextColor(getResources().getColor(R.color.colorDarkGrey));

                tvDefaultWise.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvDefaultWise.setTextColor(getResources().getColor(R.color.colorDarkGrey));

                tvUserWise.setBackgroundColor(getResources().getColor(R.color.colorDarkGrey));
                tvUserWise.setTextColor(getResources().getColor(R.color.colorWhite));
                mType = "userwise";
                reportType = AppConstants.USER_WISE_TYPE;
                setOpeningBalanceVisiblity(true);
                break;
            case AppConstants.DATE_WISE_TYPE:
                tvUserWise.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvUserWise.setTextColor(getResources().getColor(R.color.colorDarkGrey));

                tvDefaultWise.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvDefaultWise.setTextColor(getResources().getColor(R.color.colorDarkGrey));

                tvDateWise.setBackgroundColor(getResources().getColor(R.color.colorDarkGrey));
                tvDateWise.setTextColor(getResources().getColor(R.color.colorWhite));

                mType = "datewise";
                reportType = AppConstants.DATE_WISE_TYPE;
                setOpeningBalanceVisiblity(false);
                break;
            case AppConstants.DEFAULT_WISE_TYPE:
                tvUserWise.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvUserWise.setTextColor(getResources().getColor(R.color.colorDarkGrey));

                tvDateWise.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvDateWise.setTextColor(getResources().getColor(R.color.colorDarkGrey));

                tvDefaultWise.setBackgroundColor(getResources().getColor(R.color.colorDarkGrey));
                tvDefaultWise.setTextColor(getResources().getColor(R.color.colorWhite));

                mType = "default";
                reportType = AppConstants.DEFAULT_WISE_TYPE;
                setOpeningBalanceVisiblity(false);
                break;
        }
        ivProceed.callOnClick();
    }

    private void setDefaultSelected() {
        tvUserWise.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        tvUserWise.setTextColor(getResources().getColor(R.color.colorDarkGrey));

        tvDateWise.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        tvDateWise.setTextColor(getResources().getColor(R.color.colorDarkGrey));

        tvDefaultWise.setBackgroundColor(getResources().getColor(R.color.colorDarkGrey));
        tvDefaultWise.setTextColor(getResources().getColor(R.color.colorWhite));

        mType = "default";
        reportType = AppConstants.DEFAULT_WISE_TYPE;
    }

    public abstract class MyRecyclerScroll extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}