package com.skilrock.retailapp.viewmodels.drawgames;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataRequestBean;
import com.skilrock.retailapp.models.drawgames.DrawSchemaByGameResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleRequestBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.QuickPickRequestBean;
import com.skilrock.retailapp.models.drawgames.QuickPickResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LuckySixViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);
    public static String SALE_RESONSE_TIME = "";
    private MutableLiveData<DrawSchemaByGameResponseBean> liveData = new MutableLiveData<>();
    private MutableLiveData<QuickPickResponseBean> liveDataQP = new MutableLiveData<>();
    private MutableLiveData<FiveByNinetySaleResponseBean> liveDataSale = new MutableLiveData<>();

    public MutableLiveData<DrawSchemaByGameResponseBean> getLiveData() {
        return liveData;
    }
    public MutableLiveData<QuickPickResponseBean> getLiveDataQP() {
        return liveDataQP;
    }
    public MutableLiveData<FiveByNinetySaleResponseBean> getLiveDataSale() {
        return liveDataSale;
    }

    public void callSchemaByGame(UrlDrawGameBean headerModel, DrawFetchGameDataRequestBean bodyModel, String gameCode) {

        final Call<DrawSchemaByGameResponseBean> apiCall = client.fetchSchemaByGame(headerModel.getUrl(),
                headerModel.getContentType(), headerModel.getUserName(), headerModel.getPassword(), gameCode);

        Log.d("log", "SchemaByGame Request: " + apiCall.request().toString());

        apiCall.enqueue(new Callback<DrawSchemaByGameResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<DrawSchemaByGameResponseBean> call, @NonNull Response<DrawSchemaByGameResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "SchemaByGame API Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                DrawSchemaByGameResponseBean model;
                                model= gson.fromJson(errorResponse, DrawSchemaByGameResponseBean.class);
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

                Log.i("log", "SchemaByGame API Response: " + new Gson().toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<DrawSchemaByGameResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SchemaByGame API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });
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
                                liveDataQP.postValue(model);
                            } catch (JsonSyntaxException e) {
                                liveDataQP.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataQP.postValue(null);
                        return;
                    }
                }

                Log.i("log", "Quick Pick API Response: " + new Gson().toJson(response.body()));
                liveDataQP.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<QuickPickResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "QuickPick API failed: " + throwable.toString());
                liveDataQP.postValue(null);
            }
        });
    }

    public void callSaleApi(UrlDrawGameBean headerModel, FiveByNinetySaleRequestBean model) {

        final Call<FiveByNinetySaleResponseBean> apiCall = client.fiveByNinetySale(headerModel.getUrl(),
                headerModel.getContentType(), headerModel.getUserName(), headerModel.getPassword(), model);

        Log.d("log", "FiveByNinety Sale Request: " + apiCall.request().toString());

        apiCall.enqueue(new Callback<FiveByNinetySaleResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<FiveByNinetySaleResponseBean> call, @NonNull Response<FiveByNinetySaleResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "FiveByNinety-Sale API Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                FiveByNinetySaleResponseBean model;
                                model= gson.fromJson(errorResponse, FiveByNinetySaleResponseBean.class);
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
                }

                long tx = response.raw().sentRequestAtMillis();
                long rx = response.raw().receivedResponseAtMillis();

                Double seconds = (double) (rx - tx) / 1000;
                int mins = (int) Math.round(seconds / 60);
                seconds = seconds - mins * 60;

                System.out.println("sale response time : " + mins + " min " + Utils.getFormattedAmount(seconds) + " sec");
                SALE_RESONSE_TIME = mins + " min " + Utils.getFormattedAmount(seconds) + " sec";
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

    public static String getSaleResonseTime() {
        return SALE_RESONSE_TIME;
    }
}
