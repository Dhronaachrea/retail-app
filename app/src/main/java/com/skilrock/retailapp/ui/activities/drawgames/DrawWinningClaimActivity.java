package com.skilrock.retailapp.ui.activities.drawgames;

import android.Manifest;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.PayPwtRequestBean;
import com.skilrock.retailapp.models.drawgames.PayPwtResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketVerificationGameResponseBean;
import com.skilrock.retailapp.models.drawgames.VerifyGameTicketRequest;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;
import com.skilrock.retailapp.viewmodels.drawgames.DrawWinningClaimViewModel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import static com.skilrock.retailapp.utils.printer.ESCUtil.LF;

public class DrawWinningClaimActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTicketNumber;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private DrawWinningClaimViewModel viewModel;
    byte [] bytes=null;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private Button buttonProceed;
    private TextView tvMessage;
    private TextInputLayout tilTicketNumber;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_winnig_claim);
        initializeWidget();
        if (AppPermissions.checkPermission(DrawWinningClaimActivity.this)) startScanning();
        else AppPermissions.requestPermission(DrawWinningClaimActivity.this);
    }

    private void initializeWidget() {
        viewModel   = ViewModelProviders.of(this).get(DrawWinningClaimViewModel.class);
        viewModel.getliveVerifyData().observe(this, observerTicketVerify);
        viewModel.getlivePayPwt().observe(this, observerPayPwt);

        etTicketNumber  = findViewById(R.id.et_ticket_number);
        scannerView     = findViewById(R.id.scanner_view);
        buttonProceed   = findViewById(R.id.button_proceed);
        tilTicketNumber = findViewById(R.id.textInputLayoutTicketNumber);
        tvMessage       = findViewById(R.id.tvMessage);

        if (getIntent().getExtras() != null)
            menuBean = getIntent().getExtras().getParcelable("menuBean");

        buttonProceed.setOnClickListener(DrawWinningClaimActivity.this);
    }

    private void startScanning() {
        if (Camera.getNumberOfCameras() <= 0) {
            Toast.makeText(DrawWinningClaimActivity.this, "Camera not found", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mCodeScanner = new CodeScanner(DrawWinningClaimActivity.this, scannerView);
            mCodeScanner.setDecodeCallback(result -> DrawWinningClaimActivity.this.runOnUiThread(() -> onScanned(result)));
            scannerView.setOnClickListener(view1 -> {
                if (mCodeScanner != null) mCodeScanner.startPreview();
                etTicketNumber.setText("");
            });
        }catch (Exception e){
            Toast.makeText(DrawWinningClaimActivity.this, "Unable to open scanner", Toast.LENGTH_SHORT).show();
        }
    }

    private void onScanned(Result result) {
        etTicketNumber.setText(result.getText());
        Utils.vibrate(DrawWinningClaimActivity.this);
        buttonProceed.performClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION GRANTED
                    //AudioManager audioManager = (AudioManager)master.getSystemService(Context.AUDIO_SERVICE);
                    //if (!audioManager.isWiredHeadsetOn())
                    startScanning();
                } else {
                    // PERMISSION DENIED
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(DrawWinningClaimActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(DrawWinningClaimActivity.this, getString(R.string.allow_permission), DrawWinningClaimActivity.this, etTicketNumber);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_proceed:

                if (buttonProceed.getText().toString().equalsIgnoreCase("verify")) {
                    if (!etTicketNumber.getText().toString().isEmpty())
                        callTicketVerify(etTicketNumber.getText().toString());
                } else if (buttonProceed.getText().toString().equalsIgnoreCase("claim")) {
                    claimTicket(etTicketNumber.getText().toString());
                } else if (buttonProceed.getText().toString().equalsIgnoreCase("close")) {
                    DrawWinningClaimActivity.this.finish();
                }
                break;
        }
    }

    private void claimTicket(String ticket) {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText( this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(menuBean, this, "claimWin");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            PayPwtRequestBean model = new PayPwtRequestBean();
            model.setMerchantCode(urlBean.getUserName());
            model.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            model.setUserName(PlayerData.getInstance().getUsername());
            model.setTicketNumber(ticket);
            model.setInterfaceType("WEB");
            model.setSaleMerCode("NDGE");
            model.setVerificationCode("54564456415");

            viewModel.callPayPwtAPI(urlBean, model);
        }

    }

    private void callTicketVerify(String ticketNumber) {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText( this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(menuBean, this, "verifyTicket");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            VerifyGameTicketRequest model = new VerifyGameTicketRequest();
            model.setMerchantCode(urlBean.getUserName());
            model.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            model.setUserName(PlayerData.getInstance().getUsername());
            model.setTicketNumber(ticketNumber);

            viewModel.callTicketVerificationAPI(urlBean, model);
        }
    }

    Observer<TicketVerificationGameResponseBean> observerTicketVerify = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null)
            Utils.showCustomErrorDialog(this, "Ticket Verification", getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {

            if (response.getResponseData().getTotalPay() != null && Double.parseDouble(response.getResponseData().getTotalPay()) > 0.0) {
                buttonProceed.setText("Claim");
                etTicketNumber.setEnabled(false);
                tvMessage.setText("Your total Wining is \n " + response.getResponseData().getTotalPay());
            } else if(Double.parseDouble(response.getResponseData().getTotalPay()) <= 0.0) {
                tvMessage.setText("There is no winning");
                buttonProceed.setText("Close");
            }
        } else {
            //if (Utils.checkForSessionExpiry(this, response.getResponseCode())) return;

            String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
            Utils.showCustomErrorDialog(this, "Ticket Verification", errorMsg);
        }
    };

    Observer<PayPwtResponseBean> observerPayPwt = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null)
            Utils.showCustomErrorDialog(this, "Pay Pwt", getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            setPrintData(response);
        } else {
            //if (Utils.checkForSessionExpiry(this, response.getResponseCode())) return;

            String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
            Utils.showCustomErrorDialog(this, "Pay Pwt", errorMsg);
        }
    };

    private void setPrintData(PayPwtResponseBean responseBean) {
        FiveByNinetySaleResponseBean fiveByNinetySaleResponseBean = new FiveByNinetySaleResponseBean();

        ArrayList<FiveByNinetySaleResponseBean.ResponseData.DrawData> drawDataArrayList = new ArrayList<>();
        ArrayList<FiveByNinetySaleResponseBean.ResponseData.PanelData> panelData = new ArrayList<>();

        for (PayPwtResponseBean.DrawDatum drawDatum : responseBean.getResponseData().getDrawData()) {

            if (drawDatum.getWinCode() == 2001) {
                FiveByNinetySaleResponseBean.ResponseData.DrawData drawData = new FiveByNinetySaleResponseBean.ResponseData.DrawData();
                drawData.setDrawDate(drawDatum.getDrawDate());
                drawData.setDrawTime(drawDatum.getDrawTime());
                drawDataArrayList.add(drawData);
            }
        }

        if (drawDataArrayList.isEmpty()) {
            buttonProceed.setText("Close");
            tvMessage.setText("DONE");
            return;
        }

        for (PayPwtResponseBean.PanelDatum panelDatum : responseBean.getResponseData().getPanelData()) {
            FiveByNinetySaleResponseBean.ResponseData.PanelData panelData1 = new FiveByNinetySaleResponseBean.ResponseData.PanelData();
            panelData1.setBetType(panelDatum.getBetType());
            panelData1.setPickType(panelDatum.getPickType());
            panelData1.setPickConfig(panelDatum.getPickConfig());
            panelData1.setBetAmountMultiple(panelDatum.getBetAmountMultiple());
            panelData1.setQuickPick(panelDatum.getQuickPick());
            panelData1.setPickedValues(panelDatum.getPickedValues());
            panelData1.setQpPreGenerated(panelDatum.getQpPreGenerated());
            panelData1.setNumberOfLines(panelDatum.getNumberOfLines());
            panelData1.setUnitCost(panelDatum.getUnitCost());
            panelData1.setPanelPrice(panelDatum.getPanelPrice());
            panelData1.setPlayerPanelPrice(panelDatum.getPlayerPanelPrice());
            panelData.add(panelData1);
        }

        FiveByNinetySaleResponseBean.ResponseData responseData = new FiveByNinetySaleResponseBean.ResponseData();
        responseData.setTicketNumber(responseBean.getResponseData().getTicketNumber());
        responseData.setGameName(responseBean.getResponseData().getGameName());
        responseData.setPanelData(panelData);
        responseData.setDrawData(drawDataArrayList);
        fiveByNinetySaleResponseBean.setResponseData(responseData);
        print(fiveByNinetySaleResponseBean, "");
    }

    public void print(FiveByNinetySaleResponseBean bean, String saleTime) {
        //bytes = PrintUtil.getPrintDataDgeBonus(bean, saleTime, this);
        sendData(bytes, bean);
    }

    private void sendData(final byte[] send, FiveByNinetySaleResponseBean bean) {
        AidlUtil.getInstance().sendRawData(send);

        int encode = 8;
        int position = 1;

        AidlUtil.getInstance().sendRawData(nextLine(1));
        String barcode = bean.getResponseData().getTicketNumber();
        AidlUtil.getInstance().printBitmap(getBitmap(barcode, 1, 384, 90));
        byte[] breakPartial = PrintUtil.feedPaperCutPartial();
        try {
            byte[] ticknumber = getStringByte(bean.getResponseData().getTicketNumber());
            byte[][] cmdBytes = {ticknumber, breakPartial};
            byte[] data = byteMerger(cmdBytes);
            AidlUtil.getInstance().sendRawData(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    private static byte[] getStringByte(String byteString) throws UnsupportedEncodingException {
        return byteString.getBytes("gb2312");
    }

    public static byte[] nextLine(int lineNum) {
        byte[] result = new byte[lineNum];
        for (int i = 0; i < lineNum; i++) {
            result[i] = LF;
        }

        return result;
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

    public Bitmap getBitmap(String barcode, int barcodeType, int width, int height) {
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
                return BarcodeFormat.CODE_93;
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

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

}
