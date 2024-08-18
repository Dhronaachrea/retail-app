package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.SaleReportResponseBean;
import com.skilrock.retailapp.models.rms.SaleWinningReportRequestBean;
import com.skilrock.retailapp.models.rms.ServiceListResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleWinningReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<ServiceListResponseBean> liveServiceList = new MutableLiveData<>();

    private MutableLiveData<SaleReportResponseBean> liveDataSaleReport = new MutableLiveData<>();

    public MutableLiveData<SaleReportResponseBean> getLiveDataSaleReport() {
        return liveDataSaleReport;
    }

    public MutableLiveData<ServiceListResponseBean> getLiveServiceList() {
        return liveServiceList;
    }

    public void callSaleReportApi(String token, String url, SaleWinningReportRequestBean reportRequestBean){

        final Call<SaleReportResponseBean> paymentReportCall = apiClient.getSaleWinningReport(url, token, reportRequestBean);

        Log.d("log", "SaleReport Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<SaleReportResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SaleReportResponseBean> call, @NonNull Response<SaleReportResponseBean> response) {
                if(!response.isSuccessful() || response.body() == null){
                    if(response.errorBody() != null){
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log","SaleReport Report API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SaleReportResponseBean model;
                            model = gson.fromJson(errorResponse, SaleReportResponseBean.class);
                            liveDataSaleReport.postValue(model);
                        } catch (IOException e) {
                            liveDataSaleReport.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataSaleReport.postValue(null);
                    return;
                }
                Log.i("log", "SaleReport API Response : " + new Gson().toJson(response.body()));
                liveDataSaleReport.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SaleReportResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SaleReport API failed: " + throwable.toString());
                liveDataSaleReport.postValue(null);
            }
        });
    }

    public void callGetServiceListApi(String token, String url) {
        final Call<ServiceListResponseBean> paymentReportCall = apiClient.getServiceList(url, token);

        Log.d("log", "ServiceList Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<ServiceListResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<ServiceListResponseBean> call, @NonNull Response<ServiceListResponseBean> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "ServiceList API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ServiceListResponseBean model;
                            model = gson.fromJson(errorResponse, ServiceListResponseBean.class);
                            liveServiceList.postValue(model);
                        } catch (IOException e) {
                            liveServiceList.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveServiceList.postValue(null);
                    return;
                }
                Log.i("log", "ServiceList API Response : " + new Gson().toJson(response.body()));
                liveServiceList.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ServiceListResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "ServiceList API failed: " + throwable.toString());
                liveServiceList.postValue(null);
            }
        });
    }

}
