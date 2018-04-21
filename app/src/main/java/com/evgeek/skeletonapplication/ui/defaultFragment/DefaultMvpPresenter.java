package com.evgeek.skeletonapplication.ui.defaultFragment;

import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.base.MvpPresenter;

@PerView
public interface DefaultMvpPresenter<V extends DefaultMvpView> extends MvpPresenter<V> {

    void onOpenTemplateActivityClick();

    void onOpenProtectedActivityClick();

}
