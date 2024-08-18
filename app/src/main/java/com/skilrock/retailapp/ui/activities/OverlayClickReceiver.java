package com.skilrock.retailapp.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.skilrock.retailapp.ui.activities.rms.SplashScreenActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OverlayClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent toDialog = new Intent(context, SplashScreenActivity.class);
        toDialog.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(toDialog);
    }
}