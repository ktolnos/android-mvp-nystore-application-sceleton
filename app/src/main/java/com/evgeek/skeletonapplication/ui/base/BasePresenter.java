package com.evgeek.skeletonapplication.ui.base;

import com.evgeek.skeletonapplication.data.events.LoginEvent;
import com.evgeek.skeletonapplication.data.events.LogoutEvent;
import com.evgeek.skeletonapplication.data.events.UserChangedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    @Inject
    EventBus eventBus;

    private V mMvpView;

    @Inject
    public BasePresenter() {
    }

    @Override
    public void onAttach(V mvpView) {
        eventBus.unregister(this);
        eventBus.register(this);
        mMvpView = mvpView;
        updateViewData();
    }

    @Override
    public void onDetach() {
        eventBus.unregister(this);
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    @Override
    public void handleApiError(Exception error) {

    }

    @Override
    public void updateViewData() {

    }

    @Override
    public void setUserAsLoggedOut() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    protected void onUserLoggedOut(LogoutEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    protected void onUserLoggedIn(LoginEvent event) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    protected void onUserChanged(UserChangedEvent event) {
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public boolean hasMvpView() {
        return getMvpView() != null;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
