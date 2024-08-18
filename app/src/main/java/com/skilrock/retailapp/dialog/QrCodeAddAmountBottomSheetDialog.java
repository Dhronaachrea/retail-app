package com.skilrock.retailapp.dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class QrCodeAddAmountBottomSheetDialog extends BottomSheetDialogFragment {

    AddAmountToQrListener listener;

    public QrCodeAddAmountBottomSheetDialog() {

    }

    public QrCodeAddAmountBottomSheetDialog(AddAmountToQrListener listener) {
        this.listener = listener;
    }

    public static String TAG = "QrCodeAddAmountBottomSheetDialog";
    private TextInputEditText etAmountQR;
    private TextInputLayout tilAmountQR;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogSheetStyle);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_qr_amount, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etAmountQR          = view.findViewById(R.id.etAmountQR);
        tilAmountQR         = view.findViewById(R.id.tilAmountQR);
        Button btnCancel    = view.findViewById(R.id.btnCancel);
        Button btnAdd       = view.findViewById(R.id.btnAdd);

        btnCancel.setOnClickListener(view1 -> dismiss());

        btnAdd.setOnClickListener(view1 -> {
            if (validate()) {
                dismiss();
                listener.onAddAmountToQr(getEditTextText());
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(getEditTextText())) {
            etAmountQR.setError(getString(R.string.enter_amount));
            etAmountQR.requestFocus();
            tilAmountQR.startAnimation(Utils.shakeError());
            return false;
        }

        if (getEditTextText().contains(".")) {
            Log.d("log", "Double Value");
            try {
                double amt = Double.parseDouble(getEditTextText());
                if (amt == 0.0) {
                    etAmountQR.setError(getString(R.string.enter_valid_amount));
                    etAmountQR.requestFocus();
                    tilAmountQR.startAnimation(Utils.shakeError());
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                etAmountQR.setError(getString(R.string.enter_valid_amount));
                etAmountQR.requestFocus();
                tilAmountQR.startAnimation(Utils.shakeError());
                return false;
            }
        }
        else {
            Log.d("log", "Integer Value");
            try {
                int amt = Integer.parseInt(getEditTextText());
                if (amt == 0) {
                    etAmountQR.setError(getString(R.string.enter_valid_amount));
                    etAmountQR.requestFocus();
                    tilAmountQR.startAnimation(Utils.shakeError());
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                etAmountQR.setError(getString(R.string.enter_valid_amount));
                etAmountQR.requestFocus();
                tilAmountQR.startAnimation(Utils.shakeError());
                return false;
            }
        }

        return true;
    }

    private String getEditTextText() {
        return Objects.requireNonNull(etAmountQR.getText()).toString().trim();
    }

    public interface AddAmountToQrListener {
        void onAddAmountToQr(String amount);
    }

}
