package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.CallBackListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class ScratchReportDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private CallBackListener listener;
    private TextView tvPacks, tvInTransit, tvReceived, tvActivated, tvInvoiced;
    private ArrayList<String> listInTransit, listReceived, listActivated, listInvoiced;

    public ScratchReportDialog(Context context, ArrayList<String> listInTransit, ArrayList<String> listReceived, ArrayList<String> listActivated, ArrayList<String> listInvoiced) {
        super(context);
        this.context            = context;
        this.listInTransit      = listInTransit;
        this.listReceived       = listReceived;
        this.listActivated      = listActivated;
        this.listInvoiced       = listInvoiced;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_scratch_report);
        initializeWidgets();
    }

    private void initializeWidgets() {
        tvPacks         = findViewById(R.id.tvPacks);
        tvInTransit     = findViewById(R.id.tvInTransit);
        tvReceived      = findViewById(R.id.tvReceived);
        tvActivated     = findViewById(R.id.tvActivated);
        tvInvoiced      = findViewById(R.id.tvInvoiced);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 500;
            llContainer.setLayoutParams(params);
        }

        tvInTransit.setOnClickListener(this);
        tvReceived.setOnClickListener(this);
        tvActivated.setOnClickListener(this);
        tvInvoiced.setOnClickListener(this);

        tvPacks.setMovementMethod(new ScrollingMovementMethod());

        if (listInTransit == null) tvInTransit.setVisibility(View.GONE);
        else if (listInTransit.size() == 0) tvInTransit.setVisibility(View.GONE);

        if (listReceived == null) tvReceived.setVisibility(View.GONE);
        else if (listReceived.size() == 0) tvReceived.setVisibility(View.GONE);

        if (listActivated == null) tvActivated.setVisibility(View.GONE);
        else if (listActivated.size() == 0) tvActivated.setVisibility(View.GONE);

        if (listInvoiced == null) tvInvoiced.setVisibility(View.GONE);
        else if (listInvoiced.size() == 0) tvInvoiced.setVisibility(View.GONE);

        if (listInTransit != null && !listInTransit.isEmpty()) {
            makeHighlighted(tvInTransit);
            showPacks(listInTransit);
        }
        else if (listReceived != null && !listReceived.isEmpty()) {
            makeHighlighted(tvReceived);
            showPacks(listReceived);
        }
        else if (listActivated != null && !listActivated.isEmpty()) {
            makeHighlighted(tvActivated);
            showPacks(listActivated);
        }
        else if (listInvoiced != null && !listInvoiced.isEmpty()) {
            makeHighlighted(tvInvoiced);
            showPacks(listInvoiced);
        }

    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.tvInTransit:
                makeHighlighted(tvInTransit);
                showPacks(listInTransit);
                break;
            case R.id.tvReceived:
                makeHighlighted(tvReceived);
                showPacks(listReceived);
                break;
            case R.id.tvActivated:
                makeHighlighted(tvActivated);
                showPacks(listActivated);
                break;
            case R.id.tvInvoiced:
                makeHighlighted(tvInvoiced);
                showPacks(listInvoiced);
                break;
        }
    }

    private void showPacks(ArrayList<String> list) {
        String allPacks = "";
        for (String pack: list) {
            allPacks = allPacks + pack + "\n";
        }
        tvPacks.setText(allPacks);
        tvPacks.scrollTo(0,0);
    }

    private void makeHighlighted(TextView textView) {
        tvInTransit.setTextColor(ContextCompat.getColor(context, R.color.colorDarkGrey));
        tvReceived.setTextColor(ContextCompat.getColor(context, R.color.colorDarkGrey));
        tvActivated.setTextColor(ContextCompat.getColor(context, R.color.colorDarkGrey));
        tvInvoiced.setTextColor(ContextCompat.getColor(context, R.color.colorDarkGrey));

        tvInTransit.setTypeface(null, Typeface.NORMAL);
        tvReceived.setTypeface(null, Typeface.NORMAL);
        tvActivated.setTypeface(null, Typeface.NORMAL);
        tvInvoiced.setTypeface(null, Typeface.NORMAL);

        tvInTransit.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_12));
        tvReceived.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_12));
        tvActivated.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_12));
        tvInvoiced.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_12));

        textView.setTextColor(ContextCompat.getColor(context, R.color.colorAppOrange));
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_14));
    }
}
