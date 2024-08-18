package com.skilrock.retailapp.ui.fragments.rms;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.QrCodeAddAmountBottomSheetDialog;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.QRCodeHelper;
import com.skilrock.retailapp.utils.QrCodeData;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

public class DisplayQRFragment extends BaseFragment implements QrCodeAddAmountBottomSheetDialog.AddAmountToQrListener {

    private TextView tvEnterAmount, tvAmount, tvOptional;
    private ImageView ivQrCode;
    private LinearLayout llEnterAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_qr, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
        renderQr("");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        TextView tvNameQr       = view.findViewById(R.id.tvNameQr);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        ivQrCode                = view.findViewById(R.id.ivQrCode);
        llEnterAmount           = view.findViewById(R.id.llEnterAmount);
        tvEnterAmount           = view.findViewById(R.id.tvEnterAmount);
        tvAmount                = view.findViewById(R.id.tvAmount);
        tvOptional              = view.findViewById(R.id.tvOptional);
        refreshBalance();

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
        }

        tvNameQr.setText(PlayerData.getInstance().getUsername().toUpperCase());
        llEnterAmount.setOnClickListener(view1 -> onClickAddAmount());
    }

    private void renderQr(String amount) {
        String name = PlayerData.getInstance().getUsername().toUpperCase();
        String qrData = PlayerData.getInstance().getLoginData().getResponseData().getData().getQrCode();

        QrCodeData qrCodeData = new QrCodeData();
        qrCodeData.setName(name);
        qrCodeData.setDestinationAccount(qrData);
        qrCodeData.setMerchantId("RMS");
        qrCodeData.setAmount(amount);

        String serializeString = new Gson().toJson(qrCodeData);
        Bitmap bitmap = QRCodeHelper
                .newInstance(master)
                .setContent(serializeString)
                .setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
                .setMargin(2)
                .getQRCOde();

        ivQrCode.setImageBitmap(bitmap);
    }

    private void onClickAddAmount() {
        if (tvEnterAmount.getText().toString().trim().equalsIgnoreCase(getString(R.string.enter_amount_init_caps))) {
            QrCodeAddAmountBottomSheetDialog qrCodeAddAmountBottomSheetDialog =
                    new QrCodeAddAmountBottomSheetDialog(this);
            qrCodeAddAmountBottomSheetDialog.show(getFragmentManager(),
                    QrCodeAddAmountBottomSheetDialog.TAG);
        }
        else {
            tvEnterAmount.setText(getString(R.string.enter_amount_init_caps));
            tvOptional.setVisibility(View.VISIBLE);
            tvAmount.setText("");
            tvAmount.setVisibility(View.GONE);
            renderQr("");
        }
    }

    @Override
    public void onAddAmountToQr(String amount) {
        tvEnterAmount.setText(getString(R.string.remove_amount));
        tvOptional.setVisibility(View.INVISIBLE);
        tvAmount.setText((Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(master)) + " " + amount));
        tvAmount.setVisibility(View.VISIBLE);
        llEnterAmount.setVisibility(View.GONE);
        renderQr(amount);
    }
}
