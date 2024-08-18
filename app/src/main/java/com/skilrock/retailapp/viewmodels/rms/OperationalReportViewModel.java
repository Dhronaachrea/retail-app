package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.ola.OperationalReportResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationalReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OperationalReportResponseBean> liveDataOperationalReport = new MutableLiveData<>();

    public MutableLiveData<OperationalReportResponseBean> getLiveDataOperationalReport() {
        return liveDataOperationalReport;
    }

    public void callOperationalReportApi(String token, String url, String startDate, String endDate){

        final Call<OperationalReportResponseBean> operationalReportCall = apiClient.getOperationalReports(url, token, AppConstants.APP_TYPE, startDate, endDate,
                PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId(), "en", "PPL",PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());

        Log.d("log", "OperationalReport Request : " + operationalReportCall.request().toString());

        operationalReportCall.enqueue(new Callback<OperationalReportResponseBean>() {
            @Override
            public void onResponse(Call<OperationalReportResponseBean> call, Response<OperationalReportResponseBean> response) {
                Log.d("TAg", "response: " + response);
                if(!response.isSuccessful() || response.body() == null){
                    if(response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log","Payment Report API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OperationalReportResponseBean model;
                            model = gson.fromJson(errorResponse, OperationalReportResponseBean.class);
                            liveDataOperationalReport.postValue(model);
                        } catch (IOException e) {
                            liveDataOperationalReport.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataOperationalReport.postValue(null);
                    return;
                }
                Log.i("log", "Balance Report API Response : " + new Gson().toJson(response.body()));
                liveDataOperationalReport.postValue(response.body());
            }

            @Override
            public void onFailure(Call<OperationalReportResponseBean> call, Throwable throwable) {
                Log.e("log", "Payment Report API failed: " + throwable.toString());
                liveDataOperationalReport.postValue(null);
            }
        });

    }
}
