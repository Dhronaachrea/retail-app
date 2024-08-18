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
import com.skilrock.retailapp.adapter.drawgame.DateSelectDialogAdapter;
import com.skilrock.retailapp.models.OlaNetGamingExecutionResponseBean;
import com.skilrock.retailapp.ui.fragments.ola.OlaNetGamingReportFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class DateSelectionDialog extends Dialog implements View.OnClickListener, DateSelectDialogAdapter.OnDateSelectionListener {

    private Context context;
    ArrayList<OlaNetGamingExecutionResponseBean.ResponseDatum> date;
    private OnDateChange listener;
    private int SELECTED_DATE = -1;

    public DateSelectionDialog(Context context, ArrayList<OlaNetGamingExecutionResponseBean.ResponseDatum> date, OlaNetGamingReportFragment listener) {
        super(context);
        this.context = context;
        this.date = date;
        this.listener = listener;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_date_selection);
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
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        btnCancel.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        DateSelectDialogAdapter adapter = new DateSelectDialogAdapter(context, date, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.btn_submit:
                if (SELECTED_DATE == -1) {
                    Utils.showRedToast(context, "Please select Date.");
                }
                else {
                    listener.onDateSelect(SELECTED_DATE);
                    dismiss();
                }
                break;
            case R.id.btn_cancel:
                listener.onDateSelect(-1);
                dismiss();
                break;
        }
    }


    @Override
    public void onDateSelected(int pos) {
        SELECTED_DATE = pos;
    }

    public interface OnDateChange {
        void onDateSelect(int amount);
    }
}
