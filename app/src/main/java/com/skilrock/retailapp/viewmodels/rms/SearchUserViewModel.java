package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.rms.SearchUserResponseBeanNew;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.models.rms.SearchUserRequestBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<SearchUserResponseBeanNew> liveData = new MutableLiveData<>();

    public MutableLiveData<SearchUserResponseBeanNew> getLiveData(){
        return liveData;
    }

    public void searchUserApi(String url, String token, SearchUserRequestBean searchUserRequestBean) {
        Call<SearchUserResponseBeanNew> reportCall = apiClient.searchUser(url, token, searchUserRequestBean);

        Log.d("log", "OLA Report Request: " + reportCall.request().toString());

        reportCall.enqueue(new Callback<SearchUserResponseBeanNew>() {
            @Override
            public void onResponse(@NonNull Call<SearchUserResponseBeanNew> call, @NonNull Response<SearchUserResponseBeanNew> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "search user Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SearchUserResponseBeanNew model;
                            model= gson.fromJson(errorResponse, SearchUserResponseBeanNew.class);
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

                Log.i("log", "search user Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SearchUserResponseBeanNew> call, @NonNull Throwable throwable) {
                //Log.e("log", "OLA Report failed: " + throwable.toString());
                throwable.printStackTrace();
                liveData.postValue(null);
            }
        });
    }
}
