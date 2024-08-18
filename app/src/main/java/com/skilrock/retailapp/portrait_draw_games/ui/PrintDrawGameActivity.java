package com.skilrock.retailapp.portrait_draw_games.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.CancelPrintError;
import com.skilrock.retailapp.interfaces.NoPaper;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.ResultResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelResponseBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayResponseBean;
import com.skilrock.retailapp.models.scratch.ClaimTicketResponseNewBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.util.Objects;

public class PrintDrawGameActivity extends BaseActivity implements NoPaper, CancelPrintError, AppConstants {

    private FiveByNinetySaleResponseBean PRINT_SALE_BEAN;
    private ResultResponseBean PRINT_RESULT_BEAN;
    private TicketCancelResponseBean PRINT_CANCEL_BEAN;
    private WinningClaimPayResponseBean PRINT_WINNING_BEAN;
    private ClaimTicketResponseNewBean PRINT_WINNING_SCRATCH;
    private UsbThermalPrinter usbThermalPrinter = null;
    private String LAST_TICKET_NUMBER_WINNING = null;
    public static final String REPRINT = "REPRINT";
    public static final String SALE = "SALE";
    public static final String CANCEL = "CANCEL";
    public static final String WINNING = "WINNING";
    public static final String RESULT = "RESULT";
    public static final String WINNING_SCRATCH = "SCRATCH";
    private String PRINT_CATEGORY;
    private byte data[];
    private boolean isReprint = false;
    private int item = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_draw_game);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        setToolBar();
        initializeWidgets();
        startTimer();
        Log.d("TAg", "-----------<><><><");
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBalance();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            PRINT_CATEGORY = bundle.getString("Category");
            if (PRINT_CATEGORY != null) {
                if (PRINT_CATEGORY.equalsIgnoreCase(SALE)) {
                    PRINT_SALE_BEAN = bundle.getParcelable("PrintData");
                } else if (PRINT_CATEGORY.equalsIgnoreCase(RESULT)) {
                    PRINT_RESULT_BEAN = bundle.getParcelable("PrintDataResult");
                    item = bundle.getInt("item");
                } else if (PRINT_CATEGORY.equalsIgnoreCase(REPRINT)) {
                    isReprint = true;
                    PRINT_SALE_BEAN = bundle.getParcelable("PrintData");
                } else if (PRINT_CATEGORY.equalsIgnoreCase(CANCEL)) {
                    PRINT_CANCEL_BEAN = bundle.getParcelable("PrintDataCancel");
                } else if (PRINT_CATEGORY.equalsIgnoreCase(WINNING)) {
                    PRINT_WINNING_BEAN = bundle.getParcelable("PrintDataWinning");
                    LAST_TICKET_NUMBER_WINNING = bundle.getString("WinningLastTicket");
                } else if (PRINT_CATEGORY.equalsIgnoreCase(WINNING_SCRATCH)) {
                    PRINT_WINNING_SCRATCH = bundle.getParcelable("PrintDataScratch");
                } else {
                    Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    PrintDrawGameActivity.this.finish();
                }
            } else {
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                PrintDrawGameActivity.this.finish();
            }
        }
    }

    private void setToolBar() {
        ImageView ivGameIcon = findViewById(R.id.ivGameIcon);
        ImageView ivDrawer = findViewById(R.id.iv_drawer);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvUserBalance = findViewById(R.id.tvBal);
        tvUsername = findViewById(R.id.tvUserName);
        llBalance = findViewById(R.id.llBalance);

        tvTitle.setText(getString(R.string.print));
        ivGameIcon.setVisibility(View.GONE);
        ivDrawer.setVisibility(View.GONE);
    }

    private void initializeWidgets() {
        usbThermalPrinter = getUsbThermalPrinter();
        if (PRINT_CATEGORY.equalsIgnoreCase(SALE) || (PRINT_CATEGORY.equalsIgnoreCase(REPRINT))) {
            printSaleAndReprint(PRINT_SALE_BEAN, "");
        } else if (PRINT_CATEGORY.equalsIgnoreCase(RESULT)) {
            printResult(PRINT_RESULT_BEAN);
        } else if (PRINT_CATEGORY.equalsIgnoreCase(CANCEL)) {
            printCancel(PRINT_CANCEL_BEAN);
        } else if (PRINT_CATEGORY.equalsIgnoreCase(WINNING)) {
            printWinning(PRINT_WINNING_BEAN,LAST_TICKET_NUMBER_WINNING);
        } else if (PRINT_CATEGORY.equalsIgnoreCase(WINNING_SCRATCH)) {
            printWinningScratch(PRINT_WINNING_SCRATCH);
        }
    }

    private void startTimer() {
        new CountDownTimer(40000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                PrintDrawGameActivity.this.finish();
            }
        }.start();
    }

    private void printSaleAndReprint(FiveByNinetySaleResponseBean bean, String responseTime) {
        Log.d("TAg", "printSaleAndReprint");
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2s)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2_s)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2PRO)) {

            AidlUtil.getInstance().connectPrinterService(this);
            if (isReprint) {
                if (bean != null) {
                    data = PrintUtil.getPrintDataDgeReprint(bean, "", this, this, isReprint);
                    sendDataPrintSale(data);
                    noPaper("SUCCESS");
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            } else {
                if (bean != null) {
                    data = PrintUtil.getPrintDataDgeSale(bean, "", this, this);
                    sendDataPrintSale(data);
                    noPaper("SUCCESS");
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            }

        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            usbThermalPrinter = getUsbThermalPrinter();
            PrintUtil.getPrintDataTelpo570(bean, this, usbThermalPrinter, isReprint, this);
        } else {
            Log.d("TAg", "On Mobile");
        }
    }

    private void printResult(ResultResponseBean bean) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2s)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2_s)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2)) {
            if (bean != null) {
                data = PrintUtil.getPrintDgeResultNew(bean, this, this,item);
                sendDataPrintSale(data);
            } else {
                Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
            }
            noPaper("SUCCESS");
        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            usbThermalPrinter = getUsbThermalPrinter();
            PrintUtil.getPrintResultNew(bean, this, usbThermalPrinter, this, item);

        }
    }

    private void printCancel(TicketCancelResponseBean bean) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2)) {
            if (bean != null) {
                data = PrintUtil.getPrintDgeCancel(bean, this, this);
                sendDataPrint(data);
            } else {
                Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
            }
            cancelError("SUCCESS", true);
        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            usbThermalPrinter = getUsbThermalPrinter();
            PrintUtil.getPrintDataTelpoCancelTicket(bean, this, usbThermalPrinter, this);
        }
    }

    private void printWinning(WinningClaimPayResponseBean bean,String LAST_TICKET_NUMBER_WINNING) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2)) {
            if (bean != null) {
                data = PrintUtil.getPrintDgeWinSunmi(bean, this, this,LAST_TICKET_NUMBER_WINNING);
                sendDataPrint(data);
                cancelError("SUCCESS", true);
            } else {
                Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
            }
        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            usbThermalPrinter = getUsbThermalPrinter();
            PrintUtil.printWinningTelpo570(this, bean, usbThermalPrinter, this,LAST_TICKET_NUMBER_WINNING);
        }
    }

    private void printWinningScratch(ClaimTicketResponseNewBean bean) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            if (bean != null) {
                data = PrintUtil.printWinningSractchSunmi(this, bean);
                sendDataPrint(data);
                cancelError("SUCCESS", true);
            } else {
                Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
            }
        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            usbThermalPrinter = getUsbThermalPrinter();
            PrintUtil.printWinningSractchTelpo570(this, bean, usbThermalPrinter, this);
        }
    }

    public UsbThermalPrinter getUsbThermalPrinter() {
        if (usbThermalPrinter == null) {
            usbThermalPrinter = new UsbThermalPrinter(PrintDrawGameActivity.this);
        }
        return usbThermalPrinter;
    }

    @Override
    public void noPaper(String msg) {
        if (msg.equalsIgnoreCase("SUCCESS")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isPrintSuccess", true);
            setResult(Activity.RESULT_OK, returnIntent);
            PrintDrawGameActivity.this.finish();
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isPrintSuccess", false);
            returnIntent.putExtra("isBalanceUpdate", false);
            setResult(Activity.RESULT_OK, returnIntent);
            PrintDrawGameActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void sendDataPrintSale(final byte[] send) {
        AidlUtil.getInstance().sendRawData(send);
    }

    private void sendDataPrint(final byte[] send) {
        AidlUtil.getInstance().sendRawData(send);
    }

    @Override
    public void cancelError(String msg, boolean isUpdate) {
        if (msg.equalsIgnoreCase("SUCCESS")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isBalanceUpdate", true);
            setResult(Activity.RESULT_OK, returnIntent);
            PrintDrawGameActivity.this.finish();
        } else {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            returnIntent.putExtra("isBalanceUpdate", false);
            PrintDrawGameActivity.this.finish();
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }

}