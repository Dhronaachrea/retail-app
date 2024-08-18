package com.skilrock.retailapp.ui.fragments.scratch;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.PackOrderAdapter;
import com.skilrock.retailapp.dialog.QuickOrderConfirmationDialog;
import com.skilrock.retailapp.dialog.SuccessDialog;
import com.skilrock.retailapp.interfaces.CallBackListener;
import com.skilrock.retailapp.interfaces.QuickOrderListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.GameListBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.QuickOrderRequestBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.QuickOrderViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PackOrderFragment extends BaseFragment implements View.OnClickListener, CallBackListener {

    private QuickOrderViewModel quickOrderViewModel;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private RecyclerView recyclerView;
    private String amount;
    private TextView tvAmount;
    private PackOrderAdapter adapter;
    private HashMap<Integer, String> mapSelectedGame = new HashMap<>();
    private GameListBean beanGameList = null;
    private final String SCRATCH = "scratch";
    private QuickOrderConfirmationDialog quickOrderConfirmationDialog;
    private String dotFormatter, amountFormatter;
    private int decimalDegits = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quick_order, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            quickOrderViewModel = ViewModelProviders.of(this).get(QuickOrderViewModel.class);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);

        quickOrderViewModel.getGameListData().observe(this, gameListBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            allowBackAction(true);

            if (gameListBean == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.pack_order), getString(R.string.something_went_wrong), 1, getFragmentManager());
            else if (gameListBean.getResponseCode() == 1000) {
                if (gameListBean.getGames() != null && gameListBean.getGames().size() > 0) {
                    beanGameList = gameListBean;
                    adapter = new PackOrderAdapter(getContext(), new ArrayList<>(gameListBean.getGames()), listener, this);
                    RecyclerView.LayoutManager mLayoutManager;
                    if(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
                        recyclerView.setLayoutManager(new GridLayoutManager(master, 3));
                    else
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                } else
                    Utils.showCustomErrorDialogPop(getContext(), getString(R.string.pack_order), getString(R.string.no_game_found), 1, getFragmentManager());
            } else {
                if (Utils.checkForSessionExpiry(master, gameListBean.getResponseCode())) return;

                //String errorMsg = TextUtils.isEmpty(gameListBean.getResponseMessage()) ? getString(R.string.error_fetching_gamelist) : gameListBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, gameListBean.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.pack_order), errorMsg, 1, getFragmentManager());
            }
        });

        quickOrderViewModel.getResponseData().observe(this, quickOrderResponseBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            allowBackAction(true);

            if (quickOrderResponseBean == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_order), getString(R.string.something_went_wrong));

            if (quickOrderResponseBean.getResponseCode() == 1634) {
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, quickOrderResponseBean.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.pack_order), errorMsg, 1, getFragmentManager());
            } else if (quickOrderResponseBean.getResponseCode() == 1000) {
                String successMsg = getString(R.string.order_generated_with_number) + " " + quickOrderResponseBean.getOrderId();
                //String successMsg = TextUtils.isEmpty(quickOrderResponseBean.getResponseMessage()) ? getString(R.string.successfully_ticket_sold) : quickOrderResponseBean.getResponseMessage();
                //Utils.showSuccessDialog(getContext(), getString(R.string.app_name), successMsg, getFragmentManager());
                SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), "", successMsg, 1);
                dialog.show();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            } else {
                if (Utils.checkForSessionExpiry(master, quickOrderResponseBean.getResponseCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(quickOrderResponseBean.getResponseMessage()) ? getString(R.string.some_internal_error) : quickOrderResponseBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, quickOrderResponseBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_order), errorMsg);
            }
        });

    }

    private void initializeWidgets(View view) {
        recyclerView        = view.findViewById(R.id.rvGameList);
        tvAmount            = view.findViewById(R.id.tvAmount);
        Button btConfirm    = view.findViewById(R.id.buttonConfirm);
        TextView tvTitle    = view.findViewById(R.id.tvTitle);
        tvUsername          = view.findViewById(R.id.tvUserName);
        tvUserBalance       = view.findViewById(R.id.tvUserBalance);

        refreshBalance();
        btConfirm.setOnClickListener(this);
        mapSelectedGame.clear();

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        UrlBean urlBean = Utils.getUrlDetails(menuBean, getContext(), "gameList");
        if (urlBean != null && urlBean.getUrl() != null) {
            allowBackAction(false);
            ProgressBarDialog.getProgressDialog().showProgress(master);
            quickOrderViewModel.callGameListApi(urlBean);
        }

        if (ConfigData.getInstance().getConfigData() != null && ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE() != null)
            decimalDegits = Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE());

        dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(master));
        amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(master));
    }

    @Override
    public void onClick(View v) {

        if (validate() && isClickAllowed()) {
            setAddPack(false);
            new Handler().postDelayed(() -> setAddPack(true), 500);

            int totalBooks = 0;
            for (Map.Entry<Integer, String> entry : mapSelectedGame.entrySet()) {
                Log.d("log", "Game Id: " + entry.getKey() + ", Number_Of_Books###Amount: " + entry.getValue());
                totalBooks = totalBooks + Integer.parseInt(entry.getValue().trim().split("###")[0]);
            }

            //QuickOrderConfirmationDialog dialog = new QuickOrderConfirmationDialog(master, this, String.valueOf(amount.replaceAll(",", "")), totalBooks, tvAmount.getTag().toString());
            String commission = Utils.getBalanceToView((Double.parseDouble(amount) - Double.parseDouble(tvAmount.getTag().toString())), dotFormatter, amountFormatter, decimalDegits);
            quickOrderConfirmationDialog = new QuickOrderConfirmationDialog(master, this, amount, totalBooks, Utils.getBalanceToView((double) tvAmount.getTag(), dotFormatter, amountFormatter, decimalDegits),
                    tvAmount.getText().toString(), tvAmount.getTag().toString(), commission);
            quickOrderConfirmationDialog.show();
            if (quickOrderConfirmationDialog.getWindow() != null)
                quickOrderConfirmationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = quickOrderConfirmationDialog.getWindow();
            if (window != null)
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    QuickOrderListener listener = new QuickOrderListener() {
        @Override
        public void onGameSelected(int gameId, int numberOfBooks, double amount_, double commission) {
            if (numberOfBooks == 0) mapSelectedGame.remove(gameId);
            else {
                mapSelectedGame.put(gameId, numberOfBooks + "###" + amount_ + "###" + commission);
            }
            double sum = 0;
            double totalCommission = 0;
            for (Map.Entry<Integer, String> entry : mapSelectedGame.entrySet()) {
                int quantity = Integer.parseInt(entry.getValue().split("###")[0]);
                double amt = Double.parseDouble(entry.getValue().split("###")[1]);
                double comm = Double.parseDouble(entry.getValue().split("###")[2]);
                sum = sum + (quantity * amt);
                totalCommission = totalCommission + (quantity * comm);
            }

            //String totalSum = Utils.getFormattedValue(sum);

            //amount = Utils.getFormattedValue(sum);
            amount = String.valueOf(sum);
            Double cashAmount = sum;
            cashAmount = Utils.getFormattedAmount(cashAmount);
            String bal = Utils.getBalanceToView(Utils.getFormattedAmount(cashAmount),dotFormatter, amountFormatter, decimalDegits);

            tvAmount.setText(bal);
            tvAmount.setTag(totalCommission);
            Log.w("log", "Commission: " + totalCommission);
        }
    };

     String getBalanceToView(double balance, String dotformatter, String amountFormatter, int decimalDegits) {
        String text = "";
        String text2 = "";
        text = getFormattedAmount(String.valueOf(balance));

        if (decimalDegits == 0) {
            text = text.split("\\.")[0];

            if (text.contains(",")) {
                text = text.replaceAll(",", amountFormatter);
                return text;
            } else {
                text = text.replaceAll("\\.", amountFormatter);
                return text;
            }
        } else {
            String text1 = text.split("\\.")[0];
            if (text1.contains(",")) {
                text1 = text1.replaceAll(",", amountFormatter);
            } else {
                text1 = text1.replaceAll("\\.", amountFormatter);
            }

            if (text.contains("."))
                text2 = text.split("\\.")[1];
            else
                text2 = text;

            return text1 + dotformatter + text2;
        }
     }

    private boolean validate() {
        int totalSelectedBooks = 0;
        if (mapSelectedGame == null) {
            Toast.makeText(master, getString(R.string.no_books_selected), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mapSelectedGame.size() < 1) {
            Toast.makeText(master, getString(R.string.no_books_selected), Toast.LENGTH_SHORT).show();
            return false;
        }
        for (Map.Entry<Integer, String> entry : mapSelectedGame.entrySet()) {
            int quantity = Integer.parseInt(entry.getValue().split("###")[0]);
            totalSelectedBooks = totalSelectedBooks + quantity;
        }
        if (totalSelectedBooks < beanGameList.getMinimumBooksQuantity()) {
            Toast.makeText(master, String.format(getString(R.string.least_selected_booked), beanGameList.getMinimumBooksQuantity()), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (totalSelectedBooks > beanGameList.getMaximumBooksQuantity()) {
            Toast.makeText(master, String.format(getString(R.string.most_selected_booked), beanGameList.getMaximumBooksQuantity()), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void notifyEvent() {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlBean urlBean = Utils.getUrlDetails(menuBean, getContext(), "quickOrder");
        if (urlBean != null && urlBean.getUrl() != null) {
            allowBackAction(false);
            ProgressBarDialog.getProgressDialog().showProgress(master);
            ArrayList<QuickOrderRequestBean.GameOrderList> list = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : mapSelectedGame.entrySet()) {
                QuickOrderRequestBean.GameOrderList game = new QuickOrderRequestBean.GameOrderList();
                game.setGameId(entry.getKey());
                game.setBooksQuantity(Integer.parseInt(entry.getValue().split("###")[0]));
                list.add(game);
            }
            quickOrderViewModel.callBookOrderApi(urlBean, list);
        }
    }

    @Override
    public void notifyEvent(int num, ArrayList<String> list, int position) {

    }

    private String getFormattedAmount(String amt) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###,###.##", otherSymbols);

        return df.format(Double.parseDouble(amt));
    }

    private String getTempFormattedAmount(String cash) {
        String[] casharr = cash.split(",");

        Double amt = Double.parseDouble(casharr[0]);

        String startamount = String.format("%.0f", amt);
        startamount = getFormattedAmount(startamount);

        return startamount.replace(",", " ") + "," + casharr[1];
    }

    private String getTempTotalAmount(Double cashAmount){
        cashAmount = Utils.getFormattedAmount(cashAmount);
        String bal = Utils.getBalanceToView(Utils.getFormattedAmount(cashAmount),dotFormatter, amountFormatter, decimalDegits);

        String demoBal = getTempFormattedAmount(bal);

        return demoBal.replace(".", " ");
    }
    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (quickOrderConfirmationDialog != null)
            quickOrderConfirmationDialog.dismiss();
        super.onDestroy();
    }
}
