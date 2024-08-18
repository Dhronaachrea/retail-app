package com.skilrock.retailapp.utils;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2PRO;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2_s;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2s;
import static com.skilrock.retailapp.utils.PrintUtil.months;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.CountryCodeDialog;
import com.skilrock.retailapp.dialog.ErrorDialogForActivity;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.interfaces.CountryCodeListener;
import com.skilrock.retailapp.interfaces.DialogCloseListener;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.interfaces.GameChangeListener;
import com.skilrock.retailapp.interfaces.OverlayPermissionListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.models.rms.CountryCodeBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.portrait_draw_games.ui.ResultDrawDialogActivity;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.activities.rms.LoginActivity;
import com.skilrock.retailapp.ui.activities.rms.LoginActivityFieldX;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Utils {

    static String MD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(data.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32
            // chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    static void showErrorMessage(Context context, String message) {
        showMessageDialog(context, context.getString(R.string.oops), message);
    }

    static void showErrorMessageWithBackAction(final Activity activity, String message) {
        showMessageDialog(activity, activity.getString(R.string.oops), message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.onBackPressed();
            }
        });
    }

    static void showMessageDialog(Context context, String message) {
        showMessageDialog(context, BuildConfig.app_name, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    static void showMessageDialog(Context context, String title, String message) {
        showMessageDialog(context, title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    static void showMessageDialogWithCallBack(Context context, String title, String message, MethodCallback callback) {
        showMessageDialog(context, title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                callback.onClick();
            }
        });
    }

    static void showSuccessDialog(Context context, String title, String message, final FragmentManager fragmentManager) {
        showMessageDialog(context, title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                fragmentManager.popBackStack();
            }
        });
    }

    static void showSuccessCustomDialog(Context context, String title, String message, final FragmentManager fragmentManager) {
        showMessageDialog(context, title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                fragmentManager.popBackStack();
            }
        });
    }

    static void showSuccessDialog(Context context, String title, String message, final FragmentManager fragmentManager, final int popCount) {
        showMessageDialog(context, title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                for (int i = 0; i < popCount; i++)
                    fragmentManager.popBackStack();
            }
        });
    }

    static void showMessageDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        if (message == null || message.isEmpty()) {
            message = context.getString(R.string.some_internal_error);
        }

        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(context.getString(R.string.ok), listener).show();
    }

    static void showMessageDialog(Context context, String title, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        if (message == null || message.isEmpty()) {
            message = context.getString(R.string.some_internal_error);
        }

        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(context.getString(R.string.ok), okListener).setNegativeButton(context.getString(R.string.cancel), cancelListener == null ? (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }) : cancelListener).setCancelable(false).show();
    }

    /*static void showMessageDialog(Context context, String title, String message, boolean isCancelable, DialogInterface.OnClickListener okListener) {
        if (message == null || message.isEmpty()) {
            message = context.getString(R.string.some_internal_error);
        }

        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setCancelable(isCancelable).setPositiveButton(context.getString(R.string.ok), okListener).show();
    }

    static void showMessageDialog(Context context, String title, String message, boolean isCancelable, DialogInterface.OnClickListener okListener, String buttonName) {
        if (message == null || message.isEmpty()) {
            message = context.getString(R.string.some_internal_error);
        }

        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setCancelable(isCancelable).setPositiveButton(buttonName, okListener).show();
    }*/

    static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @NonNull
    static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        Window window = dialog.getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(R.layout.custom_progress_dialog);
        return dialog;
    }

    /*public static String generateUrl(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag1, String tag2) {
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            String url = menuBean.getBasePath() + jsonObject.getJSONObject(tag1).getString(tag2);
            Log.w("log", "Generated URL: " + url);
            return url;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    public static String getHeaderData(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag1, String tag2, String tag3) {
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            String headerData = jsonObject.getJSONObject(tag1).getJSONObject(tag2).getString(tag3);
            Log.i("log", "Header->" + tag3 + ": " + headerData);
            return headerData;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }*/

    static void showCustomErrorDialog(Context context, String header, String message) {
        CustomErrorDialog.getProgressDialog().showCustomErrorDialog(context, header, message);
    }

    static void showCustomErrorDialog(Context context, String header, String message, ErrorDialogListener errorDialogListener) {
        CustomErrorDialog.getProgressDialog().showCustomErrorDialog(context, header, message, errorDialogListener);
    }

    static void showCustomErrorDialogWinning(Context context, String header, String message, ErrorDialogListener errorDialogListener) {
        CustomErrorDialog.getProgressDialog().showCustomErrorDialogWinning(context, header, message, errorDialogListener);
    }

    static void dismissCustomErrorDialog() {
        CustomErrorDialog.getProgressDialog().dismiss();
    }

    static void showCustomErrorDialogAndLogout(Context context, String header, String message, boolean isPerformLogout) {
        CustomErrorDialog.getProgressDialog().showCustomErrorDialogAndLogout(context, header, message, isPerformLogout);
    }

    static void showCustomErrorDialogAndLogoutActivity(Context context, String header, String message, boolean isPerformLogout, Activity activity) {
        CustomErrorDialog.getProgressDialog().showCustomErrorDialogAndLogoutActivity(context, header, message, isPerformLogout, activity);
    }

    static void showCustomErrorDialogPop(Context context, String header, String message, int popCount, FragmentManager fragmentManager) {
        CustomErrorDialog.getProgressDialog().showCustomErrorDialogPop(context, header, message, popCount, fragmentManager);
    }

    static void showCustomSuccessDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount, DialogCloseListener listener) {
        CustomSuccessDialog.getProgressDialog().showCustomSuccessDialog(context, fragmentManager, header, message, popCount, listener);
    }

    static void showCustomSuccessDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount) {
        CustomSuccessDialog.getProgressDialog().showCustomSuccessDialog(context, fragmentManager, header, message, popCount);
    }

    static void dismissCustomSuccessDialog() {
        CustomSuccessDialog.getProgressDialog().dismiss();
    }

    static void showCustomSuccessDialog(Context context, FragmentManager fragmentManager, String header, String message, int popCount, String buttonText) {
        CustomSuccessDialog.getProgressDialog().showCustomSuccessDialog(context, fragmentManager, header, message, popCount, buttonText);
    }

    static void showCustomSuccessDialogAndLogout(Context context, FragmentManager fragmentManager, String header, String message, boolean isPerformLogout) {
        CustomSuccessDialog.getProgressDialog().showCustomSuccessDialogAndLogout(context, fragmentManager, header, message, isPerformLogout);
    }

    static void showCustomSuccessDialogAndLogout(Context context, String header, String message) {
        CustomSuccessDialog.getProgressDialog().showCustomSuccessDialogAndLogout(context, header, message);
    }

    static void showConfirmationDialog(Context context, boolean isResetPassword, String info, ConfirmationListener listener) {
        CustomSuccessDialog.getProgressDialog().showConfirmationDialog(context, isResetPassword, info, listener);
    }

    static void showCancelTicketConfirmationDialog(Context context, ConfirmationListener listener) {
        CustomSuccessDialog.getProgressDialog().showCancelTicketConfirmationDialog(context, listener);
    }

    static void showReprintTicketConfirmationDialog(Context context, ConfirmationListener listener) {
        CustomSuccessDialog.getProgressDialog().showConfirmationDialogSbs(context, listener);
    }

    static UrlBean getUrlDetailsPackRecieveFieldx(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag) {
        UrlBean urlBean = new UrlBean();
//        try {
        // JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
        String url = menuBean.getBasePath() + "/inventory/dlDetails";
        urlBean.setUrl(url);
        // JSONObject headerObject = jsonObject.getJSONObject(tag).getJSONObject("headers");
        urlBean.setClientId("RMS");
        urlBean.setClientSecret("13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2");
        // urlBean.setContentType(headerObject.getString("Content-Type"));
        Log.w("log", "Generated URL: " + urlBean);
        return urlBean;
//        } catch (JSONException e) {
//            Toast.makeText(context, context.getString(R.string.url_problem), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//            return null;
//        }
    }

    static UrlBean getUrlDetailsPackReturnFieldx(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag) {
        UrlBean urlBean = new UrlBean();
        // JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
        String url = menuBean.getBasePath() + "/inventory/getReturnNote";
        urlBean.setUrl(url);
        // JSONObject headerObject = jsonObject.getJSONObject(tag).getJSONObject("headers");
        urlBean.setClientId("RMS");
        urlBean.setClientSecret("13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2");
        // urlBean.setContentType(headerObject.getString("Content-Type"));
        Log.w("log", "Generated URL: " + urlBean);
        return urlBean;
    }

    static UrlBean getUrlDetailsPackReturn(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag) {
        UrlBean urlBean = new UrlBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            Log.d("TAg", "JSON: " + jsonObject.toString());
            urlBean.setUrl(jsonObject.getString("url"));
            JSONObject headerObject = jsonObject.getJSONObject(tag).getJSONObject("headers");
            urlBean.setClientId("RMS");
            urlBean.setClientSecret("13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2");
            urlBean.setContentType(headerObject.getString("Content-Type"));
            Log.w("log", "Generated URL: " + urlBean);
            return urlBean;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.url_problem), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    static UrlBean getUrlDetails(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag) {
        UrlBean urlBean = new UrlBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            String url = menuBean.getBasePath() + jsonObject.getJSONObject(tag).getString("url");
            urlBean.setUrl(url);
            JSONObject headerObject = jsonObject.getJSONObject(tag).getJSONObject("headers");
            urlBean.setClientId(headerObject.getString("clientId"));
            urlBean.setClientSecret(headerObject.getString("clientSecret"));
            urlBean.setContentType(headerObject.getString("Content-Type"));
            Log.w("log", "Generated URL: " + urlBean);
            return urlBean;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.url_problem), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    static String getRmsUrlDetails(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag) {
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            String url = menuBean.getBasePath() + jsonObject.getJSONObject(tag).getString("url");
            Log.w("log", "Generated URL: " + url);
            return url;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.url_problem), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    static String getFieldXCollectionReport(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag) {
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            String url = menuBean.getBasePath() + jsonObject.getJSONObject(tag).getString("url");
            Log.w("log", "Generated URL: " + url);
            return url;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.url_problem), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    static UrlOlaBean getOlaUrlDetails(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag) {
        UrlOlaBean urlOlaBean = new UrlOlaBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            String url = menuBean.getBasePath() + jsonObject.getJSONObject(tag).getString("url");
            urlOlaBean.setUrl(url);
            JSONObject headerObject = jsonObject.getJSONObject(tag).getJSONObject("headers");
            urlOlaBean.setUserName(headerObject.getString("username"));
            urlOlaBean.setPassword(headerObject.getString("password"));
            urlOlaBean.setContentType(headerObject.getString("Content-Type"));
            if (BuildConfig.FLAVOR.equalsIgnoreCase("qA_OLA_MYANMAR") || BuildConfig.FLAVOR.equalsIgnoreCase("uAT_OLA_MYANMAR") || BuildConfig.FLAVOR.equalsIgnoreCase("pROD_OLA_MYANMAR") || BuildConfig.FLAVOR.equalsIgnoreCase("uAT_CAMRN") || BuildConfig.FLAVOR.equalsIgnoreCase("pROD_CAMRN") || BuildConfig.FLAVOR.equalsIgnoreCase("qA_CAMRN") || BuildConfig.FLAVOR.equalsIgnoreCase("qA_PAYPR_BUSINESS") || BuildConfig.FLAVOR.equalsIgnoreCase("uAT_PAYPR_BUSINESS") || BuildConfig.FLAVOR.equalsIgnoreCase("pROD_PAYPR_BUSINESS")) {
                String domain = String.valueOf(PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId());
                urlOlaBean.setRetailDomainCode(domain);
            } else {
                urlOlaBean.setRetailDomainCode(headerObject.getString("retailDomainCode"));
            }
            urlOlaBean.setRetailMerchantCode(headerObject.getString("retailMerchantCode"));
            urlOlaBean.setAccept(headerObject.getString("Accept"));
            //Log.w("log", "Generated URL: " + urlOlaBean);
            return urlOlaBean;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.url_problem), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    static UrlBean getFieldxChallanUrlDetails(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, String tag) {
        UrlBean urlBean = new UrlBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            urlBean.setUrl(menuBean.getBasePath() + jsonObject.getJSONObject(tag).getString("url"));
            JSONObject jsonHeader = jsonObject.getJSONObject(tag).getJSONObject("headers");
            urlBean.setClientId(jsonHeader.getString("clientId"));
            urlBean.setClientSecret(jsonHeader.getString("clientSecret"));
            urlBean.setContentType(jsonHeader.getString("Content-Type"));
            return urlBean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static UrlBean getFieldxRetailerUrlDetails(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, String tag) {
        UrlBean urlBean = new UrlBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            urlBean.setUrl(menuBean.getBasePath() + jsonObject.getJSONObject(tag).getString("url"));
            return urlBean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static UrlBean getDoPayAmountDetailUrl(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, String tag) {
        UrlBean urlBean = new UrlBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            urlBean.setUrl(jsonObject.getJSONObject(tag).getString("url"));
            Log.e("Payment API", jsonObject.getJSONObject(tag).getString("url"));
            return urlBean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static UrlBean getPayAmountDetailUrl(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, String tag) {
        UrlBean urlBean = new UrlBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            urlBean.setUrl(jsonObject.getJSONObject(tag).getString("url"));
            Log.e("PayAmountDetailAPI", jsonObject.getJSONObject(tag).getString("url"));
            return urlBean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static UrlDrawGameBean getDrawGameUrlDetails(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag) {
        UrlDrawGameBean urlDrawGameBeanBean = new UrlDrawGameBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            String url = menuBean.getBasePath() + jsonObject.getJSONObject(tag).getString("url");
            urlDrawGameBeanBean.setUrl(url);
            JSONObject headerObject = jsonObject.getJSONObject(tag).getJSONObject("headers");
            urlDrawGameBeanBean.setUserName(headerObject.getString("username"));
            urlDrawGameBeanBean.setPassword(headerObject.getString("password"));
            urlDrawGameBeanBean.setContentType(headerObject.getString("Content-Type"));
            //Log.w("log", "Generated URL: " + urlOlaBean);
            return urlDrawGameBeanBean;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.url_problem), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }


    static UrlDrawGameBean getSleGameUrlDetails(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context, String tag) {
        UrlDrawGameBean urlDrawGameBeanBean = new UrlDrawGameBean();
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            String url = jsonObject.getJSONObject(tag).getString("url");
            urlDrawGameBeanBean.setUrl(url);
            JSONObject headerObject = jsonObject.getJSONObject(tag).getJSONObject("headers");
            urlDrawGameBeanBean.setUserName(headerObject.getString("userName"));
            urlDrawGameBeanBean.setPassword(headerObject.getString("password"));
            urlDrawGameBeanBean.setContentType(headerObject.getString("Content-Type"));
            //Log.w("log", "Generated URL: " + urlOlaBean);
            return urlDrawGameBeanBean;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.url_problem), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    static HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList getMenuBean(ArrayList<HomeDataBean.ResponseData.ModuleBeanList> drawerList, String menuCode) {
        if (drawerList != null) {
            for (HomeDataBean.ResponseData.ModuleBeanList moduleBean : drawerList) {
                ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> menuBeanList = moduleBean.getMenuBeanList();
                for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean : menuBeanList)
                    if (menuCode.equalsIgnoreCase(menuBean.getMenuCode().trim())) return menuBean;
            }
        }
        return null;
    }

    static TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(40);
    }

    static void vibrateLong(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(70);
    }

    static void performLogoutCleanUp(Context context) {
        PlayerData.getInstance().destroyInstance();
        SharedPrefUtil.clearAppSharedPref(context);
        ((MainActivity) context).finish();
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    static void performLogoutCleanUp(Activity activity) {
        PlayerData.getInstance().destroyInstance();
        SharedPrefUtil.clearAppSharedPref(activity);
        activity.finish();
        Intent intent;
        if (BuildConfig.app_name.equalsIgnoreCase(activity.getString(R.string.app_name_FieldX)))
            intent = new Intent(activity, LoginActivityFieldX.class);
        else intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    static boolean checkForSessionExpiry(final Context context, int code) {

        if (code == 1004 || code == 1015 || code == 102 || code == 107 || code == 702 || code == 105 || code == 108 || code == 109 || code == 1021 || code == 1002 || code == 10012) {
            String message;

            try {
                String name = "error_" + code;
                int id = context.getResources().getIdentifier(name, "string", context.getPackageName());
                message = context.getString(id);

            } catch (Exception e) {
                message = context.getString(R.string.login_gain);
                Log.e("log", "Error Code String Parsing Error: " + e.getMessage());
            }

            showCustomErrorDialogAndLogout(context, context.getString(R.string.service_error), message, true);
            return true;
        }
        return false;
    }

    static boolean checkForSessionExpiryFieldX(final Context context, int code) {
        if (code == 1004 || code == 1015 || code == 102 || code == 107 || code == 702 || code == 105 || code == 108 || code == 109 || code == 1021 || code == 1002 || code == 10012) {
            String message;
            try {
                String name = "error_" + code;
                int id = context.getResources().getIdentifier(name, "string", context.getPackageName());
                message = context.getString(id);
            } catch (Exception e) {
                message = context.getString(R.string.login_gain);
                Log.e("log", "Error Code String Parsing Error: " + e.getMessage());
            }

            showCustomErrorDialogAndLogout(context, context.getString(R.string.service_error), message, true);
            return true;
        }
        return false;
    }

    static boolean checkForSessionExpiryActivity(final Context context, int code, Activity activity) {
        if (code == 1004 || code == 1015 || code == 102 || code == 107 || code == 702 || code == 105 || code == 108 || code == 109 || code == 118 || code == 1021 || code == 1002 || code == 401) {
            String message;
            try {
                String name = "error_" + code;
                int id = context.getResources().getIdentifier(name, "string", context.getPackageName());
                message = context.getString(id);
            } catch (Exception e) {
                message = context.getString(R.string.login_gain);
                Log.e("log", "Error Code String Parsing Error: " + e.getMessage());
            }

            showCustomErrorDialogAndLogoutActivity(context, context.getString(R.string.service_error), message, true, activity);
            return true;
        }
        return false;
    }

    static String getResponseMessage(final Context context, String tag, int responseCode) {
        String message;
        try {
            String name = tag + "_" + responseCode;
            int id = context.getResources().getIdentifier(name, "string", context.getPackageName());
            message = context.getString(id);
        } catch (Exception e) {
            // This message will be used when responseCode is not found in String.xml
            message = context.getString(R.string.some_technical_issue);
            Log.e("log", "Response Code (" + responseCode + ") String Parsing Error: " + e.getMessage());
        }
        return message;
    }

    static void setAppBarVisibility(FragmentActivity activity, boolean visibility) {
        try {
            if (activity != null) {
                if (visibility) ((MainActivity) activity).getSupportActionBar().show();
                else ((MainActivity) activity).getSupportActionBar().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public static String getTerminalId(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String m_deviceId = TelephonyMgr.getDeviceId();
    }*/

    static boolean isNetworkConnected(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return con_manager.getActiveNetworkInfo() != null && con_manager.getActiveNetworkInfo().isAvailable() && con_manager.getActiveNetworkInfo().isConnected();
    }

    static String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return df.format(date);
    }

    static String updateDateFormat(String selectedDate , String language) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date=null;
        try {
             date = format.parse(selectedDate);
        } catch (Exception ignored) {

        }
        Locale locale =null;
        if (language.equals("uk"))
        {
            locale = new Locale("uk", "uk-UA");
        }
        else{
            locale = Locale.ENGLISH;
        }

        Locale spanish = new Locale("en", "EN");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy",  locale);

        return df.format(date);

    }


    static String getCurrentDateMinus1() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date date = cal.getTime();
        //Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return df.format(date);
    }

    static String get30BeforeDate() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -30);
        Date date = cal.getTime();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return df.format(date);
    }

    static String getLastDateOfMonth() {
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return sdf.format(lastDayOfMonth);
    }

    static String getCurrentDateOla() {
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return df.format(date);
    }

    static String getCurrentDateResult() {
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date_to = originalFormat.format(date);
        Date date_result;

        try {

            date_result = originalFormat.parse(date_to);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date_result);

    }


    static String getPreviousDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -days);
        Date date = cal.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return df.format(date);
    }

    static String addOneDay(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd/mm/yyyy");
        Date date = null;
        try {
            date = originalFormat.parse(dateTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, +1);
            date = cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        return df.format(date);
    }

    static String getDateMonthName(String strDate) {
        SimpleDateFormat month_date = new SimpleDateFormat("d MMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat(ConfigData.getInstance().getConfigData().getDEFAULTDATEFORMAT(), Locale.ENGLISH);

        try {
            Date date = sdf.parse(strDate);
            return month_date.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return strDate;
        }
    }

    static String getDateMonthYearName(String strDate) {
        SimpleDateFormat month_date = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            Date date = sdf.parse(strDate);
            return month_date.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return strDate;
        }
    }

    static String getMonthBeforeDate(String date) {
        String[] arrDate = date.split("-");
        //String day = Integer.parseInt()
        return "";
    }

    static void countryCodeFocusOperation(FragmentActivity fragmentActivity, String appType) {
        TextView tvCountryCode = fragmentActivity.findViewById(R.id.tvCountryCode);
        EditText etMobile = fragmentActivity.findViewById(R.id.et_mobile_number);
        LinearLayout llMobile = fragmentActivity.findViewById(R.id.llMobile);

        if (appType.equalsIgnoreCase(AppConstants.ACDC)) {
            tvCountryCode.setText("+1");
        } else if (appType.equalsIgnoreCase(AppConstants.SISAL)) {
            tvCountryCode.setText("+212"); //39
        } else if (appType.equalsIgnoreCase(AppConstants.CAMEROON)) {
            tvCountryCode.setText("+237");
        } else if (appType.equalsIgnoreCase(AppConstants.OLA_MYANMAR)) {
            tvCountryCode.setText("+95");
        } else if (appType.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
            tvCountryCode.setText("+380");
        } else {
            tvCountryCode.setText("+91");
        }
        etMobile.setOnFocusChangeListener((v, hasFocus) -> {
            if (SharedPrefUtil.getLanguage(fragmentActivity).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC)) {
                if (hasFocus) {
                    llMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_right_yellow_arabic));
                    tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_left_yellow_arabic));
                } else {
                    llMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_right_arabic));
                    tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_left_arabic));
                }
            } else {
                if (hasFocus) {
                    llMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_right_yellow));
                    tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_left_yellow));
                } else {
                    llMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_right));
                    tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_left));
                }
            }
        });
    }

    static void countryCodeFocusOperationForAltMobile(FragmentActivity fragmentActivity, String appname) {
        TextView tvCountryCode = fragmentActivity.findViewById(R.id.tvCountryCode_alt_mobile);
        EditText etMobile = fragmentActivity.findViewById(R.id.et_alt_mobile);
        LinearLayout llMobile = fragmentActivity.findViewById(R.id.til_alt_mobile);

        if (appname.equalsIgnoreCase(AppConstants.ACDC)) {
            tvCountryCode.setText("+1");
        } else if (appname.equalsIgnoreCase(AppConstants.SISAL)) {
            tvCountryCode.setText("+212"); //39
        } else if (appname.equalsIgnoreCase(AppConstants.CAMEROON)) {
            tvCountryCode.setText("+237");
        } else if (appname.equalsIgnoreCase(AppConstants.OLA_MYANMAR)) {
            tvCountryCode.setText("+95");
        } else {
            tvCountryCode.setText("+91");
        }

        etMobile.setOnFocusChangeListener((v, hasFocus) -> {
            if (SharedPrefUtil.getLanguage(fragmentActivity).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC)) {
                if (hasFocus) {
                    llMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_right_yellow_arabic));
                    tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_left_yellow_arabic));
                } else {
                    llMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_right_arabic));
                    tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_left_arabic));
                }
            } else {
                if (hasFocus) {
                    llMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_right_yellow));
                    tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_left_yellow));
                } else {
                    llMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_right));
                    tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(fragmentActivity), R.drawable.border_rounded_left));
                }
            }
        });
    }

    static void openCountryCodeDialog(FragmentActivity fragmentActivity) {
        Utils.vibrate(Objects.requireNonNull(fragmentActivity));
        TextView tvCountryCode = fragmentActivity.findViewById(R.id.tvCountryCode);
        CountryCodeListener listener = tvCountryCode::setText;
        CountryCodeDialog dialog = new CountryCodeDialog(fragmentActivity, listener, null);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    static void openCountryCodeDialogForForgotPassword(FragmentActivity fragmentActivity, String appType) {
        ArrayList<String> arrayList = new ArrayList<>();
        Utils.vibrate(Objects.requireNonNull(fragmentActivity));
        TextView tvCountryCode = fragmentActivity.findViewById(R.id.tvCountryCode);
        CountryCodeListener listener = tvCountryCode::setText;

        if (appType.equalsIgnoreCase(AppConstants.ACDC)) {
            arrayList.add("+1");
            arrayList.add("+91");
        } else if (appType.equalsIgnoreCase(AppConstants.SISAL)) {
            arrayList.add("+212");
            arrayList.add("+39");
            arrayList.add("+91");
        } else if (appType.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
            arrayList.add("+380");
        } else {
            arrayList = null;
        }

        CountryCodeDialog dialog = new CountryCodeDialog(fragmentActivity, listener, arrayList);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    static void openCountryCodeDialogFromConfig(FragmentActivity fragmentActivity, String codes) {
        ArrayList<String> arrayList = new ArrayList<>();

        if (codes.contains(",")) {
            List<String> arr = Arrays.asList(codes.split(","));
            for (int index = 0; index < arr.size(); index++) {
                arrayList.add(arr.get(index));
            }

        } else {
            arrayList.add(codes);
        }

        Utils.vibrate(Objects.requireNonNull(fragmentActivity));
        TextView tvCountryCode = fragmentActivity.findViewById(R.id.tvCountryCode);
        CountryCodeListener listener = tvCountryCode::setText;
        CountryCodeDialog dialog = new CountryCodeDialog(fragmentActivity, listener, arrayList);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    /*static void openStartDateDialog(Context context, TextView tvStartDate) {
        int mYear, mMonth, mDay;
        String[] arrDate = tvStartDate.getText().toString().trim().split("-");
        mYear   = Integer.parseInt(arrDate[2]);
        mMonth  = Integer.parseInt(arrDate[1]) - 1;
        mDay    = Integer.parseInt(arrDate[0]);

        DatePickerDialog dialogStartDate = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            String month, day;
            month   = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
            day     = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String date = day + "-" + month + "-" + year;
            tvStartDate.setText(date);
        }, mYear, mMonth, mDay);
        dialogStartDate.getDatePicker().setMaxDate(new Date().getTime());
        dialogStartDate.show();
    }*/

    static void openStartDateDialog(Context context, TextView tvStartDate, TextView tvEndDate) {
        Calendar calender = Calendar.getInstance();
        int mYear, mMonth, mDay, mYearEnd, mMonthEnd, mDayEnd;
        String[] arrDate = tvStartDate.getText().toString().trim().split("-");
        mYear = Integer.parseInt(arrDate[2]);
        mMonth = Integer.parseInt(arrDate[1]) - 1;
        mDay = Integer.parseInt(arrDate[0]);

        String[] arrDateEnd = tvEndDate.getText().toString().trim().split("-");
        mYearEnd = Integer.parseInt(arrDateEnd[2]);
        mMonthEnd = Integer.parseInt(arrDateEnd[1]) - 1;
        mDayEnd = Integer.parseInt(arrDateEnd[0]);

        DatePickerDialog dialogStartDate = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            String month, day;
            month = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
            day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String date = day + "-" + month + "-" + year;
            tvStartDate.setText(date);
        }, mYear, mMonth, mDay);
        calender.set(mYearEnd, mMonthEnd, mDayEnd, 0, 0);
        dialogStartDate.getDatePicker().setMaxDate(calender.getTimeInMillis());
        //dialogStartDate.getDatePicker().setMinDate(calender.getTimeInMillis());
        dialogStartDate.show();
    }

    static void openStartDateUnlDialog(Context context, TextView tvStartDate, TextView tvEndDate) {
        Calendar calender = Calendar.getInstance();
        int mYear, mMonth, mDay, mYearEnd, mMonthEnd, mDayEnd;
        String[] arrDate = tvStartDate.getText().toString().trim().split("-");
        mYear = Integer.parseInt(arrDate[2]);
        mMonth = Integer.parseInt(arrDate[1]) - 1;
        mDay = Integer.parseInt(arrDate[0]);

        String[] arrDateEnd = tvEndDate.getText().toString().trim().split("-");
        mYearEnd = Integer.parseInt(arrDateEnd[2]);
        mMonthEnd = Integer.parseInt(arrDateEnd[1]) - 1;
        mDayEnd = Integer.parseInt(arrDateEnd[0]);


        DatePickerDialog dialogStartDate = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            String month, day;
            month = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
            day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String date = day + "-" + month + "-" + year;
            tvStartDate.setText(date);
            /*Date dateCurrent = Calendar.getInstance().getTime();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            tvEndDate.setText(df.format(dateCurrent));*/
            tvEndDate.setText(getCurrentDateMinus1());

            /*try {
                Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(tvStartDate.getText().toString());
                Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(tvEndDate.getText().toString());
                if (date2.after(date1)) {
                    Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                } else {
                    tvStartDate.setText(date);
                }
            } catch (Exception e) {
                Log.e("log", "Exception");
            }*/
        }, mYear, mMonth, mDay);
        //calender.set(mYearEnd, mMonthEnd, mDayEnd, 0, 0);
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        dialogStartDate.getDatePicker().setMaxDate(cal.getTimeInMillis());
        //dialogStartDate.getDatePicker().setMinDate(calender.getTimeInMillis());
        dialogStartDate.show();
    }

    static void openStartDateUnlDialogBalanceOperational(Context context, TextView tvStartDate, TextView tvEndDate) {
        Calendar calender = Calendar.getInstance();
        int mYear, mMonth, mDay, mYearEnd, mMonthEnd, mDayEnd;
        String[] arrDate = tvStartDate.getText().toString().trim().split("-");
        mYear = Integer.parseInt(arrDate[2]);
        mMonth = Integer.parseInt(arrDate[1]) - 1;
        mDay = Integer.parseInt(arrDate[0]);

        String[] arrDateEnd = tvEndDate.getText().toString().trim().split("-");
        mYearEnd = Integer.parseInt(arrDateEnd[2]);
        mMonthEnd = Integer.parseInt(arrDateEnd[1]) - 1;
        mDayEnd = Integer.parseInt(arrDateEnd[0]);


        DatePickerDialog dialogStartDate = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            String month, day;
            month = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
            day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String date = day + "-" + month + "-" + year;
            tvStartDate.setText(date);
            /*Date dateCurrent = Calendar.getInstance().getTime();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            tvEndDate.setText(df.format(dateCurrent));*/

            //tvEndDate.setText(getCurrentDate());

            /*try {
                Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(tvStartDate.getText().toString());
                Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(tvEndDate.getText().toString());
                if (date2.after(date1)) {
                    //Toast.makeText(context, "end date is after start date", Toast.LENGTH_SHORT).show();
                } else {
                    tvEndDate.setText(getCurrentDate());
                    //Toast.makeText(context, "start date is after end date", Toast.LENGTH_SHORT).show();
                    //tvStartDate.setText(date);
                }
            } catch (Exception e) {
                Log.e("log", "Exception");
            }*/
        }, mYear, mMonth, mDay);

        try {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(tvEndDate.getText().toString());
            dialogStartDate.getDatePicker().setMaxDate(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
        //calender.set(mYearEnd, mMonthEnd, mDayEnd, 0, 0);
        //dialogStartDate.getDatePicker().setMaxDate(calender.getTimeInMillis());
        //dialogStartDate.getDatePicker().setMinDate(calender.getTimeInMillis());
        dialogStartDate.show();
    }

    static void openEndDateDialog(Context context, TextView tvStartDate, TextView tvEndDate) {
        Calendar calender = Calendar.getInstance();
        int mYear, mMonth, mDay, mYearStart, mMonthStart, mDayStart;
        String[] arrDate = tvEndDate.getText().toString().trim().split("-");
        mYear = Integer.parseInt(arrDate[2]);
        mMonth = Integer.parseInt(arrDate[1]) - 1;
        mDay = Integer.parseInt(arrDate[0]);

        String[] arrDateStart = tvStartDate.getText().toString().trim().split("-");
        mYearStart = Integer.parseInt(arrDateStart[2]);
        mMonthStart = Integer.parseInt(arrDateStart[1]) - 1;
        mDayStart = Integer.parseInt(arrDateStart[0]);

        DatePickerDialog dialogEndDate = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            String month, day;
            month = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
            day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String date = day + "-" + month + "-" + year;
            Log.d("TAg", "End date: " + date);
            tvEndDate.setText(date);
        }, mYear, mMonth, mDay);

        //dialogEndDate.getDatePicker().setMaxDate(new Date().getTime());
        dialogEndDate.getDatePicker().setMaxDate(calender.getTimeInMillis());
        calender.set(mYearStart, mMonthStart, mDayStart, 0, 0);
        //dialogEndDate.getDatePicker().setMinDate(calender.getTimeInMillis());
        dialogEndDate.show();
    }

    static void openEndDateUnlDialogBalanceOperational(Context context, TextView tvStartDate, TextView tvEndDate) {
        Calendar calender = Calendar.getInstance();
        int mYear, mMonth, mDay, mYearStart, mMonthStart, mDayStart;
        String[] arrDate = tvEndDate.getText().toString().trim().split("-");

        mYear = Integer.parseInt(arrDate[2]);
        mMonth = Integer.parseInt(arrDate[1]) - 1;
        mDay = Integer.parseInt(arrDate[0]);
        Log.d("TAg", "tvStartDate: " + tvStartDate.getText().toString());
        String[] arrDateStart = tvStartDate.getText().toString().trim().split("-");
        mYearStart = Integer.parseInt(arrDateStart[2]);
        mMonthStart = Integer.parseInt(arrDateStart[1]) - 1;
        mDayStart = Integer.parseInt(arrDateStart[0]);

        DatePickerDialog dialogEndDate = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            String month, day;
            month = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
            day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String date = day + "-" + month + "-" + year;
            Log.d("TAg", "End date: " + date);
            tvEndDate.setText(date);
        }, mYear, mMonth, mDay);

        //dialogEndDate.getDatePicker().setMaxDate(new Date().getTime());
        try {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(tvStartDate.getText().toString());
            dialogEndDate.getDatePicker().setMinDate(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;

        dialogEndDate.getDatePicker().setMaxDate(calender.getTimeInMillis());
        calender.set(mYearStart, mMonthStart, mDayStart, 0, 0);
        //dialogEndDate.getDatePicker().setMinDate(calender.getTimeInMillis());
        dialogEndDate.show();
    }

    static void openEndDateUnlDialog(Context context, TextView tvStartDate, TextView tvEndDate) {
        Calendar calender = Calendar.getInstance();
        int mYear, mMonth, mDay, mYearStart, mMonthStart, mDayStart;
        String[] arrDate = tvEndDate.getText().toString().trim().split("-");

        mYear = Integer.parseInt(arrDate[2]);
        mMonth = Integer.parseInt(arrDate[1]) - 1;
        mDay = Integer.parseInt(arrDate[0]);
        Log.d("TAg", "tvStartDate: " + tvStartDate.getText().toString());
        String[] arrDateStart = tvStartDate.getText().toString().trim().split("-");
        mYearStart = Integer.parseInt(arrDateStart[2]);
        mMonthStart = Integer.parseInt(arrDateStart[1]) - 1;
        mDayStart = Integer.parseInt(arrDateStart[0]);

        DatePickerDialog dialogEndDate = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            String month, day;
            month = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
            day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            String date = day + "-" + month + "-" + year;
            Log.d("TAg", "End date: " + date);
            tvEndDate.setText(date);
        }, mYear, mMonth, mDay);

        //dialogEndDate.getDatePicker().setMaxDate(new Date().getTime());
        try {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(tvStartDate.getText().toString());
            dialogEndDate.getDatePicker().setMinDate(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        dialogEndDate.getDatePicker().setMaxDate(cal.getTimeInMillis());
        Log.d("TAg", "--->");
        //dialogEndDate.getDatePicker().setMaxDate(calender.getTimeInMillis());
        calender.set(mYearStart, mMonthStart, mDayStart, 0, 0);
        //dialogEndDate.getDatePicker().setMinDate(calender.getTimeInMillis());
        dialogEndDate.show();
    }

    static String getPrintDateFormat(String data) {
       /* String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] date = data.split("[-]");
        String date_ = months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[0] + ", " + date[2];
       */
        return formatTimeResultNew(data);
    }

    static String getPlrTxnType(String txnType) {
        if (txnType.trim().equalsIgnoreCase("PLR_DEPOSIT")) {
            return "Deposit";
        } else if (txnType.trim().equalsIgnoreCase("PLR_WITHDRAWAL")) {
            return "Withdrawal";
        } else if (txnType.trim().equalsIgnoreCase("PLR_WAGER")) {
            return "Wager";
        } else if (txnType.trim().equalsIgnoreCase("PLR_CANCEL")) {
            return "Player Cancellation";
        } else if (txnType.trim().equalsIgnoreCase("PLR_WINNING")) {
            return "Winning";
        } else if (txnType.trim().equalsIgnoreCase("PLR_DEPOSIT_AGAINST_CANCEL")) {
            return "Deposit Against Cancellation";
        } else if (txnType.trim().equalsIgnoreCase("PLR_TX_CANCEL")) {
            return "Transaction Cancel";
        } else if (txnType.trim().equalsIgnoreCase("PLR_WAGER_REFUND")) {
            return "Wager Refund";
        } else if (txnType.trim().equalsIgnoreCase("BO_CORRECTION")) {
            return "Back Office Correction";
        } else if (txnType.trim().equalsIgnoreCase("TICKET_WAGER")) {
            return "Ticket Wager";
        } else if (txnType.trim().equalsIgnoreCase("TICKET_WAGER_REFUND")) {
            return "Ticket Wager Refund";
        } else if (txnType.trim().equalsIgnoreCase("TICKET_WINNING")) {
            return "Ticket Winning";
        } else if (txnType.trim().equalsIgnoreCase("PLR_COMM_TXN")) {
            return "Communication Transaction";
        } else if (txnType.trim().equalsIgnoreCase("WITHDRAWAL_CHARGE_TXN")) {
            return "Withdrawal Charge Transaction";
        } else if (txnType.trim().equalsIgnoreCase("WITHDRAWAL_CHARGE_REFUND")) {
            return "Withdrawal Charge Refund";
        }
        return txnType;
    }

    static void makeRequiredField(TextInputLayout editText, Activity activity) {
        String tag = editText.getHint().toString() + activity.getString(R.string.asteriskred);

        editText.setHint(tag);
    }

    static void makeRequiredField(TextView textView, Activity activity) {
        String tag = textView.getText().toString() + activity.getString(R.string.asteriskred);

        textView.setText(tag);
    }

    static void makeRequiredField(EditText editText, Activity activity) {
        String tag = editText.getHint().toString() + activity.getString(R.string.asteriskred);

        editText.setHint(tag);
    }

    static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    static void openCountryCodeDialogForAltMobile(FragmentActivity fragmentActivity, String codes) {
        ArrayList<String> arrayList = new ArrayList<>();

        if (codes.contains(",")) {
            List<String> arr = Arrays.asList(codes.split(","));
            for (int index = 0; index < arr.size(); index++) {
                arrayList.add(arr.get(index));
            }
        } else {
            arrayList.add(codes);
        }

        Utils.vibrate(Objects.requireNonNull(fragmentActivity));
        TextView tvCountryCode = fragmentActivity.findViewById(R.id.tvCountryCode_alt_mobile);
        CountryCodeListener listener = tvCountryCode::setText;
        CountryCodeDialog dialog = new CountryCodeDialog(fragmentActivity, listener, arrayList);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    static void openCountryCodeDialog(FragmentActivity fragmentActivity, TextView textView, String codes) {
        ArrayList<String> arrayList = new ArrayList<>();

        if (codes.contains(",")) {
            List<String> arr = Arrays.asList(codes.split(","));
            for (int index = 0; index < arr.size(); index++) {
                arrayList.add(arr.get(index));
            }
        } else {
            arrayList.add(codes);
        }

        Utils.vibrate(Objects.requireNonNull(fragmentActivity));
        CountryCodeListener listener = textView::setText;
        CountryCodeDialog dialog = new CountryCodeDialog(fragmentActivity, listener, arrayList);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    static String capitalizeFirstLetter(String input) {
        StringBuilder result;
        try {
            if (input.isEmpty()) {
                return "";
            }
            result = new StringBuilder(input.length());
            String words[] = input.split("\\ ");
            for (int i = 0; i < words.length; i++) {
                String upperString = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);

                result.append(" ").append(upperString);
            }

        } catch (Exception e) {
            return input;
        }
        return result.toString().trim();
    }

    static String formatDate(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat(ConfigData.getInstance().getConfigData().getDEFAULTDATEFORMAT());
        Date date;
        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }


    static String formatTimeResult(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date;

        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }


    static String formatTimeWinningClaim(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
        Date date;

        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }

    static String formatTimeResultNew(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        Date date;

        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }

    static String formatTimeResultForFieldXChallan(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd yyyy");
        Date date;

        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }

    static String formatTime(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("hh:mm aa");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm:ss");
        Date date;
        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }

    static String formatTimeForFieldX(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("kk:mm");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm:ss");
        Date date;
        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }

    static Drawable getLogo(Context context, String Key) {
        HashMap<String, Drawable> map = new HashMap<>();
        map.put(AppConstants.ACDC, context.getResources().getDrawable(R.drawable.ipt_logo));
        map.put(AppConstants.SISAL, context.getResources().getDrawable(R.drawable.logo_infinity));
        map.put(AppConstants.CAMEROON, context.getResources().getDrawable(R.drawable.wincamloto_logo));
        map.put(AppConstants.OLA_MYANMAR, context.getResources().getDrawable(R.drawable.ola_myanmar_app_icon));
        map.put(AppConstants.UNL_RETAIL, context.getResources().getDrawable(R.drawable.unl_3));

        if (!map.containsKey(Key)) {
            return context.getResources().getDrawable(R.drawable.unl_3);
        }

        return map.get(Key);
    }

    static double getFormattedAmount(Double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);

        DecimalFormat decimalFormat = new DecimalFormat("#.##", symbols);

        return Double.parseDouble(decimalFormat.format(amount));
    }

    static String getFormattedValue(Double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##", symbols);

        return String.valueOf(decimalFormat.format(amount));
    }

    static String getNewFormattedValue(String amount) {
        try {
            Double newAmount;
            Log.d("TAg", "amount in : " + amount);
            if (amount.contains(" ")) amount = amount.replaceAll(" ", "");
            if (amount.contains(",")) return amount.replaceAll(",", " ");

            amount = amount.replaceAll(" ", "");
            //Log.d("TAg", "amount: " + amount);
            newAmount = Double.valueOf(amount);

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);

            DecimalFormat decimalFormat = new DecimalFormat("########.00", symbols);

            String formatAmount = decimalFormat.format(newAmount);
            //Log.d("TAg", "formatAmount ---------------------->" + formatAmount);

            if (formatAmount.endsWith(".00")) {
                formatAmount = formatAmount.replace(".00", "");
            }

            if (formatAmount.endsWith(".0")) {
                formatAmount = formatAmount.replace(".0", "");
            }

            return formatAmount;
        } catch (Exception e) {
            Log.d("TAg", "exception ---------------------->" + e);

            return amount;
        }
    }

    /*static String getFormattedValueSisal(Double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);

        DecimalFormat decimalFormat = new DecimalFormat("#.###,##", symbols);

        return String.valueOf(decimalFormat.format(amount));
    }*/

    static String getDefaultCurrency(String language) {
        //String a = "en#.#UAH#N~uk#,#UAH#N#,"; // for Ukrain

        if (ConfigData.getInstance().getConfigData() == null || ConfigData.getInstance().getConfigData().getcCURRENCYLIST().isEmpty())
            return "";

        String[] currencyListArray = ConfigData.getInstance().getConfigData().getcCURRENCYLIST().split("~");

        if (currencyListArray.length < 1) return "";

        for (String aCurrencyListArray : currencyListArray) {

            String[] languageArray = aCurrencyListArray.split("#");

            if (languageArray[0].equalsIgnoreCase(language)) {
                return languageArray[2];
            }
        }

        return "";
    }

    static String getCountryName(String code) {
        Gson gson = new Gson();

        CountryCodeBean model = gson.fromJson(CountryCode.COUNTRY_CODE_JSON, CountryCodeBean.class);
        ArrayList<CountryCodeBean.Country> countries = new ArrayList<>();
        countries = model.getCountries();
        try {
            for (CountryCodeBean.Country country : countries) {

                if (country.getCode().equalsIgnoreCase(code)) {

                    return country.getName();
                }
            }
            return code;
        } catch (Exception e) {
            return code;
        }
    }

    static String getNegativeValueFormatted(String amount, String lang, String formatter) {
        if (!amount.contains("-")) {
            return getFormattedCash(removeExponential(amount), formatter);
        }

        if (ConfigData.getInstance().getConfigData() == null || ConfigData.getInstance().getConfigData().getcCURRENCYLIST().isEmpty())
            return "";

        return String.format(Locale.US, "%.2f", Double.parseDouble(getFormattedCash(removeExponential(amount), formatter)));
    }

    static String getAmountValueFormatted(String amount, String lang) {
        if (!amount.contains("-")) {
            return getDefaultCurrency(lang) + amount;
        }
        String[] currencyListArray = ConfigData.getInstance().getConfigData().getcCURRENCYLIST().split("~");

        if (ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO")) {
            currencyListArray = "en#,#MAD#Y~fr#,#MAD#Y~ar#,#MAD#Y".split("~");
        }

        if (currencyListArray.length < 1) return "";


        for (String aCurrencyListArray : currencyListArray) {

            String[] languageArray = aCurrencyListArray.split("#");

            if (languageArray[0].equalsIgnoreCase(lang) && languageArray[3].equalsIgnoreCase("N")) {
                // return "(" + getDefaultCurrency(lang) + String.valueOf(Math.abs(Double.parseDouble( getFormattedCash(removeExponential(amount), formatter)))) + ")";
                return "(" + getDefaultCurrency(lang) + amount.replace("-", "") + ")";
            } else if (languageArray[0].equalsIgnoreCase(lang) && languageArray[3].equalsIgnoreCase("Y")) {
                return "- " + getDefaultCurrency(lang) + amount.replace("-", "");
            }
        }

        return "";
    }

    static String removeExponential(String amount) {
        if (amount.contains(",")) {
            String formattedText = String.format(Locale.US, "%.2f", Double.parseDouble(amount.replaceAll(",", ".")));
            return formattedText;
        } else {
            return String.format(Locale.US, "%.2f", Double.parseDouble(amount));
        }
    }

    static String removeExponentital(Double amount) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMANY);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###,###.##", otherSymbols);

        String format = df.format(amount);

        return String.format(Locale.US, "%.2f", format);
    }

    static String getFormattedCash(String amount, String formatter) {
        int decimalDigits = 2;
        if (ConfigData.getInstance().getConfigData() == null || ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE() == null) {
            return amount;
        }
        decimalDigits = Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE());
        if (decimalDigits == 0 && amount.contains(formatter)) {
            return amount.split("\\\\" + formatter)[0];
        }
        return amount;
    }

    static String getFormattedCash(Double amount) {
        return String.format(Locale.getDefault(), "%.2f", amount);
    }

    static String getBalanceToView(double balance, String dotformatter, String amountFormatter, int decimalDegits) {
        String text = "";
        String text2 = "";

        text = getFormattedAmount(String.valueOf(balance));
        Log.d("TAg", "balance----->>>>>" + balance);
        Log.d("TAg", "text----->>>>>" + text);

        if (decimalDegits == 0) {
            text = text.split("\\.")[0];

            if (text.contains(",")) {
                text = text.replaceAll(",", amountFormatter);
                return text;
            } else {
                text = text.replaceAll("\\.", amountFormatter);
                return text;
            }
        } else {
            String text1 = text.split("\\.")[0];
            if (text1.contains(",")) {
                text1 = text1.replaceAll(",", amountFormatter);
            } else {
                text1 = text1.replaceAll("\\.", amountFormatter);
            }

            if (text.contains(".")) text2 = text.split("\\.")[1];
            else text2 = text;

            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)) return text1;
            else return text1 + dotformatter + text2;
        }
    }

    static String getBalanceToViewSbs(double balance, String dotformatter, String amountFormatter, int decimalDegits) {
        String text = "";
        text = getFormattedAmountSbs(String.valueOf(balance));

        if (decimalDegits == 0) {
            text = text.split("\\.")[0];

            if (text.contains(",")) {
                text = text.replaceAll(",", amountFormatter);
                return text;
            } else {
                text = text.replaceAll("\\.", amountFormatter);
                return text;
            }
        } else {
            String text1 = text.split("\\.")[0];
            if (text1.contains(",")) {
                text1 = text1.replaceAll(",", amountFormatter);
            } else {
                text1 = text1.replaceAll("\\.", amountFormatter);
            }

            String text2 = text.split("\\.")[1];

            return text1 + dotformatter + text2;
        }
    }

    static int getAppVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    static String getAppVersionName(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return context.getString(R.string.version) + " " + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    static void showCustomErrorDialogAndFinish(Activity activity, String header, String message) {
        ErrorDialogForActivity dialog = new ErrorDialogForActivity(activity, header, message);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    static void showCustomToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.getBackground().setColorFilter(context.getResources().getColor(R.color.colorDue), PorterDuff.Mode.SRC_IN);

        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(60);
        text.setPadding(10, 0, 10, 0);
        text.setTypeface(null, Typeface.BOLD);
        text.setTextColor(context.getResources().getColor(R.color.colorWhite));
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);

        toast.show();
    }

    /*static void updateLanguage(Activity context, String language) {
        Log.d("TAg", "updateLanguage: " + language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());

        Intent intent = context.getIntent();

        context.finish();
        context.startActivity(intent);
    }*/

    static void updateLanguage(Activity context, String language) {
        Log.d("TAg", "updateLanguage: " + language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }

        Intent intent = context.getIntent();

        context.finish();
        context.startActivity(intent);
    }

    static String getAppLable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "Unknown");
    }

    static String getCurrencyFormatter(String lang) {
        Log.d("TAg", "lang: " + lang);
        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
            Log.d("TAg", "getDECIMAL_CHARACTER: " + ConfigData.getInstance().getConfigData().getDECIMAL_CHARACTER());
            return (ConfigData.getInstance().getConfigData().getDECIMAL_CHARACTER() != null) ? ConfigData.getInstance().getConfigData().getDECIMAL_CHARACTER() : ".";

        } else {
            if (ConfigData.getInstance().getConfigData() == null || ConfigData.getInstance().getConfigData().getcCURRENCYLIST().isEmpty())
                return ".";
            Log.d("TAg", "ConfigData.getInstance().getConfigData().getcCURRENCYLIST(): " + ConfigData.getInstance().getConfigData().getcCURRENCYLIST());
            String[] currencyListArray = ConfigData.getInstance().getConfigData().getcCURRENCYLIST().split("~");
            Log.d("TAg", "currencyListArray[0]: " + currencyListArray[0]);
            Log.d("TAg", "currencyListArray[1]: " + currencyListArray[1]);

            if (ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO")) {
                Log.d("TAg", "---------------------------------->");
                currencyListArray = "en#,#MAD#Y~fr#,#MAD#Y~ar#,#MAD#Y,#MAD#Y~uk#,".split("~");
            }

            if (currencyListArray.length < 1) return ".";

            for (String aCurrencyListArray : currencyListArray) {

                String[] languageArray = aCurrencyListArray.split("#");
                Log.d("TAg", "languageArray: " + aCurrencyListArray.split("#"));
                Log.d("TAg", "languageArray[0]: " + languageArray[0]);
                Log.d("TAg", "languageArray[1]: " + languageArray[1]);

                if (languageArray[0].equalsIgnoreCase(lang)) {
                    return languageArray[1];
                }
            }

            return ".";
        }
    }

    static String getAmountFormatter(String lang) {
        if (ConfigData.getInstance().getConfigData() == null || ConfigData.getInstance().getConfigData().getcCURRENCYLIST().isEmpty())
            return ".";

        String[] currencyListArray = ConfigData.getInstance().getConfigData().getcCURRENCYLIST().split("~");

        if (ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO")) {
            currencyListArray = "en#,#MAD#Y# ~fr#,#MAD#Y# ~ar#,#MAD#Y# ".split("~");
        }

        if (currencyListArray.length < 1) return ".";

        for (String aCurrencyListArray : currencyListArray) {

            String[] languageArray = aCurrencyListArray.split("#");

            if (languageArray[0].equalsIgnoreCase(lang)) {
                return languageArray[4];
            }
        }

        return ".";
    }

    static String getFormattedAmount(String amt) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        DecimalFormat df = new DecimalFormat("###,###,###.##", otherSymbols);

        return df.format(Double.parseDouble(amt));
    }

    static String getFormattedAmountSbs(String amt) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat formatter = (DecimalFormat) nf;
        formatter.applyPattern("###,###,###.##");

        return formatter.format(Double.parseDouble(amt));
    }

    static String getTempFormattedAmount(String cash) {
        String[] casharr = cash.split(",");

        Double amt = Double.parseDouble(casharr[0]);

        String startamount = String.format("%.0f", amt);
        startamount = getFormattedAmount(startamount);

        return startamount.replace(",", " ") + "," + casharr[1];
    }

    static void playWinningSound(Context context, int mediaId) {
        final AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        final int originalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        //mAudioManager.setSpeakerphoneOn(true);
        MediaPlayer mediaPlayer = MediaPlayer.create(context, mediaId);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(1.0f, 1.0f);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);
            mediaPlayer.release();
            //mediaPlayer = null;
        });
    }

    static void setMaxLength(EditText editText, int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(filterArray);
    }

    static int nCr(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    static int factorial(int n) {
        int res = 1;
        for (int i = 2; i <= n; i++)
            res = res * i;
        return res;
    }

    static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei;

            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    imei = telephonyManager.getImei();
                else imei = telephonyManager.getDeviceId();

                if (imei != null && !imei.isEmpty()) return imei;
                else return android.os.Build.SERIAL;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void animateColor(View v, int startColor, int endColor) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(startColor, endColor);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(valueAnimator -> {
            GradientDrawable background = (GradientDrawable) v.getBackground();
            background.setColor((Integer) valueAnimator.getAnimatedValue());
        });

        anim.setDuration(500);
        anim.start();
    }

    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void animateColor(View v, int startColor, int endColor, Context context) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(startColor, endColor);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(valueAnimator -> {
            GradientDrawable background = (GradientDrawable) v.getBackground();
            background.setColor((Integer) valueAnimator.getAnimatedValue());
        });

        anim.setDuration(400);
        anim.start();
        popUpAnimation(context, v);
    }

    static void popUpAnimation(Context context, View view) {
        AnimationSet mAnimIn = AnimationLoader.getInAnimation(context);

        view.startAnimation(mAnimIn);
    }

    static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            if (model.equalsIgnoreCase("T2mini_s"))
                return capitalize(manufacturer) + " T2mini";
            else
                return capitalize(manufacturer) + " " + model;
        }
    }


    static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    static long getTimeDifference(String fetchDate, String currentDate_) {
        long minutes = 0;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date futureDate = dateFormat.parse(fetchDate);
            Date currentDate = dateFormat.parse(currentDate_);
            minutes = futureDate.getTime() - currentDate.getTime();
            //long seconds = diff / 1000;
            //minutes = seconds / 60;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minutes;
    }

    static void showGameSwitchDialog(Context context, int image, String info, String softMsg, String cancelBtnMsg, String okBtnMsg, GameChangeListener listener) {
        CustomSuccessDialog.getProgressDialog().showCustomGameSwitchDialog(context, image, info, softMsg, cancelBtnMsg, okBtnMsg, listener);
    }

    static void showOverlayInstructionDialog(Context context, OverlayPermissionListener listener) {
        CustomSuccessDialog.getProgressDialog().showCustomOverlayInstructionDialog(context, listener);
    }

    static void setResultDate(Context context, EditText date) {
        int year = 0, month = 0, day = 0;
        String selected_date = null;
        final Calendar c = Calendar.getInstance();
        if (date.getText().toString().equalsIgnoreCase(context.getString(R.string.date_selection)))
            selected_date = formatTimeResult(Utils.getCurrentDateResult());
        else selected_date = formatTimeResult(date.getText().toString());

        String splitDate[] = selected_date.split("-");
        year = Integer.parseInt(splitDate[0]);
        month = Integer.parseInt(splitDate[1]) - 1;
        day = Integer.parseInt(splitDate[2]);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, monthOfYear, dayOfMonth) -> {
            String format_date = getPrintDateFormat(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);
            date.setText(format_date);
            if (!date.getText().toString().equalsIgnoreCase(context.getString(R.string.date_selection))) {
                ((ResultDrawDialogActivity) context).callResult();

            } else {
                Toast.makeText(context, context.getString(R.string.select_date), Toast.LENGTH_SHORT).show();

            }

        }, year, month, day);
        c.set(year, month, day, 0, 0);
        datePickerDialog.show();
    }

    static void setTimeTo(Context context, EditText time) {
        final Calendar c = Calendar.getInstance();
        int mHour, mMinute;
        if (time.getText().toString().equalsIgnoreCase(context.getString(R.string.to_time))) {
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
        } else {
            mHour = Integer.parseInt(time.getText().toString().split(":")[0]);
            mMinute = Integer.parseInt(time.getText().toString().split(":")[1]);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            String format_hours = "";
            String format_min = "";
            format_hours = String.format("%02d", hourOfDay);
            format_min = String.format("%02d", minute);
            time.setText(format_hours + ":" + format_min);
        }, mHour, mMinute, false);
        c.set(0, 0, 0, mHour, mMinute);
        timePickerDialog.show();
    }

    static void setTimeFrom(Context context, EditText time) {
        final Calendar c = Calendar.getInstance();
        int mHour, mMinute;
        if (time.getText().toString().equalsIgnoreCase(context.getString(R.string.from_time))) {
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
        } else {
            mHour = Integer.parseInt(time.getText().toString().split(":")[0]);
            mMinute = Integer.parseInt(time.getText().toString().split(":")[1]);
        }


        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            String format_hours = "";
            String format_min = "";
            format_hours = String.format("%02d", hourOfDay);
            format_min = String.format("%02d", minute);
            time.setText(format_hours + ":" + format_min);
        }, mHour, mMinute, false);
        c.set(0, 0, 0, mHour, mMinute);
        timePickerDialog.show();
    }

    static boolean compareTime(String from_time, String to_time, Context context) {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

        try {
            Date from = parser.parse(from_time);
            Date to = parser.parse(to_time);
            Date userDate = parser.parse(from_time);

            if (from.before(to)) {
                return true;

            } else if (to.after(from)) {
                return true;
            } else if (from.equals(to)) {
                return true;
            } else {
                Utils.showRedToast(context, "From Time Can Not Be Greater Than To Time");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    static String getModelCode() {
        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS)) return "MOBILE";
        if (getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            return AppConstants.MODEL_V2PRO;
        if (getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            return AppConstants.MODEL_T2MINI;
        if (getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_TPS570))
            return AppConstants.MODEL_TPS570;
        return "NA";
    }

    static String getDeviceModelCode() {

        //for terminal
        if (getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2PRO) || getDeviceName().contains("V2_PRO")) {
            return "V2pro";
        } else if (getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2s) ||
                getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2_s) || getDeviceName().contains("V2s")) {
            return "V2s";
        } else if (getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2)) {
            return "V2";
        } else {
            return "";
        }

        //for mobile App
        //return "NA";
    }

    static String getDeviceSerialNumber() {
        String serialNumber;

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);

            serialNumber = (String) get.invoke(c, "gsm.sn1");
            if (serialNumber.equals("")) serialNumber = (String) get.invoke(c, "ril.serialnumber");
            if (serialNumber.equals("")) serialNumber = (String) get.invoke(c, "sys.serialnumber");

            if (serialNumber.equals("")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    try {

                        //this code is for particular sumni devices
                        serialNumber = (String) get.invoke(c, "ro.sunmi.serial");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    serialNumber = Build.getSerial();
                } else {
                    try {
                        serialNumber = (String) get.invoke(c, "ro.serialno");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // If none of the methods above worked
            if (serialNumber.equals("")) serialNumber = null;
        } catch (Exception e) {
            e.printStackTrace();
            serialNumber = null;
        }

        try {
            Log.i("log", "Device Serial Number: " + serialNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (serialNumber != null && serialNumber.equalsIgnoreCase("unknown")) serialNumber = "NA";


        return serialNumber;
    }


    static Bitmap scaleBitmapAndKeepRation(Bitmap targetBmp, int reqHeightInPixels, int reqWidthInPixels) {
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0, 0, targetBmp.getWidth(), targetBmp.getHeight()), new RectF(0, 0, reqWidthInPixels, reqHeightInPixels), Matrix.ScaleToFit.CENTER);
        Bitmap scaledBitmap = Bitmap.createBitmap(targetBmp, 0, 0, targetBmp.getWidth(), targetBmp.getHeight(), matrix, true);
        return scaledBitmap;
    }

    static void showGreenToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View view = toast.getView();

        view.getBackground().setColorFilter(Color.parseColor("#089148"), PorterDuff.Mode.SRC_IN);

        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    static void showCustomConfirmationDialog(Context context, int image, String info, String softMsg, String cancelBtnMsg, String okBtnMsg, ConfirmationListener listener) {
        CustomSuccessDialog.getProgressDialog().showCustomConfirmationDialog(context, image, info, softMsg, cancelBtnMsg, okBtnMsg, listener);
    }

    static void showRedToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View view = toast.getView();

        view.getBackground().setColorFilter(Color.parseColor("#d30e24"), PorterDuff.Mode.SRC_IN);

        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    static String getAmountWithCurrency(String strAmount, Context context) {
        String formattedAmount;
        try {
            formattedAmount = getBalanceToView(Double.parseDouble(strAmount), getCurrencyFormatter(SharedPrefUtil.getLanguage(context)), getAmountFormatter(SharedPrefUtil.getLanguage(context)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(strAmount);
        }
        return formattedAmount;
    }

    static int getDecimalPlacesSbs(Double amount_sbs) {
        int decimalDigits = 0;
        try {
            String amount = String.valueOf(amount_sbs);
            if (amount.contains(".")) {
                String split[] = amount.split("\\.");
                decimalDigits = Integer.parseInt(split[1]);
                if (decimalDigits == 0) {
                    decimalDigits = 0;
                } else {
                    String val = String.valueOf(decimalDigits);
                    decimalDigits = val.length();
                }
            } else {
                decimalDigits = 0;
            }
        } catch (Exception e) {
            return decimalDigits;
        }
        return decimalDigits;
    }

    static String getSbsUrl(String url, Context context) {
        try {
            String game_url = url + "sbs/retailerPos/" + PlayerData.getInstance().getUserId() + "/" + PlayerData.getInstance().getUsername() + "/" + PlayerData.getInstance().getToken().split(" ")[1] + "/" + PlayerData.getInstance().getOrgBalance() + "/" + Locale.getDefault().getLanguage() + "/" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context)) + "/" + "no_alias/SPORTS_BOOK";
            return game_url;
        } catch (Exception e) {
            return null;
        }
    }

    public static int insertAPN(String name, Activity activity) {
//Set the URIs and variables
        int id = -1;
        boolean existing = false;
        final Uri APN_TABLE_URI = Uri.parse("content://telephony/carriers");
        final Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
//Check if the specified APN is already in the APN table, if so skip the insertion
        Cursor parser = activity.getContentResolver().query(APN_TABLE_URI, null, null, null, null);
        parser.moveToLast();
        while (parser.isBeforeFirst() == false) {
            int index = parser.getColumnIndex("name");
            String n = parser.getString(index);
            if (n.equals(name)) {
                existing = true;
                Toast.makeText(activity.getApplicationContext(), "APN already configured.", Toast.LENGTH_SHORT).show();
                break;
            }
            parser.moveToPrevious();
        }
//if the entry doesn't already exist, insert it into the APN table
        if (!existing) {
//Initialize the Content Resolver and Content Provider
            ContentResolver resolver = activity.getContentResolver();
            ContentValues values = new ContentValues();
//Capture all the existing field values excluding name
            Cursor apu = activity.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
            apu.moveToFirst();
            int index;
            index = apu.getColumnIndex("apn");
            String apn = apu.getString(index);
            index = apu.getColumnIndex("type");
            String type = apu.getString(index);
            index = apu.getColumnIndex("proxy");
            String proxy = apu.getString(index);
            index = apu.getColumnIndex("port");
            String port = apu.getString(index);
            index = apu.getColumnIndex("user");
            String user = apu.getString(index);
            index = apu.getColumnIndex("password");
            String password = apu.getString(index);
            index = apu.getColumnIndex("server");
            String server = apu.getString(index);
            index = apu.getColumnIndex("mmsc");
            String mmsc = apu.getString(index);
            index = apu.getColumnIndex("mmsproxy");
            String mmsproxy = apu.getString(index);
            index = apu.getColumnIndex("mmsport");
            String mmsport = apu.getString(index);
            index = apu.getColumnIndex("mcc");
            String mcc = apu.getString(index);
            index = apu.getColumnIndex("mnc");
            String mnc = apu.getString(index);
            index = apu.getColumnIndex("numeric");
            String numeric = apu.getString(index);
//Assign them to the ContentValue object
            values.put("name", name); //the method parameter
            values.put("apn", apn);
            values.put("type", type);
            values.put("proxy", proxy);
            values.put("port", port);
            values.put("user", user);
            values.put("password", password);
            values.put("server", server);
            values.put("mmsc", mmsc);
            values.put("mmsproxy", mmsproxy);
            values.put("mmsport", mmsport);
            values.put("mcc", mcc);
            values.put("mnc", mnc);
            values.put("numeric", numeric);
//Actual insertion into table
            Cursor c = null;
            try {
                Uri newRow = resolver.insert(APN_TABLE_URI, values);
                if (newRow != null) {
                    c = resolver.query(newRow, null, null, null, null);
                    int idindex = c.getColumnIndex("_id");
                    c.moveToFirst();
                    id = c.getShort(idindex);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (c != null) c.close();
        }
        return id;
    }

    //Takes the ID of the new record generated in InsertAPN and sets that particular record the default preferred APN configuration
    public static boolean setPreferredAPN(int id, Activity activity) {
//If the id is -1, that means the record was found in the APN table before insertion, thus, no action required
        if (id == -1) {
            return false;
        }
        Uri.parse("content://telephony/carriers");
        final Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
        boolean res = false;
        ContentResolver resolver = activity.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("apn_id", id);
        try {
            resolver.update(PREFERRED_APN_URI, values, null, null);
            Cursor c = resolver.query(PREFERRED_APN_URI, new String[]{"name", "apn"}, "_id=" + id, null, null);
            if (c != null) {
                res = true;
                c.close();
            }
        } catch (SQLException e) {
        }
        return res;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    static String isConnectionFast(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        int type = info.getType();
        int subType = info.getSubtype();

        if (type == ConnectivityManager.TYPE_WIFI) {
            return "TYPE_WIFI";
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return "50-100 kbps"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "14-64 kbps"; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "50-100 kbps"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return "400-1000 kbps"; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return "600-1400 kbps"; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "100 kbps"; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return "2-14 Mbps"; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return "700-1700 kbps"; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return "1-23 Mbps"; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return "400-7000 kbps"; // ~ 400-7000 kbps
                /*
                 * Above API level 7, make sure to set android:targetSdkVersion
                 * to appropriate level to use these
                 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return "1-2 Mbps"; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return "5 Mbps"; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return "10-20 Mbps"; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return "25 kbps"; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return "10+ Mbps"; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    static int getNoOfMonths(HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(menuBean.getApiDetails());
            JSONObject headerObject = jsonObject.getJSONObject("netGamingRangeDetails").getJSONObject("headers");
            int noOfMonths = headerObject.getInt("monthCount");
            return noOfMonths;
        } catch (JSONException e) {
            Toast.makeText(context, context.getString(R.string.url_problem), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return 0;
        }
    }

    /*static String getImeiNumber(Context context) {
        String ts = Context.TELEPHONY_SERVICE;
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(ts);
        String imsi = mTelephonyMgr.getSubscriberId();
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }*/

    static boolean isValidTime(String fromTime, String toTime, String actualTime) {
        try {
            Date time1 = new SimpleDateFormat("HH:mm").parse(fromTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = new SimpleDateFormat("HH:mm").parse(toTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            Date d = new SimpleDateFormat("HH:mm").parse(actualTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                return true;
            } else if (time2.equals(d)) {
                return true;
            } else if (time1.equals(d)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    static String getPrintDateFormatDaySleResult(String data) {
        String[] date = data.split("[-]");
        return months[Integer.parseInt(date[1].toString().substring(0, date[1].length())) - 1] + " " + date[0];
    }

    static List<String> getRangeList(String a, String b) {
        Long startNo  = Long.parseLong(a.replace("-",""));
        Long endNo = Long.parseLong(b.replace("-",""));

        List<String> temp = new ArrayList<String>();
        for(Long i = (startNo + 1); i<= endNo; i++){
            temp.add(ticketFormat(i.toString()));
        }
        return temp;
    }

    static String filterTicketLen(String ticket) {

        if (ticket.contains("-")) {
           ticket = ticket.replace("-","");
        }
        int ticketLen = ticket.length();
        return ticketFormat((ticketLen <= 12) ? ticket : ticket.substring((ticketLen - 12), ticketLen));
    }

    static String ticketFormat(String ticket) {
        if (ticket.contains("-")) {
            return ticket;
        } else {
            return ticket.substring(0,3) + "-" + ticket.substring(3,9) + "-" + ticket.substring(9,ticket.length());
        }
    }

    /*static <T> T deepCopy(T object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Serialize the object to JSON string
            String jsonString = mapper.writeValueAsString(object);
            // Deserialize the JSON string back to an object
            return (T) mapper.readValue(jsonString, object.getClass());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public static <T extends Serializable> T deepCopy(T object) {
        T copiedObject = null;
        try {
            // Write the object to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            oos.close();

            // Read the object from the byte array
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            copiedObject = (T) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return copiedObject;
    }

    static String getUkFormat(double value){
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("uk", "UA"));
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        // Format the double value
        String formattedValue = numberFormat.format(value);

        return formattedValue;
    }

}
