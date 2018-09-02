package com.eric.mynews;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class MyApp extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected AndroidInjector<? extends MyApp> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
