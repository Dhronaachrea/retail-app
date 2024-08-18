package com.skilrock.retailapp.virtual_sports.ui;

import android.app.Activity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityVirtualSports extends BaseActivity {
    private WebView webView;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> menuBeanList = null;
    String game_url = null;
    String url = null;

    public String getGame_url() {
        return game_url;
    }

    public void setGame_url(String game_url) {
        this.game_url = game_url;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_sports);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setToolBar();
        setInitialParameters();
        initializeWidgets();
    }


    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            menuBeanList = bundle.getParcelableArrayList("VirtualModule");
            if (menuBeanList != null) {
                for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList model : menuBeanList) {
                    if (model.getMenuCode().equalsIgnoreCase("SBS_CANCEL_TICKET")) {
                        MENU_BEAN = model;
                    } else if (model.getMenuCode().equalsIgnoreCase("VIRTUAL_SPORTS")) {
                        setGame_url(model.getApiDetails());

                    }


                }
            }
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            ActivityVirtualSports.this.finish();
        }
    }

    private void initializeWidgets() {
        ProgressBarDialog.getProgressDialog().showProgress(ActivityVirtualSports.this);
        webView = findViewById(R.id.webView);


        webView.setWebViewClient(new WebViewClientImpl(ActivityVirtualSports.this));
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

        if (getGame_url() != null) {
            url = getGame_url() + "sbs/retailerPos/" + PlayerData.getInstance().getUserId() + "/" + PlayerData.getInstance().getUsername() + "/" + PlayerData.getInstance().getToken().split(" ")[1] + "/" + PlayerData.getInstance().getUserBalance() + "/en/THB/no_alias/VIRTUAL_SPORTS";
            Log.d("Log", url);

        } else {
            Utils.showRedToast(ActivityVirtualSports.this, getString(R.string.there_was_problem_connecting_to_server));
        }

        webView.loadUrl(url);
        AidlUtil.getInstance().connectPrinterService(ActivityVirtualSports.this);
    }

    private void setToolBar() {
        ImageView ivGameIcon = findViewById(R.id.ivGameIcon);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView win_txt = findViewById(R.id.win_txt);
        ImageView sbs_menu_img = findViewById(R.id.sbs_menu_img);

        sbs_menu_img.setImageResource(R.drawable.ic_win_sbs);
        tvUserBalance = findViewById(R.id.tvBal);
        tvUsername = findViewById(R.id.tvUserName);
        llBalance = findViewById(R.id.llBalance);
        tvTitle.setText("Virtual Sports");
        win_txt.setText(getString(R.string.winning_n_claim).toUpperCase());
        ivGameIcon.setVisibility(View.GONE);
        LinearLayout sbs_menu = findViewById(R.id.sbs_menu);
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
        ActivityVirtualSports.this.finish();
    }


    public void onClickSbsMenu(View view) {
        Toast.makeText(this, "Feature Coming Soon", Toast.LENGTH_SHORT).show();
    }
}