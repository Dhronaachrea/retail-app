package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.ola.OlaOtpBean;
import com.skilrock.retailapp.models.ola.OlaWithdrawalRequestBean;
import com.skilrock.retailapp.models.ola.OlaWithdrawalResponseBean;
import com.skilrock.retailapp.models.ResponseCodeMessageBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaWithdrawalViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<ResponseCodeMessageBean> liveDataOtp = new MutableLiveData<>();
    private MutableLiveData<ResponseCodeMessageBean> liveDataResendOtp = new MutableLiveData<>();
    private MutableLiveData<OlaWithdrawalResponseBean> liveDataWithdrawal = new MutableLiveData<>();
    private MutableLiveData<LoginBean> liveDataBalance = new MutableLiveData<>();

    public MutableLiveData<ResponseCodeMessageBean> getOtpLiveData() {
        return liveDataOtp;
    }
    public MutableLiveData<LoginBean> getLiveDataBalance() { return liveDataBalance; }

    public MutableLiveData<ResponseCodeMessageBean> getResendOtpLiveData() {
        return liveDataResendOtp;
    }

    public MutableLiveData<OlaWithdrawalResponseBean> getLiveDataWithdrawal() {
        return liveDataWithdrawal;
    }

    public void callOtp(UrlOlaBean url, OlaOtpBean bean) {

        final Call<ResponseCodeMessageBean> otpCall = client.olaRegistrationOtp(url.getUrl(), url.getUserName(), url.getPassword(), url.getContentType(), url.getAccept(), bean);

        Log.d("log", "Ola OTP Request: " + otpCall.request().toString());

        otpCall.enqueue(new Callback<ResponseCodeMessageBean>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Response<ResponseCodeMessageBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola OTP API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ResponseCodeMessageBean model;
                            model= gson.fromJson(errorResponse, ResponseCodeMessageBean.class);
                            liveDataOtp.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataOtp.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataOtp.postValue(null);
                    return;
                }

                Log.i("log", "Ola OTP API Response: " + new Gson().toJson(response.body()));
                liveDataOtp.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola OTP API failed: " + throwable.toString());
                liveDataOtp.postValue(null);
            }
        });
    }

    public void callResendOtp(UrlOlaBean url, OlaOtpBean bean) {

        final Call<ResponseCodeMessageBean> otpCall = client.olaRegistrationOtp(url.getUrl(), url.getUserName(), url.getPassword(), url.getContentType(), url.getAccept(), bean);

        Log.d("log", "Ola Resend OTP Request: " + otpCall.request().toString());

        otpCall.enqueue(new Callback<ResponseCodeMessageBean>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Response<ResponseCodeMessageBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Resend OTP API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ResponseCodeMessageBean model;
                            model= gson.fromJson(errorResponse, ResponseCodeMessageBean.class);
                            liveDataResendOtp.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataResendOtp.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataResendOtp.postValue(null);
                    return;
                }

                Log.i("log", "Ola Resend OTP API Response: " + new Gson().toJson(response.body()));
                liveDataResendOtp.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Resend OTP API failed: " + throwable.toString());
                liveDataResendOtp.postValue(null);
            }
        });
    }

    public void callOlaWithdrawal(UrlOlaBean url, OlaWithdrawalRequestBean bean) {

        final Call<OlaWithdrawalResponseBean> withdrawalCall = client.postOlaWithdrawal(url.getUrl(), url.getUserName(), url.getPassword(), url.getContentType(), url.getAccept(), bean);

        Log.d("log", "Ola Withdrawal Request: " + withdrawalCall.request().toString());

        withdrawalCall.enqueue(new Callback<OlaWithdrawalResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaWithdrawalResponseBean> call, @NonNull Response<OlaWithdrawalResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Withdrawal API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaWithdrawalResponseBean model;
                            model= gson.fromJson(errorResponse, OlaWithdrawalResponseBean.class);
                            liveDataWithdrawal.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataWithdrawal.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataWithdrawal.postValue(null);
                    return;
                }

                Log.i("log", "Ola Withdrawal API Response: " + new Gson().toJson(response.body()));
                liveDataWithdrawal.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaWithdrawalResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Withdrawal API failed: " + throwable.toString());
                liveDataWithdrawal.postValue(null);
            }
        });
    }

    public void getUpdatedBalance(final String authToken) {
        Call<LoginBean> loginCall = client.getLogin(authToken);

        Log.d("log", "Login Request: " + loginCall.request().toString());

        loginCall.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(@NonNull Call<LoginBean> call, @NonNull Response<LoginBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Login API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            LoginBean model;
                            model= gson.fromJson(errorResponse, LoginBean.class);
                            liveDataBalance.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataBalance.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataBalance.postValue(null);
                    return;
                }

                Log.i("log", "Login API Response: " + new Gson().toJson(response.body()));
                if (response.body().getResponseCode() == 0 && response.body().getResponseData().getStatusCode() == 0)
                    response.body().setToken(authToken);
                liveDataBalance.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LoginBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Login API failed: " + throwable.toString());
                liveDataBalance.postValue(null);
            }
        });
    }

}
