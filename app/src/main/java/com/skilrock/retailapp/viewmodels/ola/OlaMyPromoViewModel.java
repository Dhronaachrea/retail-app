package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.ola.OlaMyPromoResponseBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaMyPromoViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    public MutableLiveData<OlaMyPromoResponseBean> getLiveDataPromo() {
        return liveDataDeposit;
    }

    private MutableLiveData<OlaMyPromoResponseBean> liveDataDeposit = new MutableLiveData<>();


    public void callMyPromo(UrlOlaBean url) {
        long retOrgId = PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId();

        final Call<OlaMyPromoResponseBean> depositCall = client.getOlaPromo(url.getUrl(), url.getUserName(), url.getPassword(), url.getAccept(), retOrgId);

        Log.d("log", "Ola Promo Request: " + depositCall.request().toString());

        depositCall.enqueue(new Callback<OlaMyPromoResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaMyPromoResponseBean> call, @NonNull Response<OlaMyPromoResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Promo API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaMyPromoResponseBean model;
                            model= gson.fromJson(errorResponse, OlaMyPromoResponseBean.class);
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

                Log.i("log", "Ola Promo API Response: " + new Gson().toJson(response.body()));
                liveDataDeposit.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaMyPromoResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Promo API failed: " + throwable.toString());
                liveDataDeposit.postValue(null);
            }
        });
    }
}
