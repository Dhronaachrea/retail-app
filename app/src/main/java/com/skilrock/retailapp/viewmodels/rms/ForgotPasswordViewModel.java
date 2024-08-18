package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.ForgotPasswordResetBean;
import com.skilrock.retailapp.models.rms.ForgotPasswordResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<ForgotPasswordResponseBean> liveDataForgotPasswordOtp = new MutableLiveData<>();
    private MutableLiveData<ForgotPasswordResponseBean> liveDataForgotPasswordResendOtp = new MutableLiveData<>();
    private MutableLiveData<ForgotPasswordResponseBean> liveDataForgotPasswordReset = new MutableLiveData<>();

    public MutableLiveData<ForgotPasswordResponseBean> getLiveDataForgotPasswordOtp() {
        return liveDataForgotPasswordOtp;
    }

    public MutableLiveData<ForgotPasswordResponseBean> getLiveDataForgotPasswordResendOtp() {
        return liveDataForgotPasswordResendOtp;
    }

    public MutableLiveData<ForgotPasswordResponseBean> getLiveDataForgotPasswordReset() {
        return liveDataForgotPasswordReset;
    }

    public void callOtp(String userName, String mobileNumber) {

        final Call<ForgotPasswordResponseBean> otpCall = client.forgotPasswordOtp(userName, mobileNumber);

        Log.d("log", "Forgot Password OTP Request: " + otpCall.request().toString());

        otpCall.enqueue(new Callback<ForgotPasswordResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<ForgotPasswordResponseBean> call, @NonNull Response<ForgotPasswordResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Forgot Password OTP API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ForgotPasswordResponseBean model;
                            model= gson.fromJson(errorResponse, ForgotPasswordResponseBean.class);
                            liveDataForgotPasswordOtp.postValue(model);
                        } catch (IOException e) {
                            liveDataForgotPasswordOtp.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataForgotPasswordOtp.postValue(null);
                    return;
                }

                Log.i("log", "Forgot Password OTP API Response: " + new Gson().toJson(response.body()));
                liveDataForgotPasswordOtp.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ForgotPasswordResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Forgot Password OTP API failed: " + throwable.toString());
                liveDataForgotPasswordOtp.postValue(null);
            }
        });
    }

    public void callResendOtp(String userName, String mobileNumber) {

        final Call<ForgotPasswordResponseBean> otpCall = client.forgotPasswordOtp(userName, mobileNumber);

        Log.d("log", "Forgot Password Resend OTP Request: " + otpCall.request().toString());

        otpCall.enqueue(new Callback<ForgotPasswordResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<ForgotPasswordResponseBean> call, @NonNull Response<ForgotPasswordResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Forgot Password Resend OTP API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ForgotPasswordResponseBean model;
                            model= gson.fromJson(errorResponse, ForgotPasswordResponseBean.class);
                            liveDataForgotPasswordResendOtp.postValue(model);
                        } catch (IOException e) {
                            liveDataForgotPasswordResendOtp.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataForgotPasswordResendOtp.postValue(null);
                    return;
                }

                Log.i("log", "Forgot Password Resend OTP API Response: " + new Gson().toJson(response.body()));
                liveDataForgotPasswordResendOtp.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ForgotPasswordResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Forgot Password Resend OTP API failed: " + throwable.toString());
                liveDataForgotPasswordResendOtp.postValue(null);
            }
        });
    }

    public void callReset(ForgotPasswordResetBean forgotPasswordResetBean) {

        final Call<ForgotPasswordResponseBean> resetCall = client.forgotPasswordReset(forgotPasswordResetBean);

        Log.d("log", "Forgot Password Reset Request: " + resetCall.request().toString());

        resetCall.enqueue(new Callback<ForgotPasswordResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<ForgotPasswordResponseBean> call, @NonNull Response<ForgotPasswordResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Forgot Password Reset API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ForgotPasswordResponseBean model;
                            model= gson.fromJson(errorResponse, ForgotPasswordResponseBean.class);
                            liveDataForgotPasswordReset.postValue(model);
                        } catch (IOException e) {
                            liveDataForgotPasswordReset.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataForgotPasswordReset.postValue(null);
                    return;
                }

                Log.i("log", "Forgot Password Reset API Response: " + new Gson().toJson(response.body()));
                liveDataForgotPasswordReset.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ForgotPasswordResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Forgot Password Reset API failed: " + throwable.toString());
                liveDataForgotPasswordReset.postValue(null);
            }
        });
    }

}
