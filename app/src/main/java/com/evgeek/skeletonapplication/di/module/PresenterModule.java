package com.evgeek.skeletonapplication.di.module;

import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.defaultFragment.DefaultMvpPresenter;
import com.evgeek.skeletonapplication.ui.defaultFragment.DefaultMvpView;
import com.evgeek.skeletonapplication.ui.defaultFragment.DefaultPresenter;
import com.evgeek.skeletonapplication.ui.login.LoginMvpPresenter;
import com.evgeek.skeletonapplication.ui.login.LoginMvpView;
import com.evgeek.skeletonapplication.ui.login.LoginPresenter;
import com.evgeek.skeletonapplication.ui.main.MainMvpPresenter;
import com.evgeek.skeletonapplication.ui.main.MainMvpView;
import com.evgeek.skeletonapplication.ui.main.MainPresenter;
import com.evgeek.skeletonapplication.ui.protectedView.ProtectedMvpPresenter;
import com.evgeek.skeletonapplication.ui.protectedView.ProtectedMvpView;
import com.evgeek.skeletonapplication.ui.protectedView.ProtectedPresenter;
import com.evgeek.skeletonapplication.ui.template.fragment.TemplateMvpPresenter;
import com.evgeek.skeletonapplication.ui.template.fragment.TemplateMvpView;
import com.evgeek.skeletonapplication.ui.template.fragment.TemplatePresenter;
import com.evgeek.skeletonapplication.ui.template.recyclerViewFragment.ExampleRVMvpPresenter;
import com.evgeek.skeletonapplication.ui.template.recyclerViewFragment.ExampleRVMvpView;
import com.evgeek.skeletonapplication.ui.template.recyclerViewFragment.ExampleRVPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PresenterModule {

    @Binds
    @PerView
    abstract MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter);

    @Binds
    @PerView
    abstract LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter);

    @Binds
    @PerView
    abstract com.evgeek.skeletonapplication.ui.template.activity.TemplateMvpPresenter
            <com.evgeek.skeletonapplication.ui.template.activity.TemplateMvpView>
    provideTemplateActivityPresenter(
            com.evgeek.skeletonapplication.ui.template.activity.TemplatePresenter
                    <com.evgeek.skeletonapplication.ui.template.activity.TemplateMvpView> presenter);

    @Binds
    @PerView
    abstract TemplateMvpPresenter<TemplateMvpView> provideTemplateFragmentPresenter(TemplatePresenter<TemplateMvpView> presenter);

    @Binds
    @PerView
    abstract ExampleRVMvpPresenter<ExampleRVMvpView> provideBoilerplatePresenter(ExampleRVPresenter<ExampleRVMvpView> presenter);

    @Binds
    @PerView
    abstract ProtectedMvpPresenter<ProtectedMvpView> provideProtectedFragmentPresenter(ProtectedPresenter<ProtectedMvpView> presenter);

    @Binds
    @PerView
    abstract DefaultMvpPresenter<DefaultMvpView> provideDefaultPresenter(DefaultPresenter<DefaultMvpView> presenter);

}
