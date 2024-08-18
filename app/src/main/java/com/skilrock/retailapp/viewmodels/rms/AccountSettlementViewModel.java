package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.SettleUserAccountRequestBean;
import com.skilrock.retailapp.models.SimpleRmsResponseBean;
import com.skilrock.retailapp.models.UserAccountResponseBean;
import com.skilrock.retailapp.models.ola.OrgUserResponseBeanNew;
import com.skilrock.retailapp.models.rms.GetUserSearchRequestBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountSettlementViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OrgUserResponseBeanNew> liveDataOrgUser = new MutableLiveData<>();
    private MutableLiveData<UserAccountResponseBean> liveDataUserAccount = new MutableLiveData<>();
    private MutableLiveData<SimpleRmsResponseBean> liveSettleUserAccount = new MutableLiveData<>();
    private MutableLiveData<SimpleRmsResponseBean> liveSettleLater = new MutableLiveData<>();

    public MutableLiveData<OrgUserResponseBeanNew> getLiveDataOrgUser() {
        return liveDataOrgUser;
    }

    public MutableLiveData<SimpleRmsResponseBean> getLiveSettleUserAccount() {
        return liveSettleUserAccount;
    }
    public MutableLiveData<SimpleRmsResponseBean> getLiveSettleLater() {
        return liveSettleLater;
    }


    public MutableLiveData<UserAccountResponseBean> getUserAccountLiveData() {
        return liveDataUserAccount;
    }

    public void callGetOrgUserApi(String token, String url, long orgId, long domainId) {
        GetUserSearchRequestBean searchUserRequestBean = new GetUserSearchRequestBean();
        searchUserRequestBean.setOrgId(orgId);
        searchUserRequestBean.setDomainId(domainId);

        final Call<OrgUserResponseBeanNew> paymentReportCall = apiClient.getOrgUser(url, token, searchUserRequestBean);

        Log.d("log", "OrgUser Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<OrgUserResponseBeanNew>() {
            @Override
            public void onResponse(@NonNull Call<OrgUserResponseBeanNew> call, @NonNull Response<OrgUserResponseBeanNew> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "OrgUser API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            OrgUserResponseBeanNew model;
                            model = gson.fromJson(errorResponse, OrgUserResponseBeanNew.class);
                            liveDataOrgUser.postValue(model);
                        } catch (IOException e) {
                            liveDataOrgUser.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataOrgUser.postValue(null);
                    return;
                }
                Log.i("log", "OrgUser API Response : " + new Gson().toJson(response.body()));
                liveDataOrgUser.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OrgUserResponseBeanNew> call, @NonNull Throwable throwable) {
                Log.e("log", "OrgUser API failed: " + throwable.toString());
                liveDataOrgUser.postValue(null);
            }
        });
    }

    public void callGetUserAccountApi(String token, String url, String userId, String languageCode, String appType) {
        final Call<UserAccountResponseBean> paymentReportCall = apiClient.getUserAccount(url, token, userId, languageCode, appType);

        Log.d("log", "UserAccount Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<UserAccountResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<UserAccountResponseBean> call, @NonNull Response<UserAccountResponseBean> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "UserAccount API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            UserAccountResponseBean model;
                            model = gson.fromJson(errorResponse, UserAccountResponseBean.class);
                            liveDataUserAccount.postValue(model);
                        } catch (IOException e) {
                            liveDataUserAccount.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataUserAccount.postValue(null);
                    return;
                }
                Log.i("log", "UserAccount API Response : " + new Gson().toJson(response.body()));
                liveDataUserAccount.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserAccountResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "UserAccount API failed: " + throwable.toString());
                liveDataUserAccount.postValue(null);
            }
        });
    }

    public void callSettleUserAccountApi(String token, String url, SettleUserAccountRequestBean settleUserAccountRequestBean) {
        final Call<SimpleRmsResponseBean> paymentReportCall = apiClient.settleUserAccount(url, token, settleUserAccountRequestBean);

        Log.d("log", "SettleUserAccount Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<SimpleRmsResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Response<SimpleRmsResponseBean> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "SettleUserAccount API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SimpleRmsResponseBean model;
                            model = gson.fromJson(errorResponse, SimpleRmsResponseBean.class);
                            liveSettleUserAccount.postValue(model);
                        } catch (IOException e) {
                            liveSettleUserAccount.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataUserAccount.postValue(null);
                    return;
                }
                Log.i("log", "SettleUserAccount API Response : " + new Gson().toJson(response.body()));
                liveSettleUserAccount.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SettleUserAccount API failed: " + throwable.toString());
                liveSettleUserAccount.postValue(null);
            }
        });
    }

    public void callSettleLaterApi(String token, String url, SettleUserAccountRequestBean settleUserAccountRequestBean) {
        final Call<SimpleRmsResponseBean> paymentReportCall = apiClient.settleUserAccount(url, token, settleUserAccountRequestBean);

        Log.d("log", "SettleLater Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<SimpleRmsResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Response<SimpleRmsResponseBean> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "SettleLater API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SimpleRmsResponseBean model;
                            model = gson.fromJson(errorResponse, SimpleRmsResponseBean.class);
                            liveSettleLater.postValue(model);
                        } catch (IOException e) {
                            liveSettleLater.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveSettleLater.postValue(null);
                    return;
                }
                Log.i("log", "SettleLater API Response : " + new Gson().toJson(response.body()));
                liveSettleLater.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SettleLater API failed: " + throwable.toString());
                liveSettleLater.postValue(null);
            }
        });
    }
}
