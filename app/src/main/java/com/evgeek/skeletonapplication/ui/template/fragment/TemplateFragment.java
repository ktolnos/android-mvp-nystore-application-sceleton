package com.evgeek.skeletonapplication.ui.template.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.ui.base.BaseFragment;

import javax.inject.Inject;


public class TemplateFragment extends BaseFragment implements TemplateMvpView {

    @Inject
    TemplateMvpPresenter<TemplateMvpView> mPresenter;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_template;
    }

    @Override
    protected int getTitleStringID() {
        return R.string.template_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        setPresenter(mPresenter);
    }


}