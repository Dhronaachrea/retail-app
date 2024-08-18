package com.skilrock.retailapp.viewmodels.FieldXViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.FieldX.FieldxRetailerResponseBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FieldXAllRetailerViewModel extends ViewModel {
    private APIClient client = APIConfig.getInstance().create(APIClient.class);
    private MutableLiveData<FieldxRetailerResponseBean> liveFieldxRetailer = new MutableLiveData<>();

    public MutableLiveData<FieldxRetailerResponseBean> getFieldXRetailer() {
        return liveFieldxRetailer;
    }

    public void getRetailerApi(UrlBean urlBean) {
        Call<FieldxRetailerResponseBean> callFieldxRetailer = client.getFieldXRetailer(urlBean.getUrl(), PlayerData.getInstance().getToken(), PlayerData.getInstance().getUserId(), "Not assignment");
        Log.d("log", "Retailer request" + callFieldxRetailer.request().toString());
        callFieldxRetailer.enqueue(new Callback<FieldxRetailerResponseBean>() {
            @Override
            public void onResponse(Call<FieldxRetailerResponseBean> call, Response<FieldxRetailerResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Fieldx Retailer API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            FieldxRetailerResponseBean bean = gson.fromJson(errorResponse, FieldxRetailerResponseBean.class);
                            liveFieldxRetailer.postValue(bean);
                        } catch (IOException e) {
                            liveFieldxRetailer.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveFieldxRetailer.postValue(null);
                    return;
                }
                Log.i("log", "FieldxRetailer Response: " + new Gson().toJson(response.body()));
                liveFieldxRetailer.postValue(response.body());
            }

            @Override
            public void onFailure(Call<FieldxRetailerResponseBean> call, Throwable t) {
                Log.e("log", "FieldxRetailer API failed: " + t.toString());
                liveFieldxRetailer.postValue(null);
            }
        });
    }
}
