package com.skilrock.retailapp.portrait_draw_games.ui;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.skilrock.retailapp.utils.SharedPrefUtil;



public class NoPaperBroadCast extends BroadcastReceiver  {
    byte data []=null;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPrefUtil.putBooleanPrint(context, SharedPrefUtil.PRINT_NO_PAPER, false);
    }


}
