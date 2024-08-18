package com.skilrock.retailapp.virtual_sports.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.skilrock.retailapp.R
import com.skilrock.retailapp.models.rms.HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList
import com.skilrock.retailapp.models.rms.LoginBean
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity
import com.skilrock.retailapp.sle_game_portrait.SbsPurchaseBean
import com.skilrock.retailapp.utils.*
import com.skilrock.retailapp.utils.printer.AidlUtil
import com.skilrock.retailapp.virtual_sports.ui.viewmodel.VirtualSportsViewModel
import kotlinx.android.synthetic.main.virtual_sports_activity.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class VirtualSportsActivity : BaseActivity() {

    private var _menuBean: MenuBeanList? = null
    //var _menuBeanList: ArrayList<MenuBeanList>? = null
    private val REQUEST_CODE_PRINT: Int = 2121
    private var viewModel: VirtualSportsViewModel? = null
    private var _apiDetails: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.virtual_sports_activity)
        Objects.requireNonNull(supportActionBar)?.hide()
        setToolBar()
        setInitialParameters()
        setWebView()
    }

    private fun setToolBar() {
        val ivGameIcon          = findViewById<ImageView>(R.id.ivGameIcon)
        val tvTitle             = findViewById<TextView>(R.id.tvTitle)
        val tvWinText           = findViewById<TextView>(R.id.win_txt)
        val ivSbsMenu           = findViewById<ImageView>(R.id.sbs_menu_img)
        val llSbsMenu           = findViewById<LinearLayout>(R.id.sbs_menu)

        tvUserBalance           = findViewById(R.id.tvBal)
        tvUsername              = findViewById(R.id.tvUserName)
        llBalance               = findViewById(R.id.llBalance)

        tvTitle.text            = getString(R.string.virtual_sports)
        tvWinText.text          = getString(R.string.winning_n_claim_sbs).toUpperCase(Locale.ROOT)

        ivGameIcon.visibility   = View.GONE
        llSbsMenu.visibility    = View.VISIBLE

        ivSbsMenu.setImageResource(R.drawable.ic_win_sbs)
        refreshBalance()
    }

    private fun setInitialParameters() {
        val bundle = intent.extras
        bundle?.let {
            val menuBeanList: ArrayList<MenuBeanList>? = it.getParcelableArrayList("VirtualModule")

            menuBeanList?.let { listMenuBean ->
                for (menuBean in listMenuBean) {
                    if (menuBean.menuCode.equals("VSE_1", ignoreCase = true)) {
                        _menuBean   = menuBean
                        _apiDetails = menuBean.apiDetails
                        break
                    }
                }
            } ?: run {
                redToast(getString(R.string.some_technical_issue))
                finish()
            }
        } ?: run {
            redToast(getString(R.string.some_internal_error))
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        viewModel = ViewModelProviders.of(this).get(VirtualSportsViewModel::class.java)
        viewModel?.liveDataBalance?.observe(this, observerBalance)
        ProgressBarDialog.getProgressDialog().showProgress(this)

        webView.webViewClient = WebViewClientImpl(this)
        webView.webChromeClient = MyWebChromeClient()
        WebView.setWebContentsDebuggingEnabled(true)
        webView.settings.javaScriptEnabled = true
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        webView.settings.domStorageEnabled = true
        webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        /*webView.settings.setAppCacheMaxSize(10 * 1024 * 1024.toLong()) // 10MB
        webView.settings.setAppCachePath("/data/data/$packageName/cache")
        webView.settings.setAppCacheEnabled(true)*/
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.addJavascriptInterface(VirtualBetWebUtil(this, webView), "Android")

        Log.d("log", "User Agent: " + webView.settings.userAgentString)

        _menuBean?.let {
            getUrlDetails(it)?.let { url ->
                val completeUrl = url + "sbs/retailerPos/" + PlayerData.getInstance().userId + "/" + PlayerData.getInstance().username + "/" + PlayerData.getInstance().token.split(" ".toRegex()).toTypedArray()[1] + "/" + PlayerData.getInstance().userBalance + "/en" + "/" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this)) + "/no_alias/VIRTUAL_SPORTS"

                Log.w("log", "VS Complete Url: $completeUrl")
                webView.loadUrl(completeUrl)
                AidlUtil.getInstance().connectPrinterService(this)
            } ?: redToast(getString(R.string.there_was_problem_connecting_to_server))
        } ?: redToast(getString(R.string.something_went_wrong))
    }

    inner class WebViewClientImpl(activity: Activity?) : WebViewClient() {

        private var activity: Activity? = null

        init {
            this.activity = activity
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            favicon?.let { super.onPageStarted(view, url, it) }
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {

            ProgressBarDialog.getProgressDialog().dismiss()
        }

        override fun onLoadResource(view: WebView, url: String) {
            Log.v("onLoadResource:", url)
            Log.d("Log", url)
            super.onLoadResource(view, url)
        }
    }

    private class MyWebChromeClient : WebChromeClient() {
        override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
            return true
        }
    }

    fun onClickBack(view: View?) {
        val returnIntent = Intent()
        setResult(Activity.RESULT_CANCELED, returnIntent)
        this.finish()
    }

    fun onClickSbsMenu(view: View?) {
        //Toast.makeText(this, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
        if (Utils.getDeviceName().equals(AppConstants.DEVICE_T2MINI, ignoreCase = true)) {
            val intent = Intent(this, WinningClaimVirtualSportsLandActivity::class.java)
            intent.putExtra("MenuBean", _menuBean)
            startActivityForResult(intent, REQUEST_CODE_PRINT)
        } else {
            val intent = Intent(this, WinningClaimVirtualSports::class.java)
            intent.putExtra("MenuBean", _menuBean)
            startActivityForResult(intent, REQUEST_CODE_PRINT)
        }
    }

    private fun getUrlDetails(menuBean: MenuBeanList): String? {
        return try {
            val jsonObject = JSONObject(menuBean.apiDetails)
            val url = jsonObject.getJSONObject("game").getString("url")
            Log.w("log", "VS URL: $url")
            url
        } catch (e: JSONException) {
            Toast.makeText(this, getString(R.string.url_problem), Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && data.extras != null && data.extras!!.getBoolean("isPrintSuccess") && !data.extras!!.getBoolean("isfromReprint")) {
            viewModel?.getUpdatedBalance(PlayerData.getInstance().token)
        } else if (data != null && data.extras != null && !data.extras!!.getBoolean("isPrintSuccess")) {
            val errorMsg = getString(R.string.insert_paper_to_print)
            Utils.showCustomErrorDialog(this, getString(R.string.sports_betting), errorMsg)
        }
    }

    var observerBalance = Observer<LoginBean> { loginBean: LoginBean? ->
        ProgressBarDialog.getProgressDialog().dismiss()
        loginBean?.let {
            val responseData = loginBean.responseData
            if (responseData.statusCode == 0) {
                Utils.hideKeyboard(this)
                PlayerData.getInstance().setLoginData(this, loginBean)
                PlayerData.getInstance().userBalance = loginBean.responseData.data.balance.toString()
                super.refreshBalance()
            }
        }
    }

    inner class VirtualBetWebUtil(var context: Context, var webView: WebView) {

        @JavascriptInterface
        fun clientPrint(printData: String?) {
            printData?.let {
                try {
                    Log.i("log", "Print Data: $it")
                    val purchaseBean: SbsPurchaseBean? = Gson().fromJson(it, SbsPurchaseBean::class.java)

                    purchaseBean?.let {
                        val intent = Intent(context, PrintActivityVirtualSports::class.java)
                        intent.putExtra("PrintData", purchaseBean)
                        intent.putExtra("Category", PrintActivityVirtualSports.SALE)
                        startActivityForResult(intent, REQUEST_CODE_PRINT)
                    } ?: redToast(getString(R.string.something_went_wrong))
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("Parse", e.localizedMessage)
                }

            } ?: redToast(getString(R.string.some_technical_issue))
        }

        @JavascriptInterface
        fun sendValueWeb(): String? {
            val json = JsonObject()
            json.addProperty("deviceId", Utils.getDeviceSerialNumber())
            json.addProperty("lastPrintedTicketNo", "0")
            return json.toString()
        }

        @JavascriptInterface
        fun informSessionExpiry() {
            val message = getString(R.string.session_expiry_please_login_again)
            Utils.showCustomErrorDialogAndLogoutActivity(context, context.getString(R.string.service_error), message, true, this@VirtualSportsActivity)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)))
    }
}