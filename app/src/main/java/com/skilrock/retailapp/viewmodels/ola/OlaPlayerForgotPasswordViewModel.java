package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.ola.OlaPlayerForgotPasswordRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerForgotPasswordResponseBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchResponseBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaPlayerForgotPasswordViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OlaPlayerForgotPasswordResponseBean> liveData = new MutableLiveData<>();
    private MutableLiveData<OlaPlayerSearchResponseBean> liveDataPlrSearch = new MutableLiveData<>();

    public MutableLiveData<OlaPlayerForgotPasswordResponseBean> getDepositLiveData() {
        return liveData;
    }

    public MutableLiveData<OlaPlayerSearchResponseBean> getPlsSearchLiveData() {
        return liveDataPlrSearch;
    }

    public void callOlaPlayerForgotPasswordApi(UrlOlaBean url, OlaPlayerForgotPasswordRequestBean model) {

        final Call<OlaPlayerForgotPasswordResponseBean> forgotPasswordCall = client.putOlaPlayerForgotPassword(url.getUrl(),
                url.getUserName(), url.getPassword(), url.getContentType(), url.getAccept(), model);

        Log.d("log", "Ola Forgot Password Request: " + forgotPasswordCall.request().toString());

        forgotPasswordCall.enqueue(new Callback<OlaPlayerForgotPasswordResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaPlayerForgotPasswordResponseBean> call, @NonNull Response<OlaPlayerForgotPasswordResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Forgot Password API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaPlayerForgotPasswordResponseBean model;
                            model= gson.fromJson(errorResponse, OlaPlayerForgotPasswordResponseBean.class);
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

                Log.i("log", "Ola Forgot Password API Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaPlayerForgotPasswordResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Forgot Password API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });
    }

    public void callOlaPlayerSearchApi(OlaPlayerSearchRequestBean model) {

        final Call<OlaPlayerSearchResponseBean> searchCall = client.getOlaPlayerSearch(model.getUrl(), model.getPassword(), model.getContentType(),
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
                            liveDataPlrSearch.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataPlrSearch.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataPlrSearch.postValue(null);
                    return;
                }

                Log.i("log", "Ola Player Search Report API Response: " + new Gson().toJson(response.body()));
                liveDataPlrSearch.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaPlayerSearchResponseBean> call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Log.e("log", "Ola Player Search Report API failed: " + throwable.toString());
                liveDataPlrSearch.postValue(null);
            }
        });
    }

}
