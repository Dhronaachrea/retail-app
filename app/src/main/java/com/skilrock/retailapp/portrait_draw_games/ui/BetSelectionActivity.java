package com.skilrock.retailapp.portrait_draw_games.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.GameChangeListener;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.portrait_draw_games.adapter.MainBetTypePortraitAdapter;
import com.skilrock.retailapp.portrait_draw_games.viewmodels.BetSelectionViewModel;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.DrawGameData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BetSelectionActivity extends BaseActivity implements AppConstants, MainBetTypePortraitAdapter.OnMainBetTypeClickListener, View.OnClickListener {

    //private DrawFetchGameDataResponseBean.ResponseData.GameRespVO GAME_DATA;
    private BetSelectionViewModel viewModel;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private HashMap<String, DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> MAP_FIRST_BALL = new HashMap<>();
    private HashMap<String, DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> MAP_LAST_BALL = new HashMap<>();
    private HashMap<String, DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> MAP_FIRST_FIVE = new HashMap<>();
    private RecyclerView rvMainBet, rvSideBet;
    private ImageView ivLine;
    private LinearLayout llParent;
    private TextView tvSideBet, tvBetValue, tvNoOfBets, tvSideBetFirstFive, tvBetValueTag;
    public final int REQUEST_CODE_LUCKY_SIX_MAIN_BET        = 1010;
    public final int REQUEST_CODE_LUCKY_SIX_SIDE_BET        = 1011;
    public final int REQUEST_CODE_FIVE_BY_NINETY_MAIN_BET   = 2020;
    public final int REQUEST_CODE_PURCHASE_DETAILS          = 3030;
    public static long LAST_CLICK_TIME                      = 0;
    public static int CLICK_GAP                             = 200;
    private ArrayList<PanelBean> LIST_PANEL_DATA    = new ArrayList<>();
    private boolean isFromBalanceRefresh = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            setContentView(R.layout.activity_bet_selection_v2pro);
        else
            setContentView(R.layout.activity_bet_selection);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        setToolBar();
        initializeWidgets();
        calculateTotalAmount();
        setMainBetTypeAdapter();
        setMapsForSideBet();
        Log.d("TAg", "09876543");

        if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FiveByNinety"))
            tvSideBetFirstFive.setText(getString(R.string.all_balls));
        else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("LuckySix"))
            tvSideBetFirstFive.setText(getString(R.string.first_five_balls));

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBalance();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //GAME_DATA = getIntent().getExtras().getParcelable("GameResponse");
            MENU_BEAN = bundle.getParcelable("MenuBean");
        }
        else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            BetSelectionActivity.this.finish();
        }
    }

    private void setToolBar() {
        ImageView ivGameIcon    = findViewById(R.id.ivGameIcon);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);

        tvTitle.setText(DrawGameData.getSelectedGame().getGameName());
        if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase(LUCKY_SIX))
            ivGameIcon.setBackgroundResource(R.drawable.lucky_6);
        else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase(FIVE_BY_NINETY))
            ivGameIcon.setBackgroundResource(R.drawable.five_by_ninety);
    }

    private void initializeWidgets() {
        viewModel                   = ViewModelProviders.of(this).get(BetSelectionViewModel.class);
        TextView tvNoOfTickets      = findViewById(R.id.tvNoOfTickets);
        TextView tvHours            = findViewById(R.id.tvHours);
        TextView tvMinutes          = findViewById(R.id.tvMinutes);
        TextView tvSeconds          = findViewById(R.id.tvSeconds);
        TextView tvMainBet          = findViewById(R.id.tvMainBet);
        TextView tvSideBetFirstBet  = findViewById(R.id.tvSideBetFirstBall);
        TextView tvSideBetLastBall  = findViewById(R.id.tvSideBetLastBall);
        tvSideBetFirstFive          = findViewById(R.id.tvSideBetFirstFive);
        RelativeLayout rlBuy        = findViewById(R.id.rlBuy);
        tvNoOfBets                  = findViewById(R.id.tvNoOfBets);
        tvBetValue                  = findViewById(R.id.tvBetValue);
        tvBetValueTag               = findViewById(R.id.tvBetValueTag);
        tvSideBet                   = findViewById(R.id.tvSideBet);
        ivLine                      = findViewById(R.id.ivLine);
        //rvSideBet                 = findViewById(R.id.rvSideBet);
        rvMainBet                   = findViewById(R.id.rvMainBet);
        llParent                    = findViewById(R.id.llParent);
        CircularTextView ctv1       = findViewById(R.id.circle_1);
        CircularTextView ctv2       = findViewById(R.id.circle_2);

        viewModel.getLiveDataBalance().observe(this, observerBalance);
        ctv1.setStrokeColor("#e3e3e3");
        ctv2.setStrokeColor("#f5f5f5");
        String tag = getString(R.string.total_bet_value) + " (" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(BetSelectionActivity.this)) + ")";
        tvBetValueTag.setText(tag);
        tvSideBetFirstBet.setOnClickListener(this);
        tvSideBetLastBall.setOnClickListener(this);
        tvSideBetFirstFive.setOnClickListener(this);
        rlBuy.setOnClickListener(this);
    }

    private void setMainBetTypeAdapter() {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> list = new ArrayList<>();
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO model : DrawGameData.getSelectedGame().getBetRespVOs()) {
            if (model.getWinMode() != null && model.getWinMode().trim().equalsIgnoreCase("MAIN"))
                list.add(model);
        }
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> listRemovedMagic = new ArrayList<>();
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO var : list) {
            if(!var.getBetCode().contains("MAGIC")) {
                listRemovedMagic.add(var);
            }
        }

        MainBetTypePortraitAdapter betTypeAdapter = new MainBetTypePortraitAdapter(listRemovedMagic, this, this);
        rvMainBet.setHasFixedSize(true);
        rvMainBet.setItemAnimator(new DefaultItemAnimator());
        rvMainBet.setLayoutManager(new GridLayoutManager(this, 3));
        rvMainBet.setAdapter(betTypeAdapter);
    }
    private void setMapsForSideBet() {
        if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FiveByNinety")) {
            ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> betRespVOs = DrawGameData.getSelectedGame().getBetRespVOs();
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO model : betRespVOs) {
                boolean isFirstBall = model.getBetCode().equalsIgnoreCase("FirstBallColor") || model.getBetCode().equalsIgnoreCase("5/90FirstBallSum") || model.getBetCode().equalsIgnoreCase("5/90FirstBallOdd/Even");
                boolean isLastBall = model.getBetCode().equalsIgnoreCase("LastBallColor") || model.getBetCode().equalsIgnoreCase("5/90LastBallSum") || model.getBetCode().equalsIgnoreCase("5/90LastBallOdd/Even");
                boolean isFirstFiveBall = model.getBetCode().equalsIgnoreCase("FirstFiveBallSum") || model.getBetCode().equalsIgnoreCase("MoreOddEven") || model.getBetCode().equalsIgnoreCase("FiveBallColor") || model.getBetCode().equalsIgnoreCase("Increasing/Decreasing") || model.getBetCode().equalsIgnoreCase("Hill/Valley");
                if (isFirstBall) MAP_FIRST_BALL.put(model.getBetCode(), model);
                else if (isLastBall) MAP_LAST_BALL.put(model.getBetCode(), model);
                else if (isFirstFiveBall) MAP_FIRST_FIVE.put(model.getBetCode(), model);
            }
        }
        else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("LuckySix")) {
            ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> betRespVOs = DrawGameData.getSelectedGame().getBetRespVOs();
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO model : betRespVOs) {
                boolean isFirstBall = model.getBetCode().equalsIgnoreCase("LuckySixFirstBallEvenOdd") || model.getBetCode().equalsIgnoreCase("LuckySixFirstBallSum") || model.getBetCode().equalsIgnoreCase("LuckySixFirstBallColor");
                boolean isLastBall = model.getBetCode().equalsIgnoreCase("LuckySixLastBallEvenOdd") || model.getBetCode().equalsIgnoreCase("LuckySixLastBallSum") || model.getBetCode().equalsIgnoreCase("LuckySixLastBallColor");
                boolean isFirstFiveBall = model.getBetCode().equalsIgnoreCase("LuckySixFirstFiveMoreEvenOdd") || model.getBetCode().equalsIgnoreCase("LuckySixFirstFiveBallSum") || model.getBetCode().equalsIgnoreCase("LuckySixAllBallColor");
                if (isFirstBall) MAP_FIRST_BALL.put(model.getBetCode(), model);
                else if (isLastBall) MAP_LAST_BALL.put(model.getBetCode(), model);
                else if (isFirstFiveBall) MAP_FIRST_FIVE.put(model.getBetCode(), model);
            }
        }
        Log.d("draw", "MAP_FIRST_BALL Size: " + MAP_FIRST_BALL.size());
        Log.d("draw", "MAP_LAST_BALL Size: " + MAP_LAST_BALL.size());
        Log.d("draw", "MAP_FIRST_FIVE Size: " + MAP_FIRST_FIVE.size());
    }

    public void onClickBack(View view) {
        if (SystemClock.elapsedRealtime() - LAST_CLICK_TIME < CLICK_GAP) {
            return;
        }
        LAST_CLICK_TIME = SystemClock.elapsedRealtime();

        if (!LIST_PANEL_DATA.isEmpty()) {
            int image;
            GameChangeListener listener = super::onBackPressed;
            if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("LuckySix"))
                image = R.drawable.lucky_6;
            else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FullRoulette"))
                image = R.drawable.spin_win;
            else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FiveByNinety"))
                image = R.drawable.five_by_ninety;
            else
                image = R.drawable.icon_draw_game;
            Utils.showGameSwitchDialog(this, image, getString(R.string.items_in_cart_lost_msg_back), getString(R.string.do_you_want_to_leave), getString(R.string.stay), getString(R.string.leave), listener);
        }
        else
            BetSelectionActivity.this.finish();
    }

    @Override
    public void onMainBetTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO) {
        if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase(LUCKY_SIX)) {
            if (LIST_PANEL_DATA.size() < DrawGameData.getSelectedGame().getMaxPanelAllowed()) {
                Intent intent = new Intent(this, LuckySixMainBetActivity.class);
                intent.putExtra("GameResponse", DrawGameData.getSelectedGame());
                intent.putExtra("SelectedBet", betRespVO);
                intent.putExtra("MenuBean", MENU_BEAN);
                intent.putExtra("PanelSize", LIST_PANEL_DATA.size());
                startActivityForResult(intent, REQUEST_CODE_LUCKY_SIX_MAIN_BET);
            }
            else
                Utils.showRedToast(BetSelectionActivity.this, "You have reached the maximum panel limit!");
        }
        else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase(FIVE_BY_NINETY)) {
            if (LIST_PANEL_DATA.size() < DrawGameData.getSelectedGame().getMaxPanelAllowed()) {
                Intent intent = new Intent(this, FiveByNinetyMainBetActivity.class);
                intent.putExtra("GameResponse", DrawGameData.getSelectedGame());
                intent.putExtra("SelectedBet", betRespVO);
                intent.putExtra("MenuBean", MENU_BEAN);
                intent.putExtra("PanelSize", LIST_PANEL_DATA.size());
                startActivityForResult(intent, REQUEST_CODE_FIVE_BY_NINETY_MAIN_BET);
            }
            else
                Utils.showRedToast(BetSelectionActivity.this, getString(R.string.max_panel_limit));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("log", "Request Code: " + requestCode + ", Result Code: " + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_LUCKY_SIX_MAIN_BET:
                if (data != null) {
                    PanelBean model = data.getParcelableExtra("BetData");
                    if (model != null) {
                        LIST_PANEL_DATA.add(model);
                        String totalBet = Integer.toString(LIST_PANEL_DATA.size());
                        tvNoOfBets.setText(totalBet);
                        calculateTotalAmount();
                        //showToast(getString(R.string.bet_addad_successfully));
                        Utils.showGreenToast(BetSelectionActivity.this, getString(R.string.bet_addad_successfully));
                    }
                }
                break;
            case REQUEST_CODE_LUCKY_SIX_SIDE_BET:
                if (data != null) {
                    ArrayList<PanelBean> listModel = data.getParcelableArrayListExtra("ListBetData");
                    if (listModel != null) {
                        LIST_PANEL_DATA.addAll(listModel);
                        String totalBet = Integer.toString(LIST_PANEL_DATA.size());
                        tvNoOfBets.setText(totalBet);
                        calculateTotalAmount();
                        //showToast(getString(R.string.bet_addad_successfully));
                        Utils.showGreenToast(BetSelectionActivity.this, getString(R.string.bet_addad_successfully));
                    }
                }
                break;
            case REQUEST_CODE_PURCHASE_DETAILS:
                if (data != null) {
                    ArrayList<PanelBean> listModel = data.getParcelableArrayListExtra("ListBetData");
                    if (listModel != null) {
                        LIST_PANEL_DATA.clear();
                        LIST_PANEL_DATA.addAll(listModel);
                        String totalBet = Integer.toString(LIST_PANEL_DATA.size());
                        tvNoOfBets.setText(totalBet);
                        calculateTotalAmount();
                    }

                    try {
                        if (data.getExtras() != null && data.getExtras().getBoolean("doBalanceUpdate")) {
                            if (!Utils.isNetworkConnected(BetSelectionActivity.this)) {
                                Toast.makeText( BetSelectionActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                            } else {
                                isFromBalanceRefresh = false;
                                ProgressBarDialog.getProgressDialog().showProgress(BetSelectionActivity.this);
                                viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_CODE_FIVE_BY_NINETY_MAIN_BET:
                if (data != null) {
                    PanelBean model = data.getParcelableExtra("BetDataFiveByNinety");
                    if (model != null) {
                        LIST_PANEL_DATA.add(model);
                        String totalBet = Integer.toString(LIST_PANEL_DATA.size());
                        tvNoOfBets.setText(totalBet);
                        calculateTotalAmount();
                        //showToast(getString(R.string.bet_addad_successfully));
                        Utils.showGreenToast(BetSelectionActivity.this, getString(R.string.bet_addad_successfully));
                    }
                }
                break;
        }
    }

    private void calculateTotalAmount() {
        double amount = 0;
        for (PanelBean model: LIST_PANEL_DATA)
            amount = amount + model.getAmount();
        int amt = (int) amount;
        //String strAmount = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(BetSelectionActivity.this)) + amt;
        String strAmount = String.valueOf(amt);
        try {
            strAmount = Utils.getBalanceToView(Double.parseDouble(strAmount), Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE()));
        } catch (Exception e) {
            e.printStackTrace();
            strAmount = String.valueOf(amt);
        }
        tvBetValue.setText(strAmount);
    }

    @Override
    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - LAST_CLICK_TIME < CLICK_GAP) {
            return;
        }
        LAST_CLICK_TIME = SystemClock.elapsedRealtime();
        switch (view.getId()) {
            case R.id.tvSideBetFirstBall:
                if (LIST_PANEL_DATA.size() < DrawGameData.getSelectedGame().getMaxPanelAllowed()) {
                    Intent intentFirstBall;
                    if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FiveByNinety"))
                        intentFirstBall = new Intent(this, FiveByNinetySideBetActivity.class);
                    else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("LuckySix"))
                        intentFirstBall = new Intent(this, LuckySixSideBetActivity.class);
                    else
                        intentFirstBall = new Intent(this, LuckySixSideBetActivity.class);
                    intentFirstBall.putExtra("GameResponse", DrawGameData.getSelectedGame());
                    intentFirstBall.putExtra("SelectedBet", MAP_FIRST_BALL);
                    intentFirstBall.putExtra("MenuBean", MENU_BEAN);
                    intentFirstBall.putExtra("Title", ((TextView) view).getText());
                    intentFirstBall.putExtra("GameCategory", 1);
                    intentFirstBall.putExtra("PanelSize", LIST_PANEL_DATA.size());
                    startActivityForResult(intentFirstBall, REQUEST_CODE_LUCKY_SIX_SIDE_BET);
                }
                else
                    Utils.showRedToast(BetSelectionActivity.this,getString(R.string.max_panel_limit));
                break;
            case R.id.tvSideBetLastBall:
                if (LIST_PANEL_DATA.size() < DrawGameData.getSelectedGame().getMaxPanelAllowed()) {
                    Intent intentLastBall;
                    if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FiveByNinety"))
                        intentLastBall = new Intent(this, FiveByNinetySideBetActivity.class);
                    else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("LuckySix"))
                        intentLastBall = new Intent(this, LuckySixSideBetActivity.class);
                    else
                        intentLastBall = new Intent(this, LuckySixSideBetActivity.class);
                    intentLastBall.putExtra("GameResponse", DrawGameData.getSelectedGame());
                    intentLastBall.putExtra("SelectedBet", MAP_LAST_BALL);
                    intentLastBall.putExtra("MenuBean", MENU_BEAN);
                    intentLastBall.putExtra("Title", ((TextView) view).getText());
                    intentLastBall.putExtra("GameCategory", 2);
                    intentLastBall.putExtra("PanelSize", LIST_PANEL_DATA.size());
                    startActivityForResult(intentLastBall, REQUEST_CODE_LUCKY_SIX_SIDE_BET);
                }
                else
                    Utils.showRedToast(BetSelectionActivity.this, getString(R.string.max_panel_limit));
                break;
            case R.id.tvSideBetFirstFive:
                if (LIST_PANEL_DATA.size() < DrawGameData.getSelectedGame().getMaxPanelAllowed()) {
                    Intent intentFirstFive;
                    if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FiveByNinety"))
                        intentFirstFive = new Intent(this, FiveByNinetySideBetActivity.class);
                    else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("LuckySix"))
                        intentFirstFive = new Intent(this, LuckySixSideBetActivity.class);
                    else
                        intentFirstFive = new Intent(this, LuckySixSideBetActivity.class);
                    intentFirstFive.putExtra("GameResponse", DrawGameData.getSelectedGame());
                    intentFirstFive.putExtra("SelectedBet", MAP_FIRST_FIVE);
                    intentFirstFive.putExtra("MenuBean", MENU_BEAN);
                    intentFirstFive.putExtra("Title", ((TextView) view).getText());
                    intentFirstFive.putExtra("GameCategory", 3);
                    intentFirstFive.putExtra("PanelSize", LIST_PANEL_DATA.size());
                    startActivityForResult(intentFirstFive, REQUEST_CODE_LUCKY_SIX_SIDE_BET);
                }
                else
                    Utils.showRedToast(BetSelectionActivity.this, getString(R.string.max_panel_limit));
                break;
            case R.id.rlBuy:
                if (LIST_PANEL_DATA.isEmpty()) {
                    Utils.showRedToast(BetSelectionActivity.this, getString(R.string.no_bet_selected));
                    return;
                }
                Intent intentPurchase = new Intent(this, PurchaseDetailsActivity.class);
                intentPurchase.putExtra("GameResponse", DrawGameData.getSelectedGame());
                intentPurchase.putExtra("ListPanel", LIST_PANEL_DATA);
                intentPurchase.putExtra("MenuBean", MENU_BEAN);
                startActivityForResult(intentPurchase, REQUEST_CODE_PURCHASE_DETAILS);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (!LIST_PANEL_DATA.isEmpty()) {
            int image;
            GameChangeListener listener = super::onBackPressed;
            if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("LuckySix"))
                image = R.drawable.lucky_6;
            else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FullRoulette"))
                image = R.drawable.spin_win;
            else if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FiveByNinety"))
                image = R.drawable.five_by_ninety;
            else
                image = R.drawable.icon_draw_game;
            Utils.showGameSwitchDialog(this, image, getString(R.string.items_in_cart_lost_msg), getString(R.string.do_you_want_to_leave), getString(R.string.stay), getString(R.string.leave), listener);
        }
        else
            super.onBackPressed();
    }

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (loginBean == null)
            Utils.showRedToast(BetSelectionActivity.this, getString(R.string.there_was_problem_connecting_to_server));
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                Utils.hideKeyboard(BetSelectionActivity.this);
                PlayerData.getInstance().setLoginData(BetSelectionActivity.this, loginBean);
                PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));

                if (isFromBalanceRefresh)
                    Utils.showGreenToast(BetSelectionActivity.this, getString(R.string.your_balance_has_been_updated));
                isFromBalanceRefresh = true;
                refreshBalance();
            } else {
                if (Utils.checkForSessionExpiryActivity(BetSelectionActivity.this, loginBean.getResponseData().getStatusCode(), BetSelectionActivity.this))
                    return;
                String errorMsg = Utils.getResponseMessage(BetSelectionActivity.this, RMS, responseData.getStatusCode());
                Utils.showRedToast(BetSelectionActivity.this, errorMsg);
            }
        } else {
            String errorMsg = Utils.getResponseMessage(BetSelectionActivity.this, RMS, loginBean.getResponseCode());
            Utils.showRedToast(BetSelectionActivity.this, errorMsg);
        }
    };
}
