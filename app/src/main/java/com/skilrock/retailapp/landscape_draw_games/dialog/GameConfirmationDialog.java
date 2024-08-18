package com.skilrock.retailapp.landscape_draw_games.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class GameConfirmationDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private String info, softMsg, cancelBtnMsg, okBtnMsg;
    private TextView tvInfo;
    private ImageView ivIcon;
    private boolean isResetPassword;
    private ConfirmationListener listener;
    private int image;

    public GameConfirmationDialog(Context context, int image, String info, String softMsg, String cancelBtnMsg, String okBtnMsg, ConfirmationListener listener) {
        super(context);
        this.context            = context;
        this.info               = info;
        this.listener           = listener;
        this.softMsg            = softMsg;
        this.image              = image;
        this.cancelBtnMsg       = cancelBtnMsg;
        this.okBtnMsg           = okBtnMsg;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setDimAmount(0.7f);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationZoom;

        setContentView(R.layout.dialog_game_confirmation);
        initializeWidgets();
    }

    private void initializeWidgets() {
        Button btnSubmit    = findViewById(R.id.btn_submit);
        Button btnCancel    = findViewById(R.id.btn_cancel);
        TextView tvInfo     = findViewById(R.id.tvInfo);
        ImageView ivIcon    = findViewById(R.id.ivIcon);
        TextView tvSoftMsg  = findViewById(R.id.tvSoftMsg);

        /*if (isResetPassword)
            ivIcon.setImageResource(R.drawable.icon_confirm_reset_password);
        else
            ivIcon.setImageResource(R.drawable.icon_confirm_deposit);*/

        ivIcon.setImageResource(image);

        tvSoftMsg.setText(softMsg);
        tvInfo.setText(Html.fromHtml(info));
        btnSubmit.setText(okBtnMsg);
        btnCancel.setText(cancelBtnMsg);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Utils.vibrate(context);
                listener.onConfirmationAccepted();
                dismiss();
                break;
            case R.id.btn_cancel:
                Utils.vibrate(context);
                dismiss();
                break;
        }
    }
}
