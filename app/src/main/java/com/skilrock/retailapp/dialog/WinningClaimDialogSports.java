package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.skilrock.retailapp.sle_game_portrait.VerifyPayTicket;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class WinningClaimDialogSports extends Dialog implements
        View.OnClickListener {
    private Context context;
    private String header;
    private boolean isVerify;
    private WinningClaimListener listener;
    private VerifyPayTicket bean;
    private boolean showBalanceErrorNote;
    private String dotFormatter;
    private String amountFormatter;
    private String winAmount = " ";

    public WinningClaimDialogSports(Context context, WinningClaimListener listener, String header, boolean isVerify, VerifyPayTicket bean, boolean showBalanceErrorNote) {
        super(context);
        this.context = context;
        this.header = header;
        this.isVerify = isVerify;
        this.bean = bean;
        this.listener = listener;
        this.showBalanceErrorNote = showBalanceErrorNote;

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
        TextView tvHeader = findViewById(R.id.tvHeader);
        Button button = findViewById(R.id.button);
        Button button_cancel = findViewById(R.id.btn_cancel);
        ImageView ivIcon = findViewById(R.id.ivIcon);
        LinearLayout llTransNumber = findViewById(R.id.llTransactionNumber);
        LinearLayout llTransDate = findViewById(R.id.llTransactionDate);
        TextView tvTransNumber = findViewById(R.id.tvTransNumber);
        TextView tvTransDate = findViewById(R.id.tvTransDate);
        TextView tvTicketNumber = findViewById(R.id.tvTicketNumber);
        TextView tvWinningAmount = findViewById(R.id.tvWinningAmount);
        TextView tvTdsAmount = findViewById(R.id.tvTdsAmount);
        TextView tvCommission = findViewById(R.id.tvCommission);
        TextView tvBalanceNote = findViewById(R.id.tvBalanceNote);
        TextView tvNetWinningAmount = findViewById(R.id.tvNetWinningAmount);
        LinearLayout llTdsAmount = findViewById(R.id.llTdsAmount);
        LinearLayout llNetWinningAmount = findViewById(R.id.llNetWinningAmount);//llWinningAmount

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        //LinearLayout llCommission           = findViewById(R.id.llCommission);
        dotFormatter = Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context));
        amountFormatter = Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context));
        //ivIcon.setBackgroundResource(isVerify ? R.drawable.icon_verify : R.drawable.icon_success);
        tvHeader.setText(TextUtils.isEmpty(header) ? context.getString(R.string.success) : header);
        if (isVerify) {
            llTransNumber.setVisibility(View.GONE);
            llTransDate.setVisibility(View.GONE);
            llTdsAmount.setVisibility(View.GONE);
            //llCommission.setVisibility(View.GONE);
            llNetWinningAmount.setVisibility(View.GONE);
            button.setText(context.getString(R.string.claim));
            ivIcon.setBackgroundResource(R.drawable.icon_verify);
        } else {
            llTransNumber.setVisibility(View.VISIBLE);
            llTransDate.setVisibility(View.VISIBLE);
            //llCommission.setVisibility(View.VISIBLE);
            /*tvTransNumber.setText(model.getTransactionNumber());
            tvTransDate.setText(model.getResponseData().getT);
            tvCommission.setText(model.getCommissionAmount());
            button.setText(context.getString(R.string.ok));
            ivIcon.setBackgroundResource(R.drawable.icon_success);
            tvTdsAmount.setText(String.format("%s", model.getTdsAmount()));
            tvNetWinningAmount.setText(String.format("%s", model.getRespons));*/
        }

        tvTicketNumber.setText(bean.getTicketNo());
        winAmount = Utils.getBalanceToView(Double.parseDouble(bean.getPrizeAmt()), ",", " ", 0);
        tvWinningAmount.setText(winAmount);
        if (showBalanceErrorNote)
            tvBalanceNote.setVisibility(View.VISIBLE);

        button.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
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



