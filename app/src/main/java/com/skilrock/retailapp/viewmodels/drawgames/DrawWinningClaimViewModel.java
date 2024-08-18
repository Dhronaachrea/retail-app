package com.skilrock.retailapp.viewmodels.drawgames;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.PayPwtRequestBean;
import com.skilrock.retailapp.models.drawgames.PayPwtResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketVerificationGameResponseBean;
import com.skilrock.retailapp.models.drawgames.VerifyGameTicketRequest;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawWinningClaimViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<TicketVerificationGameResponseBean> liveVerifyData = new MutableLiveData<>();
    private MutableLiveData<PayPwtResponseBean> livePayPwt = new MutableLiveData<>();

    public MutableLiveData<TicketVerificationGameResponseBean> getliveVerifyData() {
        return liveVerifyData;
    }

    public MutableLiveData<PayPwtResponseBean> getlivePayPwt() {
        return livePayPwt;
    }

    public void callTicketVerificationAPI(UrlDrawGameBean headerModel, VerifyGameTicketRequest model) {

        final Call<TicketVerificationGameResponseBean> apiCall = client.ticketVerification(headerModel.getUrl(),
                headerModel.getContentType(), headerModel.getUserName(), headerModel.getPassword(), model);

        Log.d("log", "TicketVerify Request: " + apiCall.request().toString());

        apiCall.enqueue(new Callback<TicketVerificationGameResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<TicketVerificationGameResponseBean> call, @NonNull Response<TicketVerificationGameResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "TicketVerify API Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                TicketVerificationGameResponseBean model;
                                model = gson.fromJson(errorResponse, TicketVerificationGameResponseBean.class);
                                liveVerifyData.postValue(model);
                            } catch (JsonSyntaxException e) {
                                liveVerifyData.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveVerifyData.postValue(null);
                        return;
                    }
                }

                Log.i("log", "TicketVerify API Response: " + new Gson().toJson(response.body()));
                liveVerifyData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TicketVerificationGameResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "TicketVerify API failed: " + throwable.toString());
                liveVerifyData.postValue(null);
            }
        });
    }

    public void callPayPwtAPI(UrlDrawGameBean headerModel, PayPwtRequestBean model) {
        final Call<PayPwtResponseBean> apiCall = client.peyPwt(headerModel.getUrl(),
                headerModel.getContentType(), headerModel.getUserName(), headerModel.getPassword(), model);

        Log.d("log", "PayPwt Request: " + apiCall.request().toString());

        apiCall.enqueue(new Callback<PayPwtResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<PayPwtResponseBean> call, @NonNull Response<PayPwtResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "PayPwt API Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                PayPwtResponseBean model;
                                model = gson.fromJson(errorResponse, PayPwtResponseBean.class);
                                livePayPwt.postValue(model);
                            } catch (JsonSyntaxException e) {
                                livePayPwt.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        livePayPwt.postValue(null);
                        return;
                    }
                }

                Log.i("log", "PayPwt API Response: " + new Gson().toJson(response.body()));
                livePayPwt.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<PayPwtResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "PayPwt API failed: " + throwable.toString());
                livePayPwt.postValue(null);
            }
        });
    }
}
