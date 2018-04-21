package com.evgeek.skeletonapplication.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.evgeek.skeletonapplication.di.ApplicationContext;
import com.evgeek.skeletonapplication.di.ApplicationScoped;
import com.evgeek.skeletonapplication.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Locale;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 25/12/16.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationScoped
    static EventBus provideEventBus() {
        return EventBus.getDefault();
    }


    @Provides
    static SimpleDateFormat provideSdf() {
        return new SimpleDateFormat(Constants.DATE_PATTERN, Locale.US);
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationScoped
    SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences("skeleton-prefs", Context.MODE_PRIVATE);
    }
}
