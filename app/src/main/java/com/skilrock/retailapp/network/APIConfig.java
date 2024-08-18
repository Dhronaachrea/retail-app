package com.skilrock.retailapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skilrock.retailapp.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIConfig {
    public static final String baseUrl              = "http://192.168.124.193:8083/RMS/";/*UAT*//*"http://3.125.210.138:8083/RMS/"*//*"QA"*//*http://192.168.124.184:8081/RMS/"*/; //QA-OLA
    public static final String rmsConfigUrl         = "v1.0/getDomainList";
    public static final String getToken             = "get/token";
    static final String loginUrl                    = "v1.0/getLoginData";
    static final String getTicketStatusUrl          = "PPL/sale/getTicketStatusForSold";
    static final String gameListTypeUrl             = "v1.0/getUserMenus";
    static final String forgotPasswordOtpUrl        = "initiateForgotPassword";
    static final String forgotPasswordResetUrl      = "resetPassword";
    //static final String versionUrl                = "fetchVersion";
    static final String versionUrl                  = "getPreAppVersion";
    static final String retailerFieldExUrl          = "v1.0/getRetailerFieldEx";
    static final String verifyPos                   = "v1.0/verifyPOS";
    public static String getPayAmount               = "v1.0/getPayAmount";
    public static boolean verifyPosRequired         = false;
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(300, TimeUnit.SECONDS)
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder().addHeader("Connection", "keep-alive").build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(loggingInterceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            /*retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                    .client(new OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create()).build();*/
        }
        return retrofit;
    }
}
