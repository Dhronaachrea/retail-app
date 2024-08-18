package com.skilrock.retailapp.sle_game_portrait;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.RemoteException;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.telpo.tps550.api.InternalErrorException;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.printer.NoPaperException;
import com.telpo.tps550.api.printer.ThermalPrinter;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import static com.skilrock.retailapp.utils.Utils.getDeviceName;

public class PrintClass {

    public static Context context;
    private static UsbThermalPrinter usbThermalPrinter = BaseClassSle.getBaseClassSle()
            .getUsbThermalPrinter(context);


    static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    static String[] monthsBrazil = {};

    public static void printString() throws TelpoException {
        usbThermalPrinter.printString();
        usbThermalPrinter.clearString();
    }

    public static void printLine() throws TelpoException {
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.setGray(2);
        usbThermalPrinter.addString("--------------------------------\n\n");
    }



    public static void printTitleAtTop(String content) throws TelpoException {
        usbThermalPrinter.setFontSize(2);
        //usbThermalPrinter.setBold(true);
        //usbThermalPrinter.enlargeFontSize(5, 5);
        // set Align for Text String
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        // usbThermalPrinter.setGray(20);
        usbThermalPrinter.addString(content);
        // usbThermalPrinter.setGray(2);
        usbThermalPrinter.setFontSize(2);
        //  usbThermalPrinter.setBold(false);
        // usbThermalPrinter.enlargeFontSize(5, 5);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
    }




    public static void printTitle(String content) throws TelpoException {
        //  usbThermalPrinter.setFontSize(2);
        // usbThermalPrinter.enlargeFontSize(1, 2);
        // set Align for Text String
//        usbThermalPrinter.setBold(true);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        //usbThermalPrinter.setGray(20);
        usbThermalPrinter.addString(content);
        usbThermalPrinter.setGray(2);
//        usbThermalPrinter.setBold(false);

        // usbThermalPrinter.setFontSize(2);
        // usbThermalPrinter.enlargeFontSize(1, 1);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
    }

    public static void printTitleXXL(String content) throws TelpoException {
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.enlargeFontSize(1, 2);
        // set Align for Text String
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        //usbThermalPrinter.setGray(20);
        usbThermalPrinter.addString(content);
        usbThermalPrinter.setGray(2);
        // usbThermalPrinter.setFontSize(2);
        // usbThermalPrinter.enlargeFontSize(1, 1);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
    }

    public static void printTitleSub(String content) throws TelpoException {
        usbThermalPrinter.setFontSize(2);
        // usbThermalPrinter.enlargeFontSize(1, 2);
        // set Align for Text String
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        //usbThermalPrinter.setGray(20);
        usbThermalPrinter.addString(content);
        //usbThermalPrinter.setGray(2);
        usbThermalPrinter.setFontSize(2);
        //  usbThermalPrinter.enlargeFontSize(1, 2);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
    }
    public static void printTitleSub580(String content) throws TelpoException {
        usbThermalPrinter.setFontSize(1);
        // usbThermalPrinter.enlargeFontSize(1, 2);
        // set Align for Text String
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        //usbThermalPrinter.setGray(20);
        usbThermalPrinter.addString(content);
        //usbThermalPrinter.setGray(2);
        usbThermalPrinter.setFontSize(2);
        //  usbThermalPrinter.enlargeFontSize(1, 2);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
    }
    public static void printStar(String star) throws TelpoException {
        usbThermalPrinter.setFontSize(1);
        usbThermalPrinter.setBold(true);
        usbThermalPrinter.addString(star);
        usbThermalPrinter.setBold(false);
        usbThermalPrinter.setFontSize(2);

    }



    public static void printMiddle(String content) throws TelpoException {
        // set Align for Text String
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        usbThermalPrinter.addString(content);
        usbThermalPrinter.setGray(2);
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);

    }

    public static void printLeft(String content) throws TelpoException {
        // set Align for Text String
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
        usbThermalPrinter.addString(content);
        usbThermalPrinter.setGray(2);
        usbThermalPrinter.setFontSize(2);

    }



    public static void printTitleWithQP(String content, int QP) throws TelpoException {
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.enlargeFontSize(2, 1);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
        // set Align for Text String
        usbThermalPrinter.setGray(20);
        StringBuffer str = new StringBuffer();
        str.append(content);
        if (QP == 1) {
            for (int i = content.length(); i < 14; i++)
                str.append(" ");
            str.append("QP");
        }
        System.out.println(str.length());
        usbThermalPrinter.addString(str.toString() + "\n\n");
        usbThermalPrinter.setGray(2);
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.enlargeFontSize(1, 1);

    }

    public static String getExpiryDateLocally(String data){
        String[] date=data.split("[-]");
        int dateupdated=0;
        int year=Integer.parseInt(date[2]);
        if(Integer.parseInt(date[1])<12){
            dateupdated=Integer.parseInt(date[1])+1;
        }
        else{
            dateupdated=1;
            year=year+1;
        }
        return (date[0]+"-"+String.valueOf(dateupdated)+"-"+String.valueOf(year));

    }

    public static String getPrintDateFormat(String data) {
        String[] date = data.split("[-]");
        return months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[2] + ", " + date[0];
    }

       public static void printHeading(String gameName) throws TelpoException {
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        usbThermalPrinter.setFontSize(3);
        usbThermalPrinter.setBold(true);
        usbThermalPrinter.addString(gameName);

    }

    public static void printTwoStringInMiddle(String one, String two) throws TelpoException {
        usbThermalPrinter.setBold(true);
        usbThermalPrinter.setFontSize(1);
        usbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
        StringBuffer sb = new StringBuffer();
        sb.append(one);
        sb.append(two);
        usbThermalPrinter.addString(sb.toString());
        usbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.setBold(false);
    }

    public static void printDrawsSelected(String game, int noOfDraws, String[] draws) throws TelpoException {
        usbThermalPrinter.setFontSize(3);
        if (game.equalsIgnoreCase("4/52")) {
            int k = 0;
            for (int i = 0; i < noOfDraws; i++) {
                int count = 0;
                StringBuffer sb = new StringBuffer();
                for (int j = 0 + k; j < 4 + k; j++) {
                    sb.append(draws[k]);
                    sb.append("    ");
                    k++;
                    count++;

                    if (count == 4) {
                        usbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
                        usbThermalPrinter.addString(sb.toString());
                        break;
                    }

                }

            }

        }
        usbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
        usbThermalPrinter.setFontSize(2);
    }
    public static void printFooter(String title, String content) throws TelpoException{
        StringBuffer sb=new StringBuffer();
        usbThermalPrinter.setFontSize(2);
        sb.append(title);
        usbThermalPrinter.setFontSize(1);
        sb.append(content);
        usbThermalPrinter.addString(sb.toString());
        usbThermalPrinter.setFontSize(2);
    }
    private static boolean isOn;
    public static void isMatchPrintOn(boolean isOn){
        PrintClass.isOn = isOn;
    }
    public static void printTwoString(String one, String two) throws TelpoException {
        //usbThermalPrinter.setFontSize(2);
        if (getDeviceName().contains("700") || (getDeviceName().contains("700"))) {
            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_LEFT);
            int la = usbThermalPrinter.measureText(one);
            int lb = usbThermalPrinter.measureText(two);
            Log.d(" first String " + one, " Length  " + la);
            Log.d(" second String " + one, " Length  " + lb);

            if ((la + lb) > 384) {
                usbThermalPrinter.addString(one);
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_RIGHT);
                usbThermalPrinter.addString(two);
            } else {
                StringBuffer str = new StringBuffer();
                str.append(one);
                int terminateValue = 384 - (la + lb);
                for (int i = 0; i < terminateValue / 7; i++) {
                    str.append(" ");
                }
                str.append(two);
                usbThermalPrinter.addString(str.toString());
            }
        }
       /* if (true) {
            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_LEFT);
            if ((one.length() + two.length()) > 29) {
                usbThermalPrinter.addString(one);
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_RIGHT);
                usbThermalPrinter.addString(two);
            } else {
                StringBuffer str = new StringBuffer();
                str.append(one);
                int margin = 29 - two.length()-1;
                if(margin%11 == 1){
                    margin = margin - 2;
                }else if(margin%11 == 2){
                    margin = margin - 1;
                }else {
                    margin = 0;
                }
                if(PrintClass.isOn){
                    for (int i = one.length(); i < (29 - two.length()) * 4/3; i++) {
                        str.append(" ");
                    }
                }else{
                    for (int i = one.length(); i < (29 - two.length()) -2; i++) {
                        str.append(" ");
                    }
                }

                str.append(two);
                usbThermalPrinter.addString(str.toString());
            }
        }  else {
            usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
            if ((one.length() + two.length()) > 32) {
                usbThermalPrinter.addString(one);
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_RIGHT);
                usbThermalPrinter.addString(two);
            } else {
                StringBuffer str = new StringBuffer();
                str.append(one);
                for (int i = one.length(); i < (32 - two.length()); i++) {
                    str.append(" ");
                }
                str.append(two);
                usbThermalPrinter.addString(str.toString());
            }
        }*/
    }


    public  static void printHeadingSports(String gameName) throws TelpoException {
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        usbThermalPrinter.setFontSize(3);
        usbThermalPrinter.setBold(true);
        usbThermalPrinter.addString(gameName);

    }
    public static void printBetMarketName580(String name) throws TelpoException {
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
        StringBuffer str = new StringBuffer();
        if (name.length() <= 20) {
            for (int i = 0; i < 10; i++) {
                str.append(" ");
            }
            str.append(name + "\n");
            usbThermalPrinter.addString(str.toString());
        } else {
            for (int i = 0; i < 30 - name.length(); i++) {
                str.append(" ");
            }
            str.append(name + "\n");
            usbThermalPrinter.addString(str.toString());
        }
    }

    public static void printOddStake(String one, String two, String three) throws TelpoException {
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            str.append(" ");
        }
//        if (one.length() == 1) {
//            str.append(" ");
//        }
        str.append(one);

        for (int i = 0; i < (6 - one.length()); i++) {
            str.append(" ");
        }
        if (two.length() == 1) {
            str.append("  ");
        } else {
            str.append(" ");
        }
        str.append(two);

        for (int i = 0; i < (8 - two.length()); i++) {
            str.append(" ");
        }
        str.append(three + "\n");
        usbThermalPrinter.addString(str.toString());

    }


    public static String printThreeString(String one, String two, String three) {
        StringBuffer str = new StringBuffer();
        int i;
        for (i = 0; i < 26; i++) {
            str.append(" ");
        }
//        if (one.length() == 1)
//            str.append(" ");
        str.append(one);

        for (i = 0; i < (6 - one.length()); i++) {
            str.append(" ");
        }
        if (two.length() == 1) {
            str.append("  ");
        } else {
            str.append(" ");
        }
        str.append(two);
        for (i = 0; i < (8 - two.length()); i++) {
            str.append(" ");
        }
        str.append(three + "\n");
        return str.toString();

    }

    public static String printBetMarketNameAzt(String name) {
        StringBuffer str = new StringBuffer();
        if (name.length() <= 20) {
            for (int i = 0; i < 26; i++) {
                str.append(" ");
            }
            str.append(name);
            return str.toString();
        } else {
            for (int i = 0; i < 47 - name.length(); i++) {
                str.append(" ");
            }
            str.append(name);
            return str.toString();
        }
    }



    public static String printTwoString(String one, String two, String s) {

        StringBuffer str = new StringBuffer();
        str.append(one);
        for (int i = one.length(); i < (48 - two.length()); i++) {
            str.append(" ");
        }
        str.append(two + "\n");
        return str.toString();

    }

    public static String printInMiddle(String one) {

        StringBuffer str = new StringBuffer();
        int totalCount = (48 - one.length()) / 2;
//        str.append(one);
        for (int i = 0; i < totalCount; i++) {
            str.append(" ");
        }
        str.append(one + "\n");
        return str.toString();

    }

    public static String printInRight(String one) {

        StringBuffer str = new StringBuffer();
        int totalCount = 48;
//        str.append(one);
        for (int i = one.length(); i < totalCount; i++) {
            str.append(" ");
        }
        str.append(one + "\n");
        return str.toString();

    }





    public static void printNum(String num) throws TelpoException {

        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        usbThermalPrinter.setFontSize(2);
        if (num.length() > 32) {
            if (num.charAt(31) >= '0' && num.charAt(31) <= '9') {
                if (num.charAt(32) >= '0' && num.charAt(32) <= '9') {
                    num = num.substring(0, 31) + "\n" + num.substring(31);
                }
            }
        }
        usbThermalPrinter.addString(num + "\n\n");
    }

    public static void printDrawGameDetails(String drawGameName, String drawDate, String drawTime) throws TelpoException {
        if (drawGameName != null && !drawGameName.equalsIgnoreCase("null"))
            printTitle(drawGameName);
        printTwoString(drawDate, drawTime);
    }

    public static void printBetTypeDetails(String betType, int QP, String num, String noofLines, String unitPrice, String panelPrice, String currencyName) throws TelpoException {
        if (betType == null || betType.equalsIgnoreCase("null")) {
            betType = "null";
        }
        printTitleWithQP(betType, QP);
        String[] gameNumCol = num.split(",");
        printNum(gameNumCol[0] + "-" + gameNumCol[1] + "-" + gameNumCol[2]);
        if (gameNumCol.length > 3) {
            printNum(gameNumCol[3] + "-" + gameNumCol[4] + "-" + gameNumCol[5]);
        }
//        printTwoString("No. of Line(s)", noofLines);
//        printTwoString("Unit Price(" + currencyName + ")", unitPrice);
        printTwoString("Amount(" + currencyName + ")", panelPrice);
    }

    public static void printTotalAmount(int noofDraw, String totalAmount, String currencyName) throws TelpoException {
        PrintClass.printTwoString("No. of Draws", noofDraw + "");
        PrintClass.printTwoString("Total Amount(" + currencyName + ")", totalAmount);
        PrintClass.printLine();
    }

    public static void printTicketValidity(String days) throws TelpoException {
        PrintClass.printNum("Ticket Validity: " + days);
        PrintClass.printLine();
    }

    public static void printBarcode(String barcode) throws TelpoException {
        if (barcode != null && barcode.length() != 0 && barcode.length() <= 20 && barcode.length() >= 14) {
            Bitmap bitmap = CreateCode(barcode, BarcodeFormat.CODE_128, 384, 64);
            usbThermalPrinter.printLogo(bitmap, false);
        } else {
            throw new NoPaperException();
        }
    }



    public static Bitmap createBarCode(String barcode) throws TelpoException {
        Bitmap bitmap = null;
        if (barcode != null && barcode.length() != 0 && barcode.length() <= 20 && barcode.length() >= 17) {
            bitmap = CreateCode(barcode, BarcodeFormat.CODE_128, 384, 64);
//            ThermalPrinter.printLogo(bitmap,ThermalPrinter.ALGIN_MIDDLE);
        } else {
            throw new IllegalArgumentException();
        }
        return bitmap;
    }

    public static void PrintBarcodeImage(Bitmap bitmap) throws TelpoException {
        ThermalPrinter.printLogo(bitmap, ThermalPrinter.ALGIN_MIDDLE);
    }

    public static Bitmap CreateCodeNew(String str, BarcodeFormat type, int bmpWidth, int bmpHeight) throws WriterException {
        // 生成二维矩阵,编码时要指定大小,不要生成了图片以后再进行缩放,以防模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, type, bmpWidth, bmpHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组（一直横着排）
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap CreateCode(String str, BarcodeFormat type, int bmpWidth, int bmpHeight) throws InternalErrorException {
        BitMatrix matrix = null;

        try {
            matrix = (new MultiFormatWriter()).encode(str, type, bmpWidth, bmpHeight);
        } catch (WriterException var10) {
            var10.printStackTrace();
            throw new InternalErrorException("Failed to encode bitmap");
        }

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int bitmap = 0; bitmap < height; ++bitmap) {
            for (int x = 0; x < width; ++x) {
                if (matrix.get(x, bitmap)) {
                    pixels[bitmap * width + x] = -16777216;
                } else {
                    pixels[bitmap * width + x] = -1;
                }
            }
        }

        Bitmap var11 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        var11.setPixels(pixels, 0, width, 0, 0, width, height);
        return var11;
    }

    public static final int STATUS_OK = 0;
    private static final int MODE_PIC = 1;

    private static Bitmap CreateCodeNewApi(String str, BarcodeFormat type, int bmpWidth, int bmpHeight) throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(str, type, bmpWidth, bmpHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[(width * height)];
        for (int y = STATUS_OK; y < height; y += MODE_PIC) {
            for (int x = STATUS_OK; x < width; x += MODE_PIC) {
                if (matrix.get(x, y)) {
                    pixels[(y * width) + x] = -16777216;
                } else {
                    pixels[(y * width) + x] = -1;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, STATUS_OK, width, STATUS_OK, STATUS_OK, width, height);
        return bitmap;
    }

    public static void printQrcode(String str) throws TelpoException, WriterException {
        //       Bitmap bitmap= BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/time1.bmp"));
        Bitmap bitmap = CreateCodeNew(str, BarcodeFormat.QR_CODE, 128, 128);
        if (bitmap != null) {
            Log.v("Thermal Printer", "Find the Bmp");
            ThermalPrinter.printLogo(bitmap);
        }
    }

    public static void printQrcodeNew(String str) throws Exception {
        Bitmap bitmap = CreateCodeNew(str, BarcodeFormat.QR_CODE, 256, 256);
        if (bitmap != null) {
            Log.v("", "Find the Bmp");
            ThermalPrinter.printLogo(bitmap);
        }
    }






}
