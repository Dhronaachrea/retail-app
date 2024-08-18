package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.UserDetailResponseBean;
import com.skilrock.retailapp.models.rms.UserRegistrationRequestBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<UserDetailResponseBean> updateUserLiveData = new MutableLiveData<>();

    public MutableLiveData<UserDetailResponseBean> getUserDetailLiveData(){
        return updateUserLiveData;
    }

    public void userDetailApi(String url, String token, UserRegistrationRequestBean userRegistrationRequestBean) {
        Call<UserDetailResponseBean> reportCall = apiClient.updateUser(url, token, userRegistrationRequestBean);

        Log.d("log", "Update User Request: " + reportCall.request().toString());

        reportCall.enqueue(new Callback<UserDetailResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<UserDetailResponseBean> call, @NonNull Response<UserDetailResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Update User Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            UserDetailResponseBean model;
                            model= gson.fromJson(errorResponse, UserDetailResponseBean.class);
                            updateUserLiveData.postValue(model);
                        } catch (IOException e) {
                            updateUserLiveData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    updateUserLiveData.postValue(null);
                    return;
                }

                Log.i("log", "Update User Response: " + new Gson().toJson(response.body()));
                updateUserLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserDetailResponseBean> call, @NonNull Throwable throwable) {
                //Log.e("log", "OLA Report failed: " + throwable.toString());
                throwable.printStackTrace();
                updateUserLiveData.postValue(null);
            }
        });
    }
}
