package com.evgeek.skeletonapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.amulyakhare.textdrawable.TextDrawable;
import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.ui.base.BaseFragmentActivity;
import com.evgeek.skeletonapplication.ui.defaultFragment.DefaultFragment;
import com.evgeek.skeletonapplication.ui.login.LoginActivity;
import com.evgeek.skeletonapplication.ui.protectedView.ProtectedFragment;
import com.evgeek.skeletonapplication.ui.template.fragment.TemplateFragment;
import com.evgeek.skeletonapplication.ui.template.recyclerViewFragment.ExampleRVFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import javax.inject.Inject;


public class MainActivity extends BaseFragmentActivity implements MainMvpView {

    private static final String SELECTED_DRAWER_ITEM = "SELECTED_DRAWER_ITEM";

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    Drawer mDrawer;
    AccountHeader mDrawerHeader;

    IDrawerItem loginProtectedItem;
    IDrawerItem recyclerViewItem;
    IDrawerItem templateItem;
    IDrawerItem defaultDrawerItem;
    PrimaryDrawerItem loginDrawerItem;
    boolean showsLogin = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewComponent().inject(this);
        setupDrawer(savedInstanceState);

        setPresenter(mPresenter);

        if (savedInstanceState != null) {
            int selectedDrawerItem = savedInstanceState.getInt(SELECTED_DRAWER_ITEM, -1);
            if (selectedDrawerItem != -1) {
                mDrawer.setSelection(selectedDrawerItem, true);
            } else {
                mDrawer.setSelection(defaultDrawerItem, true);
            }
        } else {
            mDrawer.setSelection(defaultDrawerItem, true);
        }
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleStringID() {
        return R.string.activity_main_title;
    }

    @Override
    public int getFragmentFrame() {
        return R.id.main_frame_layout;
    }

    protected void setupDrawer(Bundle savedInstanceState) {
        mDrawerHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withHeaderBackground(R.drawable.profile_background)
                .build();

        loginProtectedItem = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.login_protected_fragment);
        recyclerViewItem = new PrimaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.recycler_view_fragment);
        templateItem = new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName(R.string.template_fragment);
        defaultDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(5)
                .withName(R.string.default_fragment);
        loginDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(6)
                .withName(R.string.login)
                .withSelectable(false);

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withToolbar(getToolbar())
                .withAccountHeader(mDrawerHeader)
                .addDrawerItems(
                        loginProtectedItem,
                        new DividerDrawerItem(),
                        recyclerViewItem,
                        templateItem,
                        defaultDrawerItem,
                        new DividerDrawerItem(),
                        loginDrawerItem
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem.equals(loginProtectedItem)) {
                        mPresenter.onProtectedClick();
                    } else if (drawerItem.equals(recyclerViewItem)) {
                        mPresenter.onRVClick();
                    } else if (drawerItem.equals(templateItem)) {
                        mPresenter.onTemplateFragmentClick();
                    } else if (drawerItem.equals(defaultDrawerItem)) {
                        mPresenter.onDefaultClick();
                    } else if (drawerItem.equals(loginDrawerItem)) {
                        if (showsLogin) {
                            mPresenter.onLoginClick();
                        } else {
                            mPresenter.onLogoutClick();
                        }
                    }
                    return false;
                })
                .build();
    }

    @Override
    public void openDefaultFragment() {
        super.openDefaultFragment();
        openFragment(new DefaultFragment());
        mDrawer.setSelection(defaultDrawerItem, false);
    }

    @Override
    public void setUserProfile(IProfile profile) {
        if (profile != null) {
            mDrawerHeader.setProfiles(new ArrayList<>());
            if (profile.getIcon() == null) {
                TextDrawable avatarDefault = TextDrawable.builder()
                        .beginConfig()
                        .height(300)
                        .width(300)
                        .endConfig()
                        .buildRect(profile.getName().toString().substring(0, 1),
                                getResources().getColor(R.color.primary));
                profile.withIcon(avatarDefault);
            }
            mDrawerHeader.addProfile(profile, 0);
        } else {
            mDrawerHeader.removeProfile(0);
        }
    }

    @Override
    public void switchButtonToLogin() {
        loginDrawerItem.withName(R.string.login);
        mDrawer.updateItem(loginDrawerItem);
        showsLogin = true;
    }

    @Override
    public void switchButtonToLogout() {
        loginDrawerItem.withName(R.string.logout);
        mDrawer.updateItem(loginDrawerItem);
        showsLogin = false;
    }

    @Override
    public void openProtectedFragment() {
        openFragment(new ProtectedFragment());
        mDrawer.setSelection(loginProtectedItem, false);
    }

    @Override
    public void openRVView() {
        openFragment(new ExampleRVFragment());
        mDrawer.setSelection(recyclerViewItem, false);
    }

    @Override
    public void openDefaultView() {
        openDefaultFragment();
    }

    @Override
    public void openTemplateFragment() {
        openFragment(new TemplateFragment());
        mDrawer.setSelection(templateItem, false);
    }

    @Override
    public void openLoginView() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mPresenter.onDetach();
        mDrawer.saveInstanceState(outState);
        mDrawerHeader.saveInstanceState(outState);
        outState.putInt(SELECTED_DRAWER_ITEM, mDrawer.getCurrentSelectedPosition());
        super.onSaveInstanceState(outState);
    }
}