package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.rms.SummarizedReportResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummarizedReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<SummarizedReportResponseBean> liveDataLedgerReport = new MutableLiveData<>();

    public MutableLiveData<SummarizedReportResponseBean> getLiveDataPaymentReport() {
        return liveDataLedgerReport;
    }

    public void callSummarizedLedgerReportApi(String token, String url, long orgId, String startDate, String endDate, String lang, String appType, String type) {

        final Call<SummarizedReportResponseBean> paymentReportCall = apiClient.getSummarizedLedgerReport(url, token, orgId, startDate, endDate, type, lang, appType);

        Log.d("log", "SummarizedLedgerReport Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<SummarizedReportResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SummarizedReportResponseBean> call, @NonNull Response<SummarizedReportResponseBean> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "SummarizedLedgerReport API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SummarizedReportResponseBean model;
                            model = gson.fromJson(errorResponse, SummarizedReportResponseBean.class);
                            liveDataLedgerReport.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataLedgerReport.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataLedgerReport.postValue(null);
                    return;
                }
                Log.i("log", "SummarizedLedgerReport API Response : " + new Gson().toJson(response.body()));
                liveDataLedgerReport.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SummarizedReportResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SummarizedLedgerReport API failed: " + throwable.toString());
                liveDataLedgerReport.postValue(null);
            }
        });
    }
}
