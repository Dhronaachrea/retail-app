package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.drawgame.BetAmountDialogAdapterLand;
import com.skilrock.retailapp.models.drawgames.FiveByNinetyBetAmountBean;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class BetAmountDialogLand extends Dialog implements View.OnClickListener, BetAmountDialogAdapterLand.OnAmountSelectionListener {

    private Context context;
    private ArrayList<FiveByNinetyBetAmountBean> listAmount;
    private OnAmountUpdatedListener listener;
    private int SELECTED_AMOUNT = -1;
    private int SELECTED_POSITION = -1;

    public BetAmountDialogLand(Context context, ArrayList<FiveByNinetyBetAmountBean> listAmount, OnAmountUpdatedListener listener) {
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
        Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setDimAmount(0.7f);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationZoom;

        setContentView(R.layout.dialog_bet_amount_land);
        initializeWidgets();
    }

    private void initializeWidgets() {
        Button btnCancel            = findViewById(R.id.btn_cancel);
        Button btnReturn            = findViewById(R.id.btn_submit);
        TextView tvHeader           = findViewById(R.id.tvHeader);
        RecyclerView recyclerView   = findViewById(R.id.recyclerView);

        String header =  this.getContext().getString(R.string.select_amount);
        tvHeader.setText(header);
        btnCancel.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 7));
        BetAmountDialogAdapterLand adapter = new BetAmountDialogAdapterLand(context, listAmount, this);
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
                    listener.onAmountChange(SELECTED_AMOUNT, SELECTED_POSITION);
                    dismiss();
                }
                break;
            case R.id.btn_cancel:
                listener.onAmountChange(-1, SELECTED_POSITION);
                dismiss();
                break;
        }
    }

    @Override
    public void onAmountSelected(int amount, int position) {
        SELECTED_AMOUNT = amount;
        SELECTED_POSITION = position;
    }

    public interface OnAmountUpdatedListener {
        void onAmountChange(int amount, int position);
    }
}