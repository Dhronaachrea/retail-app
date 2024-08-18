package com.skilrock.retailapp.sle_game_portrait;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;
import com.skilrock.retailapp.utils.printer.ESCUtil;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.printer.NoPaperException;
import com.telpo.tps550.api.printer.ThermalPrinter;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;
import static com.skilrock.retailapp.utils.PrintUtil.alignCenterSunmi;
import static com.skilrock.retailapp.utils.PrintUtil.alignHeadingSunmi;
import static com.skilrock.retailapp.utils.PrintUtil.appnedDash;
import static com.skilrock.retailapp.utils.PrintUtil.getBitmap;
import static com.skilrock.retailapp.utils.PrintUtil.getCount;
import static com.skilrock.retailapp.utils.PrintUtil.getStringByte;
import static com.skilrock.retailapp.utils.PrintUtil.printMiddle;
import static com.skilrock.retailapp.utils.PrintUtil.printTwoStringStringData;
import static com.skilrock.retailapp.utils.Utils.scaleBitmapAndKeepRation;

public class PrintActivityResult extends AppCompatActivity {
    UsbThermalPrinter usbThermalPrinter;
    PurchaseBeanSle.AdvMessageBean advMessageBean;
    ReprintBeanSle.AdvMessageBean advMessageBeanReprint;
    SaleCancelTicket saleCancelTicket = null;
    private ResultPrintBean resultResponseBean;
    static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    byte[] data;

    public  String getPrintDateFormat(String data) {
        String[] date = data.split("[-]");
        return months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[2] + "," + date[0];
    }

    public  String getPrintDateFormatDay(String data) {
        String[] date = data.split("[-]");
        return months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[0] + "," + date[2];
    }

    public  String getPrintSportsEventDate(String data) {
        String[] date = data.split("[-]");
        return months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[2];
    }

    public static String getTime(String time) {

        return time.split(":")[0] + ":" + time.split(":")[1];
    }

    private static String dotFormatter;
    private static String amountFormatter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print);
        usbThermalPrinter = BaseClassSle.getBaseClassSle().getUsbThermalPrinter(this);
        if (getIntent().getStringExtra("print").equalsIgnoreCase("printResult")) {
            AidlUtil.getInstance().printTransaction();
            ResultPrintBean drawDataTmp = new Gson().fromJson(getIntent().getExtras().getString("response"), ResultPrintBean.class);
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (drawDataTmp.getDrawWiseResultList() != null) {
                    data = printTicketSunmiResult("soccer 13", drawDataTmp.getDrawWiseResultList());
                    sendDataPrintSale(data);
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    setResult(0011, intent);
                    PrintActivityResult.this.finish();
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
                // }
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                if (drawDataTmp.getDrawWiseResultList() != null) {
                    printResult("soccer 13", drawDataTmp.getDrawWiseResultList());
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            }

        } else if (getIntent().getStringExtra("print").equalsIgnoreCase("printPurchase")) {
            PurchaseBeanSle purchaseBeanSle = new Gson().fromJson(getIntent().getExtras().getString("response"), PurchaseBeanSle.class);
            advMessageBean = purchaseBeanSle.getAdvMessage();
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (purchaseBeanSle.getTktData() != null) {
                    data = printTicketSunmiSale(purchaseBeanSle.getTktData(), advMessageBean);
                    sendDataPrintSale(data);
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    intent.putExtra("update_balance", true);
                    setResult(0012, intent);
                    PrintActivityResult.this.finish();
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                printTicketTps(purchaseBeanSle.getTktData(), advMessageBean);
            }

        } else if (getIntent().getStringExtra("print").equalsIgnoreCase("printReprint")) {
            ReprintBeanSle reprintBeanSle = new Gson().fromJson(getIntent().getStringExtra("response"), ReprintBeanSle.class);

            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (reprintBeanSle.getTktData() != null) {
                    data = printTicketSunmiReprint(reprintBeanSle.getTktData());
                    sendDataPrintSale(data);
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    setResult(0013, intent);
                    PrintActivityResult.this.finish();
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                printTicketReprint(reprintBeanSle.getTktData());
            }
        } else if (getIntent().getStringExtra("print").equalsIgnoreCase("printCancel")) {
            saleCancelTicket = new Gson().fromJson(getIntent().getStringExtra("response"), SaleCancelTicket.class);
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (saleCancelTicket != null) {
                    data = printTicketSunmiCancel(saleCancelTicket);
                    sendDataPrintSale(data);
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    intent.putExtra("update_balance", true);
                    setResult(0015, intent);
                    PrintActivityResult.this.finish();
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                if (saleCancelTicket != null) {
                    printTicketCancel(saleCancelTicket);
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            }


        } else if (getIntent().getStringExtra("print").equalsIgnoreCase("winning")) {
            PayVerifyTicket payVerifyTicket = new Gson().fromJson(getIntent().getStringExtra("winningResponse"), PayVerifyTicket.class);
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (payVerifyTicket != null) {
                    data = printTicketSunmiWinning(payVerifyTicket);
                    sendDataPrintSale(data);
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    intent.putExtra("update_balance", true);
                    setResult(0014, intent);
                    PrintActivityResult.this.finish();
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                if (payVerifyTicket != null) {
                    printWinning(payVerifyTicket);
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            }


        } else if (getIntent().getStringExtra("print").equalsIgnoreCase("Result")){
            Bundle bundle = getIntent().getExtras();
            int position = bundle.getInt("item");
            resultResponseBean = (ResultPrintBean) bundle.getSerializable("PrintDataResult");

            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (resultResponseBean.getDrawWiseResultList() != null) {
                    data = printTicketSunmiResultNew("soccer 13", resultResponseBean.getDrawWiseResultList(), position);
                    sendDataPrintSale(data);
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    setResult(0011, intent);
                    PrintActivityResult.this.finish();
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
                // }
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                if (resultResponseBean.getDrawWiseResultList() != null) {
                    printResultNew("soccer 13", resultResponseBean.getDrawWiseResultList(), position);
                } else {
                    Toast.makeText(this, R.string.some_technical_issue, Toast.LENGTH_SHORT).show();
                }
            }

        }

    }


    private byte[] printTicketSunmiCancel(SaleCancelTicket saleCancelTicket) {
        byte[] next2Line = PrintUtil.nextLine(2);
        byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
        byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
        byte[] boldOff = PrintUtil.boldOff();
        String Cancel_Amount = "";
        byte[] nextline = ESCUtil.nextLine(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            builder.append("_");
        }
        try {
            byte[] line = getStringByte(builder.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(saleCancelTicket.getGameTypeName().toUpperCase()));
            byte[] ticket_number_txt = getStringByte(printMiddle(getString(R.string.ticket_number)));
            byte[] ticket_number = getStringByte(printMiddle(appnedDash(saleCancelTicket.getTicketNumber(), 4)));
            byte[] ticket_cancelled_txt = getStringByte(printMiddle(getString(R.string.ticket_cancelled)));
            Cancel_Amount = Utils.getBalanceToView(saleCancelTicket.getRefundAmount(), ",", " ", 0);
            byte[] refund_amount = getStringByte(printTwoStringStringData(getString(R.string.refund_amount), Cancel_Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this))));
            byte[] reatiler_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, nextline, boldOn, game_name, boldOff, nextline, line, nextline, ticket_number_txt,
                    ticket_number, nextline, boldOn, ticket_cancelled_txt, boldOff, refund_amount, nextline, line, reatiler_name, next2Line, breakPartial};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder stingDash = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            stingDash.append("-");
        }

        return null;
    }


    private void printResult(final String gameName, final List<ResultPrintBean.DrawWiseResultListBean> drawDataTmp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        usbThermalPrinter.start(1);
                        usbThermalPrinter.reset();
                    } catch (TelpoException e) {
                        e.printStackTrace();
                    }
                    PrintClass.context = PrintActivityResult.this;
                    usbThermalPrinter.addString(getLineToPrint());
                    usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);

                    Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.camwinlotto);
                    bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                    if (bitmap1 != null) {
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                        usbThermalPrinter.printLogo(bitmap1, false);
                    }
                    usbThermalPrinter.setBold(true);
                    PrintClass.printHeadingSports(gameName.toUpperCase());
                    PrintClass.printTitle(getString(R.string.draw_result));
                    usbThermalPrinter.setBold(false);
                    usbThermalPrinter.setFontSize(2);

                    for (int y = 0; y < drawDataTmp.size(); y++) {
                        usbThermalPrinter.addString(getLineToPrint());
                        PrintClass.printMiddle(getString(R.string.draw_time));
                        String[] date = drawDataTmp.get(y).getDrawDateTime().split(" ");
                        PrintClass.printMiddle(drawDataTmp.get(y).getDrawName() + "- " + getPrintDateFormatDay(date[0]) + " " + date[1]);
                        usbThermalPrinter.addString(getLineToPrint());

                        List<ResultPrintBean.DrawWiseResultListBean.EventMasterListBean> eventDataTmp = drawDataTmp.get(y).getEventMasterList();
                        if (gameName.equals("soccer12")) {
                            int length = getLength("", getsoccer12String());
                            usbThermalPrinter.addString(String.format("%-" + (length) + "s", " ") + getsoccer12String());
                            for (int z = 0; z < eventDataTmp.size(); z++) {
                                usbThermalPrinter.addString(String.format("%-" + (length) + "s", eventDataTmp.get(z).getEventDisplay()) + getWinString12(eventDataTmp.get(z).getWinningOption()) + "");
                            }
                        } else {
                            int length = getLength("", getsoccer6String());
                            for (int z = 0; z < eventDataTmp.size(); z++) {
                                int num = Integer.toString(z + 1).length();
                                if (num == 2) {
                                    PrintClass.printTwoString((z + 1) + ". " + eventDataTmp.get(z).getEventDisplay().replaceAll("-", " ") + " ", eventDataTmp.get(z).getWinningOption());
                                } else {
                                    PrintClass.printTwoString("0" + (z + 1) + ". " + eventDataTmp.get(z).getEventDisplay().replaceAll("-", " ") + " ", eventDataTmp.get(z).getWinningOption());
                                }

                            }
                        }

                    }
                    usbThermalPrinter.addString(getLineToPrint());
                    usbThermalPrinter.printString();

                    usbThermalPrinter.walkPaper(10);


                } catch (TelpoException e) {
                    if (e instanceof NoPaperException) {
                        usbThermalPrinter.stop();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.putExtra("paperNotFound", "insert Paper");
                                String Result = "";
                                Result = e.toString();
                                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                                    intent.putExtra("isPrintSuccess", false);
                                }
                                setResult(0011, intent);
                                usbThermalPrinter.stop();
                                PrintActivityResult.this.finish();
                            }

                        });
                    }
                    usbThermalPrinter.stop();
                    e.printStackTrace();
                } finally {
                    usbThermalPrinter.stop();
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    setResult(0011, intent);
                    PrintActivityResult.this.finish();
                }

            }
        }).start();
    }



    private void printResultNew(final String gameName, final List<ResultPrintBean.DrawWiseResultListBean> drawDataTmp, int item) {
        new Thread(() -> {
            try {
                try {
                    usbThermalPrinter.start(1);
                    usbThermalPrinter.reset();
                } catch (TelpoException e) {
                    e.printStackTrace();
                }
                PrintClass.context = PrintActivityResult.this;
                usbThermalPrinter.addString(getLineToPrint());
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);

                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.camwinlotto);
                bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                if (bitmap1 != null) {
                    usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                    usbThermalPrinter.printLogo(bitmap1, false);
                }
                usbThermalPrinter.setBold(true);
                PrintClass.printHeadingSports(gameName.toUpperCase());
                PrintClass.printTitle(getString(R.string.draw_result));
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.setFontSize(2);


                    usbThermalPrinter.addString(getLineToPrint());
                    PrintClass.printMiddle(getString(R.string.draw_time));
                    String[] date = drawDataTmp.get(item).getDrawDateTime().split(" ");
                    PrintClass.printMiddle(drawDataTmp.get(item).getDrawName() + "- " + getPrintDateFormatDay(date[0]) + " " + date[1]);
                    usbThermalPrinter.addString(getLineToPrint());

                    for (int z = 0; z < drawDataTmp.get(item).getEventMasterList().size(); z++){

                        int length = getLength("", getsoccer6String());
                            int num = Integer.toString(z + 1).length();
                            if (num == 2) {
                                PrintClass.printTwoString((z + 1) + ". " + drawDataTmp.get(item).getEventMasterList().get(z).getEventDisplay().replaceAll("-", " ") + " ", drawDataTmp.get(item).getEventMasterList().get(z).getWinningOption());
                            } else {
                                PrintClass.printTwoString("0" + (z + 1) + ". " + drawDataTmp.get(item).getEventMasterList().get(z).getEventDisplay().replaceAll("-", " ") + " ", drawDataTmp.get(item).getEventMasterList().get(z).getWinningOption());
                            }

                        }

                usbThermalPrinter.addString(getLineToPrint());
                usbThermalPrinter.printString();

                usbThermalPrinter.walkPaper(10);


            } catch (TelpoException e) {
                if (e instanceof NoPaperException) {
                    usbThermalPrinter.stop();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.putExtra("paperNotFound", "insert Paper");
                            String Result = "";
                            Result = e.toString();
                            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                                intent.putExtra("isPrintSuccess", false);
                            }
                            setResult(0011, intent);
                            usbThermalPrinter.stop();
                            PrintActivityResult.this.finish();
                        }

                    });
                }
                usbThermalPrinter.stop();
                e.printStackTrace();
            } finally {
                usbThermalPrinter.stop();
                Intent intent = new Intent();
                intent.putExtra("paperNotFound", "insert Paper");
                intent.putExtra("isPrintSuccess", true);
                setResult(0011, intent);
                PrintActivityResult.this.finish();
            }

        }).start();
    }


    private byte[] printTicketSunmiResult(String gameName, List<ResultPrintBean.DrawWiseResultListBean> drawWiseResultList) {
        try {
            String print = "";
            byte[] next2Line = PrintUtil.nextLine(2);
            byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
            byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
            byte[] boldOff = PrintUtil.boldOff();
            byte[] nextline = ESCUtil.nextLine(1);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                builder.append("_");
            }
            byte[] line = getStringByte(builder.toString());
            StringBuilder stingDash = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                stingDash.append("-");
            }
            dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this));
            amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this));
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(gameName.toUpperCase().toUpperCase().toUpperCase()));
            byte[] draw_result_txt = alignCenterSunmi(getStringByte(getString(R.string.draw_result)));
            for (int y = 0; y < drawWiseResultList.size(); y++) {
                print = print + builder;
                String[] date = drawWiseResultList.get(y).getDrawDateTime().split(" ");
                print = print + printMiddle(getString(R.string.draw_time));
                print = print + printMiddle(drawWiseResultList.get(y).getDrawName() + "- " + getPrintDateFormatDay(date[0]) + " " + date[1]);
                print = print + builder;
                List<ResultPrintBean.DrawWiseResultListBean.EventMasterListBean> eventDataTmp = drawWiseResultList.get(y).getEventMasterList();
                for (int z = 0; z < eventDataTmp.size(); z++) {
                    int num = Integer.toString(z + 1).length();
                    if (num == 2) {
                        print = print + printTwoStringStringData((z + 1) + ". " + eventDataTmp.get(z).getEventDisplay().replaceAll("-", " ") + " ", eventDataTmp.get(z).getWinningOption());
                    } else {
                        print = print + printTwoStringStringData("0" + (z + 1) + ". " + eventDataTmp.get(z).getEventDisplay().replaceAll("-", " ") + " ", eventDataTmp.get(z).getWinningOption());
                    }
                }
            }
            byte[] restData = getStringByte(print);
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, next1Line, boldOn, game_name, next1Line, fontSize1Big, draw_result_txt, boldOff, next1Line, nextline, restData
                    , line, next2Line, next1Line, breakPartial};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


    private byte[] printTicketSunmiResultNew(String gameName, List<ResultPrintBean.DrawWiseResultListBean> drawWiseResultList, int item) {
        try {
            String print = "";
            byte[] next2Line = PrintUtil.nextLine(2);
            byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
            byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
            byte[] boldOff = PrintUtil.boldOff();
            byte[] nextline = ESCUtil.nextLine(1);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                builder.append("_");
            }
            byte[] line = getStringByte(builder.toString());
            StringBuilder stingDash = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                stingDash.append("-");
            }
            dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this));
            amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this));
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(gameName.toUpperCase().toUpperCase().toUpperCase()));
            byte[] draw_result_txt = alignCenterSunmi(getStringByte(getString(R.string.draw_result)));

                print = print + builder;
                String[] date = drawWiseResultList.get(item).getDrawDateTime().split(" ");
                print = print + printMiddle(getString(R.string.draw_time));
                print = print + printMiddle(drawWiseResultList.get(item).getDrawName() + "- " + getPrintDateFormatDay(date[0]) + " " + date[1]);
                print = print + builder;
                List<ResultPrintBean.DrawWiseResultListBean.EventMasterListBean> eventDataTmp = drawWiseResultList.get(item).getEventMasterList();
                for (int z = 0; z < eventDataTmp.size(); z++) {
                    int num = Integer.toString(z + 1).length();
                    if (num == 2) {
                        print = print + printTwoStringStringData((z + 1) + ". " + eventDataTmp.get(z).getEventDisplay().replaceAll("-", " ") + " ", eventDataTmp.get(z).getWinningOption());
                    } else {
                        print = print + printTwoStringStringData("0" + (z + 1) + ". " + eventDataTmp.get(z).getEventDisplay().replaceAll("-", " ") + " ", eventDataTmp.get(z).getWinningOption());
                    }
                }

            byte[] restData = getStringByte(print);
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, next1Line, boldOn, game_name, next1Line, fontSize1Big, draw_result_txt, boldOff, next1Line, nextline, restData
                    , line, next2Line, next1Line, breakPartial};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressLint("StaticFieldLeak")
    private void printTicketTps(final PurchaseBeanSle.TktDataBean tktBean, PurchaseBeanSle.AdvMessageBean advMessageBean) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                int serialNo = 1;
                try {
                    int i = usbThermalPrinter.checkStatus();
                    System.out.println("Printer Status " + i);

                } catch (TelpoException e) {
                    e.printStackTrace();
                }
                try {
                    usbThermalPrinter.start(1);
//                    usbThermalPrinter.reset();
                } catch (TelpoException e) {
                    e.printStackTrace();
                }
                try {
                    if (true) {

                        if (advMessageBean != null) {
                            if (advMessageBean.getTop() != null && advMessageBean.getTop().size() > 0) {
                                for (int i = 0; i < advMessageBean.getTop().size(); i++) {
                                    PrintClass.printMiddle(advMessageBean.getTop().get(i).getMsgText());
                                }
                                usbThermalPrinter.addString("                      ");
                            }

                        }

                        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.camwinlotto);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                        if (bitmap1 != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            usbThermalPrinter.printLogo(bitmap1, false);
                            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                                usbThermalPrinter.setBold(true);
                                PrintClass.printMiddle(getString(R.string.ticket_not_for_sale));
                                usbThermalPrinter.setBold(false);
                            }
                        }
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_LEFT);
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_LEFT);
                        PrintClass.printHeadingSports(tktBean.getGameTypeName().toUpperCase());
                        usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setBold(false);
                        PrintClass.printMiddle(getString(R.string.purchase_time));
                        PrintClass.printMiddle(getPrintDateFormat(tktBean.getPurchaseDate()) + "  " + tktBean.getPurchaseTime());
                        PrintClass.printMiddle(getString(R.string.ticket_number));
                        PrintClass.printMiddle(appnedDash(tktBean.getTicketNo(), 4));
                        usbThermalPrinter.addString("__________________________");
                        usbThermalPrinter.printString();
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                        PrintClass.printMiddle(getString(R.string.draw_timing));
                        PrintClass.printMiddle(tktBean.getBoardData().get(0).getDrawName() + "-" + getPrintDateFormat(tktBean.getBoardData().get(0).getDrawDate()) + "  " + getTime(tktBean.getBoardData().get(0).getDrawTime()));
                        usbThermalPrinter.addString("__________________________");
                        usbThermalPrinter.printString();
                        usbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
                        usbThermalPrinter.printString();
                        for (int i = 0; i < tktBean.getBoardData().get(0).getEventData().size(); i++) {
                            usbThermalPrinter.enlargeFontSize(1, 1);
                            String str = tktBean.getBoardData().get(0).getEventData().get(i).getSelection();
                            String date[] = tktBean.getBoardData().get(0).getEventData().get(i).getEventDate().split(" ");
                            int num = Integer.toString(i + 1).length();
                            if (num == 2) {
                                PrintClass.printTwoString((i + 1) + "." + tktBean.getBoardData().get(0).getEventData().get(i).getEventCodeHome() + " vs " + tktBean.getBoardData().get(0).getEventData().get(i).getEventCodeAway() + "(" + getPrintSportsEventDate(date[0]) + ")", str);
                            } else {
                                PrintClass.printTwoString("0" + (i + 1) + "." + tktBean.getBoardData().get(0).getEventData().get(i).getEventCodeHome() + " vs " + tktBean.getBoardData().get(0).getEventData().get(i).getEventCodeAway() + "(" + getPrintSportsEventDate(date[0]) + ")", str);
                            }
                            if (tktBean.getBoardData().get(0).getEventData().size() == i + 1) {
                                usbThermalPrinter.addString("___________________________");
                                usbThermalPrinter.setBold(false);
                            } else {

                            }

                        }
                        usbThermalPrinter.printString();

                        PrintClass.printTwoString(getString(R.string.unit_price), Utils.getBalanceToView(tktBean.getBoardData().get(0).getUnitPrice(), ",", " ", 0) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));
                        PrintClass.printTwoString(getString(R.string.no_of_lines), "" + tktBean.getBoardData().get(0).getNoOfLines());
                        PrintClass.printTwoString(getString(R.string.bet_amount_mutiple), "" + tktBean.getBoardData().get(0).getBetAmountMultiple());
                        usbThermalPrinter.setBold(true);
                        PrintClass.printTwoString(getString(R.string.total_amount).toUpperCase(), Utils.getBalanceToView(Double.parseDouble(tktBean.getTicketAmt()), ",", " ", 0) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));
                        usbThermalPrinter.setBold(false);
                        PrintClass.printBarcode(tktBean.getBrCd());
                        usbThermalPrinter.printString();
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                        usbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
                        usbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
                        printMiddleTelpo(appnedDash(tktBean.getTicketNo(), 4), usbThermalPrinter);
                        PrintClass.printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName());
                        if (tktBean.getExpiryDays() != 0) {
                            PrintClass.printMiddle(printMiddle(getString(R.string.ticket_validity) + tktBean.getExpiryDays()));
                        }
                        usbThermalPrinter.addString("__________________________");
                        usbThermalPrinter.printString();
                        if (advMessageBean != null && advMessageBean.getBottom() != null && advMessageBean.getBottom().size() > 0) {
                            usbThermalPrinter.addString("                      ");
                                for (int j = 0; j < advMessageBean.getBottom().size(); j++) {
                                    PrintClass.printMiddle(advMessageBean.getBottom().get(j).getMsgText());
                                    usbThermalPrinter.printString();
                                }
                        }
                        usbThermalPrinter.walkPaper(15);
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                    }
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.putExtra("paperNotFound", "insert Paper");
                            String Result = "";
                            Result = e.toString();
                            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                                intent.putExtra("isPrintSuccess", false);
                            }
                            setResult(0012, intent);
                            usbThermalPrinter.stop();
                            PrintActivityResult.this.finish();
                        }
                    });

                    e.printStackTrace();
                } finally {
                    usbThermalPrinter.stop();
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    setResult(0012, intent);
                    PrintActivityResult.this.finish();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent intent = new Intent();
                intent.putExtra("resetRequest", "resetData");
                setResult(59, intent);
                finish();
            }
        }.execute();
    }

    private byte[] printTicketSunmiSale(PurchaseBeanSle.TktDataBean tktData, PurchaseBeanSle.AdvMessageBean advMessageBean) {
        try {
            String print = "";
            String print_amount = "";
            byte[] next2Line = PrintUtil.nextLine(2);
            byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
            byte[] fontSize2Big = PrintUtil.fontSizeSetBig(2);
            byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
            byte[] top_msg = new byte[0];
            byte[] bottom_msg = new byte[0];
            byte[] boldOff = PrintUtil.boldOff();
            byte[] nextline = ESCUtil.nextLine(1);
            byte[] sample__ticket_msg = new byte[0];
            byte[] nextline2 = ESCUtil.nextLine(2);
            StringBuilder builder = new StringBuilder();
            byte[] ticket_validity = new byte[0];
            for (int i = 0; i < getCount(); i++) {
                builder.append("_");
            }
            byte[] line = getStringByte(builder.toString());
            StringBuilder stingDash = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                stingDash.append("-");
            }
            dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this));
            amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this));
            byte[] getDash = getStringByte(stingDash.toString());
            String top_message = "";
            String bottom_message = "";
            if (advMessageBean != null) {
                if (advMessageBean.getTop() != null && advMessageBean.getTop().size() > 0) {
                    for (int i = 0; i < advMessageBean.getTop().size(); i++) {
                        top_message = top_message + printMiddle(advMessageBean.getTop().get(i).getMsgText());
                    }
                }
                if (advMessageBean.getBottom() != null && advMessageBean.getBottom().size() > 0) {
                    for (int j = 0; j < advMessageBean.getBottom().size(); j++) {
                        bottom_message = bottom_message + printMiddle(advMessageBean.getBottom().get(j).getMsgText());
                    }
                }
                bottom_msg = alignHeadingSunmi(getStringByte(bottom_message));
                top_msg = alignHeadingSunmi(getStringByte(top_message));
            }

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.camwinlotto);
            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                sample__ticket_msg = alignHeadingSunmi(getStringByte(getString(R.string.ticket_not_for_sale)));
            }
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(tktData.getGameTypeName().toUpperCase().toUpperCase()));
            byte[] purchaseTime = alignCenterSunmi(getStringByte(getString(R.string.purchase_time)));
            String draw_date_time = getPrintDateFormat(tktData.getPurchaseDate()) + "  " + tktData.getPurchaseTime();
            byte purchase_time_print[] = alignCenterSunmi(getStringByte(draw_date_time));
            byte[] ticketNumber_txt = alignCenterSunmi(getStringByte(getString(R.string.ticket_number)));
            byte[] ticket_number = alignCenterSunmi(getStringByte(appnedDash(tktData.getTicketNo(), 4)));
            byte[] draw_timming_txt = alignCenterSunmi(getStringByte(getString(R.string.draw_timing)));
            byte[] draw_time_data = alignCenterSunmi(getStringByte(tktData.getBoardData().get(0).getDrawName() + "-" + getPrintDateFormat(tktData.getBoardData().get(0).getDrawDate()) + "  " + getTime(tktData.getBoardData().get(0).getDrawTime())));
            String matches;
            for (int i = 0; i < tktData.getBoardData().get(0).getEventData().size(); i++) {
                matches = "";
                String str = tktData.getBoardData().get(0).getEventData().get(i).getSelection().trim();
                String date[] = tktData.getBoardData().get(0).getEventData().get(i).getEventDate().split(" ");
                int num = Integer.toString(i + 1).length();
                if (num == 2) {
                    matches = printTwoStringStringData((i + 1) + "." + tktData.getBoardData().get(0).getEventData().get(i).getEventCodeHome().trim() + " vs " + tktData.getBoardData().get(0).getEventData().get(i).getEventCodeAway().trim() + "(" + getPrintSportsEventDate(date[0]) + ")", str);
                    print = print + matches;
                } else {
                    matches = printTwoStringStringData("0" + (i + 1) + "." + tktData.getBoardData().get(0).getEventData().get(i).getEventCodeHome() + " vs " + tktData.getBoardData().get(0).getEventData().get(i).getEventCodeAway() + "(" + getPrintSportsEventDate(date[0]) + ")", str);
                    print = print + matches;
                }
                if (tktData.getBoardData().get(0).getEventData().size() == i + 1) {
                    print = print + builder;
                } else {

                }
            }
            byte[] restOfDataByte = getStringByte(print);
            print_amount = print_amount + printTwoStringStringData(getString(R.string.unit_price), Utils.getBalanceToView(tktData.getBoardData().get(0).getUnitPrice(), ",", " ", 0) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));
            print_amount = print_amount + printTwoStringStringData(getString(R.string.no_of_lines), "" + tktData.getBoardData().get(0).getNoOfLines());
            print_amount = print_amount + printTwoStringStringData(getString(R.string.bet_amount_mutiple), "" + tktData.getBoardData().get(0).getBetAmountMultiple());
            print_amount = print_amount + printTwoStringStringData(getString(R.string.total_amount).toUpperCase(), Utils.getBalanceToView(Double.parseDouble(tktData.getTicketAmt()), ",", " ", 0) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));
            byte[] price_data = getStringByte(print_amount);
            String barcode = tktData.getTicketNo();
            byte[] barcode_print = ESCUtil.printBitmap(getBitmap(barcode, 1, 384, 70));
            byte[] ticket_no = getStringByte(printMiddle(appnedDash(barcode, 4)));
            byte[] reatiler_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
            if (tktData.getExpiryDays() != 0) {
                ticket_validity = getStringByte(printMiddle(getString(R.string.ticket_validity) + tktData.getExpiryDays()));
            } else {
                ticket_validity = getStringByte(printMiddle(getString(R.string.ticket_validity) + ""));
            }
            byte[] not_for_sale_txt = getStringByte(printMiddle(getString(R.string.not_for_sale)));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();

            byte[][] cmdBytes = {ESCUtil.alignCenter(), boldOn, fontSize2Big, top_msg, boldOff, ESCUtil.alignCenter(), fontSize1Big, nextline2, logo, boldOn,sample__ticket_msg,boldOff,next1Line, boldOn, game_name, next1Line, boldOff, fontSize1Big, purchaseTime, next1Line, purchase_time_print, nextline, ticketNumber_txt,
                    next1Line, ticket_number, next1Line, line, draw_timming_txt, next1Line, draw_time_data, next1Line, line, next1Line, restOfDataByte, next1Line,
                    price_data, next1Line, barcode_print, ticket_no, next1Line, reatiler_name, ticket_validity, line, fontSize2Big, boldOn, bottom_msg, fontSize1Big, boldOff, next2Line, breakPartial};
            return PrintUtil.byteMerger(cmdBytes);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressLint("StaticFieldLeak")
    private void printTicketReprint(final ReprintBeanSle.TktDataBean tktBean) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                int serialNo = 1;
                try {
                    int i = usbThermalPrinter.checkStatus();
                    System.out.println("Printer Status " + i);
                   /* if (i == 1) {
                        usbThermalPrinter.stop();
                        setResult(104, new Intent());
                        finish();
                    }*/
                } catch (TelpoException e) {
                    e.printStackTrace();
                }
                try {
                    usbThermalPrinter.start(1);
//                    usbThermalPrinter.reset();
                } catch (TelpoException e) {
                    e.printStackTrace();
                }
                try {
                    if (true) {
                        usbThermalPrinter.setLineSpace(3);
                        //usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setGray(1);
                        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.camwinlotto);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                        if (bitmap1 != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            usbThermalPrinter.printLogo(bitmap1, false);

                            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                                usbThermalPrinter.setBold(true);
                                PrintClass.printMiddle(getString(R.string.ticket_not_for_sale));
                                usbThermalPrinter.setBold(false);
                            }
                        }
                        dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(PrintActivityResult.this));
                        amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(PrintActivityResult.this));
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_LEFT);
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_LEFT);
                        PrintClass.printHeadingSports(tktBean.getGameTypeName().toUpperCase());
                        usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setBold(true);
                        PrintClass.printMiddle(getString(R.string.reprint));
                        usbThermalPrinter.setBold(false);
                        PrintClass.printMiddle(getString(R.string.purchase_time));
                        PrintClass.printMiddle(getPrintDateFormat(tktBean.getPurchaseDate()) + " " + tktBean.getPurchaseTime());
                        PrintClass.printMiddle(getString(R.string.ticket_number));
                        PrintClass.printMiddle(appnedDash(tktBean.getTicketNo(), 4));
                        usbThermalPrinter.addString("__________________________");
                        usbThermalPrinter.printString();
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                        PrintClass.printMiddle(getString(R.string.draw_timing));
                        PrintClass.printMiddle(tktBean.getBoardData().get(0).getDrawName() + "-" + getPrintDateFormat(tktBean.getBoardData().get(0).getDrawDate()) + "  " + getTime(tktBean.getBoardData().get(0).getDrawTime()));
                        usbThermalPrinter.addString("__________________________");
                        usbThermalPrinter.printString();
                        usbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
                        usbThermalPrinter.printString();
                        for (int i = 0; i < tktBean.getBoardData().get(0).getEventData().size(); i++) {
                            usbThermalPrinter.enlargeFontSize(1, 1);
                            String str = tktBean.getBoardData().get(0).getEventData().get(i).getSelection();
                            //PrintClass.printTwoString((i+1)+". "+tktBean.getBoardData().get(0).getEventData().get(i).getEventDisplayHome() +" - ", str);
                            //PrintClass.printTwoString(tktBean.getBoardData().get(0).getEventData().get(i).getEventDisplayAway(),"");
                            String date[] = tktBean.getBoardData().get(0).getEventData().get(i).getEventDate().split(" ");
                            int num = Integer.toString(i + 1).length();
                            if (num == 2) {
                                PrintClass.printTwoString((i + 1) + "." + tktBean.getBoardData().get(0).getEventData().get(i).getEventCodeHome() + " vs " + tktBean.getBoardData().get(0).getEventData().get(i).getEventCodeAway() + "(" + getPrintSportsEventDate(date[0]) + ")", str);
                            } else {
                                PrintClass.printTwoString("0" + (i + 1) + "." + tktBean.getBoardData().get(0).getEventData().get(i).getEventCodeHome() + " vs " + tktBean.getBoardData().get(0).getEventData().get(i).getEventCodeAway() + "(" + getPrintSportsEventDate(date[0]) + ")", str);
                            }
                            if (tktBean.getBoardData().get(0).getEventData().size() == i + 1) {
                                usbThermalPrinter.addString("___________________________");
                                usbThermalPrinter.setBold(false);
                            } else {

                            }
                        }
                        usbThermalPrinter.printString();
                        String Amount_Total = Utils.getBalanceToView(Double.parseDouble(tktBean.getTicketAmt()), dotFormatter, amountFormatter, 0);
                        PrintClass.printTwoString(getString(R.string.unit_price), tktBean.getBoardData().get(0).getUnitPrice() + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));
                        PrintClass.printTwoString(getString(R.string.no_of_lines), "" + tktBean.getBoardData().get(0).getNoOfLines());
                        PrintClass.printTwoString(getString(R.string.bet_amount_mutiple), "" + tktBean.getBoardData().get(0).getBetAmountMultiple());
                        PrintClass.printTwoString(getString(R.string.total_amount).toUpperCase(), Amount_Total + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));

                        usbThermalPrinter.addString("__________________________");
                        if (advMessageBean != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            String printMes = "";
                            if (printMes.trim().length() > 0) {
                                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                                usbThermalPrinter.addString(printMes);
                            }
                            usbThermalPrinter.printString();
                        }

                        PrintClass.printBarcode(tktBean.getBrCd());
                        usbThermalPrinter.printString();
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);

                        usbThermalPrinter.setAlgin(com.telpo.tps550.api.printer.UsbThermalPrinter.ALGIN_MIDDLE);
                        usbThermalPrinter.setAlgin(com.telpo.tps550.api.printer.UsbThermalPrinter.ALGIN_LEFT);
                        printMiddleTelpo(appnedDash(tktBean.getTicketNo(), 4), usbThermalPrinter);
                        PrintClass.printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName());
                        if (tktBean.getExpiryDays() != 0) {
                            PrintClass.printMiddle(printMiddle(getString(R.string.ticket_validity) + tktBean.getExpiryDays()));
                        }
                        usbThermalPrinter.addString("__________________________");
                        usbThermalPrinter.printString();
                        usbThermalPrinter.walkPaper(10);
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                    }
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.putExtra("paperNotFound", "insert Paper");
                            String Result = "";
                            Result = e.toString();
                            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                                intent.putExtra("isPrintSuccess", false);
                            }
                            setResult(0013, intent);
                            usbThermalPrinter.stop();
                            PrintActivityResult.this.finish();

                        }
                    });

                    e.printStackTrace();
                } finally {
                    usbThermalPrinter.stop();
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    setResult(0013, intent);
                    PrintActivityResult.this.finish();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent intent = new Intent();
                intent.putExtra("resetRequest", "resetData");
                setResult(59, intent);
                finish();
            }
        }.execute();
    }

    private byte[] printTicketSunmiReprint(ReprintBeanSle.TktDataBean tktData) {
        try {
            String print = "";
            String print_amount = "";
            byte[] next2Line = PrintUtil.nextLine(2);
            byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
            byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
            byte[] boldOff = PrintUtil.boldOff();
            byte[] nextline = ESCUtil.nextLine(1);
            byte[] ticket_validity;
            byte [] sample__ticket_msg = new byte[0];
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                builder.append("_");
            }
            byte[] line = getStringByte(builder.toString());
            StringBuilder stingDash = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                stingDash.append("-");
            }
            dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this));
            amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this));
            byte[] getDash = getStringByte(stingDash.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.camwinlotto);
            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                sample__ticket_msg = alignHeadingSunmi(getStringByte(getString(R.string.ticket_not_for_sale)));
            }
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] reprint_txt = alignHeadingSunmi(getStringByte(getString(R.string.reprint)));
            byte[] game_name = alignHeadingSunmi(getStringByte(tktData.getGameTypeName().toUpperCase().toUpperCase()));
            byte[] purchaseTime = alignCenterSunmi(getStringByte(getString(R.string.purchase_time)));
            String draw_date_time = getPrintDateFormat(tktData.getPurchaseDate()) + "  " + tktData.getPurchaseTime();
            byte purchase_time_print[] = alignCenterSunmi(getStringByte(draw_date_time));
            byte[] ticketNumber_txt = alignCenterSunmi(getStringByte(getString(R.string.ticket_number)));
            byte[] ticket_number = alignCenterSunmi(getStringByte(appnedDash(tktData.getTicketNo(), 4)));
            byte[] draw_timming_txt = alignCenterSunmi(getStringByte(getString(R.string.draw_timing)));
            byte[] draw_time_data = alignCenterSunmi(getStringByte(tktData.getBoardData().get(0).getDrawName() + "-" + getPrintDateFormat(tktData.getBoardData().get(0).getDrawDate()) + "  " + getTime(tktData.getBoardData().get(0).getDrawTime())));
            String matches;
            for (int i = 0; i < tktData.getBoardData().get(0).getEventData().size(); i++) {
                matches = "";
                String str = tktData.getBoardData().get(0).getEventData().get(i).getSelection().trim();
                String date[] = tktData.getBoardData().get(0).getEventData().get(i).getEventDate().split(" ");
                int num = Integer.toString(i + 1).length();
                if (num == 2) {
                    matches = printTwoStringStringData((i + 1) + "." + tktData.getBoardData().get(0).getEventData().get(i).getEventCodeHome().trim() + " vs " + tktData.getBoardData().get(0).getEventData().get(i).getEventCodeAway().trim() + "(" + getPrintSportsEventDate(date[0]) + ")", str);
                    print = print + matches;
                } else {
                    matches = printTwoStringStringData("0" + (i + 1) + "." + tktData.getBoardData().get(0).getEventData().get(i).getEventCodeHome() + " vs " + tktData.getBoardData().get(0).getEventData().get(i).getEventCodeAway() + "(" + getPrintSportsEventDate(date[0]) + ")", str);
                    print = print + matches;
                }
                if (tktData.getBoardData().get(0).getEventData().size() == i + 1) {
                    print = print + builder;
                } else {

                }
            }
            byte[] restOfDataByte = getStringByte(print);
            print_amount = print_amount + printTwoStringStringData(getString(R.string.unit_price), Utils.getBalanceToView(tktData.getBoardData().get(0).getUnitPrice(), ",", " ", 0) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));
            print_amount = print_amount + printTwoStringStringData(getString(R.string.no_of_lines), "" + tktData.getBoardData().get(0).getNoOfLines());
            print_amount = print_amount + printTwoStringStringData(getString(R.string.bet_amount_mutiple), "" + tktData.getBoardData().get(0).getBetAmountMultiple());
            print_amount = print_amount + printTwoStringStringData(getString(R.string.total_amount).toUpperCase(), Utils.getBalanceToView(Double.parseDouble(tktData.getTicketAmt()), ",", " ", 0) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));
            byte[] price_data = getStringByte(print_amount);
            String barcode = tktData.getTicketNo();
            byte[] barcode_print = ESCUtil.printBitmap(getBitmap(barcode, 1, 384, 70));
            byte[] ticket_no = getStringByte(printMiddle(appnedDash(barcode, 4)));
            byte[] reatiler_name = getStringByte(printMiddle(PlayerData.getInstance().getUsername()));
            if (tktData.getExpiryDays() != 0) {
                ticket_validity = getStringByte(printMiddle(getString(R.string.ticket_validity) + tktData.getExpiryDays()));
            } else {
                ticket_validity = getStringByte(printMiddle(getString(R.string.ticket_validity) + ""));
            }
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, boldOn,sample__ticket_msg,boldOff,next1Line, boldOn, game_name, next1Line, reprint_txt, next1Line, boldOff, fontSize1Big, purchaseTime, next1Line, purchase_time_print, nextline, ticketNumber_txt,
                    next1Line, ticket_number, next1Line, line, draw_timming_txt, next1Line, draw_time_data, next1Line, line, next1Line, restOfDataByte, next1Line,
                    price_data, next1Line, barcode_print, ticket_no, next1Line, reatiler_name, ticket_validity, line,  next2Line, breakPartial};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;

    }

    void printTicketCancel(final SaleCancelTicket CancelResponse) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        usbThermalPrinter.start(1);
                        usbThermalPrinter.reset();
                    } catch (TelpoException e) {
                        e.printStackTrace();
                    }
                    dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(PrintActivityResult.this));
                    amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(PrintActivityResult.this));

                    String amount = "";
                    Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.camwinlotto);
                    bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                    if (bitmap1 != null) {
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                        usbThermalPrinter.printLogo(bitmap1, false);
                    }
                    PrintClass.printHeadingSports(CancelResponse.getGameTypeName().toUpperCase());
                    usbThermalPrinter.setBold(false);
                    usbThermalPrinter.setFontSize(2);
                    usbThermalPrinter.addString("___________________________");
                    usbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
                    usbThermalPrinter.setLineSpace(3);
                    usbThermalPrinter.setGray(1);
                    usbThermalPrinter.printString();
                    usbThermalPrinter.reset();
                    usbThermalPrinter.setGray(1);
                    PrintClass.printMiddle(getString(R.string.ticket_number));
                    PrintClass.printMiddle(appnedDash(CancelResponse.getTicketNumber(), 4));
                    usbThermalPrinter.setBold(true);
                    PrintClass.printMiddle(getString(R.string.ticket_cancelled));
                    usbThermalPrinter.setBold(false);
                    amount = Utils.getBalanceToView(CancelResponse.getRefundAmount(), ",", " ", 0);
                    PrintClass.printTwoString(getString(R.string.refund_amount), amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));
                    usbThermalPrinter.setBold(true);
                    usbThermalPrinter.addString("___________________________");
                    PrintClass.printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName());
                    usbThermalPrinter.setBold(false);
                    usbThermalPrinter.printString();
                    usbThermalPrinter.walkPaper(10);
                } catch (TelpoException e) {
                    if (e instanceof NoPaperException) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.putExtra("paperNotFound", "insert Paper");
                                String Result = "";
                                Result = e.toString();
                                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                                    intent.putExtra("isPrintSuccess", false);
                                }
                                setResult(0015, intent);
                                usbThermalPrinter.stop();
                                PrintActivityResult.this.finish();
                            }
                        });
                    }
                    e.printStackTrace();
                } finally {
                    usbThermalPrinter.stop();
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    intent.putExtra("isUpdateBalance", true);
                    setResult(0015, intent);
                    PrintActivityResult.this.finish();
                }
            }
        }).start();
    }

    private void printWinning(PayVerifyTicket payVerifyTicket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        usbThermalPrinter.start(1);
                        usbThermalPrinter.reset();
                    } catch (TelpoException e) {
                        usbThermalPrinter.stop();
                        e.printStackTrace();
                    }
                    if (payVerifyTicket != null) {
                        String Amount = "";
                        dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(PrintActivityResult.this));
                        amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(PrintActivityResult.this));
                        if (payVerifyTicket.getAdvMessage() != null) {
                            if (payVerifyTicket.getAdvMessage() != null && payVerifyTicket.getAdvMessage().getTop().size() > 0) {
                                for (int i = 0; i < payVerifyTicket.getAdvMessage().getTop().size(); i++) {
                                    PrintClass.printMiddle(payVerifyTicket.getAdvMessage().getTop().get(i).getMsgText());
                                }
                                usbThermalPrinter.addString("                      ");
                            }

                        }
                        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.camwinlotto);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                        if (bitmap1 != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            usbThermalPrinter.printLogo(bitmap1, false);
                            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                                usbThermalPrinter.setBold(true);
                                PrintClass.printMiddle(getString(R.string.ticket_not_for_sale));
                                usbThermalPrinter.setBold(false);
                            }
                        }
                        PrintClass.printHeading(payVerifyTicket.getGameTypeName().toUpperCase());
                        usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setBold(false);
                        PrintClass.printMiddle(getString(R.string.ticket_number));
                        PrintClass.printMiddle(appnedDash(payVerifyTicket.getTicketNo(), 4));
                        String draw_date = payVerifyTicket.getDrawTime().split(" ")[0];
                        String draw_time = payVerifyTicket.getDrawTime().split(" ")[1];
                        PrintClass.printTwoString(getString(R.string.draw_date), getPrintDateFormat(draw_date));
                        PrintClass.printTwoString(getString(R.string.draw_time), draw_time);
                        Amount = Utils.getBalanceToView(Double.parseDouble(payVerifyTicket.getTotalPay()), dotFormatter, amountFormatter, 0);
                        PrintClass.printTwoString(getString(R.string.winning_amt_colon), Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this)));
                        PrintClass.printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName());
                        usbThermalPrinter.addString("___________________________");
                        usbThermalPrinter.printString();
                        if (payVerifyTicket.getAdvMessage() != null) {
                            usbThermalPrinter.addString("                      ");
                            if (payVerifyTicket.getAdvMessage().getBottom() != null && payVerifyTicket.getAdvMessage().getBottom().size() > 0) {
                                for (int j = 0; j < payVerifyTicket.getAdvMessage().getBottom().size(); j++) {
                                    PrintClass.printMiddle(payVerifyTicket.getAdvMessage().getBottom().get(j).getMsgText());
                                    usbThermalPrinter.printString();
                                }
                            }
                        }
                        usbThermalPrinter.walkPaper(15);
                    }

                } catch (TelpoException e) {
                    e.printStackTrace();
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isUpdateBalance", false);
                    String Result = "";
                    Result = e.toString();
                    if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                        intent.putExtra("isPrintSuccess", false);
                    }
                    setResult(0014, intent);
                    usbThermalPrinter.stop();
                    PrintActivityResult.this.finish();
                } finally {
                    usbThermalPrinter.stop();
                    Intent intent = new Intent();
                    intent.putExtra("paperNotFound", "insert Paper");
                    intent.putExtra("isPrintSuccess", true);
                    intent.putExtra("isUpdateBalance", true);
                    setResult(0015, intent);
                    PrintActivityResult.this.finish();
                }


            }
        }).start();

    }

    private byte[] printTicketSunmiWinning(PayVerifyTicket payVerifyTicket) {
        byte[] next2Line = PrintUtil.nextLine(2);
        byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
        byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
        byte[] boldOff = PrintUtil.boldOff();
        byte []sample__ticket_msg = new byte[0];
        byte[] fontSize2Big = PrintUtil.fontSizeSetBig(2);
        String Cancel_Amount = "";
        byte[] top_msg = new byte[0];
        byte[] bottom_msg = new byte[0];
        byte[] nextline = ESCUtil.nextLine(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            builder.append("_");
        }
        try {
            byte[] line = getStringByte(builder.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.camwinlotto);
            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                sample__ticket_msg = alignHeadingSunmi(getStringByte(getString(R.string.ticket_not_for_sale)));
            }

            String top_message = "";
            String bottom_message = "";

            if (payVerifyTicket.getAdvMessage() != null) {
                if (payVerifyTicket.getAdvMessage().getTop() != null && payVerifyTicket.getAdvMessage().getTop().size() > 0) {
                    for (int i = 0; i < payVerifyTicket.getAdvMessage().getTop().size(); i++) {
                        top_message = top_message + printMiddle(payVerifyTicket.getAdvMessage().getTop().get(i).getMsgText());
                    }
                }
                if (payVerifyTicket.getAdvMessage().getBottom() != null && payVerifyTicket.getAdvMessage().getBottom().size() > 0) {
                    for (int j = 0; j < payVerifyTicket.getAdvMessage().getBottom().size(); j++) {
                        bottom_message = bottom_message + printMiddle(payVerifyTicket.getAdvMessage().getBottom().get(j).getMsgText());
                    }
                }
                bottom_msg = alignHeadingSunmi(getStringByte(bottom_message));
                top_msg = alignHeadingSunmi(getStringByte(top_message));
            }
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(payVerifyTicket.getGameTypeName().toUpperCase()));
            byte[] ticket_number_txt = getStringByte(printMiddle(getString(R.string.ticket_number)));
            byte[] ticket_number = getStringByte(printMiddle(appnedDash(payVerifyTicket.getTicketNo(), 4)));
            String draw_date_val = payVerifyTicket.getDrawTime().split(" ")[0];
            String draw_time_val = payVerifyTicket.getDrawTime().split(" ")[1];
            byte[] draw_date = getStringByte(printTwoStringStringData(getString(R.string.draw_date), getPrintDateFormat(draw_date_val)));
            byte[] draw_time = getStringByte(printTwoStringStringData(getString(R.string.draw_time), draw_time_val));
            String Amount = Utils.getBalanceToView(Double.parseDouble(payVerifyTicket.getTotalPay()), ",", " ", 0);
            byte[] win_amount = getStringByte(printTwoStringStringData(getString(R.string.winning_amt_colon), Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PrintActivityResult.this))));
            byte[] reatiler_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), boldOn, fontSize2Big, top_msg, boldOff, ESCUtil.alignCenter(), logo, boldOn ,sample__ticket_msg, boldOff ,nextline, boldOn, game_name, boldOff, nextline, ticket_number_txt,
                    ticket_number, nextline, draw_date, draw_time, line, win_amount,  nextline, reatiler_name, line, fontSize2Big, boldOn, bottom_msg, fontSize1Big, boldOff,next2Line, breakPartial};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder stingDash = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            stingDash.append("-");
        }
        return null;
    }

    protected int getLength(String firstString, String secondString) {
        return firstString.length() + (27 - (firstString.length() + secondString.length()));
    }

    protected String getWinString12(String win) {

        if (win.equals("H")) {
            return "|X| | |";
        } else if (win.equals("D")) {
            return "| |X| |";
        } else if (win.equals("A")) {
            return "| | |X|";
        } else if (win.equals("C")) {
            return "Canceled";
        } else {
            return "";
        }
    }

    protected String getWinString6(String win) {
        if (win.equals("H+")) {
            return "|X| | | | |";
        }
        if (win.equals("H")) {
            return "| |X| | | |";
        } else if (win.equals("D")) {
            return "| | |X| | |";
        } else if (win.equals("A")) {
            return "| | | |X| |";
        } else if (win.equals("A+")) {
            return "| | | | |X|";
        } else if (win.equals("C")) {
            return "Canceled";
        } else {
            return "";
        }
    }

    public String getsoccer6String580() {
        return " " + getResources().getString(R.string.game_soccer_home_plus) + " " + getResources().getString(R.string.game_soccer_home) + " " + getResources().getString(R.string.game_soccer_draw) + " " + getResources().getString(R.string.game_soccer_away) + " " + getResources().getString(R.string.game_soccer_away_plus);
    }

    public String getsoccer12String580() {
        return getResources().getString(R.string.game_soccer_home) + " " + getResources().getString(R.string.game_soccer_draw) + " " + getResources().getString(R.string.game_soccer_away) + " ";
    }

    public String getsoccer6String() {
        return getResources().getString(R.string.game_soccer_home_plus) + getResources().getString(R.string.game_soccer_home) + getResources().getString(R.string.game_soccer_draw) + getResources().getString(R.string.game_soccer_away) + getResources().getString(R.string.game_soccer_away_plus);
    }

    public String getsoccer12String() {
        return getResources().getString(R.string.game_soccer_home) + " " + getResources().getString(R.string.game_soccer_draw) + " " + getResources().getString(R.string.game_soccer_away);
    }

    protected String getLineToPrint() {
        return "__________________________";
    }

    public static void printMiddleTelpo(String content, UsbThermalPrinter usbThermalPrinter) throws TelpoException {
        // set Align for Text String
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        usbThermalPrinter.addString(content);
        usbThermalPrinter.setGray(2);
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
    }

    private void sendDataPrintSale(final byte[] send) {
        AidlUtil.getInstance().sendRawData(send);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }

}
