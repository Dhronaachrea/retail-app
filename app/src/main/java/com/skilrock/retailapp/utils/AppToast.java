package com.skilrock.retailapp.utils;

import android.content.Context;
import android.widget.Toast;

public class AppToast {

    private static Toast mToast;

    public static void show(Context context, String text, int duration) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, text, duration);
        mToast.show();
    }
}