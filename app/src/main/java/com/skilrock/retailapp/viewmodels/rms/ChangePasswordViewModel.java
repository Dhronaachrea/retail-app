package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.ChangePasswordRequestBean;
import com.skilrock.retailapp.models.rms.ChangePasswordResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<ChangePasswordResponseBean> liveDataChangePassword = new MutableLiveData<>();

    public MutableLiveData<ChangePasswordResponseBean> getLiveDataChangePassword(){
        return liveDataChangePassword;
    }

    public void changePasswordApi(String token, String url, ChangePasswordRequestBean model) {
        Call<ChangePasswordResponseBean> changePasswordCall = apiClient.changePassword(token, url, model);

        Log.d("log", "ChangePassword Request: " + changePasswordCall.request().toString());

        changePasswordCall.enqueue(new Callback<ChangePasswordResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<ChangePasswordResponseBean> call, @NonNull Response<ChangePasswordResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "ChangePassword API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ChangePasswordResponseBean model;
                            model= gson.fromJson(errorResponse, ChangePasswordResponseBean.class);
                            liveDataChangePassword.postValue(model);
                        } catch (IOException e) {
                            liveDataChangePassword.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataChangePassword.postValue(null);
                    return;
                }

                Log.i("log", "ChangePassword Response: " + new Gson().toJson(response.body()));
                liveDataChangePassword.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ChangePasswordResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "ChangePassword failed: " + throwable.toString());
                liveDataChangePassword.postValue(null);
            }
        });

    }

}
