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

public class LanguageSelectionDialog extends Dialog implements
        View.OnClickListener, AppConstants {

    private Activity context;
    private TextView tvFrench, tvEnglish, tvArabic;
    private Button btnDone;
    private String selectedLanguage;
    private EventListener listener;

    public LanguageSelectionDialog(Activity activity, EventListener listener) {
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
        setContentView(R.layout.dialog_language_selection);
        initializeWidgets();
    }

    private void initializeWidgets() {
        btnDone = findViewById(R.id.btn_done);
        tvFrench = findViewById(R.id.tvFrench);
        tvEnglish = findViewById(R.id.tvEnglish);
        tvArabic = findViewById(R.id.tvArabic);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        tvFrench.setText(FRENCH);
        tvArabic.setText(ARABIC);
        tvEnglish.setText(ENGLISH);

        tvFrench.setOnClickListener(this);
        tvEnglish.setOnClickListener(this);
        tvArabic.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        switch (SharedPrefUtil.getLanguage(context)) {
            case LANGUAGE_ARABIC:
                selectedLanguage = tvArabic.getTag().toString();
                setClickOperation(tvArabic);
                break;
            case LANGUAGE_ENGLISH:
                selectedLanguage = tvEnglish.getTag().toString();
                setClickOperation(tvEnglish);
                break;
            case LANGUAGE_FRENCH:
                selectedLanguage = tvFrench.getTag().toString();
                setClickOperation(tvFrench);
                break;
            default:
                selectedLanguage = tvFrench.getTag().toString();
                setClickOperation(tvFrench);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.tvFrench:
                selectedLanguage = tvFrench.getTag().toString();
                setClickOperation(tvFrench);
                break;
            case R.id.tvEnglish:
                selectedLanguage = tvEnglish.getTag().toString();
                setClickOperation(tvEnglish);
                break;
            case R.id.tvArabic:
                selectedLanguage = tvArabic.getTag().toString();
                setClickOperation(tvArabic);
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
        tvArabic.setTextColor(ContextCompat.getColor(context, R.color.colorLightGrey_2));
        tvEnglish.setTextColor(ContextCompat.getColor(context, R.color.colorLightGrey_2));
        tvFrench.setTextColor(ContextCompat.getColor(context, R.color.colorLightGrey_2));

        tvArabic.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tvEnglish.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tvFrench.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

        textView.setTextColor(ContextCompat.getColor(context, R.color.colorAppOrange));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
    }
}
