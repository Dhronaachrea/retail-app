package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaPlayerSearchReportViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OlaPlayerSearchResponseBean> liveData = new MutableLiveData<>();
    private MutableLiveData<OlaPlayerSearchResponseBean> liveDataLoadMore = new MutableLiveData<>();

    public MutableLiveData<OlaPlayerSearchResponseBean> getLiveData() {
        return liveData;
    }
    public MutableLiveData<OlaPlayerSearchResponseBean> getLiveDataLoadMore() { return liveDataLoadMore; }

    public void callOlaPlayerSearchApi(OlaPlayerSearchRequestBean model) {
        Call<OlaPlayerSearchResponseBean> searchCall = null;
        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
            searchCall = client.getOlaPlayerSearchMyanmar(model.getUrl(), model.getPassword(), model.getContentType(),
                    model.getAccept(), model.getUserName(), model.getRetailDomainCode(), model.getRetailOrgId(),
                    model.getPlrDomainCode(), model.getPageIndex(), model.getPageSize(), model.getPlrUserName());
        else
            searchCall = client.getOlaPlayerSearch(model.getUrl(), model.getPassword(), model.getContentType(),
                    model.getAccept(), model.getUserName(), model.getRetailDomainCode(), model.getRetailOrgId(),
                    model.getPlrDomainCode(), model.getPageIndex(), model.getPageSize(), model.getFirstName(),
                    model.getLastName(), model.getPhone());

        Log.d("log", "Ola Player Search Report Request: " + searchCall.request().toString());

        searchCall.enqueue(new Callback<OlaPlayerSearchResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaPlayerSearchResponseBean> call, @NonNull Response<OlaPlayerSearchResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Player Search Report API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaPlayerSearchResponseBean model;
                            model= gson.fromJson(errorResponse, OlaPlayerSearchResponseBean.class);
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

                Log.i("log", "Ola Player Search Report API Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaPlayerSearchResponseBean> call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Log.e("log", "Ola Player Search Report API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });
    }

    public void loadMore(OlaPlayerSearchRequestBean model) {

        final Call<OlaPlayerSearchResponseBean> searchCall = client.getOlaPlayerSearch(model.getUrl(), model.getPassword(), model.getContentType(),
                model.getAccept(), model.getUserName(), model.getRetailDomainCode(), model.getRetailOrgId(),
                model.getPlrDomainCode(), model.getPageIndex(), model.getPageSize(), model.getFirstName(),
                model.getLastName(), model.getPhone());

        Log.d("log", "Ola Player Search Report Request (Load More): " + searchCall.request().toString());

        searchCall.enqueue(new Callback<OlaPlayerSearchResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaPlayerSearchResponseBean> call, @NonNull Response<OlaPlayerSearchResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Player Search Report API Failed (Load More): " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaPlayerSearchResponseBean model;
                            model= gson.fromJson(errorResponse, OlaPlayerSearchResponseBean.class);
                            liveDataLoadMore.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataLoadMore.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataLoadMore.postValue(null);
                    return;
                }

                Log.i("log", "Ola Player Search Report API Response (Load More): " + new Gson().toJson(response.body()));
                liveDataLoadMore.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaPlayerSearchResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Player Search Report API failed (Load More): " + throwable.toString());
                liveDataLoadMore.postValue(null);
            }
        });
    }
}
