package com.anglehack.thematch.thematch.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static String URL = "";
    private static RetrofitClient mInstance;
    private RetrofitService mService;

    private RetrofitClient() {
        //TODO this is for debugging purpose so need to change for production
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient httpClient;

        httpClient = new OkHttpClient().newBuilder()
//                .readTimeout(15, TimeUnit.SECONDS)
//                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
              //  .addInterceptor(mInterceptor)
                .build();


        Gson gson = null;

        try {
            gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
        } catch (Exception e) {
            e.printStackTrace();
        }




        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        mService = retrofit.create(RetrofitService.class);


    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public RetrofitService getService() {
        return mService;
    }

}