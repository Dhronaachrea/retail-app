package com.skilrock.retailapp.ui.fragments.rms;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.UserListAdapter;
import com.skilrock.retailapp.interfaces.UserSelectListener;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.SearchUserRequestBean;
import com.skilrock.retailapp.models.rms.SearchUserResponseBeanNew;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.SearchUserViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class SearchUserFragment extends BaseFragment implements View.OnClickListener, UserSelectListener {
    private SearchUserViewModel viewModel;
    private RecyclerView rvUsers;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private UserListAdapter adapter;
    private ArrayList<SearchUserResponseBeanNew.UserSearchDatum> arrayListUser = new ArrayList<>();
    private final String RMS = "rms";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_user, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(this).get(SearchUserViewModel.class);
            viewModel.getLiveData().observe(this, observer);
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
        rvUsers = view.findViewById(R.id.rv_user);

        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        tvUsername              = view.findViewById(R.id.tvUserName);
        TextView tvBal          = view.findViewById(R.id.tvBal);

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        refreshBalance();

        Integer domain = (int) PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId();

        SearchUserRequestBean searchUserRequestBean = new SearchUserRequestBean();
        searchUserRequestBean.setDomainId(domain);
        searchUserRequestBean.setOrgId((int) PlayerData.getInstance().getOrgId());

        String url = Utils.getRmsUrlDetails(menuBean, getActivity(), "doUserSearch");

        ProgressBarDialog.getProgressDialog().showProgress(master);
        viewModel.searchUserApi(url, PlayerData.getInstance().getToken(), searchUserRequestBean);
    }

    @Override
    public void onClick(View view) {

    }

    Observer<SearchUserResponseBeanNew> observer = new Observer<SearchUserResponseBeanNew>() {
        @Override
        public void onChanged(@Nullable SearchUserResponseBeanNew response) {
            //llNetCollection.setVisibility(View.GONE);
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.search_user), getString(R.string.something_went_wrong), 1, getFragmentManager());
            else if (response.getResponseCode() != 0) {
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                //Utils.showCustomErrorDialog(getContext(), getString(R.string.search_user), errorMsg);
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.search_user), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                //Utils.showCustomErrorDialog(getContext(), getString(R.string.search_user), errorMsg);
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.search_user), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() == 0) {
                arrayListUser = response.getResponseData().getData().getUserSearchData();
                setUserListAdapter(arrayListUser);
            } else {
                Utils.showCustomErrorDialog(getContext(), getString(R.string.search_user), getString(R.string.something_went_wrong));
            }
        }
    };

    @Override
    public void onUserClicked(int position, String userId) {
        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
            ((MainActivity) Objects.requireNonNull(getActivity())).openUserDetailsMyanmarFragment(userId);
        else
            ((MainActivity)getActivity()).openUserDetailsFragment(userId);
    }

    private void setUserListAdapter(ArrayList<SearchUserResponseBeanNew.UserSearchDatum> arrayList){
        adapter = new UserListAdapter(arrayList, getContext(), this);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.setItemAnimator(new DefaultItemAnimator());
        rvUsers.setAdapter(adapter);
    }
}