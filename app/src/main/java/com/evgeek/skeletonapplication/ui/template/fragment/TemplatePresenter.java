package com.evgeek.skeletonapplication.ui.template.fragment;

import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.base.BasePresenter;

import javax.inject.Inject;


@PerView
public class TemplatePresenter<V extends TemplateMvpView> extends BasePresenter<V>
        implements TemplateMvpPresenter<V> {

    @Inject
    public TemplatePresenter() {
        super();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

}
