package com.skilrock.retailapp.viewmodels.FieldXViewModel;

import android.annotation.SuppressLint;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.FieldX.FieldXChallanBean;
import com.skilrock.retailapp.models.FieldX.FieldXRetailerBean;
import com.skilrock.retailapp.models.FieldX.FieldXUrlBean;
import com.skilrock.retailapp.models.FieldX.FieldxCollectionReportResponseBean;
import com.skilrock.retailapp.models.FieldX.FieldxDoPaymentBean;
import com.skilrock.retailapp.models.FieldX.FieldxDoPaymentRequestBean;
import com.skilrock.retailapp.models.FieldX.FieldxGetPayAmountBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FieldXAllTaskViewModel extends ViewModel {
    private APIClient client = APIConfig.getInstance().create(APIClient.class);
    private MutableLiveData<FieldXChallanBean> retailerChallan = new MutableLiveData<>();
    private MutableLiveData<FieldXRetailerBean> fieldXRetailer = new MutableLiveData<>();
    private MutableLiveData<FieldxGetPayAmountBean> fieldxGetPayAmount = new MutableLiveData<>();
    private MutableLiveData<FieldxDoPaymentBean> fieldxDoPaymentBeanMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<FieldxCollectionReportResponseBean> fieldxCollectionReportResponseBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<FieldxCollectionReportResponseBean> getFieldxCollectionReportResponseBeanMutableLiveData() {
        return fieldxCollectionReportResponseBeanMutableLiveData;
    }

    public MutableLiveData<FieldxDoPaymentBean> getFieldxDoPaymentBeanMutableLiveData() {
        return fieldxDoPaymentBeanMutableLiveData;
    }

    public MutableLiveData<FieldxGetPayAmountBean> getFieldxGetPayAmount() {
        return fieldxGetPayAmount;
    }

    public MutableLiveData<FieldXRetailerBean> getFieldXRetailer() {
        return fieldXRetailer;
    }

    public MutableLiveData<FieldXChallanBean> getRetailerChallan() {
        return retailerChallan;
    }

    public void getFieldXChallan(FieldXUrlBean urlBean, String userName, String userSessionId) {
        Call<FieldXChallanBean> call = client.getRetailerChallan(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), userName, userSessionId);

        Log.d("FieldX Challan Request", call.request().toString());

        call.enqueue(new Callback<FieldXChallanBean>() {
            @Override
            public void onResponse(Call<FieldXChallanBean> call, Response<FieldXChallanBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "ReturnList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            FieldXChallanBean model;
                            model = gson.fromJson(errorResponse, FieldXChallanBean.class);
                            retailerChallan.postValue(model);
                        } catch (JsonSyntaxException e) {
                            retailerChallan.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    retailerChallan.postValue(null);
                    return;
                }

                Log.i("log", "ReturnList API Response: " + new Gson().toJson(response.body()));
                FieldXChallanBean challanBean = response.body();
                if (challanBean == null)
                    retailerChallan.postValue(null);
                else
                    retailerChallan.postValue(challanBean);
            }
            @Override
            public void onFailure(Call<FieldXChallanBean> call, Throwable t) {
                Log.e("FieldX challan Response", t.getMessage());
                retailerChallan.postValue(null);
            }
        });
    }

    public void getFieldXRetailer(String Authorization, String mode) {
        Call<FieldXRetailerBean> call = client.getFieldXRetailer(Authorization, PlayerData.getInstance().getUserId(),mode);

        Log.d("FieldX Retailer Request", call.request().toString());

        call.enqueue(new Callback<FieldXRetailerBean>() {
            @Override
            public void onResponse(Call<FieldXRetailerBean> call, Response<FieldXRetailerBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Retailer API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            FieldXRetailerBean model;
                            model = gson.fromJson(errorResponse, FieldXRetailerBean.class);
                            fieldXRetailer.postValue(model);
                        } catch (JsonSyntaxException e) {
                            fieldXRetailer.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    fieldXRetailer.postValue(null);
                    return;
                }

                Log.i("log", "Retailer API Response: " + new Gson().toJson(response.body()));
                FieldXRetailerBean retailerBean = response.body();
                if (retailerBean == null)
                    fieldXRetailer.postValue(null);
                else
                    fieldXRetailer.postValue(retailerBean);
            }
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<FieldXRetailerBean> call, Throwable t) {
                Log.e("FieldX Retailer Response", t.getMessage());
                fieldXRetailer.postValue(null);
            }
        });
    }

    @SuppressLint("LongLogTag")
    public void getFieldXPayAmount(UrlBean urlBean, String userSessionId, String orgId) {
        Call<FieldxGetPayAmountBean> call = client.fieldxGetPayAmount(urlBean.getUrl(), userSessionId, orgId);

        Log.d("FieldX GetPayAmount Request", call.request().toString());

        call.enqueue(new Callback<FieldxGetPayAmountBean>() {
            @Override
            public void onResponse(Call<FieldxGetPayAmountBean> call, Response<FieldxGetPayAmountBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "ReturnList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            FieldxGetPayAmountBean model;
                            model = gson.fromJson(errorResponse, FieldxGetPayAmountBean.class);
                            fieldxGetPayAmount.postValue(model);
                        } catch (JsonSyntaxException e) {
                            fieldxGetPayAmount.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    fieldxGetPayAmount.postValue(null);
                    return;
                }

                Log.i("log", "FieldxGetPayAmount API Response: " + new Gson().toJson(response.body()));
                FieldxGetPayAmountBean getPayAmountBean = response.body();
                if (getPayAmountBean == null)
                    fieldxGetPayAmount.postValue(null);
                else
                    fieldxGetPayAmount.postValue(getPayAmountBean);
            }
            @Override
            public void onFailure(Call<FieldxGetPayAmountBean> call, Throwable t) {
                Log.e("FieldX GetPayAmount Response", t.getMessage());
                fieldxGetPayAmount.postValue(null);
            }
        });
    }

    @SuppressLint("LongLogTag")
    public void getFieldXDoPayment(UrlBean urlBean, FieldxDoPaymentRequestBean fieldxDoPaymentRequestBean) {
        Call<FieldxDoPaymentBean> call = client.fieldxDoPayment(urlBean.getUrl(), PlayerData.getInstance().getToken(), fieldxDoPaymentRequestBean);

        Log.d("FieldX DoPayment Request", call.request().toString());

        call.enqueue(new Callback<FieldxDoPaymentBean>() {
            @Override
            public void onResponse(Call<FieldxDoPaymentBean> call, Response<FieldxDoPaymentBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "FieldxDoPayment API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            FieldxDoPaymentBean model;
                            model = gson.fromJson(errorResponse, FieldxDoPaymentBean.class);
                            fieldxDoPaymentBeanMutableLiveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            fieldxDoPaymentBeanMutableLiveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    fieldxDoPaymentBeanMutableLiveData.postValue(null);
                    return;
                }

                Log.i("log", "FieldxDoPayment API Response: " + new Gson().toJson(response.body()));
                FieldxDoPaymentBean fieldxDoPaymentBean = response.body();
                if (fieldxDoPaymentBean == null)
                    fieldxDoPaymentBeanMutableLiveData.postValue(null);
                else
                    fieldxDoPaymentBeanMutableLiveData.postValue(fieldxDoPaymentBean);
            }
            @Override
            public void onFailure(Call<FieldxDoPaymentBean> call, Throwable t) {
                Log.e("FieldX DoPayment Response", t.getMessage());
                fieldxDoPaymentBeanMutableLiveData.postValue(null);
            }
        });
    }

    @SuppressLint("LongLogTag")
    public void getFieldXCollectionReport(String url, String startDate, String endDate) {
        Call<FieldxCollectionReportResponseBean> call = client.fieldxCollectionReport(url, PlayerData.getInstance().getToken(), startDate, endDate);

        Log.d("FieldX CollectionReport Request", call.request().toString());

        call.enqueue(new Callback<FieldxCollectionReportResponseBean>() {
            @Override
            public void onResponse(Call<FieldxCollectionReportResponseBean> call, Response<FieldxCollectionReportResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "FieldxCollectionReport API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            FieldxCollectionReportResponseBean model;
                            model = gson.fromJson(errorResponse, FieldxCollectionReportResponseBean.class);
                            fieldxCollectionReportResponseBeanMutableLiveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            fieldxCollectionReportResponseBeanMutableLiveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    fieldxCollectionReportResponseBeanMutableLiveData.postValue(null);
                    return;
                }

                Log.i("log", "FieldxCollectionReport API Response: " + new Gson().toJson(response.body()));
                FieldxCollectionReportResponseBean fieldxCollectionReportResponseBean = response.body();
                if (fieldxCollectionReportResponseBean == null)
                    fieldxCollectionReportResponseBeanMutableLiveData.postValue(null);
                else
                    fieldxCollectionReportResponseBeanMutableLiveData.postValue(fieldxCollectionReportResponseBean);
            }
            @Override
            public void onFailure(Call<FieldxCollectionReportResponseBean> call, Throwable t) {
                Log.e("FieldX CollectionReport Response", t.getMessage());
                fieldxCollectionReportResponseBeanMutableLiveData.postValue(null);
            }
        });
    }
}
