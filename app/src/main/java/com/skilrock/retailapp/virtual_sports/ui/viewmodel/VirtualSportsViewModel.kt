package com.skilrock.retailapp.virtual_sports.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.skilrock.retailapp.models.rms.LoginBean
import com.skilrock.retailapp.network.APIClient
import com.skilrock.retailapp.network.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VirtualSportsViewModel: ViewModel() {

    private val client = APIConfig.getInstance().create(APIClient::class.java)

    public val liveDataBalance = MutableLiveData<LoginBean>()
    /*fun getLiveDataBalance(): MutableLiveData<LoginBean?>? {
        return liveDataBalance
    }*/


    fun getUpdatedBalance(authToken: String?) {
        val loginCall = client.getLogin(authToken)
        Log.d("log", "Login Request: " + loginCall.request().toString())
        loginCall.enqueue(object : Callback<LoginBean?> {
            override fun onResponse(call: Call<LoginBean?>, response: Response<LoginBean?>) {
                if (response.body() == null || !response.isSuccessful) {
                    if (response.errorBody() != null) {
                        try {
                            val errorResponse = response.errorBody()!!.string()
                            Log.e("log", "Login API Failed: $errorResponse")
                            val gson = GsonBuilder().create()
                            val model: LoginBean
                            model = gson.fromJson(errorResponse, LoginBean::class.java)
                            liveDataBalance.postValue(model)
                        } catch (e: JsonSyntaxException) {
                            liveDataBalance.postValue(null)
                            e.printStackTrace()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return
                    }
                    liveDataBalance.postValue(null)
                    return
                }
                Log.i("log", "Login API Response: " + Gson().toJson(response.body()))
                if (response.body()!!.responseCode == 0 && response.body()!!.responseData.statusCode == 0) response.body()!!.token = authToken
                liveDataBalance.postValue(response.body())
            }

            override fun onFailure(call: Call<LoginBean?>, throwable: Throwable) {
                Log.e("log", "Login API failed: $throwable")
                liveDataBalance.postValue(null)
            }
        })
    }

}