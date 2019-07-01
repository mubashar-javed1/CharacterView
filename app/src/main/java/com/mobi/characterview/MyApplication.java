package com.mobi.characterview;

import android.app.Application;

import com.mobi.characterview.di.component.AppComponent;
import com.mobi.characterview.di.component.DaggerAppComponent;
import com.mobi.characterview.di.module.AppModule;
import com.mobi.characterview.di.module.RetrofitModule;

public class MyApplication extends Application {
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).retrofitModule(new RetrofitModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}