package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.ola.OrgUserResponseBeanNew;
import com.skilrock.retailapp.models.rms.GetUserSearchRequestBean;
import com.skilrock.retailapp.models.rms.SettlementListResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettlementReportViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<OrgUserResponseBeanNew> liveDataOrgUser = new MutableLiveData<>();
    private MutableLiveData<SettlementListResponseBean> liveDataSettlementList = new MutableLiveData<>();

    public MutableLiveData<OrgUserResponseBeanNew> getLiveDataOrgUser() {
        return liveDataOrgUser;
    }

    public MutableLiveData<SettlementListResponseBean> getLiveDataSettlementList() {
        return liveDataSettlementList;
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

    public void callGetSettlementListApi(String token, String url, String userId, String fromDate, String toDate, String languageCode, String appType) {
        final Call<SettlementListResponseBean> paymentReportCall = apiClient.getSettlementList(url, token, userId, fromDate,toDate, languageCode, appType);

        Log.d("log", "SettlementList Request : " + paymentReportCall.request().toString());

        paymentReportCall.enqueue(new Callback<SettlementListResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SettlementListResponseBean> call, @NonNull Response<SettlementListResponseBean> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "SettlementList API Failed : " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SettlementListResponseBean model;
                            model = gson.fromJson(errorResponse, SettlementListResponseBean.class);
                            liveDataSettlementList.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataSettlementList.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataSettlementList.postValue(null);
                    return;
                }
                Log.i("log", "SettlementList API Response : " + new Gson().toJson(response.body()));
                liveDataSettlementList.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SettlementListResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SettlementList API failed: " + throwable.toString());
                liveDataSettlementList.postValue(null);
            }
        });
    }
}
