package com.skilrock.retailapp.sbs.ui;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.sbs.view_model.SbsViewModel;
import com.skilrock.retailapp.sle_game_portrait.SbsPurchaseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;
import java.util.ArrayList;
import java.util.Locale;
import static com.skilrock.retailapp.utils.Utils.getSbsUrl;

public class ActivitySbsLandScape extends BaseActivity {
    private WebView webView;
    SbsPurchaseBean purchaseBean = null;
    SbsViewModel viewModel;
    private static final int REQUEST_CODE_PRINT = 1111;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> menuBeanList = null;
    String game_url = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_sbs_land_scape);
        setToolBar();
        setInitialParameters();
        initializeWidgets();
    }

    void updateLanguage(Activity context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    private void initializeWidgets() {
        AidlUtil.getInstance().connectPrinterService(ActivitySbsLandScape.this);
        ProgressBarDialog.getProgressDialog().showProgress(ActivitySbsLandScape.this);
        webView     = findViewById(R.id.webView);
        viewModel   = ViewModelProviders.of(this).get(SbsViewModel.class);
        viewModel.getLiveDataBalance().observe(ActivitySbsLandScape.this, observerBalance);

        webView.setWebViewClient(new WebViewClientImpl(ActivitySbsLandScape.this));
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebContentsDebuggingEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        /*webView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024); // 10MB
        webView.getSettings().setAppCachePath("/data/data/" + getPackageName() + "/cache");
        webView.getSettings().setAppCacheEnabled(true);*/
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        Log.d("Log", webView.getSettings().getUserAgentString());
        SportsBetGame sportsBetGame = new SportsBetGame(this, webView);
        webView.addJavascriptInterface(sportsBetGame, "Android");

        if (game_url != null) {
            if (getSbsUrl(game_url, this) != null)
                webView.loadUrl(getSbsUrl(game_url, this));
            else
                Utils.showRedToast(ActivitySbsLandScape.this, getString(R.string.url_problem));
        } else {
            Utils.showRedToast(ActivitySbsLandScape.this, getString(R.string.url_problem));
        }
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            menuBeanList = bundle.getParcelableArrayList("SbsModule");
            if (menuBeanList != null) {
                for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList model : menuBeanList) {
                    if (model.getMenuCode().equalsIgnoreCase("SBS_CANCEL_TICKET")) {
                        MENU_BEAN = model;
                    } else if (model.getMenuCode().equalsIgnoreCase("SBS_SALE")) {
                        game_url = model.getApiDetails();
                    }
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            ActivitySbsLandScape.this.finish();
        }
    }

    private void setToolBar() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String language = bundle.getString("language");
            try {
                updateLanguage(ActivitySbsLandScape.this, language);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ImageView ivGameIcon    = findViewById(R.id.ivGameIcon);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        TextView win_txt        = findViewById(R.id.win_txt);
        ImageView sbs_menu_img  = findViewById(R.id.sbs_menu_img);
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);
        LinearLayout sbs_menu   = findViewById(R.id.sbs_menu);

        sbs_menu_img.setImageResource(R.drawable.ic_win_sbs);
        tvTitle.setText("SBS");
        win_txt.setText(getString(R.string.winning_n_claim_sbs).toUpperCase());
        ivGameIcon.setVisibility(View.GONE);
        sbs_menu.setVisibility(View.VISIBLE);
        refreshBalance();
    }

    public class WebViewClientImpl extends WebViewClient {

        private Activity activity = null;
        public WebViewClientImpl(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            webView.loadUrl("javascript:callJS()");
            ProgressBarDialog.getProgressDialog().dismiss();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            Log.v("onLoadResource:", url);
            Log.d("Log", url);
            super.onLoadResource(view, url);
        }

    }
    class SportsBetGame {
        private Context context;
        private WebView webView;

        public SportsBetGame(Context context, WebView webView) {
            this.context = context;
            this.webView = webView;
        }

        @JavascriptInterface
        public void updateBalance() {

        }

        @JavascriptInterface
        public void clientPrint(String printData) {
            if (printData != null) {
                try {
                    purchaseBean = new Gson().fromJson(printData, SbsPurchaseBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Parse", e.getLocalizedMessage());
                }
                if (purchaseBean != null) {
                    Intent intent = new Intent(ActivitySbsLandScape.this, PrintActivitySbs.class);
                    intent.putExtra("PrintData", purchaseBean);
                    intent.putExtra("Category", PrintActivitySbs.SALE);
                    startActivityForResult(intent, REQUEST_CODE_PRINT);
                } else {
                    Toast.makeText(context, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
            }
        }

        @JavascriptInterface
        public String sendValueWeb() {
            JsonObject object = new JsonObject();
            object.addProperty("deviceId", Utils.getDeviceSerialNumber());
            object.addProperty("lastPrintedTicketNo", "0");
            return object.toString();
        }

        @JavascriptInterface
        public void informSessionExpiry() {
            String message = getString(R.string.session_expiry_please_login_again);
            Utils.showCustomErrorDialogAndLogoutActivity(context, context.getString(R.string.service_error), message, true, ActivitySbsLandScape.this);
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

    public void onClickBack(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        ActivitySbsLandScape.this.finish();
    }

    public void onClickSbsMenu(View view) {
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            Intent winning = new Intent(ActivitySbsLandScape.this, WinningClaimSbsLandActivity.class);
            winning.putExtra("MenuBean", MENU_BEAN);
            startActivityForResult(winning, REQUEST_CODE_PRINT);
        } else {
            Intent winning_portrait = new Intent(ActivitySbsLandScape.this, WinningClaimSbsPortraitActivity.class);
            winning_portrait.putExtra("MenuBean", MENU_BEAN);
            startActivityForResult(winning_portrait, REQUEST_CODE_PRINT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isPrintSuccess") && !data.getExtras().getBoolean("isfromReprint")) {
            viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
        } else if (data != null && data.getExtras() != null && !data.getExtras().getBoolean("isPrintSuccess")) {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(this, getString(R.string.sports_betting), errorMsg);
        }

    }

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (loginBean == null)
            Toast.makeText(ActivitySbsLandScape.this, getString(R.string.there_was_problem_connecting_to_server), Toast.LENGTH_SHORT).show();
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                Utils.hideKeyboard(ActivitySbsLandScape.this);
                PlayerData.getInstance().setLoginData(ActivitySbsLandScape.this, loginBean);
                PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));
                //Toast.makeText(ActivitySbsLandScape.this, getString(R.string.your_balance_has_been_updated), Toast.LENGTH_SHORT).show();
                super.refreshBalance();
            } else {
                if (Utils.checkForSessionExpiryActivity(ActivitySbsLandScape.this, loginBean.getResponseData().getStatusCode(), ActivitySbsLandScape.this))
                    return;
                String errorMsg = Utils.getResponseMessage(ActivitySbsLandScape.this, "RMS", responseData.getStatusCode());
                Toast.makeText(ActivitySbsLandScape.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            String errorMsg = Utils.getResponseMessage(ActivitySbsLandScape.this, "RMS", loginBean.getResponseCode());
            Toast.makeText(ActivitySbsLandScape.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

}
