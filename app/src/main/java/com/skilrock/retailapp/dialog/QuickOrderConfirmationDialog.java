package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.CallBackListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class QuickOrderConfirmationDialog extends Dialog implements
        View.OnClickListener {

    private CallBackListener listener;
    private String amount;
    private int totalBooks;
    private String totalCommission;
    private Context context;
    private String amountToShow;
    private String totalCommissionFormatted;
    private String totalCommisionToShow;

    public QuickOrderConfirmationDialog(Context context, CallBackListener listener, String amount,
                                        int totalBooks, String totalCommission, String amountToShow,
                                        String totalCommissionFormatted, String totalCommisionToShow) {
        super(context);
        this.context            = context;
        this.listener           = listener;
        this.amount             = amount;
        this.totalBooks         = totalBooks;
        this.totalCommission    = totalCommission;
        this.amountToShow       = amountToShow;
        this.totalCommissionFormatted = totalCommissionFormatted;
        this.totalCommisionToShow = totalCommisionToShow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.quick_order_confirmation_dialog);
        initializeWidgets();
    }

    private void initializeWidgets() {
        TextView tvNoOfBooks    = findViewById(R.id.tvNoOfBooks);
        TextView tvAmount       = findViewById(R.id.tvAmount);
        TextView tvCommission   = findViewById(R.id.tvCommission);
        Button btnPay           = findViewById(R.id.btn_Pay);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        tvNoOfBooks.setText(String.format("%s", totalBooks));
        //tvAmount.setText(String.format("%s", Utils.getFormattedValue(Double.parseDouble(amount))));
        tvAmount.setText(String.format("%s", getFormattedAmount(amount)));
        tvAmount.setText(amountToShow);
        /*String strPayable = String.format(context.getString(R.string.total_commission_place_holder), (Double.parseDouble(amount) - Double.parseDouble(totalCommission)))
                + "\n" + String.format(context.getString(R.string.amount_payable_place_holder), totalCommission);*/
        //String totComm = String.format(Locale.US, "%.2f", (Double.parseDouble(amount) - Double.parseDouble(totalCommission)));
        /*if (totComm.contains(".")) {
            if (Integer.parseInt(totComm.split("\\.")[1]) == 0) {
                totComm = totComm.split("\\.")[0];
            }
        }*/

        /*String payable = String.format(Locale.US, "%.2f", Double.parseDouble(totalCommission));
        if (payable.contains(".")) {
            if (Integer.parseInt(payable.split("\\.")[1]) == 0) {
                payable = payable.split("\\.")[0];
            }
        }*/
        String strPayable = context.getString(R.string.total_commision) + " " + totalCommisionToShow +
                "\n" + String.format(context.getString(R.string.amount_payable_place_holder), totalCommission);

        /*String strPayable = context.getString(R.string.total_commision) + " " + totComm +
                 "\n" + String.format(context.getString(R.string.amount_payable_place_holder), String.format(Locale.US, "%.2f", Double.parseDouble(totalCommission)));*/
        tvCommission.setText(strPayable);
        btnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Pay:
                listener.notifyEvent();
                dismiss();
                break;
        }
    }

    private String getFormattedAmount(String amt) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMANY);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###,###.##", otherSymbols);

        return df.format(Double.parseDouble(amt));
    }

    private String getTempFormattedAmount(String cash) {
        String[] casharr = cash.split(",");

        Double amt = Double.parseDouble(casharr[0]);

        String startamount = String.format("%.0f", amt);
        startamount = getFormattedAmount(startamount);

        return startamount.replace(",", " ") + "," + casharr[1];
    }

}
