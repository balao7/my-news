package com.eric.mynews;

import org.greenrobot.greendao.AbstractDaoSession;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class MyApp extends DaggerApplication {
    @Inject AbstractDaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onTerminate() {
        daoSession.getDatabase()
                .close();
        super.onTerminate();
    }

    @Override
    protected AndroidInjector<? extends MyApp> applicationInjector() {
        return DaggerAppComponent.builder()
                .create(this);
    }
}
