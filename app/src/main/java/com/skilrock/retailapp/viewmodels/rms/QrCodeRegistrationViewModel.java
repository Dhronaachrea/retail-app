package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.QrCodeRegistrationRequestBean;
import com.skilrock.retailapp.models.rms.QrCodeRegistrationResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrCodeRegistrationViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<QrCodeRegistrationResponseBean> liveData = new MutableLiveData<>();

    public MutableLiveData<QrCodeRegistrationResponseBean> getLiveData(){
        return liveData;
    }

    public void callRegisterDeviceApi(String url, QrCodeRegistrationRequestBean model) {
        Call<QrCodeRegistrationResponseBean> call = apiClient.postQrCodeRegistration(url, PlayerData.getInstance().getToken(), model);

        Log.d("log", "QR Code Registration Request: " + call.request().toString());

        call.enqueue(new Callback<QrCodeRegistrationResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<QrCodeRegistrationResponseBean> call, @NonNull Response<QrCodeRegistrationResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "QR Code Registration API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            QrCodeRegistrationResponseBean model;
                            model= gson.fromJson(errorResponse, QrCodeRegistrationResponseBean.class);
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

                Log.i("log", "QR Code Registration API Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<QrCodeRegistrationResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "QR Code Registration API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });

    }

}
