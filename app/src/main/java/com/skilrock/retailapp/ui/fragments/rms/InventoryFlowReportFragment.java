package com.skilrock.retailapp.ui.fragments.rms;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2PRO;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2_s;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2s;
import static com.skilrock.retailapp.utils.Utils.updateDateFormat;

import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.InventoryFlowReportAdapterClosingBalance;
import com.skilrock.retailapp.adapter.rms.InventoryFlowReportAdapterOpeningBalance;
import com.skilrock.retailapp.adapter.rms.InventoryFlowReportAdapterReceived;
import com.skilrock.retailapp.adapter.rms.InventoryFlowReportAdapterReturned;
import com.skilrock.retailapp.adapter.rms.InventoryFlowReportAdapterSold;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.ola.InventoryFlowReportResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.PaymentReportRequestBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.InventoryFlowReportViewModel;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class InventoryFlowReportFragment extends BaseFragment implements View.OnClickListener {

    private InventoryFlowReportViewModel viewModel;
    private TextView tvStartDate, tvEndDate, tvReceivedLabel, tvReturnedLabel, tvSalesLabel,tvOpeningLabel,tvClosingLabel, tvOpeningBalanceBooks, tvReceivedBooks, tvReturnedBooks, tvSalesBooks, tvClosingBalanceBooks, tvOpeningBalanceTickets, tvReceivedTickets, tvReturnedTickets, tvSalesTickets, tvClosingBalanceTickets , tvSelectedDate;
    private ImageView ivProceed;
    private Button btnPrint;
    private RecyclerView rvGameDataReceived, rvGameDataReturned, rvGameDataSold, rvGameDataOpeningBal, rvGameDataClosingBal;
    private LinearLayout llFromDate, llEndDate, llRecyclerViewReceived, llRecyclerViewReturned, llRecyclerViewSold, llRecyclerViewOpeningBal, llRecyclerViewClosingBal;
    private InventoryFlowReportAdapterReceived adapterReceived;
    private InventoryFlowReportAdapterReturned adapterReturned;
    private InventoryFlowReportAdapterSold adapterSold;
    private InventoryFlowReportAdapterOpeningBalance adapterOpeningBal;
    private InventoryFlowReportAdapterClosingBalance adapterClosingBal;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private final String RMS = "rms";
    private String tag = "";
    private SunmiPrinterService service;
    private final byte[] boldFontEnable    = {0x1B , 0x45 , 0x1};
    private final byte[] boldFontDisable   = {0x1B, 0x45, 0x0};
    private Boolean receivedLabelOpen = false, returnedLabelOpen = false, salesLabelOpen = false, openingLabelOpen = false, closingLabelOpen = false;
    private List<InventoryFlowReportResponseBean.GameWiseDatum> responseGameData;
    private List<InventoryFlowReportResponseBean.GameWiseOpeningBalanceDatum> responseOpeningBalanceGameData;
    private List<InventoryFlowReportResponseBean.GameWiseClosingBalanceDatum> responseClosingBalanceGameData;

    private String mSelectedDateForDisplay = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            InnerPrinterManager.getInstance().bindService(getContext(),innerPrinterCallback);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_report_inventory_flow, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            viewModel = ViewModelProviders.of(this).get(InventoryFlowReportViewModel.class);
            viewModel.getLiveDataInventoryFlowReport().observe(this, observer);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);
        callApi();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        tvStartDate                 = view.findViewById(R.id.tvStartDate);
        tvEndDate                   = view.findViewById(R.id.tvEndDate);
        ivProceed                   = view.findViewById(R.id.button_proceed);
        llFromDate                  = view.findViewById(R.id.containerFromDate);
        llEndDate                   = view.findViewById(R.id.containerEndDate);
        TextView tvTitle            = view.findViewById(R.id.tvTitle);
        TextView label        = view.findViewById(R.id.label);

        tvUsername                  = view.findViewById(R.id.tvUserName);
        tvUserBalance               = view.findViewById(R.id.tvUserBalance);
        rvGameDataReceived          = view.findViewById(R.id.rvGameDataReceived);
        rvGameDataReturned          = view.findViewById(R.id.rvGameDataReturned);
        rvGameDataSold              = view.findViewById(R.id.rvGameDataSold);
        rvGameDataOpeningBal        = view.findViewById(R.id.rvGameDataOpeningBal);
        rvGameDataClosingBal        = view.findViewById(R.id.rvGameDataClosingBal);
        tvOpeningBalanceBooks       = view.findViewById(R.id.tvOpeningBalanceBooks);
        tvOpeningBalanceTickets     = view.findViewById(R.id.tvOpeningBalanceTickets);
        tvReceivedBooks             = view.findViewById(R.id.tvReceivedBooks);
        tvReturnedBooks             = view.findViewById(R.id.tvReturnedBooks);
        tvSalesBooks                = view.findViewById(R.id.tvSalesBooks);
        tvClosingBalanceBooks       = view.findViewById(R.id.tvClosingBalanceBooks);
        tvClosingBalanceTickets     = view.findViewById(R.id.tvClosingBalanceTickets);
        tvReceivedTickets           = view.findViewById(R.id.tvReceivedTickets);
        tvReturnedTickets           = view.findViewById(R.id.tvReturnedTickets);
        tvSalesTickets              = view.findViewById(R.id.tvSalesTickets);
        llRecyclerViewReceived      = view.findViewById(R.id.llRecyclerViewReceived);
        llRecyclerViewReturned      = view.findViewById(R.id.llRecyclerViewReturned);
        llRecyclerViewSold          = view.findViewById(R.id.llRecyclerViewSold);
        llRecyclerViewOpeningBal    = view.findViewById(R.id.llRecyclerViewOpeningBal);
        llRecyclerViewClosingBal    = view.findViewById(R.id.llRecyclerViewClosingBal);
        tvReceivedLabel             = view.findViewById(R.id.tvReceivedLabel);
        tvReturnedLabel             = view.findViewById(R.id.tvReturnedLabel);
        tvSalesLabel                = view.findViewById(R.id.tvSalesLabel);
        tvOpeningLabel              = view.findViewById(R.id.tvOpeningLabel);
        tvClosingLabel              = view.findViewById(R.id.tvClosingLabel);
        btnPrint                    = view.findViewById(R.id.btn_print);
        tvSelectedDate = view.findViewById(R.id.selectedDate);

        refreshBalance();

        llFromDate.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
        ivProceed.setOnClickListener(this);
        tvReceivedLabel.setOnClickListener(this);
        tvReturnedLabel.setOnClickListener(this);
        tvSalesLabel.setOnClickListener(this);
        tvOpeningLabel.setOnClickListener(this);
        tvClosingLabel.setOnClickListener(this);
        btnPrint.setOnClickListener(this);

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
                label.setText(bundle.getString("title"));

            }
            menuBean = bundle.getParcelable("MenuBean");
            Log.d("TAg", "BasePath: " + menuBean.getBasePath());
            Log.d("TAg", "RelativePath: " + menuBean.getRelativePath());
        }

        String currentDate = Utils.getCurrentDate();
        tvStartDate.setText(currentDate);
        tvEndDate.setText(currentDate);
        mSelectedDateForDisplay = currentDate+"&"+currentDate;
        tvSelectedDate.setText(updateDateFormat(currentDate, SharedPrefUtil.getLanguage(getContext())) +" - "+ updateDateFormat(currentDate, SharedPrefUtil.getLanguage(getContext())));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.containerFromDate:
                Utils.openStartDateUnlDialogBalanceOperational(master, tvStartDate, tvEndDate);
                break;
            case R.id.containerEndDate:
                if (tvStartDate.getText().toString().equalsIgnoreCase(getString(R.string.start_date)))
                    Toast.makeText(master, getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();
                else
                    Utils.openEndDateUnlDialogBalanceOperational(master, tvStartDate, tvEndDate);
                break;

            case R.id.button_proceed:
                llRecyclerViewReceived.setVisibility(View.GONE);
                rvGameDataReceived.setVisibility(View.GONE);
                llRecyclerViewReturned.setVisibility(View.GONE);
                rvGameDataReturned.setVisibility(View.GONE);
                llRecyclerViewSold.setVisibility(View.GONE);
                rvGameDataSold.setVisibility(View.GONE);
                llRecyclerViewOpeningBal.setVisibility(View.GONE);
                rvGameDataOpeningBal.setVisibility(View.GONE);
                llRecyclerViewClosingBal.setVisibility(View.GONE);
                rvGameDataClosingBal.setVisibility(View.GONE);

                receivedLabelOpen   = false;
                returnedLabelOpen   = false;
                salesLabelOpen      = false;
                openingLabelOpen    = false;
                closingLabelOpen    = false;

                callApi();
                break;

            case R.id.tvReceivedLabel:
                if (responseGameData.isEmpty()) {
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), getString(R.string.no_data_found));
                } else {
                    receivedLabelOpen = true;
                    llRecyclerViewReceived.setVisibility(View.VISIBLE);
                    rvGameDataReceived.setVisibility(View.VISIBLE);
                    if (adapterReceived != null) adapterReceived.clear();
                    setReceivedAdapter();
                }
                break;

            case R.id.tvReturnedLabel:
                if (responseGameData.isEmpty()) {
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), getString(R.string.no_data_found));
                } else {
                    returnedLabelOpen = true;
                    llRecyclerViewReturned.setVisibility(View.VISIBLE);
                    rvGameDataReturned.setVisibility(View.VISIBLE);
                    if (adapterReturned != null) adapterReturned.clear();
                    setReturnedAdapter();
                }
                break;

            case R.id.tvSalesLabel:
                Log.d("TAg", "tvSalesLabel");
                if (responseGameData.isEmpty()) {
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), getString(R.string.no_data_found));
                } else {
                    salesLabelOpen = true;
                    llRecyclerViewSold.setVisibility(View.VISIBLE);
                    rvGameDataSold.setVisibility(View.VISIBLE);
                    if (adapterSold != null) adapterSold.clear();
                    setSoldAdapter();
                }
                break;

            case R.id.tvOpeningLabel:
                if (responseOpeningBalanceGameData.isEmpty()) {
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), getString(R.string.no_data_found));
                } else {
                    openingLabelOpen = true;
                    llRecyclerViewOpeningBal.setVisibility(View.VISIBLE);
                    rvGameDataOpeningBal.setVisibility(View.VISIBLE);
                    if (adapterOpeningBal != null) adapterOpeningBal.clear();
                    setOpeningAdapter();
                }
                break;

            case R.id.tvClosingLabel:
                if (responseClosingBalanceGameData.isEmpty()) {
                    Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), getString(R.string.no_data_found));
                } else {
                    closingLabelOpen = true;
                    llRecyclerViewClosingBal.setVisibility(View.VISIBLE);
                    rvGameDataClosingBal.setVisibility(View.VISIBLE);
                    if (adapterClosingBal != null) adapterClosingBal.clear();
                    setClosingAdapter();
                }
                break;

            case R.id.btn_print:
                if(Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2PRO)

                        || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2s)
                        || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2_s)
                        || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2)){
                    samplePrint();
                } else {
                    Toast.makeText(getContext(),getString(R.string.device_not_connected),Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    private void samplePrint() {
        try{
            String[] array = mSelectedDateForDisplay.split("&");

            /*Bitmap bitImage = BitmapFactory.decodeResource(getResources(), R.drawable.group_2057);//logo
            Bitmap newResult = Bitmap.createScaledBitmap(bitImage, 160, 90, false);
            service.printText("\n\n",null);
            service.setAlignment(1, null);
            service.printBitmapCustom(newResult, 2, null);
            service.printText("\n\n",null);*/

            service.setAlignment(1, null);
            service.sendRAWData(boldFontEnable, null);
            service.printTextWithFont(getString(R.string.inventory_flow_report) + "\n\n", "", 25F, null);
            service.sendRAWData(boldFontDisable, null);

            service.sendRAWData(boldFontEnable, null);
            service.printTextWithFont(getString(R.string.date) + " " + array[0] + " " + getString(R.string.to) + " " + array[1], "", 21F, null);
            service.sendRAWData(boldFontDisable, null);
            //service.setAlignment(1, null);
            //service.setAlignment(0, null);
            //service.printText("\n--------------------------------\n", null);

            service.printText("\n--------------------------------\n", null);

            service.printColumnsString(new String[] {getString(R.string.org_id_camel) + " " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId()},
                    new int[] {(getString(R.string.org_id_camel) + " " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId()).length()}, new int[] {0}, null);

            service.printColumnsString(new String[] {getString(R.string.organization_name) + " : " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()},
                    new int[] {(getString(R.string.organization_name) + " : " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()).length()}, new int[] {0}, null);

            //service.printTextWithFont(getString(R.string.org_id_camel) + " " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId(), "", 23F, null);
            //service.printText("\n", null);
            //service.printTextWithFont(getString(R.string.organization_name) + " : " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(), "", 23F, null);

            service.printText("\n--------------------------------\n", null);

            //service.printText("\n--------------------------------\n", null);

            service.setAlignment(1, null);

            service.sendRAWData(boldFontEnable, null);
            service.printColumnsString(new String[] {"",getString(R.string.books),getString(R.string.tickets)},
                    new int[] {11, 6, 6}, new int[] {0,2,2}, null);
            service.sendRAWData(boldFontDisable, null);

            service.setFontSize(21F, null);

            service.printColumnsString(new String[] {getString(R.string.open_balance_print),tvOpeningBalanceBooks.getText().toString(),tvOpeningBalanceTickets.getText().toString()},
                    new int[] {11, 6, 6}, new int[] {0,2,2}, null);

            service.printColumnsString(new String[] {getString(R.string.received),tvReceivedBooks.getText().toString(),tvReceivedTickets.getText().toString()},
                    new int[] {11, 6, 6}, new int[] {0,2,2}, null);

            service.printColumnsString(new String[] {getString(R.string.returned),tvReturnedBooks.getText().toString(),tvReturnedTickets.getText().toString()},
                    new int[] {11, 6, 6}, new int[] {0,2,2}, null);

            service.printColumnsString(new String[] {getString(R.string.sales),tvSalesBooks.getText().toString(),tvSalesTickets.getText().toString()},
                    new int[] {11, 6, 6}, new int[] {0,2,2}, null);

            service.printColumnsString(new String[] {getString(R.string.close_and_total), tvClosingBalanceBooks.getText().toString(), tvClosingBalanceTickets.getText().toString()},
                    new int[] {11, 6, 6}, new int[] {0,2,2}, null);

            try {
                if (SharedPrefUtil.getLanguage(getContext()).equals("en")) {
                    service.printColumnsString(new String[] {getString(R.string.balance_without_colon)},
                            new int[] {11}, new int[] {0}, null);
                }
            } catch (Exception e) {
                Log.v("log", "Exception: " + e);
            }

            service.printText("\n----------------------------------\n", null);

            service.setFontSize(21F, null);

            /*if (receivedLabelOpen || returnedLabelOpen || salesLabelOpen){
                for(int i = 0; i <(responseGameData.size()) ; i++){
                    service.setAlignment(1, null);
                    service.sendRAWData(boldFontEnable, null);
                    service.printTextWithFont(responseGameData.get(i).getGameName(), "", 24F, null);
                    service.printText("\n\n",null);
                    service.sendRAWData(boldFontDisable, null);

                    service.sendRAWData(boldFontEnable, null);
                    service.printColumnsString(new String[] {"","Books","Tickets"},
                            new int[] {10, 6, 6}, new int[] {0,1,2}, null);
                    service.sendRAWData(boldFontDisable, null);

                    if (receivedLabelOpen) {
                        service.printColumnsString(new String[] {getString(R.string.received), responseGameData.get(i).getReceivedBooks().toString(), responseGameData.get(i).getReceivedTickets().toString()},
                                new int[] {10, 6, 6}, new int[] {0,1,2}, null);
                    }

                    if (returnedLabelOpen) {
                        service.printColumnsString(new String[] {getString(R.string.returned),  responseGameData.get(i).getReturnedBooks().toString(), responseGameData.get(i).getReturnedTickets().toString()},
                                new int[] {10, 6, 6}, new int[] {0,1,2}, null);
                    }

                    if (salesLabelOpen) {
                        service.printColumnsString(new String[] {getString(R.string.sales),  responseGameData.get(i).getSoldBooks().toString(), responseGameData.get(i).getSoldTickets().toString()},
                                new int[] {10, 6, 6}, new int[] {0,1,2}, null);
                    }

                    service.sendRAWData(boldFontEnable, null);
                    service.printText("--------------------------------\n", null);
                    service.sendRAWData(boldFontDisable, null);

                }
            }*/

            if (receivedLabelOpen) {
                service.sendRAWData(boldFontEnable, null);
                service.printColumnsString(new String[] {getString(R.string.received),getString(R.string.books),getString(R.string.tickets)},
                        new int[] {10, 6, 6}, new int[] {0,2,2}, null);
                service.sendRAWData(boldFontDisable, null);
                for(int i = 0; i <(responseGameData.size()) ; i++){
                    service.printColumnsString(new String[] {responseGameData.get(i).getGameName(), responseGameData.get(i).getReceivedBooks().toString(), responseGameData.get(i).getReceivedTickets().toString()},
                            new int[] {10, 6, 6}, new int[] {0,2,2}, null);
                }
                service.printText("\n----------------------------------\n", null);
            }

            if (returnedLabelOpen) {
                service.sendRAWData(boldFontEnable, null);
                service.printColumnsString(new String[] {getString(R.string.returned),getString(R.string.books),getString(R.string.tickets)},
                        new int[] {10, 6, 6}, new int[] {0,2,2}, null);
                service.sendRAWData(boldFontDisable, null);
                for(int i = 0; i <(responseGameData.size()) ; i++){
                    service.printColumnsString(new String[] {responseGameData.get(i).getGameName(),  responseGameData.get(i).getReturnedBooks().toString(), responseGameData.get(i).getReturnedTickets().toString()},
                            new int[] {10, 6, 6}, new int[] {0,2,2}, null);
                }
                service.printText("\n----------------------------------\n", null);
            }

            if (salesLabelOpen) {
                service.sendRAWData(boldFontEnable, null);
                service.printColumnsString(new String[] {getString(R.string.sales),getString(R.string.books),getString(R.string.tickets)},
                        new int[] {10, 6, 6}, new int[] {0,2,2}, null);
                service.sendRAWData(boldFontDisable, null);
                for(int i = 0; i <(responseGameData.size()) ; i++){
                    service.printColumnsString(new String[] {responseGameData.get(i).getGameName(),  responseGameData.get(i).getSoldBooks().toString(), responseGameData.get(i).getSoldTickets().toString()},
                            new int[] {10, 6, 6}, new int[] {0,2,2}, null);
                }
                service.printText("\n----------------------------------\n", null);

            }

            if (openingLabelOpen) {
                service.sendRAWData(boldFontEnable, null);
                service.printColumnsString(new String[] {getString(R.string.open_balance),getString(R.string.books),getString(R.string.tickets)},
                        new int[] {12, 6, 6}, new int[] {0,2,2}, null);
                service.sendRAWData(boldFontDisable, null);
                for(int i = 0; i <(responseOpeningBalanceGameData.size()) ; i++){
                    service.printColumnsString(new String[] {responseOpeningBalanceGameData.get(i).getGameName(),  responseOpeningBalanceGameData.get(i).getTotalBooks().toString(), responseOpeningBalanceGameData.get(i).getTotalTickets().toString()},
                            new int[] {10, 6, 6}, new int[] {0,2,2}, null);
                }
                service.printText("\n----------------------------------\n", null);
            }

            if (closingLabelOpen) {
                service.sendRAWData(boldFontEnable, null);
                service.printColumnsString(new String[] {getString(R.string.close_and_total),getString(R.string.books),getString(R.string.tickets)},
                        new int[] {10, 6, 6}, new int[] {0,2,2}, null);

                try {
                    if (SharedPrefUtil.getLanguage(getContext()).equals("en")) {
                        service.setAlignment(0, null);
                        service.printTextWithFont(getString(R.string.balance) + "\n", "", 21F, null);
                    }
                } catch (Exception e) {
                    Log.v("log", "Exception: " + e);
                }
                service.sendRAWData(boldFontDisable, null);
                for(int i = 0; i <(responseClosingBalanceGameData.size()) ; i++){
                    service.printColumnsString(new String[] {responseClosingBalanceGameData.get(i).getGameName(),  responseClosingBalanceGameData.get(i).getTotalBooks().toString(), responseClosingBalanceGameData.get(i).getTotalTickets().toString()},
                            new int[] {10, 6, 6}, new int[] {0,2,2}, null);
                }
                service.printText("\n--------------------------------", null);
                service.printText("------------------------------------\n", null);
            }

            service.printText("\n\n\n", null);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback(){

        @Override
        protected void onConnected(SunmiPrinterService sunmiPrinterService) {
            service = sunmiPrinterService;
        }

        @Override
        protected void onDisconnected() {

        }
    };

    private void callApi() {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        if (validate()) {
            ProgressBarDialog.getProgressDialog().showProgress(master);
            PaymentReportRequestBean model = new PaymentReportRequestBean();
            model.setEndDate(formatDate(tvEndDate));
            model.setOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setStartDate(formatDate(tvStartDate));
            model.setOrgTypeCode(AppConstants.ORG_TYPE_CODE);
            UrlBean urlBean = Utils.getUrlDetails(menuBean, getContext(),"getInventoryFlowReport");
            viewModel.callInventoryFlowReportApi(urlBean, formatDate(tvStartDate), formatDate(tvEndDate));
        }
    }

    private boolean validate() {
        Utils.vibrate(requireContext());
        if (tvStartDate.getText().toString().equalsIgnoreCase(getString(R.string.start_date))) {
            Toast.makeText(master, getString(R.string.select_start_date), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tvEndDate.getText().toString().equalsIgnoreCase(getString(R.string.end_date))) {
            Toast.makeText(master, getString(R.string.select_end_date), Toast.LENGTH_SHORT).show();
            return false;
        }

        /*try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy"); //"yyyy-mm-dd"

            Date date1 = format.parse(tvStartDate.getText().toString().trim());
            Date date2 = format.parse(tvEndDate.getText().toString().trim());

            if (date2.compareTo(date1) < 0) {
                Toast.makeText(master, getString(R.string.from_date_greater_than_to_date), Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }*/

        return true;
    }

    @SuppressLint("SetTextI18n")
    Observer<InventoryFlowReportResponseBean> observer = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        tvSelectedDate.setText(updateDateFormat(tvStartDate.getText().toString(), SharedPrefUtil.getLanguage(getContext())) +" - "+ updateDateFormat(tvEndDate.getText().toString(),SharedPrefUtil.getLanguage(getContext())));
        mSelectedDateForDisplay =tvStartDate.getText().toString()+"&"+tvEndDate.getText().toString();
        if (response == null) {
            setDefaultBalance();
            Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), getString(R.string.something_went_wrong));
        } else if (response.getResponseCode() == 1065) {
            String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
            setDefaultBalance();
            Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), errorMsg);
        } else if (response.getResponseCode() == 1011) {
            String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
            setDefaultBalance();
            Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), errorMsg);
        }   else if (response.getResponseCode() == 1000) {
            try{
                responseGameData = response.getGameWiseData();
                responseOpeningBalanceGameData = response.getGameWiseOpeningBalanceData();
                responseClosingBalanceGameData = response.getGameWiseClosingBalanceData();
                tvOpeningBalanceBooks.setText(String.valueOf(response.getBooksOpeningBalance()));
                tvClosingBalanceBooks.setText(String.valueOf(response.getBooksClosingBalance()));
                tvOpeningBalanceTickets.setText(String.valueOf(response.getTicketsOpeningBalance()));
                tvClosingBalanceTickets.setText(String.valueOf(response.getTicketsClosingBalance()));
                tvReturnedBooks.setText(String.valueOf(response.getReturnedBooks()));
                tvReturnedTickets.setText(String.valueOf(response.getReturnedTickets()));
                tvReceivedBooks.setText(String.valueOf(response.getReceivedBooks()));
                tvReceivedTickets.setText(String.valueOf(response.getReceivedTickets()));
                tvSalesBooks.setText(String.valueOf(response.getSoldBooks()));
                tvSalesTickets.setText(String.valueOf(response.getSoldTickets()));
                btnPrint.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                setDefaultBalance();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), getString(R.string.no_data_found));
            }
        } else {
            if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                return;
            setDefaultBalance();
            Utils.showCustomErrorDialog(getContext(), getString(R.string.inventory_flow_report), getString(R.string.something_went_wrong));
        }
    };

    private void setDefaultBalance(){
        btnPrint.setVisibility(View.GONE);
        tvOpeningBalanceBooks.setText("-");
        tvClosingBalanceBooks.setText("-");
        tvOpeningBalanceTickets.setText("-");
        tvClosingBalanceTickets.setText("-");
        tvReturnedBooks.setText("-");
        tvReturnedTickets.setText("-");
        tvReceivedBooks.setText("-");
        tvReceivedTickets.setText("-");
        tvSalesBooks.setText("-");
        tvSalesTickets.setText("-");
        llRecyclerViewReceived.setVisibility(View.GONE);
        llRecyclerViewReturned.setVisibility(View.GONE);
        llRecyclerViewSold.setVisibility(View.GONE);
        llRecyclerViewOpeningBal.setVisibility(View.GONE);
        llRecyclerViewClosingBal.setVisibility(View.GONE);
    }

    private void setReceivedAdapter() {
        if (!responseGameData.isEmpty()) {
            adapterReceived = new InventoryFlowReportAdapterReceived(new ArrayList(responseGameData), getContext());
            rvGameDataReceived.setLayoutManager(new LinearLayoutManager(getContext()));
            rvGameDataReceived.setItemAnimator(new DefaultItemAnimator());
            rvGameDataReceived.setAdapter(adapterReceived);
        }
    }

    private void setReturnedAdapter() {
        if (!responseGameData.isEmpty()) {
            adapterReturned = new InventoryFlowReportAdapterReturned(new ArrayList(responseGameData), getContext());
            rvGameDataReturned.setLayoutManager(new LinearLayoutManager(getContext()));
            rvGameDataReturned.setItemAnimator(new DefaultItemAnimator());
            rvGameDataReturned.setAdapter(adapterReturned);
        }
    }

    private void setSoldAdapter() {
        if (!responseGameData.isEmpty()) {
            adapterSold = new InventoryFlowReportAdapterSold(new ArrayList(responseGameData), getContext());
            rvGameDataSold.setLayoutManager(new LinearLayoutManager(getContext()));
            rvGameDataSold.setItemAnimator(new DefaultItemAnimator());
            rvGameDataSold.setAdapter(adapterSold);
        }
    }

    private void setOpeningAdapter() {
        Log.d("TAg", "setOpeningAdapter");
        if (!responseOpeningBalanceGameData.isEmpty()) {
            adapterOpeningBal = new InventoryFlowReportAdapterOpeningBalance(new ArrayList(responseOpeningBalanceGameData), getContext());
            rvGameDataOpeningBal.setLayoutManager(new LinearLayoutManager(getContext()));
            rvGameDataOpeningBal.setItemAnimator(new DefaultItemAnimator());
            rvGameDataOpeningBal.setAdapter(adapterOpeningBal);
        }
    }

    private void setClosingAdapter() {
        if (!responseClosingBalanceGameData.isEmpty()) {
            adapterClosingBal = new InventoryFlowReportAdapterClosingBalance(new ArrayList(responseClosingBalanceGameData), getContext());
            rvGameDataClosingBal.setLayoutManager(new LinearLayoutManager(getContext()));
            rvGameDataClosingBal.setItemAnimator(new DefaultItemAnimator());
            rvGameDataClosingBal.setAdapter(adapterClosingBal);
        }
    }

    private String formatDate(TextView tvDate)  {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd-mm-yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-mm-dd" );
        Date date = null;
        try {
            date = originalFormat.parse(tvDate.getText().toString().trim());
            Log.d("log", "Old Format: " + originalFormat.format(date));
            Log.d("log", "New Format: " + targetFormat.format(date));

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return targetFormat.format(date);
    }
}