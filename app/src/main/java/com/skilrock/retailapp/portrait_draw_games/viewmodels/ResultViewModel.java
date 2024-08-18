package com.skilrock.retailapp.portrait_draw_games.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.ResultRequestBean;
import com.skilrock.retailapp.models.drawgames.ResultResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultViewModel extends ViewModel {
    private APIClient client = APIConfig.getInstance().create(APIClient.class);
    private MutableLiveData<ResultResponseBean> liveDataResult =
            new MutableLiveData<>();

    public MutableLiveData<ResultResponseBean> getLiveDataResult() {
        return liveDataResult;
    }

    public void callResult(UrlDrawGameBean urlBean, ResultRequestBean resultRequestBean) {
        final Call<ResultResponseBean> apiCall = client.result(urlBean.getUrl(),
                urlBean.getContentType(), urlBean.getUserName(), urlBean.getPassword(), resultRequestBean);
        Log.d("log", "Result: " + apiCall.request().toString());
        apiCall.enqueue(new Callback<ResultResponseBean>() {
            @Override
            public void onResponse(Call<ResultResponseBean> call, Response<ResultResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "Cancel  Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                ResultResponseBean model;
                                model = gson.fromJson(errorResponse, ResultResponseBean.class);
                                liveDataResult.postValue(model);
                            } catch (JsonSyntaxException e) {
                                liveDataResult.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataResult.postValue(null);
                        return;
                    }
                }

                Log.i("log", "Result: " + new Gson().toJson(response.body()));
                liveDataResult.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ResultResponseBean> call, Throwable t) {
                liveDataResult.postValue(null);

            }
        });

    }
}
