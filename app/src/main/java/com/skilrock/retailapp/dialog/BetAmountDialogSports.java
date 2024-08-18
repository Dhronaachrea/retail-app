package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.sle_game_portrait.BetAmountDialogAdapterSports;
import com.skilrock.retailapp.sle_game_portrait.SportsBetAmountBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class BetAmountDialogSports extends Dialog implements View.OnClickListener, BetAmountDialogAdapterSports.OnAmountSelectionListener {

    private Context context;
    private ArrayList<SportsBetAmountBean> listAmount;
    private OnAmountUpdatedListener listener;
    private int SELECTED_AMOUNT = -1;
    private int slected_pos=-1;

    public BetAmountDialogSports(Context context, ArrayList<SportsBetAmountBean> listAmount, OnAmountUpdatedListener listener) {
        super(context);
        this.context        = context;
        this.listAmount     = listAmount;
        this.listener       = listener;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_bet_amount);
        initializeWidgets();
    }

    private void initializeWidgets() {
        Button btnCancel            = findViewById(R.id.btn_cancel);
        Button btnReturn            = findViewById(R.id.btn_submit);
        TextView tvHeader           = findViewById(R.id.tvHeader);
        RecyclerView recyclerView   = findViewById(R.id.recyclerView);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 550;
            llContainer.setLayoutParams(params);
        }

        //String header = context.getString(R.string.select_amount) + " (" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)) + ")";
        String header = context.getString(R.string.select_amount);
        tvHeader.setText(header);
        btnCancel.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
        BetAmountDialogAdapterSports adapter = new BetAmountDialogAdapterSports(context, listAmount, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.btn_submit:
                if (SELECTED_AMOUNT == -1) {
                    Utils.showRedToast(context, "Please select amount.");
                }
                else {
                    listener.onAmountChange(SELECTED_AMOUNT,slected_pos);
                    dismiss();
                }
                break;
            case R.id.btn_cancel:
                listener.onAmountChange(-1,-1);
                dismiss();
                break;
        }
    }

    @Override
    public void onAmountSelected(int amount,int pos) {
        SELECTED_AMOUNT = amount;
        slected_pos=pos;

    }

    public interface OnAmountUpdatedListener {
        void onAmountChange(int amount,int pos);
    }
}
