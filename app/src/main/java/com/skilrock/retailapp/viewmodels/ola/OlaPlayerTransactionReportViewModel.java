package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.ola.OlaPlayerTransactionReportRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerTransactionResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaPlayerTransactionReportViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OlaPlayerTransactionResponseBean> liveData = new MutableLiveData<>();
    private MutableLiveData<OlaPlayerTransactionResponseBean> liveDataLoadMore = new MutableLiveData<>();

    public MutableLiveData<OlaPlayerTransactionResponseBean> getLiveData() {
        return liveData;
    }
    public MutableLiveData<OlaPlayerTransactionResponseBean> getLiveDataLoadMore() { return liveDataLoadMore; }

    public void callLedgerReportApi(OlaPlayerTransactionReportRequestBean model) {

        final Call<OlaPlayerTransactionResponseBean> ledgerCall = client.getOlaPlayerLedgerReport(model.getUrl(), model.getUserName(), model.getPassword(),
                model.getContentType(), model.getAccept(), model.getTxnType(), model.getFromDate(), model.getToDate(), model.getPageIndex(),
                model.getPageSize(), model.getPlayerDomainCode(), model.getPlayerUserName(), model.getRetailDomainCode(), model.getRetOrgID());

        Log.d("log", "Ola Ledger Report Request: " + ledgerCall.request().toString());

        ledgerCall.enqueue(new Callback<OlaPlayerTransactionResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaPlayerTransactionResponseBean> call, @NonNull Response<OlaPlayerTransactionResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Ledger Report API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaPlayerTransactionResponseBean model;
                            model= gson.fromJson(errorResponse, OlaPlayerTransactionResponseBean.class);
                            liveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveData.postValue(null);
                    return;
                }

                Log.i("log", "Ola Ledger Report API Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaPlayerTransactionResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Ledger Report API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });
    }

    public void loadMore(OlaPlayerTransactionReportRequestBean model) {

        final Call<OlaPlayerTransactionResponseBean> ledgerCall = client.getOlaPlayerLedgerReport(model.getUrl(), model.getUserName(), model.getPassword(),
                model.getContentType(), model.getAccept(), model.getTxnType(), model.getFromDate(), model.getToDate(), model.getPageIndex(),
                model.getPageSize(), model.getPlayerDomainCode(), model.getPlayerUserName(), model.getRetailDomainCode(), model.getRetOrgID());

        Log.d("log", "Ola Ledger Report Request (Load More): " + ledgerCall.request().toString());

        ledgerCall.enqueue(new Callback<OlaPlayerTransactionResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaPlayerTransactionResponseBean> call, @NonNull Response<OlaPlayerTransactionResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Ledger Report API Failed (Load More): " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaPlayerTransactionResponseBean model;
                            model= gson.fromJson(errorResponse, OlaPlayerTransactionResponseBean.class);
                            liveDataLoadMore.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataLoadMore.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
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
            public void onFailure(@NonNull Call<OlaPlayerTransactionResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Ledger Report API failed (Load More): " + throwable.toString());
                liveDataLoadMore.postValue(null);
            }
        });
    }
}
