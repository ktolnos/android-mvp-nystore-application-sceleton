package com.evgeek.skeletonapplication.di.component;

import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.di.module.PresenterModule;
import com.evgeek.skeletonapplication.di.module.ViewModule;
import com.evgeek.skeletonapplication.ui.defaultFragment.DefaultFragment;
import com.evgeek.skeletonapplication.ui.login.LoginActivity;
import com.evgeek.skeletonapplication.ui.main.MainActivity;
import com.evgeek.skeletonapplication.ui.protectedView.ProtectedActivity;
import com.evgeek.skeletonapplication.ui.protectedView.ProtectedFragment;
import com.evgeek.skeletonapplication.ui.template.activity.TemplateActivity;
import com.evgeek.skeletonapplication.ui.template.fragment.TemplateFragment;
import com.evgeek.skeletonapplication.ui.template.recyclerViewFragment.ExampleRVFragment;

import dagger.Component;

@Component(modules = {ViewModule.class, PresenterModule.class}, dependencies = ApplicationComponent.class)
@PerView
public interface ViewComponent {

    void inject(MainActivity activity);

    void inject(TemplateActivity activity);

    void inject(ProtectedActivity activity);

    void inject(LoginActivity activity);

    void inject(TemplateFragment fragment);

    void inject(ExampleRVFragment fragment);

    void inject(ProtectedFragment fragment);

    void inject(DefaultFragment fragment);

}
