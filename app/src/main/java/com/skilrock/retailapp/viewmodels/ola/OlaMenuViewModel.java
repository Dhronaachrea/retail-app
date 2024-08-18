package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.ola.OlaDomainResponseBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaMenuViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OlaDomainResponseBean> liveData = new MutableLiveData<>();

    public MutableLiveData<OlaDomainResponseBean> getLiveData() {
        return liveData;
    }

    public void callOlaDomainApi(final UrlOlaBean urlOlaBean) {

        long retOrgId = PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgId();
        final Call<OlaDomainResponseBean> olaDomainCall = client.getOlaDomain(urlOlaBean.getUrl(), urlOlaBean.getUserName(), urlOlaBean.getPassword(), urlOlaBean.getContentType(), urlOlaBean.getRetailDomainCode(), retOrgId);

        Log.d("log", "Ola Domain Request: " + olaDomainCall.request().toString());

        olaDomainCall.enqueue(new Callback<OlaDomainResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaDomainResponseBean> call, @NonNull Response<OlaDomainResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Domain API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaDomainResponseBean model;
                            model= gson.fromJson(errorResponse, OlaDomainResponseBean.class);
                            liveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveData.postValue(null);
                    return;
                }

                Log.i("log", "Ola Domain API Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaDomainResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Domain API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });

    }
}
