package com.skilrock.retailapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.fragment.app.FragmentManager;
import android.widget.LinearLayout;

import com.skilrock.retailapp.dialog.ErrorDialog;
import com.skilrock.retailapp.dialog.ErrorDialogWinningClaim;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;

public class CustomErrorDialog {

    private static CustomErrorDialog progresDialog;
    public Context context;
    private ErrorDialog dialog;
    private ErrorDialogWinningClaim errorDialogWinningClaim;

    private CustomErrorDialog() {
    }

    public static synchronized CustomErrorDialog getProgressDialog() {
        if (progresDialog == null) {
            progresDialog = new CustomErrorDialog();
        }

        return progresDialog;
    }

    public void showCustomErrorDialog(Context context, String header, String message) {
        dismiss();

        dialog = new ErrorDialog(context, header, message);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomErrorDialog(Context context, String header, String message, ErrorDialogListener errorDialogListener) {
        dismiss();

        dialog = new ErrorDialog(context, header, message, errorDialogListener);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomErrorDialogWinning(Context context, String header, String message, ErrorDialogListener errorDialogListener) {
        dismissWinning();

        errorDialogWinningClaim = new ErrorDialogWinningClaim(context, header, message, errorDialogListener);
        errorDialogWinningClaim.show();
        if (errorDialogWinningClaim.getWindow() != null) {
            errorDialogWinningClaim.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            errorDialogWinningClaim.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }
    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.cancel();
    }

    public void dismissWinning() {
        if (errorDialogWinningClaim != null && errorDialogWinningClaim.isShowing())
            errorDialogWinningClaim.cancel();
    }


    public void showCustomErrorDialogAndLogout(Context context, String header, String message, boolean isPerformLogout) {
        dismiss();

        dialog = new ErrorDialog(context, header, message, isPerformLogout);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomErrorDialogAndLogoutActivity(Context context, String header, String message, boolean isPerformLogout, Activity activity) {
        dismiss();

        dialog = new ErrorDialog(context, header, message, isPerformLogout, activity);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomErrorDialogPop(Context context, String header, String message, int popCount, FragmentManager fragmentManager) {
        dismiss();

        dialog = new ErrorDialog(context, header, message, popCount, fragmentManager);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }
}