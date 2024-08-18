package com.skilrock.retailapp.interfaces;

import java.util.ArrayList;

public interface CallBackListener {
    void notifyEvent();
    void notifyEvent(int num, ArrayList<String> list, int position);
}
