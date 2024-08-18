package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.ola.BalanceReportResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<BalanceReportResponseBean> liveDataBalanceReport = new MutableLiveData<>();

    public MutableLiveData<BalanceReportResponseBean> getLiveDataBalanceReport() {
        return liveDataBalanceReport;
    }

    public void callBalanceReportApi(String token, String url, String startDate, String endDate){

        final Call<BalanceReportResponseBean> balanceReportCall = apiClient.getBalanceReports(url, token, AppConstants.APP_TYPE, startDate, endDate,
                PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId(), "en", PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId());

        Log.d("log", "BalanceReport Request : " + balanceReportCall.request().toString());

        balanceReportCall.enqueue(new Callback<BalanceReportResponseBean>() {
            @Override
            public void onResponse(Call<BalanceReportResponseBean> call, Response<BalanceReportResponseBean> response) {
                Log.d("TAg", "response: " + response);
                if(!response.isSuccessful() || response.body() == null){
                    if(response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log","Payment Report API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            BalanceReportResponseBean model;
                            model = gson.fromJson(errorResponse, BalanceReportResponseBean.class);
                            liveDataBalanceReport.postValue(model);
                        } catch (IOException e) {
                            liveDataBalanceReport.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataBalanceReport.postValue(null);
                    return;
                }
                Log.i("log", "Balance Report API Response : " + new Gson().toJson(response.body()));
                liveDataBalanceReport.postValue(response.body());
            }

            @Override
            public void onFailure(Call<BalanceReportResponseBean> call, Throwable throwable) {
                Log.e("log", "Payment Report API failed: " + throwable.toString());
                liveDataBalanceReport.postValue(null);
            }
        });

    }
}
