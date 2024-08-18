package com.skilrock.retailapp.ui.fragments.ola;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.models.ola.OlaDepositRequestBean;
import com.skilrock.retailapp.models.ola.OlaMyPromoResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.ola.OlaMyPromoViewModel;

public class OlaMyPromoFragment extends BaseFragment implements View.OnClickListener {

    private OlaMyPromoViewModel viewModel;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private LinearLayout button;
    private TextView tvPromo;
    private final String OLA = "ola";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_promo, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(OlaMyPromoViewModel.class);
        viewModel.getLiveDataPromo().observe(this, observer);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }


    private void callGetPromo() {
        UrlOlaBean urlBean = Utils.getOlaUrlDetails(menuBean, getContext(), "myPromoCode");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master);
            OlaDepositRequestBean model = new OlaDepositRequestBean();
            model.setRetailerUserName(PlayerData.getInstance().getUsername());

            ProgressBarDialog.getProgressDialog().showProgress(getActivity());
            viewModel.callMyPromo(urlBean);
        }
    }

    private void initializeWidgets(View view) {
        button              = view.findViewById(R.id.ll_share);
        tvPromo             = view.findViewById(R.id.tv_promo);
        TextView tvTitle    = view.findViewById(R.id.tvTitle);
        tvUsername          = view.findViewById(R.id.tvUserName);
        tvUserBalance       = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        button.setOnClickListener(this);
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }
        callGetPromo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_share:
                shareText(tvPromo.getText().toString());
                break;
        }
    }

    private void shareText(String text) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(android.content.Intent.EXTRA_TEXT, text);

        startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
    }

    Observer<OlaMyPromoResponseBean> observer = new Observer<OlaMyPromoResponseBean>() {
        @Override
        public void onChanged(@Nullable OlaMyPromoResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.my_promo_code), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                tvPromo.setText(response.getResponseData());
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode())) return;

                String errorMsg = Utils.getResponseMessage(master, OLA, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.my_promo_code), errorMsg);
            }
        }
    };

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        super.onDestroy();
    }
}
