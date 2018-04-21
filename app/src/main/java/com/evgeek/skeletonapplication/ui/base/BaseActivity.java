package com.evgeek.skeletonapplication.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.SkeletonApplication;
import com.evgeek.skeletonapplication.di.component.DaggerViewComponent;
import com.evgeek.skeletonapplication.di.component.ViewComponent;
import com.evgeek.skeletonapplication.ui.login.LoginActivity;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private static int loadingShowQueueSize = 0;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_progress)
    ProgressBar mProgressBar;
    private ViewComponent mViewComponent;
    private Unbinder mUnBinder;

    private MvpPresenter<BaseActivity> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeCreate();
        super.onCreate(savedInstanceState);
        mViewComponent = DaggerViewComponent.builder()
                .applicationComponent(SkeletonApplication.get(this).getComponent())
                .build();

        setContentView(getContentViewID());
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getTitleStringID() != 0) {
            setTitle(getTitleStringID());
        }
    }


    protected abstract @LayoutRes
    int getContentViewID();

    protected @StringRes
    int getTitleStringID() {
        return 0;
    }

    @Override
    protected void onResume() {
        if (hasPresenter() && !getPresenter().hasMvpView()) {
            mPresenter.onAttach(this);
        }
        super.onResume();
    }


    public ViewComponent getViewComponent() {
        if (mViewComponent == null) {
            mViewComponent = DaggerViewComponent.builder()
                    .applicationComponent(SkeletonApplication.get(this).getComponent())
                    .build();
        }
        return mViewComponent;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        loadingShowQueueSize++;
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (loadingShowQueueSize > 0) {
            loadingShowQueueSize--;
        } else {
            loadingShowQueueSize = 0;
        }
        if (loadingShowQueueSize == 0) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        //textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            Logger.i(message);
            showSnackBar(message);
        } else {
            showSnackBar("TODO err");
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "TODO err", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onTokenExpire() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    public void setTitle(String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e) {
            Log.e("BaseActivity", "No support actionbar");
        }
    }

    public void setTitle(@StringRes int titleRes) {
        if (titleRes != 0)
            setTitle(getString(titleRes));
    }

    protected void beforeCreate() {
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
        finish();
    }

    @Override
    public boolean hasPresenter() {
        return getPresenter() != null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
