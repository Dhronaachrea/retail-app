package com.skilrock.retailapp.dialog;

import static com.skilrock.retailapp.utils.Utils.getNewFormattedValue;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.WinningClaimListener;
import com.skilrock.retailapp.models.scratch.WinningDisplayDialogBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class ClaimDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String header;
    private FragmentManager fragmentManager;
    private boolean isVerify;
    private WinningClaimListener listener;
    private WinningDisplayDialogBean model;
    private boolean showBalanceErrorNote;

    public ClaimDialog(Context context, FragmentManager fragmentManager, WinningClaimListener listener, String header, boolean isVerify, WinningDisplayDialogBean model, boolean showBalanceErrorNote) {
        super(context);
        this.context                = context;
        this.header                 = header;
        this.fragmentManager        = fragmentManager;
        this.isVerify               = isVerify;
        this.model                  = model;
        this.listener               = listener;
        this.showBalanceErrorNote   = showBalanceErrorNote;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO)) {
            setContentView(R.layout.layout_claim_dialog_v2pro);
        } else if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            setContentView(R.layout.layout_claim_dialog_t2mini);
        } else {
            setContentView(R.layout.layout_claim_dialog);
        }

        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        initializeWidgets();
    }

    private void initializeWidgets() {
        TextView tvHeader                   = findViewById(R.id.tvHeader);
        Button button                       = findViewById(R.id.button);
        Button button_cancel                = findViewById(R.id.btn_cancel);
        ImageView ivIcon                    = findViewById(R.id.ivIcon);
        LinearLayout llTransNumber          = findViewById(R.id.llTransactionNumber);
        LinearLayout llTransDate            = findViewById(R.id.llTransactionDate);
        TextView tvTransNumber              = findViewById(R.id.tvTransNumber);
        TextView tvTransDate                = findViewById(R.id.tvTransDate);
        TextView tvTicketNumber             = findViewById(R.id.tvTicketNumber);
        TextView tvWinningAmount            = findViewById(R.id.tvWinningAmount);
        TextView tvTdsAmount                = findViewById(R.id.tvTdsAmount);
        TextView tvCommission               = findViewById(R.id.tvCommission);
        TextView tvBalanceNote              = findViewById(R.id.tvBalanceNote);
        TextView tvNetWinningAmount         = findViewById(R.id.tvNetWinningAmount);
        TextView tvTaxAmount                = findViewById(R.id.tvTaxAmount);
        LinearLayout llTdsAmount            = findViewById(R.id.llTdsAmount);
        LinearLayout llNetWinningAmount     = findViewById(R.id.llNetWinningAmount);
        LinearLayout llTaxAmount            = findViewById(R.id.llTaxAmount);


        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        tvHeader.setText(TextUtils.isEmpty(header) ? context.getString(R.string.success) : header);
        if (isVerify) {
            llTransNumber.setVisibility(View.GONE);
            llTransDate.setVisibility(View.GONE);
            llTdsAmount.setVisibility(View.GONE);
            //llCommission.setVisibility(View.GONE);
            llNetWinningAmount.setVisibility(View.VISIBLE);
            llTaxAmount.setVisibility(View.VISIBLE);
            button.setText(context.getString(R.string.claimNew));
            ivIcon.setBackgroundResource(R.drawable.icon_verify);

            button_cancel.setVisibility(View.VISIBLE);
        } else {
            llTransNumber.setVisibility(View.VISIBLE);
            llTransDate.setVisibility(View.VISIBLE);
//            llCommission.setVisibility(View.VISIBLE);
            tvTransNumber.setText(model.getTransactionNumber());
            tvTransDate.setText(model.getTransactionDate());
            tvCommission.setText(model.getCommissionAmount());
            button.setText(context.getString(R.string.ok));
            ivIcon.setBackgroundResource(R.drawable.icon_success);
            //tvTdsAmount.setText(String.format("%s", model.getTdsAmount()));
            //tvTdsAmount.setText(String.format("%s", getFormattedAmount(String.valueOf(model.getTdsAmount()))));
            //tvNetWinningAmount.setText(String.format("%s", getFormattedAmount(String.valueOf(model.getNetWinningAmount()))));
            tvNetWinningAmount.setText(String.format("%s", model.getNetWinningAmount()));
        }

        tvTicketNumber.setText(model.getTicketNumber());
        //tvWinningAmount.setText(String.format("%s", model.getWinningAmount()));
        //tvWinningAmount.setText(String.format("%s", getFormattedAmount(String.valueOf(model.getWinningAmount()))));

        try {
            tvWinningAmount.setText(getNewFormattedValue(String.format("%s", model.getWinningAmount())));
            tvTaxAmount.setText(getNewFormattedValue(String.format("%s", model.getTaxAmount())));
            tvNetWinningAmount.setText(getNewFormattedValue(String.format("%s", model.getNetWinningAmount())));
        } catch (Exception e) {
            Log.i("TaG","error ocurre-->" + e);
            tvWinningAmount.setText(String.format("%s", model.getWinningAmount()));
            tvTaxAmount.setText(String.format("%s", model.getTaxAmount()));
            tvNetWinningAmount.setText(String.format("%s", model.getNetWinningAmount()));
        }



        if (showBalanceErrorNote)
            tvBalanceNote.setVisibility(View.VISIBLE);

        button.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
    }

    private String getFormattedAmount(String amt) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMANY);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###,###.##", otherSymbols);

        return df.format(Double.parseDouble(amt));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                dismiss();
                listener.dialogButtonPress(isVerify);
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
}
