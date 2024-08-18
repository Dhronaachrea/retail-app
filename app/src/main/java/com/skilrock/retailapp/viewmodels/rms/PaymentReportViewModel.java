package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.PaymentReportRequestBean;
import com.skilrock.retailapp.models.ola.PaymentReportResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<PaymentReportResponseBean> liveDataPaymentReport = new MutableLiveData<>();

    public MutableLiveData<PaymentReportResponseBean> getLiveDataPaymentReport() {
        return liveDataPaymentReport;
    }

    public void callPaymentReportApi(String token, String url, PaymentReportRequestBean model){

        final Call<PaymentReportResponseBean> paymentReportCall = apiClient.getPaymentReports(url, token, model);

        Log.d("log", "PaymentReport Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<PaymentReportResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<PaymentReportResponseBean> call, @NonNull Response<PaymentReportResponseBean> response) {
                if(!response.isSuccessful() || response.body() == null){
                    if(response.errorBody() != null){
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log","Payment Report API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            PaymentReportResponseBean model;
                            model = gson.fromJson(errorResponse, PaymentReportResponseBean.class);
                            liveDataPaymentReport.postValue(model);
                        } catch (IOException e) {
                            liveDataPaymentReport.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataPaymentReport.postValue(null);
                    return;
                }
                Log.i("log", "Payment Report API Response : " + new Gson().toJson(response.body()));
                liveDataPaymentReport.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<PaymentReportResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Payment Report API failed: " + throwable.toString());
                liveDataPaymentReport.postValue(null);
            }
        });
    }
}
