package com.evgeek.skeletonapplication.data;

import android.content.SharedPreferences;

import com.evgeek.skeletonapplication.data.events.LoginEvent;
import com.evgeek.skeletonapplication.data.events.LogoutEvent;
import com.evgeek.skeletonapplication.data.model.DataExample;
import com.evgeek.skeletonapplication.data.model.Token;
import com.evgeek.skeletonapplication.data.model.User;
import com.evgeek.skeletonapplication.data.remote.API;
import com.evgeek.skeletonapplication.di.ApplicationScoped;
import com.evgeek.skeletonapplication.utils.Constants;
import com.nytimes.android.external.store3.base.impl.Store;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by evgeek on 10/10/17.
 */
@ApplicationScoped
public class DataManager {

    @Inject
    UserSession userSession;
    @Inject
    API api;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    EventBus eventBus;

    @Inject
    Store<User, StoreKey> userStore;
    @Inject
    Store<List<DataExample>, String> dataExampleStore;

    private boolean hasLoggedUser = false;
    private User loggedUser = null;

    private boolean userNeedsRefresh = false;

    @Inject
    public DataManager() {
    }

    public boolean hasLoggedUser() {
        return hasLoggedUser;
    }

    public Single<User> getLoggedUser() {
        Single<User> ret = userStore.get(new StoreKey(userSession.getToken()));
        if (userNeedsRefresh) {
            ret = userStore.fetch(new StoreKey(userSession.getToken()));
            userNeedsRefresh = false;
        }
        return ret.onErrorResumeNext(throwable -> {
            if (hasLoggedUser) {
                hasLoggedUser = false;
                eventBus.post(new LogoutEvent());
            }
            return Single.error(throwable);
        })
                .map(user -> {
                    if (!hasLoggedUser) {
                        hasLoggedUser = true;
                        loggedUser = user;
                        eventBus.post(new LoginEvent());
                    }
                    return user;
                });
    }


    public void setUserToken(String token) {
        if (!userSession.getToken().equals(token)) {
            userSession.setToken(token);
            getLoggedUser()
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        } else if (!hasLoggedUser) {
            getLoggedUser()
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        }
    }

    public void doLogout() {
        hasLoggedUser = false;
        userSession.logout();
        eventBus.post(new LogoutEvent());
    }

    public Single<Token> auth(String email, String password) {
        return api.auth(email, password);
    }

    public String getAuthEmail() {
        return sharedPreferences.getString(Constants.EMAIL_SHARED_PREFS, "");
    }

    public void setAuthEmail(String email) {
        sharedPreferences.edit().putString(Constants.EMAIL_SHARED_PREFS, email).apply();
    }

    public Flowable<List<DataExample>> getExampleData(int page) {
        return dataExampleStore.get(String.valueOf(page))
                .concatWith(dataExampleStore.fetch(String.valueOf(page)))
                .distinct();
    }
}
