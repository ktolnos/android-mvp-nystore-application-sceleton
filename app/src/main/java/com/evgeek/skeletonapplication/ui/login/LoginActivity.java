package com.evgeek.skeletonapplication.ui.login;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.email_input_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.password_field)
    EditText passwordField;
    @BindView(R.id.password_input_layout)
    TextInputLayout passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewComponent().inject(this);
        setPresenter(mPresenter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        emailField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                mPresenter.onEmailFieldFocusLost();
        });
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleStringID() {
        return R.string.activity_login_title;
    }

    @OnClick(R.id.login_btn)
    void onLoginClick() {
        mPresenter.onLoginClick();
    }

    @Override
    public String getEmail() {
        return emailField.getText().toString();
    }

    @Override
    public void setEmail(String email) {
        emailField.setText(email);
    }

    @Override
    public String getPassword() {
        return passwordField.getText().toString();
    }

    @Override
    public void setPasswordError(String error) {
        passwordLayout.setError(error);
        passwordField.requestFocus();
    }

    @Override
    public void setPasswordError(@StringRes int error) {
        setPasswordError(getString(error));
    }

    @Override
    public void setEmailError(String error) {
        emailLayout.setError(error);
    }

    @Override
    public void setEmailError(@StringRes int error) {
        setEmailError(getString(error));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}