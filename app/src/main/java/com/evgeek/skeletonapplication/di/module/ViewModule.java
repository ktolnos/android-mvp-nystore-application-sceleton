package com.evgeek.skeletonapplication.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.evgeek.skeletonapplication.di.ActivityContext;

import dagger.Module;
import dagger.Provides;


@Module
public class ViewModule {

    private AppCompatActivity mActivity;

    public ViewModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

}
