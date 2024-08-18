package com.skilrock.retailapp.virtual_sports.ui;

import android.Manifest;
import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.interfaces.WinningClaimListener;
import com.skilrock.retailapp.models.SbsReprintResponseBean;
import com.skilrock.retailapp.models.SbsWinPayResponse;
import com.skilrock.retailapp.models.SbsWinVerifyResponse;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.portrait_draw_games.ui.PrintDrawGameActivity;
import com.skilrock.retailapp.sbs.view_model.SbsWinningViewModel;
import com.skilrock.retailapp.utils.CustomSuccessDialog;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.ScanConfig;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.TranslateSbs;
import com.skilrock.retailapp.utils.Utils;
import com.sunmi.scan.Config;
import com.sunmi.scan.Image;
import com.sunmi.scan.ImageScanner;
import com.sunmi.scan.Symbol;
import com.sunmi.scan.SymbolSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import sunmi.sunmiui.utils.LogUtil;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

public class WinningClaimVirtualSportsLandActivity extends BaseActivity implements View.OnClickListener, WinningClaimListener, SurfaceHolder.Callback {
    private static final int REQUEST_CODE_PRINT = 1111;
    private EditText etTicketNumber;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Button buttonProceed;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    SbsWinningViewModel viewModel;
    private String ticket_no = null;
    EditText ed_first_no, ed_second_no, ed_third_no, ed_fourth_no;
    private FrameLayout contentFrame;
    private SurfaceHolder mHolder;
    private SurfaceView mSurfaceView;
    private WinningClaimVirtualSportsLandActivity.AsyncDecode asyncDecode;
    private ImageScanner mImageScanner;//Claim scanner
    private Handler mAutoFocusHandler;
    private AtomicBoolean isRUN = new AtomicBoolean(false);
    private Camera mCamera;
    private boolean scanTicket = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_claim_landscape);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        setToolBar();
        initializeWidgets();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MENU_BEAN = bundle.getParcelable("MenuBean");
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            WinningClaimVirtualSportsLandActivity.this.finish();
        }
    }

    private void setToolBar() {
        ImageView ivGameIcon    = findViewById(R.id.ivGameIcon);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        ImageView sbs_menu_img  = findViewById(R.id.sbs_menu_reprint);
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);

        LinearLayout sbs_menu = findViewById(R.id.sbs_reprint);
        sbs_menu_img.setImageResource(R.drawable.ic_reprint_sbs);
        tvTitle.setText(getString(R.string.winning_n_claim_sbs));
        ivGameIcon.setVisibility(View.GONE);
        sbs_menu.setVisibility(View.VISIBLE);
        refreshBalance();
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
        mSurfaceView      = (SurfaceView) findViewById(R.id.surface_view);
        mAutoFocusHandler = new Handler();
        mHolder           = mSurfaceView.getHolder();
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

    public void onClickBack(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        WinningClaimVirtualSportsLandActivity.this.finish();
    }

    private void callReprint() {
        ProgressBarDialog.getProgressDialog().showProgress(WinningClaimVirtualSportsLandActivity.this);
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "reprintLastTicket");
        if (urlBean != null) {
            viewModel.callSbsReprint(urlBean, Utils.getDeviceSerialNumber(), Long.parseLong(PlayerData.getInstance().getUserId()),
                    PlayerData.getInstance().getToken().split(" ")[1],
                    "0");
        }
    }

    private void initializeWidgets() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        viewModel = ViewModelProviders.of(this).get(SbsWinningViewModel.class);
        viewModel.getliveDataWinSbsVerify().observe(this, observerWinSbs);
        viewModel.getliveDataWinSbsPay().observe(this, observerWinSbsPay);
        viewModel.getLiveDataReprint().observe(this, observerReprint);
        etTicketNumber      = findViewById(R.id.et_ticket_number);
        buttonProceed       = findViewById(R.id.button_proceed);
        contentFrame        = findViewById(R.id.content_frame);
        ed_first_no         = findViewById(R.id.ed_first_no);
        ed_second_no        = findViewById(R.id.ed_second_no);
        ed_third_no         = findViewById(R.id.ed_third_no);
        ed_fourth_no        = findViewById(R.id.ed_fourth_no);

        buttonProceed.setOnClickListener(WinningClaimVirtualSportsLandActivity.this);
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
                        callWiningVerify(getTicketNumber());
                    } else {
                        Utils.showRedToast(WinningClaimVirtualSportsLandActivity.this, getString(R.string.enter_valid_ticket_number));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setConfig();
        init();
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

    private boolean validate() {
        Utils.vibrate(this);
        if (ed_first_no.getText().toString().length() != 4 || ed_second_no.getText().toString().length() != 4
                || ed_third_no.getText().toString().length() != 4 || ed_fourth_no.getText().toString().length() != 4) {
            Utils.showRedToast(WinningClaimVirtualSportsLandActivity.this, getString(R.string.enter_valid_ticket_number));
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initScanConfig();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        refreshBalance();
    }

    private void setFocus(EditText editText) {
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit_dull));
        ed_second_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit_dull));
        ed_third_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit_dull));
        ed_fourth_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit_dull));
        editText.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit));
        editText.requestFocus();
        editText.setSelection(editText.getText().toString().length());
    }

    private void resetEditTexts() {
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit_dull));
        ed_second_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit_dull));
        ed_third_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit_dull));
        ed_fourth_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit_dull));
        ed_first_no.setText("");
        ed_second_no.setText("");
        ed_third_no.setText("");
        ed_fourth_no.setText("");
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSportsLandActivity.this, R.drawable.epl_bg_edit));
        ed_first_no.requestFocus();
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
                callWiningVerify(getTicketNumber());
            }
        }
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
            Utils.vibrate(WinningClaimVirtualSportsLandActivity.this);
        }
    }

    private String getTicketNumber() {
        return ed_first_no.getText().toString() + ed_second_no.getText().toString() + ed_third_no.getText().toString() + ed_fourth_no.getText().toString();
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
                Image source = new Image(size.width, size.height, "Y800");
                source.setData(data);
                asyncDecode = new AsyncDecode();
                asyncDecode.execute(source);
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
        Camera.Parameters parameters = mCamera.getParameters();

        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        ScanConfig.BEST_RESOLUTION = getBestCameraResolution(parameters, new Point(display.getWidth(), display.getHeight()));
        parameters.setPreviewSize(320, 240);
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

                }
            } else {
                isRUN.set(false);
            }
        }

    }

    private void handleResult(ArrayList<HashMap<String, String>> result) {

    }

    @Override
    public void dialogButtonPress(boolean isVerify) {
        if (isVerify) {
            if (ticket_no != null)
                callWiningPay(ticket_no);
            else
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

    public void callWiningVerify(String ticket_no) {
        scanTicket = false;
        ProgressBarDialog.getProgressDialog().showProgress(WinningClaimVirtualSportsLandActivity.this);
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "getWinningDetails");
        if (urlBean != null) {
            viewModel.callSbsWinVerify(urlBean, Utils.getDeviceSerialNumber(), Long.parseLong(PlayerData.getInstance().getUserId()), PlayerData.getInstance().getToken().split(" ")[1], ticket_no);
        }
    }

    void onDialogClosed() {
        scanTicket = true;
        resetEditTexts();
    }

    public void callWiningPay(String ticNo) {
        ProgressBarDialog.getProgressDialog().showProgress(WinningClaimVirtualSportsLandActivity.this);
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "claimWinning");
        if (urlBean != null) {
            viewModel.callSbsWinPay(urlBean, Utils.getDeviceSerialNumber(), Long.parseLong(PlayerData.getInstance().getUserId()), PlayerData.getInstance().getToken().split(" ")[1], ticNo);
        }
    }

    private void startScanning() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isPrintSuccess")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isPrintSuccess", true);
            setResult(Activity.RESULT_OK, returnIntent);
            WinningClaimVirtualSportsLandActivity.this.finish();
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
                            AppPermissions.showMessageOKCancel(WinningClaimVirtualSportsLandActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                        } else if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(WinningClaimVirtualSportsLandActivity.this, getString(R.string.allow_permission), this, ed_first_no);
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
                                AppPermissions.showMessageOKCancel(WinningClaimVirtualSportsLandActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                            } else if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                AppPermissions.showMessageOKCancel(WinningClaimVirtualSportsLandActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                            } else {
                                init();
                                setConfig();
                            }
                        }
                        Utils.showRedToast(WinningClaimVirtualSportsLandActivity.this, getString(R.string.permission_storage_denied));
                        WinningClaimVirtualSportsLandActivity.this.finish();
                    }
                    break;
                }
        }
    }

    Observer<SbsWinVerifyResponse> observerWinSbs = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            if (response.getResponseData().getData().getTicket().getPayoutAmount() > 0 && response.getResponseData().getData().getTicket().getStatus().equalsIgnoreCase("READY_FOR_PAYOUT")) {
                ticket_no = response.getResponseData().getData().getTicketNo();
                Utils.playWinningSound(this, R.raw.small_2);
                CustomSuccessDialog.getProgressDialog().
                        showDialogSbs(this, response, this, getString(R.string.winning_claim), false, true);
            } else {
                ErrorDialogListener errorDialogListener = this::onDialogClosed;
                Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), TranslateSbs.getTranslateVal(response.getResponseData().getData().getTicket().getStatus(), WinningClaimVirtualSportsLandActivity.this), errorDialogListener);
            }

        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimVirtualSportsLandActivity.this, response.getResponseCode(), WinningClaimVirtualSportsLandActivity.this))
                return;

            String errorMsg = Utils.getResponseMessage(WinningClaimVirtualSportsLandActivity.this, "sbs", response.getResponseCode());
            ErrorDialogListener errorDialogListener = this::onDialogClosed;
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg, errorDialogListener);
        }
    };

    Observer<SbsWinPayResponse> observerWinSbsPay = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            if (ticket_no != null) {
                Intent intent = new Intent(WinningClaimVirtualSportsLandActivity.this, PrintActivityVirtualSports.class);
                intent.putExtra("PrintDataWinning", response);
                intent.putExtra("Category", PrintDrawGameActivity.WINNING);
                startActivityForResult(intent, REQUEST_CODE_PRINT);
            } else {
                Toast.makeText(this, getString(R.string.problem_with_ticket_number), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimVirtualSportsLandActivity.this, response.getResponseCode(), WinningClaimVirtualSportsLandActivity.this))
                return;
            ErrorDialogListener errorDialogListener = this::resetEditTexts;
            String errorMsg = Utils.getResponseMessage(WinningClaimVirtualSportsLandActivity.this, "sbs", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg, errorDialogListener);
        }
    };

    Observer<SbsReprintResponseBean> observerReprint = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            Intent intent = new Intent(WinningClaimVirtualSportsLandActivity.this, PrintActivityVirtualSports.class);
            intent.putExtra("ReprintData", response);
            intent.putExtra("Category", PrintActivityVirtualSports.REPRINT);
            startActivityForResult(intent, REQUEST_CODE_PRINT);

        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimVirtualSportsLandActivity.this, response.getResponseCode(), WinningClaimVirtualSportsLandActivity.this))
                return;

            String errorMsg = Utils.getResponseMessage(WinningClaimVirtualSportsLandActivity.this, "sbs", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), errorMsg);
        }
    };

    public void onClickSbsMenuReprint(View view) {
        ConfirmationListener confirmationListener = () -> {
            callReprint();
        };
        Utils.showReprintTicketConfirmationDialog(WinningClaimVirtualSportsLandActivity.this, confirmationListener);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }
}
