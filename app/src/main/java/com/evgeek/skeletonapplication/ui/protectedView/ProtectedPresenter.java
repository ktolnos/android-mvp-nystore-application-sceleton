package com.evgeek.skeletonapplication.ui.protectedView;

import com.evgeek.skeletonapplication.data.DataManager;
import com.evgeek.skeletonapplication.data.events.LogoutEvent;
import com.evgeek.skeletonapplication.data.model.User;
import com.evgeek.skeletonapplication.data.remote.CallbackWrapper;
import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@PerView
public class ProtectedPresenter<V extends ProtectedMvpView> extends BasePresenter<V>
        implements ProtectedMvpPresenter<V> {

    @Inject
    DataManager dataManager;

    @Inject
    ProtectedPresenter() {
        super();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

        dataManager.getLoggedUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CallbackWrapper<User>(getMvpView()) {
                    @Override
                    public void onNext(User user) {
                        super.onNext(user);
                        if (!hasMvpView())
                            return;
                        getMvpView().setUsername(user.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (!hasMvpView())
                            return;
                        getMvpView().setUsername(null);
                    }
                });
    }

    @Override
    public void onUserLoggedOut(LogoutEvent event) {
        super.onUserLoggedOut(event);
        if (hasMvpView())
            getMvpView().closeSelf();
    }
}
