package com.evgeek.skeletonapplication.ui.base;

import org.greenrobot.eventbus.EventBus;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface MvpPresenter<V extends MvpView> {

    boolean hasMvpView();

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(Exception error);

    void setUserAsLoggedOut();

    void updateViewData();

    EventBus getEventBus();
}
