package com.skilrock.retailapp.ui.fragments.scratch;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.PackReturnDialog;
import com.skilrock.retailapp.interfaces.PackReturnListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.GameListBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseBean;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseHomeBean;
import com.skilrock.retailapp.models.scratch.SubmitPackReturnResponseBean;
import com.skilrock.retailapp.models.scratch.SubmitReturnRequestBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.PackReturnMultipleScanningViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class PackReturnMultiScanningFragment extends BaseFragment implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private final String SCRATCH = "scratch";
    private final Handler handler = new Handler();
    public boolean isMaxLimitReached = false;
    private PackReturnMultipleScanningViewModel viewModel;
    private EditText etPackNumber;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private Button buttonProceed;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private ReturnChallanResponseBean returnChallanBean;
    private TextView tvScanCount, tvViewPacks;
    private TextInputLayout tilPackNumber;
    private LinearLayout linearLayout;
    //private ArrayList<String> listScannedPacks = new ArrayList<>();
    private ReturnChallanResponseHomeBean packReturnBean;
    private String CHALLAN_NUMBER = "";
    private GameListBean gameListBean;
    private LinkedHashMap<String, Integer> hashMapPacks = new LinkedHashMap<>();
    private LinkedHashMap<String, Boolean> hashMapSelectedPacks = new LinkedHashMap<>();
    private PackReturnDialog packReturnDialog;
    private LinkedHashMap<String, Integer> mapPackAndGameId = new LinkedHashMap<>();
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};

    Observer<SubmitPackReturnResponseBean> observerSubmitReturn = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        allowBackAction(true);

        if (mCodeScanner != null)
            mCodeScanner.stopPreview();

        if (response == null)
            Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_return_error), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 1000) {
            String msg = getString(R.string.pack_return_success);
            Utils.showCustomSuccessDialog(getContext(), getFragmentManager(), "", msg, 2);
        } else if (response.getResponseCode() == 1250) {
            String errorMsg = Utils.getResponseMessage(master, SCRATCH, response.getResponseCode());
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.pack_return_error), errorMsg, 1, getFragmentManager());
        } else {
            if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                return;

            if (response.getInvalidInventory() != null) {
                ArrayList<String> bookList = response.getInvalidInventory().get(0).getBookList();
                String errorMsg = getString(R.string.invalid_pack);
                for (String pack : bookList) {
                    errorMsg = errorMsg + pack + "\n";
                }
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_return_error), errorMsg);
            } else {
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.pack_return_error), errorMsg);
            }
        }
    };

    Observer<ReturnChallanResponseBean> observer = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        allowBackAction(true);

        Handler handler = new Handler();
        if (!BuildConfig.app_name.equalsIgnoreCase("fieldx"))
            if (mCodeScanner != null)
                handler.postDelayed(() -> mCodeScanner.stopPreview(), 500);


        if (response == null)
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.pack_return_error), getString(R.string.something_went_wrong), 1, getFragmentManager());
        else if (response.getResponseCode() == 1000) {
            if (response.getResponse().getGameDetails() == null || response.getResponse().getGameDetails().isEmpty())
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.no_data_found), getString(R.string.something_went_wrong), 1, getFragmentManager());
            else {
                returnChallanBean = response;
                createPackList();
                setScanCount();
            }
        } else {
            if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                return;

            if (response.getResponseCode() == 1011) {
                tvViewPacks.setVisibility(View.INVISIBLE);
                tvScanCount.setVisibility(View.INVISIBLE);
            }

            String errorMsg = Utils.getResponseMessage(master, SCRATCH, response.getResponseCode());
            Utils.showCustomErrorDialogPop(getContext(), getString(R.string.pack_return_error), errorMsg, 1, getFragmentManager());
        }
    };
    private boolean showSnackBar = true;
    private String retailer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pack_return_multi_scanning, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            viewModel = ViewModelProviders.of(this).get(PackReturnMultipleScanningViewModel.class);
            viewModel.getLiveDataPackReturnSubmit().observe(this, observerSubmitReturn);
            viewModel.getReturnListData().observe(this, observer);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (SharedPrefUtil.getLanguage(master).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            master.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        initializeWidgets(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etPackNumber = view.findViewById(R.id.et_ticket_number);
        scannerView = view.findViewById(R.id.scanner_view);
        if (android.os.Build.MODEL.equalsIgnoreCase("indyterm700"))
            scannerView.setVisibility(View.GONE);
        buttonProceed = view.findViewById(R.id.button_proceed);
        tvScanCount = view.findViewById(R.id.tvScanCount);
        tvViewPacks = view.findViewById(R.id.tvViewPacks);
        linearLayout = view.findViewById(R.id.container);
        tilPackNumber = view.findViewById(R.id.tilPackNumber);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);

        refreshBalance();
        //listScannedPacks.clear();
        buttonProceed.setOnClickListener(this);
        tvViewPacks.setOnClickListener(this);
        if (!android.os.Build.MODEL.equalsIgnoreCase("indyterm700")) {
            if (BuildConfig.app_name.equalsIgnoreCase("FieldX")) {
                if (!hasPermissions(getActivity(), PERMISSIONS)) {
                    requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
                }else {
                    startScanning();
                }
            } else {
                if (AppPermissions.checkPermission(getActivity())) startScanning();
                else AppPermissions.requestPermission(getFragmentManager());
            }
        }
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
                CHALLAN_NUMBER = bundle.getString("ChallanNumber");
            }
            menuBean = bundle.getParcelable("MenuBean");
            packReturnBean = bundle.getParcelable("ReturnBook");
            retailer = bundle.getString("retailer");
            //ReturnChallanResponseHomeBean = bundle.getParcelable("MenuBean");
            //returnChallanBean = bundle.getParcelable("ReturnBook");
        }

        Utils.hideKeyboard(master);
        UrlBean urlGameList;
        urlGameList = Utils.getUrlDetails(menuBean, getContext(), "getGameWiseInventory");

        if (urlGameList != null) {
            allowBackAction(false);
            ProgressBarDialog.getProgressDialog().showProgress(master);
            viewModel.callReturnListApi(urlGameList, PlayerData.getInstance().getUsername(), retailer, CHALLAN_NUMBER);
        }

        etPackNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPackNumber.getText().toString().length() == 9) {
                    Utils.vibrate(master);
                    handler.postDelayed(() -> selectPack(etPackNumber.getText().toString()), 400);

                }
            }
        });
    }

    private void createPackList() {
        ArrayList<ReturnChallanResponseBean.Response.GameDetail> gameDetails = returnChallanBean.getResponse().getGameDetails();
        for (ReturnChallanResponseBean.Response.GameDetail gameDetail : gameDetails) {
            int gameId = gameDetail.getGameId();
            for (String pack : gameDetail.getBookList()) {
                mapPackAndGameId.put(pack.replaceAll("-", ""), gameId);
                hashMapPacks.put(pack, gameId);
                hashMapSelectedPacks.put(pack, false);
            }
        }
    }

    private void setScanCount() {
        String scanCount;

        if (getSelectedPacks() < 2)
            scanCount = getSelectedPacks() + " " + getString(R.string.pack) + " " + getTotalPackCount(); //hashMapSelectedPacks.size()
        else
            scanCount = getSelectedPacks() + " " + getString(R.string.packs) + " " + getTotalPackCount(); //hashMapSelectedPacks.size()

        /*if (getSelectedPacks() == getTotalPackCount()) {
            showToast("Maximum Pack ");
            //packReturnSubmitApi();
        }*/

        tvScanCount.setText(scanCount);
    }

    private int getTotalPackCount() {
        int count = 0;
        for (ReturnChallanResponseHomeBean.Game game : packReturnBean.getGames()) {
            count = count + game.getBooksQuantity();
        }
        return count;
    }

    public void selectPack(String packNumber) {
        if (hashMapSelectedPacks.isEmpty()) {
            return;
        }

        /*if (getSelectedPacks() == getTotalPackCount()) {
            showToast(getString(R.string.max_pack_limit_reached));
            isMaxLimitReached = true;
            return;
        }*/

        int packListLength = hashMapSelectedPacks.entrySet().size();
        int index = 0;
        for (Map.Entry<String, Boolean> entry : hashMapSelectedPacks.entrySet()) {
            String key = entry.getKey();
            boolean value = entry.getValue();

            if (key.replace("-", "").equalsIgnoreCase(packNumber.replace("-", ""))) {
                if (value) {
                    //Toast.makeText(master, master.getString(R.string.pack_already_selected), Toast.LENGTH_SHORT).setGravity(Gravity.CENTER, 0, 0).show();
                    showToast(master.getString(R.string.pack_already_selected));
                    return;
                }
                if (getGameId(packNumber) == -9999) {
                    //Toast.makeText(master, master.getString(R.string.invalid_pack_colon) + " " + packNumber, Toast.LENGTH_SHORT).show();
                    etPackNumber.setText("");
                    showToast(master.getString(R.string.invalid_pack_colon) + " " + packNumber);
                    return;
                } else if (getPackCount(getGameId(packNumber)) == -100) {
                    //Toast.makeText(master, master.getString(R.string.invalid_pack_colon) + " " + packNumber, Toast.LENGTH_SHORT).show();
                    etPackNumber.setText("");
                    showToast(master.getString(R.string.invalid_pack_colon) + " " + packNumber);
                    return;
                } else if (getSelectedPacks(getGameId(packNumber)) < getPackCount(getGameId(packNumber))) {
                    hashMapSelectedPacks.put(key, true);
                    //Toast.makeText(master, master.getString(R.string.pack_selected) + ": " + key, Toast.LENGTH_SHORT).show();
                    showToast(master.getString(R.string.pack_selected) + ": " + key);
                    //pulsateAnimation();
                    break;
                } else {
                    maximumPackSelectionMessage(getGameId(packNumber));
                    return;
                }
            }
            index++;
        }

        if (index == packListLength) {
            //Toast.makeText(master, master.getString(R.string.invalid_pack_colon) + " " + packNumber, Toast.LENGTH_SHORT).show();
            etPackNumber.setText("");
            showToast(master.getString(R.string.invalid_pack_colon) + " " + packNumber);
        }
        setScanCount();
    }

    private int getSelectedPacks(int gameId) {
        int counter = 0;
        for (Map.Entry<String, Boolean> entry : hashMapSelectedPacks.entrySet()) {
            String key = entry.getKey();
            if (getGameId(key) == gameId && entry.getValue()) {
                counter++;
            }
        }
        return counter;
    }

    private int getGameId(String packNumber) {
        packNumber = packNumber.replaceAll("-", "");
        if (mapPackAndGameId.containsKey(packNumber)) {
            return mapPackAndGameId.get(packNumber);
        }
        return -9999;
        /*for (Map.Entry<String, Integer> entry : hashMapPacks.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (key.replaceAll("-", "").equalsIgnoreCase(packNumber.replaceAll("-", ""))) {
                return value;
            }
        }
        return -9999;*/
    }

    private void maximumPackSelectionMessage(int gameId) {
        for (ReturnChallanResponseHomeBean.Game game : packReturnBean.getGames()) {
            if (game.getGameId() == gameId) {
                //Toast.makeText(master, String.format(getString(R.string.max_pack_selection_msg), game.getBooksQuantity(), game.getGameNumber()), Toast.LENGTH_SHORT).show();
                showToast(String.format(getString(R.string.max_pack_selection_msg), game.getBooksQuantity(), game.getGameNumber()));
                return;
            }
        }
    }

    private int getSelectedPacks() {
        int count = 0;
        if (hashMapSelectedPacks.isEmpty()) return count;

        for (Map.Entry<String, Boolean> entry : hashMapSelectedPacks.entrySet()) {
            if (entry.getValue()) {
                count++;
            }
        }

        return count;
    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(master);
        switch (view.getId()) {
            case R.id.button_proceed:
                packReturnSubmitApi();
                break;
            case R.id.tvViewPacks:
                if (mCodeScanner != null)
                    mCodeScanner.stopPreview();

                PackReturnListener listener = hashMap -> {
                    hashMapSelectedPacks = hashMap;
                    setScanCount();
                    if (mCodeScanner != null)
                        mCodeScanner.startPreview();
                };
                if (packReturnDialog == null || !packReturnDialog.isShowing()) {
                    packReturnDialog = new PackReturnDialog(master, hashMapSelectedPacks, listener, this);
                    packReturnDialog.show();
                    if (packReturnDialog.getWindow() != null) {
                        packReturnDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        packReturnDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                }
                break;
        }
    }

    private void packReturnSubmitApi() {
        if (mCodeScanner != null)
            mCodeScanner.stopPreview();

        if (packReturnDialog != null && packReturnDialog.isShowing())
            packReturnDialog.dismiss();

        Log.i("log", "Map: " + hashMapSelectedPacks);
        HashMap<String, Integer> map = new HashMap<>();
        for (Object o : hashMapSelectedPacks.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            System.out.println(pair.getKey() + ": " + pair.getValue());
            if (Boolean.parseBoolean(pair.getValue().toString()))
                map.put(pair.getKey().toString(), hashMapPacks.get(pair.getKey().toString()));
        }

        if (!map.isEmpty()) {
            SubmitReturnRequestBean model = new SubmitReturnRequestBean();
            model.setUserName(PlayerData.getInstance().getUsername());
            model.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            model.setDlChallanNumber(CHALLAN_NUMBER);
            model.setRetUserName(retailer);


            HashSet<Integer> set = new HashSet<>(map.values());
            ArrayList<Integer> listGameId = new ArrayList<>(set);

            ArrayList<SubmitReturnRequestBean.PacksToReturn> listPacksToReturns = new ArrayList<>();
            for (int gameId : listGameId) {
                SubmitReturnRequestBean.PacksToReturn packsToReturn = new SubmitReturnRequestBean.PacksToReturn();
                packsToReturn.setGameId(gameId);
                ArrayList<String> packList = new ArrayList<>();
                for (Object object : map.entrySet()) {
                    Map.Entry pair = (Map.Entry) object;
                    System.out.println(pair.getKey() + ": " + pair.getValue());
                    if (gameId == Integer.parseInt(pair.getValue().toString()))
                        packList.add(pair.getKey().toString());
                }
                packsToReturn.setBookList(packList);
                listPacksToReturns.add(packsToReturn);
            }
            model.setPacksToReturn(listPacksToReturns);
            Log.w("log", "Model: " + model);
            allowBackAction(false);
            ProgressBarDialog.getProgressDialog().showProgress(master);
            UrlBean urlBean;
            urlBean = Utils.getUrlDetails(menuBean, getContext(), "packReturnSubmit");
            viewModel.callSubmitPackReturnApi(urlBean, model);
        } else {
            //Toast.makeText(master, master.getString(R.string.no_books_selected), Toast.LENGTH_SHORT).show();
            if (mCodeScanner != null)
                mCodeScanner.startPreview();
            showToast(master.getString(R.string.no_books_selected));
        }
    }

    private void startScanning() {
        mCodeScanner = new CodeScanner(master, scannerView);
        mCodeScanner.setDecodeCallback(result -> master.runOnUiThread(() -> {
            //etPackNumber.setText(result.getText());
            selectPack(result.getText());
            Utils.vibrate(master);
            if (getTotalPackCount() > 1 && showSnackBar) {
                Snackbar.make(linearLayout, R.string.tap_scanner_to_continue, 3000).setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
                showSnackBar = false;
            }
        }));
        scannerView.setOnClickListener(view1 -> {
            if (mCodeScanner != null) mCodeScanner.startPreview();
        });
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
                            AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), etPackNumber);
                        }
                    }
                }
                break;
        }
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

    private void pulsateAnimation() {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                tvScanCount,
                PropertyValuesHolder.ofFloat("scaleX", 0.7f),
                PropertyValuesHolder.ofFloat("scaleY", 0.7f));
        scaleDown.setDuration(310);

        scaleDown.setRepeatCount(3);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();
    }

    private int getPackCount(int gameId) {
        for (ReturnChallanResponseHomeBean.Game game : packReturnBean.getGames()) {
            if (game.getGameId() == gameId) {
                return game.getBooksQuantity();
            }
        }
        return -100;
    }

    public void showToast(String toastMessage) {
        Toast toast = Toast.makeText(master, toastMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(toast::cancel, 1000);
    }

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        Utils.dismissCustomErrorDialog();
        Utils.dismissCustomSuccessDialog();
        if (packReturnDialog != null && packReturnDialog.isShowing())
            packReturnDialog.dismiss();
        super.onDestroy();
    }
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean hasPermissions(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}