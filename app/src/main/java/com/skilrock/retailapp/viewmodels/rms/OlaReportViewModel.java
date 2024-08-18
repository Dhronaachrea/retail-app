package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.OlaReportResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OlaReportResponseBean> liveData = new MutableLiveData<>();

    public MutableLiveData<OlaReportResponseBean> getLiveData(){
        return liveData;
    }

    public void olaReportApi(String url, String startDate, String endDate) {
        Call<OlaReportResponseBean> reportCall = apiClient.getOlaReport(url, PlayerData.getInstance().getToken(), PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId(), startDate, endDate, AppConstants.ORG_TYPE_CODE);

        Log.d("log", "OLA Report Request: " + reportCall.request().toString());

        reportCall.enqueue(new Callback<OlaReportResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaReportResponseBean> call, @NonNull Response<OlaReportResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "OLA Report API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaReportResponseBean model;
                            model= gson.fromJson(errorResponse, OlaReportResponseBean.class);
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

                Log.i("log", "OLA Report Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaReportResponseBean> call, @NonNull Throwable throwable) {
                //Log.e("log", "OLA Report failed: " + throwable.toString());
                throwable.printStackTrace();
                liveData.postValue(null);
            }
        });
    }
}
