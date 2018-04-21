package com.evgeek.skeletonapplication.di.module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.evgeek.skeletonapplication.data.StoreKey;
import com.evgeek.skeletonapplication.data.model.DataExample;
import com.evgeek.skeletonapplication.data.model.User;
import com.evgeek.skeletonapplication.data.remote.API;
import com.evgeek.skeletonapplication.di.ApplicationContext;
import com.evgeek.skeletonapplication.di.ApplicationScoped;
import com.nytimes.android.external.fs3.filesystem.FileSystem;
import com.nytimes.android.external.fs3.filesystem.FileSystemFactory;
import com.nytimes.android.external.store3.base.Fetcher;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;

@Module
public abstract class StoresModule {

    @Provides
    @ApplicationScoped
    static Store<User, StoreKey> provideUsrStore(Fetcher<User, StoreKey> fetcher) {
        return StoreBuilder.<StoreKey, User>key()
                .fetcher(fetcher)
                .open();
    }

    @Provides
    @ApplicationScoped
    static Fetcher<User, StoreKey> provideUserFetcher(API api) {
        return userStoreKey -> {
            if (!userStoreKey.getUserToken().isEmpty()) {
                return api.getUserProfile("Bearer " + userStoreKey.getUserToken());
            } else {
                return Single.error(new Throwable("User token not provided"));
            }
        };
    }

    @NonNull
    @Provides
    @ApplicationScoped
    static Store<List<DataExample>, String> provideDataExamples(API api) {

        /*File dir = context.getFilesDir();
        FileSystem f = null;
        try {
            f = FileSystemFactory.create(dir);
        } catch (IOException e) {
        }*/
        return StoreBuilder.<String, List<DataExample>>key()
                .fetcher(api::getExampleData)
                /*
                .persister(new Persister<List<DataExample>, String>() {
                    @Nonnull
                    @Override
                    public Maybe<List<DataExample>> read(@Nonnull String s) {
                        return null;
                    }

                    @Nonnull
                    @Override
                    public Single<Boolean> write(@Nonnull String s, @Nonnull List<DataExample> list) {
                        return null;
                    }
                })*/
                .open();
    }


}
