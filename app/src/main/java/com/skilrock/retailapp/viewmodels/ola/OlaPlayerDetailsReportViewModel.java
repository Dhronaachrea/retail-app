package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.interfaces.OlaPlayerDetailsResponseListener;
import com.skilrock.retailapp.models.ola.OlaPlayerDetailsReportRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerDetailsResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaPlayerDetailsReportViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    /*private MutableLiveData<OlaPlayerDetailsResponseBean> liveData = new MutableLiveData<>();
    private MutableLiveData<OlaPlayerDetailsResponseBean> liveDataLoadMore = new MutableLiveData<>();

    public MutableLiveData<OlaPlayerDetailsResponseBean> getLiveData() {
        return liveData;
    }
    public MutableLiveData<OlaPlayerDetailsResponseBean> getLiveDataLoadMore() { return liveDataLoadMore; }*/

    public void callOlaPlayerDetail(OlaPlayerDetailsReportRequestBean model, OlaPlayerDetailsResponseListener listener) {

        final Call<OlaPlayerDetailsResponseBean> playerDetailCall = client.getOlaPlayerDetailsReport(model.getUrl(), model.getUsername(), model.getPassword(),
                model.getContentType(), model.getAccept(), model.getFromDate(), model.getToDate(), model.getOffset(), model.getLimit(),
                model.getMobileNo(), model.getPlrDomainCode(), model.getRetailDomainCode(), model.getRetailOrgId());

        Log.d("log", "Ola Player Details Report Request: " + playerDetailCall.request().toString());

        playerDetailCall.enqueue(new Callback<OlaPlayerDetailsResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaPlayerDetailsResponseBean> call, @NonNull Response<OlaPlayerDetailsResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Player Details Report API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaPlayerDetailsResponseBean model;
                            model= gson.fromJson(errorResponse, OlaPlayerDetailsResponseBean.class);
                            listener.onResponse(model);
                            //liveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            listener.onResponse(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    //liveData.postValue(null);
                    listener.onResponse(null);
                    return;
                }

                Log.i("log", "Ola Player Details Report API Response: " + new Gson().toJson(response.body()));
                //liveData.postValue(response.body());
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaPlayerDetailsResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Player Details Report API failed: " + throwable.toString());
                listener.onResponse(null);
                //liveData.postValue(null);
            }
        });
    }

    public void loadMore(OlaPlayerDetailsReportRequestBean model, OlaPlayerDetailsResponseListener listener) {

        final Call<OlaPlayerDetailsResponseBean> playerDetailCall = client.getOlaPlayerDetailsReport(model.getUrl(), model.getUsername(), model.getPassword(),
                model.getContentType(), model.getAccept(), model.getFromDate(), model.getToDate(), model.getOffset(), model.getLimit(),
                model.getMobileNo(), model.getPlrDomainCode(), model.getRetailDomainCode(), model.getRetailOrgId());

        Log.d("log", "Ola Player Details Report Request (Load More): " + playerDetailCall.request().toString());

        playerDetailCall.enqueue(new Callback<OlaPlayerDetailsResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaPlayerDetailsResponseBean> call, @NonNull Response<OlaPlayerDetailsResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Player Details Report API Failed (Load More): " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaPlayerDetailsResponseBean model;
                            model= gson.fromJson(errorResponse, OlaPlayerDetailsResponseBean.class);
                            listener.onResponse(model);
                            //liveDataLoadMore.postValue(model);
                        } catch (JsonSyntaxException e) {
                            listener.onResponse(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    //liveDataLoadMore.postValue(null);
                    listener.onResponse(null);
                    return;
                }

                Log.i("log", "Ola Player Details Report API Response (Load More): " + new Gson().toJson(response.body()));
                //liveDataLoadMore.postValue(response.body());
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaPlayerDetailsResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Player Details Report API failed (Load More): " + throwable.toString());
                //liveDataLoadMore.postValue(null);
                listener.onResponse(null);
            }
        });
    }
}
