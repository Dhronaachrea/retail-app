package com.skilrock.retailapp.ui.fragments.rms;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2PRO;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2;
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
import com.skilrock.retailapp.models.ola.BalanceReportResponseBean;
import com.skilrock.retailapp.models.rms.BalanceReportRequestBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.BalanceReportViewModel;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class BalanceReportFragment extends BaseFragment implements View.OnClickListener {

    private BalanceReportViewModel viewModel;
    private TextView tvStartDate, tvEndDate, tv_opening_balance, tv_closing_balance, tvSales, tvClaim, tvClaimsTax, tvCommission, tvPayments, tvOpeningBalanceLabel, tvClosingBalanceLabel, tvWinningsCommission, tvSalesCommission, tvDebitAndCredit, tvSelectedDate;
    private ImageView ivProceed;
    private Button  btnPrint;
    private LinearLayout llFromDate, llEndDate;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private final String RMS = "rms";
    private String openingBalanceValue = "";
    private String closingBalanceValue = "";
    private String mSelectedDateForDisplay = "";

    private SunmiPrinterService service;
    private final byte[] boldFontEnable    = {0x1B , 0x45 , 0x1};
    private final byte[] boldFontDisable   = {0x1B, 0x45, 0x0};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            InnerPrinterManager.getInstance().bindService(getContext(),innerPrinterCallback);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_report_balance, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            viewModel = ViewModelProviders.of(this).get(BalanceReportViewModel.class);
            viewModel.getLiveDataBalanceReport().observe(this, observer);
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
        tvStartDate             = view.findViewById(R.id.tvStartDate);
        tvEndDate               = view.findViewById(R.id.tvEndDate);
        ivProceed               = view.findViewById(R.id.button_proceed);
        llFromDate              = view.findViewById(R.id.containerFromDate);
        llEndDate               = view.findViewById(R.id.containerEndDate);
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        TextView label        = view.findViewById(R.id.label);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        tv_opening_balance      = view.findViewById(R.id.tv_opening_balance);
        tv_closing_balance      = view.findViewById(R.id.tv_closing_balance);
        tvSales                 = view.findViewById(R.id.tvSales);
        tvClaim                 = view.findViewById(R.id.tvClaim);
        tvClaimsTax             = view.findViewById(R.id.tvClaimsTax);
        tvCommission            = view.findViewById(R.id.tvCommission);
        tvWinningsCommission    = view.findViewById(R.id.tvWinningCommission);
        tvSalesCommission       = view.findViewById(R.id.tvSalesCommission);
        tvPayments              = view.findViewById(R.id.tvPayments);
        tvDebitAndCredit        = view.findViewById(R.id.tvDebitAndCreditTxn);
        tvOpeningBalanceLabel   = view.findViewById(R.id.tvOpeningBalanceLabel);
        tvClosingBalanceLabel   = view.findViewById(R.id.tvClosingBalanceLabel);
        btnPrint                = view.findViewById(R.id.buttonPrint);
        tvSelectedDate = view.findViewById(R.id.selectedDate);

        refreshBalance();

        llFromDate.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
        ivProceed.setOnClickListener(this);
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
        tvSelectedDate.setText(updateDateFormat(currentDate,SharedPrefUtil.getLanguage(getContext())) +" - "+ updateDateFormat(currentDate, SharedPrefUtil.getLanguage(getContext())));
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
                callApi();
                break;
            case R.id.buttonPrint:
                if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2PRO) ||
                        Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2)
                        || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2s)
                        || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2_s)
                ) {
                    samplePrint();

                  /*  try {
                        String data = String.valueOf(service.updatePrinterState());
                        Log.e("asadsad",data.toString());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }*/

                } else {
                    Toast.makeText(getContext(),getString(R.string.device_not_connected),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callApi() {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        if (validate()) {
            ProgressBarDialog.getProgressDialog().showProgress(master);
            BalanceReportRequestBean model = new BalanceReportRequestBean();
            model.setEndDate(formatDate(tvEndDate));
            model.setOrgId(PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());
            model.setStartDate(formatDate(tvStartDate));
            model.setOrgTypeCode(AppConstants.ORG_TYPE_CODE);
            model.setLanguageCode("en");
            viewModel.callBalanceReportApi(PlayerData.getInstance().getToken(), menuBean.getBasePath() + menuBean.getRelativePath(), formatDate(tvStartDate), formatDate(tvEndDate));
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
            service.printTextWithFont( getString(R.string.balance_invoice_report)+ "\n\n", "", 25F, null);
            service.sendRAWData(boldFontDisable, null);


            service.sendRAWData(boldFontEnable, null);
            service.printTextWithFont(getString(R.string.date) + " " + array[0] + " " + getString(R.string.to) + " " + array[1], "", 21F, null);
            service.sendRAWData(boldFontDisable, null);
            //service.setAlignment(1, null);
            //service.setAlignment(0, null);
            service.printText("\n--------------------------------\n", null);

            service.printColumnsString(new String[] {getString(R.string.org_id_camel) + " " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId()},
                    new int[] {(getString(R.string.org_id_camel) + " " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId()).length()}, new int[] {0}, null);

            service.printColumnsString(new String[] {getString(R.string.organization_name) + " : " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()},
                    new int[] {(getString(R.string.organization_name) + " : " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName()).length()}, new int[] {0}, null);

            //service.printTextWithFont(getString(R.string.org_id_camel) + " " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId(), "", 23F, null);
            //service.printText("\n", null);
            //service.printTextWithFont(getString(R.string.organization_name) + " : " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName(), "", 23F, null);

            service.printText("\n--------------------------------\n", null);

            service.setAlignment(1, null);
            service.setFontSize(18F, null);
            service.sendRAWData(boldFontEnable, null);
            service.printColumnsString(new String[] {getString(R.string.opening_balance), getString(R.string.closing_balance)},
                    new int[] {getString(R.string.opening_balance).length(), getString(R.string.closing_balance).length()}, new int[] {1,1}, null);

            service.printColumnsString(new String[] {(" ( " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())) + " )"), (" ( " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())) + " )")},
                    new int[] {(" ( " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())) + " )").length(), (" ( " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())) + " )").length()}, new int[] {1,1}, null);

            service.printColumnsString(new String[] {tv_opening_balance.getText().toString(), tv_closing_balance.getText().toString()},
                    new int[] {tv_opening_balance.getText().toString().length(), tv_opening_balance.getText().toString().length()}, new int[] {1,1}, null);

            service.printText("------------------------------------------\n\n", null);

            service.sendRAWData(boldFontDisable, null);

            service.setFontSize(23F, null);

            service.printColumnsString(new String[] {getString(R.string.sales), tvSales.getText().toString()},
                    new int[] {(getString(R.string.sales)).length(), tvSales.getText().toString().length()}, new int[] {0,2}, null);

            service.printColumnsString(new String[] {getString(R.string.claims), tvClaim.getText().toString()},
                    new int[] {(getString(R.string.claims)).length(), tvClaim.getText().toString().length()}, new int[] {0,2}, null);

            service.printColumnsString(new String[] {getString(R.string.claims_tax), tvClaimsTax.getText().toString()},
                    new int[] {(getString(R.string.claims_tax)).length(), tvClaimsTax.getText().toString().length()}, new int[] {0,2}, null);

            /*service.printColumnsString(new String[] {getString(R.string.commission), tvCommission.getText().toString()},
                    new int[] {(getString(R.string.commission)).length(), tvCommission.getText().toString().length()}, new int[] {0,2}, null);*/
            ///

            service.printColumnsString(new String[] {getString(R.string.sales_commission), tvSalesCommission.getText().toString()},
                    new int[] {(getString(R.string.sales_commission)).length(), tvSalesCommission.getText().toString().length()}, new int[] {0,2}, null);

            service.printColumnsString(new String[] {getString(R.string.winning_commission), tvWinningsCommission.getText().toString()},
                    new int[] {(getString(R.string.winning_commission)).length(), tvWinningsCommission.getText().toString().length()}, new int[] {0,2}, null);

            service.printColumnsString(new String[] {getString(R.string.payments), tvPayments.getText().toString()},
                    new int[] {(getString(R.string.payments)).length(), tvPayments.getText().toString().length()}, new int[] {0,2}, null);

            service.printColumnsString(new String[] {getString(R.string.debit_credit_txn), tvDebitAndCredit.getText().toString()},
                    new int[] {(getString(R.string.debit_credit_txn)).length(), tvDebitAndCredit.getText().toString().length()}, new int[] {0,2}, null);

            service.printText("\n--------------------------------", null);

            service.printText("--------------------------------\n", null);


            service.printText("\n\n\n", null);
            service.updatePrinterState();

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

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
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
    Observer<BalanceReportResponseBean> observer = response -> {

        tvSelectedDate.setText(updateDateFormat(tvStartDate.getText().toString(),SharedPrefUtil.getLanguage(getContext())) +" - "+ updateDateFormat(tvEndDate.getText().toString(),SharedPrefUtil.getLanguage(getContext())));
        mSelectedDateForDisplay =tvStartDate.getText().toString()+"&"+tvEndDate.getText().toString();
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null) {
            setDefaultBalance();
            Utils.showCustomErrorDialog(getContext(), getString(R.string.balance_report), getString(R.string.something_went_wrong));
        } else if (response.getResponseCode() != 0) {
            String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
            setDefaultBalance();
            Utils.showCustomErrorDialog(getContext(), getString(R.string.balance_report), errorMsg);
        }
        else if(response.getResponseData().getStatusCode() != 0) {
            if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                return;
            setDefaultBalance();
            String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
            Utils.showCustomErrorDialog(getContext(), getString(R.string.balance_report), errorMsg);
        }
        else if (response.getResponseData().getStatusCode() == 0) {
            if (response.getResponseData() == null) {
                setDefaultBalance();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.balance_report), getString(R.string.no_data_found));
            } else if (response.getResponseData().getData() == null) {
                setDefaultBalance();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.balance_report), getString(R.string.no_data_found));
            } else {
                BalanceReportResponseBean.ResponseData.Data responseData = response.getResponseData().getData();
                tvOpeningBalanceLabel.setText(getString(R.string.opening_balance) + " ( " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())) + " )");
                tvClosingBalanceLabel.setText(getString(R.string.closing_balance) + " ( " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())) + " )");
                String openingBalance =  responseData.getOpeningBalance() + " " + "(" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())) + ")";
                String closingBalance =  responseData.getClosingBalance() + " " + "(" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())) + ")";
                openingBalanceValue = responseData.getOpeningBalance();
                closingBalanceValue = responseData.getClosingBalance();
                tv_opening_balance.setText(openingBalance);
                tv_closing_balance.setText(closingBalance);
                tvSales.setText(responseData.getSales());
                tvClaim.setText(responseData.getClaims());
                tvCommission.setText(responseData.getCommission());
                tvWinningsCommission.setText(responseData.getWinningsCommission());
                tvSalesCommission.setText(responseData.getSalesCommission());
                tvClaimsTax.setText(responseData.getClaimTax());
                tvPayments.setText(responseData.getPayments());
                tvDebitAndCredit.setText(responseData.getCreditDebitTxn());
                btnPrint.setVisibility(View.VISIBLE);
            }
        } else {
            setDefaultBalance();
            Utils.showCustomErrorDialog(getContext(), getString(R.string.balance_report), getString(R.string.something_went_wrong));
        }
    };

    private void setDefaultBalance(){
        btnPrint.setVisibility(View.GONE);
        tv_opening_balance.setText("-");
        tv_closing_balance.setText("-");
        tvSales.setText("-");
        tvClaim.setText("-");
        tvCommission.setText("-");
        tvWinningsCommission.setText("-");
        tvSalesCommission.setText("-");
        tvClaimsTax.setText("-");
        tvPayments.setText("-");
    }

    private String formatDate(TextView tvDate)  {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
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