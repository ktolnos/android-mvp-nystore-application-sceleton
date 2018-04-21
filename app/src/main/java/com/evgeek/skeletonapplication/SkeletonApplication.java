package com.evgeek.skeletonapplication;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.evgeek.skeletonapplication.di.component.ApplicationComponent;
import com.evgeek.skeletonapplication.di.component.DaggerApplicationComponent;
import com.evgeek.skeletonapplication.di.module.ApplicationModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


public class SkeletonApplication extends Application {

    private static Resources mResources;

    //@Inject DataManager dataManager;
    protected ApplicationComponent mApplicationComponent;

    @Nullable
    public static Resources getApplicationResources() {
        return mResources;
    }

    public static SkeletonApplication get(Context context) {
        return (SkeletonApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(5)         // (Optional) How many method line to show. Default 2
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        mResources = getResources();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    @Override
    public void onTerminate() {
        mResources = null;
        super.onTerminate();
    }
}
