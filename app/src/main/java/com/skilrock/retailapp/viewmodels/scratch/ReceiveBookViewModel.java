package com.skilrock.retailapp.viewmodels.scratch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.scratch.ChallanResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiveBookViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);
    private MutableLiveData<ChallanResponseBean> liveDataChallan = new MutableLiveData<>();

    public MutableLiveData<ChallanResponseBean> getLiveDataChallan() {
        return liveDataChallan;
    }

    public void callChallanApi(UrlBean urlBean, String challanId, boolean isFieldX) {
        Call<ChallanResponseBean> callChallanApi;

        if (isFieldX)
            callChallanApi = client.getChallanDetailsFieldX(urlBean.getUrl(), urlBean.getClientId(),
                urlBean.getClientSecret(), urlBean.getContentType(), PlayerData.getInstance().getUsername(),
                PlayerData.getInstance().getToken().split(" ")[1], challanId, "dlBookDetail");
        else
            callChallanApi = client.getChallanDetails(urlBean.getUrl(), urlBean.getClientId(),
                    urlBean.getClientSecret(), urlBean.getContentType(), PlayerData.getInstance().getUsername(),
                    PlayerData.getInstance().getToken().split(" ")[1], challanId);

        Log.d("log", "Challan Request: " + callChallanApi.request().toString());

        callChallanApi.enqueue(new Callback<ChallanResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<ChallanResponseBean> call, @NonNull Response<ChallanResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Challan API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ChallanResponseBean model;
                            model= gson.fromJson(errorResponse, ChallanResponseBean.class);
                            liveDataChallan.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataChallan.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataChallan.postValue(null);
                    return;
                }

                Log.i("log", "Challan Response: " + new Gson().toJson(response.body()));
                liveDataChallan.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ChallanResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "ChangePassword failed: " + throwable.toString());
                liveDataChallan.postValue(null);
            }
        });
    }

    public void callChallanApi(UrlBean urlBean, String challanId, boolean isFieldX, String challanNumber, String dlId) {
        Call<ChallanResponseBean> callChallanApi;

        if (isFieldX)
            callChallanApi = client.getChallanDetailsFieldX(urlBean.getUrl(), urlBean.getClientId(),
                    urlBean.getClientSecret(), urlBean.getContentType(), PlayerData.getInstance().getUsername(),
                    PlayerData.getInstance().getToken().split(" ")[1], challanId, "dlBookDetail");
        else
            callChallanApi = client.getChallanDetails(urlBean.getUrl(), urlBean.getClientId(),
                    urlBean.getClientSecret(), urlBean.getContentType(), PlayerData.getInstance().getUsername(),
                    PlayerData.getInstance().getToken().split(" ")[1], challanId);

        Log.d("log", "Challan Request: " + callChallanApi.request().toString());

        callChallanApi.enqueue(new Callback<ChallanResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<ChallanResponseBean> call, @NonNull Response<ChallanResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Challan API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ChallanResponseBean model;
                            model= gson.fromJson(errorResponse, ChallanResponseBean.class);
                            liveDataChallan.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataChallan.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataChallan.postValue(null);
                    return;
                }

                Log.i("log", "Challan Response: " + new Gson().toJson(response.body()));
                response.body().setDlChallanId(dlId);
                response.body().setDlChallanNumber(challanNumber);
                liveDataChallan.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ChallanResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "ChangePassword failed: " + throwable.toString());
                liveDataChallan.postValue(null);
            }
        });
    }

}
