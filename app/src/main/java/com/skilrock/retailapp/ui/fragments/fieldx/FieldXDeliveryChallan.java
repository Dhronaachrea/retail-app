package com.skilrock.retailapp.ui.fragments.fieldx;


import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.FieldX.FieldXChallanAdapter;
import com.skilrock.retailapp.models.FieldX.FieldXChallanItems;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.ChallanResponseBean;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.fragments.scratch.ChallanFragment;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.ReceiveBookViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FieldXDeliveryChallan extends BaseFragmentFieldx {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private FieldXChallanAdapter challanAdapter;
    private ArrayList<FieldXChallanItems> items = new ArrayList<>();
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private Bundle bundle;
    private Activity activity;
    TextView tvTitle;
    private ReceiveBookViewModel viewModel;
    private final String FIELDX = "fieldx";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(ReceiveBookViewModel.class);
        viewModel.getLiveDataChallan().observe(this, observerDlDetail);
    }

    Observer<ChallanResponseBean> observerDlDetail = new Observer<ChallanResponseBean>() {
        @Override
        public void onChanged(@Nullable ChallanResponseBean challanResponseBean) {
            ProgressBarDialog.getProgressDialog().dismiss();
            if (challanResponseBean == null)
                Utils.showCustomErrorDialog(master, getString(R.string.delivery_challan), getString(R.string.something_went_wrong));
            else {
                if (challanResponseBean.getResponseCode() == 1000) {
                    if (challanResponseBean.getGameWiseDetails().size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("MenuBean", menuBean);
                        bundle.putParcelable("ChallanResponseBean", challanResponseBean);
                        bundle.putString("ChallanNumber", challanResponseBean.getDlChallanNumber());
                        bundle.putString("title", tvTitle.getText().toString());
                        ((MainActivity) master).openFragment(new ChallanFragment(), "ChallanFragment", true, bundle);
                    } else
                        Toast.makeText(master, activity.getString(R.string.no_data_found_for_this_challan), Toast.LENGTH_LONG).show();
                } else {
                    if (Utils.checkForSessionExpiry(master, challanResponseBean.getResponseCode())) {
                        return;
                    }

                    String errorMsg = Utils.getResponseMessage(master, FIELDX, challanResponseBean.getResponseCode());
                    Utils.showCustomErrorDialog(master, activity.getString(R.string.delivery_challan), errorMsg);
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_field_xdelivery_challan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        recyclerView = view.findViewById(R.id.delivery_challan);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        tvTitle.setText(getActivity().getString(R.string.fieldx_delivery_challan));
        refreshBalance();
        bundle=getArguments();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                //tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        challanAdapter = new FieldXChallanAdapter(getDeliveryChallans(), menuBean, master, tvTitle.getText().toString(), viewModel);
        recyclerView.setAdapter(challanAdapter);
        challanAdapter.notifyDataSetChanged();
    }

    private ArrayList<FieldXChallanItems> getDeliveryChallans() {
        items.clear();
        for(FieldXChallanItems challanItems: FieldXHomeFragment.challans){
            if(challanItems.getOrId().equalsIgnoreCase(bundle.get("orgId").toString()) && challanItems.getTask().equalsIgnoreCase("delivery"))
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
