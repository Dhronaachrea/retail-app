package com.skilrock.retailapp.utils;

import android.content.Context;

import com.skilrock.retailapp.R;

public interface TranslateSbs {
    static String getTranslateVal(String code, Context context) {
        try {
            switch (code) {
                case "PAYOUT_DONE":
                    return context.getString(R.string.payout_done);
                case "INITIATED":
                    return context.getString(R.string.result_avaited);
                case "SUCCESS":
                    return context.getString(R.string.result_avaited);
                case "PARTIALLY_SUCCESS":
                    return context.getString(R.string.result_avaited);
                case "FAILED":
                    return context.getString(R.string.fialed);
                case "PAYOUT_NOT_REQUIRED":
                    return context.getString(R.string.bet_lost);
                case "CANCELLED":
                    return context.getString(R.string.ticket_cancelled);
                case "AUTO_CANCELLED":
                    return context.getString(R.string.ticket_cancelled_auto);
                default:
                    return code;
            }

        }catch (Exception e){
            return code;
        }
    }
}
