package com.skilrock.retailapp.utils;

import android.content.Context;

import com.skilrock.retailapp.models.rms.ConfigurationResponseBean;

public class ConfigData {

    private static ConfigData INSTANCE = null;
    private ConfigurationResponseBean.Data configBean;

    private ConfigData() {

    }

    public static synchronized ConfigData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigData();
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
    }

    public void setConfigData(Context context, ConfigurationResponseBean.Data configData) {
        configBean = configData;
        SharedPrefUtil.putString(context, SharedPrefUtil.DEFAULT_DATETIME_FORMAT, configData.getDEFAULTDATETIMEFORMAT());
        SharedPrefUtil.putString(context, SharedPrefUtil.DEFAULT_DATE_FORMAT, configData.getDEFAULTDATEFORMAT());
        SharedPrefUtil.putString(context, SharedPrefUtil.COUNTRY_CODE, configData.getCOUNTRYCODE());
    }

    public void setConfigData(ConfigurationResponseBean.Data configData) {
        configBean = configData;
    }

    public ConfigurationResponseBean.Data getConfigData() {
        return configBean;
    }
}
