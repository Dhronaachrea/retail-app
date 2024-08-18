package com.skilrock.retailapp.portrait_draw_games.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.models.drawgames.SpinAndWinSelectedNumbersBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.utils.DrawGameData;
import com.skilrock.retailapp.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class SpinAndWinActivity extends BaseActivity {

    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private HashMap<String, SelectedPickDetails> MAP_PICKED_DATA = new HashMap<>();
    private ArrayList<PanelBean> listPanelBean = new ArrayList<>();
    private WebView webView;
    public static long LAST_CLICK_TIME              = 0;
    public static int CLICK_GAP                     = 200;
    private final int REQUEST_CODE_PURCHASE_DETAILS = 3030;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_and_win);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        setToolBar();
        setMapForPicks();
        initializeWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBalance();
    }

    public void onClickBack(View view) {
        if (SystemClock.elapsedRealtime() - LAST_CLICK_TIME < CLICK_GAP) {
            return;
        }
        LAST_CLICK_TIME = SystemClock.elapsedRealtime();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        SpinAndWinActivity.this.finish();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MENU_BEAN       = bundle.getParcelable("MenuBean");
            String language = bundle.getString("language");
            try { updateLanguage(SpinAndWinActivity.this, language); } catch (Exception e) { e.printStackTrace(); }
        }
        else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            SpinAndWinActivity.this.finish();
        }
    }

    private void setToolBar() {
        ImageView ivGameIcon    = findViewById(R.id.ivGameIcon);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);

        tvTitle.setText(DrawGameData.getSelectedGame().getGameName());
        ivGameIcon.setVisibility(View.GONE);
    }

    void updateLanguage(Activity context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    private void setMapForPicks() {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> listBetRespVOs = DrawGameData.getSelectedGame().getBetRespVOs();
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

    @SuppressLint("SetJavaScriptEnabled")
    private void initializeWidgets() {
        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(new WebViewClientImpl(SpinAndWinActivity.this));
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);
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
            Utils.showRedToast(SpinAndWinActivity.this, getString(R.string.something_went_wrong));
    }

    private String createJsonForUrl() {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO selectedGame = DrawGameData.getSelectedGame();
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

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("log", message);
            result.confirm();
            return true;
        }
    }

    public class JavaScriptInterface {
        SpinAndWinActivity parentActivity;
        WebView mWebView;

        JavaScriptInterface(SpinAndWinActivity _activity, WebView _webView)  {
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
            } else Utils.showRedToast(SpinAndWinActivity.this, getString(R.string.please_play_game));
        }

        @JavascriptInterface
        public void showAndroidToast(String message) {
            Utils.showRedToast(SpinAndWinActivity.this, message);
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
                    Utils.showRedToast(SpinAndWinActivity.this, getString(R.string.some_technical_issue));
                    return;
                }
            } else {
                SelectedPickDetails selectedPickDetails = MAP_PICKED_DATA.get(data.getPickType());
                if (selectedPickDetails != null)
                    listPanelBean.add(setDataToPanelBean(data, selectedPickDetails));
                else {
                    Utils.showRedToast(SpinAndWinActivity.this, getString(R.string.something_went_wrong));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("log", "Request Code: " + requestCode + ", Result Code: " + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_PURCHASE_DETAILS:
                if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isSaleResponseSuccess")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("isBalanceUpdate", true);//data.getExtras().getBoolean("doBalanceUpdate")
                    setResult(Activity.RESULT_OK, returnIntent);
                    SpinAndWinActivity.this.finish();
                }
                else {
                    if (data != null && data.getExtras() != null && data.getExtras().containsKey("ListBetData")) {
                        listPanelBean = data.getParcelableArrayListExtra("ListBetData");
                    }
                }
                break;
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (!listPanelBean.isEmpty()) {
            GameChangeListener listener = super::onBackPressed;
            Utils.showGameSwitchDialog(this, R.drawable.spin_win, "You have some item in your cart. If you leave the game your cart will be cleared.", "Do you want to leave?", "Stay", "Leave", listener);
        }
        else
            super.onBackPressed();*/
    }
}
