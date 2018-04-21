package com.evgeek.skeletonapplication.ui.main;

import com.evgeek.skeletonapplication.ui.base.MvpView;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Created by evgeek on 10/8/17.
 */

public interface MainMvpView extends MvpView {

    void setUserProfile(IProfile profile);

    void switchButtonToLogin();

    void switchButtonToLogout();

    void openLoginView();

    void openProtectedFragment();

    void openRVView();

    void openTemplateFragment();

    void openDefaultView();
}
