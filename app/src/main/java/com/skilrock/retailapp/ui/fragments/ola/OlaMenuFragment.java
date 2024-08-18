package com.skilrock.retailapp.ui.fragments.ola;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.ola.OlaMenuAdapter;
import com.skilrock.retailapp.interfaces.ModuleListener;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;

import java.util.ArrayList;

public class OlaMenuFragment extends BaseFragment implements View.OnClickListener {

    private ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> listMenuBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ola_menu, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);
        /*UrlOlaBean urlBean = Utils.getOlaUrlDetails(listMenuBean.get(0), getContext(), "domain");
        if (urlBean != null) {
            progressDialog = Utils.getProgressDialog(getContext());
            viewModel.callOlaDomainApi(urlBean);
        }*/
    }

    private void initializeWidgets(View view) {
        TextView tvTitle            = view.findViewById(R.id.tvTitle);
        tvUsername                  = view.findViewById(R.id.tvUserName);
        tvUserBalance               = view.findViewById(R.id.tvUserBalance);
        RecyclerView recyclerView   = view.findViewById(R.id.rvOlaMenu);
        refreshBalance();

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }

            listMenuBean = bundle.getParcelableArrayList("ListOlaModule");
            if (listMenuBean == null)
                Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            else if (listMenuBean.size() < 1)
                Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            else {
                OlaMenuAdapter adapter = new OlaMenuAdapter(getContext(), listMenuBean, moduleListener);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }

        }
    }

    ModuleListener moduleListener = new ModuleListener() {
        @Override
        public void onModuleClicked(String menuCode, int index, HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean) {
            Bundle bundle = new Bundle();
            switch (menuCode) {
                case AppConstants.OLA_REGISTER:
                    bundle.putInt("index", index);
                    bundle.putParcelable("MenuBean", menuBean);
                    bundle.putString("title", menuBean.getCaption());
                    master.openFragment(new OlaRegistrationFragment(), "OlaRegistrationFragment", true, bundle);
                    break;

                case AppConstants.OLA_DEPOSIT:
                    bundle.putInt("index", index);
                    bundle.putParcelable("MenuBean", menuBean);
                    bundle.putString("title", menuBean.getCaption());
                    master.openFragment(new OlaDepositFragment(), "OlaDepositFragment", true, bundle);
                    break;

                case AppConstants.OLA_WITHDRAW:
                    bundle.putInt("index", index);
                    bundle.putParcelable("MenuBean", menuBean);
                    bundle.putString("title", menuBean.getCaption());
                    master.openFragment(new OlaWithdrawalFragment(), "OlaWithdrawalFragment", true, bundle);
                    break;

                case AppConstants.OLA_PLR_LEDGER:
                    bundle.putInt("index", index);
                    bundle.putParcelable("MenuBean", menuBean);
                    bundle.putString("title", menuBean.getCaption());
                    master.openFragment(new OlaPlayerTransactionReportFragment(), "OlaPlayerLedgerReportFragment", true, bundle);
                    break;

                case AppConstants.OLA_PLR_DETAILS:
                    bundle.putInt("index", index);
                    bundle.putParcelable("MenuBean", menuBean);
                    bundle.putString("title", menuBean.getCaption());
                    master.openFragment(new OlaPlayerDetailsReportFragment(), "OlaPlayerDetailsReportFragment", true, bundle);
                    break;

                case AppConstants.OLA_PLR_SEARCH:
                    bundle.putInt("index", index);
                    bundle.putParcelable("MenuBean", menuBean);
                    bundle.putString("title", menuBean.getCaption());
                    master.openFragment(new OlaPlayerSearchReportFragment(), "OlaPlayerSearchReportFragment", true, bundle);
                    break;

                case AppConstants.OLA_PLR_PASSWORD:
                    bundle.putInt("index", index);
                    bundle.putParcelable("MenuBean", menuBean);
                    bundle.putString("title", menuBean.getCaption());
                    master.openFragment(new OlaPlayerForgotPasswordFragment(), "OlaPlayerForgotPasswordFragment", true, bundle);
                    break;

                case AppConstants.OLA_PLR_PENDING_TXN:
                    bundle.putInt("index", index);
                    bundle.putParcelable("MenuBean", menuBean);
                    bundle.putString("title", menuBean.getCaption());
                    master.openFragment(new OlaPendingTransactionFragment(), "OlaPendingTransactionFragment", true, bundle);
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {

    }

}
