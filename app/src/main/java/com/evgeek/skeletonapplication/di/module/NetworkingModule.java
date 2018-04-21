package com.evgeek.skeletonapplication.di.module;

import android.content.Context;

import com.evgeek.skeletonapplication.BuildConfig;
import com.evgeek.skeletonapplication.data.GsonStringConverterFactory;
import com.evgeek.skeletonapplication.data.remote.API;
import com.evgeek.skeletonapplication.data.remote.MockApi;
import com.evgeek.skeletonapplication.di.ApplicationContext;
import com.evgeek.skeletonapplication.di.ApplicationScoped;
import com.evgeek.skeletonapplication.utils.Constants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by evgeek on 10/8/17.
 */

@Module
public abstract class NetworkingModule {

    @Provides
    static Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();
    }

    @Provides
    static Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    static OkHttpClient provideOkHttpClient(@ApplicationContext Context context) {
        ChuckInterceptor chuckInterceptor = new ChuckInterceptor(context);
        chuckInterceptor.maxContentLength(100000);
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
                    return chain.proceed(request);
                })
                .addInterceptor(chuckInterceptor)
                .build();
    }

    @Provides
    static Retrofit provideRetrofit(Converter.Factory factory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(new GsonStringConverterFactory())
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScoped
    static API provideMainApi(Retrofit retrofit, MockApi mockApi) {
        if (BuildConfig.FLAVOR.equals("offline")) {
            return mockApi;
        }
        return retrofit.create(API.class);
    }

}
