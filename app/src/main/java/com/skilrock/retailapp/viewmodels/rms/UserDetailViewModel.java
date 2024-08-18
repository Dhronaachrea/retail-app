package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.rms.CountryListResponseBean;
import com.skilrock.retailapp.models.SimpleRmsResponseBean;
import com.skilrock.retailapp.models.rms.StateAndCityListResponseBean;
import com.skilrock.retailapp.models.rms.UserRegistrationRequestBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.models.rms.UserDetailResponseBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<UserDetailResponseBean> userDetailLiveData = new MutableLiveData<>();
    private MutableLiveData<SimpleRmsResponseBean> blockUserLiveData = new MutableLiveData<>();
    private MutableLiveData<SimpleRmsResponseBean> unBlockUserLiveData = new MutableLiveData<>();
    private MutableLiveData<StateAndCityListResponseBean> stateAndCityListLiveData = new MutableLiveData<>();
    private MutableLiveData<CountryListResponseBean> countryList = new MutableLiveData<>();
    private MutableLiveData<UserDetailResponseBean> updateUserLiveData = new MutableLiveData<>();

    public MutableLiveData<UserDetailResponseBean> getUpdateUserLiveData(){
        return updateUserLiveData;
    }

    public MutableLiveData<CountryListResponseBean> getCountryList() {
        return countryList;
    }

    public MutableLiveData<UserDetailResponseBean> getUserDetailLiveData(){
        return userDetailLiveData;
    }
    public MutableLiveData<SimpleRmsResponseBean> getBlockUserLiveData(){
        return blockUserLiveData;
    }
    public MutableLiveData<SimpleRmsResponseBean> getUnblockUserLiveData(){
        return unBlockUserLiveData;
    }

    public MutableLiveData<StateAndCityListResponseBean> getStateAndCityListLiveData() {
        return stateAndCityListLiveData;
    }

    public void userDetailApi(String url, String token, String userId) {
        Call<UserDetailResponseBean> reportCall = apiClient.getUserDetails(url, token, userId);

        Log.d("log", "User Detail Request: " + reportCall.request().toString());

        reportCall.enqueue(new Callback<UserDetailResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<UserDetailResponseBean> call, @NonNull Response<UserDetailResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "User Detail Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            UserDetailResponseBean model;
                            model= gson.fromJson(errorResponse, UserDetailResponseBean.class);
                            userDetailLiveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            userDetailLiveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    userDetailLiveData.postValue(null);
                    return;
                }

                Log.i("log", "User Detail Response: " + new Gson().toJson(response.body()));
                userDetailLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserDetailResponseBean> call, @NonNull Throwable throwable) {
                //Log.e("log", "OLA Report failed: " + throwable.toString());
                throwable.printStackTrace();
                userDetailLiveData.postValue(null);
            }
        });
    }

    public void blockUserApi(String url, String token, String userId) {
        Call<SimpleRmsResponseBean> reportCall = apiClient.blockUser(url, token, userId);

        Log.d("log", "blockUser Request: " + reportCall.request().toString());

        reportCall.enqueue(new Callback<SimpleRmsResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Response<SimpleRmsResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "blockUser Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SimpleRmsResponseBean model;
                            model= gson.fromJson(errorResponse, SimpleRmsResponseBean.class);
                            blockUserLiveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            blockUserLiveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    blockUserLiveData.postValue(null);
                    return;
                }

                Log.i("log", "blockUser Response: " + new Gson().toJson(response.body()));
                blockUserLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Throwable throwable) {
                //Log.e("log", "OLA Report failed: " + throwable.toString());
                throwable.printStackTrace();
                blockUserLiveData.postValue(null);
            }
        });
    }

    public void unBlockUserApi(String url, String token, String userId) {
        Call<SimpleRmsResponseBean> reportCall = apiClient.unBlockUser(url, token, userId);

        Log.d("log", "Unblock Request: " + reportCall.request().toString());

        reportCall.enqueue(new Callback<SimpleRmsResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Response<SimpleRmsResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Unblock Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            SimpleRmsResponseBean model;
                            model= gson.fromJson(errorResponse, SimpleRmsResponseBean.class);
                            unBlockUserLiveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            unBlockUserLiveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    unBlockUserLiveData.postValue(null);
                    return;
                }

                Log.i("log", "Unblock Response: " + new Gson().toJson(response.body()));
                unBlockUserLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SimpleRmsResponseBean> call, @NonNull Throwable throwable) {
                //Log.e("log", "OLA Report failed: " + throwable.toString());
                throwable.printStackTrace();
                unBlockUserLiveData.postValue(null);
            }
        });
    }
    public void getStateAndCityList(String token, String url, String countryCode) {
        Call<StateAndCityListResponseBean> changePasswordCall = apiClient.getStateAndCityList(token, url, countryCode);

        Log.d("log", "getStateAndCityList Request: " + changePasswordCall.request().toString());

        changePasswordCall.enqueue(new Callback<StateAndCityListResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<StateAndCityListResponseBean> call, @NonNull Response<StateAndCityListResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "getStateAndCityList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            StateAndCityListResponseBean model;
                            model = gson.fromJson(errorResponse, StateAndCityListResponseBean.class);
                            stateAndCityListLiveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            stateAndCityListLiveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    stateAndCityListLiveData.postValue(null);
                    return;
                }

                Log.i("log", "getStateAndCityList Response: " + new Gson().toJson(response.body()));
                stateAndCityListLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<StateAndCityListResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "getStateAndCityList failed: " + throwable.toString());
                stateAndCityListLiveData.postValue(null);
            }
        });
    }


    public void getCountryList(String token, String url) {
        Call<CountryListResponseBean> changePasswordCall = apiClient.getCountryList(token, url);

        Log.d("log", "getCountryList Request: " + changePasswordCall.request().toString());

        changePasswordCall.enqueue(new Callback<CountryListResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<CountryListResponseBean> call, @NonNull Response<CountryListResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "getCountryList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            CountryListResponseBean model;
                            model = gson.fromJson(errorResponse, CountryListResponseBean.class);
                            countryList.postValue(model);
                        } catch (JsonSyntaxException e) {
                            countryList.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    countryList.postValue(null);
                    return;
                }

                Log.i("log", "getCountryList Response: " + new Gson().toJson(response.body()));
                countryList.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CountryListResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "getCountryList failed: " + throwable.toString());
                countryList.postValue(null);
            }
        });
    }

    public void callUpdateUserApi(String url, UserRegistrationRequestBean model) {
        Call<UserDetailResponseBean> call = apiClient.updateUser(url, PlayerData.getInstance().getToken(), model);

        Log.d("log", "User Registration Request: " + call.request().toString());

        call.enqueue(new Callback<UserDetailResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<UserDetailResponseBean> call, @NonNull Response<UserDetailResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "User Registration API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            UserDetailResponseBean model;
                            model = gson.fromJson(errorResponse, UserDetailResponseBean.class);
                            updateUserLiveData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            updateUserLiveData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    updateUserLiveData.postValue(null);
                    return;
                }

                Log.i("log", "User Registration API Response: " + new Gson().toJson(response.body()));
                updateUserLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserDetailResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "User Registration API failed: " + throwable.toString());
                updateUserLiveData.postValue(null);
            }
        });
    }
}
