package com.skilrock.retailapp.viewmodels.ola;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.ResponseCodeMessageBean;
import com.skilrock.retailapp.models.UrlOlaBean;
import com.skilrock.retailapp.models.ola.OlaOtpBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchResponseBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarRequestBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarResponseBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationRequestBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationResponseBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlaRegistrationViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<ResponseCodeMessageBean> liveDataOtp = new MutableLiveData<>();
    private MutableLiveData<ResponseCodeMessageBean> liveDataResendOtp = new MutableLiveData<>();
    private MutableLiveData<OlaRegistrationResponseBean> liveDataRegistration = new MutableLiveData<>();
    private MutableLiveData<OlaRegistrationMyanmarResponseBean> liveDataRegistrationMyanmar = new MutableLiveData<>();
    private MutableLiveData<OlaPlayerSearchResponseBean> liveDataPlrSearch = new MutableLiveData<>();
    private MutableLiveData<LoginBean> liveDataBalance = new MutableLiveData<>();

    public MutableLiveData<ResponseCodeMessageBean> getOtpLiveData() {
        return liveDataOtp;
    }
    public MutableLiveData<LoginBean> getLiveDataBalance() { return liveDataBalance; }

    public MutableLiveData<ResponseCodeMessageBean> getResendOtpLiveData() {
        return liveDataResendOtp;
    }

    public MutableLiveData<OlaRegistrationResponseBean> getLiveDataRegistration() {
        return liveDataRegistration;
    }

    public MutableLiveData<OlaRegistrationMyanmarResponseBean> getLiveDataRegistrationMyanmar() {
        return liveDataRegistrationMyanmar;
    }

    public MutableLiveData<OlaPlayerSearchResponseBean> getPlsSearchLiveData() {
        return liveDataPlrSearch;
    }

    public void callOtp(UrlOlaBean url, OlaOtpBean bean) {

        final Call<ResponseCodeMessageBean> otpCall = client.olaRegistrationOtp(url.getUrl(), url.getUserName(), url.getPassword(), url.getContentType(), url.getAccept(), bean);

        Log.d("log", "Ola OTP Request: " + otpCall.request().toString());

        otpCall.enqueue(new Callback<ResponseCodeMessageBean>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Response<ResponseCodeMessageBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola OTP API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ResponseCodeMessageBean model;
                            model= gson.fromJson(errorResponse, ResponseCodeMessageBean.class);
                            liveDataOtp.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataOtp.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataOtp.postValue(null);
                    return;
                }

                Log.i("log", "Ola OTP API Response: " + new Gson().toJson(response.body()));
                liveDataOtp.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola OTP API failed: " + throwable.toString());
                liveDataOtp.postValue(null);
            }
        });
    }

    public void callResendOtp(UrlOlaBean url, OlaOtpBean bean) {

        final Call<ResponseCodeMessageBean> otpCall = client.olaRegistrationOtp(url.getUrl(), url.getUserName(), url.getPassword(), url.getContentType(), url.getAccept(), bean);

        Log.d("log", "Ola Resend OTP Request: " + otpCall.request().toString());

        otpCall.enqueue(new Callback<ResponseCodeMessageBean>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Response<ResponseCodeMessageBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Resend OTP API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ResponseCodeMessageBean model;
                            model= gson.fromJson(errorResponse, ResponseCodeMessageBean.class);
                            liveDataResendOtp.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataResendOtp.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataResendOtp.postValue(null);
                    return;
                }

                Log.i("log", "Ola Resend OTP API Response: " + new Gson().toJson(response.body()));
                liveDataResendOtp.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Resend OTP API failed: " + throwable.toString());
                liveDataResendOtp.postValue(null);
            }
        });
    }

    public void callOlaRegistration(UrlOlaBean url, OlaRegistrationRequestBean bean) {

        final Call<OlaRegistrationResponseBean> registrationCall = client.postOlaRegistration(url.getUrl(), url.getUserName(), url.getPassword(), url.getContentType(), url.getAccept(), bean);

        Log.d("log", "Ola Registration Request: " + registrationCall.request().toString());

        registrationCall.enqueue(new Callback<OlaRegistrationResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaRegistrationResponseBean> call, @NonNull Response<OlaRegistrationResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Registration API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaRegistrationResponseBean model;
                            model= gson.fromJson(errorResponse, OlaRegistrationResponseBean.class);
                            liveDataRegistration.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataRegistration.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataRegistration.postValue(null);
                    return;
                }

                Log.i("log", "Ola Registration API Response: " + new Gson().toJson(response.body()));
                liveDataRegistration.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaRegistrationResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Registration API failed: " + throwable.toString());
                liveDataRegistration.postValue(null);
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

    public void getUpdatedBalance(final String authToken) {
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
                //liveDataBalance.postValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<LoginBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Login API failed: " + throwable.toString());
                liveDataBalance.postValue(null);
            }
        });
    }

    public void callOlaMyanmarRegistration(UrlOlaBean url, OlaRegistrationMyanmarRequestBean bean) {

        final Call<OlaRegistrationMyanmarResponseBean> registrationCall = client.postOlaMyanmarRegistration(url.getUrl(), url.getUserName(), url.getPassword(), url.getContentType(), url.getAccept(), bean);

        Log.d("log", "Ola Registration Request: " + registrationCall.request().toString());

        registrationCall.enqueue(new Callback<OlaRegistrationMyanmarResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<OlaRegistrationMyanmarResponseBean> call, @NonNull Response<OlaRegistrationMyanmarResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Ola Registration API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OlaRegistrationMyanmarResponseBean model;
                            model= gson.fromJson(errorResponse, OlaRegistrationMyanmarResponseBean.class);
                            liveDataRegistrationMyanmar.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataRegistrationMyanmar.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataRegistrationMyanmar.postValue(null);
                    return;
                }

                Log.i("log", "Ola Registration API Response: " + new Gson().toJson(response.body()));
                liveDataRegistrationMyanmar.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OlaRegistrationMyanmarResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Ola Registration API failed: " + throwable.toString());
                liveDataRegistrationMyanmar.postValue(null);
            }
        });
    }

}
