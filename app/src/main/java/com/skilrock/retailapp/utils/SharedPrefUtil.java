package com.skilrock.retailapp.utils;

import android.content.Context;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skilrock.retailapp.models.FieldX.RetailerLocationBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefUtil {

    public static final String USER_PREF                = "userPref";
    public static final String USER_PREF_LANGUAGE       = "userPrefLanguage";
    public static final String AUTH_TOKEN               = "authToken";
    public static final String USERNAME                 = "username";
    public static final String PASSWORD                 = "password";
    public static final String LAST_TICKET_NUMBER       = "lastTicketNumber";
    public static final String USER_ID                  = "userId";
    public static final String LOGIN_BEAN               = "loginBean";
    public static final String SHOW_HOME_SCREEN         = "showHomeScreen";
    public static final String DATE_TIME_FORMAT         = "datetimeformat";
    public static final String COUNTRY_CODE             = "countrycode";
    public static final String DEFAULT_DATETIME_FORMAT  = "defaultdatetimeformat";
    public static final String DEFAULT_DATE_FORMAT      = "defaultdateformat";
    public static final String APP_LANGUAGE             = "appLanguage";
    public static final String DRAW_LAST_TICKET_NUMBER  = "drawLastTicketNumber";
    public static final String WIN_LAST_TICKET_NUMBER   = "winLasTticketNumber";
    public static final String SBS_LAST_TICKET_NUMBER   = "winLasTticketNumber";
    public static final String DRAW_LAST_GAME_CODE      = "drawLastGameCode";
    public static final String PRINT_NO_PAPER           = "printNoPaper";
    public static final String CONTACT_US_NUMBER        = "contactUsNumber";
    public static final String USER_NAME                = "userName";
    public static final String RETAILER_LOCATION        = "retailer_location";

    public static String getLanguage(Context context) {
        return context.getSharedPreferences(USER_PREF_LANGUAGE, Context.MODE_PRIVATE).getString(APP_LANGUAGE, "uk");
    }

    public static void putLanguage(Context context, String value) {
        context.getSharedPreferences(USER_PREF_LANGUAGE, Context.MODE_PRIVATE).edit().putString(APP_LANGUAGE, value).apply();
    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).getString(key, "");
    }

    public static boolean getBoolean(Context context, String key) {
        return context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).getBoolean(key, true);
    }

    public static void putString(Context context, String key, String value) {
        context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static void putBoolean(Context context, String key, boolean flag) {
        context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).edit().putBoolean(key, flag).apply();
    }

    public static void clearAppSharedPref(Context context) {
        context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).edit().clear().apply();
    }

    public static void putLastTicketNumber(Context context, String key, String value) {
        context.getSharedPreferences(DRAW_LAST_TICKET_NUMBER, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static String getLastTicketNumber(Context context, String retailerName) {
        return context.getSharedPreferences(DRAW_LAST_TICKET_NUMBER, Context.MODE_PRIVATE).getString(retailerName, "0");
    }

    public static void putLastTicketNumberWinning(Context context, String key, String value) {
        context.getSharedPreferences(WIN_LAST_TICKET_NUMBER, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static String getLastTicketNumberWinning(Context context, String retailerName) {
        return context.getSharedPreferences(WIN_LAST_TICKET_NUMBER, Context.MODE_PRIVATE).getString(retailerName, "0");
    }


    public static void putLastTicketSbs(Context context, String key, String value) {
        context.getSharedPreferences(SBS_LAST_TICKET_NUMBER, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static String getLastTicketNumberSbs(Context context, String retailerName) {
        return context.getSharedPreferences(SBS_LAST_TICKET_NUMBER, Context.MODE_PRIVATE).getString(retailerName, "0");
    }

    public static void putLastGameCode(Context context, String key, String value) {
        context.getSharedPreferences(DRAW_LAST_GAME_CODE, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static String getLastGameCode(Context context, String gameCode) {
        return context.getSharedPreferences(DRAW_LAST_GAME_CODE, Context.MODE_PRIVATE).getString(gameCode, "0");
    }

    public static void putBooleanPrint(Context context, String key, boolean flag) {
        context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).edit().putBoolean(key, flag).apply();
    }

    public static boolean getBooleanPrint(Context context, String key) {
        return context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).getBoolean(key, true);
    }

    public static void putUserName(Context context, String userName) {
        context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE).edit().putString(USER_NAME, userName).apply();
    }

    public static String getUserName(Context context) {
        return context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE).getString(USER_NAME, "");
    }

    public static void setRetailerLocation(Context context, ArrayList<RetailerLocationBean> arrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);

        context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).edit().putString(RETAILER_LOCATION, json).apply();
    }

    public static ArrayList<RetailerLocationBean> getRetailerLocationArray(Context context) {
        Gson gson = new Gson();
        String json = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).getString(RETAILER_LOCATION, "");
        Type type = new TypeToken<ArrayList<RetailerLocationBean>>() {
        }.getType();

        ArrayList<RetailerLocationBean> arrayList = gson.fromJson(json, type);

        return arrayList;
    }

    public static Location getRetailerLocation(Context context, String id) {
        Location location = null;
        Gson gson = new Gson();
        String json = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).getString(RETAILER_LOCATION, "");
        Type type = new TypeToken<ArrayList<RetailerLocationBean>>() {
        }.getType();

        ArrayList<RetailerLocationBean> arrayList = gson.fromJson(json, type);

        for (RetailerLocationBean retailerLocationBean : arrayList) {
            if (retailerLocationBean.getId().equalsIgnoreCase(id)) {
                location = new Location("retailer location");
                location.setLatitude(retailerLocationBean.getLat());
                location.setLongitude(retailerLocationBean.getLon());
            }
        }

        if (location == null) {
            location = new Location("retailer location");
            location.setLongitude(0.0);
            location.setLatitude(0.0);
        }
        return location;
    }

    public static void addRetailerLocation(Context context, String id, double lat, double lng) {
        ArrayList<RetailerLocationBean> arrayList = getRetailerLocationArray(context);
        if (arrayList != null) {
            RetailerLocationBean bean = new RetailerLocationBean();
            bean.setId(id);
            bean.setLat(lat);
            bean.setLon(lng);
            arrayList.add(bean);

            setRetailerLocation(context, arrayList);
        }
    }

    public static boolean checkIfRetailerExists(String id, ArrayList<RetailerLocationBean> arrayList) {
        for (RetailerLocationBean retailerLocationBean : arrayList) {
            if (id.equalsIgnoreCase(retailerLocationBean.getId()))
                return true;
        }
        return false;
    }
}
