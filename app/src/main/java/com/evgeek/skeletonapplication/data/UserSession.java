package com.evgeek.skeletonapplication.data;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.evgeek.skeletonapplication.di.ApplicationScoped;
import com.evgeek.skeletonapplication.utils.Constants;

import javax.inject.Inject;

/**
 * Created by evgeek on 10/10/17.
 */
@ApplicationScoped
public class UserSession {
    @Inject
    SharedPreferences sharedPreferences;

    private String token;

    @Inject
    UserSession() {
    }

    @NonNull
    public String getToken() {
        if (token == null)
            token = sharedPreferences.getString(Constants.TOKEN_SHARED_PREFS, "");
        return token;
    }

    void setToken(@NonNull String token) {
        if (!token.equals(this.getToken())) {
            this.token = token;
            sharedPreferences.edit().putString(Constants.TOKEN_SHARED_PREFS, token).apply();
        }
    }

    @NonNull
    public String getTokenWithBearer() {
        return "Bearer " + getToken();
    }

    void logout() {
        setToken("");
    }
}
