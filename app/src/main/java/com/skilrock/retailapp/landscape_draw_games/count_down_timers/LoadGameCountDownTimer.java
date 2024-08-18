package com.skilrock.retailapp.landscape_draw_games.count_down_timers;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;

import com.skilrock.retailapp.landscape_draw_games.ui.activities.DrawGameBaseActivity;

public class LoadGameCountDownTimer extends CountDownTimer {

    public String gameCode;
    private static LoadGameCountDownTimer myCountDownTimer;
    public boolean running;
    public Activity activity;

    public static LoadGameCountDownTimer getMyCountDownTimer(long millisInFuture) {
        myCountDownTimer = new LoadGameCountDownTimer(millisInFuture, 1000);

        return myCountDownTimer;
    }

    public void startCountDownTimer(String gameCode, boolean running, Activity activity) {
        this.activity = activity;
        this.gameCode = gameCode;
        this.running = running;
        this.start();
    }

    private LoadGameCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        int progress = (int) (millisUntilFinished / 1000);
        int mins = progress / 60;
        progress = progress - mins * 60;
        Log.d("TIMER", "30 sec timer " + gameCode + " " + progress);
    }

    @Override
    public void onFinish() {
        if (running) {
            running = false;
            ((DrawGameBaseActivity) activity).refreshGame(gameCode);
        }
    }

    public void stop() {
        this.running = false;
        cancel();
    }

    public boolean isCountDownTimerRunning(String gamecode) {
        if (!gamecode.equalsIgnoreCase(gameCode)) return false;

        return running;
    }
}