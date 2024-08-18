package com.skilrock.retailapp.viewmodels.ola

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.skilrock.retailapp.models.UrlOlaBean
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarKotlinResponseData
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarRequestBean
import com.skilrock.retailapp.models.ola.OlaRegistrationRequestBean
import com.skilrock.retailapp.models.ola.OlaRegistrationResponseBean
import com.skilrock.retailapp.models.rms.LoginBean
import com.skilrock.retailapp.models.rms.LoginResponseData
import com.skilrock.retailapp.network.APIClientKotlin
import com.skilrock.retailapp.network.APIConfig
import com.skilrock.retailapp.network.APIConfigKotlin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OlaRegistrationFragmentMyanmarViewModel : ViewModel() {

    private val apiClient = APIConfigKotlin.instance?.create(APIClientKotlin::class.java)

    private var liveDataRegistration: MutableLiveData<OlaRegistrationMyanmarKotlinResponseData> = MutableLiveData<OlaRegistrationMyanmarKotlinResponseData>()
    private var liveDataLogin: MutableLiveData<LoginBean> = MutableLiveData<LoginBean>()

    fun getRegistrationLiveData(): LiveData<OlaRegistrationMyanmarKotlinResponseData> {
        return liveDataRegistration
    }

    fun getLoginLiveData(): LiveData<LoginBean> {
        return liveDataLogin
    }

    fun callRegistrationApi(url: UrlOlaBean, bean: OlaRegistrationMyanmarRequestBean) {
        val registrationCall: Call<OlaRegistrationMyanmarKotlinResponseData?>? = apiClient?.postOlaRegistration(url.url, url.userName, url.password, url.contentType, url.accept, bean)

        Log.d("log", "Ola Registration Request: ${registrationCall?.request().toString()}")

        registrationCall?.enqueue(object: Callback<OlaRegistrationMyanmarKotlinResponseData?> {

            override fun onResponse(call: Call<OlaRegistrationMyanmarKotlinResponseData?>, response: Response<OlaRegistrationMyanmarKotlinResponseData?>) {
                if (response.body() == null || !response.isSuccessful) {
                    if (response.errorBody() != null) {
                        try {
                            val errorResponse = response.errorBody()!!.string()
                            Log.e("log", "Ola Registration API Failed: $errorResponse")
                            val gson = GsonBuilder().create()
                            val model: OlaRegistrationMyanmarKotlinResponseData
                            model = gson.fromJson(errorResponse, OlaRegistrationMyanmarKotlinResponseData::class.java)
                            liveDataRegistration.postValue(model)
                        } catch (e: JsonSyntaxException) {
                            liveDataRegistration.postValue(null)
                            e.printStackTrace()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return
                    }

                    liveDataRegistration.postValue(null)
                    return
                }

                Log.i("log", "Ola Registration API Response: ${Gson().toJson(response.body())}")
                liveDataRegistration.postValue(response.body())
            }

            override fun onFailure(call: Call<OlaRegistrationMyanmarKotlinResponseData?>, throwable: Throwable) {
                Log.e("log", "Ola Registration API failed: $throwable")
                liveDataRegistration.postValue(null)
            }
        })
    }

    fun callBalanceApi(authToken: String ) {
        val loginCall = apiClient?.getLogin(authToken)

        Log.d("log", "Login Request: ${loginCall?.request().toString()}")

        loginCall?.enqueue(object: Callback<LoginBean?>{

            override fun onResponse(call: Call<LoginBean?>, response: Response<LoginBean?>) {
                if (response.body() == null || !response.isSuccessful) {
                    if (response.errorBody() != null) {
                        try {
                            val errorResponse = response.errorBody()!!.string()
                            Log.e("log", "Login API Failed: $errorResponse")
                            val gson = GsonBuilder().create()
                            val model: LoginBean
                            model = gson.fromJson(errorResponse, LoginBean::class.java)
                            liveDataLogin.postValue(model)
                        } catch (e: JsonSyntaxException) {
                            liveDataLogin.postValue(null)
                            e.printStackTrace()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return
                    }

                    liveDataLogin.postValue(null)
                    return
                }

                Log.i("log", "Login API Response: ${Gson().toJson(response.body())}")
                liveDataLogin.postValue(response.body())
            }

            override fun onFailure(call: Call<LoginBean?>, throwable: Throwable) {
                Log.e("log", "Login API failed: $throwable")
                liveDataLogin.postValue(null)
            }

        })
    }
}
