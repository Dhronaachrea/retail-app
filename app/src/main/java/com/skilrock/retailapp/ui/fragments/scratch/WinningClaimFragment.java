package com.skilrock.retailapp.ui.fragments.scratch;

import android.Manifest;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.zxing.Result;
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
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.barcode.BarcodeFormat;
import com.skilrock.retailapp.utils.barcode.BarcodeResultParser;
import com.skilrock.retailapp.viewmodels.scratch.CameraXViewModel;
import com.skilrock.retailapp.viewmodels.scratch.WinningClaimViewModel;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

public class WinningClaimFragment extends BaseFragment implements View.OnClickListener, ErrorDialogListener {

    private static final int REQUEST_CODE_WINNING = 1111;
    private WinningClaimViewModel winningClaimViewModel;
    private EditText etTicketNumber;
    private CodeScanner mCodeScanner;
    private ImageView ivFlash;
    private CodeScannerView scannerView;
    private Button buttonProceed;
    private UsbThermalPrinter usbThermalPrinter = null;
    private TextInputLayout tilTicketNumber;
    private boolean isClicked = false;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private final String SCRATCH = "scratch";
    private WinningDisplayDialogBean winningModel;
    FrameLayout contentFrame;
    private static final int PERMISSION_CAMERA_REQUEST = 1;

    private PreviewView previewView;
    private ProcessCameraProvider cameraProvider = null;
    private CameraSelector cameraSelector = null;
    private final int lensFacing = CameraSelector.LENS_FACING_BACK;
    private Preview previewUseCase = null;
    private ImageAnalysis analysisUseCase = null;
    private ArrayList<String> scannedNumbersList = new ArrayList<String>();
    private boolean isLocked = true;
    private boolean isFlashLightOn = false;
    private androidx.camera.core.Camera camm = null;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_winning_claim, container, false);           //earlier
        return inflater.inflate(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI) ? R.layout.fragment_winning_claim_landscape : R.layout.fragment_winning_claim, container, false);

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
        Log.d("TAg", "--------> Claim <------");
        if (SharedPrefUtil.getLanguage(master).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        initializeWidgets(view);

        ErrorDialogListener errorDialogListener = this::callBack;

        winningClaimViewModel.getGameListData().observe(this, gameListBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (mCodeScanner != null)
                mCodeScanner.stopPreview();

            if (gameListBean == null)
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong), errorDialogListener);
            else if (gameListBean.getResponseCode() == 57575) {
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket), errorDialogListener);
                etTicketNumber.setText("");
            } else if (gameListBean.getResponseCode() == 75757)
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, getString(R.string.no_games_found), errorDialogListener);
            else if (gameListBean.getResponseCode() == 74747)
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket_length), errorDialogListener);
            else {
                //String errorMsg = TextUtils.isEmpty(gameListBean.getResponseMessage()) ? getString(R.string.error_fetching_gamelist) : gameListBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, gameListBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
            }
            allowBackAction(true);
        });

        winningClaimViewModel.getVerifyTicketResponseData().observe(this, verifyTicketResponseNewBean -> {
            /*String res = "{\n" +
                    "  \"responseCode\": 1000,\n" +
                    "  \"responseMessage\": \"Success\",\n" +
                    "  \"ticketNumber\": \"92-004008-011\",\n" +
                    "  \"virnNumber\": \"9238333267\",\n" +
                    "  \"winningAmount\": \"10000\"\n" +
                    "}";
            VerifyTicketResponseNewBean verifyTicketResponseNewBean = new Gson().fromJson(res, VerifyTicketResponseNewBean.class);*/
            ProgressBarDialog.getProgressDialog().dismiss();
            isClicked = false;
            if (mCodeScanner != null)
                mCodeScanner.stopPreview();

            if (verifyTicketResponseNewBean == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ticket_verification), getString(R.string.something_went_wrong), errorDialogListener);
            else if (verifyTicketResponseNewBean.getResponseCode() == 1000) {
                Utils.playWinningSound(master, R.raw.tone_dialog);
                WinningDisplayDialogBean model = new WinningDisplayDialogBean();
                model.setTicketNumber(verifyTicketResponseNewBean.getTicketNumber());
                model.setWinningAmount(String.valueOf(verifyTicketResponseNewBean.getWinningAmount()));
                model.setVirnNumber(verifyTicketResponseNewBean.getVirnNumber());
                model.setNetWinningAmount(verifyTicketResponseNewBean.getNetWinningAmount());
                model.setTaxAmount(verifyTicketResponseNewBean.getTaxAmount());
                Log.i("TaG", "=====================first time====================");
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
                //CustomSuccessDialog.getProgressDialog().showCustomInfoDialog(master, getFragmentManager(), getString(R.string.ticket_verification), msg, 1, master.getResources().getString(R.string.ok));
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ticket_verification), msg, errorDialogListener);
            } else {
                if (Utils.checkForSessionExpiry(master, verifyTicketResponseNewBean.getResponseCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(verifyTicketResponseNewBean.getResponseMessage()) ? getString(R.string.some_internal_error) : verifyTicketResponseNewBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, verifyTicketResponseNewBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.ticket_verification), errorMsg, errorDialogListener);
            }
            allowBackAction(true);
        });

        winningClaimViewModel.getClaimTicketResponseData().observe(this, claimTicketResponseBean -> {
          /*  String res2 = "{\n" +
                    "  \"responseCode\": 1000,\n" +
                    "  \"responseMessage\": \"Success\",\n" +
                    "  \"transactionId\": \"893\",\n" +
                    "  \"transactionNumber\": \"WCRR2003000893\",\n" +
                    "  \"transactionDate\": \"2020-03-16 15:35:00\",\n" +
                    "  \"ticketNumber\": \"92-004008-089\",\n" +
                    "  \"virnNumber\": \"9262987987\",\n" +
                    "  \"winningAmount\": 20000,\n" +
                    "  \"commissionAmount\": 1000,\n" +
                    "  \"tdsAmount\": 0,\n" +
                    "  \"netWinningAmount\": 21000,\n" +
                    "  \"requestId\": \"2730\",\n" +
                    "  \"txnStatus\": \"DONE\",\n" +
                    "  \"advMessages\": {\n" +
                    "    \"top\": [\n" +
                    "      {\n" +
                    "        \"msgText\": \"scratch test\",\n" +
                    "        \"msgMode\": \"PRINT\",\n" +
                    "        \"msgType\": \"TEXT\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"bottom\": null\n" +
                    "  }\n" +
                    "}";
            ClaimTicketResponseNewBean claimTicketResponseBean = new Gson().fromJson(res2, ClaimTicketResponseNewBean.class);*/
            ProgressBarDialog.getProgressDialog().dismiss();
            isClicked = false;
            if (mCodeScanner != null)
                mCodeScanner.stopPreview();

            if (claimTicketResponseBean == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), getString(R.string.something_went_wrong), errorDialogListener);
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
                    winningModel.setTaxAmount(String.valueOf(claimTicketResponseBean.getTaxAmount()));

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
                Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), errorMsg, errorDialogListener);
            }
            allowBackAction(true);
        });

    }

    ErrorDialogListener errorDialogListener = this::callBack;

    Observer<LoginBean> observerBalance = new Observer<LoginBean>() {
        @Override
        public void onChanged(@Nullable LoginBean loginBean) {
            ProgressBarDialog.getProgressDialog().dismiss();
            allowBackAction(true);

            if (mCodeScanner != null)
                mCodeScanner.stopPreview();

            ClaimDialog dialog;

            if (loginBean == null) {
                //dialog = new ClaimDialog(master, getFragmentManager(), listener, "", false, winningModel, true);
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

    Observer<LoginBean> observerBalanceTerminal = new Observer<LoginBean>() {
        @Override
        public void onChanged(@Nullable LoginBean loginBean) {

            ProgressBarDialog.getProgressDialog().dismiss();
            allowBackAction(true);

            if (mCodeScanner != null)
                mCodeScanner.stopPreview();
            if (loginBean == null) {
                Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), getString(R.string.something_went_wrong), errorDialogListener);
            } else if (loginBean.getResponseCode() == 0) {
                LoginBean.ResponseData responseData = loginBean.getResponseData();
                if (responseData.getStatusCode() == 0) {
                    PlayerData.getInstance().setLoginData(master, loginBean);
                    refreshBalance();
                }
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        }

    };

    private void callBack() {
        etTicketNumber.setText("");
        final Handler handler1 = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second

        handler1.postDelayed(new Runnable() {
            public void run() {
                if (isAdded() && isVisible()) {
                    isFlashLightOn = true;
                    enableDisableFlashLight();
                    setupCamera();
                    //handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etTicketNumber = view.findViewById(R.id.et_ticket_number);
        scannerView = view.findViewById(R.id.scanner_view);
        previewView = view.findViewById(R.id.preview_view);
        ivFlash = view.findViewById(R.id.ivFlash);
        buttonProceed = view.findViewById(R.id.button_proceed);
        tilTicketNumber = view.findViewById(R.id.textInputLayoutTicketNumber);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        contentFrame = view.findViewById(R.id.content_frame);
        refreshBalance();
        buttonProceed.setOnClickListener(this);
        ivFlash.setOnClickListener(this);

        contentFrame.setVisibility(View.VISIBLE);
            /*if (AppPermissions.checkPermission(getActivity())) startScanning();
            else AppPermissions.requestPermission(getActivity());*/

        handler = new Handler();
        final int delay = 200; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                isLocked = false;
                Log.e("----------------", "mein call huaa hu");
                handler.postDelayed(this, delay);
            }
        }, delay);

        setupCamera();

            /*if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                contentFrame.setVisibility(View.VISIBLE);
                if (AppPermissions.checkPermission(getActivity())) startScanning();
                else AppPermissions.requestPermission(getActivity());
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                contentFrame.setVisibility(View.INVISIBLE);
            }*/

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
                buttonProceed.performClick();
                return true;
            }
            return false;
        });

            /*scannerView.setOnClickListener(v -> {
                etTicketNumber.setText("");
                if (mCodeScanner != null) mCodeScanner.startPreview();
            });*/
    }

    private void checkEarphones() {
        Handler handler2 = new Handler();
        AudioManager audioManager = (AudioManager) master.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.isWiredHeadsetOn()) {
            if (mCodeScanner != null)
                handler2.postDelayed(() -> mCodeScanner.stopPreview(), 500);

            CustomSuccessDialog.getProgressDialog().showCustomWarningDialog(master, getFragmentManager(), getString(R.string.earphones_detected), getString(R.string.unplug_earphones), 1, getString(R.string.ok));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_proceed: {
                if (validate() && isClickAllowed()) {
                    Handler handler3 = new Handler();
                    AudioManager audioManager = (AudioManager) master.getSystemService(Context.AUDIO_SERVICE);
                    if (audioManager.isWiredHeadsetOn()) {
                        if (mCodeScanner != null)
                            handler3.postDelayed(() -> mCodeScanner.stopPreview(), 500);

                        CustomSuccessDialog.getProgressDialog().showCustomWarningDialog(master, getFragmentManager(), getString(R.string.earphones_detected), getString(R.string.unplug_earphones), 1, getString(R.string.ok));
                        return;
                    }
                        /*BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                            mBluetoothAdapter.disable();
                        }*/
                    isClicked = true;
                    callApi("verifyTicket");
                }
                break;
            }
            case R.id.ivFlash: {
                enableDisableFlashLight();
            }
        }
    }

    private void callApi(String urlTag) {
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

    private void enableDisableFlashLight() {
        if (camm != null) {
            if (camm.getCameraInfo().hasFlashUnit()) {
                if (!isFlashLightOn) {
                    camm.getCameraControl().enableTorch(true);
                    isFlashLightOn = true;
                    ivFlash.setImageResource(R.drawable.baseline_flash_on_24);
                } else {
                    camm.getCameraControl().enableTorch(false);
                    isFlashLightOn = false;
                    ivFlash.setImageResource(R.drawable.baseline_flash_off_24);
                }
            }
        }
    }

    private void setupCamera() {

        cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();
        ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.getInstance(master.getApplication())).get(CameraXViewModel.class)
                .getProcessCameraProvider().observe(this, provider -> {
                    cameraProvider = provider;
                    if (isCameraPermissionGranted()) {
                        bindCameraUseCases();
                    } else {
                        ActivityCompat.requestPermissions(master, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST);
                    }
                });
    }

    public boolean areSame() {
        // Put all array elements in a HashSet
        Set<String> s = new HashSet<>(scannedNumbersList);

        // If all elements are same, size of
        // HashSet should be 1. As HashSet contains only distinct values.
        return (s.size() == 1);
    }

    Boolean isCameraPermissionGranted() {
        return ContextCompat.checkSelfPermission(master, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    void bindCameraUseCases() {
        bindPreviewUseCase();
        bindAnalyseUseCase();
    }

    private void bindPreviewUseCase() {
        if (cameraProvider == null) {
            return;
        }
        if (previewUseCase != null) {
            cameraProvider.unbind(previewUseCase);
        }

        previewUseCase = new Preview.Builder()
                .setTargetRotation(previewView.getDisplay().getRotation())
                .build();
        previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());

        try {

            camm = cameraProvider.bindToLifecycle(this, cameraSelector, previewUseCase);
        } catch (IllegalStateException illegalStateException) {
            Log.e("TAg", "IllegalStateException");
        }
    }

    private void bindAnalyseUseCase() {
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                        .build();

        //BarcodeScanner scanner = BarcodeScanning.getClient();
        // Or, to specify the formats to recognize:
        BarcodeScanner barcodeScanner = BarcodeScanning.getClient(options);

        if (cameraProvider == null) {
            return;
        }
        if (analysisUseCase != null) {
            cameraProvider.unbind(analysisUseCase);
        }

        analysisUseCase = new ImageAnalysis.Builder()
                .setTargetRotation(previewView.getDisplay().getRotation())
                .build();

        // Initialize our background executor
        ExecutorService cameraExecutor = Executors.newSingleThreadExecutor();

        analysisUseCase.setAnalyzer(cameraExecutor, image -> processImageProxy(barcodeScanner, image));

        try {
            cameraProvider.bindToLifecycle(
                    /* lifecycleOwner= */this,
                    cameraSelector,
                    analysisUseCase
            );
        } catch (IllegalStateException illegalStateException) {
            Log.e("TAg", "IllegalStateException");
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    void processImageProxy(BarcodeScanner barcodeScanner, ImageProxy imageProxy) {
        InputImage inputImage = InputImage.fromMediaImage(imageProxy.getImage(), imageProxy.getImageInfo().getRotationDegrees());

        barcodeScanner.process(inputImage).addOnSuccessListener(barcodes -> {
            if (!isLocked) {
                isLocked = true;
                for (Barcode barcode : barcodes) {
                    Rect bounds = barcode.getBoundingBox();
                    Point[] corners = barcode.getCornerPoints();
                    String rawValue = barcode.getRawValue();

                    com.skilrock.retailapp.utils.barcode.Barcode result = new BarcodeResultParser().parseBarcode(rawValue, BarcodeFormat.GS1_128);

                    assert result != null;
                    rawValue = result.getFields().get(0).getRawData().trim();


                    if (scannedNumbersList.size() >= 2) {
                        if (areSame()) {
                            Log.d("TAg", "areSame: " + areSame());
                            etTicketNumber.setText(rawValue);
                            Utils.vibrate(master);
                            buttonProceed.performClick();
                            cameraProvider.unbindAll();
                        } else {
                            scannedNumbersList.clear();
                            Toast.makeText(getContext(), R.string.cannot_able_to_read_the_code, Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Log.d("TAg", "rawValue: " + rawValue);
                        Log.d("TAg", "scannedNumbersList: " + scannedNumbersList);
                        scannedNumbersList.add(rawValue);
                    }
                    Log.d("TAg", "barcode.rawValue: " + barcode.getRawValue());
                    int valueType = barcode.getValueType();
                    switch (valueType) {
                        case Barcode.TYPE_WIFI: {

                        }
                        case Barcode.TYPE_URL: {

                        }
                    }
                }
            }
        });

        barcodeScanner.process(inputImage).addOnFailureListener(it -> {
            Log.d("TAg", "barcode error message: " + it.getMessage());
        });

        barcodeScanner.process(inputImage).addOnCompleteListener(it -> {
            // When the image is from CameraX analysis use case, must call image.close() on received
            // images when finished using them. Otherwise, new images may not be received or the camera
            // may stall.
            imageProxy.close();

        });
    }

    private void startScanning() {
        if (Camera.getNumberOfCameras() <= 0) {
            //Toast.makeText(master, "Camera not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mCodeScanner = new CodeScanner(master, scannerView);
            mCodeScanner.setDecodeCallback(result -> master.runOnUiThread(() -> onScanned(result)));
            scannerView.setOnClickListener(view1 -> {
                if (mCodeScanner != null) mCodeScanner.startPreview();
                etTicketNumber.setText("");
            });
        } catch (Exception e) {
            Toast.makeText(master, R.string.unable_to_open_scanner, Toast.LENGTH_SHORT).show();
        }
    }

    private void onScanned(Result result) {
        etTicketNumber.setText(result.getText());
        Utils.vibrate(master);
        buttonProceed.performClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION GRANTED
                    AudioManager audioManager = (AudioManager) master.getSystemService(Context.AUDIO_SERVICE);
                    if (!audioManager.isWiredHeadsetOn()) {
                        setupCamera();
                        //startScanning();
                    }

                } else {
                    // PERMISSION DENIED
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), etTicketNumber);
                        }
                    }
                }
                break;
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
                    if (mCodeScanner != null) {
                        mCodeScanner.stopPreview();
                    }
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
        if (mCodeScanner != null) mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        if (mCodeScanner != null) mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        if (handler != null) handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isBalanceUpdate")) {
            //Objects.requireNonNull(getActivity()).onBackPressed();
            winningClaimViewModel.getUpdatedBalanceTerminal(PlayerData.getInstance().getToken());
        } else {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(getActivity(), getString(R.string.winning), errorMsg, errorDialogListener);
        }

    }


    @Override
    public void onDialogClosed() {

    }
}

