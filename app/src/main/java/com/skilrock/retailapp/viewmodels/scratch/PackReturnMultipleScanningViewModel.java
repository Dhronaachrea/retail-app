package com.skilrock.retailapp.viewmodels.scratch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseBean;
import com.skilrock.retailapp.models.scratch.SubmitPackReturnResponseBean;
import com.skilrock.retailapp.models.scratch.SubmitReturnRequestBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackReturnMultipleScanningViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<SubmitPackReturnResponseBean> responseData = new MutableLiveData<>();

    public MutableLiveData<SubmitPackReturnResponseBean> getLiveDataPackReturnSubmit() {
        return responseData;
    }

    public void callSubmitPackReturnApi(UrlBean urlBean, SubmitReturnRequestBean submitReturnRequestBean) {
        Call<SubmitPackReturnResponseBean> call = client.submitPackReturn(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), submitReturnRequestBean);

        Log.d("log", "Submit Return Request: " + call.request().toString());

        call.enqueue(new Callback<SubmitPackReturnResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SubmitPackReturnResponseBean> call, @NonNull Response<SubmitPackReturnResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Submit Return API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SubmitPackReturnResponseBean model;
                            model= gson.fromJson(errorResponse, SubmitPackReturnResponseBean.class);
                            responseData.postValue(model);
                        } catch (IOException e) {
                            responseData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    responseData.postValue(null);
                    return;
                }

                Log.i("log", "Submit Return API Response: " + new Gson().toJson(response.body()));
                responseData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SubmitPackReturnResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Submit Return API failed: " + throwable.toString());
                responseData.postValue(null);
            }
        });
    }

    private MutableLiveData<ReturnChallanResponseBean> returnListData = new MutableLiveData<>();

    public MutableLiveData<ReturnChallanResponseBean> getReturnListData() {
        return returnListData;
    }

    public void callReturnListApi(UrlBean gameListUrl, String userName,String retailerName,String CHALLAN_NUMBER) {
        Call<ReturnChallanResponseBean> callGameListApi = client.getReturnListPacks(gameListUrl.getUrl(), gameListUrl.getClientId(), gameListUrl.getClientSecret(), gameListUrl.getContentType(),PlayerData.getInstance().getToken().split(" ")[1], userName,retailerName,CHALLAN_NUMBER);

        Log.d("log", "ReturnList Request: " + callGameListApi.request().toString());

        callGameListApi.enqueue(new Callback<ReturnChallanResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<ReturnChallanResponseBean> call, @NonNull Response<ReturnChallanResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "ReturnList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ReturnChallanResponseBean model;
                            model= gson.fromJson(errorResponse, ReturnChallanResponseBean.class);
                            returnListData.postValue(model);
                        } catch (IOException e) {
                            returnListData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    returnListData.postValue(null);
                    return;
                }

                Log.i("log", "ReturnList API Response: " + new Gson().toJson(response.body()));
                ReturnChallanResponseBean gameListBean = response.body();
                if (gameListBean == null)
                    returnListData.postValue(null);
                else
                    returnListData.postValue(gameListBean);
            }

            @Override
            public void onFailure(@NonNull Call<ReturnChallanResponseBean> call, @NonNull Throwable t) {
                Log.e("log", "ReturnList API Response: FAILED");
                returnListData.postValue(null);
            }
        });
    }
}
