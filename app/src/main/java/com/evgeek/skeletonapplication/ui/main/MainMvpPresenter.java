package com.evgeek.skeletonapplication.ui.main;

import com.evgeek.skeletonapplication.ui.base.MvpPresenter;

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void onLoginClick();

    void onLogoutClick();

    void onProtectedClick();

    void onRVClick();

    void onTemplateFragmentClick();

    void onDefaultClick();

}
