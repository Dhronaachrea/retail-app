package com.skilrock.retailapp.ui.fragments.fieldx;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.FieldX.AllTaskAdapter;

public class PickupFragment extends BaseFragmentFieldx implements SwipeRefreshLayout.OnRefreshListener {
    private static RecyclerView recyclerView;
    private AllTaskAdapter adapter;
    private static TextView noData;
    private SwipeRefreshLayout refreshLayout;
    // private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;

    public PickupFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_task, container, false);
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                //tvTitle.setText(bundle.getString("title"));
            }
            // menuBean = bundle.getParcelable("MenuBean");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.all_task);
        noData = view.findViewById(R.id.noData);
        refreshLayout=view.findViewById(R.id.swiper);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new AllTaskAdapter(FieldXHomeFragment.pickup, master, getActivity(), false);
        recyclerView.setAdapter(adapter);
        if (FieldXHomeFragment.pickup.size() == 0)
            noData.setVisibility(View.VISIBLE);
        else {
            noData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        getFieldxRetailerData();
        notifyRetailer();
        refreshLayout.setRefreshing(false);
    }

    public void notifyRetailer() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new AllTaskAdapter(FieldXHomeFragment.pickup, master, getActivity(), false);
        recyclerView.setAdapter(adapter);
        if(FieldXHomeFragment.pickup.size()>0)
            noData.setVisibility(View.GONE);
        else
            noData.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }
}
