package com.skilrock.retailapp.ui.fragments.scratch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.zxing.Result;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.MultiSaleSeriesWarningDialog;
import com.skilrock.retailapp.dialog.SuccessDialog;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.GetTicketStatusResponse;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.MethodCallback;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.barcode.BarcodeFormat;
import com.skilrock.retailapp.utils.barcode.BarcodeResultParser;
import com.skilrock.retailapp.viewmodels.scratch.CameraXViewModel;
import com.skilrock.retailapp.viewmodels.scratch.SaleTicketViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SaleTicketGelsaFragment extends BaseFragment implements View.OnClickListener, ImageAnalysis.Analyzer, ErrorDialogListener, MethodCallback {

    private SaleTicketViewModel saleTicketViewModel;
    private LinearLayoutCompat llContainer;
    private LinearLayoutCompat llContainer2;
    private LinearLayout linearLayout2;
    private MultiSaleSeriesWarningDialog multiSaleSeriesWarningDialog;
    private EditText etTicketNumber;
    private SwitchCompat toggleSeries;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private FrameLayout content_frame;
    private Button buttonProceed;
    private Button addMoreBtn;
    private Button discardSeries;
    private Button endSeries;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private final String SCRATCH = "scratch";
    private TextInputLayout tilTicketNumber;
    BarcodeScanner barCodeScanner;
    private static final int PERMISSION_CAMERA_REQUEST = 1;
    private PreviewView previewView;
    private ImageView ivFlash;
    private ImageView ivCamReload;
    private ProcessCameraProvider cameraProvider = null;
    private CameraSelector cameraSelector = null;
    private final int lensFacing = CameraSelector.LENS_FACING_BACK;
    private Preview previewUseCase = null;
    private ImageAnalysis analysisUseCase = null;
    private final ArrayList<String> scannedNumbersList = new ArrayList<>();
    private boolean isLocked = true;
    private boolean isFlashLightOn = false;
    private androidx.camera.core.Camera camm = null;
    private Handler handler;
    private boolean isToggleClick = false;

    private boolean isFirstTimeStatusApiCall = true;

    private List<String> ticketList = new ArrayList<>();

    private GetTicketStatusResponse ticketCartList = null; // this is main variable
    Map<String, String> lastAddTicket = new HashMap<>();
    private boolean isAddSepraitley = false;
    private boolean isEditTextChange = false;
    private boolean isClickEnable = false;
    private boolean isProceedBtnEnable = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanning_mutli_sale, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            saleTicketViewModel = ViewModelProviders.of(this).get(SaleTicketViewModel.class);
            setupCamera();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);

        getParentFragmentManager().setFragmentResultListener("delete", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // Handle the result here
                ArrayList<String> deleteTicketList = bundle.getStringArrayList("ticket"); // deleting ticket no.

                if(ticketCartList != null) {
                Log.i("TaG","viewmodel ticket List ===============> " + ticketCartList.getResponse().getData());
                    assert deleteTicketList != null;
                    for( String mTicket : deleteTicketList) {
                        for(GetTicketStatusResponse.Game gameData : ticketCartList.getResponse().getData()) {
                            List<GetTicketStatusResponse.Ticket> ticketList = gameData.getTicketAndStatusList();
                            if (ticketList != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    ticketList.removeIf(ticket -> ticket.getTicketNumber().equals(mTicket));
                                }
                            }
                        }
                }

            }
                // Do something with the data
            }
        });

        getParentFragmentManager().setFragmentResultListener("cleanData", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Handle the result here
                boolean cleanData = result.getBoolean("cleanData");
                if(cleanData){ // this code not working
                    saleTicketViewModel.setTicketStatusData(null);
                    llContainer.setVisibility(View.GONE);
                    isFirstTimeStatusApiCall = true;
                    ticketCartList = null;
                    lastAddTicket.clear();
                }

            }
        });

        saleTicketViewModel.getGameListData().observe(this, gameListBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (gameListBean == null)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong));
            else if (gameListBean.getResponseCode() == 57575)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket));
            else if (gameListBean.getResponseCode() == 75757)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.no_games_found));
            else if (gameListBean.getResponseCode() == 74747)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket_length));
            else {
                //String errorMsg = TextUtils.isEmpty(gameListBean.getResponseMessage()) ? getString(R.string.error_fetching_gamelist) : gameListBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, gameListBean.getResponseCode());
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, errorMsg);
            }
        });

        saleTicketViewModel.getResponseData().observe(this, responseCodeMessageBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            if (responseCodeMessageBean == null)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong));
            else if (responseCodeMessageBean.getResponseCode() == 1000) {
                String successMsg = getString(R.string.ticket_is_marked_as_sold);
                SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), 1, "", successMsg, responseCodeMessageBean.getSaleTicketDetails().get(0).getGameName(), responseCodeMessageBean.getSaleTicketDetails().get(0).getTicketPrice().toString(), responseCodeMessageBean.getSaleTicketDetails().get(0).getTicketNumbers().toString());
                dialog.show();
                //String successMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.successfully_ticket_sold) : responseCodeMessageBean.getResponseMessage();
                //Utils.showSuccessDialog(getContext(), getString(R.string.app_name), successMsg, getFragmentManager());
                /*SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), "", successMsg, 1);
                dialog.show();*/
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            } else {

                if (Utils.checkForSessionExpiry(master, responseCodeMessageBean.getResponseCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.some_internal_error) : responseCodeMessageBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, responseCodeMessageBean.getResponseCode());
                Log.d("TAg", "responseCodeMessageBean: " + responseCodeMessageBean);
                ErrorDialogListener errorDialogListener = this::callBack;
                if (responseCodeMessageBean.getSoldTickets() == null && responseCodeMessageBean.getInvalidTickets() == null) {
                    Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                } else if (responseCodeMessageBean.getSoldTickets() != null) {
                    Log.d("TAg", "responseCodeMessageBean.getSoldTickets(): " + responseCodeMessageBean.getSoldTickets());
                    if (responseCodeMessageBean.getSoldTickets().get(0) == null) {
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                    } else {
                        Log.d("TAg", "responseCodeMessageBean.getSoldTickets(): " + responseCodeMessageBean.getSoldTickets());
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, responseCodeMessageBean.getSoldTickets().get(0) + " " + getString(R.string.is_already_sold), errorDialogListener);
                    }
                } else if (responseCodeMessageBean.getInvalidTickets() != null) {
                    if (responseCodeMessageBean.getInvalidTickets().get(0) == null) {
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                    } else
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, responseCodeMessageBean.getInvalidTickets().get(0) + " " + getString(R.string.is_already_sold), errorDialogListener);
                } else
                    Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
            }
        });

        saleTicketViewModel.getTicketStatusData().observe(getViewLifecycleOwner(), GetTicketStatusResponse -> {
            // in this multi sale screen only this api called.

            isEditTextChange = false;
            ProgressBarDialog.getProgressDialog().dismiss();
            if (GetTicketStatusResponse == null) {
                enableEditText();
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong));
            } else if (Utils.checkForSessionExpiry(master, GetTicketStatusResponse.getResponseCode())) {
                return;
            } else if (GetTicketStatusResponse.getResponseCode() != 1000) {
                etTicketNumber.setText("");
                isEditTextChange = false;
                enableEditText();
                bindCameraUseCases();
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, GetTicketStatusResponse.getResponseMessage());
              /*  String successMsg = getString(R.string.ticket_is_marked_as_sold);
                SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), 1, "", successMsg, responseCodeMessageBean.getSaleTicketDetails().get(0).getGameName(), responseCodeMessageBean.getSaleTicketDetails().get(0).getTicketPrice().toString(), responseCodeMessageBean.getSaleTicketDetails().get(0).getTicketNumbers().toString());
                dialog.show();
                //String successMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.successfully_ticket_sold) : responseCodeMessageBean.getResponseMessage();
                //Utils.showSuccessDialog(getContext(), getString(R.string.app_name), successMsg, getFragmentManager());
                *//*SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), "", successMsg, 1);
                dialog.show();*//*
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }*/
            } else {

                if (isFirstTimeStatusApiCall || validate()) {
                    if (isFirstTimeStatusApiCall) {
                        Log.i("TaG","First time Calling");
                        ticketCartList = Utils.deepCopy(GetTicketStatusResponse);
                        ticketCartList.getResponse().getData().clear();
                        isFirstTimeStatusApiCall = false;
                    }

                    List<GetTicketStatusResponse.Game> ticketData = GetTicketStatusResponse.getResponse().getData();
                    Log.i("TaG","getting response ==========>" + ticketData);
                    //String errorMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.some_internal_error) : responseCodeMessageBean.getResponseMessage();
                    String errorMsg = Utils.getResponseMessage(master, SCRATCH, GetTicketStatusResponse.getResponseCode());
                    //  Log.d("TaG", "GetTicketStatusResponse: " + GetTicketStatusResponse.getResponse().getData());
                    ErrorDialogListener errorDialogListener = SaleTicketGelsaFragment.this::callBack;
                    if (ticketData == null) {
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                    } else if (GetTicketStatusResponse.getResponse() != null) {
                        llContainer.setVisibility(View.VISIBLE);
                        isClickEnable = true;
                        isProceedBtnEnable = true;
                        toggleSeries.setChecked(false);
                        addMoreBtn.setBackground(getContext().getDrawable(R.drawable.button_rounded_border_bg));
                        addMoreBtn.setTextColor(getResources().getColor(R.color.colorAppOrange));
                        buttonProceed.setBackground(getContext().getDrawable(R.drawable.button_rounded_bg));

                        int noOfTickets = 0;

                        try {
                            boolean thisGameAvailableInList = false;
                            for (GetTicketStatusResponse.Game games : ticketCartList.getResponse().getData()) {
                                if (!ticketData.isEmpty()) {
                                    Log.i("TaG","---------------->>>"+ games.getGameName().equals(ticketData.get(0).getGameName()));
                                    if (games.getGameName().equals(ticketData.get(0).getGameName())) {

                                        List<GetTicketStatusResponse.Ticket> tempTicket = new ArrayList<GetTicketStatusResponse.Ticket>();
                                        for (GetTicketStatusResponse.Ticket ticket : ticketData.get(0).getTicketAndStatusList()) {
                                            Log.i("TaG", "-=-=-=-=-=-=-=2 equal check " + games.getTicketAndStatusList().contains(ticket));
                                            boolean flag =  false;
                                            for (com.skilrock.retailapp.models.scratch.GetTicketStatusResponse.Ticket game : games.getTicketAndStatusList()) {
                                                Log.i("TaG", "-=-=-=-=-=-=-= equal check " + game.getTicketNumber().equals(ticket.getTicketNumber()));
                                                if (game.getTicketNumber().equals(ticket.getTicketNumber())) {
                                                    flag = true;
                                                }
                                            }
                                            if(!flag) {
                                                tempTicket.add(ticket);
                                            }
                                        }
                                        games.getTicketAndStatusList().addAll(tempTicket);

                                        thisGameAvailableInList = true;
                                    }
                                }
                            }
                            for (GetTicketStatusResponse.Game ticket : ticketData) {
                                Log.i("TaG",">>>>>>>>>>>>>>>>>>>>" + ticket.getTicketAndStatusList().size());
                                noOfTickets += ticket.getTicketAndStatusList().size();
                            }
                            Log.i("TaG","value added 1---->" + noOfTickets);
                            if (noOfTickets > 1) {
                                Toast.makeText(getContext(), getString(R.string.series_n_tickets_added_to_check, "unused", noOfTickets), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), getString(R.string.ticket_added_to_check), Toast.LENGTH_SHORT).show();
                            }

                            for (GetTicketStatusResponse.Game gameData : ticketData) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    gameData.getTicketAndStatusList().sort(Comparator.comparing(com.skilrock.retailapp.models.scratch.GetTicketStatusResponse.Ticket::getTicketNumber));
                                }
                            }

                            if (!thisGameAvailableInList) {

                                ticketCartList.getResponse().getData().addAll(ticketData);
                            }

                            Log.i("TaG", "checking condition------> " + (!ticketData.isEmpty() && !isAddSepraitley));
                            if (!ticketData.isEmpty() && !isAddSepraitley) {
                                Log.i("TaG","before last ticket saving" + lastAddTicket);
                                lastAddTicket.put("ticketNo", ticketData.get(0).getTicketAndStatusList().get((ticketData.get(0).getTicketAndStatusList().size() - 1)).getTicketNumber());
                                lastAddTicket.put("gameName", ticketData.get(0).getGameName());
                            }

                            isAddSepraitley = false;

                            Log.i("TaG", "last added ticket 1------> " + lastAddTicket);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                }

            }
        });
    }

    /*Observer<GetTicketStatusResponse> ticketStatusObserver =  new Observer<GetTicketStatusResponse>() {

        @Override
        public void onChanged(GetTicketStatusResponse GetTicketStatusResponse) {
            ProgressBarDialog.getProgressDialog().dismiss();


            if (GetTicketStatusResponse == null) {
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong));
            } else if (Utils.checkForSessionExpiry(master, GetTicketStatusResponse.getResponseCode())) {
                return;
            } else if (GetTicketStatusResponse.getResponseCode() != 1000) {
                etTicketNumber.setText("");
                bindCameraUseCases();
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, GetTicketStatusResponse.getResponseMessage());
              *//*  String successMsg = getString(R.string.ticket_is_marked_as_sold);
                SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), 1, "", successMsg, responseCodeMessageBean.getSaleTicketDetails().get(0).getGameName(), responseCodeMessageBean.getSaleTicketDetails().get(0).getTicketPrice().toString(), responseCodeMessageBean.getSaleTicketDetails().get(0).getTicketNumbers().toString());
                dialog.show();
                //String successMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.successfully_ticket_sold) : responseCodeMessageBean.getResponseMessage();
                //Utils.showSuccessDialog(getContext(), getString(R.string.app_name), successMsg, getFragmentManager());
                *//**//*SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), "", successMsg, 1);
                dialog.show();*//**//*
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }*//*
            } else {

                if (isFirstTimeStatusApiCall || validate()) {
                    if (isFirstTimeStatusApiCall) {
                        Log.i("TaG","First time Calling");
                        ticketCartList = Utils.deepCopy(GetTicketStatusResponse);
                        ticketCartList.getResponse().getData().clear();
                        isFirstTimeStatusApiCall = false;
                    }

                    List<GetTicketStatusResponse.Game> ticketData = GetTicketStatusResponse.getResponse().getData();
                    //String errorMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.some_internal_error) : responseCodeMessageBean.getResponseMessage();
                    String errorMsg = Utils.getResponseMessage(master, SCRATCH, GetTicketStatusResponse.getResponseCode());
                    //  Log.d("TaG", "GetTicketStatusResponse: " + GetTicketStatusResponse.getResponse().getData());
                    ErrorDialogListener errorDialogListener = SaleTicketGelsaFragment.this::callBack;
                    if (ticketData == null) {
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                    } else if (GetTicketStatusResponse.getResponse() != null) {
                        llContainer.setVisibility(View.VISIBLE);
                        boolean thisGameAvailableInList = false;
                        Log.i("TaG","before adding ========= ========>" + ticketCartList.getResponse().getData().size());
                        Log.i("TaG","=================================================");
                        for (GetTicketStatusResponse.Game game : ticketCartList.getResponse().getData()) {
                            if (!ticketData.isEmpty()) {
                                Log.i("TaG","---------------->>>"+ game.getGameName().equals(ticketData.get(0).getGameName()));
                                if (game.getGameName().equals(ticketData.get(0).getGameName())) {
                                    Log.i("TaG", "111111111111111111111111111111 equal ");
                                    for(GetTicketStatusResponse.Ticket ticket : ticketData.get(0).getTicketAndStatusList()) {
                                        Log.i("TaG", "-=-=-=-=-=-=-= equal check " + game.getTicketAndStatusList().contains(ticket));
                                       for (GetTicketStatusResponse.Ticket a : game.getTicketAndStatusList()) {
                                           if(!a.getTicketNumber().equals(ticket.getTicketNumber())){
                                               game.getTicketAndStatusList().add(ticket);
                                           }
                                       }

                                    }
                                    thisGameAvailableInList = true;
                                }
                            }

                        }
                        if (!thisGameAvailableInList) {
                            Log.i("TaG","value added 1");
                            ticketCartList.getResponse().getData().addAll(ticketData);
                        }

                        if (!ticketData.isEmpty() && !isAddSepraitley) {
                            lastAddTicket.put("ticketNo", ticketData.get(0).getTicketAndStatusList().get((ticketData.get(0).getTicketAndStatusList().size() - 1)).getTicketNumber());
                            lastAddTicket.put("gameName", ticketData.get(0).getGameName());
                        }

                        isAddSepraitley = false;

                        Log.i("TaG", "last added ticket 1------> " + lastAddTicket);
                    } else
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                }

            }
        }

    };
*/
    private void callBack() {
        etTicketNumber.setText("");
        enableEditText();
        final Handler handler1 = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second
        handler1.postDelayed(new Runnable() {
            public void run() {
                if (isAdded() && isVisible()) {
                    isFlashLightOn = true;
                    enableDisableFlashLight();
                    setupCamera();
                    Log.e("----------------", "mein call huaa hu");
                    //handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        etTicketNumber = view.findViewById(R.id.et_ticket_number);
        scannerView = view.findViewById(R.id.scanner_view);
        previewView = view.findViewById(R.id.preview_view);
        ivFlash = view.findViewById(R.id.ivFlash);
        ivCamReload = view.findViewById(R.id.ivCamReload);
        buttonProceed = view.findViewById(R.id.button_proceed);
        addMoreBtn = view.findViewById(R.id.add_more_proceed);
        tilTicketNumber = view.findViewById(R.id.textInputLayoutTicketNumber);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        content_frame = view.findViewById(R.id.content_frame);
        toggleSeries = view.findViewById(R.id.toggleSeries);
        llContainer = view.findViewById(R.id.llContainer);


        refreshBalance();
        buttonProceed.setOnClickListener(this);
        addMoreBtn.setOnClickListener(this);
        ivFlash.setOnClickListener(this);
        ivCamReload.setOnClickListener(this);
        toggleSeries.setOnClickListener(this);
        content_frame.setVisibility(View.VISIBLE);


        toggleSeries.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle the switch state change
            if (isChecked) {
                //on
                isToggleClick = true;
                isProceedBtnEnable = false;
                buttonProceed.setBackground(getContext().getDrawable(R.drawable.button_rounded_grey_fill_bg));
            } else {
                //off
                isToggleClick = false;
                isProceedBtnEnable = true;
                buttonProceed.setBackground(getContext().getDrawable(R.drawable.button_rounded_bg));
            }
        });

        /*if (AppPermissions.checkPermission(getActivity())) startScanning();
        else AppPermissions.requestPermission(getFragmentManager());*/

        Bundle bundle = getArguments();
        Log.i("TaG", "Back argument ======================> " + bundle.getString("name"));

        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        etTicketNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable text) {
                Log.i("TaG","step1 ====>" + text + "------" + etTicketNumber.isFocused() + "====> " + !isEditTextChange);
                if (!isEditTextChange && etTicketNumber.isFocused()) {
                    Log.i("TaG","step2 ====>");
                    String enterTicket = text.toString();
                    Log.i("TaG","step3 ====>" + enterTicket);
                    if (enterTicket.contains("-")) {
                        enterTicket = enterTicket.replace("-","");
                    }
                    Log.i("TaG","step4 ====>" + enterTicket);
                    if (enterTicket.length() == 12) {
                        Log.i("TaG","step5 ====>");
                        isEditTextChange = true;
                        afterTicketScanned(Utils.filterTicketLen(text.toString()));
                        disableEditText();
                    }
                }

            }
        });
        etTicketNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                buttonProceed.performClick();
                return true;
            }
            return false;
        });


        handler = new Handler();
        final int delay = 200; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                isLocked = false;
//                Log.e("----------------", "run after 200 ms");
                handler.postDelayed(this, delay);
            }
        }, delay);


    }

    @Override
    public void analyze(ImageProxy imageProxy) {
        @SuppressLint({"UnsafeExperimentalUsageError", "UnsafeOptInUsageError"})
        Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
            // Pass image to an ML Kit Vision API
            // ...
            Task<List<Barcode>> result = barCodeScanner.process(image)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            Log.d("TAg", "barcodes: " + barcodes);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAg", " Exception e: " + e);

                        }
                    });

            Log.d("TAg", "result: " + result);

        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_proceed: {
                //check pattern

                if (isClickEnable && isProceedBtnEnable) {
                    if (ticketCartList != null) {
                        etTicketNumber.setText("");
                        enableEditText();
                        GetTicketStatusResponse ticketDataForShare = Utils.deepCopy(ticketCartList);;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ticketInfo", ticketDataForShare.getResponse());
                        bundle.putParcelable("MenuBean", getArguments().getParcelable("MenuBean"));
                        master.openFragment(new MultiSaleTicketCartGelsaFragment(), "MultiSaleTicketCartGelsaFragment", true, bundle);
                        // ticketCartList.getResponse().getData().clear();

                        Log.i("TaG", "===============>" + ticketDataForShare.getResponse().getData());// success response
                    } else {// make dynamic
                        Toast.makeText(master, getString(R.string.add_a_ticket_first), Toast.LENGTH_LONG).show();

                    }
                    if (validate()) {

                    } else {
//                    Toast.makeText(master, "Enter valid ticket", Toast.LENGTH_LONG).show();

                    }

                }



                break;
            }
            case R.id.ivFlash: {
                Log.i("TaG", "in on click");
                enableDisableFlashLight();
                break;
            }
            case R.id.ivCamReload: {
                etTicketNumber.setText("");
                enableEditText();
                bindCameraUseCases();
                break;
            }
            case R.id.add_more_proceed: {
                if (isClickEnable) {
                    etTicketNumber.setText("");
                    enableEditText();
                    bindCameraUseCases();
                }
                break;
            }
        }
    }

    private void enableDisableFlashLight() {
        if (camm != null) {
            if (camm.getCameraInfo().hasFlashUnit()) {
                if (!isFlashLightOn) {
                    camm.getCameraControl().enableTorch(true);
                    isFlashLightOn = true;
                    ivFlash.setImageResource(R.drawable.baseline_flash_on_24);
                } else {
                    camm.getCameraControl().enableTorch(false);
                    isFlashLightOn = false;
                    ivFlash.setImageResource(R.drawable.baseline_flash_off_24);
                }
            }
        }
    }

    private void startScanning() {
        if (Camera.getNumberOfCameras() <= 0) {
            //Toast.makeText(master, "Camera not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mCodeScanner = new CodeScanner(master, scannerView);
            mCodeScanner.setDecodeCallback(result -> master.runOnUiThread(() -> onScanned(result)));
            scannerView.setOnClickListener(view1 -> mCodeScanner.startPreview());
        } catch (Exception e) {
            Toast.makeText(master, R.string.unable_to_open_scanner, Toast.LENGTH_SHORT).show();
        }
    }

    private void onScanned(Result result) {
        mCodeScanner.stopPreview();
        etTicketNumber.setText(result.getText());
        Utils.vibrate(master);
        //buttonProceed.performClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION GRANTED
                    setupCamera();
                    //startScanning();
                } else {
                    // PERMISSION DENIED
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(getActivity(), getString(R.string.allow_permission), getFragmentManager(), etTicketNumber);
                        }
                    }
                }
                break;

            case PERMISSION_CAMERA_REQUEST:
                if (isCameraPermissionGranted()) {
                    bindCameraUseCases();
                } else {
                    Log.e("TAg", "no camera permission");
                }
        }
    }

    private void setupCamera() {

        cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();
        ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.getInstance(master.getApplication())).get(CameraXViewModel.class)
                .getProcessCameraProvider().observe(this, provider -> {
                    cameraProvider = provider;
                    if (isCameraPermissionGranted()) {
                        bindCameraUseCases();
                    } else {
                        ActivityCompat.requestPermissions(master, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST);
                    }
                });
    }

    Boolean isCameraPermissionGranted() {
        return ContextCompat.checkSelfPermission(master, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    void bindCameraUseCases() {
        bindPreviewUseCase();
        bindAnalyseUseCase();
    }

    private void bindPreviewUseCase() {
        if (cameraProvider == null) {
            return;
        }

        if (previewUseCase != null) {
            cameraProvider.unbind(previewUseCase);
        }

        previewUseCase = new Preview.Builder()
                .setTargetRotation(previewView.getDisplay().getRotation())
                .build();
        previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());

        try {
            camm = cameraProvider.bindToLifecycle(this, cameraSelector, previewUseCase);
        } catch (IllegalStateException illegalStateException) {
            Log.e("TAg", "IllegalStateException");
        }
    }

    private void bindAnalyseUseCase() {
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                        .build();

        //BarcodeScanner scanner = BarcodeScanning.getClient();
        // Or, to specify the formats to recognize:
        BarcodeScanner barcodeScanner = BarcodeScanning.getClient(options);

        if (cameraProvider == null) {
            return;
        }
        if (analysisUseCase != null) {
            cameraProvider.unbind(analysisUseCase);
        }

        analysisUseCase = new ImageAnalysis.Builder()
                .setTargetRotation(previewView.getDisplay().getRotation())
                .build();

        // Initialize our background executor
        ExecutorService cameraExecutor = Executors.newSingleThreadExecutor();

        analysisUseCase.setAnalyzer(cameraExecutor, image -> processImageProxy(barcodeScanner, image));

        try {
            cameraProvider.bindToLifecycle(
                    /* lifecycleOwner= */this,
                    cameraSelector,
                    analysisUseCase
            );
        } catch (IllegalStateException illegalStateException) {
            Log.e("TAg", "IllegalStateException");
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    void processImageProxy(BarcodeScanner barcodeScanner, ImageProxy imageProxy) {
        imageProxy.getCropRect();
        InputImage inputImage = InputImage.fromMediaImage(imageProxy.getImage(), imageProxy.getImageInfo().getRotationDegrees());

        barcodeScanner.process(inputImage).addOnSuccessListener(scanBarcodes -> {
            if (!isLocked) {
                isLocked = true;
                List<Barcode> barcodes = new ArrayList<>();

                if(!scanBarcodes.isEmpty()){
                    barcodes.add(scanBarcodes.get(0));
                }
                Log.i("TaG","sacnneed =-=-=-=-=-=-==-=->>>>" + barcodes);
                for (Barcode barcode : barcodes) {
                    Rect bounds = barcode.getBoundingBox();
                    Point[] corners = barcode.getCornerPoints();
                    String rawValue = barcode.getRawValue();

                    com.skilrock.retailapp.utils.barcode.Barcode result = new BarcodeResultParser().parseBarcode(rawValue, BarcodeFormat.GS1_128);

                    assert result != null;
                    rawValue = result.getFields().get(0).getRawData().trim();



                    if (scannedNumbersList.size() >= 2) {
                        Log.i("TaG","----11----------->" + rawValue);
                        cameraProvider.unbindAll();
                        disableEditText();
                        if (areSame()) {
                            String rawValue_ = Utils.filterTicketLen(rawValue);//000-000000-000"
                            if(rawValue_.replaceAll("-","").length() == 12 && rawValue_.replaceAll("-","").matches("\\d+")) {
                                afterTicketScanned(rawValue_);
                            } else {
                                Log.i("TaG","----33----------->" + rawValue);
                                Toast.makeText(getContext(),R.string.may_be_your_barcode_not_correct_please_check_and_try_again, Toast.LENGTH_LONG).show();
                            }
//                            buttonProceed.performClick();
                        } else {
                            Log.i("TaG","----22----------->" + rawValue);
                            scannedNumbersList.clear();
                            Toast.makeText(getContext(), R.string.cannot_able_to_read_the_code, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("TAg", "rawValue: " + rawValue);
                        Log.d("TAg", "scannedNumbersList: " + scannedNumbersList);
                        scannedNumbersList.add(rawValue);
                    }
                    Log.d("TAg", "barcode.rawValue: " + barcode.getRawValue());
                    int valueType = barcode.getValueType();
                    switch (valueType) {
                        case Barcode.TYPE_WIFI: {

                        }
                        case Barcode.TYPE_URL: {

                        }
                    }

                }

                barcodes.clear();
                barcodes = null;
                System.gc();
            }
        });

        barcodeScanner.process(inputImage).addOnFailureListener(it -> {
            Log.d("TAg", "barcode error message: " + it.getMessage());
        });

        barcodeScanner.process(inputImage).addOnCompleteListener(it -> {
            // When the image is from CameraX analysis use case, must call image.close() on received
            // images when finished using them. Otherwise, new images may not be received or the camera
            // may stall.
            imageProxy.close();

        });
    }

    private void afterTicketScanned(String rawValue) {
        Log.d("TAg", "areSame: " + areSame());
        cameraProvider.unbindAll();
        ProgressBarDialog.getProgressDialog().showProgress(master);
        UrlBean urlSale = Utils.getUrlDetails(menuBean, getContext(), "sale");
        String ticket = rawValue;//000-000000-000"
        Log.i("TaG", "11111111111111111" + " " + ticket);
        Utils.vibrate(master);
        ticketList.clear();
        etTicketNumber.setText(Utils.ticketFormat(Utils.filterTicketLen(rawValue)));
        if (isToggleClick) {//ticketFormat
            Log.i("TaG","2222222222---->" + (ticket.split("-")[0].equals(lastAddTicket.get("ticketNo").split("-")[0]) && ticket.split("-")[1].equals(lastAddTicket.get("ticketNo").split("-")[1])));
            if (ticket.split("-")[0].equals(lastAddTicket.get("ticketNo").split("-")[0]) && ticket.split("-")[1].equals(lastAddTicket.get("ticketNo").split("-")[1])) { // check last add game ticket
                saleTicketViewModel.callTicketStatus(urlSale, Utils.getRangeList(lastAddTicket.get("ticketNo"), ticket));
            } else {
                if (multiSaleSeriesWarningDialog == null || !multiSaleSeriesWarningDialog.isShowing()) {
                    multiSaleSeriesWarningDialog = new MultiSaleSeriesWarningDialog(master, isAddSeparately -> {

                        if (isAddSeparately) {
                            isAddSepraitley = true;
                            ticketList.add(etTicketNumber.getText().toString());
                            saleTicketViewModel.callTicketStatus(urlSale, ticketList);
                        } else {
                            // start new series
                            isAddSepraitley = false;
                            ticketList.add(etTicketNumber.getText().toString());
                            saleTicketViewModel.callTicketStatus(urlSale, ticketList);
                            /*etTicketNumber.setText("");
                            bindCameraUseCases();
                            ProgressBarDialog.getProgressDialog().dismiss();*/

                        }
                    });
                    multiSaleSeriesWarningDialog.show();
                    if (multiSaleSeriesWarningDialog.getWindow() != null) {
                        multiSaleSeriesWarningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        multiSaleSeriesWarningDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                }
                // show dialog this ticket not for series ;
            }
        }
        else {
            ticketList.clear();
            ticketList.add(etTicketNumber.getText().toString());
            saleTicketViewModel.callTicketStatus(urlSale, ticketList);
        }
    }

    public boolean areSame() {
        // Put all array elements in a HashSet
        Set<String> s = new HashSet<>(scannedNumbersList);

        // If all elements are same, size of
        // HashSet should be 1. As HashSet contains only distinct values.
        return (s.size() == 1);
    }


    private boolean validate() {
        if (TextUtils.isEmpty(etTicketNumber.getText().toString().trim())) {
            etTicketNumber.setError(getString(R.string.enter_ticket_number));
            etTicketNumber.requestFocus();
            tilTicketNumber.startAnimation(Utils.shakeError());
            return false;
        }
        if (etTicketNumber.getText().toString().trim().length() != 14) {
            etTicketNumber.setError(getString(R.string.enter_valid_ticket_number));
            etTicketNumber.requestFocus();
            tilTicketNumber.startAnimation(Utils.shakeError());
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindCameraUseCases();

        if (ticketCartList != null ) {
            llContainer.setVisibility(View.VISIBLE);
            isClickEnable = true;
            addMoreBtn.setBackground(getContext().getDrawable(R.drawable.button_rounded_border_bg));
            addMoreBtn.setTextColor(getResources().getColor(R.color.colorAppOrange));
            buttonProceed.setBackground(getContext().getDrawable(R.drawable.button_rounded_bg));
        }
        if (mCodeScanner != null) mCodeScanner.startPreview();

    }

    void enableEditText() {
        etTicketNumber.setEnabled(true);
        etTicketNumber.setFocusable(true);
        etTicketNumber.setFocusableInTouchMode(true); // Necessary to ensure the EditText can gain focus
        etTicketNumber.setClickable(true);
        etTicketNumber.setCursorVisible(true);
    }

    void disableEditText() {
        etTicketNumber.setEnabled(false);
        etTicketNumber.setFocusable(false);
        etTicketNumber.setClickable(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (handler != null) handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            Log.i("TaG", "Exception Occured");
        }
    }

    @Override
    public void onPause() {
        if (mCodeScanner != null) mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onDialogClosed() {
        Log.d("TAg", "error --- >");
    }

    @Override
    public void onClick() {

    }


    @FunctionalInterface
    public interface DialogCallback {
        void onResult(boolean isAddSeparately);
    }

}

