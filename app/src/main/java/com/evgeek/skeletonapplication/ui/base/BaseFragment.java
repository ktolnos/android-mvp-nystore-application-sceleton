package com.evgeek.skeletonapplication.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evgeek.skeletonapplication.di.component.ViewComponent;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements MvpView {

    private BaseFragmentActivity mActivity;
    private Unbinder mUnBinder;

    private int mLoadingQueue = 0;

    private MvpPresenter<BaseFragment> mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(getMenuID() != 0);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mainView = inflater.inflate(getContentViewID(), container, false);
        ButterKnife.bind(this, mainView);
        mActivity.setTitle(getTitleStringID());
        return mainView;
    }

    protected abstract @LayoutRes
    int getContentViewID();

    protected @StringRes
    int getTitleStringID() {
        return 0;
    }

    protected @MenuRes
    int getMenuID() {
        return 0;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(getMenuID(), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseFragmentActivity) {
            BaseFragmentActivity activity = (BaseFragmentActivity) context;
            mActivity = activity;
            activity.onFragmentAttached(this);
        } else {
            Logger.e(context.getClass().getName());
        }
    }

    @Override
    public void showLoading() {
        mLoadingQueue++;
        if (mActivity != null)
            mActivity.showLoading();
    }

    @Override
    public void hideLoading() {
        if (mLoadingQueue > 0)
            mLoadingQueue--;
        if (mActivity != null)
            mActivity.hideLoading();
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public void onDetach() {
        if (mActivity != null) {
            for (int i = 0; i < mLoadingQueue; i++) {
                hideLoading();
            }
            mActivity.onFragmentDetached(this);
            mActivity = null;
        }
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void onTokenExpire() {
        if (mActivity != null) {
            mActivity.onTokenExpire();
        }
    }

    public ViewComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getViewComponent();
        }
        return null;
    }

    public BaseFragmentActivity getBaseActivity() {
        if (mActivity == null) {
            Activity activity = getActivity();
            if (activity != null && activity instanceof BaseActivity) {
                mActivity = (BaseFragmentActivity) activity;
            }
        }
        return mActivity;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    protected void setUp(View view) {
    }

    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public MvpPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(MvpPresenter presenter) {
        mPresenter = presenter;
        if (presenter != null) {
            mPresenter.onAttach(this);
        }
    }

    @Override
    public void closeSelf() {
        if (getBaseActivity() != null)
            getBaseActivity().openDefaultFragment();
    }

    @Override
    public boolean hasPresenter() {
        return getPresenter() != null;
    }

    public interface Callback {

        void onFragmentAttached(BaseFragment fragment);

        void onFragmentDetached(BaseFragment fragment);
    }
}
