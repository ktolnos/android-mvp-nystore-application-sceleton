package com.evgeek.skeletonapplication.ui.login;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.data.DataManager;
import com.evgeek.skeletonapplication.data.events.LoginEvent;
import com.evgeek.skeletonapplication.data.model.Token;
import com.evgeek.skeletonapplication.data.remote.CallbackWrapper;
import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.base.BasePresenter;
import com.evgeek.skeletonapplication.utils.Constants;

import java.util.regex.Matcher;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@PerView
public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {
    @Inject
    DataManager dataManager;

    @Inject
    public LoginPresenter() {
        super();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        getMvpView().setEmail(dataManager.getAuthEmail());
    }

    @Override
    protected void onUserLoggedIn(LoginEvent event) {
        if (getMvpView() != null) {
            getMvpView().closeSelf();
        }
    }

    @Override
    public void onEmailFieldFocusLost() {
        getValidEmail();
    }

    @Override
    public void onLoginClick() {
        if (dataManager.hasLoggedUser()) {
            getMvpView().closeSelf();
        }
        String email = getValidEmail();
        if (email == null)
            return;

        getMvpView().showLoading();
        dataManager.auth(email, getMvpView().getPassword()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CallbackWrapper<Token>(getMvpView()) {
                    @Override
                    public void onNext(@NonNull Token token) {
                        super.onNext(token);

                        dataManager.setAuthEmail(email);
                        dataManager.setUserToken(token.getJwt());
                        dataManager.getLoggedUser();
                        if (getMvpView() != null) {
                            getMvpView().closeSelf();
                        }
                    }

                    @Override
                    public void onHttpError(int code, ResponseBody body) {
                        if (!hasMvpView())
                            return;
                        switch (code) {
                            case 404:
                                getMvpView().setPasswordError(R.string.error_wrong_password);
                                break;
                            default:
                                super.onHttpError(code, body);
                        }
                    }

                    @Override
                    public void onUnknownError(Throwable e) {
                        super.onUnknownError(e);
                        if (!hasMvpView())
                            return;
                        getMvpView().setPasswordError(R.string.error_wrong_password);
                    }
                });
    }

    private String getValidEmail() {
        if (!hasMvpView())
            return null;
        getMvpView().setEmailError(null);
        String emailStr = getMvpView().getEmail();
        if (emailStr == null || emailStr.isEmpty()) {
            getMvpView().setEmailError(R.string.error_field_required);
            return null;
        }
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        if (!matcher.find()) {
            getMvpView().setEmailError(R.string.error_invalid_email);
            return null;
        }
        return emailStr;
    }
}
