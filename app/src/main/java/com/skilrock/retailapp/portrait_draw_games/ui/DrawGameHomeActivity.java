package com.skilrock.retailapp.portrait_draw_games.ui;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataRequestBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.models.drawgames.SpinAndWinSelectedNumbersBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketReprintBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.portrait_draw_games.adapter.DrawGamesAdapter;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.DrawGameData;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.SortGame;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.drawgames.DrawGameBaseViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawGameHomeActivity extends BaseActivity implements View.OnClickListener, DrawGamesAdapter.GameSelectionListener, DrawGamesAdapter.DrawResultTime, AppConstants {

    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private DrawGameBaseViewModel viewModel;
    private RecyclerView rvGames;
    ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> menuBeanList = null;
    private boolean isActivityActive = true;
    LinearLayout layout_winning_claim, layout_result, layout_cancel, layout_reprint;
    private final int REQUEST_CODE_PRINT    = 1111;
    private final int REQUEST_CODE_CANCEL   = 2222;
    private final int REQUEST_CODE_SNW      = 3333;
    private LinearLayout viewMenuMain, viewGameMain;
    private TextView tvUserBalanceMain, tvUsernameMain;
    private WebView webView;
    private ArrayList<PanelBean> listPanelBean = new ArrayList<>();
    private HashMap<String, SelectedPickDetails> MAP_PICKED_DATA = new HashMap<>();
    public static long LAST_CLICK_TIME              = 0;
    public static int CLICK_GAP                     = 200;
    private final int REQUEST_CODE_PURCHASE_DETAILS = 3030;
    private  LinearLayout llUtility;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_game_home);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        initializeWidgets();

        callFetchApi();
        Log.d("TAg", "1234567890");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }

    private void initializeWidgetsGame() {
        /*webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(new WebViewClientImpl(DrawGameHomeActivity.this));
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //webView.getSettings().setAppCacheMaxSize(5 *1024* 1024); // 5MB
        webView.getSettings().setDomStorageEnabled(true);
        //webView.getSettings().setAppCachePath("/data/data/"+ getPackageName()+"/cache");
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        WebView.setWebContentsDebuggingEnabled(true);
        //webView.addJavascriptInterface(this, "JSInterface");
        webView.addJavascriptInterface(new JavaScriptInterface(this, webView), "Android");
        String url = null;
        try {
            JSONObject jsonObject = new JSONObject(MENU_BEAN.getApiDetails());
            //   url = jsonObject.getJSONObject("spinAndWin").getString("url");
            url = jsonObject.getJSONObject("spinAndWinDomain").getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (url != null) {
            url = url + "?gameData=" + createJsonForUrl();
            Log.i("log", "SpinAndWin URL: " + url);
            webView.loadUrl(url);
        } else
            Utils.showRedToast(DrawGameHomeActivity.this, getString(R.string.something_went_wrong));*/
        llUtility.setVisibility(View.VISIBLE);
    }


    private String createJsonForUrl() {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGame = getSpinAndWinData();
        Log.i("log", "Selected Game: " + selectedGame.getGameCode());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("unitPrice", selectedGame.getBetRespVOs().get(0).getUnitPrice());
            jsonObject.put("maxBetAmtMul", selectedGame.getBetRespVOs().get(0).getMaxBetAmtMul());
            jsonObject.put("maxPanelAllowed", selectedGame.getMaxPanelAllowed());
            jsonObject.put("language", Locale.getDefault().getLanguage());
            ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> ball = selectedGame.getNumberConfig().getRange().get(0).getBall();
            JSONArray jsonArray = new JSONArray();
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball data: ball) {
                JSONObject jsonBallObject = new JSONObject();
                jsonBallObject.put("color", data.getColor());
                jsonBallObject.put("label", data.getLabel());
                jsonBallObject.put("number", data.getNumber());
                jsonArray.put(jsonBallObject);
            }
            jsonObject.put("ball", jsonArray);
            Log.i("log", "JSON: " + jsonObject.toString());
            return jsonObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void setToolBar() {
        ImageView ivGameIcon    = findViewById(R.id.ivGameIcon);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);

        //tvTitle.setText(getSpinAndWinData().getGameName());
        //ivGameIcon.setImageDrawable(DrawGameHomeActivity.this.getResources().getDrawable(R.drawable.spin_win));
        tvUsername.setTextColor(getResources().getColor(R.color.white));
        refreshBalance();
    }

    public class JavaScriptInterface {
        DrawGameHomeActivity parentActivity;
        WebView mWebView;

        JavaScriptInterface(DrawGameHomeActivity _activity, WebView _webView)  {
            parentActivity = _activity;
            mWebView = _webView;
        }

        @JavascriptInterface
        public void getGameData(String data) {
            if (!TextUtils.isEmpty(data)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                SpinAndWinSelectedNumbersBean model = gson.fromJson(data, SpinAndWinSelectedNumbersBean.class);
                Log.d("log", "Data: " + model);

                ArrayList<SpinAndWinSelectedNumbersBean.SelectedData> listSelectedData = new ArrayList<>();

                for (SpinAndWinSelectedNumbersBean.SelectedData selectedData: model.getSelectedData()) {
                    if (listSelectedData.contains(selectedData)) {
                        for (int index=0; index<listSelectedData.size(); index++) {
                            if (selectedData.getPickType().equalsIgnoreCase(listSelectedData.get(index).getPickType())
                                    && selectedData.getSelectedNumbers().equalsIgnoreCase(listSelectedData.get(index).getSelectedNumbers())) {
                                String amt = String.valueOf(Integer.parseInt(listSelectedData.get(index).getAmount())
                                        + Integer.parseInt(selectedData.getAmount()));
                                listSelectedData.get(index).setAmount(amt);

                                break;
                            }
                        }
                    }
                    else listSelectedData.add(selectedData);
                }

                model.setSelectedData(listSelectedData);
                Log.i("log", "Data: " + model);

                addBet(model);
            } else Utils.showRedToast(DrawGameHomeActivity.this, getString(R.string.please_play_game));
        }

        @JavascriptInterface
        public void showAndroidToast(String message) {
            Utils.showRedToast(DrawGameHomeActivity.this, message);
        }
    }

    private void addBet(SpinAndWinSelectedNumbersBean model) {
        /* ----- ALL PICK CODES -----
            Direct1 Direct2 Direct3 Direct4 Direct6 Direct12
            FirstBallLesser FirstBallBetween FirstBallRed FirstBallBalck FirstBallOdd FirstBallEven*/
        ArrayList<SpinAndWinSelectedNumbersBean.SelectedData> selectedData = model.getSelectedData();
        listPanelBean.clear();
        for (SpinAndWinSelectedNumbersBean.SelectedData data: selectedData) {
            if (data.getPickType().equalsIgnoreCase("None")) {
                String[] arrSelectedNumbers = data.getSelectedNumbers().split(",");
                SelectedPickDetails selectedPickDetails = null;
                if (arrSelectedNumbers.length == 1)
                    selectedPickDetails = MAP_PICKED_DATA.get("Direct1");
                else if (arrSelectedNumbers.length == 2)
                    selectedPickDetails = MAP_PICKED_DATA.get("Direct2");
                else if (arrSelectedNumbers.length == 3)
                    selectedPickDetails = MAP_PICKED_DATA.get("Direct3");
                else if (arrSelectedNumbers.length == 4)
                    selectedPickDetails = MAP_PICKED_DATA.get("Direct4");
                else if (arrSelectedNumbers.length == 6)
                    selectedPickDetails = MAP_PICKED_DATA.get("Direct6");
                else if (arrSelectedNumbers.length == 12)
                    selectedPickDetails = MAP_PICKED_DATA.get("Direct12");

                if (selectedPickDetails != null)
                    listPanelBean.add(setDataToPanelBean(data, selectedPickDetails));
                else {
                    Utils.showRedToast(DrawGameHomeActivity.this, getString(R.string.some_technical_issue));
                    return;
                }
            } else {
                SelectedPickDetails selectedPickDetails = MAP_PICKED_DATA.get(data.getPickType());
                if (selectedPickDetails != null)
                    listPanelBean.add(setDataToPanelBean(data, selectedPickDetails));
                else {
                    Utils.showRedToast(DrawGameHomeActivity.this, getString(R.string.something_went_wrong));
                    return;
                }
            }
        }

        Log.w("log", "PanelBean: " + listPanelBean);
        Intent intentPurchase = new Intent(this, PurchaseDetailsActivity.class);
        intentPurchase.putExtra("GameResponse", DrawGameData.getSelectedGame());
        intentPurchase.putExtra("ListPanel", listPanelBean);
        intentPurchase.putExtra("MenuBean", MENU_BEAN);
        startActivityForResult(intentPurchase, REQUEST_CODE_PURCHASE_DETAILS);

    }

    private PanelBean setDataToPanelBean(SpinAndWinSelectedNumbersBean.SelectedData data, SelectedPickDetails selectedPickDetails) {
        PanelBean panelBean = new PanelBean();
        String[] split = data.getSelectedNumbers().split(",");
        panelBean.setListSelectedNumber(new ArrayList<>(Arrays.asList(split)));
        if (selectedPickDetails.getWinMode().equalsIgnoreCase("MAIN"))
            panelBean.setPickedValues(data.getSelectedNumbers());
        else
            panelBean.setPickedValues(selectedPickDetails.getPickValue());
        panelBean.setGameName(DrawGameData.getSelectedGame().getGameName());
        panelBean.setAmount(Integer.parseInt(data.getAmount()));
        panelBean.setWinMode(selectedPickDetails.getWinMode());
        panelBean.setBetName(selectedPickDetails.getBetDispName());
        panelBean.setPickName(selectedPickDetails.getPickName());
        panelBean.setBetCode(selectedPickDetails.getBetCode());
        panelBean.setPickCode(selectedPickDetails.getPickCode());
        panelBean.setPickConfig(selectedPickDetails.getPickConfig());
        int betAmountMultiple = (int) (Integer.parseInt(data.getAmount()) / DrawGameData.getSelectedGame().getBetRespVOs().get(0).getUnitPrice());
        //panelBean.setBetAmountMultiple(betAmountMultiple);
        panelBean.setBetAmountMultiple(betAmountMultiple);
        panelBean.setSelectedBetAmount(Integer.parseInt(data.getAmount()));
        panelBean.setNumberOfDraws(1);
        panelBean.setNumberOfLines(1);
        panelBean.setQuickPick(false);
        panelBean.setQpPreGenerated(false);
        if (selectedPickDetails.getWinMode().equalsIgnoreCase("MAIN"))
            panelBean.setMainBet(true);
        else
            panelBean.setMainBet(false);
        panelBean.setColorCode("");

        return panelBean;
    }

    public class WebViewClientImpl extends WebViewClient {

        private Activity activity = null;

        public WebViewClientImpl(Activity activity) {
            this.activity = activity;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            //if(url.indexOf("index.php") > -1 ) return false;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //Toast.makeText(activity, "onPageFinished", Toast.LENGTH_SHORT).show();

            webView.loadUrl("javascript:showAlert()");
        }
    }

    @Override
    protected void onResume() {
        isActivityActive = true;
        super.onResume();
        //ProgressBarDialog.getProgressDialog().showProgress(DrawGameHomeActivity.this);
        viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            TextView tvTitle = findViewById(R.id.tvTitleMenu);
            tvTitle.setText(bundle.getString("title"));
            menuBeanList = bundle.getParcelableArrayList("DrawGamesModule");
            if (menuBeanList != null) {
                for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList model : menuBeanList) {
                    if (model.getMenuCode().equalsIgnoreCase(AppConstants.DGE_GAME_LIST)) {
                        MENU_BEAN = model;
                        break;
                    }
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            DrawGameHomeActivity.this.finish();
        }
    }

    private void initializeWidgets() {
        viewModel               = ViewModelProviders.of(this).get(DrawGameBaseViewModel.class);
        llBalance               = findViewById(R.id.llBalance);
        tvUserBalanceMain       = findViewById(R.id.tvBalMain);
        tvUsernameMain          = findViewById(R.id.tvUserNameMain);
        Button btnViewMyTicket  = findViewById(R.id.btnViewMyTicket);
        LinearLayout llBack     = findViewById(R.id.llBack);
        rvGames                 = findViewById(R.id.rvGames);
        layout_winning_claim    = findViewById(R.id.layout_winning_claim);
        layout_cancel           = findViewById(R.id.layout_cancel);
        layout_reprint          = findViewById(R.id.layout_reprint);
        layout_result           = findViewById(R.id.layout_result);
        viewGameMain            = findViewById(R.id.view_web_main);
        viewMenuMain            = findViewById(R.id.view_menu);
        ImageView ivBack        = findViewById(R.id.ivBack);
        llUtility              = findViewById(R.id.ll_utility);
        viewModel.getLiveData().observe(this, observer);
        viewModel.getLiveDataReprint().observe(this, observerReprint);
        viewModel.getLiveDataCancel().observe(this, observerCancel);
        viewModel.getLiveDataBalance().observe(this, observerBalance);

        btnViewMyTicket.setOnClickListener(this);
        layout_winning_claim.setOnClickListener(this);
        layout_cancel.setOnClickListener(this);
        layout_reprint.setOnClickListener(this);
        layout_result.setOnClickListener(this);
        llBack.setOnClickListener(this);
        //ivBack.setOnClickListener(this);
        //refreshBalance();
    }

    private void loadGames(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> games) {
        setGameData();
        rvGames.setHasFixedSize(true);
        rvGames.setLayoutManager(new GridLayoutManager(this, 2));
        rvGames.setVisibility(View.VISIBLE);
        DrawGamesAdapter homeModuleAdapter = new DrawGamesAdapter(this, games, this, DrawGameData.getFullResponse().getResponseData().getCurrentDate(), this);
        rvGames.setAdapter(homeModuleAdapter);
    }

    private void callFetchApi() {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText( this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        Utils.vibrate(this);
        Utils.hideKeyboard(this);
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "fetchGames");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            DrawFetchGameDataRequestBean model = new DrawFetchGameDataRequestBean();
            //model.setLastTicketNumber(SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)));
            model.setLastTicketNumber("0");
            model.setRetailerId(PlayerData.getInstance().getUserId());
            model.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            viewModel.callFetchGameData(urlBean, model);
        }
    }

    Observer<DrawFetchGameDataResponseBean> observer = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null) {
            ErrorDialogListener listener = DrawGameHomeActivity.this::onDialogClosed;
            Utils.showCustomErrorDialog(DrawGameHomeActivity.this, getString(R.string.draw_games), getString(R.string.something_went_wrong), listener);
        } else if (response.getResponseCode() == 0) {
            //FETCH_GAME_DATA = response;
            DrawGameData.setFullResponse(response);
            Collections.sort(DrawGameData.getFullResponse().getResponseData().getGameRespVOs(), new SortGame());
            loadGames(DrawGameData.getFullResponse().getResponseData().getGameRespVOs());
            initializeWidgetsGame();
            //setMapForPicks();
            setToolBar();
        } else {
            if (Utils.checkForSessionExpiryActivity(DrawGameHomeActivity.this, response.getResponseCode(), DrawGameHomeActivity.this))
                return;

            ErrorDialogListener listener = DrawGameHomeActivity.this::onDialogClosed;
            //String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
            String errorMsg = Utils.getResponseMessage(DrawGameHomeActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(DrawGameHomeActivity.this, getString(R.string.draw_games), errorMsg, listener);
        }
    };

    public void callFetchApiForTimer(String gameCode) {
        APIClient client = APIConfig.getInstance().create(APIClient.class);
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "fetchGames");
        try {
            if (urlBean != null && PlayerData.getInstance().getUserId() != null && PlayerData.getInstance().getToken() != null) {
                DrawFetchGameDataRequestBean model = new DrawFetchGameDataRequestBean();
                //model.setLastTicketNumber(SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)));
                model.setLastTicketNumber("0");
                model.setRetailerId(PlayerData.getInstance().getUserId());
                model.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
                final Call<DrawFetchGameDataResponseBean> apiCall = client.fetchGameData(urlBean.getUrl(), urlBean.getContentType(), urlBean.getUserName(), urlBean.getPassword(), model);

                Log.d("log", "FetchGameData Request: " + apiCall.request().toString());

                apiCall.enqueue(new Callback<DrawFetchGameDataResponseBean>() {
                    @Override
                    public void onResponse(@NonNull Call<DrawFetchGameDataResponseBean> call, @NonNull Response<DrawFetchGameDataResponseBean> response) {
                        sendMessage(gameCode, 0);
                        //ProgressBarDialog.getProgressDialog().dismiss();
                        if (response.body() == null || !response.isSuccessful()) {
                            if (response.body() == null || !response.isSuccessful()) {
                                if (response.errorBody() != null) {
                                    return;
                                }
                                return;
                            }
                        }
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        Log.i("log", "FetchGameData API Response: " + gson.toJson(response.body()));
                        if (response.body().getResponseCode() == 0) {
                            //FETCH_GAME_DATA = response.body();
                            DrawGameData.setFullResponse(response.body());
                            Collections.sort(DrawGameData.getFullResponse().getResponseData().getGameRespVOs(), new SortGame());
                            loadGames(DrawGameData.getFullResponse().getResponseData().getGameRespVOs());
                            //Toast.makeText(DrawGameHomeActivity.this, "FETCH API CALLED", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DrawFetchGameDataResponseBean> call, @NonNull Throwable throwable) {
                        sendMessage(gameCode, 0);
                        //ProgressBarDialog.getProgressDialog().dismiss();
                        Log.e("log", "FetchGameData API failed: " + throwable.toString());
                    }
                });
            } else {
                Utils.checkForSessionExpiryActivity(DrawGameHomeActivity.this, 1015, DrawGameHomeActivity.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.checkForSessionExpiryActivity(DrawGameHomeActivity.this, 1015, DrawGameHomeActivity.this);
        }
    }

    void onDialogClosed() {
        DrawGameHomeActivity.this.finish();
    }

    public void onClickBack(View view) {
        setGameVisiblity(false);
    }

    private void setGameVisiblity(boolean visiblity) {
        viewGameMain.setVisibility(visiblity ? View.VISIBLE : View.INVISIBLE);
        viewMenuMain.setVisibility(visiblity ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onGameSelection(DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGameObject) {
        if (selectedGameObject.getGameCode().equalsIgnoreCase("FullRoulette")) {
            //Toast.makeText(this, "Work In Progress!", Toast.LENGTH_SHORT).show();
            DrawGameData.setSelectedGame(selectedGameObject);
           /* Intent intent = new Intent(DrawGameHomeActivity.this, SpinAndWinActivity.class);
            intent.putExtra("MenuBean", MENU_BEAN);
            intent.putExtra("language", Locale.getDefault().getLanguage());
            startActivityForResult(intent, REQUEST_CODE_SNW);*/

            setGameVisiblity(true);
            return;
        }
        //GAME_DATA = selectedGameObject;
        DrawGameData.setSelectedGame(selectedGameObject);
        Intent intent = new Intent(DrawGameHomeActivity.this, BetSelectionActivity.class);
        //intent.putExtra("GameResponse", selectedGameObject);
        intent.putExtra("MenuBean", MENU_BEAN);
        startActivity(intent);
    }

    @Override
    public void onDrawResultTime(DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGameObject) {
        long diff = Utils.getTimeDifference(selectedGameObject.getDrawRespVOs().get(0).getDrawFreezeTime(), selectedGameObject.getDrawRespVOs().get(0).getDrawSaleStopTime()) + 3000;
        /*if (isActivityActive)
            ProgressBarDialog.getProgressDialog().showProgress(this);*/
        sendMessage(selectedGameObject.getGameCode(), 1);
        if (isActivityActive)
            Toast.makeText(DrawGameHomeActivity.this, getString(R.string.updating_draws), Toast.LENGTH_LONG).show();
        new CountDownTimer(diff, 100) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                callFetchApiForTimer(selectedGameObject.getGameCode());
            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnViewMyTicket) {
            //Toast.makeText(this, "View Ticket", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.layout_winning_claim) {
            Intent winning = new Intent(DrawGameHomeActivity.this, WinningClaimActivity.class);
            winning.putExtra("MenuBean", MENU_BEAN);
            startActivityForResult(winning, REQUEST_CODE_PRINT);
        } else if (view.getId() == R.id.layout_result)
            callResult();
        else if (view.getId() == R.id.layout_cancel)
            callCancel();
        else if (view.getId() == R.id.layout_reprint)
            callReprint();
        else if (view.getId() == R.id.llBack)
            DrawGameHomeActivity.this.finish();
    }

    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO getSpinAndWinData() {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> gameRespVOS =
                DrawGameData.getFullResponse().getResponseData().getGameRespVOs();

        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO data : gameRespVOS) {

            if (data.getGameCode().equalsIgnoreCase(AppConstants.SPIN_WIN))

                return data;
        }

        return null;
    }

    private void setMapForPicks() {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> listBetRespVOs = getSpinAndWinData().getBetRespVOs();
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO: listBetRespVOs) {

            ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> listPickType = betRespVO.getPickTypeData().getPickType();
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType: listPickType) {
                SelectedPickDetails selectedPickDetails = new SelectedPickDetails();
                selectedPickDetails.setBetCode(betRespVO.getBetCode());
                selectedPickDetails.setBetDispName(betRespVO.getBetDispName());
                selectedPickDetails.setBetName(betRespVO.getBetName());
                selectedPickDetails.setInputCount(betRespVO.getInputCount());
                selectedPickDetails.setMaxBetAmtMul(betRespVO.getMaxBetAmtMul());
                selectedPickDetails.setUnitPrice(betRespVO.getUnitPrice());
                selectedPickDetails.setWinMode(betRespVO.getWinMode());

                Log.d("log", "Pick Code: " + pickType.getCode());
                selectedPickDetails.setPickCode(pickType.getCode());
                selectedPickDetails.setPickName(pickType.getName());
                selectedPickDetails.setPickConfig(pickType.getRange().get(0).getPickConfig());
                selectedPickDetails.setPickCount(pickType.getRange().get(0).getPickCount());
                selectedPickDetails.setPickMode(pickType.getRange().get(0).getPickMode());
                selectedPickDetails.setPickValue(pickType.getRange().get(0).getPickValue());
                selectedPickDetails.setQpAllowed(pickType.getRange().get(0).getQpAllowed());
                MAP_PICKED_DATA.put(pickType.getCode(), selectedPickDetails);
            }
        }
        //Log.w("log", "MAP_PICKED_DATA: " + MAP_PICKED_DATA);
    }
    private void setGameData() {
        if (DrawGameData.getSelectedGame() != null && DrawGameData.getFullResponse() != null) {
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO model : DrawGameData.getFullResponse().getResponseData().getGameRespVOs()) {
                if (model.getGameCode().equalsIgnoreCase(DrawGameData.getSelectedGame().getGameCode())) {
                    //GAME_DATA = model;
                    DrawGameData.setSelectedGame(model);
                    break;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isBalanceUpdate")) {
            //ProgressBarDialog.getProgressDialog().showProgress(DrawGameHomeActivity.this);
            viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
        } else if (data != null && data.getExtras() != null && data.getExtras().containsKey("isBalanceUpdate")) {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), errorMsg);
        }
        switch (requestCode) {
            case REQUEST_CODE_PURCHASE_DETAILS:
                if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isSaleResponseSuccess")) {
                    setGameVisiblity(false);
                    //ProgressBarDialog.getProgressDialog().showProgress(DrawGameHomeActivity.this);
                    viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
                } else {
                    if (data != null && data.getExtras() != null && data.getExtras().containsKey("ListBetData")) {
                        listPanelBean = data.getParcelableArrayListExtra("ListBetData");
                    }
                }
                break;
        }
    }

    private void sendMessage(String gameCode, int flag) {
        // 1 means start loader
        // 0 means stop loader
        Intent intent = new Intent("DrawListener");
        intent.putExtra("GameCode", gameCode);
        intent.putExtra("LoaderFlag", flag);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void callCancel() {
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

        Utils.showCancelTicketConfirmationDialog(DrawGameHomeActivity.this, confirmationListener);
    }

    Observer<TicketCancelResponseBean> observerCancel = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.cancel_ticket), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
           /* usbThermalPrinter = getUsbThermalPrinter();
            PrintUtil.getPrintDataTelpoCancelTicket(response, this, usbThermalPrinter);*/
            Intent intent = new Intent(DrawGameHomeActivity.this, PrintDrawGameActivity.class);
            intent.putExtra("PrintDataCancel", response);
            intent.putExtra("Category", PrintDrawGameActivity.CANCEL);
            startActivityForResult(intent, REQUEST_CODE_CANCEL);
        } else {
            if (Utils.checkForSessionExpiryActivity(DrawGameHomeActivity.this, response.getResponseCode(), DrawGameHomeActivity.this))
                return;

            String errorMsg = Utils.getResponseMessage(DrawGameHomeActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.cancel_ticket), errorMsg);
        }
    };
    private void callReprint() {
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

    @Override
    protected void onPause() {
        isActivityActive = false;
        super.onPause();
    }

    Observer<FiveByNinetySaleResponseBean> observerReprint = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            Intent intent = new Intent(DrawGameHomeActivity.this, PrintDrawGameActivity.class);
            intent.putExtra("PrintData", response);
            intent.putExtra("Category", PrintDrawGameActivity.REPRINT);
            startActivityForResult(intent, REQUEST_CODE_PRINT);
        } else {
            if (Utils.checkForSessionExpiryActivity(DrawGameHomeActivity.this, response.getResponseCode(), DrawGameHomeActivity.this))
                return;

            //String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
            String errorMsg = Utils.getResponseMessage(DrawGameHomeActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), errorMsg);
        }

    };

    private void callResult() {
        Intent intent = new Intent(DrawGameHomeActivity.this, ResultDrawDialogActivity.class);
        intent.putExtra("MenuBean", MENU_BEAN);
        intent.putParcelableArrayListExtra("GameResponse", DrawGameData.getFullResponse().getResponseData().getGameRespVOs());
        startActivity(intent);

    }

    public void callBalanceApi(View view) {
        ProgressBarDialog.getProgressDialog().showProgress(DrawGameHomeActivity.this);
        viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
    }

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (loginBean == null)
            Toast.makeText(DrawGameHomeActivity.this, getString(R.string.there_was_problem_connecting_to_server), Toast.LENGTH_SHORT).show();
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                Utils.hideKeyboard(DrawGameHomeActivity.this);
                PlayerData.getInstance().setLoginData(DrawGameHomeActivity.this, loginBean);
                PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));
                //Toast.makeText(DrawGameHomeActivity.this, getString(R.string.your_balance_has_been_updated), Toast.LENGTH_SHORT).show();

                try {
                    String isDisplayUserBalance = ConfigData.getInstance().getConfigData().getDISPLAYUSERBALANCE();
                    String isHead = PlayerData.getInstance().getLoginData().getResponseData().getData().getIsHead();
                    if (isDisplayUserBalance.equalsIgnoreCase("YES") && isHead.equalsIgnoreCase("NO")) {
                        if (tvUsername != null)
                            tvUsername.setText(getString(R.string.cash_in_hand));
                        tvUsernameMain.setText(getString(R.string.cash_in_hand));
                        String balance;
                        balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? PlayerData.getInstance().getUserBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this)) : PlayerData.getInstance().getUserBalance();
                        tvUserBalanceMain.setText(balance);
                        if (tvUserBalance != null)
                            tvUserBalance.setText(balance);

                        tvUserBalanceMain.setTextColor(PlayerData.getInstance().getLoginData().getResponseData().getData().getRawUserBalance() < 0.0 ?
                                getResources().getColor(R.color.colorAppOrangeDark) : getResources().getColor(R.color.colorGreen));
                        if (tvUserBalance != null)
                            tvUserBalance.setTextColor(PlayerData.getInstance().getLoginData().getResponseData().getData().getRawUserBalance() < 0.0 ?
                                    getResources().getColor(R.color.colorAppOrangeDark) : getResources().getColor(R.color.colorGreen));
                    } else {
                        if (tvUsername != null)
                            tvUsername.setText(getString(R.string.balance_without_colon));
                        tvUsernameMain.setText(getString(R.string.balance_without_colon));
                        String balance;
                        balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? PlayerData.getInstance().getOrgBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this)) : PlayerData.getInstance().getOrgBalance();
                        if (tvUserBalance != null)
                            tvUserBalance.setText(balance);
                        tvUserBalanceMain.setText(balance);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    String balance;
                    balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ? Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this)) + PlayerData.getInstance().getOrgBalance() : PlayerData.getInstance().getOrgBalance();
                    tvUserBalanceMain.setText(balance);
                    if (tvUserBalance != null)
                        tvUserBalance.setText(balance);
                }
            } else {
                if (Utils.checkForSessionExpiryActivity(DrawGameHomeActivity.this, loginBean.getResponseData().getStatusCode(), DrawGameHomeActivity.this))
                    return;
                String errorMsg = Utils.getResponseMessage(DrawGameHomeActivity.this, RMS, responseData.getStatusCode());
                Toast.makeText(DrawGameHomeActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            String errorMsg = Utils.getResponseMessage(DrawGameHomeActivity.this, RMS, loginBean.getResponseCode());
            Toast.makeText(DrawGameHomeActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };
    public class SelectedPickDetails {

        @SerializedName("betCode")
        @Expose
        private String betCode;

        @SerializedName("betDispName")
        @Expose
        private String betDispName;

        @SerializedName("betName")
        @Expose
        private String betName;

        @SerializedName("inputCount")
        @Expose
        private String inputCount;

        @SerializedName("maxBetAmtMul")
        @Expose
        private Integer maxBetAmtMul;

        @SerializedName("pickCode")
        @Expose
        private String pickCode;

        @SerializedName("pickName")
        @Expose
        private String pickName;

        @SerializedName("pickConfig")
        @Expose
        private String pickConfig;

        @SerializedName("pickCount")
        @Expose
        private String pickCount;

        @SerializedName("pickMode")
        @Expose
        private String pickMode;

        @SerializedName("pickValue")
        @Expose
        private String pickValue;

        @SerializedName("qpAllowed")
        @Expose
        private String qpAllowed;

        @SerializedName("winMode")
        @Expose
        private String winMode;

        @SerializedName("unitPrice")
        @Expose
        private double unitPrice;

        public String getBetCode() {
            return betCode;
        }

        public void setBetCode(String betCode) {
            this.betCode = betCode;
        }

        public String getBetDispName() {
            return betDispName;
        }

        public void setBetDispName(String betDispName) {
            this.betDispName = betDispName;
        }

        public String getBetName() {
            return betName;
        }

        public void setBetName(String betName) {
            this.betName = betName;
        }

        public String getInputCount() {
            return inputCount;
        }

        public void setInputCount(String inputCount) {
            this.inputCount = inputCount;
        }

        public Integer getMaxBetAmtMul() {
            return maxBetAmtMul;
        }

        public void setMaxBetAmtMul(Integer maxBetAmtMul) {
            this.maxBetAmtMul = maxBetAmtMul;
        }

        public String getPickCode() {
            return pickCode;
        }

        public void setPickCode(String pickCode) {
            this.pickCode = pickCode;
        }

        public String getPickName() {
            return pickName;
        }

        public void setPickName(String pickName) {
            this.pickName = pickName;
        }

        public String getPickConfig() {
            return pickConfig;
        }

        public void setPickConfig(String pickConfig) {
            this.pickConfig = pickConfig;
        }

        public String getPickCount() {
            return pickCount;
        }

        public void setPickCount(String pickCount) {
            this.pickCount = pickCount;
        }

        public String getPickMode() {
            return pickMode;
        }

        public void setPickMode(String pickMode) {
            this.pickMode = pickMode;
        }

        public String getPickValue() {
            return pickValue;
        }

        public void setPickValue(String pickValue) {
            this.pickValue = pickValue;
        }

        public String getQpAllowed() {
            return qpAllowed;
        }

        public void setQpAllowed(String qpAllowed) {
            this.qpAllowed = qpAllowed;
        }

        public String getWinMode() {
            return winMode;
        }

        public void setWinMode(String winMode) {
            this.winMode = winMode;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        @NonNull
        @Override
        public String toString() {
            return "SelectedPickDetails{" + "betCode='" + betCode + '\'' + ", betDispName='" + betDispName + '\'' + ", betName='" + betName + '\'' + ", inputCount='" + inputCount + '\'' + ", maxBetAmtMul=" + maxBetAmtMul + ", pickCode='" + pickCode + '\'' + ", pickName='" + pickName + '\'' + ", pickConfig='" + pickConfig + '\'' + ", pickCount='" + pickCount + '\'' + ", pickMode='" + pickMode + '\'' + ", pickValue='" + pickValue + '\'' + ", qpAllowed='" + qpAllowed + '\'' + ", winMode='" + winMode + '\'' + ", unitPrice='" + unitPrice + '\'' + '}';
        }
    }

}


