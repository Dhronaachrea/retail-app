package com.skilrock.retailapp.viewmodels.scratch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseHomeBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackReturnViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<ReturnChallanResponseHomeBean> returnListData = new MutableLiveData<>();

    public MutableLiveData<ReturnChallanResponseHomeBean> getReturnListData() {
        return returnListData;
    }

    public void callReturnListApi(UrlBean gameListUrl, String userName, String challanNumber,String retailerName) {
        Call<ReturnChallanResponseHomeBean> callGameListApi = client.getReturnList(gameListUrl.getUrl(), gameListUrl.getClientId(), gameListUrl.getClientSecret(), gameListUrl.getContentType(), PlayerData.getInstance().getToken().split(" ")[1], userName, challanNumber,retailerName);

        Log.d("log", "ReturnList Request: " + callGameListApi.request().toString());

        callGameListApi.enqueue(new Callback<ReturnChallanResponseHomeBean>() {
            @Override
            public void onResponse(@NonNull Call<ReturnChallanResponseHomeBean> call, @NonNull Response<ReturnChallanResponseHomeBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "ReturnList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ReturnChallanResponseHomeBean model;
                            model= gson.fromJson(errorResponse, ReturnChallanResponseHomeBean.class);
                            returnListData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            returnListData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    returnListData.postValue(null);
                    return;
                }

                Log.i("log", "ReturnList API Response: " + new Gson().toJson(response.body()));
                response.body().setRetailerName(retailerName);
                ReturnChallanResponseHomeBean gameListBean = response.body();
                if (gameListBean == null)
                    returnListData.postValue(null);
                else
                    returnListData.postValue(gameListBean);
            }

            @Override
            public void onFailure(@NonNull Call<ReturnChallanResponseHomeBean> call, @NonNull Throwable t) {
                Log.e("log", "ReturnList API Response: FAILED");
                returnListData.postValue(null);
            }
        });
    }
}
