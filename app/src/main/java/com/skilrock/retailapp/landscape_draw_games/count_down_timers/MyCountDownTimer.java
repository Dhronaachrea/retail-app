package com.skilrock.retailapp.landscape_draw_games.count_down_timers;

import android.app.Activity;
import android.os.CountDownTimer;
import android.text.Html;
import android.widget.TextView;

import com.skilrock.retailapp.landscape_draw_games.ui.activities.DrawGameBaseActivity;


public class MyCountDownTimer extends CountDownTimer {

    public TextView textView;
    public String gameCode;
    private static MyCountDownTimer myCountDownTimer;
    public boolean running;
    public Activity activity;

    public static MyCountDownTimer getMyCountDownTimer(long millisInFuture, long countDownInterval) {
        myCountDownTimer = new MyCountDownTimer(millisInFuture, countDownInterval);

        return myCountDownTimer;
    }

    public void createCountDownTimer(TextView textView, String gameCode, boolean running, Activity activity) {
        this.textView = textView;
        this.activity = activity;
        this.gameCode = gameCode;
        this.running = running;
        //animation = AnimationUtils.loadAnimation(context, R.anim.anim_blink);
        this.start();
    }

    private MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        int progress = (int) (millisUntilFinished / 1000);
        int mins = progress / 60;
        progress = progress - mins * 60;
        int hours = mins / 60;
        int days = hours / 24;
        int remMinutes = mins % 60;

        String textMin = "<font color=#fcd116>" + mins + " </font> <font color=#f3f3f3> min </font>";
        String textRemMinutes = "<font color=#fcd116>" + remMinutes + " </font> <font color=#f3f3f3> min </font>";
        String textSeconds = "<font color=#fcd116>" + progress + " </font> <font color=#f3f3f3> sec</font>";
        String textHour = hours > 1 ? "<font color=#fcd116>" + hours + " </font> <font color=#f3f3f3> Hrs </font>" : "<font color=#fcd116> " + hours + " </font> <font color=#f3f3f3> Hr </font>";
        String textDays = days > 1 ? "<font color=#fcd116>" + days + " </font> <font color=#f3f3f3> Days</font>" : "<font color=#fcd116> " + days + " </font> <font color=#f3f3f3> Day </font>";

        if (days > 0)
            textView.setText(Html.fromHtml(textDays));
        else if (hours > 0)
            textView.setText(remMinutes > 0 ? Html.fromHtml(textHour + textRemMinutes) : Html.fromHtml(textHour));
        else if (mins > 0)
            textView.setText(Html.fromHtml(textMin + textSeconds));
        else
            textView.setText(Html.fromHtml(textSeconds));
    }

    @Override
    public void onFinish() {
        if(running) {
            ((DrawGameBaseActivity)activity).callFetchApi(gameCode);
            textView.setText("Fetching..");
        }
    }

    public void stop() {
        this.running = false;
        cancel();
    }
}