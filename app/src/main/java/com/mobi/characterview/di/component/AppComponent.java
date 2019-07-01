package com.mobi.characterview.di.component;


import com.mobi.characterview.di.module.AppModule;
import com.mobi.characterview.di.module.RetrofitModule;
import com.mobi.characterview.ui.masterdetailactivity.MasterDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RetrofitModule.class})
@Singleton
public interface AppComponent {
    void injectMasterActivity(MasterDetailActivity masterDetailActivity);
}
