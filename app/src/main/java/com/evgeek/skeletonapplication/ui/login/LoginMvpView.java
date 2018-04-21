package com.evgeek.skeletonapplication.ui.login;

import android.support.annotation.StringRes;

import com.evgeek.skeletonapplication.ui.base.MvpView;

/**
 * Created by evgeek on 10/8/17.
 */

public interface LoginMvpView extends MvpView {

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPasswordError(String error);

    void setPasswordError(@StringRes int error);

    void setEmailError(String error);

    void setEmailError(@StringRes int error);

    void closeSelf();
}
