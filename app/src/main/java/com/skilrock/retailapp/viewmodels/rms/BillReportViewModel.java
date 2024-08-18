package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.BillPaymentsResponseBean;
import com.skilrock.retailapp.models.rms.BillReportRequestBean;
import com.skilrock.retailapp.models.rms.BillReportResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<BillReportResponseBean> liveData = new MutableLiveData<>();
    private MutableLiveData<BillPaymentsResponseBean> liveDataBillPayments = new MutableLiveData<>();

    public MutableLiveData<BillReportResponseBean> getLiveData() {
        return liveData;
    }

    public MutableLiveData<BillPaymentsResponseBean> getLiveDataBillPayments() {
        return liveDataBillPayments;
    }

    public void callBillReportApi(String token, String url, BillReportRequestBean model){

        final Call<BillReportResponseBean> billReportCall = apiClient.postBillReport(url, token, model);

        Log.d("log", "BillReport Request : " + billReportCall.request().toString());

        billReportCall.enqueue(new Callback<BillReportResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<BillReportResponseBean> call, @NonNull Response<BillReportResponseBean> response) {
                if(!response.isSuccessful() || response.body() == null){
                    if(response.errorBody() != null){
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log","BillReport API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            BillReportResponseBean model;
                            model = gson.fromJson(errorResponse, BillReportResponseBean.class);
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
                Log.i("log", "BillReport API Response : " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<BillReportResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "BillReport API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });

    }

    public void callBillPaymentApi(String url, long orgId, long domainId, long billId) {

        final Call<BillPaymentsResponseBean> billPaymentCall = apiClient.getBillPayments(url, PlayerData.getInstance().getToken(), orgId, domainId, billId);

        Log.d("log", "Bill Payment Request : " + billPaymentCall.request().toString());

        billPaymentCall.enqueue(new Callback<BillPaymentsResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<BillPaymentsResponseBean> call, @NonNull Response<BillPaymentsResponseBean> response) {
                if(!response.isSuccessful() || response.body() == null){
                    if(response.errorBody() != null){
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log","Bill Payment API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            BillPaymentsResponseBean model;
                            model = gson.fromJson(errorResponse, BillPaymentsResponseBean.class);
                            liveDataBillPayments.postValue(model);
                        } catch (IOException e) {
                            liveDataBillPayments.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataBillPayments.postValue(null);
                    return;
                }
                Log.i("log", "Bill Payment API Response : " + new Gson().toJson(response.body()));
                liveDataBillPayments.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<BillPaymentsResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Bill Payment API failed: " + throwable.toString());
                liveDataBillPayments.postValue(null);
            }
        });

    }

}
