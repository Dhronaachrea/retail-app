package com.skilrock.retailapp.ui.fragments.scratch;

import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.ChallanAdapter;
import com.skilrock.retailapp.dialog.SuccessDialog;
import com.skilrock.retailapp.interfaces.TotalPackReceivingNowListener;
import com.skilrock.retailapp.models.ResponseCodeMessageBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.ChallanResponseBean;
import com.skilrock.retailapp.models.scratch.ReceiveBookRequestBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.ChallanViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChallanFragment extends BaseFragment implements View.OnClickListener {

    private ChallanViewModel challanViewModel;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private ChallanAdapter challanAdapter;
    private String challanNumber;
    private final String SCRATCH = "scratch";
    private TextView tvTotalReceivingBooks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challan, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        challanViewModel = ViewModelProviders.of(this).get(ChallanViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        RecyclerView recyclerView   = view.findViewById(R.id.rv_challan);
        Button button               = view.findViewById(R.id.button);
        tvTotalReceivingBooks       = view.findViewById(R.id.tvTotalReceivingBooks);
        TextView tvDate             = view.findViewById(R.id.tvDate);
        TextView tvChallanNo        = view.findViewById(R.id.tvChallanNumber);
        TextView tvTitle            = view.findViewById(R.id.tvTitle);
        tvUsername                  = view.findViewById(R.id.tvUserName);
        tvUserBalance               = view.findViewById(R.id.tvUserBalance);

        if(BuildConfig.app_name.equalsIgnoreCase(getString(R.string.app_name_FieldX))){
            button.setVisibility(View.GONE);
            tvTotalReceivingBooks.setVisibility(View.GONE);
        }

        refreshBalance();
        button.setOnClickListener(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            menuBean = bundle.getParcelable("MenuBean");
            ChallanResponseBean challanResponseBean = bundle.getParcelable("ChallanResponseBean");
            challanNumber = bundle.getString("ChallanNumber");
            Log.w("log", "ChallanResponseBean: " + challanResponseBean);
            tvChallanNo.setText(challanNumber);
            if(BuildConfig.app_name.equalsIgnoreCase(getString(R.string.app_name_FieldX)))
                tvDate.setText(Utils.formatTimeResultForFieldXChallan(Objects.requireNonNull(challanResponseBean).getDlDate().split(" ")[0]));
            else
                tvDate.setText(Utils.getDateMonthYearName(Objects.requireNonNull(challanResponseBean).getDlDate().split(" ")[0]));
            tvTitle.setText(bundle.getString("title"));
            if (challanResponseBean.getGameWiseDetails().size() > 0) {
                TotalPackReceivingNowListener listener = ChallanFragment.this::onPackSelected;
                challanAdapter = new ChallanAdapter(getContext(), challanResponseBean.getGameWiseDetails(), listener);

                if(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
                    recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
                else
                    recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(challanAdapter);
            }
            else Toast.makeText(master, getString(R.string.unable_to_draw_challan), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("DefaultLocale")
    private void onPackSelected() {
        int sum = 0;
        ArrayList<ChallanResponseBean.GameWiseDetail> listGameWiseDetails = challanAdapter.getListGameWiseDetails();
        for(ChallanResponseBean.GameWiseDetail list: listGameWiseDetails)
            sum = sum + list.getScannedBooks();
        String text = getString(R.string.total_pack_receiving_now_place_holder) + " " + sum;
        tvTotalReceivingBooks.setText(text);
    }

    Observer<ResponseCodeMessageBean> observer = new Observer<ResponseCodeMessageBean>() {
        @Override
        public void onChanged(@Nullable ResponseCodeMessageBean responseCodeMessageBean) {
            ProgressBarDialog.getProgressDialog().dismiss();
            allowBackAction(true);

            if (responseCodeMessageBean == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_receive), getString(R.string.something_went_wrong));
            else if (responseCodeMessageBean.getResponseCode() == 1000) {
                challanViewModel.getLiveDataResponse().removeObserver(this);
                String successMsg = getString(R.string.pack_received_successfully);
                SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), "", successMsg, 2);
                dialog.show();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            }
            else {
                if (Utils.checkForSessionExpiry(master, responseCodeMessageBean.getResponseCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.some_internal_error) : responseCodeMessageBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, responseCodeMessageBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_receive), errorMsg);
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        if (validate() && isClickAllowed()) {
            UrlBean urlBean = Utils.getUrlDetails(menuBean, getContext(), "receiveBook");

            if (urlBean != null) {
                ProgressBarDialog.getProgressDialog().showProgress(master);
                challanViewModel.getLiveDataResponse().observe(this, observer);

                ReceiveBookRequestBean model = new ReceiveBookRequestBean();
                model.setUserName(PlayerData.getInstance().getUsername());
                model.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
                model.setDlChallanNumber(challanNumber);
                model.setReceiveType(AppConstants.RECEIVED);

                HashMap<Integer, ArrayList<String>> map = challanAdapter.getMapSelectedBooks();
                ArrayList<ReceiveBookRequestBean.BookInfo> listBookInfo = new ArrayList<>();
                for (Map.Entry<Integer, ArrayList<String>> entry : map.entrySet()) {
                    Log.d("log", "Game Id: " + entry.getKey());
                    ReceiveBookRequestBean.BookInfo bookInfo = new ReceiveBookRequestBean.BookInfo();
                    bookInfo.setGameId(entry.getKey());
                    bookInfo.setGameType(AppConstants.SCRATCH);
                    bookInfo.setBookList(entry.getValue());
                    listBookInfo.add(bookInfo);
                }

                model.setBookInfo(listBookInfo);
                allowBackAction(false);
                challanViewModel.callReceiveBookApi(urlBean, model);
            }
            else Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(master, getString(R.string.no_books_selected), Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        HashMap<Integer, ArrayList<String>> mapSelectedBooks = challanAdapter.getMapSelectedBooks();
        return mapSelectedBooks != null && mapSelectedBooks.size() > 0;
    }

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        if (challanAdapter != null)
            challanAdapter.closePacksDialog();
        super.onDestroy();
    }
}
