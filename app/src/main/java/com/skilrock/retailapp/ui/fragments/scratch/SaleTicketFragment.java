package com.skilrock.retailapp.ui.fragments.scratch;

import android.Manifest;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.SuccessDialog;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.SaleTicketViewModel;

import java.util.Objects;

public class SaleTicketFragment extends BaseFragment implements View.OnClickListener {

    private SaleTicketViewModel saleTicketViewModel;
    private EditText etTicketNumber;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private Button buttonProceed;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private final String SCRATCH = "scratch";
    private TextInputLayout tilTicketNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanning, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            saleTicketViewModel = ViewModelProviders.of(this).get(SaleTicketViewModel.class);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);

        saleTicketViewModel.getGameListData().observe(this, gameListBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (gameListBean == null)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong));
            else if (gameListBean.getResponseCode() == 57575)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket));
            else if (gameListBean.getResponseCode() == 75757)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.no_games_found));
            else if (gameListBean.getResponseCode() == 74747)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket_length));
            else {
                //String errorMsg = TextUtils.isEmpty(gameListBean.getResponseMessage()) ? getString(R.string.error_fetching_gamelist) : gameListBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, gameListBean.getResponseCode());
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, errorMsg);
            }
        });

        saleTicketViewModel.getResponseData().observe(this, responseCodeMessageBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            if (responseCodeMessageBean == null)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong));
            else if (responseCodeMessageBean.getResponseCode() == 1000) {
                String successMsg = getString(R.string.ticket_is_marked_as_sold);
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
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, errorMsg);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etTicketNumber          = view.findViewById(R.id.et_ticket_number);
        scannerView             = view.findViewById(R.id.scanner_view);
        buttonProceed           = view.findViewById(R.id.button_proceed);
        tilTicketNumber         = view.findViewById(R.id.textInputLayoutTicketNumber);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);

        refreshBalance();
        buttonProceed.setOnClickListener(this);
        /*if (AppPermissions.checkPermission(getActivity())) startScanning();
        else AppPermissions.requestPermission(getFragmentManager());*/
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
    }

    @Override
    public void onClick(View v) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        if (validate() && isClickAllowed()) {
            UrlBean urlGameList = Utils.getUrlDetails(menuBean, getContext(), "gameList");
            UrlBean urlSale     = Utils.getUrlDetails(menuBean, getContext(), "sale");

            if (urlSale != null && urlGameList != null) {
                ProgressBarDialog.getProgressDialog().showProgress(master);
                if (etTicketNumber.getText().toString().trim().contains("-"))
                    saleTicketViewModel.callSaleTicket(urlSale, etTicketNumber.getText().toString());
                else
                    saleTicketViewModel.callGameListApi(urlGameList, urlSale, etTicketNumber.getText().toString().trim());
            }
        }
    }

    private void startScanning() {
        if (Camera.getNumberOfCameras() <= 0) {
            //Toast.makeText(master, "Camera not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mCodeScanner = new CodeScanner(master, scannerView);
            mCodeScanner.setDecodeCallback(result -> master.runOnUiThread(() -> etTicketNumber.setText(result.getText())));
            scannerView.setOnClickListener(view1 -> mCodeScanner.startPreview());
        }catch (Exception e){
            Toast.makeText(master, "Unable to open scanner", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION GRANTED
                    startScanning();
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
        if (TextUtils.isEmpty(etTicketNumber.getText().toString().trim())) {
            etTicketNumber.setError(getString(R.string.enter_ticket_number));
            etTicketNumber.requestFocus();
            tilTicketNumber.startAnimation(Utils.shakeError());
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

}