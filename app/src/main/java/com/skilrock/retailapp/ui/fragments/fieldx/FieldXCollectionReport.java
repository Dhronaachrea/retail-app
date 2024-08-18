package com.skilrock.retailapp.ui.fragments.fieldx;

import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
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
import com.skilrock.retailapp.adapter.FieldX.FieldXCollectionReportAdapter;
import com.skilrock.retailapp.models.FieldX.FieldXCollectionReportItem;
import com.skilrock.retailapp.models.FieldX.FieldxCollectionReportResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class FieldXCollectionReport extends BaseFragmentFieldx implements View.OnClickListener {

    private TextView tvStartDate, tvEndDate;
    private RecyclerView rvReport;
    private final String RMS = "rms";
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private FieldXCollectionReportAdapter adapter;
    private ArrayList<FieldXCollectionReportItem> data = new ArrayList<>();

    Observer<FieldxCollectionReportResponseBean> observerCollectionReport = new Observer<FieldxCollectionReportResponseBean>() {
        @Override
        public void onChanged(@Nullable FieldxCollectionReportResponseBean fieldxCollectionReportResponseBean) {
            data.clear();
            ProgressBarDialog.getProgressDialog().dismiss();
            if (fieldxCollectionReportResponseBean == null) {
                Utils.showCustomErrorDialog(getContext(), getString(R.string.payment), getString(R.string.something_went_wrong));
            }
            else {
                if (fieldxCollectionReportResponseBean.getResponseCode() == 0) {
                    FieldxCollectionReportResponseBean.ResponseData respData = fieldxCollectionReportResponseBean.getResponseData();
                    if (respData.getStatusCode() == 0) {
                        ArrayList<FieldxCollectionReportResponseBean.ResponseData.Data> respDataJSONArray = respData.getData();
                        for (int i = 0; i < respDataJSONArray.size(); i++) {
                            FieldxCollectionReportResponseBean.ResponseData.Data jsonObject = respDataJSONArray.get(i);
                            FieldXCollectionReportItem item = new FieldXCollectionReportItem(jsonObject.getTxnDate().toString(), jsonObject.getOrgId() + "", jsonObject.getOrgName(), jsonObject.getOrgBalancePostTxn() + "", jsonObject.getOrgNetAmount() + "", jsonObject.getCreatedAt());
                            data.add(item);
                        }

                        adapter = new FieldXCollectionReportAdapter(sort(data), getActivity());
                        rvReport.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvReport.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        if (Utils.checkForSessionExpiry(getActivity(), respData.getStatusCode()))
                            return;

                        String errorMsg = Utils.getResponseMessage(master, RMS, respData.getStatusCode());
                        Utils.showCustomErrorDialog(getActivity(), Objects.requireNonNull(getActivity()).getString(R.string.filedx_collection), errorMsg);
                    }
                } else {
                    if (Utils.checkForSessionExpiry(getActivity(), fieldxCollectionReportResponseBean.getResponseCode()))
                        return;

                    String errorMsg = Utils.getResponseMessage(master, RMS, fieldxCollectionReportResponseBean.getResponseCode());
                    Utils.showCustomErrorDialogAndFinish(getActivity(), Objects.requireNonNull(getActivity()).getString(R.string.filedx_collection), errorMsg);
                }
            }
        }
    };

    public FieldXCollectionReport() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fieldXAllTaskViewModel.getFieldxCollectionReportResponseBeanMutableLiveData().observe(this, observerCollectionReport);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_field_xcollection_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        tvStartDate = view.findViewById(R.id.tvStartDate);
        tvEndDate = view.findViewById(R.id.tvEndDate);
        rvReport = view.findViewById(R.id.rv_report);
        ImageView ivProceed = view.findViewById(R.id.button_proceed);
        LinearLayout llFromDate = view.findViewById(R.id.containerFromDate);
        LinearLayout llEndDate = view.findViewById(R.id.containerEndDate);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
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

        tvStartDate.setText(Utils.getPreviousDate(30));
        tvEndDate.setText(Utils.getCurrentDate());
    }

    private ArrayList<FieldXCollectionReportItem> sort(ArrayList<FieldXCollectionReportItem> data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Collections.sort(data, new Comparator<FieldXCollectionReportItem>() {
            @Override
            public int compare(FieldXCollectionReportItem r1, FieldXCollectionReportItem r2) {
                try {
                    return dateFormat.parse(r2.getCreatedAt()).compareTo(dateFormat.parse(r1.getCreatedAt()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        return data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                    Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                if (validate()) {
                    ProgressBarDialog.getProgressDialog().showProgress(master);
                    fieldXAllTaskViewModel.getFieldXCollectionReport(Utils.getFieldXCollectionReport(menuBean, getActivity(), "getFieldXCollection"), formatDate(tvStartDate), formatDate(tvEndDate));
                }
                break;
        }
    }

    private String formatDate(TextView tvDate) {
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
}
