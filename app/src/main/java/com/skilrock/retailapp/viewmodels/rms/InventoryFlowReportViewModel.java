package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.ola.InventoryFlowReportResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryFlowReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<InventoryFlowReportResponseBean> liveDataInventoryFlowReport = new MutableLiveData<>();

    public MutableLiveData<InventoryFlowReportResponseBean> getLiveDataInventoryFlowReport() {
        return liveDataInventoryFlowReport;
    }

    public void callInventoryFlowReportApi(UrlBean urlBean, String startDate, String endDate){

        final Call<InventoryFlowReportResponseBean> inventoryFlowReportCall = apiClient.getInventoryFlowReport(urlBean.getUrl(),urlBean.getClientId(), urlBean.getClientSecret(),urlBean.getContentType(), startDate, endDate, PlayerData.getInstance().getUsername(), PlayerData.getInstance().getToken().split(" ")[1]);

        Log.d("log", "InventoryFlowReport Request : " + inventoryFlowReportCall.request().toString());

        inventoryFlowReportCall.enqueue(new Callback<InventoryFlowReportResponseBean>() {
            @Override
            public void onResponse(Call<InventoryFlowReportResponseBean> call, Response<InventoryFlowReportResponseBean> response) {
                Log.d("TAg", "response: " + response);
                if(!response.isSuccessful() || response.body() == null) {
                    if(response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log","Inventory Report API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            InventoryFlowReportResponseBean model;
                            model = gson.fromJson(errorResponse, InventoryFlowReportResponseBean.class);
                            liveDataInventoryFlowReport.postValue(model);
                        } catch (IOException e) {
                            liveDataInventoryFlowReport.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataInventoryFlowReport.postValue(null);
                    return;
                }
                Log.i("log", "Inventory Report API Response : " + new Gson().toJson(response.body()));
                liveDataInventoryFlowReport.postValue(response.body());
            }

            @Override
            public void onFailure(Call<InventoryFlowReportResponseBean> call, Throwable throwable) {
                Log.e("log", "Inventory Report API failed: " + throwable.toString());
                liveDataInventoryFlowReport.postValue(null);
            }
        });

    }
}
