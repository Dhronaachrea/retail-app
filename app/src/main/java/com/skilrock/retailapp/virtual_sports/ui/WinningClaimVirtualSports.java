package com.skilrock.retailapp.virtual_sports.ui;

import android.Manifest;
import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.zxing.Result;
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
import com.skilrock.retailapp.sbs.view_model.SbsWinningViewModel;
import com.skilrock.retailapp.utils.CustomSuccessDialog;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.TranslateSbs;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

public class WinningClaimVirtualSports extends BaseActivity implements View.OnClickListener, WinningClaimListener {

    private final int REQUEST_CODE_WINNING = 2222;
    private final int REQUEST_CODE_REPRINT = 3333;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private Button buttonProceed;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private TextInputLayout tilTicketNumber;
    SbsWinningViewModel viewModel;
    String ticket_no;
    EditText ed_first_no, ed_second_no, ed_third_no, ed_fourth_no;
    boolean request = true;
    private CodeScannerView scannerView;
    private CodeScanner mCodeScanner;
    private FrameLayout contentFrame;

    public String res = "{\n" +
            "  \"responseHttpStatusCode\": 200,\n" +
            "  \"responseCode\": 0,\n" +
            "  \"responseMessage\": \"SUCCESS\",\n" +
            "  \"responseData\": {\n" +
            "    \"message\": \"SUCCESS\",\n" +
            "    \"statusCode\": 0,\n" +
            "    \"data\": {\n" +
            "      \"ticketNo\": \"9611114703690230\",\n" +
            "      \"ticketStatus\": \"READY_FOR_PAYOUT\",\n" +
            "      \"dateTime\": \"2020-05-26 13:22:05\",\n" +
            "      \"ticket\": {\n" +
            "        \"id\": 163,\n" +
            "        \"year\": 2020,\n" +
            "        \"month\": 4,\n" +
            "        \"day\": 26,\n" +
            "        \"ticketDate\": \"2020-05-26T00:00:00.000+0000\",\n" +
            "        \"retailerUserId\": 248,\n" +
            "        \"deviceId\": \"TN04196D40062\",\n" +
            "        \"randomNo\": 961,\n" +
            "        \"rmsServiceId\": 1,\n" +
            "        \"rmsGameId\": 1,\n" +
            "        \"dayOfYear\": 147,\n" +
            "        \"retailerCode\": 369,\n" +
            "        \"txnOfDay\": 23,\n" +
            "        \"ticketNo\": \"9611114703690230\",\n" +
            "        \"ticketType\": \"SINGLE\",\n" +
            "        \"txnCount\": 1,\n" +
            "        \"saleAmount\": 10.0,\n" +
            "        \"payoutAmount\": 35.5,\n" +
            "        \"payoutRetailerUserId\": null,\n" +
            "        \"paidAt\": null,\n" +
            "        \"status\": \"READY_FOR_PAYOUT\",\n" +
            "        \"reprintCount\": 0,\n" +
            "        \"lastTicketNo\": null,\n" +
            "        \"lastReprintAt\": null,\n" +
            "        \"createdAt\": \"2020-05-26T13:22:05.000+0000\",\n" +
            "        \"updatedAt\": \"2020-05-26T13:39:17.000+0000\"\n" +
            "      },\n" +
            "      \"transactions\": [\n" +
            "        {\n" +
            "          \"transactionId\": 549,\n" +
            "          \"ticketType\": \"SINGLE\",\n" +
            "          \"transactionDate\": \"2020-05-26 13:22:05.0\",\n" +
            "          \"stake\": 10.0,\n" +
            "          \"status\": \"ACCEPTED\",\n" +
            "          \"winningStatus\": \"FULL_WIN\",\n" +
            "          \"payout\": 35.5,\n" +
            "          \"payoutStatus\": \"DUE\",\n" +
            "          \"transactionIdAndTicketType\": \"549/SINGLE\",\n" +
            "          \"selectedSystems\": \"1\",\n" +
            "          \"isCancelable\": false,\n" +
            "          \"authoriserRemarks\": \"Success\",\n" +
            "          \"mtsTicketId\": \"SR-1590499325645-5450\",\n" +
            "          \"betResponseDetailBeans\": [\n" +
            "            {\n" +
            "              \"sport\": \"Tennis\",\n" +
            "              \"eventName\": \"Firsin, Grigorii vs. Budov, Daniil\",\n" +
            "              \"homeTeamName\": \"Firsin, Grigorii\",\n" +
            "              \"awayTeamName\": \"Budov, Daniil\",\n" +
            "              \"eventDate\": \"26 May 2020, 19:00\",\n" +
            "              \"eventDateActual\": \"2020-05-26T19:00:00.000+0000\",\n" +
            "              \"marketName\": \"Winner\",\n" +
            "              \"outcomeName\": \"Firsin, Grigorii\",\n" +
            "              \"oddValue\": 3.55,\n" +
            "              \"oddId\": 209505,\n" +
            "              \"betSelectionDetail\": \"Tennis > Firsin, Grigorii vs. Budov, Daniil > Winner > Firsin, Grigorii @3.55  (NotStarted)\",\n" +
            "              \"eventResult\": \"NotStarted\",\n" +
            "              \"sportName\": \"Tennis\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"winnableAmount\": 35.5\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";

    String res2 = "{\n" +
            "  \"responseCode\": 0,\n" +
            "  \"responseData\": {\n" +
            "    \"data\": {\n" +
            "      \"dateTime\": \"2020-05-26 13:22:05\",\n" +
            "      \"ticket\": {\n" +
            "        \"day\": 26,\n" +
            "        \"dayOfYear\": 147,\n" +
            "        \"deviceId\": \"TN04196D40062\",\n" +
            "        \"id\": 163,\n" +
            "        \"month\": 4,\n" +
            "        \"payoutAmount\": 35.5,\n" +
            "        \"randomNo\": 961,\n" +
            "        \"reprintCount\": 0,\n" +
            "        \"retailerCode\": 369,\n" +
            "        \"retailerUserId\": 248,\n" +
            "        \"rmsGameId\": 1,\n" +
            "        \"rmsServiceId\": 1,\n" +
            "        \"saleAmount\": 10.0,\n" +
            "        \"status\": \"READY_FOR_PAYOUT\",\n" +
            "        \"ticketNo\": \"9611114703690230\",\n" +
            "        \"ticketType\": \"SINGLE\",\n" +
            "        \"txnCount\": 1,\n" +
            "        \"txnOfDay\": 23,\n" +
            "        \"updatedAt\": \"2020-05-26T13:39:17.000+0000\",\n" +
            "        \"year\": 2020\n" +
            "      },\n" +
            "      \"ticketNo\": \"9611114703690230\",\n" +
            "      \"transactions\": [\n" +
            "        {\n" +
            "          \"mtsTicketId\": \"SR-1590499325645-5450\",\n" +
            "          \"payout\": 35.5,\n" +
            "          \"payoutStatus\": \"DUE\",\n" +
            "          \"selectedSystems\": \"1\",\n" +
            "          \"stake\": 10.0,\n" +
            "          \"status\": \"ACCEPTED\",\n" +
            "          \"ticketType\": \"SINGLE\",\n" +
            "          \"transactionDate\": \"2020-05-26 13:22:05.0\",\n" +
            "          \"transactionId\": 549,\n" +
            "          \"winnableAmount\": 35.5,\n" +
            "          \"winningStatus\": \"FULL_WIN\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"message\": \"SUCCESS\",\n" +
            "    \"statusCode\": 0\n" +
            "  },\n" +
            "  \"responseHttpStatusCode\": 200,\n" +
            "  \"responseMessage\": \"SUCCESS\"\n" +
            "}";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_claim_virtual_sports_potrait);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        setToolBar();
        initializeWidgets();
    }

    @Override
    public void onPause() {
        if (mCodeScanner != null) mCodeScanner.releaseResources();
        super.onPause();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MENU_BEAN = bundle.getParcelable("MenuBean");
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            WinningClaimVirtualSports.this.finish();
        }
    }

    private void setToolBar() {
        ImageView ivGameIcon = findViewById(R.id.ivGameIcon);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView win_txt = findViewById(R.id.win_txt);
        ImageView sbs_menu_img = findViewById(R.id.sbs_menu_reprint);

        sbs_menu_img.setImageResource(R.drawable.ic_reprint_sbs);
        tvUserBalance = findViewById(R.id.tvBal);
        tvUsername = findViewById(R.id.tvUserName);
        llBalance = findViewById(R.id.llBalance);
        tvTitle.setText(getString(R.string.winning_n_claim_sbs));
        win_txt.setText(getString(R.string.reprint).toUpperCase());
        win_txt.setVisibility(View.GONE);
        ivGameIcon.setVisibility(View.GONE);
        LinearLayout sbs_menu = findViewById(R.id.sbs_reprint);
        sbs_menu.setVisibility(View.VISIBLE);
        refreshBalance();
    }

    private void initializeWidgets() {
        viewModel = ViewModelProviders.of(this).get(SbsWinningViewModel.class);
        viewModel.getliveDataWinSbsVerify().observe(this, observerWinSbs);
        viewModel.getliveDataWinSbsPay().observe(this, observerWinSbsPay);
        viewModel.getLiveDataReprint().observe(this, observerReprint);
        buttonProceed = findViewById(R.id.button_proceed);
        contentFrame = findViewById(R.id.content_frame);
        tilTicketNumber = findViewById(R.id.textInputLayoutTicketNumber);
        ed_first_no = findViewById(R.id.ed_first_no);
        ed_second_no = findViewById(R.id.ed_second_no);
        ed_third_no = findViewById(R.id.ed_third_no);
        ed_fourth_no = findViewById(R.id.ed_fourth_no);
        scannerView = findViewById(R.id.scanner_view);

        buttonProceed.setOnClickListener(WinningClaimVirtualSports.this);

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
                        Utils.showRedToast(WinningClaimVirtualSports.this, getString(R.string.enter_valid_ticket_number));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        scannerView.setOnClickListener(v -> {
            ed_first_no.setText("");
            ed_second_no.setText("");
            ed_third_no.setText("");
            ed_fourth_no.setText("");
            if (mCodeScanner != null)
                mCodeScanner.startPreview();
        });

    }

    private void setFocus(EditText editText) {
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit_dull));
        ed_second_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit_dull));
        ed_third_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit_dull));
        ed_fourth_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit_dull));
        editText.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit));
        editText.requestFocus();
        editText.setSelection(editText.getText().toString().length());
    }

    private void resetEditTexts() {
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit_dull));
        ed_second_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit_dull));
        ed_third_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit_dull));
        ed_fourth_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit_dull));
        ed_first_no.setText("");
        ed_second_no.setText("");
        ed_third_no.setText("");
        ed_fourth_no.setText("");
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimVirtualSports.this, R.drawable.epl_bg_edit));
        ed_first_no.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCodeScanner != null) mCodeScanner.startPreview();
        refreshBalance();
    }

    public void onClickBack(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        WinningClaimVirtualSports.this.finish();
    }

    private void startScanning() {
        if (Camera.getNumberOfCameras() <= 0) {
            //Toast.makeText(WinningClaimActivity.this, "Camera not found", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mCodeScanner = new CodeScanner(WinningClaimVirtualSports.this, scannerView);
            mCodeScanner.setDecodeCallback(result -> WinningClaimVirtualSports.this.runOnUiThread(() -> onScanned(result)));
            scannerView.setOnClickListener(view1 -> {
                if (mCodeScanner != null)
                    mCodeScanner.startPreview();

                ed_first_no.setText("");
                ed_second_no.setText("");
                ed_third_no.setText("");
                ed_fourth_no.setText("");
            });
        } catch (Exception e) {
            Toast.makeText(WinningClaimVirtualSports.this, "Unable to open scanner", Toast.LENGTH_SHORT).show();
        }
    }

    private void onScanned(Result result) {
        String scannedValue = result.getText().replaceAll("-", "");
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
            Utils.vibrate(WinningClaimVirtualSports.this);
            //buttonProceed.performClick();
        }
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

    private String getTicketNumber() {
        return ed_first_no.getText().toString() + ed_second_no.getText().toString() + ed_third_no.getText().toString() + ed_fourth_no.getText().toString();
    }

    private boolean validate() {
        Utils.vibrate(this);
        if (ed_first_no.getText().toString().length() != 4 || ed_second_no.getText().toString().length() != 4
                || ed_third_no.getText().toString().length() != 4 || ed_fourth_no.getText().toString().length() != 4) {
            Utils.showRedToast(WinningClaimVirtualSports.this, getString(R.string.enter_valid_ticket_number));
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isPrintSuccess")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isPrintSuccess", true);
            setResult(Activity.RESULT_OK, returnIntent);
            WinningClaimVirtualSports.this.finish();
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
                            AppPermissions.showMessageOKCancel(WinningClaimVirtualSports.this, getString(R.string.allow_permission), this, ed_first_no);
                        } else if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(WinningClaimVirtualSports.this, getString(R.string.allow_permission), this, ed_first_no);
                        } else {
                            startScanning();
                        }
                    } else {
                        // PERMISSION DENIED
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                AppPermissions.showMessageOKCancel(WinningClaimVirtualSports.this, getString(R.string.allow_permission), this, ed_first_no);
                            } else if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                AppPermissions.showMessageOKCancel(WinningClaimVirtualSports.this, getString(R.string.allow_permission), this, ed_first_no);
                            }
                        }
                        Utils.showRedToast(WinningClaimVirtualSports.this, "Permission denied to use Storage");
                        WinningClaimVirtualSports.this.finish();
                    }
                    break;

                }
        }
    }

    public void callWiningVerify(String ticket_no) {
        ProgressBarDialog.getProgressDialog().showProgress(WinningClaimVirtualSports.this);
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "getWinningDetails");
        if (urlBean != null) {
            viewModel.callSbsWinVerify(urlBean, Utils.getDeviceSerialNumber(), Long.parseLong(PlayerData.getInstance().getUserId()), PlayerData.getInstance().getToken().split(" ")[1], ticket_no);
        }
    }

    public void callWiningPay(String ticNo) {
        ProgressBarDialog.getProgressDialog().showProgress(WinningClaimVirtualSports.this);
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "claimWinning");
        if (urlBean != null) {
            viewModel.callSbsWinPay(urlBean, Utils.getDeviceSerialNumber(), Long.parseLong(PlayerData.getInstance().getUserId()), PlayerData.getInstance().getToken().split(" ")[1], ticNo);
        }
    }

    private void callReprint() {
        ProgressBarDialog.getProgressDialog().showProgress(WinningClaimVirtualSports.this);
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "reprintLastTicket");
        if (urlBean != null) {
            viewModel.callSbsReprint(urlBean, Utils.getDeviceSerialNumber(), Long.parseLong(PlayerData.getInstance().getUserId()),
                    PlayerData.getInstance().getToken().split(" ")[1],
                    "0");
        }
    }

    Observer<SbsWinVerifyResponse> observerWinSbs = response -> {
        //SbsWinVerifyResponse response = new Gson().fromJson(res,SbsWinVerifyResponse.class);
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
                ErrorDialogListener errorDialogListener = this::resetEditTexts;
                Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), TranslateSbs.getTranslateVal(response.getResponseData().getData().getTicket().getStatus(), WinningClaimVirtualSports.this), errorDialogListener);
            }

        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimVirtualSports.this, response.getResponseCode(), WinningClaimVirtualSports.this))
                return;

            String errorMsg = Utils.getResponseMessage(WinningClaimVirtualSports.this, "sbs", response.getResponseCode());
            ErrorDialogListener errorDialogListener = this::resetEditTexts;
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg, errorDialogListener);
        }
    };

    Observer<SbsWinPayResponse> observerWinSbsPay = response -> {
        //SbsWinPayResponse response = new Gson().fromJson(res,SbsWinPayResponse.class);
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            if (ticket_no != null) {
                Intent intent = new Intent(WinningClaimVirtualSports.this, PrintActivityVirtualSports.class);
                intent.putExtra("PrintDataWinning", response);
                intent.putExtra("Category", PrintActivityVirtualSports.WINNING);
                startActivityForResult(intent, REQUEST_CODE_WINNING);
            } else {
                Toast.makeText(this, getString(R.string.problem_with_ticket_number), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimVirtualSports.this, response.getResponseCode(), WinningClaimVirtualSports.this))
                return;
            ErrorDialogListener errorDialogListener = this::resetEditTexts;
            String errorMsg = Utils.getResponseMessage(WinningClaimVirtualSports.this, "sbs", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg, errorDialogListener);
        }
    };

    Observer<SbsReprintResponseBean> observerReprint = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            Intent intent = new Intent(WinningClaimVirtualSports.this, PrintActivityVirtualSports.class);
            intent.putExtra("ReprintData", response);
            intent.putExtra("Category", PrintActivityVirtualSports.REPRINT);
            startActivityForResult(intent, REQUEST_CODE_REPRINT);


        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimVirtualSports.this, response.getResponseCode(), WinningClaimVirtualSports.this))
                return;

            String errorMsg = Utils.getResponseMessage(WinningClaimVirtualSports.this, "sbs", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), errorMsg);
        }
    };

    @Override
    public void dialogButtonPress(boolean isVerify) {
        if (isVerify) {
            if (ticket_no != null)
                callWiningPay(ticket_no);
            else
                Toast.makeText(this, getString(R.string.problem_with_ticket_number), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSbsMenuReprint(View view) {

        ConfirmationListener confirmationListener = () -> {
            callReprint();
        };

        Utils.showReprintTicketConfirmationDialog(WinningClaimVirtualSports.this, confirmationListener);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }
}