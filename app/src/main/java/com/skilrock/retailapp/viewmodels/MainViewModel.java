package com.skilrock.retailapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.rms.ConfigurationResponseBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.rms.LogoutResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<LogoutResponseBean> logoutLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginBean> liveDataBalance = new MutableLiveData<>();
    private MutableLiveData<ConfigurationResponseBean> liveDataConfig = new MutableLiveData<>();

    public MutableLiveData<ConfigurationResponseBean> getLiveDataConfig() { return liveDataConfig; }

    public MutableLiveData<LogoutResponseBean> getLogoutLiveData() {
        return logoutLiveData;
    }
    public MutableLiveData<LoginBean> getLiveDataBalance() { return liveDataBalance; }

    public void callLogoutApi(String url) {
        final Call<LogoutResponseBean> logoutCall = client.logout(PlayerData.getInstance().getToken(), url);

        Log.d("log", "Logout Request: " + logoutCall.request().toString());

        logoutCall.enqueue(new Callback<LogoutResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<LogoutResponseBean> call, @NonNull Response<LogoutResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Logout API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            LogoutResponseBean model;
                            model = gson.fromJson(errorResponse, LogoutResponseBean.class);
                            logoutLiveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            logoutLiveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    logoutLiveData.postValue(null);
                    return;
                }

                Log.i("log", "Logout Response: " + new Gson().toJson(response.body()));
                logoutLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LogoutResponseBean> call, @NonNull Throwable t) {
                Log.e("log", "Logout API failed: " + t.toString());
                logoutLiveData.postValue(null);
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
