package com.skilrock.retailapp.network

import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarKotlinResponseData
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarRequestBean
import com.skilrock.retailapp.models.ola.OlaRegistrationRequestBean
import com.skilrock.retailapp.models.ola.OlaRegistrationResponseBean
import com.skilrock.retailapp.models.rms.LoginBean
import com.skilrock.retailapp.models.rms.LoginResponseData
import retrofit2.Call
import retrofit2.http.*

interface APIClientKotlin {

    @POST
    fun postOlaRegistration(
            @Url url: String?,
            @Header("username") userName: String?,
            @Header("password") password: String?,
            @Header("Content-Type") contentType: String?,
            @Header("Accept") accept: String?,
            @Body olaRegistrationOtpRequestBean: OlaRegistrationMyanmarRequestBean?
    ): Call<OlaRegistrationMyanmarKotlinResponseData?>?

    @GET(APIConfig.loginUrl)
    fun getLogin(
            @Header("Authorization") token: String?
    ): Call<LoginBean?>?

}