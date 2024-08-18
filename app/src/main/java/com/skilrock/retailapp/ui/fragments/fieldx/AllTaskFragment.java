package com.skilrock.retailapp.ui.fragments.fieldx;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.skilrock.retailapp.models.FieldX.AllTaskItem;

import java.util.ArrayList;

public class AllTaskFragment extends BaseFragmentFieldx implements SwipeRefreshLayout.OnRefreshListener{

    private static RecyclerView recyclerView;
    private static AllTaskAdapter adapter;
    private static View view;
    private static TextView noData;
    // private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public AllTaskFragment() {
    }

    public static AllTaskFragment getInstance() {
        return new AllTaskFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_task, container, false);
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                //tvTitle.setText(bundle.getString("title"));
            }
            //menuBean = bundle.getParcelable("MenuBean");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.all_task);
        noData = view.findViewById(R.id.noData);
        refreshLayout = view.findViewById(R.id.swiper);
        refreshLayout.setOnRefreshListener(this);
        // initializeWidgets(view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new AllTaskAdapter(allTask, master, getActivity(), true, fieldXAllTaskViewModel);
        recyclerView.setAdapter(adapter);
        if (allTask.size() == 0)
            noData.setVisibility(View.VISIBLE);
        else {
            noData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    private ArrayList<AllTaskItem> sort(ArrayList<AllTaskItem> items) {
        return null;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        getFieldxRetailerData();
        notifyAllTaskRetailer();
        refreshLayout.setRefreshing(false);
    }

    public void notifyAllTaskRetailer() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new AllTaskAdapter(allTask, master, getActivity(), true);
        if(allTask.size()>0)
            noData.setVisibility(View.GONE);
        else
            noData.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
