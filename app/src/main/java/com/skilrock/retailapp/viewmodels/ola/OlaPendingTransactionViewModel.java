package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.SimpleRmsResponseBean;
import com.skilrock.retailapp.models.ola.OlaPendingTransactionResponseBean;
import com.skilrock.retailapp.models.ola.OlaPlayerTransactionReportRequestBean;
import com.skilrock.retailapp.models.ola.SettleTransactionRequestBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaPendingTransactionViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OlaPendingTransactionResponseBean> liveData = new MutableLiveData<>();
    private MutableLiveData<OlaPendingTransactionResponseBean> liveDataLoadMore = new MutableLiveData<>();
    private MutableLiveData<SimpleRmsResponseBean> liveDataSettleTransaction = new MutableLiveData<>();

    public MutableLiveData<OlaPendingTransactionResponseBean> getLiveData() {
        return liveData;
    }

    public MutableLiveData<OlaPendingTransactionResponseBean> getLiveDataLoadMore() {
        return liveDataLoadMore;
    }

    public MutableLiveData<SimpleRmsResponseBean> getLiveDataSettleTransaction() {
        return liveDataSettleTransaction;
    }

    public void callPendingTransactionApi(OlaPlayerTransactionReportRequestBean model) {

        final Call<OlaPendingTransactionResponseBean> ledgerCall = client.getOlaPlayerPendingTransaction(model.getUrl(), model.getUserName(), model.getPassword(),
                model.getContentType(), model.getAccept(), model.getTxnType(), model.getFromDate(), model.getToDate(), model.getPageIndex(),
                model.getPageSize(), model.getPlayerDomainCode(), model.getPlayerUserName(), model.getRetailDomainCode(), model.getRetOrgID());

        Log.d("log", "Ola Pending Transaction Request: " + ledgerCall.request().toString());

        ledgerCall.enqueue(new Callback<OlaPendingTransactionResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaPendingTransactionResponseBean> call, @NonNull Response<OlaPendingTransactionResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Pending Transaction API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaPendingTransactionResponseBean model;
                            model = gson.fromJson(errorResponse, OlaPendingTransactionResponseBean.class);
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

                Log.i("log", "Ola Pending Transaction API Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaPendingTransactionResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Pending Transaction API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });
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

    public void loadMore(OlaPlayerTransactionReportRequestBean model) {

        final Call<OlaPendingTransactionResponseBean> ledgerCall = client.getOlaPlayerPendingTransaction(model.getUrl(), model.getUserName(), model.getPassword(),
                model.getContentType(), model.getAccept(), model.getTxnType(), model.getFromDate(), model.getToDate(), model.getPageIndex(),
                model.getPageSize(), model.getPlayerDomainCode(), model.getPlayerUserName(), model.getRetailDomainCode(), model.getRetOrgID());

        Log.d("log", "Ola Ledger Report Request (Load More): " + ledgerCall.request().toString());

        ledgerCall.enqueue(new Callback<OlaPendingTransactionResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaPendingTransactionResponseBean> call, @NonNull Response<OlaPendingTransactionResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Ledger Report API Failed (Load More): " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaPendingTransactionResponseBean model;
                            model = gson.fromJson(errorResponse, OlaPendingTransactionResponseBean.class);
                            liveDataLoadMore.postValue(model);
                        } catch (IOException e) {
                            liveDataLoadMore.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataLoadMore.postValue(null);
                    return;
                }

                Log.i("log", "Ola Ledger Report API Response (Load More): " + new Gson().toJson(response.body()));
                liveDataLoadMore.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaPendingTransactionResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Ledger Report API failed (Load More): " + throwable.toString());
                liveDataLoadMore.postValue(null);
            }
        });
    }
}
