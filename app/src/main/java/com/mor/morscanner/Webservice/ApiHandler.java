package com.mor.morscanner.Webservice;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mor.morscanner.Constant.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiHandler {

    /*private static final String BASE_URL = Constant.str_BaseUrl;*/


    private static WebServices apiService;


    public static WebServices getApiService() {


        if (apiService == null) {


            //Here a logging interceptor is created
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            //The logging interceptor will be added to the http client
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);


            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl(Constant.str_BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            apiService = retrofit.create(WebServices.class);


            return apiService;
        } else {
            return apiService;
        }
    }
}
