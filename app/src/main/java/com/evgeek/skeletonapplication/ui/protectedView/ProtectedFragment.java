package com.evgeek.skeletonapplication.ui.protectedView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;


public class ProtectedFragment extends BaseFragment implements ProtectedMvpView {

    @Inject
    ProtectedMvpPresenter<ProtectedMvpView> mPresenter;

    @BindView(R.id.user_email_text)
    TextView userEmailText;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_protected;
    }

    @Override
    protected int getTitleStringID() {
        return R.string.login_protected_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        setPresenter(mPresenter);
    }

    @Override
    public void setUsername(String email) {
        if (email != null) {
            userEmailText.setText(String.format(getString(R.string.you_are_logged_as), email));
        } else {
            userEmailText.setText("");
        }
    }
}