package com.skilrock.retailapp.portrait_draw_games.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.printer.AidlUtil;


public class PrintReady extends BroadcastReceiver  {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPrefUtil.putBooleanPrint(context, SharedPrefUtil.PRINT_NO_PAPER, true);

    }


}
