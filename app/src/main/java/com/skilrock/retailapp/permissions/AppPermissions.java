package com.skilrock.retailapp.permissions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class AppPermissions {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean checkPermission(FragmentActivity fragmentActivity) {
        return ContextCompat.checkSelfPermission(fragmentActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean checkPermissionLocation(Activity fragmentActivity) {
        return ActivityCompat.checkSelfPermission(fragmentActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void requestPermission(FragmentManager fragmentManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (fragmentManager != null) {
                List<Fragment> fragments = fragmentManager.getFragments();
                for (Fragment fragment: fragments)
                    fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, 200);
            }
        }
    }

    public static void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    1);
        }
    }

    public static void requestPermissionLocation(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
        }
    }

    public static void requestPermissionWinning(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    200);
        }
    }

    public static void showMessageOKCancel(FragmentActivity fragmentActivity, String message, final FragmentManager fragmentManager, final EditText editText) {
        new AlertDialog.Builder(fragmentActivity)
                .setMessage(message)
                .setPositiveButton(fragmentActivity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppPermissions.requestPermission(fragmentManager);
                    }
                })
                .setNegativeButton(fragmentActivity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText != null)
                            editText.requestFocus();
                    }
                })
                .create()
                .show();
    }

    public static void showMessageOKCancel(FragmentActivity fragmentActivity, String message, final Activity activity, final EditText editText) {
        new AlertDialog.Builder(fragmentActivity)
                .setMessage(message)
                .setPositiveButton(fragmentActivity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppPermissions.requestPermission(activity);
                    }
                })
                .setNegativeButton(fragmentActivity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText.requestFocus();
                    }
                })
                .create()
                .show();
    }

    public static void showMessageOKCancel(AppCompatActivity fragmentActivity, String message, final Activity activity, final EditText editText) {
        new AlertDialog.Builder(fragmentActivity)
                .setMessage(message)
                .setPositiveButton(fragmentActivity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppPermissions.requestPermission(activity);
                    }
                })
                .setNegativeButton(fragmentActivity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText.requestFocus();
                    }
                })
                .create()
                .show();
    }



    public static void showMessageOKCancel(Activity fragmentActivity, String message, final Activity activity) {
        new AlertDialog.Builder(fragmentActivity)
                .setMessage(message)
                .setPositiveButton(fragmentActivity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppPermissions.requestPermission(activity);
                    }
                })
                .setNegativeButton(fragmentActivity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }


    public static boolean CheckGPs(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    public static void turnOnGps(final Activity activity, String message){
        showGPSDisabledAlertToUser(activity, message);
    }

    public static void showGPSDisabledAlertToUser(final Activity activity, String message) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(callGPSSettingIntent);

                    }
                })
                .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.showRedToast(activity, "Kindly Turn On Gps");
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

}
