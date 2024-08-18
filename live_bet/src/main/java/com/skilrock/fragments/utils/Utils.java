package com.skilrock.fragments.utils;

import android.os.Build;

public interface Utils {
    static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            if (model.equalsIgnoreCase("T2mini_s"))
                return capitalize(manufacturer) + " T2mini";
            else
                return capitalize(manufacturer) + " " + model;
        }
    }

    static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
