package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.CashManagementPaymentRequestBean;
import com.skilrock.retailapp.models.rms.CashManagementPaymentResponseBean;
import com.skilrock.retailapp.models.rms.CashManagementTxnTypeResponseBean;
import com.skilrock.retailapp.models.rms.CashManagementUserRequestBean;
import com.skilrock.retailapp.models.rms.CashManagementUserResponseBeanNew;
import com.skilrock.retailapp.models.rms.TransactionRemarksResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashManagementViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<CashManagementUserResponseBeanNew> liveDataUser        = new MutableLiveData<>();
    private MutableLiveData<CashManagementTxnTypeResponseBean> liveDataTxnType  = new MutableLiveData<>();
    private MutableLiveData<CashManagementPaymentResponseBean> liveDataPayment  = new MutableLiveData<>();
    private MutableLiveData<TransactionRemarksResponseBean> liveDataRemarks  = new MutableLiveData<>();

    public MutableLiveData<CashManagementUserResponseBeanNew> getLiveDataUser(){ return liveDataUser; }

    public MutableLiveData<TransactionRemarksResponseBean> getiveRemarks(){ return liveDataRemarks; }

    public MutableLiveData<CashManagementTxnTypeResponseBean> getLiveDataTxnType() { return liveDataTxnType; }

    public MutableLiveData<CashManagementPaymentResponseBean> getLiveDataPayment() { return liveDataPayment; }

    public void userSearchApi(String url) {
        CashManagementUserRequestBean model = new CashManagementUserRequestBean();
        model.setDomainId(PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId());
        model.setOrgId(PlayerData.getInstance().getOrgId());

        Call<CashManagementUserResponseBeanNew> userCall = apiClient.postUserSearch(url, PlayerData.getInstance().getToken(), model);

        Log.d("log", "Cash Management User: " + userCall.request().toString());

        userCall.enqueue(new Callback<CashManagementUserResponseBeanNew>() {
            @Override
            public void onResponse(@NonNull Call<CashManagementUserResponseBeanNew> call, @NonNull Response<CashManagementUserResponseBeanNew> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Cash Management User API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            CashManagementUserResponseBeanNew model;
                            model= gson.fromJson(errorResponse, CashManagementUserResponseBeanNew.class);
                            liveDataUser.postValue(model);
                        } catch (IOException e) {
                            liveDataUser.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataUser.postValue(null);
                    return;
                }

                Log.i("log", "Cash Management User Response: " + new Gson().toJson(response.body()));
                liveDataUser.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CashManagementUserResponseBeanNew> call, @NonNull Throwable throwable) {
                Log.e("log", "Cash Management User failed: " + throwable.toString());
                throwable.printStackTrace();
                liveDataUser.postValue(null);
            }
        });
    }

    public void txnTypeApi(String url) {
        Call<CashManagementTxnTypeResponseBean> transactionTypeCall = apiClient.getTransactionType(url, PlayerData.getInstance().getToken(), "INTRA", true);

        Log.d("log", "Cash Management Txn Type: " + transactionTypeCall.request().toString());

        transactionTypeCall.enqueue(new Callback<CashManagementTxnTypeResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<CashManagementTxnTypeResponseBean> call, @NonNull Response<CashManagementTxnTypeResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Cash Management Txn Type API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            CashManagementTxnTypeResponseBean model;
                            model= gson.fromJson(errorResponse, CashManagementTxnTypeResponseBean.class);
                            liveDataTxnType.postValue(model);
                        } catch (IOException e) {
                            liveDataTxnType.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataTxnType.postValue(null);
                    return;
                }

                Log.i("log", "Cash Management Txn Type Response: " + new Gson().toJson(response.body()));
                liveDataTxnType.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CashManagementTxnTypeResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Cash Management Txn Type failed: " + throwable.toString());
                throwable.printStackTrace();
                liveDataTxnType.postValue(null);
            }
        });
    }

    public void getRemarksApi(String url, String type) {
        Call<TransactionRemarksResponseBean> transactionTypeCall = apiClient.getTxnRemarks(url, PlayerData.getInstance().getToken(), type);

        Log.d("log", "TransactionRemarks: " + transactionTypeCall.request().toString());

        transactionTypeCall.enqueue(new Callback<TransactionRemarksResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<TransactionRemarksResponseBean> call, @NonNull Response<TransactionRemarksResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "TransactionRemarks API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            TransactionRemarksResponseBean model;
                            model= gson.fromJson(errorResponse, TransactionRemarksResponseBean.class);
                            liveDataRemarks.postValue(model);
                        } catch (IOException e) {
                            liveDataRemarks.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataRemarks.postValue(null);
                    return;
                }

                Log.i("log", "TransactionRemarks Response: " + new Gson().toJson(response.body()));
                liveDataRemarks.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TransactionRemarksResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "TransactionRemarks failed: " + throwable.toString());
                throwable.printStackTrace();
                liveDataRemarks.postValue(null);
            }
        });
    }

    public void paymentApi(String url, CashManagementPaymentRequestBean model) {
        Call<CashManagementPaymentResponseBean> paymentCall = apiClient.postCashManagementPayment(url, PlayerData.getInstance().getToken(), model);

        Log.d("log", "Cash Management Payment: " + paymentCall.request().toString());

        paymentCall.enqueue(new Callback<CashManagementPaymentResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<CashManagementPaymentResponseBean> call, @NonNull Response<CashManagementPaymentResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Cash Management Payment API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            CashManagementPaymentResponseBean model;
                            model= gson.fromJson(errorResponse, CashManagementPaymentResponseBean.class);
                            liveDataPayment.postValue(model);
                        } catch (IOException e) {
                            liveDataPayment.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataPayment.postValue(null);
                    return;
                }

                Log.i("log", "Cash Management Payment Response: " + new Gson().toJson(response.body()));
                liveDataPayment.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CashManagementPaymentResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Cash Management Payment failed: " + throwable.toString());
                throwable.printStackTrace();
                liveDataPayment.postValue(null);
            }
        });
    }

}
