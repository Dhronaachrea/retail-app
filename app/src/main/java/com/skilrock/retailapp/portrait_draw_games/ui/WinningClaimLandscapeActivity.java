package com.skilrock.retailapp.portrait_draw_games.ui;


import android.Manifest;
import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.interfaces.WinningClaimListener;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayRequestBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayResponseBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimVerifyRequestBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimVerifyResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.portrait_draw_games.viewmodels.WinningClaimViewModel;
import com.skilrock.retailapp.sle_game_portrait.BaseClassSle;
import com.skilrock.retailapp.sle_game_portrait.HttpRequest;
import com.skilrock.retailapp.sle_game_portrait.ResponseLis;
import com.skilrock.retailapp.sle_game_portrait.VerifyPayTicket;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CustomSuccessDialog;
import com.skilrock.retailapp.utils.EaiUtil;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.ScanConfig;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.sunmi.scan.Config;
import com.sunmi.scan.Image;
import com.sunmi.scan.ImageScanner;
import com.sunmi.scan.Symbol;
import com.sunmi.scan.SymbolSet;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import sunmi.sunmiui.utils.LogUtil;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;


public class WinningClaimLandscapeActivity extends BaseActivity implements View.OnClickListener, WinningClaimListener, ResponseLis, SurfaceHolder.Callback {

    private static final int REQUEST_CODE_PRINT = 1111;
    private EditText etTicketNumber;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private int api_call = 0;
    private Button buttonProceed;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private TextInputLayout tilTicketNumber;
    private WinningClaimViewModel viewModel;
    private UsbThermalPrinter usbThermalPrinter;
    private JsonObject object;
    private String response;
    private boolean fromSports = false;
    EditText ed_first_no, ed_second_no, ed_third_no, ed_fourth_no;
    String headerData1 = "userName,E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6|password,p@55w0rd|Content-Type,application/x-www-form-urlencoded";
    boolean request = true;
    int length = 0;
    String responseTicketNumber = null;
    private CodeScanner mCodeScanner;
    private FrameLayout contentFrame;
    private SurfaceHolder mHolder;
    private SurfaceView mSurfaceView;
    private AsyncDecode asyncDecode;
    private ImageScanner mImageScanner;//Claim scanner
    private Handler mAutoFocusHandler;
    private AtomicBoolean isRUN = new AtomicBoolean(false);
    private Camera mCamera;
    private boolean scanTicket = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            setContentView(R.layout.activity_winning_claim_landscape);
        else
            setContentView(R.layout.activity_winning_claim);
        setInitialParameters();
        setToolBar();
        initializeWidgets();
    }

    private void setConfig() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            //当前分辨率
            // ScanConfig.CURRENT_PPI = intent.getIntExtra("CURRENT_PPI", 0X0003);
            //扫描完成声音提示
            ScanConfig.PLAY_SOUND = intent.getBooleanExtra("PLAY_SOUND", true);
            //扫描完成震动
            ScanConfig.PLAY_VIBRATE = intent.getBooleanExtra("PLAY_VIBRATE", false);
            //识别反色二维码
            ScanConfig.IDENTIFY_INVERSE_QR_CODE = intent.getBooleanExtra("IDENTIFY_INVERSE_QR_CODE", true);
            //识别画面中多个二维码
            ScanConfig.IDENTIFY_MORE_CODE = intent.getBooleanExtra("IDENTIFY_MORE_CODE", false);
            //是否显示设置按钮
            ScanConfig.IS_SHOW_SETTING = intent.getBooleanExtra("IS_SHOW_SETTING", true);
            //是否显示选择相册按钮
            ScanConfig.IS_SHOW_ALBUM = intent.getBooleanExtra("IS_SHOW_ALBUM", true);
            //是否显示闪光灯
            ScanConfig.IS_OPEN_LIGHT = intent.getBooleanExtra("IS_OPEN_LIGHT", false);
            //是否是循环模式
            ScanConfig.SCAN_MODE = intent.getBooleanExtra("SCAN_MODE", false);

        }
    }

    private void init() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mAutoFocusHandler = new Handler();
        mHolder = mSurfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
        mImageScanner = new ImageScanner();//创建扫描器
        mImageScanner.setConfig(0, Config.X_DENSITY, 2);//行扫描间隔
        mImageScanner.setConfig(0, Config.Y_DENSITY, 2);//列扫描间隔
        if (!ScanConfig.IS_SHOW_ALBUM) {
            findViewById(R.id.album_item).setVisibility(View.GONE);
        }
        if (!ScanConfig.IS_SHOW_SETTING) {
            //mSetting.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPause() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        EaiUtil.controlLamp("0", ScanConfig.LIGHT_BRIGHT_TIME, ScanConfig.LIGHT_DROWN_TIME, EaiUtil.LED_CAM_NAME);
        super.onPause();
    }


    private void initializeWidgets() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent i = getIntent();
        fromSports = i.getBooleanExtra("from_sports", false);
        viewModel = ViewModelProviders.of(this).get(WinningClaimViewModel.class);
        viewModel.getLiveDataWinningVerify().observe(this, observerVerifyWinning);
        viewModel.getLiveDataWinningPay().observe(this, observerWinningPay);
        viewModel.getliveDataWinningFailedPrint().observe(this, observerWinningFailedPrint);
        etTicketNumber = findViewById(R.id.et_ticket_number);
        buttonProceed = findViewById(R.id.button_proceed);
        contentFrame = findViewById(R.id.content_frame);
        tilTicketNumber = findViewById(R.id.textInputLayoutTicketNumber);
        ed_first_no = findViewById(R.id.ed_first_no);
        ed_second_no = findViewById(R.id.ed_second_no);
        ed_third_no = findViewById(R.id.ed_third_no);
        ed_fourth_no = findViewById(R.id.ed_fourth_no);
        buttonProceed.setOnClickListener(WinningClaimLandscapeActivity.this);

        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            contentFrame.setVisibility(View.VISIBLE);
            if (AppPermissions.checkPermission(this)) startScanning();
            else AppPermissions.requestPermissionWinning(this);
        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            contentFrame.setVisibility(View.INVISIBLE);
        }

        setFocus(ed_first_no);
        ed_first_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                setFocus(ed_first_no);
        });
        ed_second_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                setFocus(ed_second_no);
        });
        ed_third_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                setFocus(ed_third_no);
        });
        ed_fourth_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                setFocus(ed_fourth_no);
        });

        ed_first_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = ed_first_no.getText().toString();
                if (text.length() >= 4) {
                    if (text.length() > 4)
                        ed_first_no.setText(text.substring(0, 4));
                    setFocus(ed_second_no);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ed_second_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = ed_second_no.getText().toString();
                if (text.length() >= 4) {
                    if (text.length() > 4)
                        ed_second_no.setText(text.substring(0, 4));
                    setFocus(ed_third_no);
                } else if (ed_second_no.getText().toString().length() == 0) {
                    setFocus(ed_first_no);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ed_third_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = ed_third_no.getText().toString();
                if (text.length() >= 4) {
                    if (text.length() > 4)
                        ed_third_no.setText(text.substring(0, 4));
                    setFocus(ed_fourth_no);
                } else if (ed_third_no.getText().toString().length() == 0) {
                    setFocus(ed_second_no);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ed_fourth_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ed_fourth_no.getText().toString().length() == 0) {
                    setFocus(ed_third_no);
                } else if (ed_fourth_no.getText().toString().length() == 4) {
                    if (ed_first_no.getText().toString().length() == 4 && ed_second_no.getText().toString().length() == 4 &&
                            ed_third_no.getText().toString().length() == 4 && ed_fourth_no.getText().toString().length() == 4) {
                        if (fromSports)
                            verifyPayTicket(getTicketNumber());
                        else
                            callWinningVerify(getTicketNumber());
                    } else {
                        Utils.showRedToast(WinningClaimLandscapeActivity.this, getString(R.string.enter_valid_ticket_number));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

      /*  scannerView.setOnClickListener(v -> {
            ed_first_no.setText("");
            ed_second_no.setText("");
            ed_third_no.setText("");
            ed_fourth_no.setText("");
            if (mCodeScanner != null)
                mCodeScanner.startPreview();
        });*/
        setConfig();
        init();
    }

    private void setFocus(EditText editText) {
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit_dull));
        ed_second_no.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit_dull));
        ed_third_no.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit_dull));
        ed_fourth_no.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit_dull));
        editText.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit));
        editText.requestFocus();
        editText.setSelection(editText.getText().toString().length());
    }

    private void resetEditTexts() {
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit_dull));
        ed_second_no.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit_dull));
        ed_third_no.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit_dull));
        ed_fourth_no.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit_dull));
        ed_first_no.setText("");
        ed_second_no.setText("");
        ed_third_no.setText("");
        ed_fourth_no.setText("");
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimLandscapeActivity.this, R.drawable.epl_bg_edit));
        ed_first_no.requestFocus();

       /* if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
            mCamera.startPreview();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        // initBeepSound();
        initScanConfig();
        startFlash();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        refreshBalance();
    }

    private void startFlash() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ScanConfig.IS_OPEN_LIGHT) {
                    EaiUtil.controlLamp("1", ScanConfig.LIGHT_BRIGHT_TIME, ScanConfig.LIGHT_DROWN_TIME, EaiUtil.LED_CAM_NAME);
                } else {
                    EaiUtil.controlLamp("0", ScanConfig.LIGHT_BRIGHT_TIME, ScanConfig.LIGHT_DROWN_TIME, EaiUtil.LED_CAM_NAME);
                }
            }
        }).start();

    }

    private void initScanConfig() {
        //是否开启同一幅图一次解多个条码,0表示只解一个，1为多个
        if (ScanConfig.IDENTIFY_MORE_CODE) {
            mImageScanner.setConfig(0, Config.ENABLE_MULTILESYMS, 1);
        } else {
            mImageScanner.setConfig(0, Config.ENABLE_MULTILESYMS, 0);
        }
        //是否开启识别反色二维码,0表示不识别，1为识别
        if (ScanConfig.IDENTIFY_INVERSE_QR_CODE) {
            mImageScanner.setConfig(0, Config.ENABLE_INVERSE, 1);
        } else {
            mImageScanner.setConfig(0, Config.ENABLE_INVERSE, 0);
        }
    }

    public void onClickBack(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        WinningClaimLandscapeActivity.this.finish();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MENU_BEAN = bundle.getParcelable("MenuBean");
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            WinningClaimLandscapeActivity.this.finish();
        }
    }

    private void startScanning() {
      /*  if (Camera.getNumberOfCameras() <= 0) {
            //Toast.makeText(WinningClaimActivity.this, "Camera not found", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mCodeScanner = new CodeScanner(WinningClaimLandscapeActivity.this, scannerView);
            mCodeScanner.setDecodeCallback(result -> WinningClaimLandscapeActivity.this.runOnUiThread(() -> onScanned(result)));
          *//*  scannerView.setOnClickListener(view1 -> {
                if (mCodeScanner != null)
                    mCodeScanner.startPreview();

                ed_first_no.setText("");
                ed_second_no.setText("");
                ed_third_no.setText("");
                ed_fourth_no.setText("");
            });*//*
        } catch (Exception e) {
            Toast.makeText(WinningClaimLandscapeActivity.this, "Unable to open scanner", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void onScanned(String result) {
        String scannedValue = result.replaceAll("-", "");
        String number = "";
        if (scannedValue.length() == 16) {
            for (int index = 0; index < scannedValue.length(); index++) {
                if (index == 3) {
                    number = number + scannedValue.charAt(index);
                    ed_first_no.setText(number);
                    number = "";
                } else if (index == 7) {
                    number = number + scannedValue.charAt(index);
                    ed_second_no.setText(number);
                    number = "";
                } else if (index == 11) {
                    number = number + scannedValue.charAt(index);
                    ed_third_no.setText(number);
                    number = "";
                } else if (index == 15) {
                    number = number + scannedValue.charAt(index);
                    ed_fourth_no.setText(number);
                    number = "";
                } else {
                    number = number + scannedValue.charAt(index);
                }
            }
            Utils.vibrate(WinningClaimLandscapeActivity.this);
            //buttonProceed.performClick();
        }
    }

    private void setToolBar() {
        ImageView ivGameIcon = findViewById(R.id.ivGameIcon);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvUserBalance = findViewById(R.id.tvBal);
        tvUsername = findViewById(R.id.tvUserName);
        llBalance = findViewById(R.id.llBalance);

        tvTitle.setText(getString(R.string.winning_claim));
        ivGameIcon.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ed_first_no) {
            setFocus(ed_first_no);
        }
        if (view.getId() == R.id.ed_second_no) {
            if (ed_first_no.getText().toString().length() == 4)
                setFocus(ed_second_no);
            else
                setFocus(ed_first_no);
        }
        if (view.getId() == R.id.ed_third_no) {
            if (ed_second_no.getText().toString().length() == 4)
                setFocus(ed_third_no);
            else {
                if (ed_first_no.getText().toString().length() == 4)
                    setFocus(ed_second_no);
                else
                    setFocus(ed_first_no);
            }
        }
        if (view.getId() == R.id.ed_fourth_no) {
            if (ed_third_no.getText().toString().length() == 4)
                setFocus(ed_fourth_no);
            else {
                if (ed_second_no.getText().toString().length() == 4)
                    setFocus(ed_third_no);
                else {
                    if (ed_first_no.getText().toString().length() == 4)
                        setFocus(ed_second_no);
                    else
                        setFocus(ed_first_no);
                }
            }
        }
        if (view.getId() == R.id.button_proceed) {
            if (validate()) {
                if (fromSports)
                    verifyPayTicket(getTicketNumber());
                else
                    callWinningVerify(getTicketNumber());

            }
        }
    }

    private String getTicketNumber() {
        return ed_first_no.getText().toString() + ed_second_no.getText().toString() + ed_third_no.getText().toString() + ed_fourth_no.getText().toString();
    }

    private void callWinningVerify(String ticket_number) {
        scanTicket = false;
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText(this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "verifyTicket");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            WinningClaimVerifyRequestBean verifyRequestBean = new WinningClaimVerifyRequestBean();
            verifyRequestBean.setUserName(PlayerData.getInstance().getUsername());
            verifyRequestBean.setMerchantCode("LotteryRMS");
            verifyRequestBean.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            verifyRequestBean.setTicketNumber(ticket_number);
            verifyRequestBean.setLastPWTTicket(SharedPrefUtil.getLastTicketNumberWinning(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)));
            viewModel.callWinningVerify(urlBean, verifyRequestBean);
        }
    }

    private boolean validate() {
        Utils.vibrate(this);
        if (ed_first_no.getText().toString().length() != 4 || ed_second_no.getText().toString().length() != 4
                || ed_third_no.getText().toString().length() != 4 || ed_fourth_no.getText().toString().length() != 4) {
            Utils.showRedToast(WinningClaimLandscapeActivity.this, getString(R.string.enter_valid_ticket_number));
            return false;
        }
        return true;
    }

    Observer<WinningClaimVerifyResponseBean> observerVerifyWinning = response -> {
        //WinningClaimVerifyResponseBean response = new Gson().fromJson(res, WinningClaimVerifyResponseBean.class);
        String show_error = "";
        ProgressBarDialog.getProgressDialog().dismiss();
        boolean showError = false;
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            responseTicketNumber = response.getResponseData().getTicketNumber();
            for (int k = 0; k < response.getResponseData().getDrawData().size(); k++) {
                for (int j = 0; j < response.getResponseData().getDrawData().get(k).getPanelWinList().size(); j++) {
                    if (response.getResponseData().getDrawData().get(k).getPanelWinList().get(j).getStatus().equalsIgnoreCase("UNCLAIMED") && response.getResponseData().getWinClaimAmount() > 0) {
                        showError = false;
                        break;
                    } else {
                        showError = true;
                    }
                }
                if (!showError) {
                    break;
                }
            }
            for (int i = 0; i < response.getResponseData().getDrawData().size(); i++) {
                boolean isWinning = false;
                for (int j = 0; j < response.getResponseData().getDrawData().get(i).getPanelWinList().size(); j++) {
                    if (response.getResponseData().getDrawData().get(i).getPanelWinList().get(j).getStatus().equalsIgnoreCase("UNCLAIMED") && response.getResponseData().getWinClaimAmount() > 0) {
                        Utils.playWinningSound(this, R.raw.small_2);
                        CustomSuccessDialog.getProgressDialog().
                                showDialogClaim(this, response, this, getString(R.string.winning_claim), false, true);
                        isWinning = true;
                        break;
                    }
                    if (showError && ((j + 1) == response.getResponseData().getDrawData().get(i).getPanelWinList().size())) {
                        if (showError && response.getResponseData().getDrawData().get(i).getWinStatus().equalsIgnoreCase("WIN!!")) {
                            String time[] = response.getResponseData().getDrawData().get(i).getDrawTime().split(":");
                            String bold_status = "<b>" + getString(R.string.ticket_already_claimed) + "</b> ";
                            show_error = show_error + PrintUtil.printTwoStringWinningError(Utils.formatTimeWinningClaim(response.getResponseData().getDrawData().get(i).getDrawDate()) + " " + time[0] + ":" + time[1], String.valueOf(Html.fromHtml(bold_status))) + "\n";
                        } else if (response.getResponseData().getDrawData().get(i).getWinStatus().equalsIgnoreCase("NON WIN!!")) {
                            String time[] = response.getResponseData().getDrawData().get(i).getDrawTime().split(":");
                            String bold_status = "<b>" + getString(R.string.better_luck_next_time) + "</b> ";
                            show_error = show_error + PrintUtil.printTwoStringWinningError(Utils.formatTimeWinningClaim(response.getResponseData().getDrawData().get(i).getDrawDate()) + " " + time[0] + ":" + time[1], String.valueOf(Html.fromHtml(bold_status))) + "\n";
                        } else {
                            String time[] = response.getResponseData().getDrawData().get(i).getDrawTime().split(":");
                            SpannableString bold_status = new SpannableString(response.getResponseData().getDrawData().get(i).getWinStatus());
                            bold_status.setSpan(new StyleSpan(Typeface.BOLD), 0, response.getResponseData().getDrawData().get(i).getWinStatus().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            show_error = show_error + PrintUtil.printTwoStringWinningError(Utils.formatTimeWinningClaim(response.getResponseData().getDrawData().get(i).getDrawDate()) + " " + time[0] + ":" + time[1], String.valueOf(bold_status)) + "\n";
                        }
                    }
                }
                if (isWinning) {
                    break;
                }

            }
            if (showError) {
                ErrorDialogListener errorDialogListener = this::onDialogClosed;
                Utils.showCustomErrorDialogWinning(this, getString(R.string.winning_claim), show_error, errorDialogListener);
            }

        } else if (response.getResponseCode() == 1164) {
            if (getTicketNumber() != null)
                printDuplicateWinningReceipt(response.getResponseData().getUser(), getTicketNumber());

        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimLandscapeActivity.this, response.getResponseCode(), WinningClaimLandscapeActivity.this))
                return;
            ErrorDialogListener errorDialogListener = this::onDialogClosed;
            String errorMsg = Utils.getResponseMessage(WinningClaimLandscapeActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg, errorDialogListener);
        }
    };


    void onDialogClosed() {
        scanTicket = true;
    }

    Observer<WinningClaimPayResponseBean> observerWinningPay = response -> {
        //WinningClaimPayResponseBean response=new Gson().fromJson(res2,WinningClaimPayResponseBean.class);
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            if (responseTicketNumber != null) {
                Intent intent = new Intent(WinningClaimLandscapeActivity.this, PrintDrawGameActivity.class);
                intent.putExtra("PrintDataWinning", response);
                intent.putExtra("WinningLastTicket", responseTicketNumber);
                intent.putExtra("Category", PrintDrawGameActivity.WINNING);
                startActivityForResult(intent, REQUEST_CODE_PRINT);
            } else {
                Toast.makeText(this, getString(R.string.problem_with_ticket_number), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimLandscapeActivity.this, response.getResponseCode(), WinningClaimLandscapeActivity.this))
                return;
            ErrorDialogListener errorDialogListener = this::resetEditTexts;
            String errorMsg = Utils.getResponseMessage(WinningClaimLandscapeActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg, errorDialogListener);
        }
    };

    private void printDuplicateWinningReceipt(String user, String ticket_number) {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText(this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "initiatedpwt");
        if (urlBean != null) {
            viewModel.printDuplicateWinning(urlBean, user, ticket_number);
        }

    }

    Observer<WinningClaimPayResponseBean> observerWinningFailedPrint = response -> {
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            if (getTicketNumber() != null) {
                Intent intent = new Intent(WinningClaimLandscapeActivity.this, PrintDrawGameActivity.class);
                intent.putExtra("PrintDataWinning", response);
                intent.putExtra("WinningLastTicket", getTicketNumber());
                intent.putExtra("Category", PrintDrawGameActivity.WINNING);
                startActivityForResult(intent, REQUEST_CODE_PRINT);
            } else {
                Toast.makeText(this, getString(R.string.problem_with_ticket_number), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimLandscapeActivity.this, response.getResponseCode(), WinningClaimLandscapeActivity.this))
                return;
            ErrorDialogListener errorDialogListener = this::resetEditTexts;
            String errorMsg = Utils.getResponseMessage(WinningClaimLandscapeActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg, errorDialogListener);
        }
    };

    @Override
    public void dialogButtonPress(boolean isVerify) {
        if (isVerify) {
            callPayPwt();
        }
    }

    private void verifyPayTicket(String ticketNo) {
        scanTicket = false;
        ProgressBarDialog.getProgressDialog().showProgress(this);
        headerData1 = "userName," + BaseClassSle.getBaseClassSle().getVerifyBean().getUserName() + "|password," + BaseClassSle.getBaseClassSle().getVerifyBean().getPassword() + "|Content-Type," + BaseClassSle.getBaseClassSle().getVerifyBean().getContentType();
        object = new JsonObject();
        object.addProperty("userName", PlayerData.getInstance().getUsername() + "");
        object.addProperty("sessionId", PlayerData.getInstance().getToken().split(" ")[1]);
        object.addProperty("ticketNumber", ticketNo);
        object.addProperty("merchantCode", "NEWRMS");
        object.addProperty("modelCode", Utils.getModelCode());
        object.addProperty("terminalId", Utils.getDeviceSerialNumber());
        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getVerifyBean().getUrl() + "?requestData=" + URLEncoder.encode(object.toString(), "UTF-8"),

                    this, "loading", WinningClaimLandscapeActivity.this, "verify", headerData1);

            httpRequest.executeTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callPayPwt() {
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "claimWin");
        if (urlBean != null) {
            if (responseTicketNumber != null) {
                ProgressBarDialog.getProgressDialog().showProgress(this);
                WinningClaimPayRequestBean payRequestBean = new WinningClaimPayRequestBean();
                payRequestBean.setInterfaceType("WEB");
                payRequestBean.setMerchantCode("LotteryRMS");
                payRequestBean.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
                payRequestBean.setTicketNumber(responseTicketNumber);
                payRequestBean.setUserName(PlayerData.getInstance().getUsername());
                payRequestBean.setSaleMerCode("NDGE");
                payRequestBean.setVerificationCode("54564456415");
                payRequestBean.setModelCode(Utils.getModelCode());
                payRequestBean.setTerminalId(Utils.getDeviceSerialNumber());
                viewModel.callWinningPayPwt(urlBean, payRequestBean);
            } else
                Toast.makeText(this, getString(R.string.problem_with_ticket_number), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAutoFocusHandler != null) {
            mAutoFocusHandler.removeCallbacksAndMessages(null);
            mAutoFocusHandler = null;
        }
        if (mImageScanner != null) {
            mImageScanner.destroy();
        }

    }

    @Override
    public void onResponse(String response, String requestedMethod) {
        ProgressBarDialog.getProgressDialog().dismiss();
        Intent intent;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has("errorCode")) {
                //Utils.showCustomErrorDialog(WinningClaimLandscapeActivity.this, getString(R.string.winning_claim), jsonObject.optString("errorMsg"));
                String errorMsg = Utils.getResponseMessage(WinningClaimLandscapeActivity.this, "sle", Integer.parseInt(jsonObject.optString("errorCode")));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (requestedMethod.equalsIgnoreCase("verify")) {
            VerifyPayTicket verifyPayTicket = new Gson().fromJson(response, VerifyPayTicket.class);
            if (verifyPayTicket.getMessageCode() == 0 && verifyPayTicket.getResponseCode() == 0) {
                intent = new Intent();
                intent.putExtra("winning_response", response);
                setResult(03, intent);
                finish();
            } else if (jsonObject.has("responseCode") && verifyPayTicket.getResponseCode() != 0) {
                ErrorDialogListener errorDialogListener = this::onDialogClosed;
                //Utils.showCustomErrorDialog(WinningClaimLandscapeActivity.this, getString(R.string.winning_claim), verifyPayTicket.getResponseMsg(), errorDialogListener);
                String errorMsg = Utils.getResponseMessage(WinningClaimLandscapeActivity.this, "sle", verifyPayTicket.getResponseCode());
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg, errorDialogListener);
            } else {
                ErrorDialogListener errorDialogListener = this::onDialogClosed;
                //Utils.showCustomErrorDialog(WinningClaimLandscapeActivity.this, getString(R.string.winning_claim), verifyPayTicket.getMessage(), errorDialogListener);
                String errorMsg = Utils.getResponseMessage(WinningClaimLandscapeActivity.this, "sle", verifyPayTicket.getMessageCode());
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg, errorDialogListener);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isBalanceUpdate")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isBalanceUpdate", true);
            setResult(Activity.RESULT_OK, returnIntent);
            WinningClaimLandscapeActivity.this.finish();
        } else if (data != null && data.getExtras() != null && !data.getExtras().getBoolean("isBalanceUpdate")) {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION GRANTED
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(WinningClaimLandscapeActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                        } else if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(WinningClaimLandscapeActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                        } else {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    } else {
                        // PERMISSION DENIED
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                AppPermissions.showMessageOKCancel(WinningClaimLandscapeActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                            } else if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                AppPermissions.showMessageOKCancel(WinningClaimLandscapeActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                            }else {
                                init();
                                setConfig();
                            }
                        }
                        Utils.showRedToast(WinningClaimLandscapeActivity.this, getString(R.string.permission_storage_denied));
                        WinningClaimLandscapeActivity.this.finish();
                    }
                    break;
                }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
        }
        try {
            setCameraParameters();
//            mCamera.setDisplayOrientation(90);//竖屏显示
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(previewCallback);
            mCamera.startPreview();
            mCamera.autoFocus(autoFocusCallback);
        } catch (Exception e) {
            LogUtil.e("DBG", "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (scanTicket) {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = parameters.getPreviewSize();//获取预览分辨率

                //创建解码图像，并转换为原始灰度数据，注意图片是被旋转了90度的
                Image source = new Image(size.width, size.height, "Y800");
                //图片旋转了90度，将扫描框的TOP作为left裁剪
                source.setData(data);//填充数据
                asyncDecode = new AsyncDecode();
                asyncDecode.execute(source);//调用异步执行解码
            }
        }
    };

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            if (mAutoFocusHandler != null) {
                mAutoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        }
    };

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (null == mCamera || null == autoFocusCallback) {
                return;
            }
            mCamera.autoFocus(autoFocusCallback);
        }
    };

    private void setCameraParameters() {
        if (mCamera == null) return;
        //摄像头预览分辨率设置和图像放大参数设置，非必须，根据实际解码效果可取舍
        Camera.Parameters parameters = mCamera.getParameters();

        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        ScanConfig.BEST_RESOLUTION = getBestCameraResolution(parameters, new Point(display.getWidth(), display.getHeight()));
        parameters.setPreviewSize(320, 240);
//        parameters.set("orientation", "portrait");
//        parameters.set("zoom", String.valueOf(27 / 10.0));//放大图像2.7倍
        mCamera.setParameters(parameters);
    }

    private Point getBestCameraResolution(Camera.Parameters parameters, Point screenResolution) {
        float tmp = 0f;
        float mindiff = 100f;
        float x_d_y = (float) screenResolution.x / (float) screenResolution.y;
        Camera.Size best = null;
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size s : supportedPreviewSizes) {
            tmp = Math.abs(((float) s.height / (float) s.width) - x_d_y);
            if (tmp < mindiff) {
                mindiff = tmp;
                best = s;
            }
        }
        return new Point(best.width, best.height);
    }

    private class AsyncDecode extends AsyncTask<Image, Void, ArrayList<HashMap<String, String>>> {

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Image... params) {

            final ArrayList<HashMap<String, String>> result = new ArrayList<>();
            Image src_data = params[0];//获取灰度数据
            //解码，返回值为0代表失败，>0表示成功
            final int data = mImageScanner.scanImage(src_data);
            if (data != 0) {
                //  playBeepSoundAndVibrate();//解码成功播放提示音
                SymbolSet syms = mImageScanner.getResults();//获取解码结果
                for (Symbol sym : syms) {
                    HashMap<String, String> temp = new HashMap<>();
                    temp.put(ScanConfig.TYPE, sym.getSymbolName());
                    temp.put(ScanConfig.VALUE, sym.getResult());
                    result.add(temp);
                    if (!ScanConfig.IDENTIFY_MORE_CODE) {
                        break;
                    }
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);

            if (!result.isEmpty()) {

                if (ScanConfig.SCAN_MODE) {
                    runOnUiThread(() -> handleResult(result));

                } else {
                    onScanned(Objects.requireNonNull(result.get(0).get("VALUE")));
                  /*  setConfig();
                    init();*/
                }
            } else {
                isRUN.set(false);
            }
        }

    }

    private void handleResult(ArrayList<HashMap<String, String>> result) {

    }

}
