package com.skilrock.retailapp.ui.fragments.fieldx;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.FieldX.AllRetailerAdapter;
import com.skilrock.retailapp.models.FieldX.AllTaskItem;
import com.skilrock.retailapp.models.FieldX.FieldXCollectionReportItem;
import com.skilrock.retailapp.models.FieldX.FieldxRetailerResponseBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.FieldXViewModel.FieldXAllRetailerViewModel;

import java.util.ArrayList;

public class FieldXAllRetailerFragment extends BaseFragmentFieldx implements View.OnClickListener {

    private RecyclerView rvReport;
    private final String RMS = "rms";
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private AllRetailerAdapter adapter;
    private ArrayList<FieldXCollectionReportItem> data = new ArrayList<>();
    private FieldXAllRetailerViewModel fieldXAllRetailerViewModel;
    private  ArrayList<AllTaskItem> allRetailer = new ArrayList<>();


    public FieldXAllRetailerFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fieldXAllRetailerViewModel = ViewModelProviders.of(this).get(FieldXAllRetailerViewModel.class);

        fieldXAllRetailerViewModel.getFieldXRetailer().observe(this, observerRetailer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_retailers_fieldx, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        rvReport = view.findViewById(R.id.rv_retailers);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
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

        getRetailerList();
    }

    private void setRetailerAdapter() {
        adapter = new AllRetailerAdapter(allRetailer, getActivity());
        rvReport.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvReport.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    Observer<FieldxRetailerResponseBean> observerRetailer = new Observer<FieldxRetailerResponseBean>() {
        @Override
        public void onChanged(@Nullable FieldxRetailerResponseBean fieldXRetailerBean) {
            allTask.clear();
            ProgressBarDialog.getProgressDialog().dismiss();
            if (fieldXRetailerBean == null)
                Utils.showCustomErrorDialog(master, "", getString(R.string.something_went_wrong));
            else {
                if (fieldXRetailerBean.getResponseCode() == 0) {
                    FieldxRetailerResponseBean.ResponseData responseData = fieldXRetailerBean.getResponseData();
                    if (responseData.getStatusCode() == 0) {
                        FieldxRetailerResponseBean.ResponseData.Data data = responseData.getData();
                        ArrayList<FieldxRetailerResponseBean.ResponseData.Data.AssignOrg> org = data.getAssignOrg();
                        for (int i = 0; i < org.size(); i++) {
                            FieldxRetailerResponseBean.ResponseData.Data.AssignOrg jsonObject = org.get(i);
                            AllTaskItem allTaskItem = new AllTaskItem(jsonObject.getOrgId(), jsonObject.getOrgName(),
                                    jsonObject.getCountry() + ", " + jsonObject.getState(), jsonObject.getBalance(),
                                    1,
                                    1,
                                    1,
                                    jsonObject.getCountry() + ", " + jsonObject.getState() + ", " + jsonObject.getCity(), jsonObject.getOrgTypeCode());

                            allRetailer.add(allTaskItem);
                            setRetailerAdapter();
                        }
                    } else {
                        if (Utils.checkForSessionExpiry(getActivity(), responseData.getStatusCode()))
                            return;

                        String errorMsg = Utils.getResponseMessage(master, RMS, responseData.getStatusCode());
                        Utils.showCustomErrorDialogAndLogout(getActivity(), getString(R.string.fieldx_retailer), errorMsg, true);
                    }
                } else {
                    if (Utils.checkForSessionExpiry(master, fieldXRetailerBean.getResponseCode()))
                        return;

                    String errorMsg = Utils.getResponseMessage(master, RMS, fieldXRetailerBean.getResponseCode());
                    Utils.showCustomErrorDialogAndLogout(getContext(), getActivity().getString(R.string.fieldx_retailer), errorMsg, true);
                }
            }
        }
    };

    public void getRetailerList() {
        ProgressBarDialog.getProgressDialog().showProgress(master);
        try {
            if (!Utils.isNetworkConnected(master)) {
                ProgressBarDialog.getProgressDialog().dismiss();
                Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return;
            }
            UrlBean urlBean = Utils.getFieldxRetailerUrlDetails(getMenuBeanFieldx(), "getRetList");
            fieldXAllRetailerViewModel.getRetailerApi(urlBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
