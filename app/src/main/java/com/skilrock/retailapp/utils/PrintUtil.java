package com.skilrock.retailapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.CancelPrintError;
import com.skilrock.retailapp.interfaces.NoPaper;
import com.skilrock.retailapp.models.SbsReprintResponseBean;
import com.skilrock.retailapp.models.SbsWinPayResponse;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.ResultResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelResponseBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayResponseBean;
import com.skilrock.retailapp.models.scratch.ClaimTicketResponseNewBean;
import com.skilrock.retailapp.models.scratch.OlaRegistrationPrintBean;
import com.skilrock.retailapp.sbs.ui.PrintActivitySbs;
import com.skilrock.retailapp.sle_game_portrait.PrintActivityResult;
import com.skilrock.retailapp.sle_game_portrait.SbsPurchaseBean;
import com.skilrock.retailapp.utils.printer.AidlUtil;
import com.skilrock.retailapp.utils.printer.ESCUtil;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.printer.NoPaperException;
import com.telpo.tps550.api.printer.ThermalPrinter;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.io.UnsupportedEncodingException;
import java.util.EnumMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;
import static com.skilrock.retailapp.utils.Utils.getDecimalPlacesSbs;
import static com.skilrock.retailapp.utils.Utils.scaleBitmapAndKeepRation;


public class PrintUtil {

    public static final byte ESC = 27;// Escape
    private static Context context;
    private static NoPaper noPaper;
    public static String LAST_GAME_CODE = "lastGameCode";
    public static final byte FS = 28;// Text delimiter
    private static double total_amount = 0.0;
    private static double win_reprint_total_amount = 0.0;
    private static String Amount = "";
    static String no_paper;
    private final int NOPAPER = 3;
    private final int LOWBATTERY = 4;
    private final int PRINTVERSION = 5;
    private final int PRINTCONTENT = 9;
    private static String Result;
    public static final byte GS = 29;// Group separator
    public static final byte DLE = 16;// data link escape
    public static final byte EOT = 4;// End of transmission
    public static final byte ENQ = 5;// Enquiry character
    public static final byte SP = 32;// Spaces
    public static final byte HT = 9;// Horizontal list
    public static final byte LF = 10;//Print and wrap (horizontal orientation)
    public static final byte CR = 13;// Home key
    public static final byte FF = 12;// Carriage control (print and return to the standard mode (in page mode))
    public static final byte CAN = 24;// Canceled (cancel print data in page mode)
    static int count;
    private static boolean reprint_data = false;
    static String[] sunSin = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    private static String dotFormatter;
    private static String amountFormatter;
    private static int decimalDegits = 0;
    private static String Pannel_Price = "";


    // ------------------------Initialize the printer-----------------------------
    static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    /**
     * Initialize the printer
     *
     * @return
     */
    public static byte[] init_printer() {
        byte[] result = new byte[2];
        result[0] = ESC;
        result[1] = 64;
        return result;
    }
    // ------------------------Wrap-----------------------------

    public static String getPrintDateFormatDraws(String data) {
        String[] date = data.split("[-]");
        return months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[0];
    }

    /**
     * Wrap
     *
     * @param lineNum how many line do you want wrap
     * @return
     */
    public static byte[] nextLine(int lineNum) {
        byte[] result = new byte[lineNum];
        for (int i = 0; i < lineNum; i++) {
            result[i] = LF;
        }

        return result;
    }

    // ------------------------underline-----------------------------

    /**
     * draw a underline（1 pixel width）
     *
     * @return
     */
    public static byte[] underlineWithOneDotWidthOn() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 45;
        result[2] = 1;
        return result;
    }

    /**
     * draw a underline（2 pixel width）
     *
     * @return
     */
    public static byte[] underlineWithTwoDotWidthOn() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 45;
        result[2] = 2;
        return result;
    }

    /**
     * cancel draw a underline
     *
     * @return
     */
    public static byte[] underlineOff() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 45;
        result[2] = 0;
        return result;
    }

    // ------------------------bold-----------------------------

    /**
     * select bold option
     *
     * @return
     */
    public static byte[] boldOn() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0xF;
        return result;
    }


    /**
     * cancel bold option
     *
     * @return
     */
    public static byte[] boldOff() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0;
        return result;
    }

    // ------------------------Align-----------------------------

    /**
     * Align left
     *
     * @return
     */
    public static byte[] alignLeft() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 97;
        result[2] = 0;
        return result;
    }

    /**
     * 居中对齐
     *
     * @return
     */
    public static byte[] alignCenter() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 97;
        result[2] = 1;
        return result;
    }

    /**
     * Align right
     *
     * @return
     */
    public static byte[] alignRight() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 97;
        result[2] = 2;
        return result;
    }

    /**
     * Horizontal move col columns to the right
     *
     * @param col
     * @return
     */
    public static byte[] set_HT_position(byte col) {
        byte[] result = new byte[4];
        result[0] = ESC;
        result[1] = 68;
        result[2] = col;
        result[3] = 0;
        return result;
    }
    // ------------------------Font bigger-----------------------------

    /**
     * Font bigger 5 times than normal
     *
     * @param num
     * @return
     */
    public static byte[] fontSizeSetBig(int num) {
        byte realSize = 0;
        switch (num) {
            case 1:
                realSize = 0;
                break;
            case 2:
                realSize = 17;
                break;
            case 3:
                realSize = 34;
                break;
            case 4:
                realSize = 51;
                break;
            case 5:
                realSize = 68;
                break;
            case 6:
                realSize = 85;
                break;
            case 7:
                realSize = 102;
                break;
            case 8:
                realSize = 119;
                break;

        }
        byte[] result = new byte[3];
        result[0] = 29;
        result[1] = 33;
        result[2] = realSize;
        return result;
    }

    public static byte[] alignHeadingSunmi(byte[] data) {
        byte[] center = PrintUtil.alignCenter();
        byte[] fontSize = fontSizeSetBig(1);
        byte[][] cmdBytes = {fontSize, data, center};

        return PrintUtil.byteMerger(cmdBytes);
    }

    public static byte[] fontSizeSetBig(int num, byte font_size) {
        byte realSize = 0;
        byte font = font_size;
        switch (num) {
            case 1:
                realSize = 0;
                break;
            case 2:
                realSize = 17;
                break;
            case 3:
                realSize = 34;
                break;
            case 4:
                realSize = 51;
                break;
            case 5:
                realSize = 68;
                break;
            case 6:
                realSize = 85;
                break;
            case 7:
                realSize = 102;
                break;
            case 8:
                realSize = 119;
                break;

            case 9:
                realSize = 13;
                break;
        }
        byte[] result = new byte[3];
        result[0] = font;
        result[1] = 33;
        result[2] = realSize;
        return result;
    }

    // ------------------------Font smaller-----------------------------

    /**
     * font smaller
     *
     * @param num
     * @return
     */
    public static byte[] fontSizeSetSmall(int num) {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 33;

        return result;
    }

    // ------------------------Paper cutting-----------------------------

    /**
     * Paper cutting
     *
     * @return
     */
    public static byte[] feedPaperCutAll() {
        byte[] result = new byte[4];
        result[0] = GS;
        result[1] = 86;
        result[2] = 65;
        result[3] = 0;
        return result;
    }

    /**
     * Paper cutting（the left leave some）
     *
     * @return
     */
    public static byte[] feedPaperCutPartial() {
        byte[] result = new byte[4];
        result[0] = GS;
        result[1] = 86;
        result[2] = 66;
        result[3] = 0;
        return result;
    }

    // ------------------------Cutting paper-----------------------------
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    public static byte[] byteMerger(byte[][] byteList) {

        int length = 0;
        for (int i = 0; i < byteList.length; i++) {
            length += byteList[i].length;
        }
        byte[] result = new byte[length];

        int index = 0;
        for (int i = 0; i < byteList.length; i++) {
            byte[] nowByte = byteList[i];
            for (int k = 0; k < byteList[i].length; k++) {
                result[index] = nowByte[k];
                index++;
            }
        }
        for (int i = 0; i < index; i++) {
            // CommonUtils.LogWuwei("", "result[" + i + "] is " + result[i]);
        }
        return result;
    }


    public static Bitmap getBitmap(String barcode, int barcodeType, int width, int height) {
        Bitmap barcodeBitmap = null;
        BarcodeFormat barcodeFormat = convertToZXingFormat(barcodeType);
        try {
            barcodeBitmap = encodeAsBitmap(barcode, barcodeFormat, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return barcodeBitmap;
    }

    private static BarcodeFormat convertToZXingFormat(int format) {
        switch (format) {
            case 8:
                return BarcodeFormat.CODABAR;
            case 1:
                return BarcodeFormat.CODE_128;
            case 2:
                return BarcodeFormat.CODE_39;
            case 4:
                return BarcodeFormat.QR_CODE;
            case 32:
                return BarcodeFormat.EAN_13;
            case 64:
                return BarcodeFormat.EAN_8;
            case 128:
                return BarcodeFormat.ITF;
            case 512:
                return BarcodeFormat.UPC_A;
            case 1024:
                return BarcodeFormat.UPC_E;
            //default 128?
            default:
                return BarcodeFormat.CODE_128;
        }
    }

    private static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        if (contents == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contents);
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contents, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    public static String getPrintDateFormat(String data) {
        String[] date = data.split("[-]");
        return months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[0] + ", " + date[2];
    }
    public static String getPrintDateFormatResult(String data) {
        String[] date = data.split("[-]");
        return months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[0] ;
    }

    public static String getPrintDateFormatWinning(String data) {
        String[] date = data.split("[-]");
        return months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[2] + "," + date[0];
    }

    public static int getCount() {
        return 32;
    }



    public static int getCount1() {
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            return 32;
        } else if(Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI)) {
            return 32;
        }else {
            return 27;
        }
    }

    static String getDashLine() {
        String dash = "";
        for (int i = 0; i < getCount(); i++) {
            dash = dash + "_";
        }
        dash = dash + "\n";
        return dash;
    }

    public static byte[] alignCenterSunmi(byte[] data) {
        byte[] center = PrintUtil.alignCenter();
        byte[][] cmdBytes = {data, center};

        return PrintUtil.byteMerger(cmdBytes);
    }

    public static String printTwoStringStringData(String one, String two) throws UnsupportedEncodingException {
        StringBuffer str = new StringBuffer();
        if ((one.length() + two.length()) > getCount()) {
            str.append(one + "\n");
            str.append(two + "\n");
        } else {
            str.append(one);
            for (int i = one.length(); i < (getCount() - two.length()); i++) {
                str.append(" ");
            }
            str.append(two + "\n");
        }
        return str.toString();
    }


    public static String printTwoStringResult(String one, String two)  {
        StringBuffer str = new StringBuffer();
        if ((one.length() + two.length()) > 40) {
            str.append(one + "\n");
            str.append(two + "\n");
        } else {
            str.append(one);
            for (int i = one.length(); i < (40 - two.length()); i++) {
                str.append(" ");
            }
            str.append(two + "\n");
        }
        return str.toString();
    }

    public static String printTwoStringStringData(String one, String two, String three) throws UnsupportedEncodingException {
        StringBuffer str = new StringBuffer();
        if ((one.length() + two.length() + three.length()) > getCount()) {
            str.append(one + "\n");
            str.append(two + "\n");
            str.append(three + "\n");

        } else {
            str.append(one);
            for (int i = 0; i < two.length(); i++) {
                str.append(" ");
            }
            str.append(two);
            for (int j = 0; j < two.length(); j++) {
                str.append(" ");
            }
            str.append(three + "\n");
        }
        return str.toString();
    }

    public static String printStringAlignLeft(String one) throws UnsupportedEncodingException {
        StringBuffer str = new StringBuffer();
        str.append(one + "\n");

        return str.toString();
    }



    public static String printTwoStringWinningError(String one, String two) {
        StringBuffer str = new StringBuffer();
        if ((one.length() + two.length()) > 20) {
            str.append(one + "\n");
            str.append(two + "\n");
        } else {
            str.append(one);
            for (int i = one.length(); i < (20 - two.length()); i++) {
                str.append(" ");
            }
            str.append(two + "\n");
        }
        return str.toString();
    }

    static String getdash() {
        StringBuilder stringBuilderDarkline = new StringBuilder();
        for (int i = 0; i < getCount1(); i++) {
            stringBuilderDarkline.append("_");
        }
        return stringBuilderDarkline.toString();
    }

    static String getLine() {
        StringBuilder stringBuilderDash = new StringBuilder();
        for (int i = 0; i < getCount1(); i++) {
            stringBuilderDash.append("-");
        }
        return stringBuilderDash.toString();
    }

    private static void printHeading(String gameName, UsbThermalPrinter usbThermalPrinter) throws TelpoException {
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        usbThermalPrinter.setFontSize(3);
        usbThermalPrinter.setBold(true);
        usbThermalPrinter.addString(gameName);
    }

    public static byte[] getStringByte(String byteString) throws UnsupportedEncodingException {
        //return byteString.getBytes("gb2312");
        return byteString.getBytes("gb18030");
    }

    public static byte[] printTwoStringSumni(String one, String two) throws UnsupportedEncodingException {
        StringBuffer str = new StringBuffer();

        if ((one.length() + two.length()) > getCount()) {
            str.append(one + "\n");
            str.append(two + "\n");
        } else {

            str.append(one);
            for (int i = one.length(); i < (getCount() - two.length()); i++) {
                str.append(" ");
            }
            str.append(two + "\n");
        }
        return getStringByte(str.toString());
    }

    public static String printTwoStringStringDataSunmi(String one, String two) throws UnsupportedEncodingException {
        StringBuffer str = new StringBuffer();

        if ((one.length() + two.length()) > getCount()) {
            str.append(one + "\n");
            str.append(two + "\n");
        } else {

            str.append(one);
            for (int i = one.length(); i < (getCount() - two.length()); i++) {
                str.append(" ");
            }
            str.append(two + "\n");
        }
        return str.toString();
    }

    public static String printMiddle(String string) {
        StringBuffer str = new StringBuffer();
        int length;

        if (string.length() <= getCount()) {
            length = (getCount() - string.length()) / 14;

            for (int i = 0; i < (length); i++) {
                str.append(" ");
            }
            str.append(string + "\n");
        } else {
            str.append(string + "\n");
        }
        return str.toString();
    }

    public static Bitmap CreateCode(String str, BarcodeFormat type, int bmpWidth, int bmpHeight) {
        BitMatrix matrix = null;

        try {
            matrix = (new MultiFormatWriter()).encode(str, type, bmpWidth, bmpHeight);
        } catch (WriterException var10) {
            var10.printStackTrace();
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


     // DGE Print Module

    public static byte[] getPrintDataDgeSale(FiveByNinetySaleResponseBean gameSale, String responseTime, Context context, NoPaper listner) {
        try {
            byte[] next2Line = PrintUtil.nextLine(2);
            byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
            byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
            byte[] fontSize2Big = PrintUtil.fontSizeSetBig(2);
            byte[] boldOff = PrintUtil.boldOff();
            byte[] top_msg = new byte[0];
            byte[] bottom_msg = new byte[0];
            byte[] sample__ticket_msg = new byte[0];

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
            dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context));
            amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context));
            total_amount = 0.0;
            Amount = "";
            Pannel_Price = "";
            String top_message = "";
            String bottom_message = "";
            byte[] getDash = getStringByte(stingDash.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                sample__ticket_msg = alignHeadingSunmi(getStringByte(context.getString(R.string.ticket_not_for_sale)));
            }
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            if (gameSale.getResponseData().getAdvMessages() != null) {
                if (gameSale.getResponseData().getAdvMessages().getTop() != null && gameSale.getResponseData().getAdvMessages().getTop().size() > 0) {
                    for (int i = 0; i < gameSale.getResponseData().getAdvMessages().getTop().size(); i++) {
                        top_message = top_message + printMiddle(gameSale.getResponseData().getAdvMessages().getTop().get(i).getMsgText());
                    }
                }
                if (gameSale.getResponseData().getAdvMessages().getBottom() != null && gameSale.getResponseData().getAdvMessages().getBottom().size() > 0) {
                    for (int j = 0; j < gameSale.getResponseData().getAdvMessages().getBottom().size(); j++) {
                        bottom_message = bottom_message + printMiddle(gameSale.getResponseData().getAdvMessages().getBottom().get(j).getMsgText());
                    }
                }
                bottom_msg = alignHeadingSunmi(getStringByte(bottom_message));
                top_msg = alignHeadingSunmi(getStringByte(top_message));
            }
            byte[] game_name = alignHeadingSunmi(getStringByte(gameSale.getResponseData().getGameName().toUpperCase()));
            byte[] purchaseTime = alignCenterSunmi(getStringByte(context.getString(R.string.purchase_time)));
            String purchase_date = gameSale.getResponseData().getPurchaseTime().split(" ")[0];
            String purchase_time = gameSale.getResponseData().getPurchaseTime().split(" ")[1];
            String draw_date_time = getPrintDateFormat(purchase_date) + "  " + purchase_time;
            byte purchase_time_print[] = alignCenterSunmi(getStringByte(draw_date_time));
            byte[] ticketNumber_txt = alignCenterSunmi(getStringByte(context.getString(R.string.ticket_number)));
            byte[] ticket_number = alignCenterSunmi(getStringByte(appnedDash(gameSale.getResponseData().getTicketNumber(), 4)));
            byte bet_details_txt[] = alignCenterSunmi(getStringByte(context.getString(R.string.bet_details)));
            String datetToPrint = "";
            byte[] draw_text = alignCenterSunmi(getStringByte(context.getString(R.string.draw_timing)));
            for (int i = 0; i < gameSale.getResponseData().getDrawData().size(); i++) {
                datetToPrint = datetToPrint + printMiddle(getPrintDateFormat(gameSale.getResponseData().getDrawData().get(i).getDrawDate()) + "  " + gameSale.getResponseData().getDrawData().get(i).getDrawTime());
            }
            byte[] drawDateAndTime = getStringByte(datetToPrint);
            String restOfData = "";
            for (int i = 0; i < gameSale.getResponseData().getPanelData().size(); i++) {
                String numberString = "";
                numberString = "";
                Pannel_Price = Utils.getBalanceToView(gameSale.getResponseData().getPanelData().get(i).getUnitCost() * gameSale.getResponseData().getPanelData().get(i).getBetAmountMultiple() * gameSale.getResponseData().getPanelData().get(i).getNumberOfLines(), dotFormatter, amountFormatter, decimalDegits);
                String isQp = gameSale.getResponseData().getPanelData().get(i).getQuickPick() == true ? "/QP" : "  ";
                if (gameSale.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_NUMBER)) {
                    if (gameSale.getResponseData().getPanelData().get(i).getPickType().equalsIgnoreCase(AppConstants.BANKER)) {
                        numberString = gameSale.getResponseData().getPanelData().get(i).getPickedValues();
                        String banker[] = numberString.split("-");
                        restOfData = restOfData + printMiddle("UL- " + banker[0]);
                        restOfData = restOfData + printMiddle("LL- " + banker[1]);
                        if (gameSale.getResponseData().getPanelData().get(i).getQuickPick()) {
                            restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        } else {
                            restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + gameSale.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        }

                        restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_lines), "" + gameSale.getResponseData().getPanelData().get(i).getNumberOfLines());
                    } else {
                        numberString = gameSale.getResponseData().getPanelData().get(i).getPickedValues();
                        restOfData = restOfData + printMiddle(numberString);
                        if (gameSale.getResponseData().getPanelData().get(i).getQuickPick()) {
                            restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        } else {
                            restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + gameSale.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        }
                        restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_lines), "" + gameSale.getResponseData().getPanelData().get(i).getNumberOfLines());
                    }
                } else if (gameSale.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_MARKET)) {
                    numberString = gameSale.getResponseData().getPanelData().get(i).getBetDisplayName();
                    restOfData = restOfData + printMiddle(numberString);
                    restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getPickDisplayName() + "", Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                    restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_lines), "" + gameSale.getResponseData().getPanelData().get(i).getNumberOfLines());
                }
                total_amount += gameSale.getResponseData().getPanelData().get(i).getUnitCost() * gameSale.getResponseData().getPanelData().get(i).getBetAmountMultiple() * gameSale.getResponseData().getPanelData().get(i).getNumberOfLines();
                if (gameSale.getResponseData().getPanelData().size() == i + 1) {
                    restOfData = restOfData + builder.toString();
                } else {
                    restOfData = restOfData + stingDash.toString();
                }
            }
            Amount = Utils.getBalanceToView(total_amount, ",", " ", 0);
            restOfData = restOfData + printTwoStringStringData(context.getString(R.string.amount), Amount + "");
            restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_draws), gameSale.getResponseData().getDrawData().size() + "");
            byte[] restOfDataByte = getStringByte(restOfData);
            String Amount_Total = Utils.getBalanceToView(gameSale.getResponseData().getTotalPurchaseAmount(), dotFormatter, amountFormatter, decimalDegits);
            byte[] total_amount = printTwoStringSumni(context.getString(R.string.total_amount).toUpperCase(), Amount_Total + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
            String barcode = gameSale.getResponseData().getTicketNumber();
            byte[] barcode_print = ESCUtil.printBitmap(getBitmap(barcode, 1, 384, 70));
            byte[] ticket_no = getStringByte(printMiddle(appnedDash(gameSale.getResponseData().getTicketNumber(), 4)));
            byte[] retailer_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
            byte[] ticket_validity = getStringByte(printMiddle(context.getString(R.string.ticket_validity) + gameSale.getResponseData().getTicketExpiry()));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), boldOn, fontSize2Big, top_msg, boldOff, ESCUtil.alignCenter(), next1Line, logo, boldOn, sample__ticket_msg, boldOff, next1Line, boldOn, game_name, boldOff, next1Line, boldOff, fontSize1Big, purchaseTime, next1Line, purchase_time_print, fontSize2Small, nextline, ticketNumber_txt, next1Line, ticket_number, next1Line, line, draw_text, next1Line, drawDateAndTime, line, bet_details_txt, next1Line, restOfDataByte, boldOn, total_amount, boldOff, next1Line, barcode_print, ticket_no, next1Line, retailer_name, ticket_validity, line, fontSize2Big, boldOn, bottom_msg, fontSize1Big, boldOff, next2Line, breakPartial};
            listner.noPaper("SUCCESS");
            saveTicket(gameSale, context);
            return PrintUtil.byteMerger(cmdBytes);

        } catch (UnsupportedEncodingException e) {

        }
        return null;
    }

    public static byte[] getPrintDataDgeReprint(FiveByNinetySaleResponseBean gameSale, String responseTime, Context context, NoPaper listner, boolean isReprint) {
        try {
            byte[] next2Line = PrintUtil.nextLine(2);
            byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
            byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
            byte[] boldOff = PrintUtil.boldOff();
            byte[] sample__ticket_msg = new byte[0];
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
            dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context));
            amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context));
            total_amount = 0.0;
            Amount = "";
            Pannel_Price = "";
            byte[] getDash = getStringByte(stingDash.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                sample__ticket_msg = alignHeadingSunmi(getStringByte(context.getString(R.string.ticket_not_for_sale)));
            }
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(gameSale.getResponseData().getGameName().toUpperCase()));
            byte[] reprint_txt = alignHeadingSunmi(getStringByte(context.getString(R.string.reprint)));
            byte[] purchaseTime = alignCenterSunmi(getStringByte(context.getString(R.string.purchase_time)));
            String purchase_date = gameSale.getResponseData().getPurchaseTime().split(" ")[0];
            String purchase_time = gameSale.getResponseData().getPurchaseTime().split(" ")[1];
            String draw_date_time = getPrintDateFormat(purchase_date) + "  " + purchase_time;
            byte purchase_time_print[] = alignCenterSunmi(getStringByte(draw_date_time));
            byte[] ticketNumber_txt = alignCenterSunmi(getStringByte(context.getString(R.string.ticket_number)));
            byte[] ticket_number = alignCenterSunmi(getStringByte(appnedDash(gameSale.getResponseData().getTicketNumber(), 4)));
            byte bet_details_txt[] = alignCenterSunmi(getStringByte(context.getString(R.string.bet_details)));
            String datetToPrint = "";
            byte[] draw_text = alignCenterSunmi(getStringByte(context.getString(R.string.draw_timing)));
            for (int i = 0; i < gameSale.getResponseData().getDrawData().size(); i++) {
                datetToPrint = datetToPrint + printMiddle(getPrintDateFormat(gameSale.getResponseData().getDrawData().get(i).getDrawDate()) + "  " + gameSale.getResponseData().getDrawData().get(i).getDrawTime());
            }
            byte[] drawDateAndTime = getStringByte(datetToPrint);
            String restOfData = "";
            for (int i = 0; i < gameSale.getResponseData().getPanelData().size(); i++) {
                String numberString = "";
                numberString = "";
                Pannel_Price = Utils.getBalanceToView(gameSale.getResponseData().getPanelData().get(i).getUnitCost() * gameSale.getResponseData().getPanelData().get(i).getBetAmountMultiple() * gameSale.getResponseData().getPanelData().get(i).getNumberOfLines(), dotFormatter, amountFormatter, decimalDegits);
                String isQp = gameSale.getResponseData().getPanelData().get(i).getQuickPick() == true ? "/QP" : "  ";
                if (gameSale.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_NUMBER)) {
                    if (gameSale.getResponseData().getPanelData().get(i).getPickType().equalsIgnoreCase(AppConstants.BANKER)) {
                        numberString = gameSale.getResponseData().getPanelData().get(i).getPickedValues();
                        String banker[] = numberString.split("-");
                        restOfData = restOfData + printMiddle("UL- " + banker[0]);
                        restOfData = restOfData + printMiddle("LL- " + banker[1]);
                        if (gameSale.getResponseData().getPanelData().get(i).getQuickPick()) {
                            restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        } else {
                            restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + gameSale.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        }
                        restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_lines), "" + gameSale.getResponseData().getPanelData().get(i).getNumberOfLines());
                    } else {
                        numberString = gameSale.getResponseData().getPanelData().get(i).getPickedValues();
                        restOfData = restOfData + printMiddle(numberString);
                        if (gameSale.getResponseData().getPanelData().get(i).getQuickPick()) {
                            restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        } else {
                            restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + gameSale.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        }
                        restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_lines), "" + gameSale.getResponseData().getPanelData().get(i).getNumberOfLines());
                    }
                } else if (gameSale.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_MARKET)) {
                    numberString = gameSale.getResponseData().getPanelData().get(i).getBetDisplayName();
                    restOfData = restOfData + printMiddle(numberString);
                    restOfData = restOfData + printTwoStringStringData(gameSale.getResponseData().getPanelData().get(i).getPickDisplayName() + "", Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                    restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_lines), "" + gameSale.getResponseData().getPanelData().get(i).getNumberOfLines());
                }
                total_amount += gameSale.getResponseData().getPanelData().get(i).getUnitCost() * gameSale.getResponseData().getPanelData().get(i).getBetAmountMultiple() * gameSale.getResponseData().getPanelData().get(i).getNumberOfLines();
                if (gameSale.getResponseData().getPanelData().size() == i + 1) {
                    restOfData = restOfData + builder.toString();
                } else {
                    restOfData = restOfData + stingDash.toString();
                }
            }
            Amount = Utils.getBalanceToView(total_amount, ",", " ", 0);
            restOfData = restOfData + printTwoStringStringData(context.getString(R.string.amount), Amount + "");
            restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_draws), gameSale.getResponseData().getDrawData().size() + "");
            byte[] restOfDataByte = getStringByte(restOfData);
            String Amount_Total = Utils.getBalanceToView(gameSale.getResponseData().getTotalPurchaseAmount(), dotFormatter, amountFormatter, decimalDegits);
            byte[] total_amount = printTwoStringSumni(context.getString(R.string.total_amount).toUpperCase(), Amount_Total + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
            String barcode = gameSale.getResponseData().getTicketNumber();
            byte[] barcode_print = ESCUtil.printBitmap(getBitmap(barcode, 1, 384, 70));
            byte[] ticket_no = getStringByte(printMiddle(appnedDash(gameSale.getResponseData().getTicketNumber(), 4)));
            byte[] retailer_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
            byte[] ticket_validity = getStringByte(printMiddle(context.getString(R.string.ticket_validity) + gameSale.getResponseData().getTicketExpiry()));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, boldOn, sample__ticket_msg, boldOff, next1Line, boldOn, game_name, next1Line, reprint_txt, next1Line, boldOff, fontSize1Big, purchaseTime, next1Line, purchase_time_print, fontSize2Small, nextline, ticketNumber_txt, next1Line, ticket_number, next1Line, line, draw_text, next1Line, drawDateAndTime, line, bet_details_txt, next1Line, restOfDataByte, boldOn, total_amount, boldOff, next1Line, barcode_print, ticket_no, next1Line, retailer_name, next1Line, ticket_validity, line, next2Line, breakPartial};
            saveTicket(gameSale, context);
            return PrintUtil.byteMerger(cmdBytes);

        } catch (UnsupportedEncodingException e) {

        }
        return null;
    }

    public static void getPrintDataTelpo570(FiveByNinetySaleResponseBean bean, Context context, UsbThermalPrinter usbThermalPrinter, boolean reprint, NoPaper listener) {
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
                    if (bean != null) {
                        if (bean.getResponseData().getAdvMessages() != null) {
                            if (bean.getResponseData().getAdvMessages() != null && bean.getResponseData().getAdvMessages().getTop().size() > 0) {
                                for (int i = 0; i < bean.getResponseData().getAdvMessages().getTop().size(); i++) {
                                    printMiddleTelpo(bean.getResponseData().getAdvMessages().getTop().get(i).getMsgText(), usbThermalPrinter);
                                }
                                usbThermalPrinter.addString("                      ");
                            }

                        }
                        if (ConfigData.getInstance().getConfigData() != null && ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE() != null)
                            if (ConfigData.getInstance().getConfigData() != null && ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE() != null)
                                decimalDegits = Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE());
                        dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context));
                        amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context));
                        total_amount = 0.0;
                        Amount = "";
                        Pannel_Price = "";
                        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                        if (bitmap1 != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            usbThermalPrinter.printLogo(bitmap1, false);
                            usbThermalPrinter.addString("                      ");
                            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                                usbThermalPrinter.setBold(true);
                                printMiddleTelpo(context.getString(R.string.ticket_not_for_sale), usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            }

                        }
                        printHeading(bean.getResponseData().getGameName().toUpperCase(), usbThermalPrinter);
                        usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setBold(false);
                        if (reprint) {
                            usbThermalPrinter.setBold(true);
                            usbThermalPrinter.setFontSize(3);
                            printMiddleTelpo(context.getString(R.string.reprint), usbThermalPrinter);
                            usbThermalPrinter.setFontSize(2);
                            usbThermalPrinter.setBold(false);
                        }
                        String draw_date = bean.getResponseData().getPurchaseTime().split(" ")[0];
                        String draw_time = bean.getResponseData().getPurchaseTime().split(" ")[1];
                        printMiddleTelpo(context.getString(R.string.purchase_time), usbThermalPrinter);
                        printMiddleTelpo(getPrintDateFormat(draw_date) + "  " + draw_time, usbThermalPrinter);
                        printMiddleTelpo(context.getString(R.string.ticket_number), usbThermalPrinter);
                        printMiddleTelpo(appnedDash(bean.getResponseData().getTicketNumber(), 4), usbThermalPrinter);
                        printDarkline("___________________________", usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        printMiddleTelpo(context.getString(R.string.draw_timing), usbThermalPrinter);
                        for (int i = 0; i < bean.getResponseData().getDrawData().size(); i++) {
                            printMiddleTelpo(getPrintDateFormat(bean.getResponseData().getDrawData().get(i).getDrawDate()) + "  " + bean.getResponseData().getDrawData().get(i).getDrawTime(), usbThermalPrinter);
                        }
                        printDarkline("___________________________", usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        usbThermalPrinter.setBold(true);
                        printMiddleTelpo(context.getString(R.string.bet_details), usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        usbThermalPrinter.printString();
                        for (int i = 0; i < bean.getResponseData().getPanelData().size(); i++) {
                            Pannel_Price = Utils.getBalanceToView(bean.getResponseData().getPanelData().get(i).getUnitCost() * bean.getResponseData().getPanelData().get(i).getBetAmountMultiple() * bean.getResponseData().getPanelData().get(i).getNumberOfLines(), dotFormatter, amountFormatter, decimalDegits);
                            if (bean.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_NUMBER)) {
                                if (bean.getResponseData().getPanelData().get(i).getPickType().equalsIgnoreCase(AppConstants.BANKER)) {
                                    String isQp = bean.getResponseData().getPanelData().get(i).getQuickPick() == true ? "/QP" : "";
                                    String numberString = "";
                                    numberString = bean.getResponseData().getPanelData().get(i).getPickedValues();
                                    String barnker[] = numberString.split("-");
                                    printMiddleTelpo("UL -" + barnker[0], usbThermalPrinter);
                                    printMiddleTelpo("LL -" + barnker[1], usbThermalPrinter);
                                    if (bean.getResponseData().getPanelData().get(i).getQuickPick()) {
                                        printTwoStringTelpo(bean.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                    } else {
                                        printTwoStringTelpo(bean.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + bean.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                    }
                                    printTwoStringTelpo(context.getString(R.string.no_of_lines), "" + bean.getResponseData().getPanelData().get(i).getNumberOfLines(), usbThermalPrinter);
                                } else {
                                    String isQp = bean.getResponseData().getPanelData().get(i).getQuickPick() == true ? "/QP" : "";
                                    String numberString = "";
                                    numberString = bean.getResponseData().getPanelData().get(i).getPickedValues();
                                    printMiddleTelpo(numberString, usbThermalPrinter);
                                    if (bean.getResponseData().getPanelData().get(i).getQuickPick()) {
                                        printTwoStringTelpo(bean.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                    } else {
                                        printTwoStringTelpo(bean.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + bean.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                    }
                                    printTwoStringTelpo(context.getString(R.string.no_of_lines), "" + bean.getResponseData().getPanelData().get(i).getNumberOfLines(), usbThermalPrinter);
                                }

                            } else if (bean.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_MARKET)) {
                                printMiddleTelpo(bean.getResponseData().getPanelData().get(i).getBetDisplayName(), usbThermalPrinter);
                                printTwoStringTelpo(bean.getResponseData().getPanelData().get(i).getPickDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                printTwoStringTelpo(context.getString(R.string.no_of_lines), "" + bean.getResponseData().getPanelData().get(i).getNumberOfLines(), usbThermalPrinter);
                            }
                            total_amount += bean.getResponseData().getPanelData().get(i).getUnitCost() * bean.getResponseData().getPanelData().get(i).getBetAmountMultiple() * bean.getResponseData().getPanelData().get(i).getNumberOfLines();
                            if (bean.getResponseData().getPanelData().size() == i + 1) {
                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }

                        }
                        Amount = Utils.getBalanceToView(total_amount, dotFormatter, amountFormatter, decimalDegits);
                        printTwoStringTelpo(context.getString(R.string.amount), "" + Amount, usbThermalPrinter);
                        printTwoStringTelpo(context.getString(R.string.no_of_draws), "" + bean.getResponseData().getDrawData().size(), usbThermalPrinter);
                        String Amount_Total = Utils.getBalanceToView(bean.getResponseData().getTotalPurchaseAmount(), dotFormatter, amountFormatter, decimalDegits);
                        usbThermalPrinter.setBold(true);
                        printTwoStringTelpo(context.getString(R.string.total_amount).toUpperCase(), Amount_Total + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        printBarcode(bean.getResponseData().getTicketNumber(), usbThermalPrinter);
                        printMiddleTelpo(appnedDash(bean.getResponseData().getTicketNumber(), 4), usbThermalPrinter);
                        usbThermalPrinter.addString("                              ");
                        printMiddleTelpo(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(), usbThermalPrinter);
                        usbThermalPrinter.addString("                              ");
                        printMiddleTelpo(context.getString(R.string.ticket_validity) + bean.getResponseData().getTicketExpiry(), usbThermalPrinter);
                        printDarkline("___________________________", usbThermalPrinter);
                        if (bean.getResponseData().getAdvMessages() != null && bean.getResponseData().getAdvMessages().getBottom().size() > 0) {
                            usbThermalPrinter.addString("                      ");
                            if (bean.getResponseData().getAdvMessages().getBottom() != null) {
                                for (int j = 0; j < bean.getResponseData().getAdvMessages().getBottom().size(); j++) {
                                    printMiddleTelpo(bean.getResponseData().getAdvMessages().getBottom().get(j).getMsgText(), usbThermalPrinter);
                                    usbThermalPrinter.printString();
                                }
                            }
                        }
                        usbThermalPrinter.setBold(false);
                        usbThermalPrinter.printString();
                        usbThermalPrinter.walkPaper(15);
                        usbThermalPrinter.addString("                 ");
                        saveTicket(bean, context);
                        listener.noPaper("SUCCESS");
                    } else {
                        Toast.makeText(context, context.getText(R.string.some_technical_issue), Toast.LENGTH_SHORT).show();
                    }

                } catch (TelpoException e) {
                    e.printStackTrace();
                    usbThermalPrinter.stop();
                    if (e instanceof NoPaperException) {
                        Log.v("no paper", "no paper found");
                    }
                    Result = e.toString();
                    if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listener.noPaper("No Paper");
                            }
                        });
                    }

                } finally {
                    try {
                        usbThermalPrinter.stop();

                    } catch (Exception e) {

                    }
                }
            }
        }).start();

    }

    public static void getPrintDataTelpoCancelTicket(TicketCancelResponseBean ticketCancelResponseBean, Context context, UsbThermalPrinter usbThermalPrinter, CancelPrintError listner) {
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
                    if (ticketCancelResponseBean != null) {
                        String Cancel_Amount = "";
                        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                        if (bitmap1 != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            usbThermalPrinter.printLogo(bitmap1, false);
                        }
                        printHeading(ticketCancelResponseBean.getResponseData().getGameName().toUpperCase(), usbThermalPrinter);
                        usbThermalPrinter.printString();
                        usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setBold(false);
                        printDarkline("___________________________", usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        printMiddleTelpo(context.getString(R.string.ticket_number), usbThermalPrinter);
                        printMiddleTelpo(appnedDash(ticketCancelResponseBean.getResponseData().getTicketNo(), 4), usbThermalPrinter);
                        usbThermalPrinter.setBold(true);
                        printMiddleTelpo(context.getString(R.string.ticket_cancelled), usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        Cancel_Amount = Utils.getBalanceToView(Double.parseDouble(ticketCancelResponseBean.getResponseData().getRefundAmount()), dotFormatter, amountFormatter, decimalDegits);
                        printTwoStringTelpo(context.getString(R.string.refund_amount), Cancel_Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                        printDarkline("___________________________", usbThermalPrinter);
                        printMiddleTelpo(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(), usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        usbThermalPrinter.printString();
                        usbThermalPrinter.walkPaper(10);
                        listner.cancelError("SUCCESS", true);
                    } else {
                        Toast.makeText(context, context.getText(R.string.some_technical_issue), Toast.LENGTH_SHORT).show();
                    }

                } catch (TelpoException e) {
                    e.printStackTrace();
                    if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listner.cancelError("No Paper", false);
                            }
                        });
                    }
                } finally {
                    try {
                        usbThermalPrinter.stop();
                    } catch (Exception e) {

                    }
                }
            }
        }).start();
    }


    public static byte[] getPrintDgeCancel(TicketCancelResponseBean bean, Context context, CancelPrintError listner) {
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
        StringBuilder stingDash = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            stingDash.append("-");
        }
        try {
            byte[] line = getStringByte(builder.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(bean.getResponseData().getGameName().toUpperCase()));
            byte[] ticket_number_txt = getStringByte(printMiddle(context.getString(R.string.ticket_number)));
            byte[] ticket_number = getStringByte(printMiddle(appnedDash(bean.getResponseData().getTicketNo(), 4)));
            byte[] ticket_cancelled_txt = getStringByte(printMiddle(context.getString(R.string.ticket_cancelled)));
            Cancel_Amount = Utils.getBalanceToView(Double.parseDouble(bean.getResponseData().getRefundAmount()), dotFormatter, amountFormatter, decimalDegits);
            byte[] refund_amount = getStringByte(printTwoStringStringDataSunmi(context.getString(R.string.refund_amount), Cancel_Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context))));
            byte[] retailer_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, nextline, boldOn, game_name, boldOff, nextline, line, nextline, ticket_number_txt, ticket_number, nextline, boldOn, ticket_cancelled_txt, boldOff, refund_amount, nextline, line, retailer_name, next2Line, breakPartial};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void printWinningTelpo570(Context context, WinningClaimPayResponseBean payResponseBean, UsbThermalPrinter usbThermalPrinter, CancelPrintError listner, String ticket_number) {
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
                    if (payResponseBean != null) {
                        if (payResponseBean.getResponseData().getAdvMessages() != null) {
                            if (payResponseBean.getResponseData().getAdvMessages() != null) {
                                if (payResponseBean.getResponseData().getAdvMessages().getTop() != null && payResponseBean.getResponseData().getAdvMessages().getTop().size() > 0) {
                                    for (int i = 0; i < payResponseBean.getResponseData().getAdvMessages().getTop().size(); i++) {
                                        printMiddleTelpo(payResponseBean.getResponseData().getAdvMessages().getTop().get(i).getMsgText(), usbThermalPrinter);
                                    }
                                    usbThermalPrinter.addString("                      ");
                                }

                            }
                        }
                        dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context));
                        amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context));
                        boolean isReprint = false;
                        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                        if (bitmap1 != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            usbThermalPrinter.printLogo(bitmap1, false);
                            if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                                usbThermalPrinter.setBold(true);
                                printMiddleTelpo(context.getString(R.string.ticket_not_for_sale), usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            }
                        }
                        reprint_data = false;
                        printHeading(payResponseBean.getResponseData().getGameName().toUpperCase(), usbThermalPrinter);
                        usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setBold(false);
                        //printTwoStringTelpo(context.getString(R.string.ticket_number), payResponseBean.getResponseData().getTicketNumber(), usbThermalPrinter);
                        printMiddleTelpo(context.getString(R.string.ticket_number), usbThermalPrinter);
                        printMiddleTelpo(appnedDash(payResponseBean.getResponseData().getTicketNumber(), 4), usbThermalPrinter);
                        printDarkline("___________________________", usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        usbThermalPrinter.printString();
                        for (int i = 0; i < payResponseBean.getResponseData().getDrawData().size(); i++) {
                            String winAmount = "0";
                            printTwoStringTelpo(context.getString(R.string.draw_date), getPrintDateFormatWinning(payResponseBean.getResponseData().getDrawData().get(i).getDrawDate()), usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.draw_time), payResponseBean.getResponseData().getDrawData().get(i).getDrawTime(), usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.win_status), payResponseBean.getResponseData().getDrawData().get(i).getWinStatus(), usbThermalPrinter);
                            winAmount = Utils.getBalanceToView(Double.parseDouble(payResponseBean.getResponseData().getDrawData().get(i).getWinningAmount()), dotFormatter, amountFormatter, 0);
                            printTwoStringTelpo(context.getString(R.string.winning_amt_colon), winAmount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                            printDarkline("___________________________", usbThermalPrinter);
                            usbThermalPrinter.setBold(false);
                            usbThermalPrinter.printString();
                            if (payResponseBean.getResponseData().getDrawData().get(i).getWinStatus().equalsIgnoreCase(AppConstants.RESULT_AWAITED)) {
                                isReprint = true;
                            }
                        }
                        if (isReprint) {
                            usbThermalPrinter.addString("                              ");
                            if (payResponseBean.getResponseData().getPanelData() != null && payResponseBean.getResponseData().getPanelData().size() > 0) {
                                if (ConfigData.getInstance().getConfigData() != null && ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE() != null)
                                    if (ConfigData.getInstance().getConfigData() != null && ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE() != null)
                                        decimalDegits = Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE());
                                dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context));
                                amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context));
                                total_amount = 0.0;
                                Amount = "";
                                Pannel_Price = "";
                                usbThermalPrinter.setFontSize(2);
                                usbThermalPrinter.setBold(false);
                                usbThermalPrinter.setBold(true);
                                printMiddleTelpo(context.getString(R.string.reprint_ticket), usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                                //printMiddleTelpo(appnedDash(payResponseBean.getResponseData().getTicketNumber(), 4), usbThermalPrinter);
                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                                printMiddleTelpo(context.getString(R.string.draw_timing), usbThermalPrinter);
                                for (int i = 0; i < payResponseBean.getResponseData().getDrawData().size(); i++) {
                                    printMiddleTelpo(getPrintDateFormatWinning(payResponseBean.getResponseData().getDrawData().get(i).getDrawDate()) + "  " + payResponseBean.getResponseData().getDrawData().get(i).getDrawTime(), usbThermalPrinter);
                                }
                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                                usbThermalPrinter.setBold(true);
                                printMiddleTelpo(context.getString(R.string.bet_details), usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                                usbThermalPrinter.printString();
                                for (int i = 0; i < payResponseBean.getResponseData().getPanelData().size(); i++) {
                                    Pannel_Price = "";
                                    Pannel_Price = Utils.getBalanceToView(payResponseBean.getResponseData().getPanelData().get(i).getUnitCost() * payResponseBean.getResponseData().getPanelData().get(i).getBetAmountMultiple() * payResponseBean.getResponseData().getPanelData().get(i).getNumberOfLines(), ",", " ", 0);
                                    if (payResponseBean.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_NUMBER)) {
                                        if (payResponseBean.getResponseData().getPanelData().get(i).getPickType().equalsIgnoreCase(AppConstants.BANKER)) {
                                            String isQp = payResponseBean.getResponseData().getPanelData().get(i).getQuickPick() == true ? "/QP" : "";
                                            String numberString = "";
                                            numberString = payResponseBean.getResponseData().getPanelData().get(i).getPickedValues();
                                            String barnker[] = numberString.split("-");
                                            printMiddleTelpo("UL -" + barnker[0], usbThermalPrinter);
                                            printMiddleTelpo("LL -" + barnker[1], usbThermalPrinter);
                                            if (payResponseBean.getResponseData().getPanelData().get(i).getQuickPick()) {
                                                printTwoStringTelpo(payResponseBean.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                            } else {
                                                printTwoStringTelpo(payResponseBean.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + payResponseBean.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                            }
                                            printTwoStringTelpo(context.getString(R.string.no_of_lines), "" + payResponseBean.getResponseData().getPanelData().get(i).getNumberOfLines(), usbThermalPrinter);
                                        } else {
                                            String isQp = payResponseBean.getResponseData().getPanelData().get(i).getQuickPick() == true ? "/QP" : "";
                                            String numberString = "";
                                            numberString = payResponseBean.getResponseData().getPanelData().get(i).getPickedValues();
                                            printMiddleTelpo(numberString, usbThermalPrinter);
                                            if (payResponseBean.getResponseData().getPanelData().get(i).getQuickPick()) {
                                                printTwoStringTelpo(payResponseBean.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                            } else {
                                                printTwoStringTelpo(payResponseBean.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + payResponseBean.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                            }
                                            printTwoStringTelpo(context.getString(R.string.no_of_lines), "" + payResponseBean.getResponseData().getPanelData().get(i).getNumberOfLines(), usbThermalPrinter);
                                        }
                                    } else if (payResponseBean.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_MARKET)) {
                                        printMiddleTelpo(payResponseBean.getResponseData().getPanelData().get(i).getBetDisplayName(), usbThermalPrinter);
                                        printTwoStringTelpo(payResponseBean.getResponseData().getPanelData().get(i).getPickDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                        printTwoStringTelpo(context.getString(R.string.no_of_lines), "" + payResponseBean.getResponseData().getPanelData().get(i).getNumberOfLines(), usbThermalPrinter);
                                    }
                                    total_amount += payResponseBean.getResponseData().getPanelData().get(i).getUnitCost() * payResponseBean.getResponseData().getPanelData().get(i).getBetAmountMultiple() * payResponseBean.getResponseData().getPanelData().get(i).getNumberOfLines();
                                    if (payResponseBean.getResponseData().getPanelData().size() == i + 1) {
                                        printDarkline("___________________________", usbThermalPrinter);
                                        usbThermalPrinter.setBold(false);
                                    } else {
                                        usbThermalPrinter.addString("---------------------------");
                                    }

                                }
                                Amount = Utils.getBalanceToView(total_amount, dotFormatter, amountFormatter, decimalDegits);
                                printTwoStringTelpo(context.getString(R.string.amount), "" + Amount, usbThermalPrinter);
                                printTwoStringTelpo(context.getString(R.string.no_of_draws), "" + payResponseBean.getResponseData().getDrawData().size(), usbThermalPrinter);
                                String Amount_Total = Utils.getBalanceToView(payResponseBean.getResponseData().getTotalPurchaseAmount(), ",", " ", 0);
                                usbThermalPrinter.setBold(true);
                                printTwoStringTelpo(context.getString(R.string.total_amount).toUpperCase(), Amount_Total + "  " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                                usbThermalPrinter.printString();
                                printBarcode(payResponseBean.getResponseData().getTicketNumber(), usbThermalPrinter);
                                printMiddleTelpo(appnedDash(payResponseBean.getResponseData().getTicketNumber(), 4), usbThermalPrinter);
                                usbThermalPrinter.setBold(false);

                            }
                        }
                        printMiddleTelpo(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(), usbThermalPrinter);
                        usbThermalPrinter.addString("___________________________");
                        if (payResponseBean.getResponseData().getAdvMessages() != null && payResponseBean.getResponseData().getAdvMessages().getBottom().size() > 0) {
                            usbThermalPrinter.addString("                      ");
                            if (payResponseBean.getResponseData().getAdvMessages().getBottom() != null) {
                                for (int j = 0; j < payResponseBean.getResponseData().getAdvMessages().getBottom().size(); j++) {
                                    printMiddleTelpo(payResponseBean.getResponseData().getAdvMessages().getBottom().get(j).getMsgText(), usbThermalPrinter);
                                    usbThermalPrinter.printString();
                                }
                            }
                        }


                        usbThermalPrinter.printString();
                        usbThermalPrinter.walkPaper(10);
                        usbThermalPrinter.addString("                 ");
                        if (ticket_number != null) {
                            saveTicketWinning(ticket_number, context);
                        }
                        listner.cancelError("SUCCESS", true);
                    } else {
                        Toast.makeText(context, context.getText(R.string.some_technical_issue), Toast.LENGTH_SHORT).show();
                    }

                } catch (TelpoException e) {
                    e.printStackTrace();
                    Result = e.toString();
                    if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listner.cancelError("No Paper", false);
                            }
                        });
                    }
                } finally {
                    try {
                        usbThermalPrinter.stop();
                    } catch (Exception e) {

                    }

                }
            }
        }).start();

    }

    public static byte[] getPrintDgeWinSunmi(WinningClaimPayResponseBean bean, Context context, CancelPrintError listner, String ticket_number_win) {
        if (bean != null) {
            boolean isReprint = false;
            String win_print = "";
            byte[] draw_text = new byte[0];
            byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
            byte[] drawDateAndTime = new byte[0];
            byte[] restOfDataByte = new byte[0];
            byte[] amountDataByte = new byte[0];
            byte[] total_amount_byte = new byte[0];
            byte[] fontSize2Big = PrintUtil.fontSizeSetBig(2);
            byte[] boldOff = PrintUtil.boldOff();
            byte[] top_msg = new byte[0];
            byte[] bottom_msg = new byte[0];
            byte[] next2Line = PrintUtil.nextLine(2);
            byte[] nextline = ESCUtil.nextLine(1);
            String Amount_Total = "";
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                builder.append("_");
            }
            StringBuilder stingDash = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                stingDash.append("-");
            }
            try {
                String top_message = "";
                String bottom_message = "";

                if (bean.getResponseData().getAdvMessages() != null) {
                    if (bean.getResponseData().getAdvMessages().getTop() != null && bean.getResponseData().getAdvMessages().getTop().size() > 0) {
                        for (int i = 0; i < bean.getResponseData().getAdvMessages().getTop().size(); i++) {
                            top_message = top_message + printMiddle(bean.getResponseData().getAdvMessages().getTop().get(i).getMsgText());
                        }
                    }
                    if (bean.getResponseData().getAdvMessages().getBottom() != null && bean.getResponseData().getAdvMessages().getBottom().size() > 0) {
                        for (int j = 0; j < bean.getResponseData().getAdvMessages().getBottom().size(); j++) {
                            bottom_message = bottom_message + printMiddle(bean.getResponseData().getAdvMessages().getBottom().get(j).getMsgText());
                        }
                    }
                    bottom_msg = alignHeadingSunmi(getStringByte(bottom_message));
                    top_msg = alignHeadingSunmi(getStringByte(top_message));
                }
                byte bet_details_txt[] = alignCenterSunmi(getStringByte(context.getString(R.string.bet_details)));
                byte[] line = getStringByte(builder.toString());
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
                byte[] sample__ticket_msg = new byte[0];
                if (BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN")) {
                    sample__ticket_msg = alignHeadingSunmi(getStringByte(context.getString(R.string.ticket_not_for_sale)));
                }
                byte[] boldOn = PrintUtil.boldOn();
                byte[] game_name = alignHeadingSunmi(getStringByte(bean.getResponseData().getGameName().toUpperCase()));
                byte[] ticket_number_txt = getStringByte(printMiddle(context.getString(R.string.ticket_number)));
                byte[] reprint_ticket_txt = getStringByte(printMiddle(context.getString(R.string.reprint_ticket)));
                byte[] ticket_number = getStringByte(printMiddle(appnedDash(bean.getResponseData().getTicketNumber(), 4)));
                for (int i = 0; i < bean.getResponseData().getDrawData().size(); i++) {
                    String winAmount = "0";
                    win_print = win_print + printTwoStringStringDataSunmi(context.getString(R.string.draw_date), getPrintDateFormatWinning(bean.getResponseData().getDrawData().get(i).getDrawDate()));
                    win_print = win_print + printTwoStringStringDataSunmi(context.getString(R.string.draw_time), bean.getResponseData().getDrawData().get(i).getDrawTime());
                    win_print = win_print + printTwoStringStringDataSunmi(context.getString(R.string.win_status), bean.getResponseData().getDrawData().get(i).getWinStatus());
                    winAmount = Utils.getBalanceToView(Double.parseDouble(bean.getResponseData().getDrawData().get(i).getWinningAmount()), ",", " ", 0);
                    win_print = win_print + printTwoStringStringDataSunmi(context.getString(R.string.winning_amt_colon), winAmount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                    win_print = win_print + builder;
                    if (bean.getResponseData().getDrawData().get(i).getWinStatus().equalsIgnoreCase(AppConstants.RESULT_AWAITED)) {
                        isReprint = true;
                    }
                }
                byte[] win_amount_print = getStringByte(win_print);
                if (isReprint) {
                    String datetToPrint = "";
                    String amountData = "";
                    Amount = "";
                    total_amount = 0.0;
                    draw_text = alignCenterSunmi(getStringByte(context.getString(R.string.draw_timing)));
                    for (int j = 0; j < bean.getResponseData().getDrawData().size(); j++) {
                        datetToPrint = datetToPrint + printMiddle(getPrintDateFormatWinning(bean.getResponseData().getDrawData().get(j).getDrawDate()) + "  " + bean.getResponseData().getDrawData().get(j).getDrawTime());
                    }
                    drawDateAndTime = getStringByte(datetToPrint);
                    String restOfData = "";
                    for (int i = 0; i < bean.getResponseData().getPanelData().size(); i++) {
                        String numberString = "";
                        numberString = "";
                        Pannel_Price = "";

                        Pannel_Price = Utils.getBalanceToView(bean.getResponseData().getPanelData().get(i).getUnitCost() * bean.getResponseData().getPanelData().get(i).getBetAmountMultiple() * bean.getResponseData().getPanelData().get(i).getNumberOfLines(), ",", " ", 0);
                        String isQp = bean.getResponseData().getPanelData().get(i).getQuickPick() == true ? "/QP" : "  ";
                        if (bean.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_NUMBER)) {
                            if (bean.getResponseData().getPanelData().get(i).getPickType().equalsIgnoreCase(AppConstants.BANKER)) {
                                numberString = bean.getResponseData().getPanelData().get(i).getPickedValues();
                                String banker[] = numberString.split("-");
                                restOfData = restOfData + printMiddle("UL- " + banker[0]);
                                restOfData = restOfData + printMiddle("LL- " + banker[1]);
                                if (bean.getResponseData().getPanelData().get(i).getQuickPick()) {
                                    restOfData = restOfData + printTwoStringStringData(bean.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                                } else {
                                    restOfData = restOfData + printTwoStringStringData(bean.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + bean.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                                }
                                restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_lines), "" + bean.getResponseData().getPanelData().get(i).getNumberOfLines());
                            } else {
                                numberString = bean.getResponseData().getPanelData().get(i).getPickedValues();
                                restOfData = restOfData + printMiddle(numberString);
                                if (bean.getResponseData().getPanelData().get(i).getQuickPick()) {
                                    restOfData = restOfData + printTwoStringStringData(bean.getResponseData().getPanelData().get(i).getBetDisplayName() + "" + isQp, Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                                } else {
                                    restOfData = restOfData + printTwoStringStringData(bean.getResponseData().getPanelData().get(i).getPickDisplayName() + "/" + bean.getResponseData().getPanelData().get(i).getBetDisplayName(), Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                                }
                                restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_lines), "" + bean.getResponseData().getPanelData().get(i).getNumberOfLines());
                            }
                        } else if (bean.getResponseData().getPanelData().get(i).getPickConfig().equalsIgnoreCase(AppConstants.PICK_CONFIG_MARKET)) {
                            numberString = bean.getResponseData().getPanelData().get(i).getBetDisplayName();
                            restOfData = restOfData + printMiddle(numberString);
                            restOfData = restOfData + printTwoStringStringData(bean.getResponseData().getPanelData().get(i).getPickDisplayName() + "", Pannel_Price + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                            restOfData = restOfData + printTwoStringStringData(context.getString(R.string.no_of_lines), "" + bean.getResponseData().getPanelData().get(i).getNumberOfLines());
                        }
                        total_amount += bean.getResponseData().getPanelData().get(i).getUnitCost() * bean.getResponseData().getPanelData().get(i).getBetAmountMultiple() * bean.getResponseData().getPanelData().get(i).getNumberOfLines();
                        if (bean.getResponseData().getPanelData().size() == i + 1) {
                            restOfData = restOfData + builder.toString();
                        } else {
                            restOfData = restOfData + stingDash.toString();
                        }
                        Amount = Utils.getBalanceToView(total_amount, ",", " ", 0);
                        Amount_Total = Utils.getBalanceToView(bean.getResponseData().getTotalPurchaseAmount(), ",", " ", 0);
                    }
                    restOfDataByte = getStringByte(restOfData);
                    amountData = amountData + printTwoStringStringData(context.getString(R.string.amount), Amount + "");
                    amountData = amountData + printTwoStringStringData(context.getString(R.string.no_of_draws), bean.getResponseData().getDrawData().size() + "");
                    amountDataByte = getStringByte(amountData);
                    total_amount_byte = printTwoStringSumni(context.getString(R.string.total_amount).toUpperCase(), Amount_Total + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                    String barcode = bean.getResponseData().getTicketNumber();
                    byte[] barcode_print = ESCUtil.printBitmap(getBitmap(barcode, 1, 384, 70));
                    byte[] ticket_no = getStringByte(printMiddle(appnedDash(bean.getResponseData().getTicketNumber(), 4)));
                    byte[] reatiler_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
                    byte[] breakPartial = PrintUtil.feedPaperCutPartial();
                    saveTicketWinning(ticket_number_win, context);
                    byte[][] cmdBytes = {ESCUtil.alignCenter(), boldOn, fontSize2Big, top_msg, boldOff, ESCUtil.alignCenter(), nextline, ESCUtil.alignCenter(), logo, boldOn, sample__ticket_msg, boldOff, nextline, boldOn, game_name, boldOff, nextline, line, nextline, ticket_number_txt, ticket_number, nextline, win_amount_print, next2Line, boldOn, reprint_ticket_txt, boldOff, line, draw_text, nextline, drawDateAndTime, nextline, line, nextline, bet_details_txt, nextline, restOfDataByte, nextline, amountDataByte, boldOn, total_amount_byte, boldOff, nextline, barcode_print, ticket_no, reatiler_name, line, fontSize2Big, boldOn, bottom_msg, fontSize1Big, boldOff, next2Line, breakPartial};
                    return PrintUtil.byteMerger(cmdBytes);

                } else {
                    byte[] reatiler_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
                    byte[] breakPartial = PrintUtil.feedPaperCutPartial();
                    saveTicketWinning(ticket_number_win, context);
                    byte[][] cmdBytes = {ESCUtil.alignCenter(), boldOn, fontSize2Big, top_msg, boldOff, ESCUtil.alignCenter(), nextline, ESCUtil.alignCenter(), logo, boldOn, sample__ticket_msg, boldOff, nextline, boldOn, game_name, boldOff, nextline, line, nextline, ticket_number_txt, ticket_number, nextline, win_amount_print, next2Line, reatiler_name, line, fontSize2Big, boldOn, bottom_msg, fontSize1Big, boldOff, next2Line, breakPartial};
                    return PrintUtil.byteMerger(cmdBytes);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static void  getPrintResult(ResultResponseBean bean, Context context, UsbThermalPrinter usbThermalPrinter, NoPaper listener) {
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
                    if (bean != null) {
                        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                        if (bitmap1 != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            usbThermalPrinter.printLogo(bitmap1, false);
                        }
                        printHeading(bean.getResponseData().get(0).getGameName().toUpperCase(), usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setBold(false);
                        usbThermalPrinter.printString();
                        for (int i = 0; i < bean.getResponseData().size(); i++) {
                            for (int j = 0; j < bean.getResponseData().get(i).getResultData().size(); j++) {
                                for (int k = 0; k < bean.getResponseData().get(i).getResultData().get(j).getResultInfo().size(); k++) {
                                    usbThermalPrinter.setBold(false);
                                    usbThermalPrinter.setFontSize(2);
                                    usbThermalPrinter.setBold(false);
                                    printMiddleTelpo(context.getString(R.string.draw_time), usbThermalPrinter);
                                    printMiddleTelpo(getPrintDateFormat(bean.getResponseData().get(i).getResultData().get(j).getResultDate()) + "  " + bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getDrawTime(), usbThermalPrinter);
                                    printMiddleTelpo(context.getString(R.string.result), usbThermalPrinter);
                                    printMiddleTelpo(bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getWinningNo(), usbThermalPrinter);
                                    usbThermalPrinter.addString(getdash());
                                    if (bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getWinningMultiplierInfo() != null) {
                                        printTwoStringTelpo(context.getString(R.string.winning_multiplier), bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).
                                                getWinningMultiplierInfo().getMultiplierCode() + "(" + bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getWinningMultiplierInfo().getValue() + ")", usbThermalPrinter);
                                        usbThermalPrinter.addString(getLine());
                                    }
                                    if (bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo() != null && bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo().size() > 0) {
                                        for (int l = 0; l < bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo().size(); l++) {
                                            printTwoStringTelpo(bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo().get(l).getMultiplierCode(), "" + bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo().get(l).getBallValue(), usbThermalPrinter);
                                            usbThermalPrinter.addString(getLine());
                                        }
                                    }
                                    if (bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo() != null && bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().size() > 0) {
                                        for (int m = 0; m < bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().size(); m++) {
                                            printTwoStringTelpo(bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().get(m).getBetDisplayName(), "" + bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().get(m).getPickTypeName(), usbThermalPrinter);
                                            if (bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().size() == m + 1) {

                                            } else {
                                                usbThermalPrinter.addString(getLine());
                                            }
                                        }
                                    }
                                    usbThermalPrinter.addString(getdash());
                                }
                            }
                        }
                        usbThermalPrinter.printString();
                        usbThermalPrinter.walkPaper(10);
                        listener.noPaper("SUCCESS");
                    } else {
                        Toast.makeText(context, context.getText(R.string.some_technical_issue), Toast.LENGTH_SHORT).show();
                    }

                } catch (TelpoException e) {
                    e.printStackTrace();
                    usbThermalPrinter.stop();
                    if (e instanceof NoPaperException) {
                        Log.v("no paper", "no paper found");
                    }
                    Result = e.toString();
                    if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listener.noPaper("No Paper");
                            }
                        });
                    }
                } finally {
                    try {
                        usbThermalPrinter.stop();

                    } catch (Exception e) {

                    }
                }
            }
        }).start();

    }



    public static void  getPrintResultNew(ResultResponseBean bean, Context context, UsbThermalPrinter usbThermalPrinter, NoPaper listener, int item) {
        new Thread(() -> {
            try {
                try {
                    usbThermalPrinter.start(1);
                    usbThermalPrinter.reset();
                } catch (TelpoException e) {
                    usbThermalPrinter.stop();
                    e.printStackTrace();
                }
                if (bean != null) {
                    Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                    bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                    if (bitmap1 != null) {
                        usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                        usbThermalPrinter.printLogo(bitmap1, false);
                    }
                    printHeading(bean.getResponseData().get(0).getGameName().toUpperCase(), usbThermalPrinter);
                    usbThermalPrinter.setBold(false);
                    usbThermalPrinter.setFontSize(2);
                    usbThermalPrinter.setBold(false);
                    usbThermalPrinter.printString();
                    //for (int i = 0; i < bean.getResponseData().size(); i++) {
                        //for (int j = 0; j < bean.getResponseData().get(i).getResultData().size(); j++) {
                          //  for (int k = 0; k < bean.getResponseData().get(i).getResultData().get(j).getResultInfo().size(); k++) {
                                usbThermalPrinter.setBold(false);
                                usbThermalPrinter.setFontSize(2);
                                usbThermalPrinter.setBold(false);
                                printMiddleTelpo(context.getString(R.string.draw_time), usbThermalPrinter);
                                printMiddleTelpo(getPrintDateFormat(bean.getResponseData().get(0).getResultData().get(0).getResultDate()) + "  " + bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getDrawTime(), usbThermalPrinter);
                                printMiddleTelpo(context.getString(R.string.result), usbThermalPrinter);
                                printMiddleTelpo(bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getWinningNo(), usbThermalPrinter);
                                usbThermalPrinter.addString(getdash());
                                if (bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getWinningMultiplierInfo() != null) {
                                    printTwoStringTelpo(context.getString(R.string.winning_multiplier), bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).
                                            getWinningMultiplierInfo().getMultiplierCode() + "(" + bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getWinningMultiplierInfo().getValue() + ")", usbThermalPrinter);
                                    usbThermalPrinter.addString(getLine());
                                }
                                if (bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo() != null && bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo().size() > 0) {
                                    for (int l = 0; l < bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo().size(); l++) {
                                        printTwoStringTelpo(bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo().get(l).getMultiplierCode(), "" + bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo().get(l).getBallValue(), usbThermalPrinter);
                                        usbThermalPrinter.addString(getLine());
                                    }
                                }
                                if (bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo() != null && bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().size() > 0) {
                                    for (int m = 0; m < bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().size(); m++) {
                                        printTwoStringTelpo(bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().get(m).getBetDisplayName(), "" + bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().get(m).getPickTypeName(), usbThermalPrinter);
                                        if (bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().size() == m + 1) {

                                        } else {
                                            usbThermalPrinter.addString(getLine());
                                        }
                                    }
                                }
                                usbThermalPrinter.addString(getdash());
                        //    }
                     //   }
                    //}
                    usbThermalPrinter.printString();
                    usbThermalPrinter.walkPaper(10);
                    listener.noPaper("SUCCESS");
                } else {
                    Toast.makeText(context, context.getText(R.string.some_technical_issue), Toast.LENGTH_SHORT).show();
                }

            } catch (TelpoException e) {
                e.printStackTrace();
                usbThermalPrinter.stop();
                if (e instanceof NoPaperException) {
                    Log.v("no paper", "no paper found");
                }
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.noPaper("No Paper");
                        }
                    });
                }
            } finally {
                try {
                    usbThermalPrinter.stop();

                } catch (Exception e) {

                }
            }
        }).start();

    }


    public static byte[] getPrintDgeResult(ResultResponseBean bean, Context context, CancelPrintError listner) {
        byte[] next2Line = ESCUtil.nextLine(3);
        byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
        byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
        byte[] boldOff = PrintUtil.boldOff();
        String print = "";
        byte[] nextline = ESCUtil.nextLine(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            builder.append("_");
        }
        StringBuilder stingDash = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            stingDash.append("-");
        }

        try {
            byte[] line = getStringByte(builder.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(bean.getResponseData().get(0).getGameName().toUpperCase()));
            for (int i = 0; i < bean.getResponseData().size(); i++) {
                print = "";
                for (int j = 0; j < bean.getResponseData().get(i).getResultData().size(); j++) {
                    for (int k = 0; k < bean.getResponseData().get(i).getResultData().get(j).getResultInfo().size(); k++) {
                        print = print + printMiddle(context.getString(R.string.draw_time));
                        print = print + printMiddle(getPrintDateFormat(bean.getResponseData().get(i).getResultData().get(j).getResultDate()) + "  " + bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getDrawTime());
                        print = print + printMiddle(context.getString(R.string.result));
                        print = print + printMiddle(bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getWinningNo());
                        print = print + getDashLine();

                        if (bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getWinningMultiplierInfo() != null) {
                            print = print + printTwoStringStringData(context.getString(R.string.winning_multiplier), bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).
                                    getWinningMultiplierInfo().getMultiplierCode() + "(" + bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getWinningMultiplierInfo().getValue() + ")");
                            print = print + getLine();
                        }
                        if (bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo() != null && bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo().size() > 0) {
                            for (int l = 0; l < bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo().size(); l++) {
                                print = print + printTwoStringStringData(bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo().get(l).getMultiplierCode(), "" + bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getRunTimeFlagInfo().get(l).getBallValue());
                                print = print + getLine();
                            }
                        }
                        if (bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo() != null && bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().size() > 0) {
                            for (int m = 0; m < bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().size(); m++) {
                                print = print + printTwoStringStringData(bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().get(m).getBetDisplayName(), "" + bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().get(m).getPickTypeName());
                                if (bean.getResponseData().get(i).getResultData().get(j).getResultInfo().get(k).getSideBetMatchInfo().size() == m + 1) {

                                } else {
                                    print = print + getLine();
                                }

                            }
                        }
                    }
                }
            }
            byte[] result_data = getStringByte(print);
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, nextline, boldOn, game_name, boldOff, nextline, line, result_data, breakPartial, next2Line};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] getPrintDgeResultNew(ResultResponseBean bean, Context context, CancelPrintError listner, int item) {
        byte[] next2Line = ESCUtil.nextLine(3);
        byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
        byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
        byte[] boldOff = PrintUtil.boldOff();
        String print = "";
        byte[] nextline = ESCUtil.nextLine(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            builder.append("_");
        }
        StringBuilder stingDash = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            stingDash.append("-");
        }

        try {
            byte[] line = getStringByte(builder.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(bean.getResponseData().get(0).getGameName().toUpperCase()));
           // for (int i = 0; i < bean.getResponseData().size(); i++) {
                print = "";
                //for (int j = 0; j < bean.getResponseData().get(i).getResultData().size(); j++) {
                    //for (int k = 0; k < bean.getResponseData().get(i).getResultData().get(j).getResultInfo().size(); k++) {
                        print = print + printMiddle(context.getString(R.string.draw_time));
                        print = print + printMiddle(getPrintDateFormat(bean.getResponseData().get(0).getResultData().get(0).getResultDate()) + "  " + bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getDrawTime());
                        print = print + printMiddle(context.getString(R.string.result));
                        print = print + printMiddle(bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getWinningNo());
                        print = print + getDashLine();

                        if (bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getWinningMultiplierInfo() != null) {
                            print = print + printTwoStringStringData(context.getString(R.string.winning_multiplier), bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).
                                    getWinningMultiplierInfo().getMultiplierCode() + "(" + bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getWinningMultiplierInfo().getValue() + ")");
                            print = print + getLine();
                        }
                        if (bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo() != null && bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo().size() > 0) {
                            for (int l = 0; l < bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo().size(); l++) {
                                print = print + printTwoStringStringData(bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo().get(l).getMultiplierCode(), "" + bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getRunTimeFlagInfo().get(l).getBallValue());
                                print = print + getLine();
                            }
                        }
                        if (bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo() != null && bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().size() > 0) {
                            for (int m = 0; m < bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().size(); m++) {
                                print = print + printTwoStringStringData(bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().get(m).getBetDisplayName(), "" + bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().get(m).getPickTypeName());
                                if (bean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(item).getSideBetMatchInfo().size() == m + 1) {

                                } else {
                                    print = print + getLine();
                                }

                            }
                        }
                   // }
               // }
            //}
            byte[] result_data = getStringByte(print);
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, nextline, boldOn, game_name, boldOff, nextline, line, result_data, breakPartial, next2Line};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


   /* public static void getPrintDataTelpo570(FiveByNinetySaleResponseBean bean, Context context, UsbThermalPrinter usbThermalPrinter, boolean reprint) {
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

                    if (bean != null) {


                        total_amount=0.0;
                        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                        if (bitmap1 != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            usbThermalPrinter.printLogo(bitmap1, false);
                        }

                        printHeading(bean.getResponseData().getGameName(), usbThermalPrinter);
                        usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setBold(false);
                        String draw_date = bean.getResponseData().getPurchaseTime().split(" ")[0];
                        String draw_time = bean.getResponseData().getPurchaseTime().split(" ")[1];
                        printMiddleTelpo("Purchase Time", usbThermalPrinter);
                        printMiddleTelpo(getPrintDateFormat(draw_date) + " " + draw_time, usbThermalPrinter);
                        printMiddleTelpo("Ticket No ", usbThermalPrinter);
                        printMiddleTelpo(appnedDash(bean.getResponseData().getTicketNumber(), 5), usbThermalPrinter);
                        if (reprint) {
                            printMiddleTelpo("Reprint", usbThermalPrinter);
                        }

                        printDarkline("___________________________", usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        printMiddleTelpo("Draw Timming ", usbThermalPrinter);

                        for (int i = 0; i < bean.getResponseData().getDrawData().size(); i++) {
                            printMiddleTelpo(getPrintDateFormat(bean.getResponseData().getDrawData().get(i).getDrawDate()) + " " + bean.getResponseData().getDrawData().get(i).getDrawTime(), usbThermalPrinter);
                        }
                        printDarkline("___________________________", usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        printMiddleTelpo("Bet Details", usbThermalPrinter);
                        usbThermalPrinter.printString();
                        for (int i = 0; i < bean.getResponseData().getPanelData().size(); i++) {

                            String isQp = bean.getResponseData().getPanelData().get(i).getQuickPick() == true ? "QP" : "Manual";

                            //printTwoStringTelpo(bean.getResponseData().getPanelData().get(i).getBetType(), isQp,usbThermalPrinter);
                            String numberString = "";
                            numberString = bean.getResponseData().getPanelData().get(i).getPickedValues();
                            printTwoStringTelpo(numberString, Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)) + bean.getResponseData().getPanelData().get(i).getPanelPrice(), usbThermalPrinter);
                            total_amount+= bean.getResponseData().getPanelData().get(i).getPlayerPanelPrice();
                            usbThermalPrinter.addString("Main Bet" + "/" + bean.getResponseData().getPanelData().get(i).getPickType() + "/" + isQp);
                            usbThermalPrinter.addString("---------------------------");

                        }
                        usbThermalPrinter.setBold(true);
                        printTwoStringTelpo("    ", "------", usbThermalPrinter);
                        usbThermalPrinter.setBold(false);
                        printTwoStringTelpo("Amount ", "" + total_amount, usbThermalPrinter);
                        printTwoStringTelpo("No. of Draws", "" + bean.getResponseData().getDrawData().size(), usbThermalPrinter);
                        printTwoStringTelpo("TOTAL AMOUNT", Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context))+ bean.getResponseData().getTotalPurchaseAmount() + "", usbThermalPrinter);
                        printBarcode(bean.getResponseData().getTicketNumber(), usbThermalPrinter);
                        usbThermalPrinter.addString("                              ");
                        printMiddleTelpo(bean.getResponseData().getTicketNumber(), usbThermalPrinter);
                        usbThermalPrinter.addString("__________________________");
                        printMiddleTelpo(PlayerData.getInstance().getUsername(),usbThermalPrinter);
                        usbThermalPrinter.printString();
                        usbThermalPrinter.walkPaper(10);
                        saveTicket(bean, context);
                    }

                } catch (TelpoException e) {
                    e.printStackTrace();
                    usbThermalPrinter.stop();
                    if(e instanceof NoPaperException){
                        Log.v("no paper","no paper found");
                    }
                    Result = e.toString();
                    if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                BaseClassSle.getBaseClassSle().getNoPaper().noPaper("No Paper");
                            }
                        });

                    }



                } finally {
                    try {
                        usbThermalPrinter.stop();

                    } catch (Exception e) {


                    }

                }
            }


        }).start();

    }
*/


    private static void printDarkline(String dark_line, UsbThermalPrinter usbThermalPrinter) throws TelpoException {
        usbThermalPrinter.setBold(true);
        usbThermalPrinter.addString(dark_line);
    }


    public static String appnedDash(String ticketNumber, int appendPos) {
        String edit_ticket_number = "";
        if (ticketNumber != null && !ticketNumber.equalsIgnoreCase(" ")) {
            StringBuffer buffer = new StringBuffer(ticketNumber);
            for (int i = 1; i < ticketNumber.length() / 4; i++)

                if (i == 1) {
                    edit_ticket_number = "" + buffer.insert(4, "-");
                } else if (i == 2) {
                    edit_ticket_number = "" + buffer.insert((appendPos * i) + 1, "-");
                } else if (i == 3) {
                    edit_ticket_number = "" + buffer.insert((appendPos * i) + 2, "-");
                } else if (i == 4) {
                    edit_ticket_number = "" + buffer.insert((appendPos * i) + 3, "-");
                }

        }

        return edit_ticket_number;

    }


    private static void saveTicket(FiveByNinetySaleResponseBean responseBean, Context context) {
        SharedPrefUtil.putLastTicketNumber(context, SharedPrefUtil.getString(context, SharedPrefUtil.USERNAME), responseBean.getResponseData().getTicketNumber());
        SharedPrefUtil.putLastGameCode(context, LAST_GAME_CODE, responseBean.getResponseData().getGameCode());
    }

    private static void saveTicketWinning(String ticket_number, Context context) {
        SharedPrefUtil.putLastTicketNumberWinning(context, SharedPrefUtil.getString(context, SharedPrefUtil.USERNAME), ticket_number);
    }


    public static void printBarcode(String barcode, UsbThermalPrinter usbThermalPrinter) throws TelpoException {

        Bitmap bitmap = CreateCode(barcode, BarcodeFormat.CODE_128, 384, 64);
        usbThermalPrinter.printLogo(bitmap, false);

    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


    public static void printMiddleTelpo(String content, UsbThermalPrinter usbThermalPrinter) throws TelpoException {
        // set Align for Text String
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_MIDDLE);
        usbThermalPrinter.addString(content);
        usbThermalPrinter.setGray(2);
        usbThermalPrinter.setFontSize(2);
        usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
    }

    public static void printTwoStringTelpo(String one, String two, UsbThermalPrinter usbThermalPrinter) throws TelpoException {
        //usbThermalPrinter.setFontSize(2);
        if (getDeviceName().contains("900") || (getDeviceName().contains("900"))) {
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
        } else if (getDeviceName().contains("580") || getDeviceName().contains("550") || (getDeviceName().contains("510"))) {
            usbThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
            String a = "", b = "";
            if (getDeviceName().contains("550")) {
                a = one.replace(" ", "  ");
                b = two.replace(" ", "  ");
            } else {
                a = one;
                b = two;
            }

            if ((one.length() + two.length()) > 27 && !getDeviceName().contains("550")) {
                usbThermalPrinter.addString(a);
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_RIGHT);
                usbThermalPrinter.addString(b);
            } else if ((one.length() + two.length()) > 32 && getDeviceName().contains("550")) {
                usbThermalPrinter.addString(a);
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_RIGHT);
                usbThermalPrinter.addString(b);
            } else {
                StringBuffer str = new StringBuffer();
                str.append(a);

                if (!getDeviceName().contains("550")) {
                    for (int i = one.length(); i < (27 - two.length()); i++) {
                        str.append("  ");
                    }
                    str.append(b);
                } else {
                    for (int i = one.length(); i < (32 - two.length()); i++) {
                        str.append(" ");
                    }
                    str.append(b);
                }

                usbThermalPrinter.addString(str.toString());
            }
        } else {
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
        }
    }

    public static void printWinningSractchTelpo570(Context context, ClaimTicketResponseNewBean bean, UsbThermalPrinter usbThermalPrinter, CancelPrintError listner) {

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
                    if (bean != null) {
                        String winAmount = "";
                        dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context));
                        amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context));

                        if (bean.getAdvMessages() != null) {
                            if (bean.getAdvMessages() != null) {
                                if (bean.getAdvMessages().getBottom() != null && bean.getAdvMessages().getTop().size() > 0) {
                                    for (int i = 0; i < bean.getAdvMessages().getTop().size(); i++) {
                                        printMiddleTelpo(bean.getAdvMessages().getTop().get(i).getMsgText(), usbThermalPrinter);
                                    }
                                    usbThermalPrinter.addString("                      ");
                                }

                            }
                        }
                        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1, 300, 120, false);
                        if (bitmap1 != null) {
                            usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                            usbThermalPrinter.printLogo(bitmap1, false);
                        }
                        printHeading(context.getString(R.string.scratch), usbThermalPrinter);
                        printDarkline("___________________________", usbThermalPrinter);
                        printHeading(context.getString(R.string.winning_ticket), usbThermalPrinter);
                        usbThermalPrinter.setFontSize(2);
                        usbThermalPrinter.setBold(false);
                        printMiddleTelpo(context.getString(R.string.invoice_number), usbThermalPrinter);
                        printMiddleTelpo(bean.getTransactionNumber(), usbThermalPrinter);
                        String dateTime[] = bean.getTransactionDate().split(" ");
                        printTwoStringTelpo("Invoice Date:", dateTime[0], usbThermalPrinter);
                        printTwoStringTelpo("Invoice Time:", dateTime[1], usbThermalPrinter);
                        printTwoStringTelpo("Ticket Number:", bean.getTicketNumber(), usbThermalPrinter);
                        winAmount = Utils.getBalanceToView(Double.parseDouble(bean.getWinningAmount()), dotFormatter, amountFormatter, 0);
                        printTwoStringTelpo(context.getString(R.string.winning_amt_colon), winAmount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)), usbThermalPrinter);
                        printDarkline("___________________________", usbThermalPrinter);

                        if (bean.getAdvMessages() != null) {
                            usbThermalPrinter.addString("                      ");
                            if (bean.getAdvMessages() != null) {
                                if (bean.getAdvMessages().getBottom() != null && bean.getAdvMessages().getBottom().size() > 0) {
                                    for (int i = 0; i < bean.getAdvMessages().getBottom().size(); i++) {
                                        printMiddleTelpo(bean.getAdvMessages().getBottom().get(i).getMsgText(), usbThermalPrinter);
                                    }
                                }
                            }
                        }
                        usbThermalPrinter.printString();
                        usbThermalPrinter.walkPaper(10);
                        usbThermalPrinter.addString("                 ");
                        listner.cancelError("SUCCESS", true);
                    } else {
                        Toast.makeText(context, context.getText(R.string.some_technical_issue), Toast.LENGTH_SHORT).show();
                    }

                } catch (TelpoException e) {
                    e.printStackTrace();
                    Result = e.toString();
                    if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listner.cancelError("No Paper", false);
                            }
                        });
                    }
                } finally {
                    try {
                        usbThermalPrinter.stop();
                    } catch (Exception e) {

                    }

                }
            }
        }).start();
    }


    public static byte[] printWinningSractchSunmi(Context context, ClaimTicketResponseNewBean bean) {
        byte[] next2Line = PrintUtil.nextLine(2);
        byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
        byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
        byte[] boldOff = PrintUtil.boldOff();
        byte[] fontSize2Big = PrintUtil.fontSizeSetBig(2);
        byte[] top_msg = new byte[0];
        byte[] bottom_msg = new byte[0];
        String Cancel_Amount = "";
        byte[] nextline = ESCUtil.nextLine(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            builder.append("_");
        }
        try {
            String top_message = "";
            String bottom_message = "";
            if (bean.getAdvMessages() != null) {
                if (bean.getAdvMessages().getTop() != null && bean.getAdvMessages().getTop().size() > 0) {
                    for (int i = 0; i < bean.getAdvMessages().getTop().size(); i++) {
                        top_message = top_message + printMiddle(bean.getAdvMessages().getTop().get(i).getMsgText());
                    }
                }
                if (bean.getAdvMessages().getBottom() != null && bean.getAdvMessages().getBottom().size() > 0) {
                    for (int j = 0; j < bean.getAdvMessages().getBottom().size(); j++) {
                        bottom_message = bottom_message + printMiddle(bean.getAdvMessages().getBottom().get(j).getMsgText());
                    }
                }
                bottom_msg = alignHeadingSunmi(getStringByte(bottom_message));
                top_msg = alignHeadingSunmi(getStringByte(top_message));
            }
            byte[] line = getStringByte(builder.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(context.getString(R.string.scratch)));
            byte[] win_tkt_txt = getStringByte(printMiddle(context.getString(R.string.winning_ticket)));
            byte[] invoice_no = getStringByte(printMiddle(context.getString(R.string.invoice_number)));
            byte[] transaction_no = getStringByte(printMiddle(bean.getTransactionNumber()));
            String dateTime[] = bean.getTransactionDate().split(" ");
            byte[] draw_date = getStringByte(printTwoStringStringData(context.getString(R.string.invoice_date_colon), dateTime[0]));
            byte[] draw_time = getStringByte(printTwoStringStringData(context.getString(R.string.invoice_time_colon), dateTime[1]));
            String Amount = Utils.getBalanceToView(Double.parseDouble(bean.getWinningAmount()), ",", " ", 0);
            byte[] win_amount = getStringByte(printTwoStringStringData(context.getString(R.string.winning_amt_colon), Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context))));
            byte[] retailer_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), boldOn, fontSize2Big, top_msg, boldOff, nextline, ESCUtil.alignCenter(), logo, boldOn, nextline, game_name, boldOff, nextline, line, boldOn, win_tkt_txt, boldOff, nextline, invoice_no, transaction_no, draw_date, draw_time, win_amount, nextline, retailer_name, line, fontSize2Big, boldOn, bottom_msg, fontSize1Big, boldOff, next2Line, breakPartial};
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

    public static byte[] printOlaRegistrationReceipt(OlaRegistrationPrintBean model, Context context) {
        byte[] next2Line = PrintUtil.nextLine(2);
        byte[] next3Line = PrintUtil.nextLine(3);
        byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
        byte[] fontSize2Big = PrintUtil.fontSizeSetBig(1);
        byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
        byte[] boldOff = PrintUtil.boldOff();
        String Cancel_Amount = "";
        byte[] nextline = ESCUtil.nextLine(1);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            builder.append(".");
        }
        try {
            byte[] line = getStringByte(builder.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] register_txt = getStringByte(printMiddle(context.getString(R.string.registered_success)));
            byte[] userName = getStringByte(printMiddle(model.getUserNameTag()));
            byte[] userNametxt = getStringByte(printMiddle(model.getUserNameValue()));
            byte[] password = getStringByte(printMiddle(model.getPasswordTag()));
            byte[] password_val = getStringByte(printMiddle(model.getPasswordValue()));
            byte[] depositAmount = getStringByte(printMiddle(model.getDepositAmountTag()));
            byte[] depositAmount_val = getStringByte(printMiddle(model.getDepositAmountValue()));
            byte[] barcode_print = ESCUtil.printBitmap(getBitmap(model.getUserNameValue() + "###" + model.getPasswordValue(), 4, 420, 250));
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, next2Line, fontSize2Small, register_txt, next2Line, userName, boldOn, fontSize2Big, userNametxt, fontSize2Small, boldOff, nextline, password, fontSize2Big, boldOn, password_val, boldOff, fontSize2Small, line, nextline, fontSize1Big, boldOn, depositAmount, fontSize2Big, depositAmount_val, fontSize2Small, nextline, barcode_print, next3Line, fontSize2Small, boldOff, PrintUtil.feedPaperCutPartial()};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void printCamWinTicket(String printData[], Context context,Bitmap image, NoPaper listner) throws UnsupportedEncodingException {
        if (printData != null) {
            String print_cam = "";
            byte[] data;
            String bar_val = "";
            byte[] logo = new byte[0];

            for (int i = 0; i < printData.length; i++) {
                String print = printData[i];
                if (print.contains("/")) {
                    String split_date_ref[] = print.split("##");
                    if (split_date_ref.length == 2) {
                        print_cam = print_cam + printMiddle(" ");
                        print_cam = print_cam + printTwoStringStringData(context.getString(R.string.date), split_date_ref[0]);
                        print_cam = print_cam + printTwoStringStringData(context.getString(R.string.trans_ref), split_date_ref[1]) + "\n";
                        bar_val = split_date_ref[1];

                    }
                }
                if (print.contains("##") && print.contains("X") && !print.contains("/")) {
                    String split[] = print.split("##");
                    print_cam = print_cam + printMiddle(split[0]);
                    print_cam = print_cam + split[1];
                    print_cam = print_cam + printMiddle(" ");
                }
                if ((print.contains("Amount") && !print.contains("Bonus")) || print.contains("Min/col") || print.contains("Max/col") ||
                        print.contains("Winning")) {
                    String split_two[] = print.split(" ");
                    print_cam = print_cam + printTwoStringStringData(split_two[0], split_two[1]) + "";
                } else if (print.contains("Bonus Amount Min:") || print.contains("Bonus Amount Max")) {
                    String split_two[] = print.split(" ");
                    print_cam = print_cam + printTwoStringStringData(split_two[0] + " " + split_two[1] + " " + split_two[2], split_two[3]) + "";
                } else if (print.contains("Bonus Amount:") || print.contains("Possible win")) {
                    String split_two[] = print.split(" ");
                    print_cam = print_cam + printTwoStringStringData(split_two[0] + " " + split_two[1], split_two[2]) + "";
                } else if (print.contains("out")) {
                    String split[] = print.split("##");
                    print_cam = print_cam + printTwoStringStringData(context.getString(R.string.combination), split[0]);
                    print_cam = print_cam + printTwoStringStringData(context.getString(R.string.bet_colon), split[1]) + "\n";
                } else {
                    if (print.contains("Date Transaction ref.") || print.contains("/")) {

                    } else if (print.contains("Combinations Bets")) {

                    } else if (print.contains("##") && print.contains("X")) {

                    } else {
                        if (i == 1) {
                            //print_cam = print_cam + "\n";
                        }
                        print_cam = print_cam + print + "\n";
                    }
                }

            }
            try {
                data = getStringByte(print_cam);
                byte[] nextLine = PrintUtil.nextLine(1);
                byte[] next2Line = PrintUtil.nextLine(2);
                if (image != null) {
                    logo = ESCUtil.printBitmap(image, 0);
                }
                byte[] barcode_print = ESCUtil.printBitmap(getBitmap(bar_val, 1, 384, 70));
                byte[] ticket_no = getStringByte(printMiddle(bar_val));
                byte[] breakPartial = PrintUtil.feedPaperCutPartial();
                byte[][] cmdBytes = {ESCUtil.alignCenter(), logo,nextLine, data, nextLine,barcode_print,ticket_no, next2Line, breakPartial};
                listner.noPaper("SUCCESS");
                AidlUtil.getInstance().sendRawData(PrintUtil.byteMerger(cmdBytes));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    public static void printCamWinTicketTps(String[] printData, Context context, UsbThermalPrinter usbThermalPrinter,Bitmap bitmap,NoPaper listner) {

        try {
            try {
                usbThermalPrinter.start(1);
                usbThermalPrinter.reset();
            } catch (TelpoException e) {
                usbThermalPrinter.stop();
                e.printStackTrace();
            }
            if (bitmap != null) {
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                usbThermalPrinter.printLogo(bitmap, false);
            }

            if (printData != null) {
                String print_cam = "";
                byte[] data;
                String bar_val = "";

                for (int i = 0; i < printData.length; i++) {
                    String print = printData[i];
                    if (!print.equalsIgnoreCase("")) {
                        if (print.contains("/")) {
                            String split_date_ref[] = print.split("##");
                            if (split_date_ref.length == 2) {
                                usbThermalPrinter.addString("  ");
                                printTwoStringTelpo(context.getString(R.string.date), split_date_ref[0], usbThermalPrinter);
                                printTwoStringTelpo(context.getString(R.string.trans_ref), split_date_ref[1], usbThermalPrinter);
                                bar_val = split_date_ref[1];
                            }
                        }
                        if (print.contains("##") && print.contains("X") && !print.contains("/")) {
                            String split[] = print.split("##");
                            printMiddleTelpo(split[0], usbThermalPrinter);
                            printMiddleTelpo(split[1], usbThermalPrinter);
                            printMiddleTelpo(" ", usbThermalPrinter);

                        }
                        if ((print.contains("Amount") && !print.contains("Bonus")) || print.contains("Min/col") || print.contains("Max/col") ||
                                print.contains("Winning")) {
                            String split_two[] = print.split(" ");
                            printTwoStringTelpo(split_two[0], split_two[1], usbThermalPrinter);

                        } else if (print.contains("Bonus Amount Min:") || print.contains("Bonus Amount Max")) {
                            String split_two[] = print.split(" ");
                            printTwoStringTelpo(split_two[0] + " " + split_two[1] + " " + split_two[2], split_two[3], usbThermalPrinter);
                            usbThermalPrinter.printString();
                        } else if (print.contains("Bonus Amount:") || print.contains("Possible win")) {
                            String split_two[] = print.split(" ");
                            printTwoStringTelpo(split_two[0] + " " + split_two[1], split_two[2], usbThermalPrinter);

                        } else if (print.contains("out")) {
                            String split[] = print.split("##");
                            printTwoStringTelpo(context.getString(R.string.combination), split[0], usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.bet_colon), split[1], usbThermalPrinter);

                        } else {
                            if (print.contains("Date Transaction ref.") || print.contains("/")) {

                            } else if (print.contains("Combinations Bets")) {

                            } else if (print.contains("##") && print.contains("X")) {

                            } else {
                                if (i == 1) {
                                    usbThermalPrinter.addString("   ");
                                }
                                //print_cam = print_cam + print + "\n";
                                printMiddleTelpo(print, usbThermalPrinter);
                            }
                            usbThermalPrinter.printString();
                        }


                        usbThermalPrinter.printString();
                    }
                }
                if (!bar_val.equalsIgnoreCase("")) {
                    usbThermalPrinter.addString("       ");
                    printBarcode(bar_val, usbThermalPrinter);
                    printMiddleTelpo(bar_val, usbThermalPrinter);
                }
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.printString();
                usbThermalPrinter.walkPaper(10);
                listner.noPaper("SUCCESS");
            }

        } catch (Exception e) {
            if (e instanceof NoPaperException) {
                Log.v("no paper", "no paper found");
            }
            Result = e.toString();
            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        listner.noPaper("No Paper");
                    }
                });

            }

        } finally {
            try {
                usbThermalPrinter.stop();
            } catch (Exception e) {
            }

        }
    }

    //SBS Print

    public static byte[] printSbsTicket(SbsPurchaseBean purchaseBean, Context context) {
        try {
            String print = "";
            String footer = "";
            String print_amount = "";
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

            byte[] getDash = getStringByte(stingDash.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(context.getString(R.string.sports_betting)));
            String time[] = purchaseBean.getDateTime().split(" ");
            byte[] purchase_time = getStringByte(printMiddle(context.getString(R.string.purchase_time)));
            byte[] time_val_txt = getStringByte(printMiddle(getPrintDateFormat(time[0]) + "  " + time[1]));
            byte[] ticketNumber_txt = getStringByte(printMiddle(context.getString(R.string.ticket_number)));
            byte[] bet_type = getStringByte(printMiddle(context.getString(R.string.bet_type)));
            byte[] bet_type_val = getStringByte(printMiddle(purchaseBean.getTransactions().get(0).getTicketType()));
            byte[] ticketNumber_val = getStringByte(printMiddle(appnedDash(purchaseBean.getTicketNo(), 4)));

            for (int i = 0; i < purchaseBean.getTransactions().size(); i++) {

                for (int j = 0; j < purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size(); j++) {
                    if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SINGLE")) {
                        print = print + printTwoStringStringDataSbs("(" + (i + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "");
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());
                        print = print + printTwoStringStringData(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        print = print + printTwoStringStringData(context.getString(R.string.possible_win), "" +   Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(purchaseBean.getTransactions().get(i).getWinnableAmount())));
                        print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus());

                        if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }
                    } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                        print = print + printTwoStringStringDataSbs("(" + (j + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "");                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());

                        if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }
                    } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                        print = print + printTwoStringStringDataSbs("(" + (j + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase() + " # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "");
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());
                        if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }
                    }
                }
                if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                    if (purchaseBean.getTransactions().get(i).getWinnableAmount() > 0) {

                        print = print + printTwoStringStringData(context.getString(R.string.possible_win), "" +   Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(purchaseBean.getTransactions().get(i).getWinnableAmount())));
                    }
                    print = print + printTwoStringStringData(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                    print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus());
                } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                    print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus());
                }
            }
            byte[] print_game_data = getStringByte(print);
            String Amount = Utils.getBalanceToViewSbs(purchaseBean.getSaleAmount(), ",", " ", 0);
            footer = footer + printTwoStringStringData(context.getString(R.string.tatal_stake_amount), "" + Amount +" "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
            byte[] footer_data = getStringByte(footer);
            byte[] retailer = getStringByte(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName());
            byte[] qrCode = ESCUtil.printBitmap(getBitmap(purchaseBean.getTicketNo(), 1, 384, 70));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, next1Line, boldOn, game_name, next1Line, boldOff, fontSize1Big,
                    purchase_time,time_val_txt, ESCUtil.boldOn(), ticketNumber_txt, ESCUtil.boldOff(),ticketNumber_val, ESCUtil.boldOn(), bet_type, ESCUtil.boldOff(), bet_type_val, line, print_game_data, footer_data, next1Line, qrCode, ticketNumber_val, next1Line, retailer, next1Line, line, next2Line, breakPartial};

            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] printVsTicket(SbsPurchaseBean purchaseBean, Context context) {
        try {
            String print = "";
            String footer = "";
            String print_amount = "";
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

            byte[] getDash = getStringByte(stingDash.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(context.getString(R.string.virtual_sports_print)));
            String time[] = purchaseBean.getDateTime().split(" ");
            byte[] purchase_time = getStringByte(printMiddle(context.getString(R.string.purchase_time)));
            byte[] time_val_txt = getStringByte(printMiddle(getPrintDateFormat(time[0]) + "  " + time[1]));
            byte[] ticketNumber_txt = getStringByte(printMiddle(context.getString(R.string.ticket_number)));
            byte[] bet_type = getStringByte(printMiddle(context.getString(R.string.bet_type)));
            byte[] bet_type_val = getStringByte(printMiddle(purchaseBean.getTransactions().get(0).getTicketType()));
            byte[] ticketNumber_val = getStringByte(printMiddle(appnedDash(purchaseBean.getTicketNo(), 4)));

            for (int i = 0; i < purchaseBean.getTransactions().size(); i++) {

                for (int j = 0; j < purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size(); j++) {
                    if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SINGLE")) {
                        print = print + printTwoStringStringDataSbs("(" + (i + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "");
                        if (purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds")||
                                purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby")||
                                purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")) {
                            print = print + printMiddle(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName());
                        } else {
                            print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        }
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());
                        print = print + printTwoStringStringData(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));

                        print = print + printTwoStringStringData(context.getString(R.string.possible_win), "" +   Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(purchaseBean.getTransactions().get(i).getWinnableAmount())));
                        print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus());

                        if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }

                    } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                        print = print + printTwoStringStringDataSbs("(" + (j + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "");
                        if (purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds")||
                                purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby")||
                                purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")) {
                            print = print + printMiddle(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName());
                        } else {
                            print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        }
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());

                        if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }
                    } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                        print = print + printTwoStringStringDataSbs("(" + (j + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "");
                        if (purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby") ||
                                purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")) {
                            print = print + printMiddle(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName());
                        } else {
                            print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        }
                        print = print + printTwoStringStringData(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());

                        if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }
                    }
                }

                if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                    if (purchaseBean.getTransactions().get(i).getWinnableAmount() > 0) {

                        print = print + printTwoStringStringData(context.getString(R.string.possible_win), "" +   Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(purchaseBean.getTransactions().get(i).getWinnableAmount())));
                    }
                    print = print + printTwoStringStringData(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                    print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus());
                } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                    print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus());
                }

            }

            byte[] print_game_data = getStringByte(print);
            String Amount = Utils.getBalanceToViewSbs(purchaseBean.getSaleAmount(), ",", " ", 0);
            footer = footer + printTwoStringStringData(context.getString(R.string.tatal_stake_amount), "" + Amount +" "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
            byte[] footer_data = getStringByte(footer);
            byte[] retailer = getStringByte(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName());
            byte[] qrCode = ESCUtil.printBitmap(getBitmap(purchaseBean.getTicketNo(), 1, 384, 70));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, next1Line, boldOn, game_name, next1Line, boldOff, fontSize1Big,
                    purchase_time,time_val_txt, ESCUtil.boldOn(), ticketNumber_txt, ESCUtil.boldOff(),ticketNumber_val, ESCUtil.boldOn(), bet_type, ESCUtil.boldOff(), bet_type_val, line, print_game_data, footer_data, next1Line, qrCode, ticketNumber_val, next1Line, retailer, next1Line, line, next2Line, breakPartial};

            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;


    }
    public static String printTwoStringStringDataSbs(String one, String two) throws
            UnsupportedEncodingException {
        StringBuffer str = new StringBuffer();

        if ((one.length() + two.length()) > getCount()) {
            str.append(one + "\n");
            str.append(two + "\n");
        } else {

            str.append(one);
            for (int i = one.length(); i < (getCount() - two.length()); i++) {
                str.append(" ");
            }
            str.append(two + "\n");
        }
        return str.toString();
    }
    public static byte[] printSbsWin(SbsWinPayResponse print_winning_bean, Context context) {
        byte[] next2Line = PrintUtil.nextLine(2);
        byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
        byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
        byte[] boldOff = PrintUtil.boldOff();
        byte[] sample__ticket_msg = new byte[0];
        byte[] fontSize2Big = PrintUtil.fontSizeSetBig(2);
        byte[] nextline = ESCUtil.nextLine(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            builder.append("_");
        }
        try {
            byte[] line = getStringByte(builder.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(context.getString(R.string.sports_betting).toUpperCase()));
            byte[] ticket_number_txt = getStringByte(printMiddle(context.getString(R.string.ticket_number)));
            byte[] ticket_number = getStringByte(printMiddle(appnedDash(print_winning_bean.getResponseData().getData().getTicketNo(), 4)));
            String draw_date_val = print_winning_bean.getResponseData().getData().getPaidDateTime().split(" ")[0];
            String draw_time_val = print_winning_bean.getResponseData().getData().getPaidDateTime().split(" ")[1];
            byte[] draw_date = getStringByte(printTwoStringStringData(context.getString(R.string.claim_date), getPrintDateFormat(draw_date_val)));
            byte[] draw_time = getStringByte(printTwoStringStringData(context.getString(R.string.claim_time), draw_time_val));
            String Amount = Utils.getBalanceToViewSbs(print_winning_bean.getResponseData().getData().getTicket().getPayoutAmount(), ",", " ", getDecimalPlacesSbs(print_winning_bean.getResponseData().getData().getTicket().getPayoutAmount()));
            byte[] win_amount = getStringByte(printTwoStringStringData(context.getString(R.string.winning_amt_colon), Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context))));
            byte[] reatiler_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), boldOn, fontSize2Big ,boldOff, ESCUtil.alignCenter(), logo, boldOn, nextline,game_name, boldOff, nextline, boldOn, boldOff, nextline, ticket_number_txt,
                    ticket_number, nextline, draw_date, draw_time, line, win_amount, nextline, reatiler_name, line, fontSize2Big, next2Line, breakPartial};
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


    public static byte[] printVsWin(SbsWinPayResponse print_winning_bean, Context context) {
        byte[] next2Line = PrintUtil.nextLine(2);
        byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
        byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
        byte[] boldOff = PrintUtil.boldOff();
        byte[] sample__ticket_msg = new byte[0];
        byte[] fontSize2Big = PrintUtil.fontSizeSetBig(2);
        String Cancel_Amount = "";
        byte[] nextline = ESCUtil.nextLine(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            builder.append("_");
        }
        try {
            byte[] line = getStringByte(builder.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.camwinlotto);

            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(context.getString(R.string.virtual_sports_print).toUpperCase()));
            byte[] ticket_number_txt = getStringByte(printMiddle(context.getString(R.string.ticket_number)));
            byte[] ticket_number = getStringByte(printMiddle(appnedDash(print_winning_bean.getResponseData().getData().getTicketNo(), 4)));
            String draw_date_val = print_winning_bean.getResponseData().getData().getPaidDateTime().split(" ")[0];
            String draw_time_val = print_winning_bean.getResponseData().getData().getPaidDateTime().split(" ")[1];
            //String draw_time_val = print_winning_bean.getResponseData().getData().getDateTime().split(" ")[0];
            byte[] draw_date = getStringByte(printTwoStringStringData(context.getString(R.string.claim_date), getPrintDateFormat(draw_date_val)));
            byte[] draw_time = getStringByte(printTwoStringStringData(context.getString(R.string.claim_time), draw_time_val));
            String Amount = Utils.getBalanceToViewSbs(print_winning_bean.getResponseData().getData().getTicket().getPayoutAmount(), ",", " ", getDecimalPlacesSbs(print_winning_bean.getResponseData().getData().getTicket().getPayoutAmount()));
            byte[] win_amount = getStringByte(printTwoStringStringData(context.getString(R.string.winning_amt_colon), Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context))));
            byte[] reatiler_name = getStringByte(printMiddle(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()));
            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), boldOn, fontSize2Big ,boldOff, ESCUtil.alignCenter(), logo, boldOn, nextline,game_name, boldOff, nextline, boldOn, boldOff, nextline, ticket_number_txt,
                    ticket_number, nextline, draw_date, draw_time, line, win_amount, nextline, reatiler_name, line, fontSize2Big, next2Line, breakPartial};
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

    public static byte[] printSbsReprint(SbsReprintResponseBean reprint_bean, Context context) {

        try {
            String print = "";
            String footer = "";
            String print_amount = "";
            byte[] next2Line = PrintUtil.nextLine(2);
            byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
            byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
            byte[] boldOff = PrintUtil.boldOff();
            byte[] nextline = ESCUtil.nextLine(1);
            byte[] bet_type_val = new byte[0];
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                builder.append("_");
            }
            byte[] line = getStringByte(builder.toString());
            StringBuilder stingDash = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                stingDash.append("-");
            }

            byte[] getDash = getStringByte(stingDash.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(context.getString(R.string.sports_betting)));
            byte[] reprint = alignHeadingSunmi(getStringByte(context.getString(R.string.reprint)));
            String time[] = reprint_bean.getResponseData().getData().getDateTime().split(" ");
            String time_val[] = time[1].split(":");
            byte[] purchase_time = getStringByte(printMiddle(context.getString(R.string.purchase_time)));
            byte[] time_val_txt = getStringByte(printMiddle(getPrintDateFormat(time[0]) + " " + time_val[0] + ":" + time_val[1]));
            byte[] ticketNumber_txt = getStringByte(printMiddle(context.getString(R.string.ticket_number)));
            byte[] ticketNumber_val = alignCenterSunmi(getStringByte(appnedDash(reprint_bean.getResponseData().getData().getTicketNo(), 4)));
            byte[] bet_type = getStringByte(printMiddle(context.getString(R.string.bet_type)));
            if (reprint_bean.getResponseData().getData().getTransactions() != null  && reprint_bean.getResponseData().getData().getTransactions().size() > 0) {
                 bet_type_val = getStringByte(printMiddle(reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType()));
            }
            for (int i = 0; i < reprint_bean.getResponseData().getData().getTransactions().size(); i++) {

                for (int j = 0; j < reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size(); j++) {
                    if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SINGLE")) {
                        print = print + printTwoStringStringDataSbs("(" + (i + 1) + ")" + " " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase(), "# " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());
                        print = print + printTwoStringStringData(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        print = print + printTwoStringStringData(context.getString(R.string.possible_win), "" +  Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount())));
                        print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getStatus());

                        if (j + 1 == reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == reprint_bean.getResponseData().getData().getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }

                    } else if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                        print = print + printTwoStringStringDataSbs("(" + (j + 1) + ")" + " " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase(), "# " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());

                        if (j + 1 == reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == reprint_bean.getResponseData().getData().getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }
                    } else if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                        print = print + printTwoStringStringDataSbs("(" + (j + 1) + ")" + " " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase(), "# " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());

                        if (j + 1 == reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == reprint_bean.getResponseData().getData().getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }
                    }
                }
                if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                    if (reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount() > 0) {
                        print = print + printTwoStringStringData(context.getString(R.string.possible_win), "" +  Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount())));
                    }
                    print = print + printTwoStringStringData(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                    print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getStatus());
                } else if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                    print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getStatus());

                }

            }

            byte[] print_game_data = getStringByte(print);
            String Amount = Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTicket().getSaleAmount(), ",", " ", 0);
            footer = footer + printTwoStringStringData(context.getString(R.string.tatal_stake_amount), "" + Amount +" "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
            byte[] footer_data = getStringByte(footer);
            byte[] retailer = getStringByte(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName());
            byte[] qrCode = ESCUtil.printBitmap(getBitmap(reprint_bean.getResponseData().getData().getTicketNo(), 1, 384, 70));

            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, next1Line, boldOn, game_name, next1Line, reprint ,boldOff, fontSize1Big,
                    next1Line, purchase_time,time_val_txt, ESCUtil.boldOn(), ticketNumber_txt, ESCUtil.boldOff(), ticketNumber_val, ESCUtil.boldOn(), next1Line, bet_type, ESCUtil.boldOff(), bet_type_val, line, print_game_data, footer_data, next1Line, qrCode, ticketNumber_val, next1Line, retailer, next1Line, line, next2Line, breakPartial};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static byte[] printVsReprint(SbsReprintResponseBean reprint_bean, Context context) {

        try {
            String print = "";
            String footer = "";
            String print_amount = "";
            byte[] next2Line = PrintUtil.nextLine(2);
            byte[] fontSize1Big = PrintUtil.fontSizeSetBig(1);
            byte[] fontSize2Small = PrintUtil.fontSizeSetSmall(3);
            byte[] boldOff = PrintUtil.boldOff();
            byte[] nextline = ESCUtil.nextLine(1);
            byte[] bet_type_val = new byte[0];
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                builder.append("_");
            }
            byte[] line = getStringByte(builder.toString());
            StringBuilder stingDash = new StringBuilder();
            for (int i = 0; i < getCount(); i++) {
                stingDash.append("-");
            }

            byte[] getDash = getStringByte(stingDash.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.camwinlotto);
            byte[] logo = ESCUtil.printBitmap(scaleBitmapAndKeepRation(bitmap, 300, 200), 0);
            byte[] next1Line = PrintUtil.nextLine(1);
            byte[] boldOn = PrintUtil.boldOn();
            byte[] game_name = alignHeadingSunmi(getStringByte(context.getString(R.string.virtual_sports_print)));
            byte[] reprint = alignHeadingSunmi(getStringByte(context.getString(R.string.reprint)));
            String time[] = reprint_bean.getResponseData().getData().getDateTime().split(" ");
            String time_val[] = time[1].split(":");
            byte[] purchase_time = getStringByte(printMiddle(context.getString(R.string.purchase_time)));
            byte[] time_val_txt = getStringByte(printMiddle(getPrintDateFormat(time[0]) + " " + time_val[0] + ":" + time_val[1]));
            byte[] ticketNumber_txt = getStringByte(printMiddle(context.getString(R.string.ticket_number)));
            byte[] ticketNumber_val = alignCenterSunmi(getStringByte(appnedDash(reprint_bean.getResponseData().getData().getTicketNo(), 4)));
            byte[] bet_type = getStringByte(printMiddle(context.getString(R.string.bet_type)));
            if (reprint_bean.getResponseData().getData().getTransactions() != null  && reprint_bean.getResponseData().getData().getTransactions().size() > 0) {
                bet_type_val = getStringByte(printMiddle(reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType()));
            }


            for (int i = 0; i < reprint_bean.getResponseData().getData().getTransactions().size(); i++) {

                for (int j = 0; j < reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size(); j++) {
                    if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SINGLE")) {
                        print = print + printTwoStringStringDataSbs("(" + (i + 1) + ")" + " " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase(), "# " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                        if (reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby")||
                                reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")){
                            print = print + printMiddle(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName());
                        }else{
                            print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        }
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());
                        print = print + printTwoStringStringData(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                        print = print + printTwoStringStringData(context.getString(R.string.possible_win), "" +  Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount())));
                        print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getStatus());

                        if (j + 1 == reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == reprint_bean.getResponseData().getData().getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }

                    } else if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                        print = print + printTwoStringStringDataSbs("(" + (j + 1) + ")" + " " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase(), "# " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                        if (reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby")||
                                reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")){
                            print = print + printMiddle(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName());
                        }else{
                            print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        }
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());

                        if (j + 1 == reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == reprint_bean.getResponseData().getData().getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }
                    } else if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                        print = print + printTwoStringStringDataSbs("(" + (j + 1) + ")" + " " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase(), "# " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                        if (reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby")||
                                reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")){
                            print = print + printMiddle(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName());
                        }else{
                            print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), "vs", reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName());
                        }
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ");
                        print = print + printTwoStringStringData(reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ");
                        print = print + printTwoStringStringData(context.getString(R.string.odds), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue());

                        if (j + 1 == reprint_bean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == reprint_bean.getResponseData().getData().getTransactions().size()) {
                            print = print + builder.toString();
                        } else {
                            print = print + stingDash.toString();
                        }
                    }
                }

                if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                    if (reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount() > 0) {
                        print = print + printTwoStringStringData(context.getString(R.string.possible_win), "" +  Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getWinnableAmount())));
                    }
                    print = print + printTwoStringStringData(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
                    print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getStatus());
                } else if (reprint_bean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                    print = print + printTwoStringStringData(context.getString(R.string.status_sbs), "" + reprint_bean.getResponseData().getData().getTransactions().get(i).getStatus());
                }

            }

            byte[] print_game_data = getStringByte(print);
            String Amount = Utils.getBalanceToViewSbs(reprint_bean.getResponseData().getData().getTicket().getSaleAmount(), ",", " ", 0);
            footer = footer + printTwoStringStringData(context.getString(R.string.tatal_stake_amount), "" + Amount +" "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)));
            byte[] footer_data = getStringByte(footer);
            byte[] retailer = getStringByte(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName());
            byte[] qrCode = ESCUtil.printBitmap(getBitmap(reprint_bean.getResponseData().getData().getTicketNo(), 1, 384, 70));

            byte[] breakPartial = PrintUtil.feedPaperCutPartial();
            byte[][] cmdBytes = {ESCUtil.alignCenter(), logo, next1Line, boldOn, game_name, next1Line, reprint ,boldOff, fontSize1Big,
                    next1Line, purchase_time,time_val_txt, ESCUtil.boldOn(), ticketNumber_txt, ESCUtil.boldOff(), ticketNumber_val, ESCUtil.boldOn(), next1Line, bet_type, ESCUtil.boldOff(), bet_type_val, line, print_game_data, footer_data, next1Line, qrCode, ticketNumber_val, next1Line, retailer, next1Line, line, next2Line, breakPartial};
            return PrintUtil.byteMerger(cmdBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void printVsSaleTps(SbsPurchaseBean purchaseBean, Context context, NoPaper listner,UsbThermalPrinter usbThermalPrinter) {
        try {
            try {
                usbThermalPrinter.start(1);
                usbThermalPrinter.reset();
            } catch (TelpoException e) {
                usbThermalPrinter.stop();
                e.printStackTrace();
            }

            if (purchaseBean != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 120, false);

                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                usbThermalPrinter.printLogo(bitmap, false);

                usbThermalPrinter.setBold(true);
                usbThermalPrinter.setFontSize(3);
                printMiddleTelpo(context.getString(R.string.virtual_sports_print),usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.setFontSize(2);
                String time[] = purchaseBean.getDateTime().split(" ");
                printMiddleTelpo(context.getString(R.string.purchase_time),usbThermalPrinter);
                printMiddleTelpo(getPrintDateFormat(time[0]) + "  " + time[1],usbThermalPrinter);
                printMiddleTelpo(context.getString(R.string.ticket_number),usbThermalPrinter);
                printMiddleTelpo(appnedDash(purchaseBean.getTicketNo(), 4),usbThermalPrinter);
                printMiddleTelpo(context.getString(R.string.bet_type),usbThermalPrinter);
                printMiddleTelpo(purchaseBean.getTransactions().get(0).getTicketType(),usbThermalPrinter);
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.printString();
                for (int i = 0; i < purchaseBean.getTransactions().size(); i++) {

                    for (int j = 0; j < purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size(); j++) {
                        if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SINGLE")) {
                            usbThermalPrinter.addString("(" + (i + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                            if (purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                    purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby") ||
                                    purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")) {
                                printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName(), usbThermalPrinter);
                            } else {
                                printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                                printMiddleTelpo("vs", usbThermalPrinter);
                                printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            }
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.possible_win), "" + Utils.getBalanceToViewSbs( purchaseBean.getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs( purchaseBean.getTransactions().get(i).getWinnableAmount())),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus(),usbThermalPrinter);

                            if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {

                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }

                        } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                            usbThermalPrinter.addString("(" + (j + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                            if (purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                    purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby") ||
                                    purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")) {
                                printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName(), usbThermalPrinter);
                            } else {
                                printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                                printMiddleTelpo("vs", usbThermalPrinter);
                                printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            }
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {

                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }
                        } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                            usbThermalPrinter.addString("(" + (j + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                            if (purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                    purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby") ||
                                    purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")) {
                                printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName(), usbThermalPrinter);
                            } else {
                                printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                                printMiddleTelpo("vs", usbThermalPrinter);
                                printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            }
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {
                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }
                        }
                    }

                    if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                        if (purchaseBean.getTransactions().get(i).getWinnableAmount() > 0) {
                            printTwoStringTelpo(context.getString(R.string.possible_win), "" + Utils.getBalanceToViewSbs( purchaseBean.getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs( purchaseBean.getTransactions().get(i).getWinnableAmount())),usbThermalPrinter);
                        }
                        printTwoStringTelpo(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                        printTwoStringTelpo(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus(),usbThermalPrinter);
                    } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                        printTwoStringTelpo(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus(),usbThermalPrinter);
                    }
                }
                usbThermalPrinter.printString();
                String Amount = Utils.getBalanceToViewSbs(purchaseBean.getSaleAmount(), ",", " ", 0);
                 printTwoStringTelpo(context.getString(R.string.tatal_stake_amount), "" + Amount + " "+Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                printBarcode(purchaseBean.getTicketNo(), usbThermalPrinter);
                printMiddleTelpo(appnedDash(purchaseBean.getTicketNo(), 4), usbThermalPrinter);
                usbThermalPrinter.addString("        ");
                printMiddleTelpo(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(),usbThermalPrinter);
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.printString();
                usbThermalPrinter.walkPaper(10);
                listner.noPaper("SUCCESS");

            }

        } catch (Exception e) {
            if (e instanceof NoPaperException) {
                Log.v("no paper", "no paper found");
            }
            Result = e.toString();
            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                new Handler(Looper.getMainLooper()).post(() -> listner.noPaper("No Paper"));
            }

        } finally {
            try {
                usbThermalPrinter.stop();
            } catch (Exception e) {
            }

        }

    }

    public static void printSbsSaleTps(SbsPurchaseBean purchaseBean, Context context, NoPaper listner,UsbThermalPrinter usbThermalPrinter) {
        try {
            try {
                usbThermalPrinter.start(1);
                usbThermalPrinter.reset();
            } catch (TelpoException e) {
                usbThermalPrinter.stop();
                e.printStackTrace();
            }

            if (purchaseBean != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 120, false);
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                usbThermalPrinter.printLogo(bitmap, false);
                usbThermalPrinter.setBold(true);
                usbThermalPrinter.setFontSize(3);
                printMiddleTelpo(context.getString(R.string.sports_betting),usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.setFontSize(2);
                String time[] = purchaseBean.getDateTime().split(" ");
                printMiddleTelpo(context.getString(R.string.purchase_time),usbThermalPrinter);
                printMiddleTelpo(getPrintDateFormat(time[0]) + "  " + time[1],usbThermalPrinter);
                printMiddleTelpo(context.getString(R.string.ticket_number),usbThermalPrinter);
                printMiddleTelpo(appnedDash(purchaseBean.getTicketNo(), 4),usbThermalPrinter);
                printMiddleTelpo(context.getString(R.string.bet_type),usbThermalPrinter);
                printMiddleTelpo(purchaseBean.getTransactions().get(0).getTicketType(),usbThermalPrinter);
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.printString();
                for (int i = 0; i < purchaseBean.getTransactions().size(); i++) {
                    for (int j = 0; j < purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size(); j++) {
                        if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SINGLE")) {
                            printTwoStringTelpo("(" + (i + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "",usbThermalPrinter);
                            printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                            printMiddleTelpo("vs", usbThermalPrinter);
                            printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.possible_win), "" + Utils.getBalanceToViewSbs( purchaseBean.getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs( purchaseBean.getTransactions().get(i).getWinnableAmount())),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus(),usbThermalPrinter);

                            if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {

                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }

                        } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                            printTwoStringTelpo("(" + (j + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "",usbThermalPrinter);
                            printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                            printMiddleTelpo("vs", usbThermalPrinter);
                            printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {

                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }
                        } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                            printTwoStringTelpo("(" + (j + 1) + ")" + " " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "",usbThermalPrinter);
                            printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                            printMiddleTelpo("vs", usbThermalPrinter);
                            printMiddleTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo(purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" + purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            if (j + 1 == purchaseBean.getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 == purchaseBean.getTransactions().size()) {
                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }
                        }
                    }

                    if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                        if (purchaseBean.getTransactions().get(i).getWinnableAmount() > 0) {
                            printTwoStringTelpo(context.getString(R.string.possible_win), "" + Utils.getBalanceToViewSbs( purchaseBean.getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs( purchaseBean.getTransactions().get(i).getWinnableAmount())),usbThermalPrinter);
                        }
                        printTwoStringTelpo(context.getString(R.string.stake), "" + Utils.getBalanceToViewSbs(purchaseBean.getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                        printTwoStringTelpo(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus(),usbThermalPrinter);
                    } else if (purchaseBean.getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                        printTwoStringTelpo(context.getString(R.string.status_sbs), "" + purchaseBean.getTransactions().get(i).getStatus(),usbThermalPrinter);
                    }

                }
                usbThermalPrinter.printString();
                String Amount = Utils.getBalanceToViewSbs(purchaseBean.getSaleAmount(), ",", " ", 0);
                printTwoStringTelpo(context.getString(R.string.tatal_stake_amount), "" + Amount + " "+Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                printBarcode(purchaseBean.getTicketNo(), usbThermalPrinter);
                printMiddleTelpo(appnedDash(purchaseBean.getTicketNo(), 4), usbThermalPrinter);
                usbThermalPrinter.addString("        ");
                printMiddleTelpo(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(),usbThermalPrinter);
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.printString();
                usbThermalPrinter.walkPaper(10);
                listner.noPaper("SUCCESS");
            }

        } catch (Exception e) {
            if (e instanceof NoPaperException) {
                Log.v("no paper", "no paper found");
            }
            Result = e.toString();
            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        listner.noPaper("No Paper");
                    }
                });

            }

        } finally {
            try {
                usbThermalPrinter.stop();
            } catch (Exception e) {
            }

        }

    }

    public static void reprintSbsTelpo(SbsReprintResponseBean purchaseBean, Context context, NoPaper listner, UsbThermalPrinter usbThermalPrinter) {
        try {
            try {
                usbThermalPrinter.start(1);
                usbThermalPrinter.reset();
            } catch (TelpoException e) {
                usbThermalPrinter.stop();
                e.printStackTrace();
            }

            if (purchaseBean != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 120, false);
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                usbThermalPrinter.printLogo(bitmap, false);
                usbThermalPrinter.addString("      ");
                usbThermalPrinter.setBold(true);
                usbThermalPrinter.setFontSize(3);
                printMiddleTelpo(context.getString(R.string.sports_betting),usbThermalPrinter);
                usbThermalPrinter.setFontSize(2);
                printMiddleTelpo(context.getString(R.string.reprint),usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                String time[] = purchaseBean.getResponseData().getData().getDateTime().split(" ");
                printMiddleTelpo(context.getString(R.string.purchase_time),usbThermalPrinter);
                printMiddleTelpo(getPrintDateFormat(time[0]) + "  " + time[1],usbThermalPrinter);
                printMiddleTelpo(context.getString(R.string.ticket_number),usbThermalPrinter);
                printMiddleTelpo(appnedDash( purchaseBean.getResponseData().getData().getTicketNo(), 4),usbThermalPrinter);
                printMiddleTelpo(context.getString(R.string.bet_type),usbThermalPrinter);
                if (purchaseBean.getResponseData().getData().getTransactions() != null && purchaseBean.getResponseData().getData().getTransactions().size() >0) {
                    printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType(), usbThermalPrinter);
                }
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.printString();
                for (int i = 0; i <  purchaseBean.getResponseData().getData().getTransactions().size(); i++) {

                    for (int j = 0; j <  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size(); j++) {
                        if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SINGLE")) {
                            printTwoStringTelpo("(" + (i + 1) + ")" + " " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "",usbThermalPrinter);
                            printMiddleTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                            printMiddleTelpo("vs", usbThermalPrinter);
                            printMiddleTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.stake), "" +  Utils.getBalanceToViewSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.possible_win), "" +  Utils.getBalanceToViewSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount())),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.status_sbs), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getStatus(),usbThermalPrinter);

                            if (j + 1 ==  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 ==  purchaseBean.getResponseData().getData().getTransactions().size()) {

                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }

                        } else if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                            printTwoStringTelpo("(" + (j + 1) + ")" + " " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "",usbThermalPrinter);
                            printMiddleTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                            printMiddleTelpo("vs", usbThermalPrinter);
                            printMiddleTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            if (j + 1 ==  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 ==  purchaseBean.getResponseData().getData().getTransactions().size()) {

                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }
                        } else if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                            printTwoStringTelpo("(" + (j + 1) + ")" + " " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId(), "",usbThermalPrinter);
                            printMiddleTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                            printMiddleTelpo("vs", usbThermalPrinter);
                            printMiddleTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            if (j + 1 ==  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 ==  purchaseBean.getResponseData().getData().getTransactions().size()) {
                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }
                        }
                    }
                    if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                        if ( purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount() > 0) {
                            printTwoStringTelpo(context.getString(R.string.possible_win), "" +  Utils.getBalanceToViewSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount())),usbThermalPrinter);
                        }
                        printTwoStringTelpo(context.getString(R.string.stake), "" +   Utils.getBalanceToViewSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                        printTwoStringTelpo(context.getString(R.string.status_sbs), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getStatus(),usbThermalPrinter);
                    } else if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                        printTwoStringTelpo(context.getString(R.string.status_sbs), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getStatus(),usbThermalPrinter);
                    }

                }
                usbThermalPrinter.printString();
                String Amount = Utils.getBalanceToViewSbs( purchaseBean.getResponseData().getData().getTicket().getSaleAmount(), ",", " ", 0);
                printTwoStringTelpo(context.getString(R.string.tatal_stake_amount), "" + Amount +" "+Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                printBarcode( purchaseBean.getResponseData().getData().getTicketNo(), usbThermalPrinter);
                printMiddleTelpo(appnedDash( purchaseBean.getResponseData().getData().getTicketNo(), 4), usbThermalPrinter);
                usbThermalPrinter.addString("        ");
                printMiddleTelpo(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(),usbThermalPrinter);
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.printString();
                usbThermalPrinter.walkPaper(10);
                listner.noPaper("SUCCESS");
            }

        } catch (Exception e) {
            if (e instanceof NoPaperException) {
                Log.v("no paper", "no paper found");
            }
            Result = e.toString();
            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        listner.noPaper("No Paper");
                    }
                });

            }
        } finally {
            try {
                usbThermalPrinter.stop();
            } catch (Exception e) {
            }
        }
    }

    public static void reprintVsTelpo(SbsReprintResponseBean purchaseBean, Context context, NoPaper listner, UsbThermalPrinter usbThermalPrinter) {
        try {
            try {
                usbThermalPrinter.start(1);
                usbThermalPrinter.reset();
            } catch (TelpoException e) {
                usbThermalPrinter.stop();
                e.printStackTrace();
            }

            if (purchaseBean != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 120, false);

                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                usbThermalPrinter.printLogo(bitmap, false);
                usbThermalPrinter.addString("      ");
                usbThermalPrinter.setBold(true);
                usbThermalPrinter.setFontSize(3);
                printMiddleTelpo(context.getString(R.string.virtual_sports_print),usbThermalPrinter);
                usbThermalPrinter.setFontSize(2);
                printMiddleTelpo(context.getString(R.string.reprint),usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                String time[] = purchaseBean.getResponseData().getData().getDateTime().split(" ");
                printMiddleTelpo(context.getString(R.string.purchase_time),usbThermalPrinter);
                printMiddleTelpo(getPrintDateFormat(time[0]) + "  " + time[1],usbThermalPrinter);
                printMiddleTelpo(context.getString(R.string.ticket_number),usbThermalPrinter);
                printMiddleTelpo(appnedDash( purchaseBean.getResponseData().getData().getTicketNo(), 4),usbThermalPrinter);
                printMiddleTelpo(context.getString(R.string.bet_type),usbThermalPrinter);
                if (purchaseBean.getResponseData().getData().getTransactions() != null && purchaseBean.getResponseData().getData().getTransactions().size() >0) {
                    printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType(), usbThermalPrinter);
                }
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.printString();
                for (int i = 0; i <  purchaseBean.getResponseData().getData().getTransactions().size(); i++) {

                    for (int j = 0; j <  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size(); j++) {
                        if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SINGLE")) {
                            usbThermalPrinter.addString("(" + (i + 1) + ")" + " " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                            if (purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                    purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby") ||
                                    purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")) {
                                printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName(), usbThermalPrinter);
                            } else {
                                printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                                printMiddleTelpo("vs", usbThermalPrinter);
                                printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            }
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.stake), "" +  Utils.getBalanceToViewSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.possible_win), "" +  Utils.getBalanceToViewSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount())),usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.status_sbs), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getStatus(),usbThermalPrinter);

                            if (j + 1 ==  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 ==  purchaseBean.getResponseData().getData().getTransactions().size()) {

                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }

                        } else if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                            usbThermalPrinter.addString("(" + (j + 1) + ")" + " " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                            if (purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                    purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby") ||
                                    purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")) {
                                printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName(), usbThermalPrinter);
                            } else {
                                printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                                printMiddleTelpo("vs", usbThermalPrinter);
                                printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            }
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            if (j + 1 ==  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 ==  purchaseBean.getResponseData().getData().getTransactions().size()) {

                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }
                        } else if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                            usbThermalPrinter.addString("(" + (j + 1) + ")" + " " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().toUpperCase()+" # " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventId());
                            if (purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Platinum hounds") ||
                                    purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Dashing Derby") ||
                                    purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getSport().equalsIgnoreCase("Motor Racing")) {
                                printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventName(), usbThermalPrinter);
                            } else {
                                printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getHomeTeamName(), usbThermalPrinter);
                                printMiddleTelpo("vs", usbThermalPrinter);
                                printMiddleTelpo(purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getAwayTeamName(), usbThermalPrinter);
                            }
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getEventDate(), "          ",usbThermalPrinter);
                            printTwoStringTelpo( purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getMarketName() + " / " +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOutcomeName(), "         ",usbThermalPrinter);
                            printTwoStringTelpo(context.getString(R.string.odds), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().get(j).getOddValue(),usbThermalPrinter);
                            if (j + 1 ==  purchaseBean.getResponseData().getData().getTransactions().get(i).getBetResponseDetailBeans().size() || i + 1 ==  purchaseBean.getResponseData().getData().getTransactions().size()) {
                                printDarkline("___________________________", usbThermalPrinter);
                                usbThermalPrinter.setBold(false);
                            } else {
                                usbThermalPrinter.addString("---------------------------");
                            }
                        }
                    }

                    if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("MULTIPLE")) {
                        if ( purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount() > 0) {
                            printTwoStringTelpo(context.getString(R.string.possible_win), "" +  Utils.getBalanceToViewSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount(), ",", " ", getDecimalPlacesSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getWinnableAmount())),usbThermalPrinter);
                        }
                        printTwoStringTelpo(context.getString(R.string.stake), "" +   Utils.getBalanceToViewSbs(purchaseBean.getResponseData().getData().getTransactions().get(i).getStake(), ",", " ", 0)+ " "+ Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                        printTwoStringTelpo(context.getString(R.string.status_sbs), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getStatus(),usbThermalPrinter);
                    } else if ( purchaseBean.getResponseData().getData().getTransactions().get(0).getTicketType().equalsIgnoreCase("SYSTEM")) {
                        printTwoStringTelpo(context.getString(R.string.status_sbs), "" +  purchaseBean.getResponseData().getData().getTransactions().get(i).getStatus(),usbThermalPrinter);
                    }

                }

                usbThermalPrinter.printString();
                String Amount = Utils.getBalanceToViewSbs( purchaseBean.getResponseData().getData().getTicket().getSaleAmount(), ",", " ", 0);
                printTwoStringTelpo(context.getString(R.string.tatal_stake_amount), "" + Amount +" "+Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                printBarcode( purchaseBean.getResponseData().getData().getTicketNo(), usbThermalPrinter);
                printMiddleTelpo(appnedDash( purchaseBean.getResponseData().getData().getTicketNo(), 4), usbThermalPrinter);
                usbThermalPrinter.addString("        ");
                printMiddleTelpo(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(),usbThermalPrinter);
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.printString();
                usbThermalPrinter.walkPaper(10);
                listner.noPaper("SUCCESS");

            }

        } catch (Exception e) {
            if (e instanceof NoPaperException) {
                Log.v("no paper", "no paper found");
            }
            Result = e.toString();
            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        listner.noPaper("No Paper");
                    }
                });

            }

        } finally {
            try {
                usbThermalPrinter.stop();
            } catch (Exception e) {
            }

        }

    }

    public static void printWinningSbs(SbsWinPayResponse print_winning_bean, Context context, NoPaper listner, UsbThermalPrinter usbThermalPrinter) {
        try {
            try {
                usbThermalPrinter.start(1);
                usbThermalPrinter.reset();
            } catch (TelpoException e) {
                usbThermalPrinter.stop();
                e.printStackTrace();
            }
            if (print_winning_bean != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 120, false);
                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                usbThermalPrinter.printLogo(bitmap, false);
                usbThermalPrinter.setBold(true);
                usbThermalPrinter.addString("   ");
                printMiddleTelpo(context.getString(R.string.sports_betting).toUpperCase(),usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                printMiddleTelpo(context.getString(R.string.ticket_number),usbThermalPrinter);
                printMiddleTelpo(appnedDash(print_winning_bean.getResponseData().getData().getTicketNo(), 4),usbThermalPrinter);
                String draw_date_val = print_winning_bean.getResponseData().getData().getPaidDateTime().split(" ")[0];
                String draw_time_val = print_winning_bean.getResponseData().getData().getPaidDateTime().split(" ")[1];
                printTwoStringTelpo(context.getString(R.string.claim_date), getPrintDateFormat(draw_date_val),usbThermalPrinter);
                printTwoStringTelpo(context.getString(R.string.claim_time), draw_time_val,usbThermalPrinter);
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                String Amount = Utils.getBalanceToViewSbs(print_winning_bean.getResponseData().getData().getTicket().getPayoutAmount(), ",", " ", getDecimalPlacesSbs(print_winning_bean.getResponseData().getData().getTicket().getPayoutAmount()));
                printTwoStringTelpo(context.getString(R.string.winning_amt_colon), Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                printMiddleTelpo(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(),usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.printString();
                usbThermalPrinter.walkPaper(10);
                listner.noPaper("SUCCESS");
            }
        } catch (Exception e) {
            if (e instanceof NoPaperException) {
                Log.v("no paper", "no paper found");
            }
            Result = e.toString();
            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        listner.noPaper("No Paper");
                    }
                });
            }
        } finally {
            try {
                usbThermalPrinter.stop();
            } catch (Exception e) {
            }

        }
    }


    public static void printWinningVs(SbsWinPayResponse print_winning_bean, Context context, NoPaper listner, UsbThermalPrinter usbThermalPrinter) {
        try {
            try {
                usbThermalPrinter.start(1);
                usbThermalPrinter.reset();
            } catch (TelpoException e) {
                usbThermalPrinter.stop();
                e.printStackTrace();
            }

            if (print_winning_bean != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camwinlotto);
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 120, false);

                usbThermalPrinter.setAlgin(usbThermalPrinter.ALGIN_MIDDLE);
                usbThermalPrinter.printLogo(bitmap, false);
                usbThermalPrinter.setBold(true);
                usbThermalPrinter.addString("   ");
                printMiddleTelpo(context.getString(R.string.virtual_sports_print).toUpperCase(),usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                printMiddleTelpo(context.getString(R.string.ticket_number),usbThermalPrinter);
                printMiddleTelpo(appnedDash(print_winning_bean.getResponseData().getData().getTicketNo(), 4),usbThermalPrinter);
                String draw_date_val = print_winning_bean.getResponseData().getData().getPaidDateTime().split(" ")[0];
                String draw_time_val = print_winning_bean.getResponseData().getData().getPaidDateTime().split(" ")[1];
                printTwoStringTelpo(context.getString(R.string.claim_date), getPrintDateFormat(draw_date_val),usbThermalPrinter);
                printTwoStringTelpo(context.getString(R.string.claim_time), draw_time_val,usbThermalPrinter);
                printDarkline("___________________________", usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                String Amount = Utils.getBalanceToViewSbs(print_winning_bean.getResponseData().getData().getTicket().getPayoutAmount(), ",", " ", getDecimalPlacesSbs(print_winning_bean.getResponseData().getData().getTicket().getPayoutAmount()));
                printTwoStringTelpo(context.getString(R.string.winning_amt_colon), Amount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)),usbThermalPrinter);
                printMiddleTelpo(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(),usbThermalPrinter);
                usbThermalPrinter.setBold(false);
                usbThermalPrinter.addString("        ");
                usbThermalPrinter.printString();
                usbThermalPrinter.walkPaper(10);
                listner.noPaper("SUCCESS");

            }

        } catch (Exception e) {
            if (e instanceof NoPaperException) {
                Log.v("no paper", "no paper found");
            }
            Result = e.toString();
            if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        listner.noPaper("No Paper");
                    }
                });

            }

        } finally {
            try {
                usbThermalPrinter.stop();
            } catch (Exception e) {
            }

        }

    }
}