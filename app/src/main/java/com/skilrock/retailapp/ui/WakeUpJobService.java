package com.skilrock.retailapp.ui;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;

import com.skilrock.retailapp.ui.activities.rms.LoginActivity;

public class WakeUpJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            contentIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        return false;
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}