package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatTextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.scratch.WinningDisplayDialogBean;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

public class MultiTicketVerifySuccessDialog extends Dialog implements View.OnClickListener {

    private final Context context;
    private final WinningDisplayDialogBean respModel;

    public MultiTicketVerifySuccessDialog(Context context, WinningDisplayDialogBean respModel) {
        super(context);
        this.context   = context;
        this.respModel = respModel;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_multi_ticket_verify);
        initializeWidgets();
    }

    private void initializeWidgets() {
        AppCompatTextView etWinAmount        = findViewById(R.id.etWinAmount);
        AppCompatTextView etTaxAmount        = findViewById(R.id.etTaxAmount);
        AppCompatTextView etNetAmount        = findViewById(R.id.etNetAmount);
        AppCompatTextView etTicketNumber     = findViewById(R.id.et_ticket_number);
        AppCompatTextView etGameName         = findViewById(R.id.etGameName);
        Button closeBtn                      = findViewById(R.id.closeBtn);

        closeBtn.setOnClickListener(this);


        etWinAmount.setText(Utils.getUkFormat(Double.parseDouble(respModel.getWinningAmount())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));
        etTaxAmount.setText(Utils.getUkFormat(Double.parseDouble(respModel.getTaxAmount())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));
        etNetAmount.setText(Utils.getUkFormat(Double.parseDouble(respModel.getNetWinningAmount())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));
        etTicketNumber.setText(respModel.getTicketNumber());
        etGameName.setText(respModel.getGameName());


    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.closeBtn: {
                dismiss();
                break;
            }
        }
    }


}
