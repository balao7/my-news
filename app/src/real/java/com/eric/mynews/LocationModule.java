package com.eric.mynews;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {
    @Provides
    @Singleton
    MyLocationManager provideLocationManager(MyApp myApp) {
        return new MyLocationManager((LocationManager) myApp.getSystemService(Context.LOCATION_SERVICE));
    }
}
