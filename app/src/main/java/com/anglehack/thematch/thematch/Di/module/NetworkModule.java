package com.anglehack.thematch.thematch.Di.module;

import android.support.annotation.Nullable;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
<<<<<<< HEAD
    public String baseURL = "http://192.168.43.231/theMatch/public/index.php/api/";
   // public static String baseURL = "http://192.168.43.188:90/api/";


   // private String akmServer = "http://192.168.43.188:90/api/";
=======
    public String baseURL = "http://192.168.43.231/theMatch/public/index.php/";
    //public static String baseURL = "http://192.168.43.188:90";


    private String akmServer = "http://192.168.43.188:90/api/";
>>>>>>> 6cc313492a2a8c961c748ef904f129b488ce474a
    @Provides
    @Singleton
    OkHttpClient providehttpClient() {

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

        return httpClient;
    }
    @Provides
    @Singleton
    GsonConverterFactory provideGsonFactory() {

        return GsonConverterFactory.create(new Gson());
    }


    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient client, GsonConverterFactory converterFactory) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .baseUrl(baseURL)
                .client(client)
                .build();


    }
    @Provides
    @Singleton
    RetrofitService providesRetrofitservice(Retrofit retrofit) {
        return retrofit.create(RetrofitService.class);


    }


}
