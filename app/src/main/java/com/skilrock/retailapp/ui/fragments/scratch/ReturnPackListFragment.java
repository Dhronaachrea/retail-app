package com.skilrock.retailapp.ui.fragments.scratch;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.ReturnBookListAdapter;
import com.skilrock.retailapp.models.rms.GameListBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;

public class ReturnPackListFragment extends BaseFragment {

    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private RecyclerView recyclerView;
    private ReturnChallanResponseBean responseBean;

    private ReturnBookListAdapter adapter;
    private GameListBean beanGameList = null;
    private final String SCRATCH = "scratch";
    private String CHALLAN_NUMBER = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_returun_book_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        recyclerView = view.findViewById(R.id.rvGameList);
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
                CHALLAN_NUMBER = bundle.getString("ChallanNumber");
            }
            menuBean = bundle.getParcelable("MenuBean");
            responseBean = bundle.getParcelable("ReturnBook");
            setAdapter();
        }
    }

    private void setAdapter() {
        /*ReturnPackListener listener = this::openReturnChallanDetailFragment;
        adapter = new ReturnBookListAdapter(getContext(), responseBean.getGames(), listener);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);*/
    }

    private void openReturnChallanDetailFragment(ReturnChallanResponseBean returnChallanResponseBean) {
        Bundle bundle = new Bundle();
        bundle.putString("title", menuBean.getCaption());
        bundle.putParcelable("MenuBean", menuBean);
        bundle.putParcelable("ReturnBook", returnChallanResponseBean);
        bundle.putString("ChallanNumber", CHALLAN_NUMBER);
        master.openFragment(new PackReturnMultiScanningFragment(), "PackReturnMultiScanningFragment", true, bundle);
    }

}