package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.InputListener;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class InputDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String header, hint, etText;
    private TextInputEditText etInput;
    private InputListener listener;
    private TextInputLayout layoutInput;
    private int editTextMaxLength, editTextInputType;

    public InputDialog(Context context, InputListener listener, String header, String hint, int editTextMaxLength, int editTextInputType, String etText) {
        super(context);
        this.context            = context;
        this.listener           = listener;
        this.header             = header;
        this.hint               = hint;
        this.etText             = etText;
        this.editTextMaxLength  = editTextMaxLength;
        this.editTextInputType  = editTextInputType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_input);
        initializeWidgets();
    }

    private void initializeWidgets() {
        etInput             = findViewById(R.id.et_input);
        layoutInput         = findViewById(R.id.layout_input);
        Button btnSubmit    = findViewById(R.id.btn_submit);
        Button btnCancel    = findViewById(R.id.btn_cancel);
        TextView tvHeader   = findViewById(R.id.tvHeader);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        etInput.setText(etText);
        if (etInput.getText() != null)
            etInput.setSelection(etInput.getText().toString().length());
        layoutInput.setHint(hint);
        setEditTextMaxLength(editTextMaxLength);
        etInput.setInputType(editTextInputType);

        tvHeader.setText(header);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Utils.vibrate(context);
                if (validate()) {
                    if (etInput.getText() != null)
                        listener.onInputReceived(etInput.getText().toString().trim());
                    dismiss();
                }
                break;
            case R.id.btn_cancel:
                Utils.vibrate(context);
                dismiss();
                break;
        }
    }

    private void setEditTextMaxLength(int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        etInput.setFilters(filterArray);
    }

    private boolean validate() {
        if (TextUtils.isEmpty(Objects.requireNonNull(etInput.getText()).toString().trim())) {
            etInput.setError(context.getString(R.string.this_field_cannot_be_empty));
            etInput.requestFocus();
            layoutInput.startAnimation(Utils.shakeError());
            return false;
        }
        return true;
    }
}
