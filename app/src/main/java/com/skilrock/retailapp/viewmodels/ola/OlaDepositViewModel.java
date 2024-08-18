package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.ola.OlaDepositRequestBean;
import com.skilrock.retailapp.models.ola.OlaDepositResponseBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaDepositViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OlaDepositResponseBean> liveDataDeposit = new MutableLiveData<>();
    private MutableLiveData<LoginBean> liveDataBalance = new MutableLiveData<>();

    public MutableLiveData<OlaDepositResponseBean> getDepositLiveData() {
        return liveDataDeposit;
    }
    public MutableLiveData<LoginBean> getLiveDataBalance() { return liveDataBalance; }

    public void callDeposit(UrlOlaBean url, OlaDepositRequestBean bean) {

        final Call<OlaDepositResponseBean> depositCall = client.postOlaDeposit(url.getUrl(), url.getUserName(), url.getPassword(), url.getContentType(), url.getAccept(), bean);

        Log.d("log", "Ola Deposit Request: " + depositCall.request().toString());

        depositCall.enqueue(new Callback<OlaDepositResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaDepositResponseBean> call, @NonNull Response<OlaDepositResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Deposit API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaDepositResponseBean model;
                            model= gson.fromJson(errorResponse, OlaDepositResponseBean.class);
                            liveDataDeposit.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataDeposit.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataDeposit.postValue(null);
                    return;
                }

                Log.i("log", "Ola Deposit API Response: " + new Gson().toJson(response.body()));
                liveDataDeposit.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaDepositResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Deposit API failed: " + throwable.toString());
                liveDataDeposit.postValue(null);
            }
        });
    }

    public void getUpdatedBalance(final String authToken) {
        Call<LoginBean> loginCall = client.getLogin(authToken);

        Log.d("log", "Login Request: " + loginCall.request().toString());

        loginCall.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(@NonNull Call<LoginBean> call, @NonNull Response<LoginBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Login API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            LoginBean model;
                            model= gson.fromJson(errorResponse, LoginBean.class);
                            liveDataBalance.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataBalance.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataBalance.postValue(null);
                    return;
                }

                Log.i("log", "Login API Response: " + new Gson().toJson(response.body()));
                if (response.body().getResponseCode() == 0 && response.body().getResponseData().getStatusCode() == 0)
                    response.body().setToken(authToken);
                liveDataBalance.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LoginBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Login API failed: " + throwable.toString());
                liveDataBalance.postValue(null);
            }
        });
    }
}
