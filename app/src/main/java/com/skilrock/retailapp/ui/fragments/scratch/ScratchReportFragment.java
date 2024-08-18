package com.skilrock.retailapp.ui.fragments.scratch;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.ScratchReportAdapter;
import com.skilrock.retailapp.dialog.ScratchReportDialog;
import com.skilrock.retailapp.interfaces.ScratchInventoryReportListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.ScratchReportBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.ScratchReportViewModel;

import java.util.ArrayList;

public class ScratchReportFragment extends BaseFragment {

    private ScratchReportViewModel viewModel;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private RecyclerView recyclerView;
    private final String SCRATCH = "scratch";
    private ScratchReportAdapter challanAdapter;
    private ScratchReportDialog scratchReportDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scratch_report, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(ScratchReportViewModel.class);
        viewModel.getLiveDataResponse().observe(this, observer);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        recyclerView                = view.findViewById(R.id.rv_report);
        TextView tvTitle            = view.findViewById(R.id.tvTitle);
        tvUsername                  = view.findViewById(R.id.tvUserName);
        tvUserBalance               = view.findViewById(R.id.tvUserBalance);

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

        callReportApi();
    }

    private void callReportApi() {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlBean urlBean = Utils.getUrlDetails(menuBean, getContext(), "inventoryReport");

        if (urlBean != null) {
            allowBackAction(false);
            ProgressBarDialog.getProgressDialog().showProgress(master);
            viewModel.callReportApi(urlBean);
        }
    }

    Observer<ScratchReportBean> observer = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        allowBackAction(true);

        if (response == null)
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.report_error), getString(R.string.something_went_wrong), 1, getFragmentManager());
        else if (response.getResponseCode() == 1000) {
            ArrayList<ScratchReportBean.GameWiseBookDetailList> gameWiseBookDetailList = response.getGameWiseBookDetailList();

            if (gameWiseBookDetailList == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.report_error), getString(R.string.no_data_found), 1, getFragmentManager());
            else if (gameWiseBookDetailList.size() < 1)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.report_error), getString(R.string.no_data_found), 1, getFragmentManager());
            else {
                ScratchInventoryReportListener listener = this::onCardClick;
                challanAdapter = new ScratchReportAdapter(getContext(), gameWiseBookDetailList, listener);
                if(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
                    recyclerView.setLayoutManager(new GridLayoutManager(master, 3));
                else
                    recyclerView.setLayoutManager(new GridLayoutManager(master, 2));

                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(challanAdapter);
            }
        }
        else {
            if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                return;

            String errorMsg = Utils.getResponseMessage(master, SCRATCH, response.getResponseCode());
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.report_error), errorMsg, 1, getFragmentManager());
        }
    };

    private void onCardClick(ArrayList<String> listInTransit, ArrayList<String> listReceived, ArrayList<String> listActivated, ArrayList<String> listInvoiced) {
        scratchReportDialog = new ScratchReportDialog(master, listInTransit, listReceived, listActivated, listInvoiced);
        scratchReportDialog.show();
        if (scratchReportDialog.getWindow() != null) {
            scratchReportDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            scratchReportDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        scratchReportDialog.setOnDismissListener(arg0 -> {if (challanAdapter != null) challanAdapter.setCardClickableTrue();});
    }

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        if (scratchReportDialog != null && scratchReportDialog.isShowing())
            scratchReportDialog.dismiss();
        super.onDestroy();
    }
}
