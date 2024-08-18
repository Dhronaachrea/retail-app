package com.skilrock.retailapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.internet_speed_testing.InternetSpeedBuilder;
import com.example.internet_speed_testing.ProgressionModel;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.EventListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FloatingViewService;
import com.skilrock.retailapp.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class InternetSpeedDialog extends Dialog implements
        View.OnClickListener, AppConstants {

    private Activity context;
    private Button btnDone, btnClose;
    private String selectedLanguage;
    private EventListener listener;
    private CustomGauge gauge1;
    int i = 0;
    private long mLastRxBytes = 0;
    private long mLastTxBytes = 0;
    private int simCount = 1;
    private long mLastTime = 0;
    private InternetSpeed mSpeed;
    private TextView tvSpeed, tvManf;
    private InternetSpeedBuilder builder;
    final private Handler mHandler = new Handler();

    public InternetSpeedDialog(Activity activity, EventListener listener) {
        super(activity);
        this.context        = activity;
        this.listener       = listener;
        selectedLanguage    = "";
        mSpeed = new InternetSpeed(context);

        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_internet_speed);
        setCanceledOnTouchOutside(true);
        initializeWidgets();
    }

    public void updateDialog() {
        checkNetworkOperator();
        context.stopService(new Intent(context, FloatingViewService.class));
    }

    private void initializeWidgets() {
        btnDone = findViewById(R.id.btn_done);
        btnClose = findViewById(R.id.btn_close);
        gauge1 = findViewById(R.id.gauge1);
        tvSpeed = findViewById(R.id.tv_speed);
        tvManf = findViewById(R.id.tv_manf);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        mSpeed.setIsSpeedUnitBits(true);

        btnDone.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        mHandler.post(mHandlerRunnable);
        checkNetworkOperator();
        //startDownloading();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mHandler.removeCallbacks(mHandlerRunnable);
        builder = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.btn_done:
                try {
                    context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                    context.startService(new Intent(context, FloatingViewService.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_close:
                InternetSpeedDialog.this.cancel();
                break;
        }
    }

    private boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        }
        return false;
    }

    private void checkNetworkOperator() {
        TelephonyManager tManager = (TelephonyManager) context.getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        String carrierName = tManager.getNetworkOperatorName();
        simCount = tManager.getPhoneCount();


       /* if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            Log.i("LOG", "Defualt device ID " + tManager.getDeviceId());
            Log.i("LOG", "Single 1 "+tManager.getDeviceId(0));
            Log.i("LOG", "Single 2 " + tManager.getDeviceId(1));

        }
*/
        if (!isWifiConnected())
            tvManf.setText(carrierName.toUpperCase());
        else
            tvManf.setText(context.getString(R.string.connected_through_wifi));
    }

    private final Runnable mHandlerRunnable = new Runnable() {
        @Override
        public void run() {
            long currentRxBytes = TrafficStats.getTotalRxBytes();
            long currentTxBytes = TrafficStats.getTotalTxBytes();
            long usedRxBytes = currentRxBytes - mLastRxBytes;
            long usedTxBytes = currentTxBytes - mLastTxBytes;
            long currentTime = System.currentTimeMillis();
            long usedTime = currentTime - mLastTime;

            mLastRxBytes = currentRxBytes;
            mLastTxBytes = currentTxBytes;
            mLastTime = currentTime;

            mSpeed.calcSpeed(usedTime, usedRxBytes, usedTxBytes);

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());

            NetworkInfo tt = cm.getActiveNetworkInfo();



            Log.i("TaG", String.valueOf(tt.getSubtype()));

            /*// DownSpeed in MBPS
            int downSpeed = ((nc.getLinkDownstreamBandwidthKbps())/1000);

            // UpSpeed  in MBPS
            int upSpeed = ((nc.getLinkUpstreamBandwidthKbps())/1000);

            Log.i("TaG","Up Speed: " + upSpeed + "Mbps \nDown Speed: " + downSpeed + " Mbps");*/

            try {
                gauge1.setValue((int) Double.parseDouble(mSpeed.total.speedValue));
                if (mSpeed.total.speedUnit.contains("Mb") || mSpeed.total.speedUnit.contains("MB")) {
                    gauge1.setEndValue(100);
                    gauge1.setPointStartColor(context.getColor(R.color.green_bright));
                    gauge1.setPointEndColor(context.getColor(R.color.green_bright));
                } else {
                    if (gauge1.getValue() < 200) {
                        gauge1.setPointStartColor(context.getColor(R.color.bright_red));
                        gauge1.setPointEndColor(context.getColor(R.color.bright_red));
                    } else {
                        gauge1.setPointStartColor(context.getColor(R.color.colorDrawPickYellow));
                        gauge1.setPointEndColor(context.getColor(R.color.colorDrawPickYellow));
                    }
                    gauge1.setEndValue(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvSpeed.setText(mSpeed.total.speedValue + " " + mSpeed.total.speedUnit);
            mHandler.postDelayed(this, 1000);
        }
    };

    private void startDownloading() {
        builder = new InternetSpeedBuilder(context);
        builder.setOnEventInternetSpeedListener(new InternetSpeedBuilder.OnEventInternetSpeedListener() {
            @Override
            public void onDownloadProgress(int i, @NotNull ProgressionModel progressModel) {
                Log.d("DOWNLOAD", "onDownloadProgress " + i);

            }

            @Override
            public void onUploadProgress(int i, @NotNull ProgressionModel progressionModel) {
            }

            @Override
            public void onTotalProgress(int i, @NotNull ProgressionModel progressionModel) {
                Log.d("DOWNLOAD", "onTotalProgress " + i);

            }
        });
        builder.start("https://uat-content.camwinlotto.com/Download_Temp/test.7z", 1);
    }
}
