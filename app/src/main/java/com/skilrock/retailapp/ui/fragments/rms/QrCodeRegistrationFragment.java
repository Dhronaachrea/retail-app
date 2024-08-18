package com.skilrock.retailapp.ui.fragments.rms;

import android.Manifest;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.DialogCloseListener;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.QrCodeRegistrationRequestBean;
import com.skilrock.retailapp.models.rms.QrCodeRegistrationResponseBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.QrCodeRegistrationViewModel;

import java.util.HashMap;
import java.util.Objects;

public class QrCodeRegistrationFragment extends BaseFragment {

    private QrCodeRegistrationViewModel viewModel;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private HashMap<String, String> mapDevice = new HashMap<>();
    private final String RMS = "rms";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr_code_registration, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            viewModel = ViewModelProviders.of(this).get(QrCodeRegistrationViewModel.class);
            viewModel.getLiveData().observe(this, observer);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        scannerView             = view.findViewById(R.id.scanner_view);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        if (AppPermissions.checkPermission(master)) startScanning();
        else AppPermissions.requestPermission(getFragmentManager());
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        scannerView.setOnClickListener(v -> {if(mCodeScanner != null) mCodeScanner.startPreview();});
    }

    Observer<QrCodeRegistrationResponseBean> observer = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        DialogCloseListener listener = QrCodeRegistrationFragment.this::onDialogClosed;

        if (response == null)
            Utils.showCustomErrorDialog(getContext(), getString(R.string.qr_code_registration), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() != 0) {
            //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
            String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
            Utils.showCustomErrorDialog(getContext(), getString(R.string.qr_code_registration), errorMsg);
        }
        else if(response.getResponseData().getStatusCode() != 0) {
            if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                return;

            //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
            String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
            Utils.showCustomErrorDialog(getContext(), getString(R.string.qr_code_registration), errorMsg);
        }
        else if (response.getResponseData().getStatusCode() == 0) {
            String msg = getString(R.string.qr_code_registered_successfully_logout);
            Utils.showCustomSuccessDialog(getContext(), getFragmentManager(), "", msg, 1, listener);
        }
        else
            Utils.showCustomErrorDialog(getContext(), getString(R.string.qr_code_registration), getString(R.string.something_went_wrong));
    };

    private void onDialogClosed() {
        Utils.performLogoutCleanUp(getContext());
    }

    private void startScanning() {
        if (Camera.getNumberOfCameras() <= 0) {
            Toast.makeText(master, "Camera not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mCodeScanner = new CodeScanner(master, scannerView);
            mCodeScanner.setDecodeCallback(result -> master.runOnUiThread(() -> callRegisterApi(result.getText())));
            scannerView.setOnClickListener(view1 -> {
                if (mCodeScanner != null) mCodeScanner.startPreview();
            });
        } catch (Exception e) {
            Toast.makeText(master, "Unable to open scanner", Toast.LENGTH_SHORT).show();
        }
    }

    private void callRegisterApi(String qrCodeValue) {
        Utils.vibrate(master);
        String url = menuBean.getBasePath() + menuBean.getRelativePath();
        QrCodeRegistrationRequestBean model = new QrCodeRegistrationRequestBean();
        model.setQrCode(qrCodeValue);
        ProgressBarDialog.getProgressDialog().showProgress(master);
        viewModel.callRegisterDeviceApi(url, model);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.d("log", "Request Code: " + requestCode);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION GRANTED
                    startScanning();
                } else {
                    // PERMISSION DENIED
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), null);
                        }
                    }
                }
                break;
        }
    }
    DialogCloseListener listener = new DialogCloseListener() {
        @Override
        public void onDialogClosed() {

        }
    };
}
