package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.DeviceRegistrationBean;
import com.skilrock.retailapp.models.SimpleRmsResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceRegistrationViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<SimpleRmsResponseBean> liveData = new MutableLiveData<>();

    public MutableLiveData<SimpleRmsResponseBean> getLiveData(){
        return liveData;
    }

    public void callRegisterDeviceApi(String url, DeviceRegistrationBean model) {
        Call<SimpleRmsResponseBean> call = apiClient.postDeviceRegistration(url, PlayerData.getInstance().getToken(), model);

        Log.d("log", "Device Registration Request: " + call.request().toString());

        call.enqueue(new Callback<SimpleRmsResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Response<SimpleRmsResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Device Registration API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SimpleRmsResponseBean model;
                            model= gson.fromJson(errorResponse, SimpleRmsResponseBean.class);
                            liveData.postValue(model);
                        } catch (IOException e) {
                            liveData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveData.postValue(null);
                    return;
                }

                Log.i("log", "Device Registration API Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Device Registration API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });

    }

}
