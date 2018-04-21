package com.evgeek.skeletonapplication.di.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.evgeek.skeletonapplication.SkeletonApplication;
import com.evgeek.skeletonapplication.data.DataManager;
import com.evgeek.skeletonapplication.data.StoreKey;
import com.evgeek.skeletonapplication.data.UserSession;
import com.evgeek.skeletonapplication.data.model.User;
import com.evgeek.skeletonapplication.data.remote.API;
import com.evgeek.skeletonapplication.di.ApplicationContext;
import com.evgeek.skeletonapplication.di.ApplicationScoped;
import com.evgeek.skeletonapplication.di.module.ApplicationModule;
import com.evgeek.skeletonapplication.di.module.NetworkingModule;
import com.evgeek.skeletonapplication.di.module.StoresModule;
import com.google.gson.Gson;
import com.nytimes.android.external.store3.base.Fetcher;
import com.nytimes.android.external.store3.base.impl.Store;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;


@ApplicationScoped
@Component(modules = {ApplicationModule.class, StoresModule.class, NetworkingModule.class})
public interface ApplicationComponent {

    void inject(SkeletonApplication demoApplication);

    @ApplicationContext
    Context getContext();

    Application application();

    Gson getGson();

    Converter.Factory getConverterFactory();

    Retrofit getRetrofit();

    API getMainApi();

    Store<User, StoreKey> getUsrStore();

    Fetcher<User, StoreKey> getUserFetcher();

    UserSession getUserSession();

    OkHttpClient getOkHttpClient();

    DataManager getDataManager();

    EventBus getEventBus();

    SharedPreferences getSharedPreferences();

    SimpleDateFormat getSimpleDateFormat();
}
