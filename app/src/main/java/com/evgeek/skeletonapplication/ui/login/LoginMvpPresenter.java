package com.evgeek.skeletonapplication.ui.login;


import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.base.MvpPresenter;


@PerView
public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onLoginClick();

    void onEmailFieldFocusLost();

}
