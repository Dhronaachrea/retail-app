package com.skilrock.retailapp.landscape_draw_games.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.drawgame.LastWinningSpinAndWinAdapter;
import com.skilrock.retailapp.adapter.drawgame.SpinAndWinSchemaAdapter;
import com.skilrock.retailapp.dialog.DrawDialogLand;
import com.skilrock.retailapp.interfaces.RefreshGameListener;
import com.skilrock.retailapp.landscape_draw_games.dialog.ConfirmPurchaseDialog;
import com.skilrock.retailapp.landscape_draw_games.ui.activities.DrawGameBaseActivity;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleRequestBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.models.drawgames.SpinAndWinSelectedNumbersBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.portrait_draw_games.ui.PrintDrawGameActivity;
import com.skilrock.retailapp.portrait_draw_games.ui.WinningClaimLandscapeActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.LuckySixBallColorMap;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.drawgames.SpinAndWinFragViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Objects;

public class SpinAndWinGameFragment extends Fragment implements RefreshGameListener, View.OnClickListener,
        ConfirmPurchaseDialog.ConfirmPurchaseListener, DrawDialogLand.SelectedDrawsListener {

    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameResponse;
    private RelativeLayout progressContainer, viewDescription;
    private TextView tvFiveBallColor, loadingText;
    double amount = 0;
    private ProgressBar progressBar;
    private RecyclerView rvSchema, rvLastWinningResult;
    private WebView webView;
    private TextView tvTime, tvNoOfDraw, tvDrawCount, tvNoOfAdvanceDraw,tvTotalAmount;
    private LinearLayout llWinningClaim;
    private boolean IS_ADVANCE_PLAY = false;
    private HashMap<String, String> mapNumberConfig = new HashMap<>();
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> matchDetailList = new ArrayList<>();
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private SpinAndWinFragViewModel viewModel;
    private HashMap<String, SelectedPickDetails> MAP_PICKED_DATA = new HashMap<>();
    private ArrayList<PanelBean> LIST_PANEL = new ArrayList<>();
    public static long LAST_CLICK_TIME              = 0;
    public static int CLICK_GAP                     = 200;
    private final int REQUEST_CODE_PURCHASE_DETAILS = 3030;
    private SpinAndWinSchemaAdapter schemaAdapter;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private LinearLayout viewOtherAmount, viewReprint, viewCancel, viewResult;
    private final int REQUEST_CODE_PRINT = 1111;
    private ArrayList<String> LIST_ADVANCE_DRAWS = new ArrayList<>();
    public int MAX_SELECTION_LIMIT = 0, MIN_SELECTION_LIMIT = 0, SELECTED_BET_AMOUNT = 0, NUMBER_OF_DRAWS = 1, INDEX_CONSECUTIVE_DRAWS_LIST = 0;
    private ArrayList<String> LIST_CONSECUTIVE_DRAWS = new ArrayList<>();
    private Button btnPlus, btnMinus;
    private ConfirmPurchaseDialog confirmPurchaseDialog;
    private DrawDialogLand drawDialog;
    private View VIEW;

    public SpinAndWinGameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_roulette_game_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VIEW = view;
        viewModel = ViewModelProviders.of(this).get(SpinAndWinFragViewModel.class);
        viewModel.getLiveDataSale().observe(this, observerSale);
        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String language = bundle.getString("language");
            try { updateLanguage(Objects.requireNonNull(getActivity()), language); } catch (Exception e) { e.printStackTrace(); }
            gameResponse    = bundle.getParcelable("GameResponse");
            menuBean        = bundle.getParcelable("MenuBean");
            MENU_BEAN       = bundle.getParcelable("MenuBean");
        }

        if (gameResponse != null && gameResponse.getNumberConfig().getRange().size() > 0) {
            DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range ballDataList = gameResponse.getNumberConfig().getRange().get(0);
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball ball : ballDataList.getBall())
                mapNumberConfig.put(ball.getNumber(), ball.getColor());
            LuckySixBallColorMap.setMapNumberConfigFiveByNinty(mapNumberConfig);
        }

        tvNoOfDraw = view.findViewById(R.id.tvNoOfDraw);

        tvDrawCount = view.findViewById(R.id.tv_draw_count);
        tvTotalAmount = view.findViewById(R.id.tv_total_bet_amount);
        progressBar = view.findViewById(R.id.progress);
        loadingText = view.findViewById(R.id.loading_text);
        progressContainer = view.findViewById(R.id.progress_container);
        webView = view.findViewById(R.id.webView);
        rvSchema = view.findViewById(R.id.rv_rules);
        tvTime = view.findViewById(R.id.tv_time);
        rvLastWinningResult = view.findViewById(R.id.rv_balls);
        llWinningClaim = view.findViewById(R.id.llWinningClaim);
        viewReprint = view.findViewById(R.id.llReprint);
        viewCancel = view.findViewById(R.id.llCancel);
        viewResult = view.findViewById(R.id.llResult);
        tvNoOfAdvanceDraw = view.findViewById(R.id.tvNoOfAdvanceDraw);
        btnPlus = view.findViewById(R.id.btnPlus);
        btnMinus = view.findViewById(R.id.btnMinus);
        RelativeLayout ivAdvanceDraw = view.findViewById(R.id.ivAdvanceDraw);
        RelativeLayout rlBuyNowContainer = view.findViewById(R.id.rlBuyNowContainer);
        TextView tvPayoutGameName = view.findViewById(R.id.tvPayoutGameName);
        TextView tvGameName = view.findViewById(R.id.tvGameName);

        llWinningClaim.setOnClickListener(this);
        rlBuyNowContainer.setOnClickListener(this);
        viewReprint.setOnClickListener(this);
        viewCancel.setOnClickListener(this);
        viewResult.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        ivAdvanceDraw.setOnClickListener(this);

        tvTotalAmount.setText(Utils.getAmountWithCurrency(String.valueOf(0), getActivity()));

        ((DrawGameBaseActivity) getActivity()).registerListener(this);

        if (((DrawGameBaseActivity) getActivity()).isLoadingFragment(gameResponse.getGameCode())) {
            showLoader(true, getString(R.string.loading));
        }


        if (gameResponse != null && !TextUtils.isEmpty(gameResponse.getConsecutiveDraw())) {
            LIST_CONSECUTIVE_DRAWS = new ArrayList<>(Arrays.asList(gameResponse.getConsecutiveDraw().split(",")));
            tvPayoutGameName.setText(getString(R.string.payout_s) + gameResponse.getGameName());
            tvGameName.setText(gameResponse.getGameName());

            if (LIST_CONSECUTIVE_DRAWS.size() > 0)
                tvNoOfDraw.setText(LIST_CONSECUTIVE_DRAWS.get(0));
        }

        setWebView();
        setSchemaAdapter(gameResponse.getGameSchemas().getMatchDetail());
        setLastWinningAdapter();
        setMapForPicks();

        tvTime.setText(formatTime(gameResponse.getLastDrawWinningResultVOs().get(0).getLastDrawDateTime()));
        setPlusMinusButton();
    }

    void updateLanguage(Activity context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    private String formatTime(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("EEEE \n dd MMM yyyy");
        Date date;
        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(new WebViewClientImpl(getActivity()));
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        //webView.addJavascriptInterface(this, "JSInterface");
        webView.addJavascriptInterface(new JavaScriptInterface(getActivity(), webView), "Android");

        /*String url = "http://3.121.11.169/spinNwin/index.php?gameData=" + createJsonForUrl();
        Log.i("log", "SpinAndWin URL: " + url);
        ProgressBarDialog.getProgressDialog().showProgress(getActivity());

        webView.loadUrl(url);*/

        String url = null;
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
           // url = jsonObject.getJSONObject("spinAndWin").getString("url");
            url = jsonObject.getJSONObject("spinAndWinDomain").getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (url != null) {
            url = url + "?gameData=" + createJsonForUrl();
            Log.i("log", "SpinAndWin URL: " + url);
            webView.loadUrl(url);
        } else
            Utils.showRedToast(getActivity(), getString(R.string.something_went_wrong));
    }

    private void setSchemaAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> matchDetail) {
        matchDetailList.clear();
        matchDetailList.addAll(matchDetail);

        LinkedHashMap<String, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail>> hashMap =
                setSchema(matchDetailList);

        if (schemaAdapter == null) {
            schemaAdapter = new SpinAndWinSchemaAdapter(hashMap, getContext());

            rvSchema.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rvSchema.setHasFixedSize(true);
            rvSchema.setItemAnimator(new DefaultItemAnimator());
            rvSchema.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.HORIZONTAL));
            rvSchema.setAdapter(schemaAdapter);
        } else {
            schemaAdapter.notifyDataSetChanged();
        }
    }

    private LinkedHashMap<String, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail>> setSchema(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> matchDetailList) {
        LinkedHashMap<String, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail>> hashMap = new LinkedHashMap<>();

        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail matchDetail1 : matchDetailList) {
            if (hashMap.containsKey(matchDetail1.getBetType())) {

                hashMap.get(matchDetail1.getBetType()).add(matchDetail1);
            } else {
                ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> arrayList
                        = new ArrayList<>();
                arrayList.add(matchDetail1);
                Collections.sort(arrayList, (o1, o2) -> o1.getRank().compareTo(o2.getRank()));

                hashMap.put(matchDetail1.getBetType(), arrayList);
            }
        }

        return hashMap;
    }

    private void setLastWinningAdapter() {
        LastWinningSpinAndWinAdapter lastWinningSpinAndWinAdapter = new LastWinningSpinAndWinAdapter(gameResponse.getLastDrawWinningResultVOs(), mapNumberConfig, getContext());

        rvLastWinningResult.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvLastWinningResult.setHasFixedSize(true);
        rvLastWinningResult.setItemAnimator(new DefaultItemAnimator());
        rvLastWinningResult.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.HORIZONTAL));

        rvLastWinningResult.setAdapter(lastWinningSpinAndWinAdapter);
    }

    private String createJsonForUrl() {
        Log.i("log", "Selected Game: " + gameResponse.getGameCode());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("unitPrice", gameResponse.getBetRespVOs().get(0).getUnitPrice());
            jsonObject.put("maxBetAmtMul", gameResponse.getBetRespVOs().get(0).getMaxBetAmtMul());
            jsonObject.put("maxPanelAllowed", gameResponse.getMaxPanelAllowed());
            jsonObject.put("language", Locale.getDefault().getLanguage());
            ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> ball = gameResponse.getNumberConfig().getRange().get(0).getBall();
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

    public void setMessage(String message) {
        if (loadingText != null && getActivity() != null)
            loadingText.setText(message != null && !message.equals("") ? message : getActivity().getString(R.string.loading));

        if (progressBar != null && getActivity() != null)
            progressBar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.dialog_progress_dialog_color), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void setMapForPicks() {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> listBetRespVOs = gameResponse.getBetRespVOs();
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

    @Override
    public void onShowLoader(String gameCode, boolean show) {
        showLoader(show, "");
    }
    public void showLoader(boolean show, String message) {
        if (progressContainer != null) {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            setMessage(message);
        }
    }

    @Override
    public void onUpdateGame(DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO) {
        gameResponse = gameRespVO;

        setLastWinningAdapter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llWinningClaim:
                Intent winning = new Intent(getActivity(), WinningClaimLandscapeActivity.class);
                winning.putExtra("MenuBean", menuBean);
                startActivityForResult(winning, REQUEST_CODE_PRINT);
                break;
            case R.id.llReprint:
                ((DrawGameBaseActivity) getActivity()).callReprint(menuBean);
                break;
            case R.id.llCancel:
                ((DrawGameBaseActivity) getActivity()).callCancel(menuBean);
                break;
            case R.id.llResult:
                ((DrawGameBaseActivity) getActivity()).callResult(menuBean);
                break;
            case R.id.btnPlus:
                resetAdvanceDraws();

                IS_ADVANCE_PLAY = false;
                Log.w("draw", "INDEX (BEFORE): " + INDEX_CONSECUTIVE_DRAWS_LIST);
                if (INDEX_CONSECUTIVE_DRAWS_LIST < gameResponse.getDrawRespVOs().size()) {
                    NUMBER_OF_DRAWS = Integer.parseInt(LIST_CONSECUTIVE_DRAWS.get(++INDEX_CONSECUTIVE_DRAWS_LIST));
                    String text = NUMBER_OF_DRAWS + "";
                    tvNoOfDraw.setText(text);
                    Log.w("draw", "INDEX (AFTER): " + INDEX_CONSECUTIVE_DRAWS_LIST);
                    tvDrawCount.setText(NUMBER_OF_DRAWS == 1 ? NUMBER_OF_DRAWS + " " + getString(R.string.draw) : NUMBER_OF_DRAWS + " " + getString(R.string.draws));
                }
                setPlusMinusButton();

                //betValueCalculation();
                recalculatePanelAmount();
                break;
            case R.id.btnMinus:
                resetAdvanceDraws();

                IS_ADVANCE_PLAY = false;
                Log.i("draw", "INDEX (BEFORE): " + INDEX_CONSECUTIVE_DRAWS_LIST);
                if (INDEX_CONSECUTIVE_DRAWS_LIST > 0) {
                    NUMBER_OF_DRAWS = Integer.parseInt(LIST_CONSECUTIVE_DRAWS.get(--INDEX_CONSECUTIVE_DRAWS_LIST));
                    String text = NUMBER_OF_DRAWS + "";
                    tvNoOfDraw.setText(text);
                    Log.i("draw", "INDEX (AFTER): " + INDEX_CONSECUTIVE_DRAWS_LIST);

                    tvDrawCount.setText(NUMBER_OF_DRAWS == 1 ? NUMBER_OF_DRAWS + " Draw" : NUMBER_OF_DRAWS + " Draws");
                }

                setPlusMinusButton();

                //betValueCalculation();
                recalculatePanelAmount();
                break;
            case R.id.ivAdvanceDraw://dohere
                if (drawDialog != null) {
                    drawDialog.cancel();
                }
                ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> drawRespVOs = gameResponse.getDrawRespVOs();
                drawDialog = new DrawDialogLand(getContext(), drawRespVOs, this, gameResponse.getMaxAdvanceDraws(), LIST_ADVANCE_DRAWS);
                drawDialog.show();
                if (drawDialog.getWindow() != null) {
                    drawDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    drawDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                break;
            case R.id.rlBuyNowContainer:
                double amount = 0;
                for (PanelBean model : LIST_PANEL)
                    amount = amount + model.getAmount();
                if (amount == 0)
                    showToast(getString(R.string.your_cart_is_empty), Toast.LENGTH_LONG);
                else showBuyNowConfirmation();

                break;
        }
    }

    private void showToast(String text, int duration) {
        try {
            Snackbar snackbar = Snackbar.make(VIEW, text, Snackbar.LENGTH_SHORT);
            (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            View mView = snackbar.getView();
            TextView mTextView = mView.findViewById(R.id.snackbar_text);
            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPurchase() {
        buyTicket();
    }

    @Override
    public void getSelectedAdvanceDraws(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws) {
        LIST_ADVANCE_DRAWS.clear();
        tvNoOfDraw.setText("0");
        Log.e("draw", "Selected Advance Draws: " + listDraws);
        String numOfDraws = listDraws.size() + "";
        NUMBER_OF_DRAWS = listDraws.size();
        INDEX_CONSECUTIVE_DRAWS_LIST = -1;

        setPlusMinusButton();
        btnMinus.setBackgroundResource(R.drawable.minus_grey);
        btnMinus.setEnabled(false);

        IS_ADVANCE_PLAY = NUMBER_OF_DRAWS > 0;

        tvNoOfAdvanceDraw.setText(numOfDraws);

        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO draw : listDraws)
            LIST_ADVANCE_DRAWS.add(draw.getDrawId() + "");

        recalculatePanelAmount();

        tvDrawCount.setText(listDraws.size() == 1 ? listDraws.size() + " " + getString(R.string.draw) : listDraws.size() + " " + getString(R.string.draws));
    }

    private void recalculatePanelAmount() {
        for (int index = 0; index < LIST_PANEL.size(); index++) {
            LIST_PANEL.get(index).setNumberOfDraws(NUMBER_OF_DRAWS);
            double amt = LIST_PANEL.get(index).getSelectedBetAmount() * LIST_PANEL.get(index).getNumberOfDraws() * LIST_PANEL.get(index).getNumberOfLines();
            LIST_PANEL.get(index).setAmount(amt);
        }

        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        amount = 0;
        for (PanelBean model : LIST_PANEL)
            amount = amount + model.getAmount();
        getActivity().runOnUiThread(() -> tvTotalAmount.setText(Utils.getAmountWithCurrency(String.valueOf(amount), getActivity())));
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
            ProgressBarDialog.getProgressDialog().dismiss();
            webView.loadUrl("javascript:showAlert()");
        }
    }

    Observer<FiveByNinetySaleResponseBean> observerSale = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null)
            Utils.showCustomErrorDialog(getActivity(), "Draw Games", getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            /*resetGame();
            deleteAllPanel();
            print(response);*/
            //DialogCloseListener listener = () -> { resetGame(); deleteAllPanel(); };
            //resetGame();
            //deleteAllPanel();
            //Utils.showCustomSuccessDialog(getContext(), getFragmentManager(), "", "TICKET SOLD", 0, listener);
            //String saleTime = LuckySixViewModel.getSaleResonseTime();
            print(response);
            saveTicket(response);
            LIST_PANEL.clear();
            calculateTotalAmount();
            resetNoOfDraws();
            resetAdvanceDraws();
            setPlusMinusButton();
            ((DrawGameBaseActivity) getActivity()).refreshBalance();
        } else {
            if (Utils.checkForSessionExpiryActivity(getActivity(), response.getResponseCode(), getActivity())) return;

            String errorMsg = Utils.getResponseMessage(getActivity(), "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(getActivity(), "Draw Games", errorMsg);
        }
    };

    private void showBuyNowConfirmation() {
        if (confirmPurchaseDialog != null)
            confirmPurchaseDialog.cancel();
        confirmPurchaseDialog = new ConfirmPurchaseDialog(getContext(), this);
        confirmPurchaseDialog.show();
        if (confirmPurchaseDialog.getWindow() != null) {
            confirmPurchaseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmPurchaseDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }
    public void print(FiveByNinetySaleResponseBean bean) {
        Intent intent = new Intent(getActivity(), PrintDrawGameActivity.class);
        intent.putExtra("PrintData", bean);
        intent.putExtra("Category", PrintDrawGameActivity.SALE);
        startActivityForResult(intent, REQUEST_CODE_PRINT);
    }

    private void saveTicket(FiveByNinetySaleResponseBean responseBean) {
        SharedPrefUtil.putLastTicketNumber(getActivity(), SharedPrefUtil.getString(getContext(), SharedPrefUtil.USERNAME),
                responseBean.getResponseData().getTicketNumber());
    }

    private void resetAdvanceDraws() {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO data : gameResponse.getDrawRespVOs())
            data.setSelected(false);
        tvNoOfAdvanceDraw.setText("0");
        LIST_ADVANCE_DRAWS.clear();
        IS_ADVANCE_PLAY = false;
        btnMinus.setBackgroundResource(R.drawable.minus_grey);
        btnMinus.setEnabled(false);
    }

    private void resetNoOfDraws() {
        NUMBER_OF_DRAWS = 1;
        INDEX_CONSECUTIVE_DRAWS_LIST = 0;
        tvNoOfDraw.setText(LIST_CONSECUTIVE_DRAWS.get(0));
        tvDrawCount.setText(NUMBER_OF_DRAWS == 1 ? NUMBER_OF_DRAWS + " "+getString(R.string.draw) : NUMBER_OF_DRAWS + " "+getString(R.string.draws));
        setPlusMinusButton();
    }

    private void setPlusMinusButton() {
        btnMinus.setBackgroundResource(INDEX_CONSECUTIVE_DRAWS_LIST == 0 ? R.drawable.minus_grey : R.drawable.minus);
        btnPlus.setBackgroundResource(INDEX_CONSECUTIVE_DRAWS_LIST == gameResponse.getDrawRespVOs().size() - 1 ? R.drawable.plus_grey : R.drawable.plus_round);
        btnPlus.setEnabled(INDEX_CONSECUTIVE_DRAWS_LIST == gameResponse.getDrawRespVOs().size() - 1 ? false : true);
        btnMinus.setEnabled(INDEX_CONSECUTIVE_DRAWS_LIST == 0 ? false : true);
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("log", message);
            result.confirm();
            return true;
        }
    }

    public class JavaScriptInterface {
        FragmentActivity parentActivity;
        WebView mWebView;

        JavaScriptInterface(FragmentActivity _activity, WebView _webView)  {
            parentActivity = _activity;
            mWebView = _webView;
        }

        @JavascriptInterface
        public void getGameData(String data) {
            if (!TextUtils.isEmpty(data)) {

                getActivity().runOnUiThread(() -> {
                    resetNoOfDraws();
                    resetAdvanceDraws();
                });

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

                addBet(model);

            } else Utils.showRedToast(getActivity(), getString(R.string.please_play_game));
        }

        @JavascriptInterface
        public void showAndroidToast(String message) {
            Utils.showRedToast(getActivity(), message);
        }

        @JavascriptInterface
        public void clearGame() {
            getActivity().runOnUiThread(() -> {
                LIST_PANEL.clear();
                calculateTotalAmount();
                resetNoOfDraws();
                resetAdvanceDraws();
            });
        }
    }

    private void addBet(SpinAndWinSelectedNumbersBean model) {
        try {
            LIST_PANEL.clear();
            calculateTotalAmount();
            ArrayList<SpinAndWinSelectedNumbersBean.SelectedData> selectedData = model.getSelectedData();
            for (SpinAndWinSelectedNumbersBean.SelectedData data : selectedData) {
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
                        LIST_PANEL.add(setDataToPanelBean(data, selectedPickDetails));
                    else {
                        Utils.showRedToast(getActivity(), getString(R.string.some_technical_issue));
                        return;
                    }
                } else {
                    SelectedPickDetails selectedPickDetails = MAP_PICKED_DATA.get(data.getPickType());
                    if (selectedPickDetails != null)
                        LIST_PANEL.add(setDataToPanelBean(data, selectedPickDetails));
                    else {
                        Utils.showRedToast(getActivity(), getString(R.string.some_technical_issue));
                        return;
                    }
                }
            }

            calculateTotalAmount();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private PanelBean setDataToPanelBean(SpinAndWinSelectedNumbersBean.SelectedData data, SelectedPickDetails selectedPickDetails) {
        PanelBean panelBean = new PanelBean();
        String[] split = data.getSelectedNumbers().split(",");
        panelBean.setListSelectedNumber(new ArrayList<>(Arrays.asList(split)));
        if (selectedPickDetails.getWinMode().equalsIgnoreCase("MAIN"))
            panelBean.setPickedValues(data.getSelectedNumbers());
        else
            panelBean.setPickedValues(selectedPickDetails.getPickValue());
        panelBean.setGameName(gameResponse.getGameName());
        panelBean.setAmount(Integer.parseInt(data.getAmount()));
        panelBean.setWinMode(selectedPickDetails.getWinMode());
        panelBean.setBetName(selectedPickDetails.getBetDispName());
        panelBean.setPickName(selectedPickDetails.getPickName());
        panelBean.setBetCode(selectedPickDetails.getBetCode());
        panelBean.setPickCode(selectedPickDetails.getPickCode());
        panelBean.setPickConfig(selectedPickDetails.getPickConfig());
        int betAmountMultiple = (int) (Integer.parseInt(data.getAmount()) / gameResponse.getBetRespVOs().get(0).getUnitPrice());
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

    private void buyTicket() {
        if (!Utils.isNetworkConnected(getActivity())) {
            Toast.makeText( getActivity(), getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(menuBean, getActivity(), "buy");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(getActivity());
            FiveByNinetySaleRequestBean model = new FiveByNinetySaleRequestBean();
            model.setMerchantCode(urlBean.getUserName());

            FiveByNinetySaleRequestBean.MerchantData merchantData = new FiveByNinetySaleRequestBean.MerchantData();
            merchantData.setUserId(Integer.parseInt(PlayerData.getInstance().getUserId()));
            merchantData.setUserName(PlayerData.getInstance().getUsername());
            merchantData.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            model.setMerchantData(merchantData);

            model.setGameCode(gameResponse.getGameCode());
            model.setCurrencyCode(Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getActivity())));
            model.setPurchaseDeviceId(Utils.getDeviceSerialNumber());
            model.setPurchaseDeviceType(AppConstants.PURCHASE_TYPE);
            model.setLastTicketNumber(SharedPrefUtil.getLastTicketNumber(getActivity(), SharedPrefUtil.getString(getActivity(), SharedPrefUtil.USERNAME)));
            model.setModelCode(Utils.getModelCode());

            model.setIsAdvancePlay(IS_ADVANCE_PLAY);
            model.setNoOfDraws(NUMBER_OF_DRAWS);

            ArrayList<FiveByNinetySaleRequestBean.DrawData> list = new ArrayList<>();
            if (IS_ADVANCE_PLAY && LIST_ADVANCE_DRAWS.size() > 0) {
                for (String drawId: LIST_ADVANCE_DRAWS) {
                    FiveByNinetySaleRequestBean.DrawData drawData = new FiveByNinetySaleRequestBean.DrawData();
                    drawData.setDrawId(drawId);
                    list.add(drawData);
                }
            }
            model.setDrawData(list);

            ArrayList<FiveByNinetySaleRequestBean.PanelData> listPanel = new ArrayList<>();
            for (PanelBean panelData: LIST_PANEL) {
                FiveByNinetySaleRequestBean.PanelData modelPanel = new FiveByNinetySaleRequestBean.PanelData();
                //modelPanel.setPickType(panelData.getPickName().replace("QP", "").trim());
                modelPanel.setPickType(panelData.getPickCode());
                modelPanel.setBetType(panelData.getBetCode());
                //modelPanel.setPickType(panelData.getPickName());
                modelPanel.setPickConfig(panelData.getPickConfig());
                modelPanel.setBetAmountMultiple(panelData.getBetAmountMultiple());
                modelPanel.setQuickPick(panelData.getQuickPick());
                modelPanel.setQpPreGenerated(panelData.getQpPreGenerated());
                modelPanel.setPickedValues(panelData.getPickedValues());
                modelPanel.setTotalNumbers(panelData.getTotalNumbers());
                listPanel.add(modelPanel);
            }
            model.setPanelData(listPanel);

            callSaleApiSpinAndWin(urlBean, model);
        }
    }

    public void callSaleApiSpinAndWin(UrlDrawGameBean bean, FiveByNinetySaleRequestBean requestBean){
        viewModel.callSaleApi(bean , requestBean);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }

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
