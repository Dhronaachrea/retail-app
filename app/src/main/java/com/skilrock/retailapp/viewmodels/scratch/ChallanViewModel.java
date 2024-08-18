package com.skilrock.retailapp.viewmodels.scratch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.scratch.ReceiveBookRequestBean;
import com.skilrock.retailapp.models.ResponseCodeMessageBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallanViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);
    private MutableLiveData<ResponseCodeMessageBean> liveDataResponse = new MutableLiveData<>();

    public MutableLiveData<ResponseCodeMessageBean> getLiveDataResponse() {
        return liveDataResponse;
    }

    public void callReceiveBookApi(UrlBean urlBean, ReceiveBookRequestBean receiveBookRequestBean) {
        Call<ResponseCodeMessageBean> callReceiveBookApi = client.bookReceive(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), receiveBookRequestBean);

        Log.d("log", "Receive Book Request: " + callReceiveBookApi.request().toString());

        callReceiveBookApi.enqueue(new Callback<ResponseCodeMessageBean>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Response<ResponseCodeMessageBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Receive Book Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ResponseCodeMessageBean model;
                            model= gson.fromJson(errorResponse, ResponseCodeMessageBean.class);
                            liveDataResponse.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataResponse.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataResponse.postValue(null);
                    return;
                }

                Log.i("log", "Receive Book: " + new Gson().toJson(response.body()));
                liveDataResponse.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Receive Book failed: " + throwable.toString());
                liveDataResponse.postValue(null);
            }
        });
    }

}
