package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.RemarkListener;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;
import java.util.Objects;

public class CustomRemarksDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String title;
    private RemarkListener remarkListener;
    private TextInputLayout layoutRemarks;
    private EditText etRemark;
    private String info;
    private String selectedText;
    private boolean isSelected = false;
    private List<String> defaultList;
    private RadioGroup radioGroup;
    private LinearLayout llOtherContainer;
    private boolean showOtherField;
    private String remarkMsg;

    public CustomRemarksDialog(Context context, RemarkListener remarkListener, String title, String info, List<String> list, String selectedText, boolean showOtherField, String remarkMsg) {
        super(context);
        this.context        = context;
        this.remarkListener = remarkListener;
        this.title          = title;
        this.info           = info;
        this.defaultList    = list;
        this.selectedText   = selectedText;
        this.showOtherField = showOtherField;
        this.remarkMsg      = remarkMsg;
        this.defaultList.add(context.getString(R.string.other));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_remarks);
        if (SharedPrefUtil.getLanguage(context).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            ((MainActivity) context).getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            ((MainActivity) context).getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        initializeWidgets();
    }

    private void initializeWidgets() {
        etRemark            = findViewById(R.id.et_remark);
        layoutRemarks       = findViewById(R.id.til_remark);
        radioGroup          = findViewById(R.id.radio_group);
        llOtherContainer    = findViewById(R.id.llOtherContainer);
        Button btnSubmit    = findViewById(R.id.btn_submit);
        Button btnCancel    = findViewById(R.id.btn_cancel);
        TextView tvTitle    = findViewById(R.id.tvHeader);
        TextView tvInfo     = findViewById(R.id.tvInfo);

        tvTitle.setText(title);
        tvInfo.setText(info);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        setRadioButtons(defaultList);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checkedRadioButton = group.findViewById(checkedId);
            boolean isChecked = checkedRadioButton.isChecked();
            if (checkedRadioButton.getText().toString().equalsIgnoreCase(context.getString(R.string.other))) {
                llOtherContainer.setVisibility(View.VISIBLE);
            }
            else if (isChecked) {
                remarkListener.onRemarkListener(checkedRadioButton.getText().toString(), false);
                dismiss();
            }
        });

        if (showOtherField) {
            llOtherContainer.setVisibility(View.VISIBLE);
            etRemark.setText(remarkMsg);
        }

        if (remarkMsg == null) {
            llOtherContainer.setVisibility(View.GONE);
            etRemark.setText("");
        }
        else if (remarkMsg.trim().isEmpty()) {
            llOtherContainer.setVisibility(View.GONE);
            etRemark.setText("");
        }

        if (SharedPrefUtil.getLanguage(context).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            etRemark.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Utils.vibrate(context);
                if (validate()) {
                    remarkListener.onRemarkListener(etRemark.getText().toString(), true);
                    dismiss();
                }
                break;
            case R.id.btn_cancel:
                Utils.vibrate(context);
                dismiss();
                break;
        }
    }

    private void setRadioButtons(List<String> list) {
        RadioGroup.LayoutParams rprms;
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        radioGroup.setGravity(Gravity.START);
        for (int row = 0; row < list.size(); row++) {
            AppCompatRadioButton rdbtn = new AppCompatRadioButton(context);
            rdbtn.setId(View.generateViewId());
            rdbtn.setTextColor(context.getResources().getColor(R.color.colorDarkGrey));
            rdbtn.setHighlightColor(context.getResources().getColor(R.color.colorDarkGrey));
            rdbtn.setTextDirection(View.TEXT_ALIGNMENT_TEXT_START);
            rdbtn.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

            if (SharedPrefUtil.getLanguage(context).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
                rdbtn.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            rdbtn.setTextSize(13);
            rdbtn.setText(list.get(row));

            if (selectedText.equalsIgnoreCase(list.get(row))) {
                rdbtn.setChecked(true);
                isSelected = true;
            }

            rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.addView(rdbtn, rprms);
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(getText(etRemark))) {
            etRemark.setError(context.getString(R.string.this_field_cannot_be_empty));
            etRemark.requestFocus();
            layoutRemarks.startAnimation(Utils.shakeError());
            return false;
        }

        if (context != null) {
            if (!Utils.isNetworkConnected(getContext())) {
                Toast.makeText(context, context.getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }
}
