package com.skilrock.retailapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.EventListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class LanguageSelectionMyanmarDialog extends Dialog implements
        View.OnClickListener, AppConstants {

    private Activity context;
    private TextView tvThai, tvEnglish, tvBurmese;
    private Button btnDone;
    private String selectedLanguage;
    private EventListener listener;

    public LanguageSelectionMyanmarDialog(Activity activity, EventListener listener) {
        super(activity);
        this.context        = activity;
        this.listener       = listener;
        selectedLanguage    = "";
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_language_selection_myanmar);
        initializeWidgets();
    }

    private void initializeWidgets() {
        btnDone     = findViewById(R.id.btn_done);
        tvThai      = findViewById(R.id.tvThai);
        tvEnglish   = findViewById(R.id.tvEnglish);
        tvBurmese   = findViewById(R.id.tvBurmese);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        tvThai.setOnClickListener(this);
        tvEnglish.setOnClickListener(this);
        tvBurmese.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        switch (SharedPrefUtil.getLanguage(context)) {
            case LANGUAGE_THAI:
                selectedLanguage = tvThai.getTag().toString();
                setClickOperation(tvThai);
                break;
            case LANGUAGE_BURMESE:
                selectedLanguage = tvBurmese.getTag().toString();
                setClickOperation(tvBurmese);
                break;
            default:
                selectedLanguage = tvEnglish.getTag().toString();
                setClickOperation(tvEnglish);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.tvThai:
                selectedLanguage = tvThai.getTag().toString();
                setClickOperation(tvThai);
                break;
            case R.id.tvEnglish:
                selectedLanguage = tvEnglish.getTag().toString();
                setClickOperation(tvEnglish);
                break;
            case R.id.tvBurmese:
                selectedLanguage = tvBurmese.getTag().toString();
                setClickOperation(tvBurmese);
                break;
            case R.id.btn_done:
                if (validate()) {
                    SharedPrefUtil.putLanguage(context, selectedLanguage);
                    Utils.updateLanguage(context, selectedLanguage);
                    dismiss();
                    listener.notifyEvent();
                }
                break;
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(selectedLanguage)) {
            Toast.makeText(context, context.getString(R.string.please_select_any_language), Toast.LENGTH_SHORT).show();
            btnDone.startAnimation(Utils.shakeError());
            return false;
        }
        return true;
    }

    private void setClickOperation (TextView textView) {
        tvBurmese.setTextColor(ContextCompat.getColor(context, R.color.colorLightGrey_2));
        tvEnglish.setTextColor(ContextCompat.getColor(context, R.color.colorLightGrey_2));
        tvThai.setTextColor(ContextCompat.getColor(context, R.color.colorLightGrey_2));

        tvBurmese.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tvEnglish.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tvThai.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

        textView.setTextColor(ContextCompat.getColor(context, R.color.colorAppOrange));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
    }
}
