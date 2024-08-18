package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.OlaNetGamingExecutionResponseBean;
import com.skilrock.retailapp.models.SimpleRmsResponseBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.models.ola.OlaNetGamingResponseBean;
import com.skilrock.retailapp.models.ola.OlaPlayerTransactionReportRequestBean;
import com.skilrock.retailapp.models.ola.SettleTransactionRequestBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaNetGamingViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OlaNetGamingResponseBean> liveData = new MutableLiveData<>();
    private MutableLiveData<OlaNetGamingResponseBean> liveNetGamingReport = new MutableLiveData<>();
    private MutableLiveData<SimpleRmsResponseBean> liveDataSettleTransaction = new MutableLiveData<>();
    private MutableLiveData<OlaNetGamingExecutionResponseBean> liveDataExecution = new MutableLiveData<>();

    public MutableLiveData<OlaNetGamingResponseBean> getLiveData() {
        return liveData;
    }

    public MutableLiveData<OlaNetGamingResponseBean> getLiveDataLoadMore() {
        return liveNetGamingReport;
    }
    public MutableLiveData<OlaNetGamingExecutionResponseBean> getLiveDataDataExecution() {
        return liveDataExecution;
    }

    public MutableLiveData<SimpleRmsResponseBean> getLiveDataSettleTransaction() {
        return liveDataSettleTransaction;
    }


    public void callSettleTransactionApi(OlaPlayerTransactionReportRequestBean model, SettleTransactionRequestBean settleTransactionRequestBean) {

        final Call<SimpleRmsResponseBean> ledgerCall = client.settleTransaction(model.getUrl(), model.getUserName(), model.getPassword(),
                model.getContentType(), model.getAccept(), settleTransactionRequestBean);

        Log.d("log", "Ola Settle Transaction Request: " + ledgerCall.request().toString());

        ledgerCall.enqueue(new Callback<SimpleRmsResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Response<SimpleRmsResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Settle Transaction API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SimpleRmsResponseBean model;
                            model = gson.fromJson(errorResponse, SimpleRmsResponseBean.class);
                            liveDataSettleTransaction.postValue(model);
                        } catch (IOException e) {
                            liveDataSettleTransaction.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataSettleTransaction.postValue(null);
                    return;
                }

                Log.i("log", "Ola Settle Transaction API Response: " + new Gson().toJson(response.body()));
                liveDataSettleTransaction.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Settle Transaction API failed: " + throwable.toString());
                liveDataSettleTransaction.postValue(null);
            }
        });
    }

    public void callGetNetGamingReport(OlaPlayerTransactionReportRequestBean model) {

        final Call<OlaNetGamingResponseBean> ledgerCall = client.getOlaNetGamingDetails(model.getUrl(), model.getUserName(), model.getPassword(),
                model.getContentType(), model.getAccept(), model.getTxnType(), model.getFromDate(), model.getToDate(), model.getPageIndex(),
                model.getPageSize(), model.getPlayerDomainCode(), model.getPlayerUserName(), model.getRetailDomainCode(), model.getRetOrgID());

        Log.d("log", "Ola Ledger Report Request (Load More): " + ledgerCall.request().toString());

        ledgerCall.enqueue(new Callback<OlaNetGamingResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaNetGamingResponseBean> call, @NonNull Response<OlaNetGamingResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Ledger Report API Failed (Load More): " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaNetGamingResponseBean model;
                            model = gson.fromJson(errorResponse, OlaNetGamingResponseBean.class);
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

                Log.i("log", "Ola Ledger Report API Response (Load More): " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaNetGamingResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Ledger Report API failed (Load More): " + throwable.toString());
                liveData.postValue(null);
            }
        });
    }

    public void callGetNetGamingExecutionDates(UrlOlaBean urlBean, int range) {
        final Call<OlaNetGamingExecutionResponseBean> ledgerCall = client.getOlaNetGamingRangeDetail(urlBean.getUrl(), urlBean.getUserName(), urlBean.getPassword(),
                urlBean.getContentType(), urlBean.getAccept(),range , (int) PlayerData.getInstance().getOrgId()
        );

        Log.d("log", "Ola date range " + ledgerCall.request().toString());

        ledgerCall.enqueue(new Callback<OlaNetGamingExecutionResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaNetGamingExecutionResponseBean> call, @NonNull Response<OlaNetGamingExecutionResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola date range API Failed  " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaNetGamingExecutionResponseBean model;
                            model = gson.fromJson(errorResponse, OlaNetGamingExecutionResponseBean.class);
                            liveDataExecution.postValue(model);
                        } catch (IOException e) {
                            liveDataExecution.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveData.postValue(null);
                    return;
                }

                Log.i("log", "Ola date range Response  " + new Gson().toJson(response.body()));
                liveDataExecution.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaNetGamingExecutionResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola date range API failed " + throwable.toString());
                liveDataExecution.postValue(null);
            }
        });
    }
}
