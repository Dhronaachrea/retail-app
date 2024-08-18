package com.skilrock.retailapp.landscape_draw_games.ui.activities;

import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.BetAmountDialogLand;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.interfaces.RefreshGameListener;
import com.skilrock.retailapp.landscape_draw_games.adapter.DrawGameAdapter;
import com.skilrock.retailapp.landscape_draw_games.count_down_timers.LoadGameCountDownTimer;
import com.skilrock.retailapp.landscape_draw_games.count_down_timers.MyCountDownTimer;
import com.skilrock.retailapp.landscape_draw_games.ui.fragments.FiveByNinetyGameFragment;
import com.skilrock.retailapp.landscape_draw_games.ui.fragments.LuckySixGameFragment;
import com.skilrock.retailapp.landscape_draw_games.ui.fragments.SpinAndWinGameFragment;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataRequestBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetyBetAmountBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketReprintBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.portrait_draw_games.ui.PrintDrawGameActivity;
import com.skilrock.retailapp.portrait_draw_games.ui.ResultDrawDialogActivity;
import com.skilrock.retailapp.sle_game_portrait.ResponseLis;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.DrawGameData;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.MyViewPagerFieldX;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.SortGame;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.drawgames.DrawGameBaseViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

public class DrawGameBaseActivity extends BaseActivity implements AppConstants, View.OnClickListener,
        DrawGameAdapter.GameSelectionListener, ResponseLis {

    private DrawGameBaseViewModel viewModel;
    ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> menuBeanList = null;
    private DrawFetchGameDataResponseBean fetchGameDataBean = new DrawFetchGameDataResponseBean();
    private RecyclerView rvGames;
    private FrameLayout frameLayout;
    private String SELECTED_GAME_CODE = "";
    private String UPDATING_GAME_CODE = "";
    private TextView tvBalance;
    private LoadGameCountDownTimer countDownTimer;
    private HashMap<String, LoadGameCountDownTimer> countDownTimerHashMap = new HashMap<>();
    private RefreshGameListener listener;
    private DrawGameAdapter gameAdapter;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private TextView tvUserName;
    private Fragment BASE_FRAGMENT;
    private TextView tabOne;
    private HashMap<String, MyCountDownTimer> countDownTimers;
    private TextView tabTwo;
    private TextView tabThree;
    private TabLayout tabLayout;
    private MyViewPagerFieldX viewPager;
    private HashSet<String> updatingGameCodeMap = new HashSet<>();
    private boolean isApiCalling = false;
    private BetAmountDialogLand betAmountDialog;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> GAME_LIST = new ArrayList<>();
    private final int REQUEST_CODE_PRINT    = 1111;
    private final int REQUEST_CODE_CANCEL   = 2222;
    private final int REQUEST_CODE_SNW      = 3333;
    private boolean IS_SALE_RESPONSE_SUCCESS = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_draw_game_base);
        setInitialParameters();
        setToolBar();
        initializeWidgets();
        viewModel.getLiveData().observe(this, observer);
        viewModel.getLiveDataTimer().observe(this, observerTimeFinished);
        viewModel.getLiveDataReprint().observe(this, observerReprint);
        viewModel.getLiveDataCancel().observe(this, observerCancel);
        viewModel.getSpinAndWindSaleData().observe(this, observerSale);
        viewModel.getLiveDataBalance().observe(this, observerBalance);
        callFetchGameDataApi(true, "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.refreshBalance();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //TextView tvTitle = findViewById(R.id.tvTitle);
            //tvTitle.setText(bundle.getString("title"));
            menuBeanList = bundle.getParcelableArrayList("DrawGamesModule");
            if (menuBeanList != null) {
                for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList model : menuBeanList) {
                    if (model.getMenuCode().equalsIgnoreCase(AppConstants.DGE_GAME_LIST)) {
                        menuBean = model;
                        break;
                    }
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            DrawGameBaseActivity.this.finish();
        }
    }

    private void setToolBar() {
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeWidgets() {
        viewModel = ViewModelProviders.of(this).get(DrawGameBaseViewModel.class);
        //tvUserName = findViewById(R.id.tvUserName);
        rvGames = findViewById(R.id.rvGames);
        frameLayout = findViewById(R.id.frameLayout);
        //tvUserName.setText(PlayerData.getInstance().getUsername().toUpperCase());
    }

    public void onClickBack(View view) {
        finish();
    }

    public void callBalanceApiParent(View view) {
        callBalanceApi(null);
    }

    private synchronized void setGameAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> games, int index) {
        GAME_LIST.clear();
        GAME_LIST.addAll(games);

        if (GAME_LIST.size() > 0) {
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO : GAME_LIST) {
                if (SELECTED_GAME_CODE.equalsIgnoreCase(gameRespVO.getGameCode()))
                    gameRespVO.setSelected(true);

                if (UPDATING_GAME_CODE.equalsIgnoreCase(gameRespVO.getGameCode())) {
                    gameRespVO.setCountDownStarted(false);
                    Log.d("setCountDownStarted", gameRespVO.getGameCode() + " : " + UPDATING_GAME_CODE);
                }
            }
            gameAdapter.notifyDataSetChanged();

        } else
            Toast.makeText(this, R.string.no_game_found, Toast.LENGTH_LONG).show();
    }

    private synchronized void setGameAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> games) {
        GAME_LIST.clear();
        GAME_LIST.addAll(games);
        if (GAME_LIST.size() > 0) {
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO : GAME_LIST) {
                gameRespVO.setCountDownStarted(false);
            }
            gameAdapter = new DrawGameAdapter(GAME_LIST, this, this, fetchGameDataBean.getResponseData().getCurrentDate(), true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rvGames.setLayoutManager(linearLayoutManager);
            rvGames.setAdapter(gameAdapter);
            gameAdapter.setSelection(0);
            onGameSelection(games.get(0));
            SELECTED_GAME_CODE = games.get(0).getGameCode();
        } else
            Toast.makeText(this, R.string.no_game_found, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGameSelection(DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGameObject) {
        if (SELECTED_GAME_CODE.equalsIgnoreCase(selectedGameObject.getGameCode()))
            return;

        if (SELECTED_GAME_CODE.equalsIgnoreCase(AppConstants.FIVE_BY_NINETY)) {
            FiveByNinetyGameFragment fiveByNinetyGameFragment = (FiveByNinetyGameFragment) getSupportFragmentManager().findFragmentByTag("FiveByNinetyGameFragment");
            if (fiveByNinetyGameFragment != null && fiveByNinetyGameFragment.isVisible() && fiveByNinetyGameFragment.getCartSize() > 0) {
                ConfirmationListener listener = () -> changeGame(selectedGameObject);
                Utils.showCustomConfirmationDialog(this, R.drawable.five_by_ninety, getString(R.string.you_have_item_in), getString(R.string.do_you_want_leave), getString(R.string.stay), getString(R.string.leave), listener);
            } else
                changeGame(selectedGameObject);
        } else if (SELECTED_GAME_CODE.equalsIgnoreCase(AppConstants.LUCKY_SIX)) {
            LuckySixGameFragment luckySixGameFragment = (LuckySixGameFragment) getSupportFragmentManager().findFragmentByTag("LuckySixGameFragment");
            if (luckySixGameFragment != null && luckySixGameFragment.isVisible() && luckySixGameFragment.getCartSize() > 0) {
                ConfirmationListener listener = () -> changeGame(selectedGameObject);
                Utils.showCustomConfirmationDialog(this, R.drawable.lucky_6_large, getString(R.string.you_have_item_in), getString(R.string.do_you_want_leave), getString(R.string.stay), getString(R.string.leave), listener);
            } else
                changeGame(selectedGameObject);
        } else
            changeGame(selectedGameObject);

    }

    public void changeGame(DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGameObject) {
        Log.e("draw", "Game: " + selectedGameObject.getGameCode());
        SELECTED_GAME_CODE = selectedGameObject.getGameCode();
        Bundle bundle = new Bundle();
        bundle.putParcelable("GameResponse", selectedGameObject);
        bundle.putParcelable("MenuBean", menuBean);
        bundle.putString("language", Locale.getDefault().getLanguage());
        if (selectedGameObject.getGameCode().equalsIgnoreCase(AppConstants.FIVE_BY_NINETY))
            loadFragment(new FiveByNinetyGameFragment(), "FiveByNinetyGameFragment", bundle);
        else if (selectedGameObject.getGameCode().equalsIgnoreCase(AppConstants.FULL_ROULETTE))
            loadFragment(new SpinAndWinGameFragment(), "SpinAndWinGameFragment", bundle);
        else if (selectedGameObject.getGameCode().equalsIgnoreCase(AppConstants.LUCKY_SIX))
            loadFragment(new LuckySixGameFragment(), "LuckySixGameFragment", bundle);
        else if (selectedGameObject.getGameCode().equalsIgnoreCase(AppConstants.THAI_LOTTERY))
            loadFragment(new LuckySixGameFragment(), "ThaiLotteryGameFragment", bundle);

        for (int index = 0; index < fetchGameDataBean.getResponseData().getGameRespVOs().size(); index++) {
            if (fetchGameDataBean.getResponseData().getGameRespVOs().get(index).getGameCode().equalsIgnoreCase(selectedGameObject.getGameCode())) {
                if (gameAdapter != null)
                    gameAdapter.setSelection(index);
                break;
            }
        }
    }

    public synchronized void loadFragment(Fragment fragment, String fragmentTag, Bundle args) {
        BASE_FRAGMENT = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragment.setArguments(args);
        transaction.replace(R.id.frameLayout, BASE_FRAGMENT, fragmentTag);
        transaction.commit();
    }

    private synchronized void callFetchGameDataApi(boolean isNormal, String gameCode) {
        Utils.vibrate(this);
        Utils.hideKeyboard(this);
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(menuBean, this, "fetchGames");
        DrawFetchGameDataRequestBean model = new DrawFetchGameDataRequestBean();

        if (urlBean != null) {
            //model.setLastTicketNumber(SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)));
            model.setLastTicketNumber("0");
            model.setRetailerId(PlayerData.getInstance().getUserId());
            model.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
        }

        if (isNormal) ProgressBarDialog.getProgressDialog().showProgress(this);

        if (isNormal)
            viewModel.callFetchGameData(urlBean, model);
        else {
            isApiCalling = true;
            viewModel.callFetchGameDataSync(urlBean, model, gameCode);
        }
    }

    Observer<DrawFetchGameDataResponseBean> observer = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null) {
            ErrorDialogListener listener = DrawGameBaseActivity.this::onDialogClosed;
            String errorMsg = getString(R.string.something_went_wrong);
            Utils.showCustomErrorDialog(DrawGameBaseActivity.this, getString(R.string.draw_games), errorMsg, listener);
        } else if (response.getResponseCode() == 0) {
            fetchGameDataBean = response;
            setCurrentTimeInGame(fetchGameDataBean);

            Collections.sort(fetchGameDataBean.getResponseData().getGameRespVOs(), new SortGame());
            setGameAdapter(fetchGameDataBean.getResponseData().getGameRespVOs());

        } else {
            ErrorDialogListener listener = DrawGameBaseActivity.this::onDialogClosed;
            String errorMsg = Utils.getResponseMessage(DrawGameBaseActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(DrawGameBaseActivity.this, getString(R.string.draw_games), errorMsg, listener);
        }
    };

    Observer<DrawFetchGameDataResponseBean> observerTimeFinished = response -> {
        countDownTimerHashMap.remove(response != null && response.getUpdatingGameCode() != null  ? response.getUpdatingGameCode() : UPDATING_GAME_CODE);
        updatingGameCodeMap.remove(response != null && response.getUpdatingGameCode() != null ? response.getUpdatingGameCode() : UPDATING_GAME_CODE);

        Log.d("updatingGameCodeMap", response != null && response.getUpdatingGameCode() != null ? response.getUpdatingGameCode(): UPDATING_GAME_CODE);

        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null) {
            ErrorDialogListener listener = DrawGameBaseActivity.this::onDialogClosed;
            String errorMsg = getString(R.string.something_went_wrong);
            Utils.showCustomErrorDialog(DrawGameBaseActivity.this, getString(R.string.draw_games), errorMsg, listener);
        } else if (response.getResponseCode() == 0) {
            listener.onShowLoader(response.getUpdatingGameCode(), false);

            updateGame(response.getUpdatingGameCode(), getGameResponseByCode(response.getUpdatingGameCode(), response), response);

            Collections.sort(fetchGameDataBean.getResponseData().getGameRespVOs(), new SortGame());
            for (int index = 0; index < fetchGameDataBean.getResponseData().getGameRespVOs().size(); index++) {
                if (fetchGameDataBean.getResponseData().getGameRespVOs().get(index).getGameCode().equalsIgnoreCase(SELECTED_GAME_CODE)) {
                    listener.onUpdateGame(fetchGameDataBean.getResponseData().getGameRespVOs().get(index));
                    Log.d("UPDATING_GAME_CODE", response.getUpdatingGameCode());

                    break;
                }
            }
            setGameAdapter(fetchGameDataBean.getResponseData().getGameRespVOs(), 0);
            UPDATING_GAME_CODE = "";

        } else {
            ErrorDialogListener listener = DrawGameBaseActivity.this::onDialogClosed;
            String errorMsg = Utils.getResponseMessage(DrawGameBaseActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(DrawGameBaseActivity.this, getString(R.string.draw_games), errorMsg, listener);
        }
    };

    Observer<TicketCancelResponseBean> observerCancel = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.cancel_ticket), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                refreshBalance();
            }
            Intent intent = new Intent(DrawGameBaseActivity.this, PrintDrawGameActivity.class);
            intent.putExtra("PrintDataCancel", response);
            intent.putExtra("Category", PrintDrawGameActivity.CANCEL);
            startActivityForResult(intent, REQUEST_CODE_CANCEL);
        } else {
            if (Utils.checkForSessionExpiryActivity(DrawGameBaseActivity.this, response.getResponseCode(), DrawGameBaseActivity.this))
                return;
            String errorMsg = Utils.getResponseMessage(DrawGameBaseActivity.this, "dge", response.getResponseCode());

            Utils.showCustomErrorDialog(this, getString(R.string.cancel_ticket) ,errorMsg);
        }
    };

    Observer<FiveByNinetySaleResponseBean> observerReprint = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            Intent intent = new Intent(DrawGameBaseActivity.this, PrintDrawGameActivity.class);
            intent.putExtra("PrintData", response);
            intent.putExtra("Category", PrintDrawGameActivity.REPRINT);
            startActivityForResult(intent, REQUEST_CODE_PRINT);
        } else {
            if (Utils.checkForSessionExpiryActivity(DrawGameBaseActivity.this, response.getResponseCode(), DrawGameBaseActivity.this))
                return;

            String errorMsg = Utils.getResponseMessage(DrawGameBaseActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), errorMsg);
        }

    };

    Observer<FiveByNinetySaleResponseBean> observerSale = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        IS_SALE_RESPONSE_SUCCESS = false;
        if (response == null)
            Utils.showCustomErrorDialog(this, DrawGameData.getSelectedGame().getGameName(), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            IS_SALE_RESPONSE_SUCCESS = true;
            /*Intent intent = new Intent(DrawGameBaseActivity.this, PrintDrawGameActivity.class);
            intent.putExtra("PrintData", response);
            intent.putExtra("Category", PrintDrawGameActivity.SALE);
            startActivityForResult(intent, REQUEST_CODE_PRINT);*/
            /*print(response, "");
            LIST_PANEL.clear();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("ListBetData", LIST_PANEL);
            setResult(Activity.RESULT_OK, returnIntent);
            PurchaseDetailsActivity.this.finish();*/
        } else {
            if (response.getResponseCode() != 1004) {
                if (Utils.checkForSessionExpiryActivity(DrawGameBaseActivity.this, response.getResponseCode(), DrawGameBaseActivity.this))
                    return;
            }

            String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
            Utils.showCustomErrorDialog(this, DrawGameData.getSelectedGame().getGameName(), errorMsg);
        }
    };

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (loginBean == null)
            Toast.makeText(DrawGameBaseActivity.this, getString(R.string.there_was_problem_connecting_to_server), Toast.LENGTH_SHORT).show();
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                Utils.hideKeyboard(DrawGameBaseActivity.this);
                PlayerData.getInstance().setLoginData(DrawGameBaseActivity.this, loginBean);
                PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));
               // Toast.makeText(DrawGameBaseActivity.this, getString(R.string.your_balance_has_been_updated), Toast.LENGTH_SHORT).show();
                super.refreshBalance();
            } else {
                if (Utils.checkForSessionExpiryActivity(DrawGameBaseActivity.this, loginBean.getResponseData().getStatusCode(), DrawGameBaseActivity.this))
                    return;
                String errorMsg = Utils.getResponseMessage(DrawGameBaseActivity.this, RMS, responseData.getStatusCode());
                Toast.makeText(DrawGameBaseActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            String errorMsg = Utils.getResponseMessage(DrawGameBaseActivity.this, RMS, loginBean.getResponseCode());
            Toast.makeText(DrawGameBaseActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO getGameResponseByCode(String gameCode, DrawFetchGameDataResponseBean responseBean) {
        for (int index = 0; index < responseBean.getResponseData().getGameRespVOs().size(); index++) {
            if (responseBean.getResponseData().getGameRespVOs().get(index).getGameCode().equalsIgnoreCase(gameCode)) {
                return responseBean.getResponseData().getGameRespVOs().get(index);
            }
        }

        return null;
    }

    private void setCurrentTimeInGame(DrawFetchGameDataResponseBean responseBean) {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO : responseBean.getResponseData().getGameRespVOs()) {
            gameRespVO.setCurrentDate(responseBean.getResponseData().getCurrentDate());
        }
    }

    private synchronized void updateGame(String code, DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO,
                                         DrawFetchGameDataResponseBean responseBean) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> gameAray =
                fetchGameDataBean.getResponseData().getGameRespVOs();

        for (int index = 0; index < gameAray.size(); index++) {
            if (code.equalsIgnoreCase(gameAray.get(index).getGameCode())) {
                gameRespVO.setCurrentDate(responseBean.getResponseData().getCurrentDate());
                gameAray.set(index, gameRespVO);
            }
        }
        fetchGameDataBean.getResponseData().setGameRespVOs(gameAray);
        fetchGameDataBean.getResponseData().setCurrentDate(responseBean.getResponseData().getCurrentDate());
    }

    void onDialogClosed() {
        DrawGameBaseActivity.this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
          /*  case R.id.ivBack:
                finish();
                break;
            case R.id.tvBalance:
                if (com.tablet.common.utils.Utils.connectivityExists(DrawGameBaseActivity.this)) {
                    JSONObject refresh = WeaverServices.getRefreshAmountForDrawGames(DrawGameBaseActivity.this);
                    if (refresh != null) {
                        WeaverServices.setRequestParams(this, WeaverServices.REFRESH_AMOUNT, "BALANCE", refresh.toString(), null, true);
                    } else {
                        com.tablet.common.utils.Utils.showServerErr(DrawGameBaseActivity.this);
                    }
                } else {
                    com.tablet.common.utils.Utils.showDataAlert(DrawGameBaseActivity.this, false);
                }
                break;
            case R.id.ivMenu:
                Toast.makeText(this, "Opening Menu", Toast.LENGTH_SHORT).show();
                break;*/
        }
    }

    public synchronized void refreshGame(String gameCode) {
        UPDATING_GAME_CODE = gameCode;
        callFetchGameDataApi(false, gameCode);
    }

    public void callFetchApi(String gameCode) {
        UPDATING_GAME_CODE = gameCode;
        updatingGameCodeMap.add(UPDATING_GAME_CODE);

        if (countDownTimerHashMap.get(gameCode) == null) {

            DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO = getGameResponseByCode(UPDATING_GAME_CODE);
            DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO drawRespVO = getDraw(gameRespVO);

            countDownTimer = LoadGameCountDownTimer.getMyCountDownTimer(getTimeDifference(drawRespVO.getDrawFreezeTime(), drawRespVO.getDrawSaleStopTime()));
            countDownTimer.startCountDownTimer(gameCode, true, DrawGameBaseActivity.this);
            countDownTimerHashMap.put(gameCode, countDownTimer);
        } else {
            countDownTimer = countDownTimerHashMap.get(gameCode);
         /*   if (countDownTimer != null)
                countDownTimer.stop();*/

            countDownTimer.startCountDownTimer(gameCode, true, DrawGameBaseActivity.this);
        }

        if (UPDATING_GAME_CODE.equalsIgnoreCase(SELECTED_GAME_CODE) && listener != null)
            listener.onShowLoader(UPDATING_GAME_CODE, true);
    }

    public boolean isLoadingFragment(String gameCode) {
        if (updatingGameCodeMap.isEmpty()) return false;

        if (updatingGameCodeMap.contains(gameCode)) {
            return true;
        }
        return false;
    }

    private long getTimeDifference(String fetchDate, String currentDate_) {
        long diff = 0;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date futureDate = dateFormat.parse(fetchDate);
            Date currentDate = dateFormat.parse(currentDate_);

            diff = futureDate.getTime() - currentDate.getTime();
            /*long seconds = diff / 1000;
            minutes = seconds / 60;*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    public void showOtherAmountDialog(ArrayList<FiveByNinetyBetAmountBean> LIST_BET_AMOUNT, BetAmountDialogLand.OnAmountUpdatedListener listener) {
        if (true) {
            if (betAmountDialog == null || !betAmountDialog.isShowing()) {
                betAmountDialog = new BetAmountDialogLand(DrawGameBaseActivity.this, LIST_BET_AMOUNT, listener);
                betAmountDialog.show();
                if (betAmountDialog.getWindow() != null) {
                    betAmountDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    betAmountDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            }
        }
    }

    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO getGameResponseByCode(String gameCode) {
        for (int index = 0; index < fetchGameDataBean.getResponseData().getGameRespVOs().size(); index++) {
            if (fetchGameDataBean.getResponseData().getGameRespVOs().get(index).getGameCode().equalsIgnoreCase(gameCode)) {
                return fetchGameDataBean.getResponseData().getGameRespVOs().get(index);
            }
        }

        return null;
    }

    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO getDraw(DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> arrayList = gameRespVO.getDrawRespVOs();
        Comparator<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> compareByOrder =
                (o1, o2) -> o1.getDrawDateTime().compareTo(o2.getDrawDateTime());
        Collections.sort(arrayList, compareByOrder);
        return arrayList.get(0);
    }

    public void callResult( HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN) {
        Intent intent = new Intent(DrawGameBaseActivity.this, ResultDrawDialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("MenuBean", MENU_BEAN);
        intent.putParcelableArrayListExtra("GameResponse", fetchGameDataBean.getResponseData().getGameRespVOs());
        startActivity(intent);
    }

    public void callCancel(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN) {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText( this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }

        ConfirmationListener confirmationListener = () -> {
            if (SharedPrefUtil.getLastGameCode(this, PrintUtil.LAST_GAME_CODE).equalsIgnoreCase("0")||SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)).equalsIgnoreCase("0")){
                Utils.showCustomErrorDialog(this,getString(R.string.cancel_ticket),getString(R.string.there_is_no_ticket_cancel));
                return;
            }
            UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "cancelTicket");
            if (urlBean != null) {
                ProgressBarDialog.getProgressDialog().showProgress(this);
                TicketCancelBean ticketCancelBean = new TicketCancelBean();
                ticketCancelBean.setGameCode(SharedPrefUtil.getLastGameCode(this, PrintUtil.LAST_GAME_CODE));
                ticketCancelBean.setAutoCancel(AppConstants.AUTO_CANCEL);
                ticketCancelBean.setCancelChannel(AppConstants.CHANNEL_TYPE);
                ticketCancelBean.setAutoCancel(false);
                ticketCancelBean.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
                ticketCancelBean.setUserId(PlayerData.getInstance().getUserId());
                ticketCancelBean.setTicketNumber(SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)));
                ticketCancelBean.setModelCode(Utils.getModelCode());
                ticketCancelBean.setTerminalId(Utils.getDeviceSerialNumber());
                viewModel.callCancel(urlBean, ticketCancelBean);
            }
        };

        Utils.showCancelTicketConfirmationDialog(DrawGameBaseActivity.this, confirmationListener);
    }

    public void callReprint(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN) {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText( this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        if (SharedPrefUtil.getLastGameCode(this, PrintUtil.LAST_GAME_CODE).equalsIgnoreCase("0")||SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)).equalsIgnoreCase("0")){
            Utils.showCustomErrorDialog(this,getString(R.string.reprint_ticket),getString(R.string.there_is_no_ticket_reprint));
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "reprintTicket");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            TicketReprintBean ticketReprintBean = new TicketReprintBean();
            ticketReprintBean.setGameCode(SharedPrefUtil.getLastGameCode(this, PrintUtil.LAST_GAME_CODE));
            ticketReprintBean.setPurchaseChannel("RETAIL");
            ticketReprintBean.setPwt(false);
            ticketReprintBean.setTicketNumber(SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)));
            viewModel.callReprint(urlBean, ticketReprintBean);
        }
    }

    public void refreshBalance() {
        ProgressBarDialog.getProgressDialog().showProgress(DrawGameBaseActivity.this);
        viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
    }

    public void registerListener(RefreshGameListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(String response, String requestedMethod) {

    }

}