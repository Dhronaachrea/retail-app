package com.skilrock.retailapp.viewmodels.drawgames;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleRequestBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawGamePurchaseViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<FiveByNinetySaleResponseBean> liveDataSale = new MutableLiveData<>();

    public MutableLiveData<FiveByNinetySaleResponseBean> getLiveDataSale() {
        return liveDataSale;
    }

    public void callSaleApi(UrlDrawGameBean headerModel, FiveByNinetySaleRequestBean model) {

        final Call<FiveByNinetySaleResponseBean> apiCall = client.fiveByNinetySale(headerModel.getUrl(), headerModel.getContentType(), headerModel.getUserName(), headerModel.getPassword(), model);

        Log.d("log", "FiveByNinety Sale Request: " + apiCall.request().toString());

        apiCall.enqueue(new Callback<FiveByNinetySaleResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<FiveByNinetySaleResponseBean> call, @NonNull Response<FiveByNinetySaleResponseBean> response) {
                if (response.body() == null || !response.isSuccessful() || response.code() != 200) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "FiveByNinety-Sale API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            FiveByNinetySaleResponseBean model;
                            model = gson.fromJson(errorResponse, FiveByNinetySaleResponseBean.class);
                            liveDataSale.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataSale.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataSale.postValue(null);
                    return;
                }

                Log.i("log", "FiveByNinety Sale API Response: " + new Gson().toJson(response.body()));
                liveDataSale.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<FiveByNinetySaleResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "FiveByNinety Sale API failed: " + throwable.toString());
                liveDataSale.postValue(null);
            }
        });
    }

}
