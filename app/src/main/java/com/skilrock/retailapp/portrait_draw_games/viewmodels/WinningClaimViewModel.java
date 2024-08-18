package com.skilrock.retailapp.portrait_draw_games.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayRequestBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayResponseBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimVerifyRequestBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimVerifyResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WinningClaimViewModel extends ViewModel {
    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<WinningClaimVerifyResponseBean> liveDataWinningVerify =
            new MutableLiveData<>();
    public MutableLiveData<WinningClaimVerifyResponseBean>getLiveDataWinningVerify() {
        return liveDataWinningVerify;
    }


    private MutableLiveData<WinningClaimPayResponseBean> liveDataWinningPay =
            new MutableLiveData<>();
    public MutableLiveData<WinningClaimPayResponseBean>getLiveDataWinningPay() {
        return liveDataWinningPay;
    }

    private MutableLiveData<WinningClaimPayResponseBean> liveDataWinningFailedPrint =
            new MutableLiveData<>();
    public MutableLiveData<WinningClaimPayResponseBean>getliveDataWinningFailedPrint() {
        return liveDataWinningFailedPrint;
    }

    public void callWinningVerify(UrlDrawGameBean urlBean, WinningClaimVerifyRequestBean winningClaimVerifyRequestBean) {
        final Call<WinningClaimVerifyResponseBean> apiCall = client.winningClaimVerify(urlBean.getUrl(),
                urlBean.getContentType(), urlBean.getUserName(), urlBean.getPassword(), winningClaimVerifyRequestBean);
        Log.d("log", "Winning response: " + apiCall.request().toString());
        apiCall.enqueue(new Callback<WinningClaimVerifyResponseBean>() {
            @Override
            public void onResponse(Call<WinningClaimVerifyResponseBean> call, Response<WinningClaimVerifyResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "Cancel  Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                WinningClaimVerifyResponseBean model;
                                model = gson.fromJson(errorResponse, WinningClaimVerifyResponseBean.class);
                                liveDataWinningVerify.postValue(model);

                            } catch (JsonSyntaxException e) {
                                liveDataWinningVerify.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataWinningVerify.postValue(null);
                        return;
                    }
                }

                Log.i("log", "Winning: " + new Gson().toJson(response.body()));
                liveDataWinningVerify.postValue(response.body());
            }
            @Override
            public void onFailure(Call<WinningClaimVerifyResponseBean> call, Throwable t) {
                liveDataWinningVerify.postValue(null);

            }
        });

    }

    public void callWinningPayPwt(UrlDrawGameBean urlBean, WinningClaimPayRequestBean payRequestBean) {

        final Call<WinningClaimPayResponseBean> apiCall = client.winningClaimPay(urlBean.getUrl(),
                urlBean.getContentType(), urlBean.getUserName(), urlBean.getPassword(), payRequestBean);
        Log.d("log", "Pay response: " + apiCall.request().toString());
        apiCall.enqueue(new Callback<WinningClaimPayResponseBean>() {
            @Override
            public void onResponse(Call<WinningClaimPayResponseBean> call, Response<WinningClaimPayResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {

                                String errorResponse = response.errorBody().string();
                                Log.e("log", "Cancel  Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                WinningClaimPayResponseBean model;
                                model = gson.fromJson(errorResponse, WinningClaimPayResponseBean.class);
                                liveDataWinningPay.postValue(model);

                            } catch (JsonSyntaxException e) {
                                liveDataWinningPay.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataWinningPay.postValue(null);
                        return;
                    }
                }

                Log.i("log", "Pay : " + new Gson().toJson(response.body()));
                liveDataWinningPay.postValue(response.body());
            }
            @Override
            public void onFailure(Call<WinningClaimPayResponseBean> call, Throwable t) {
                liveDataWinningPay.postValue(null);

            }
        });
    }

    public void printDuplicateWinning(UrlDrawGameBean urlBean,String user,String ticket_number) {
        Call<WinningClaimPayResponseBean> apiCall=client.printWinFailedReceipt(urlBean.getUrl(),urlBean.getContentType(),urlBean.getUserName(),urlBean.getPassword(),ticket_number,user, Utils.getModelCode(),Utils.getDeviceSerialNumber(), PlayerData.getInstance().getToken().split(" ")[1]);
        Log.d("log", "InitiatePWt:" + apiCall.request().toString());
        apiCall.enqueue(new Callback<WinningClaimPayResponseBean>() {
            @Override
            public void onResponse(Call<WinningClaimPayResponseBean> call, Response<WinningClaimPayResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {

                                String errorResponse = response.errorBody().string();
                                Log.e("log", "InitiatePWt:" + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                WinningClaimPayResponseBean model;
                                model = gson.fromJson(errorResponse, WinningClaimPayResponseBean.class);
                                liveDataWinningFailedPrint.postValue(model);

                            } catch (JsonSyntaxException e) {
                                liveDataWinningFailedPrint.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataWinningFailedPrint.postValue(null);
                        return;
                    }
                }

                Log.i("log", "InitiatePWt:" + new Gson().toJson(response.body()));
                liveDataWinningFailedPrint.postValue(response.body());
            }

            @Override
            public void onFailure(Call<WinningClaimPayResponseBean> call, Throwable t) {
                liveDataWinningFailedPrint.postValue(null);
            }
        });

    }
}
