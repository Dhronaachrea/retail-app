package com.skilrock.retailapp.ui.fragments.scratch;

import android.Manifest;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.ChallanResponseBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.ScanConfig;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.ReceiveBookViewModel;
import com.sunmi.scan.Config;
import com.sunmi.scan.Image;
import com.sunmi.scan.ImageScanner;
import com.sunmi.scan.Symbol;
import com.sunmi.scan.SymbolSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import sunmi.sunmiui.utils.LogUtil;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

public class PackReceiveLandscapeFragment extends BaseFragment implements View.OnClickListener,SurfaceHolder.Callback {

    private ReceiveBookViewModel receiveBookViewModel;
    private EditText etChallanNumber;
    boolean running=false;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};
    private Button buttonProceed;
    private TextInputLayout tilChallanNumber;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    ErrorDialogListener errorDialogListener = this::onDialogClosed;
    private TextView tvTitle;
    private final String SCRATCH = "scratch";
    FrameLayout contentFrame;
    private String challaneId = null;
    private SurfaceHolder mHolder;
    private SurfaceView mSurfaceView;
    private PackReceiveLandscapeFragment.AsyncDecode asyncDecode;
    private ImageScanner mImageScanner;//Claim scanner
    private Handler mAutoFocusHandler;
    private AtomicBoolean isRUN = new AtomicBoolean(false);
    private Camera mCamera;
    private boolean scanTicket=true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_receive_book_landscape, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            receiveBookViewModel = ViewModelProviders.of(this).get(ReceiveBookViewModel.class);
            MutableLiveData<ChallanResponseBean> liveDataChallan = receiveBookViewModel.getLiveDataChallan();
            liveDataChallan.observe(this, observer);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        running=true;
        mSurfaceView =  view.findViewById(R.id.surface_view);
        mAutoFocusHandler = new Handler();
        mHolder = mSurfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
        mImageScanner = new ImageScanner();//创建扫描器
        mImageScanner.setConfig(0, Config.X_DENSITY, 2);//行扫描间隔
        mImageScanner.setConfig(0, Config.Y_DENSITY, 2);//列扫描间隔
        if (SharedPrefUtil.getLanguage(master).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        initializeWidgets(view);
        setConfig();
    }

    private void setConfig() {
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.getExtras() != null) {
            //当前分辨率
            // ScanConfig.CURRENT_PPI = intent.getIntExtra("CURRENT_PPI", 0X0003);
            //扫描完成声音提示
            ScanConfig.PLAY_SOUND = intent.getBooleanExtra("PLAY_SOUND", true);
            //扫描完成震动
            ScanConfig.PLAY_VIBRATE = intent.getBooleanExtra("PLAY_VIBRATE", false);
            //识别反色二维码
            ScanConfig.IDENTIFY_INVERSE_QR_CODE = intent.getBooleanExtra("IDENTIFY_INVERSE_QR_CODE", true);
            //识别画面中多个二维码
            ScanConfig.IDENTIFY_MORE_CODE = intent.getBooleanExtra("IDENTIFY_MORE_CODE", false);
            //是否显示设置按钮
            ScanConfig.IS_SHOW_SETTING = intent.getBooleanExtra("IS_SHOW_SETTING", true);
            //是否显示选择相册按钮
            ScanConfig.IS_SHOW_ALBUM = intent.getBooleanExtra("IS_SHOW_ALBUM", true);
            //是否显示闪光灯
            ScanConfig.IS_OPEN_LIGHT = intent.getBooleanExtra("IS_OPEN_LIGHT", false);
            //是否是循环模式
            ScanConfig.SCAN_MODE = intent.getBooleanExtra("SCAN_MODE", false);

        }
    }
    Observer<ChallanResponseBean> observer =  new Observer<ChallanResponseBean>() {

        @Override
        public void onChanged(@Nullable ChallanResponseBean challanResponseBean) {
            ProgressBarDialog.getProgressDialog().dismiss();
            allowBackAction(true);

            if (challanResponseBean == null) Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_receive), getString(R.string.something_went_wrong));
            else if (challanResponseBean.getResponseCode() == 1000) {
                String dlStatus = challanResponseBean.getDlStatus();
                if (dlStatus != null) {
                    if (challanResponseBean.getGameWiseDetails().size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("MenuBean", menuBean);
                        bundle.putParcelable("ChallanResponseBean", challanResponseBean);
                        bundle.putString("ChallanNumber", etChallanNumber.getText().toString().trim());
                        bundle.putString("title", tvTitle.getText().toString().trim());
                        master.openFragment(new ChallanFragment(), "ChallanFragment", true, bundle);
                    }
                    else Toast.makeText(master, getString(R.string.no_data_found_for_this_challan), Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(master, getString(R.string.error_fetching_pack_status), Toast.LENGTH_LONG).show();

            } else {
                if (Utils.checkForSessionExpiry(master, challanResponseBean.getResponseCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(challanResponseBean.getResponseMessage()) ? getString(R.string.some_internal_error) : challanResponseBean.getResponseMessage();

                String errorMsg = Utils.getResponseMessage(master, SCRATCH, challanResponseBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_receive), errorMsg,errorDialogListener);
            }
        }




    };

    private void onDialogClosed() {
        scanTicket=true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etChallanNumber         = view.findViewById(R.id.et_ticket_number);
        buttonProceed           = view.findViewById(R.id.button_proceed);
        tilChallanNumber        = view.findViewById(R.id.textInputLayoutChallanNumber);
        tvTitle                 = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        contentFrame            = view.findViewById(R.id.content_frame);

        refreshBalance();
        etChallanNumber = view.findViewById(R.id.et_ticket_number);
        buttonProceed = view.findViewById(R.id.button_proceed);
        tilChallanNumber = view.findViewById(R.id.textInputLayoutChallanNumber);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }

            if (bundle.containsKey("challan")) {
                challaneId = bundle.getString("url");
                etChallanNumber.setText(bundle.getString("challan"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        try {
            refreshBalance();
        } catch (Exception e) {

        }
        buttonProceed.setOnClickListener(this);
        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            contentFrame.setVisibility(View.VISIBLE);
            if (!hasPermissions(getActivity(), PERMISSIONS)) {
                requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
            }
        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            contentFrame.setVisibility(View.INVISIBLE);
        }

        etChallanNumber.setOnEditorActionListener(this::onEditorAction);

        etChallanNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    etChallanNumber.setText(s);
                    etChallanNumber.setSelection(etChallanNumber.getText().length());
                }
            }
        });
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean hasPermissions(Context context, String [] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        if (validate() && isClickAllowed()) {
            Utils.vibrate(Objects.requireNonNull(getContext()));
            Utils.hideKeyboard(master);
            // getChallane();
            UrlBean urlBean;
            if(BuildConfig.app_name.equalsIgnoreCase(getString(R.string.app_name_FieldX)))
                urlBean = Utils.getUrlDetailsPackRecieveFieldx(menuBean, getContext(), "dlDetails");
            else
                urlBean = Utils.getUrlDetails(menuBean, getContext(), "dlDetails");

            if (urlBean != null) {
                allowBackAction(false);
                ProgressBarDialog.getProgressDialog().showProgress(master);

                if (challaneId == null)
                    receiveBookViewModel.callChallanApi(urlBean, etChallanNumber.getText().toString().trim(), false);
                else
                    receiveBookViewModel.callChallanApi(urlBean, challaneId, true);
            }
        }
    }

    private void startScanning() {
        final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.detach(this);
        fragmentTransaction.attach(this);
        fragmentTransaction.commit();
    }


    private void onScanned(String result) {
        etChallanNumber.setText(result);
        Utils.vibrate(master);
        scanTicket = false;
        buttonProceed.performClick();
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
                            AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), etChallanNumber);
                        }
                    }
                }
                break;
        }
    }

    private boolean validate() {
        Utils.vibrate(master);
        if (TextUtils.isEmpty(etChallanNumber.getText().toString().trim())) {
            etChallanNumber.setError(getString(R.string.enter_challan_number));
            etChallanNumber.requestFocus();
            tilChallanNumber.startAnimation(Utils.shakeError());
            return false;
        }
        return true;
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

    @Override
    public void onResume() {
        super.onResume();
        initScanConfig();
    }
    private void initScanConfig() {
        //是否开启同一幅图一次解多个条码,0表示只解一个，1为多个
        if (ScanConfig.IDENTIFY_MORE_CODE) {
            mImageScanner.setConfig(0, Config.ENABLE_MULTILESYMS, 1);
        } else {
            mImageScanner.setConfig(0, Config.ENABLE_MULTILESYMS, 0);
        }
        //是否开启识别反色二维码,0表示不识别，1为识别
        if (ScanConfig.IDENTIFY_INVERSE_QR_CODE) {
            mImageScanner.setConfig(0, Config.ENABLE_INVERSE, 1);
        } else {
            mImageScanner.setConfig(0, Config.ENABLE_INVERSE, 0);
        }
    }
    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAutoFocusHandler != null) {
            mAutoFocusHandler.removeCallbacksAndMessages(null);
            mAutoFocusHandler = null;
        }
        if (mImageScanner != null) {
            mImageScanner.destroy();
        }
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        super.onDestroy();
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
        }
        try {
            setCameraParameters();
//            mCamera.setDisplayOrientation(90);//竖屏显示
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(previewCallback);
            mCamera.startPreview();
            mCamera.autoFocus(autoFocusCallback);
        } catch (Exception e) {
            LogUtil.e("DBG", "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (scanTicket) {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = parameters.getPreviewSize();//获取预览分辨率

                //创建解码图像，并转换为原始灰度数据，注意图片是被旋转了90度的
                Image source = new Image(size.width, size.height, "Y800");
                //图片旋转了90度，将扫描框的TOP作为left裁剪
                source.setData(data);//填充数据
                if (running) {
                    asyncDecode = new PackReceiveLandscapeFragment.AsyncDecode();
                    asyncDecode.execute(source);//调用异步执行解码
                }
            }
        }
    };

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            if (mAutoFocusHandler != null) {
                mAutoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        }
    };

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (null == mCamera || null == autoFocusCallback) {
                return;
            }
            mCamera.autoFocus(autoFocusCallback);
        }
    };

    private void setCameraParameters() {
        if (mCamera == null) return;
        //摄像头预览分辨率设置和图像放大参数设置，非必须，根据实际解码效果可取舍
        Camera.Parameters parameters = mCamera.getParameters();

        WindowManager manager = (WindowManager) master.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        ScanConfig.BEST_RESOLUTION = getBestCameraResolution(parameters, new Point(display.getWidth(), display.getHeight()));
        parameters.setPreviewSize(320, 240);
//        parameters.set("orientation", "portrait");
//        parameters.set("zoom", String.valueOf(27 / 10.0));//放大图像2.7倍
        mCamera.setParameters(parameters);
    }

    private Point getBestCameraResolution(Camera.Parameters parameters, Point screenResolution) {
        float tmp = 0f;
        float mindiff = 100f;
        float x_d_y = (float) screenResolution.x / (float) screenResolution.y;
        Camera.Size best = null;
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size s : supportedPreviewSizes) {
            tmp = Math.abs(((float) s.height / (float) s.width) - x_d_y);
            if (tmp < mindiff) {
                mindiff = tmp;
                best = s;
            }
        }
        return new Point(best.width, best.height);
    }

    private class AsyncDecode extends AsyncTask<Image, Void, ArrayList<HashMap<String, String>>> {

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Image... params) {

            final ArrayList<HashMap<String, String>> result = new ArrayList<>();
            Image src_data = params[0];//获取灰度数据
            //解码，返回值为0代表失败，>0表示成功
            final int data = mImageScanner.scanImage(src_data);
            if (data != 0) {
                //  playBeepSoundAndVibrate();//解码成功播放提示音
                SymbolSet syms = mImageScanner.getResults();//获取解码结果
                for (Symbol sym : syms) {
                    HashMap<String, String> temp = new HashMap<>();
                    temp.put(ScanConfig.TYPE, sym.getSymbolName());
                    temp.put(ScanConfig.VALUE, sym.getResult());
                    result.add(temp);
                    if (!ScanConfig.IDENTIFY_MORE_CODE) {
                        break;
                    }
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);

            if (!result.isEmpty()) {

                if (ScanConfig.SCAN_MODE) {
                    master.runOnUiThread(() -> handleResult(result));

                } else {
                    onScanned(result.get(0).get("VALUE"));
                  /*  setConfig();
                    init();*/
                }
            } else {
                isRUN.set(false);
            }
        }

    }

    private void handleResult(ArrayList<HashMap<String, String>> result) {

    }
  
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        running=false;
    }

}
