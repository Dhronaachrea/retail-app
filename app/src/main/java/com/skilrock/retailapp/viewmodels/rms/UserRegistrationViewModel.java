package com.skilrock.retailapp.viewmodels.rms;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.models.rms.CheckUserNameResponseBean;
import com.skilrock.retailapp.models.rms.CityListResponseBean;
import com.skilrock.retailapp.models.rms.CountryListResponseBean;
import com.skilrock.retailapp.models.rms.GetRoleListResponseBean;
import com.skilrock.retailapp.models.rms.StateAndCityListResponseBean;
import com.skilrock.retailapp.models.rms.StateListResponseBean;
import com.skilrock.retailapp.models.rms.UserDetailResponseBean;
import com.skilrock.retailapp.models.rms.UserRegistrationRequestBean;
import com.skilrock.retailapp.models.rms.UserRegistrationResponseBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationViewModel extends ViewModel {

    private APIClient apiClient = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<CheckUserNameResponseBean> checkUserNameliveData = new MutableLiveData<>();
    private MutableLiveData<GetRoleListResponseBean> roleListliveData = new MutableLiveData<>();
    private MutableLiveData<CountryListResponseBean> countryList = new MutableLiveData<>();
    private MutableLiveData<CityListResponseBean> cityListLiveData = new MutableLiveData<>();
    private MutableLiveData<StateListResponseBean> stateListLiveData = new MutableLiveData<>();
    private MutableLiveData<StateAndCityListResponseBean> stateAndCityListLiveData = new MutableLiveData<>();
    private MutableLiveData<UserRegistrationResponseBean> userRegistrationLiveData = new MutableLiveData<>();
    private MutableLiveData<UserDetailResponseBean> updateUserLiveData = new MutableLiveData<>();

    public MutableLiveData<UserDetailResponseBean> getUpdateUserLiveData(){
        return updateUserLiveData;
    }
    public MutableLiveData<CheckUserNameResponseBean> getLiveData(){
        return checkUserNameliveData;
    }

    public MutableLiveData<GetRoleListResponseBean> getRoleListLiveData(){
        return roleListliveData;
    }

    public MutableLiveData<CountryListResponseBean> getCountryList() {
        return countryList;
    }

    public MutableLiveData<CityListResponseBean> getCityListLiveData() {
        return cityListLiveData;
    }

    public MutableLiveData<StateListResponseBean> getStateListLiveData() {
        return stateListLiveData;
    }

    public MutableLiveData<StateAndCityListResponseBean> getStateAndCityListLiveData() {
        return stateAndCityListLiveData;
    }

    public MutableLiveData<UserRegistrationResponseBean> getUserRegistrationLiveData() {
        return userRegistrationLiveData;
    }

    public void checkUserNameApi(String token, String url, String loginName) {
        Call<CheckUserNameResponseBean> changePasswordCall = apiClient.checkUserName(token, url, loginName);

        Log.d("log", "CheckUserName Request: " + changePasswordCall.request().toString());

        changePasswordCall.enqueue(new Callback<CheckUserNameResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<CheckUserNameResponseBean> call, @NonNull Response<CheckUserNameResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "CheckUserName API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            CheckUserNameResponseBean model;
                            model= gson.fromJson(errorResponse, CheckUserNameResponseBean.class);
                            checkUserNameliveData.postValue(model);
                        } catch (IOException e) {
                            checkUserNameliveData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    checkUserNameliveData.postValue(null);
                    return;
                }

                Log.i("log", "CheckUserName Response: " + new Gson().toJson(response.body()));
                checkUserNameliveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CheckUserNameResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "CheckUserName failed: " + throwable.toString());
                checkUserNameliveData.postValue(null);
            }
        });
    }

    public void getRoleList(String token, String url) {
        Call<GetRoleListResponseBean> changePasswordCall = apiClient.getRoleList(token, url,  AppConstants.ORG_TYPE_CODE);

        Log.d("log", "GetRoleList Request: " + changePasswordCall.request().toString());

        changePasswordCall.enqueue(new Callback<GetRoleListResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<GetRoleListResponseBean> call, @NonNull Response<GetRoleListResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "GetRoleList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            GetRoleListResponseBean model;
                            model = gson.fromJson(errorResponse, GetRoleListResponseBean.class);
                            roleListliveData.postValue(model);
                        } catch (IOException e) {
                            roleListliveData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    roleListliveData.postValue(null);
                    return;
                }

                Log.i("log", "GetRoleList Response: " + new Gson().toJson(response.body()));
                roleListliveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<GetRoleListResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "GetRoleList failed: " + throwable.toString());
                roleListliveData.postValue(null);
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
                        } catch (IOException e) {
                            countryList.postValue(null);
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

    public void getCityList(String token, String url, String stateCode) {
        Call<CityListResponseBean> changePasswordCall = apiClient.getCityList(token, url, stateCode);

        Log.d("log", "getCityList Request: " + changePasswordCall.request().toString());

        changePasswordCall.enqueue(new Callback<CityListResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<CityListResponseBean> call, @NonNull Response<CityListResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "getCityList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            CityListResponseBean model;
                            model = gson.fromJson(errorResponse, CityListResponseBean.class);
                            cityListLiveData.postValue(model);
                        } catch (IOException e) {
                            cityListLiveData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    cityListLiveData.postValue(null);
                    return;
                }

                Log.i("log", "getCityList Response: " + new Gson().toJson(response.body()));
                cityListLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CityListResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "getCityList failed: " + throwable.toString());
                cityListLiveData.postValue(null);
            }
        });
    }

    public void getStateList(String token, String url, String countryCode) {
        Call<StateListResponseBean> changePasswordCall = apiClient.getStateList(token, url, countryCode);

        Log.d("log", "getStateList Request: " + changePasswordCall.request().toString());

        changePasswordCall.enqueue(new Callback<StateListResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<StateListResponseBean> call, @NonNull Response<StateListResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "getStateList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            StateListResponseBean model;
                            model = gson.fromJson(errorResponse, StateListResponseBean.class);
                            stateListLiveData.postValue(model);
                        } catch (IOException e) {
                            stateListLiveData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    stateListLiveData.postValue(null);
                    return;
                }

                Log.i("log", "getStateList Response: " + new Gson().toJson(response.body()));
                stateListLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<StateListResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "getStateList failed: " + throwable.toString());
                stateListLiveData.postValue(null);
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
                        } catch (IOException e) {
                            stateAndCityListLiveData.postValue(null);
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

    public void callUserRegistration(String url, UserRegistrationRequestBean model) {
        Call<UserRegistrationResponseBean> call = apiClient.userRegistration(url, PlayerData.getInstance().getToken(), model);

        Log.d("log", "User Registration Request: " + call.request().toString());

        call.enqueue(new Callback<UserRegistrationResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<UserRegistrationResponseBean> call, @NonNull Response<UserRegistrationResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "User Registration API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            UserRegistrationResponseBean model;
                            model = gson.fromJson(errorResponse, UserRegistrationResponseBean.class);
                            userRegistrationLiveData.postValue(model);
                        } catch (IOException e) {
                            userRegistrationLiveData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    userRegistrationLiveData.postValue(null);
                    return;
                }

                Log.i("log", "User Registration API Response: " + new Gson().toJson(response.body()));
                userRegistrationLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserRegistrationResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "User Registration API failed: " + throwable.toString());
                userRegistrationLiveData.postValue(null);
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
                        } catch (IOException e) {
                            updateUserLiveData.postValue(null);
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
