package com.skilrock.retailapp.network

import com.google.gson.GsonBuilder
import com.skilrock.retailapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIConfigKotlin {

    //QA-OLA
    const val rmsConfigUrl = "v1.0/getDomainList"
    const val getToken = "get/token"
    const val loginUrl = "v1.0/getLoginData"
    const val gameListTypeUrl = "v1.0/getUserMenus"
    const val forgotPasswordOtpUrl = "initiateForgotPassword"
    const val forgotPasswordResetUrl = "resetPassword"
    const val versionUrl = "fetchVersion"
    const val retailerFieldExUrl = "v1.0/getRetailerFieldEx"
    const val verifyPos = "v1.0/verifyPOS"
    var getPayAmount = "v1.0/getPayAmount"
    private var retrofit: Retrofit? = null

    val instance: Retrofit?
        get() {
            if (retrofit == null) {
                val gson = GsonBuilder()
                        .setLenient()
                        .create()
                val okHttpClient = OkHttpClient.Builder()
                        .readTimeout(300, TimeUnit.SECONDS)
                        .connectTimeout(300, TimeUnit.SECONDS)
                        .addInterceptor { chain ->
                            val request = chain.request().newBuilder().addHeader("Connection", "keep-alive").build()
                            chain.proceed(request)
                        }
                        .build()
                retrofit = Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
                /*retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                    .client(new OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create()).build();*/
            }
            return retrofit
        }
}