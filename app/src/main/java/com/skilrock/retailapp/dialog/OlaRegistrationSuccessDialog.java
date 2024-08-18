package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.DialogCloseListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class OlaRegistrationSuccessDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String userName, password, depositAmount, retailerInfo;
    private int popCount = 0;
    private FragmentManager fragmentManager;
    private DialogCloseListener dialogCloseListener;
    private boolean isDepositError;

    public OlaRegistrationSuccessDialog(Context context, FragmentManager fragmentManager, String userName, String password, String depositAmount, boolean isDepositError, String retailerInfo, int popCount, DialogCloseListener dialogCloseListener) {
        super(context);
        this.context                = context;
        this.userName               = userName;
        this.password               = password;
        this.depositAmount          = depositAmount;
        this.isDepositError         = isDepositError;
        this.retailerInfo           = retailerInfo;
        this.popCount               = popCount;
        this.fragmentManager        = fragmentManager;
        this.dialogCloseListener    = dialogCloseListener;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_success_dialog_ola);
        initializeWidgets();
    }

    private void initializeWidgets() {
        TextView tvUserName         = findViewById(R.id.tvUserName);
        TextView tvPassword         = findViewById(R.id.tvPassword);
        TextView tvDepositAmount    = findViewById(R.id.tvDepositAmount);
        TextView tvDepositAmountTag = findViewById(R.id.tvDepositAmountTag);
        TextView tvRetailerInfo     = findViewById(R.id.tvRetailerInfo);
        Button button               = findViewById(R.id.button);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        tvUserName.setText(userName);
        tvPassword.setText(password);
        tvDepositAmount.setText(depositAmount);

        if (isDepositError) {
            tvDepositAmountTag.setText(context.getString(R.string.deposit_error_without_colon));
            tvDepositAmount.setTextColor(Color.parseColor("#b20000"));
        }

        tvRetailerInfo.setText(retailerInfo);

        if (TextUtils.isEmpty(retailerInfo))
            tvRetailerInfo.setVisibility(View.GONE);

        button.setOnClickListener(this);
        /*tvHeader.setText(TextUtils.isEmpty(header) ? context.getString(R.string.success) : header);
        tvMessage.setText(message);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Utils.vibrate(context);
                dismiss();
                for (int i = 0; i < popCount; i++)
                    fragmentManager.popBackStack();

                if (dialogCloseListener != null)
                    dialogCloseListener.onDialogClosed();
                break;
        }
    }
}
