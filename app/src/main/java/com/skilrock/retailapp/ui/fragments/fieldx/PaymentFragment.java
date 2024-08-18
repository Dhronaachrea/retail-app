package com.skilrock.retailapp.ui.fragments.fieldx;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.FieldX.AllTaskAdapter;

public class PaymentFragment extends BaseFragmentFieldx implements SwipeRefreshLayout.OnRefreshListener {
    public static int position;
    private View view;
    private static RecyclerView recyclerView;
    private AllTaskAdapter adapter;
    private static TextView noData;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_task, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.all_task);
        noData = view.findViewById(R.id.noData);
        refreshLayout=view.findViewById(R.id.swiper);
        refreshLayout.setOnRefreshListener(this);
        adapter = new AllTaskAdapter(FieldXHomeFragment.payment, master, getActivity(), false, fieldXAllTaskViewModel);
        recyclerView.setAdapter(adapter);
        if (FieldXHomeFragment.payment.size() == 0)
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
        adapter = new AllTaskAdapter(FieldXHomeFragment.payment, master, getActivity(), false);
        recyclerView.setAdapter(adapter);
        if(FieldXHomeFragment.payment.size()>0)
            noData.setVisibility(View.GONE);
        else
            noData.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }
}









