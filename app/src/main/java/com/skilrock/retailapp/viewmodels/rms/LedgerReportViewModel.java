package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.LedgerReportResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LedgerReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<LedgerReportResponseBean> liveDataLedgerReport = new MutableLiveData<>();

    public MutableLiveData<LedgerReportResponseBean> getLiveDataPaymentReport() {
        return liveDataLedgerReport;
    }

    public void callLedgerReportApi(String token, String url, long orgId, String startDate, String endDate, String lang, String appType){

        final Call<LedgerReportResponseBean> paymentReportCall = apiClient.getLedgerReport(url, token, orgId, startDate, endDate, lang, appType);

        Log.d("log", "LedgerRepor Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<LedgerReportResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<LedgerReportResponseBean> call, @NonNull Response<LedgerReportResponseBean> response) {
                if(!response.isSuccessful() || response.body() == null){
                    if(response.errorBody() != null){
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log","LedgerRepor Report API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            LedgerReportResponseBean model;
                            model = gson.fromJson(errorResponse, LedgerReportResponseBean.class);
                            liveDataLedgerReport.postValue(model);
                        } catch (IOException e) {
                            liveDataLedgerReport.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataLedgerReport.postValue(null);
                    return;
                }
                Log.i("log", "LedgerRepor API Response : " + new Gson().toJson(response.body()));
                liveDataLedgerReport.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LedgerReportResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "LedgerRepor API failed: " + throwable.toString());
                liveDataLedgerReport.postValue(null);
            }
        });
    }
}
