package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.SettlementDetailResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettlementDetailViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<SettlementDetailResponseBean> liveDataSettlementDetail = new MutableLiveData<>();

    public MutableLiveData<SettlementDetailResponseBean> getLiveDataSettlementList() {
        return liveDataSettlementDetail;
    }

    public void callGetSettlementDetailApi(String token, String url, String userId, String languageCode, String appType) {
        final Call<SettlementDetailResponseBean> paymentReportCall = apiClient.getSettlementDetails(url, token, userId, languageCode, appType);

        Log.d("log", "SettlementDetail Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<SettlementDetailResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SettlementDetailResponseBean> call, @NonNull Response<SettlementDetailResponseBean> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "SettlementDetail API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SettlementDetailResponseBean model;
                            model = gson.fromJson(errorResponse, SettlementDetailResponseBean.class);
                            liveDataSettlementDetail.postValue(model);
                        } catch (IOException e) {
                            liveDataSettlementDetail.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataSettlementDetail.postValue(null);
                    return;
                }
                Log.i("log", "SettlementDetail API Response : " + new Gson().toJson(response.body()));
                liveDataSettlementDetail.postValue(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<SettlementDetailResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SettlementDetail API failed: " + throwable.toString());
                liveDataSettlementDetail.postValue(null);
            }
        });
    }
}
