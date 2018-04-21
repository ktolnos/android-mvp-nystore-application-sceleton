package com.evgeek.skeletonapplication.ui.defaultFragment;

import com.evgeek.skeletonapplication.data.DataManager;
import com.evgeek.skeletonapplication.data.events.LoginEvent;
import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.base.BasePresenter;

import javax.inject.Inject;


@PerView
public class DefaultPresenter<V extends DefaultMvpView> extends BasePresenter<V>
        implements DefaultMvpPresenter<V> {

    @Inject
    DataManager dataManager;

    private Runnable openViewOnLoginRunnable;

    @Inject
    public DefaultPresenter() {
        super();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        tryRunOpenLoginRunnable();
    }

    @Override
    public void onOpenTemplateActivityClick() {
        if (hasMvpView())
            getMvpView().openTemplateActivity();
    }

    @Override
    public void onOpenProtectedActivityClick() {
        executeViewOpeningRunnable(() -> getMvpView().openProtectedActivity());
    }

    private void executeViewOpeningRunnable(Runnable openingRunnable) {
        if (dataManager.hasLoggedUser() && hasMvpView()) {
            openingRunnable.run();
            openViewOnLoginRunnable = null;
        } else {
            openViewOnLoginRunnable = openingRunnable;
            if (hasMvpView())
                getMvpView().openLoginView();
        }
    }

    @Override
    public void onUserLoggedIn(LoginEvent event) {
        tryRunOpenLoginRunnable();
    }

    private void tryRunOpenLoginRunnable() {
        if (openViewOnLoginRunnable != null && dataManager.hasLoggedUser() && hasMvpView()) {
            openViewOnLoginRunnable.run();
            openViewOnLoginRunnable = null;
        }
    }
}
