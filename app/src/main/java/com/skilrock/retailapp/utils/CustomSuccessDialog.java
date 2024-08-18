package com.skilrock.retailapp.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;

import com.skilrock.retailapp.dialog.CancelTicketConfirmationDialog;
import com.skilrock.retailapp.dialog.ClaimDialog;
import com.skilrock.retailapp.dialog.ConfirmationDialog;
import com.skilrock.retailapp.dialog.GameSwitchConfirmationDialog;
import com.skilrock.retailapp.dialog.InfoDialog;
import com.skilrock.retailapp.dialog.OverlayPermissionInstructionDialog;
import com.skilrock.retailapp.dialog.ReprintlTicketConfirmationDialog;
import com.skilrock.retailapp.dialog.SuccessDialog;
import com.skilrock.retailapp.dialog.WarningDialog;
import com.skilrock.retailapp.dialog.WinningClaimDialog;
import com.skilrock.retailapp.dialog.WinningClaimDialogSbs;
import com.skilrock.retailapp.dialog.WinningClaimDialogSports;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.interfaces.DialogCloseListener;
import com.skilrock.retailapp.interfaces.GameChangeListener;
import com.skilrock.retailapp.interfaces.OverlayPermissionListener;
import com.skilrock.retailapp.interfaces.WinningClaimListener;
import com.skilrock.retailapp.landscape_draw_games.dialog.GameConfirmationDialog;
import com.skilrock.retailapp.models.SbsWinVerifyResponse;
import com.skilrock.retailapp.models.drawgames.WinningClaimVerifyResponseBean;
import com.skilrock.retailapp.models.scratch.WinningDisplayDialogBean;
import com.skilrock.retailapp.sle_game_portrait.VerifyPayTicket;

public class CustomSuccessDialog {

    private static CustomSuccessDialog progresDialog;
    public Context context;
    private SuccessDialog dialog;
    InfoDialog infoDialog;
    WarningDialog warningDialog;
    private ClaimDialog claimDialog;
    private WinningClaimDialog winningClaimDialog;
    private ConfirmationDialog confirmationDialog;
    private WinningClaimDialogSports  claimDialogSports;
    private WinningClaimDialogSbs claimDialogSbs;
    private GameSwitchConfirmationDialog gameSwitchConfirmationDialog;
    private GameConfirmationDialog gameConfirmationDialog;
    private OverlayPermissionInstructionDialog overlayPermissionInstructionDialog;
    private CancelTicketConfirmationDialog cancelTicketConfirmationDialog;
    private ReprintlTicketConfirmationDialog reprintlTicketConfirmationDialog;

    private CustomSuccessDialog() {
    }

    public static synchronized CustomSuccessDialog getProgressDialog() {
        if (progresDialog == null) {
            progresDialog = new CustomSuccessDialog();
        }

        return progresDialog;
    }

    public void showCustomSuccessDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount) {
        dismiss();

        dialog = new SuccessDialog(context, fragmentManager, header, message, popCount);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomGameSwitchDialog(Context context, int image, String info, String softMsg, String cancelBtnMsg, String okBtnMsg, GameChangeListener listener) {
        dismiss();
        gameSwitchConfirmationDialog = new GameSwitchConfirmationDialog(context, image, info, softMsg, cancelBtnMsg, okBtnMsg, listener);
        gameSwitchConfirmationDialog.show();
        if (gameSwitchConfirmationDialog.getWindow() != null) {
            gameSwitchConfirmationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            gameSwitchConfirmationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomOverlayInstructionDialog(Context context, OverlayPermissionListener listener) {
        dismiss();
        overlayPermissionInstructionDialog = new OverlayPermissionInstructionDialog(context, listener);
        overlayPermissionInstructionDialog.show();
        if (overlayPermissionInstructionDialog.getWindow() != null) {
            overlayPermissionInstructionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            overlayPermissionInstructionDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomConfirmationDialog(Context context, int image, String info, String softMsg, String cancelBtnMsg, String okBtnMsg, ConfirmationListener listener) {
        dismiss();

        gameConfirmationDialog = new GameConfirmationDialog(context, image, info, softMsg, cancelBtnMsg, okBtnMsg, listener);
        gameConfirmationDialog.show();
        if (gameConfirmationDialog.getWindow() != null) {
            gameConfirmationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            gameConfirmationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomSuccessDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount, DialogCloseListener dialogCloseListener) {
        dismiss();
        dialog = new SuccessDialog(context, fragmentManager, header, message, popCount, dialogCloseListener);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomSuccessDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount, String buttonText) {
        dismiss();

        dialog = new SuccessDialog(context, fragmentManager, header, message, popCount, buttonText);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    void showCustomSuccessDialogAndLogout(Context context, FragmentManager fragmentManager, String header, String message, boolean isPerformLogout) {
        dismiss();

        dialog = new SuccessDialog(context, fragmentManager, header, message, isPerformLogout);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    void showCustomSuccessDialogAndLogout(Context context, String header, String message) {
        dismiss();

        dialog = new SuccessDialog(context, header, message);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    void showConfirmationDialog(Context context, boolean isResetPassword, String info, ConfirmationListener listener) {
        dismissConfirmation();

        confirmationDialog = new ConfirmationDialog(context, isResetPassword, info, listener);
        confirmationDialog.show();
        if (confirmationDialog.getWindow() != null) {
            confirmationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }


    void showConfirmationDialogSbs(Context context,  ConfirmationListener listener) {
        dismissConfirmation();

        reprintlTicketConfirmationDialog = new ReprintlTicketConfirmationDialog(context,  listener);
        reprintlTicketConfirmationDialog.show();
        if (reprintlTicketConfirmationDialog.getWindow() != null) {
            reprintlTicketConfirmationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            reprintlTicketConfirmationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCancelTicketConfirmationDialog(Context context, ConfirmationListener listener) {
        dismissConfirmation();

        cancelTicketConfirmationDialog = new CancelTicketConfirmationDialog(context, listener);
        cancelTicketConfirmationDialog.show();
        if (cancelTicketConfirmationDialog.getWindow() != null) {
            cancelTicketConfirmationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cancelTicketConfirmationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

   public void showClaimDialog(Context context, FragmentManager fragmentManager, WinningDisplayDialogBean winningModel, WinningClaimListener listener,String text, boolean showBalanceErrorNote, boolean isVerify) {
        dismissClaim();

        claimDialog = new ClaimDialog(context, fragmentManager, listener, text, isVerify, winningModel, showBalanceErrorNote);

        claimDialog.show();
        if (claimDialog.getWindow() != null) {
            claimDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            claimDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
   }


    public void showDialogClaimSports(Context context, VerifyPayTicket bean, WinningClaimListener listener, String text, boolean showBalanceErrorNote, boolean isVerify) {
        dismissClaim();

        claimDialogSports = new WinningClaimDialogSports(context, listener, text, isVerify, bean, showBalanceErrorNote);

        claimDialogSports.show();
        if (claimDialogSports.getWindow() != null) {
            claimDialogSports.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            claimDialogSports.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showDialogSbs(Context context, SbsWinVerifyResponse bean, WinningClaimListener listener, String text, boolean showBalanceErrorNote, boolean isVerify) {
        dismissClaim();

        claimDialogSbs = new WinningClaimDialogSbs(context, listener, text, isVerify, bean, showBalanceErrorNote);

        claimDialogSbs.show();
        if (claimDialogSbs.getWindow() != null) {
            claimDialogSbs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            claimDialogSbs.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showDialogClaim(Context context, WinningClaimVerifyResponseBean bean, WinningClaimListener listener, String text, boolean showBalanceErrorNote, boolean isVerify) {
        dismissClaim();

        winningClaimDialog = new WinningClaimDialog(context, listener, text, isVerify, bean, showBalanceErrorNote);

        winningClaimDialog.show();
        if (winningClaimDialog.getWindow() != null) {
            winningClaimDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            winningClaimDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomInfoDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount, String buttonText) {
        dismissInfo();

        infoDialog = new InfoDialog(context, fragmentManager, header, message, popCount, buttonText);
        infoDialog.show();
        if (infoDialog.getWindow() != null) {
            infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            infoDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showCustomInfoClaimDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount, String buttonText) {
        dismissInfo();

        infoDialog = new InfoDialog(context, fragmentManager, header, message, popCount, buttonText, true);
        infoDialog.show();
        if (infoDialog.getWindow() != null) {
            infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            infoDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }



    public void showCustomWarningDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount, String buttonText) {
        dismissWarning();

        warningDialog = new WarningDialog(context, fragmentManager, header, message, popCount, buttonText);
        warningDialog.show();
        if (warningDialog.getWindow() != null) {
            warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            warningDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.cancel();
    }

    void dismissConfirmation() {
        if (confirmationDialog != null && confirmationDialog.isShowing())
            confirmationDialog.cancel();
    }

    void dismissClaim() {
        if (claimDialog != null && claimDialog.isShowing())
            claimDialog.cancel();
    }

    void dismissInfo() {
        if (infoDialog != null && infoDialog.isShowing())
            infoDialog.cancel();
    }

    void dismissWarning() {
        if (warningDialog != null && warningDialog.isShowing())
            warningDialog.cancel();
    }
}