package com.skilrock.retailapp.utils;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class LockNotificationView extends ViewGroup {

    public LockNotificationView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v("CustomViewGroup", "**********Intercepted");
        return true;
    }

}
