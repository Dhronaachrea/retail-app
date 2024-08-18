package com.skilrock.retailapp.ui.fragments.ola;

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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.ola.OlaPlayerSearchAdapter;
import com.skilrock.retailapp.interfaces.PlayerSelectionListener;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchResponseBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.StringMapOla;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.ola.OlaPlayerSearchReportViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class OlaPlayerSearchReportMyanmarFragment extends BaseFragment implements View.OnClickListener {

    private OlaPlayerSearchReportViewModel viewModel;
    private EditText etFirstName, etLastName, etUserName;
    private ImageView ivProceed;
    private RecyclerView rvReport;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, playerSearchBean;
    private int PAGE_INDEX = 1;
    private boolean isLoading = false;
    private OlaPlayerSearchAdapter adapter;
    private ArrayList<OlaPlayerSearchResponseBean.ResponseData.Player> listRV = new ArrayList<>();
    private boolean isDataFinished = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_search_report_myanmar, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            viewModel = ViewModelProviders.of(this).get(OlaPlayerSearchReportViewModel.class);
            viewModel.getLiveData().observe(this, observer);
            viewModel.getLiveDataLoadMore().observe(this, observerLoadMore);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
        initScrollListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etFirstName             = view.findViewById(R.id.et_first_name);
        etLastName              = view.findViewById(R.id.et_last_name);
        etUserName                = view.findViewById(R.id.et_user_name);
        rvReport                = view.findViewById(R.id.rv_report);
        ivProceed               = view.findViewById(R.id.button_proceed);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        ivProceed.setOnClickListener(this);

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
            playerSearchBean = bundle.getParcelable("PlayerSearch");
        }

        etUserName.setOnEditorActionListener(this::onEditorAction);

        if (listRV != null && listRV.size() > 0) {
            PlayerSelectionListener listener = OlaPlayerSearchReportMyanmarFragment.this::redirectToPlayerSearchFragment;
            adapter = new OlaPlayerSearchAdapter(getContext(), listRV, listener);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvReport.setLayoutManager(mLayoutManager);
            rvReport.setItemAnimator(new DefaultItemAnimator());
            rvReport.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_proceed:
                Utils.hideKeyboard(Objects.requireNonNull(getActivity()));
                if (validate())
                    callPlayerSearchApi(true);
                break;
        }
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (TextUtils.isEmpty(getText(etFirstName)) && TextUtils.isEmpty(getText(etLastName)) && TextUtils.isEmpty(getText(etUserName))) {
            Toast.makeText(master, getString(R.string.provide_any_detail), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    Observer<OlaPlayerSearchResponseBean> observer = new Observer<OlaPlayerSearchResponseBean>() {
        @Override
        public void onChanged(@Nullable OlaPlayerSearchResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null) Utils.showCustomErrorDialog(getContext(), getString(R.string.player_search), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                if (response.getResponseData() != null && response.getResponseData().getPlayers().size() < 1) {
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.player_search), getString(R.string.no_data_found));
                }
                else {
                    PAGE_INDEX = 1;
                    isDataFinished = false;
                    ArrayList<OlaPlayerSearchResponseBean.ResponseData.Player> list = Objects.requireNonNull(response.getResponseData()).getPlayers();
                    if (list != null) {
                        listRV.clear();
                        if (list.size() < AppConstants.PAGE_SIZE)
                            isDataFinished = true;
                        listRV.addAll(list);
                        PlayerSelectionListener listener = OlaPlayerSearchReportMyanmarFragment.this::redirectToPlayerSearchFragment;
                        adapter = new OlaPlayerSearchAdapter(getContext(), listRV, listener);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        rvReport.setLayoutManager(mLayoutManager);
                        rvReport.setItemAnimator(new DefaultItemAnimator());
                        rvReport.setAdapter(adapter);
                    }
                }
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode())) return;

                String errorMsg = Utils.getResponseMessage(master, "ola", response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.player_search), errorMsg);
            }
        }
    };

    Observer<OlaPlayerSearchResponseBean> observerLoadMore = response -> {
        if (response == null) {
            loadMoreAfterApiResponse(null);
            Toast.makeText(master, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }
        else if (response.getResponseCode() == 0) {
            if (response.getResponseData() != null && response.getResponseData().getPlayers().size() < 1) {
                Toast.makeText(master, getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
                isDataFinished = true;
                loadMoreAfterApiResponse(null);
            }
            else {
                if (Objects.requireNonNull(response.getResponseData()).getPlayers().size() < AppConstants.PAGE_SIZE)
                    isDataFinished = true;
                loadMoreAfterApiResponse(Objects.requireNonNull(response.getResponseData()).getPlayers());
            }
        } else {
            loadMoreAfterApiResponse(null);
            if (Utils.checkForSessionExpiry(master, response.getResponseCode())) return;

            String errorMsg = Utils.getResponseMessage(master, "ola", response.getResponseCode());
            Toast.makeText(master, errorMsg, Toast.LENGTH_LONG).show();
        }
    };

    private void redirectToPlayerSearchFragment(String mobileNumber) {
        Bundle bundle = new Bundle();
        bundle.putString("title",  StringMapOla.getCaption(menuBean.getMenuCode(), menuBean.getCaption()));
        bundle.putParcelable("MenuBean", playerSearchBean);
        bundle.putString("userName", mobileNumber);
        master.openFragment(new OlaPlayerTransactionReportMyanmarFragment(), "OlaPlayerTransactionReportMyanmarFragment", true, bundle);
    }

    private void callPlayerSearchApi(boolean isFirstTime) {
        if (adapter != null) adapter.clear();
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "searchPlayer");
        if (urlBean != null) {
            if (isFirstTime)
                ProgressBarDialog.getProgressDialog().showProgress(master);

            String domain = PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgCode() + "_" +
                    PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName();
            OlaPlayerSearchRequestBean model = new OlaPlayerSearchRequestBean();
            model.setAccept(urlBean.getAccept());
            model.setContentType(urlBean.getContentType());

            model.setPageIndex(isFirstTime ? 1 : ++PAGE_INDEX);
            model.setPageSize(AppConstants.PAGE_SIZE);
            model.setPassword(urlBean.getPassword());

            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                model.setPlrUserName(getText(etUserName));
            else {
                model.setPhone(getText(etUserName));
                model.setFirstName(getText(etFirstName));
                model.setLastName(getText(etLastName));
            }

            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                model.setPlrDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_OLA_MYANMAR") ||
                        BuildConfig.FLAVOR.equalsIgnoreCase("pROD_OLA_MYANMAR")
                        ? "www.bet2winasia.com" : "www.igamew.com");
            else
                model.setPlrDomainCode(BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")
                        ? "www.camwinlotto.cm" : "www.camwinlotto.cm");

            model.setRetailDomainCode(urlBean.getRetailDomainCode());
            model.setRetailOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setUrl(urlBean.getUrl());
            model.setUserName(urlBean.getUserName());

            if (isFirstTime)
                viewModel.callOlaPlayerSearchApi(model);
            else
                viewModel.loadMore(model);
        }
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void initScrollListener() {
        rvReport.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listRV.size() - 1) {
                        if (!isDataFinished) {
                            loadMore();
                            isLoading = true;
                        }
                    }
                }
            }
        });
    }

    private void loadMore() {
        try {
            listRV.add(null);
            adapter.notifyItemInserted(listRV.size() - 1);
            callPlayerSearchApi(false);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void loadMoreAfterApiResponse(ArrayList<OlaPlayerSearchResponseBean.ResponseData.Player> listData) {
        listRV.remove(listRV.size() - 1);
        if (listData != null) {
            int scrollPosition = listRV.size();
            adapter.notifyItemRemoved(scrollPosition);
            listRV.addAll(listData);
        }
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    private boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            //ivProceed.performClick();
            onClick(ivProceed);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        super.onDestroy();
    }
}