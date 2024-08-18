package com.skilrock.retailapp.viewmodels.scratch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.rms.GameListBean;
import com.skilrock.retailapp.models.scratch.QuickOrderRequestBean;
import com.skilrock.retailapp.models.scratch.QuickOrderResponseBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuickOrderViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<QuickOrderResponseBean> responseData = new MutableLiveData<>();

    private MutableLiveData<GameListBean> gameListData = new MutableLiveData<>();

    public void callGameListApi(UrlBean urlBean) {
        //String queryString = "{\"gameStatus\":\"OPEN\",\"gameType\":\"SCRATCH\"}";
        //String queryString = "{\"userName\":\"" + PlayerData.getInstance().getUserId() + "\"}";

        Call<GameListBean> callGameListApi = client.getNewGameList(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), PlayerData.getInstance().getUsername(), PlayerData.getInstance().getToken().split(" ")[1]);

        Log.d("log", "GameList Request: " + callGameListApi.request().toString());

        callGameListApi.enqueue(new Callback<GameListBean>() {
            @Override
            public void onResponse(@NonNull Call<GameListBean> call, @NonNull Response<GameListBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "GameList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            GameListBean model;
                            model= gson.fromJson(errorResponse, GameListBean.class);
                            gameListData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            gameListData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    gameListData.postValue(null);
                    return;
                }

                Log.i("log", "GameList API Response: " + new Gson().toJson(response.body()));
                GameListBean gameListBean = response.body();
                if (gameListBean == null)
                    gameListData.postValue(null);
                else
                    gameListData.postValue(gameListBean);
            }

            @Override
            public void onFailure(@NonNull Call<GameListBean> call, @NonNull Throwable t) {
                Log.e("log", "GameList API Response: FAILED");
                gameListData.postValue(null);
            }
        });

    }

    public void callBookOrderApi(UrlBean urlBean, ArrayList<QuickOrderRequestBean.GameOrderList> listSelectedGame) {
        QuickOrderRequestBean model = new QuickOrderRequestBean();
        model.setUserName(PlayerData.getInstance().getUsername());
        model.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
        model.setGameOrderList(listSelectedGame);

        Call<QuickOrderResponseBean> callQuickOrderApi = client.quickOrder(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), model);

        Log.d("log", "Quick Order Request: " + callQuickOrderApi.request().toString());

        callQuickOrderApi.enqueue(new Callback<QuickOrderResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<QuickOrderResponseBean> call, @NonNull Response<QuickOrderResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Quick Order API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            QuickOrderResponseBean model;
                            model= gson.fromJson(errorResponse, QuickOrderResponseBean.class);
                            responseData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            responseData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    responseData.postValue(null);
                    return;
                }

                Log.i("log", "Quick Order API Response: " + new Gson().toJson(response.body()));
                responseData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<QuickOrderResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Quick Order API failed: " + throwable.toString());
                responseData.postValue(null);
            }
        });
    }

    public MutableLiveData<QuickOrderResponseBean> getResponseData() {
        return responseData;
    }

    public MutableLiveData<GameListBean> getGameListData() {
        return gameListData;
    }

}
