package com.skilrock.retailapp.ui.fragments.scratch;

import android.Manifest;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScannerView;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.ClaimDialog;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.interfaces.WinningClaimListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.scratch.WinningDisplayDialogBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.portrait_draw_games.ui.PrintDrawGameActivity;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CustomSuccessDialog;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.ScanConfig;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.WinningClaimViewModel;
import com.sunmi.scan.Config;
import com.sunmi.scan.Image;
import com.sunmi.scan.ImageScanner;
import com.sunmi.scan.Symbol;
import com.sunmi.scan.SymbolSet;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import sunmi.sunmiui.utils.LogUtil;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

public class WinningClaimLandscapeFragment extends BaseFragment implements View.OnClickListener, SurfaceHolder.Callback {

    private static final int REQUEST_CODE_WINNING = 1111;
    private WinningClaimViewModel winningClaimViewModel;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};
    private EditText etTicketNumber;
    private CodeScannerView scannerView;
    boolean running=false;
    private Button buttonProceed;
    private UsbThermalPrinter usbThermalPrinter = null;
    private TextInputLayout tilTicketNumber;
    private boolean isClicked = false;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private final String SCRATCH = "scratch";
    private WinningDisplayDialogBean winningModel;
    FrameLayout contentFrame;
    private SurfaceHolder mHolder;
    private SurfaceView mSurfaceView;
    private WinningClaimLandscapeFragment.AsyncDecode asyncDecode;
    private ImageScanner mImageScanner;//Claim scanner
    private Handler mAutoFocusHandler;
    private AtomicBoolean isRUN = new AtomicBoolean(false);
    private Camera mCamera;
    private boolean scanTicket = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_winning_claim_landscape, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            winningClaimViewModel = ViewModelProviders.of(this).get(WinningClaimViewModel.class);
            winningClaimViewModel.getLiveDataBalance().observe(this, observerBalance);
            winningClaimViewModel.getLiveDataBalanceTerminal().observe(this, observerBalanceTerminal);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        running=true;
        if (SharedPrefUtil.getLanguage(master).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        initializeWidgets(view);
        mSurfaceView = view.findViewById(R.id.surface_view);
        mAutoFocusHandler = new Handler();
        mHolder = mSurfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
        mImageScanner = new ImageScanner();//创建扫描器
        mImageScanner.setConfig(0, Config.X_DENSITY, 2);//行扫描间隔
        mImageScanner.setConfig(0, Config.Y_DENSITY, 2);//列扫描间隔

        if (!ScanConfig.IS_SHOW_SETTING) {
            //mSetting.setVisibility(View.GONE);
        }
        winningClaimViewModel.getGameListData().observe(this, gameListBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            if (gameListBean == null)
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong));
            else if (gameListBean.getResponseCode() == 57575) {
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket));
                etTicketNumber.setText("");
            } else if (gameListBean.getResponseCode() == 75757)
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, getString(R.string.no_games_found));
            else if (gameListBean.getResponseCode() == 74747)
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket_length));
            else {
                //String errorMsg = TextUtils.isEmpty(gameListBean.getResponseMessage()) ? getString(R.string.error_fetching_gamelist) : gameListBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, gameListBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg);
            }
            allowBackAction(true);
        });

        winningClaimViewModel.getVerifyTicketResponseData().observe(this, verifyTicketResponseNewBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            isClicked = false;
            if (verifyTicketResponseNewBean == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ticket_verification), getString(R.string.something_went_wrong));
            else if (verifyTicketResponseNewBean.getResponseCode() == 1000) {
                Utils.playWinningSound(master, R.raw.small_2);
                WinningDisplayDialogBean model = new WinningDisplayDialogBean();
                model.setTicketNumber(verifyTicketResponseNewBean.getTicketNumber());
                model.setWinningAmount(String.valueOf(verifyTicketResponseNewBean.getWinningAmount()));
                model.setVirnNumber(verifyTicketResponseNewBean.getVirnNumber());
                CustomSuccessDialog.getProgressDialog().showClaimDialog(master, getFragmentManager(), model, listener, getString(R.string.claim_ticket), false, true);
            } else if (verifyTicketResponseNewBean.getResponseCode() == 1431) {
                Utils.playWinningSound(master, R.raw.big_2);
                String msg = Utils.getResponseMessage(master, SCRATCH, verifyTicketResponseNewBean.getResponseCode());
                //Utils.showCustomSuccessDialog(master, getFragmentManager(), "", msg, 1, master.getResources().getString(R.string.ok));
                CustomSuccessDialog.getProgressDialog().showCustomInfoDialog(master, getFragmentManager(), getString(R.string.winning), msg, 1, master.getResources().getString(R.string.ok));
            } else if (verifyTicketResponseNewBean.getResponseCode() == 1428) {
                Utils.playWinningSound(master, R.raw.big_2);
                String msg = Utils.getResponseMessage(master, SCRATCH, verifyTicketResponseNewBean.getResponseCode());
                //Utils.showCustomSuccessDialog(master, getFragmentManager(), "", msg, 1, master.getResources().getString(R.string.ok));
                CustomSuccessDialog.getProgressDialog().showCustomInfoDialog(master, getFragmentManager(), getString(R.string.winning), msg, 1, master.getResources().getString(R.string.ok));
            } else if (verifyTicketResponseNewBean.getResponseCode() == 1402 || verifyTicketResponseNewBean.getResponseCode() == 1401) {
                String msg = Utils.getResponseMessage(master, SCRATCH, verifyTicketResponseNewBean.getResponseCode());
                //Utils.showCustom(master, getFragmentManager(), "", msg, 1, master.getResources().getString(R.string.ok));
                CustomSuccessDialog.getProgressDialog().showCustomInfoDialog(master, getFragmentManager(), getString(R.string.ticket_verification), msg, 1, master.getResources().getString(R.string.ok));
            } else {
                if (Utils.checkForSessionExpiry(master, verifyTicketResponseNewBean.getResponseCode()))
                    return;
                ErrorDialogListener errorDialogListener = this::onDialogClosed;
                //String errorMsg = TextUtils.isEmpty(verifyTicketResponseNewBean.getResponseMessage()) ? getString(R.string.some_internal_error) : verifyTicketResponseNewBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, verifyTicketResponseNewBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ticket_verification), errorMsg, errorDialogListener);
            }
            allowBackAction(true);
        });

        winningClaimViewModel.getClaimTicketResponseData().observe(this, claimTicketResponseBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            isClicked = false;

            if (claimTicketResponseBean == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), getString(R.string.something_went_wrong));
            else if (claimTicketResponseBean.getResponseCode() == 1000) {
                if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                    Intent intent = new Intent(getActivity(), PrintDrawGameActivity.class);
                    intent.putExtra("PrintDataScratch", claimTicketResponseBean);
                    intent.putExtra("Category", PrintDrawGameActivity.WINNING_SCRATCH);
                    startActivityForResult(intent, REQUEST_CODE_WINNING);
                } else {
                    winningModel = new WinningDisplayDialogBean();
                    winningModel.setTransactionNumber(claimTicketResponseBean.getTransactionNumber());
                    winningModel.setTransactionDate(claimTicketResponseBean.getTransactionDate());

                    String tdsAmount = claimTicketResponseBean.getTdsAmount() == null ? "0" : String.valueOf(claimTicketResponseBean.getTdsAmount());
                    winningModel.setTicketNumber(claimTicketResponseBean.getTicketNumber());
                    winningModel.setWinningAmount(String.valueOf(claimTicketResponseBean.getWinningAmount()));
                    winningModel.setTdsAmount(tdsAmount);
                    winningModel.setNetWinningAmount(String.valueOf(claimTicketResponseBean.getNetWinningAmount()));
                    winningModel.setCommissionAmount(String.valueOf(claimTicketResponseBean.getCommissionAmount()));

                    winningClaimViewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
                    /*ClaimDialog dialog = new ClaimDialog(master, getFragmentManager(), listener, "", false, winningModel);
                    dialog.show();
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }*/
                }
            } else {
                if (Utils.checkForSessionExpiry(master, claimTicketResponseBean.getResponseCode()))
                    return;

                String errorMsg = Utils.getResponseMessage(master, SCRATCH, claimTicketResponseBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), errorMsg);
            }
            allowBackAction(true);
        });
        setConfig();
    }

    private void setConfig() {
        Intent intent = getActivity().getIntent();
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

    Observer<LoginBean> observerBalance = new Observer<LoginBean>() {
        @Override
        public void onChanged(@Nullable LoginBean loginBean) {
            ProgressBarDialog.getProgressDialog().dismiss();
            allowBackAction(true);

            ClaimDialog dialog;

            if (loginBean == null) {
                dialog = new ClaimDialog(master, getFragmentManager(), listener, "", false, winningModel, true);
                CustomSuccessDialog.getProgressDialog().showClaimDialog(master, getFragmentManager(), winningModel, listener, "", true, false);
            } else if (loginBean.getResponseCode() == 0) {
                LoginBean.ResponseData responseData = loginBean.getResponseData();
                if (responseData.getStatusCode() == 0) {
                    PlayerData.getInstance().setLoginData(master, loginBean);
                    refreshBalance();
                    CustomSuccessDialog.getProgressDialog().showClaimDialog(master, getFragmentManager(), winningModel, listener, "", false, false);
                } else {
                    CustomSuccessDialog.getProgressDialog().showClaimDialog(master, getFragmentManager(), winningModel, listener, "", true, false);
                }
            } else {
                CustomSuccessDialog.getProgressDialog().showClaimDialog(master, getFragmentManager(), winningModel, listener, "", true, false);

            }

        }
    };


    Observer<LoginBean> observerBalanceTerminal = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        allowBackAction(true);


        if (loginBean == null) {
            Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), getString(R.string.something_went_wrong));
        } else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                PlayerData.getInstance().setLoginData(master, loginBean);
                refreshBalance();
            }
            Objects.requireNonNull(getActivity()).onBackPressed();
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etTicketNumber = view.findViewById(R.id.et_ticket_number);
        scannerView = view.findViewById(R.id.scanner_view);
        buttonProceed = view.findViewById(R.id.button_proceed);
        tilTicketNumber = view.findViewById(R.id.textInputLayoutChallanNumber);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        contentFrame = view.findViewById(R.id.content_frame);
        refreshBalance();
        buttonProceed.setOnClickListener(this);
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            contentFrame.setVisibility(View.VISIBLE);
            if (AppPermissions.checkPermission(getActivity())){
                //startScanning();
            }
           /* else AppPermissions.requestPermission(getActivity());
            if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
            }*/
            if (!hasPermissions(getActivity(), PERMISSIONS)) {
                requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
            }


        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            contentFrame.setVisibility(View.INVISIBLE);
        }

        checkEarphones();
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        etTicketNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                buttonProceed.performClick();;
                return true;
            }
            return false;
        });

//        scannerView.setOnClickListener(v -> {etTicketNumber.setText(""); if(mCodeScanner != null) mCodeScanner.startPreview();});
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean hasPermissions(Context context, String [] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private void checkEarphones() {
        Handler handler = new Handler();
        AudioManager audioManager = (AudioManager) master.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.isWiredHeadsetOn()) {


            CustomSuccessDialog.getProgressDialog().showCustomWarningDialog(master, getFragmentManager(), getString(R.string.earphones_detected), getString(R.string.unplug_earphones), 1, getString(R.string.ok));
        }
    }

    @Override
    public void onClick(View v) {
        if (validate() && isClickAllowed()) {
            Handler handler = new Handler();
            AudioManager audioManager = (AudioManager) master.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.isWiredHeadsetOn()) {

                CustomSuccessDialog.getProgressDialog().showCustomWarningDialog(master, getFragmentManager(), getString(R.string.earphones_detected), getString(R.string.unplug_earphones), 1, getString(R.string.ok));
                return;
            }
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }
            isClicked = true;
            callApi("verifyTicket");
        }
    }

    private void callApi(String urlTag) {
        scanTicket = false;
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlBean verifyTicketUrl = Utils.getUrlDetails(menuBean, getContext(), urlTag);
        UrlBean gameListUrl = Utils.getUrlDetails(menuBean, getContext(), "gameList");
        if (verifyTicketUrl != null && gameListUrl != null) {
            allowBackAction(false);
            ProgressBarDialog.getProgressDialog().showProgress(this.getContext());
            winningClaimViewModel.callVerifyWinningTicket(verifyTicketUrl, etTicketNumber.getText().toString().trim());
        }
    }

    private void startScanning() {
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.detach(this);
        fragmentTransaction.attach(this);
        fragmentTransaction.commit();
    }

    private void onScanned(String result) {
        etTicketNumber.setText(result);
        Utils.vibrate(master);
        buttonProceed.performClick();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanning();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), etTicketNumber);
                    }else if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), etTicketNumber);
                    }
                }
            }
        }
    }

    private boolean validate() {
        Utils.vibrate(master);
        if (TextUtils.isEmpty(etTicketNumber.getText().toString().trim())) {
            etTicketNumber.setError(getString(R.string.enter_barcode_number));
            etTicketNumber.requestFocus();
            tilTicketNumber.startAnimation(Utils.shakeError());
            return false;
        }
        return true;
    }

    WinningClaimListener listener = new WinningClaimListener() {
        @Override
        public void dialogButtonPress(boolean isVerify) {
            if (isVerify) {
                if (!Utils.isNetworkConnected(master)) {
                    Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                UrlBean urlBeanClaimWin = Utils.getUrlDetails(menuBean, getContext(), "claimWin");
                if (urlBeanClaimWin != null) {
                    allowBackAction(false);
                    ProgressBarDialog.getProgressDialog().showProgress(master);
                    winningClaimViewModel.callClaimTicket(urlBeanClaimWin, etTicketNumber.getText().toString().trim());
                }
            } else {
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null)
                    fragmentManager.popBackStack();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        initScanConfig();
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

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAutoFocusHandler != null) {
            mAutoFocusHandler.removeCallbacksAndMessages(null);
            mAutoFocusHandler = null;
        }
        if (mImageScanner != null) {
            mImageScanner.destroy();
        }
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isBalanceUpdate")) {
            //Objects.requireNonNull(getActivity()).onBackPressed();
            winningClaimViewModel.getUpdatedBalanceTerminal(PlayerData.getInstance().getToken());
        } else {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(getActivity(), getString(R.string.winning), errorMsg);
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
                if (running) {
                    asyncDecode = new WinningClaimLandscapeFragment.AsyncDecode();
                    asyncDecode.execute(source);//调用异步执行解码
                }
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

        WindowManager manager = (WindowManager) master.getSystemService(Context.WINDOW_SERVICE);
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
                    master.runOnUiThread(() -> handleResult(result));

                } else {
                    onScanned(result.get(0).get("VALUE"));
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

    void onDialogClosed() {
        scanTicket = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        running=false;
    }

}
