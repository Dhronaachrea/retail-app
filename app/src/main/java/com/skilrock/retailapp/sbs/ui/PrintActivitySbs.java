package com.skilrock.retailapp.sbs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.NoPaper;
import com.skilrock.retailapp.models.SbsReprintResponseBean;
import com.skilrock.retailapp.models.SbsWinPayResponse;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.sle_game_portrait.SbsPurchaseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.util.Objects;

public class PrintActivitySbs extends BaseActivity implements NoPaper, AppConstants {
    public static final String WINNING = "WINNING";
    public static final String REPRINT = "REPRINT";
    public static final String SALE    = "SALE";
    private String PRINT_CATEGORY;
    private SbsWinPayResponse PRINT_WINNING_BEAN;
    private SbsReprintResponseBean REPRINT_BEAN;
    private SbsPurchaseBean SBS_SALE_BEAN;
    private byte data[];
    UsbThermalPrinter usbThermalPrinter;
    private boolean fromReprint = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_sbs);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
             setDelay();
        }else{
            setInitialParameters();
            initializeWidgets();
            startTimer();
        }

    }


    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            PRINT_CATEGORY = bundle.getString("Category");
            if (PRINT_CATEGORY != null) {
                if (PRINT_CATEGORY.equalsIgnoreCase(WINNING)) {
                    PRINT_WINNING_BEAN = bundle.getParcelable("PrintDataWinning");
                }else if (PRINT_CATEGORY.equalsIgnoreCase(REPRINT)){
                    REPRINT_BEAN = bundle.getParcelable("ReprintData");
                }else if (PRINT_CATEGORY.equalsIgnoreCase(SALE)){
                    SBS_SALE_BEAN = bundle.getParcelable("PrintData");
                }
            } else {
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                PrintActivitySbs.this.finish();
            }
        }
    }



    private void initializeWidgets() {
        usbThermalPrinter = getUsbThermalPrinter();
        if (PRINT_CATEGORY.equalsIgnoreCase(WINNING)) {
            printWinSbs(PRINT_WINNING_BEAN);
        }
        if (PRINT_CATEGORY.equalsIgnoreCase(REPRINT)) {
            reprintSbs(REPRINT_BEAN);
        }
        if (PRINT_CATEGORY.equalsIgnoreCase(SALE)) {
            printSale(SBS_SALE_BEAN);
        }
    }

    private void printSale(SbsPurchaseBean sbs_sale_bean) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            data = PrintUtil.printSbsTicket(sbs_sale_bean, PrintActivitySbs.this);
            sendDataPrintSale(data, sbs_sale_bean);
            noPaper("SUCCESS");

        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            PrintUtil.printSbsSaleTps(sbs_sale_bean, PrintActivitySbs.this, this, usbThermalPrinter);
        }
    }

    private void startTimer() {
        new CountDownTimer(40000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                PrintActivitySbs.this.finish();
            }
        }.start();
    }

    private void printWinSbs(SbsWinPayResponse print_winning_bean) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            if (print_winning_bean != null) {
                data = PrintUtil.printSbsWin(print_winning_bean, PrintActivitySbs.this);
                sendDataPrintSale(data);
                noPaper("SUCCESS");
            }

        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            PrintUtil.printWinningSbs(print_winning_bean, this, this, usbThermalPrinter);
        }
    }

    private void reprintSbs(SbsReprintResponseBean reprint_bean) {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            if (reprint_bean != null) {
                data = PrintUtil.printSbsReprint(reprint_bean, PrintActivitySbs.this);
                sendDataReprint(data, reprint_bean);
                noPaper("SUCCESS");
                fromReprint = true;
            }

        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            PrintUtil.reprintSbsTelpo(reprint_bean, PrintActivitySbs.this, this, usbThermalPrinter);
            fromReprint = true;
        }
    }


    @Override
    public void noPaper(String msg) {
        if (msg.equalsIgnoreCase("SUCCESS")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isPrintSuccess", true);
            returnIntent.putExtra("isfromReprint", fromReprint);
            setResult(Activity.RESULT_OK, returnIntent);
            PrintActivitySbs.this.finish();
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isPrintSuccess", false);
            setResult(Activity.RESULT_OK, returnIntent);
            PrintActivitySbs.this.finish();
        }
    }

    private void sendDataPrintSale(final byte[] send) {
        AidlUtil.getInstance().sendRawData(send);
    }

    private void sendDataPrintSale(final byte[] send, SbsPurchaseBean sbsPurchaseBean) {
        AidlUtil.getInstance().sendRawData(send);
        saveTicket(sbsPurchaseBean.getTicketNo(), PrintActivitySbs.this);
    }

    private void sendDataReprint(final byte[] send, SbsReprintResponseBean reprint_bean) {
        AidlUtil.getInstance().sendRawData(send);
        saveTicket(reprint_bean.getResponseData().getData().getTicketNo(), PrintActivitySbs.this);
    }

    private void saveTicket(String ticket_number, Context context) {
        SharedPrefUtil.putLastTicketSbs(context, SharedPrefUtil.getString(context, SharedPrefUtil.USERNAME), ticket_number);
    }

    public UsbThermalPrinter getUsbThermalPrinter() {
        if (usbThermalPrinter == null) {
            usbThermalPrinter = new UsbThermalPrinter(PrintActivitySbs.this);
        }
        return usbThermalPrinter;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    private void setDelay() {
        Handler handler = new Handler();
        int delay = 1000;
        handler.postDelayed(() -> {
            setInitialParameters();
            initializeWidgets();
            startTimer();
        }, delay);
    }
}
