package com.skilrock.retailapp.interfaces;

import java.util.ArrayList;

public interface OtpListener {

    void onOtpReceived(String otp, ArrayList<String> listData);
    void onResendOtp();

}
