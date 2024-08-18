package com.skilrock.retailapp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.Log;

import java.util.Locale;

public class ContextWrapper extends android.content.ContextWrapper {

    public ContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context, Locale newLocale) {
        Log.d("TAg", "wrap");

        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)) {
            Log.d("TAg", "(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)");

            configuration.setLocale(newLocale);

            LocaleList localeList = null;
            localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);

            context = context.createConfigurationContext(configuration);

        }  else {
            Log.d("TAg", "(else)");
            configuration.locale = newLocale;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }

        return new ContextWrapper(context);
    }
}
