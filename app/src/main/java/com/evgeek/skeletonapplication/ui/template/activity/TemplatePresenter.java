package com.evgeek.skeletonapplication.ui.template.activity;

import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.base.BasePresenter;

import javax.inject.Inject;

@PerView
public class TemplatePresenter<V extends TemplateMvpView> extends BasePresenter<V>
        implements TemplateMvpPresenter<V> {


    @Inject
    TemplatePresenter() {
        super();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }
}
