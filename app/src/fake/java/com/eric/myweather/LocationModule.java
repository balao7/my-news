package com.eric.mynews;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {
    @Provides
    @Singleton
    MyLocationManager provideLocationManager(MyApp myApp) {
        return new FakeMyLocationManager();
    }
}
