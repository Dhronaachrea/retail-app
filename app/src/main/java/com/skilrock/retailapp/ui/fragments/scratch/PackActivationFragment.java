package com.skilrock.retailapp.ui.fragments.scratch;

import android.Manifest;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.SuccessDialog;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.barcode.BarcodeFormat;
import com.skilrock.retailapp.utils.barcode.BarcodeResultParser;
import com.skilrock.retailapp.viewmodels.scratch.ActiveBookViewModel;
import com.skilrock.retailapp.viewmodels.scratch.CameraXViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PackActivationFragment extends BaseFragment implements View.OnClickListener, ErrorDialogListener {

    private ActiveBookViewModel activeBookViewModel;
    private TextInputEditText etBookNumber;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private ImageView ivFlash;
    private Button buttonProceed;
    private TextInputLayout tilPackNumber;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private final String SCRATCH = "scratch";
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

    ErrorDialogListener errorDialogListener = this::callBack;
    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI) ? R.layout.fragment_activate_book_landscape : R.layout.fragment_activate_book, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            activeBookViewModel = ViewModelProviders.of(this).get(ActiveBookViewModel.class);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (SharedPrefUtil.getLanguage(master).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        initializeWidgets(view);
        activeBookViewModel.getGameListData().observe(this, gameListBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            allowBackAction(true);

            if (mCodeScanner != null)
                mCodeScanner.stopPreview();

            if (gameListBean == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_activation), getString(R.string.something_went_wrong), errorDialogListener);
            else if (gameListBean.getResponseCode() == 57575)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_activation), getString(R.string.invalid_book), errorDialogListener);
            else if (gameListBean.getResponseCode() == 75757)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_activation), getString(R.string.no_games_found), errorDialogListener);
            else if (gameListBean.getResponseCode() == 74747)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_activation), getString(R.string.invalid_book_length), errorDialogListener);
            else {
                //String errorMsg = TextUtils.isEmpty(gameListBean.getResponseMessage()) ? getString(R.string.error_fetching_gamelist) : gameListBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, gameListBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_activation), errorMsg, errorDialogListener);

                if(mCodeScanner != null) mCodeScanner.startPreview();
                etBookNumber.setText("");
            }
        });

        activeBookViewModel.getResponseData().observe(this, responseCodeMessageBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            allowBackAction(true);

            if (mCodeScanner != null)
                mCodeScanner.stopPreview();

            if (responseCodeMessageBean == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_activation), getString(R.string.something_went_wrong), errorDialogListener);
            else if (responseCodeMessageBean.getResponseCode() == 1000) {
                String successMsg = getString(R.string.pack_activated_successfully);
                //String successMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.successfully_ticket_sold) : responseCodeMessageBean.getResponseMessage();
                //Utils.showSuccessDialog(getContext(), getString(R.string.app_name), successMsg, getFragmentManager());
                SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), "", successMsg, 1);
                dialog.show();
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            }
            else {
                if (Utils.checkForSessionExpiry(master, responseCodeMessageBean.getResponseCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.some_internal_error) : responseCodeMessageBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, responseCodeMessageBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_activation), errorMsg, errorDialogListener);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etBookNumber            = view.findViewById(R.id.et_ticket_number);
        scannerView             = view.findViewById(R.id.scanner_view);
        previewView             = view.findViewById(R.id.preview_view);
        ivFlash                 = view.findViewById(R.id.ivFlash);
        buttonProceed           = view.findViewById(R.id.button_proceed);
        tilPackNumber           = view.findViewById(R.id.textInputLayoutPackNumber);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        contentFrame            = view.findViewById(R.id.content_frame);

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

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        etBookNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                buttonProceed.performClick();
                return true;
            }
            return false;
        });

        /*scannerView.setOnClickListener(v -> {etBookNumber.setText(""); if(mCodeScanner != null) mCodeScanner.startPreview();});*/

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


    private void callBack() {
        etBookNumber.setText("");
        final Handler handler1 = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second

        handler1.postDelayed(new Runnable() {
            public void run() {
                if (isAdded() && isVisible()){
                    isFlashLightOn = true;
                    enableDisableFlashLight();
                    setupCamera();
                    //handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_proceed: {
                if (!Utils.isNetworkConnected(master)) {
                    Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                if (validate() && isClickAllowed()) {
                    UrlBean activateBookUrl = Utils.getUrlDetails(menuBean, getContext(), "activateBook");
                    UrlBean gameListUrl     = Utils.getUrlDetails(menuBean, getContext(), "gameList");
                    if (gameListUrl != null && activateBookUrl != null) {
                        allowBackAction(false);
                        ProgressBarDialog.getProgressDialog().showProgress(master);

                        if (etBookNumber.getText() != null && etBookNumber.getText().toString().trim().contains("-")) {
                            activeBookViewModel.callBookActivation(activateBookUrl, etBookNumber.getText().toString());
                        } else {
                            activeBookViewModel.callGameListApi(gameListUrl, activateBookUrl, etBookNumber.getText().toString().trim());
                        }
                    }
                }
                break;
            }
            case R.id.ivFlash: {
                enableDisableFlashLight();
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
                ActivityCompat.requestPermissions(master, new String[] { Manifest.permission.CAMERA } , PERMISSION_CAMERA_REQUEST);
            }
        });
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
                    rawValue = result.getFields().get(0).getRawData().trim();;


                    if (scannedNumbersList.size() >= 2) {
                        if (areSame()) {
                            Log.d("TAg", "areSame: " + areSame());
                            etBookNumber.setText(rawValue);
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

        barcodeScanner.process(inputImage).addOnCompleteListener ( it -> {
            // When the image is from CameraX analysis use case, must call image.close() on received
            // images when finished using them. Otherwise, new images may not be received or the camera
            // may stall.
            imageProxy.close();

        });
    }

    public boolean areSame() {
        // Put all array elements in a HashSet
        Set<String> s = new HashSet<>(scannedNumbersList);

        // If all elements are same, size of
        // HashSet should be 1. As HashSet contains only distinct values.
        return (s.size() == 1);
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
                etBookNumber.setText("");
            });
        } catch (Exception e) {
            Toast.makeText(master, R.string.unable_to_open_scanner, Toast.LENGTH_SHORT).show();
        }
    }

    private void onScanned(Result result) {
        mCodeScanner.stopPreview();
        etBookNumber.setText(result.getText());
        Utils.vibrate(master);
        buttonProceed.performClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION GRANTED
                    //startScanning();
                    setupCamera();
                } else {
                    // PERMISSION DENIED
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), etBookNumber);
                        }
                    }
                }
                break;
        }
    }

    private boolean validate() {
        Utils.vibrate(master);
        if (TextUtils.isEmpty(etBookNumber.getText().toString().trim())) {
            etBookNumber.setError(getString(R.string.enter_book_number));
            etBookNumber.requestFocus();
            tilPackNumber.startAnimation(Utils.shakeError());
            return false;
        }
        return true;
    }

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
    public void onDialogClosed() {

    }
}
