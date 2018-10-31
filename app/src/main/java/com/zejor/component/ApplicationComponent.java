package com.zejor.component;

import android.content.Context;

import com.zejor.App;
import com.zejor.contants.Api;
import com.zejor.module.HttpModule;
import com.zejor.module.ApplicationModule;

import dagger.Component;

@Component(modules = {ApplicationModule.class,HttpModule.class})
public interface ApplicationComponent {

    App getApplication();

    Context getContext();

    Api getApi();

}
