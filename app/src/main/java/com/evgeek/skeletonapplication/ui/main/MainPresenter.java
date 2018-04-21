package com.evgeek.skeletonapplication.ui.main;

import com.evgeek.skeletonapplication.data.DataManager;
import com.evgeek.skeletonapplication.data.events.LoginEvent;
import com.evgeek.skeletonapplication.data.events.UserChangedEvent;
import com.evgeek.skeletonapplication.data.model.User;
import com.evgeek.skeletonapplication.data.remote.CallbackWrapper;
import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.base.BasePresenter;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

@PerView
public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    @Inject
    DataManager dataManager;
    private Runnable openViewOnLoginRunnable;

    @Inject
    MainPresenter() {
        super();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        updateViewData();

        tryRunOpenLoginRunnable();
    }


    @Override
    public void onUserChanged(UserChangedEvent event) {
        super.onUserChanged(event);
        updateViewData();
    }

    @Override
    public void onUserLoggedIn(LoginEvent event) {
        tryRunOpenLoginRunnable();
    }

    @Override
    public void updateViewData() {
        super.updateViewData();
        getMvpView().showLoading();
        dataManager.getLoggedUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CallbackWrapper<User>(getMvpView()) {
                    @Override
                    public void onNext(@NonNull User user) {
                        super.onNext(user);
                        if (!hasMvpView())
                            return;


                        IProfile userProfile = new ProfileDrawerItem()
                                .withEmail(user.getEmail())
                                .withName(user.getName());

                        getMvpView().setUserProfile(userProfile);
                        getMvpView().switchButtonToLogout();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (!hasMvpView())
                            return;
                        getMvpView().setUserProfile(null);
                        getMvpView().switchButtonToLogin();
                    }
                });
    }

    @Override
    public void onLoginClick() {
        if (hasMvpView())
            getMvpView().openLoginView();
    }

    @Override
    public void onLogoutClick() {
        dataManager.doLogout();
    }

    @Override
    public void onProtectedClick() {
        executeViewOpeningRunnable(() -> getMvpView().openProtectedFragment());
    }

    @Override
    public void onRVClick() {
        if (hasMvpView())
            getMvpView().openRVView();
    }

    @Override
    public void onTemplateFragmentClick() {
        if (hasMvpView())
            getMvpView().openTemplateFragment();
    }

    @Override
    public void onDefaultClick() {
        if (hasMvpView())
            getMvpView().openDefaultView();
    }

    private void executeViewOpeningRunnable(@Nonnull Runnable openingRunnable) {
        if (dataManager.hasLoggedUser() && hasMvpView()) {
            openingRunnable.run();
            openViewOnLoginRunnable = null;
        } else {
            openViewOnLoginRunnable = openingRunnable;
            if (hasMvpView())
                getMvpView().openLoginView();
        }
    }

    private void tryRunOpenLoginRunnable() {
        if (openViewOnLoginRunnable != null && dataManager.hasLoggedUser() && hasMvpView()) {
            openViewOnLoginRunnable.run();
            openViewOnLoginRunnable = null;
        }
    }
}
