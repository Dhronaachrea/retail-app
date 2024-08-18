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
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.SimpleRmsResponseBean;
import com.skilrock.retailapp.models.rms.DeviceRegistrationBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.DeviceRegistrationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DeviceRegistrationNewFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private DeviceRegistrationViewModel viewModel;
    private EditText etSerialNumber;
    private Spinner spinnerDeviceType;
    private TextInputLayout tilSerialNumber;
    private RelativeLayout spinnerContainer;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private Button buttonProceed;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private HashMap<String, String> mapDevice = new HashMap<>();
    private final String RMS = "rms";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_registration_new, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            viewModel = ViewModelProviders.of(this).get(DeviceRegistrationViewModel.class);
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
        etSerialNumber          = view.findViewById(R.id.et_serial_number);
        spinnerDeviceType       = view.findViewById(R.id.spinnerDeviceType);
        spinnerContainer        = view.findViewById(R.id.spinnerContainer);
        tilSerialNumber         = view.findViewById(R.id.til_serial_number);
        buttonProceed           = view.findViewById(R.id.button_proceed);
        scannerView             = view.findViewById(R.id.scanner_view);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        refreshBalance();

        buttonProceed.setOnClickListener(this);
        spinnerDeviceType.setOnItemSelectedListener(this);
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
            parseApiDetails(Objects.requireNonNull(menuBean).getApiDetails());
        }

        etSerialNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                buttonProceed.performClick();
                return true;
            }
            return false;
        });

        etSerialNumber.setOnEditorActionListener(this::onEditorAction);
    }

    @Override
    public void onClick(View v) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        if (validate()) {
            String url = menuBean.getBasePath() + menuBean.getRelativePath();
            DeviceRegistrationBean deviceDetails = getDeviceDetails(mapDevice.get(spinnerDeviceType.getSelectedItem().toString().trim()));
            if (deviceDetails != null) {
                ProgressBarDialog.getProgressDialog().showProgress(master);
                viewModel.callRegisterDeviceApi(url, deviceDetails);
            }
            else Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void parseApiDetails(String apiDetails) {
        try {
            JSONArray jArray = new JSONObject(apiDetails).getJSONArray("deviceType");
            mapDevice.clear();
            List<String> listSpinner = new ArrayList<>();
            for (int index=0; index<jArray.length(); index++) {
                JSONObject obj = jArray.getJSONObject(index);
                mapDevice.put(obj.getString("deviceName"), obj.getString("deviceId"));
                listSpinner.add(obj.getString("deviceName"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, listSpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDeviceType.setAdapter(adapter);
        } catch (JSONException e) {
            Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    Observer<SimpleRmsResponseBean> observer = new Observer<SimpleRmsResponseBean>() {
        @Override
        public void onChanged(@Nullable SimpleRmsResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.device_registration), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.device_registration), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;

                String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.device_registration), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                String msg = getString(R.string.device_registered_successfully);
                Utils.showCustomSuccessDialog(getContext(), getFragmentManager(), "", msg, 1);
            }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.device_registration), getString(R.string.something_went_wrong));
        }
    };

    private void startScanning() {
        try {
            if (Camera.getNumberOfCameras() <= 0) {
                Toast.makeText(master, "Camera not found", Toast.LENGTH_SHORT).show();
                return;
            }

            mCodeScanner = new CodeScanner(master, scannerView);
            mCodeScanner.setDecodeCallback(result -> master.runOnUiThread(() -> etSerialNumber.setText(result.getText())));
            scannerView.setOnClickListener(view1 -> mCodeScanner.startPreview());
        } catch (Exception e) {
            Toast.makeText(master, "Unable to open scanner", Toast.LENGTH_SHORT).show();
        }
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
                            AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), etSerialNumber);
                        }
                    }
                }
                break;
        }
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (spinnerDeviceType.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.select_device_type))) {
            Toast.makeText(master, getString(R.string.invalid_device_type), Toast.LENGTH_LONG).show();
            spinnerContainer.startAnimation(Utils.shakeError());
            return false;
        }
        if (TextUtils.isEmpty(etSerialNumber.getText().toString().trim())) {
            etSerialNumber.setError(getString(R.string.this_field_cannot_be_empty));
            etSerialNumber.requestFocus();
            tilSerialNumber.startAnimation(Utils.shakeError());
            return false;
        }
        if (getContext() != null) {
            if (!Utils.isNetworkConnected(getContext())) {
                Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private DeviceRegistrationBean getDeviceDetails (String deviceId) {
        DeviceRegistrationBean model = new DeviceRegistrationBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails()).getJSONObject("details");
            JSONObject deviceObject = jsonObject.getJSONObject(deviceId);
            model.setBrandId(deviceObject.optInt("brandId"));
            model.setInventoryId(deviceObject.optInt("inventoryId"));
            model.setModelId(deviceObject.optInt("modelId"));
            String serviceCodes = deviceObject.optString("serviceCode");
            model.setServiceCode(new ArrayList<>((Arrays.asList(serviceCodes.split(",")))));
            model.setSerialNumber(etSerialNumber.getText().toString().trim());
            return model;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.w("log", "Selected Item: " + parent.getItemAtPosition(position).toString());
        try {
            etSerialNumber.setEnabled(true);
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails()).getJSONObject("details");
            JSONObject deviceObject = jsonObject.getJSONObject(mapDevice.get(parent.getItemAtPosition(position).toString().trim()));
            if (deviceObject.optInt("serialNoLength") != 0) {
                InputFilter[] filterArray = new InputFilter[1];
                filterArray[0] = new InputFilter.LengthFilter(deviceObject.optInt("serialNoLength"));
                etSerialNumber.setFilters(filterArray);
            }
            else {
                InputFilter[] filterArray = new InputFilter[1];
                filterArray[0] = new InputFilter.LengthFilter(100);
                etSerialNumber.setFilters(filterArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            buttonProceed.performClick();
            return true;
        }
        return false;
    }
}
