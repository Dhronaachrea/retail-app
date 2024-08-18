package com.skilrock.retailapp.viewmodels.scratch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.scratch.ScratchReportBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScratchReportViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);
    private MutableLiveData<ScratchReportBean> liveDataResponse = new MutableLiveData<>();

    public MutableLiveData<ScratchReportBean> getLiveDataResponse() {
        return liveDataResponse;
    }

    public void callReportApi(UrlBean urlBean) {
        Call<ScratchReportBean> callScratchReportApi = client.getScratchResport(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), PlayerData.getInstance().getUsername(),
                PlayerData.getInstance().getToken().split(" ")[1]);

        Log.d("log", "Scratch Report Request: " + callScratchReportApi.request().toString());

        callScratchReportApi.enqueue(new Callback<ScratchReportBean>() {
            @Override
            public void onResponse(@NonNull Call<ScratchReportBean> call, @NonNull Response<ScratchReportBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Scratch Report Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ScratchReportBean model;
                            model= gson.fromJson(errorResponse, ScratchReportBean.class);
                            liveDataResponse.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataResponse.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataResponse.postValue(null);
                    return;
                }

                Log.i("log", "Scratch Report: " + new Gson().toJson(response.body()));
                liveDataResponse.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ScratchReportBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Scratch Report failed: " + throwable.toString());
                liveDataResponse.postValue(null);
            }
        });
    }

}
