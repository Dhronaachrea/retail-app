package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.BetDeviceRequestBean;
import com.skilrock.retailapp.models.BetDeviceResponseBean;
import com.skilrock.retailapp.models.FieldX.FieldXChallanBean;
import com.skilrock.retailapp.models.FieldX.FieldxRetailerResponseBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.ConfigurationResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.SharedPrefUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<ConfigurationResponseBean> liveDataConfig = new MutableLiveData<>();
    private MutableLiveData<LoginBean> liveDataBalance = new MutableLiveData<>();

    public MutableLiveData<ConfigurationResponseBean> getLiveDataConfig() { return liveDataConfig; }

    private MutableLiveData<HomeDataBean> liveHomeData = new MutableLiveData<>();
    private MutableLiveData<BetDeviceResponseBean> liveDataBetGame = new MutableLiveData<>();
    private MutableLiveData<FieldxRetailerResponseBean> liveFieldxRetailer = new MutableLiveData<>();
    private MutableLiveData<FieldXChallanBean> liveFieldxChallan = new MutableLiveData<>();

    public MutableLiveData<FieldXChallanBean> getLiveFieldxChallan() {
        return liveFieldxChallan;
    }

    public MutableLiveData<FieldxRetailerResponseBean> getLiveFieldxRetailer() {
        return liveFieldxRetailer;
    }

    public MutableLiveData<BetDeviceResponseBean> getLiveDataBetGame() {
        return liveDataBetGame;
    }

    public MutableLiveData<HomeDataBean> getLiveHomeData() {
        return liveHomeData;
    }

    public MutableLiveData<LoginBean> getLiveDataBalance() { return liveDataBalance; }

    public void getHomeModuleList(String token, String userId, Context context) {

        Call<HomeDataBean> homeModulesBeanCall = apiClient.getMainModules(token, userId, AppConstants.appType, AppConstants.engineCode,
                SharedPrefUtil.getLanguage(context));

        Log.d("log", "HomeModule Request: " + homeModulesBeanCall.request().toString());

        homeModulesBeanCall.enqueue(new Callback<HomeDataBean>() {
            @Override
            public void onResponse(@NonNull Call<HomeDataBean> call, @NonNull Response<HomeDataBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "HomeModule API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            HomeDataBean model;
                            model= gson.fromJson(errorResponse, HomeDataBean.class);
                            liveHomeData.postValue(model);
                        } catch (IOException e) {
                            Log.e("log", "HomeModule API Failed: " + e.getMessage());
                            liveHomeData.postValue(null);
                        }
                        return;
                    }
                    liveHomeData.postValue(null);
                    return;
                }

                Log.i("log", "HomeModule Response: " + new Gson().toJson(response.body()));
                printLog(new Gson().toJson(response.body()));
                liveHomeData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<HomeDataBean> call, @NonNull Throwable throwable) {
                Log.e("log", "HomeModule Failed: " + throwable.getMessage());
                liveHomeData.postValue(null);
            }
        });
    }

    void printLog(String message) {
        int i = 0;

        int length = message.length();
        while (i < length) {
            int newline = message.indexOf('\n', i);
//            newline = if (newline != -1) newline else length
            if (newline != -1) {
                newline = newline;
            } else
                newline = length;
            do {
                int end = Math.min(newline, i + 4000);
                Log.i("OkHttp", message.substring(i, end));
                /*if (i == 0)
                    Log.i("OkHttp", "[Response for $url] -> ${message.substring(i, end)}")
                else
                    Log.i("OkHttp", message.substring(i, end))*/
                i = end;
            } while (i < newline);
            i++;
        }
    }

    public void getConfigApi(String url, String authToken, long domain) {
        Call<ConfigurationResponseBean> loginCall = apiClient.getConfig(url, authToken, domain);

        Log.d("log", "Config Request: " + loginCall.request().toString());

        loginCall.enqueue(new Callback<ConfigurationResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<ConfigurationResponseBean> call, @NonNull Response<ConfigurationResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Config API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ConfigurationResponseBean model;
                            model = gson.fromJson(errorResponse, ConfigurationResponseBean.class);
                            liveDataConfig.postValue(model);
                        } catch (Exception e) {
                            liveDataConfig.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataConfig.postValue(null);
                    return;
                }

                Log.i("log", "Config API Response: " + new Gson().toJson(response.body()));
                liveDataConfig.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ConfigurationResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Config API failed: " + throwable.toString());
                liveDataConfig.postValue(null);
            }
        });
    }

    public void getUpdatedBalance(final String authToken) {
        Call<LoginBean> loginCall = apiClient.getLogin(authToken);

        Log.d("log", "Login Request: " + loginCall.request().toString());

        loginCall.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(@NonNull Call<LoginBean> call, @NonNull Response<LoginBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Login API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            LoginBean model;
                            model= gson.fromJson(errorResponse, LoginBean.class);
                            liveDataBalance.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataBalance.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataBalance.postValue(null);
                    return;
                }

                Log.i("log", "Login API Response: " + new Gson().toJson(response.body()));
                if (response.body().getResponseCode() == 0 && response.body().getResponseData().getStatusCode() == 0)
                    response.body().setToken(authToken);
                liveDataBalance.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LoginBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Login API failed: " + throwable.toString());
                liveDataBalance.postValue(null);
            }
        });
    }

    public void getLiveBetData(String url, BetDeviceRequestBean model, String displayName) {
        Call<BetDeviceResponseBean> callBetGame = apiClient.betDeviceVerify(url + "/", PlayerData.getInstance().getToken(), model);

        Log.d("log", "Bet Game Request: " + callBetGame.request().toString());

        callBetGame.enqueue(new Callback<BetDeviceResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<BetDeviceResponseBean> call, @NonNull Response<BetDeviceResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "BetGame API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            BetDeviceResponseBean model;
                            model= gson.fromJson(errorResponse, BetDeviceResponseBean.class);
                            liveDataBetGame.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataBetGame.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataBetGame.postValue(null);
                    return;
                }

                if (response.body().getResponseCode() == 0 && response.body().getResponseData().getStatusCode() == 0)
                    response.body().setDisplayName(displayName);
                Log.i("log", "BetGame Response: " + new Gson().toJson(response.body()));
                liveDataBetGame.postValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<BetDeviceResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "BetGame API failed: " + throwable.toString());
                liveDataBetGame.postValue(null);
            }
        });
    }

    public void getRetailerApi(UrlBean urlBean) {
        Call<FieldxRetailerResponseBean> callFieldxRetailer = apiClient.getFieldXRetailer(urlBean.getUrl(), PlayerData.getInstance().getToken(), PlayerData.getInstance().getUserId(), "Not assignment");
        Log.d("log", "Retailer request" + callFieldxRetailer.request().toString());
        callFieldxRetailer.enqueue(new Callback<FieldxRetailerResponseBean>() {
            @Override
            public void onResponse(Call<FieldxRetailerResponseBean> call, Response<FieldxRetailerResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Fieldx Retailer API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            FieldxRetailerResponseBean bean = gson.fromJson(errorResponse, FieldxRetailerResponseBean.class);
                            liveFieldxRetailer.postValue(bean);
                        } catch (IOException e) {
                            liveFieldxRetailer.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveFieldxRetailer.postValue(null);
                    return;
                }
                Log.i("log", "FieldxRetailer Response: " + new Gson().toJson(response.body()));
                liveFieldxRetailer.postValue(response.body());
            }

            @Override
            public void onFailure(Call<FieldxRetailerResponseBean> call, Throwable t) {
                Log.e("log", "FieldxRetailer API failed: " + t.toString());
                liveFieldxRetailer.postValue(null);
            }
        });
    }

    public void getFieldxChallanApi(UrlBean urlBean) {
        Call<FieldXChallanBean> callFieldxRetailer = apiClient.getRetailerChallan(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), PlayerData.getInstance().getUsername(), PlayerData.getInstance().getToken().replace("Bearer",""));
        Log.d("log", "FieldxChallan request" + callFieldxRetailer.request().toString());
        callFieldxRetailer.enqueue(new Callback<FieldXChallanBean>() {
            @Override
            public void onResponse(Call<FieldXChallanBean> call, Response<FieldXChallanBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Fieldx Challan API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            FieldXChallanBean bean = gson.fromJson(errorResponse, FieldXChallanBean.class);
                            liveFieldxChallan.postValue(bean);
                        } catch (IOException e) {
                            liveFieldxChallan.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveFieldxChallan.postValue(null);
                    return;
                }
                Log.i("log", "FieldxChallan Response: " + new Gson().toJson(response.body()));
                liveFieldxChallan.postValue(response.body());
            }

            @Override
            public void onFailure(Call<FieldXChallanBean> call, Throwable t) {
                Log.e("log", "FieldxChallan API failed: " + t.toString());
                liveFieldxChallan.postValue(null);
            }
        });
    }
}
