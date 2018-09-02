package com.eric.mynews;

import com.eric.mynews.repositories.FakeWeatherRepoImpl;
import com.eric.mynews.repositories.WeatherRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    WeatherRepository provideWeatherRepository() {
        return new FakeWeatherRepoImpl();
    }
}
