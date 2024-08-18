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
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseHomeBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.barcode.BarcodeFormat;
import com.skilrock.retailapp.utils.barcode.BarcodeResultParser;
import com.skilrock.retailapp.viewmodels.scratch.CameraXViewModel;
import com.skilrock.retailapp.viewmodels.scratch.PackReturnViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PackReturnFragment extends BaseFragment implements View.OnClickListener, ErrorDialogListener {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private final String SCRATCH = "scratch";
    private PackReturnViewModel packReturnViewModel;
    private EditText etChallanNumber;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private Button buttonProceed;
    private ImageView ivFlash;
    private TextInputLayout tilChallanNumber;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private String dlId;
    FrameLayout contentFrame;
    private String retailer;

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


    ErrorDialogListener errorDialogListener = this::callBack;

    Observer<ReturnChallanResponseHomeBean> observer = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        allowBackAction(true);

        if (mCodeScanner != null)
            mCodeScanner.stopPreview();

        if (response == null)
            Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_return_error), getString(R.string.something_went_wrong), errorDialogListener);
        else if (response.getResponseCode() == 1000) {
            if (response.getGames() == null || response.getGames().isEmpty())
                Utils.showCustomErrorDialog(getContext(), getString(R.string.no_data_found), getString(R.string.something_went_wrong), errorDialogListener);
            else {
                openReturnChallanDetailFragment(response);
            }
        } else {
            if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                return;
            String errorMsg = Utils.getResponseMessage(master, SCRATCH, response.getResponseCode());
            Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_return_error), errorMsg, errorDialogListener);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pack_return, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            packReturnViewModel = ViewModelProviders.of(this).get(PackReturnViewModel.class);
            packReturnViewModel.getReturnListData().observe(this, observer);
        }
    }

    private void callBack() {
        etChallanNumber.setText("");
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (SharedPrefUtil.getLanguage(master).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        initializeWidgets(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etChallanNumber = view.findViewById(R.id.et_ticket_number);
        scannerView = view.findViewById(R.id.scanner_view);
        previewView = view.findViewById(R.id.preview_view);
        ivFlash = view.findViewById(R.id.ivFlash);
        buttonProceed = view.findViewById(R.id.button_proceed);
        tilChallanNumber = view.findViewById(R.id.textInputLayoutChallanNumber);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        contentFrame = view.findViewById(R.id.content_frame);

        refreshBalance();
        buttonProceed.setOnClickListener(this);
        ivFlash.setOnClickListener(this);
        contentFrame.setVisibility(View.VISIBLE);
        /*if (AppPermissions.checkPermission(getActivity())) startScanning();
        else AppPermissions.requestPermission(getFragmentManager());*/

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

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
            retailer = bundle.getString("retailer");
        }

        etChallanNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                buttonProceed.performClick();
                return true;
            }
            return false;
        });

        etChallanNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    etChallanNumber.setText(s);
                    etChallanNumber.setSelection(etChallanNumber.getText().length());
                }
            }
        });

        /*scannerView.setOnClickListener(v -> {
            etChallanNumber.setText("");
            if (mCodeScanner != null) mCodeScanner.startPreview();
        });*/
    }

    public boolean areSame() {
        // Put all array elements in a HashSet
        Set<String> s = new HashSet<>(scannedNumbersList);

        // If all elements are same, size of
        // HashSet should be 1. As HashSet contains only distinct values.
        return (s.size() == 1);
    }

    private void enableDisableFlashLight() {
        if (camm != null) {
            if (camm.getCameraInfo().hasFlashUnit()) {
                if (!isFlashLightOn) {
                    Log.i("TaG","1111111111111111111111");
                    camm.getCameraControl().enableTorch(true);
                    isFlashLightOn = true;
                    ivFlash.setImageResource(R.drawable.baseline_flash_on_24);
                } else {
                    Log.i("TaG","222222222222222222222222");
                    camm.getCameraControl().enableTorch(false);
                    isFlashLightOn = false;
                    ivFlash.setImageResource(R.drawable.baseline_flash_off_24);
                }
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_proceed: {
                if (!Utils.isNetworkConnected(master)) {
                    Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                if (validate() && isClickAllowed()) {
                    UrlBean urlGameList;
                    if (BuildConfig.app_name.equalsIgnoreCase(getString(R.string.app_name_FieldX)))
                        urlGameList = Utils.getUrlDetailsPackReturnFieldx(menuBean, getContext(), "getReturnNote");
                    else {
                        //urlGameList = Utils.getUrlDetailsPackReturn(menuBean, getContext(), "getReturnNote");
                        urlGameList = Utils.getUrlDetails(menuBean, getContext(), "getReturnNote");
                    }

                    if (urlGameList != null) {
                        allowBackAction(false);
                        ProgressBarDialog.getProgressDialog().showProgress(master);
                        // getData();
                        packReturnViewModel.callReturnListApi(urlGameList, PlayerData.getInstance().getUsername(), etChallanNumber.getText().toString().trim(), retailer);
                    }
                }
                break;
            }
            case R.id.ivFlash: {

                Log.i("TaG","333333333333333333333");
                enableDisableFlashLight();

            }
        }

    }

    private void openReturnChallanDetailFragment(ReturnChallanResponseHomeBean returnChallanResponseBean) {
        Bundle bundle = new Bundle();
        bundle.putString("retailer", retailer);
        bundle.putString("title", menuBean.getCaption());
        bundle.putBoolean("FromChallanScreen", true);
        bundle.putParcelable("MenuBean", menuBean);
        bundle.putParcelable("ReturnBook", returnChallanResponseBean);
        bundle.putString("ChallanNumber", etChallanNumber.getText().toString().trim());
        master.openFragment(new PackReturnMultiScanningFragment(), "PackReturnMultiScanningFragment", true, bundle);
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
                            etChallanNumber.setText(rawValue);
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
                etChallanNumber.setText("");
            });
        } catch (Exception e) {
            Toast.makeText(master, R.string.unable_to_open_scanner, Toast.LENGTH_SHORT).show();
        }
    }

    private void onScanned(Result result) {
        etChallanNumber.setText(result.getText());
        Utils.vibrate(master);
        buttonProceed.performClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION GRANTED
                    setupCamera();
                    //startScanning();
                } else {
                    // PERMISSION DENIED
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), etChallanNumber);
                        }
                    }
                }
                break;
        }
    }

    private boolean validate() {
        Utils.vibrate(master);
        if (TextUtils.isEmpty(etChallanNumber.getText().toString().trim())) {
            etChallanNumber.setError(getString(R.string.enter_return_note));
            etChallanNumber.requestFocus();
            tilChallanNumber.startAnimation(Utils.shakeError());
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