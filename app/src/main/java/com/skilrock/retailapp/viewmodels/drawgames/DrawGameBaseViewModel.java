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
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleRequestBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketReprintBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawGameBaseViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<DrawFetchGameDataResponseBean> liveData = new MutableLiveData<>();
    private MutableLiveData<FiveByNinetySaleResponseBean> liveDataSaleSpinAndWin = new MutableLiveData<>();
    private MutableLiveData<DrawFetchGameDataResponseBean> liveDataTimer = new MutableLiveData<>();
    private MutableLiveData<LoginBean> liveDataBalance = new MutableLiveData<>();

    private MutableLiveData<FiveByNinetySaleResponseBean> liveDataReprint = new MutableLiveData<>();
    public MutableLiveData<FiveByNinetySaleResponseBean> getLiveDataReprint() {
        return liveDataReprint;
    }

    public MutableLiveData<FiveByNinetySaleResponseBean> getSpinAndWindSaleData() {
        return liveDataSaleSpinAndWin;
    }

    private MutableLiveData<TicketCancelResponseBean> liveDataCancel = new MutableLiveData<>();
    public MutableLiveData<TicketCancelResponseBean> getLiveDataCancel() {
        return liveDataCancel;
    }

    public MutableLiveData<DrawFetchGameDataResponseBean> getLiveData() {
        return liveData;
    }
    public MutableLiveData<DrawFetchGameDataResponseBean> getLiveDataTimer() {
        return liveDataTimer;
    }

    public MutableLiveData<LoginBean> getLiveDataBalance() { return liveDataBalance; }

    public void callFetchGameData(UrlDrawGameBean headerModel, DrawFetchGameDataRequestBean bodyModel) {

        final Call<DrawFetchGameDataResponseBean> apiCall = client.fetchGameData(headerModel.getUrl(),
                headerModel.getContentType(), headerModel.getUserName(), headerModel.getPassword(), bodyModel);

        Log.d("log", "FetchGameData Request: " + apiCall.request().toString());

        apiCall.enqueue(new Callback<DrawFetchGameDataResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<DrawFetchGameDataResponseBean> call, @NonNull Response<DrawFetchGameDataResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "FetchGameData API Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                DrawFetchGameDataResponseBean model;
                                model= gson.fromJson(errorResponse, DrawFetchGameDataResponseBean.class);
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

                //response.body().getResponseData().setCurrentDate("2020-02-05 20:07:30");

                Gson  gson = new GsonBuilder().disableHtmlEscaping().create();
                Log.i("log", "FetchGameData API Response: " + gson.toJson(response.body()));
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<DrawFetchGameDataResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "FetchGameData API failed: " + throwable.toString());
                liveData.postValue(null);
            }
        });
    }

    public void callFetchGameDataTimer(UrlDrawGameBean headerModel, DrawFetchGameDataRequestBean bodyModel) {

        final Call<DrawFetchGameDataResponseBean> apiCall = client.fetchGameData(headerModel.getUrl(),
                headerModel.getContentType(), headerModel.getUserName(), headerModel.getPassword(), bodyModel);

        Log.d("log", "FetchGameData Request: " + apiCall.request().toString());

        apiCall.enqueue(new Callback<DrawFetchGameDataResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<DrawFetchGameDataResponseBean> call, @NonNull Response<DrawFetchGameDataResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "FetchGameData API Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                DrawFetchGameDataResponseBean model;
                                model= gson.fromJson(errorResponse, DrawFetchGameDataResponseBean.class);
                                liveDataTimer.postValue(model);
                            } catch (JsonSyntaxException e) {
                                liveDataTimer.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataTimer.postValue(null);
                        return;
                    }
                }
                Gson  gson = new GsonBuilder().disableHtmlEscaping().create();

                Log.i("log", "FetchGameData API Response: " + gson.toJson(response.body()));
                liveDataTimer.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<DrawFetchGameDataResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "FetchGameData API failed: " + throwable.toString());
                liveDataTimer.postValue(null);
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
            }

            @Override
            public void onFailure(@NonNull Call<LoginBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Login API failed: " + throwable.toString());
                liveDataBalance.postValue(null);
            }
        });
    }

    public void callReprint(UrlDrawGameBean urlBean, TicketReprintBean ticketReprintBean) {

        final Call<FiveByNinetySaleResponseBean> apiCall = client.reprint(urlBean.getUrl(),
                urlBean.getContentType(), urlBean.getUserName(), urlBean.getPassword(), ticketReprintBean);
        Log.d("log", "Reprint response: " + apiCall.request().toString());
        apiCall.enqueue(new Callback<FiveByNinetySaleResponseBean>() {
            @Override
            public void onResponse(Call<FiveByNinetySaleResponseBean> call, Response<FiveByNinetySaleResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "Reprint Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                FiveByNinetySaleResponseBean model;
                                model = gson.fromJson(errorResponse, FiveByNinetySaleResponseBean.class);
                                liveDataReprint.postValue(model);
                            } catch (JsonSyntaxException e) {
                                liveDataReprint.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataReprint.postValue(null);
                        return;
                    }
                }

                Log.i("log", "Reprint: " + new Gson().toJson(response.body()));
                liveDataReprint.postValue(response.body());
            }
            @Override
            public void onFailure(Call<FiveByNinetySaleResponseBean> call, Throwable t) {
                liveDataReprint.postValue(null);

            }
        });

    }

    public void callCancel(UrlDrawGameBean urlBean, TicketCancelBean ticketCancelBean) {
        final Call<TicketCancelResponseBean> apiCall = client.cancelTicket(urlBean.getUrl(),
                urlBean.getContentType(), urlBean.getUserName(), urlBean.getPassword(), ticketCancelBean);
        Log.d("log", "Cancel response: " + apiCall.request().toString());
        apiCall.enqueue(new Callback<TicketCancelResponseBean>() {
            @Override
            public void onResponse(Call<TicketCancelResponseBean> call, Response<TicketCancelResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("log", "Cancel  Failed: " + errorResponse);
                                Gson gson = new GsonBuilder().create();
                                FiveByNinetySaleResponseBean model;
                                model = gson.fromJson(errorResponse, FiveByNinetySaleResponseBean.class);
                                liveDataReprint.postValue(model);
                            } catch (JsonSyntaxException e) {
                                liveDataReprint.postValue(null);
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        liveDataReprint.postValue(null);
                        return;
                    }
                }

                Log.i("log", "Cancel: " + new Gson().toJson(response.body()));
                liveDataCancel.postValue(response.body());
            }
            @Override
            public void onFailure(Call<TicketCancelResponseBean> call, Throwable t) {
                liveDataReprint.postValue(null);

            }
        });

    }

    public synchronized void callFetchGameDataSync(UrlDrawGameBean headerModel, DrawFetchGameDataRequestBean bodyModel, String gameCode) {
        Thread thread = new Thread(() -> {
            try {

                final Call<DrawFetchGameDataResponseBean> apiCall = client.fetchGameDataOther(headerModel.getUrl(),
                        headerModel.getContentType(), headerModel.getUserName(), headerModel.getPassword(), bodyModel);

                Log.d("log", "FetchGameDataOther Request: " + apiCall.request().toString() + "GAME CODE: " + gameCode);

                try {
                    Response<DrawFetchGameDataResponseBean> response = apiCall.execute();

                    if (response.body() == null || !response.isSuccessful()) {
                        if (response.body() == null || !response.isSuccessful()) {
                            if (response.errorBody() != null) {
                                try {
                                    String errorResponse = response.errorBody().string();
                                    Log.e("log", "FetchGameData API Failed: " + errorResponse);
                                    Gson gson = new GsonBuilder().create();
                                    DrawFetchGameDataResponseBean model;
                                    model = gson.fromJson(errorResponse, DrawFetchGameDataResponseBean.class);
                                    model.setUpdatingGameCode(gameCode);
                                    liveDataTimer.postValue(null);
                                } catch (JsonSyntaxException e) {
                                    liveDataTimer.postValue(null);
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                            liveDataTimer.postValue(null);
                            return;
                        }
                    }
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    response.body().setUpdatingGameCode(gameCode);
                    Log.i("log", "FetchGameData API Response: " + "GAME CODE:" + gameCode + ":" + gson.toJson(response.body()) + "GAME CODE:" + gameCode);
                    liveDataTimer.postValue(response.body());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e("log", "FetchGameData API failed: " + ex.toString());
                    liveDataTimer.postValue(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
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
                            liveDataSaleSpinAndWin.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataSaleSpinAndWin.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataSaleSpinAndWin.postValue(null);
                    return;
                }

                Log.i("log", "FiveByNinety Sale API Response: " + new Gson().toJson(response.body()));
                liveDataSaleSpinAndWin.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<FiveByNinetySaleResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "FiveByNinety Sale API failed: " + throwable.toString());
                liveDataSaleSpinAndWin.postValue(null);
            }
        });
    }
}
