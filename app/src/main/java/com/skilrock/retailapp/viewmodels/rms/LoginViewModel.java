package com.skilrock.retailapp.viewmodels.rms;

import static com.skilrock.retailapp.utils.UtilsKt.md5Convert;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.models.ResponseCodeMessageBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.rms.TokenBean;
import com.skilrock.retailapp.models.rms.VerifyPosRequestBean;
import com.skilrock.retailapp.models.rms.VerifyPosResponseBean;
import com.skilrock.retailapp.models.scratch.SaleTicketRequestBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<TokenBean> liveDataToken = new MutableLiveData<>();
    private MutableLiveData<LoginBean> liveDataLogin = new MutableLiveData<>();
    private MutableLiveData<VerifyPosResponseBean> liveDataVerifyPos = new MutableLiveData<>();
    String encodedValueUsername = "";
    String encodedValuePassword = "";
    public MutableLiveData<VerifyPosResponseBean> getLiveDataVerifyPos() {
        return liveDataVerifyPos;
    }
    private MutableLiveData<ResponseCodeMessageBean> responseData = new MutableLiveData<>();

    public MutableLiveData<ResponseCodeMessageBean> getResponseData() {
        return responseData;
    }
    public MutableLiveData<TokenBean> getLiveDataToken() {
        return liveDataToken;
    }

    public MutableLiveData<LoginBean> getLiveDataLogin() {
        return liveDataLogin;
    }


    public void callSaleTicket(String url, String ticketNumber) {
        Log.i("log", "Ticket Number: " + ticketNumber);
        Log.i("TaG", "Ticket-------------------------------------");

        SaleTicketRequestBean saleTicketRequestBean = new SaleTicketRequestBean();
        saleTicketRequestBean.setUserName("mridulret");
        saleTicketRequestBean.setGameType(AppConstants.SCRATCH);
        saleTicketRequestBean.setModelCode(Utils.getDeviceModelCode());
        saleTicketRequestBean.setTerminalId(Utils.getDeviceSerialNumber());

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.GELSA_RETAIL)) {
            saleTicketRequestBean.setSoldChannel(AppConstants.PLATFORM_WEB);
        } else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
            saleTicketRequestBean.setSoldChannel(AppConstants.PLATFORM_WEB);
        } else {
            saleTicketRequestBean.setSoldChannel(AppConstants.PLATFORM);
        }
        saleTicketRequestBean.setTicketNumberList(new String[]{ticketNumber});

        Call<ResponseCodeMessageBean> callSaleTicketApi = client.getTicketSold(url, "RMS1", "13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU1", "application/json", saleTicketRequestBean);

        Log.d("log", "SaleTicket Request: " + callSaleTicketApi.request().toString());

        callSaleTicketApi.enqueue(new Callback<ResponseCodeMessageBean>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Response<ResponseCodeMessageBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "SaleTicket API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ResponseCodeMessageBean model;
                            model= gson.fromJson(errorResponse, ResponseCodeMessageBean.class);
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

                Log.i("log", "SaleTicket API Response: " + new Gson().toJson(response.body()));
                responseData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SaleTicket API failed: " + throwable.toString());
                responseData.postValue(null);
            }
        });
    }

    public void callRetailerApi(final String username, final String password) {

        final Call<TokenBean> tokenCall = client.getToken(username, password, AppConstants.RMS, AppConstants.CLIENT_SCERET, "NA", "NA");
        Log.d("log", "Token Request: " + tokenCall.request().toString());

        tokenCall.enqueue(new Callback<TokenBean>() {
            @Override
            public void onResponse(@NonNull Call<TokenBean> call, @NonNull Response<TokenBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Token API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            TokenBean model;
                            model = gson.fromJson(errorResponse, TokenBean.class);
                            liveDataToken.postValue(model);
                        } catch (IOException e) {
                            liveDataToken.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataToken.postValue(null);
                    return;
                }

                Log.i("log", "Token API Response: " + new Gson().toJson(response.body()));
                liveDataToken.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TokenBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Token API failed: " + throwable.toString());
                liveDataToken.postValue(null);
            }
        });
    }

    public void callTokenApi(final String username, final String password, String terminalId, String modelCode) {

        try {
            encodedValueUsername = URLEncoder.encode(username, "UTF-8");
            encodedValuePassword = URLEncoder.encode(password, "UTF-8");
            //encodedValuePassword = md5Convert(password);

        } catch (UnsupportedEncodingException uee) { }

        final Call<TokenBean> tokenCall = client.getToken(encodedValueUsername, encodedValuePassword, AppConstants.RMS, AppConstants.CLIENT_SCERET, terminalId, modelCode);
        Log.d("log", "Token Request Header: " + tokenCall.request().headers().toString());
        Log.d("log", "Token Request: " + tokenCall.request().toString());

        tokenCall.enqueue(new Callback<TokenBean>() {
            @Override
            public void onResponse(@NonNull Call<TokenBean> call, @NonNull Response<TokenBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Token API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            TokenBean model;
                            model = gson.fromJson(errorResponse, TokenBean.class);
                            liveDataToken.postValue(model);
                        } catch (IOException e) {
                            liveDataToken.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataToken.postValue(null);
                    return;
                }

                Log.i("log", "Token API Response: " + new Gson().toJson(response.body()));
                liveDataToken.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TokenBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Token API failed: " + throwable.toString());
                liveDataToken.postValue(null);
            }
        });
    }

    public void callLoginApi(final String authToken) {
        Call<LoginBean> loginCall = client.getLogin(authToken);
        Log.d("log", "Login Header: " + loginCall.request().headers().toString());
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
                            model = gson.fromJson(errorResponse, LoginBean.class);
                            liveDataLogin.postValue(model);
                        } catch (IOException e) {
                            liveDataLogin.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataLogin.postValue(null);
                    return;
                }

                Log.i("log", "Login API Response: " + new Gson().toJson(response.body()));
                if (response.body().getResponseCode() == 0 && response.body().getResponseData().getStatusCode() == 0) {
                    response.body().setToken(authToken);
                }
                liveDataLogin.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LoginBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Login API failed: " + throwable.toString());
                liveDataLogin.postValue(null);
            }
        });
    }

    public void callVerifyPosApi(String token, VerifyPosRequestBean model) {

        Call<VerifyPosResponseBean> verifyPosResponseBeanCall = client.postVerifyPos(token, model);

        Log.d("log", "VerifyPos Request: " + verifyPosResponseBeanCall.request().toString());

        verifyPosResponseBeanCall.enqueue(new Callback<VerifyPosResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<VerifyPosResponseBean> call, @NonNull Response<VerifyPosResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "VerifyPos API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            VerifyPosResponseBean model;
                            model= gson.fromJson(errorResponse, VerifyPosResponseBean.class);
                            liveDataVerifyPos.postValue(model);
                        } catch (IOException e) {
                            Log.e("log", "VerifyPos API Failed: " + e.getMessage());
                            liveDataVerifyPos.postValue(null);
                        }
                        return;
                    }
                    liveDataVerifyPos.postValue(null);
                    return;
                }
                Log.i("log", "VerifyPos Response: " + new Gson().toJson(response.body()));
                liveDataVerifyPos.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<VerifyPosResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "VerifyPos Failed: " + throwable.getMessage());
                liveDataVerifyPos.postValue(null);
            }
        });
    }

    /*public void callConfigApi(final String authToken) {
        Call<LoginBean> loginCall = client.getLogin(authToken);

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
                            model = gson.fromJson(errorResponse, LoginBean.class);
                            liveDataLogin.postValue(model);
                        } catch (IOException e) {
                            liveDataLogin.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataLogin.postValue(null);
                    return;
                }

                Log.i("log", "Login API Response: " + new Gson().toJson(response.body()));
                if (response.body().getResponseCode() == 0 && response.body().getResponseData().getStatusCode() == 0)
                    response.body().setToken(authToken);
                liveDataLogin.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LoginBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Login API failed: " + throwable.toString());
                liveDataLogin.postValue(null);
            }
        });
    }*/

}
