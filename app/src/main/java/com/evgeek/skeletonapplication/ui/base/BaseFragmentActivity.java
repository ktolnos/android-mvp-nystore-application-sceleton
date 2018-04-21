package com.evgeek.skeletonapplication.ui.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

public abstract class BaseFragmentActivity extends BaseActivity implements BaseFragment.Callback {

    @IdRes
    public abstract int getFragmentFrame();

    public void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(getFragmentFrame(), fragment).commit();
    }

    public void openLoginUnprotectedFragment() {
        openDefaultFragment();
    }

    public void openDefaultFragment() {

    }


    @Override
    public void onFragmentAttached(BaseFragment fragment) {
    }

    @Override
    public void onFragmentDetached(BaseFragment fragment) {
    }

}
