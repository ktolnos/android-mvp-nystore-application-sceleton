package com.evgeek.skeletonapplication.ui.template.activity;

import android.os.Bundle;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.ui.base.BaseActivity;

import javax.inject.Inject;

public class TemplateActivity extends BaseActivity implements TemplateMvpView {

    @Inject
    TemplateMvpPresenter<TemplateMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewComponent().inject(this);
        setPresenter(mPresenter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_template;
    }

    @Override
    protected int getTitleStringID() {
        return R.string.template_activity;
    }

}
