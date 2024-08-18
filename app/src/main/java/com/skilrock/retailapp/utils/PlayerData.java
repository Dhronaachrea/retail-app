package com.skilrock.retailapp.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.rms.LoginResponseData;

public class PlayerData {

    private static PlayerData INSTANCE = null;
    private LoginBean loginData;
    private String password;

    private PlayerData() {

    }

    public static synchronized PlayerData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerData();
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
    }

    public void setLoginData(Context context, LoginBean loginBean) {
        loginData = loginBean;

        SharedPrefUtil.putString(context, SharedPrefUtil.AUTH_TOKEN, loginBean.getToken());
        SharedPrefUtil.putString(context, SharedPrefUtil.USER_ID, loginBean.getResponseData().getData().getUserId());
        SharedPrefUtil.putString(context, SharedPrefUtil.USERNAME, loginBean.getResponseData().getData().getUsername());
        SharedPrefUtil.putString(context, SharedPrefUtil.LOGIN_BEAN, new Gson().toJson(loginBean));
    }

    public void setLoginData(Context context, LoginBean loginBean,String password) {
        loginData = loginBean;
        this.password=password;
        SharedPrefUtil.putString(context, SharedPrefUtil.AUTH_TOKEN, loginBean.getToken());
        SharedPrefUtil.putString(context, SharedPrefUtil.USER_ID, loginBean.getResponseData().getData().getUserId());
        SharedPrefUtil.putString(context, SharedPrefUtil.USERNAME, loginBean.getResponseData().getData().getUsername());
        SharedPrefUtil.putString(context, SharedPrefUtil.LOGIN_BEAN, new Gson().toJson(loginBean));
        SharedPrefUtil.putString(context,SharedPrefUtil.PASSWORD,password);
    }

    public void setLoginData(LoginBean loginBean) {
        Log.d("TAg", "loginBean: " + loginBean);
        loginData = loginBean;
    }

    public LoginBean getLoginData() {
        return loginData;
    }

    public String getDomain() {
        return loginData.getResponseData().getData().getOrgCode() + "_" +
                loginData.getResponseData().getData().getOrgName();
    }

    public String getToken() {
        return loginData.getToken();
    }

    public String getUsername() {
        return loginData.getResponseData().getData().getUsername();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrgBalance() {
        return loginData.getResponseData().getData().getBalance();
    }

    public String getUserBalance() {
        return loginData.getResponseData().getData().getUserBalance();
    }

    public long getOrgId() { return  loginData.getResponseData().getData().getOrgId(); }

    public void setUserBalance(String amount) {
        loginData.getResponseData().getData().setBalance(amount);
    }

    public void setRawUserBalance(Double amount) {
        loginData.getResponseData().getData().setRawUserBalance(amount);
    }

    public String getCreditLimit() {
        return loginData.getResponseData().getData().getCreditLimit();
    }

    public String getMobileNumber() {
        return loginData.getResponseData().getData().getMobileNumber();
    }

    public String getMobileCode() {
        return loginData.getResponseData().getData().getMobileCode();
    }

    public String getUserId() {
        return loginData.getResponseData().getData().getUserId();
    }

    public String getUserStatus() {
        return loginData.getResponseData().getData().getUserStatus();
    }

    public String getIsAffiliate (){
        return loginData.getResponseData().getData().getIsAffiliate();
    }
}
