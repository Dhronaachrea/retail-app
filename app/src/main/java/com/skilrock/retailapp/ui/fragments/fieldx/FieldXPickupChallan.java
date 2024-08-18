package com.skilrock.retailapp.ui.fragments.fieldx;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.FieldX.FieldXPickupChallanAdapter;
import com.skilrock.retailapp.models.FieldX.FieldXChallanItems;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseHomeBean;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.fragments.scratch.PackReturnMultiScanningFragment;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.PackReturnViewModel;

import java.util.ArrayList;

public class FieldXPickupChallan extends BaseFragmentFieldx {
    private RecyclerView recyclerView;
    private Bundle bundle;
    private Activity activity;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private ArrayList<FieldXChallanItems> items = new ArrayList<>();
    private FieldXPickupChallanAdapter challanAdapter;
    private PackReturnViewModel packReturnViewModel;
    private final String FIELDX = "fieldx";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        packReturnViewModel = ViewModelProviders.of(this).get(PackReturnViewModel.class);
        packReturnViewModel.getReturnListData().observe(this, observerReturnNote);
    }

    Observer<ReturnChallanResponseHomeBean> observerReturnNote = new Observer<ReturnChallanResponseHomeBean>() {
        @Override
        public void onChanged(@Nullable ReturnChallanResponseHomeBean returnChallanResponseHomeBean) {
            ProgressBarDialog.getProgressDialog().dismiss();
            if (returnChallanResponseHomeBean == null)
                Utils.showCustomErrorDialog(master, getString(R.string.pickup_challan), getString(R.string.something_went_wrong));
            else {
                if (returnChallanResponseHomeBean.getResponseCode() == 1000) {
                    Bundle bundle = new Bundle();
                    bundle.putString("retailer", returnChallanResponseHomeBean.getRetailerName());
                    bundle.putString("title", getString(R.string.pickup_challan));
                    bundle.putBoolean("FromChallanScreen", true);
                    bundle.putParcelable("MenuBean", menuBean);
                    bundle.putParcelable("ReturnBook", returnChallanResponseHomeBean);
                    bundle.putString("ChallanNumber", returnChallanResponseHomeBean.getDlChallanNumber());
                    ((MainActivity) master).openFragment(new PackReturnMultiScanningFragment(), "PackReturnMultiScanningFragment", true, bundle);
                } else {
                    if (Utils.checkForSessionExpiry(master, returnChallanResponseHomeBean.getResponseCode())) {
                        return;
                    }

                    String errorMsg = Utils.getResponseMessage(master, FIELDX, returnChallanResponseHomeBean.getResponseCode());
                    Utils.showCustomErrorDialog(master, getString(R.string.pickup_challan), errorMsg);
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_field_xdelivery_challan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        recyclerView = view.findViewById(R.id.delivery_challan);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        tvTitle.setText(getActivity().getString(R.string.fieldx_pickup_challan));
        refreshBalance();
        bundle = getArguments();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        challanAdapter = new FieldXPickupChallanAdapter(getPickupChallans(), menuBean, master, bundle.getString("title"), packReturnViewModel);
        recyclerView.setAdapter(challanAdapter);
        challanAdapter.notifyDataSetChanged();
    }

    private ArrayList<FieldXChallanItems> getPickupChallans() {
        items.clear();
        for (FieldXChallanItems challanItems : FieldXHomeFragment.challans) {
            if (challanItems.getOrId().equalsIgnoreCase(bundle.getString("orgId")) && challanItems.getTask().equalsIgnoreCase("pickup"))
                items.add(challanItems);
        }
        return items;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFieldxRetailerData();
        challanAdapter.notifyDataSetChanged();
    }
}
