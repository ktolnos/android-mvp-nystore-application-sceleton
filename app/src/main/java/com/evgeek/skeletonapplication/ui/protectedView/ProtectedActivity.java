package com.evgeek.skeletonapplication.ui.protectedView;

import android.os.Bundle;
import android.widget.TextView;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class ProtectedActivity extends BaseActivity implements ProtectedMvpView {


    @BindView(R.id.user_email_text)
    TextView userEmailText;

    @Inject
    ProtectedMvpPresenter<ProtectedMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewComponent().inject(this);
        setPresenter(mPresenter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_protected;
    }

    @Override
    protected int getTitleStringID() {
        return R.string.protected_activity;
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
