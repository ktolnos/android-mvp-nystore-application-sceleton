package com.evgeek.skeletonapplication.ui.defaultFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.ui.base.BaseFragment;
import com.evgeek.skeletonapplication.ui.login.LoginActivity;
import com.evgeek.skeletonapplication.ui.protectedView.ProtectedActivity;
import com.evgeek.skeletonapplication.ui.template.activity.TemplateActivity;

import javax.inject.Inject;

import butterknife.OnClick;


public class DefaultFragment extends BaseFragment implements DefaultMvpView {

    @Inject
    DefaultMvpPresenter<DefaultMvpView> mPresenter;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_default;
    }

    @Override
    protected int getTitleStringID() {
        return R.string.default_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        setPresenter(mPresenter);
    }

    @OnClick(R.id.btn_open_template)
    public void onOpenTemplateActivityClick() {
        mPresenter.onOpenTemplateActivityClick();
    }

    @OnClick(R.id.btn_open_protected)
    public void onOpenProtectedActivityClick() {
        mPresenter.onOpenProtectedActivityClick();
    }

    @Override
    public void openTemplateActivity() {
        startActivity(new Intent(getContext(), TemplateActivity.class));
    }

    @Override
    public void openProtectedActivity() {
        startActivity(new Intent(getContext(), ProtectedActivity.class));
    }

    @Override
    public void openLoginView() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}