package com.skilrock.retailapp.sbs.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.SbsReprintResponseBean;
import com.skilrock.retailapp.models.SbsWinPayResponse;
import com.skilrock.retailapp.models.SbsWinVerifyResponse;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SbsWinningViewModel extends ViewModel {
    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<SbsWinVerifyResponse> liveDataWinSbsVerify =
            new MutableLiveData<>();
    public MutableLiveData<SbsWinVerifyResponse> getliveDataWinSbsVerify() {
        return liveDataWinSbsVerify;
    }
    private MutableLiveData<SbsWinPayResponse> liveDataWinSbsPay =
            new MutableLiveData<>();
    public MutableLiveData<SbsWinPayResponse> getliveDataWinSbsPay() {
        return liveDataWinSbsPay;
    }


    private MutableLiveData<SbsReprintResponseBean> liveDataReprint = new MutableLiveData<>();
    public MutableLiveData<SbsReprintResponseBean> getLiveDataReprint() { return liveDataReprint; }

    public void callSbsWinVerify(UrlDrawGameBean urlBean, String deviceSerialNumber, long userId, String sessionId, String ticketNo) {
        Call<SbsWinVerifyResponse> apiCall = client.verifyWinSbs(urlBean.getUrl(), urlBean.getContentType(), urlBean.getUserName(), urlBean.getPassword(), deviceSerialNumber, userId, sessionId, ticketNo);
        Log.d("log", "WinSbsVerify Request: " + apiCall.request().toString());
        apiCall.enqueue(new Callback<SbsWinVerifyResponse>() {
            @Override
            public void onResponse(Call<SbsWinVerifyResponse> call, Response<SbsWinVerifyResponse> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {

                                String errorResponse = response.errorBody().string();
                                Log.e("log", "WinSbsVerify:" + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                SbsWinVerifyResponse model;
                                model = gson.fromJson(errorResponse, SbsWinVerifyResponse.class);
                                liveDataWinSbsVerify.postValue(model);

                            } catch (JsonSyntaxException e) {
                                liveDataWinSbsVerify.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataWinSbsVerify.postValue(null);
                        return;
                    }
                }

                Log.i("log", "WinSbsVerify: " + new Gson().toJson(response.body()));
                liveDataWinSbsVerify.postValue(response.body());
            }

            @Override
            public void onFailure(Call<SbsWinVerifyResponse> call, Throwable t) {
                Log.e("log", "WinSbsVerify Failed: " + t.getMessage());
                liveDataWinSbsVerify.postValue(null);
            }
        });
    }

    public void callSbsWinPay(UrlDrawGameBean urlBean, String deviceSerialNumber, long userId, String sessionId, String ticNo) {
        Call<SbsWinPayResponse> apiCall = client.payWinSbs(urlBean.getUrl(), urlBean.getContentType(), urlBean.getUserName(), urlBean.getPassword(), deviceSerialNumber, userId, sessionId, ticNo);
        Log.d("log", "WinSbsVerify" + apiCall.request().toString());
        apiCall.enqueue(new Callback<SbsWinPayResponse>() {
            @Override
            public void onResponse(Call<SbsWinPayResponse> call, Response<SbsWinPayResponse> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {

                                String errorResponse = response.errorBody().string();
                                Log.e("log", "WinSbsVerify:" + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                SbsWinPayResponse model;
                                model = gson.fromJson(errorResponse, SbsWinPayResponse.class);
                                liveDataWinSbsPay.postValue(model);

                            } catch (JsonSyntaxException e) {
                                liveDataWinSbsPay.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataWinSbsPay.postValue(null);
                        return;
                    }
                }

                Log.i("log", "WinSbsVerify:" + new Gson().toJson(response.body()));
                liveDataWinSbsPay.postValue(response.body());
            }

            @Override
            public void onFailure(Call<SbsWinPayResponse> call, Throwable t) {
                liveDataWinSbsPay.postValue(null);

            }
        });
    }

    public void callSbsReprint(UrlDrawGameBean urlBean, String device_serial_number, long retailer_id, String session_id, String ticket_no) {
        Call<SbsReprintResponseBean> apiCall = client.reprintSbs(urlBean.getUrl(), urlBean.getContentType(), urlBean.getUserName(), urlBean.getPassword(), device_serial_number, retailer_id, session_id, ticket_no);
        Log.d("log", "Reprint Request: " + apiCall.request().toString());
        apiCall.enqueue(new Callback<SbsReprintResponseBean>() {
            @Override
            public void onResponse(Call<SbsReprintResponseBean> call, Response<SbsReprintResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {

                                String errorResponse = response.errorBody().string();
                                Log.e("log", "Reprint error:" + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                SbsReprintResponseBean model;
                                model = gson.fromJson(errorResponse, SbsReprintResponseBean.class);
                                liveDataReprint.postValue(model);

                            } catch (JsonSyntaxException e) {
                                liveDataReprint.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataReprint.postValue(null);
                        return;
                    }
                }

                Log.i("log", "Reprint Response:" + new Gson().toJson(response.body()));
                liveDataReprint.postValue(response.body());
            }

            @Override
            public void onFailure(Call<SbsReprintResponseBean> call, Throwable t) {
                liveDataReprint.postValue(null);

            }
        });
    }
}
