package com.skilrock.retailapp.portrait_draw_games.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.QuickPickRequestBean;
import com.skilrock.retailapp.models.drawgames.QuickPickResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LuckySixPortraitViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<QuickPickResponseBean> liveData = new MutableLiveData<>();


    public MutableLiveData<QuickPickResponseBean> getLiveData() {
        return liveData;
    }

    public void callQPApi(UrlDrawGameBean headerModel, QuickPickRequestBean model, String gameCode) {
        final Call<QuickPickResponseBean> apiCall = client.quickPick(headerModel.getUrl() + "/" + gameCode,
                headerModel.getContentType(), headerModel.getUserName(), headerModel.getPassword(), model);

        Log.d("log", "Quick Pick Request: " + apiCall.request().toString());

        apiCall.enqueue(new Callback<QuickPickResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<QuickPickResponseBean> call, @NonNull Response<QuickPickResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "Quick Pick API Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                QuickPickResponseBean model;
                                model= gson.fromJson(errorResponse, QuickPickResponseBean.class);
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
                }

                Log.i("log", "Quick Pick API Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<QuickPickResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "QuickPick API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });
    }
}
